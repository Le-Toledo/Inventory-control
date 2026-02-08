import React from "react";
import { Link } from "react-router-dom";

function Home() {
  return (
    <div className="container">
      <div className="card" style={{ textAlign: "center" }}>
        <h2 style={{ fontSize: "2.5rem", marginBottom: "1rem" }}>
          🎯 Sistema de Gestão de Estoque
        </h2>
        <p
          style={{ fontSize: "1.2rem", color: "#7f8c8d", marginBottom: "3rem" }}
        >
          Gerencie seus produtos, matérias-primas e calcule possibilidades de
          produção de forma inteligente e otimizada.
        </p>

        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fit, minmax(250px, 1fr))",
            gap: "2rem",
            marginTop: "2rem",
          }}
        >
          <Link to="/products" style={{ textDecoration: "none" }}>
            <div
              className="feature-card"
              style={{
                background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
                padding: "2rem",
                borderRadius: "20px",
                color: "white",
                transition: "all 0.3s ease",
                cursor: "pointer",
                boxShadow: "0 10px 30px rgba(102, 126, 234, 0.3)",
              }}
            >
              <div style={{ fontSize: "3rem", marginBottom: "1rem" }}>📦</div>
              <h3 style={{ marginBottom: "0.5rem", fontSize: "1.5rem" }}>
                Produtos
              </h3>
              <p style={{ fontSize: "0.95rem", opacity: 0.9 }}>
                Gerencie seu catálogo de produtos com preços e composição
              </p>
            </div>
          </Link>

          <Link to="/raw-materials" style={{ textDecoration: "none" }}>
            <div
              className="feature-card"
              style={{
                background: "linear-gradient(135deg, #f093fb 0%, #f5576c 100%)",
                padding: "2rem",
                borderRadius: "20px",
                color: "white",
                transition: "all 0.3s ease",
                cursor: "pointer",
                boxShadow: "0 10px 30px rgba(240, 147, 251, 0.3)",
              }}
            >
              <div style={{ fontSize: "3rem", marginBottom: "1rem" }}>🧪</div>
              <h3 style={{ marginBottom: "0.5rem", fontSize: "1.5rem" }}>
                Matérias-Primas
              </h3>
              <p style={{ fontSize: "0.95rem", opacity: 0.9 }}>
                Controle seu estoque com custos e unidades de medida
              </p>
            </div>
          </Link>

          <Link to="/production" style={{ textDecoration: "none" }}>
            <div
              className="feature-card"
              style={{
                background: "linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)",
                padding: "2rem",
                borderRadius: "20px",
                color: "white",
                transition: "all 0.3s ease",
                cursor: "pointer",
                boxShadow: "0 10px 30px rgba(79, 172, 254, 0.3)",
              }}
            >
              <div style={{ fontSize: "3rem", marginBottom: "1rem" }}>📊</div>
              <h3 style={{ marginBottom: "0.5rem", fontSize: "1.5rem" }}>
                Relatório de Produção
              </h3>
              <p style={{ fontSize: "0.95rem", opacity: 0.9 }}>
                Análise de custos, lucros e capacidade produtiva
              </p>
            </div>
          </Link>
        </div>

        <div
          style={{
            marginTop: "4rem",
            padding: "2rem",
            background: "rgba(102, 126, 234, 0.1)",
            borderRadius: "20px",
          }}
        >
          <h3 style={{ marginBottom: "1rem", color: "#2c3e50" }}>
            ✨ Recursos Principais
          </h3>
          <div
            style={{
              display: "grid",
              gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
              gap: "1.5rem",
              textAlign: "left",
              marginTop: "1.5rem",
            }}
          >
            <div>
              <strong>💰 Controle Financeiro</strong>
              <p
                style={{
                  fontSize: "0.9rem",
                  color: "#7f8c8d",
                  marginTop: "0.5rem",
                }}
              >
                Custos unitários e totais por produto
              </p>
            </div>
            <div>
              <strong>📈 Análise de Lucro</strong>
              <p
                style={{
                  fontSize: "0.9rem",
                  color: "#7f8c8d",
                  marginTop: "0.5rem",
                }}
              >
                Margem de lucro e rentabilidade
              </p>
            </div>
            <div>
              <strong>⚖️ Unidades Customizadas</strong>
              <p
                style={{
                  fontSize: "0.9rem",
                  color: "#7f8c8d",
                  marginTop: "0.5rem",
                }}
              >
                Peso, quantidade ou litros
              </p>
            </div>
            <div>
              <strong>🎯 Otimização</strong>
              <p
                style={{
                  fontSize: "0.9rem",
                  color: "#7f8c8d",
                  marginTop: "0.5rem",
                }}
              >
                Maximize o valor da produção
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
