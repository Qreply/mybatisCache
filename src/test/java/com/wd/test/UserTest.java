package com.wd.test;

import com.wd.entity.User;
import com.wd.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class UserTest {

    private InputStream in;
    private SqlSession session;
    SqlSessionFactory factory;
    private UserMapper userMapper;



    @Before //用于在测试方法执行之前执行
    public void init() throws IOException {
        //1.读取配置文件,生成读取字节流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        session = factory.openSession();
        //4.使用SqlSession创建Mapper代理的接口对象
        userMapper = session.getMapper(UserMapper.class);
    }

    /**
     * 释放资源
     * @throws IOException
     */
    @After //用于在测试方法执行之后执行
    public void destroy() throws IOException {
        //提交事务
        // session.commit();
        session.close();
        in.close();
    }

    /**
     * 测试一级缓存
     */
    @Test
    public void testFirstLevelCache(){
        User user1 = userMapper.findById(41);
        System.out.println(user1);

//        session.close();
//
//        //再次获取SqlSession对象
//        session = factory.openSession();

        //此方法也可以清空缓存
        session.clearCache();

        userMapper = session.getMapper(UserMapper.class);

        User user2 = userMapper.findById(41);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }


    /**
     * 测试缓存的同步
     */
    @Test
    public void testClearCache(){
        //1.根据id查询用户
        User user1 = userMapper.findById(41);
        System.out.println(user1);

        //2.更新用户信息
        user1.setUsername("update user clear cahce");
        user1.setAddress("beijing");
        userMapper.updateUser(user1);

        //3.再次查询id为41
        User user2 = userMapper.findById(41);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

}
