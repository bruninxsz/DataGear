<template>
  <div
    class="min-h-screen bg-zinc-900 text-white flex flex-col gap-4 md:gap-8 font-sans selection:bg-cyan-500 selection:text-zinc-900"
  >
    <Header />

    <main
      class="flex-1 flex items-center flex-col gap-10 md:gap-14 lg:gap-16 px-6 py-12 lg:py-24"
    >
      <!-- TÍTULO -->
      <section id="titulo" class="relative z-10 text-center">
        <div
          class="absolute bg-sky-200/60 rounded-full z-0 w-32 md:w-64 lg:w-96 h-12 blur-3xl"
        ></div>

        <h1
          class="relative z-10 font-bold bg-gradient-to-r from-sky-200 to-cyan-600 bg-clip-text text-transparent text-2xl md:text-4xl lg:text-5xl hover:scale-105 duration-300"
        >
          Dashboard
        </h1>

        <p class="relative z-10 text-zinc-400 mt-3 text-sm md:text-base">
          Visão geral dos dados comerciais analisados.
        </p>
      </section>

      <!-- KPIs -->
      <section
        class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5 w-full max-w-7xl"
      >
        <!-- CLIENTES -->
        <div
          class="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6 shadow-sm shadow-cyan-500/10 hover:border-cyan-500/40 duration-300"
        >
          <p class="text-sm text-zinc-400">Total de clientes</p>
          <h2 class="text-3xl font-bold text-cyan-300 mt-2">
            {{ dados.length }}
          </h2>
        </div>

        <!-- CONTRATAÇÕES -->
        <div
          class="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6 shadow-sm shadow-cyan-500/10 hover:border-cyan-500/40 duration-300"
        >
          <p class="text-sm text-zinc-400">Total de contratações</p>
          <h2 class="text-3xl font-bold text-green-300 mt-2">
            {{ totalContratacoes }}
          </h2>
        </div>

        <!-- FATURAMENTO -->
        <div
          class="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6 shadow-sm shadow-cyan-500/10 hover:border-cyan-500/40 duration-300"
        >
          <p class="text-sm text-zinc-400">Faturamento total</p>
          <h2 class="text-2xl md:text-3xl font-bold text-sky-300 mt-2">
            {{ formatarMoeda(totalVendas) }}
          </h2>
        </div>

        <!-- TICKET MÉDIO -->
        <div
          class="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6 shadow-sm shadow-cyan-500/10 hover:border-cyan-500/40 duration-300"
        >
          <p class="text-sm text-zinc-400">Ticket médio</p>
          <h2 class="text-2xl md:text-3xl font-bold text-purple-300 mt-2">
            {{ formatarMoeda(ticketMedio) }}
          </h2>
        </div>
      </section>

      <!-- GRÁFICOS -->
      <section
        class="grid grid-cols-1 lg:grid-cols-2 gap-6 w-full max-w-7xl"
      >
        <!-- GRÁFICO 1 -->
        <div
          class="bg-zinc-800/70 border border-zinc-700 rounded-xl p-6 md:p-8 shadow-sm shadow-cyan-500/10"
        >
          <div class="mb-6">
            <h2 class="text-xl md:text-2xl font-semibold">
              Distribuição por segmento
            </h2>
            <p class="text-sm text-zinc-400 mt-1">
              Valor total de vendas por segmento.
            </p>
          </div>

          <div class="relative w-full h-80">
            <canvas ref="graficoSegmento"></canvas>
          </div>
        </div>

        <!-- GRÁFICO 2 -->
        <div
          class="bg-zinc-800/70 border border-zinc-700 rounded-xl p-6 md:p-8 shadow-sm shadow-cyan-500/10"
        >
          <div class="mb-6">
            <h2 class="text-xl md:text-2xl font-semibold">
              Distribuição por nível
            </h2>
            <p class="text-sm text-zinc-400 mt-1">
              Distribuição dos clientes entre os níveis A, B e C.
            </p>
          </div>

          <div class="relative w-full h-80 flex justify-center">
            <canvas ref="graficoNivel"></canvas>
          </div>
        </div>

        <!-- GRÁFICO 3 -->
        <div
          class="lg:col-span-2 bg-zinc-800/70 border border-zinc-700 rounded-xl p-6 md:p-8 shadow-sm shadow-cyan-500/10"
        >
          <div class="mb-6">
            <h2 class="text-xl md:text-2xl font-semibold">
              Evolução de contratações
            </h2>
            <p class="text-sm text-zinc-400 mt-1">
              Quantidade de novas contratações ao longo dos meses.
            </p>
          </div>

          <div class="relative w-full h-80 md:h-96">
            <canvas ref="graficoContratacoes"></canvas>
          </div>
        </div>
      </section>

      <!-- RESUMO -->
      <section class="w-full max-w-7xl">
        <div
          class="rounded-xl border border-zinc-800 bg-zinc-900/50 overflow-hidden"
        >
          <div class="p-6 border-b border-zinc-800">
            <h2 class="text-xl md:text-2xl font-semibold">
              Resumo comercial
            </h2>
            <p class="text-sm text-zinc-400 mt-1">
              Principais informações encontradas nos dados analisados.
            </p>
          </div>

          <div class="overflow-x-auto">
            <table class="w-full text-sm">
              <thead class="bg-zinc-800">
                <tr>
                  <th class="px-6 py-4 text-left text-zinc-400">Indicador</th>
                  <th class="px-6 py-4 text-left text-zinc-400">Resultado</th>
                </tr>
              </thead>
              <tbody>
                <tr class="border-t border-zinc-800">
                  <td class="px-6 py-4 text-zinc-300">Maior venda</td>
                  <td class="px-6 py-4 text-cyan-300 font-semibold">
                    {{ formatarMoeda(maiorVenda) }}
                  </td>
                </tr>
                <tr class="border-t border-zinc-800">
                  <td class="px-6 py-4 text-zinc-300">
                    Segmento com maior faturamento
                  </td>
                  <td class="px-6 py-4 text-cyan-300 font-semibold">
                    {{ maiorSegmento }}
                  </td>
                </tr>
                <tr class="border-t border-zinc-800">
                  <td class="px-6 py-4 text-zinc-300">Nível predominante</td>
                  <td class="px-6 py-4 text-cyan-300 font-semibold">
                    Nível {{ nivelPredominante }}
                  </td>
                </tr>
                <tr class="border-t border-zinc-800">
                  <td class="px-6 py-4 text-zinc-300">Total de segmentos</td>
                  <td class="px-6 py-4 text-cyan-300 font-semibold">
                    {{ Object.keys(vendasPorSegmento).length }}
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
import { ref, computed, onMounted, onBeforeUnmount } from "vue"

