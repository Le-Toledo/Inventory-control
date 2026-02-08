import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import {
  createProduct,
  updateProduct,
  fetchProducts,
} from "../store/slices/productsSlice";
import { buscarMateriasPrimas } from "../store/slices/rawMaterialsSlice";

function ProductForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { items: produtos } = useSelector((state) => state.products);
  const { items: materiasPrimas } = useSelector(
    (state) => state.materiasPrimas,
  );

  const [dadosFormulario, setDadosFormulario] = useState({
    name: "",
    value: "",
    materiasPrimas: [],
  });

  const [materiaPrimaSelecionada, setMateriaPrimaSelecionada] = useState("");
  const [quantidade, setQuantidade] = useState("");

  useEffect(() => {
    dispatch(buscarMateriasPrimas());
    if (id) {
      dispatch(fetchProducts());
    }
  }, [dispatch, id]);

  useEffect(() => {
    if (id && produtos.length > 0) {
      const produto = produtos.find((p) => p.id === parseInt(id));
      if (produto) {
        setDadosFormulario({
          name: produto.name,
          value: produto.value,
          materiasPrimas: produto.rawMaterials || [],
        });
      }
    }
  }, [id, produtos]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Converter materiasPrimas para o formato esperado pela API
      const dadosParaAPI = {
        name: dadosFormulario.name,
        value: dadosFormulario.value,
        rawMaterials: dadosFormulario.materiasPrimas.map((mp) => ({
          rawMaterialId: mp.idMateriaPrima,
          rawMaterialName: mp.nomeMateriaPrima,
          quantityRequired: mp.quantidadeNecessaria,
        })),
      };

      if (id) {
        await dispatch(
          updateProduct({ id: parseInt(id), product: dadosParaAPI }),
        );
      } else {
        await dispatch(createProduct(dadosParaAPI));
      }
      navigate("/products");
    } catch (erro) {
      alert("Erro ao salvar produto: " + erro.message);
    }
  };

  const handleAdicionarMateriaPrima = () => {
    if (!materiaPrimaSelecionada || !quantidade || quantidade <= 0) {
      alert(
        "Por favor, selecione uma matéria-prima e insira uma quantidade válida",
      );
      return;
    }

    const materiaPrima = materiasPrimas.find(
      (mp) => mp.id === parseInt(materiaPrimaSelecionada),
    );
    const novaMateriaPrima = {
      idMateriaPrima: materiaPrima.id,
      nomeMateriaPrima: materiaPrima.name,
      quantidadeNecessaria: parseInt(quantidade),
    };

    setDadosFormulario({
      ...dadosFormulario,
      materiasPrimas: [...dadosFormulario.materiasPrimas, novaMateriaPrima],
    });

    setMateriaPrimaSelecionada("");
    setQuantidade("");
  };

  const handleRemoverMateriaPrima = (index) => {
    setDadosFormulario({
      ...dadosFormulario,
      materiasPrimas: dadosFormulario.materiasPrimas.filter(
        (_, i) => i !== index,
      ),
    });
  };

  return (
    <div className="container">
      <div className="card">
        <h2>{id ? "Editar Produto" : "Novo Produto"}</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nome do Produto </label>
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
            <label>Valor (R$) </label>
            <input
              type="number"
              step="0.01"
              value={dadosFormulario.value}
              onChange={(e) =>
                setDadosFormulario({
                  ...dadosFormulario,
                  value: e.target.value,
                })
              }
              required
            />
          </div>

          <div className="card" style={{ backgroundColor: "#f8f9fa" }}>
            <h3>Matérias-Primas</h3>

            <div
              style={{
                display: "flex",
                gap: "1rem",
                marginBottom: "1rem",
                flexWrap: "wrap",
              }}
            >
              <div style={{ flex: "1", minWidth: "200px" }}>
                <label>Selecionar Matéria-Prima</label>
                <select
                  value={materiaPrimaSelecionada}
                  onChange={(e) => setMateriaPrimaSelecionada(e.target.value)}
                >
                  <option value="">-- Selecione --</option>
                  {materiasPrimas.map((mp) => (
                    <option key={mp.id} value={mp.id}>
                      {mp.name} (Estoque: {mp.stockQuantity})
                    </option>
                  ))}
                </select>
              </div>

              <div style={{ flex: "0.5", minWidth: "150px" }}>
                <label>Quantidade Necessária</label>
                <input
                  type="number"
                  value={quantidade}
                  onChange={(e) => setQuantidade(e.target.value)}
                  min="1"
                />
              </div>

              <div style={{ display: "flex", alignItems: "flex-end" }}>
                <button
                  type="button"
                  onClick={handleAdicionarMateriaPrima}
                  className="btn btn-success"
                >
                  Adicionar
                </button>
              </div>
            </div>

            {dadosFormulario.materiasPrimas.length > 0 && (
              <div className="materias-primas-lista">
                <h4>Matérias-Primas Adicionadas:</h4>
                {dadosFormulario.materiasPrimas.map((mp, index) => (
                  <div key={index} className="materia-prima-item">
                    <span>
                      <strong>{mp.nomeMateriaPrima}</strong> - Quantidade:{" "}
                      {mp.quantidadeNecessaria}
                    </span>
                    <button
                      type="button"
                      onClick={() => handleRemoverMateriaPrima(index)}
                      className="btn btn-danger"
                    >
                      Remover
                    </button>
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-success">
              {id ? "Atualizar" : "Criar"} Produto
            </button>
            <button
              type="button"
              onClick={() => navigate("/products")}
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

export default ProductForm;
