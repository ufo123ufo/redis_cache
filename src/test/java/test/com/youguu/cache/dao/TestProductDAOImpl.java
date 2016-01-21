package test.com.youguu.cache.dao;

import com.youguu.cache.config.CacheAnnotation;
import org.springframework.stereotype.Service;

import sun.misc.Cache;
import test.com.youguu.cache.pojo.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdong on 2016/1/12.
 */
@Service("ITestProductDAO")
public class TestProductDAOImpl implements ITestProductDAO{

    @CacheAnnotation(poolName = "user" , expireAt = "14:20")
    @Override
    public Product get() {
        return Product.get();
    }

    @CacheAnnotation(key = "1",field = CacheAnnotation.FIELD_AUTO , op = CacheAnnotation.OP_SEL , expireAt = "17:00" , poolName = "user")
	@Override
    public Product get(int type){
        return Product.get();
    }

    @CacheAnnotation(poolName = "user")
    @Override
    public int getId() {
        return 123;
    }

    @CacheAnnotation(poolName = "user")
    @Override
    public Integer getInteger() {
        return 12345;
    }

    @CacheAnnotation(poolName = "user")
    @Override
    public float getFloat() {
        return 12.13F;
    }

    @CacheAnnotation(poolName = "user")
    @Override
    public Float getFFloat() {
        return 12.111123F;
    }

    @CacheAnnotation(poolName = "user")
    @Override
    public double getDouble() {
        return 12.123455;
    }

    @CacheAnnotation(poolName = "user")
    @Override
    public Double getDDouble() {
        return 12.123455565;
    }


    @CacheAnnotation(key="{0}",poolName = "user")
    @Override
    public String getName(int uid, String accountId) {
        return "王东";
    }

    @CacheAnnotation(poolName = "user")
    @Override
    public List<Product> findList() {
        List<Product> list = new ArrayList<>();
        list.add(Product.get());
        return list;
    }

    @CacheAnnotation(poolName = "user")
    @Override
    public Map<String, String> findMap() {
        Map<String,String> map = new HashMap<>();
        map.put("a","b");
        return map;
    }

    @CacheAnnotation(poolName = "user" , op = CacheAnnotation.OP_DEL, key="get")
    @Override
    public void update() {

    }
}
