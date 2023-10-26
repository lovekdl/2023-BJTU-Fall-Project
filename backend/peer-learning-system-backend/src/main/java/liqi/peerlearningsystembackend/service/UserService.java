package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import liqi.peerlearningsystembackend.dao.UserDao;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.utils.Tool;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService{

    @Autowired
    UserDao userDao;

    public String addUser(String username, String password) {

        String uuid = UUID.randomUUID().toString();

        try {
            userDao.insert(new UserPojo(uuid, username, password));
            return uuid;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }
    public String addUser(String username, String password, String email, int authority) {

        String uuid = UUID.randomUUID().toString();

        try {
            userDao.insert(new UserPojo(uuid, username, password, email, authority));
            return uuid;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    public void deleteUser(String username) {
        userDao.deleteById(username);
    }

    @Nullable
    public UserPojo getUserByName(String username) {
        return userDao.selectOne(new QueryWrapper<UserPojo>().eq("username", username));
    }

}
