import React from "react";
import { Link, useLocation } from "react-router-dom";

function Navigation() {
  const location = useLocation();

  return (
    <nav className="navbar">
      <h1>Inventory Management System</h1>
      <ul className="nav-links">
        <li>
          <Link to="/" className={location.pathname === "/" ? "active" : ""}>
            Home
          </Link>
        </li>
        <li>
          <Link
            to="/products"
            className={location.pathname.includes("/products") ? "active" : ""}
          >
            Products
          </Link>
        </li>
        <li>
          <Link
            to="/raw-materials"
            className={
              location.pathname.includes("/raw-materials") ? "active" : ""
            }
          >
            Raw Materials
          </Link>
        </li>
        <li>
          <Link
            to="/production"
            className={location.pathname === "/production" ? "active" : ""}
          >
            Production Report
          </Link>
        </li>
      </ul>
    </nav>
  );
}

export default Navigation;
