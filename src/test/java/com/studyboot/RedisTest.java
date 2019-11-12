package com.studyboot;

import com.studyboot.bean.Student;
import com.studyboot.common.redisUtils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void setRedis(){
        Student student = new Student();
        student.setAge(10);
        student.setEmail("123@qq.com");
        student.setName("小明");
        redisUtils.set("student",student);

    }

    @Test
    public void getRedis(){
        Student student = (Student)redisUtils.get("student");
        System.out.println("student = " + student);
        redisUtils.remove("student");
        Student studenta = (Student)redisUtils.get("student");
        System.out.println("student = " + studenta);
    }

}
