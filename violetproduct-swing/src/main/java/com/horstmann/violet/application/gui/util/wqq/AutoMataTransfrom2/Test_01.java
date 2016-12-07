package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
/**
 * ����TemPlateToAutomatic����
 * @author Seryna
 *
 */
public class Test_01 {
	public static void main(String[] args) {
		UppaalTemPlate template=new UppaalTemPlate();
		ArrayList<UppaalTransition> transitions=new ArrayList<UppaalTransition>();
		ArrayList<UppaalLocation> locations=new ArrayList<UppaalLocation>();
		UppaalLocation InitState=new UppaalLocation();
		String name="��һ��ʱ���Զ���";
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");

		ArrayList<String> ar0 =new ArrayList<String>();
		ar0.add("x<2");
		ar0.add("x<=y");
		ar0.add("x>=y");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=2");
		
		UppaalLocation l0=new UppaalLocation();
		l0.setName("l0");
		l0.setInvariant(ar0);
		UppaalLocation l1=new UppaalLocation();
		l1.setName("l1");
		l1.setInvariant(ar1);
		UppaalLocation l2=new UppaalLocation();
		l2.setName("l2");
		UppaalLocation l3=new UppaalLocation();
		l3.setName("l3");
			
		locations.add(l0);
		locations.add(l1);
		locations.add(l2);
		locations.add(l3);
		
		UppaalTransition e0 =new UppaalTransition();
		e0.setSource(l0.getName());
		e0.setTarget(l1.getName());
		ArrayList<String> reset0 =new ArrayList<String>();
		reset0.add("y");
		e0.setResetClocks(reset0);
		ArrayList<String>constraint0 =new ArrayList<String>();
		constraint0.add("x>1");
		e0.setConstraint(constraint0);
		
		
		UppaalTransition e1=new UppaalTransition();
		e1.setSource(l1.getName());
		e1.setTarget(l3.getName());
		ArrayList<String> constraint1 =new ArrayList<String>();
		constraint1.add("y>1");
		constraint1.add("x<=3");
		e1.setConstraint(constraint1);
		
		UppaalTransition e2 =new UppaalTransition();
		e2.setSource(l3.getName());
		e2.setTarget(l1.getName());
		ArrayList<String> constraint2 =new ArrayList<String>();
		constraint2.add("y<2");
		e2.setConstraint(constraint2);
		
		UppaalTransition e3 =new UppaalTransition();
		e3.setSource(l1.getName());
		e3.setTarget(l2.getName());
		ArrayList<String> SetClock3 =new ArrayList<String>();
		//SetClock3.add("y=2");
		//e3.setSetClock(SetClock3);
		
		UppaalTransition e4 =new UppaalTransition();
		e4.setSource(l2.getName());
		e4.setTarget(l3.getName());
		ArrayList<String> constraint4 =new ArrayList<String>();
		constraint4.add("x<=3");
		e4.setConstraint(constraint4);
		
		
		transitions.add(e0);
		transitions.add(e1);
		transitions.add(e2);
		transitions.add(e3);
		transitions.add(e4);
		
		template.setTransitions(transitions);
		template.setLocations(locations);
		template.setClockSet(Clocks);
		template.setInitState(l0);
		template.setName(name);
		
		InitState=l0;
		template.setInitState(InitState);
		/*
		System.out.println(template.getName());
		for(String clo:template.getClocks()){
			System.out.println(clo);
		}*/
		/*for(UppaalLocation loc:template.getLocations()){//����״̬
			System.out.println(loc.getName());
			if(loc.getInvariant()!=null){
				for(String inv:loc.getInvariant()){
					System.out.println(inv);
				}				
			}		
			System.out.println("***********");
		}*/
		/*for(UppaalTransition tran:template.getTransitions()){//����ת��
			System.out.println("Դ"+tran.getSource());
			System.out.println("Ŀ��"+tran.getTarget());
			if(tran.getResetClocks()!=null){
				for(String clo:tran.getResetClocks()){
					System.out.println("��λ��ʱ��"+clo);
				}
			}		
			if(tran.getConstraint()!=null){
				for(String con:tran.getConstraint()){
					System.out.println("ת���е�Լ��"+con);
				}
			}
			
			if(tran.getSetClock()!=null){
				for(String setclo:tran.getSetClock()){
					System.out.println("���õ�ʱ��"+setclo);
				}
			}
			System.out.println("-------------");
		}*/
		
		
		
		
		
		
		
		
		System.out.println("&&&&&&&&&&&&&&&&&&&");
		ArrayList<State> StateSet = new ArrayList<State>();//automatic�е�״̬����
		for(UppaalLocation loc:locations){//��������״̬
			if(loc.getInvariant()!=null){
				ArrayList<String> invariant=loc.getInvariant();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//��״̬�еĲ���ʽת��DBM����
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				state.setFinalState(loc.isFinalState());
				StateSet.add(state);
				
				/*int len=DBM.length;
				for(int i=0;i<len;i++){
					for(int j=0;j<len;j++){
						DBM_element cons=DBM[i][j];
						System.out.println("DBM_i:"+cons.getDBM_i());
						System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());
						System.out.println("***********");
										
					}
				}*/
			}
			else{
				State state=new State();
				state.setName(loc.getName());
				state.setFinalState(loc.isFinalState());
				StateSet.add(state);
			}
			
		}
		
		System.out.println("^^^^^^^^^^^^^^");
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//automatic�е�ת������
		for(UppaalTransition tran:template.getTransitions()){//����ת������
			if(tran.getConstraint()!=null){
				ArrayList<String> constraint=tran.getConstraint();//��ȡת���е�Լ��
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, constraint));//��ת���е�Լ��ת��DBM����
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
				transition.setEventSet(tran.getEvents());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				TransitionSet.add(transition);
				
				/*int len=DBM.length;
				for(int i=0;i<len;i++){
					for(int j=0;j<len;j++){
						DBM_element cons=DBM[i][j];
						System.out.println("DBM_i:"+cons.getDBM_i());
						System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());
						System.out.println("***********");
										
					}
				}*/
			}
			else{
				Transition transition=new Transition();
				transition.setEventSet(tran.getEvents());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				TransitionSet.add(transition);
			}	
		}
	

		
		//����TemPlateToAutomatic����
		System.out.println("%%%%%%%%%%%%%%%%%%%%%");
		Automatic automatic=TemPlateToAutomatic.temPlateToAutomatic(template);
		/*System.out.println("automatic��name: "+automatic.getName());//�������
		for(String clo:automatic.getClockSet()){
			System.out.println(clo);//���ʱ�Ӽ���
		}*/
		
		State initstate=automatic.getInitState();//�����ʼ״̬	
		/*System.out.println("��ʼ״̬name��"+initstate.getName());
		System.out.println("�Ƿ��ǽ���״̬��"+initstate.isFinalState());
		if(initstate.getInvariantDBM()!=null){
			int n=initstate.getInvariantDBM().length;
			DBM_element[][] initDBM=initstate.getInvariantDBM();
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					System.out.println("DBM_i:"+initDBM[i][j].getDBM_i());
					System.out.println("DBM_j:"+initDBM[i][j].getDBM_j());
					System.out.println("value:"+initDBM[i][j].getValue());
					System.out.println("Strictness:"+initDBM[i][j].isStrictness());
					System.out.println("%%%%%%%%%%%");
				}
			}
		}*/
		
	
		/*System.out.println("##############");//���״̬����
		for(State state:automatic.getStateSet()){
			System.out.println("name:"+state.getName());
			System.out.println("�Ƿ��ǽ���״̬:"+state.isFinalState());
			if(state.getInvariantDBM()!=null){
				int len=state.getInvariantDBM().length;
				DBM_element[][] DBM=state.getInvariantDBM();			
				for(int i=0;i<len;i++){
					for(int j=0;j<len;j++){
						System.out.println("DBM_i:"+DBM[i][j].getDBM_i());
						System.out.println("DBM_j:"+DBM[i][j].getDBM_j());
						System.out.println("value:"+DBM[i][j].getValue());
						System.out.println("Strictness:"+DBM[i][j].isStrictness());
						System.out.println("##############");
					}
				}
				
			}
		}*/
		
		
		System.out.println("@@@@@@@@@@@@@@@@@@");//���ת������
		for(Transition t:automatic.getTransitionSet()){
			System.out.println("Դ�� "+t.getSource());
			System.out.println("Ŀ�ģ� "+t.getTarget());
			/*if(t.getEventSet()!=null){
				for(String event:t.getEventSet()){
					System.out.println(event);				
				}
			}
			if(t.getSetClock()!=null){
				for(String clo:t.getSetClock()){
					System.out.println("����ʱ�ӣ�"+clo);
				}
			}
			if(t.getResetClockSet()!=null){
				for(String clo:t.getResetClockSet()){
					System.out.println("��λʱ�ӣ�"+clo);
				}
			}
			if(t.getConstraintDBM()!=null){
				int len=t.getConstraintDBM().length;
				DBM_element[][] DBM=t.getConstraintDBM();			
				for(int i=0;i<len;i++){
					for(int j=0;j<len;j++){
						System.out.println("DBM_i:"+DBM[i][j].getDBM_i());
						System.out.println("DBM_j:"+DBM[i][j].getDBM_j());
						System.out.println("value:"+DBM[i][j].getValue());
						System.out.println("Strictness:"+DBM[i][j].isStrictness());
						System.out.println("@@@@");
					}
				}
			}
			System.out.println("@@@@@@@@@@@@@@@@@@");*/
			/*if(t.getCom_constraint()!=null){
				System.out.println(t.getCom_constraint().size());
				for(DBM_element[][] DBM:t.getCom_constraint()){
					System.out.println("???????????");
					int n=DBM.length;
					for(int i=0;i<n;i++){
						for(int j=0;j<n;j++){
							System.out.println("DBM_i:"+DBM[i][j].getDBM_i());
							System.out.println("DBM_j:"+DBM[i][j].getDBM_j());
							System.out.println("value:"+DBM[i][j].getValue());
							System.out.println("Strictness:"+DBM[i][j].isStrictness());
						
						}
					}
				}
			}*/
		}
		
	}
}
