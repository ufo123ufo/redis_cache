package com.youguu.cache.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * Created by wangdong on 2016/1/13.
 */
public class CacheConfig {
    /**
     * 缓存的key值，不必须 ， 默认使用cache:包名.类名:方法名 作为key
     */
    private String key;

    /**
     * 缓存的字段（hmap结构）
     */
    private String field;

    /**
     * 过期时间 ， 格式 HH24:mi ,优先于expire
     */
    private String expireAt;

    /**
     * 过期时间 ， 缓存多少毫秒之后过期 ， 如果设置了expireAt，此属性将失效
     */
    private int expire;

    /**
     *当前方法属于查询还是 增删改 。 默认是查询 ， 增删改请使用：CacheAnnotation.OP_DEL
     */
    private String op;
    private Object Data;
    private ProceedingJoinPoint point;
    /**
     * 使用redis缓存的实例名
     */
    private String poolName;

    public CacheConfig(CacheAnnotation cache , ProceedingJoinPoint point){
        this.point = point;
        this.expireAt = cache.expireAt();
        this.expire = cache.expire();
        this.op = cache.op();
        this.poolName = cache.poolName();

        StringBuilder sb = new StringBuilder("cache");
        sb.append(":").append(point.getTarget().getClass().getName());
        if(cache.op().equals(CacheAnnotation.OP_SEL)){
            MethodSignature retVal = (MethodSignature)point.getSignature();
            Method m = retVal.getMethod();
            sb.append(":").append(m.getName());
            if(!"".equals(cache.key())){
                sb.append(":").append(cache.key());
            }
            if(CacheAnnotation.FIELD_AUTO.equals(cache.field())){
                StringBuilder field_sb = new StringBuilder();
                Object[] objs = this.point.getArgs();
                if(objs!=null && objs.length>0){
                    for(int i=0;i<objs.length;i++){
                        field_sb.append(objs[i].toString());
                        if(i!=objs.length-1){
                            field_sb.append(":");
                        }
                    }
                    this.field = field_sb.toString();
                }
            }else if(!"".equals(cache.field())){
                this.field = MessageFormat.format(cache.field(),this.point.getArgs());
            }
            this.key = sb.toString();
        }else{
            this.key = sb.append(":").append(cache.key()).toString();
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public ProceedingJoinPoint getPoint() {
        return point;
    }

    public void setPoint(ProceedingJoinPoint point) {
        this.point = point;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
