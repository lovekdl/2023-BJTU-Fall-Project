package liqi.peerlearningsystembackend.controller;

import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.service.UserService;
import liqi.peerlearningsystembackend.utils.Result;
import liqi.peerlearningsystembackend.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;



@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password) {

        String encode_password = Tool.passwordEncoder(password);
        String message = userService.addUser(username, encode_password);

        if(message.equals("ERROR"))
            return Result.errorGetStringByMessage("403","register failed");
        else
            return Result.okGetStringByMessage("register success");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        String encode_password = Tool.passwordEncoder(password);
        UserPojo user = userService.getUserByName(username);
        if(user == null) {
            return Result.errorGetStringByMessage("404", "user not exist");
        } else if(!user.getPassword().equals(encode_password)) {
            return Result.errorGetStringByMessage("403", "password error");
        } else {
            String token = Tool.tokenEncoder(username, encode_password);
            return Result.okGetStringByData("login success",
                                            new HashMap<String, String>() {{
                                                put("token", token);
                                                }}
            );
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam("username") String username) {

        userService.deleteUser(username);
        return Result.okGetStringByMessage("delete success");
    }

}


