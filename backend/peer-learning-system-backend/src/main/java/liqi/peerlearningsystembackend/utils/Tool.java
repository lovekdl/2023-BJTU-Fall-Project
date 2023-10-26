package liqi.peerlearningsystembackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.Base64;

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
     * @param username 该用户的用户名
     * @param password 该用户的密码
     * @return token
     */
    public static String tokenEncoder(String username, String password) {
        String token = Jwts.builder()
                .setSubject(username) // 使用用户名作为token的主题
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
}
