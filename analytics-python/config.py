"""Configuração da conexão PostgreSQL via variáveis de ambiente."""
import os
from pathlib import Path

from dotenv import load_dotenv
from sqlalchemy.engine import URL

load_dotenv(Path(__file__).resolve().parent / ".env")


def database_url() -> URL:
    """Monta a URL do SQLAlchemy sem expor credenciais em logs."""
    obrigatorias = ("DB_USER", "DB_PASSWORD", "DB_HOST", "DB_NAME")
    faltando = [nome for nome in obrigatorias if not os.getenv(nome)]
    if faltando:
        raise RuntimeError(
            "Variáveis ausentes no .env: " + ", ".join(faltando)
        )

    return URL.create(
        "postgresql+psycopg",
        username=os.getenv("DB_USER"),
        password=os.getenv("DB_PASSWORD"),
        host=os.getenv("DB_HOST"),
        port=int(os.getenv("DB_PORT", "5432")),
        database=os.getenv("DB_NAME"),
        query={"sslmode": os.getenv("DB_SSLMODE", "require")},
    )
