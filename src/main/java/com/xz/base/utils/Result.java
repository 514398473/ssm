/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.utils;

import java.io.Serializable;

/**
 * 返回结果类
 * <p>
 * 返回结果类
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年3月24日
 */

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;

	private Object data;

	private boolean success;

	/** 成功 */
	public static final Result SUCCESS = new Result(true);

	/** 失败 */
	public static final Result FAILURE = new Result(false);

	/**
	 * Result
	 */
	public Result() {
		super();
	}

	/**
	 * Result
	 * 
	 * @param message
	 * @param success
	 */
	public Result(boolean success, String message) {
		super();
		this.message = message;
		this.success = success;
	}

	/**
	 * Result
	 * 
	 * @param success
	 */
	public Result(boolean success) {
		super();
		this.success = success;
	}

	/**
	 * Result
	 * 
	 * @param message
	 * @param data
	 * @param success
	 */
	public Result(boolean success, String message, Object data) {
		super();
		this.message = message;
		this.data = data;
		this.success = success;
	}

	/**
	 * 创建一个成功的结果
	 * 
	 * @param data 返回的数据对象
	 * @return 新创建的成功结果
	 */
	public static Result createSuccess(Object data) {
		return new Result(true, null, data);
	}

	/**
	 * 创建一个失败结果
	 * 
	 * @param message 失败原因（错误消息）
	 * @return 新创建的失败结果
	 */
	public static Result createFailure(String message) {
		return new Result(false, message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}