import Header from "../components/Header.vue"
import Footer from "../components/Footer.vue"

import {
  Chart,
  BarController,
  BarElement,
  DoughnutController,
  ArcElement,
  LineController,
  LineElement,
  PointElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
  Filler
} from "chart.js"

Chart.register(
  BarController,
  BarElement,
  DoughnutController,
  ArcElement,
  LineController,
  LineElement,
  PointElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
  Filler
)

/* ============================== */
/* REFERÊNCIAS DOS GRÁFICOS */
/* ============================== */

const graficoSegmento = ref(null)
const graficoNivel = ref(null)
const graficoContratacoes = ref(null)

let chartSegmento = null
let chartNivel = null
let chartContratacoes = null

/* ============================== */
/* DADOS */
/* ============================== */

const dados = ref([
  {
    nome: "Bruno Silva",
    idade: 22,
    cidade: "Campinas",
    segmento: "Tecnologia",
    nivel: "A",
    vendas: 4500,
    contratacao: "2026-01"
  },
  {
    nome: "Ana Oliveira",
    idade: 28,
    cidade: "São Paulo",
    segmento: "Comércio",
    nivel: "B",
    vendas: 7200,
    contratacao: "2026-01"
  },
  {
    nome: "Carlos Santos",
    idade: 35,
    cidade: "Americana",
    segmento: "Indústria",
    nivel: "A",
    vendas: 9800,
    contratacao: "2026-02"
  },
  {
    nome: "Mariana Souza",
    idade: 24,
    cidade: "Sumaré",
    segmento: "Serviços",
    nivel: "C",
    vendas: 5300,
    contratacao: "2026-02"
  },
  {
    nome: "Lucas Ferreira",
    idade: 31,
    cidade: "Hortolândia",
    segmento: "Comércio",
    nivel: "B",
    vendas: 6800,
    contratacao: "2026-03"
  },
  {
    nome: "Juliana Costa",
    idade: 27,
    cidade: "Campinas",
    segmento: "Serviços",
    nivel: "C",
    vendas: 4100,
    contratacao: "2026-03"
  },
  {
    nome: "Rafael Almeida",
    idade: 40,
    cidade: "Valinhos",
    segmento: "Indústria",
    nivel: "A",
    vendas: 12500,
    contratacao: "2026-04"
  },
  {
    nome: "Beatriz Martins",
    idade: 29,
    cidade: "Vinhedo",
    segmento: "Tecnologia",
    nivel: "A",
    vendas: 8900,
    contratacao: "2026-05"
  },
  {
    nome: "Gabriel Rodrigues",
    idade: 33,
    cidade: "Paulínia",
    segmento: "Indústria",
    nivel: "B",
    vendas: 11200,
    contratacao: "2026-05"
  },
  {
    nome: "Larissa Gomes",
    idade: 26,
    cidade: "Campinas",
    segmento: "Comércio",
    nivel: "C",
    vendas: 6100,
    contratacao: "2026-06"
  }
])

/* ============================== */
/* INDICADORES (COMPUTED) */
/* ============================== */

const maiorVenda = computed(() =>
  Math.max(...dados.value.map((cliente) => cliente.vendas))
)

const totalVendas = computed(() =>
  dados.value.reduce((total, cliente) => total + cliente.vendas, 0)
)

const ticketMedio = computed(() =>
  dados.value.length ? totalVendas.value / dados.value.length : 0
)

const totalContratacoes = computed(() => dados.value.length)

/* ============================== */
/* AGREGAÇÕES (COMPUTED) */
/* ============================== */

