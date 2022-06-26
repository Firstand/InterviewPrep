package com.yonyou.controller;

import com.yonyou.entity.UserVo;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyu
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    private String hello() {
        return "你好，我是服务9092";
    }

    @PostMapping("hello/addUser")
    private UserVo addUser(@RequestParam("cUserId") String cUserId,
                           @RequestParam("userPassword") String userPassword,
                           @RequestParam("userName") String userName,
                           @RequestParam("userCode") String userCode) {
        UserVo userVo = new UserVo();
        userVo.setcUserId(cUserId);
        userVo.setUserPassword(userPassword);
        userVo.setUserName(userName);
        userVo.setUserCode(userCode);
        userVo.setUserNode("你好，我是服务9092。现在执行新增操作！");
        System.out.println(userVo);
        return userVo;
    }

    @PostMapping("hello/deleteUser")
    private UserVo deleteUser(@RequestParam("cUserId") String cUserId,
                           @RequestParam("userPassword") String userPassword,
                           @RequestParam("userName") String userName,
                           @RequestParam("userCode") String userCode) {
        UserVo userVo = new UserVo();
        userVo.setcUserId(cUserId);
        userVo.setUserPassword(userPassword);
        userVo.setUserName(userName);
        userVo.setUserCode(userCode);
        userVo.setUserNode("你好，我是服务9092。现在执行删除操作！");
        System.out.println(userVo);
        return userVo;
    }

    @PutMapping("hello/updateUser")
    public void updateUser(@RequestParam("cUserId") String cUserId,
                           @RequestParam("userPassword") String userPassword,
                           @RequestParam("userName") String userName,
                           @RequestParam("userCode") String userCode) {
        UserVo userVo = new UserVo();
        userVo.setcUserId(cUserId);
        userVo.setUserPassword(userPassword);
        userVo.setUserName(userName);
        userVo.setUserCode(userCode);
        userVo.setUserNode("你好，我是服务9092。现在执行删除操作！");
        System.out.println(userVo);
    }

    @GetMapping("hello/queryUser")
    private UserVo queryUser(@RequestParam("cUserId") String cUserId,
                              @RequestParam("userPassword") String userPassword,
                              @RequestParam("userName") String userName,
                              @RequestParam("userCode") String userCode) {
        UserVo userVo = new UserVo();
        userVo.setcUserId(cUserId);
        userVo.setUserPassword(userPassword);
        userVo.setUserName(userName);
        userVo.setUserCode(userCode);
        userVo.setUserNode("你好，我是服务9092。现在执行查询操作！");
        System.out.println(userVo);
        return userVo;
    }


    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                sum+=2;
                System.out.print(i + "_" + j +"("+sum+") ");
            }
            System.out.println();
        }
    }
}
