import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { productionService } from "../../services/productionService";

export const calculateProduction = createAsyncThunk(
  "production/calculateProduction",
  async () => {
    const response = await productionService.calculate();
    return response.data;
  },
);

export const consumeProduction = createAsyncThunk(
  "production/consumeProduction",
  async () => {
    const response = await productionService.consume();
    return response.data;
  },
);

const productionSlice = createSlice({
  name: "production",
  initialState: {
    report: null,
    carregando: false,
    erro: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(calculateProduction.pending, (state) => {
        state.carregando = true;
        state.erro = null;
      })
      .addCase(calculateProduction.fulfilled, (state, action) => {
        state.carregando = false;
        state.report = action.payload;
      })
      .addCase(calculateProduction.rejected, (state, action) => {
        state.carregando = false;
        state.erro = action.error.message;
      });
    builder
      .addCase(consumeProduction.pending, (state) => {
        state.carregando = true;
        state.erro = null;
      })
      .addCase(consumeProduction.fulfilled, (state, action) => {
        state.carregando = false;
        state.report = action.payload;
      })
      .addCase(consumeProduction.rejected, (state, action) => {
        state.carregando = false;
        state.erro = action.error.message;
      });
  },
});

export default productionSlice.reducer;
