import axios from "axios";
import React, { useEffect, useState } from "react";

const PlaceOrder = () => {
  const [subtotal, setSubtotal] = useState(0);
  const [couponCode, setCouponCode] = useState("");
  const [discountAmount, setDiscountAmount] = useState(0);
  const [finalAmount, setFinalAmount] = useState(0);
  const [addressId, setAddressId] = useState("");
  const [username, setUsername] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");
        const user = localStorage.getItem("username");
        const addr = localStorage.getItem("selectedAddressId");

        setUsername(user || "");
        setAddressId(addr || "");

        const res = await axios.get("http://localhost:8080/api/cart/items", {
          headers: { Authorization: `Bearer ${token}` },
        });

        const total = res.data.reduce((sum, item) => sum + item.totalPrice, 0);
        setSubtotal(total);
        setFinalAmount(total + 50);
      } catch (err) {
        console.error("Cart fetch error:", err);
      }
    };

    fetchData();
  }, []);

  const applyCoupon = () => {
    const percentage = parseInt(couponCode.match(/\d+/)) || 0;
    const discount = ((subtotal + 50) * percentage) / 100;
    setDiscountAmount(discount);
    setFinalAmount(subtotal + 50 - discount);
    alert("Coupon applied");
  };

  const handlePlaceOrder = async () => {
    if (!addressId || !username) {
      alert("Address or username not found");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      let url = `http://localhost:8080/api/orders/place?username=${username}&addressId=${addressId}`;
      if (couponCode.trim()) {
        url += `&coupon=${couponCode.trim()}`;
      }

      const res = await axios.post(url, {}, {
        headers: { Authorization: `Bearer ${token}` },
      });

      alert("Order placed successfully!");
      console.log("Order response:", res.data);
    } catch (err) {
      console.error("Order failed:", err);
      alert("Failed to place order");
    }
  };

  return (
    <>
      <div className="cart container mx-auto mt-10 px-4">
        <div className="cart-bottom">
          <div className="place-order-right">
            <div className="cart-total">
              <h2>Cart Totals</h2>
              <div>
                <div className="cart-total-details">
                  <p>Subtotal</p>
                  <p>₹{subtotal}</p>
                </div>
                <hr />
                <div className="cart-total-details">
                  <p>Delivery Fee</p>
                  <p>₹50</p>
                </div>
                {discountAmount > 0 && (
                  <>
                    <hr />
                    <div className="cart-total-details">
                      <p>Discount</p>
                      <p>-₹{discountAmount.toFixed(2)}</p>
                    </div>
                  </>
                )}
                <hr />
                <div className="cart-total-details">
                  <b>Total</b>
                  <b>₹{finalAmount.toFixed(2)}</b>
                </div>
              </div>
              <button type="button" onClick={handlePlaceOrder}>
                Proceed To Placeorder
              </button>
            </div>
          </div>

          <div className="cart-promocode">
            <div>
              <p>If you have a promo code, Enter it here</p>
              <div className="cart-promocode-input">
                <input
                  type="text"
                  placeholder="promo code"
                  value={couponCode}
                  onChange={(e) => setCouponCode(e.target.value)}
                />
                <button type="button" onClick={applyCoupon}>
                  Submit
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default PlaceOrder;
