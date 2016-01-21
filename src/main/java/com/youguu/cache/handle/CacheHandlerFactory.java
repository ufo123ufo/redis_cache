package com.youguu.cache.handle;

import com.youguu.cache.handle.redis.RedisCacheHandler;

/**
 * Created by wangdong on 2016/1/13.
 */
public class CacheHandlerFactory {
    public static ICacheHandler getICacheHandler(){
        return  RedisCacheHandler.getInstance();
    }
}
