package liqi.peerlearningsystembackend;

import io.jsonwebtoken.Claims;
import liqi.peerlearningsystembackend.pojo.HomeworkPojo;
import liqi.peerlearningsystembackend.pojo.UserPojo;
import liqi.peerlearningsystembackend.service.HomeworkService;
import liqi.peerlearningsystembackend.service.UserService;
import liqi.peerlearningsystembackend.utils.MailUtils;
import liqi.peerlearningsystembackend.utils.Result;
import liqi.peerlearningsystembackend.utils.Tool;
import liqi.peerlearningsystembackend.utils.SimHash2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.init.ResourceReader;

import java.awt.desktop.SystemEventListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Test
    void allocationTest() {
        List<Integer> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(i);
        }
        Map<Integer, List<Integer>> result = Tool.allocation(students, 3);
        for (Map.Entry<Integer, List<Integer>> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

    @Test
    void compareString() {
//        String a = "我草泥马";
//        String b = "我草你妈";
//        System.out.println(Tool.calcSimilarityWithJaro(a, b));
//        System.out.println(Tool.calcSimilarityWithJaroWinkler(a, b));
//        System.out.println(Tool.calcSimilarityWithCosine(a, b));
//        System.out.println(Tool.calcSimilarityWithLevenshtein(a, b));
        //从resource中读取lcqmc文件夹下的test.tsv文件
        // 从resource中读取lcqmc文件夹下的test.tsv文件
        try {
            // 使用Spring的ClassPathResource定位资源文件
            ClassPathResource resource = new ClassPathResource("/data/lcqmc/train.tsv");
            int Jaro = 0;
            int JaroWinkler = 0;
            int Cosine = 0;
            int Levenshtein = 0;
            int SimHash = 0;

            // 使用InputStreamReader和BufferedReader读取文件
            try (InputStreamReader isr = new InputStreamReader(resource.getInputStream());
                 BufferedReader reader = new BufferedReader(isr)) {

                String line;
                SimHash2 simHash2 = new SimHash2();
                while ((line = reader.readLine()) != null) {
                    // 在这里处理每行数据
                    String[] fields = line.split("\t");
                    String a = fields[0];
                    String b = fields[1];
                    String label = fields[2];
                    double jaro = Tool.calcSimilarityWithJaro(a, b);
                    double jaroWinkler = Tool.calcSimilarityWithJaroWinkler(a, b);
                    double cosine = Tool.calcSimilarityWithCosine(a, b);
                    double levenshtein = Tool.calcSimilarityWithLevenshtein(a, b);
                    double simHash = simHash2.getSemblance(a, b);
                    if (jaro > 0.8 && label.equals("1")) {
                        Jaro++;
                    }
                    if (jaroWinkler > 0.8 && label.equals("1")) {
                        JaroWinkler++;
                    }
                    if (cosine > 0.8 && label.equals("1")) {
                        Cosine++;
                    }
                    if (levenshtein > 0.8 && label.equals("1")) {
                        Levenshtein++;
                    }
                    if (simHash > 0.8 && label.equals("1")) {
                        SimHash++;
                    }
                }
                System.out.println("Jaro: " + Jaro);
                System.out.println("JaroWinkler: " + JaroWinkler);
                System.out.println("Cosine: " + Cosine);
                System.out.println("Levenshtein: " + Levenshtein);
                System.out.println("SimHash: " + SimHash);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
