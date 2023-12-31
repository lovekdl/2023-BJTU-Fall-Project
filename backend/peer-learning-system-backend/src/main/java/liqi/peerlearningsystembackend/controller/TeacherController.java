package liqi.peerlearningsystembackend.controller;

import liqi.peerlearningsystembackend.pojo.*;
import liqi.peerlearningsystembackend.service.*;
import liqi.peerlearningsystembackend.utils.Constants;
import liqi.peerlearningsystembackend.utils.Result;
import liqi.peerlearningsystembackend.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    HomeworkService homeworkService;

    @Autowired
    PeerService peerService;

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
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 添加课程
        String message = courseService.addCourse(courseName, user.getUuid(), courseDescribe);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 教师删除课程
     */
    @RequestMapping(value = "/deleteCourseByCourseID", method = RequestMethod.POST)
    public ResponseEntity<String> deleteCourseByCourseID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        if (token == null || courseID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 删除课程
        String message = courseService.deleteCourseByCourseID(Integer.parseInt(courseID));
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
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
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置课程名称
        String message = courseService.setCourseName(Integer.parseInt(courseID), courseName);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
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
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置课程名称
        String message = courseService.setCourseIntro(Integer.parseInt(courseID), courseDescribe);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
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
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 获取课程列表
        List<CoursePojo> courses = courseService.getCourseListByUserUUID(user.getUuid());
        if (courses == null)
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
        String username = data.get("username");
        String studentID = data.get("uid");
        if (token == null || courseID == null || (studentID == null && username == null))
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 添加学生到课程
        UserPojo student;
        if (studentID != null)
            student = userService.getUserByUid(Integer.parseInt(studentID));
        else
            student = userService.getUserByName(username);
        if (student == null)
            return Result.errorGetStringByMessage("403", "student does not exist");
        if (student.getAuthority() != Constants.AUTHORITY_STUDENT)
            return Result.errorGetStringByMessage("403", "only student can be added");

        String message;
        if (studentID != null)
            message = scService.addSCByCourseIDAndStudentID(Integer.parseInt(courseID), Integer.parseInt(studentID));
        else
            message = scService.addSCByCourseIDAndUsername(Integer.parseInt(courseID), username);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 教师从课程删除学生
     */
    @RequestMapping(value = "/deleteStudentFromCourse", method = RequestMethod.POST)
    public ResponseEntity<String> deleteStudentFromCourse(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        String studentID = data.get("uid");
        if (token == null || courseID == null || studentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 删除学生
        String message = scService.deleteSCByCourseIDAndStudentID(Integer.parseInt(courseID), Integer.parseInt(studentID));
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 教师获取课程学生列表
     */
    @RequestMapping(value = "/getStudentListByCourseID", method = RequestMethod.POST)
    public ResponseEntity<String> getStudentListByCourseID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
//            System.out.println(data);
        if (token == null || courseID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo teacher = userService.checkToken(token);
        if (teacher == null || teacher.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 获取课程学生列表
        List<UserPojo> students = scService.getStudentsByCourseID(Integer.parseInt(courseID));
        if (students == null)
            return Result.okGetStringByMessage("don't have any student");
        List<Object> studentsInfo = new ArrayList<>();

        // 将学生信息转换为Map
        for (UserPojo student : students) {
            HashMap<String, String> studentInfo = new HashMap<>();
            studentInfo.put("key", String.valueOf(student.getUid()));
            studentInfo.put("uid", String.valueOf(student.getUid()));
            studentInfo.put("username", student.getUsername());
            studentsInfo.add(studentInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("students", studentsInfo);
                    put("courseNumber", students.size());
                }}
        );
    }

    /**
     * 教师添加无附件作业
     */
    @RequestMapping(value = "/addAssignmentWithoutFile", method = RequestMethod.POST)
    public ResponseEntity<String> addAssignmentWithoutFile(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        String title = data.get("assignmentName");
        String content = data.get("assignmentDescribe");
//        String deadline = data.get("deadline");
        String date = data.get("date");
        String time = data.get("time");
        if (token == null || courseID == null || title == null || content == null || date == null || time == null)
            return Result.errorGetStringByMessage("400", "something is null");

        String deadline = date + " " + time;
//        System.out.println(deadline);

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 添加作业
        String message = assignmentService.addAssignmentWithoutFile(Integer.parseInt(courseID), title, content, deadline);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 教师添任务附件
     */
    @RequestMapping(value = "/setAssignmentFile", method = RequestMethod.POST)
    public ResponseEntity<String> setAssignmentFile(
            @RequestParam("token") String token,
            @RequestParam("file") MultipartFile file,
            @RequestParam("assignmentID") String assignmentID) {

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        AssignmentPojo assignment = assignmentService.getAssignmentByID(Integer.parseInt(assignmentID));
        if (assignment == null)
            return Result.errorGetStringByMessage("403", "assignment is null");

        // 设置作业附件
        String message = assignmentService.setAssignmentFile(Integer.parseInt(assignmentID), file);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);

        return Result.okGetString();
    }

    /**
     * 教师删除作业
     */
    @RequestMapping(value = "/deleteAssignmentByAssignmentID", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAssignmentByAssignmentID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if (user == null || user.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 删除作业
        String message = assignmentService.deleteAssignmentByAssignmentID(Integer.parseInt(assignmentID));
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 教师获取课程作业列表
     */
    @RequestMapping(value = "/getAssignmentListByCourseID", method = RequestMethod.POST)
    public ResponseEntity<String> getAssignmentListByCourseID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String courseID = data.get("courseID");
        if (token == null || courseID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo teacher = userService.checkToken(token);
        if (teacher == null || teacher.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

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
            studentInfo.put("assignmentHasExcellent", assignment.getExcellent() == null ? "false" : "true");
            studentInfo.put("assignmentStatus", assignment.getStatus());
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
     * 教师设置作业标题
     */
    @RequestMapping(value = "/setAssignmentTitle", method = RequestMethod.POST)
    public ResponseEntity<String> setAssignmentTitle(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        String title = data.get("assignmentName");
        if (token == null || assignmentID == null || title == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo teacher = userService.checkToken(token);
        if (teacher == null || teacher.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置作业标题
        String message = assignmentService.setAssignmentTitle(Integer.parseInt(assignmentID), title);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 教师设置作业内容
     */
    @RequestMapping(value = "/setAssignmentContent", method = RequestMethod.POST)
    public ResponseEntity<String> setAssignmentContent(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        String content = data.get("assignmentDescribe");
        if (token == null || assignmentID == null || content == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo teacher = userService.checkToken(token);
        if (teacher == null || teacher.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置作业内容
        String message = assignmentService.setAssignmentContent(Integer.parseInt(assignmentID), content);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 教师设置作业截止时间
     */
    @RequestMapping(value = "/setAssignmentDeadline", method = RequestMethod.POST)
    public ResponseEntity<String> setAssignmentDeadline(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        String date = data.get("date");
        String time = data.get("time");
        if (token == null || assignmentID == null || date == null || time == null)
            return Result.errorGetStringByMessage("400", "something is null");

        String deadline = date + " " + time;

        // 检验用户是否是教师
        UserPojo teacher = userService.checkToken(token);
        if (teacher == null || teacher.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 设置作业截止时间
        String message = assignmentService.setAssignmentDeadline(Integer.parseInt(assignmentID), deadline);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);
        else
            return Result.okGetString();
    }

    /**
     * 教师开始某任务互评
     */
    @RequestMapping(value = "/startPeer", method = RequestMethod.POST)
    public ResponseEntity<String> startPeer(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        String peerNumber = data.get("peerNumber");
        String assignmentAnswer = data.get("assignmentAnswer");
        if (token == null || assignmentID == null || peerNumber == null || assignmentAnswer == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo teacher = userService.checkToken(token);
        if (teacher == null || teacher.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");


        // 获取任务
        AssignmentPojo assignment = assignmentService.getAssignmentByID(Integer.parseInt(assignmentID));


        if (assignment == null)
            return Result.errorGetStringByMessage("403", "assignment is null");
        if (!assignment.getStatus().equals("未开始互评"))
            return Result.errorGetStringByMessage("403", "assignment status is wrong");

        // 设置作业答案
        String message = assignmentService.setAssignmentAnswer(Integer.parseInt(assignmentID), assignmentAnswer);
        if (message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", message);

        // 获取课程学生列表
        CoursePojo course = courseService.getCourseByUUID(assignment.getCourseUUID());
        List<UserPojo> students = scService.getStudentsByCourseID(course.getCourseID());
        if (students == null)
            return Result.okGetStringByMessage("don't have any student");

        List<Integer> studentIDs = new ArrayList<>();
        for (UserPojo student : students) {
            // 判断学生作业是否已经提交
            if(homeworkService.getHomeworkByUserIDAndAssignmentID(student.getUid(), Integer.parseInt(assignmentID)) != null)
                studentIDs.add(student.getUid());
        }

        if (studentIDs.isEmpty())
            return Result.errorGetStringByMessage("403", "does not exist a single homework");
        if (studentIDs.size() <= Integer.parseInt(peerNumber))
            return Result.errorGetStringByMessage("403", "don't have enough students who have submitted homework");



//        if (studentIDs.size() < students.size())
//            return Result.errorGetStringByMessage("403", "don't have enough homeworks");

        // 获取互评分配
        Map<Integer, List<Integer>> allocation = Tool.allocation(studentIDs, Integer.parseInt(peerNumber));

        // 保存互评分配
        for (Map.Entry<Integer, List<Integer>> entry : allocation.entrySet()) {
            Integer uid = entry.getKey();
            UserPojo student = userService.getUserByUid(uid);
            for (Integer peerID : entry.getValue()) {
                HomeworkPojo homework = homeworkService.getHomeworkByUserIDAndAssignmentID(peerID, Integer.parseInt(assignmentID));
                peerService.addPeer(student.getUuid(), homework.getUuid(), homework.getAssignmentUUID(), student.getUsername(), homework.getHomeworkID(), assignment.getAssignmentID());
            }
        }


        // 设置任务状态为互评中
        assignmentService.setAssignmentStatus(Integer.parseInt(assignmentID), "互评中");

        // 设置任务截止时间为当前时间 或 先于当前时间的截止时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String deadline = assignment.getDeadline();
        if (now.isBefore(LocalDateTime.parse(deadline, formatter)))
            assignmentService.setAssignmentDeadline(Integer.parseInt(assignmentID), now.format(formatter));

        return Result.okGetString();
    }

    /**
     * 教师结束某任务互评
     */
    @RequestMapping(value = "/endPeer", method = RequestMethod.POST)
    public ResponseEntity<String> endPeer(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo teacher = userService.checkToken(token);
        if (teacher == null || teacher.getAuthority() != Constants.AUTHORITY_TEACHER)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 获取任务
        AssignmentPojo assignment = assignmentService.getAssignmentByID(Integer.parseInt(assignmentID));
        if (assignment == null)
            return Result.errorGetStringByMessage("403", "assignment is null");

        // 获取课程
        CoursePojo course = courseService.getCourseByUUID(assignment.getCourseUUID());
        if (course == null)
            return Result.errorGetStringByMessage("403", "course is null");

        // 获取课程学生列表
        List<UserPojo> students = scService.getStudentsByCourseID(course.getCourseID());

        // 对每个学生的作业统计互评分数
        for (UserPojo student : students) {
            HomeworkPojo homework = homeworkService.getHomeworkByUserIDAndAssignmentID(student.getUid(), Integer.parseInt(assignmentID));
            List<PeerPojo> peers = peerService.getPeerListByAssignmentUUID(homework.getAssignmentUUID());
            if (peers.isEmpty())
                continue;
            Integer peerNumber = 0;
            Integer peerScore = 0;
            for (PeerPojo peer : peers) {
                if (Objects.equals(peer.getHomeworkUUID(), homework.getUuid())) {
                    peerNumber++;
                    peerScore += peer.getScore() == null ? 60 : peer.getScore();
                    peerService.setStatus(peer.getPeerID(), "互评结束");
                }
            }
            if (peerNumber == 0)
                homeworkService.setScore(homework.getHomeworkID(), 60);
            else
                homeworkService.setScore(homework.getHomeworkID(), peerScore / peerNumber);

//            System.out.println(homework.getHomeworkID() + " " + homework.getScore());
        }

        // 设置任务状态为互评结束
        assignmentService.setAssignmentStatus(Integer.parseInt(assignmentID), "互评结束");

        return Result.okGetString();
    }

    /**
     * 教师获取某任务互评列表
     */
    @RequestMapping(value = "/getPeerListByAssignmentID", method = RequestMethod.POST)
    public ResponseEntity<String> getPeerListByAssignmentID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师或学生
        UserPojo user = userService.checkToken(token);
        if (user == null || (user.getAuthority() != Constants.AUTHORITY_TEACHER))
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 获取任务
        AssignmentPojo assignment = assignmentService.getAssignmentByID(Integer.parseInt(assignmentID));
        if (assignment == null)
            return Result.errorGetStringByMessage("403", "assignment is null");

        // 获取课程
        CoursePojo course = courseService.getCourseByUUID(assignment.getCourseUUID());
        if (course == null)
            return Result.errorGetStringByMessage("403", "course is null");

        // 获取互评列表
        List<PeerPojo> peers = peerService.getPeerListByAssignmentUUID(assignment.getUuid());
        if (peers == null)
            return Result.okGetStringByMessage("don't have any peer");

        List<Object> peersInfo = new ArrayList<>();

        // 将互评信息转换为Map
        for (PeerPojo peer : peers) {
            HashMap<String, String> peerInfo = new HashMap<>();
            peerInfo.put("key", String.valueOf(peer.getPeerID()));
            peerInfo.put("peerID", String.valueOf(peer.getPeerID()));
            peerInfo.put("userName", peer.getUsername());
            peerInfo.put("peerScore", peer.getScore() == null ? "/" : String.valueOf(peer.getScore()));
            peerInfo.put("peerComment", peer.getComment() == null ? "/" : peer.getComment());
            HomeworkPojo homework = homeworkService.getHomeworkByID(peer.getHomeworkID());
            UserPojo peerUser = userService.getUserByUUID(homework.getUserUUID());
            peerInfo.put("peerName", peerUser.getUsername());
            peerInfo.put("peerStatus", peer.getStatus());
            peersInfo.add(peerInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("peers", peersInfo);
                }}
        );
    }

    /**
     * 教师获取某次任务的作业相似度
     */
    @RequestMapping(value = "/getSimilarityByAssignmentID", method = RequestMethod.POST)
    public ResponseEntity<String> getSimilarityByAssignmentID(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String assignmentID = data.get("assignmentID");
        if (token == null || assignmentID == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是教师
        UserPojo user = userService.checkToken(token);
        if (user == null || (user.getAuthority() != Constants.AUTHORITY_TEACHER))
            return Result.errorGetStringByMessage("403", "token is wrong or user is not teacher");

        // 获取作业列表
        List<HomeworkPojo> homeworks = homeworkService.getHomeworkListByAssignmentID(Integer.parseInt(assignmentID));
        if (homeworks == null)
            return Result.okGetStringByMessage("don't have any homework");

        int n = homeworks.size();
        List<Object> similarityInfos = new ArrayList<>();

        // 计算相似度
        for (int i = 0; i < n; i++) {
            HomeworkPojo homework1 = homeworks.get(i);
            for (int j = i + 1; j < n; j++) {
                HashMap<String, String> similarityInfo = new HashMap<>();
                HomeworkPojo homework2 = homeworks.get(j);
                String student1 = userService.getUserByUUID(homework1.getUserUUID()).getUsername();
                String student2 = userService.getUserByUUID(homework2.getUserUUID()).getUsername();
                float similarity = Tool.calcSimilarityWithJaroWinkler(homework1.getContent(), homework2.getContent());
                similarityInfo.put("key", String.valueOf(i*(n+1)+j));
                similarityInfo.put("username1", student1);
                similarityInfo.put("username2", student2);
                similarityInfo.put("content1", homework1.getContent());
                similarityInfo.put("content2", homework2.getContent());
                similarityInfo.put("similarity", String.valueOf(similarity));
                similarityInfos.add(similarityInfo);
            }
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("similarityRecode", similarityInfos);
                }}
        );
    }
}
