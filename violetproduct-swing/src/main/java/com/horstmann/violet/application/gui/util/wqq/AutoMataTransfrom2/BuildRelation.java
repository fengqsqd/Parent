package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class BuildRelation {
	public static void main(String[] args) {
		Automatic automatic=Test_split_01_new.getAutomatic();
		ArrayList<Transition> transitions=bulidRelation(automatic);
		System.out.println("�ߵ�������"+transitions.size());
		for(Transition t:transitions){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println("********");
		}
	}
	
	public static ArrayList<Transition> bulidRelation(Automatic automatic) {
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();//���ʱ���Զ���Ǩ�Ƽ���
		ArrayList<String> ClockSet=automatic.getClockSet();//���ʱ���Զ���ʱ�Ӽ���

		ArrayList<State> new_stateSet=Minimization.minimization(automatic);//���ʱ���Զ���
		
		ArrayList<Transition> transitions=new ArrayList<Transition>();//Ҫ���ص�Ǩ�Ƽ���
		
		for(State source:new_stateSet){//����״̬����			
			ArrayList<State> posts=PostAndPre.post(source, new_stateSet, TransitionSet, ClockSet);//���source���
			for(State target:posts){//������̼��ϣ�����Ǩ��
				Transition tran=new Transition();
				tran.setSource(source.getName());
				tran.setTarget(target.getName());
				
				if(source.getPosition().equals(target.getPosition())){//���Դ��Ŀ����ͬ����Ǩ����Ϊ*
					ArrayList<String> events=new ArrayList<String>();
					events.add("*");
					tran.setEventSet(events);
				}
				else{//���Դ��Ŀ�Ĳ�ͬ����Ǩ����Ϊ��Ӧ�¼�
					for(Transition t:TransitionSet){
						if(source.getPosition().equals(t.getSource())&&target.getPosition().equals(t.getTarget())){
							tran.setEventSet(t.getEventSet());
						}						
					}
					
				}						
				transitions.add(tran);
			}
		}
		return transitions;
	}
}
