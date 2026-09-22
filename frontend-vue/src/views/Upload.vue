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
          <template v-if="!store.arquivo">
            <div class="text-5xl">
              ↑
            </div>

            <p class="text-sm md:text-lg text-center">
              Arraste sua planilha aqui
            </p>

            <p class="text-xs md:text-sm text-zinc-400">
              ou clique para escolher um arquivo
            </p>

            <span class="text-xs text-zinc-500">
              XLSX, XLS ou CSV
            </span>
          </template>

          <!-- COM ARQUIVO -->
          <template v-else>
            <div class="text-4xl">
              ✓
            </div>

            <p class="text-sm text-zinc-400">
              Arquivo selecionado
            </p>

            <p class="text-cyan-400 font-medium text-sm md:text-lg text-center break-all">
              {{ store.arquivo.name }}
            </p>

            <p class="text-xs text-zinc-500">
              Clique para escolher outro arquivo
            </p>
          </template>
        </label>

        <!-- ERRO -->
        <p v-if="store.erro" class="text-red-400 text-sm text-center">
          {{ store.erro }}
        </p>

        <!-- PROCESSAR -->
        <button type="button" @click="store.processarArquivo()" :disabled="!store.arquivo || store.processando"
          class="w-full md:w-auto cursor-pointer bg-gradient-to-b from-cyan-500 to-sky-200 text-zinc-900 border border-white px-8 py-3 rounded-lg font-medium hover:shadow-md duration-300 shadow-cyan-300 text-sm md:text-lg disabled:opacity-40 disabled:cursor-not-allowed">
          {{ store.processando ? 'Processando...' : 'Processar arquivo' }}
        </button>

        <!-- RESULTADO -->
        <div v-if="store.validacaoRealizada"
          class="w-full rounded-xl border border-zinc-800 bg-zinc-900/50 p-6 text-center">
          <p class="text-sm text-zinc-300">
            Arquivo processado e validado com sucesso!
          </p>

          <div class="flex flex-wrap justify-center gap-x-6 gap-y-2 mt-3 text-sm">
            <span class="text-cyan-400">
              {{ store.totalLinhas }} linhas
            </span>

            <span class="text-zinc-500">
              ·
            </span>

            <span class="text-cyan-400">
              {{ store.totalColunas }} colunas
            </span>

            <span class="text-zinc-500">
              ·
            </span>

            <span :class="store.totalErros > 0
              ? 'text-red-400'
              : 'text-green-400'
              ">
              {{ store.totalErros }}
              {{ store.totalErros === 1 ? 'erro encontrado' : 'erros encontrados' }}
            </span>
          </div>

          <!-- LINK/AVISO DO RELATÓRIO -->
          <p v-if="store.totalErros > 0" class="text-xs text-zinc-500 mt-4">
            Consulte a página de Relatório para visualizar os detalhes dos erros.
          </p>

          <p v-else class="text-xs text-green-400/70 mt-4">
            Nenhum problema foi encontrado na validação da planilha.
          </p>
        </div>
      </section>

      <!-- PRÉVIA DOS DADOS -->
      <section v-if="store.dadosTratados.length > 0" class="w-full max-w-6xl flex flex-col gap-4">
        <div>
          <h2 class="text-lg font-semibold text-white">
            Prévia dos dados
          </h2>

          <p class="text-sm text-zinc-500 mt-1">
            Exibindo os primeiros 10 registros processados da planilha.
          </p>
        </div>

        <div class="w-full overflow-x-auto rounded-xl border border-zinc-700">
          <table class="w-full min-w-max text-sm">

            <!-- CABEÇALHO -->
            <thead class="bg-zinc-800">
              <tr>
                <th class="px-4 py-3 text-left text-zinc-400 font-medium border-b border-zinc-700">
                  #
                </th>

                <th v-for="coluna in store.colunas" :key="coluna"
                  class="px-4 py-3 text-left text-cyan-400 font-medium border-b border-zinc-700">
                  {{ coluna }}
                </th>
              </tr>
            </thead>

            <!-- DADOS -->
            <tbody>
              <tr v-for="(linha, index) in store.dadosTratados.slice(0, 10)" :key="index"
                class="hover:bg-zinc-800/60 duration-200">
                <td class="px-4 py-3 text-zinc-500 border-b border-zinc-800">
                  {{ index + 1 }}
                </td>

                <td v-for="coluna in store.colunas" :key="coluna"
                  class="px-4 py-3 text-zinc-300 border-b border-zinc-800">
                  {{ linha[coluna] }}
                </td>
              </tr>
            </tbody>

          </table>
        </div>

        <!-- INFORMAÇÕES -->
        <div class="flex flex-wrap gap-4 text-sm text-zinc-400">
          <span>
            {{ store.totalLinhas }} linhas
          </span>

          <span>
            {{ store.totalColunas }} colunas
          </span>

          <span>
            {{ store.totalErros }}
            {{ store.totalErros === 1 ? 'erro' : 'erros' }}
          </span>
        </div>
      </section>

      <!-- SEM DADOS -->
      <section v-else-if="store.arquivo && !store.processando"
        class="w-full max-w-6xl rounded-xl border border-zinc-800 bg-zinc-900/50 p-6 text-center">
        <p class="text-sm text-zinc-500">
          Nenhum dado foi processado para exibição.
        </p>
      </section>
    </main>

    <footer>
      <Footer />
    </footer>
  </div>
</template>

<script setup>
import { useUploadStore } from '../stores/uploadStore.js'

import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

const store = useUploadStore()

function escolherArquivo(event) {
  const file = event.target.files?.[0]

  if (file) {
    store.selecionarArquivo(file)
  }
}
</script>