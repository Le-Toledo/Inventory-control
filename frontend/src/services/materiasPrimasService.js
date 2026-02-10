import api from "./api";
import { ENDPOINTS } from "../config/endpoints";

export const materiasPrimasService = {
  getAll: () => api.get(ENDPOINTS.materiasPrimas),
  getById: (id) => api.get(`${ENDPOINTS.materiasPrimas}/${id}`),
  create: (materiaPrima) => api.post(ENDPOINTS.materiasPrimas, materiaPrima),
  update: (id, materiaPrima) =>
    api.put(`${ENDPOINTS.materiasPrimas}/${id}`, materiaPrima),
  delete: (id) => api.delete(`${ENDPOINTS.materiasPrimas}/${id}`),
};
