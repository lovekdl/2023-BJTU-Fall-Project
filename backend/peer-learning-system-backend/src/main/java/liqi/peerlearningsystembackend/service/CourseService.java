package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import liqi.peerlearningsystembackend.dao.CounterDao;
import liqi.peerlearningsystembackend.dao.CourseDao;
import liqi.peerlearningsystembackend.dao.UserDao;
import liqi.peerlearningsystembackend.pojo.CounterPojo;
import liqi.peerlearningsystembackend.pojo.CoursePojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    CounterDao counterDao;

    /**
     * 插入一条新的课程
     * @param courseName 课程名
     * @param teacherUUID 教师的UUID
     * @param intro 课程介绍
     * @return 返回该课程的UUID
     */
    public String addCourse(String courseName, String teacherUUID, String intro) {
        String uuid = UUID.randomUUID().toString();
        try {
            // 插入课程
            Integer courseID = counterDao.selectById(Constants.COURSE_COUNTER).getUid();
            courseDao.insert(new CoursePojo(uuid, courseID, teacherUUID, courseName, intro, 0));

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

    /**
     * 根据课程ID删除课程
     * @param courseID 课程ID
     * @return 返回"OK"或"ERROR"
     */
    public String deleteCourseByCourseID(int courseID) {
        try {
            CoursePojo course = getCourseByCourseID(courseID);
            if(course == null)
                    return "ERROR: course not exists";
            courseDao.deleteById(course.getUuid());
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据课程ID改变课程名称
     * @param courseID 课程ID
     * @param courseName 课程名称
     * @return 返回"OK"或"ERROR"
     */
    public String setCourseName(int courseID, String courseName) {
        try {
            CoursePojo course = getCourseByCourseID(courseID);
            if(course == null)
                return "ERROR: course not exists";
            course.setCourseName(courseName);
            courseDao.updateById(course);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据课程ID改变课程介绍
     * @param courseID 课程ID
     * @param intro 课程介绍
     * @return 返回"OK"或"ERROR"
     */
    public String setCourseIntro(int courseID, String intro) {
        try {
            CoursePojo course = getCourseByCourseID(courseID);
            if(course == null)
                return "ERROR: course not exists";
            course.setIntro(intro);
            courseDao.updateById(course);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据课程UUID查询课程
     */
    @Nullable
    public CoursePojo getCourseByUUID(String courseUUID) {
        return courseDao.selectById(courseUUID);
    }

    /**
     * 根据课程ID查询课程
     */
    @Nullable
    CoursePojo getCourseByCourseID(int courseID) {
        return courseDao.selectOne(new QueryWrapper<CoursePojo>().eq("courseID", courseID));
    }

    /**
     * 查看所有课程
     */
    @Nullable
    public List<CoursePojo> getCourseList() {
        return courseDao.selectList(new QueryWrapper<>());
    }


    /**
     * 根据教师UUID查询课程
     */
    @Nullable
    public List<CoursePojo> getCourseListByUserUUID(String teacherUUID) {
        return courseDao.selectList(new QueryWrapper<CoursePojo>().eq("userUUID", teacherUUID));
    }

}
