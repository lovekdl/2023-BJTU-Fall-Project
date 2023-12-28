/*
	存储模块
	用于状态管理和响应式更新
*/
import LoginStore from "./login.store.jsx"
import ProfileStore from "./profile.store.jsx"
import ManagerStore from "./manager.store.jsx"
import TeacherStore from "./teacher.store.jsx"
import StudentStore from "./student.store.jsx"
import React from "react"
class RootStore {
	constructor() {
		this.loginStore = new LoginStore()
		this.ProfileStore = new ProfileStore();
		this.ManagerStore = new ManagerStore();
		this.TeacherStore = new TeacherStore()
		this.StudentStore = new StudentStore();
	}
}

const rootStore = new RootStore()
const context = React.createContext(rootStore)
const useStore = () => React.useContext(context)

export {useStore, rootStore}