const vendasPorSegmento = computed(() => {
  const acc = {}
  dados.value.forEach((cliente) => {
    acc[cliente.segmento] = (acc[cliente.segmento] || 0) + cliente.vendas
  })
  return acc
})

const clientesPorNivel = computed(() => {
  const acc = { A: 0, B: 0, C: 0 }
  dados.value.forEach((cliente) => {
    if (acc[cliente.nivel] !== undefined) acc[cliente.nivel]++
  })
  return acc
})

const contratacoesPorMes = computed(() => {
  const acc = {}
  dados.value.forEach((cliente) => {
    acc[cliente.contratacao] = (acc[cliente.contratacao] || 0) + 1
  })
  return acc
})

const maiorSegmento = computed(() => {
  const entries = Object.entries(vendasPorSegmento.value)
  return entries.sort((a, b) => b[1] - a[1])[0]?.[0] || "-"
})

const nivelPredominante = computed(() => {
  const entries = Object.entries(clientesPorNivel.value)
  return entries.sort((a, b) => b[1] - a[1])[0]?.[0] || "-"
})

/* ============================== */
/* FORMATAÇÃO */
/* ============================== */

function formatarMoeda(valor) {
  return (valor || 0).toLocaleString("pt-BR", {
    style: "currency",
    currency: "BRL"
  })
}

/* ============================== */
/* GRÁFICOS */
/* ============================== */

onMounted(() => {
  /* GRÁFICO DE SEGMENTOS */
  if (graficoSegmento.value) {
    chartSegmento = new Chart(graficoSegmento.value, {
      type: "bar",
      data: {
        labels: Object.keys(vendasPorSegmento.value),
        datasets: [
          {
            label: "Vendas",
            data: Object.values(vendasPorSegmento.value),
            backgroundColor: ["#06b6d4", "#3b82f6", "#8b5cf6", "#f59e0b"],
            borderColor: ["#22d3ee", "#60a5fa", "#a78bfa", "#fbbf24"],
            borderWidth: 1,
            borderRadius: 6
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { labels: { color: "#ffffff" } },
          tooltip: {
            callbacks: {
              label: (context) => ` ${formatarMoeda(context.raw)}`
            }
          }
        },
        scales: {
          x: {
            ticks: { color: "#ffffff" },
            grid: { color: "rgba(255,255,255,0.08)" }
          },
          y: {
            ticks: {
              color: "#ffffff",
              callback: (value) => formatarMoeda(value)
            },
            grid: { color: "rgba(255,255,255,0.08)" }
          }
        }
      }
    })
  }

  /* GRÁFICO DE NÍVEL */
  if (graficoNivel.value) {
    chartNivel = new Chart(graficoNivel.value, {
      type: "doughnut",
      data: {
        labels: ["Nível A", "Nível B", "Nível C"],
        datasets: [
          {
            data: [
              clientesPorNivel.value.A,
              clientesPorNivel.value.B,
              clientesPorNivel.value.C
            ],
            backgroundColor: ["#06b6d4", "#8b5cf6", "#f59e0b"],
            borderColor: "#27272a",
            borderWidth: 3,
            hoverOffset: 8
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        cutout: "55%",
        plugins: {
          legend: {
            position: "bottom",
            labels: { color: "#ffffff", padding: 20, font: { size: 14 } }
          },
          tooltip: {
            callbacks: {
              label: (context) => {
                const valor = context.raw
                const total = context.dataset.data.reduce((a, b) => a + b, 0)
                const percentual = total ? ((valor / total) * 100).toFixed(1) : 0
                return ` ${context.label}: ${valor} (${percentual}%)`
              }
            }
          }
        }
      }
    })
  }

  /* GRÁFICO DE CONTRATAÇÕES */
  if (graficoContratacoes.value) {
    chartContratacoes = new Chart(graficoContratacoes.value, {
      type: "line",
      data: {
        labels: Object.keys(contratacoesPorMes.value),
        datasets: [
          {
            label: "Contratações",
            data: Object.values(contratacoesPorMes.value),
            borderColor: "#22d3ee",
            backgroundColor: "rgba(34, 211, 238, 0.15)",
            borderWidth: 3,
            tension: 0.3,
            fill: true,
            pointRadius: 5,
            pointHoverRadius: 8,
            pointBackgroundColor: "#22d3ee",
            pointBorderColor: "#ffffff",
            pointBorderWidth: 2
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { labels: { color: "#ffffff" } },
          tooltip: {
            callbacks: {
              label: (context) => ` ${context.raw} contratações`
            }
          }
        },
        scales: {
          x: {
            ticks: { color: "#ffffff" },
            grid: { color: "rgba(255,255,255,0.08)" }
          },
          y: {
            beginAtZero: true,
            ticks: { color: "#ffffff", precision: 0 },
            grid: { color: "rgba(255,255,255,0.08)" }
          }
        }
      }
    })
  }
})

/* ============================== */
/* LIMPEZA */
/* ============================== */

onBeforeUnmount(() => {
  chartSegmento?.destroy()
  chartNivel?.destroy()
  chartContratacoes?.destroy()
})
</script>