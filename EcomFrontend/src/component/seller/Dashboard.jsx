import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchAllProducts } from "../../store/actions/ProductAction";
import React from "react";

import { Chart } from "primereact/chart";
import axios from "axios";
export const Dashboard = () => {
  const dispatch = useDispatch();
  const products = useSelector((state) => state.products.products);

  useEffect(() => {
    fetchAllProducts(dispatch)();
  }, [dispatch]);

  // charts
 const [chartData, setChartData] = useState({});
    const [chartOptions, setChartOptions] = useState({});
    const [titles, setTitles] = useState([]);
    const [quantities, setQuantities] = useState([]);

     useEffect(() => {
        const fetchTopProducts = async () => {
            const response = await axios.get("http://localhost:8080/api/orders/seller/chart/top-products", {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });

            const productTitles = response.data.map(p => p.productTitle); // response.data.productTitle
            const totalQuantities = response.data.map(p => p.totalQuantitySold);

            setTitles(productTitles);
            setQuantities(totalQuantities);

            const data = {
                labels: productTitles, //response?.data?.productTitles,
                datasets: [
                    {
                        label: 'Total Quantity Sold',
                        data: totalQuantities, //response?.data?.totalQuantities
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)'
                        ],
                        borderColor: [
                            'rgb(255, 99, 132)',
                            'rgb(54, 162, 235)',
                            'rgb(255, 206, 86)',
                            'rgb(75, 192, 192)',
                            'rgb(153, 102, 255)'
                        ],
                        borderWidth: 1
                    }
                ]
            };

            const options = {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };

            setChartData(data);
            setChartOptions(options);
        };

        fetchTopProducts();
    }, []);

  return (

    <>
     <div>
            <h1 className="text-2xl font-bold mb-4">Top Selling Products</h1>
            <div className="col-lg-8 col-lg-offset-2" style={{ width: '80%' }}>
                <div className="card">
                    <div className="card-body">
                        <Chart type="bar" data={chartData} options={chartOptions} />
                    </div>
                </div>
                <ul className="mt-6 space-y-3">
  {
  titles.map((title, index) => (
    <li
      key={index}
      className="flex items-center justify-between px-4 py-2 bg-white shadow-md rounded-lg border border-gray-200 hover:shadow-lg transition duration-300"
    >
      <span className="font-medium text-gray-800">{title}</span>
      <span className="text-sm text-gray-600">
        <span className="font-semibold text-green-600">{quantities[index]}</span> sold
      </span>
    </li>
  ))}
</ul>

            </div>
        </div>

        
    <div className="container mt-4">
      <h2 className="text-dark mb-4">Dashboard - Product List</h2>
      <div className="row">
        {products.map((product, index) => (
          <div className="col-md-4 mb-4" key={index}>
            <div className="card h-100 shadow-sm">
              {product.imageUrl ? (
                <img
                  className="card-img-top"
                  style={{ padding: '50px', height: '21rem', objectFit: 'contain' }}
                  src={`/images/${product.imageUrl}`}
                  alt={product.title}
                />
              ) : (
                <div
                  className="d-flex align-items-center justify-content-center bg-light"
                  style={{ height: "21rem" }}
                >
                  No Image
                </div>
              )}

              <div className="card-body d-flex flex-column">
                <h5 className="card-title">{product.title}</h5>
                <p className="card-text text-muted">{product.description}</p>
                <p className="card-text">Price: ₹{product.sellingPrice}</p>
                <p className="card-text">
                  Category:{" "}
                  {product.categoryNames}
                </p>
                {/* <Link
                  to={`/product/details/${product.id}`}
                  className="btn btn-primary mt-auto"
                >
                  View Details
                </Link> */}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
    </>
  );
};
