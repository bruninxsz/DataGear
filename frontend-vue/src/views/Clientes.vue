<template>
  <div
    class="min-h-screen bg-zinc-900 text-white flex flex-col gap-4 md:gap-8 font-sans selection:bg-cyan-500 selection:text-zinc-900">
    <Header />

    <main class="flex-1 flex flex-col gap-8 md:gap-12 px-4 py-8 md:px-8 lg:px-12 lg:py-12">
      <!-- TÍTULO -->
      <section class="relative z-10 w-full max-w-7xl mx-auto">
        <div class="absolute bg-sky-200/60 rounded-full z-0 w-32 h-12 blur-3xl"></div>

        <div class="relative flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <div>
            <h1
              class="font-bold bg-gradient-to-r from-sky-200 to-cyan-600 bg-clip-text text-transparent text-3xl md:text-4xl">
              Clientes
            </h1>

            <p class="text-sm text-zinc-400 mt-2">
              Gerencie seus clientes e acompanhe as informações comerciais.
            </p>
          </div>

          <button @click="buscarClientes"
            class="cursor-pointer px-5 py-3 rounded-lg bg-cyan-500 hover:bg-cyan-400 text-zinc-950 font-semibold transition-colors">
            Atualizar dados
          </button>
        </div>
      </section>

      <!-- INDICADORES -->
      <section class="w-full max-w-7xl mx-auto grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-4">
        <div class="bg-zinc-800 border border-zinc-700 rounded-xl p-5">
          <p class="text-sm text-zinc-400">Total de clientes</p>
          <p class="text-3xl font-bold mt-3">{{ clientes.length }}</p>
          <p class="text-xs text-zinc-500 mt-2">
            Clientes cadastrados
          </p>
        </div>

        <div class="bg-zinc-800 border border-zinc-700 rounded-xl p-5">
          <p class="text-sm text-zinc-400">Clientes ativos</p>
          <p class="text-3xl font-bold text-emerald-400 mt-3">
            {{ totalAtivos }}
          </p>
          <p class="text-xs text-zinc-500 mt-2">
            Com status ativo
          </p>
        </div>

        <div class="bg-zinc-800 border border-zinc-700 rounded-xl p-5">
          <p class="text-sm text-zinc-400">Nível A</p>
          <p class="text-3xl font-bold text-cyan-400 mt-3">
            {{ totalNivelA }}
          </p>
          <p class="text-xs text-zinc-500 mt-2">
            Clientes de nível A
          </p>
        </div>

        <div class="bg-zinc-800 border border-zinc-700 rounded-xl p-5">
          <p class="text-sm text-zinc-400">Faturamento anual</p>
          <p class="text-2xl font-bold text-sky-300 mt-3">
            {{ formatarMoeda(faturamentoTotal) }}
          </p>
          <p class="text-xs text-zinc-500 mt-2">
            Soma do faturamento cadastrado
          </p>
        </div>
      </section>

      <!-- LISTAGEM -->
      <section class="w-full max-w-7xl mx-auto">
        <div class="bg-zinc-800 border border-zinc-700 rounded-xl overflow-hidden">
          <!-- CABEÇALHO DA TABELA -->
          <div class="p-5 md:p-6 border-b border-zinc-700 flex flex-col gap-5">
            <div>
              <h2 class="text-xl font-semibold">Lista de clientes</h2>
              <p class="text-sm text-zinc-400 mt-1">
                Consulte e filtre os clientes cadastrados.
              </p>
            </div>

            <!-- BUSCA E FILTROS -->
            <div class="grid grid-cols-1 md:grid-cols-4 gap-3">
              <input v-model="busca" type="text" placeholder="Buscar por nome, código ou consultor..."
                class="w-full bg-zinc-900 border border-zinc-700 rounded-lg px-4 py-3 text-sm outline-none focus:border-cyan-500 transition-colors" />

              <select v-model="filtroSegmento"
                class="w-full bg-zinc-900 border border-zinc-700 rounded-lg px-4 py-3 text-sm outline-none focus:border-cyan-500">
                <option value="">Todos os segmentos</option>
                <option v-for="segmento in segmentos" :key="segmento" :value="segmento">
                  {{ segmento }}
                </option>
              </select>

              <select v-model="filtroStatus"
                class="w-full bg-zinc-900 border border-zinc-700 rounded-lg px-4 py-3 text-sm outline-none focus:border-cyan-500">
                <option value="">Todos os status</option>
                <option value="ATIVO">Ativo</option>
                <option value="ENCERRADO">Encerrado</option>
              </select>

              <button @click="deletarBase"
                class="cursor-pointer w-full bg-zinc-900 border border-zinc-700 rounded-lg px-4 py-3 text-sm text-red-400 hover:text-red-300 transition-colors">
                Deletar base de clientes
              </button>
            </div>


            <div class="flex flex-wrap items-center justify-between gap-3 text-sm text-zinc-400">
              <span>
                {{ clientesFiltrados.length }}
                cliente(s) encontrado(s)
              </span>

              <button @click="limparFiltros" class="text-cyan-400 hover:text-cyan-300 transition-colors">
                Limpar filtros
              </button>
            </div>
          </div>

          <!-- ESTADOS DA REQUISIÇÃO -->
          <div v-if="carregando" class="p-10 text-center text-zinc-400">
            Carregando clientes...
          </div>

          <div v-else-if="erro" class="p-8 text-center">
            <p class="text-red-400">{{ erro }}</p>
            <button @click="buscarClientes" class="mt-4 text-cyan-400 hover:text-cyan-300">
              Tentar novamente
            </button>
          </div>

          <!-- TABELA -->
          <div v-else class="overflow-x-auto">
            <table class="w-full text-left text-sm whitespace-nowrap">
              <thead class="bg-zinc-900/70 text-zinc-400">
                <tr>
                  <th class="px-5 py-4 font-medium">Cliente</th>
                  <th class="px-5 py-4 font-medium">Código</th>
                  <th class="px-5 py-4 font-medium">Segmento</th>
                  <th class="px-5 py-4 font-medium">Nível</th>
                  <th class="px-5 py-4 font-medium">Faturamento anual</th>
                  <th class="px-5 py-4 font-medium">Localização</th>
                  <th class="px-5 py-4 font-medium">Consultor</th>
                  <th class="px-5 py-4 font-medium">Status</th>
                  <th class="px-5 py-4 font-medium">Serviços</th>
                </tr>
              </thead>

              <tbody class="divide-y divide-zinc-700">
                <tr v-for="cliente in clientesFiltrados" :key="cliente.id_cliente"
                  class="hover:bg-zinc-700/30 transition-colors">
                  <td class="px-5 py-4">
                    <div class="font-medium text-white">
                      {{ cliente.nome_cliente }}
                    </div>
                    <div class="text-xs text-zinc-500 mt-1">
                      ID: {{ cliente.id_cliente }}
                    </div>
                  </td>

                  <td class="px-5 py-4 text-zinc-300">
                    {{ cliente.codigo_cliente || '—' }}
                  </td>

                  <td class="px-5 py-4 text-zinc-300">
                    {{ cliente.segmento || '—' }}
                  </td>

                  <td class="px-5 py-4">
                    <span class="px-2.5 py-1 rounded-md text-xs font-semibold" :class="{
                      'bg-cyan-500/15 text-cyan-300':
                        cliente.nivel_cliente === 'A',
                      'bg-amber-500/15 text-amber-300':
                        cliente.nivel_cliente === 'B',
                      'bg-zinc-600/40 text-zinc-300':
                        cliente.nivel_cliente === 'C',
                    }">
                      {{ cliente.nivel_cliente || '—' }}
                    </span>
                  </td>

                  <td class="px-5 py-4 text-zinc-200">
                    {{ formatarMoeda(cliente.faturamento_anual) }}
                  </td>

                  <td class="px-5 py-4 text-zinc-300">
                    {{
                      [cliente.cidade, cliente.uf]
                        .filter(Boolean)
                        .join(' - ') || '—'
                    }}
                  </td>

                  <td class="px-5 py-4 text-zinc-300">
                    {{ obterNomeConsultor(cliente.consultor) }}
                  </td>

                  <td class="px-5 py-4">
                    <span class="inline-flex items-center gap-2 px-2.5 py-1 rounded-full text-xs font-medium" :class="cliente.status === 'ATIVO'
                        ? 'bg-emerald-500/10 text-emerald-400'
                        : 'bg-red-500/10 text-red-400'
                      ">
                      <span class="w-1.5 h-1.5 rounded-full" :class="cliente.status === 'ATIVO'
                          ? 'bg-emerald-400'
                          : 'bg-red-400'
                        "></span>
                      {{ cliente.status || '—' }}
                    </span>
                  </td>

                  

                  <td class="px-5 py-4 text-zinc-300">
                    <div class="flex flex-wrap gap-1 max-w-xs">
                      <span v-for="servico in listarServicos(
                        cliente.servicos_contratados
                      )" :key="servico" class="px-2 py-1 rounded bg-zinc-700 text-xs">
                        {{ servico }}
                      </span>

                      <span v-if="!listarServicos(cliente.servicos_contratados).length" class="text-zinc-500">
                        —
                      </span>
                    </div>
                  </td>
                </tr>

                <tr v-if="!clientesFiltrados.length">
                  <td colspan="9" class="px-5 py-12 text-center text-zinc-400">
                    Nenhum cliente encontrado com os filtros selecionados.
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
import { ref, computed, onMounted } from 'vue'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

