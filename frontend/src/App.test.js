import { render, screen } from "@testing-library/react";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import { configureStore } from "@reduxjs/toolkit";
import App from "./App";
import productsReducer from "./store/slices/productsSlice";
import rawMaterialsReducer from "./store/slices/rawMaterialsSlice";
import productionReducer from "./store/slices/productionSlice";

const createTestStore = () => {
  return configureStore({
    reducer: {
      products: productsReducer,
      rawMaterials: rawMaterialsReducer,
      production: productionReducer,
    },
  });
};

test("renders navigation", () => {
  const store = createTestStore();
  render(
    <Provider store={store}>
      <App />
    </Provider>,
  );
  const linkElement = screen.getByText(/Inventory Management System/i);
  expect(linkElement).toBeInTheDocument();
});
