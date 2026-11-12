import axios from "axios";

const api = axios.create({
  baseURL: "https://backend-java-production-5e455b77.up.railway.app/",
  headers: {
    'Content-Type': 'application/json'
  }
});

export default api;

