/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.annotation.listener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Set;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import com.xz.annotation.field.GenerateGetAndSet;
import com.xz.annotation.utils.ScanPackages;
import com.xz.base.utils.PropertiesUtil;

/**
 * 项目启动扫描自定义注解
 * <p>
 * 项目启动扫描自定义注解
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年4月7日
 */
public class StartupListener {

	/**
	 * 扫描注解并动态添加方法
	 */
	public void initAnnotation() {
		try {
			boolean flag = false;
			String packages = PropertiesUtil.getValue("properties/annotation.properties", "scan_path");
			Set<String> findPackageAnnotationField = ScanPackages.findPackageAnnotationField(packages, GenerateGetAndSet.class);
			for (String className : findPackageAnnotationField) {
				Class<?> forName = Class.forName(className);
				Field[] declaredFields = forName.getDeclaredFields();
				ClassPool pool = ClassPool.getDefault();
				ClassClassPath classPath = new ClassClassPath(this.getClass());
				pool.insertClassPath(classPath);
				CtClass cc = pool.get(className);// 获取已有的类
				for (Field field : declaredFields) {
					if (null != field.getAnnotation(GenerateGetAndSet.class)) {
						Method[] methods = forName.getDeclaredMethods();
						if (methods.length == 0) {
							flag = true;
							String name = field.getName();
							name = name.substring(0, 1).toUpperCase() + name.substring(1);
							String type = field.getType().getSimpleName();
							CtMethod set = CtNewMethod.make("public void set" + name + "(" + type + " " + field.getName() + "){this." + field.getName() + " = "
									+ field.getName() + ";}", cc);
							CtMethod get = CtNewMethod.make("public " + type + " get" + name + "(){return " + field.getName() + ";}", cc);
							cc.addMethod(set);
							cc.addMethod(get);
						} else {
							flag = false;
						}
					}
				}
				if (flag) {
					URL resource = this.getClass().getResource("/");
					cc.writeFile(resource.getFile());
				}
				// byte[] classFile = cc.toBytecode();
				// HotSwapper hs = new HotSwapper(9000);// 8000是一个端口号。
				// hs.reload("com.xz.user.model.User", classFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
