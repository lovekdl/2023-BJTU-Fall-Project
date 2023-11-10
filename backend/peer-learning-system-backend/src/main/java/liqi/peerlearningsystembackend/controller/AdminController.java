package liqi.peerlearningsystembackend.controller;

import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.service.UserService;
import liqi.peerlearningsystembackend.utils.Constants;
import liqi.peerlearningsystembackend.utils.Result;
import liqi.peerlearningsystembackend.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    /**
     * 管理员根据uid删除用户
     */
    @RequestMapping(value = "/deleteUserByUid", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@RequestBody Map<String, String> data) {

        // 获取数据
        String uid = data.get("uid");
        String token = data.get("token");
        if(uid == null || token == null)
            return Result.errorGetStringByMessage("400", "uid or token is null");

        // 检验用户是否是管理员
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != 1)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not admin");

        // 删除用户
        String message = userService.deleteUserByUid(Integer.parseInt(uid));
        if(message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403", "delete failed");
        else
            return Result.okGetString();
    }

    /**
     * 管理员添加用户
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseEntity<String> addByAdmin(@RequestBody Map<String, String> data) {

        // 获取数据
        String username = data.get("username");
        String password = data.get("password");
        String email = data.get("email");
        String authority = data.get("authority");
        String token = data.get("token");
        if(username == null || password == null || email == null || authority == null || token == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是管理员
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_ADMIN)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not admin");

        // 添加用户
        String encode_password = Tool.passwordEncoder(password);
        String message = userService.addUser(username, encode_password, email, Integer.parseInt(authority));
        if(message.startsWith("ERROR"))
            return Result.errorGetStringByMessage("403",message);
        else
            return Result.okGetString();
    }

    /**
     * 管理员查询某些用户
     */
    @RequestMapping(value = "/getUsers", method = RequestMethod.POST)
    public ResponseEntity<String> getByAdmin(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String value = data.get("value");
        if(token == null || value == null)
            return Result.errorGetStringByMessage("400", "token or value is null");

        // 检验用户是否是管理员
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_ADMIN)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not admin");

        // 查询用户
        List<UserPojo> users = userService.getUsersByValue(value);
        List<Object> usersInfo = new ArrayList<>();

        for (UserPojo usr : users) {
            HashMap<String, String> userInfo = new HashMap<>();
            userInfo.put("key", String.valueOf(usr.getUid()));
            userInfo.put("uid", String.valueOf(usr.getUid()));
            userInfo.put("email", usr.getEmail());
            userInfo.put("username", usr.getUsername());
            switch (usr.getAuthority()) {
                case Constants.AUTHORITY_ADMIN:
                    userInfo.put("authority", "管理员");
                    break;
                case Constants.AUTHORITY_TEACHER:
                    userInfo.put("authority", "老师");
                    break;
                case Constants.AUTHORITY_STUDENT:
                    userInfo.put("authority", "学生");
                    break;
            }

            usersInfo.add(userInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("users", usersInfo);
                }}
        );
    }

    /**
     * 管理员修改用户信息
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<String> updateByAdmin(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String uid = data.get("uid");
        String username = data.get("username");
        String email = data.get("email");
//        String authority = data.get("authority");
        if(token == null || uid == null || username == null || email == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验用户是否是管理员
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_ADMIN)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not admin");

        // 修改用户信息
        UserPojo usr = userService.getUserByUid(Integer.parseInt(uid));
        if(usr == null)
            return Result.errorGetStringByMessage("404", "user not exist");
        if(usr.getAuthority() == Constants.AUTHORITY_ADMIN)
            return Result.errorGetStringByMessage("403", "admin can not be modified");


        try {
            usr.setUsername(username);
            usr.setEmail(email);
            userService.updateUser(usr);
            return Result.okGetString();
        } catch (Exception e) {
            return Result.errorGetStringByMessage("403", "update failed");
        }
    }

    /**
     * 管理员查看所有用户
     */
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
    public ResponseEntity<String> getAllByAdmin(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        if(token == null)
            return Result.errorGetStringByMessage("400", "token is null");

        // 检验用户是否是管理员
        UserPojo user = userService.checkToken(token);
        if(user == null || user.getAuthority() != Constants.AUTHORITY_ADMIN)
            return Result.errorGetStringByMessage("403", "token is wrong or user is not admin");

        // 查询用户
        List<UserPojo> users = userService.getAllUsers();
        List<Object> usersInfo = new ArrayList<>();

        for (UserPojo usr : users) {
            HashMap<String, String> userInfo = new HashMap<>();
            userInfo.put("key", String.valueOf(usr.getUid()));
            userInfo.put("uid", String.valueOf(usr.getUid()));
            userInfo.put("email", usr.getEmail());
            userInfo.put("username", usr.getUsername());
            switch (usr.getAuthority()) {
                case Constants.AUTHORITY_ADMIN:
                    userInfo.put("authority", "管理员");
                    break;
                case Constants.AUTHORITY_TEACHER:
                    userInfo.put("authority", "老师");
                    break;
                case Constants.AUTHORITY_STUDENT:
                    userInfo.put("authority", "学生");
                    break;
            }

            usersInfo.add(userInfo);
        }

        return Result.okGetStringByData("success",
                new HashMap<String, Object>() {{
                    put("users", usersInfo);
                }}
        );
    }
}
