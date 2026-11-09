# DataGear CTI

**Transforme planilhas operacionais em uma visão clara da carteira de clientes.**

O DataGear CTI é uma aplicação web para importar, higienizar e analisar dados de clientes. A solução reúne uma interface em Vue, uma API Java, um serviço analítico em Python e persistência em PostgreSQL. Após a importação, o dashboard consulta os clientes salvos e apresenta indicadores, gráficos e insights calculados sobre esses dados.

## Visão geral

```text
Planilha XLSX / XLS / CSV
          │
          ▼
Frontend Vue ──► API Spring Boot ──► API FastAPI / ETL Python ──► PostgreSQL
     ▲                                      │                         │
     └──────── Dashboard ◄─────────────────┴──── consulta e análise ─┘
```

O Java recebe as requisições da aplicação e integra o frontend ao serviço analítico. O Python padroniza e valida as linhas, grava clientes novos ou atualizados no PostgreSQL e calcula os dados exibidos no dashboard. A API Python também oferece documentação interativa em `/docs`.

## O que o sistema oferece

### Importação e qualidade dos dados

- Leitura de planilhas `.xlsx`, `.xls` e `.csv` no navegador.
- Prévia dos registros e validação de campos obrigatórios antes do envio.
- Relatório de problemas encontrados, incluindo linhas rejeitadas pelo ETL.
- Padronização de nomes, consultores, segmentos, níveis, status, serviços, faturamento e datas.
- Identificação de códigos duplicados durante o processamento.
- Download da planilha modelo diretamente na tela de upload.
- Persistência por código do cliente: registros existentes são atualizados e novos registros são inseridos.

### Dashboard analítico

Os indicadores são calculados a partir dos clientes consultados no banco de dados:

- Total de clientes, faturamento total e médio e serviço mais contratado.
- Gráficos de faturamento por segmento, clientes por nível e contratações por mês.
- Resumo estatístico com mediana, desvio padrão, quartis, valores mínimo e máximo.
- Insights sobre a carteira, serviços, consultores e evolução das contratações.
- Lista dos cinco clientes com maior faturamento anual.
- Data de geração da análise e ação para atualizar os dados.

### Gestão e acesso

- Interface de login e cadastro.
- Área de clientes com listagem e ação para encerrar um cliente.
- Rotas de upload, clientes e dashboard protegidas pela verificação de sessão da aplicação.

## Tecnologias

| Camada | Tecnologias |
|---|---|
| Frontend | Vue 3, Vue Router, Pinia, Vite, Tailwind CSS, Axios, Chart.js e SheetJS |
| API da aplicação | Java 17, Spring Boot, Spring Web MVC, Spring Data JPA, Bean Validation e Spring Security |
| ETL e analytics | Python, FastAPI, pandas, NumPy, SciPy e SQLAlchemy |
| Banco de dados | PostgreSQL |

## Estrutura do projeto

```text
frontend-vue/   Interface web e dashboard
backend-java/   API REST, persistência JPA e integração com o serviço Python
analytics-python/ ETL, análise estatística e acesso ao PostgreSQL
```

## Arquitetura e deploy

A aplicação é organizada em três componentes integrados: frontend Vue, API Java e serviço analítico Python, com PostgreSQL como repositório relacional. Essa separação permite publicar cada componente em um ambiente compatível com sua tecnologia e manter a comunicação entre eles por APIs HTTP.

No deploy, o frontend deve apontar para a API Java; a API Java encaminha importações e consultas analíticas ao serviço Python; e o serviço Python acessa o PostgreSQL. Endereços de serviços, credenciais e demais dados específicos do ambiente de produção pertencem à configuração segura da plataforma de hospedagem e não devem ser registrados neste README nem versionados junto ao código.

O projeto inclui `npm run build` para gerar os arquivos de produção do frontend e o Maven Wrapper (`mvnw` / `mvnw.cmd`) para o backend Java. O serviço Python é uma API FastAPI e possui dependências declaradas em `analytics-python/requirements.txt`.

## Fluxo de dados

1. O usuário seleciona a planilha, confere a prévia e inicia a validação/importação.
2. O frontend envia os registros para `POST /api/cliente/importar` no backend Java.
3. O Java encaminha o lote ao `POST /etl` do serviço Python.
4. O ETL padroniza e valida as linhas e grava os clientes válidos no PostgreSQL. O retorno inclui o resumo de qualidade e as linhas rejeitadas.
5. O dashboard solicita `GET /api/cliente/dashboard`; o Java consulta o serviço Python, que lê os clientes persistidos e calcula os indicadores e gráficos.

## Endpoints principais

| Serviço | Endpoint | Uso |
|---|---|---|
| Java | `POST /api/cliente/importar` | Encaminha registros para processamento e gravação pelo ETL |
| Java | `GET /api/cliente/dashboard` | Retorna indicadores e séries para o dashboard |
| Java | `GET /api/cliente/listar` | Lista clientes |
| Java | `PATCH /api/cliente/{id}/encerrar` | Encerra um cliente |
| Python | `GET /saude` | Verifica se a API está ativa |
| Python | `POST /etl` | Processa e persiste um lote de clientes |
| Python | `GET /dashboard` | Lê os clientes salvos e calcula a análise |
| Python | `POST /testar` | Executa limpeza e análise sem persistir no banco |

## Observações sobre os dados

- O ETL usa `codigo_cliente` como chave lógica para atualizar ou inserir clientes.
- O dashboard é recalculado com base nos registros persistidos no PostgreSQL.
- A importação não cria registros na tabela `contrato`: a planilha modelo não fornece valor, data final e status necessários para formar um contrato real.
- A API Java está configurada com `spring.jpa.hibernate.ddl-auto=validate`; o Hibernate valida o mapeamento em relação ao schema existente, sem criá-lo ou alterá-lo automaticamente.

