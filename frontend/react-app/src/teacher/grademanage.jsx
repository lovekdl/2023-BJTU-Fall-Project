import {
  HomeOutlined,DownloadOutlined,EditOutlined,SearchOutlined
} from '@ant-design/icons';
import { Breadcrumb, Card, Button, Form, Input,Table, message, Space,Popconfirm,Modal,Divider,InputNumber } from 'antd';
import {useEffect, useState} from 'react'
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import { http } from '../utils/http.jsx';
function GradeManage(prop) {

  const {TeacherStore} = useStore()
  const [visible, setVisible] = useState(false)
  const [currentUsername, setCurrentUsername] = useState('aaa')
  const [gradeValue,setGradeValue] = useState(10)
  const [content, setContent] = useState('1+1=2')
  const [excellentHomeworkID, setExcellentHomeworkID] = useState('123')
  async function getmessage() {
    try {
      const ret = await http.post('/homework/getExcellentHomeworkByAssignmentID',{
        assignmentID:prop.assignmentID,
      })
      if(ret.data?.message == 'success') {
        setExcellentHomeworkID(ret.data.data.homework.homeworkID)
        // console.log(ret.data.data)
      }
      // else message.error(ret.data.message)
      
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
  }
  useEffect(()=>{
    // console.log('prop.assignmentID = ' + prop.assignmentID);
    // console.log('currentCourseID = ' + TeacherStore.currentCourseID);
    TeacherStore.updateStudentGradeData(TeacherStore.currentCourseID, prop.assignmentID)
    // TeacherStore.updateAssignmentData(prop.assignmentID)
    
    getmessage()
    
  },[])

  const onChange = (value) => {
    console.log('changed', value);
    setGradeValue(value)

  };
  async function changeGrade(homeworkID, gradeValue) {
    try {
      const ret = await http.post('/homework/setScore', {
        homeworkID,
        grade:gradeValue
      })
      if(ret.data) {
        message.success('修改成功')
      }


      // window.location.reload()
    } catch(e) {
      console.log('catch ')
      console.log(e)
    }
    TeacherStore.updateStudentGradeData(TeacherStore.currentCourseID, prop.assignmentID)
  }
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
          {
            title: '作业管理',
          },
          {
            title: '成绩管理',
          },
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title={'成绩管理-'+prop.assignmentName} >
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
        <Table pagination={{ pageSize:  6}}
        columns={[
        // {
        //   title:'序号',
        //   width:160,
        //   align:'center',
        //   dataIndex:'id',
        //   key:'id',
        // },
        {
          title:'学号',
          dataIndex:'uid',
          key:'uid',
        },
        {
          title:'学生名',
          dataIndex:'username',
          key:'username',
        },
        {
          title:'提交状态',
          dataIndex:'submit',
          key:'submit',
        },
        {
          title:'提交日期',
          dataIndex:'date',
          key:'date',
        },
        
        {
          title:'提交时间',
          dataIndex:'time',
          key:'time',
        },
        {
          title:'成绩',
          dataIndex:'grade',
          key:'grade',
          render(_r,c) {
            return(
              <div>
                {c.grade}
                {c.submit=='已提交'&& 
                
                  
                  <Popconfirm title='修改成绩'  description={<InputNumber onChange={onChange} min={0} max={100} defaultValue={c.grade}/>} icon={<EditOutlined/>} 
                  
                    onConfirm={()=>{
                    //todo
                    changeGrade(c.homeworkID,gradeValue)
                    
                    console.log('changed', gradeValue);
                  }} 
                  
                  okText="确定" cancelText="取消" >
                    <Button type='primary' style={{marginLeft:'10px'}} icon={ <EditOutlined/>}></Button>
                  </Popconfirm>
                
              }
                
              </div>
            )
          }
        },
        {
          title:'查看作业',
          dataIndex:'assignment',
          key:'assignment',
          width:160,
          render(_r, c) {
            return (<Space style={{height:'50px'}}>
             
                <Button type="primary" disabled={c.submit=='已提交'?false:true} onClick={async ()=>{
                  setCurrentUsername(c.username)
                  setVisible(true)
                  try {
                    const ret = await http.post('/homework/getContentByHomeworkID', {
                      homeworkID:c.homeworkID

                    })
                    if(ret.data) {
                      setContent(ret.data.data.homework.homeworkContent)
                    }
              
                    // window.location.reload()
                  } catch(e) {
                    if(e.response) message.error(e.response.data.message)
                    else message.error(e.message)
                    console.log(e)
                  }
                  
                }} > 查看作业 </Button>
              
            </Space>)
          }
        },
        {
          title:'优秀作业',
          dataIndex:'excellentHomework',
          key:'excellentHomework',
          width:160,
          render(_r, c) {
            return (
              <div>{c.homeworkID!=excellentHomeworkID?<Space style={{height:'50px'}}>
             
                <Button type="primary" disabled={c.submit=='已提交'?false:true} onClick={async ()=>{
                  try {
                    const ret = await http.post('/homework/setHomeworkExcellent', {
                      homeworkID:c.homeworkID,
                    })
                    if(ret.data) {
                      console.log('设置成功')
                    }
              
                    // window.location.reload()
                  } catch(e) {
                    if(e.response) message.error(e.response.data.message)
                    else message.error(e.message)
                    console.log(e)
                  }
                  TeacherStore.updateStudentGradeData(TeacherStore.currentCourseID, prop.assignmentID)
                  getmessage()
                }} > 设为优秀作业 </Button>
              
            </Space>:<div style={{color:'green'}}>已设为优秀作业</div>}</div>)
          }
        }
        ]}
          dataSource={TeacherStore.studentData}
        ></Table>
      </Card>
      <div>
        <Button type='primary' onClick = {() => {
          prop.changeContent('HomeworkManage')
        }}
          style={{marginTop:'0.5vh'}}
        >返回</Button>
      </div>
      <Modal title={'查看作业-'+currentUsername} open={visible} maskClosable={false} onCancel={() => setVisible(false)} destroyOnClose>
        <Card title='作业内容：'>
        <div  dangerouslySetInnerHTML={{ __html: content }}>
        </div></Card>
        <Divider></Divider>
        {/* 附件下载：<Space style={{marginTop:'10px'}}><Button type="primary" icon={<DownloadOutlined/>}></Button></Space> */}
        
      </Modal>
    </div>
    
  )
}

export default observer(GradeManage);