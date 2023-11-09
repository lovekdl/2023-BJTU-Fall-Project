package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import liqi.peerlearningsystembackend.dao.CourseDao;
import liqi.peerlearningsystembackend.dao.SCDao;
import liqi.peerlearningsystembackend.dao.UserDao;
import liqi.peerlearningsystembackend.pojo.CoursePojo;
import liqi.peerlearningsystembackend.pojo.SCPojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCService {

    @Autowired
    SCDao scDao;

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    /**
     * 根据课程ID和学生ID删除选课记录
     * @param courseID 课程ID
     * @param studentID 学生ID
     * @return 返回"OK"或"ERROR"
     */
    public String deleteSCByCourseIDAndStudentID(Integer courseID, Integer studentID) {
        try {
            UserPojo user = userService.getUserByUid(studentID);
            CoursePojo course = courseService.getCourseByCourseID(courseID);
            if(user == null || course == null)
                return "ERROR";
            // 删除选课记录
            SCPojo sc = new SCPojo(user.getUuid(), course.getUuid());
            QueryWrapper<SCPojo> scPojoQueryWrapper = new QueryWrapper<>(sc);
            scDao.delete(scPojoQueryWrapper);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }

    }

    /**
     * 根据课程ID和学生ID添加选课记录
     * @param courseID 课程ID
     * @param studentID 学生ID
     * @return 返回"OK"或"ERROR"
     */
    public String addSCByCourseIDAndStudentID(Integer courseID, Integer studentID) {
        try {
            UserPojo user = userService.getUserByUid(studentID);
            CoursePojo course = courseService.getCourseByCourseID(courseID);
            if (user == null || course == null)
                return "ERROR";
            // 添加选课记录
            SCPojo sc = new SCPojo(user.getUuid(), course.getUuid());
            scDao.insert(sc);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }
}
