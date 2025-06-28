import React, { useEffect, useState } from 'react';
import axios from 'axios';
import parcel_icon from '../../../assets/parcel_icon.png';
import './MyOrder.css';

const MyOrder = () => {
  const [orders, setOrders] = useState([]);
  const username = localStorage.getItem("username");
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const res = await axios.get(
          `http://localhost:8080/api/orders/customer/${username}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setOrders(res.data);
      } catch (error) {
        console.error("Error fetching orders:", error);
      }
    };

    if (username && token) {
      fetchOrders();
    }
  }, [username, token]);

  return (
    <div className='my-orders'>
      <h2>My Orders</h2>
      <div className="container">
        {orders.length === 0 ? (
          <p style={{ textAlign: "center", color: "gray" }}>No orders found.</p>
        ) : (
          orders.map((order, index) => (
            <div className="my-orders-order" key={index}>
              <img src={parcel_icon} alt="Parcel Icon" />
              <p>{order.address.firstName} {order.address.lastName}</p>
              <p>₹{order.totalAmount.toFixed(2)}</p>
              <p>{new Date(order.orderDate).toLocaleDateString()}</p>
              <p><span>&#x25cf;</span> <b>{order.status}</b></p>
              <button>Track Order</button>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default MyOrder;
