import axios from "axios";
import "./ExploreProduct.css";
import React, { useEffect, useState } from "react";
import ProductCardUi from "../ProductCardUi/ProductCardUi";

const ExploreProduct = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    const getAllProducts = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/product/all", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setProducts(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    getAllProducts();
  }, []);

  return (
    <div className="product-display">
      <h2>Explore Top Selling Products</h2>
      <div className="product-display-list">
        {products.map((item) => (
          <ProductCardUi
            key={item.id}
            productId={item.id}
            title={item.title}
            description={item.description}
            sellingPrice={item.sellingPrice}
            imageUrl={item.imageUrl}
          />
        ))}
      </div>
    </div>
  );
};

export default ExploreProduct;
