package com.alvin.cases;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.alibaba.fastjson.JSONPath;
import com.alvin.constants.Constants;
import com.alvin.pojo.API;
import com.alvin.pojo.Case;
import com.alvin.pojo.WriteBackData;
import com.alvin.utils.EnvironmentUtils;
import com.alvin.utils.ExcelUtils;

public class BaseCase {
	
	
	/**设置默认请求头
	 * @param headers  
	 * 
	 */
	public   void setDefaultHeaders(Map<String, String> headers ) {
 		headers.put("X-Lemonban-Media-Type", Constants.MEDIA_TYPE);
		headers.put("Content-Type", Constants.CONTENT_TYPE);
	}
	
	/**
	 * 设置变量到环境变量中
	 * @param body
	 * @param jsonPath
	 * @param envKey
	 */
	public void setVariableInEnv(String body,String jsonPath,String envKey) {
		Object token = JSONPath.read(body, jsonPath);
		// 防止只有member_id没有token值
 		if (token != null) {
			EnvironmentUtils.env.put(envKey,token.toString());
		}
	}
	
	/***
	 * 从环境变量获取token设置到Headers中
	 * @param headers
	 */
	public void getTokenToHeader(Map<String, String> headers) {
		String token = EnvironmentUtils.env.get("${token}");
		if (StringUtils.isNotBlank(token)) {
			//Bearer 后有空格 规则要求
			headers.put("Authorization", "Bearer "+token);
		}
	}
	
	/**
	 * 添加wbd回写对象到wbdList集合中。
	 * @param sheetIndex		回写sheetIndex
	 * @param rowNum			回写行号
	 * @param cellNum			回写列号
	 * @param content			回写内容
	 */
	public void writeResponBackData(int sheetIndex,int rowNum,int cellNum, String content) {
		WriteBackData wbd = new WriteBackData(sheetIndex, rowNum, cellNum, content);
		ExcelUtils.wbdList.add(wbd);
	}
	
	
	/**
	 * 响应断言
	 * @param expect
	 * @param body
	 * @return
	 */
	public String responseAssert(String expect, String body) {
			//按照@@切割期望断言
			String[] expectArray = expect.split("@@");
			//定义返回值
			String reponseAssertFlag = "断言成功";
			//循环期望值切割之后的数组
			for (String expectValue : expectArray) {
//				如果响应体包含期望值 断言为成功,flag为true
				boolean flag = body.contains(expectValue) ;
//				如果不包含期望值 判断断言失败flag为false
				if ( flag == false) {
					System.out.println("期望值"+expectValue +"不在相同体里");
					break;
				}
			}
			System.out.println("断言响应结果："+reponseAssertFlag);
			return reponseAssertFlag;
		}

	
	//所以代码最先执行 初始化静态数据做准备,testng的注解属性特性
	@BeforeSuite
	public void init() {
		System.out.println("=======================项目初始化============================");
		ExcelUtils.apiList = ExcelUtils.readExcel(0, 1, API.class);
		ExcelUtils.caseList = ExcelUtils.readExcel(1, 1, Case.class);
	}
	
	@AfterSuite
	public void finish() {		
		System.out.println("=======================项目结束============================");
		ExcelUtils.batchWrite();
	}
}