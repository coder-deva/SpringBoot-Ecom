import axios from "axios";
import { useEffect, useState } from "react";
import { FaEdit, FaTrashAlt } from "react-icons/fa";
import './ListProduct.css';
import React from "react";
import { useNavigate } from "react-router-dom";

function ListProduct() {


  const [listProduct, SetListProduct] = useState([]);
  const navigate = useNavigate();

  // redux
//  const dispatch = useDispatch();
//   const products = useSelector((state) => state.products.products);


  useEffect(() => {
    const getAllProducts = async () => {
      try {
        let response = await axios.get('http://localhost:8080/api/product/by-seller',
          { headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') } }
        );
        SetListProduct(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    getAllProducts();
  }, []);


// useEffect(() => {
//     fetchAllProducts(dispatch)();
//   }, [dispatch]);



// delete the product

const handleDelete = async (id) => {
  try {
    const confirmDelete = window.confirm("Are you sure you want to delete this product?");
    if (!confirmDelete) return;

    await axios.delete(`http://localhost:8080/api/product/delete/${id}`, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token')
      }
    });

    // Filter out deleted item from the UI
    SetListProduct(prev => prev.filter(product => product.id !== id));
    alert("Product deleted successfully!");
  } catch (error) {
    console.error("Delete failed:", error);
    alert("Failed to delete product.");
  }
};




  return (
    <>
      <div className='list add flex-col'>
        <p>All Product List</p>
        <div className="list-table">
          <div className="list-table-format title">
            <b>Image</b>
            <b>Title</b>
            <b>Description</b>
            <b>Category</b>
            <b>Price</b>
            <b>Action</b>
          </div>

          {
             // products.map((item, index) => (
            listProduct.map((item, index) => (
              <div key={index} className="list-table-format">
                {/* Image rendering fallback for null */}
                {item.imageUrl ? (
                  <img
                    src={`/images/${item.imageUrl}`}
                    alt={item.title}
                    className="w-12 h-12 object-cover border rounded"
                  />
                ) : (
                  <div className="text-gray-400 text-sm">No Image</div>
                )}
                <p>{item.title}</p>
                <p>{item.description}</p>
                <p>{item.categoryNames?.join(', ')}</p>  {/**item.categoryNames = ["Laptops", "Electronics"]*/}


                <p>₹{item.sellingPrice}</p>

                <div className="flex gap-4 items-center justify-center action-icons">
      <button title="Edit" className="text-blue-600 hover:text-blue-800 edit" onClick={() => navigate(`/seller/edit-product/${item.id}`, { state: { product: item } })}>
        <FaEdit />
      </button>
      <button
  title="Delete"
  className="text-red-600 hover:text-red-800 delete"
  onClick={() => handleDelete(item.id)}
>
  <FaTrashAlt />
</button>

    </div>
              </div>
            ))
          }
        </div>
      </div>
    </>
  );
}

export default ListProduct;
