package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class UppaalLocation {
	private String name;//����
	private ArrayList<String> invariant ;//״̬�е�ʱ�Ӳ���ʽ����
	//private ArrayList<String> com_invariant ;//ʱ�Ӳ���ʽ�Ĳ���
	boolean finalState;//��־״̬�Ƿ�Ϊ��ֹ״̬
	
	/*public ArrayList<String> getCom_invariant() {
		return com_invariant;
	}
	public void setCom_invariant(ArrayList<String> com_invariant) {
		this.com_invariant = com_invariant;
	}*/
	public boolean isFinalState() {
		return finalState;
	}
	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}
	public ArrayList<String> getInvariant() {
		return invariant;
	}
	public void setInvariant(ArrayList<String> invariant) {
		this.invariant = invariant;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
