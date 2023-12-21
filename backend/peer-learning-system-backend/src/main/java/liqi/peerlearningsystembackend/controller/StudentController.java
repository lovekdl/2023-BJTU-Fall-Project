package liqi.peerlearningsystembackend.controller;

import liqi.peerlearningsystembackend.pojo.*;
import liqi.peerlearningsystembackend.service.*;
import liqi.peerlearningsystembackend.utils.Constants;
import liqi.peerlearningsystembackend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    PeerService peerService;

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

        // 检验用户是否是学生
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
            UserPojo teacher = userService.getUserByUUID(course.getUserUUID());
            courseInfo.put("key", String.valueOf(course.getCourseID()));
            courseInfo.put("courseID", String.valueOf(course.getCourseID()));
            courseInfo.put("courseName", course.getCourseName());
            courseInfo.put("courseDescribe", course.getIntro());
            courseInfo.put("teacherName", teacher.getUsername());
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
     * 学生根据课程号获取课程作业列表
     */
    @RequestMapping(value = "/getAssignmentListByCourseID", method = RequestMethod.POST)
    public ResponseEntity<String> getAssignmentListByCourseID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        if (token == null || courseID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 获取课程作业列表
        List<AssignmentPojo> assignments = assignmentService.getAssignmentListByCourseID(Integer.parseInt(courseID));
        if (assignments == null)
            return Result.okGetStringByMessage("don't have any assignment");

        List<Object> assignmentsInfo = new ArrayList<>();

        // 将作业信息转换为Map
        for (AssignmentPojo assignment : assignments) {
            HashMap<String, String> studentInfo = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime deadline = LocalDateTime.parse(assignment.getDeadline(), formatter);
            LocalDateTime now = LocalDateTime.now();
            HomeworkPojo homework = homeworkService.getHomeworkByUserIDAndAssignmentID(user.getUid(), assignment.getAssignmentID());
            if (deadline.isBefore(now))
                studentInfo.put("submit", "已截止");
            else if (homework == null)
                studentInfo.put("submit", "未提交");
            else
                studentInfo.put("submit", "已提交");
            studentInfo.put("key", String.valueOf(assignment.getAssignmentID()));
            studentInfo.put("assignmentID", String.valueOf(assignment.getAssignmentID()));
            studentInfo.put("assignmentName", assignment.getTitle());
            studentInfo.put("assignmentDescribe", assignment.getContent());
            studentInfo.put("date", assignment.getDeadline().split(" ")[0]);
            studentInfo.put("time", assignment.getDeadline().split(" ")[1]);
            if (homework == null) {
                studentInfo.put("grade", "/");
                studentInfo.put("homeworkID", "/");
            }
            else {
                studentInfo.put("grade", homework.getScore() == null ? "未评分" : String.valueOf(homework.getScore()));
                studentInfo.put("homeworkID", String.valueOf(homework.getHomeworkID()));
            }
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

        // 获取任务信息
        AssignmentPojo assignment = assignmentService.getAssignmentByID(Integer.parseInt(assignmentID));
        // 检验是否已经截止
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime deadline = LocalDateTime.parse(assignment.getDeadline(), formatter);
        LocalDateTime now = LocalDateTime.now();
        if (deadline.isBefore(now))
            return Result.errorGetStringByMessage("403", "assignment is overdue");

        // 添加作业
        String message = homeworkService.addHomeworkWithoutFile(Integer.parseInt(assignmentID), user.getUid(), content, submitTime);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 学生根据任务ID获取作业信息
     */
    @RequestMapping(value = "/getHomeworkByAssignmentID", method = RequestMethod.POST)
    public ResponseEntity<String> getHomeworkByAssignmentID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 获取作业信息
        HomeworkPojo homework = homeworkService.getHomeworkByUserIDAndAssignmentID(user.getUid(), Integer.parseInt(assignmentID));

        HashMap<String, String> homeworkInfo = new HashMap<>();
        homeworkInfo.put("username", user.getUsername());
        homeworkInfo.put("uid", String.valueOf(user.getUid()));
        homeworkInfo.put("key", String.valueOf(user.getUid()));
        if (homework == null) {
            homeworkInfo.put("submit", "未提交");
            homeworkInfo.put("homeworkID", "/");
            homeworkInfo.put("date", "/");
            homeworkInfo.put("time", "/");
            homeworkInfo.put("grade", "/");
        } else {
            homeworkInfo.put("submit", "已提交");
            homeworkInfo.put("homeworkID", String.valueOf(homework.getHomeworkID()));
            homeworkInfo.put("date", homework.getSubmitTime().split(" ")[0]);
            homeworkInfo.put("time", homework.getSubmitTime().split(" ")[1]);
            homeworkInfo.put("grade", homework.getScore() == null ? "未评分" : String.valueOf(homework.getScore()));
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homework", homeworkInfo);
                }}
        );
    }

    /**
     * 学生根据任务ID获取互评作业信息
     */
    @RequestMapping(value = "/getPeerHomeworkByAssignmentID", method = RequestMethod.POST)
    public ResponseEntity<String> getPeerHomeworkByAssignmentID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 获取任务信息
        AssignmentPojo assignment = assignmentService.getAssignmentByID(Integer.parseInt(assignmentID));
        if (assignment == null)
            return Result.errorGetStringByMessage("403", "assignmentID is wrong");

        // 获取作业信息
        List<PeerPojo> peerList = peerService.getPeerListByUserUUIDAndAssignmentUUID(user.getUuid(), assignment.getUuid());
        if (peerList == null)
            return Result.okGetStringByMessage("don't have any peer homework");

