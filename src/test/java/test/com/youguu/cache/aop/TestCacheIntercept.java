package test.com.youguu.cache.aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.youguu.cache.aop.CacheIntercept;

/**
 * Created by wangdong on 2016/1/12.
 */
@Aspect
@Component("TestCacheIntercept")
public class TestCacheIntercept extends  CacheIntercept {
	
	@Pointcut("execution(* test.com.youguu.cache.dao..*.*(..))")
    @Override
    public void pointcut() {}
}
