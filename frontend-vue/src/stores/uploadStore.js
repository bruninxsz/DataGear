import { defineStore } from "pinia";
import * as XLSX from "xlsx";

export const useUploadStore = defineStore("upload", {
  state: () => ({
    arquivo: null,
    dadosOriginais: [],
    dadosTratados: [],
    erros: [],
    erro: "",
    processando: false,
    validacaoRealizada: false,
  }),

  getters: {
    totalLinhas: (state) => {
      return state.dadosTratados.length;
    },

    totalColunas: (state) => {
      if (state.dadosTratados.length === 0) {
        return 0;
      }

      return Object.keys(state.dadosTratados[0]).length;
    },

    colunas: (state) => {
      if (state.dadosTratados.length === 0) {
        return [];
      }

      return Object.keys(state.dadosTratados[0]);
    },

    totalErros: (state) => {
      return state.erros.length;
    },

    linhasComErro: (state) => {
      return new Set(state.erros.map((erro) => erro.linha)).size;
    },

    linhasValidas: (state) => {
      return (
        state.dadosTratados.length -
        new Set(state.erros.map((erro) => erro.linha)).size
      );
    },

    errosPorTipo: (state) => {
      const resultado = {};

      state.erros.forEach((erro) => {
        if (!resultado[erro.tipo]) {
          resultado[erro.tipo] = 0;
        }

        resultado[erro.tipo]++;
      });

      return resultado;
    },
  },

  actions: {
    // Apenas recebe o arquivo
    selecionarArquivo(file) {
      this.erro = "";
      this.dadosOriginais = [];
      this.dadosTratados = [];

      if (!file) {
        this.arquivo = null;
        return;
      }

      const extensao = file.name.split(".").pop()?.toLowerCase();

      if (!["xlsx", "xls", "csv"].includes(extensao)) {
        this.arquivo = null;
        this.erro = "Formato inválido. Use XLSX, XLS ou CSV.";
        return;
      }

      this.arquivo = file;
    },

    // Processa o arquivo somente quando o botão for clicado
    async processarArquivo() {
      if (!this.arquivo) {
        this.erro = "Selecione um arquivo antes de processar.";
        return;
      }

      this.processando = true;
      this.erro = "";

      try {
        const buffer = await this.arquivo.arrayBuffer();

        const workbook = XLSX.read(buffer, {
          type: "array",
        });

        const nomeAba = workbook.SheetNames.includes("upload_clientes")
          ? "upload_clientes"
          : workbook.SheetNames[0];

        const worksheet = workbook.Sheets[nomeAba];

        this.dadosOriginais = XLSX.utils.sheet_to_json(worksheet, {
          defval: "",
        });

        this.tratarDados();
        this.validarDados();
      } catch (error) {
        console.error(error);
        this.erro = "Não foi possível processar a planilha.";
        this.dadosOriginais = [];
        this.dadosTratados = [];
      } finally {
        this.processando = false;
      }
    },

    tratarDados() {
      this.dadosTratados = this.dadosOriginais.map((linha) => {
        const novaLinha = {};

        for (const [chave, valor] of Object.entries(linha)) {
          if (typeof valor === "string") {
            novaLinha[chave] = valor.trim();
          } else {
            novaLinha[chave] = valor;
          }
        }

        if (typeof novaLinha.segmento === "string") {
          const segmento = novaLinha.segmento.trim().toUpperCase();

          const mapaSegmentos = {
            "IND.": "Indústria",
            INDUSTRIA: "Indústria",
            INDÚSTRIA: "Indústria",
            COMERCIO: "Comércio",
            COMÉRCIO: "Comércio",
            SERVICOS: "Serviços",
            SERVIÇOS: "Serviços",
          };

          novaLinha.segmento = mapaSegmentos[segmento] || novaLinha.segmento;
        }

        if (typeof novaLinha.nivel_cliente === "string") {
          novaLinha.nivel_cliente = novaLinha.nivel_cliente.toUpperCase();
        }

        return novaLinha;
      });
    },

    limpar() {
      this.arquivo = null;
      this.dadosOriginais = [];
      this.dadosTratados = [];
      this.erros = [];
      this.erro = "";
      this.processando = false;
      this.validacaoRealizada = false;
    },
    validarDados() {
      this.erros = [];

      const camposObrigatorios = [
        "codigo_cliente",
        "nome_cliente",
        "consultor",
        "segmento",
        "nivel_cliente",
        "faturamento_anual",
        "servicos_contratados",
        "data_contratacao",
        "cidade",
        "uf",
      ];

      const codigos = new Map();

      this.dadosTratados.forEach((linha, index) => {
        const numeroLinha = index + 2;

        // =========================
        // CAMPOS OBRIGATÓRIOS
        // =========================

        camposObrigatorios.forEach((campo) => {
          const valor = linha[campo];

          if (
            valor === undefined ||
            valor === null ||
            String(valor).trim() === ""
          ) {
            this.erros.push({
              linha: numeroLinha,
              campo,
              tipo: "Campo obrigatório",
              descricao: `O campo "${campo}" está vazio.`,
            });
          }
        });

        // =========================
        // CÓDIGO DUPLICADO
        // =========================

        const codigo = linha.codigo_cliente;

        if (codigo !== undefined && codigo !== "") {
          if (codigos.has(codigo)) {
            this.erros.push({
              linha: numeroLinha,
              campo: "codigo_cliente",
              tipo: "Registro duplicado",
              descricao: `O código do cliente "${codigo}" já foi utilizado na linha ${codigos.get(codigo)}.`,
            });
          } else {
            codigos.set(codigo, numeroLinha);
          }
        }

        // =========================
        // NÍVEL DO CLIENTE
        // =========================

        if (linha.nivel_cliente) {
          const nivel = String(linha.nivel_cliente).toUpperCase();

          if (!["A", "B", "C"].includes(nivel)) {
            this.erros.push({
              linha: numeroLinha,
              campo: "nivel_cliente",
              tipo: "Dado fora do padrão",
              descricao: "O nível do cliente deve ser A, B ou C.",
            });
          }
        }

        // =========================
        // UF
        // =========================

        if (linha.uf) {
          const uf = String(linha.uf).trim().toUpperCase();

          if (!/^[A-Z]{2}$/.test(uf)) {
            this.erros.push({
              linha: numeroLinha,
              campo: "uf",
              tipo: "Dado fora do padrão",
              descricao: "A UF deve possuir exatamente duas letras.",
            });
          }
        }

        // =========================
        // FATURAMENTO
        // =========================

        if (
          linha.faturamento_anual !== undefined &&
          linha.faturamento_anual !== ""
        ) {
          const faturamento = Number(linha.faturamento_anual);

          if (isNaN(faturamento) || faturamento < 0) {
            this.erros.push({
              linha: numeroLinha,
              campo: "faturamento_anual",
              tipo: "Dado fora do padrão",
              descricao:
                "O faturamento anual deve ser um número válido e não negativo.",
            });
          }
        }

        // =========================
        // DATA DE CONTRATAÇÃO
        // =========================

        if (linha.data_contratacao) {
          const data = new Date(linha.data_contratacao);

          if (isNaN(data.getTime())) {
            this.erros.push({
              linha: numeroLinha,
              campo: "data_contratacao",
              tipo: "Dado fora do padrão",
              descricao: "A data de contratação não possui um formato válido.",
            });
          }
        }

        // =========================
        // SEGMENTO
        // =========================

        if (linha.segmento) {
          const segmentosValidos = ["Indústria", "Comércio", "Serviços"];

          if (!segmentosValidos.includes(linha.segmento)) {
            this.erros.push({
              linha: numeroLinha,
              campo: "segmento",
              tipo: "Dado fora do padrão",
              descricao: `Segmento "${linha.segmento}" não reconhecido.`,
            });
          }
        }
      });

      this.validacaoRealizada = true;
    },
  },
});
