package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

public class State {
	private int id;
	private String name;//����
	private DBM_element[][] invariantDBM ;//״̬�е�ʱ�Ӳ���ʽ����
	private DBM_element[][] addClockRelationDBM ;//������ʱ�ӹ�ϵ�Ĳ���ʽ����
	private String position;//�ж�����״̬λ���Ƿ���ͬ�ı�־λ
	private String type;//���ڿ�����ӵ�״̬�������ԣ��ǲ��ǳ�ʼ״̬��
	boolean finalState;//��־״̬�Ƿ�Ϊ��ֹ״̬
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public DBM_element[][] getAddClockRelationDBM() {
		return addClockRelationDBM;
	}
	public void setAddClockRelationDBM(DBM_element[][] addClockRelationDBM) {
		this.addClockRelationDBM = addClockRelationDBM;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DBM_element[][] getInvariantDBM() {
		return invariantDBM;
	}
	public void setInvariantDBM(DBM_element[][] invariantDBM) {
		this.invariantDBM = invariantDBM;
	}
	public boolean isFinalState() {
		return finalState;
	}
	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}
	
	
	
}
