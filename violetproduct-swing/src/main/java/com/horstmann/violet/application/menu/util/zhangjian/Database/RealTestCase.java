package com.horstmann.violet.application.menu.util.zhangjian.Database;

import java.io.Serializable;
/**
 *ʵ�����������������ݽṹ
 * @author Administrator
 *
 */
public class RealTestCase implements Serializable {
	private int id ;
	private String name;
	private String realTestRouter;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealTestRouter() {
		return realTestRouter;
	}
	public void setRealTestRouter(String realTestRouter) {
		this.realTestRouter = realTestRouter;
	}
	 
}
