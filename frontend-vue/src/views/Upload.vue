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
        <div v-if="store.dadosTratados.length" class="text-center text-sm text-zinc-300">
          <p>
            Arquivo processado com sucesso!
          </p>

          <p class="text-cyan-400 mt-1">
            {{ store.totalLinhas }} linhas ·
            {{ store.totalColunas }} colunas
          </p>
        </div>
      </section>
      <!-- PRÉVIA DOS DADOS -->
      <section v-if="store.dadosTratados.length" class="w-full max-w-6xl flex flex-col gap-4">
        <div>
          <h2 class="text-xl md:text-2xl font-semibold">
            Prévia dos dados
          </h2>

          <p class="text-sm text-zinc-400 mt-1">
            Visualização das primeiras {{ Math.min(store.dadosTratados.length, 10) }}
            linhas do arquivo.
          </p>
        </div>

        <!-- TABELA -->
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
        </div>
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