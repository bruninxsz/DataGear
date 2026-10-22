<template>
  <div
    class="min-h-screen bg-zinc-900 text-white flex flex-col gap-4 md:gap-8 font-sans selection:bg-cyan-500 selection:text-zinc-900">
    <Header />

    <main class="flex-1 flex items-center flex-col gap-24 md:gap-32 lg:gap-36 px-6 py-12 lg:py-24">
      <!-- TÍTULO -->
      <section id="titulo" class="relative z-10">
        <div class="absolute bg-sky-200/60 rounded-full z-0 w-32 md:w-64 lg:w-112 h-12 blur-3xl"></div>

        <h1
          class="relative z-10 font-bold bg-gradient-to-r from-sky-200 to-cyan-600 bg-clip-text text-transparent text-2xl md:text-4xl lg:text-5xl hover:scale-105 duration-300">
          Envie a sua Planilha
        </h1>
      </section>

      <!-- ÁREA DE UPLOAD -->
      <section id="upload" class="flex flex-col items-center w-full max-w-3xl gap-6 lg:gap-12">
        <!-- INPUT ESCONDIDO -->
        <input ref="inputArquivo" type="file" id="arquivo" accept=".xlsx,.xls,.csv" class="hidden"
          @change="escolherArquivo" />

        <!-- DROPZONE -->
        <label for="arquivo"
          class="w-full min-h-72 flex flex-col items-center justify-center gap-4 border-2 border-dashed border-sky-200/60 rounded-xl p-8 cursor-pointer hover:border-cyan-400 hover:bg-sky-200/5 duration-300">
          <!-- SEM ARQUIVO -->
          <template v-if="!arquivo">
            <div class="text-5xl">↑</div>
            <p class="text-sm md:text-lg text-center">Arraste sua planilha aqui</p>
            <p class="text-xs md:text-sm text-zinc-400">ou clique para escolher um arquivo</p>
            <span class="text-xs text-zinc-500">XLSX, XLS ou CSV</span>
          </template>

          <!-- COM ARQUIVO -->
          <template v-else>
            <div class="text-4xl">✓</div>
            <p class="text-sm text-zinc-400">Arquivo selecionado</p>
            <p class="text-cyan-400 font-medium text-sm md:text-lg text-center break-all">
              {{ arquivo.name }}
            </p>
            <p class="text-xs text-zinc-500">Clique para escolher outro arquivo</p>
          </template>
        </label>

        <!-- ERRO DE ARQUIVO -->
        <p v-if="erro" class="text-red-400 text-sm text-center">
          Não foi possível enviar os dados.
        </p>

        <!-- PROCESSAR -->
        <button type="button" @click="processarEImportar" :disabled="!arquivo || processando || importando"
          class="w-full md:w-auto cursor-pointer bg-gradient-to-b from-cyan-500 to-sky-200 text-zinc-900 border border-white px-8 py-3 rounded-lg font-medium hover:shadow-md duration-300 shadow-cyan-300 text-sm md:text-lg disabled:opacity-40 disabled:cursor-not-allowed">
          {{ processando || importando ? 'Processando...' : 'Validar e importar' }}
        </button>

        <!-- RESULTADO SINTÉTICO DO PROCESSAMENTO -->
        <div v-if="validacaoRealizada"
          class="w-full rounded-xl border border-zinc-800 bg-zinc-900/50 p-6 text-center">
          <p class="text-sm text-zinc-300">
            Arquivo processado e validado com sucesso!
          </p>

          <div class="flex flex-wrap justify-center gap-x-6 gap-y-2 mt-3 text-sm">
            <span class="text-cyan-400">{{ totalLinhas }} linhas</span>
            <span class="text-zinc-500">·</span>
            <span class="text-cyan-400">{{ totalColunas }} colunas</span>
            <span class="text-zinc-500">·</span>
            <span :class="totalErros > 0 ? 'text-red-400' : 'text-green-400'">
              {{ totalErros }} {{ totalErros === 1 ? 'erro encontrado' : 'erros encontrados' }}
            </span>
          </div>

          <p v-if="totalErros > 0" class="text-xs text-zinc-400 mt-4">
            <br>
            Dados não enviados ao banco.<br>
            Consulte os detalhes abaixo para visualizar a lista completa de erros.
          </p>
          <p v-if="totalErros === 0 && sucessoImportacao" class="text-xs text-green-400/70 mt-4">
            Dados enviados ao banco.
            Nenhum problema foi encontrado na validação da planilha.
          </p>
        </div>
      </section>

      <!-- PRÉVIA DOS DADOS TRATADOS -->
      <section v-if="dadosTratados && dadosTratados.length > 0" class="w-full max-w-6xl flex flex-col gap-4">
        <div>
          <h2 class="text-lg font-semibold text-white">Prévia dos dados</h2>
          <p class="text-sm text-zinc-500 mt-1">
            Exibindo os primeiros 10 registros processados da planilha.
          </p>
        </div>

        <div class="w-full overflow-x-auto rounded-xl border border-zinc-700">
          <table class="w-full min-w-max text-sm">
            <thead class="bg-zinc-800">
              <tr>
                <th class="px-4 py-3 text-left text-zinc-400 font-medium border-b border-zinc-700">#</th>
                <th v-for="coluna in colunas" :key="coluna"
                  class="px-4 py-3 text-left text-cyan-400 font-medium border-b border-zinc-700">
                  {{ coluna }}
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(linha, index) in dadosTratados.slice(0, 10)" :key="index"
                class="hover:bg-zinc-800/60 duration-200">
                <td class="px-4 py-3 text-zinc-500 border-b border-zinc-800">{{ index + 1 }}</td>
                <td v-for="coluna in colunas" :key="coluna"
                  class="px-4 py-3 text-zinc-300 border-b border-zinc-800">
                  {{ linha[coluna] }}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>

      <!-- PAINEL DE RELATÓRIO E ERROS (Aparece se a validação foi realizada) -->
      <section v-if="validacaoRealizada" class="flex flex-col gap-12 w-full max-w-7xl">
        <!-- TOP CARDS -->
        <section id="topcards" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 w-full">
          <div class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm">
            <h2 class="text-cyan-200 font-semibold text-2xl lg:text-3xl">{{ totalLinhas }}</h2>
            <p class="text-gray-400 text-sm">Total de registros</p>
          </div>

          <div class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm">
            <h2 class="text-green-300 font-semibold text-2xl lg:text-3xl">{{ linhasValidas }}</h2>
            <p class="text-gray-400 text-sm">Registros válidos</p>
          </div>

          <div class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm">
            <h2 class="text-red-300 font-semibold text-2xl lg:text-3xl">{{ linhasComErro }}</h2>
            <p class="text-gray-400 text-sm">Registros com erro</p>
          </div>

          <div class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm">
            <h2 class="text-red-300 font-semibold text-2xl lg:text-3xl">{{ totalErros }}</h2>
            <p class="text-gray-400 text-sm">Erros encontrados</p>
          </div>
        </section>

        <!-- RESUMO DOS ERROS POR TIPO -->
        <section class="w-full">
          <div class="rounded-xl border border-zinc-800 overflow-hidden">
            <div class="p-6 border-b border-zinc-800">
              <h2 class="text-xl md:text-2xl font-semibold">Validações realizadas</h2>
              <p class="text-sm text-zinc-400 mt-1">Quantidade de problemas encontrados por tipo.</p>
            </div>

            <div v-if="errosPorTipo && Object.keys(errosPorTipo).length" class="p-6 space-y-4">
              <div v-for="(quantidade, tipo) in errosPorTipo" :key="tipo"
                class="flex items-center justify-between border-b border-zinc-800 pb-3">
                <span class="text-zinc-300">{{ tipo }}</span>
                <span class="text-red-300 font-semibold">{{ quantidade }}</span>
              </div>
            </div>

            <div v-else class="p-6">
              <p class="text-zinc-500">Nenhum erro por tipo registrado.</p>
            </div>
          </div>
        </section>

        <!-- DETALHAMENTO COMPLETO DOS ERROS -->
        <section class="w-full">
          <div class="rounded-xl border border-zinc-800 overflow-hidden">
            <div class="p-6 border-b border-zinc-800">
              <h2 class="text-xl md:text-2xl font-semibold">Detalhamento dos erros</h2>
              <p class="text-sm text-zinc-400 mt-1">Problemas encontrados durante a validação da planilha.</p>
            </div>

            <!-- TABELA DE ERROS -->
            <div v-if="erros && erros.length" class="overflow-x-auto">
              <table class="w-full text-sm">
                <thead class="bg-zinc-800">
                  <tr>
                    <th class="px-6 py-4 text-left text-zinc-400">Linha</th>
                    <th class="px-6 py-4 text-left text-zinc-400">Campo</th>
                    <th class="px-6 py-4 text-left text-zinc-400">Tipo</th>
                    <th class="px-6 py-4 text-left text-zinc-400">Descrição</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(erroItem, index) in erros" :key="index"
                    class="border-t border-zinc-800 hover:bg-zinc-800/50 duration-200">
                    <td class="px-6 py-4 text-zinc-400">{{ erroItem.linha }}</td>
                    <td class="px-6 py-4 text-white">{{ erroItem.campo }}</td>
                    <td class="px-6 py-4 text-red-300">{{ erroItem.tipo }}</td>
                    <td class="px-6 py-4 text-zinc-400">{{ erroItem.descricao }}</td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- MENSAGEM QUANDO NÃO HÁ ERROS -->
            <div v-else class="p-6">
              <p class="text-zinc-400">Nenhum erro foi encontrado na planilha.</p>
            </div>
          </div>
        </section>
      </section>
    </main>

    <footer>
      <Footer />
    </footer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { storeToRefs } from 'pinia'

