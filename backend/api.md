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

- **请求方式**: POST

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

- **请求 URL**: `/admin/getUsers`

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
            "users": [
                  {
                      "uid": "uid",
                      "authority": "authority",
                      "key": "key",
                      "email": "email",
                      "username": "username"
                  },
            ]
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
      "email": "email"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 5. 管理员查看所有用户

- **请求 URL**: `/admin/getAllUsers`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
      "data": {
            "users": [
                  {
                      "uid": "uid",
                      "authority": "authority",
                      "key": "key",
                      "email": "email",
                      "username": "username"
                  },
            ]
      }
  }
  ```

## 教师模块

### 1. 教师添加课程

- **请求 URL**: `/teacher/addCourse`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "courseName": "cname",
      "courseDescribe": "courseDescribe"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 2. 教师删除课程

- **请求 URL**: `/teacher/deleteCourseByCourseID`

- **请求方式**: DELETE

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "courseID": "courseID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 3. 教师设置课程名称

- **请求 URL**: `/teacher/setCourseName`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "courseID": "courseID",
      "courseName": "courseName"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 4. 教师设置课程描述

- **请求 URL**: `/teacher/setCourseDescribe`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "courseID": "courseID",
      "courseDescribe": "courseDescribe"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 5. 教师获取课程列表

- **请求 URL**: `/teacher/getCourseList`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
      "data": {
          "courses": [
              {
                  "courseName": "courseName",
                  "courseDescribe": "courseDescribe",
                  "courseNumber": "courseNumber",
                  "courseID": "courseID",
                  "key": "key"
              }
          ]
      },
  }
  ```

### 6. 教师添加学生到课程

- **请求 URL**: `/teacher/addStudentToCourse`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "courseID": "courseID",
      "uid": "uid"
      , or ,
      "username": "username"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 7. 教师从课程删除学生

- **请求 URL**: `/teacher/deleteStudentFromCourse`

- **请求方式**: DELETE

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "courseID": "courseID",
      "uid": "uid"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 8. 教师获取课程学生列表

- **请求 URL**: `/teacher/getStudentListByCourseID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",  				// token
      "courseID": "courseID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "students": [
              {
                  "uid": "uid",
                  "key": "key",
                  "username": "username"
              }
          ]
      },
      "message": "success"
  }
  ```

### 9. 教师添加无附件作业

- **请求 URL**: `/teacher/addAssignmentWithoutFile`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "courseID": "cid",
      "assignmentName": "assignmentName",
      "assignmentDescribe": "assignmentDescribe",
      "date": "date",
      "time": "time"
  }
  ```
  
- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 10. 教师删除作业

- **请求 URL**: `/teacher/deleteAssignmentByAssignmentID`

- **请求方式**: DELETE

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 11. 教师获取课程任务列表

- **请求 URL**: `/teacher/getAssignmentListByCourseID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "courseID": "courseID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "assignments": [
              {
                  "assignmentName": "assignmentName",
                  "assignmentStatus":"assignmentStatus",
                  "date": "date",
                  "time": "time",
                  "assignmentID": "assignmentID",
                  "key": "key",
                  "assignmentDescribe": "assignmentDescribe"
              },
          ]
      },
      "message": "success"
  }
  ```

### 12. 教师设置作业标题

- **请求 URL**: `/teacher/setAssignmentTitle`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID",
      "assignmentName": "assignmentName"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 13. 教师设置作业内容

- **请求 URL**: `/teacher/setAssignmentContent`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID",
      "assignmentDescribe": "assignmentDescribe"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 14. 教师设置作业截止时间

- **请求 URL**: `/teacher/setAssignmentDeadline`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID",
      "date": "date",
      "time": "time"
  }
  ```
  
- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 15. 教师获取开始某任务互评

- **请求 URL**: `/teacher/startPeer`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID",
      "courseID": "courseID",
      "peerNumber": "peerNumber"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 16. 教师结束某任务互评

- **请求 URL**: `/teacher/endPeer`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 

## 作业模块

### 1. 学生添加无附件作业

- **请求 URL**: `/homework/addHomeworkWithoutFile`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID",
      "content": "content",
      "date": "2023-11-18",
      "time": "00:00:00"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 2. 设置作业分数

- **请求 URL**: `/homework/setScore`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "homeworkID": "homeworkID",
      "grade": "grade"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 3. 学生投诉作业

- **请求 URL**: `/homework/setHomeworkArgument`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "homeworkID": "homeworkID",
      "argument": "argument"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 4. 取消作业投诉

- **请求 URL**: `/homework/cancelHomeworkArgument`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "homeworkID": "homeworkID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 5. 获取有投诉的作业列表

