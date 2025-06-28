import axios from "axios";
import { useState } from "react";
import "./AddProduct.css";
import React from "react";

function AddProduct() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [mrpPrice, setMrpPrice] = useState("");
  const [sellingPrice, setSellingPrice] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [color, setColor] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  // const [category, setCategory] = useState("");
  const [msg, setMsg] = useState("");

  const AddProduct = async () => {
    const token = localStorage.getItem("token");
    try {
      await axios.post(
        "http://localhost:8080/api/product/add",
        {
          product: {
            title,
            description,
            mrpPrice,
            sellingPrice,
            imageUrl,
            color,
          },
          categoryNames: [selectedCategory],
        },
        {
          headers: {
            Authorization: "Bearer " + token,
            "Content-Type": "application/json",
          },
        }
      );

      setMsg("Product added successfully!");
    } catch (err) {
      console.error(err);
      setMsg("Operation Failed, Try again");
    }
  };

  return (
    <div className="container mt-4">
      <div className="row">
        <div className="card p-4 shadow">
          <h2 className="mb-3">Add New Product</h2>

          <div className="mb-3">
            <label>Product Title</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="form-control required:"
            />
          </div>

          <div className="mb-3">
            <label>Description</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="form-control"
              rows="3"
            />
          </div>

          <div className="mb-3">
            <label>MRP Price</label>
            <input
              type="number"
              value={mrpPrice}
              onChange={(e) => setMrpPrice(e.target.value)}
              className="form-control"
            />
          </div>

          <div className="mb-3">
            <label>Selling Price</label>
            <input
              type="number"
              value={sellingPrice}
              onChange={(e) => setSellingPrice(e.target.value)}
              className="form-control"
            />
          </div>

          <div className="mb-3">
            <label>ImageUrl</label>
            <input
              type="text"
              value={imageUrl}
              onChange={(e) => setImageUrl(e.target.value)}
              className="form-control"
            />
          </div>

          <div className="mb-3">
            <label>Color</label>
            <input
              type="text"
              value={color}
              onChange={(e) => setColor(e.target.value)}
              className="form-control"
            />
          </div>

          <div className="mb-3">
            <label>Category</label>
            <select
              className="form-control"
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <option value="">-- Select Category --</option>
              <option value="Mobiles">Mobiles</option>
              <option value="Laptops">Laptops</option>
            </select>
          </div>

          {/* <div className="mb-3">
            <label>Category</label>
            <input type="text" value={category} onChange={e => setCategory(e.target.value)} className="form-control" />
          </div> */}

          <button className="btn btn-success mt-3" onClick={AddProduct}>
            Add Product
          </button>

          {msg && <div className="alert alert-info mt-3">{msg}</div>}
        </div>
      </div>
    </div>
  );
}

export default AddProduct;
