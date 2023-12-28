package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import liqi.peerlearningsystembackend.dao.AssignmentDao;
import liqi.peerlearningsystembackend.dao.CounterDao;
import liqi.peerlearningsystembackend.pojo.*;
import liqi.peerlearningsystembackend.utils.Constants;
import liqi.peerlearningsystembackend.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AssignmentService {

    @Autowired
    AssignmentDao assignmentDao;

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    CounterDao counterDao;

    /**
     * 添加一条没有附件的作业
     * @param courseID 课程ID
     * @param title 作业标题
     * @param content 作业内容
     * @param deadline 作业截止时间
     * @return 返回该作业的UUID
     */
    public String addAssignmentWithoutFile(int courseID, String title, String content, String deadline) {
        String uuid = UUID.randomUUID().toString();
        try {
            // 插入课程
            Integer assignmentID = counterDao.selectById(Constants.ASSIGNMENT_COUNTER).getUid();
            CoursePojo course = courseService.getCourseByCourseID(courseID);
            if(course == null)
                return "ERROR";
            assignmentDao.insert(new AssignmentPojo(uuid, assignmentID, course.getUuid(), title, content, null, deadline, "未开始互评", null, null));

            // 更新计数器
            CounterPojo counterPojo = counterDao.selectById(Constants.ASSIGNMENT_COUNTER);
            counterPojo.setUid(counterPojo.getUid() + 1);
            counterDao.updateById(counterPojo);

            return uuid;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause.getMessage().contains("Incorrect datetime value")) {
                return "ERROR: Incorrect datetime value";
            }
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据作业ID删除作业
     * @param assignmentID 作业ID
     * @return 返回"OK"或"ERROR"
     */
    public String deleteAssignmentByAssignmentID(int assignmentID) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if(assignment == null)
                return "ERROR";
            assignmentDao.deleteById(assignment.getUuid());
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据作业ID获取作业
     * @param assignmentID 作业ID
     * @return 作业
     */
    @Nullable
    public AssignmentPojo getAssignmentByID(int assignmentID) {
        return assignmentDao.selectOne(new QueryWrapper<AssignmentPojo>().eq("assignmentID", assignmentID));
    }

    /**
     * 根据作业UUID获取作业
     * @param assignmentUUID 作业ID
     * @return 作业
     */
    @Nullable
    public AssignmentPojo getAssignmentByUUID(String assignmentUUID) {
        return assignmentDao.selectById(assignmentUUID);
    }

    /**
     * 根据课程ID获取作业
     * @param courseID 作业ID
     * @return 作业
     */
    @Nullable
    public List<AssignmentPojo> getAssignmentListByCourseID(int courseID) {
        CoursePojo course = courseService.getCourseByCourseID(courseID);
        if(course == null)
            return null;
        return assignmentDao.selectList(new QueryWrapper<AssignmentPojo>().eq("courseUUID", course.getUuid()));
    }

    /**
     * 教师设置作业题目
     * @param assignmentID 作业ID
     * @param title 题目
     * @return 返回"OK"或"ERROR"
     */
    public String setAssignmentTitle(int assignmentID, String title) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if(assignment == null)
                return "ERROR";
            assignment.setTitle(title);
            assignmentDao.updateById(assignment);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 教师设置作业有优秀作业
     * @param assignmentID 作业ID
     * @param homeworkID 作业ID
     * @return 返回"OK"或"ERROR"
     */
    public String setAssignmentExcellent(int assignmentID, String homeworkID) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if (assignment == null)
                return "ERROR";
            assignment.setExcellent(homeworkID);
            assignmentDao.updateById(assignment);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 教师取消任务有优秀作业
     * @param assignmentID 作业ID
     * @return 返回"OK"或"ERROR"
     */
    public String cancelAssignmentExcellent(int assignmentID) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if (assignment == null)
                return "ERROR";
            assignment.setExcellent(null);
            assignmentDao.updateById(assignment);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 教师设置作业内容
     * @param assignmentID 作业ID
     * @param content 内容
     * @return 返回"OK"或"ERROR"
     */
    public String setAssignmentContent(int assignmentID, String content) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if (assignment == null)
                return "ERROR";
            assignment.setContent(content);
            assignmentDao.updateById(assignment);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 教师设置作业答案
     * @param assignmentID 作业ID
     * @param answer 答案
     * @return 返回"OK"或"ERROR"
     */
    public String setAssignmentAnswer(int assignmentID, String answer) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if (assignment == null)
                return "ERROR";
            assignment.setAnswer(answer);
            assignmentDao.updateById(assignment);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 教师设置作业截止时间
     * @param assignmentID 作业ID
     * @param deadline 截止时间
     * @return 返回"OK"或"ERROR"
     */
    public String setAssignmentDeadline(int assignmentID, String deadline) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if (assignment == null)
                return "ERROR";
            assignment.setDeadline(deadline);
            assignmentDao.updateById(assignment);
            return "OK";
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause.getMessage().contains("Incorrect datetime value")) {
                return "ERROR: Incorrect datetime value";
            }
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 教师设置作业状态
     * @param assignmentID 作业ID
     * @param status 状态
     * @return 返回"OK"或"ERROR"
     */
    public String setAssignmentStatus(int assignmentID, String status) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if (assignment == null)
                return "ERROR";
            assignment.setStatus(status);
            assignmentDao.updateById(assignment);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 设置作业附件
     * @param assignmentID 任务ID
     * @param file 附件
     * @return 返回"OK"或"ERROR"
     */
    public String setAssignmentFile(int assignmentID, MultipartFile file) {
        try {
            AssignmentPojo assignment = getAssignmentByID(assignmentID);
            if (assignment == null)
                return "ERROR";

            Path filePath = Tool.saveFile(Constants.ASSIGNMENT_FILE_PATH, file);

            assignment.setFilePath(filePath.toString());
            assignmentDao.updateById(assignment);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }
}
