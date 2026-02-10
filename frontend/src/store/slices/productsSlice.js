import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { productsService } from "../../services/productsService";

export const fetchProducts = createAsyncThunk(
  "products/fetchProducts",
  async () => {
    const response = await productsService.getAll();
    return response.data;
  },
);

export const createProduct = createAsyncThunk(
  "products/createProduct",
  async (product) => {
    const response = await productsService.create(product);
    return response.data;
  },
);

export const updateProduct = createAsyncThunk(
  "products/updateProduct",
  async ({ id, product }) => {
    const response = await productsService.update(id, product);
    return response.data;
  },
);

export const deleteProduct = createAsyncThunk(
  "products/deleteProduct",
  async (id) => {
    await productsService.delete(id);
    return id;
  },
);

const productsSlice = createSlice({
  name: "products",
  initialState: {
    items: [],
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      })
      .addCase(createProduct.fulfilled, (state, action) => {
        state.items.push(action.payload);
      })
      .addCase(updateProduct.fulfilled, (state, action) => {
        const index = state.items.findIndex(
          (item) => item.id === action.payload.id,
        );
        if (index !== -1) {
          state.items[index] = action.payload;
        }
      })
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.items = state.items.filter((item) => item.id !== action.payload);
      });
  },
});

export default productsSlice.reducer;
