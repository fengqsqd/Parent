package com.horstmann.violet.application.gui.util.chenzuo.Bean;

/**
 * ��������ִ��ʱ�׳��쳣
 */
public class TestCaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestCaseException(String message) {
		super("TestCaseException"+message);
	}

	public TestCaseException(String message, Throwable cause) {
		super("TestCaseException"+message, cause);
	}

}
