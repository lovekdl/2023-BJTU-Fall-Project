package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import liqi.peerlearningsystembackend.dao.CourseDao;
import liqi.peerlearningsystembackend.dao.SCDao;
import liqi.peerlearningsystembackend.dao.UserDao;
import liqi.peerlearningsystembackend.pojo.CoursePojo;
import liqi.peerlearningsystembackend.pojo.SCPojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SCService {

    @Autowired
    SCDao scDao;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseDao courseDao;

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
            SCPojo sc = new SCPojo(user.getUuid(), course.getUuid(), user.getUsername(), course.getCourseID());
            QueryWrapper<SCPojo> scPojoQueryWrapper = new QueryWrapper<>(sc);
            scDao.delete(scPojoQueryWrapper);
            course.setNumber(course.getNumber() - 1);
            courseDao.updateById(course);
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
            SCPojo sc = new SCPojo(user.getUuid(), course.getUuid(), user.getUsername(), course.getCourseID());
            scDao.insert(sc);
            course.setNumber(course.getNumber() + 1);
            courseDao.updateById(course);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据课程ID和学生用户名添加选课记录
     * @param courseID 课程ID
     * @param username 学生用户名
     * @return 返回"OK"或"ERROR"
     */
    public String addSCByCourseIDAndUsername(int courseID, String username) {
        try {
            UserPojo user = userService.getUserByName(username);
            CoursePojo course = courseService.getCourseByCourseID(courseID);
            if (user == null || course == null)
                return "ERROR";
            // 添加选课记录
            SCPojo sc = new SCPojo(user.getUuid(), course.getUuid(), user.getUsername(), course.getCourseID());
            scDao.insert(sc);
            course.setNumber(course.getNumber() + 1);
            courseDao.updateById(course);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据课程ID返回选课学生列表
     * @param courseID 课程ID
     * @return 返回学生列表
     */
    public List<UserPojo> getStudentsByCourseID(int courseID) {
        CoursePojo course = courseService.getCourseByCourseID(courseID);
        if(course == null)
            return null;
        String courseUUID = course.getUuid();
        MPJLambdaWrapper<SCPojo> queryWrapper = new MPJLambdaWrapper<SCPojo>()
                // Specify the join condition between the sc and user tables
                .selectAll(UserPojo.class) // Select all fields from the UserPojo
                .leftJoin(UserPojo.class, UserPojo::getUuid, SCPojo::getUserUUID)
                .eq(SCPojo::getCourseUUID, courseUUID); // Filter by the specific courseUUID

        // Perform the join operation and return the results
        return scDao.selectJoinList(UserPojo.class, queryWrapper);

    }
}
