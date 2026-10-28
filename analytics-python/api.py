"""API FastAPI para integração do Spring Boot com o ETL Python."""
import logging
import os
from typing import Any

import pandas as pd
from fastapi import FastAPI, Header, HTTPException
from pydantic import BaseModel, Field

import config  # carrega o .env
from analise import calcular
from limpeza import tratar
from main import obter_dashboard, processar_lote

logging.basicConfig(level=os.getenv("LOG_LEVEL", "INFO"))
log = logging.getLogger("analytics")

app = FastAPI(title="CTI Insights — ETL em Python")
_engine = None


class Pedido(BaseModel):
    # loteId é aceito por compatibilidade com o Spring, mas não é usado no banco.
    loteId: int | None = None
    recebidas: int | None = Field(default=None, ge=0)
    linhas: list[dict[str, Any]]


def _engine_azure():
    global _engine
    if _engine is None:
        import banco
        _engine = banco.criar_engine()
    return _engine


@app.get("/saude")
def saude() -> dict:
    return {"status": "ok"}


@app.post("/etl")
def etl(
    pedido: Pedido,
    x_etl_key: str | None = Header(default=None),
) -> dict:
    chave = os.getenv("ETL_API_KEY", "").strip()
    if chave and x_etl_key != chave:
        raise HTTPException(status_code=401, detail="Chave do ETL inválida.")
    if not pedido.linhas:
        raise HTTPException(
            status_code=422,
            detail="Nenhuma linha foi enviada para o ETL.",
        )

    try:
        payload = processar_lote(
            engine=_engine_azure(),
            linhas=pedido.linhas,
            recebidas=pedido.recebidas,
        )
        return {"ok": True, "loteId": pedido.loteId, "resultado": payload}
    except ValueError as erro:
        raise HTTPException(status_code=422, detail=str(erro)) from erro
    except Exception as erro:
        log.exception("Falha durante o ETL")
        raise HTTPException(
            status_code=500,
            detail="Falha no ETL. Confira o log do serviço Python.",
        ) from erro


@app.get("/dashboard")
def dashboard(
    x_etl_key: str | None = Header(default=None),
) -> dict:
    chave = os.getenv("ETL_API_KEY", "").strip()
    if chave and x_etl_key != chave:
        raise HTTPException(status_code=401, detail="Chave do ETL inválida.")

    try:
        return obter_dashboard(_engine_azure())
    except ValueError as erro:
        raise HTTPException(status_code=422, detail=str(erro)) from erro
    except Exception as erro:
        log.exception("Falha ao gerar dashboard")
        raise HTTPException(
            status_code=500,
            detail="Falha ao gerar o dashboard. Confira o log do serviço Python.",
        ) from erro


@app.post("/testar")
def testar(linhas: list[dict[str, Any]]) -> dict:
    """Executa limpeza e análise sem conectar nem gravar no banco."""
    if not linhas:
        raise HTTPException(status_code=422, detail="Envie pelo menos uma linha.")
    resultado = tratar(pd.DataFrame(linhas).astype(object))
    if resultado.tratado.empty:
        raise HTTPException(
            status_code=422,
            detail="Nenhuma linha passou pelas validações.",
        )
    return calcular(resultado, len(linhas))
