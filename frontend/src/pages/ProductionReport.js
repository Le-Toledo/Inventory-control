import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { calculateProduction } from "../store/slices/productionSlice";

function ProductionReport() {
  const dispatch = useDispatch();
  const {
    report: relatorio,
    carregando,
    erro,
  } = useSelector((state) => state.production);

  useEffect(() => {
    dispatch(calculateProduction());
  }, [dispatch]);

  const handleAtualizar = () => {
    dispatch(calculateProduction());
  };

  if (carregando) return <div className="loading">Calculando produção...</div>;
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
          }}
        >
          <h2>Relatório de Produção</h2>
          <button onClick={handleAtualizar} className="btn btn-primary">
            Atualizar Relatório
          </button>
        </div>

        {relatorio && (
          <>
            <div className="production-summary">
              <h3>Valor Total de Produção</h3>
              <p>R$ {relatorio.totalValue?.toFixed(2) || "0,00"}</p>
            </div>

            {relatorio.suggestions && relatorio.suggestions.length > 0 ? (
              <div className="table-container">
                <table>
                  <thead>
                    <tr>
                      <th>Produto</th>
                      <th>Valor Unitário</th>
                      <th>Custo Unitário</th>
                      <th>Quantidade</th>
                      <th>Custo Total</th>
                      <th>Valor Total</th>
                      <th>Lucro</th>
                      <th>Margem</th>
                    </tr>
                  </thead>
                  <tbody>
                    {relatorio.suggestions.map((sugestao) => (
                      <tr key={sugestao.productId}>
                        <td>{sugestao.productName}</td>
                        <td>
                          R${" "}
                          {parseFloat(sugestao.productValue || 0)
                            .toFixed(2)
                            .replace(".", ",")}
                        </td>
                        <td>
                          R${" "}
                          {parseFloat(sugestao.unitCost || 0)
                            .toFixed(2)
                            .replace(".", ",")}
                        </td>
                        <td>{sugestao.quantityCanProduce}</td>
                        <td>
                          R${" "}
                          {parseFloat(sugestao.totalCost || 0)
                            .toFixed(2)
                            .replace(".", ",")}
                        </td>
                        <td>
                          <strong>
                            R${" "}
                            {parseFloat(sugestao.totalValue || 0)
                              .toFixed(2)
                              .replace(".", ",")}
                          </strong>
                        </td>
                        <td
                          style={{
                            color:
                              (sugestao.profit || 0) >= 0 ? "green" : "red",
                            fontWeight: "bold",
                          }}
                        >
                          R${" "}
                          {parseFloat(sugestao.profit || 0)
                            .toFixed(2)
                            .replace(".", ",")}
                        </td>
                        <td
                          style={{
                            color:
                              (sugestao.profitMargin || 0) >= 0
                                ? "green"
                                : "red",
                            fontWeight: "bold",
                          }}
                        >
                          {parseFloat(sugestao.profitMargin || 0)
                            .toFixed(2)
                            .replace(".", ",")}
                          %
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            ) : (
              <div
                style={{
                  textAlign: "center",
                  padding: "2rem",
                  backgroundColor: "#f8f9fa",
                  borderRadius: "8px",
                }}
              >
                <p>
                  Nenhum produto pode ser produzido com os níveis de estoque
                  atuais.
                </p>
                <p style={{ marginTop: "1rem", color: "#7f8c8d" }}>
                  Por favor, adicione matérias-primas ao seu inventário ou crie
                  produtos com os materiais disponíveis.
                </p>
              </div>
            )}

            <div
              style={{
                marginTop: "2rem",
                padding: "1rem",
                backgroundColor: "#e8f4f8",
                borderRadius: "8px",
              }}
            >
              <h3>Sobre este relatório:</h3>
              <ul style={{ marginLeft: "1.5rem", marginTop: "0.5rem" }}>
                <li>Produtos são priorizados pelo maior valor primeiro</li>
                <li>
                  Cálculos são baseados nos níveis atuais de estoque de
                  matérias-primas
                </li>
                <li>
                  Matérias-primas são alocadas de forma otimizada para maximizar
                  o valor total de produção
                </li>
              </ul>
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default ProductionReport;
