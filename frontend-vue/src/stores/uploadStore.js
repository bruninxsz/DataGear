import { defineStore } from 'pinia'
import * as XLSX from 'xlsx'

export const useUploadStore = defineStore('upload', {
  state: () => ({
    arquivo: null,
    dadosOriginais: [],
    dadosTratados: [],
    erro: '',
    processando: false
  }),

  getters: {
    totalLinhas: (state) => state.dadosTratados.length,

    totalColunas: (state) => {
      if (!state.dadosTratados.length) return 0
      return Object.keys(state.dadosTratados[0]).length
    },

    colunas: (state) => {
      if (!state.dadosTratados.length) return []
      return Object.keys(state.dadosTratados[0])
    }
  },

  actions: {
    // Apenas recebe o arquivo
    selecionarArquivo(file) {
      this.erro = ''
      this.dadosOriginais = []
      this.dadosTratados = []

      if (!file) {
        this.arquivo = null
        return
      }

      const extensao = file.name.split('.').pop()?.toLowerCase()

      if (!['xlsx', 'xls', 'csv'].includes(extensao)) {
        this.arquivo = null
        this.erro = 'Formato inválido. Use XLSX, XLS ou CSV.'
        return
      }

      this.arquivo = file
    },

    // Processa o arquivo somente quando o botão for clicado
    async processarArquivo() {
      if (!this.arquivo) {
        this.erro = 'Selecione um arquivo antes de processar.'
        return
      }

      this.processando = true
      this.erro = ''

      try {
        const buffer = await this.arquivo.arrayBuffer()

        const workbook = XLSX.read(buffer, {
          type: 'array'
        })

        const nomePrimeiraAba = workbook.SheetNames[0]
        const worksheet = workbook.Sheets[nomePrimeiraAba]

        this.dadosOriginais = XLSX.utils.sheet_to_json(worksheet, {
          defval: ''
        })

        this.tratarDados()

      } catch (error) {
        console.error(error)
        this.erro = 'Não foi possível processar a planilha.'
        this.dadosOriginais = []
        this.dadosTratados = []

      } finally {
        this.processando = false
      }
    },

    tratarDados() {
      this.dadosTratados = this.dadosOriginais.map((linha) => {
        const novaLinha = {}

        for (const [chave, valor] of Object.entries(linha)) {
          if (typeof valor === 'string') {
            novaLinha[chave] = valor.trim()
          } else {
            novaLinha[chave] = valor
          }
        }

        // Tratamento do segmento
        if (typeof novaLinha.segmento === 'string') {
          const segmento = novaLinha.segmento
            .trim()
            .toUpperCase()

          const mapaSegmentos = {
            'IND.': 'Indústria',
            'INDUSTRIA': 'Indústria',
            'INDÚSTRIA': 'Indústria',
            'COMERCIO': 'Comércio',
            'COMÉRCIO': 'Comércio',
            'SERVICOS': 'Serviços',
            'SERVIÇOS': 'Serviços'
          }

          novaLinha.segmento =
            mapaSegmentos[segmento] || novaLinha.segmento
        }

        // Tratamento do nível do cliente
        if (typeof novaLinha.nivel_cliente === 'string') {
          novaLinha.nivel_cliente =
            novaLinha.nivel_cliente.toUpperCase()
        }

        return novaLinha
      })
    },

    limpar() {
      this.arquivo = null
      this.dadosOriginais = []
      this.dadosTratados = []
      this.erro = ''
      this.processando = false
    }
  }
})