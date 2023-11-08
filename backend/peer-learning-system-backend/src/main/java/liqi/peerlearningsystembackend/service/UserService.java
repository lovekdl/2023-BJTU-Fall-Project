package liqi.peerlearningsystembackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import liqi.peerlearningsystembackend.dao.CounterDao;
import liqi.peerlearningsystembackend.dao.UserDao;
import liqi.peerlearningsystembackend.pojo.CounterPojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.utils.Tool;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
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
            // 插入用户
            Integer uid = counterDao.selectById(authority).getUid();
            userDao.insert(new UserPojo(uuid, uid, username, password, email, authority));

            // 更新计数器
            CounterPojo counterPojo = counterDao.selectById(authority);
            counterPojo.setUid(counterPojo.getUid() + 1);
            counterDao.updateById(counterPojo);

            return uuid;
        }  catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause.getMessage().contains("Duplicate entry") && cause.getMessage().contains("for key 'user.email'")) {
                return "ERROR: duplicate email";
            }
            if (cause.getMessage().contains("Duplicate entry") && cause.getMessage().contains("for key 'user.username'")) {
                return "ERROR: duplicate username";
            }
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
        if (user == null)
            return "ERROR";

        // 删除用户
        try {
            userDao.deleteById(user.getUuid());
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * 根据uid删除一条用户
     */
    public String deleteUserByUid(Integer uid) {
        UserPojo user = getUserByUid(uid);
        if (user == null)
            return "ERROR";

        // 删除用户
        try {
            userDao.deleteById(user.getUuid());
            return "OK";
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
     * 根据uid查询一条用户信息
     */
    public UserPojo getUserByUid(Integer uid) {
        return userDao.selectOne(new QueryWrapper<UserPojo>().eq("uid", uid));
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
            String encodedPassword = claims.get("encodedPassword", String.class);
            UserPojo usr = getUserByName(username);
            return (usr == null ? null : usr.getPassword().equals(encodedPassword) ? usr : null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 根据邮箱查询一条用户信息
     */
    @Nullable
    public UserPojo getUserByEmail(String email) {
        return userDao.selectOne(new QueryWrapper<UserPojo>().eq("email", email));
    }

    /**
     * 根据名字获取UUID
     */
    public String getUUIDByName(String name) {
        UserPojo pojo = userDao.selectOne(new QueryWrapper<UserPojo>().eq("username", name));
        return (pojo == null ? "" : pojo.getUuid());
    }


    /**
     * 查询所有用户
     */
    public List<UserPojo> getAllUsers() {
        return userDao.selectList(new QueryWrapper<>());
    }

    /**
     * 根据某一字段查询用户
     */
    public List<UserPojo> getUsersByValue(Object value) {
        QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();

        // 假设我们有一个字段列表，我们想要在这些字段中搜索值
        List<String> fieldsToSearch = Arrays.asList("username", "email", "uid", "authority");

        for (String field : fieldsToSearch) {
            queryWrapper.or(wrapper -> wrapper.eq(field, value));
        }

        return userDao.selectList(queryWrapper);
    }

    /**
     * 根据Token返回用户
     */
    @Nullable
    public UserPojo checkToken(String token) {
        try {
            Claims claims = Tool.parseToken(token);
            String username = claims.getSubject();
            String encodedPassword = claims.get("encodedPassword", String.class);
            if (username == null || encodedPassword == null)
                return null;
            UserPojo usr = getUserByName(username);
            return (usr == null ? null : usr.getPassword().equals(encodedPassword) ? usr : null);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 更新邮箱
     */
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
