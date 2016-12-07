package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class UppaalTemPlate {

	private String name;//����
	private UppaalLocation InitState;//��ʼ״̬
	private ArrayList<UppaalTransition> transitions;//״̬����
	private ArrayList<UppaalLocation> locations;//ת������
	private ArrayList<String> Clocks;//ʱ�Ӽ���
	
	public UppaalLocation getInitState() {
		return InitState;
	}
	public void setInitState(UppaalLocation initState) {
		InitState = initState;
	}
	public ArrayList<String> getClocks() {
		return Clocks;
	}
	public void setClockSet(ArrayList<String> clocks) {
		Clocks = clocks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<UppaalTransition> getTransitions() {
		return transitions;
	}
	public void setTransitions(ArrayList<UppaalTransition> transitions) {
		this.transitions = transitions;
	}
	public ArrayList<UppaalLocation> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<UppaalLocation> locations) {
		this.locations = locations;
	}
	
}
