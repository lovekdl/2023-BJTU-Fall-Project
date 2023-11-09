package liqi.peerlearningsystembackend.controller;

import liqi.peerlearningsystembackend.pojo.CoursePojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.service.AssignmentService;
import liqi.peerlearningsystembackend.service.CourseService;
import liqi.peerlearningsystembackend.service.SCService;
import liqi.peerlearningsystembackend.service.UserService;
import liqi.peerlearningsystembackend.utils.Constants;
import liqi.peerlearningsystembackend.utils.Result;
import liqi.peerlearningsystembackend.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    SCService scService;

    @Autowired
    AssignmentService assignmentService;

    /**
     * 教师添加课程
     */
    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    public ResponseEntity<String> addCourse(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseName = data.get("courseName");
        String courseDescribe = data.get("courseDescribe");
        if (token == null || courseName == null || courseDescribe == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 添加课程
        String message = courseService.addCourse(courseName, user.getUuid(), courseDescribe);
        if(message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403",message);
        else
            return Result.okGetString();
    }

    /**
     * 教师删除课程
     */
    @RequestMapping(value = "/deleteCourseByCourseID", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCourseByCourseID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        if (token == null || courseID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 删除课程
        String message = courseService.deleteCourseByCourseID(Integer.parseInt(courseID));
        if(message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403",message);
        else
            return Result.okGetString();
    }

    /**
     * 教师设置课程名称
     */
    @RequestMapping(value = "/setCourseName", method = RequestMethod.POST)
    public ResponseEntity<String> setCourseName(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        String courseName = data.get("courseName");
        if (token == null || courseID == null || courseName == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置课程名称
        String message = courseService.setCourseName(Integer.parseInt(courseID), courseName);
        if(message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403",message);
        else
            return Result.okGetString();
    }

    /**
     * 教师设置课程名称
     */
    @RequestMapping(value = "/setCourseDescribe", method = RequestMethod.POST)
    public ResponseEntity<String> setCourseIntro(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        String courseDescribe = data.get("courseDescribe");
        if (token == null || courseID == null || courseDescribe == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置课程名称
        String message = courseService.setCourseIntro(Integer.parseInt(courseID), courseDescribe);
        if(message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403",message);
        else
            return Result.okGetString();
    }

    /**
     * 教师获取课程列表
     */
    @RequestMapping(value = "/getCourseList", method = RequestMethod.POST)
    public ResponseEntity<String> getCourseList(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        if (token == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 获取课程列表
        List<CoursePojo> courses = courseService.getCourseList();
        if(courses == null)
            return Result.okGetStringByMessage("don't have any course");
        List<Object> coursesInfo = new ArrayList<>();

        // 将课程信息转换为Map
        for (CoursePojo course : courses) {
            HashMap<String, String> courseInfo = new HashMap<>();
            courseInfo.put("key", String.valueOf(course.getCourseID()));
            courseInfo.put("courseID", String.valueOf(course.getCourseID()));
            courseInfo.put("courseName", course.getCourseName());
            courseInfo.put("courseDescribe", course.getIntro());
            courseInfo.put("courseNumber", String.valueOf(course.getNumber()));
            coursesInfo.add(courseInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("courses", coursesInfo);
                }}
        );
    }

    /**
     * 教师添加学生到课程
     */
    @RequestMapping(value = "/addStudentToCourse", method = RequestMethod.POST)
    public ResponseEntity<String> addStudentToCourse(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        String studentID = data.get("uid");
        if (token == null || courseID == null || studentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 添加学生到课程
        String message = scService.addSCByCourseIDAndStudentID(Integer.parseInt(courseID), Integer.parseInt(studentID));
        if(message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403",message);
        else
            return Result.okGetString();
    }

    /**
     * 教师从课程删除学生
     */
    @RequestMapping(value = "/deleteStudentFromCourse", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStudentFromCourse(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        String studentID = data.get("uid");
        if (token == null || courseID == null || studentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 删除学生
        String message = scService.deleteSCByCourseIDAndStudentID(Integer.parseInt(courseID), Integer.parseInt(studentID));
        if(message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403",message);
        else
            return Result.okGetString();
    }

    /**
     * 教师添加作业
     */
    @RequestMapping(value = "/addAssignmentWithoutFile", method = RequestMethod.POST)
    public ResponseEntity<String> addAssignment(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        String title = data.get("title");
        String content = data.get("content");
        String deadline = data.get("deadline");
        if (token == null || courseID == null || title == null || content == null || deadline == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 添加作业
//        String message = assignmentService.addAssignment(Integer.parseInt(courseID), assignmentName, assignmentContent, ddl);
//        if (message.startsWith("ERROR"))
//            return Result.errorGetStringByMessage("403", message);
//        else
//            return Result.okGetString();
        return null;
    }
}
