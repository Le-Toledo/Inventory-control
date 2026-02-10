import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  buscarMateriasPrimas,
  excluirMateriaPrimaAsync,
} from "../store/slices/materiasPrimasSlice";

function MateriaPrimaList() {
  const dispatch = useDispatch();
  const {
    items: materiasPrimas,
    carregando,
    erro,
  } = useSelector((state) => state.materiasPrimas);

  const obterRotuloUnidade = (unidade) => {
    const unidades = {
      QUANTITY: "Un",
      WEIGHT: "Kg",
      LITERS: "L",
    };
    return unidades[unidade] || "Un";
  };

  useEffect(() => {
    dispatch(buscarMateriasPrimas());
  }, [dispatch]);

  const handleExcluir = async (id) => {
    if (window.confirm("Tem certeza que deseja excluir esta matéria-prima?")) {
      await dispatch(excluirMateriaPrimaAsync(id));
      dispatch(buscarMateriasPrimas());
    }
  };

  if (carregando)
    return <div className="loading">Carregando matérias-primas...</div>;
  if (erro) return <div className="error">Erro: {erro}</div>;

  return (
    <div className="container">
      <div className="card">
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            marginBottom: "1.5rem",
            flexWrap: "wrap",
            gap: "1rem",
          }}
        >
          <div>
            <h2 style={{ marginBottom: "0.5rem" }}>🧪 Matérias-Primas</h2>
            <p style={{ color: "#7f8c8d", margin: 0 }}>
              {materiasPrimas.length} matéria(s)-prima(s) cadastrada(s)
            </p>
          </div>
          <Link to="/materias-primas/new" className="btn btn-success">
            ➕ Adicionar Nova Matéria-Prima
          </Link>
        </div>

        {materiasPrimas.length === 0 ? (
          <div
            style={{
              textAlign: "center",
              padding: "3rem",
              background: "rgba(240, 147, 251, 0.05)",
              borderRadius: "15px",
            }}
          >
            <div style={{ fontSize: "4rem", marginBottom: "1rem" }}>🧪</div>
            <h3 style={{ color: "#2c3e50", marginBottom: "0.5rem" }}>
              Nenhuma matéria-prima cadastrada
            </h3>
            <p style={{ color: "#7f8c8d", marginBottom: "1.5rem" }}>
              Comece cadastrando suas matérias-primas!
            </p>
            <Link to="/materias-primas/new" className="btn btn-success">
              ➕ Criar Primeira Matéria-Prima
            </Link>
          </div>
        ) : (
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>🆔 ID</th>
                  <th>🧪 Nome</th>
                  <th>📊 Estoque</th>
                  <th>⚖️ Unidade</th>
                  <th>💰 Custo Unitário</th>
                  <th>⚙️ Ações</th>
                </tr>
              </thead>
              <tbody>
                {materiasPrimas.map((materiaPrima) => (
                  <tr key={materiaPrima.id}>
                    <td>
                      <strong>#{materiaPrima.id}</strong>
                    </td>
                    <td style={{ fontWeight: 600 }}>{materiaPrima.name}</td>
                    <td>
                      <span
                        style={{
                          background:
                            materiaPrima.stockQuantity > 50
                              ? "rgba(86, 171, 47, 0.1)"
                              : materiaPrima.stockQuantity > 20
                                ? "rgba(255, 193, 7, 0.1)"
                                : "rgba(235, 59, 90, 0.1)",
                          color:
                            materiaPrima.stockQuantity > 50
                              ? "#56ab2f"
                              : materiaPrima.stockQuantity > 20
                                ? "#ff9800"
                                : "#eb3b5a",
                          padding: "0.3rem 0.8rem",
                          borderRadius: "15px",
                          fontSize: "0.9rem",
                          fontWeight: 700,
                        }}
                      >
                        {materiaPrima.stockQuantity}
                      </span>
                    </td>
                    <td>
                      <span
                        style={{
                          background: "rgba(79, 172, 254, 0.1)",
                          padding: "0.3rem 0.8rem",
                          borderRadius: "15px",
                          fontSize: "0.9rem",
                          fontWeight: 600,
                        }}
                      >
                        {obterRotuloUnidade(materiaPrima.unit)}
                      </span>
                    </td>
                    <td style={{ color: "#e74c3c", fontWeight: 600 }}>
                      R${" "}
                      {parseFloat(materiaPrima.unitCost || 0)
                        .toFixed(2)
                        .replace(".", ",")}
                    </td>
                    <td className="table-actions">
                      <Link
                        to={`/materias-primas/edit/${materiaPrima.id}`}
                        className="btn btn-primary"
                        style={{ fontSize: "0.85rem", padding: "0.5rem 1rem" }}
                      >
                        ✏️ Editar
                      </Link>
                      <button
                        onClick={() => handleExcluir(materiaPrima.id)}
                        className="btn btn-danger"
                        style={{ fontSize: "0.85rem", padding: "0.5rem 1rem" }}
                      >
                        🗑️ Excluir
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

export default MateriaPrimaList;
