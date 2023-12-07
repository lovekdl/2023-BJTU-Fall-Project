package liqi.peerlearningsystembackend.controller;

import liqi.peerlearningsystembackend.pojo.AssignmentPojo;
import liqi.peerlearningsystembackend.pojo.CoursePojo;
import liqi.peerlearningsystembackend.pojo.HomeworkPojo;
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
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    HomeworkService homeworkService;

    @Autowired
    UserService userService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    CourseService courseService;

    @Autowired
    SCService scService;

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

    /**
     * 学生设置作业内容
     */
    @RequestMapping(value = "/setHomeworkContent", method = RequestMethod.POST)
    public ResponseEntity<String> setHomeworkContent(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        String content = data.get("homeworkContent");
        if (token == null || homeworkID == null || content == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 设置作业标题
        String message = homeworkService.setHomeworkContent(Integer.parseInt(homeworkID), content);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 学生设置作业上交时间
     */
    @RequestMapping(value = "/setHomeworkSubmitTime", method = RequestMethod.POST)
    public ResponseEntity<String> setHomeworkSubmitTime(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        String date = data.get("date");
        String time = data.get("time");
        if (token == null || homeworkID == null || date == null || time == null)
            return Result.errorGetStringByMessage("400", "something is null");

        String submitTime = date + " " + time;

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 设置作业上交时间
        String message = homeworkService.setHomeworkSubmitTime(Integer.parseInt(homeworkID), submitTime);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 设置作业分数
     */
    @RequestMapping(value = "/setScore", method = RequestMethod.POST)
    public ResponseEntity<String> setScore(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        String score = data.get("grade");
        if (token == null || homeworkID == null || score == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户
        UserPojo user = userService.checkToken(token);
        if (user == null)
            return Result.errorGetStringByMessage("403", "token is wrong");

        // 设置作业分数
        String message = homeworkService.setScore(Integer.parseInt(homeworkID), Integer.parseInt(score));
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 学生投诉作业
     */
    @RequestMapping(value = "/setHomeworkArgument", method = RequestMethod.POST)
    public ResponseEntity<String> setHomeworkArgument(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        String argument = data.get("argument");
        if (token == null || homeworkID == null || argument == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 设置作业分数
        String message = homeworkService.setHomeworkArgument(Integer.parseInt(homeworkID), argument);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 取消作业投诉
     */
    @RequestMapping(value = "/cancelHomeworkArgument", method = RequestMethod.POST)
    public ResponseEntity<String> cancelHomeworkArgument(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        if (token == null || homeworkID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户
        UserPojo user = userService.checkToken(token);
        if (user == null)
            return Result.errorGetStringByMessage("403", "token is wrong");

        // 设置作业分数
        String message = homeworkService.cancelHomeworkArgument(Integer.parseInt(homeworkID));
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 获取有投诉的作业列表
     */
    @RequestMapping(value = "/getArgumentHomeworkList", method = RequestMethod.POST)
    public ResponseEntity<String> getArgumentHomeworkList(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        if (token == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是老师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 获取有投诉的作业列表
        List<HomeworkPojo> homeworks = homeworkService.getHomeworksHaveArgument();
        if (homeworks == null)
            return Result.okGetStringByMessage("don't have any argument homework");

        List<Object> homeworksInfo = new ArrayList<>();

        for (HomeworkPojo homework : homeworks) {
            HashMap<String, String> homeworkInfo = new HashMap<>();
            homeworkInfo.put("key", String.valueOf(homework.getHomeworkID()));
            homeworkInfo.put("homeworkID", String.valueOf(homework.getHomeworkID()));
            homeworkInfo.put("username", userService.getUserByUUID(homework.getUserUUID()).getUsername());
            homeworkInfo.put("argument", homework.getArgument());

            // 获取作业对应的任务
            AssignmentPojo assignment = assignmentService.getAssignmentByUUID(homework.getAssignmentUUID());
            if (assignment == null)
                return Result.errorGetStringByMessage("403", "assignment is null");
            homeworkInfo.put("assignmentID", String.valueOf(assignment.getAssignmentID()));
            homeworkInfo.put("assignmentName", assignment.getTitle());

            // 获取作业对应的课程
            CoursePojo course = courseService.getCourseByUUID(assignment.getCourseUUID());
            if (course == null)
                return Result.errorGetStringByMessage("403", "course is null");
            homeworkInfo.put("courseID", String.valueOf(course.getCourseID()));
            homeworkInfo.put("courseName", course.getCourseName());

            homeworksInfo.add(homeworkInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homeworks", homeworksInfo);
                }}
        );

    }

    /**
     * 设置作业优秀
     */
    @RequestMapping(value = "/setHomeworkExcellent", method = RequestMethod.POST)
    public ResponseEntity<String> setHomeworkExcellent(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        String excellent = data.get("excellentReason");
        if (token == null || homeworkID == null || excellent == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是老师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置作业优秀
        String message = homeworkService.setHomeworkExcellent(Integer.parseInt(homeworkID), excellent);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);

        try {
            HomeworkPojo homework = homeworkService.getHomeworkByID(Integer.parseInt(homeworkID));
            AssignmentPojo assignment = assignmentService.getAssignmentByUUID(homework.getAssignmentUUID());
            assignmentService.setAssignmentExcellent(assignment.getAssignmentID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.errorGetStringByMessage("403", "ERROR");
        }

        return Result.okGetString();

    }

    /**
     * 取消作业优秀
     */
    @RequestMapping(value = "/cancelHomeworkExcellent", method = RequestMethod.POST)
    public ResponseEntity<String> cancelHomeworkExcellent(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        if (token == null || homeworkID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是老师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置作业优秀
        String message = homeworkService.cancelHomeworkExcellent(Integer.parseInt(homeworkID));
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);

        try {
            HomeworkPojo homework = homeworkService.getHomeworkByID(Integer.parseInt(homeworkID));
            AssignmentPojo assignment = assignmentService.getAssignmentByUUID(homework.getAssignmentUUID());
            assignmentService.cancelAssignmentExcellent(assignment.getAssignmentID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.errorGetStringByMessage("403", "ERROR");
        }

        return Result.okGetString();

    }

    /**
     * 根据任务ID获取优秀作业
     */
    @RequestMapping(value = "/getExcellentHomeworkByAssignmentID", method = RequestMethod.POST)
    public ResponseEntity<String> getExcellentHomeworkByAssignmentID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是老师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 获取优秀作业列表
        AssignmentPojo assignment = assignmentService.getAssignmentByID(Integer.parseInt(assignmentID));
        HomeworkPojo homework = homeworkService.getExcellentHomeworkByAssignmentID(assignment.getUuid());
        if (homework == null)
            return Result.okGetStringByMessage("don't have any excellent homework");

        HashMap<String, String> homeworkInfo = new HashMap<>();

        homeworkInfo.put("key", String.valueOf(homework.getHomeworkID()));
        homeworkInfo.put("homeworkID", String.valueOf(homework.getHomeworkID()));

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homework", homeworkInfo);
                }}
        );


    }

    /**
     * 根据用户ID获取作业列表
     */
    @RequestMapping(value = "/getHomeworkListByUserID", method = RequestMethod.POST)
    public ResponseEntity<String> getHomeworkListByUserID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String userID = data.get("userID");
        if (token == null || userID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户
        UserPojo user = userService.checkToken(token);
        if (user == null)
            return Result.errorGetStringByMessage("403", "token is wrong");

        // 获取用户作业列表
        List<HomeworkPojo> homeworks = homeworkService.getHomeworkListByUserID(Integer.parseInt(userID));
        if (homeworks == null)
            return Result.okGetStringByMessage("don't have any homework");

        List<Object> homeworksInfo = new ArrayList<>();

        // 将作业信息转换为Map
        for (HomeworkPojo homework : homeworks) {
            HashMap<String, String> homeworkInfo = new HashMap<>();
            homeworkInfo.put("key", String.valueOf(homework.getHomeworkID()));
            homeworkInfo.put("homeworkID", String.valueOf(homework.getHomeworkID()));
            homeworkInfo.put("homeworkContent", homework.getContent());
            homeworkInfo.put("date", homework.getSubmitTime().split(" ")[0]);
            homeworkInfo.put("time", homework.getSubmitTime().split(" ")[1]);
            homeworksInfo.add(homeworkInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homeworks", homeworksInfo);
                }}
        );
    }

    /**
     * 根据任务ID获取作业
     */
    @RequestMapping(value = "/getHomeworkByAssignmentID", method = RequestMethod.POST)
    public ResponseEntity<String> getHomeworkByAssignmentID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        String courseID = data.get("courseID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户
        UserPojo user = userService.checkToken(token);
        if (user == null)
            return Result.errorGetStringByMessage("403", "token is wrong");

        // 获取课程学生列表
        List<UserPojo> students = scService.getStudentsByCourseID(Integer.parseInt(courseID));

        List<Object> homeworksInfo = new ArrayList<>();

        // 返回每个学生的作业
        for (UserPojo student : students) {
            // 获取作业
            HomeworkPojo homework = homeworkService.getHomeworkByUserIDAndAssignmentID(student.getUid(), Integer.parseInt(assignmentID));
            HashMap<String, String> homeworkInfo = new HashMap<>();
            homeworkInfo.put("username", student.getUsername());
            homeworkInfo.put("uid", String.valueOf(student.getUid()));
            homeworkInfo.put("key", String.valueOf(student.getUid()));
            if (homework == null) {
                homeworkInfo.put("submit", "未提交");
                homeworkInfo.put("homeworkID", "/");
                homeworkInfo.put("date", "/");
                homeworkInfo.put("time", "/");
                homeworkInfo.put("grade", "/");
                homeworksInfo.add(homeworkInfo);
            } else {
                homeworkInfo.put("submit", "已提交");
                homeworkInfo.put("homeworkID", String.valueOf(homework.getHomeworkID()));
                homeworkInfo.put("date", homework.getSubmitTime().split(" ")[0]);
                homeworkInfo.put("time", homework.getSubmitTime().split(" ")[1]);
                homeworkInfo.put("grade", homework.getScore() == null ? "未评分" : String.valueOf(homework.getScore()));
                homeworksInfo.add(homeworkInfo);
            }
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homeworks", homeworksInfo);
                }}
        );
    }

    /**
     * 根据任务ID和用户ID获取作业
     */
    @RequestMapping(value = "/getHomeworkByAssignmentIDAndUserID", method = RequestMethod.POST)
    public ResponseEntity<String> getHomeworkByAssignmentIDAndUserID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        String userID = data.get("userID");
        if (token == null || assignmentID == null || userID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户
        UserPojo user = userService.checkToken(token);
        if (user == null)
            return Result.errorGetStringByMessage("403", "token is wrong");

        // 获取作业
        HomeworkPojo homework = homeworkService.getHomeworkByUserIDAndAssignmentID(Integer.parseInt(userID), Integer.parseInt(assignmentID));
        if (homework == null)
            return Result.okGetStringByMessage("don't have any homework");

        List<Object> homeworksInfo = new ArrayList<>();

        // 将作业信息转换为Map
        HashMap<String, String> homeworkInfo = new HashMap<>();
        homeworkInfo.put("key", String.valueOf(homework.getHomeworkID()));
        homeworkInfo.put("homeworkID", String.valueOf(homework.getHomeworkID()));
        homeworkInfo.put("homeworkContent", homework.getContent());
        homeworkInfo.put("date", homework.getSubmitTime().split(" ")[0]);
        homeworkInfo.put("time", homework.getSubmitTime().split(" ")[1]);

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homework", homeworksInfo);
                }}
        );
    }

    /**
     * 根据作业ID获取作业内容
     */
    @RequestMapping(value = "/getContentByHomeworkID", method = RequestMethod.POST)
    public ResponseEntity<String> getContentByHomeworkID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        if (token == null || homeworkID == null)
            return Result.errorGetStringByMessage("400", "something is null");

//        System.out.println(homeworkID);

        // 检验用户
        UserPojo user = userService.checkToken(token);
        if (user == null)
            return Result.errorGetStringByMessage("403", "token is wrong");

        // 获取作业
        HomeworkPojo homework = homeworkService.getHomeworkByID(Integer.parseInt(homeworkID));
        if (homework == null)
            return Result.okGetStringByMessage("don't have any homework");

        HashMap<String, String> homeworkInfo = new HashMap<>();
        homeworkInfo.put("homeworkContent", homework.getContent());

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homework", homeworkInfo);
                }}
        );
    }
}
