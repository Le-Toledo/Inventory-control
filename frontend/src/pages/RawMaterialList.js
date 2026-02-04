import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  fetchRawMaterials,
  deleteRawMaterial,
} from "../store/slices/rawMaterialsSlice";

function RawMaterialList() {
  const dispatch = useDispatch();
  const {
    items: rawMaterials,
    loading,
    error,
  } = useSelector((state) => state.rawMaterials);

  useEffect(() => {
    dispatch(fetchRawMaterials());
  }, [dispatch]);

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this raw material?")) {
      await dispatch(deleteRawMaterial(id));
      dispatch(fetchRawMaterials());
    }
  };

  if (loading) return <div className="loading">Loading raw materials...</div>;
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
          <h2>Raw Materials</h2>
          <Link to="/raw-materials/new" className="btn btn-primary">
            Add New Raw Material
          </Link>
        </div>

        {rawMaterials.length === 0 ? (
          <p>No raw materials found. Create your first raw material!</p>
        ) : (
          <div className="table-container">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Stock Quantity</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {rawMaterials.map((material) => (
                  <tr key={material.id}>
                    <td>{material.id}</td>
                    <td>{material.name}</td>
                    <td>{material.stockQuantity}</td>
                    <td className="table-actions">
                      <Link
                        to={`/raw-materials/edit/${material.id}`}
                        className="btn btn-primary"
                      >
                        Edit
                      </Link>
                      <button
                        onClick={() => handleDelete(material.id)}
                        className="btn btn-danger"
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
}

export default RawMaterialList;
