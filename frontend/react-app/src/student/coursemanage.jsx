import { useState, useEffect} from 'react';
import {
  HomeOutlined,
  PlusOutlined, EditOutlined,DeleteOutlined,ExclamationCircleOutlined,DownOutlined,CopyOutlined,UserOutlined
} from '@ant-design/icons';

import {  SearchOutlined } from '@material-ui/icons';
import { Breadcrumb, Card, Button, Form, Input,Divider,Pagination} from 'antd';
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import { http } from '../utils/http.jsx';
import logo from '../assets/logo.png'
function CourseManage() {
  const {StudentStore} = useStore();
  useEffect(()=>{
    //ManagerStore.updateData()
  },[])
  
  const [searchString, setSearchString] = useState('');
  const handleOnChange=(e)=>{
    setSearchString(e.target.value)
  }
  const handleSearch = async()=> {

  }
  // 每页显示的卡片数量
  const cardsPerPage = 6;

  // 当前页码
  const [currentPage, setCurrentPage] = useState(1);

  // 计算当前页应该显示的卡片
  const startIndex = (currentPage - 1) * cardsPerPage;
  const endIndex = startIndex + cardsPerPage;
  const currentCards = StudentStore.courseData.slice(startIndex, endIndex);

  // 处理页码变更事件
  const handlePageChange = (page) => {
    setCurrentPage(page);
  };
  return (
    <div>
      
      <Breadcrumb
        items={[
          {
            href: '',
            title: <HomeOutlined />,
          },
          {
            title: '我的课程',
          },
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title='我的课程' extra={
        <Button type='primary' icon={<PlusOutlined></PlusOutlined>} ></Button>
      } style={{height:'80vh'}}>
        <Form >
          <div style={{display:'flex'}}>
            <Form.Item label='搜索：' style={{width:'10vw'}}>
              <Input placeholder='请输入关键词' onChange={handleOnChange}></Input>
            </Form.Item>
            <Form.Item style={{marginLeft:'0.5vw'}}>
              <Button type='primary' icon={<SearchOutlined></SearchOutlined> } onClick={()=>handleSearch()}></Button>
            </Form.Item>
          </div>
          
        </Form>
        <div style={{display:'flex', flexWrap:'wrap', alignItems:'center', marginLeft:'40px'}}>
          {currentCards.map((card, index) => (
            <div key={index}>
              <Card  title={card.courseName} hoverable cover={<img alt="example" src={logo} />} style={{ width: 240,margin:'10px', overflow:'hidden'}}>
                <Divider></Divider>
                
                授课教师：{card.teacher}
                <br></br>
                课程描述：{card.courseDescribe}
                <br></br>
                
              </Card>
            </div>
          ))}
        </div>
        <Pagination
          current={currentPage}
          total={StudentStore.courseData.length}
          pageSize={cardsPerPage}
          onChange={handlePageChange}
          style={{  left: '50%', transform: 'translateX(-50%)', position:'absolute', bottom:"20px"}}
        />
      </Card>
      

      
    </div>
  )
}

export default observer(CourseManage);