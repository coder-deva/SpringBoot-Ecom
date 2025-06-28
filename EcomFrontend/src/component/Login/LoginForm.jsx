import axios from "axios";
import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setUserDetails } from "../../store/actions/UserAction";
import "./LoginForm.css";
const LoginForm = () => {
  let [username, setUsername] = useState("");
  let [password, setPassword] = useState("");
  let [msg, setMsg] = useState("");
  const navigate = useNavigate();
   const dispatch =useDispatch()

  const processLogin = async () => {
    // Encode username and password using btoa
    let encodedString = window.btoa(username + ":" + password);
    //console.log(encodedString)
    //console.log(window.atob(encodedString))
    try {
      const response = await axios.get("http://localhost:8080/api/user/token", {
        headers: { Authorization: "Basic " + encodedString },
      });
      //console.log(response.data.token)
      let token = response.data.token; //<-- this is our access token, save it for later usage. (redux,localstorage)
      localStorage.setItem("token", token); //<-- saving token for future use in browsers local storage mem
      // Step 2: Get User Details
      let details = await axios.get("http://localhost:8080/api/user/details", {
        headers: { Authorization: "Bearer " + token },
      });

      // redux

      let user = {
                'username': username,
                'role': details.data.user.role
            }
             setUserDetails(dispatch)(user); //<-- this is where i save this user details in store
localStorage.setItem("role" , user.role)
localStorage.setItem("username" , user.username)

      let name = details.data.name;
      localStorage.setItem("name", name);
      let role = details.data.user.role;
      switch (role) {
        case "ROLE_CUSTOMER":
          navigate("/customer");
          break;
        case "ROLE_SELLER":
          navigate("/seller");
          break;
        case "ROLE_ADMIN":
          navigate("/admin");
          break;
        default:
          setMsg("Login Disabled, Contact Admin at admin@example.com");
      }
      setMsg("Login Success!!!");
    } catch (err) {
       console.error(err);
      setMsg("Invalid Credentials");
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h2 className="login-title">Welcome Back</h2>

        {msg !== "" ? (
          <div>
            <div class="alert alert-info">{msg}</div>
          </div>
        ) : (
          ""
        )}

        <form>
          <div className="input-field">
            <label>Username</label>
            <input
              type="text"
              id="email"
              placeholder="Enter your username"
              onChange={($e) => setUsername($e.target.value)}
            />
          </div>

          <div className="input-field">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              placeholder="Enter your password"
              onChange={($e) => setPassword($e.target.value)}
            />
          </div>

          <div className="form-footer">
            <label className="remember-me">
              <input type="checkbox" />
              Remember me
            </label>
            <a href="#" className="forgot-link">
              Forgot Password?
            </a>
          </div>

          <button
            type="submit"
            className="login-btn"
            onClick={(e) => {
              e.preventDefault();
              processLogin();
            }}
          >
            Login
          </button>
        </form>
        <p className="register-text">
          Don't have an account? <a href="#">Register</a>
        </p>
      </div>
    </div>
  );
};

export default LoginForm;
