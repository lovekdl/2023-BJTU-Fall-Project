/*
	存储模块
	用于状态管理和响应式更新
*/
import LoginStore from "./login.store"
import ProfileStore from "./profile.store"
import ManagerStore from "./manager.store"
import TeacherStore from "./teacher.store"
import React from "react"
class RootStore {
	constructor() {
		this.loginStore = new LoginStore()
		this.ProfileStore = new ProfileStore();
		this.ManagerStore = new ManagerStore();
		this.TeacherStore = new TeacherStore()
	}
}

const rootStore = new RootStore()
const context = React.createContext(rootStore)
const useStore = () => React.useContext(context)

export {useStore, rootStore}