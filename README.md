# springmvcweb
spriingmvc+mybatis

此代码运行环境：jdk1.8 + tomcat8 +  idea

springmvc +mybatis的纯净示例框架
其中内容有：

1、添加自定义注解（@OperateLog）的代码

2、添加了拦截器的示例

3、添加了切面的示例

4、加入事务(@Transactional)注解，事务放在Service层(如果不在Service层执行的第一条sql执行成功，而后续代码出现异常，此时sql不会发生回滚，加入此注解如果在后续中发生异常，则回回滚)


后续继续添加，会陆续加入一些功能
例如：shiro、webservice、利用模板自动生成
