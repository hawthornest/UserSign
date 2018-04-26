package com.user.signin;

import com.user.signin.controller.LoginController;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SigninApplicationTests {
	Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private MockMvc mvc;

	@BeforeClass
	public static void initConfig()
	{
		PropertyConfigurator.configure("config/log4j.properties");
	}

	@Test
	public void contextLoads() {
		String testCase = "登录:密码为空";
		try {
			String responseString = mvc.perform(MockMvcRequestBuilders.get("/login?userName=test&passWord=")).andReturn().getResponse().getContentAsString(); ;
			logger.info(testCase+"的返回包为:"+responseString);
			org.junit.Assert.assertEquals(responseString,"200");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			logger.error("异常报错:"+sw.toString());
		}
	}
}
