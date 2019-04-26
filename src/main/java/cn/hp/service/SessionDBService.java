package cn.hp.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SessionDBService {
    int insert(Map<String, Object> params);
    int update(Map<String, Object> params);
    int delete(String sessionid);
    Map<String,Object> get(Map<String, Object> params);
    List<Map<String,Object>> list();
    Integer login(String username,String password);
}
