package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import liqi.peerlearningsystembackend.dao.CounterDao;
import liqi.peerlearningsystembackend.dao.UserDao;
import liqi.peerlearningsystembackend.pojo.CounterPojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.utils.Tool;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService{

    @Autowired
    UserDao userDao;

    @Autowired
    CounterDao counterDao;

    /**
     * 插入一条新的用户
     * @param username 用户名
     * @param password 密码
     * @return 返回该用户的UUID
     */
    public String addUser(String username, String password) {

        String uuid = UUID.randomUUID().toString();

        try {
            // 更新计数器
            CounterPojo counterPojo = counterDao.selectById(3);
            counterPojo.setUid(counterPojo.getUid() + 1);
            counterDao.updateById(counterPojo);
            // 插入用户
            Integer uid = counterDao.selectById(3).getUid();
            userDao.insert(new UserPojo(uuid, uid, username, password));
            return uuid;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 插入一条新的用户
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param authority 权限
     * @return 返回该用户的UUID
     */
    public String addUser(String username, String password, String email, int authority) {

        String uuid = UUID.randomUUID().toString();

        try {
            // 更新计数器
            CounterPojo counterPojo = counterDao.selectById(authority);
            counterPojo.setUid(counterPojo.getUid() + 1);
            counterDao.updateById(counterPojo);
            // 插入用户
            Integer uid = counterDao.selectById(authority).getUid();
            userDao.insert(new UserPojo(uuid, uid, username, password, email, authority));
            return uuid;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    public String updateUser(UserPojo user) {
        try {
            userDao.updateById(user);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据UUID删除一条用户
     * 在得到UUID时，请注意你的UserPojo对象非空，否则会在函数外部产生NullPointerException
     */
    public String deleteUserByUUID(String uuid) {
        try {
            userDao.deleteById(uuid);
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据名称删除一条用户
     */
    public String deleteUserByName(String username) {
        UserPojo user = getUserByName(username);
        if(user != null) {
            try {
                userDao.deleteById(user.getUuid());
                return "OK";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return "ERROR";
            }
        } else {
            return "ERROR";
        }
    }

    /**
     * 根据UUID查询一条用户信息
     */
    @Nullable
    public UserPojo getUserByUUID(String uuid) {
        return userDao.selectById(uuid);
    }

    /**
     * 根据名字查询一条用户信息
     */
    @Nullable
    public UserPojo getUserByName(String username) {
        return userDao.selectOne(new QueryWrapper<UserPojo>().eq("username", username));
    }

    /**
     * 根据token查询一条用户信息
     */
    @Nullable
    public UserPojo getUserByToken(String token) {
        try {
            Claims claims = Tool.parseToken(token);
            String username = claims.getSubject();
            return getUserByName(username);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Nullable
    public UserPojo getUserByEamil(String email) {
        return userDao.selectOne(new QueryWrapper<UserPojo>().eq("email", email));
    }

    /**
     * 根据名字获取UUID
     */
    public String getUUIDByName(String name) {
        UserPojo pojo = userDao.selectOne(new QueryWrapper<UserPojo>().eq("username", name));
        return (pojo == null ? "" : pojo.getUuid());
    }

    @Nullable
    public UserPojo checkToken(String token) {
        try {
            Claims claims = Tool.parseToken(token);
            String username = claims.getSubject();
            String encodedPassword = claims.get("encodedPassword", String.class);
            if (username == null || encodedPassword == null) {
                return null;
            } else {
                UserPojo usr = userDao.selectOne(new QueryWrapper<UserPojo>().eq("username", username));
                return (usr == null ? null : usr.getPassword().equals(encodedPassword) ? usr : null);
            }
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 查询所有用户
     */
    public List<UserPojo> getAllUsers() {
        return userDao.selectList(new QueryWrapper<>());
    }

    public String updateEmail(String username, String email) {
        UserPojo user = getUserByName(username);
        if(user != null) {
            user.setEmail(email);
            return updateUser(user);
        } else {
            return "ERROR";
        }
    }
}
