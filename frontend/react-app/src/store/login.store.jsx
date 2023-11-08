import {makeAutoObservable} from 'mobx'
import {http, setTokenFromLocalStorage, getTokenFromLocalStorage} from '../utils'
class LoginStore {
	token = getTokenFromLocalStorage()||''
	waiting = -1;
	interval = 0;
	waiting2 = -1;
	constructor() {
		
		//mobx 设置响应式
		makeAutoObservable(this)
		this.interval = setInterval(() => {
			this.waiting -= 1;
		},1000)
		this.interval2 = setInterval(() => {
			this.waiting2 -= 1;
		},1000)
	}
	getTokenByLogin = async ({ username, password }) => {
		//调用登录接口
		console.log('password is ' + password)
		const ret = await http.post('/usr/login',{
			username : username,
			password : password,
		})
		console.log('hahahaha')
		console.log(ret)
		this.setToken(ret.data.data.token)
		// if(ret.data.code > 300) {
		// 	this
		// } 
	}

	// getTokenByLogin = async ({ username, password }) => {
  //   //调用登录接口
  //   console.log('password is ' + password)
	// 	// this.setToken("");
  //   // 将数据转换为x-www-form-urlencoded格式
  //   const params = new URLSearchParams();
  //   params.append('username', username);
  //   params.append('password', password);

  //   // 发送请求
  //   const ret = await http.post('/api/login', params.toString(), {
  //       headers: {
  //           'Content-Type': 'application/x-www-form-urlencoded'
  //       }
  //   })
	// }

	
	getTokenByRegister = async ({username, password}) => {
		//调用注册接口
		const ret = await http.post('/register', {
			username,
			password
		})
		this.setToken(ret.data.data.token)
	}
	setToken = (token)=> {
		this.token = token
		setTokenFromLocalStorage(this.token)
	}

	setWaiting = (x) => {
		this.waiting = x
	}
	setWaiting2 = (x) => {
		this.waiting2 = x
	}
	resetWaiting = () => {
		clearInterval(this.interval);
		this.setWaiting(60);
		this.interval = setInterval(() => {
			this.waiting -= 1;
		},1000)
	}
	resetWaiting2 = () => {
		clearInterval(this.interval2);
		this.setWaiting2(60);
		console.log('waiting2 is ' + this.waiting2)
		this.interval2 = setInterval(() => {
			this.waiting2 -= 1;
		},1000)
	}
}

export default LoginStore