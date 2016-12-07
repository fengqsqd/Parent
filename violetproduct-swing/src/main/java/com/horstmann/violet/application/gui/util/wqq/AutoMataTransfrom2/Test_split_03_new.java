package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
/**
 * ��֤l00��û�б���ȷ���
 * @author Seryna
 *
 */
public class Test_split_03_new {
	public static void main(String[] args) {
		ArrayList<State> newStates=newState();
		ArrayList<State> getPs=getPs();
	}
	
	
	/**
	 * ���l00������l00����ֺ��״̬��
	 * @return
	 */
	public static ArrayList<State> newState(){
		Automatic automatic=Test_split_01_new.getAutomatic();
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<String> ClockSet=automatic.getClockSet();
		
		ArrayList<State> StateSet=Test_split_02_new.getPs();//�����״̬������l0����ֺ���µ�״̬����
		/*for(State s:StateSet){
			System.out.println("״̬name: "+s.getName());
			System.out.println("״̬position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
			DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=DBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		State l1=StateSet.get(0);
		
		ArrayList<State> newStates=SplitSuseSs_new1.splitSuseSs(l1, StateSet, TransitionSet, ClockSet);//l00����ֳ��µ�״̬����
		
		//System.out.println(newStates.size());
		/*for(State s:newStates){
			System.out.println("״̬name: "+s.getName());
			System.out.println("״̬position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
			DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=add_clock_DBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		ArrayList<State> Ps=StateSet;//�鿴��ֹ���״̬������û�иı䣨����ʽ��Ӧ�øı䣬������ǵ�ʱ�Ӹ�λ���Լ��Ӧ�øı䣩
		//System.out.println(Ps.size());
		/*for(State s:Ps){
			System.out.println("name: "+s.getName());
			System.out.println("position: "+s.getPosition());
			DBM_element[][] state=s.getInvariantDBM();
			DBM_element[][] add_clock_state=s.getAddClockRelationDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=state[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		return newStates;
	}
	
	/**
	 * l00û�б���֣�Ps����
	 * @return
	 */
	public static ArrayList<State> getPs(){
		Automatic automatic=Test_split_01_new.getAutomatic();
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<String> ClockSet=automatic.getClockSet();
		
		ArrayList<State> StateSet=Test_split_02_new.getPs();//�����״̬������l0����ֺ���µ�״̬����
		State l00=StateSet.get(0);
		
		ArrayList<State> newStates=SplitSuseSs_new1.splitSuseSs(l00, StateSet, TransitionSet, ClockSet);//l00����ֳ��µ�״̬����
		
		
		ArrayList<State> Ps=new ArrayList<State>();
		
		for(State s:StateSet){//����StateSet���Է�StateSet���ı�
			State ss=new State();
			ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
			ss.setInvariantDBM(s.getInvariantDBM());
			ss.setName(s.getName());
			ss.setPosition(s.getPosition());
			Ps.add(ss);
		}
		for(State s:newStates){//������״̬
			State ss=new State();
			ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
			ss.setInvariantDBM(s.getInvariantDBM());
			ss.setName(s.getName());
			ss.setPosition(s.getPosition());
			Ps.add(ss);
		}
		Ps.remove(0);//ɾ��l1
	/*	System.out.println(Ps.size());
		for(State s:Ps){
			System.out.println("name: "+s.getName());
			System.out.println("position: "+s.getPosition());
			DBM_element[][] state=s.getInvariantDBM();
			DBM_element[][] add_clock_state=s.getAddClockRelationDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=add_clock_state[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		return Ps;
	}
}
