// src/store/store.js


import { configureStore } from "@reduxjs/toolkit";

import ProductReducer from "./reducers/ProductReducer";
import UserReducer from "./reducers/UserReducer";


const store = configureStore({
    reducer: {
        user: UserReducer,
        products:ProductReducer
        
    }
})

export default store; 