import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navigation from "./components/Navigation";
import Home from "./pages/Home";
import ProductList from "./pages/ProductList";
import ProductForm from "./pages/ProductForm";
import MateriaPrimaList from "./pages/MateriaPrimaList";
import MateriaPrimaForm from "./pages/MateriaPrimaForm";
import ProductionReport from "./pages/ProductionReport";

function App() {
  return (
    <Router>
      <Navigation />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/products" element={<ProductList />} />
        <Route path="/products/new" element={<ProductForm />} />
        <Route path="/products/edit/:id" element={<ProductForm />} />
        <Route path="/materias-primas" element={<MateriaPrimaList />} />
        <Route path="/materias-primas/new" element={<MateriaPrimaForm />} />
        <Route path="/materias-primas/edit/:id" element={<MateriaPrimaForm />} />
        <Route path="/production" element={<ProductionReport />} />
      </Routes>
    </Router>
  );
}

export default App;

