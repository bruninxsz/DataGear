<template>
  <div class="min-h-screen bg-zinc-900 text-white flex flex-col gap-8 font-sans">
    <Header />

    <main class="flex-1 flex flex-col items-center gap-10 px-6 py-12 lg:py-20">
      <section class="relative z-10 text-center">
        <div class="absolute left-1/2 -translate-x-1/2 bg-sky-200/50 rounded-full w-64 h-12 blur-3xl"></div>
        <h1 class="relative font-bold bg-gradient-to-r from-sky-200 to-cyan-500 bg-clip-text text-transparent text-3xl md:text-5xl">
          Dashboard
        </h1>
        <p class="relative text-zinc-400 mt-3">Indicadores e insights calculados pelo serviço Python.</p>
      </section>

      <div v-if="carregando" class="text-cyan-300 animate-pulse" role="status">
        Carregando a análise...
      </div>

      <section v-else-if="erro" class="w-full max-w-3xl rounded-xl border border-red-800 bg-red-950/40 p-6 text-center">
        <p class="text-red-300">{{ erro }}</p>
        <button class="mt-4 rounded-lg bg-zinc-800 px-4 py-2 hover:bg-zinc-700" @click="carregarDashboard">
          Tentar novamente
        </button>
      </section>

      <template v-else>
        <section class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-5 w-full max-w-7xl">
          <article class="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6">
            <p class="text-sm text-zinc-400">Clientes analisados</p>
            <p class="mt-2 text-3xl font-bold text-cyan-300">{{ resumo.totalClientes || 0 }}</p>
          </article>
          <article class="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6">
            <p class="text-sm text-zinc-400">Faturamento total</p>
            <p class="mt-2 text-2xl font-bold text-sky-300">{{ formatarMoeda(resumo.faturamentoTotal) }}</p>
          </article>
          <article class="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6">
            <p class="text-sm text-zinc-400">Faturamento medio por cliente</p>
            <p class="mt-2 text-2xl font-bold text-purple-300">{{ formatarMoeda(resumo.media) }}</p>
          </article>
          <article class="rounded-xl border border-zinc-800 bg-zinc-900/60 p-6">
            <p class="text-sm text-zinc-400">Servico mais contratado</p>
            <p class="mt-2 text-xl font-bold text-green-300">{{ resumo.servicoMaisContratado || '—' }}</p>
          </article>
        </section>

        <section v-if="insights.length" class="w-full max-w-7xl rounded-xl border border-cyan-900/70 bg-cyan-950/20 p-6 md:p-8">
          <div class="mb-5">
            <h2 class="text-xl md:text-2xl font-semibold text-cyan-100">Insights</h2>
            <p class="mt-1 text-sm text-zinc-400">Conclusoes geradas pelo ETL Python a partir dos dados tratados.</p>
          </div>
          <ul class="grid gap-3 md:grid-cols-2">
            <li v-for="(insight, index) in insights" :key="index" class="rounded-lg border border-zinc-800 bg-zinc-900/70 p-4 text-zinc-200">
              {{ insight }}
            </li>
          </ul>
        </section>
        <section v-else class="w-full max-w-7xl rounded-xl border border-zinc-800 bg-zinc-900/50 p-6 text-zinc-400">
          Ainda nao ha insights para exibir.
        </section>

        <section class="grid grid-cols-1 lg:grid-cols-2 gap-6 w-full max-w-7xl">
          <article class="rounded-xl border border-zinc-700 bg-zinc-800/70 p-6">
            <h2 class="text-xl font-semibold">Faturamento por segmento</h2>
            <p class="mt-1 text-sm text-zinc-400">Soma calculada pelo Python em cada segmento.</p>
            <div class="relative mt-5 h-80"><canvas ref="graficoSegmento" aria-label="Faturamento por segmento"></canvas></div>
          </article>

          <article class="rounded-xl border border-zinc-700 bg-zinc-800/70 p-6">
            <h2 class="text-xl font-semibold">Clientes por nivel</h2>
            <p class="mt-1 text-sm text-zinc-400">Distribuicao dos clientes nos niveis A, B e C.</p>
            <div class="relative mt-5 flex h-80 justify-center"><canvas ref="graficoNivel" aria-label="Clientes por nivel"></canvas></div>
          </article>

          <article class="rounded-xl border border-zinc-700 bg-zinc-800/70 p-6 lg:col-span-2">
            <h2 class="text-xl font-semibold">Contratacoes por mes</h2>
            <p class="mt-1 text-sm text-zinc-400">Contagem mensal calculada pelo Python.</p>
            <div class="relative mt-5 h-80"><canvas ref="graficoContratacoes" aria-label="Contratacoes por mes"></canvas></div>
          </article>
        </section>

        <section class="grid grid-cols-1 lg:grid-cols-2 gap-6 w-full max-w-7xl">
          <article class="overflow-hidden rounded-xl border border-zinc-800 bg-zinc-900/50">
            <div class="border-b border-zinc-800 p-6">
              <h2 class="text-xl font-semibold">Resumo estatistico</h2>
              <p class="mt-1 text-sm text-zinc-400">Medidas calculadas sobre o faturamento anual tratado.</p>
            </div>
            <dl class="divide-y divide-zinc-800 px-6">
              <div v-for="item in resumoEstatistico" :key="item.label" class="flex justify-between gap-4 py-3 text-sm">
                <dt class="text-zinc-400">{{ item.label }}</dt>
                <dd class="text-right font-medium text-cyan-200">{{ item.value }}</dd>
              </div>
            </dl>
          </article>

          <article class="overflow-hidden rounded-xl border border-zinc-800 bg-zinc-900/50">
            <div class="border-b border-zinc-800 p-6">
              <h2 class="text-xl font-semibold">Maiores clientes</h2>
              <p class="mt-1 text-sm text-zinc-400">Top 5 por faturamento anual, calculado pelo Python.</p>
            </div>
            <div v-if="topClientes.length" class="overflow-x-auto">
              <table class="w-full text-sm">
                <thead class="bg-zinc-800 text-zinc-400">
                  <tr><th class="px-4 py-3 text-left">Cliente</th><th class="px-4 py-3 text-left">Segmento</th><th class="px-4 py-3 text-right">Faturamento</th></tr>
                </thead>
                <tbody>
                  <tr v-for="cliente in topClientes" :key="cliente.codigo" class="border-t border-zinc-800">
                    <td class="px-4 py-3"><span class="block">{{ cliente.nome }}</span><span class="text-xs text-zinc-500">{{ cliente.codigo }}</span></td>
                    <td class="px-4 py-3 text-zinc-300">{{ cliente.segmento }}</td>
                    <td class="px-4 py-3 text-right text-cyan-200">{{ formatarMoeda(cliente.faturamento) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
            <p v-else class="p-6 text-sm text-zinc-500">Sem clientes para listar.</p>
          </article>
        </section>

        <section class="flex w-full max-w-7xl flex-wrap items-center justify-between gap-3 text-sm text-zinc-500">
          <p v-if="analise.qualidade">Qualidade: {{ analise.qualidade.linhasValidas }} linhas validas de {{ analise.qualidade.linhasRecebidas }} analisadas.</p>
          <p v-if="analise.geradoEm">Analise atualizada em {{ formatarData(analise.geradoEm) }}</p>
          <button class="rounded-lg border border-zinc-700 px-4 py-2 text-zinc-300 hover:border-cyan-500 hover:text-cyan-200" @click="carregarDashboard">
            Atualizar dados
          </button>
        </section>
      </template>
    </main>

    <Footer />
  </div>
</template>

<script setup>
import { computed, nextTick, onBeforeUnmount, onMounted, ref } from "vue"
import api from "@/services/api"
import Header from "../components/Header.vue"
import Footer from "../components/Footer.vue"
import {
  ArcElement,
  BarController,
  BarElement,
  CategoryScale,
  Chart,
  DoughnutController,
  Filler,
  Legend,
  LinearScale,
  LineController,
  LineElement,
  PointElement,
  Tooltip,
} from "chart.js"

Chart.register(
  ArcElement,
  BarController,
  BarElement,
  CategoryScale,
  DoughnutController,
  Filler,
  Legend,
  LinearScale,
  LineController,
  LineElement,
  PointElement,
  Tooltip,
)

const carregando = ref(true)
const erro = ref("")
const analise = ref({})
const graficoSegmento = ref(null)
const graficoNivel = ref(null)
const graficoContratacoes = ref(null)
let chartSegmento = null
let chartNivel = null
let chartContratacoes = null

const resumo = computed(() => analise.value.resumo || {})
const insights = computed(() => analise.value.insights || [])
const topClientes = computed(() => analise.value.topClientes || [])
const porSegmento = computed(() => analise.value.porSegmento || [])
const porNivel = computed(() => analise.value.porNivel || [])
const porMes = computed(() => analise.value.porMes || [])

const resumoEstatistico = computed(() => [
  { label: "Mediana", value: formatarMoeda(resumo.value.mediana) },
  { label: "Desvio padrao", value: formatarMoeda(resumo.value.desvioPadrao) },
  { label: "Valor minimo", value: formatarMoeda(resumo.value.minimo) },
  { label: "Primeiro quartil (Q1)", value: formatarMoeda(resumo.value.q1) },
  { label: "Terceiro quartil (Q3)", value: formatarMoeda(resumo.value.q3) },
  { label: "Valor maximo", value: formatarMoeda(resumo.value.maximo) },
  { label: "Consultores", value: String(resumo.value.consultores ?? 0) },
  { label: "Media de servicos por cliente", value: String(resumo.value.mediaServicosPorCliente ?? "—") },
])

function formatarMoeda(valor) {
  if (valor === null || valor === undefined || valor === "") return "—"
  const numero = Number(valor)
  if (!Number.isFinite(numero)) return "—"
  return numero.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })
}

