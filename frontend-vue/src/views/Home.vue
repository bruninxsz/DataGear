<template>
  <div
    class="min-h-screen bg-zinc-900 text-white flex flex-col gap-8 md:gap-16 lg:gap-32 font-sans selection:bg-cyan-500 selection:text-zinc-900">
    <Header />
    <main class="flex-1 flex items-center flex-col gap-24 md:gap-48 lg:gap-60 px-6 py-12 lg:py-24">
      <section id="apresentacao" 
        class="max-w-7xl mx-auto flex flex-col-reverse lg:flex-row items-center justify-between gap-12 lg:gap-16" >

        <!-- Conteúdo do Texto -->
        <div class="flex flex-col gap-6 text-center lg:text-left max-w-2xl">

          <h1 class="text-3xl sm:text-4xl lg:text-5xl font-extrabold tracking-tight text-white leading-tight">
            Do Excel ao <span class="bg-gradient-to-r from-cyan-400 to-sky-300 bg-clip-text text-transparent">Dashboard
              Inteligente</span>
          </h1>

          <p class="text-zinc-400 text-sm sm:text-base lg:text-lg leading-relaxed">
            Sua empresa acumula dados ou gera inteligência? O <strong class="text-white font-semibold">DataGear</strong>
            captura suas planilhas de clientes, corrige falhas de digitação e converte registros isolados em gráficos
            dinâmicos de desempenho por consultor, segmento e ticket. Tudo pronto para acelerar sua tomada de decisão.
          </p>

          <!-- Ações / CTAs -->
          <div class="flex flex-col sm:flex-row gap-4 justify-center lg:justify-start pt-2">
            <router-link to="/cadastro"
              class="relative rounded-lg font-medium px-9 py-4 bg-cyan-500 text-zinc-900 hover:bg-cyan-400 transition-all shadow-[0_0_20px_rgba(34,211,238,0.3)] hover:shadow-[0_0_25px_rgba(34,211,238,0.5)]">
              Cadastre-se
            </router-link>
          </div>
        </div>

        <!-- Imagem da Lupa com Efeito Glow -->
        <div class="relative flex justify-center items-center">
          <!-- Brilho ao fundo da imagem -->
          <div
            class="absolute -inset-4 bg-gradient-to-r from-cyan-500 to-sky-500 rounded-full blur-3xl opacity-20 group-hover:opacity-40 transition duration-1000">
          </div>

          <img src="/src/assets/lupa.png" alt="Análise de dados com lupa tecnológica"
            class="relative w-60 sm:w-80 lg:w-[420px] h-auto object-contain drop-shadow-[0_10px_20px_rgba(0,0,0,0.5)] transition-transform duration-500 hover:scale-105">
        </div>

      </section>

      <!-- Section Cards -->
      <section id="cards" class="flex flex-col gap-12 md:gap-16 lg:gap-32 min-w-xs max-w-xs md:max-w-xl lg:max-w-7xl">
        <h2 class="bg-gradient-to-b from-cyan-500 to-cyan-100 bg-clip-text text-transparent hover:scale-[1.01] duration-300 text-xl md:text-3xl lg:text-4xl font-extrabold text-center">
          Conheça as funcionalidades da plataforma
        </h2>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 md:gap-12 lg:gap-18 items-center">

          <div class="flex flex-col gap-3 rounded-lg px-4 py-14 shadow-xs hover:shadow-md duration-400 shadow-cyan-500">
            <h2 class="text-cyan-200 font-semibold text-lg lg:text-xl mb-3">
              Tratamento de Dados
            </h2>
            <p class="text-gray-400 text-sm leading-relaxed">
              Limpeza, organização e padronização dos dados comerciais para garantir
              informações mais confiáveis e preparadas para análise.
            </p>
          </div>

          <div class="flex flex-col gap-3 rounded-lg px-4 py-14 shadow-xs hover:shadow-md duration-400 shadow-cyan-500">
            <h2 class="text-cyan-200 font-semibold text-lg lg:text-xl mb-3">
              Análise da Carteira
            </h2>
            <p class="text-gray-400 text-sm leading-relaxed">
              Análise dos clientes, contratos e serviços para compreender a composição
              da carteira comercial e identificar os principais padrões.
            </p>
          </div>

          <div class="flex flex-col gap-3 rounded-lg px-4 py-14 shadow-xs hover:shadow-md duration-400 shadow-cyan-500">
            <h2 class="text-cyan-200 font-semibold text-lg lg:text-xl mb-3">
              Segmentação de Clientes
            </h2>
            <p class="text-gray-400 text-sm leading-relaxed">
              Identificação de perfis e segmentos de clientes para entender onde estão
              as maiores concentrações e oportunidades comerciais.
            </p>
          </div>

        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 md:gap-12 lg:gap-24 items-center">

          <div class="flex flex-col gap-3 rounded-lg px-5 py-14 shadow-xs hover:shadow-md duration-400 shadow-cyan-500">
            <h2 class="text-cyan-200 font-semibold text-lg lg:text-xl mb-3">
              Serviços Mais Contratados
            </h2>
            <p class="text-gray-400 text-sm leading-relaxed">
              Visualização dos serviços mais contratados e identificação das principais
              demandas presentes na carteira de clientes.
            </p>
          </div>

          <div class="flex flex-col gap-3 rounded-lg px-5 py-14 shadow-xs hover:shadow-md duration-400 shadow-cyan-500">
            <h2 class="text-cyan-200 font-semibold text-lg lg:text-xl mb-3">
              Insights Estratégicos
            </h2>
            <p class="text-gray-400 text-sm leading-relaxed">
              Geração de insights a partir dos dados para revelar padrões, oportunidades
              e informações relevantes para a área comercial.
            </p>
          </div>

          <div class="flex flex-col gap-3 rounded-lg px-5 py-14 shadow-xs hover:shadow-md duration-400 shadow-cyan-500">
            <h2 class="text-cyan-200 font-semibold text-lg lg:text-xl mb-3">
              Apoio à Decisão
            </h2>
            <p class="text-gray-400 text-sm leading-relaxed">
              Informações que auxiliam na definição de prioridades, direcionamento de
              esforços e planejamento de futuras ações comerciais.
            </p>
          </div>

        </div>
      </section>

      <!-- Section Contato -->
      <section id="contato" class="flex justify-between gap-8 md:gap-0 lg:gap-0 md:min-w-2xl lg:min-w-5xl">
        <div class="flex flex-col gap-2 hover:scale-[1.03] duration-500 mt-8">
          <h2 class="text-2xl md:text-4xl lg:text-6xl font-bold">Dúvidas?</h2>
          <h2 class="text-2xl md:text-4xl lg:text-6xl font-extrabold bg-gradient-to-r from-cyan-500 to-sky-200 bg-clip-text text-transparent">Contate-nos</h2>
        </div>
        <form class="flex flex-col gap-6">
          <input type="email" class="border border-cyan-200 rounded-lg w-50 md:w-90 lg:w-md py-3 px-2 text-sm lg:text-base" placeholder="Digite o seu e-mail:">
          <input type="text" class="border border-cyan-200 rounded-lg w-50 md:w-90 lg:w-md py-3 px-2 h-30 lg:h-40 text-sm lg:text-base" placeholder="Deixe aqui a sua dúvida:">
          <button type="submit" class="bg-gradient-to-b from-cyan-500 to-sky-200 text-zinc-900 opacity-80 border border-white py-2 rounded-lg font-medium hover:shadow-md duration-300 shadow-cyan-300 duration-400"> Enviar </button>
        </form>
      </section>  
    </main>
    <footer>
      <Footer />
    </footer>
  </div>
</template>

<script setup>
import Header from "../components/Header.vue"
import Footer from "../components/Footer.vue"

</script>

