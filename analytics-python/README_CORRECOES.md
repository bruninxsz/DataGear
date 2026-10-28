# Analytics Python — versão alinhada ao schema atual

## O que mudou
- Removidas dependências de `upload_lote` e `estatistica_lote`.
- `POST /etl` continua aceitando `loteId` opcional para compatibilidade, mas não usa esse campo para gravar no banco.
- `/testar` continua sendo uma rota sem persistência.
- Clientes são inseridos ou atualizados usando `codigo_cliente` como chave lógica.
- Os serviços normalizados ficam em `cliente.servicos_contratados`; a planilha não fornece a categoria obrigatória do catálogo `servico`.
- As frases de `payload["insights"]` são registradas na tabela `insight`.
- `POST /etl` devolve as linhas rejeitadas e seus motivos em `resultado.qualidade.rejeitadas`.
- `GET /dashboard` relê clientes persistidos, aplica o tratamento Python e devolve indicadores, séries, top clientes e insights.
- O teste de conexão não consulta tabelas de lotes.

## Importante sobre contratos
A planilha não fornece um valor próprio de contrato, enquanto `contrato.valor` é obrigatório (`NOT NULL`). Por isso, esta versão **não cria linhas em `contrato`** e não inventa valores. Clientes e serviços são salvos, e a lista de serviços fica em `cliente.servicos_contratados`. Para gerar contratos reais, será necessário definir de onde vem `valor` e como calcular `data_fim`/`status`.

## Como usar
1. Copie `.env.example` para `.env` e informe as credenciais reais.
2. Instale as dependências: `pip install -r requirements.txt`.
3. Teste a conexão: `python testar_conexao.py`.
4. Inicie a API: `uvicorn api:app --reload --port 8000`.
5. Acesse `http://127.0.0.1:8000/docs`.

## Formato de POST /etl
```json
{
  "loteId": 123,
  "recebidas": 2,
  "linhas": [
    {
      "codigoCliente": "CTI001",
      "nomeCliente": "Metalúrgica Horizonte",
      "consultor": "Ana Souza",
      "segmento": "IND.",
      "nivelCliente": "A",
      "faturamentoAnual": 1850000,
      "servicosContratados": "Internet Dedicada;Firewall",
      "status": "Ativo",
      "dataContratacao": "2025-01-15",
      "cidade": "Campinas",
      "uf": "SP"
    }
  ]
}
```

`loteId` pode ser omitido. O campo é retornado para compatibilidade, mas não existe controle de lote no banco. O backend Java encaminha o payload ao Python, e o Python persiste os resultados do ETL.

## Atenção ao status
A limpeza valida e normaliza `status` para Ativo/Inativo. A persistência converte esses valores para o enum `status_cliente` (`ATIVO`/`ENCERRADO`) do banco.
