package com.youguu.cache.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdong on 2016/1/13.
 */
public class ParserConfig {


    public static Object StringToObj(String json,CacheConfig config){

        if(json!=null && !"".equals(json)){
            MethodSignature retVal = (MethodSignature)config.getPoint().getSignature();

            Method m = retVal.getMethod();

            Class clazz = (Class)m.getReturnType();
            if(clazz == List.class){
                Type return_type = m.getGenericReturnType();
                //泛型
                if(return_type instanceof ParameterizedType){
                    Type t = ((ParameterizedType) return_type).getActualTypeArguments()[0];
                    return JSON.parseArray(json,(Class)t);
                }
            }else if(clazz == Map.class){
                return JSON.parseObject(json);
            }else{
                return JSON.parseObject(json, clazz);
            }
        }
        return null;
    }

}
