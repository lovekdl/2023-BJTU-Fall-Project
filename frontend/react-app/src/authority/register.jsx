import {useState} from 'react'
import "./authority.style.css"
import { useRef } from 'react';
import { useStore } from '../store';
// import Scrolls from "./scroll";
import { motion } from "framer-motion";
import { observer } from 'mobx-react-lite';
// import { RegisterForm } from './register';
import { DownOutlined } from '@ant-design/icons';
import { Dropdown } from 'antd';
import {http} from '../utils'
import {message} from 'antd'

function RegisterForm  (prop)  {
  const usernameRef = useRef();
  const passwordRef = useRef() ;
  const emailRef = useRef();
  const confirmedPasswordRef = useRef();
  const codeRef = useRef();
  const {loginStore} = useStore();
  const [accountType, setAccountType] = useState('教师');
  const items = [
    {
      label: '教师',
      key: '1',
    },
    {
      label: '学生',
      key: '2',
      
    },
  ];
  const handleLoginOnClicked = () => {
    prop.setCurrentPage('login')
  }
  async function handleRegisterSubmit(event) {
    event.preventDefault();

    async function register() {
      try {
          const ret = await http.post('usr/register',{
          username : usernameRef.current?.value,
          email : emailRef.current?.value,
          password: passwordRef.current?.value,
          code:codeRef.current?.value,
          authority:accountType==='教师'?2:3,
        })
        if(ret.data.message == 'success') {
          message.success('注册成功')
          prop.setCurrentPage('login')
        }
        else message.error('unknown error.')
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
      }
    }
    register()
  }
  const handleSendClicked = () => {
    if(!usernameRef.current?.value || ! passwordRef.current?.value || !emailRef.current?.value || !confirmedPasswordRef.current?.value  ) {
      message.error('输入不能为空')
      return;}
    async function send() {
      try {
        const ret = await http.post('/usr/register/sendCode',{
          // username : usernameRef.current?.value,
          email : emailRef.current?.value,
        })
        if(ret.data.message == 'success') {
          message.success('邮件已经发送')
          loginStore.resetWaiting();
        }
        else message.error('unknown error.')
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
      }
    }
    send()
    
  }
  const onClick = ({ key }) => {
    
    if(key === '1') {
      setAccountType('教师')
    }
    if(key === '2') {
      setAccountType('学生')
    }
    
  };
  return (
    
    <div className="right-login-form">
      
      <div className="form-wrapper">
        
      <form onSubmit={handleRegisterSubmit}>
        <h1>{'Sign up'}</h1>
        <div className="input-items">
          <span className="input-tips">
                {'账号类型'}
          </span>
            
          {/* <input type="password" className="inputs" placeholder={"输入账号类型"} ref={passwordRef}/> */}
          <Dropdown.Button
        icon={<DownOutlined />}
          
          menu={{
            items,
            selectable: true,
            defaultSelectedKeys: ['1'],
            onClick,
          }}
          
        >
          {accountType}
        </Dropdown.Button>
            
            {/* <span className="input-tips">
                {'用户名'}
                
            </span>
            <input type="text" className="inputs" placeholder={"输入用户名"} ref={usernameRef}></input> */}
            
          
            <span className="input-tips">
              {'姓名'}
          </span>
          
          <input type="text" className="inputs" placeholder={"输入姓名"} ref={usernameRef}/>

          <span className="input-tips">
              {'密码'}
          </span>
          
          <input type="password" className="inputs" placeholder={"输入密码"} ref={passwordRef}/>
          <span className="input-tips">
              {'确认密码'}
          </span>
          <input type="password" className="inputs" placeholder={"再次输入密码"} ref={confirmedPasswordRef}/>
          
          <span className="input-tips">
                {'邮箱'}
            </span>
            
            
            <input type="text" className="inputs" placeholder={"输入邮箱"}  ref={emailRef}></input>
            <div className='vertification' >
              <input type="text" className="inputs2" placeholder={"输入验证码"}  ref={codeRef}/>
              {loginStore.waiting <= 0? <motion.div
                className='box3'
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
                onClick={handleSendClicked}
              >
                  {'发送'}
              </motion.div> : <motion.div
                
                className='box2'
              >
                  {loginStore.waiting}
              </motion.div>} 
            </div>

        </div>
        {/* <button className="btn">Log in</button> */}
        <motion.button
          className="box" 
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
          transition={{ type: "spring", stiffness: 400, damping: 20 }}
          type='submit'
        >
          {'注册'}
        </motion.button>
        </form>
        <div className="siginup-tips">
          <span>{'已有账号?'}</span>
          <span onClick={handleLoginOnClicked}>Login</span>
        </div>
        
        
      </div>
      </div>
    
  );
}


export default observer(RegisterForm);
