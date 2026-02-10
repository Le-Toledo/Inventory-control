import api from "./api";
import { ENDPOINTS } from "../config/endpoints";

export const productionService = {
  calculate: () => api.get(ENDPOINTS.productionCalculate),
  consume: () => api.post(ENDPOINTS.productionConsume),
};
