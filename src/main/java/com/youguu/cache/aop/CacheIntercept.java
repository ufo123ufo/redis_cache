package com.youguu.cache.aop;

import com.alibaba.fastjson.JSON;
import com.youguu.cache.config.CacheAnnotation;
import com.youguu.cache.config.CacheConfig;
import com.youguu.cache.handle.CacheHandlerFactory;
import com.youguu.cache.handle.ICacheHandler;
import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
import org.aspectj.apache.bcel.classfile.ConstantString;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;

/**
 * Created by wangdong on 2016/1/12.
 */

public abstract class CacheIntercept {
    Log log = LogFactory.getLog(CacheIntercept.class);

    //need pointcut
    public abstract void pointcut();

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        CacheConfig config = this.getConfig(point);

        //handler get
        if(config!=null && config.getOp().equals(CacheAnnotation.OP_SEL)){
            ICacheHandler handler = CacheHandlerFactory.getICacheHandler();
            Object result = handler.getData(config);
            if(result != null){
                log.debug("from cache");
                return result;
            }
        }

        Object result = point.proceed();

        //handler save or del
        if(config!=null){
            ICacheHandler handler = CacheHandlerFactory.getICacheHandler();
            if(config.getOp().equals(CacheAnnotation.OP_SEL)){//reload data
                config.setData(result);
                handler.saveData(config);
            }else if(config.getOp().equals(CacheAnnotation.OP_DEL)){//del data
                handler.delData(config);
            }
        }

        return result;
    }

    private CacheConfig getConfig(ProceedingJoinPoint point){
        CacheConfig config = null;
        try {
            MethodSignature retVal = (MethodSignature)point.getSignature();

            Method m = retVal.getMethod();

            if(m!=null) {
                if(m.isAnnotationPresent(CacheAnnotation.class)){
                    CacheAnnotation cache = m.getAnnotation(CacheAnnotation.class);
                    config = new CacheConfig(cache,point);
                }
            }
        }catch (Exception e){
            log.error(e);
        }
        return config;

    }
}
