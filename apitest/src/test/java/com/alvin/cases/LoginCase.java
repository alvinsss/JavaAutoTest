package com.alvin.cases;

import java.util.Arrays;

import org.bouncycastle.asn1.cms.EnvelopedData;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONPath;
import com.alvin.pojo.API;
import com.alvin.pojo.Case;
import com.alvin.utils.EnvironmentUtils;
import com.alvin.utils.ExcelUtils;
import com.alvin.utils.HttpUtils;

public class LoginCase {
	
	public static void main(String[] args) {
		String json="{\"code\":0,\"msg\":\"OK\",\"data\":{\"id\":15290,\"leave_amount\":0.0,\"mobile_phone\":\"13221400113\",\"reg_name\":\"qatest\",\"reg_time\":\"2022-07-18 10:14:01.0\",\"type\":1,\"token_info\":{\"token_type\":\"Bearer\",\"expires_in\":\"2022-07-20 09:03:45\",\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJtZW1iZXJfaWQiOjE1MjkwLCJleHAiOjE2NTgyNzkwMjV9.7EQOwY_KrAs5JQq5WQvxibtj7JOR2PNEFJ4Y0qSAeMdMSPvwRIWOr-35fT_YNe07T4O0sOXnQm7fHzq_666NOQ\"}},\"copyright\":\"Copyright 柠檬班 © 2017-2019 湖南省零檬信息技术有限公司 All Rights Reserved\"}";
		Object read = JSONPath.read(json, "$.data.token_info.token");
		Object read2 = JSONPath.read(json, "$..token");
		System.out.println(read2);

	}
	
	/**
	 * 注册用例的测试方法
	 * @param url			接口请求地址
	 * @param method		接口请求方法
	 * @param params		接口请求参数
	 * @param contentType	接口类型
	 */
	@Test(dataProvider = "datas")
	public void test(API api,Case c) {
//		1、参数化替换
//		2、数据库前置查询结果(数据断言必须在接口执行前后都查询)
//		3、调用接口
		String body=HttpUtils.call(api.getUrl(), api.getMethod(), c.getParams(), api.getContentType());
		Object token = JSONPath.read(body, "$.data.token_info.token");

		// 防止只有member_id没有token值
		if (token != null) {
			EnvironmentUtils.env.put("${token}",token.toString());
			Object member_Id = JSONPath.read(body, "$.data.id");
			if (member_Id != null) {
				EnvironmentUtils.env.put("${member_id}",member_Id.toString());
			}
		}

//		4、断言响应结果
//		5、添加接口响应回写内容
//		6、数据库后置查询结果
//		7、据库断言
//		8、添加断言回写内容
//		9、添加日志
//		10、报表断言
	}

	
	@DataProvider
	public Object[][] datas() {
//		Object[][] datas = ExcelUtils.read();
//		return datas;
		Object[][] datas = ExcelUtils.getAPIAndCaseByApiId("2");
		System.out.println("LoginCase datas:");
		for (Object[] objects : datas) {
			System.out.println(Arrays.toString(objects));
		}
		return datas;
	}
	
	//所以代码最先执行 初始化静态数据做准备
	@BeforeSuite
	public void init() {
		ExcelUtils.apiList = ExcelUtils.readExcel(0,1,API.class);
		ExcelUtils.caseList = ExcelUtils.readExcel(1,1,Case.class);
 
	}
}
