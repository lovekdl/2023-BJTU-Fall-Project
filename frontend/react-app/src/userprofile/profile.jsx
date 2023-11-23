import React,{useState,useEffect} from 'react';
import "./userprofile.style.css"
import { observer } from 'mobx-react-lite'
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined,
} from '@ant-design/icons';
import logo from '../assets/logo.jpg'
import { Layout, Menu, Button, theme, ConfigProvider } from 'antd';
import Innerprofile from './innerprofile';
import { useNavigate } from 'react-router-dom';
import {Avatar} from 'antd';

const { Header, Sider, Content } = Layout;

function Profile() {
  
  const [collapsed, setCollapsed] = useState(false);
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  const [nowKey, setNowKey] = useState(1);
  const navigate = useNavigate()
  const [content, setContent] = useState(<div></div>);
  function getLabel(x) {
    if(x === 1) {
      return "个人信息"
    }
  }
  const handleAvatarOnClicked = ()=> {
    navigate("/profile", {replace:false});
  }
  useEffect(() => {
    const handleWheel = (e) => {
      e.preventDefault();
    };
    window.addEventListener('wheel', handleWheel, { passive: false });
    return () => {
      window.removeEventListener('wheel', handleWheel);
    };
  }, []);
  useEffect(()=>{
    if(nowKey == 1) {
      setContent(
        <Innerprofile></Innerprofile>
      )
    }
  },[nowKey])
  
  function handleLeftMenuClicked  ({key}) {
    console.log(key)
    setNowKey(key)
  }
  function returnToMainPage() {
    navigate("/", {replace:true});
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
          <img onClick={returnToMainPage} className='img' src={logo} alt='作业互评系统' />
        </div>
        <Menu
            theme='light'
            mode="inline"
            defaultSelectedKeys={['1']}
            // className='SliderMenu'
            items={[UserOutlined].map(
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
            <div style={{marginLeft:'auto', marginRight: '1vw' }}>
              <Avatar className = 'MenuAvatar' size={50} onClick ={handleAvatarOnClicked}></Avatar>
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
}

// function Profile() {
  
//   const [nowKey, setNowKey] = useState(1);
//   const [content, setContent] = useState(<div></div>);
//   function getLabel(x) {
//     if(x === 1) {
//       return "个人信息"
//     }
//     // if(x === 2) {
//     //   return t("planets")
//     // }
    
//   }
//   useEffect(() => {
//     const handleWheel = (e) => {
//       e.preventDefault();
//     };

//     // 在组件挂载时添加滚轮事件监听器
//     window.addEventListener('wheel', handleWheel, { passive: false });

//     // 在组件卸载时移除滚轮事件监听器
//     return () => {
//       window.removeEventListener('wheel', handleWheel);
//     };
//   }, []);
//   useEffect(()=>{
//     if(nowKey == 1) {
//       setContent(
//         <Innerprofile></Innerprofile>
//       )
//     } 
//     // else if(nowKey == 2) {
//     //   setContent(
//     //     <ProfilePlanets></ProfilePlanets>
//     //   )
//     // }
//     // else {
//     //   setContent(<div></div>);
//     // } 
//   },[nowKey])
  
//   function handleLeftMenuClicked  ({key}) {
//     console.log(key)
//     setNowKey(key)
//   }
//   return (
//     <div>
      
//       <Layout>
//         <Sider
//           breakpoint="lg"
//           collapsedWidth="0"
//           onBreakpoint={(broken) => {
//             console.log(broken);
//           }}
//           onCollapse={(collapsed, type) => {
//             console.log(collapsed, type);
//           }}
//           className='Slider'
//         >
//           <div className="demo-logo-vertical" />
//           <Menu
//             mode="inline"
//             defaultSelectedKeys={['1']}
//             className='SliderMenu'
//             items={[UserOutlined].map(
//               (icon, index) => ({
//                 key: String(index + 1),
//                 icon: React.createElement(icon),
//                 label: <span> {`${getLabel(index+1)}`}</span>,
//               }),
//             )}
//             onSelect={handleLeftMenuClicked}
//           />
            
//         </Sider>
      
//         <Layout style={{ padding: "0 24px 0px" }}>
          
//           <Content className='ProfileContent'>
            
//             {content}

//           </Content>
//         </Layout>
        
//       </Layout>
//     </div>
//   )
// }

export default observer(Profile);