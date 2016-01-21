package test.com.youguu.cache;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by wangdong on 2016/1/12.
 */
@Configuration("testConfig")
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan("test.com.youguu.*")
public class InitLoader {
}