const API_URL = 'http://localhost:8080/api/cliente/listar'

const clientes = ref([])
const busca = ref('')
const filtroSegmento = ref('')
const filtroStatus = ref('')
const carregando = ref(false)
const erro = ref('')

const segmentos = computed(() => {
  return [
    ...new Set(
      clientes.value
        .map((cliente) => cliente.segmento)
        .filter(Boolean),
    ),
  ].sort()
})

const totalAtivos = computed(
  () =>
    clientes.value.filter(
      (cliente) => cliente.status === 'ATIVO',
    ).length,
)

const totalNivelA = computed(
  () =>
    clientes.value.filter(
      (cliente) => cliente.nivel_cliente === 'A',
    ).length,
)

const faturamentoTotal = computed(() =>
  clientes.value.reduce(
    (total, cliente) =>
      total + (Number(cliente.faturamento_anual) || 0),
    0,
  ),
)

const clientesFiltrados = computed(() => {
  const termo = busca.value.trim().toLowerCase()

  return clientes.value.filter((cliente) => {
    const correspondeBusca =
      !termo ||
      [
        cliente.nome_cliente,
        cliente.codigo_cliente,
        obterNomeConsultor(cliente.consultor),
        cliente.cidade,
        cliente.uf,
      ].some((valor) =>
        String(valor ?? '').toLowerCase().includes(termo),
      )

    const correspondeSegmento =
      !filtroSegmento.value ||
      cliente.segmento === filtroSegmento.value

    const correspondeStatus =
      !filtroStatus.value ||
      cliente.status === filtroStatus.value

    return (
      correspondeBusca &&
      correspondeSegmento &&
      correspondeStatus
    )
  })
})

