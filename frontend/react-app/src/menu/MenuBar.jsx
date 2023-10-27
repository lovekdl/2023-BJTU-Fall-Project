
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import "./menu.css"
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import Button from '@material-ui/core/Button';
import { useNavigate } from 'react-router-dom';
import {Avatar} from 'antd';
import {
  UserOutlined
} from "@ant-design/icons";
function ButtonAppBar() {

  const {loginStore} = useStore();
  const navigate = useNavigate()
  const handleClicked = () =>{
    console.log("clicked")
    navigate('/login', {replace:false})
  }
  const handleAvatarOnClicked = ()=> {
    navigate("/profile", {replace:false});
  }
  return (
    
    <div className='menu'>
      
      <AppBar color='inherit' position='absolute'>
        <Toolbar className = 'TollBar'>
          {<div style={{color:"white"}}>学生互评系统</div>}
          <div style={{ position: 'absolute', right: 30 , color:"white"}}>
            {loginStore.token===''? <Button color="inherit" onClick={handleClicked}>Login</Button> : (<Avatar className = 'MenuAvatar' size={50}  onClick ={handleAvatarOnClicked} icon={<UserOutlined />} />)}
          </div>
        </Toolbar>
      </AppBar>
    </div>
  );
}

ButtonAppBar.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default observer(ButtonAppBar);