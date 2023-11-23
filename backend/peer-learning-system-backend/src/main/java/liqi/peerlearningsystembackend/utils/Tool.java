package liqi.peerlearningsystembackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import liqi.peerlearningsystembackend.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Tool {
    private static String ENCODER_SALT() {
        return "1926081719260817";
    }
    private static final String SECRET = "ThisIsALongerSecretKeyForJWTWithMoreThan256Bits";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 密码加密器
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String passwordEncoder(String password) {
        return DigestUtils.md5Hex(password + ENCODER_SALT());
    }

    /**
     * Token加密
     * @param value 该用户的邮箱
     * @param password 该用户的密码
     * @return token
     */
    public static String tokenEncoder(String value, String password) {
        String token = Jwts.builder()
                .setSubject(value) // 使用邮箱作为token的主题
                .claim("encodedPassword", password) // 添加编码后的密码作为声明
                .signWith(SECRET_KEY) // 使用指定的密钥签名token
                .compact();

        return token;
    }

    /**
     * Token解密
     * @param token token
     * @return claims
     */
    public static Claims parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims;

        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature.");
            throw e;
        } catch (Exception e) {
            System.out.println("Invalid JWT token.");
            throw e;
        }
    }

    /**
     * 生成随机验证码
     * @return 验证码
     */
    static public String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for(int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    /**
     * 检查邮箱格式
     * @param email 邮箱
     * @return "OK"或错误信息
     */
    static public String checkEmailFormat(String email) {
        if(email == null || email.isEmpty()) {
            return "email can not be empty";
        }
        if(!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            return "email format error";
        }
        return "OK";
    }

    /**
     * 互评分配算法
     * @param students 学生列表
     * @param m 每个学生需要评价的人数
     * @return 分配结果
     */
    static public Map<Integer, List<Integer>> allocation(List<Integer> students, Integer m) {

        Map<Integer, List<Integer>> ret = new HashMap<>();
        for (Integer student : students) {
            ret.put(student, new ArrayList<>());
        }

        // 随机打乱
        Collections.shuffle(students);

        // 分配
        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < m; j++) {
                ret.get(students.get(i)).add(students.get((i + j + 1) % students.size()));
            }
        }

        return ret;
    }

}
