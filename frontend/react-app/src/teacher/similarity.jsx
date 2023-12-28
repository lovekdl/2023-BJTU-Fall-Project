import {
  HomeOutlined,EditOutlined,SearchOutlined
} from '@ant-design/icons';
import { Breadcrumb, Card, Button, Form, Input,Table, message, Space,Popconfirm,Modal,Divider,InputNumber } from 'antd';
import {useEffect, useState} from 'react'
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import { http } from '../utils/http.jsx';
function Similarity(prop) {

  const {TeacherStore} = useStore()
  const [visible, setVisible] = useState(false)
  useEffect(()=>{
    TeacherStore.updateSimilarityData(prop.assignmentID)
    
    
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
      <Card title={'相似度检测-'+prop.assignmentName} >
      <Table pagination={{ pageSize:  6}}
        columns={[
        {
          title:'学生名1',
          dataIndex:'username1',
          key:'username1',
        },
        {
          title:'学生名2',
          dataIndex:'username2',
          key:'username2',
        },
        {
          title:'作业相似度',
          dataIndex:'similarity',
          key:'similarity',
        },
        
        {
          title:'详情',
          align:'center',
          dataIndex:'situation',
          width:300,
          render(_r, c) {
            return (<Space style={{height:'50px'}}>
              <Button type='primary'  onClick={async()=>{
                TeacherStore.setHomework1(c.content1)
                TeacherStore.setHomework2(c.content2)
                TeacherStore.setCurrentSim(c)
               
                setVisible(1)
              }} >对比查看</Button>
               
            </Space>)
          }
        },
        
        ]}
          dataSource={TeacherStore.similarityData}
        ></Table>
        
      </Card>
      <div>
        <Button type='primary' onClick = {() => {
          prop.changeContent('HomeworkManage')
        }}
          style={{marginTop:'0.5vh'}}
        >返回</Button>
      </div>
      <Modal  width="80vw" title={'相似度对比'} open={visible} maskClosable={false} onCancel={() => setVisible(false)}  destroyOnClose>
          <h3>相似度:{TeacherStore.currentSim.similarity}</h3>
          <div style={{display:'flex'}}>
            <div style={{flexGrow:'1'}}>
              <h2> 同学一的答案</h2>
              <Card style={{height:'18vh', overflowY: 'auto'}}>
                <div dangerouslySetInnerHTML={{ __html: TeacherStore.homework1 }} />
              </Card>
            </div>
            <Divider type='vertical'></Divider>
            <div style={{flexGrow:'1'}}>
              <h2> 同学二的答案</h2>
              <Card style={{height:'18vh', overflowY: 'auto'}}>
                <div dangerouslySetInnerHTML={{ __html: TeacherStore.homework2 }} />
              </Card>
            </div>
          </div>
      </Modal>
    </div>
    
  )
}

export default observer(Similarity);