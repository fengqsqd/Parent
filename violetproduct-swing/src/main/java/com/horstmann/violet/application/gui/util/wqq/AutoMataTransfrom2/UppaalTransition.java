package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class UppaalTransition {
	
	private ArrayList<String> Events;//�¼�
	private String name;//�������Ǩ������
	private String target;//ת��������Դ״̬����
	private String source;//ת�������Ŀ��״̬����
	private ArrayList<String> ResetClocks;//Ǩ���и�λ��ʱ���������
	private ArrayList<String> constraint;//Ǩ���ϵ�ʱ��Լ������
	//private ArrayList<String> com_constraint;//ʱ��Լ���Ĳ���
	//private ArrayList<String> SetClock;//Ǩ�������õ�ʱ��
	private ArrayList<String> types;
	private ArrayList<String> typeIds;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getTypes() {
		return types;
	}
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	public ArrayList<String> getTypeIds() {
		return typeIds;
	}
	public void setTypeIds(ArrayList<String> typeIds) {
		this.typeIds = typeIds;
	}
	/*public ArrayList<String> getCom_constraint() {
		return com_constraint;
	}
	public void setCom_constraint(ArrayList<String> com_constraint) {
		this.com_constraint = com_constraint;
	}
	public ArrayList<String> getSetClock() {
		return SetClock;
	}
	public void setSetClock(ArrayList<String> setClock) {
		SetClock = setClock;
	}*/
	public ArrayList<String> getEvents() {
		return Events;
	}
	public void setEvents(ArrayList<String> events) {
		Events = events;
	}
	public ArrayList<String> getResetClocks() {
		return ResetClocks;
	}
	public void setResetClocks(ArrayList<String> resetClocks) {
		ResetClocks = resetClocks;
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
	public ArrayList<String> getConstraint() {
		return constraint;
	}
	public void setConstraint(ArrayList<String> constraint) {
		this.constraint = constraint;
	}	 
}
