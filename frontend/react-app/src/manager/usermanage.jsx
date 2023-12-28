import { useState, useEffect} from 'react';
import {
  HomeOutlined,
  PlusOutlined, EditOutlined,DeleteOutlined,ExclamationCircleOutlined,DownOutlined,SearchOutlined
} from '@ant-design/icons';

import { Breadcrumb, Card, Button, Form, Input,Table,Modal, message, Space, Popconfirm, Dropdown} from 'antd';
import { observer } from 'mobx-react-lite'
import { useStore } from '../store';
import { http } from '../utils/http.jsx';
function UserManage() {
  const [visible,setVisible]=useState(false);
  const [editing, setEditing] = useState({})
  const [editVisible, setEditVisible] = useState(false);
  const [accountType, setAccountType] = useState('教师');
  const items = [
    {
      label: '教师',
      key: '1',
    },
    {
      label: '学生',
      key: '2',
    },
  ];
  const onClick = ({ key }) => {
    
    if(key === '1') {
      setAccountType('教师')
    }
    if(key === '2') {
      setAccountType('学生')
    }
  };
  //添加用户
  const handleAdd= async(v) =>{
    console.log(v.username)
    try {
      const ret = await http.post('/admin/addUser',{
        username:v.username,
        email:v.email,
        password:v.password,
        authority:accountType==='教师'?2:3,
      })
      if(ret.data?.message == 'success') {
        message.success('添加用户成功')
        setVisible(false)
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    ManagerStore.updateData();
  }
  const handleEdit = async(v) => {
    // console.log(v.username, v.email)
    try {
      const ret = await http.post('/admin/updateUser',{
        username:v.username?v.username:'',
        email:v.email?v.email:'',
        uid:editing.uid,
      })
      if(ret.data?.message == 'success') {
        message.success('修改用户信息成功')
        setEditVisible(false)
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    ManagerStore.updateData();
  }
  const {ManagerStore} = useStore();
  const [myForm] = Form.useForm()
  const [editForm] = Form.useForm()
  useEffect(()=>{
    ManagerStore.updateData()
    // console.log('haha')
    // console.log(ManagerStore.data)
  },[])
  const handeUserDelete = async(v)=> {
    try {
      const ret = await http.post('/admin/deleteUserByUid',{
        username:v.username,
        email:v.email,
        password:v.password,
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
    ManagerStore.updateData();
  }
  const [searchString, setSearchString] = useState('');
  const handleOnChange=(e)=>{
    setSearchString(e.target.value)
  }
   const handleSearch = async()=> {
    // console.log(searchString)
    if(searchString == '') {
      ManagerStore.updateData();
      return
    }
    try {
      const ret = await http.post('/admin/getUsers',{
        value:searchString
      })
      if(ret.data?.message == 'success') {
        ManagerStore.setData(ret.data.data.users)
      }
      else message.error(ret.data.message)
    }
    catch(e) {
      console.log('catch : ',e)
      if(e.response) message.error(e.response.data.message)
      else message.error(e.message)
    }
    // ManagerStore.updateData();
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
            title: '用户管理',
          },
        ]}
        style={{marginBottom:'7px'}}
      />
      <Card title='用户列表' extra={
        <Button type='primary' icon={<PlusOutlined></PlusOutlined>} onClick={()=>setVisible(true)}></Button>
      }>
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
        <Table pagination={{ pageSize:  6}}
        columns={[{
          title:'序号',
          width:160,
          align:'center',
          dataIndex:'uid',
          key:'uid',
        },
        {
          title:'用户名',
          dataIndex:'username',
          key:'username',
        },
        {
          title:'用户类型',
          dataIndex:'authority',
          key:'authority',
        },
        {
          title:'邮箱',
          dataIndex:'email',
          key:'email',
        },
        {
          title:'操作',
          align:'center',
          width:300,
          render(_r, c) {
            return (<Space style={{height:'50px'}}>
              <Button type='primary' icon={<EditOutlined></EditOutlined>} onClick={() => {
                setEditing(c)
                setEditVisible(true)
              }}></Button>
              <Popconfirm title='删除用户' description='确定要删除此用户？' icon={<ExclamationCircleOutlined/>} onConfirm={()=>handeUserDelete(c)} okText="确定" cancelText="取消">
                <Button type="primary" icon={<DeleteOutlined/>} danger></Button>
              </Popconfirm>
            </Space>)
          }
        }
        ]}
          dataSource={ManagerStore.data}
        ></Table>
      </Card>
      

      <Modal title='新增' open={visible} maskClosable={false} onCancel={() => setVisible(false)} onOk={()=>myForm.submit()} destroyOnClose>
        <Form style={{marginTop:'2vh'}} onFinish={(v)=>{
            // console.log(v.username)
            handleAdd(v)  
          }}
          form={myForm}
        >
          <Form.Item label='用户类型' style={{width:'10vw'}}>
            <Dropdown.Button
              icon={<DownOutlined />}
                
                menu={{
                  items,
                  selectable: true,
                  defaultSelectedKeys: ['1'],
                  onClick,
                }}
                
              >
                {accountType}
             </Dropdown.Button>
         </Form.Item>
          <Form.Item label="用户名" name="username" rules={[
            {
              required: true,
              message: '请输入用户名'
            }
          ]}>
            <Input placeholder='请输入用户名'></Input>
          </Form.Item>
          <Form.Item label="邮箱" name="email" rules={[
            {
              required: true,
              message: '请输入邮箱'
            }
          ]}>
            <Input placeholder='请输入邮箱' ></Input>
          </Form.Item>
          <Form.Item label="密码" name="password" rules={[
            {
              required: true,
              message: '请输入密码'
            }
          ]}>
            <Input  placeholder='请输入密码' ></Input>
          </Form.Item> 
        </Form>
      </Modal>

      <Modal title='修改' open={editVisible} maskClosable={false} onCancel={() => setEditVisible(false)} onOk={()=>editForm.submit()} destroyOnClose>
        <Form style={{marginTop:'2vh'}} onFinish={(v)=>{
            // console.log(v.username)
            // handleAdd(v)  
            handleEdit(v)
          }}
          form={editForm}
        >
           <Form.Item label="用户名" name="username" >
            <Input placeholder='请输入用户名' defaultValue={editing.username}></Input>
          </Form.Item>
          <Form.Item label="邮箱" name="email">
            <Input placeholder='请输入密码' defaultValue={editing.email} ></Input>
          </Form.Item>
          {/* <Form.Item label="密码" name="password" rules={[
            {
              required: true,
              message: '请输入密码'
            }
          ]}>
            <Input  placeholder='请输入密码' defaultValue={editing.password} ></Input>
          </Form.Item>  */}
        </Form>
      </Modal>
    </div>
  )
}

export default observer(UserManage);