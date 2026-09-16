<template>
  <div
    class="min-h-screen bg-zinc-900 text-white flex flex-col gap-4 md:gap-8 font-sans selection:bg-cyan-500 selection:text-zinc-900">
    <Header />

    <main class="flex-1 flex items-center flex-col gap-16 md:gap-24 lg:gap-28 px-6 py-12 lg:py-24">
      <!-- TÍTULO -->
      <section id="titulo" class="relative z-10">
        <div class="absolute bg-sky-200/60 rounded-full z-0 w-16 md:w-32 lg:w-64 h-12 blur-3xl"></div>

        <h1
          class="relative z-10 font-bold bg-gradient-to-r from-sky-200 to-cyan-600 bg-clip-text text-transparent text-2xl md:text-4xl lg:text-5xl hover:scale-105 duration-300">
          Relatórios
        </h1>
      </section>

      <!-- TOP CARDS -->
      <section id="topcards" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 w-full max-w-7xl">
        <!-- Clientes -->
        <div
          class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm hover:shadow-md duration-300">
          <h2 class="text-cyan-200 font-semibold text-2xl lg:text-3xl">
            {{ dados.length }}
          </h2>

          <p class="text-gray-400 text-sm">
            Clientes
          </p>
        </div>

        <!-- Ticket médio -->
        <div
          class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm hover:shadow-md duration-300">
          <h2 class="text-cyan-200 font-semibold text-2xl lg:text-3xl">
            R$ {{ ticketMedio }}
          </h2>

          <p class="text-gray-400 text-sm">
            Ticket médio
          </p>
        </div>

        <!-- Total arrecadado -->
        <div
          class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm hover:shadow-md duration-300">
          <h2 class="text-cyan-200 font-semibold text-2xl lg:text-3xl">
            R$ {{ totalVendas }}
          </h2>

          <p class="text-gray-400 text-sm">
            Total arrecadado
          </p>
        </div>

        <!-- Maior venda -->
        <div
          class="flex flex-col gap-3 rounded-xl px-6 py-8 border border-zinc-800 shadow-cyan-500/20 shadow-sm hover:shadow-md duration-300">
          <h2 class="text-cyan-200 font-semibold text-2xl lg:text-3xl">
            R$ {{ maiorVenda }}
          </h2>

          <p class="text-gray-400 text-sm">
            Maior venda registrada
          </p>
        </div>
      </section>

      <!-- RESUMO -->
      <section id="resumo" class="grid grid-cols-1 md:grid-cols-3 gap-6 w-full max-w-7xl">
        <!-- Menor venda -->
        <div class="rounded-xl border border-zinc-800 bg-zinc-900/50 p-6">
          <p class="text-sm text-zinc-400 mb-2">
            Menor venda registrada
          </p>

          <h2 class="text-2xl font-semibold text-white">
            R$ {{ menorVenda.toLocaleString("pt-BR") }}
          </h2>
        </div>

        <!-- Média de idade -->
        <div class="rounded-xl border border-zinc-800 bg-zinc-900/50 p-6">
          <p class="text-sm text-zinc-400 mb-2">
            Média de idade dos clientes
          </p>

          <h2 class="text-2xl font-semibold text-white">
            {{ mediaIdade.toFixed(1) }} anos
          </h2>
        </div>

        <!-- Maior segmento -->
        <div class="rounded-xl border border-zinc-800 bg-zinc-900/50 p-6">
          <p class="text-sm text-zinc-400 mb-2">
            Segmento com maior faturamento
          </p>

          <h2 class="text-2xl font-semibold text-cyan-200">
            {{ segmentoMaiorFaturamento[0] }}
          </h2>

          <p class="text-sm text-zinc-500 mt-1">
            R$ {{ segmentoMaiorFaturamento[1].toLocaleString("pt-BR") }}
          </p>
        </div>
      </section>

      <!-- RANKING -->
      <section id="ranking" class="w-full max-w-7xl">
        <div class="rounded-xl border border-zinc-800 overflow-hidden">
          <div class="p-6 border-b border-zinc-800">
            <h2 class="text-xl md:text-2xl font-semibold">
              Ranking de vendas
            </h2>

            <p class="text-sm text-zinc-400 mt-1">
              Clientes organizados pelo valor de vendas.
            </p>
          </div>

          <div class="overflow-x-auto">
            <table class="w-full text-sm">
              <thead class="bg-zinc-800">
                <tr>
                  <th class="px-6 py-4 text-left text-zinc-400">
                    #
                  </th>

                  <th class="px-6 py-4 text-left text-zinc-400">
                    Cliente
                  </th>

                  <th class="px-6 py-4 text-left text-zinc-400">
                    Cidade
                  </th>

                  <th class="px-6 py-4 text-left text-zinc-400">
                    Segmento
                  </th>

                  <th class="px-6 py-4 text-right text-zinc-400">
                    Vendas
                  </th>
                </tr>
              </thead>

              <tbody>
                <tr v-for="(cliente, index) in clientesOrdenados" :key="cliente.nome"
                  class="border-t border-zinc-800 hover:bg-zinc-800/50 duration-200">
                  <td class="px-6 py-4 text-zinc-500">
                    {{ index + 1 }}
                  </td>

                  <td class="px-6 py-4 font-medium">
                    {{ cliente.nome }}
                  </td>

                  <td class="px-6 py-4 text-zinc-400">
                    {{ cliente.cidade }}
                  </td>

                  <td class="px-6 py-4 text-zinc-400">
                    {{ cliente.segmento }}
                  </td>

                  <td class="px-6 py-4 text-right text-cyan-200 font-medium">
                    R$ {{ cliente.vendas.toLocaleString("pt-BR") }}
                  </td>
                </tr>
              </tbody>
            </table>
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
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

