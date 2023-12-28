import {
  HomeOutlined,FileAddOutlined,UploadOutlined
} from '@ant-design/icons';
import { Breadcrumb, Card, Button, Divider, message,Space,Modal,Form,Input,Upload  } from 'antd';

import {useState, useEffect} from 'react'
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import { http } from '../utils/http.jsx';

import 'froala-editor/css/froala_style.min.css';
import 'froala-editor/css/froala_editor.pkgd.min.css';

import FroalaEditor from 'react-froala-wysiwyg';

import { getTokenFromLocalStorage } from '../utils/token'
const { TextArea } = Input;
const config = {
  language: 'zh_cn', // 使用中文语言包
  placeholderText: '输入内容',
  height:'120px'
  // 其他配置项...
};

const props = {
  name: 'file',
  action: 'https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188',
  headers: {
    authorization: 'authorization-text',
    token:getTokenFromLocalStorage()
  },
  onChange(info) {
    if (info.file.status !== 'uploading') {
      console.log(info.file, info.fileList);
    }
    if (info.file.status === 'done') {
      message.success(`${info.file.name} file uploaded successfully`);
    } else if (info.file.status === 'error') {
      message.error(`${info.file.name} file upload failed.`);
    }
  },
};


function HomeworkSubmit(prop) {

  const {StudentStore} = useStore()
  const [model,setModel] = useState(StudentStore.editorContent);
  const [currentTime, setCurrentTime] = useState(new Date());
  const [visible, setVisible] = useState(false)
  const [myform] = Form.useForm()
  const [argument, setArgument] = useState('未投诉')
  const handleModelChange= (event)=>{
    setModel(event)
    
  }

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
    // console.log(StudentStore.editorContent)
    setModel(StudentStore.editorContent)
    const intervalId = setInterval(() => {
      setCurrentTime(new Date());
      //console.log(formatDate(currentTime), formatTime(currentTime))
    }, 1000);

    return () => {
      clearInterval(intervalId);
    };
  }, []);
  useEffect(() => {
    setModel(StudentStore.editorContent)
  }, [StudentStore.editorContent]);
  const handleSubmitClicked = async() => {
    console.log(model)
    try {
      const ret = await http.post('/student/addHomeworkWithoutFile',{
        assignmentID:StudentStore.currentAssignmentID,
        content:model,
        date: formatDate(currentTime),
        time: formatTime(currentTime),
      })
      if(ret.data?.message == 'success') {
        // message.success('成功')
        console.log(ret.data)
        message.success('提交成功')
        prop.changeContent('HomeworkManage')
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
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
            title: '我的课程',
          },
          {
            title: '作业查看',
          },
          {
            title: '提交作业',
          },
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title={'提交-'+StudentStore.currentCourseName+'-'+StudentStore.currentAssignmentName}>
      <h2> 作业内容</h2>
      <Card style={{height:'18vh', overflowY: 'auto'}}>
        <div dangerouslySetInnerHTML={{ __html: StudentStore.currentAssignmentDescribe }} />
      </Card>
      <Divider></Divider>
      {
        StudentStore.currentAssignment.submit=='已截止'?<div >
        <h2> 你的作业</h2>
        <Card style={{height:'20vh', overflowY: 'auto'}}>
          <div dangerouslySetInnerHTML={{ __html: StudentStore.editorContent }} />
        </Card>
          <Divider></Divider>
          <Space>
          <h2> 你的成绩:{StudentStore.currentAssignment.grade}</h2>
          <Button type='primary' onClick = {async ()=>{
            try {
              const ret = await http.post('/homework/getArgumentByHomeworkID',{
                homeworkID:StudentStore.currentHomework.homeworkID
              })
              if(ret.data?.message == 'success') {
                // message.success('成功')
                console.log(ret.data)
                setArgument(ret.data.data.homework.argument)
              }
              else message.error(ret.data.message)
            }
            catch(e) {
              console.log('catch : ',e)
              if(e.response) message.error(e.response.data.message)
              else message.error(e.message)
            }
            setVisible(true)
          }}>投诉</Button></Space>
        </div>:
        <div >
        <h2> 提交作业</h2>
        {/* <div>
          <Upload {...props}>
            <Button icon={<UploadOutlined />}> 上传文件</Button>
          </Upload>
        </div> */}
        <br></br>
          <FroalaEditor  config={config} model={model} tag="textarea"  onModelChange={handleModelChange} />
          <Button type='primary' style={{margin:'20px'}} onClick = {()=>handleSubmitClicked()}> 提交</Button>
        </div>
      }
      </Card>
      

      <div>
        <Button type='primary' onClick = {() => {
          prop.changeContent('HomeworkManage')
        }}
          style={{marginTop:'0.5vh'}}
        >返回</Button>
      </div>
      <Modal title='投诉' open={visible} maskClosable={false}  onCancel={() => setVisible(false)} onOk={()=>myform.submit()}   destroyOnClose>
        {
          argument=='未投诉'?
          <Form style={{marginTop:'2vh'}} onFinish={async (v)=>{
                console.log(v.reason)
                console.log(StudentStore.currentHomework.homeworkID)
                try {
                  const ret = await http.post('/homework/setHomeworkArgument',{
                    argument:v.reason,
                    homeworkID: StudentStore.currentHomework.homeworkID,
                  })
                  if(ret.data?.message == 'success') {
                    if(!v.courseDescribe) {
                      
                      message.success('投诉提交成功')
                      setVisible(false)
                    }
                  }
                  else message.error(ret.data.message)
                }
                catch(e) {
                  console.log('catch : ',e)
                  if(e.response) message.error(e.response.data.message)
                  else message.error(e.message)
                  return
                }
              }}
              form={myform}
            >
              
              <Form.Item label={<div>{"理由"}</div>} name="reason" rules={[
              {
                required: false,
                message: '请输入投诉的理由'
              }
            ]}>
              <TextArea  placeholder='请输入投诉的理由'></TextArea>
            </Form.Item>
              
          </Form> : argument=='已处理'?
           <div><h3> 状态:已处理</h3></div>:<div>

            <h3> 状态:投诉处理中</h3>
            <Card title='你的投诉内容'>
              <div dangerouslySetInnerHTML={{ __html: argument }} />
            </Card>
          </div>
        }
      </Modal>
    </div>
    
  )
}

export default observer(HomeworkSubmit);