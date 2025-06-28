
import axios from "axios"

// export const fetchAllProducts = (dispatch) => () => {
//     console.log('In action....')
//     //call the API 
//     axios.get('http://localhost:8080/api/product/all')
//         .then(function (response) {
//             console.log(response.data)
//             dispatch({
//                 'payload': response.data,
//                 'type': 'FETCH_ALL_PRODUCTS'
//             })
//         })

// }

export const fetchAllProducts = (dispatch) => () => {
  console.log('In action....');

  const token = localStorage.getItem('token'); 

  axios.get('http://localhost:8080/api/product/by-seller', {
    headers: {
      Authorization: `Bearer ${token}` 
    }
  })
  .then((response) => {
    console.log(response.data);
    dispatch({
      payload: response.data,
      type: 'FETCH_ALL_PRODUCTS'
    });
  })
  .catch((error) => {
    console.error("Error fetching products:", error);
    alert("Unauthorized. Please log in again.");
  });
};