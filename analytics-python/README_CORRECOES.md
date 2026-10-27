# Analytics Python — versão alinhada ao schema atual

## O que mudou
- Removidas dependências de `upload_lote` e `estatistica_lote`.
- `POST /etl` continua aceitando `loteId` opcional para compatibilidade, mas não usa esse campo para gravar no banco.
- `/testar` continua sendo uma rota sem persistência.
- Clientes são inseridos ou atualizados usando `codigo_cliente` como chave lógica.
- Os serviços encontrados são inseridos no catálogo `servico` quando ainda não existem.
- As frases de `payload["insights"]` são registradas na tabela `insight`.
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

`loteId` pode ser omitido. O campo é retornado para compatibilidade, mas não existe controle de lote no banco.

## Atenção ao status
A limpeza atual aceita `status` na entrada, mas a persistência não sobrescreve `cliente.status`: o banco aplica o default `ATIVO` em novos registros. Se for necessário importar status Ativo/Inativo da planilha, a persistência deve ser adaptada ao enum `status_cliente` existente.
