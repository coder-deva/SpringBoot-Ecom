import axios from "axios";
import React, { useEffect, useState } from 'react';
import './Orders.css';
import parcelIcon from '../../../../assets/parcel_icon.png';

const Orders = () => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const getAllOrders = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/orders/seller/orders', {
          headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
        });
        setOrders(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    getAllOrders();
  }, []);

  const statusOptions = ['Placed', 'Processing', 'Shipped', 'Delivered', 'Cancelled'];

  return (
    <div className='order add'>
      <h3>Order Page</h3>

      <div className="order-list">
        {
        orders.map((order, index) => (
          <div key={index} className='order-item'>
            <img src={parcelIcon} alt="Parcel Icon" />

            <div>
              <p className='order-item-product'><span className="label">{order.productTitle} </span>x {order.quantity}</p>
              <p className="order-item-name"><span className="label">CustomerName:  </span>  {order.customerName}</p>
              <div className="order-item-address" ><span className="label">Address: </span> {order.customerStreet},{order.customerCity}, {order.customerState}, {order.customerZipCode}
              </div>
              <p className="order-item-email"><span className="label">Email:</span>  {order.customerEmail}</p>
              <p className="order-item-date"><span className="label">Ordered on:</span> {new Date(order.orderDate).toLocaleString()}</p>
            </div>

            <div className="flex flex-col items-end gap-2 text-sm">
              <p><span className="label">Items:</span> {order.quantity}</p>
              <p><span className="label">Price:</span> ₹{order.price.toLocaleString()}</p>
              <select value={order.status}>
                {statusOptions.map(status => (
                  <option key={status} value={status}>{status}</option>
                ))}
              </select>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Orders;
