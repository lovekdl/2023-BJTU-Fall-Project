import React,{ useState, useEffect } from 'react';
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined,
  SnippetsOutlined,
  BellOutlined
} from '@ant-design/icons';
import { observer } from 'mobx-react-lite'
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.jpg'
import { Layout, Menu, Button, theme, ConfigProvider, FloatButton, Popover, message} from 'antd';
import {Avatar} from 'antd';
import touxiang from '../assets/touxiang.png'
import CourseManage from './coursemanage.jsx';
import HomeworkEvaluate from './homeworkevaluate.jsx';
import { http } from '../utils/http.jsx';
const { Header, Sider, Content } = Layout;
const StudentLayout = () => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate()
  const [nowKey, setNowKey] = useState(1);
  const [a, setA] = useState(0)
  const [b, setB] = useState(0)
  const {
    token: { colorBgContainer },
  } = theme.useToken();
  const [content, setContent] = useState(<div></div>);
  const handleAvatarOnClicked = ()=> {
    navigate("/profile", {replace:false});
  }
  useEffect(()=>{
    const getMessage = async()=>{
      try {
        const ret = await http.post('/student/getUnfinished',{
        })
        if(ret.data?.message == 'success') {
          console.log(ret.data)
          setA(ret.data.data.unfinishedAssignment)
          setB(ret.data.data.unfinishedPeerHomework)
        }
        else message.error(ret.data.message)
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
      }
    }
    getMessage()
  },[])
  useEffect(()=>{
    if(nowKey == 1) {
      setContent(
        <CourseManage setContent={setContent}></CourseManage>
      )
    }
    if(nowKey == 2) {
      setContent(
        <HomeworkEvaluate setContent={setContent}></HomeworkEvaluate>
      )
    }
  },[nowKey])
  function handleLeftMenuClicked  ({key}) {
    console.log(key)
    setNowKey(key)
    if(nowKey == 1) {
      setContent(
        <CourseManage setContent={setContent}></CourseManage>
      )
    }
    if(nowKey == 2) {
      setContent(
        <HomeworkEvaluate setContent={setContent}></HomeworkEvaluate>
      )
    }
  }
  
  function getLabel(x) {
    if(x === 1) {
      return "课程列表"
    }
    if(x === 2) {
      return "作业互评"
    }
  }
  const [open, setOpen] = useState(false);  
  const handleOpenChange = async (newOpen) => {
    setOpen(newOpen);
    
    try {
      const ret = await http.post('/student/getUnfinished',{
      })
      if(ret.data?.message == 'success') {
        console.log(ret.data)
        setA(ret.data.data.unfinishedAssignment)
        setB(ret.data.data.unfinishedPeerHomework)
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
  };
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
              <Avatar className = 'MenuAvatar' size={50} onClick ={handleAvatarOnClicked} src={touxiang} ></Avatar>
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
      <Popover
      content={<div><p>您有{a}门待提交的作业</p><p>您有{b}个待处理的互评任务</p></div>}
      title="消息"
      trigger="click"
      open={open}
      onOpenChange={handleOpenChange}
    >
      <FloatButton style={{right: 70, height:50,width:50}}badge={{count: a+b,}} icon={<BellOutlined />}/>
      </Popover>
    </Layout>
    </ConfigProvider>
  );
};
export default observer(StudentLayout);