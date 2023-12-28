import {
  HomeOutlined,
  PlusOutlined, EditOutlined,DeleteOutlined,DownloadOutlined,ExclamationCircleOutlined,SearchOutlined
} from '@ant-design/icons';
import { Breadcrumb, Card, Button, Form, Input,Table,Modal, message, Space,DatePicker,TimePicker, Popconfirm,Checkbox } from 'antd';
const CheckboxGroup = Checkbox.Group;

import {useState, useEffect} from 'react'
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import { http } from '../utils/http.jsx';

const plainOptions = ['未提交', '已提交', '已截止'];
const defaultCheckedList = ['未提交', '已提交', '已截止'];

function HomeworkManage(prop) {

  const {StudentStore} = useStore()
  const [excellentHomeworkContent, setExcellentHomeworkContent] = useState('优秀作业的内容')
  const [checkedList, setCheckedList] = useState(defaultCheckedList);
  const [excellentHomeworkVisible,setExcellentHomeworkVisible] = useState(false)
  const [excellentHomeworkID, setExcellentHomeworkID] = useState('124')
  const onCheckedChange = (list) => {
    setCheckedList(list);
    StudentStore.setFilterList(list)
  };
  useEffect(()=>{
    // console.log('prop.courseID = ' + prop.courseID);
    console.log(prop.courseID)
    StudentStore.updateAssignmentData(prop.courseID)
    StudentStore.setCurrentCourseID(prop.courseID)
    StudentStore.setCurrentCourseName(prop.courseName)
    StudentStore.setFilterList(checkedList)
  },[])
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
          {
            title: '作业查看',
          },
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title={prop.courseName+'-作业'} >
          <div style={{display:'flex'}}>
            <CheckboxGroup options={plainOptions} value={checkedList} onChange={onCheckedChange} />
          </div>
          
        
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
          title:'作业名',
          dataIndex:'assignmentName',
          key:'assignmentName',
        },
        {
          title:'截止日期',
          dataIndex:'date',
          key:'assignmentDate',
        },
        {
          title:'截止时间',
          dataIndex:'time',
          key:'assignmentTime',
        },
        // {
        //   title:'作业附件',
        //   dataIndex:'assignmentFile',
        //   key:'assignmentFile',
        //   width:160,
        //   render() {
        //     return (<Space style={{height:'50px'}}>
             
        //         <Button type="primary" icon={<DownloadOutlined/>}></Button>
              
        //     </Space>)
        //   }
        // },
        {
          title:'操作',
          align:'center',
          width:300,
          render(_r, c) {
            return (
              <div>
                {c.submit=='未提交'?
                <Button type="primary" onClick={
                  
                  ()=> {
                    StudentStore.setCurrentAssignmentID(c.assignmentID)
                    StudentStore.setCurrentAssignmentName(c.assignmentName)
                    StudentStore.setCurrentAssignmentDescribe(c.assignmentDescribe)
                    StudentStore.setEditorContent('')
                    prop.changeContent('HomeworkSubmit')
                  }
                }> 提交作业</Button> : c.submit=='已提交'?<div>
                    <Button onClick={
                      async ()=> {
                        StudentStore.setCurrentAssignmentID(c.assignmentID)
                        StudentStore.setCurrentAssignmentName(c.assignmentName)
                        StudentStore.setCurrentAssignmentDescribe(c.assignmentDescribe)
                        StudentStore.setCurrentAssignment(c)
                        StudentStore.setEditorContent('')
                        
                        prop.changeContent('HomeworkSubmit')
                        try {
                          const ret = await http.post('/student/getHomeworkContentByAssignmentID',{
                            assignmentID:StudentStore.currentAssignmentID,

                          })
                          if(ret.data?.message == 'success') {
                            // message.success('成功')
                            console.log(ret.data.data)
                            // console.log(ret.data.data.homework.homeworkContent)
                            StudentStore.setEditorContent(ret.data.data.homework.homeworkContent)
                            StudentStore.setCurrentHomework(ret.data.data.homework)
                          }
                          else message.error(ret.data.message)
                        }
                        catch(e) {
                          console.log('catch : ',e)
                          if(e.response) message.error(e.response.data.message)
                          else message.error(e.message)
                        }
                        
                      }
                    } > 已提交<EditOutlined/></Button>
                  </div> : <div>
                    <Button disabled={true}>已截止</Button> 
                    <Button icon={<SearchOutlined onClick={
                      async ()=> {
                        StudentStore.setCurrentAssignmentID(c.assignmentID)
                        StudentStore.setCurrentAssignmentName(c.assignmentName)
                        StudentStore.setCurrentAssignmentDescribe(c.assignmentDescribe)
                        StudentStore.setCurrentAssignment(c)
                        
                        StudentStore.setEditorContent('')
                        prop.changeContent('HomeworkSubmit')
                        try {
                          const ret = await http.post('/student/getHomeworkContentByAssignmentID',{
                            assignmentID:StudentStore.currentAssignmentID,

                          })
                          if(ret.data?.message == 'success') {
                            // message.success('成功')
                            // console.log(ret.data.data.homework)
                            console.log(ret.data.data)
                            StudentStore.setEditorContent(ret.data.data.homework.homeworkContent)
                            StudentStore.setCurrentHomework(ret.data.data.homework)
                          }
                          else message.error(ret.data.message)
                        }
                        catch(e) {
                          console.log('catch : ',e)
                          if(e.response) message.error(e.response.data.message)
                          else message.error(e.message)
                        }
                        
                      }
                    }></SearchOutlined>}></Button>
                    </div>}
              </div>
            )
          }
        },
        {
          title:'成绩',
          align:'center',
          dataIndex:'grade',
          key:'grade',
        },
        {
          title:'优秀作业',
          align:'center',
          dataIndex:'excellentHomework',
          key:'grade',
          render(_r, c) {
            return (
            <div>
              <Button type='primary' onClick={
                async() => {
                  // console.log(c.assignmentID)
                  
                  try {
                    var ret = await http.post('/homework/getExcellentHomeworkByAssignmentID',{
                      assignmentID:c.assignmentID,
                    })
                    if(ret.data?.message == 'success') {
                      // console.log(ret.data)
                      setExcellentHomeworkID(ret.data.data.homework.homeworkID)
                      ret = await http.post('/homework/getContentByHomeworkID',{
                        homeworkID:excellentHomeworkID
                      })
                      if(ret.data?.message == 'success') {
                        setExcellentHomeworkContent(ret.data.data.homework.homeworkContent)
                        // console.log(ret.data.data)
                        setExcellentHomeworkVisible(true)
                      }
                      else message.error(ret.data.message)
                    }
                    else message.error(ret.data.message)
                    console.log(excellentHomeworkID)
                    
                    
                  }
                  catch(e) {
                    console.log('catch : ',e)
                    if(e.response) message.error(e.response.data.message)
                    else message.error(e.message)
                  }
                }
              }> 查看 </Button>
            </div>)
          }
        },
        ]}
          dataSource={StudentStore.filteredAssignments}
        ></Table>
      </Card>

      <div>
        <Button type='primary' onClick = {() => {
          prop.changeContent('CourseManage')
        }}
          style={{marginTop:'0.5vh'}}
        >返回</Button>
      </div>
      <Modal title='查看优秀作业' open={excellentHomeworkVisible} maskClosable={false} onCancel={() => setExcellentHomeworkVisible(false)} destroyOnClose>
        <Card> 
        <div  dangerouslySetInnerHTML={{ __html: excellentHomeworkContent }}></div>
        </Card>
      </Modal>
    </div>
    
  )
}

export default observer(HomeworkManage);