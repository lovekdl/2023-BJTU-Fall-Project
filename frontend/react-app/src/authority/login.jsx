import {useState} from 'react'
import "./authority.style.css"
import { useRef } from 'react';
import { useStore } from '../store';
import { useNavigate } from 'react-router-dom';
// import Scrolls from "./scroll";
import { motion } from "framer-motion";
import { observer } from 'mobx-react-lite';
import qq from '../assets/QQ.png'
// import { RegisterForm } from './register';
import {message} from 'antd'
import RegisterForm from "./register"
import FindPasswordForm from "./findpassword"
function LoginForm  ()  {

  const navigate = useNavigate()
  const usernameRef = useRef();
  const passwordRef = useRef();
  const [ current_page , setCurrentPage ] = useState('login');
  
  const {loginStore,ProfileStore} = useStore();
  

  const handleSignUpOnClicked = () => {
    // message.error("联系管理员注册")
    setCurrentPage('register')
  }
  const handleFindPasswordClicked = () => {
    setCurrentPage('findPassword')
  }
  async function handleLoginSubmit(event) {
    event.preventDefault();
    if(!usernameRef.current?.value || !passwordRef.current?.value) {
      message.error('输入不能为空')
      return;
    }


    try {
      await loginStore.getTokenByLogin({
        username: usernameRef.current.value,
        password: passwordRef.current.value
      })

      message.success('Success')
      ProfileStore.getProfile();
      navigate('/', {replace:true})

      // window.location.reload()
    } catch(e) {
      console.log(e)
      if(e.response)
        message.error(e.response.data.message)
      else message.error(e.message)
    }
  }

  const handleGithubClicked = () => {
    window.open('https://github.com/lovekdl/2023-BJTU-Summer-Project', '_blank');
  }
  // const handleBilibiliClicked = () => {
  //   window.open('https://space.bilibili.com/99798809', '_blank');
  // }
  return (
    
    
    <div className="content" >
      {/* <Scrolls></Scrolls> */}
      <div className="login-wrapper">
        <div className="left-img">
          <div className="title">
            <div className="tips">
              <h1>{'作业互评系统'}</h1>
              {/* <span>{'explore the universe.'}</span>
              <span>{'try to find your planet.'}</span> */}
            </div>
          </div>
        </div>
        {current_page === 'login'? <div className="right-login-form">
          <div className="form-wrapper">
            
          
            <h1>{'登录'}</h1>
            <form onSubmit={handleLoginSubmit}>
              <div className="input-items">
                  <span className="input-tips">
                      {'学号/工号'}
                  </span>
                  <input type="text"  className="inputs" placeholder={"请输入学号或工号"} ref={usernameRef}  ></input>
                  
              </div>
              <div className="input-items">
                <span className="input-tips">
                    {'密码'}
                </span>
                
                <input type="password" className="inputs" placeholder={"请输入密码"} ref={passwordRef}/>
                
                <span className="forgot" onClick ={handleFindPasswordClicked}>{'忘记密码'}</span>
              </div>
              
              <motion.button
                className="box" 
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
                transition={{ type: "spring", stiffness: 400, damping: 20 }}
                // onClick={handleOnClicked}
                type = 'submit'
              >
                {'登录'}
              </motion.button>
            </form>
            {/* <button className="btn">Log in</button> */}
            
            <div className="siginup-tips">
              <span>{"没有账号?"}</span>
              <span onClick={handleSignUpOnClicked}>{'注册'}</span>
            </div>
            <div className="other-login">
              <div className="divider">
                <span className="line"></span>
                <span className="divider-text">{'About us'}</span>
                <span className="line"></span>
              </div>
              <div className="other-login-wrapper">
                <div className="other-login-item">
                  <img src={qq} alt="QQ" onClick={handleGithubClicked}/>
                </div>
                {/* <div className="other-login-item">
                  <img style = {{width:'80px'}}src={wechat} alt="WeChat" onClick = {handleBilibiliClicked}/>
                </div> */}
              </div>
                
            </div>
            
          </div>
          
        </div>:
        
          current_page === 'register'? <RegisterForm setCurrentPage = {setCurrentPage}></RegisterForm> : <FindPasswordForm setCurrentPage={setCurrentPage}></FindPasswordForm>
        
        }
      </div>
    </div>
  );


}


export default observer(LoginForm);