function formatarMoeda(valor) {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
    maximumFractionDigits: 2,
  }).format(Number(valor) || 0)
}

function obterNomeConsultor(consultor) {
  if (!consultor) return '—'

  // Aceita tanto um nome quanto uma entidade Consultor.
  if (typeof consultor === 'string') return consultor

  return (
    consultor.nomeConsultor ??
    consultor.nome ??
    '—'
  )
}

function listarServicos(servicos) {
  if (!servicos) return []

  if (Array.isArray(servicos)) {
    return servicos
      .map((servico) =>
        typeof servico === 'string'
          ? servico
          : servico.nome_servico ?? servico.nome ?? '',
      )
      .filter(Boolean)
  }

  // Aceita serviços armazenados como
  // "Internet Dedicada;Firewall;Backup".
  return String(servicos)
    .split(';')
    .map((servico) => servico.trim())
    .filter(Boolean)
}

async function buscarClientes() {
  carregando.value = true
  erro.value = ''

  try {
    const resposta = await fetch(API_URL)

    if (!resposta.ok) {
      throw new Error(
        `Erro ao consultar clientes: HTTP ${resposta.status}`,
      )
    }

    const dados = await resposta.json()

    // Suporta uma lista direta ou respostas paginadas.
    clientes.value = Array.isArray(dados)
      ? dados
      : dados.content ?? dados.clientes ?? []
  } catch (error) {
    console.error('Erro ao buscar clientes:', error)

    erro.value =
      'Não foi possível carregar os clientes. Verifique se o backend está funcionando e se a rota da API está correta.'
  } finally {
    carregando.value = false
  }
}

function limparFiltros() {
  busca.value = ''
  filtroSegmento.value = ''
  filtroStatus.value = ''
}

async function deletarBase() {
  const confirmacao = confirm(
    'Tem certeza de que deseja deletar toda a base de clientes? Esta ação não pode ser desfeita.',
  )

  if (!confirmacao) return

  try {
    const resposta = await fetch(
      'http://localhost:8080/api/cliente/deletar',
      {
        method: 'DELETE',
      },
    )

    if (!resposta.ok) {
      throw new Error(
        `Erro ao deletar base de clientes: HTTP ${resposta.status}`,
      )
    }

    alert('Base de clientes deletada com sucesso.')
    buscarClientes()
  } catch (error) {
    console.error('Erro ao deletar base de clientes:', error)
    alert(
      'Não foi possível deletar a base de clientes.',
    )
  }
}

onMounted(buscarClientes)
</script>