package liqi.peerlearningsystembackend;

import io.jsonwebtoken.Claims;
import liqi.peerlearningsystembackend.service.UserService;
import liqi.peerlearningsystembackend.utils.Tool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PeerLearningSystemBackendApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        System.out.println("test");
        userService.addUser("liqi", "123456");
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


}
