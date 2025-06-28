import React from 'react'
import './FooterUi.css'
import facebook_icon from "../../../src/assets/facebook_icon.png";
import twitter_icon from "../../../src/assets/twitter_icon.png";
import linkedin_icon from "../../../src/assets/linkedin_icon.png";
import logo from "../../../src/assets/logo.png";

const FooterUi = () => {
  return (
    <div className='footer' id='footer'>
        <div className="footer-content">
            <div className="footer-content-left">
              <img src={logo} alt="" style={{ width: "220px", maxWidth: "100%", height: "auto", display: "flex" }} />
                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Amet sapiente in voluptatem inventore quidem nesciunt adipisci ad, dolor itaque optio voluptate non deserunt porro! Sunt quia aperiam cumque adipisci nobis?</p>
                <div className="footer-social-icons">
                    <img src={facebook_icon} alt="" />
                    <img src={twitter_icon} alt="" />
                    <img src={linkedin_icon} alt="" />
                </div>
                
            </div>

            <div className="footer-content-center">
                <h2>COMPANY</h2>
                <ul>
                    <li>Home</li>
                    <li>About us</li>
                    <li>Delivery</li>
                    <li>Privacy policy</li>
                </ul>

            </div>
            <div className="footer-content-right">
                <h2>GET IN TOUCH</h2>
                <ul>
                    <li>7305488113</li>
                    <li>2116012@saec.ac.in</li>
                </ul>


            </div>


        </div>
        <hr />
        <p className="footer-copyright">Copyright 2024 @ Hexkart.com-All Right Reserved.</p>


    </div>
  )
}

export default FooterUi