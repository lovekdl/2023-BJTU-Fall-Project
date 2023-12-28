// import { useState } from 'react'
import './App.css'
import { LoginForm } from "./authority";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { AuthComponent } from './route_auth/AuthComponent.jsx';
import Profile from './userprofile/profile.jsx';
import { Management } from './manager/index.jsx';
import { TeacherLayout } from './teacher/index.jsx';
import { StudentLayout } from './student/index.jsx'
function App() {

  return (
    <Router>
      <Routes>
      
        {/* <Route path = "/" element={
          <div>
            <ButtonAppBar></ButtonAppBar>

            登录成功！
          
          </div>} 
        /> */}
        <Route path = "/" element={<AuthComponent></AuthComponent>} />
        <Route path = "/login" element={
          <div>
            
          <LoginForm></LoginForm>
          </div>} 
        />
        <Route path = "/manager" element={
          <div>
            {/* <ButtonAppBar></ButtonAppBar> */}
            <Management></Management>
          
          </div>} 
        />
        <Route path = "/teacher" element={
          <div>
            {/* <ButtonAppBar></ButtonAppBar> */}
            {/* <Management></Management> */}
            <TeacherLayout></TeacherLayout>
          </div>} 
        />
        <Route path = "/student" element={
          <div>
            {/* <ButtonAppBar></ButtonAppBar> */}
            {/* <Management></Management> */}
            <StudentLayout></StudentLayout>
          </div>} 
        />
        <Route path = "/profile" element={<div>
          {/* <ButtonAppBar></ButtonAppBar> */}
          <Profile></Profile>
        </div>} />
      </Routes>
    </Router>
  )
}

export default App
