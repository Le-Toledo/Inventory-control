import api from "./api";
import { ENDPOINTS } from "../config/endpoints";

export const productsService = {
  getAll: () => api.get(ENDPOINTS.products),
  getById: (id) => api.get(`${ENDPOINTS.products}/${id}`),
  create: (product) => api.post(ENDPOINTS.products, product),
  update: (id, product) => api.put(`${ENDPOINTS.products}/${id}`, product),
  delete: (id) => api.delete(`${ENDPOINTS.products}/${id}`),
};
