import React from "react";
import { Link, useLocation } from "react-router-dom";

function Navigation() {
  const location = useLocation();

  return (
    <nav className="navbar">
      <h1>Sistema de Gestão de Estoque</h1>
      <ul className="nav-links">
        <li>
          <Link to="/" className={location.pathname === "/" ? "active" : ""}>
            Início
          </Link>
        </li>
        <li>
          <Link
            to="/products"
            className={location.pathname.includes("/products") ? "active" : ""}
          >
            Produtos
          </Link>
        </li>
        <li>
          <Link
            to="/raw-materials"
            className={
              location.pathname.includes("/raw-materials") ? "active" : ""
            }
          >
            Matérias-Primas
          </Link>
        </li>
        <li>
          <Link
            to="/production"
            className={location.pathname === "/production" ? "active" : ""}
          >
            Relatório de Produção
          </Link>
        </li>
      </ul>
    </nav>
  );
}

export default Navigation;
