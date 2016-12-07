package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;


public class IPR__1 {
	public static void main(String[] args) {
		/*Automatic automatic=Test_split_01_new.getAutomatic();
		Automatic newAutomatic=iPR(automatic);*/
	}
	/*
	 ������С���㷨����һ���µ�ʱ���Զ���
	 */
	public static Automatic iPR(Automatic automatic) {
		ArrayList<State> new_stateSet=Minimization__1.minimization(automatic);
		//System.out.println("��ֺ��״̬������ "+new_stateSet.size());
		/*for(State s:new_stateSet){
			System.out.println("״̬name: "+s.getName());
			System.out.println("״̬position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
			DBM_element[][] fDBM=Floyds.floyds(DBM);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=fDBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		
		ArrayList<Transition> transitions=BuildRelation__1.bulidRelation(automatic);
		/*System.out.println("�ߵ�������"+transitions.size());
		for(Transition t:transitions){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			if(t.getEventSet().size()!=0){
				System.out.println(t.getEventSet().get(0));
			}
			System.out.println(t.getTypeIds());
			System.out.println(t.getTypes());
			System.out.println("********");
		}*/
		
		Automatic newaotu=new Automatic();//����һ���µ�ʱ���Զ���
		State intiState=new State();
		for(State s:new_stateSet){//�ж��ĸ��ǳ�ʼ״̬
			int h=Minimization__1.includeZero(s.getInvariantDBM());
			if(s.getPosition().equals(automatic.getInitState().getPosition())&&h==1){
				intiState.setName(s.getName());
				intiState.setPosition(s.getPosition());
				intiState.setInvariantDBM(s.getInvariantDBM());
				//System.out.println(intiState.getName());
				//System.out.println(intiState.getPosition());
			}
		}
		newaotu.setClockSet(automatic.getClockSet());
		newaotu.setInitState(intiState);
		newaotu.setStateSet(new_stateSet);
		newaotu.setTransitionSet(transitions);
		newaotu.setName("G");
		 
		return newaotu;
	}
}
