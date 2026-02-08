import { configureStore } from "@reduxjs/toolkit";
import productsReducer from "./slices/productsSlice";
import materiasPrimasReducer from "./slices/rawMaterialsSlice";
import productionReducer from "./slices/productionSlice";

export const store = configureStore({
  reducer: {
    products: productsReducer,
    materiasPrimas: materiasPrimasReducer,
    production: productionReducer,
  },
});

export default store;
