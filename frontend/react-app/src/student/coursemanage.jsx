import { useState, useEffect} from 'react';
import {
  HomeOutlined,SearchOutlined
} from '@ant-design/icons';
import HomeworkManage from './homeworkmanage.jsx'
import HomeworkSubmit from './homeworksubmit.jsx'
import { Breadcrumb, Card, Button, Form, Input,Divider,Pagination} from 'antd';
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import logo from '../assets/logo.png'
function CourseManage(prop) {
  const {StudentStore} = useStore();
  useEffect(()=>{
    //ManagerStore.updateData()
    StudentStore.updateCourseData()
  },[])
  
  const [searchString, setSearchString] = useState('');
  const handleOnChange=(e)=>{
    setSearchString(e.target.value)
  }
  const handleSearch = async()=> {

  }
  const changeContent = (s, c=null) => {
    if(s == 'CourseManage') {
      prop.setContent(<CourseManage setContent={prop.setContent} ></CourseManage>)
    }
    if(s == 'HomeworkSubmit') {
      prop.setContent(<HomeworkSubmit changeContent={changeContent}></HomeworkSubmit>)
    }
    if(s == 'HomeworkManage') {
      prop.setContent(<HomeworkManage changeContent={changeContent} courseID={StudentStore.currentCourseID} courseName={StudentStore.currentCourseName}></HomeworkManage>)
    }
  }


  // 每页显示的卡片数量
  const cardsPerPage = 5;

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
      <Card title='我的课程'  style={{height:'80vh'}}
        >
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
              <Card  title={card.courseName} hoverable cover={<img alt="example" src={logo} />} style={{ width: 240,margin:'10px', overflow:'hidden'}} onClick={()=>prop.setContent(<HomeworkManage changeContent={changeContent} courseID={card.courseID} courseName={card.courseName}></HomeworkManage>)}>
                <Divider></Divider>
                <div style={{height:'7vh', overflow:'auto'}}>
                  授课教师：{card.teacherName}
                  <br></br>
                  课程描述：{card.courseDescribe}
                  <br></br>
                </div>
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