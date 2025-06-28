import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginForm from "./component/Login/LoginForm";
import { Dashboard } from "./component/seller/Dashboard";
import DashboardLayoutBasic from "./component/seller/SellerDashboard";
import React from "react";
import AddProduct from "./component/seller/pages/Add/AddProduct";
import ListProduct from "./component/seller/pages/List/ListProduct";
import Orders from "./component/seller/pages/Orders/Orders";
import EditProduct from "./component/seller/pages/List/EditProduct";
import Profile from "./component/seller/pages/ProfilePage/Profile";
import CustomerLayout from "./pages/customer/CustomerLayout/CustomerLayout";
import Home from "./pages/customer/home/home";
import Cart from "./pages/customer/Cart/Cart";
import Address from "./pages/customer/address/Address";
import PlaceOrder from "./pages/customer/placeorder/PlaceOrder";
import MyOrder from "./pages/customer/myorders/MyOrder";
import CustomerProfile from "./pages/customer/profile/CustomerProfile";
import AdminDashboard from "./component/admin/AdminDashboard";
import Stats from "./component/admin/Stats";
import AdminProfile from "./component/admin/pages/ProfilePage/AdminProfile";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginForm />} />

        {/* Seller Routes (under /seller layout) */}
        <Route path="/seller" element={<DashboardLayoutBasic />}>
          <Route index element={<Dashboard />} />
          <Route path="addproducts" element={<AddProduct />} />
          <Route path="listproducts" element={<ListProduct />} />
          <Route path="edit-product/:id" element={<EditProduct />} />
          <Route path="orders" element={<Orders />} />
          <Route path="profile" element={<Profile />} />
        </Route>

        {/* Customer Routes (under /customer layout) */}
        <Route path="/customer" element={<CustomerLayout />}>
          <Route index element={<Home/>} />
          <Route path="cart" element={<Cart/>}/>
          <Route path="address" element={<Address/>}/>
          <Route path="placeorder" element={<PlaceOrder/>}/>
          <Route path="myorder" element={<MyOrder/>}/>
          <Route path="profile" element={<CustomerProfile />} />




        </Route>

         {/* Admin Routes (under /admin layout) */}


        <Route path="/admin" element={<AdminDashboard />}>
          <Route index element={<Stats />} />
          <Route path="profile" element={<AdminProfile />} />


        </Route>


      </Routes>
    </BrowserRouter>
  );
}

export default App;
