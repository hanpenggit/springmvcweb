package cn.hp.service.impl;

import cn.hp.dao.TestDao;
import cn.hp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestDao testDao;
    @Override
    public List<Map<String, Object>> list() {
        return testDao.list();
    }

    @Override
    public int insert(Map<String, Object> params) {
        return testDao.insert(params);
    }

    @Override
    public int update(Map<String, Object> params) {
        return testDao.update(params);
    }

    @Override
    public int delete(String id) {
        return testDao.delete(id);
    }
}
