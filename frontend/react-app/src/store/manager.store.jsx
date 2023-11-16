import {makeAutoObservable} from 'mobx'
import {http} from '../utils'
import { toJS } from 'mobx';
// import {http} from '../utils'
class ManagerStore {
  data= [
    {
      key:'1',
      uid:'1',
      username:'test',
      email:'12345@bjtu.edu.cn',
      authority:'教师'
    },
    {
      key:'2',
      uid:'2',
      username:'drj',
      email:'21301032@bjtu.edu.cn',
      authority:'学生'
    },
    {
      key:'3',
      uid:'2',
      username:'drj',
      email:'21301032@bjtu.edu.cn',
      authority:'学生'
    },
    {
      key:'4',
      uid:'2',
      username:'drj',
      email:'21301032@bjtu.edu.cn',
      authority:'学生'
    },
    {
      key:'5',
      uid:'2',
      username:'drj',
      email:'21301032@bjtu.edu.cn',
      authority:'学生'
    },
    {
      key:'6',
      uid:'2',
      username:'drj',
      email:'21301032@bjtu.edu.cn',
      authority:'学生'
    },
    {
      key:'7',
      uid:'2',
      username:'drj',
      email:'21301032@bjtu.edu.cn',
      authority:'学生'
    },{
      key:'8',
      uid:'2',
      username:'drj',
      email:'21301032@bjtu.edu.cn',
      authority:'学生'
    },
    
  ]
	constructor() {
		
		//mobx 设置响应式
		makeAutoObservable(this)
    this.updateData()
	}
	setData = (newData)=> {
    var jsonString = JSON.stringify(toJS(newData))

    this.data = JSON.parse(jsonString)

    
  }
  updateData = async () => {
    try {
      const ret = await http.post('/admin/getAllUsers', {
        
      })
      // console.log(ret.data.data.users);
      if(ret.data.message=='success' && ret.data.data.users)this.setData(ret.data.data.users)
      

      // window.location.reload()
    } catch(e) {
      console.log(e)
    }
    this.setData()
  }
}

export default ManagerStore