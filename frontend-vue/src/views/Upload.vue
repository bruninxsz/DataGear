<template>
  <div
    class="min-h-screen bg-zinc-900 text-white flex flex-col gap-4 md:gap-8  font-sans selection:bg-cyan-500 selection:text-zinc-900">
    <Header />
    <main class="flex-1 flex items-center flex-col gap-24 md:gap-32 lg:gap-36 x-6 py-12 lg:py-24">
      <section id="titulo" class="relative z-10">
        <div class="absolute bg-sky-200/60 rounded-full z-0 w-32 md:w-64 lg:w-112 h-12 blur-3xl"></div>
        <h1
          class="relative z-10 font-bold bg-gradient-to-r from-sky-200 to-cyan-600 bg-clip-text text-transparent text-2xl md:text-4xl lg:text-5xl hover:scale-105 duration-300">
          Envie a sua Planilha</h1>
      </section>
      <section id="upload" class="flex flex-col p-24 md:p-32 lg:p-48 border border-sky-200 rounded-xl gap-4">
        <label for="arquivo" class="cursor-pointer text-sm md:text-lg ">
          Arraste seu arquivo aqui
        </label>

        <button @click="store.tratarDados()"
          class="cursor-pointer bg-gradient-to-b from-cyan-500 to-sky-200 text-zinc-900 opacity-80 border border-white py-2 rounded-lg font-medium hover:shadow-md duration-300 shadow-cyan-300 text-center text-xs md:text-lg">
          Processar arquivo
        </button>

        <input type="file" id="arquivo" accept=".xlsx,.xls,.csv" class="hidden" @change="escolherArquivo" />



      </section>
    </main>
    <footer>
      <Footer />
    </footer>
  </div>
</template>

<script setup>
import { useUploadStore } from '../stores/uploadStore.js'
import Header from "../components/Header.vue"
import Footer from "../components/Footer.vue"

const store = useUploadStore()

function escolherArquivo(event) {
  const file = event.target.files?.[0]
  if (file) {
    store.lerArquivo(file)
  }
}

</script>
