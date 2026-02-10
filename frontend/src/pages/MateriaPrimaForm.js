import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import {
  criarMateriaPrima,
  atualizarMateriaPrimaAsync,
  buscarMateriasPrimas,
} from "../store/slices/materiasPrimasSlice";

function MateriaPrimaForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { items: materiasPrimas } = useSelector(
    (state) => state.materiasPrimas,
  );

  const [dadosFormulario, setDadosFormulario] = useState({
    name: "",
    stockQuantity: "",
    unitCost: "",
    unit: "QUANTITY",
  });

  const normalizeDecimalInput = (value) =>
    String(value ?? "")
      .trim()
      .replace(",", ".");

  const parseUnitCost = (value) => {
    const normalized = normalizeDecimalInput(value);
    const parsed = parseFloat(normalized);
    return Number.isNaN(parsed) ? null : parsed;
  };

  useEffect(() => {
    if (id) {
      dispatch(buscarMateriasPrimas());
    }
  }, [dispatch, id]);

  useEffect(() => {
    if (id && materiasPrimas.length > 0) {
      const materiaPrima = materiasPrimas.find((mp) => mp.id === parseInt(id));
      if (materiaPrima) {
        setDadosFormulario({
          name: materiaPrima.name,
          stockQuantity: materiaPrima.stockQuantity,
          unitCost: materiaPrima.unitCost || 0,
          unit: materiaPrima.unit || "QUANTITY",
        });
      }
    }
  }, [id, materiasPrimas]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const unitCost = parseUnitCost(dadosFormulario.unitCost);
    if (unitCost === null) {
      alert("Informe um custo unitario valido");
      return;
    }

    const dadosParaEnviar = {
      name: dadosFormulario.name,
      stockQuantity: parseInt(dadosFormulario.stockQuantity),
      unitCost,
      unit: dadosFormulario.unit,
    };

    try {
      if (id) {
        await dispatch(
          atualizarMateriaPrimaAsync({
            id: parseInt(id),
            data: dadosParaEnviar,
          }),
        );
      } else {
        await dispatch(criarMateriaPrima(dadosParaEnviar));
      }
      navigate("/materias-primas");
    } catch (erro) {
      alert("Erro ao salvar matéria-prima: " + erro.message);
    }
  };

  return (
    <div className="container">
      <div className="card">
        <h2>{id ? "Editar Matéria-Prima" : "Nova Matéria-Prima"}</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nome </label>
            <input
              type="text"
              value={dadosFormulario.name}
              onChange={(e) =>
                setDadosFormulario({ ...dadosFormulario, name: e.target.value })
              }
              required
            />
          </div>

          <div className="form-group">
            <label>Quantidade em Estoque </label>
            <input
              type="number"
              value={dadosFormulario.stockQuantity}
              onChange={(e) =>
                setDadosFormulario({
                  ...dadosFormulario,
                  stockQuantity: e.target.value,
                })
              }
              min="0"
              required
            />
          </div>

          <div className="form-group">
            <label>Unidade de Medida </label>
            <select
              value={dadosFormulario.unit}
              onChange={(e) =>
                setDadosFormulario({ ...dadosFormulario, unit: e.target.value })
              }
              required
            >
              <option value="QUANTITY">Quantidade</option>
              <option value="WEIGHT">Peso (Kg)</option>
              <option value="LITERS">Litros</option>
            </select>
          </div>

          <div className="form-group">
            <label>Custo Unitário (R$) </label>
            <input
              type="text"
              inputMode="decimal"
              step="0.01"
              value={dadosFormulario.unitCost}
              onChange={(e) =>
                setDadosFormulario({
                  ...dadosFormulario,
                  unitCost: e.target.value,
                })
              }
              min="0"
              required
            />
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-success">
              {id ? "Atualizar" : "Criar"}
            </button>
            <button
              type="button"
              onClick={() => navigate("/materias-primas")}
              className="btn btn-secondary"
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default MateriaPrimaForm;
