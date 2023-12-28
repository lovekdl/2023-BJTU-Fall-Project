import { useState, useEffect} from 'react';
import {
  HomeOutlined,
  PlusOutlined,
  DownloadOutlined,
  LoadingOutlined
} from '@ant-design/icons';
import { Breadcrumb, Card, Button, Form, Modal,Divider,Pagination, Space, Input, InputNumber, message} from 'antd';
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import { http } from '../utils/http.jsx';
import logo from '../assets/logo.png'
import EvaluatePage from './evaluatepage.jsx';
const { TextArea } = Input;
function HomeworkEvaluate(prop) {
  const {StudentStore} = useStore();
  const [visible, setVisible] = useState(false)
  const [editing, setEditing] = useState({})
  const [comment, setComment] = useState('')
  const [homeworkContent, setHomeworkContent] = useState(<LoadingOutlined />);
  const [gradeValue,setGradeValue] = useState(10)
  useEffect(()=>{
    //ManagerStore.updateData()
    StudentStore.updateEvaluateCourseData()
  },[])

  const onChange = (value) => {
    console.log('changed', value);
    setGradeValue(value)
    StudentStore.updateEvaluateCourseData()
  };
  const changeContent = () =>{ 
    prop.setContent(<HomeworkEvaluate setContent={prop.setContent}></HomeworkEvaluate>)
  }
  // 每页显示的卡片数量
  const cardsPerPage = 6;

  // 当前页码
  const [currentPage, setCurrentPage] = useState(1);

  // 计算当前页应该显示的卡片
  const startIndex = (currentPage - 1) * cardsPerPage;
  const endIndex = startIndex + cardsPerPage;
  //const currentCards = StudentStore.peerHomework.slice(startIndex, endIndex);
  const [currentCards, setCurrentCards] = useState(StudentStore.peerHomework.slice(startIndex, endIndex))
  // 处理页码变更事件
  const handlePageChange = (page) => {
    setCurrentPage(page);
    
  };
  useEffect(()=> {
    setCurrentCards(StudentStore.peerHomework.slice(startIndex, endIndex))
  },[StudentStore.peerHomework])
  const getHomeworkContent= async(x) => {
    console.log(x.homeworkID)
    try {
      const ret = await http.post('/student/getPeerHomeworkContentByHomeworkID',{
        homeworkID:x.homeworkID
      })
      if(ret.data?.message == 'success') {
        setHomeworkContent(ret.data.data.homework.homeworkContent)
        console.log(ret.data.data)
        StudentStore.setAssignmentContent(ret.data.data.homework.assignmentContent)
        StudentStore.setEvaluateHomework(ret.data.data.homework.homeworkContent)
        StudentStore.setAnswer(ret.data.data.homework.assignmentAnswer)
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
            title: '互评作业',
          },
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title='互评任务'  style={{height:'80vh'}}
        >
        <Form >
          
        </Form>
        <div style={{display:'flex', flexWrap:'wrap', alignItems:'center', marginLeft:'40px'}}>
          {StudentStore.peerHomework.slice(startIndex, endIndex).map((card, index) => (
            <div key={index}>
              <Card  title={card.courseName+'-'+card.assignmentName} hoverable  style={{width:'80vw', marginBottom:"10px", overflow:'hidden'}} onClick={
                ()=>{
                  // prop.setContent()
                  prop.setContent(<EvaluatePage changeContent={changeContent} peerID={card.peerID}></EvaluatePage>)
                  getHomeworkContent(card);
                  
                  setEditing(card);setVisible(true);setGradeValue(60)
                  
                }} >
                
                互评分数：{card.grade}
                <br></br>
                你的评价：{card.comment}
                <br></br>
                
              </Card>
            </div>
          ))}
        </div>
        <Pagination
          current={currentPage}
          total={StudentStore.peerHomework.length}
          pageSize={cardsPerPage}
          onChange={handlePageChange}
          style={{  left: '50%', transform: 'translateX(-50%)', position:'absolute', bottom:"20px"}}
        />
      </Card>
      <Modal title={'互评-'+editing.courseName} open={visible} maskClosable={false} onCancel={() => setVisible(false)}  destroyOnClose>
          作业内容：<br></br>
          <Card style={{height:'10vh', overflow:'auto'}}>
              <div  dangerouslySetInnerHTML={{ __html: editing.assignmentDescribe }} />
            </Card>
          <br></br>
          {/* 附件下载：<Button type="primary" icon={<DownloadOutlined/>}></Button> */}
          <Divider></Divider>
          回答内容：
          <br></br>
            <Card style={{height:'10vh', overflow:'auto'}}>
              <div  dangerouslySetInnerHTML={{ __html: homeworkContent }} />
            </Card>
          <br></br>
          {/* 附件下载：<Button type="primary" icon={<DownloadOutlined/>}></Button> */}
          <Divider></Divider>
          评分：<InputNumber style={{width:'3vw'}} onChange={onChange} min={0} max={100} defaultValue={60}></InputNumber>
          <br></br>
          留言：<TextArea value={comment}
            onChange={(e) => setComment(e.target.value)}
            placeholder="Controlled autosize"
            autoSize={{ minRows: 3, maxRows: 5 }}></TextArea>
          <div>
            <Button type='primary' style={{marginTop:'10px'}} onClick={async()=>{
              try {
                const ret = await http.post('/student/gradePeerHomework',{
                  peerID:editing.peerID,
                  grade:gradeValue,
                  comment:comment,
                })
                if(ret.data?.message == 'success') {
                  // message.success('成功')
                  console.log(ret.data)
                  message.success('互评成功')
                }
                else message.error(ret.data.message)
              }
              catch(e) {
                console.log('catch : ',e)
                if(e.response) message.error(e.response.data.message)
                else message.error(e.message)
              }
              StudentStore.updateEvaluateCourseData()
            }}> 完成互评</Button>
          </div>
      </Modal>

      
    </div>
  )
}

export default observer(HomeworkEvaluate);