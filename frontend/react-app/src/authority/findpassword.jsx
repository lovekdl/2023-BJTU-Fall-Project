import "./authority.style.css"
import { useRef } from 'react';
import { useStore } from '../store';
// import Scrolls from "./scroll";
import { motion } from "framer-motion";
import { observer } from 'mobx-react-lite';
// import { RegisterForm } from './register';
import {http} from '../utils'
import {message} from 'antd'

function FindPasswordForm  (prop)  {
  const passwordRef = useRef();
  const emailRef = useRef();
  const codeRef = useRef();
  const {loginStore} = useStore();

  const handlebackOnClicked = () => {
    prop.setCurrentPage('login')
  }
  const checkFormat = (x) => {
    var flag1 = false, flag2 = false;
    for(var ch of x) {
      if(ch == '@') {
        flag1=true
      }
      if(flag1==true&&ch=='.'){
        flag2=true
      }
    }
    return flag2
  }
  const handleOkOnClicked = () => {
    


    if( !passwordRef.current?.value ) {
      message.error('密码不能为空')
    }
    if( !codeRef.current?.value ) {
      message.error('验证码不能为空')
    }
    if( !emailRef.current?.value ) {
      message.error('邮箱不能为空')
      return;
    }
    if( !checkFormat(emailRef.current?.value)) {
      message.error('邮箱格式错误')
      return;
    }
    async function findPassword() {
      try {
        const ret = await http.post('/usr/findPassword',{
          // username : usernameRef.current?.value,
          email : emailRef.current?.value,
          code : codeRef.current?.value,
          newPassword:passwordRef.current?.value
        })
        if(ret.data.message == 'success') {
          prop.setCurrentPage('login')
          message.success('密码修改成功')
        }
        else message.error('unknown error.')
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
      }
    }
    findPassword()
  }
  const handleSendClicked = () => {
    
    if( !emailRef.current?.value ) {
      message.error('请不要输入空邮箱')
      return;
    }
    if( !checkFormat(emailRef.current?.value)) {
      message.error('邮箱格式错误')
      return;
    }
    async function send() {
      try {
        const ret = await http.post('/usr/findPassword/sendCode',{
          // username : usernameRef.current?.value,
          email : emailRef.current?.value,
        })
        if(ret.data.message == 'success') {
          message.success('验证码已发送')
          loginStore.resetWaiting2();
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
  // const handleBilibiliClicked = () => {
  //   window.open('https://space.bilibili.com/99798809', '_blank');
  // }
  return (
    
    
    
        
        <div className="right-login-form">
          <div className="form-wrapper">
            

            <h1>{'找回密码'}</h1>
            
            
            <div className="input-items">
            <span className="input-tips">
                {'邮箱'}
            </span>
            <input type="text" className="inputs" placeholder={"输入邮箱"}  ref={emailRef}></input>

            

            <div className='vertification' >
              <input type="text" className="inputs2" placeholder={"输入验证码"}  ref={codeRef}/>
              {loginStore.waiting2 <= 0? <motion.div
                className='box3'
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
                onClick={handleSendClicked}
              >
                  {'发送'}
              </motion.div> : <motion.div
                
                className='box2'
              >
                  {loginStore.waiting2}
              </motion.div>} 
            </div>
            
            <span className="input-tips">
                {'新密码'}
            </span>
            
            <input type="password" className="inputs" placeholder={"输入新密码"} ref={passwordRef}/>

            <span className="forgot" onClick={handlebackOnClicked}>{'返回'}</span>
            <motion.button
              className="box" 
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
              transition={{ type: "spring", stiffness: 400, damping: 20 }}
              onClick = {handleOkOnClicked}
            >
              {'确认'}
            </motion.button>
              
            
            
            
          </div>
          </div>
        </div>
  );
}


export default observer(FindPasswordForm);
