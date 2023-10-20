import { useState } from 'react'
import './App.css'
import { LoginForm } from "./authority";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
function App() {

  return (
    <Router>
      <Routes>
      
        <Route path = "/" element={
          <div>
            登录成功！
          
          </div>} 
        />
        <Route path = "/login" element={
          <div>
            
          <LoginForm></LoginForm>
          </div>} 
        />
      </Routes>
    </Router>
  )
}

export default App
