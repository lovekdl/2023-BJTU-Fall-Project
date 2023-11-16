package liqi.peerlearningsystembackend;

import io.jsonwebtoken.Claims;
import liqi.peerlearningsystembackend.pojo.HomeworkPojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.service.HomeworkService;
import liqi.peerlearningsystembackend.service.UserService;
import liqi.peerlearningsystembackend.utils.MailUtils;
import liqi.peerlearningsystembackend.utils.Result;
import liqi.peerlearningsystembackend.utils.Tool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PeerLearningSystemBackendApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    MailUtils mailUtils;

    @Autowired
    HomeworkService homeworkService;

    @Test
    void sendMail() {
        mailUtils.sendMail("21301034@bjtu.edu.cn", "test", "test");
    }

    @Test
    void generateCode() {
        System.out.println(Tool.generateCode());
    }

    @Test
    void contextLoads() {
        System.out.println("test");
        String username = "admin";
        String password = "admin";
        String authority = "1";
        String email = "1832271620@qq.com";
        String encode_password = Tool.passwordEncoder(password);
        userService.addUser(username, encode_password, email, Integer.parseInt(authority));
    }

    @Test
    void setEmail() {
        String username = "drj";
        String email = "lalalal";
        UserPojo usr = userService.getUserByName(username);
        System.out.println(usr);
        System.out.println(usr.getEmail());
        usr.setEmail(email);
        userService.updateUser(usr);
    }

    @Test
    void encodeToken() {
        String username = "drj";
        String encode_password = "6f4f7a1cdc4be188677cbaa98880b005";
        System.out.println(Tool.tokenEncoder(username, encode_password));
    }

    @Test
    void decodeToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkcmoiLCJlbmNvZGVkUGFzc3dvcmQiOiI2ZjRmN2ExY2RjNGJlMTg4Njc3Y2JhYTk4ODgwYjAwNSJ9.gNLId_SxhJQZV6qbKqNbSwc7cDs1gRHH20oyBx9bGVM";
        Claims claims = Tool.parseToken(token);
        String username = claims.getSubject();
        String encodedPassword = claims.get("encodedPassword", String.class);

        System.out.println("Username: " + username);
        System.out.println("Encoded Password: " + encodedPassword);
    }

    @Test
    void getArgumentHomeworks() {
        List<HomeworkPojo> homeworks = homeworkService.getHomeworksHaveArgument();
        if (homeworks == null)
            System.out.println("don't have any argument homework");

        for (HomeworkPojo homework : homeworks) {
            System.out.println(homework.getArgument());
        }

    }


}
