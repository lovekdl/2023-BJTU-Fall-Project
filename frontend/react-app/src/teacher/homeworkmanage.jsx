import {
  HomeOutlined,
  PlusOutlined, EditOutlined,DeleteOutlined,ExclamationCircleOutlined,SearchOutlined
} from '@ant-design/icons';
import { Breadcrumb, Card, Button, Form, Input,Table,Modal, message, Space,DatePicker,TimePicker, Popconfirm, Divider, InputNumber } from 'antd';
import {useState, useEffect} from 'react'
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import { http } from '../utils/http.jsx';
const timeFormat='HH:mm:ss'
const { TextArea } = Input;
import dayjs from 'dayjs';
function HomeworkManage(prop) {
  const [visible,setVisible]=useState(false);
  const [editVisible,setEditVisible]=useState(false);
  const [evaluateVisible, setEvaluateVisible] = useState(false)
  const [time, setTime] = useState('00:00:00');
  const [date, setDate] = useState('2015');
  const {TeacherStore} = useStore()
  const [editing, setEditing] = useState({})
  const [peerNumber, setPeerNumber] = useState(1)
  const [answer, setAnswer] = useState('')

  const [currentTime, setCurrentTime] = useState(new Date());

  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const formatTime = (date) => {
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${hours}:${minutes}:${seconds}`;
  };

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentTime(new Date());
      //console.log(formatDate(currentTime), formatTime(currentTime))
    }, 1000);

    return () => {
      clearInterval(intervalId);
    };
  }, []); // 空数组表示只在组件挂载时执行一次
  const handlePeerNumberOnChange = (value) => {
    setPeerNumber(value)
  }
  useEffect(()=>{
    // console.log('prop.courseID = ' + prop.courseID);
    TeacherStore.updateAssignmentData(prop.courseID)
    TeacherStore.setCurrentCourseID(prop.courseID)
    TeacherStore.setCurrentCourseName(prop.courseName)
  },[])
  const updatePeerState = async(id) => {
    console.log("update PeerState: "+id)
    try {
      const ret = await http.post('/teacher/getPeerListByAssignmentID',{
        assignmentID:id,
      })
      if(ret.data?.message == 'success') {
        console.log(ret.data.data)
        TeacherStore.setPeerState(ret.data.data.peers)
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
  }
  const handleAddAssignment= async(v) =>{
    if(!date || !time) {message.error('截止时间不能为空!');return;}
    // console.log(date, time)
    // message.success('新增成功')
    try {
      const ret = await http.post('/teacher/addAssignmentWithoutFile',{
        courseID:prop.courseID,
        assignmentName:v.assignmentName,
        assignmentDescribe:v.assignmentDescribe,
        date:date,
        time:time
      })
      if(ret.data?.message == 'success') {
        message.success('新增成功')
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    setVisible(false)
    TeacherStore.updateAssignmentData(prop.courseID)
  }
  
  const handeDeleteAssignment = async(v) => {
    try {
      const ret = await http.post('/teacher/deleteAssignmentByAssignmentID',{
        courseID:prop.courseID,
        assignmentID:v.assignmentID,
        assignmentName:v.assignmentName,
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
    TeacherStore.updateAssignmentData(prop.courseID)
  }
  const handleEditAssignment = async(v) => {
    if(v.assignmentName) {
      try {
        const ret = await http.post('/teacher/setAssignmentTitle',{
          courseID:prop.courseID,
          assignmentID:editing.assignmentID,
          assignmentName:v.assignmentName,
        })
        if(ret.data?.message == 'success') {
          
          console.log('修改成功1')
        }
        else message.error(ret.data.message)
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
        TeacherStore.updateAssignmentData(prop.courseID)
        return
      }
      
    }
    if(v.assignmentDescribe) {
      try {
        const ret = await http.post('/teacher/setAssignmentContent',{
          courseID:prop.courseID,
          assignmentID:editing.assignmentID,
          assignmentDescribe:v.assignmentDescribe,
        })
        if(ret.data?.message == 'success') {
          console.log('修改成功2')
        }
        else message.error(ret.data.message)
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
        TeacherStore.updateAssignmentData(prop.courseID)
        return
      }
      
    } 
    if(date && time){
      try {
        const ret = await http.post('/teacher/setAssignmentDeadline',{
          courseID:prop.courseID,
          assignmentID:editing.assignmentID,
          date:date,
          time:time
        })
        if(ret.data?.message == 'success') {
          console.log('修改成功2')
        }
        else message.error(ret.data.message)
      }
      catch(e) {
        console.log('catch : ',e)
        if(e.response) message.error(e.response.data.message)
        else message.error(e.message)
        TeacherStore.updateAssignmentData(prop.courseID)
        return
      }
    }
    message.success('修改成功')
    setEditVisible(false);
    TeacherStore.updateAssignmentData(prop.courseID)
  }
  const [myForm] = Form.useForm()
  const [editForm] = Form.useForm()
  const onTimeChange = (ntime, ntimestring)=> {
    setTime(ntimestring)
    // console.log(ntime, ntimestring)
    console.log(ntimestring)
  }
  const onDateChange = (ndate, ndatestring)=> {
    setDate(ndatestring)
    // setDate('check data : ' + ndatestring)
    console.log(ndatestring)
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
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title={'作业-'+prop.courseName} extra={
        <Button type='primary' icon={<PlusOutlined></PlusOutlined>} onClick={()=>setVisible(true)}></Button>
      }>
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
        {
          title:'作业名',
          dataIndex:'assignmentName',
          key:'assignmentName',
        },
        {
          title:'作业描述',
          dataIndex:'assignmentDescribe',
          key:'assignmentDescribe',
        },
        {
          title:'截止日期',
          dataIndex:'date',
          key:'assignmentDate',
          sorter:(a,b) => {

            const dateA = new Date(a.date);
            const dateB = new Date(b.date);
            console.log(dateA - dateB)
            return dateA - dateB;
          }
        },
        {
          title:'截止时间',
          dataIndex:'time',
          key:'assignmentTime',
        },
        
        {
          title:'操作',
          dataIndex:'op',
          align:'center',
          width:200,
          render(_r, c) {
            return (<Space style={{height:'50px'}}>
              <Button type='primary' icon={<EditOutlined></EditOutlined>} onClick={()=>{
                setEditing(c);
                console.log(c.date, c.time)
                setEditVisible(true);
              }} ></Button>
               <Popconfirm title='删除作业' description='确定要删除此作业？' icon={<ExclamationCircleOutlined/>} onConfirm={()=>handeDeleteAssignment(c)} okText="确定" cancelText="取消">
                <Button type="primary" icon={<DeleteOutlined/>} danger></Button>
              </Popconfirm>
            </Space>)
          }
        },
        {
          title:'详情',
          align:'center',
          dataIndex:'situation',
          width:300,
          render(_r, c) {
            return (<Space style={{height:'50px'}}>
              <Button type='primary'  onClick={()=>{
                // setEditing(c);
                // console.log(c.date, c.time)
                // setEditVisible(true);
                prop.changeContent("GradeManage", c)
              }} >成绩查看</Button>
               
            </Space>)
          }
        },
        {
          title:'互评',
          align:'center',
          dataIndex:'assess',
          width:300,
          render(_r, c) {
            return (<Space style={{height:'50px'}}>
               <Button type='primary' onClick={()=>{setEditing(c);setEvaluateVisible(true);updatePeerState(c.assignmentID)}} >互评设置</Button>
               
            </Space>)
          }
        },
        {
          title:'相似度检测',
          align:'center',
          dataIndex:'sim',
          render(_r, c) {
            return (<Space style={{height:'50px'}}>
              <Button type='primary'  onClick={()=>{
                // setEditing(c);
                // console.log(c.date, c.time)
                // setEditVisible(true);
                prop.changeContent("Similarity", c)
              }} >查看相似度</Button>
               
            </Space>)
          }
        }
        ]}
          dataSource={TeacherStore.assignmentData}
        ></Table>
      </Card>
      <Modal title='新增作业' open={visible} maskClosable={false} onCancel={() => setVisible(false)} onOk={()=>myForm.submit()} destroyOnClose>
        <Form style={{marginTop:'2vh'}} onFinish={(v)=>{
            // console.log(v.username)
            handleAddAssignment(v)  
          }}
          form={myForm}
        >
          <Form.Item label="作业名" name="assignmentName" rules={[
            {
              required: true,
              message: '请输入作业名'
            }
          ]}>
            <Input   placeholder='请输入作业名'></Input>
          </Form.Item>
          <Form.Item label="作业内容" name="assignmentDescribe">
            <TextArea  rows={3} placeholder='请输入作业内容' ></TextArea>
          </Form.Item>
          
          截止时间：
           <DatePicker onChange={onDateChange} defaultValue={dayjs('2021-11-31', 'YYYY-MM-DD')} ></DatePicker>
           <TimePicker onChange={onTimeChange} format={timeFormat} defaultValue={dayjs('00:00:00', timeFormat)}></TimePicker>
          
        </Form>
      </Modal>

      <Modal title={'修改作业-'+editing.assignmentName} open={editVisible} maskClosable={false} onCancel={() => setEditVisible(false)} onOk={()=>editForm.submit()} destroyOnClose>
        <Form style={{marginTop:'2vh'}} onFinish={(v)=>{
            // console.log(v.username)
            handleEditAssignment(v)  
          }}
          form={editForm}
        >
          <Form.Item label="作业名" name="assignmentName" rules={[
            {
              required: false,
              message: '请输入作业名'
            }
          ]}>
            <Input   placeholder='请输入作业名' defaultValue={editing.assignmentName}></Input>
          </Form.Item>
          <Form.Item label="作业内容" name="assignmentDescribe">
            <TextArea  rows={3} placeholder='请输入作业内容' defaultValue={editing.assignmentDescribe}></TextArea>
          </Form.Item>
          
          截止时间：
           <DatePicker onChange={onDateChange} defaultValue={dayjs(editing.date, 'YYYY-MM-DD')} ></DatePicker>
           <TimePicker onChange={onTimeChange} format={timeFormat} defaultValue={dayjs(editing.time, timeFormat)}></TimePicker>
        </Form>
      </Modal>
      
      <Modal title={'设置互评-'+editing.assignmentName} open={evaluateVisible} maskClosable={false} onCancel={() => setEvaluateVisible(false)}  destroyOnClose >
          <h3>互评状态：{editing.assignmentStatus}</h3>
          <Divider></Divider>
          
          {editing.assignmentStatus=='未开始互评'&&<div>每人互评作业份数：<InputNumber min={1} defaultValue={1} onChange={handlePeerNumberOnChange}></InputNumber>
          <br></br>
          <br></br>
          <span>标准答案</span>
          <br></br>
          <Space style={{marginTop:'10px',marginBottom:'10px'}}>
          
          
          <TextArea style={{width:'20vw'}} rows={5} onChange={(e)=>setAnswer(e.target.value)}></TextArea>
          </Space>
          <br></br>
          <Popconfirm title='开始互评' description='互评开始后，将不可修改作业截止时间，确定要开始互评作业？' icon={<ExclamationCircleOutlined/>} onConfirm={async ()=>{
            try {
              const ret = await http.post('/teacher/startPeer',{
                courseID:prop.courseID,
                assignmentID:editing.assignmentID,
                peerNumber:peerNumber,
                assignmentAnswer:answer
              })
              if(ret.data?.message == 'success') {
                message.success('互评已开始')
                setEvaluateVisible(false)
              }
              else message.error(ret.data.message)
            }
            catch(e) {
              console.log('catch : ',e)
              if(e.response) message.error(e.response.data.message)
              else message.error(e.message)
            }
            TeacherStore.updateAssignmentData(prop.courseID)
          }} okText="确定" cancelText="取消">
              <Button type='primary'> 开始互评</Button>
              
          </Popconfirm> </div>}
          <br></br>
          {editing.assignmentStatus=='互评中'&&
          <div>
            <Table pagination={{ pageSize:  6}}
            columns={[
            {
              title:'评分人',
              dataIndex:'userName',
              key:'userName',
            },
            {
              title:'被评分人',
              dataIndex:'peerName',
              key:'peerName',
            },
            {
              title:'分数',
              dataIndex:'peerScore',
              key:'peerScore',
            },
            {
              title:'留言',
              dataIndex:'peerComment',
              key:'peerComment',
            },
            
            ]}
              dataSource={TeacherStore.peerState}
            ></Table>
            <Popconfirm title='停止互评' description='互评截止后，将公布成绩，确定要停止互评作业并公布成绩？' icon={<ExclamationCircleOutlined/>} onConfirm={async()=>{
            try {
              const ret = await http.post('/teacher/endPeer',{
                assignmentID:editing.assignmentID,
              })
              if(ret.data?.message == 'success') {
                message.success('互评结束')
                setEvaluateVisible(false)
              }
              else message.error(ret.data.message)
            }
            catch(e) {
              console.log('catch : ',e)
              if(e.response) message.error(e.response.data.message)
              else message.error(e.message)
            }
          }} okText="确定" cancelText="取消">
            <Button type='primary' style={{marginLeft:'20px'}}> 停止互评</Button>
          </Popconfirm></div>}
          {editing.assignmentStatus=='互评结束'&&
          <div>
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
              title:'评分人',
              dataIndex:'userName',
              key:'userName',
            },
            {
              title:'被评分人',
              dataIndex:'peerName',
              key:'peerName',
            },
            {
              title:'分数',
              dataIndex:'peerScore',
              key:'peerScore',
            },
            {
              title:'留言',
              dataIndex:'peerComment',
              key:'peerComment',
            },
            
            ]}
              dataSource={TeacherStore.peerState}
            ></Table>
          </div>}
          
      </Modal>


      <div>
        <Button type='primary' onClick = {() => {
          prop.changeContent('ClassManage')
        }}
          style={{marginTop:'0.5vh'}}
        >返回</Button>
      </div>
    </div>
    
  )
}

export default observer(HomeworkManage);