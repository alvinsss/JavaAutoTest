package com.alvin.constants;

import java.util.ArrayList;

public class ConstantsDemo {
	
	//常量=恒定不变 final修饰类不能被继承，修饰方法不能被重写 修饰变量变成常量
	// 常量只能赋值一次 基本类型不能改变 引用数据地址值不能改变 但是可以调用方法
	public static final String EXCEL_PATH="src/test/resources/cases_v3.xlsx";
	private  String a;
	final ArrayList list = new ArrayList();
	
	public static void main(String[] args) {
		int a = 10;
	}

}
