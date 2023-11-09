package liqi.peerlearningsystembackend.service;

import liqi.peerlearningsystembackend.dao.AssignmentDao;
import liqi.peerlearningsystembackend.dao.CounterDao;
import liqi.peerlearningsystembackend.pojo.AssignmentPojo;
import liqi.peerlearningsystembackend.pojo.CounterPojo;
import liqi.peerlearningsystembackend.pojo.CoursePojo;
import liqi.peerlearningsystembackend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            assignmentDao.insert(new AssignmentPojo(uuid, assignmentID, course.getUuid(), title, content, "", deadline));

            // 更新计数器
            CounterPojo counterPojo = counterDao.selectById(Constants.COURSE_COUNTER);
            counterPojo.setUid(counterPojo.getUid() + 1);
            counterDao.updateById(counterPojo);

            return uuid;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }
}
