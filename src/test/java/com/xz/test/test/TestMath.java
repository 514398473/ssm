/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.test.test;

/**
 * 测试Math类
 * <p>
 * 测试Math类
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年2月8日
 */

public class TestMath {

	public static void main(String[] args) {
		// 绝对值
		System.out.println(Math.abs(-7.5));
		// 向上取值
		System.out.println(Math.ceil(-7.5));
		// 向下取值
		System.out.println(Math.floor(5.6));
		// 两者比较去较大的值
		System.out.println(Math.max(1, 3));
		// 两者比较取较小的值
		System.out.println(Math.min(2, 5));
		// 取得一个大于或者等于0.0小于不等于1.0的随机数
		System.out.println(Math.random());
		// 开平方
		System.out.println(Math.sqrt(9));
		// 求次幂
		System.out.println(Math.pow(3, 3));
		// 四舍五入，返回double值 注意.5的时候会取偶数
		System.out.println(Math.rint(6.5));
		// 四舍五入，float时返回int值，double时返回long值
		System.out.println(Math.round(5.7));
	}
}
