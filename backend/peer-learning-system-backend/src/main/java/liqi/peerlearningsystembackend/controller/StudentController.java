package liqi.peerlearningsystembackend.controller;

import liqi.peerlearningsystembackend.pojo.AssignmentPojo;
import liqi.peerlearningsystembackend.pojo.CoursePojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.service.*;
import liqi.peerlearningsystembackend.utils.Constants;
import liqi.peerlearningsystembackend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    HomeworkService homeworkService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    SCService scService;

    /**
     * 学生获取所有课程列表
     */
    @RequestMapping(value = "/getAllCourseList", method = RequestMethod.POST)
    public ResponseEntity<String> getAllCourseList(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        if (token == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

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
     * 学生获取自己的课程列表
     */
    @RequestMapping(value = "/getMyCourseList", method = RequestMethod.POST)
    public ResponseEntity<String> getMyCourseList(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        if (token == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 获取课程列表
        List<CoursePojo> courses = scService.getCoursesByStudentID(user.getUid());
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
     * 学生获取课程作业列表
     */
    @RequestMapping(value = "/getAssignmentListByCourseID", method = RequestMethod.POST)
    public ResponseEntity<String> getAssignmentListByCourseID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        if (token == null || courseID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo teacher = userService.checkToken(token);
        if (teacher == null || teacher.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 获取课程作业列表
        List<AssignmentPojo> assignments = assignmentService.getAssignmentListByCourseID(Integer.parseInt(courseID));
        if (assignments == null)
            return Result.okGetStringByMessage("don't have any assignment");

        List<Object> assignmentsInfo = new ArrayList<>();

        // 将作业信息转换为Map
        for (AssignmentPojo assignment : assignments) {
            HashMap<String, String> studentInfo = new HashMap<>();
            studentInfo.put("key", String.valueOf(assignment.getAssignmentID()));
            studentInfo.put("assignmentID", String.valueOf(assignment.getAssignmentID()));
            studentInfo.put("assignmentName", assignment.getTitle());
            studentInfo.put("assignmentDescribe", assignment.getContent());
            studentInfo.put("date", assignment.getDeadline().split(" ")[0]);
            studentInfo.put("time", assignment.getDeadline().split(" ")[1]);
            assignmentsInfo.add(studentInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("assignments", assignmentsInfo);
                }}
        );
    }

    /**
     * 学生添加无附件作业
     */
    @RequestMapping(value = "/addHomeworkWithoutFile", method = RequestMethod.POST)
    public ResponseEntity<String> addHomeworkWithoutFile(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        String content = data.get("content");
        String date = data.get("date");
        String time = data.get("time");
        if (token == null || date == null || time == null)
            return Result.errorGetStringByMessage("400", "something is null");

        String submitTime = date + " " + time;
//        System.out.println(submitTime);

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 添加作业
        String message = homeworkService.addHomeworkWithoutFile(Integer.parseInt(assignmentID), user.getUid(), content, submitTime);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }
}
