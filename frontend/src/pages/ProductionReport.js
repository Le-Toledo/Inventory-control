import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { calculateProduction } from "../store/slices/productionSlice";

function ProductionReport() {
  const dispatch = useDispatch();
  const { report, loading, error } = useSelector((state) => state.production);

  useEffect(() => {
    dispatch(calculateProduction());
  }, [dispatch]);

  const handleRefresh = () => {
    dispatch(calculateProduction());
  };

  if (loading) return <div className="loading">Calculating production...</div>;
  if (error) return <div className="error">Error: {error}</div>;

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
          <h2>Production Report</h2>
          <button onClick={handleRefresh} className="btn btn-primary">
            Refresh Report
          </button>
        </div>

        {report && (
          <>
            <div className="production-summary">
              <h3>Total Production Value</h3>
              <p>${report.totalValue?.toFixed(2) || "0.00"}</p>
            </div>

            {report.suggestions && report.suggestions.length > 0 ? (
              <div className="table-container">
                <table>
                  <thead>
                    <tr>
                      <th>Product</th>
                      <th>Unit Value</th>
                      <th>Quantity Can Produce</th>
                      <th>Total Value</th>
                    </tr>
                  </thead>
                  <tbody>
                    {report.suggestions.map((suggestion) => (
                      <tr key={suggestion.productId}>
                        <td>{suggestion.productName}</td>
                        <td>${suggestion.productValue?.toFixed(2)}</td>
                        <td>{suggestion.quantityCanProduce}</td>
                        <td>
                          <strong>${suggestion.totalValue?.toFixed(2)}</strong>
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
                <p>No products can be produced with current stock levels.</p>
                <p style={{ marginTop: "1rem", color: "#7f8c8d" }}>
                  Please add raw materials to your inventory or create products
                  with available materials.
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
              <h3>About this report:</h3>
              <ul style={{ marginLeft: "1.5rem", marginTop: "0.5rem" }}>
                <li>Products are prioritized by highest value first</li>
                <li>
                  Calculations are based on current raw material stock levels
                </li>
                <li>
                  Raw materials are allocated optimally to maximize total
                  production value
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
