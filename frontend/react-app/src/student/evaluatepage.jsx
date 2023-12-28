import { useState, useEffect} from 'react';
import {
  HomeOutlined,SearchOutlined
} from '@ant-design/icons';
import { http } from '../utils/http.jsx';
import { Breadcrumb, Card, Button, Divider,Input, InputNumber,message} from 'antd';
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
const { TextArea } = Input;
function EvaluatePage(prop) {
  const {StudentStore} = useStore();
  const [gradeValue, setGradeValue] = useState(60)
  const [comment, setComment] = useState('')
  const onChange = (value) => {
    console.log('changed', value);
    setGradeValue(value)
    StudentStore.updateEvaluateCourseData()
  };
  useEffect(()=>{
    //ManagerStore.updateData()
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
            title: '互评任务',
          },
          {
            title: '作业互评',
          },
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title='互评'  style={{height:'80vh'}}
        >
          <h2> 作业内容</h2>
          <Card style={{height:'18vh', overflowY: 'auto'}}>
            <div dangerouslySetInnerHTML={{ __html: StudentStore.assignmentContent }} />
          </Card>
          
          <div style={{display:'flex'}}>
            <div style={{flexGrow:'1',flex:"1"}}>
              <h2> 对方答案</h2>
              <Card style={{height:'18vh', overflowY: 'auto'}}>
                <div dangerouslySetInnerHTML={{ __html: StudentStore.evaluateHomework }} />
              </Card>
            </div>
            <Divider type='vertical'></Divider>
            <div style={{flexGrow:'1',flex:"1"}}>
              <h2> 标准答案</h2>
              <Card style={{height:'18vh', overflowY: 'auto'}}>
                <div dangerouslySetInnerHTML={{ __html: StudentStore.answer }} />
              </Card>
            </div>
          </div>
          评分：<InputNumber style={{width:'3vw'}} onChange={onChange} min={0} max={100} defaultValue={60}></InputNumber>
          <br></br>
          留言：<TextArea value={comment}
            onChange={(e) => setComment(e.target.value)}
            placeholder="Controlled autosize"
            autoSize={{ minRows: 3, maxRows: 5 }}></TextArea>
          <div>
          <Button type='primary' onClick = {() => {
          prop.changeContent()
        }}
          style={{margin:'1vh'}}
        >返回</Button>
            <Button type='primary' style={{marginTop:'0.5vh'}} onClick={async()=>{
              try {
                const ret = await http.post('/student/gradePeerHomework',{
                  peerID:prop.peerID,
                  grade:gradeValue,
                  comment:comment
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
              setTimeout(()=>{prop.changeContent()},500)
            }}> 完成互评</Button>
          </div>
      </Card>

      <div>
        
      </div>

      
    </div>
  )
}

export default observer(EvaluatePage);