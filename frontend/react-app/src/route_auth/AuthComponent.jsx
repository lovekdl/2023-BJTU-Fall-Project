import { getTokenFromLocalStorage } from "../utils";
import { Navigate } from "react-router";
import { useStore } from '../store';
import { useEffect } from "react";
function AuthComponent() {
  const {ProfileStore} = useStore()
	const isToken = getTokenFromLocalStorage()
	useEffect(()=>{
    console.log('usertype is ' + ProfileStore.usertype)
    
  },[]);
  if(isToken) {
		// console.log('cookie is ' + getTokenFromLocalStorage())
    if(ProfileStore.usertype==='manager') return <Navigate to='/manager' replace></Navigate>
    else if(ProfileStore.usertype==='teacher') return <Navigate to='/teacher' replace></Navigate>
    else if(ProfileStore.usertype==='student') return <Navigate to='/student' replace></Navigate>
	}
	return <Navigate to='/login' replace></Navigate>
}

export {AuthComponent}