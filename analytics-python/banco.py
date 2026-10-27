"""Persistência do ETL nas tabelas existentes do PostgreSQL."""
from __future__ import annotations

import logging
from datetime import date
from typing import Any

import pandas as pd
from sqlalchemy import create_engine, text
from sqlalchemy.engine import Engine

from config import database_url

log = logging.getLogger("analytics")


def criar_engine() -> Engine:
    return create_engine(database_url(), pool_pre_ping=True)


def _texto(valor: Any, limite: int | None = None) -> str | None:
    if valor is None or pd.isna(valor):
        return None
    resultado = str(valor).strip()
    if not resultado:
        return None
    return resultado[:limite] if limite else resultado


def _data(valor: Any) -> date | None:
    if valor is None or pd.isna(valor):
        return None
    return pd.Timestamp(valor).date()


def gravar_resultado(
    engine: Engine,
    tratado: pd.DataFrame,
    payload: dict,
) -> None:
    """
    Faz upsert de clientes por codigo_cliente e registra os insights como linhas.
    Não usa upload_lote/estatistica_lote.

    Contratos não são criados aqui: a planilha não informa o valor do contrato,
    e contrato.valor é NOT NULL. Usar faturamento anual como valor do contrato
    inventaria um dado de negócio.
    """
    clientes_inseridos = 0
    clientes_atualizados = 0
    servicos_processados: set[str] = set()

    with engine.begin() as conn:
        for linha in tratado.itertuples(index=False):
            codigo = _texto(getattr(linha, "codigo_cliente", None), 120)
            nome = _texto(getattr(linha, "nome_cliente", None), 255)
            consultor_nome = _texto(getattr(linha, "consultor", None), 255)
            segmento = _texto(getattr(linha, "segmento", None), 120)
            nivel = _texto(getattr(linha, "nivel_cliente", None), 1)
            faturamento = float(linha.faturamento_anual)
            cidade = _texto(getattr(linha, "cidade", None), 120)
            uf = _texto(getattr(linha, "uf", None), 2)
            data_contratacao = _data(getattr(linha, "data_contratacao", None))
            servicos = getattr(linha, "servicos", [])
            servicos = [str(s).strip() for s in (servicos or []) if str(s).strip()]
            servicos_texto = ";".join(dict.fromkeys(servicos)) or None

            # O código do cliente é usado como chave lógica. A consulta evita
            # depender de uma constraint UNIQUE que não foi confirmada no schema.
            cliente_id = None
            if codigo:
                cliente_id = conn.execute(
                    text("SELECT id_cliente FROM public.cliente "
                         "WHERE codigo_cliente = :codigo ORDER BY id_cliente LIMIT 1"),
                    {"codigo": codigo},
                ).scalar_one_or_none()

            parametros = {
                "codigo": codigo,
                "nome": nome,
                "segmento": segmento,
                "fat": round(faturamento, 2),
                "nivel": nivel,
                "cidade": cidade,
                "uf": uf,
                "consultor": consultor_nome,
                "servicos": servicos_texto,
                "data": data_contratacao,
            }

            if cliente_id is not None:
                conn.execute(text("""
                    UPDATE public.cliente
                    SET nome_cliente = :nome,
                        segmento = :segmento,
                        faturamento_anual = :fat,
                        nivel_cliente = :nivel,
                        cidade = :cidade,
                        uf = :uf,
                        consultor = :consultor,
                        servicos_contratados = :servicos,
                        data_contratacao = :data
                    WHERE id_cliente = :id
                """), {**parametros, "id": cliente_id})
                clientes_atualizados += 1
            else:
                cliente_id = conn.execute(text("""
                    INSERT INTO public.cliente (
                        codigo_cliente, nome_cliente, segmento, faturamento_anual,
                        nivel_cliente, cidade, uf, consultor,
                        servicos_contratados, data_contratacao
                    )
                    VALUES (
                        :codigo, :nome, :segmento, :fat,
                        :nivel, :cidade, :uf, :consultor,
                        :servicos, :data
                    )
                    RETURNING id_cliente
                """), parametros).scalar_one()
                clientes_inseridos += 1

            # Mantém o catálogo de serviços sincronizado sem presumir IDs.
            for nome_servico in servicos:
                servicos_processados.add(nome_servico)
                conn.execute(text("""
                    INSERT INTO public.servico (nome)
                    SELECT :nome
                    WHERE NOT EXISTS (
                        SELECT 1 FROM public.servico WHERE lower(nome) = lower(:nome)
                    )
                """), {"nome": nome_servico[:255]})

        # A tabela insight aceita registros textuais, não um JSON completo.
        # Registramos cada frase gerada como um insight do ETL.
        insights = payload.get("insights", [])
        for descricao in insights:
            descricao = str(descricao).strip()
            if not descricao:
                continue
            conn.execute(text("""
                INSERT INTO public.insight (tipo, descricao, data_geracao)
                VALUES (:tipo, :descricao, CURRENT_DATE)
            """), {"tipo": "ETL", "descricao": descricao[:255]})

    log.info(
        "ETL gravado. Clientes novos: %s; atualizados: %s; serviços vistos: %s; insights: %s",
        clientes_inseridos, clientes_atualizados, len(servicos_processados),
        len(payload.get("insights", [])),
    )
