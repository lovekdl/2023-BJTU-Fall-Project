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

- **请求 URL**: `/usr/register/send_code`

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
      "data": {
          "token": "token_string"
      }
  }
  ```

### 4. 根据用户名删除用户

- **请求 URL**: `/usr/delete_by_name`

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