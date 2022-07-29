
package com.alvin.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.alvin.businessflow.CreateProjectFlow;
import com.alvin.businessflow.LoginFlow;
import com.alvin.pageobject.IndexPage;
import com.alvin.testdata.Constant;
import com.alvin.testdata.LoginDatas;
import com.alvin.testdemo.OpenBrowser;
import com.alvin.util.BrowserUtil;

/**
* @Title: CreateProjectTest
* @Description: 
* @author: alvin
* @date 2022年7月29日 上午11:30:35
*/
public class CreateProjectTest {
	
	@Parameters({"browerName"})
	@BeforeTest
	public void setUp(String browerName) {
		BrowserUtil.OpenBrowser(browerName);
		BrowserUtil.driver.get(Constant.LOGIN_URL);
		BrowserUtil.driver.manage().window().maximize();
	}
	
	@Test
	public void createProjectTest() throws Exception {
		
		//登录
		Thread.sleep(1000);
		System.out.println("createProjectTest 登录");
		LoginFlow loginFlow = new LoginFlow(LoginDatas.CORRECT_ACCOUNT,LoginDatas.CORRECT_PASSWORD);
		loginFlow.login();
		Thread.sleep(1000);
		
		//点击创建项目按钮
		System.out.println("createProjectTest 点击创建项目按钮");
		IndexPage indexPage = new IndexPage();
		indexPage.buttonProject();
		Thread.sleep(1000);

 
		CreateProjectFlow createProject = new CreateProjectFlow("Web自动化");
		//点击创建项目链接
		createProject.click_projectCreateLink();
		//输入项目数据
		createProject.input_projectData();
		System.out.println("createProjectTest模块维护");
		createProject.input_moduleUpdataProject();
		System.out.println("createProjectTest 编辑项目");
		createProject.input_editProject();
		System.out.println(BrowserUtil.driver.getCurrentUrl());
		Thread.sleep(2000);
		String get_inputCreateProjectName = createProject.get_inputCreateProjectName();
		String get_currentProjectNameText = createProject.get_autoTestcurrentProjectNameText();
		
		System.out.println("get_currentProjectNameText:"+get_currentProjectNameText);
 		System.out.println("get_inputCreateProjectName:"+get_inputCreateProjectName);
		Assert.assertEquals(get_inputCreateProjectName, get_currentProjectNameText);
	}
	
	@AfterTest
	public void tearDown() {
		System.out.println("LoginTest AfterTest");
		BrowserUtil.driver.quit();
	}
	
}