import React, { useState } from "react";
import axios from "axios";
import add_icon_white from "../../../../assets/add_icon_white.png";
import add_icon_green from "../../../../assets/add_icon_green.png";
import remove_icon_red from "../../../../assets/remove_icon_red.png";
import "./ProductCardUi.css";

 // props receiving the data
const ProductCardUi = ({
  productId,
  title,
  description,
  sellingPrice,
  imageUrl
}) => {
  const [localCount, setLocalCount] = useState(0);

  // for adding the product
  const increase = async () => {
    try {
      const token = localStorage.getItem("token");

      // Step 1: Call API to add 1 quantity of this product
      await axios.post(
        "http://localhost:8080/api/cart/add",
        {
          productId: productId,
          quantity: 1
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );

      // Step 2: Only update local count after successful add
      setLocalCount((prev) => prev + 1);
    } catch (error) {
      console.error("Error adding product to cart:", error.response?.data || error.message);
    }
  };

  // const decrease = () => {
  //   if (localCount > 0) {
  //     setLocalCount(localCount - 1);
  //     
  //   }
  // };

  const decrease = async () => {
  if (localCount === 0) return;

  try {
    const token = localStorage.getItem("token");

    await axios.delete(
      `http://localhost:8080/api/cart/remove?productId=${productId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );

    setLocalCount((prev) => prev - 1);
  } catch (error) {
    console.log("Error removing product from cart:",error);
  }
};



  return (
    <div className="product-item">
      <div className="product-item-img-container">
        <img
          className="product-item-image"
          src={`/images/${imageUrl}`}
          alt={title}
        />
      </div>

      <div className="product-item-info">
        <div className="product-item-name-rating">
          <p>{title}</p>
        </div>
        <p className="product-item-desc">{description}</p>

        <div className="product-item-bottom-row">
          <p className="product-item-price">₹{sellingPrice}</p>
          {localCount === 0 ? (
            <img
              className="add-inline"
              onClick={increase}
              src={add_icon_white}
              alt="Add"
            />
          ) : (
            <div className="inline-counter">
              <img onClick={decrease} src={remove_icon_red} alt="Remove" />
              <p>{localCount}</p>
              <img onClick={increase} src={add_icon_green} alt="Add More" />
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductCardUi;
