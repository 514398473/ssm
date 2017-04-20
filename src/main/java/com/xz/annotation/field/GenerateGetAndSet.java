/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.annotation.field;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动生成get和set方法的注解
 * <p>
 * 自动生成get和set方法的注解
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年4月5日
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GenerateGetAndSet {

	/**
	 * 字段名称
	 * 
	 * @return
	 */
	public String name() default "";

	/**
	 * 字段描述
	 * 
	 * @return
	 */
	public String description() default "";

}
