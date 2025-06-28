import Carousels from "../../../component/customer/Home/Carousel";

import React from "react";
import ExploreProduct from "../../../component/customer/Home/ExploreProduct/ExploreProduct";
const Home = () => {
  return (
    <div className="max-w-7xl mx-auto px-2 py-6 ">
      <div className="mt-24">
        <Carousels />
      </div>

      <div className="mt-5">
        <ExploreProduct />
      </div>
    </div>
  );
};

export default Home;
