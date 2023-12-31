import React,{ useState, useEffect } from 'react';
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined,
  SnippetsOutlined
} from '@ant-design/icons';
import { observer } from 'mobx-react-lite'
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.jpg'
import { Layout, Menu, Button, theme, ConfigProvider } from 'antd';
import {Avatar} from 'antd';
import ClassManage from './classmanage.jsx';
import ArgumentManage from './argumentmanage.jsx'
import touxiang from '../assets/touxiang.png'
const { Header, Sider, Content } = Layout;
const TeacherLayout = () => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate()
  const [nowKey, setNowKey] = useState(1);
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  const [content, setContent] = useState(<div></div>);
  const handleAvatarOnClicked = ()=> {
    navigate("/profile", {replace:false});
  }
  useEffect(()=>{
    if(nowKey == 1) {
      setContent(
        <ClassManage setContent={setContent}></ClassManage>
      )
    }
    if(nowKey==2) {
      setContent(
        <ArgumentManage setContent={setContent}></ArgumentManage>
      )
    }
  },[nowKey])
  function handleLeftMenuClicked  ({key}) {
    console.log(key)
    setNowKey(key)
    if(nowKey == 1) {
      setContent(
        <ClassManage setContent={setContent}></ClassManage>
      )
    }
    if(nowKey==2) {
      setContent(
        <ArgumentManage setContent={setContent}></ArgumentManage>
      )
    }
  }
  
  function getLabel(x) {
    if(x === 1) {
      return "课程管理"
    }
    if(x === 2) {
      return "作业投诉"
    }
  }
  return (
    <ConfigProvider
    theme={{
      token: {
        // Seed Token，影响范围大
        colorPrimary: '#ED4192',
        borderRadius: 10,
        // 派生变量，影响范围小
        colorBgContainer: 'white',
        
      },
    }}
  >
    
  
    <Layout className='myLayOut'>
      
      <Sider trigger={null} collapsible collapsed={collapsed} 
        style={{
          overflow: 'auto',
          height: '100vh',
          left: 0,
          top: 0,
          bottom: 0,
          
        }} 
      >
        <div className="demo-logo-vertical" />
        <div className='logo'>
          <img className='img' src={logo} alt='作业互评系统' />
        </div>
        <Menu
          theme='light'
          mode="inline"
          defaultSelectedKeys={['1']}
          items={[UserOutlined,SnippetsOutlined].map(
            (icon, index) => ({
              key: String(index + 1),
              icon: React.createElement(icon),
              label: <span> {`${getLabel(index+1)}`}</span>,
            }),
          )}
          onSelect={handleLeftMenuClicked}
          style={{
             height: '92vh',
            
            borderRight: 0,
          }}
        />
      </Sider>
      <Layout >
        <Header
          style={{
            padding: 0,
            top: 0,
            zIndex: 100,
            background: colorBgContainer,
            position:'sticky',
            
          }}
          
        >
          <div style={{display:'flex'}}>
            <Button
              type="text"
              icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
              onClick={() => setCollapsed(!collapsed)}
              style={{
                fontSize: '16px',
                width: 64,
                height: 64,
              }}
            />
            作业互评系统
            <div style={{marginLeft:'auto', marginRight: '1vw' }}>
              <Avatar className = 'MenuAvatar' size={50} onClick ={handleAvatarOnClicked} src={touxiang}></Avatar>
            </div>
          </div>
        </Header>
        
        <Content
          style={{
            margin: '24px 16px',
            padding: 24,
            minHeight: 280,
            background: colorBgContainer,
          }}
        >
          {content}
        </Content>
      </Layout>
    </Layout>
    </ConfigProvider>
  );
};
export default observer(TeacherLayout);