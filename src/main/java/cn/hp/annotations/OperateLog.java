package cn.hp.annotations;

import java.lang.annotation.*;

@Documented //文档生成时，该注解将被包含在javadoc中，可去掉
@Target(ElementType.METHOD)//目标是方法
@Retention(RetentionPolicy.RUNTIME) //注解会在class中存在，运行时可通过反射获取
@Inherited
public @interface OperateLog {
    /**
     * 是否写操作日志，默认为true，写日志[示例]
     * @return
     */
    boolean write() default true;

    /**
     * 需要记录的内容
     * @return
     */
    String value();
}
