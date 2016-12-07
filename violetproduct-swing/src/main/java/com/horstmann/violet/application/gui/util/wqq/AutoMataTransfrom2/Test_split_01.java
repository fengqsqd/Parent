package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
/**
 * ��֤l0��û�б���ȷ���
 * @author Seryna
 *
 */
public class Test_split_01 {
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
		ar0.add("y<=x");
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
		ArrayList<String> constraint3 =new ArrayList<String>();
		constraint3.add("y<=2");
		constraint3.add("y>=2");
		e3.setConstraint(constraint3);
		
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
		
		Automatic automatic=TemPlateToAutomatic.temPlateToAutomatic(template);
		ArrayList<State> StateSet=automatic.getStateSet();
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<String> ClockSet=automatic.getClockSet();
		State Init_State=automatic.getInitState();
		
		ArrayList<State> newStates=SplitSuseSs.splitSuseSs(Init_State, StateSet, TransitionSet, ClockSet);//l0����ֳ��µ�״̬����
		/*System.out.println(newStates.size());
		for(State s:newStates){
			System.out.println("״̬name: "+s.getName());
			System.out.println("״̬position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
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
		
		ArrayList<State> Ps=automatic.getStateSet();//�鿴��ֹ���״̬������û�б��ı�
		System.out.println(Ps.size());
		for(State s:Ps){
			System.out.println("name: "+s.getName());
			System.out.println("position: "+s.getPosition());
			DBM_element[][] state=s.getInvariantDBM();
			DBM_element[][] fstate=Floyds.floyds(state);
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
		}
	}
}
