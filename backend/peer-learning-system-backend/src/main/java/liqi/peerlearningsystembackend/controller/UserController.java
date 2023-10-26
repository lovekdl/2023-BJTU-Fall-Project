package liqi.peerlearningsystembackend.controller;

import io.jsonwebtoken.Claims;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.service.UserService;
import liqi.peerlearningsystembackend.utils.MailUtils;
import liqi.peerlearningsystembackend.utils.Result;
import liqi.peerlearningsystembackend.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/usr")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MailUtils mailUtils;

    @Resource
    RedisTemplate<String, Object> redisTemplate;


    /**
     * 验证邮箱并注册学生用户
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody Map<String, String> data) {

        // 获取数据
        String username = data.get("username");
        String password = data.get("password");
        String email = data.get("email");
        String code = data.get("code");
        String authority = data.get("authority");
        if(username == null || password == null || email == null || code == null || authority == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验验证码
        String codeInRedis = (String) redisTemplate.opsForValue().get(email);
        if(codeInRedis == null) {
            return Result.errorGetStringByMessage("403", "code is expired");
        } else if(!codeInRedis.equals(code)) {
            return Result.errorGetStringByMessage("403", "code is wrong");
        }

        // 添加用户
        String encode_password = Tool.passwordEncoder(password);
        String message = userService.addUser(username, encode_password, email, Integer.parseInt(authority));
        if(message.equals("ERROR"))
            return Result.errorGetStringByMessage("403","register failed");
        else
            return Result.okGetString();
    }


    /**
     * 注册发送验证码
     */
    @RequestMapping(value = "/register/sendCode", method = RequestMethod.POST)
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> data) {

        // 获取数据
        String email = data.get("email");
        if(email == null)
            return Result.errorGetStringByMessage("400", "email is null");

        // 查找是否存在用户使用该邮箱
        UserPojo usr = userService.getUserByEamil(email);
        if(usr != null)
            return Result.errorGetStringByMessage("403", "email has been used");

        // 发送验证码
        String code = Tool.generateCode();
        boolean sendMail = mailUtils.sendMail(email, "您的验证码为：" + code + "，请在5分钟内使用。", "互评平台验证码");
        if(sendMail) {
            // 将验证码存入redis, 并设置过期时间为5分钟
            long expire = 5 * 60;
            redisTemplate.opsForValue().set(email, code);
            redisTemplate.expire(email, expire, java.util.concurrent.TimeUnit.SECONDS);
            return Result.okGetString();
        } else {
            return Result.errorGetStringByMessage("403", "send email failed");
        }
    }


    /**
     * 登录用户
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String, String> data) {

        // 获取数据
        String username = data.get("username");
        String password = data.get("password");
        if(username == null || password == null)
            return Result.errorGetStringByMessage("400", "username or password is null");

        // 检验用户
        String encode_password = Tool.passwordEncoder(password);
        UserPojo user = userService.getUserByName(username);
        if(user == null) {
            return Result.errorGetStringByMessage("404", "user not exist");
        } else if(!user.getPassword().equals(encode_password)) {
            return Result.errorGetStringByMessage("403", "password error");
        } else {
            // 生成token
            String token = Tool.tokenEncoder(username, encode_password);
            return Result.okGetStringByData("success",
                                            new HashMap<String, String>() {{
                                                put("token", token);
                                                }}
            );
        }
    }

    /**
     * 查看用户信息
     */
    @RequestMapping(value = "/getInfoByToken", method = RequestMethod.POST)
    public ResponseEntity<String> getInfo(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        if(token == null)
            return Result.errorGetStringByMessage("400", "token is null");

        // 检验用户
        Claims claims = Tool.parseToken(token);
        String username = claims.getSubject();
        UserPojo user = userService.getUserByName(username);
        if(user == null) {
            return Result.errorGetStringByMessage("404", "user not exist");
        } else {
            return Result.okGetStringByData("success",
                    new HashMap<String, String>() {{
                        put("uid", String.valueOf(user.getUid()));
                        put("username", user.getUsername());
                        put("email", user.getEmail());
                        put("authority", String.valueOf(user.getAuthority()));
                    }}
            );
        }
    }

    /**
     * 修改用户密码
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> data) {

        // 获取数据
        String token = data.get("token");
        String oldPassword = data.get("oldPassword");
        String newPassword = data.get("newPassword");
        if(token == null || oldPassword == null || newPassword == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 校验token
        UserPojo user = userService.checkToken(token);

        // 校验用户信息
        if(user != null) {
            // 修改密码
            String encode_password = Tool.passwordEncoder(newPassword);
            user.setPassword(encode_password);
            userService.updateUser(user);
            // 生成token
            String newToken = Tool.tokenEncoder(user.getUsername(), encode_password);
            return Result.okGetStringByData("success",
                    new HashMap<String, String>() {{
                        put("token", newToken);
                    }}
            );
        } else {
            return Result.errorGetStringByMessage("403", "token is wrong");
        }
    }

    /**
     * 找回密码
     */
    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public ResponseEntity<String> findPassword(@RequestBody Map<String, String> data) {

        // 获取数据
        String email = data.get("email");
        String newPassword = data.get("newPassword");
        String code = data.get("code");
        if(email == null || newPassword == null || code == null)
            return Result.errorGetStringByMessage("400", "something is null");

        // 检验验证码
        String codeInRedis = (String) redisTemplate.opsForValue().get(email);
        if(codeInRedis == null) {
            return Result.errorGetStringByMessage("403", "code is expired");
        } else if(!codeInRedis.equals(code)) {
            return Result.errorGetStringByMessage("403", "code is wrong");
        }

        // 修改密码
        UserPojo user = userService.getUserByEamil(email);
        if(user == null) {
            return Result.errorGetStringByMessage("404", "user not exist");
        } else {
            String encode_password = Tool.passwordEncoder(newPassword);
            user.setPassword(encode_password);
            userService.updateUser(user);
            return Result.okGetString();
        }
    }

    /**
     * 找回密码时发送验证码
     */
    @RequestMapping(value = "/findPassword/sendCode", method = RequestMethod.POST)
    public ResponseEntity<String> sendEmailForFindPassword(@RequestBody Map<String, String> data) {

        // 获取数据
        String email = data.get("email");
        if(email == null)
            return Result.errorGetStringByMessage("400", "email is null");


        String code = Tool.generateCode();
        boolean sendMail = mailUtils.sendMail(email, "您正在找回账户密码，您的验证码为：" + code + "，请在5分钟内使用。", "互评平台验证码");
        if(sendMail) {
            // 将验证码存入redis, 并设置过期时间为5分钟
            long expire = 5 * 60;
            redisTemplate.opsForValue().set(email, code);
            redisTemplate.expire(email, expire, java.util.concurrent.TimeUnit.SECONDS);
            return Result.okGetString();
        } else {
            return Result.errorGetStringByMessage("403", "send email failed");
        }
    }

    /**
     * 根据用户名删除用户
     */
    @RequestMapping(value = "/deleteByName", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@RequestBody Map<String, String> data) {

        // 获取数据
        String username = data.get("username");
        if(username == null)
            return Result.errorGetStringByMessage("400", "username is null");

        // 删除用户
        String message = userService.deleteUserByName(username);
        if(message.equals("ERROR"))
            return Result.errorGetStringByMessage("403", "delete failed");
        else
            return Result.okGetString();
    }


}


