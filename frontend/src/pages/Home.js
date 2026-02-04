import React from "react";

function Home() {
  return (
    <div className="container">
      <div className="card">
        <h2>Welcome to Inventory Management System</h2>
        <p>
          This system allows you to manage products, raw materials, and
          calculate production possibilities based on available stock.
        </p>
        <h3>Features:</h3>
        <ul>
          <li>
            Manage Products - Create, read, update, and delete products with
            their values
          </li>
          <li>Manage Raw Materials - Control inventory of raw materials</li>
          <li>
            Associate Raw Materials - Link raw materials to products with
            required quantities
          </li>
          <li>
            Production Report - Calculate which products can be produced with
            available stock
          </li>
        </ul>
        <p style={{ marginTop: "2rem" }}>
          Use the navigation menu above to get started!
        </p>
      </div>
    </div>
  );
}

export default Home;
