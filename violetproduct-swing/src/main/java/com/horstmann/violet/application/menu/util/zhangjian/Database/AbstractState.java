package com.horstmann.violet.application.menu.util.zhangjian.Database;

import java.io.Serializable;
/**
 * ���������������е�״̬�ڵ�����ݽṹ
 * @author ZhangJian
 *
 */
public class AbstractState implements Serializable {
	private int sid;
	private String sname;
	private String invariantDBM;
	private String position;
	private String type;//��ע״̬���ͣ���ʼ�ڵ���߽����ڵ�
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getInvariantDBM() {
		return invariantDBM;
	}
	public void setInvariantDBM(String invariantDBM) {
		this.invariantDBM = invariantDBM;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	 
}
