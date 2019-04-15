# springmvcweb
springmvc+mybatis

此代码运行环境：jdk1.8 + tomcat8 +  idea

springmvc +mybatis的纯净示例框架
其中内容有：<br/>
<p>-------------------------2019年4月13日----------------------------------</p>
<ul>
<li>连接池 启用 Web 监控统计功能 (阿里的连接池) 详见web.xml</li>
<li>配置文件由properties改为yml  (springboot貌似推荐yml? 所以在springmvc中来试试)</li>
<li>添加自定义注解（@OperateLog）的代码</li>
<li>添加了拦截器的示例</li>
<li>添加了切面的示例</li>
<li>加入事务(@Transactional)注解，事务放在Service层(如果不在Service层执行的第一条sql执行成功，而后续代码出现异常，此时sql不会发生回滚，加入此注解如果在后续中发生异常，则回回滚)</li>
</ul>
<br/>
<p>-------------------------2019年4月15日----------------------------------</p>
<p>增加了shiro权限验证，修改的内容有：</p>
  <ul>
    <li>修改了web.xml，添加shiro的映射</li>
    <li>增加了 cn.hp.realm.CustomRealm </li>
    <li>增加了shiro配置文件：spring-shiro.xml</li>
    <li>修改了IndexController，增加了登录的相关跳转</li>
  </ul>

<p>后续继续添加，会陆续加入一些功能 例如：webservice、利用模板自动生成</p>
