package cn.hp.dao;

import java.util.List;
import java.util.Map;

public interface TestDao {
    List<Map<String,Object>> list();
    int insert(Map<String,Object> params);
    int update(Map<String,Object> params);
    int delete(String id);
}
