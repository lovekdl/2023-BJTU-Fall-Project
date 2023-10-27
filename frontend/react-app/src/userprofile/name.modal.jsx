/*
  修改用户名
*/

import {Modal} from 'antd'
import { useRef} from 'react'
import { observer } from 'mobx-react-lite';

import { motion } from 'framer-motion';
import {message} from 'antd'
import { http } from '../utils/http.jsx';
import { useStore } from '../store/index.jsx';
function NameModal (prop) {
  const nameRef = useRef();
  const {loginStore,ProfileStore} = useStore()
  const handleOk = ()=>{
    prop.setVisible(false)
  }

  const handleSubmitted = () => {
    if(!nameRef.current?.value) {
      message.error('输入不能为空');
      return ;
    }
    async function changePassword() {
      try {
        const ret = await http.post('usr/changeUsername',{
          newUsername:nameRef.current?.value,
        })
        if(ret.data.message == 'success') {
          message.success('Success')
          loginStore.setToken(ret.data.data.token)
          ProfileStore.getProfile();
          
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
    <Modal title={"Modify your name"} open={prop.visible} onCancel={handleOk} destroyOnClose={true} footer={null} >
      <br></br>
      <div className = 'ModalInnerDiv'>
        
        <span className = 'ModalSpan'>{'旧姓名'} &nbsp;&nbsp;</span>
        <div className = 'Modal-purple-underline'> {ProfileStore.username}</div>
      </div>
      
      
      <div className = 'ModalInnerDiv'>
        
        <span className = 'ModalSpan'>{'新姓名'} &nbsp;&nbsp;</span>
        <input type="text" className="input-box" placeholder={"输入你的新姓名"} ref={nameRef}/>
        
      </div>
      
      <div className = 'ModalInnerButtonDiv'>
      <motion.button
          className="ModalChange" 
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
          transition={{ type: "spring", stiffness: 400, damping: 20 }}
          onClick={handleSubmitted}
        >
          {'确认'}
        </motion.button>
      </div>
    </Modal>
  )
}

export default observer(NameModal)
