<template>
  <header class="bg-zinc-900 flex flex-col border-b-1 border-sky-300 shadow-sm shadow-sky-300"> 
  <div class=" flex justify-center text-xl gap-10 md:gap-24 lg:justify-between sm:p-3 lg:p-1">
    <div class="items-center lg:ml-54"><img src="../assets/logo.png" class="min-w-24 max-w-32 md:w-36 lg:w-42 lg:max-w-42 hover:scale-[1.05] duration-300"></div>
    <nav class="flex justify-center gap-3 md:gap-8 lg:gap-12 text-white text-xs md:text-sm lg:text-lg font-bold items-center">
      <a href="#cards" class="relative inline-block
         after:absolute after:left-1/2 after:-bottom-2
         after:h-[2px] after:w-0
         after:-translate-x-1/2
         after:bg-cyan-400  
         after:shadow-[0_0_10px_#22d3ee]
         after:transition-all after:duration-300
         hover:after:w-8 hover:text-sky-300">
        Funcionalidades
      </a>
      <a href="#contato" class="relative inline-block
         after:absolute after:left-1/2 after:-bottom-2
         after:h-[2px] after:w-0
         after:-translate-x-1/2
         after:bg-cyan-400
         after:shadow-[0_0_10px_#22d3ee]
         after:transition-all after:duration-300
         hover:after:w-8 hover:text-sky-300">
        Contato
      </a>
      <router-link to="/login" class="relative rounded-md border sm:border-1 lg:border-0 border-white font-bold px-3 lg:px-5 py-2 text-white transition-colors duration-600
               before:absolute before:-inset-[1px] before:rounded-md before:border-2 before:border-sky-500
               before:[clip-path:inset(0_0_100%_0)] hover:before:[clip-path:inset(0_0_0_0)]
               before:transition-all before:duration-500 hover:text-sky-300 hover:before:shadow-sm shadow-sky-300 mr-2 lg:mr-54">
        Entrar
      </router-link>
    </nav>
    </div>
  </header>
</template>

<script setup>
import {ref, onMounted} from 'vue';

const actualPage = ref('')

</script>