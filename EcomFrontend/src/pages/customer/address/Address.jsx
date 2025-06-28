import React, { useState } from "react";
import "./Address.css";
import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Address = () => {
  const navigate = useNavigate();

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [street, setStreet] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [zipCode, setZipCode] = useState("");
  const [country, setCountry] = useState("");
  const [phone, setPhone] = useState("");
  const [msg, setMsg] = useState("");
  // address to add
  const addAddress = async (e) => {
    e.preventDefault();

    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/api/address/add",
        {
          firstName,
          lastName,
          email,
          street,
          city,
          state,
          zipCode,
          country,
          phone,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      // i get the added address and store it in the new address
      const newAddress = response.data;

      //add it to the list immediately
      setAddresses((prevAddresses) => [...prevAddresses, newAddress]);

      setMsg("Address added successfully!");
      setFirstName("");
      setLastName("");
      setEmail("");
      setStreet("");
      setCity("");
      setState("");
      setZipCode("");
      setCountry("");
      setPhone("");
    } catch (error) {
      console.error(
        "Error adding address:",
        error.response?.data || error.message
      );
      setMsg("Failed to add address.");
    }
  };

  // select delivery address

  const [addresses, setAddresses] = useState([]);
  const [selectedAddressId, setSelectedAddressId] = useState(null);

  useEffect(() => {
    const fetchAddresses = async () => {
      try {
        const token = localStorage.getItem("token");
        const res = await axios.get("http://localhost:8080/api/address/list", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setAddresses(res.data);
      } catch (err) {
        console.error("Failed to fetch addresses:", err);
      }
    };

    fetchAddresses();
  }, []);

  const handleSelect = (addressId) => {
    setSelectedAddressId(addressId);
  };

  // fetch the cart totals includes in tha cart page

  const [subtotal, setSubtotal] = useState(0);

  useEffect(() => {
    const fetchCart = async () => {
      try {
        const token = localStorage.getItem("token");
        const res = await axios.get("http://localhost:8080/api/cart/items", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const total = res.data.reduce((sum, item) => sum + item.totalPrice, 0);
        setSubtotal(total);
      } catch (err) {
        console.error("Failed to fetch cart total:", err);
      }
    };

    fetchCart();
  }, []);

  return (
    <>
      <div className="address-wrapper">
        <form className="place-order" onSubmit={addAddress}>
          <div className="place-order-left">
            <p className="title">Delivery Information</p>
            {msg && (
              <p
                style={{
                  color: msg.includes("successfully") ? "green" : "red",
                }}
              >
                {msg}
              </p>
            )}

            <div className="multi-fields">
              <input
                type="text"
                placeholder="First Name"
                value={firstName}
                required
                onChange={(e) => setFirstName(e.target.value)}
              />
              <input
                type="text"
                placeholder="Last Name"
                value={lastName}
                required
                onChange={(e) => setLastName(e.target.value)}
              />
            </div>

            <input
              type="email"
              placeholder="Email address"
              value={email}
              required
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type="text"
              placeholder="Street"
              value={street}
              required
              onChange={(e) => setStreet(e.target.value)}
            />

            <div className="multi-fields">
              <input
                type="text"
                placeholder="City"
                value={city}
                required
                onChange={(e) => setCity(e.target.value)}
              />
              <input
                type="text"
                placeholder="State"
                value={state}
                required
                onChange={(e) => setState(e.target.value)}
              />
            </div>

            <div className="multi-fields">
              <input
                type="text"
                placeholder="Zip code"
                value={zipCode}
                required
                onChange={(e) => setZipCode(e.target.value)}
              />
              <input
                type="text"
                placeholder="Country"
                value={country}
                required
                onChange={(e) => setCountry(e.target.value)}
              />
            </div>

            <input
              type="text"
              placeholder="Phone"
              value={phone}
              required
              onChange={(e) => setPhone(e.target.value)}
            />
            <div className="add-address">
              <button type="submit">Add Address</button>
            </div>
          </div>

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
                <hr />
                <div className="cart-total-details">
                  <b>Total</b>
                  <b>₹{subtotal + 50}</b>
                </div>
              </div>
              <button
                type="button"
                onClick={() => {
                  if (selectedAddressId) { 
                    // i used local storage to set in application using that i get the address id to place the order in placeorder.jsx
                 localStorage.setItem("selectedAddressId", selectedAddressId);
                    navigate("/customer/placeorder", {
                      state: { addressId: selectedAddressId },
                    });
                  } else {
                    alert(
                      "Please select a delivery address before proceeding to payment."
                    );
                  }
                }}
              >
                Proceed To Payment
              </button>
            </div>
          </div>
        </form>
      </div>

      <div className="address-list-container">
        <h2>Select Delivery Address</h2>
        {addresses.length === 0 && <p>No addresses found.</p>}
        <div className="address-list">
          {addresses.map((addr) => (
            <label key={addr.addressId} className="address-card">
              <input
                type="radio"
                name="selectedAddress"
                value={addr.addressId}
                checked={selectedAddressId === addr.addressId}
                onChange={() => handleSelect(addr.addressId)}
              />
              <div className="address-details">
                <p>
                  <strong>
                    {addr.firstName} {addr.lastName}
                  </strong>
                </p>
                <p>{addr.street}</p>
                <p>
                  {addr.city}, {addr.state}, {addr.zipCode}
                </p>
                <p>{addr.country}</p>
                <p>Email: {addr.email}</p>
                <p>Phone: {addr.phone}</p>
              </div>
            </label>
          ))}
        </div>
      </div>
    </>
  );
};

export default Address;