function formatarData(valor) {
  const data = new Date(valor)
  return Number.isNaN(data.getTime()) ? valor : data.toLocaleString("pt-BR")
}

function mensagemErro(err) {
  const detalhe = err.response?.data?.detail || err.response?.data?.message
  if (typeof detalhe === "string") {
    try {
      const interno = JSON.parse(detalhe)
      if (interno.detail) return interno.detail
    } catch {
      // A resposta pode ser uma mensagem textual simples.
    }
    return detalhe
  }
  return "Nao foi possivel carregar a analise do Python. Verifique se os servicos estao ativos."
}

async function carregarDashboard() {
  carregando.value = true
  erro.value = ""
  destruirGraficos()

  try {
    const resposta = await api.get("/api/cliente/dashboard")
    analise.value = resposta.data || {}
    await nextTick()
    renderizarGraficos()
  } catch (err) {
    console.error("Erro ao carregar dashboard analitico:", err)
    erro.value = mensagemErro(err)
  } finally {
    carregando.value = false
  }
}

function destruirGraficos() {
  chartSegmento?.destroy()
  chartNivel?.destroy()
  chartContratacoes?.destroy()
  chartSegmento = null
  chartNivel = null
  chartContratacoes = null
}

function opcoesComuns() {
  return {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { labels: { color: "#e4e4e7" } },
      tooltip: { titleColor: "#fff", bodyColor: "#e4e4e7" },
    },
  }
}

