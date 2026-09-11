import { defineStore } from 'pinia'
import * as XLSX from 'xlsx'

export const useUploadStore = defineStore('upload', {
  // STATE: arquivo e dados lidos da planilha.
  state: () => ({
    arquivo: null,
    dadosOriginais: [],
    dadosTratados: [],
    erro: ''
  }),

  // GETTERS: informações derivadas do state.
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

  // ACTIONS: leitura, tratamento e limpeza.
  actions: {
    async lerArquivo(file) {
      this.erro = ''
      this.arquivo = file
      this.dadosOriginais = []
      this.dadosTratados = []

      if (!file) return

      const extensao = file.name.split('.').pop()?.toLowerCase()

      if (!['xlsx', 'xls', 'csv'].includes(extensao)) {
        this.erro = 'Formato inválido. Use XLSX, XLS ou CSV.'
        return
      }

      try {
        const buffer = await file.arrayBuffer()
        const workbook = XLSX.read(buffer, { type: 'array' })

        const nomePrimeiraAba = workbook.SheetNames[0]
        const worksheet = workbook.Sheets[nomePrimeiraAba]

        this.dadosOriginais = XLSX.utils.sheet_to_json(worksheet, {
          defval: ''
        })

        this.tratarDados()
      } catch (error) {
        console.error(error)
        this.erro = 'Não foi possível ler a planilha.'
      }
    },

    tratarDados() {
      // Tratamento leve apenas para aula de Front-end.
      // A Ciência de Dados completa ficará em Python/Pandas.
      this.dadosTratados = this.dadosOriginais.map((linha) => {
        const novaLinha = {}

        for (const [chave, valor] of Object.entries(linha)) {
          if (typeof valor === 'string') {
            novaLinha[chave] = valor.trim()
          } else {
            novaLinha[chave] = valor
          }
        }

        if (typeof novaLinha.segmento === 'string') {
          const segmento = novaLinha.segmento.trim().toUpperCase()

          const mapaSegmentos = {
            'IND.': 'Indústria',
            'INDUSTRIA': 'Indústria',
            'INDÚSTRIA': 'Indústria',
            'COMERCIO': 'Comércio',
            'COMÉRCIO': 'Comércio',
            'SERVICOS': 'Serviços',
            'SERVIÇOS': 'Serviços'
          }

          novaLinha.segmento = mapaSegmentos[segmento] || novaLinha.segmento
        }

        if (typeof novaLinha.nivel_cliente === 'string') {
          novaLinha.nivel_cliente = novaLinha.nivel_cliente.toUpperCase()
        }

        return novaLinha
      })
    },

    limpar() {
      this.arquivo = null
      this.dadosOriginais = []
      this.dadosTratados = []
      this.erro = ''
    }

    // FUTURO:
    // async enviarParaBackend() {
    //   Aqui entrará Axios para enviar a planilha/dados ao Spring Boot.
    // }
  }
})
