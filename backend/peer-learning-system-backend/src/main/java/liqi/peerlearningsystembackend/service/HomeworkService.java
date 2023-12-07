package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import liqi.peerlearningsystembackend.dao.CounterDao;
import liqi.peerlearningsystembackend.dao.HomeworkDao;
import liqi.peerlearningsystembackend.pojo.*;
import liqi.peerlearningsystembackend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HomeworkService {

    @Autowired
    HomeworkDao homeworkDao;

    @Autowired
    CounterDao counterDao;

    @Autowired
    CourseService courseService;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    UserService userService;

    /**
     * 添加一条没有附件的作业
     * @param assignmentID 作业ID
     * @param userID 用户ID
     * @param content 作业内容
     * @param submitTime 提交时间
     * @return 返回该作业的UUID
     */
    public String addHomeworkWithoutFile(int assignmentID, int userID, String content, String submitTime) {
        // 判断是否已提交过作业
        HomeworkPojo homework = getHomeworkByUserIDAndAssignmentID(userID, assignmentID);
        if(homework != null) {
            homework.setContent(content);
            homework.setSubmitTime(submitTime);
            homeworkDao.updateById(homework);
            return homework.getUuid();
        }
        String uuid = UUID.randomUUID().toString();
        try {
            // 插入作业
            Integer homeworkID = counterDao.selectById(Constants.HOMEWORK_COUNTER).getUid();
            AssignmentPojo assignment = assignmentService.getAssignmentByID(assignmentID);
            UserPojo user = userService.getUserByUid(userID);
            if(assignment == null || user == null)
                return "ERROR";
            homeworkDao.insert(new HomeworkPojo(uuid, homeworkID, assignment.getUuid(), user.getUuid(), null, submitTime, content, null, null, null));

            // 更新计数器
            CounterPojo counterPojo = counterDao.selectById(Constants.HOMEWORK_COUNTER);
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
     * @param homeworkID 作业ID
     * @return 返回"OK"或"ERROR"
     */
    public String deleteHomeworkByHomeworkID(int homeworkID) {
        try {
            HomeworkPojo homework = getHomeworkByID(homeworkID);
            if(homework == null)
                return "ERROR";
            homeworkDao.deleteById(homework.getUuid());
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据作业ID获取作业
     * @param homeworkID 作业ID
     * @return 作业
     */
    @Nullable
    public HomeworkPojo getHomeworkByID(int homeworkID) {
        return homeworkDao.selectOne(new QueryWrapper<HomeworkPojo>().eq("homeworkID", homeworkID));
    }

    /**
     * 根据作业UUID获取作业
     * @param homeworkUUID 作业ID
     * @return 作业
     */
    @Nullable
    public HomeworkPojo getAssignmentByUUID(int homeworkUUID) {
        return homeworkDao.selectById(homeworkUUID);
    }

    /**
     * 根据任务ID获取作业
     * @param assignmentID 任务ID
     * @return 作业
     */
    @Nullable
    public List<HomeworkPojo> getHomeworkListByAssignmentID(int assignmentID) {
        AssignmentPojo assignment = assignmentService.getAssignmentByID(assignmentID);
        if(assignment == null)
            return null;
        return homeworkDao.selectList(new QueryWrapper<HomeworkPojo>().eq("assignmentUUID", assignment.getUuid()));
    }

    /**
     * 根据学生ID获取作业
     * @param userID 学生ID
     * @return 作业
     */
    @Nullable
    public List<HomeworkPojo> getHomeworkListByUserID(int userID) {
        UserPojo user = userService.getUserByUid(userID);
        if(user == null)
            return null;
        return homeworkDao.selectList(new QueryWrapper<HomeworkPojo>().eq("userUUID", user.getUuid()));
    }

    /**
     * 根据学生ID和任务ID获取作业
     * @param userID 学生ID
     * @param assignmentID 任务ID
     * @return 作业
     */
    @Nullable
    public HomeworkPojo getHomeworkByUserIDAndAssignmentID(int userID, int assignmentID) {
        UserPojo user = userService.getUserByUid(userID);
        AssignmentPojo assignment = assignmentService.getAssignmentByID(assignmentID);
        if(user == null || assignment == null)
            return null;
        return homeworkDao.selectOne(new QueryWrapper<HomeworkPojo>().eq("userUUID", user.getUuid()).eq("assignmentUUID", assignment.getUuid()));
    }

    /**
     * 查询有投诉的作业
     */
    public List<HomeworkPojo> getHomeworksHaveArgument() {
        return homeworkDao.selectList(new QueryWrapper<HomeworkPojo>().isNotNull("argument"));
    }

    /**
     * 设置作业分数
     */
    public String setScore(int homeworkID, int score) {
        try {
            HomeworkPojo homework = getHomeworkByID(homeworkID);
            if (homework == null)
                return "ERROR";
            homework.setScore(score);
            homeworkDao.updateById(homework);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 设置作业内容
     * @param homeworkID 作业ID
     * @param content 内容
     * @return 返回"OK"或"ERROR"
     */
    public String setHomeworkContent(int homeworkID, String content) {
        try {
            HomeworkPojo homework = getHomeworkByID(homeworkID);
            if (homework == null)
                return "ERROR";
            homework.setContent(content);
            homeworkDao.updateById(homework);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 设置作业上交时间
     * @param homeworkID 作业ID
     * @param submitTime 截止时间
     * @return 返回"OK"或"ERROR"
     */
    public String setHomeworkSubmitTime(int homeworkID, String submitTime) {
        try {
            HomeworkPojo homework = getHomeworkByID(homeworkID);
            if (homework == null)
                return "ERROR";
            homework.setSubmitTime(submitTime);
            homeworkDao.updateById(homework);
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
     * 设置作业投诉
     * @param homeworkID 作业ID
     * @param argument 投诉
     * @return 返回"OK"或"ERROR"
     */
    public String setHomeworkArgument(int homeworkID, String argument) {
        try {
            HomeworkPojo homework = getHomeworkByID(homeworkID);
            if (homework == null)
                return "ERROR";
            homework.setArgument(argument);
            homeworkDao.updateById(homework);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 取消作业投诉
     * @param homeworkID 作业ID
     * @return 返回"OK"或"ERROR"
     */
    public String cancelHomeworkArgument(int homeworkID) {
        try {
            HomeworkPojo homework = getHomeworkByID(homeworkID);
            if (homework == null)
                return "ERROR";
            homework.setArgument(null);
            homeworkDao.updateById(homework);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 设置作业优秀
     * @param homeworkID 作业ID
     * @param excellent 优秀
     * @return 返回"OK"或"ERROR"
     */
    public String setHomeworkExcellent(int homeworkID, String excellent) {
        try {
            HomeworkPojo homework = getHomeworkByID(homeworkID);
            if (homework == null)
                return "ERROR";
            homework.setExcellent(excellent);
            homeworkDao.updateById(homework);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 取消作业优秀
     * @param homeworkID 作业ID
     * @return 返回"OK"或"ERROR"
     */
    public String cancelHomeworkExcellent(int homeworkID) {
        try {
            HomeworkPojo homework = getHomeworkByID(homeworkID);
            if (homework == null)
                return "ERROR";
            homework.setExcellent(null);
            homeworkDao.updateById(homework);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据任务ID获取优秀作业
     * @param assignmentID 任务ID
     * @return 优秀作业列表
     */
    @Nullable
    public HomeworkPojo getExcellentHomeworkByAssignmentID(String assignmentID) {
        return homeworkDao.selectOne(new QueryWrapper<HomeworkPojo>().eq("assignmentUUID", assignmentID).isNotNull("excellent"));
    }
}
