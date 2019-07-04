
package com.demo.exception;

/**
 * 业务层异常类
 *
 * @author 43291
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final String message;
	private final Object data;

	public BusinessException(String message) {
		this.message = message;
		this.data = null;
	}

	public BusinessException(String message, Object data) {
		this.message = message;
		this.data = data;
	}

	public BusinessException(String message, Throwable throwable) {
		super(throwable);
		this.message = message;
		this.data = null;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}
}
