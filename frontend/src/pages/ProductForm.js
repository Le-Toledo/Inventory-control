import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import {
  createProduct,
  updateProduct,
  fetchProducts,
} from "../store/slices/productsSlice";
import { fetchRawMaterials } from "../store/slices/rawMaterialsSlice";

function ProductForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { items: products } = useSelector((state) => state.products);
  const { items: rawMaterials } = useSelector((state) => state.rawMaterials);

  const [formData, setFormData] = useState({
    name: "",
    value: "",
    rawMaterials: [],
  });

  const [selectedRawMaterial, setSelectedRawMaterial] = useState("");
  const [quantity, setQuantity] = useState("");

  useEffect(() => {
    dispatch(fetchRawMaterials());
    if (id) {
      dispatch(fetchProducts());
    }
  }, [dispatch, id]);

  useEffect(() => {
    if (id && products.length > 0) {
      const product = products.find((p) => p.id === parseInt(id));
      if (product) {
        setFormData({
          name: product.name,
          value: product.value,
          rawMaterials: product.rawMaterials || [],
        });
      }
    }
  }, [id, products]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (id) {
        await dispatch(updateProduct({ id: parseInt(id), product: formData }));
      } else {
        await dispatch(createProduct(formData));
      }
      navigate("/products");
    } catch (error) {
      alert("Error saving product: " + error.message);
    }
  };

  const handleAddRawMaterial = () => {
    if (!selectedRawMaterial || !quantity || quantity <= 0) {
      alert("Please select a raw material and enter a valid quantity");
      return;
    }

    const rawMaterial = rawMaterials.find(
      (rm) => rm.id === parseInt(selectedRawMaterial),
    );
    const newRawMaterial = {
      rawMaterialId: rawMaterial.id,
      rawMaterialName: rawMaterial.name,
      quantityRequired: parseInt(quantity),
    };

    setFormData({
      ...formData,
      rawMaterials: [...formData.rawMaterials, newRawMaterial],
    });

    setSelectedRawMaterial("");
    setQuantity("");
  };

  const handleRemoveRawMaterial = (index) => {
    setFormData({
      ...formData,
      rawMaterials: formData.rawMaterials.filter((_, i) => i !== index),
    });
  };

  return (
    <div className="container">
      <div className="card">
        <h2>{id ? "Edit Product" : "New Product"}</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Product Name *</label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) =>
                setFormData({ ...formData, name: e.target.value })
              }
              required
            />
          </div>

          <div className="form-group">
            <label>Value ($) *</label>
            <input
              type="number"
              step="0.01"
              value={formData.value}
              onChange={(e) =>
                setFormData({ ...formData, value: e.target.value })
              }
              required
            />
          </div>

          <div className="card" style={{ backgroundColor: "#f8f9fa" }}>
            <h3>Raw Materials</h3>

            <div
              style={{
                display: "flex",
                gap: "1rem",
                marginBottom: "1rem",
                flexWrap: "wrap",
              }}
            >
              <div style={{ flex: "1", minWidth: "200px" }}>
                <label>Select Raw Material</label>
                <select
                  value={selectedRawMaterial}
                  onChange={(e) => setSelectedRawMaterial(e.target.value)}
                >
                  <option value="">-- Select --</option>
                  {rawMaterials.map((rm) => (
                    <option key={rm.id} value={rm.id}>
                      {rm.name} (Stock: {rm.stockQuantity})
                    </option>
                  ))}
                </select>
              </div>

              <div style={{ flex: "0.5", minWidth: "150px" }}>
                <label>Quantity Required</label>
                <input
                  type="number"
                  value={quantity}
                  onChange={(e) => setQuantity(e.target.value)}
                  min="1"
                />
              </div>

              <div style={{ display: "flex", alignItems: "flex-end" }}>
                <button
                  type="button"
                  onClick={handleAddRawMaterial}
                  className="btn btn-success"
                >
                  Add
                </button>
              </div>
            </div>

            {formData.rawMaterials.length > 0 && (
              <div className="raw-materials-list">
                <h4>Added Raw Materials:</h4>
                {formData.rawMaterials.map((rm, index) => (
                  <div key={index} className="raw-material-item">
                    <span>
                      <strong>{rm.rawMaterialName}</strong> - Quantity:{" "}
                      {rm.quantityRequired}
                    </span>
                    <button
                      type="button"
                      onClick={() => handleRemoveRawMaterial(index)}
                      className="btn btn-danger"
                    >
                      Remove
                    </button>
                  </div>
                ))}
              </div>
            )}
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-success">
              {id ? "Update Product" : "Create Product"}
            </button>
            <button
              type="button"
              onClick={() => navigate("/products")}
              className="btn btn-secondary"
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default ProductForm;
