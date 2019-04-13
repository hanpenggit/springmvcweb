package cn.hp.service.impl;

import cn.hp.annotations.OperateLog;
import cn.hp.dao.TestDao;
import cn.hp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    TestDao testDao;

    /**
     * OperateLog为自定义注解
     * Transactional注解，为手动加入事务注解，在此方法内如果出现异常，则回滚事务
     * @return
     */
    @Override
    @OperateLog(value="查询test列表")
    @Transactional
    public List<Map<String, Object>> list() {
        return testDao.list();
    }

    /**
     * OperateLog为自定义注解
     * Transactional注解，为手动加入事务注解，在此方法内如果出现异常，
     * @return
     */
    @Override
    @OperateLog(value="增加test")
    @Transactional
    public int insert(Map<String, Object> params) {
        int result=testDao.insert(params);
        if(result>0){
            System.out.println(result);
        }
        int j=10/0;
        return result;
    }

    /**
     * OperateLog为自定义注解
     * Transactional注解，为手动加入事务注解，在此方法内如果出现异常，则回滚事务
     * 比如此方法中update执行成功之后，后面除以0，抛出异常，此时，更新回滚
     * @return
     */
    @Override
    @OperateLog(value="更新test")
    @Transactional
    public int update(Map<String, Object> params) {
        int result=testDao.update(params);
        if(result>0){
            System.out.println(result);
        }
        int j=10/0;
        return result;
    }

    @OperateLog(value="删除test")
    @Override
    @Transactional
    public int delete(String id) {
        return testDao.delete(id);
    }
}
