package com.youguu.cache.handle.redis;

import com.alibaba.fastjson.JSON;
import com.youguu.cache.config.CacheConfig;
import com.youguu.cache.config.ParserConfig;
import com.youguu.cache.handle.ICacheHandler;
import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
import com.youguu.core.util.ParamUtil;
import com.youguu.core.util.RedisUtil;
import com.youguu.core.util.redis.RedisPool;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.xml.ws.Response;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wangdong on 2016/1/13.
 */

public class RedisCacheHandler implements ICacheHandler{
    Log log = LogFactory.getLog(RedisCacheHandler.class);
    private  static  RedisCacheHandler handler = new RedisCacheHandler();

    public static RedisCacheHandler getInstance() {
        return handler;
    }

    /**
     *
     */
    private RedisCacheHandler(){};


    @Override
    public boolean saveData(CacheConfig config) {
        if(config.getData() == null){
            return true;
        }else {
            String json = JSON.toJSONString(config.getData());
            RedisPool pool = RedisUtil.getRedisPool(config.getPoolName());
            Jedis jedis = null;
            Pipeline p = null;
            try{
                jedis = pool.getJedis();
                p = jedis.pipelined();

                //hmap
                if (config.getField() != null && !"".equals(config.getField())) {
                    p.hset(config.getKey(), config.getField(), json);
                } else {
                    p.set(config.getKey(), json);
                }

                if(config.getExpireAt()!=null && !"".equals(config.getExpireAt())){
                    int sec = getTimes(config.getExpireAt());
                    p.expire(config.getKey(), sec);
                }else{
                    p.expire(config.getKey(), config.getExpire());
                }

                p.sync();
                pool.releaseJedisInstance(jedis);
            }catch(Exception e){
                pool.destoryJedisResource(jedis);
                log.error(e);
            }

        }
        return true;
    }

    @Override
    public Object getData(CacheConfig config) {

        RedisPool pool = RedisUtil.getRedisPool(config.getPoolName());

        String result = null;

        if (config.getField() != null && !"".equals(config.getField())) {
            result = pool.hget(config.getKey(), config.getField());
        }else{
            result = pool.get(config.getKey());
        }
        return ParserConfig.StringToObj(result,config);

    }

    @Override
    public boolean delData(CacheConfig config) {

        RedisPool pool = RedisUtil.getRedisPool(config.getPoolName());

        return pool.del(config.getKey()) > 0 ? true:false;
    }

    /**
     * 返回距离现在的毫秒值
     * @param expireAt
     * @return
     */
    private int getTimes(String expireAt){
        int value = 0;

        String[] time_split = expireAt.split(":");
        int hour = ParamUtil.CheckParam(time_split[0],0);
        int minute = ParamUtil.CheckParam(time_split[1],0);

        Calendar c = Calendar.getInstance();
        long now_time = c.getTimeInMillis();
        int now_hour = c.get(Calendar.HOUR_OF_DAY);
        int now_minute = c.get(Calendar.MINUTE);
        if(now_hour > hour || (now_hour==hour && now_minute>=minute)){//超过今天
            c.add(Calendar.DAY_OF_MONTH,1);

        }
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);
        return (int)((c.getTimeInMillis()-now_time)/1000);
    }

}
