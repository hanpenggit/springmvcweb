package cn.hp.service.impl;

import cn.hp.dao.SessionDBDao;
import cn.hp.service.SessionDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class SessionDBServiceImpl implements SessionDBService {
    @Autowired
    SessionDBDao sessionDao;
    @Override
    @Transactional
    public int insert(Map<String, Object> params) {
        return sessionDao.insert(params);
    }

    @Override
    @Transactional
    public int update(Map<String, Object> params) {
        return sessionDao.update(params);
    }

    @Override
    @Transactional
    public int delete(String sessionid) {
        return sessionDao.delete(sessionid);
    }

    @Override
    @Transactional
    public Map<String,Object> get(Map<String, Object> params) {
        return sessionDao.get(params);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> list() {
        return sessionDao.list();
    }

    @Override
    @Transactional
    public Integer login(String username, String password) {
        return sessionDao.login(username,password);
    }
}