const dados = [
  {
    nome: "Bruno Silva",
    idade: 22,
    cidade: "Campinas",
    segmento: "Tecnologia",
    vendas: 4500
  },
  {
    nome: "Ana Oliveira",
    idade: 28,
    cidade: "São Paulo",
    segmento: "Comércio",
    vendas: 7200
  },
  {
    nome: "Carlos Santos",
    idade: 35,
    cidade: "Americana",
    segmento: "Indústria",
    vendas: 9800
  },
  {
    nome: "Mariana Souza",
    idade: 24,
    cidade: "Sumaré",
    segmento: "Serviços",
    vendas: 5300
  },
  {
    nome: "Lucas Ferreira",
    idade: 31,
    cidade: "Hortolândia",
    segmento: "Comércio",
    vendas: 6800
  },
  {
    nome: "Juliana Costa",
    idade: 27,
    cidade: "Campinas",
    segmento: "Serviços",
    vendas: 4100
  },
  {
    nome: "Rafael Almeida",
    idade: 40,
    cidade: "Valinhos",
    segmento: "Indústria",
    vendas: 12500
  },
  {
    nome: "Beatriz Martins",
    idade: 29,
    cidade: "Vinhedo",
    segmento: "Tecnologia",
    vendas: 8900
  },
  {
    nome: "Gabriel Rodrigues",
    idade: 33,
    cidade: "Paulínia",
    segmento: "Indústria",
    vendas: 11200
  },
  {
    nome: "Larissa Gomes",
    idade: 26,
    cidade: "Campinas",
    segmento: "Comércio",
    vendas: 6100
  }
]

const totalVendas = dados.reduce(
  (total, cliente) => total + cliente.vendas,
  0
)

const maiorVenda = Math.max(
  ...dados.map((cliente) => cliente.vendas)
)

const menorVenda = Math.min(
  ...dados.map((cliente) => cliente.vendas)
)

const mediaIdade =
  dados.reduce((total, cliente) => total + cliente.idade, 0) / dados.length

const clientesOrdenados = [...dados].sort(
  (a, b) => b.vendas - a.vendas
)

const ticketMedio = totalVendas / dados.length

const vendasPorSegmento = {}

dados.forEach((cliente) => {
  const segmento = cliente.segmento

  if (!vendasPorSegmento[segmento]) {
    vendasPorSegmento[segmento] = 0
  }

  vendasPorSegmento[segmento] += cliente.vendas
})

const segmentoMaiorFaturamento = Object.entries(vendasPorSegmento)
  .sort((a, b) => b[1] - a[1])[0]

</script>
