import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navigation from "./components/Navigation";
import Home from "./pages/Home";
import ProductList from "./pages/ProductList";
import ProductForm from "./pages/ProductForm";
import RawMaterialList from "./pages/RawMaterialList";
import RawMaterialForm from "./pages/RawMaterialForm";
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
        <Route path="/raw-materials" element={<RawMaterialList />} />
        <Route path="/raw-materials/new" element={<RawMaterialForm />} />
        <Route path="/raw-materials/edit/:id" element={<RawMaterialForm />} />
        <Route path="/production" element={<ProductionReport />} />
      </Routes>
    </Router>
  );
}

export default App;
