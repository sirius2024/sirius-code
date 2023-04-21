package com.sirius.springboot.controller;

import com.sirius.springboot.domain.Jvm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 1、第一次
 * -Xms1536M -Xmx1536M -Xmn512M -Xss256K -XX:SurvivorRatio=6 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly
 * 2、第二次
 * -Xms1536M -Xmx1536M -Xmn1024M -Xss256K -XX:SurvivorRatio=6  -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+UseParNewGC  -XX:+UseConcMarkSweepGC  -XX:CMSInitiatingOccupancyFraction=92 -XX:+UseCMSInitiatingOccupancyOnly
 */
@RestController
@RequestMapping("/jvm")
public class JvmController {

    @RequestMapping("/process")
    public String processUserData() throws InterruptedException {
        ArrayList<Jvm> users = queryUsers();

        for (Jvm user : users) {
            //TODO 业务处理
            System.out.println("user:" + user.toString());
        }
        return "end";
    }

    /**
     * 模拟批量查询用户场景
     *
     * @return
     */
    private ArrayList<Jvm> queryUsers() {
        ArrayList<Jvm> users = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            users.add(new Jvm(i, "zhuge"));
        }
        return users;
    }
}
