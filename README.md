# 🚀 DataGear CTI

> Sistema web inteligente para higienização de dados, análise de carteira e geração de insights estratégicos a partir de planilhas operacionais da CTI Comunicação de Dados e Tecnologia LTDA.

---

## 📌 Sobre o Projeto

A **Provedor CTI** mantinha sua base de clientes centralizada em planilhas Excel gerenciadas manualmente. Esse modelo gerava gargalos como inconsistências de digitação (ex: *"IND."*, *"Industria"*, *"INDUSTRIA"* para o mesmo segmento), campos em branco e falta de visibilidade sobre o perfil da carteira.

O **DataGear CTI** foi desenvolvido para resolver esse problema, transformando arquivos brutos `.xlsx`/`.csv` em um ecossistema web moderno. O sistema realiza o tratamento automático dos dados (ETL) e apresenta métricas estratégicas em tempo real através de dashboards interativos.

---

## ✨ Funcionalidades Principais

* 🧹 **Higienização Automática de Dados (ETL):** Padronização de textos, remoção de acentuação/caixa mista e tratamento de duplicidades na importação.
* 📊 **Dashboards Dinâmicos:** Visualização instantânea de métricas e gráficos sobre a base de clientes:
  * Concentração por segmento de atuação (Indústria, Comércio, Serviços).
  * Distribuição da carteira por nível de cliente (A, B e C).
  * Serviços mais contratados e adesão de pacotes.
  * Performance e distribuição da carteira por consultor.
* 🔍 **Filtros Avançados:** Segmentação de dados por faixa de faturamento, consultor, nível ou serviço contratado.
* 📝 **Gestão de Clientes (CRUD):** Interface para cadastro e edição manual com campos padronizados para evitar novas inconsistências.
* 📄 **Exportação de Relatórios:** Geração de relatórios consolidados para reuniões e tomadas de decisão.

---

## 🛠️ Tecnologias Utilizadas

**Front-end:**
* **Vue.js 3** (Composition API)
* **Tailwind CSS** (Estilização responsiva e UI tecnológica)
* **Vue Router** (Navegação SPA)

**Back-end & Banco de Dados (Sugerido/Implementado):**
* **Node.js / Python** (Módulo de tratamento de dados e API REST)
* **PostgreSQL / MySQL** (Armazenamento relacional dos dados higienizados)

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

Antes de começar, você precisará ter instalado em sua máquina:
* [Node.js](https://nodejs.org/) (versão 18 ou superior)
* [Git](https://git-scm.com/)

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/datagear-cti.git](https://github.com/seu-usuario/datagear-cti.git)
