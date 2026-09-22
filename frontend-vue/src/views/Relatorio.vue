<template>
  <div
    class="min-h-screen bg-zinc-900 text-white flex flex-col gap-4 md:gap-8 font-sans selection:bg-cyan-500 selection:text-zinc-900"
  >
    <Header />

    <main
      class="flex-1 flex items-center flex-col gap-16 md:gap-24 lg:gap-28 px-6 py-12 lg:py-24"
    >
      <!-- TÍTULO -->
      <section id="titulo" class="relative z-10">
        <div
          class="absolute bg-sky-200/60 rounded-full z-0 w-64 md:w-100 lg:w-134 h-6 lg:h-12 blur-3xl"
        ></div>

        <h1
          class="relative z-10 font-bold bg-gradient-to-r from-sky-200 to-cyan-600 bg-clip-text text-transparent text-2xl md:text-4xl lg:text-5xl hover:scale-105 duration-300"
        >
          Relatório de Validação
        </h1>
      </section>

      <!-- ARQUIVO ANALISADO -->
      <section class="w-full max-w-7xl">
        <div
          class="rounded-xl border border-zinc-800 bg-zinc-900/50 p-6 shadow-cyan-500/10 shadow-sm"
        >
          <p class="text-sm text-zinc-400 mb-2">
            Arquivo analisado
          </p>

          <h2 class="text-lg md:text-xl font-semibold text-white">
            {{ nomeArquivo || 'Nenhum arquivo analisado' }}
          </h2>

          <p
            v-if="!validacaoRealizada"
            class="text-sm text-zinc-500 mt-2"
          >
            Faça o upload e processe uma planilha para visualizar o relatório.
          </p>

          <p
            v-else
            class="text-sm text-zinc-500 mt-2"
          >
            Validação concluída.
          </p>
        </div>
      </section>

      <!-- TOP CARDS -->
      <section
        id="topcards"
        class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 w-full max-w-7xl"
      >
        <!-- Total de registros -->
        <div
          class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm hover:shadow-md duration-300"
        >
          <h2
            class="text-cyan-200 font-semibold text-2xl lg:text-3xl"
          >
            {{ totalLinhas }}
          </h2>

          <p class="text-gray-400 text-sm">
            Total de registros
          </p>
        </div>

        <!-- Registros válidos -->
        <div
          class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm hover:shadow-md duration-300"
        >
          <h2
            class="text-cyan-200 font-semibold text-2xl lg:text-3xl"
          >
            {{ linhasValidas }}
          </h2>

          <p class="text-gray-400 text-sm">
            Registros válidos
          </p>
        </div>

        <!-- Registros com erro -->
        <div
          class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm hover:shadow-md duration-300"
        >
          <h2
            class="text-cyan-200 font-semibold text-2xl lg:text-3xl"
          >
            {{ linhasComErro }}
          </h2>

          <p class="text-gray-400 text-sm">
            Registros com erro
          </p>
        </div>

        <!-- Total de erros -->
        <div
          class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm hover:shadow-md duration-300"
        >
          <h2
            class="text-cyan-200 font-semibold text-2xl lg:text-3xl"
          >
            {{ totalErros }}
          </h2>

          <p class="text-gray-400 text-sm">
            Erros encontrados
          </p>
        </div>
      </section>

      <!-- RESUMO DOS ERROS -->
      <section
        id="resumo"
        class="grid grid-cols-1 md:grid-cols-2 gap-6 w-full max-w-7xl"
      >
        <!-- Quantidade de colunas -->
        <div
          class="rounded-xl border border-zinc-800 bg-zinc-900/50 p-6"
        >
          <p class="text-sm text-zinc-400 mb-2">
            Colunas analisadas
          </p>

          <h2 class="text-2xl font-semibold text-white">
            {{ totalColunas }}
          </h2>
        </div>

        <!-- Tipos de erro -->
        <div
          class="rounded-xl border border-zinc-800 bg-zinc-900/50 p-6"
        >
          <p class="text-sm text-zinc-400 mb-2">
            Tipos de problemas encontrados
          </p>

          <h2 class="text-2xl font-semibold text-cyan-200">
            {{ Object.keys(errosPorTipo).length }}
          </h2>
        </div>
      </section>

      <!-- VALIDAÇÕES REALIZADAS -->
      <section class="w-full max-w-7xl">
        <div
          class="rounded-xl border border-zinc-800 overflow-hidden"
        >
          <div class="p-6 border-b border-zinc-800">
            <h2 class="text-xl md:text-2xl font-semibold">
              Validações realizadas
            </h2>

            <p class="text-sm text-zinc-400 mt-1">
              Quantidade de problemas encontrados por tipo.
            </p>
          </div>

          <div
            v-if="Object.keys(errosPorTipo).length"
            class="p-6 space-y-4"
          >
            <div
              v-for="(quantidade, tipo) in errosPorTipo"
              :key="tipo"
              class="flex items-center justify-between border-b border-zinc-800 pb-3"
            >
              <span class="text-zinc-300">
                {{ tipo }}
              </span>

              <span
                class="text-cyan-200 font-semibold"
              >
                {{ quantidade }}
              </span>
            </div>
          </div>

          <div
            v-else
            class="p-6"
          >
            <p class="text-zinc-500">
              Nenhum erro encontrado.
            </p>
          </div>
        </div>
      </section>

      <!-- DETALHAMENTO DOS ERROS -->
      <section class="w-full max-w-7xl">
        <div
          class="rounded-xl border border-zinc-800 overflow-hidden"
        >
          <div class="p-6 border-b border-zinc-800">
            <h2 class="text-xl md:text-2xl font-semibold">
              Detalhamento dos erros
            </h2>

            <p class="text-sm text-zinc-400 mt-1">
              Problemas encontrados durante a validação da planilha.
            </p>
          </div>

          <!-- TABELA -->
          <div
            v-if="erros.length"
            class="overflow-x-auto"
          >
            <table class="w-full text-sm">
              <thead class="bg-zinc-800">
                <tr>
                  <th class="px-6 py-4 text-left text-zinc-400">
                    Linha
                  </th>

                  <th class="px-6 py-4 text-left text-zinc-400">
                    Campo
                  </th>

                  <th class="px-6 py-4 text-left text-zinc-400">
                    Tipo
                  </th>

                  <th class="px-6 py-4 text-left text-zinc-400">
                    Descrição
                  </th>
                </tr>
              </thead>

              <tbody>
                <tr
                  v-for="(erro, index) in erros"
                  :key="index"
                  class="border-t border-zinc-800 hover:bg-zinc-800/50 duration-200"
                >
                  <td class="px-6 py-4 text-zinc-400">
                    {{ erro.linha }}
                  </td>

                  <td class="px-6 py-4 text-white">
                    {{ erro.campo }}
                  </td>

                  <td class="px-6 py-4 text-cyan-200">
                    {{ erro.tipo }}
                  </td>

                  <td class="px-6 py-4 text-zinc-400">
                    {{ erro.descricao }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- SEM ERROS -->
          <div
            v-else
            class="p-6"
          >
            <p
              v-if="validacaoRealizada"
              class="text-zinc-400"
            >
              Nenhum erro foi encontrado na planilha.
            </p>

            <p
              v-else
              class="text-zinc-500"
            >
              Nenhuma validação foi realizada ainda.
            </p>
          </div>
        </div>
      </section>
    </main>

    <footer>
      <Footer />
    </footer>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { storeToRefs } from 'pinia'

import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

import { useUploadStore } from '../stores/uploadStore'

const uploadStore = useUploadStore()

const {
  totalLinhas,
  totalColunas,
  totalErros,
  linhasComErro,
  linhasValidas,
  errosPorTipo,
  erros,
  validacaoRealizada
} = storeToRefs(uploadStore)

const nomeArquivo = computed(() => {
  return uploadStore.arquivo?.name || ''
})
</script>