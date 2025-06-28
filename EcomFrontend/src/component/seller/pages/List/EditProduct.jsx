import React, { useState, useEffect } from "react";
import { useLocation, useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import './EditProfile.css'


function EditProduct() {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();

  const [product, setProduct] = useState(location.state?.product || {
    title: "",
    description: "",
    sellingPrice: "",
    imageUrl: "",
    categoryNames: [],
  });

  useEffect(() => {
    if (!location.state) {
      axios
        .get(`http://localhost:8080/api/product/${id}`, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        })
        .then((res) => setProduct(res.data))
        .catch((err) => console.log(err));
    }
  }, [id, location.state]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleCategoryChange = (e) => {
    setProduct((prev) => ({
      ...prev,
      categoryNames: [e.target.value],
    }));
  };

  const handleUpdate = async () => {
    try {
      await axios.put(
        `http://localhost:8080/api/product/update/${id}`,
        product,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        }
      );
      alert("Product updated successfully!");
      navigate("/seller/listproducts");
    } catch (err) {
      console.error("Update failed:", err);
      alert("Failed to update product.");
    }
  };

  return (
    <div className="edit-container">
      <h2 className="edit-title">Edit Product</h2>

      <div className="edit-form-group">
        <label>Title</label>
        <input
          type="text"
          name="title"
          value={product.title}
          onChange={handleChange}
        />
      </div>

      <div className="edit-form-group">
        <label>Description</label>
        <textarea
          name="description"
          value={product.description}
          onChange={handleChange}
          rows={4}
        />
      </div>

      <div className="edit-form-group">
        <label>Selling Price</label>
        <input
          type="number"
          name="sellingPrice"
          value={product.sellingPrice}
          onChange={handleChange}
        />
      </div>

      <div className="edit-form-group">
        <label>Image URL</label>
        <input
          type="text"
          name="imageUrl"
          value={product.imageUrl}
          onChange={handleChange}
        />
      </div>

      <div className="edit-form-group">
        <label>Category</label>
        <select
          value={product.categoryNames?.[0] || ""}
          onChange={handleCategoryChange}
        >
          <option value="">-- Select Category --</option>
          <option value="Mobiles">Mobiles</option>
          <option value="Laptops">Laptops</option>
        </select>
      </div>

      <button className="edit-button" onClick={handleUpdate}>
        Update Product
      </button>
    </div>
  );
}

export default EditProduct;
