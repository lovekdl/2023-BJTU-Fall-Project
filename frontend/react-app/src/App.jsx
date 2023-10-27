// import { useState } from 'react'
import './App.css'
import { LoginForm } from "./authority";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { ButtonAppBar } from './menu';
import Profile from './userprofile/profile';
function App() {

  return (
    <Router>
      <Routes>
      
        <Route path = "/" element={
          <div>
            <ButtonAppBar></ButtonAppBar>
            登录成功！
          
          </div>} 
        />
        <Route path = "/login" element={
          <div>
            
          <LoginForm></LoginForm>
          </div>} 
        />
        <Route path = "/profile" element={<div>
          <ButtonAppBar></ButtonAppBar>
          <Profile></Profile>
        </div>} />
      </Routes>
    </Router>
  )
}

export default App
