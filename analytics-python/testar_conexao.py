"""Testa a conexão com o PostgreSQL configurado no .env."""
from sqlalchemy import text
from banco import criar_engine

try:
    with criar_engine().connect() as conexao:
        versao = conexao.execute(text("SELECT version()")).scalar()
        banco = conexao.execute(text("SELECT current_database()")).scalar()
        print("Conectou com sucesso!")
        print("Banco:", banco)
        print("PostgreSQL:", versao)
        tabelas = conexao.execute(text("""
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = 'public'
              AND table_name IN (
                'cliente', 'consultor', 'contrato',
                'servico', 'insight', 'telemetria'
              )
            ORDER BY table_name
        """)).scalars().all()
        print("Tabelas encontradas:", ", ".join(tabelas) or "(nenhuma)")
except Exception as erro:
    print("Não foi possível conectar. Confira o .env, o firewall e o SSL da Azure.")
    print("Detalhe:", str(erro).splitlines()[0])
