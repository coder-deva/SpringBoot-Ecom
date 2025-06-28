import { createRoot } from 'react-dom/client'
import React from 'react'
import "primereact/resources/themes/lara-light-cyan/theme.css"
import { Provider } from 'react-redux'
import App from './App.jsx'
import store from './store/store.js'

createRoot(document.getElementById('root')).render(
    <Provider store={store}>
    <App />
  </Provider>
  

  
  
)
