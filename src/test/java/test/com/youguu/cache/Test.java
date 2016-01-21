package test.com.youguu.cache;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import test.com.youguu.cache.dao.ITestProductDAO;

import java.text.MessageFormat;

/**
 * Created by wangdong on 2016/1/12.
 */
public class Test {

	ITestProductDAO service;

    @Before
    public void setup() {
        ApplicationContext context = new AnnotationConfigApplicationContext(InitLoader.class);
        service = context.getBean(ITestProductDAO.class);
    }


    @org.junit.Test
    public void get(){
        service.get();

        long stime = System.currentTimeMillis();
        for (int i=0;i<1;i++){
            System.out.println(service.get().getName());
        }

        System.out.println("times:" + (System.currentTimeMillis()-stime));
    }

    @org.junit.Test
    public void getType(){
        service.get(2);

        long stime = System.currentTimeMillis();
        for (int i=0;i<1;i++){
            service.get(1);
        }

        System.out.println("times:" + (System.currentTimeMillis()-stime));
    }

    @org.junit.Test
    public void getId(){
        System.out.println(service.getId());
    }

    @org.junit.Test
    public void getInteger(){
        System.out.println(service.getInteger());
    }

    @org.junit.Test
    public void getFloat(){
        System.out.println(service.getFloat());
    }

    @org.junit.Test
    public void getFFloat(){
        System.out.println(service.getFFloat());
    }

    @org.junit.Test
    public void getDouble(){
        System.out.println(service.getDouble());
    }

    @org.junit.Test
    public void getDDouble(){
        System.out.println(service.getDDouble());
    }


    @org.junit.Test
    public void getName(){
        System.out.println(service.getName(1, "哈"));
    }


    @org.junit.Test
    public void findList(){
        System.out.println(service.findList().get(0).getName());
    }


    @org.junit.Test
    public void findMap(){
        String b = service.findMap().get("a");
        System.out.println(b);
    }

    @org.junit.Test
    public void update(){
        service.update();
    }


    @org.junit.Test
    public void keyTest(){
        String key = "cache:user:aa:{0}";
        Object[] objs = new Object[2];
        objs[0] = 1;
        objs[1] = "2111";
        long stime = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
//            String value = MessageFormat.format(key, objs);
            key.replace("{0}","1");
        }
        System.out.println("times" + (System.currentTimeMillis() - stime));
    }
}
