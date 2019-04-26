package cn.hp.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SessionDBDao {
    int insert(Map<String, Object> params);
    int update(Map<String, Object> params);
    int delete(String id);
    Map<String,Object> get(Map<String, Object> params);
    List<Map<String,Object>> list();
    Integer login(@Param("username") String username,@Param("password") String password);
}
