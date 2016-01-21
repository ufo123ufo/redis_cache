package com.youguu.cache.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangdong on 2016/1/12.
 * 缓存标签的标注类
 * 使用例子：查询方法
 * @CacheAnnotation(key="",expire=120000)
 *
 * 增删改方法
 * @CacheAnnotation(key="",op=Constants.OP_DEL)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CacheAnnotation {
    /**
     * 查询的方法
     */
    public static final String OP_SEL = "select";

    /**
     * 增删改的方法
     */
    public static final String OP_DEL = "delete";

    /**
     * field auto 表示使用当前所有参数自动组织
     */
    public static final String FIELD_AUTO = "auto";

    /**
     * 缓存使用的key , 如果不设置 ， 默认使用 cache:类名:方法名 作为key
     * 若没有cache开头 会自动补齐cache
     * @return
     */
    String key() default "";

    /**
     * 支持 hmap的结构
     * @return
     */
    String field() default "";

    //标注方法的实际作用 ， 默认是查询
    String op() default CacheAnnotation.OP_SEL;

    //指定时间过期格式 HH24:mi expireAt优先于expire
    String expireAt() default "";

    //过期时间 默认为2天（只支持毫秒的计时）
    int expire() default 172800;

    //redis连接池使用的名称
    String poolName();
}
