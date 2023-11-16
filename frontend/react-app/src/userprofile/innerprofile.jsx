import { observer } from "mobx-react-lite";
import {useState} from 'react';
import {motion} from "framer-motion";
import {Avatar} from 'antd'
import { useStore } from '../store';

import {
  UserOutlined
} from "@ant-design/icons";
import NameModal from "./name.modal";
import PasswordModal from "./password.modal";
import { useNavigate } from "react-router-dom";


function InnerProfile() {
  const [nameVisible, setNameVisible] = useState(false);
  const [passwordVisible, setPasswordVisible] = useState(false);
  const {ProfileStore,loginStore} = useStore();
  const navigate = useNavigate();
 
  const handleModifyName = ()=> {
    setNameVisible(!nameVisible)
  }
  const handleModifyPassword = ()=> {
    setPasswordVisible(!nameVisible)
  }
  
  const handleLogoutClicked = () => {
    loginStore.setToken('')
    navigate('/login',{replace:false})
  }
  return (
  <div>
    <NameModal visible = {nameVisible} setVisible={setNameVisible}></NameModal>
    <PasswordModal visible = {passwordVisible} setVisible={setPasswordVisible} ></PasswordModal>
    <div className = 'HeadPicture'>
      <Avatar size={128} icon={<UserOutlined />} />
    </div >
    <div className = 'InnerDiv'>
      
      <span className = 'ProfileSpan'>{'用户类型'} &nbsp;&nbsp;</span>
      <div className = 'purple-underline'> {ProfileStore.usertype=='student'?'学生':ProfileStore.usertype == 'teacher'?'教师' : '管理员'}</div>&nbsp;&nbsp;&nbsp;&nbsp;
      
    </div>
    <div className = 'InnerDiv'>
      
      <span className = 'ProfileSpan'>{ProfileStore.usertype=='student'?'学号':'工号'} &nbsp;&nbsp;</span>
      <div className = 'purple-underline'> {ProfileStore.uid}</div>&nbsp;&nbsp;&nbsp;&nbsp;
      
    </div>
    <div className = 'InnerDiv'>
      
      <span className = 'ProfileSpan'>{'姓名'} &nbsp;&nbsp;</span>
      <div className = 'purple-underline'> {ProfileStore.username}</div>&nbsp;&nbsp;&nbsp;&nbsp;
      <motion.button
        className="ProfileChange" 
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 0.95 }}
        transition={{ type: "spring", stiffness: 400, damping: 20 }}
        onClick={handleModifyName}
      >
        {'修改'}
      </motion.button>
      {/* <Button>{'修改'}</Button> */}
      {/* <input type="text" readOnly placeholder="Enter your email"></input> */}
    </div>

    <div className = 'InnerDiv'>
      <span className = 'ProfileSpan'>{'邮箱'} &nbsp;&nbsp;</span>
      <div className = 'purple-underline'> {ProfileStore.email}</div>&nbsp;&nbsp;&nbsp;&nbsp;
      {/* <input type="text" readOnly placeholder="Enter your email"></input> */}
    </div>
    <div className = 'InnerDiv'>
      <span className = 'ProfileSpan'>{'密码'} &nbsp;&nbsp;</span>
      <div className = 'purple-underline'> ********</div>&nbsp;&nbsp;&nbsp;&nbsp;
      <motion.button
        className="ProfileChange" 
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 0.95 }}
        transition={{ type: "spring", stiffness: 400, damping: 20 }}
        onClick={handleModifyPassword}
      >
        {'修改'}
      </motion.button>
      {/* <input type="text" readOnly placeholder="Enter your email"></input> */}
    </div>
    <br></br><br></br><br></br>
    <div className = 'InnerDiv'>
      
      <div className = 'hahaplace'></div>&nbsp;&nbsp;&nbsp;&nbsp;
      <motion.button
        className="LogoutButton" 
        whileHover={{ scale: 1.05 }}
        whileTap={{ scale: 0.95 }}
        transition={{ type: "spring", stiffness: 400, damping: 20 }}
        onClick={handleLogoutClicked}
      >
        {'退出登录'}
      </motion.button>
    </div>
    {/* <div className = 'InnerDiv'>
    <span className = 'ProfileSpan'>Email Address &nbsp;&nbsp;<br></br></span>
      <br></br>
      <div className = 'purple-underline'> haha</div>&nbsp;&nbsp;&nbsp;&nbsp;
        <motion.button
          className="ProfileChange" 
          whileHover={{ scale: 1.05 }}
          whileTap={{ scale: 0.95 }}
          transition={{ type: "spring", stiffness: 400, damping: 20 }}
          onClick={handleUploadClicked}
        >
          Modify
        </motion.button>
    </div> */}
  </div>
  )
}

export default observer(InnerProfile)