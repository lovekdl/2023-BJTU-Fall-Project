package liqi.peerlearningsystembackend.utils;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Result {

    public static String okGetString(){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "success");
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String okGetStringByMessage(String message){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", message);
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String okGetStringByData(String message, Object data){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", message);
        map.put("data", data);
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String errorGetString(String code){
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", "error");
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String errorGetStringByMessage(String code, String message){
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String errorGetStringByData(String code, String message, Object data){
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        String s = JSONObject.toJSONString(map);
        return s;
    }

}
