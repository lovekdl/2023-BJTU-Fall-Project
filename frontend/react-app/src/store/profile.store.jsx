import {makeAutoObservable} from 'mobx'
import {http, setTokenFromLocalStorage, getTokenFromLocalStorage} from '../utils'
class ProfileStore {
	token = getTokenFromLocalStorage()||''

	username = '';
	email  = '';
	usertype = '';
	uid = '';
	//manager teacher student
	

	constructor() {
		//mobx 设置响应式
		makeAutoObservable(this)
		this.getProfile();
	}
	
	
	setToken = (token)=> {
		this.token = token
		setTokenFromLocalStorage(this.token)
	}
	setUsertype = (usertype) => {
		this.usertype = usertype;
	}
	setUsername = (username) => {
		this.username=username
	}
	setEmail = (email) => {
		this.email=email
	}

	setUid = (uid) => {
		this.uid = uid;
	}
	
	getProfile = async() => {
		try{
			const ret = await http.post('/usr/getInfoByToken', {
				
			})
			this.setEmail(ret.data.data.email)
			this.setUsername(ret.data.data.username)
			this.setUid(ret.data.data.uid)
			console.log(ret.data)
			if(ret.data.data.authority == 1)this.setUsertype('manager');
			else if(ret.data.data.authority == 2)this.setUsertype('teacher');
			else if(ret.data.data.authority == 3)this.setUsertype('student');
			console.log('get authorit is ' + ret.data.data.authority + this.usertype)
			
		}
		catch (e) {
			console.log(e);
		}
	}

}

export default ProfileStore