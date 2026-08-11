<template>
  <header class="bg-zinc-800 flex flex-col">
  <div class=" flex justify-center text-xl gap-14 lg:justify-between p-1">
    <div class="items-center lg:ml-54"><img src="../assets/logo.png" class="w-32 lg:w-40"></div>
    <nav class="flex justify-center gap-3 lg:gap-12 text-white text-xs lg:text-lg font-normal items-center">
      <a href="" class="relative inline-block
         after:absolute after:left-1/2 after:-bottom-2
         after:h-[2px] after:w-0
         after:-translate-x-1/2
         after:bg-cyan-400
         after:shadow-[0_0_10px_#22d3ee]
         after:transition-all after:duration-300
         hover:after:w-8 hover:text-sky-300">
        Sobre
      </a>
      <a href="" class="relative inline-block
         after:absolute after:left-1/2 after:-bottom-2
         after:h-[2px] after:w-0
         after:-translate-x-1/2
         after:bg-cyan-400  
         after:shadow-[0_0_10px_#22d3ee]
         after:transition-all after:duration-300
         hover:after:w-8 hover:text-sky-300">
        Funcionalidades
      </a>
      <a href="" class="relative inline-block
         after:absolute after:left-1/2 after:-bottom-2
         after:h-[2px] after:w-0
         after:-translate-x-1/2
         after:bg-cyan-400
         after:shadow-[0_0_10px_#22d3ee]
         after:transition-all after:duration-300
         hover:after:w-8 hover:text-sky-300">
        Contato
      </a>
      <router-link to="/login" class="relative rounded-md border border-white font-normal px-5 py-2 text-white transition-colors duration-300
               before:absolute before:-inset-[1px] before:rounded-md before:border-2 before:border-sky-500
               before:[clip-path:inset(0_0_100%_0)] hover:before:[clip-path:inset(0_0_0_0)]
               before:transition-all before:duration-500 hover:text-sky-300 hover:before:shadow-sm shadow-sky-300 lg:mr-54">
        Entrar
      </router-link>
    </nav>
    </div>
  </header>
</template>
