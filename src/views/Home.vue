<template>
  <div class="min-h-screen bg-zinc-900 text-white flex flex-col font-sans selection:bg-cyan-500 selection:text-zinc-900">
    <Header />

    <main class="flex-1 flex items-center justify-center px-6 py-12 lg:py-24">
      <section class="max-w-7xl mx-auto flex flex-col-reverse lg:flex-row items-center justify-between gap-12 lg:gap-16">
        
        <!-- Conteúdo do Texto -->
        <div class="flex flex-col gap-6 text-center lg:text-left max-w-2xl">
          <span class="inline-flex items-center gap-2 self-center lg:self-start px-3 py-1 rounded-full bg-cyan-500/10 border border-cyan-500/20 text-cyan-400 text-xs font-medium uppercase tracking-wider">
            Inteligência de Dados CTI
          </span>

          <h1 class="text-3xl sm:text-4xl lg:text-5xl font-extrabold tracking-tight text-white leading-tight">
            Do Excel ao <span class="bg-gradient-to-r from-cyan-400 to-sky-300 bg-clip-text text-transparent">Dashboard Inteligente</span>
          </h1>

          <p class="text-zinc-400 text-sm sm:text-base lg:text-lg leading-relaxed">
            Sua empresa acumula dados ou gera inteligência? O <strong class="text-white font-semibold">DataGear CTI</strong> captura suas planilhas de clientes, corrige falhas de digitação e converte registros isolados em gráficos dinâmicos de desempenho por consultor, segmento e ticket. Tudo pronto para acelerar sua tomada de decisão.
          </p>

          <!-- Ações / CTAs -->
          <div class="flex flex-col sm:flex-row gap-4 justify-center lg:justify-start pt-2">
            <router-link to="/login" class="relative rounded-lg font-medium px-6 py-3 bg-cyan-500 text-zinc-950 hover:bg-cyan-400 transition-all shadow-[0_0_20px_rgba(34,211,238,0.3)] hover:shadow-[0_0_25px_rgba(34,211,238,0.5)]">
              Transformar Minhas Planilhas
            </router-link>
          </div>
        </div>

        <!-- Imagem da Lupa com Efeito Glow -->
        <div class="relative flex justify-center items-center">
          <!-- Brilho ao fundo da imagem -->
          <div class="absolute -inset-4 bg-gradient-to-r from-cyan-500 to-sky-500 rounded-full blur-3xl opacity-20 group-hover:opacity-40 transition duration-1000"></div>
          
          <img 
            src="/src/assets/lupa.png" 
            alt="Análise de dados com lupa tecnológica"
            class="relative w-60 sm:w-80 lg:w-[420px] h-auto object-contain drop-shadow-[0_10px_20px_rgba(0,0,0,0.5)] transition-transform duration-500 hover:scale-105"
          >
        </div>

      </section>
    </main>
  </div>
</template>

<script setup>
  import Header from "../components/Header.vue"
</script>