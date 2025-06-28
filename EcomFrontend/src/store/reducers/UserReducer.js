//src/store/reducers/UserReducer
const initialState = {
    username: "",
    role: ""
}
const UserReducer = (state = initialState, action) => {

    if (action.type === "SET_USER_DETAILS") {  // This is for login 
        let user = action.payload;
        return {
            ...state,
            username: user.username,
            role: user.role
        }
    }
    if (action.type === "DELETE_USER_DETAILS") {  // This is for logout
        return {
            ...state,
            username: "",
            role: ""
        }
    }
    return state;
}
export default UserReducer;
