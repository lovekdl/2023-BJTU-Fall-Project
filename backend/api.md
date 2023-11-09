# 互评平台 API 文档

## 用户模块

### 1. 注册学生用户

- **请求 URL**: `/usr/register`

- **请求方式**: POST

- **请求参数**:

  ```json
  {
      "username": "string",    // 用户名
      "password": "string",    // 密码
      "email": "string",       // 邮箱
      "code": "string",        // 验证码
      "authority": "authority" // 账户权限
  }
  ```

- **返回示例**:

  ```
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 2. 注册发送验证码

- **请求 URL**: `/usr/register/sendCode`

- **请求方式**: POST

- **请求参数**:

  ```json
  {
      "email": "string"  // 邮箱
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 3. 用户登录

- **请求 URL**: `/usr/login`

- **请求方式**: POST

- **请求参数**:

  ```json
  {
      "username": "string",  // 用户名
      "password": "string"   // 密码
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message" "success",
      "data": {
          "token": "token_string"
      }
  }
  ```

### 4. 根据token获取用户信息

- **请求 URL**: `/usr/getInfoByToken`
- **请求方式**: POST
- **请求参数**:
- 
  ```json
  {
      "token": "token"  // token
  }
  ```
- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
      "data": {
          "uid": "uid",
          "username": "username",
          "email": "email",
          "authority": "authority"
      }
  }
  ```
  
### 5. 修改账户密码

- **请求 URL**: `/usr/changePassword`
- **请求方式**: POST
- **请求参数**:
- 
  ```json
  {
      "token": "token",  				// token
      "oldPassword": "oldpassword",   // 新密码
      "newPassword": "newpassword"    // 旧密码
  }
  ```
- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
      "data": {
          "token": "token"
      }
  }
  ```
### 6. 找回账户密码

- **请求 URL**: `/usr/findPassword`
- **请求方式**: POST
- **请求参数**:
- 
  ```json
  {
      "email": "email",             // 邮箱
      "code": "code",               // 验证码
      "newPassword": "newpassword"  // 新密码
  }
  ```
- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
  }
  ```
### 7. 找回密码发送验证码

- **请求 URL**: `/usr/findPassword/sendCode`

- **请求方式**: POST

- **请求参数**:

  ```json
  {
      "email": "string"  // 邮箱
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 8. 根据用户名删除用户

- **请求 URL**: `/usr/deleteByName`

- **请求方式**: DELETE

- **请求参数**:

  ```json
  {
      "username": "string"  // 用户名
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```
### 9. 修改用户名

- **请求 URL**: `/usr/changeUsername`
- **请求方式**: POST
- **请求参数**:
- 
  ```json
  {
      "token": "token",  				// token
      "newUsername": "newusername"    // 新用户名
  }
  ```
- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
      "data": {
          "token": "token"
      }
  }
  ```

## 管理员模块

### 1. 管理员根据uid删除用户

- **请求 URL**: `/admin/deleteUserByUid`

- **请求方式**: DELETE

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "uid": "uid"    				// uid
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 2. 管理员添加用户

- **请求 URL**: `/admin/addUser`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "username": "username",
      "email": "email",
      "password": "password",
      "authority": "authority"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 3. 管理员查询某些用户

- **请求 URL**: `/admin/deleteUserByUid`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "value": "value"    			// value
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
      "data": {
          "users": [{"uid":"300001","authority":"学生","key":"300001","email":"21301034@bjtu.edu.cn","username":"liqi"},{"uid":"300002","authority":"学生","key":"300002","email":"21301114@bjtu.edu.cn","username":"yxh"},]
      }
  }
  ```

### 4. 管理员修改用户信息

- **请求 URL**: `/admin/updateUser`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "uid": "uid",    				// uid
      "username": "username", 		// username
      "email": "email",
      "authority": "authority"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

