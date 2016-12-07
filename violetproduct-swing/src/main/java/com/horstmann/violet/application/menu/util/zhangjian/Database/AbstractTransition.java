package com.horstmann.violet.application.menu.util.zhangjian.Database;

import java.io.Serializable;
/**
 * ������������е���Ϣ�����ݽṹ
 * @author ZhangJian
 *
 */
public class AbstractTransition  implements Serializable{
	private int tid;
	private String targetID;
	private String target;
	private String sourceID;
	private String source;
	private String constraintDBM;
	private String type;//�����Ϣ������һ���͵����Ƭ��
	private String resetClockSet;//��ע��Ϣ�ϵ�ʱ�Ӹ�λ����
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResetClockSet() {
		return resetClockSet;
	}
	public void setResetClockSet(String resetClockSet) {
		this.resetClockSet = resetClockSet;
	}
	public String getTargetID() {
		return targetID;
	}
	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}
	public String getSourceID() {
		return sourceID;
	}
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getConstraintDBM() {
		return constraintDBM;
	}
	public void setConstraintDBM(String constraintDBM) {
		this.constraintDBM = constraintDBM;
	}
	
}
