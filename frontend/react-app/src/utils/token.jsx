//封装，localstorage存储token

const key = 'BlueSpace-cookie-token'
const langKey = 'BlueSpace-language-key'
const firtMessageKey = 'BlueSpace-First-Message'
const setTokenFromLocalStorage = (token) => {
  return window.localStorage.setItem(key, token)
}

const getTokenFromLocalStorage = () => {
  return window.localStorage.getItem(key);
}

const removeTokenFromLocalStorage = () => {
  return window.localStorage.removeItem(key);
}

export {
  setTokenFromLocalStorage,
  getTokenFromLocalStorage,
  removeTokenFromLocalStorage,
  
}