- **请求 URL**: `/homework/getArgumentHomeworkList`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "homeworks": [
              {
                  "homeworkID": "homeworkID",
                  "argument": "argument",
                  "courseName": "courseName",
                  "assignmentID": "assignmentID",
                  "courseID": "courseID",
                  "key": "key",
                  "assignmentName": "assignmentName",
                  "username": "username"
              }
          ]
      },
      "message": "success"
  }
  ```

### 6. 根据任务ID获取作业

- **请求 URL**: `/homework/getHomeworkByAssignmentID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "courseID": "courseID",
      "assignmentID": "assignmentID"
      
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "homeworks": [
              {
                  "date": "/",
                  "uid": "300001",
                  "homeworkID": "/",
                  "submit": "未提交",
                  "grade": "/",
                  "time": "/",
                  "key": "300001",
                  "username": "liqi"
              },
              {
                  "date": "2023-11-18",
                  "uid": "300025",
                  "homeworkID": "600001",
                  "submit": "已提交",
                  "grade": "未评分",
                  "time": "00:00:00",
                  "key": "300025",
                  "username": "student"
              }
          ]
      },
      "message": "success"
  }
  ```

### 7. 根据作业ID获取作业内容

- **请求 URL**: `/homework/getContentByHomeworkID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "homeworkID": "homeworkID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "homework": {
              "homeworkContent": "homeworkContent"
          }
      },
      "message": "success"
  }
  ```

### 8. 设置作业优秀

- **请求 URL**: `/homework/setHomeworkExcellent`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "homeworkID": "homeworkID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 9. 根据任务ID获取优秀作业

- **请求 URL**: `/homework/getExcellentHomeworkByAssignmentID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "homework": {
              "homeworkID": "homeworkID",
              "key": "key"
          }
      },
      "message": "success"
  }
  ```

### 10. 取消作业优秀

- **请求 URL**: `/homework/cancelHomeworkExcellent`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "homeworkID": "homeworkID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "message": "success"
  }
  ```

### 11. 处理作业投诉

- **请求 URL**: `/homework/handleHomeworkArgument`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "homeworkID": "homeworkID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 

## 学生模块

 ### 1. 学生获取所有课程列表

- **请求 URL**: `/student/getAllCourseList`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
      "data": {
          "courses": [
              {
                  "courseName": "courseName",
                  "courseDescribe": "courseDescribe",
                  "courseNumber": "courseNumber",
                  "courseID": "courseID",
                  "key": "key"
              }
          ]
      },
  }
  ```

### 2. 学生获取自己的课程列表

- **请求 URL**: `/student/getMyCourseList`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result",
      "data": {
          "courses": [
              {
                  "courseName": "courseName",
                  "courseDescribe": "courseDescribe",
                  "courseNumber": "courseNumber",
                  "teacherName": "teacherName",
                  "courseID": "courseID",
                  "key": "key"
              }
          ]
      },
  }
  ```

### 3. 学生根据课程号获取课程作业列表

- **请求 URL**: `/student/getAssignmentListByCourseID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "courseID": "courseID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "assignments": [
              {
                  "date": "2023-11-18",
                  "assignmentDescribe": "3333333",
                  "time": "00:00:00",
                  "assignmentID": "500004",
                  "key": "500004",
                  "assignmentName": "assignment4",
                  "submit": "未提交"
              }
          ]
      },
      "message": "success"
  }
  ```

### 4. 学生添加无附件作业

- **请求 URL**: `/student/addHomeworkWithoutFile`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID",
      "content": "content",
      "date": "2023-11-18",
      "time": "00:00:00"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

### 5. 学生根据任务ID获取作业信息

- **请求 URL**: `/student/getHomeworkByAssignmentID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "homework": {
              "date": "2023-11-17",
              "uid": "300002",
              "homeworkID": "600002",
              "submit": "已提交",
              "grade": "未评分",
              "time": "13:10:00",
              "key": "300002",
              "username": "yxh"
          }
      },
      "message": "success"
  }
  ```
  
###  6. 学生根据任务ID获取互评作业信息

- **请求 URL**: `/student/getPeerHomeworkByAssignmentID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "peerHomework": [
              {
                  "peerID": "700011",
                  "homeworkID": "600009",
                  "courseName": "testCourse1",
                  "teacherName": "fjq",
                  "assignmentDescribe": "test assignment for allocation",
                  "grade": "未评分",
                  "comment": "未评价",
                  "assignmentName": "test assignment"
              },
          ]
      },
      "message": "success"
  }
  ```

###  7. 学生根据作业ID获取互评作业内容

- **请求 URL**: `/student/getPeerHomeworkContentByHomeworkID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "homeworkID": "homeworkID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "homework": {
              "homeworkContent": "yxh的作业"
          }
      },
      "message": "success"
  }
  ```

###  8. 学生给互评作业评分

- **请求 URL**: `/student/gradePeerHomework`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "peerID": "peerID",
      "grade": "grade",
      "comment": "comment"
  }
  ```
  
- **返回示例**:

  ```json
  {
      "code": "status code",
      "message": "description about the result"
  }
  ```

###  9. 学生根据uid获取互拼作业信息

- **请求 URL**: `/student/getPeerHomeworkByUID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "peerHomework": [
              {
                  "peerID": "700011",
                  "homeworkID": "600009",
                  "courseName": "testCourse1",
                  "teacherName": "fjq",
                  "assignmentDescribe": "test assignment for allocation",
                  "grade": "未评分",
                  "comment": "未评价",
                  "assignmentName": "test assignment"
              },
          ]
      },
      "message": "success"
  }
  ```

###  9. 学生根据任务ID获取作业内容

- **请求 URL**: `/student/getHomeworkContentByAssignmentID`

- **请求方式**: POST

- **请求参数**:

- ```json
  {
      "token": "token",
      "assignmentID": "assignmentID"
  }
  ```

- **返回示例**:

  ```json
  {
      "code": 200,
      "data": {
          "homework": {
              "homeworkContent": "yxh的作业"
          }
      },
      "message": "success"
  }
  ```

###  
