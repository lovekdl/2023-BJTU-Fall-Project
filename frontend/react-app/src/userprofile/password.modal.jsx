import {Modal} from 'antd'
import {useRef } from 'react'
import { observer } from 'mobx-react-lite';
import { motion } from 'framer-motion';
import {message} from 'antd'
import { http } from '../utils/http.jsx';
import { useStore } from '../store';
function PasswordModal (prop) {
  const oldPasswordRef = useRef() ;
  const passwordRef = useRef();
  const confirmedPasswordRef = useRef();
  const {loginStore} = useStore()
  const handleOk = ()=>{
    prop.setVisible(false)
  }
  const handleSubmitted = () => {
    if(!oldPasswordRef.current?.value || !passwordRef.current?.value || !confirmedPasswordRef.current?.value) {
      message.error("输入不能为空");
      return;
    }
    if(passwordRef.current?.value !== confirmedPasswordRef.current?.value) {
      message.error('两次输入的密码不同');
      return;
    }
    async function changePassword() {
      try {
        const ret = await http.post('usr/changePassword',{
          oldPassword:oldPasswordRef.current?.value,
          newPassword:passwordRef.current?.value
        })
        if(ret.data.message == 'success') {
          console.log("修改成功")
          prop.setVisible(false);
          message.success('修改成功')
          loginStore.setToken(ret.data.data.token)
        }
        else message.error('unknown error.')
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
      }
    }
    changePassword()
  }
  return (
    <Modal title="修改密码" open={prop.visible} onCancel={handleOk} destroyOnClose={true} footer={null} >
      <br></br>
      <div className = 'ModalInnerDiv'>
        <span className = 'ModalSpan2'>{'旧密码'} &nbsp;&nbsp;</span>
      </div>
      <div className = 'ModalInnerDiv'>
        
        <input type="password" className="input-box" placeholder={"输入你的旧密码"} ref = {oldPasswordRef}/>
      </div>
      <div className = 'ModalInnerDiv'>
        <span className = 'ModalSpan2'>{'新密码'} &nbsp;&nbsp;</span>
      </div>
      <div className = 'ModalInnerDiv'>
        
        <input type="password" className="input-box" placeholder={"输入你的新密码"}ref = {passwordRef}/>
      </div>   
      <div className = 'ModalInnerDiv'>
        
        <input type="password" className="input-box" placeholder={"再次输入新密码"} ref = {confirmedPasswordRef}/>
        
      </div>   
      <div className = 'ModalInnerButtonDiv'>
      <motion.button
          className="ModalChange" 
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
          transition={{ type: "spring", stiffness: 400, damping: 20 }}
          onClick={handleSubmitted}
        >
          确认
        </motion.button>
      </div>
    </Modal>
  )
}

export default observer(PasswordModal)
