# 互评平台 API 文档

## 用户模块

### 1. 注册学生用户

- **请求 URL**: `/usr/register`

- **请求方式**: POST

- **请求参数**:

  ```json
  {
      "username": "string",  // 用户名
      "password": "string",  // 密码
      "email": "string",     // 邮箱
      "code": "string"       // 验证码
  }
  ```

- **返回示例**:

  ```
  {
      "status": "success or error",
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
      "status": "success or error",
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
      "status": "success",
      "message" "success",
      "data": {
          "token": "token_string"
      }
  }
  ```

### 4. 根据token获取用户信息

- **请求 URL**: `/usr/getInfo`
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
      "status": "success or error",
      "message": "description about the result",
      "data": {
          "uuid": "uuid",
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
      "token": "token"  // token
      "oldPassword": "oldpassword",
      "newPassword": "newpassword"
  }
  ```
- **返回示例**:

  ```json
  {
      "status": "success or error",
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
      "email": "email",
      "code": "code",
      "newPassword": "newpassword"
  }
  ```
- **返回示例**:

  ```json
  {
      "status": "success or error",
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
      "status": "success or error",
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
      "status": "success or error",
      "message": "description about the result"
  }
  ```