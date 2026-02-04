import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { rawMaterialService } from "../../services/inventoryService";

// Async thunk for fetching raw materials
export const fetchRawMaterials = createAsyncThunk(
  "rawMaterials/fetchRawMaterials",
  async () => {
    const response = await rawMaterialService.getAll();
    return response.data;
  },
);

// Async thunk for creating a raw material
export const createRawMaterial = createAsyncThunk(
  "rawMaterials/createRawMaterial",
  async (rawMaterialData) => {
    const response = await rawMaterialService.create(rawMaterialData);
    return response.data;
  },
);

// Async thunk for updating a raw material
export const updateRawMaterialAsync = createAsyncThunk(
  "rawMaterials/updateRawMaterial",
  async ({ id, data }) => {
    const response = await rawMaterialService.update(id, data);
    return response.data;
  },
);

// Async thunk for deleting a raw material
export const deleteRawMaterialAsync = createAsyncThunk(
  "rawMaterials/deleteRawMaterial",
  async (id) => {
    await rawMaterialService.delete(id);
    return id;
  },
);

const rawMaterialsSlice = createSlice({
  name: "rawMaterials",
  initialState: {
    items: [],
    loading: false,
    error: null,
  },
  reducers: {
    addRawMaterial: (state, action) => {
      state.items.push(action.payload);
    },
    updateRawMaterial: (state, action) => {
      const index = state.items.findIndex(
        (item) => item.id === action.payload.id,
      );
      if (index !== -1) {
        state.items[index] = action.payload;
      }
    },
    deleteRawMaterial: (state, action) => {
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch raw materials
      .addCase(fetchRawMaterials.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchRawMaterials.fulfilled, (state, action) => {
        state.items = action.payload;
        state.loading = false;
      })
      .addCase(fetchRawMaterials.rejected, (state, action) => {
        state.error = action.error.message;
        state.loading = false;
      })
      // Create raw material
      .addCase(createRawMaterial.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createRawMaterial.fulfilled, (state, action) => {
        state.items.push(action.payload);
        state.loading = false;
      })
      .addCase(createRawMaterial.rejected, (state, action) => {
        state.error = action.error.message;
        state.loading = false;
      })
      // Update raw material
      .addCase(updateRawMaterialAsync.fulfilled, (state, action) => {
        const index = state.items.findIndex(
          (item) => item.id === action.payload.id,
        );
        if (index !== -1) {
          state.items[index] = action.payload;
        }
      })
      // Delete raw material
      .addCase(deleteRawMaterialAsync.fulfilled, (state, action) => {
        state.items = state.items.filter((item) => item.id !== action.payload);
      });
  },
});

export const { addRawMaterial, updateRawMaterial, deleteRawMaterial } =
  rawMaterialsSlice.actions;

export default rawMaterialsSlice.reducer;
