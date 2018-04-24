package com.user.signin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.user.signin.config.GetConfig;
import com.user.signin.response.LoginResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;


/**
 * @Author yyhu3
 * @Date 2018-04-24 14:53
 */


@RestController
public class LoginController {
    Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    GetConfig getConfig;
    @GetMapping("/login")
    public String login(String userName,String passWord)
    {
        PropertyConfigurator.configure("config/log4j.properties");
        LoginResponse loginResponse = new LoginResponse();
        logger.info("获取的配置mysql驱动:"+getConfig.getDriverClassName());
        logger.info("获取的mysql的url:"+getConfig.getUrl());
        logger.info("获取到的username:"+getConfig.getUsername());
        logger.info("获取到的password:"+getConfig.getPassword());
        if (userName==""||passWord=="")
        {
            loginResponse.setCode(400);
            loginResponse.setMsg("用户名或者密码为空");
            JSON.toJSON(loginResponse);
        }
        else {
            try {
                Connection mysqlConnection = DriverManager.getConnection(getConfig.getUrl(), getConfig.getUsername(), getConfig.getPassword());
                logger.info("数据库连接成功");
                Statement statement = mysqlConnection.createStatement();
                ResultSet querySet = statement.executeQuery("select PassWord from TEST_YYHU3_USERINFO where UserName='"+userName+"';");
                String realPassWord="";
                while (querySet.next()){
                    realPassWord = querySet.getString("PassWord");
                }
                logger.info("查询结果为:"+realPassWord);
                if (realPassWord.equals(""))
                {
                    loginResponse.setCode(400);
                    loginResponse.setMsg("用户不存在");
                }
                else if (realPassWord.equals(passWord))
                {
                    loginResponse.setCode(200);
                    loginResponse.setMsg("登录成功");
                }
            } catch (SQLException e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                pw.flush();
                sw.flush();
                logger.error("数据库连接异常" + sw.toString());
            }
        }
        logger.info("返回包为:"+JSON.toJSONString(loginResponse));
        return JSON.toJSONString(loginResponse);
    }
}
