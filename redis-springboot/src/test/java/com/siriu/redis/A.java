package com.siriu.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirius.redis.pojo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class A {

    @Autowired
    private StringRedisTemplate redisTemplate;
    // JSON工具
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testOne() {
        redisTemplate.opsForValue().set("name", "卷心菜");
    }
    @Test
    void testTwo() throws IOException {
        Person person = new Person("我是一棵卷心菜", 21);
        //  手动序列化
        String json = mapper.writeValueAsString(person);
        redisTemplate.opsForValue().set("person", json);
        String personJson = redisTemplate.opsForValue().get("person");
        // 反序列化
        Person person1 = mapper.readValue(personJson, Person.class);
        System.out.println(person1);
    }
}
