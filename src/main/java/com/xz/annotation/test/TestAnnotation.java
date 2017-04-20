/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.annotation.test;

import java.lang.reflect.Field;
import java.util.Set;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import org.junit.Test;

import com.xz.annotation.field.GenerateGetAndSet;
import com.xz.annotation.utils.ScanPackages;
import com.xz.base.test.BaseTest;

/**
 * 测试注解
 * <p>
 * 测试注解
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年4月6日
 */

public class TestAnnotation extends BaseTest{

	/**
	 * 测试注解
	 * 
	 * @param args
	 * @throws Exception
	 */
	@Test
	public void AnnotationTest() throws Exception {
		String packages = "com.xz.*.model";
		Set<String> findPackageAnnotationField = ScanPackages.findPackageAnnotationField(packages, GenerateGetAndSet.class);
		for (String className : findPackageAnnotationField) {
			Class<?> forName = Class.forName(className);
			Field[] declaredFields = forName.getDeclaredFields();
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.get(className);// 获取已有的类
			for (Field field : declaredFields) {
				if (null != field.getAnnotation(GenerateGetAndSet.class)) {
					String name = field.getName();
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					String type = field.getType().getSimpleName();
					CtMethod set = CtNewMethod.make(
							"public void set" + name + "(" + type + " " + field.getName() + "){this." + field.getName() + " = " + field.getName() + ";}", cc);
					CtMethod get = CtNewMethod.make("public " + type + " get" + name + "(){return " + field.getName() + ";}", cc);
					cc.addMethod(set);
					cc.addMethod(get);
				}
			}
			cc.writeFile();
			logger.info("测试注解写入方法成功!");
		}
	}
}
