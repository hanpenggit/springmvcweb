package cn.hp.service;

import java.util.List;
import java.util.Map;

public interface TestService {
    List<Map<String,Object>> list();
    int insert(Map<String,Object> params);
    int update(Map<String,Object> params);
    int delete(String id);
}
