package com.horstmann.violet.application.menu.util.zhangjian.Database;

import java.io.Serializable;
/**
 * ʵ��������������Process�����ݽṹ
 * @author ZhangJian
 *
 */
public class RealProcess implements Serializable {
	private int pid ;
	private String operation;
	private String inputInfo;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getInputInfo() {
		return inputInfo;
	}
	public void setInputInfo(String inputInfo) {
		this.inputInfo = inputInfo;
	}
	
	
}