function renderizarGraficos() {
  destruirGraficos()

  if (graficoSegmento.value && porSegmento.value.length) {
    chartSegmento = new Chart(graficoSegmento.value, {
      type: "bar",
      data: {
        labels: porSegmento.value.map((item) => item.segmento),
        datasets: [{
          label: "Faturamento",
          data: porSegmento.value.map((item) => item.faturamentoTotal),
          backgroundColor: ["#06b6d4", "#3b82f6", "#8b5cf6", "#f59e0b", "#10b981", "#ec4899"],
          borderRadius: 6,
        }],
      },
      options: {
        ...opcoesComuns(),
        scales: {
          x: { ticks: { color: "#d4d4d8" }, grid: { color: "#ffffff10" } },
          y: { ticks: { color: "#d4d4d8", callback: (value) => formatarMoeda(value) }, grid: { color: "#ffffff10" } },
        },
      },
    })
  }

  if (graficoNivel.value && porNivel.value.length) {
    chartNivel = new Chart(graficoNivel.value, {
      type: "doughnut",
      data: {
        labels: porNivel.value.map((item) => `Nivel ${item.nivel}`),
        datasets: [{
          data: porNivel.value.map((item) => item.clientes),
          backgroundColor: ["#06b6d4", "#8b5cf6", "#f59e0b"],
          borderColor: "#27272a",
          borderWidth: 3,
        }],
      },
      options: { ...opcoesComuns(), cutout: "58%" },
    })
  }

  if (graficoContratacoes.value && porMes.value.length) {
    chartContratacoes = new Chart(graficoContratacoes.value, {
      type: "line",
      data: {
        labels: porMes.value.map((item) => item.mes),
        datasets: [{
          label: "Clientes contratados",
          data: porMes.value.map((item) => item.clientes),
          borderColor: "#22d3ee",
          backgroundColor: "rgba(34, 211, 238, 0.15)",
          borderWidth: 3,
          tension: 0.3,
          fill: true,
          pointRadius: 4,
        }],
      },
      options: {
        ...opcoesComuns(),
        scales: {
          x: { ticks: { color: "#d4d4d8" }, grid: { color: "#ffffff10" } },
          y: { beginAtZero: true, ticks: { color: "#d4d4d8", precision: 0 }, grid: { color: "#ffffff10" } },
        },
      },
    })
  }
}

onMounted(carregarDashboard)
onBeforeUnmount(destruirGraficos)
</script>
