import {useState, useEffect} from 'react'
import { useStore } from '../store';
import {
  HomeOutlined,LoadingOutlined
} from '@ant-design/icons';
import { Breadcrumb,Card,Divider,Pagination,Modal,message,InputNumber,Button,Space } from 'antd';
import { observer } from 'mobx-react-lite'
import logo from '../assets/logo.png'
import { http } from '../utils/http.jsx';
function ArgumentManage() {
  
  // 每页显示的卡片数量
  const cardsPerPage = 6;
  const {TeacherStore} = useStore()
  // 当前页码
  const [currentPage, setCurrentPage] = useState(1);

  // 计算当前页应该显示的卡片
  const startIndex = (currentPage - 1) * cardsPerPage;
  const endIndex = startIndex + cardsPerPage;
  const currentCards = TeacherStore.argumentData.slice(startIndex, endIndex);
  const [visible, setVisible] = useState(false)
  const [argument, setArgument] = useState('womeicuoya')
  const [content, setContent] = useState(<LoadingOutlined />)
  const [newGrade, setNewGrade] = useState(60)
  const [currentHomeworkID,setCurrentHomeworkID] = useState(null)
  const onChange = (value) => {
    console.log('changed', value);
    setNewGrade(value)

  };
  useEffect(()=>{
    TeacherStore.updateArgumentData()
  },[])


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
            title: '作业投诉',
          },
          
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title='作业投诉' style={{height:'80vh'}}>
        {/* <Card
          hoverable
          style={{ width: 240 }}
          cover={<img alt="example" src={logo} />}
        >
          <Divider></Divider>
          haha
        </Card> */}
        {/* 显示当前页的卡片 */}
        <div style={{display:'flex', flexWrap:'wrap', alignItems:'center', marginLeft:'40px'}}>
          {currentCards.map((card, index) => (
            <Card key={index} title={card.courseName+'-'+card.assignmentName} hoverable cover={<img alt="example" src={logo} />} style={{ width: 240,margin:'10px',overflow:'hidden' }}
              onClick={async ()=>{
                setArgument(card.argument)
                setCurrentHomeworkID(card.homeworkID)
                setVisible(true)
                try {
                  const ret = await http.post('/homework/getContentByHomeworkID', {
                    homeworkID:card.homeworkID

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
                
                
              }}
            >
              <Divider></Divider>
              学生名：{card.username}
            </Card>
          ))}
        </div>
        <Pagination
          current={currentPage}
          total={TeacherStore.argumentData.length}
          pageSize={cardsPerPage}
          onChange={handlePageChange}
          style={{  left: '50%', transform: 'translateX(-50%)', position:'absolute', bottom:"20px"}}
        />
      </Card>

      <Modal title='处理投诉' open={visible} maskClosable={false}  onCancel={() => setVisible(false)}  destroyOnClose>
        作业内容：{content}
        <Divider></Divider>
        学生留言：{argument}
        <Divider></Divider>
        <Space>
          <div>
            <InputNumber onChange={onChange} min={0} max={100} defaultValue={60} style={{borderTopRightRadius:'0',borderBottomRightRadius:'0'}}  />
            <Button type='primary' style={{borderTopLeftRadius:'0',borderBottomLeftRadius:'0'}} 
              onClick={async()=> {
                try {
                  const ret2 = await http.post('/homework/setScore', {
                    homeworkID:currentHomeworkID,
                    grade:newGrade
                  })
                  const ret = await http.post('/homework/cancelHomeworkArgument', {
                    homeworkID:currentHomeworkID,
                  })
                  
                  if(ret2.data&&ret.data) {
                    message.success('处理完成')
                  }
                  // window.location.reload()
                  setVisible(false)
                  
                } catch(e) {
                  console.log('catch ')
                  console.log(e)
                }
                TeacherStore.updateArgumentData()
              }}>修改分数</Button>
          </div>
          <Divider type='vertical'></Divider>
          <Button type='primary' onClick={async()=> {
            try {
              const ret = await http.post('/homework/cancelHomeworkArgument', {
                homeworkID:currentHomeworkID,
              })
              if(ret.data) {
                message.success('处理完成')
              }
              // window.location.reload()
              setVisible(false)
              
            } catch(e) {
              console.log('catch ')
              console.log(e)
            }
            TeacherStore.updateArgumentData()
          }}>不更改分数</Button>
        </Space>
      </Modal>
    </div>
  )
}
export default observer(ArgumentManage);