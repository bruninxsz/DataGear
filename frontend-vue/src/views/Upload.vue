<template>
  <div
    class="min-h-screen bg-zinc-900 text-white flex flex-col gap-8 md:gap-16 lg:gap-32 font-sans selection:bg-cyan-500 selection:text-zinc-900">
    <Header />
    <main class="flex-1 flex items-center flex-col gap-24 md:gap-48 lg:gap-60 px-6 py-12 lg:py-24">
      
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

