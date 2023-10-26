package liqi.peerlearningsystembackend.utils;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Result {

    /**
     * 生成一个状态码为200的Http请求的Response
     * @return 字符串形式的Response
     */
    public static ResponseEntity<String> okGetString(){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "success");
        String s = JSONObject.toJSONString(map);
        return ResponseEntity.status(200).body(s);
    }

    /**
     * 生成一个状态码为200的Http请求的Response
     * @param message 信息内容
     * @return 字符串形式的Response
     */
    public static ResponseEntity<String> okGetStringByMessage(String message){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", message);
        String s = JSONObject.toJSONString(map);
        return ResponseEntity.status(200).body(s);
    }

    /**
     * 生成一个状态码为200的Http请求的Response
     * @param message 信息内容
     * @param data 数据
     * @return 字符串形式的Response
     */
    public static ResponseEntity<String> okGetStringByData(String message, Object data){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", message);
        map.put("data", data);
        String s = JSONObject.toJSONString(map);
        return ResponseEntity.status(200).body(s);
    }

    /**
     * 生成一个Http请求的Response
     * @param code 状态码
     * @return 字符串形式的Response
     */
    public static ResponseEntity<String> errorGetString(String code){
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", "error");
        String s = JSONObject.toJSONString(map);
        return ResponseEntity.status(Integer.parseInt(code)).body(s);
    }

    /**
     * 生成一个Http请求的Response
     * @param code 状态码
     * @param message 信息内容
     * @return 字符串形式的Response
     */
    public static ResponseEntity<String> errorGetStringByMessage(String code, String message){
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        String s = JSONObject.toJSONString(map);
        return ResponseEntity.status(Integer.parseInt(code)).body(s);
    }

    /**
     * 生成一个Http请求的Response
     * @param code 状态码
     * @param message 信息内容
     * @param data 数据
     * @return 字符串形式的Response
     */
    public static ResponseEntity<String> errorGetStringByData(String code, String message, Object data){
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        String s = JSONObject.toJSONString(map);
        return ResponseEntity.status(Integer.parseInt(code)).body(s);
    }

}
