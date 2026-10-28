"""Orquestra a limpeza, a análise e a persistência do ETL."""
from __future__ import annotations

import logging
from typing import Any

import pandas as pd
from sqlalchemy import text

from analise import calcular
from limpeza import tratar

log = logging.getLogger("analytics")


def processar_lote(
    engine,
    linhas: list[dict[str, Any]],
    recebidas: int | None = None,
) -> dict:
    """Processa as linhas e persiste clientes/insights, sem tabela de lotes."""
    if not linhas:
        raise ValueError("O backend não enviou linhas para processamento.")

    bruto = pd.DataFrame(linhas).astype(object)
    resultado = tratar(bruto)

    total_recebidas = len(linhas) if recebidas is None else recebidas
    if resultado.tratado.empty:
        return {
            "resumo": {"totalClientes": 0, "faturamentoTotal": 0, "media": 0},
            "porSegmento": [],
            "porNivel": [],
            "porMes": [],
            "insights": [],
            "qualidade": {
                "linhasRecebidas": total_recebidas,
                "linhasValidas": 0,
                "linhasInvalidas": total_recebidas,
                "rejeitadas": resultado.rejeitadas.to_dict(orient="records"),
            },
        }

    payload = calcular(resultado, total_recebidas)

    import banco
    banco.gravar_resultado(
        engine=engine,
        tratado=resultado.tratado,
        payload=payload,
    )

    log.info(
        "ETL concluído: %s clientes gravados; %s linhas rejeitadas na limpeza.",
        len(resultado.tratado),
        len(resultado.rejeitadas),
    )
    return payload


def obter_dashboard(engine) -> dict:
    """Recalcula o dashboard usando os clientes salvos no banco pelo ETL."""
    bruto = pd.read_sql(
        text("""
            SELECT codigo_cliente, nome_cliente, consultor, segmento, nivel_cliente,
                   faturamento_anual, servicos_contratados, status,
                   data_contratacao, cidade, uf
            FROM public.cliente
            ORDER BY id_cliente
        """),
        engine,
    ).astype(object)

    if bruto.empty:
        raise ValueError("Ainda não há clientes importados para gerar o dashboard.")

    resultado = tratar(bruto)
    if resultado.tratado.empty:
        raise ValueError("Não há clientes válidos para gerar o dashboard.")

    return calcular(resultado, len(bruto))
