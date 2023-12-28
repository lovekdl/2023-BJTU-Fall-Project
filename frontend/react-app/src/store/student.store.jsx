import {makeAutoObservable} from 'mobx'
import {http} from '../utils'
import { toJS } from 'mobx';
// import {http} from '../utils'
class StudentStore {
  courseData=[
    {
      key:'1',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'1',
      teacher:'lhm',
    },
    {
      key:'2',
      courseName:'sleep',
      courseDescribe:'sleep in the bed',
      courseNumber :'55',
      courseID:'2',
      teacher:'yxh',
    },
    {
      key:'3',
      courseName:'eat',
      courseDescribe:'I love eat',
      courseNumber :'66',
      courseID:'3',
      teacher:'gj',
    },
    {
      key:'4',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'4',
      teacher:'xxx',
    },
    {
      key:'5',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'5',
      teacher:'abc',
    },
    {
      key:'6',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'6',
      teacher:'def',
    },
    {
      key:'7',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'7',
      teacher:'fh',
    },{
      key:'8',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'8',
      teacher:'zzz',
    },
    
  ]
  peerHomework=[
    {
      key:'1',
      courseName:'test',
      courseDescribe:'test',
      courseNumber :'55',
      courseID:'1',
      teacher:'lhm',
      
    }]
  assignmentData=[
    {
      key:'1',
      assignmentName:'作业1',
      assignmentDescribe:'实训',
      date:'2023-11-11',
      time:'10:10:10',
      assignmentID:'1',
      grade:'80',
      submit: '未提交',
    },
    {
      key:'2',
      assignmentName:'作业2',
      assignmentDescribe:'好难',
      time:'10:10:10',
      date:'2023-11-11',
      assignmentID:'2',
      grade:'100',
      submit: '已提交',
    },
    {
      key:'3',
      assignmentName:'作业3',
      assignmentDescribe:'不会写怎么办',
      date:'2023-12-11',
      time:'10:10:10',
      assignmentID:'3',
      grade:'95',
      submit: '已截止',
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
  currentCourse='hhaha'
  currentCourseID=''
  currentName='sss'
  currentAssignmentName="aaa"
  currentAssignmentID='bbb'
  currentAssignmentDescribe='describe'
  currentAssignment = {}
  homeworkContent='1+1=?'
  editorContent=''
  filterList= ['未提交','已提交','已截止'];
  evaluateHomework='aaa'
  answer = '标准答案'
  currentHomework = {
    homeworkID:'1010',
  }
  
	constructor() {
		
		//mobx 设置响应式
		makeAutoObservable(this)
	}
  get filteredAssignments() {
    
    return this.assignmentData.filter(assignment => this.filterList.includes(assignment.submit));
  }
  assignmentContent = ''
  setAssignmentContent = (x) => {
    this.assignmentContent = x
  }
  setCurrentHomework = (x) => {
    this.currentHomework = x
  }
  setAnswer = (x) => {
    this.answer = x
  }
  setFilterList = (x) => {
    this.filterList = x
  }
  setEvaluateHomework = (x) => {
    this.evaluateHomework = x
  }
  setCurrentAssignment = (x) => {
    this.currentAssignment = x
  }
  setCurrentAssignmentName = (x) => {
    this.currentAssignmentName=x
  }
  setCurrentAssignmentDescribe = (x) => {
    this.currentAssignmentDescribe=x
  }
  setCurrentAssignmentID = (x) => {
    this.currentAssignmentID=x
  }
  setPeerHomework = (x) =>{
    this.peerHomework=x
  }
  get SlicedAssignments() {
    
    return this.assignmentData.filter(assignment => this.filterList.includes(assignment.submit));
  }
  setCourseData = (newData)=> {
    // console.log(newData)
    this.courseData=newData
    // var jsonString = JSON.stringify(toJS(newData))
    // console.log(jsonString)
    // this.coursedata = JSON.parse(jsonString)
  }
  setEditorContent = (x) => {
    this.editorContent=x
  }
	updateCourseData = async () => {
    try {
      const ret = await http.post('/student/getMyCourseList', {
        
      })
      if(ret.data) this.setCourseData(ret.data.data.courses)
      

      // window.location.reload()
    } catch(e) {
      console.log(e)
    }
  }
  updateEvaluateCourseData = async()=>{
    try {
      const ret = await http.post('/student/getPeerHomeworkByUID', {
        
      })
      if(ret.data) this.setPeerHomework(ret.data.data.peerHomework)
      

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
  


  setAssignmentData=(newData) => {
    this.assignmentData = newData
    // var jsonString = JSON.stringify(toJS(newData))
    // this.assignmentData = JSON.parse(jsonString)
  }
  //todo
  updateAssignmentData = async (s) => {
    try {
      const ret = await http.post('/student/getAssignmentListByCourseID', {
        courseID:s
      })
      if(ret.data) {
        this.setAssignmentData(ret.data.data.assignments)
        console.log(ret.data)
      }
  

      // window.location.reload()
    } catch(e) {
      console.log(e)
    }
  }
}

export default StudentStore