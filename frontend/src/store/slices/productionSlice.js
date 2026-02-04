import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { productionService } from "../../services/inventoryService";

export const calculateProduction = createAsyncThunk(
  "production/calculateProduction",
  async () => {
    const response = await productionService.calculate();
    return response.data;
  },
);

const productionSlice = createSlice({
  name: "production",
  initialState: {
    report: null,
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(calculateProduction.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(calculateProduction.fulfilled, (state, action) => {
        state.loading = false;
        state.report = action.payload;
      })
      .addCase(calculateProduction.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message;
      });
  },
});

export default productionSlice.reducer;
