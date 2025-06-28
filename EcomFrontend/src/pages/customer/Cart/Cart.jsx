import React, { useEffect, useState } from "react";
import "./Cart.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Cart = () => {

    const navigate =useNavigate();
  const [cartItems, setCartItems] = useState([]);

  useEffect(() => {
    const fetchCart = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/cart/items", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
        setCartItems(res.data);
      } catch (err) {
        console.error("Failed to fetch cart:", err);
      }
    };

    fetchCart();
  }, []);
  // decrease the quantity
  const handleRemove = async (productId) => {
    try {
      const token = localStorage.getItem("token");
      await axios.delete(
        `http://localhost:8080/api/cart/remove?productId=${productId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      // Refresh cart to show the current quantity of the product
      const res = await axios.get("http://localhost:8080/api/cart/items", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setCartItems(res.data);
    } catch (err) {
      console.error("Failed to remove item from cart:", err);
    }
  };

  const getSubtotal = () => {
    return cartItems.reduce((sum, item) => sum + item.totalPrice, 0);
  };

  return (
    <div className="cart container mx-auto mt-10 px-4">
      <div className="cart-items">
        <div className="cart-items-title">
          <p>Items</p>
          <p>Title</p>
          <p>Price</p>
          <p>Quantity</p>
          <p>Total</p>
          <p>Remove</p>
        </div>
        <hr />

        {cartItems.map((item, index) => (
          <div key={index}>
            <div className="cart-items-title cart-items-item">
              <img src={`/images/${item.imageUrl}`} alt={item.productTitle} />

              <p>{item.productTitle}</p>
              <p>₹{item.sellingPrice}</p>
              <p>{item.quantity}</p>
              <p>₹{item.totalPrice}</p>
              <p
                className="cross"
                onClick={() => handleRemove(item.productId)}
                style={{ cursor: "pointer", color: "red" }}
              >
                x
              </p>
            </div>
            <hr />
          </div>
        ))}
      </div>

      <div className="cart-bottom">
        <div className="cart-total">
          <h2>Cart Totals</h2>
          <div>
            <div className="cart-total-details">
              <p>Subtotal</p>
              <p>₹{getSubtotal()}</p>
            </div>
            <hr />
            <div className="cart-total-details">
              <p>Delivery Fee</p>
              <p>₹50</p>
            </div>
            <hr />
            <div className="cart-total-details">
              <b>Total</b>
              <b>₹{getSubtotal() + 50}</b>
            </div>
          </div>
          <button onClick={()=>navigate("/customer/address")}>PROCEED TO CHECKOUT</button>
        </div>

        <div className="cart-promocode">
          {/* <div>
            <p>If you have a promo code, Enter it here</p>
            <div className="cart-promocode-input">
              <input type="text" placeholder="promo code" />
              <button>Submit</button>
            </div>
          </div> */}
        </div>
      </div>
    </div>
  );
};

export default Cart;
