import {
  HomeOutlined,
  PlusOutlined, EditOutlined,DeleteOutlined,CopyOutlined,UserOutlined,ExclamationCircleOutlined,SearchOutlined
} from '@ant-design/icons';
import { Breadcrumb, Card, Button, Form, Input,Table,Modal, message, Space, Popconfirm,Pagination,Divider } from 'antd';
import {useState, useEffect} from 'react'
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import HomeworkManage from './homeworkmanage.jsx'
import GradeManage from './grademanage.jsx'
import { http } from '../utils/http.jsx';
import logo from '../assets/logo.png'
import Similarity from './similarity.jsx'
const { TextArea } = Input;
function ClassManage(prop) {
  const [visible,setVisible]=useState(false);
  const [editVisible,setEditVisible]=useState(false);
  const [studentVisible,setStudentVisible]=useState(false);
  const [editing, setEditing] = useState({})
  useEffect(()=>{
    TeacherStore.updateCourseData()
  },[])
  const handeDeleteCourse = async(v) => {
    try {
      const ret = await http.post('/teacher/deleteCourseByCourseID',{
        courseID:v.courseID,
      })
      if(ret.data?.message == 'success') {
        message.success('删除成功')
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    TeacherStore.updateCourseData()
  }
  const handleDeleteStudent = async(v) => {
    try {
      const ret = await http.post('/teacher/deleteStudentFromCourse',{
        // courseName:v.courseName,
        courseID:editing.courseID,
        uid:v.uid,
      })
      if(ret.data?.message == 'success') {
        message.success('删除成功')
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    TeacherStore.updateStudentData(editing.courseID)
  }
  const handleAddCourse = async(v)=> {
    try {
      const ret = await http.post('/teacher/addCourse',{
        courseName:v.courseName,
        courseDescribe:v.courseDescribe,
      })
      if(ret.data?.message == 'success') {
        message.success('添加成功')
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    TeacherStore.updateCourseData()
    TeacherStore.updateStudentData(editing.courseID)
    setVisible(false)
  }
  const handleEditCourse = async(v)=> {
    console.log('inEdit ' + v.courseName + editing.courseID )
    if(v.courseName){
      try {
        const ret = await http.post('/teacher/setCourseName',{
          courseID:editing.courseID,
          courseName:v.courseName,
        })
        if(ret.data?.message == 'success') {
          if(!v.courseDescribe) {
            
            message.success('修改成功')
            setEditVisible(false)
          }
            console.log('ok handle edit1')
        }
        else message.error(ret.data.message)
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
        return
      }
      TeacherStore.updateCourseData()
        TeacherStore.updateStudentData(editing.courseID)
    }
    if(v.courseDescribe){
      try {
        const ret = await http.post('/teacher/setCourseDescribe',{
          courseID:editing.courseID,
          courseDescribe:v.courseDescribe,
        })
        if(ret.data?.message == 'success') {
          // message.success('成功')
          console.log('ok handle edit2')
          message.success('修改成功')
          setEditVisible(false)
        }
        else message.error(ret.data.message)
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
      }
      
      
    }
    TeacherStore.updateCourseData()
      TeacherStore.updateStudentData(editing.courseID)
  }
  const handleAddStudentByUsername = async() => {
    // console.log(editing.courseID, sname)
    try {
      const ret = await http.post('/teacher/addStudentToCourse',{
        courseID:editing.courseID,
        username:sname
      })
      if(ret.data?.message == 'success') {
        // message.success('成功')
        console.log(ret.data)
        message.success('添加成功')
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    TeacherStore.updateCourseData()
    TeacherStore.updateStudentData(editing.courseID)
  }
  const handleAddStudentByUID = async() => {
    try {
      const ret = await http.post('/teacher/addStudentToCourse',{
        courseID:editing.courseID,
        uid:sid
      })
      if(ret.data?.message == 'success') {
        // message.success('成功')
        message.success('添加成功')
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    TeacherStore.updateCourseData()
    TeacherStore.updateStudentData(editing.courseID)
  }
  
  const [sname, setSname] = useState('')
  const [sid, setSid] = useState('')
  const handleStudentNameOnChange = (e) => {
    setSname(e.target.value)
  }
  const handleStudentUIDOnChange = (e) => {
    setSid(e.target.value)
  }
  const changeContent = (s, c=null) => {
    console.log("s is " + s)
    if(s == 'ClassManage') {
      prop.setContent(<ClassManage setContent={prop.setContent} ></ClassManage>)
    }
    if(s == 'HomeworkManage') {
      prop.setContent(<HomeworkManage changeContent={changeContent} courseName={TeacherStore.currentCourseName} courseID={TeacherStore.currentCourseID}></HomeworkManage>)
    }
    if(s == 'GradeManage') {
      prop.setContent(<GradeManage changeContent={changeContent} assignmentID={c.assignmentID} assignmentName={c.assignmentName}></GradeManage>)
    }
    if(s == 'Similarity') {
      
      prop.setContent(<Similarity changeContent={changeContent} assignmentID={c.assignmentID} assignmentName={c.assignmentName}></Similarity>)
    }
  }
  const {TeacherStore} = useStore();
  const [myForm] = Form.useForm()
  const [editForm] = Form.useForm()

  // 每页显示的卡片数量
  const cardsPerPage = 5;

  // 当前页码
  const [currentPage, setCurrentPage] = useState(1);

  // 计算当前页应该显示的卡片
  const startIndex = (currentPage - 1) * cardsPerPage;
  const endIndex = startIndex + cardsPerPage;
  const currentCards = TeacherStore.courseData.slice(startIndex, endIndex);

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
            title: '课程管理',
          },
          
        ]}
        style={{marginBottom:'7px'}}
      />

      <Card title='我的课程' extra={
        <Button type='primary' icon={<PlusOutlined></PlusOutlined>} onClick={()=>setVisible(true)}></Button>
      } style={{height:'80vh'}}>
        <Form >
          <div style={{display:'flex'}}>
            <Form.Item label='搜索：' style={{width:'10vw'}}>
              <Input placeholder='请输入关键词'></Input>
            </Form.Item>
            <Form.Item style={{marginLeft:'0.5vw'}}>
              <Button type='primary' icon={<SearchOutlined></SearchOutlined>}></Button>
            </Form.Item>
          </div>
          
        </Form>


        <div style={{display:'flex', flexWrap:'wrap', alignItems:'center', marginLeft:'40px'}}>
          {TeacherStore.courseData.slice(startIndex, endIndex).map((card, index) => (
            <div key={index}>
              <Card  title={card.courseName} hoverable cover={<img alt="example" src={logo} />} style={{ width: 240,margin:'10px', overflow:'hidden'}}>
                <Divider></Divider>
                
                课程人数：{card.courseNumber}人
                <br></br>
                课程描述：{card.courseDescribe}
                <br></br>
                <Space style={{height:'50px'}}>
                  <Button type='primary' icon={<CopyOutlined></CopyOutlined>} onClick={()=>{
                    console.log('course name is ' + card.courseName)
                    setEditing(card)
                    prop.setContent(<HomeworkManage changeContent={changeContent} courseName={card.courseName} courseID={card.courseID}></HomeworkManage>)
                    
                  }}></Button>
                  <Button type='primary' icon={<UserOutlined></UserOutlined>} onClick={()=>{
                    setEditing(card)
                    console.log('c is ' + card.courseID)
                    TeacherStore.updateStudentData(card.courseID)
                    setStudentVisible(true)
                    
                    // console.log(c.key)
                  }}></Button>
                  <Button type='primary' icon={<EditOutlined></EditOutlined>} onClick={()=>{
                    setEditing(card)
                    setEditVisible(true)
                    // console.log(c.key)
                  }}></Button>
                  <Popconfirm title='删除课程' description='确定要删除这门课程？' icon={<ExclamationCircleOutlined/>} onConfirm={()=>handeDeleteCourse(card)} okText="确定" cancelText="取消">
                    <Button type="primary" icon={<DeleteOutlined/>} danger></Button>
                  </Popconfirm>
                </Space>
              </Card>
            </div>
          ))}
        </div>
        <Pagination
          current={currentPage}
          total={TeacherStore.courseData.length}
          pageSize={cardsPerPage}
          onChange={handlePageChange}
          style={{  left: '50%', transform: 'translateX(-50%)', position:'absolute', bottom:"20px"}}
        />


        {/* <Table pagination={{ pageSize:  6}}
        columns={[
        // {
        //   title:'序号',
        //   width:160,
        //   align:'center',
        //   dataIndex:'id',
        //   key:'id',
        // },
        {
          title:'课程名',
          dataIndex:'courseName',
          key:'courseName',
        },
        {
          title:'课程描述',
          dataIndex:'courseDescribe',
          key:'coursedescribe',
        },
        {
          title:'课程人数',
          dataIndex:'courseNumber',
          key:'courseNumber',
          width:160
        },
        {
          title:'操作',
          align:'center',
          width:300,
          render(_r, c) {
            return (<Space style={{height:'50px'}}>
              <Button type='primary' icon={<CopyOutlined></CopyOutlined>} onClick={()=>{
                console.log('course name is ' + c.courseName)
                setEditing(c)
                prop.setContent(<HomeworkManage changeContent={changeContent} courseName={c.courseName} courseID={c.courseID}></HomeworkManage>)
                
              }}></Button>
              <Button type='primary' icon={<UserOutlined></UserOutlined>} onClick={()=>{
                setEditing(c)
                console.log('c is ' + c.courseID)
                TeacherStore.updateStudentData(c.courseID)
                setStudentVisible(true)
                
                // console.log(c.key)
              }}></Button>
              <Button type='primary' icon={<EditOutlined></EditOutlined>} onClick={()=>{
                setEditing(c)
                setEditVisible(true)
                // console.log(c.key)
              }}></Button>
              <Popconfirm title='删除课程' description='确定要删除这门课程？' icon={<ExclamationCircleOutlined/>} onConfirm={()=>handeDeleteCourse(c)} okText="确定" cancelText="取消">
                <Button type="primary" icon={<DeleteOutlined/>} danger></Button>
              </Popconfirm>
            </Space>)
          }
        }
        ]}
          dataSource={TeacherStore.courseData}
        ></Table> */}
      </Card>

      <Modal title='修改课程' open={editVisible} maskClosable={false}  onCancel={() => setEditVisible(false)} onOk={()=>editForm.submit()}   destroyOnClose>
        <Form style={{marginTop:'2vh'}} onFinish={(v)=>{
            // console.log(v.username)
            //todo
            handleEditCourse(v)  
          }}
          form={editForm}
        >
          <Form.Item label="课程名" name="courseName" rules={[
            {
              required: false,
              message: '请输入课程名'
            }
          ]}>
            <Input   placeholder='请输入课程名' defaultValue={editing.courseName}></Input>
          </Form.Item>
          <Form.Item label="课程描述" name="courseDescribe">
            <TextArea  rows={3} placeholder='请输入课程的描述信息' defaultValue={editing.courseDescribe} ></TextArea>
          </Form.Item>
          
        </Form>
      </Modal>

      <Modal title={editing.courseName+'-学生信息'} open={studentVisible} maskClosable={false} onCancel={() => setStudentVisible(false)} onOk={()=>setStudentVisible(false)} destroyOnClose>
        <Card title='添加学生'>
          <div style={{display:'flex'}}>
            <span style={{flex:1.7,alignItems:'center',justifyItems:'center'}}>学号:</span> 
            <Input style={{flex:7}} onChange={handleStudentUIDOnChange}></Input>
            <Button type='primary' style={{flex:1, marginLeft:'0.5vw'}} onClick={()=>handleAddStudentByUID()}> 添加</Button>
          </div>
          <div style={{display:'flex', marginTop:'1vh'}}>
            <span style={{flex:1.7,alignItems:'center',justifyItems:'center'}}>用户名:</span> 
            <Input style={{flex:7}} onChange={handleStudentNameOnChange}></Input>
            <Button type='primary' style={{flex:1, marginLeft:'0.5vw'}} onClick={()=>handleAddStudentByUsername()}> 添加</Button>
          </div>
        </Card>
        <Card style={{marginTop:'1vh'}} title='已有学生'>
        <Table pagination={{pageSize: 5}}
          columns={[
          {
            title:'学号',
            dataIndex:'uid',
            key:'uid',
          },
          {
            title:'用户名',
            dataIndex:'username',
            key:'username',
          },
          {
            title:'删除',
            align:'center',
            render(_r, c) {
              return (<Space >
                <Popconfirm title='删除学生' description='确定要在这门课程中删除这名学生？' icon={<ExclamationCircleOutlined/>} onConfirm={()=>handleDeleteStudent(c)} okText="确定" cancelText="取消">
                  <Button type="primary" icon={<DeleteOutlined/>} danger></Button>
                </Popconfirm>
              </Space>)
            }
          }
          ]}
            dataSource={TeacherStore.studentData}
        ></Table></Card>
      </Modal>

      <Modal title='新增课程' open={visible} maskClosable={false} onCancel={() => setVisible(false)} onOk={()=>myForm.submit()} destroyOnClose>
        <Form style={{marginTop:'2vh'}} onFinish={(v)=>{
            // console.log(v.username)
            handleAddCourse(v)  
          }}
          form={myForm}
        >
          <Form.Item label="课程名" name="courseName" rules={[
            {
              required: true,
              message: '请输入课程名'
            }
          ]}>
            <Input   placeholder='请输入课程名'></Input>
          </Form.Item>
          <Form.Item label="课程描述" name="courseDescribe">
            <TextArea  rows={3} placeholder='请输入课程的描述信息' ></TextArea>
          </Form.Item>
          
        </Form>
        
      </Modal>
    </div>
  )
}

export default observer(ClassManage);