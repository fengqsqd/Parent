package com.horstmann.violet.application.gui.util.wujun.SequenceTransfrom;

import java.util.ArrayList;

public class UppaalTemplate 
{
	
	String declaration;
	String Name;//�Զ�������
	ArrayList<UppaalLocation> locations=new ArrayList<UppaalLocation>();//״̬����
	ArrayList<UppaalTransition>transitions=new ArrayList<UppaalTransition>();//Ǩ�Ƽ���
	
	public String getName() 
	{
		return Name;
	}
	public void setName(String name) 
	{
		Name = name;
	}
	public ArrayList<UppaalLocation> getLocations() 
	{
		return locations;
	}
	public void setLocations(ArrayList<UppaalLocation> locations) 
	{
		this.locations = locations;
	}
	public ArrayList<UppaalTransition> getTransitions() 
	{
		return transitions;
	}
	public void setTransitions(ArrayList<UppaalTransition> transitions) 
	{
		this.transitions = transitions;
	}
}
