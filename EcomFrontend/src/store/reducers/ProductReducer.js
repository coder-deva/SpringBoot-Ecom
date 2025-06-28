//src/store/reducers/ProductReducer
const initialState = {
    products: []
}
const ProductReducer = (state = initialState, action) => {
    if (action.type === "FETCH_ALL_PRODUCTS") {
        console.log('in reducer ' + action.payload)
        return {
            ...state,
            products: action.payload
        }
    }
    return state;
}
export default ProductReducer;