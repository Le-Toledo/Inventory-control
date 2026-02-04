import api from "./api";

export const productService = {
  getAll: () => api.get("/products"),
  getById: (id) => api.get(`/products/${id}`),
  create: (product) => api.post("/products", product),
  update: (id, product) => api.put(`/products/${id}`, product),
  delete: (id) => api.delete(`/products/${id}`),
};

export const rawMaterialService = {
  getAll: () => api.get("/raw-materials"),
  getById: (id) => api.get(`/raw-materials/${id}`),
  create: (rawMaterial) => api.post("/raw-materials", rawMaterial),
  update: (id, rawMaterial) => api.put(`/raw-materials/${id}`, rawMaterial),
  delete: (id) => api.delete(`/raw-materials/${id}`),
};

export const productionService = {
  calculate: () => api.get("/production/calculate"),
};