//        System.out.println(peerList);

        List<Object> peerHomeworkInfo = new ArrayList<>();
        for (PeerPojo peer : peerList) {
            if (peer.getStatus().equals("互评结束"))
                continue;
            HashMap<String, String> peerInfo = new HashMap<>();
            peerInfo.put("peerID", String.valueOf(peer.getPeerID()));
            peerInfo.put("homeworkID", String.valueOf(peer.getHomeworkID()));
            peerInfo.put("assignmentName", assignment.getTitle());
            peerInfo.put("assignmentDescribe", assignment.getContent());
            peerInfo.put("courseName", courseService.getCourseByUUID(assignment.getCourseUUID()).getCourseName());
            peerInfo.put("teacherName", userService.getUserByUUID(courseService.getCourseByUUID(assignment.getCourseUUID()).getUserUUID()).getUsername());
            peerInfo.put("grade", peer.getScore() == null ? "未评分" : String.valueOf(peer.getScore()));
            peerInfo.put("comment", peer.getComment() == null ? "未评价" : peer.getComment());
            peerHomeworkInfo.add(peerInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("peerHomework", peerHomeworkInfo);
                }}
        );
    }

    /**
     * 学生根据uid获取互拼作业信息
     */
    @RequestMapping(value = "/getPeerHomeworkByUID", method = RequestMethod.POST)
    public ResponseEntity<String> getPeerHomeworkByUID(@RequestBody Map<String, String> data) {
        // 获取数据
        String token = data.get("token");
        if (token == null)
            return Result.errorGetStringByMessage("400", "token is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 获取作业信息
        List<PeerPojo> peerList = peerService.getPeerListByUserUUID(user.getUuid());
        if (peerList == null)
            return Result.okGetStringByMessage("do not have any peer homework");

        List<Object> peerHomeworkInfo = new ArrayList<>();
        for (PeerPojo peer : peerList) {
            if (peer.getStatus().equals("互评结束"))
                continue;
            HashMap<String, String> peerInfo = new HashMap<>();
            AssignmentPojo assignment = assignmentService.getAssignmentByID(peer.getAssignmentID());
            peerInfo.put("peerID", String.valueOf(peer.getPeerID()));
            peerInfo.put("homeworkID", String.valueOf(peer.getHomeworkID()));
            peerInfo.put("assignmentName", assignment.getTitle());
            peerInfo.put("assignmentDescribe", assignment.getContent());
            peerInfo.put("courseName", courseService.getCourseByUUID(assignment.getCourseUUID()).getCourseName());
            peerInfo.put("teacherName", userService.getUserByUUID(courseService.getCourseByUUID(assignment.getCourseUUID()).getUserUUID()).getUsername());
            peerInfo.put("grade", peer.getScore() == null ? "未评分" : String.valueOf(peer.getScore()));
            peerInfo.put("comment", peer.getComment() == null ? "未评价" : peer.getComment());
            peerHomeworkInfo.add(peerInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("peerHomework", peerHomeworkInfo);
                }}
        );
    }

    /**
     * 学生根据作业ID获取互评作业内容
     */
    @RequestMapping(value = "/getPeerHomeworkContentByHomeworkID", method = RequestMethod.POST)
    public ResponseEntity<String> getPeerHomeworkContentByHomeworkID(@RequestBody Map<String, String> data) {
        // 获取数据
        String token = data.get("token");
        String homeworkID = data.get("homeworkID");
        if (token == null || homeworkID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 获取作业信息
        HomeworkPojo homework = homeworkService.getHomeworkByID(Integer.parseInt(homeworkID));
        if (homework == null)
            return Result.errorGetStringByMessage("403", "homeworkID is wrong");

        // 获取任务答案
        AssignmentPojo assignment = assignmentService.getAssignmentByUUID(homework.getAssignmentUUID());
        if (assignment == null)
            return Result.errorGetStringByMessage("403", "assignmentID is wrong");


        HashMap<String, String> homeworkInfo = new HashMap<>();
        homeworkInfo.put("homeworkContent", homework.getContent());
        homeworkInfo.put("assignmentAnswer", assignment.getAnswer());

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homework", homeworkInfo);
                }}
        );
    }

    /**
     * 学生给互评作业评分
     */
    @RequestMapping(value = "/gradePeerHomework", method = RequestMethod.POST)
    public ResponseEntity<String> gradePeerHomework(@RequestBody Map<String, String> data) {
        // 获取数据
        String token = data.get("token");
        String peerID = data.get("peerID");
        String grade = data.get("grade");
        String comment = data.get("comment");
        if (token == null || peerID == null || grade == null || comment == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not student");

        // 获取互评作业信息
        // 设置作业分数
        String message1 = peerService.setScore(Integer.parseInt(peerID), Integer.parseInt(grade));
        String message2 = peerService.setComment(Integer.parseInt(peerID), comment);
        if (message1.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message1);
        if (message2.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message2);
        peerService.setStatus(Integer.parseInt(peerID), "已互评");
        return Result.okGetString();
    }

    /**
     * 学生根据任务ID获取作业内容
     */
    @RequestMapping(value = "/getHomeworkContentByAssignmentID", method = RequestMethod.POST)
    public ResponseEntity<String> getHomeworkContentByAssignmentID(@RequestBody Map<String, String> data) {
        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null)
            return Result.errorGetStringByMessage("403", "token is wrong");

        // 获取作业信息
        HomeworkPojo homework = homeworkService.getHomeworkByUserIDAndAssignmentID(user.getUid(), Integer.parseInt(assignmentID));
        if (homework == null)
            return Result.errorGetStringByMessage("403", "homeworkID is wrong");

        HashMap<String, String> homeworkInfo = new HashMap<>();
        homeworkInfo.put("homeworkContent", homework.getContent());
        homeworkInfo.put("homeworkID", String.valueOf(homework.getHomeworkID()));

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("homework", homeworkInfo);
                }}
        );
    }

    /**
     * 学生获取未完成任务数量未完成互评作业数量
     */
    @RequestMapping(value = "/getUnfinished", method = RequestMethod.POST)
    public ResponseEntity<String> getUnfinished(@RequestBody Map<String, String> data) {
        // 获取数据
        String token = data.get("token");
        if (token == null)
            return Result.errorGetStringByMessage("400", "token is null");

        // 检验用户是否是学生
        UserPojo user = userService.checkToken(token);
        if (user == null)
            return Result.errorGetStringByMessage("403", "token is wrong");

        // 获取未完成任务数量
        List<CoursePojo> courseList = scService.getCoursesByStudentID(user.getUid());
        int unfinishedAssignmentNumber = 0;
        if (courseList != null)
            for (CoursePojo course : courseList) {
                List<AssignmentPojo> assignmentList = assignmentService.getAssignmentListByCourseID(course.getCourseID());
                if (assignmentList != null)
                    for (AssignmentPojo assignment : assignmentList) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime deadline = LocalDateTime.parse(assignment.getDeadline(), formatter);
                        LocalDateTime now = LocalDateTime.now();
                        HomeworkPojo homework = homeworkService.getHomeworkByUserIDAndAssignmentID(user.getUid(), assignment.getAssignmentID());
                        if (deadline.isAfter(now) && homework == null)
                            unfinishedAssignmentNumber++;
                    }
            }

        // 获取未完成互评作业数量
        List<PeerPojo> peerList = peerService.getPeerListByUserUUID(user.getUuid());
        int unfinishedPeerHomeworkNumber = 0;
        if (peerList != null)
            for (PeerPojo peer : peerList) {
                if (peer.getStatus().equals("正在互评中"))
                    unfinishedPeerHomeworkNumber++;
            }

        int finalUnfinishedAssignmentNumber = unfinishedAssignmentNumber;
        int finalUnfinishedPeerHomeworkNumber = unfinishedPeerHomeworkNumber;
        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("unfinishedAssignment", finalUnfinishedAssignmentNumber);
                    put("unfinishedPeerHomework", finalUnfinishedPeerHomeworkNumber);
                }}
        );
    }
}
