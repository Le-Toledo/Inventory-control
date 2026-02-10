import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { servicoMateriaPrima } from "../../services/inventoryService";

// Async thunk for fetching materias primas
export const buscarMateriasPrimas = createAsyncThunk(
  "materiasPrimas/buscarMateriasPrimas",
  async () => {
    const response = await servicoMateriaPrima.getAll();
    return response.data;
  },
);

// Async thunk for creating a materia prima
export const criarMateriaPrima = createAsyncThunk(
  "materiasPrimas/criarMateriaPrima",
  async (dadosMateriaPrima) => {
    const response = await servicoMateriaPrima.create(dadosMateriaPrima);
    return response.data;
  },
);

// Async thunk for updating a materia prima
export const atualizarMateriaPrimaAsync = createAsyncThunk(
  "materiasPrimas/atualizarMateriaPrima",
  async ({ id, data }) => {
    const response = await servicoMateriaPrima.update(id, data);
    return response.data;
  },
);

// Async thunk for deleting a materia prima
export const excluirMateriaPrimaAsync = createAsyncThunk(
  "materiasPrimas/excluirMateriaPrima",
  async (id) => {
    await servicoMateriaPrima.delete(id);
    return id;
  },
);

const materiasPrimasSlice = createSlice({
  name: "materiasPrimas",
  initialState: {
    items: [],
    carregando: false,
    erro: null,
  },
  reducers: {
    adicionarMateriaPrima: (state, action) => {
      state.items.push(action.payload);
    },
    atualizarMateriaPrima: (state, action) => {
      const index = state.items.findIndex(
        (item) => item.id === action.payload.id,
      );
      if (index !== -1) {
        state.items[index] = action.payload;
      }
    },
    excluirMateriaPrima: (state, action) => {
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch materias primas
      .addCase(buscarMateriasPrimas.pending, (state) => {
        state.carregando = true;
        state.erro = null;
      })
      .addCase(buscarMateriasPrimas.fulfilled, (state, action) => {
        state.items = action.payload;
        state.carregando = false;
      })
      .addCase(buscarMateriasPrimas.rejected, (state, action) => {
        state.erro = action.error.message;
        state.carregando = false;
      })
      // Create materia prima
      .addCase(criarMateriaPrima.pending, (state) => {
        state.carregando = true;
        state.erro = null;
      })
      .addCase(criarMateriaPrima.fulfilled, (state, action) => {
        state.items.push(action.payload);
        state.carregando = false;
      })
      .addCase(criarMateriaPrima.rejected, (state, action) => {
        state.erro = action.error.message;
        state.carregando = false;
      })
      // Update materia prima
      .addCase(atualizarMateriaPrimaAsync.fulfilled, (state, action) => {
        const index = state.items.findIndex(
          (item) => item.id === action.payload.id,
        );
        if (index !== -1) {
          state.items[index] = action.payload;
        }
      })
      // Delete materia prima
      .addCase(excluirMateriaPrimaAsync.fulfilled, (state, action) => {
        state.items = state.items.filter((item) => item.id !== action.payload);
      });
  },
});

export const {
  adicionarMateriaPrima,
  atualizarMateriaPrima,
  excluirMateriaPrima,
} = materiasPrimasSlice.actions;

export default materiasPrimasSlice.reducer;
