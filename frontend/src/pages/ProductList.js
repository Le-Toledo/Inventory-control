import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { fetchProducts, deleteProduct } from "../store/slices/productsSlice";

function ProductList() {
  const dispatch = useDispatch();
  const {
    items: produtos,
    carregando,
    erro,
  } = useSelector((state) => state.products);

  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);

  const handleExcluir = async (id) => {
    if (window.confirm("Tem certeza que deseja excluir este produto?")) {
      await dispatch(deleteProduct(id));
      dispatch(fetchProducts());
    }
  };

  if (carregando) return <div className="loading">Carregando produtos...</div>;
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
            <h2 style={{ marginBottom: "0.5rem" }}>📦 Produtos</h2>
            <p style={{ color: "#7f8c8d", margin: 0 }}>
              {produtos.length} produto(s) cadastrado(s)
            </p>
          </div>
          <Link to="/products/new" className="btn btn-success">
            ➕ Adicionar Novo Produto
          </Link>
        </div>

        {produtos.length === 0 ? (
          <div
            style={{
              textAlign: "center",
              padding: "3rem",
              background: "rgba(102, 126, 234, 0.05)",
              borderRadius: "15px",
            }}
          >
            <div style={{ fontSize: "4rem", marginBottom: "1rem" }}>📦</div>
            <h3 style={{ color: "#2c3e50", marginBottom: "0.5rem" }}>
              Nenhum produto cadastrado
            </h3>
            <p style={{ color: "#7f8c8d", marginBottom: "1.5rem" }}>
              Comece criando seu primeiro produto!
            </p>
            <Link to="/products/new" className="btn btn-success">
              ➕ Criar Primeiro Produto
            </Link>
          </div>
        ) : (
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>🆔 ID</th>
                  <th>📦 Nome</th>
                  <th>💰 Valor</th>
                  <th>🧪 Matérias-Primas</th>
                  <th>⚙️ Ações</th>
                </tr>
              </thead>
              <tbody>
                {produtos.map((produto) => (
                  <tr key={produto.id}>
                    <td>
                      <strong>#{produto.id}</strong>
                    </td>
                    <td style={{ fontWeight: 600 }}>{produto.name}</td>
                    <td style={{ color: "#27ae60", fontWeight: 600 }}>
                      R${" "}
                      {parseFloat(produto.value).toFixed(2).replace(".", ",")}
                    </td>
                    <td>
                      <span
                        style={{
                          background: "rgba(102, 126, 234, 0.1)",
                          padding: "0.3rem 0.8rem",
                          borderRadius: "15px",
                          fontSize: "0.9rem",
                          fontWeight: 600,
                        }}
                      >
                        {produto.rawMaterials?.length || 0} material(is)
                      </span>
                    </td>
                    <td className="table-actions">
                      <Link
                        to={`/products/edit/${produto.id}`}
                        className="btn btn-primary"
                        style={{ fontSize: "0.85rem", padding: "0.5rem 1rem" }}
                      >
                        ✏️ Editar
                      </Link>
                      <button
                        onClick={() => handleExcluir(produto.id)}
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

export default ProductList;
