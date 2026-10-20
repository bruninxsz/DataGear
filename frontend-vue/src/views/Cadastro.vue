<template>
  <main class="grid grid-cols-1 bg-gradient-to-b from-zinc-950 to-cyan-900 h-screen">
    <form @submit.prevent="formSubmit"
      class="flex flex-col items-center gap-8 m-auto bg-zinc-900 shadow-md shadow-cyan-300 p-4 min-w-sm md:min-w-xl lg:min-w-2xl text-cyan-100 rounded-4xl">
      <h2 class="text-cyan-200 text-2xl mt-8 font-bold mb-4">Faça seu Cadastro</h2>
      <input type="text" v-model="nome"
      class="border border-cyan-200 rounded-lg w-70 md:w-90 lg:w-md py-3 px-2 text-sm lg:text-base"
      placeholder="Digite o seu nome:"> 
      <input type="tel" v-model="telefone"
      class="border border-cyan-200 rounded-lg w-70 md:w-90 lg:w-md py-3 px-2 text-sm lg:text-base"
      placeholder="Digite o seu telefone:">
      <input type="email" v-model="email"
      class="border border-cyan-200 rounded-lg w-70 md:w-90 lg:w-md py-3 px-2 text-sm lg:text-base"
      placeholder="Digite o seu e-mail:">
      <input type="password" v-model="senha"
        class="border border-cyan-200 rounded-lg w-70 md:w-90 lg:w-md py-3 px-2 text-sm lg:text-base"
        placeholder="Digite a sua senha:">
      <button type="submit"
        class="bg-gradient-to-b from-cyan-500 to-sky-200 text-zinc-900 opacity-80 border border-white py-2 rounded-lg font-medium hover:shadow-md duration-300 shadow-cyan-300 duration-400 min-w-40 mt-2 mb-6">
        Enviar </button>
      <router-link to="login" class="mb-8 hover:scale-[1.03] duration-200">Já possui conta?</router-link>
    </form>
  </main>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter }from 'vue-router'
import axios from 'axios'

const router = useRouter()

const nome = ref('')
const email = ref('')
const telefone = ref('')
const senha = ref('')

async function formSubmit() {

  try {
    const response = await axios.post('http://localhost:8080/api/consultor', {
      nome: nome.value,
      email: email.value,
      telefone: telefone.value,
      senha: senha.value
    })

    console.log('response:', response.data)
    router.push('/login');

  } catch (error) {

    console.error('Erro ao realizar cadastro:', error)

    if (error.response && error.response.data) {
      console.log('Detalhes da validação do Spring:', error.response.data)
    }

    alert('Erro ao realizar cadastro. Verifique os campos preenchidos.')
  }
}

</script>