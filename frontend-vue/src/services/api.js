import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:3000", // Substitua pela URL do seu backend
  headers: {
    'Content-Type': 'application/json'
  }
});

export default api;

