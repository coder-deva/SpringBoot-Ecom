import { Outlet } from 'react-router-dom'
import NavBar from '../../../component/customer/NavBar'
import Footer from '../footer/Footer'
import React from 'react'


const CustomerLayout = () => {
  return (
    <div className='w-full'>
<div className="w-full">
    <NavBar/>
</div>
<div className="w-full pt-[90px]">
  <Outlet />
</div>

<div>
    <Footer/>
</div>
    </div>
  )
}

export default CustomerLayout