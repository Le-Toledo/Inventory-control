import api from "./api";

export const productService = {
  getAll: () => api.get("/products"),
  getById: (id) => api.get(`/products/${id}`),
  create: (product) => api.post("/products", product),
  update: (id, product) => api.put(`/products/${id}`, product),
  delete: (id) => api.delete(`/products/${id}`),
};

export const servicoMateriaPrima = {
  getAll: () => api.get("/raw-materials"),
  getById: (id) => api.get(`/raw-materials/${id}`),
  create: (materiaPrima) => api.post("/raw-materials", materiaPrima),
  update: (id, materiaPrima) => api.put(`/raw-materials/${id}`, materiaPrima),
  delete: (id) => api.delete(`/raw-materials/${id}`),
};

export const productionService = {
  calculate: () => api.get("/production/calculate"),
};
