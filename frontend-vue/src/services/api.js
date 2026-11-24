import axios from "axios";

const api = axios.create({
  baseURL: "https://backend-datagear.up.railway.app/",
  headers: {
    'Content-Type': 'application/json'
  }
});

export default api;

