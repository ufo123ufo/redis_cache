package com.youguu.cache.handle;

import com.youguu.cache.config.CacheConfig;

/**
 * Created by wangdong on 2016/1/13.
 */
public interface ICacheHandler {

    boolean saveData(CacheConfig config);

    Object getData(CacheConfig config);

    boolean delData(CacheConfig config);
}
