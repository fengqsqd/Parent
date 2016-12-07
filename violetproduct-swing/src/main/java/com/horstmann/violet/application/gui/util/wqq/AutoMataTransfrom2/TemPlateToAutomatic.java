package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class TemPlateToAutomatic {
	
	public static Automatic temPlateToAutomatic(UppaalTemPlate template){
		Automatic automatic=new Automatic();
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//automatic�е�ת������
		ArrayList<State> StateSet = new ArrayList<State>();//automatic�е�״̬����

		
		ArrayList<String> Clocks=template.getClocks();//��ȡʱ���Զ����е�ʱ�Ӽ���
		
		ArrayList<UppaalLocation> locations=template.getLocations();//��ȡʱ���Զ����е�����״̬
		for(UppaalLocation loc:locations){//��������״̬
			if(loc.getInvariant()!=null){
				ArrayList<String> invariant=loc.getInvariant();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//��״̬�еĲ���ʽת��DBM����
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				StateSet.add(state);
			}
			else{
				ArrayList<String> invariant=new ArrayList<String>();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//��״̬�еĲ���ʽΪ�գ���DBM����Ϊȫ��
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				StateSet.add(state);
			}
			
		}
		automatic.setStateSet(StateSet);//�趨automatic�е�״̬����
		
		ArrayList<UppaalTransition> transitions=template.getTransitions();//��ȡtemplate�е�����ת��
		for(UppaalTransition tran:transitions){//����ת������
			if(tran.getConstraint()!=null){
				ArrayList<String> constraint=tran.getConstraint();//��ȡת���е�Լ��
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, constraint));//��ת���е�Լ��ת��DBM����
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
				transition.setEventSet(tran.getEvents());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				
				//��ʱ���
				//ArrayList<DBM_element[][]> com_constraint=Complement.complement(constraint, Clocks);
				//transition.setCom_constraint(com_constraint);
				
				TransitionSet.add(transition);
			}
			else{
				ArrayList<String> constraint=new ArrayList<String>();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, constraint));//ת���е�Լ��Ϊ�գ���DBM����Ϊȫ��
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
				transition.setEventSet(tran.getEvents());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				TransitionSet.add(transition);
			}
			
		}
		automatic.setTransitionSet(TransitionSet);//�趨automatic�е�ת������
		
		
		
		ArrayList<String> ClockSet=template.getClocks();
		automatic.setClockSet(ClockSet);//�趨autotimatic�е�ʱ�Ӽ���
		
		//�趨automatic�еĳ�ʼ״̬
		State initstate=new State();
		//initstate.setFinalState(template.getInitState().isFinalState());
		initstate.setName(template.getInitState().getName());
		if(template.getInitState().getInvariant()!=null){
			initstate.setInvariantDBM(DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, template.getInitState().getInvariant())));
			initstate.setPosition(template.getInitState().getName());
		}
		else{
			ArrayList<String> invariant=new ArrayList<String>();
			DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//��״̬�еĲ���ʽת��DBM����
			initstate.setInvariantDBM(DBM);
			initstate.setPosition(template.getInitState().getName());
		}
		automatic.setInitState(initstate);
		//�趨automatic��name
		String name=template.getName();
		automatic.setName(name);
		
		return automatic;
	}
}