import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import { useUploadStore } from '../stores/uploadStore'

const uploadStore = useUploadStore()

const sucessoImportacao = ref(false)

// Desestrutura todas as propriedades reativas do Pinia
const {
  arquivo,
  erro,
  processando,
  validacaoRealizada,
  totalLinhas,
  totalColunas,
  totalErros,
  linhasComErro,
  linhasValidas,
  errosPorTipo,
  erros,
  colunas,
  dadosTratados
} = storeToRefs(uploadStore)

const importando = ref(false)

function escolherArquivo(event) {
  const file = event.target.files?.[0]
  if (file) {
    uploadStore.selecionarArquivo(file)
  }
}

async function processarEImportar() {
  sucessoImportacao.value = false
  try {
    // 1. Executa a leitura e validação
    await uploadStore.processarArquivo()

    // 2. Se houver erros na planilha, encerra para exibir o relatório de erros
    if (uploadStore.totalErros > 0) {
      return
    }

    // 3. Se a planilha estiver 100% limpa, importa no banco de dados
    importando.value = true
    await uploadStore.enviarDadosValidos()

    sucessoImportacao.value = true
  } catch (erroOcorrido) {
    console.error('Erro ao processar/importar:', erroOcorrido)
    uploadStore.erro =
      erroOcorrido.response?.data?.message ||
      erroOcorrido.message ||
      'Ocorreu um erro ao processar a planilha.'
    
  } finally {
    importando.value = false
    sucessoImportacao.value = false
  }
}
</script>