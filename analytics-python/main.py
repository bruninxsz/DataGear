"""Orquestra a limpeza, a análise e a persistência do ETL."""
from __future__ import annotations

import logging
from typing import Any

import pandas as pd

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

    if resultado.tratado.empty:
        raise ValueError("Nenhum cliente passou pelas validações do ETL.")

    total_recebidas = len(linhas) if recebidas is None else recebidas
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
