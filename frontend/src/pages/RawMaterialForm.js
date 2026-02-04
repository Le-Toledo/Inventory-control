import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import {
  createRawMaterial,
  updateRawMaterial,
  fetchRawMaterials,
} from "../store/slices/rawMaterialsSlice";

function RawMaterialForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { items: rawMaterials } = useSelector((state) => state.rawMaterials);

  const [formData, setFormData] = useState({
    name: "",
    stockQuantity: "",
  });

  useEffect(() => {
    if (id) {
      dispatch(fetchRawMaterials());
    }
  }, [dispatch, id]);

  useEffect(() => {
    if (id && rawMaterials.length > 0) {
      const material = rawMaterials.find((rm) => rm.id === parseInt(id));
      if (material) {
        setFormData({
          name: material.name,
          stockQuantity: material.stockQuantity,
        });
      }
    }
  }, [id, rawMaterials]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (id) {
        await dispatch(
          updateRawMaterial({ id: parseInt(id), rawMaterial: formData }),
        );
      } else {
        await dispatch(createRawMaterial(formData));
      }
      navigate("/raw-materials");
    } catch (error) {
      alert("Error saving raw material: " + error.message);
    }
  };

  return (
    <div className="container">
      <div className="card">
        <h2>{id ? "Edit Raw Material" : "New Raw Material"}</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Name *</label>
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
            <label>Stock Quantity *</label>
            <input
              type="number"
              value={formData.stockQuantity}
              onChange={(e) =>
                setFormData({ ...formData, stockQuantity: e.target.value })
              }
              min="0"
              required
            />
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-success">
              {id ? "Update Raw Material" : "Create Raw Material"}
            </button>
            <button
              type="button"
              onClick={() => navigate("/raw-materials")}
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

export default RawMaterialForm;
