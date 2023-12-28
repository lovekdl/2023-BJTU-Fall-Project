import {makeAutoObservable} from 'mobx'
import {http} from '../utils'
import { toJS } from 'mobx';
// import {http} from '../utils'
class TeacherStore {
  courseData=[
    {
      key:'1',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'1',
    },
    {
      key:'2',
      courseName:'sleep',
      courseDescribe:'sleep in the bed',
      courseNumber :'55',
      courseID:'2',
    },
    {
      key:'3',
      courseName:'eat',
      courseDescribe:'I love eat',
      courseNumber :'66',
      courseID:'3',
    },
    {
      key:'4',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'4',
    },
    {
      key:'5',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'5',
    },
    {
      key:'6',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'6',
    },
    {
      key:'7',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'7',
    },{
      key:'8',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'8',
    },
    
  ]
  
  assignmentData=[
    {
      key:'1',
      assignmentName:'作业1',
      assignmentDescribe:'你好',
      date:'2023-11-11',
      time:'10:10:10',
      assignmentID:'1',
      assignmentStatus:'未开始互评',
    },
    {
      key:'2',
      assignmentName:'作业2',
      assignmentDescribe:'我不好',
      time:'10:10:10',
      date:'2023-11-11',
      assignmentID:'2',
      assignmentStatus:'未开始互评',
    },
    {
      key:'3',
      assignmentName:'作业3',
      assignmentDescribe:'嗯',
      date:'2023-11-11',
      time:'10:10:10',
      assignmentID:'3',
      assignmentStatus:'未开始互评',
    },
    
    
  ]
  studentData=[
    {
      key:'1',
      username:'sss',
      uid:'123',
      submit:'已提交',
      date:'2023-11-17',
      time:'12:11:10',
      grade:'100',
    },
    {
      key:'2',
      username:'test',
      uid:'123',
      submit:'未提交',
      date:'/',
      time:'/',
      grade:'/',
    },
    {
      key:'3',
      username:'ttt',
      uid:'123',
      submit:'已提交',
      date:'2023-11-17',
      time:'12:11:10',
      grade:'100',
    },
    
  ]
  argumentData = [
    { courseName:'math',assignmentName:'homework1',username:'liming',argument:"1"},
    { courseName:'math',assignmentName:'homework2',username:'liming',argument:"2" },
    { courseName:'french',assignmentName:'homework3',username:'liming',argument:"3" },
    { courseName:'math',assignmentName:'homework4',username:'liming',argument:"4" },
    { courseName:'math',assignmentName:'homework5',username:'liming',argument:"5" },
    { courseName:'english',assignmentName:'homework7',username:'liming',argument:"6" },
    { courseName:'computer',assignmentName:'homework1',username:'liming',argument:"7" },
    { courseName:'art',assignmentName:'homework2',username:'liming',argument:"8" },
  ];
  similarityData = [{username1:"test1",username2:"test2",similarity:"30%"}]
  currentCourse='hhaha'
  currentCourseID=''
  currentName='sss'
  currentSim ={}
  homeworkContent='1+1='
  homework1 = ''
  homework2 = ''
  peerState = []
	constructor() {
		
		//mobx 设置响应式
		makeAutoObservable(this)
	}
  setHomework1 = (x) => {
    this.homework1 = x
  }
  setHomework2 = (x) => {
    this.homework2 = x
  }
  setSimilarityData = (x) => {
    this.similarityData = x;
  }
  setCurrentSim = (x) =>{
    this.currentSim = x
  }
  setPeerState = (x)=> {
    // this.peerState = x
    var jsonString = JSON.stringify(toJS(x))
    this.peerState = JSON.parse(jsonString)
  }
  setCourseData = (newData)=> {
    // console.log(newData)
    this.courseData=newData
    // var jsonString = JSON.stringify(toJS(newData))
    // console.log(jsonString)
    // this.coursedata = JSON.parse(jsonString)
  }

  updateSimilarityData = async(id) =>{
    try {
      const ret = await http.post('/teacher/getSimilarityByAssignmentID', {
        assignmentID:id
      })
      if(ret.data.message =='success') {
        console.log(ret.data)
        this.setSimilarityData(ret.data.data.similarityRecode)
        
      }

      // window.location.reload()
    } catch(e) {
      if(e.response) console.log(e.response.data.message)
      else console.log(e.message)
      console.log(e)
    }
  }

	updateCourseData = async () => {
    try {
      const ret = await http.post('/teacher/getCourseList', {
        
      })
      if(ret.data) this.setCourseData(ret.data.data.courses)
      

      // window.location.reload()
    } catch(e) {
      console.log(e)
    }
  }
  setArgumentData = (x) => {
    this.argumentData=x
  }
  updateArgumentData = async() => {
    try {
      const ret = await http.post('/homework/getArgumentHomeworkList', {
        
      })
      if(ret.data) this.setArgumentData(ret.data.data.homeworks)
      

      // window.location.reload()
    } catch(e) {
      console.log(e)
    }
  }
  setCurrentCourseID = (x) => {
    this.currentCourseID=x
  }
  
  setCurrentCourseName = (x) => {
    this.currentCourseName=x
  }
  setHomeworkContent = (x) => {
    this.homeworkContent=x
  }
  setStudentData=(newData) => {
    // this.studentData = newData
    var jsonString = JSON.stringify(toJS(newData))
    this.studentData = JSON.parse(jsonString)
  }
  
  updateStudentData = async (s) => {
    try {
      console.log('s is '+ s)
      const ret = await http.post('/teacher/getStudentListByCourseID', {
        courseID : s
      })
      if(ret.data) this.setStudentData(ret.data.data.students)
      console.log(ret.data.data.students)

      // window.location.reload()
    } catch(e) {
      console.log('catch ')
      console.log(e)
    }
  }
  updateStudentGradeData = async (courseID,assignmentID) => {
    try {
      const ret = await http.post('/homework/getHomeworkByAssignmentID', {
        courseID,
        assignmentID
      })
      if(ret.data) this.setStudentData(ret.data.data.homeworks)
      console.log(ret.data.data.homeworks)

      // window.location.reload()
    } catch(e) {
      console.log('catch ')
      console.log(e)
    }
  }

  setAssignmentData=(newData) => {
    this.assignmentData = newData
    // var jsonString = JSON.stringify(toJS(newData))
    // this.assignmentData = JSON.parse(jsonString)
  }
  //todo
  updateAssignmentData = async (s) => {
    try {
      const ret = await http.post('/teacher/getAssignmentListByCourseID', {
        courseID:s
      })
      if(ret.data) this.setAssignmentData(ret.data.data.assignments)
      console.log(ret.data)

      // window.location.reload()
    } catch(e) {
      console.log(e)
    }
  }
}

export default TeacherStore