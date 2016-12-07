package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.io.File;
import java.util.ArrayList;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.XML2UppaalUtil;


public class GetAutomatic {
	public static void main(String[] args) {
		//String xml="Draw MoneyForXStream(2).xml";
		/*String xml="throttle_zero_flagForXStream.xml";
		Automatic automatic=GetAutomatic.getAutomatic(xml);*/
		String xml="throttle_zero_flagForXStream.xml";
		Automatic automatic=GetAutomatic.getAutomatic(xml);
		//Automatic automatic=AddType.addType(auto);//
		for(State state:automatic.getStateSet()){
			System.out.println("״̬id����"+state.getId()+"$$$$$$$$$$$");
		}
		for(Transition tran:automatic.getTransitionSet()){
			System.out.println("Ǩ��id����"+tran.getId()+"^^^^^^^^^^^");
			//+tran.getSource()+"---->"+tran.getTarget()+"Լ���� "+tran.getEventSet());
		}
		System.out.println("ʱ���Զ�������:"+automatic.getName());
		System.out.println("ʱ���Զ���ʱ�Ӽ��ϣ�");
		for(String c:automatic.getClockSet()){
			System.out.println("ʱ�Ӽ��ϣ�"+c);
		}
		State iniState=automatic.getInitState();
		System.out.println("��ʼ״̬���֣�"+iniState.getName());
		System.out.println(iniState.getPosition());
		System.out.println(iniState.isFinalState());
		System.out.println("��ʼ״̬TypeΪ��"+iniState.getType());
		System.out.println("��ʼ״̬idΪ��"+iniState.getId());
		DBM_element[][] DBM=iniState.getInvariantDBM();
		for(int i=0;i<automatic.getClockSet().size()+1;i++){
			for(int j=0;j<automatic.getClockSet().size()+1;j++){
				DBM_element cons=DBM[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());					
			}
		}
		
		System.out.println("״̬������"+automatic.getStateSet().size());
		int k=0;
		for(State state:automatic.getStateSet()){
			System.out.println("��"+k+"��״̬");
			k++;
			DBM_element[][] dbm=state.getInvariantDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println(state.getName());
			System.out.println("״̬Type:"+state.getType());//
			System.out.println("״̬id��"+state.getId());//
			DBM_element[][] adddbm=state.getAddClockRelationDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=adddbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println(state.getPosition());
			System.out.println(state.isFinalState());
			System.out.println("--------------------");
		}
		
		System.out.println("Ǩ�Ƹ���"+automatic.getTransitionSet().size());
		int p=0;
		for(Transition tran:automatic.getTransitionSet()){
			System.out.println(tran.getName()+"########################");
			System.out.println("��"+p+"��Ǩ��");
			System.out.println("Ǩ�Ƶ�idΪ��"+tran.getId());//
			p++;
			DBM_element[][] dbm=tran.getConstraintDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			
			System.out.println("Դ:"+tran.getSource());
			System.out.println("Ŀ�ģ�"+tran.getTarget());
			
			ArrayList<String> events=tran.getEventSet();
			//System.out.println(events.size());
			for(String e:events){
				System.out.println("�¼���"+e);
			}
			
			ArrayList<String> reset=tran.getResetClockSet();
			for(String r:reset){
				System.out.println("���õ�ʱ�ӣ�"+r);
			}
			
			ArrayList<String> typeid=tran.getTypeIds();
			for(String i:typeid){
				System.out.println("typeid:"+i);
			}
			
			ArrayList<String> types=tran.getTypes();
			for(String t:types){
				System.out.println("types:"+t);
			}
			
			
			System.out.println("********************");
		}
	}
	/**
	 * ���룺xml�ļ���
	 * �����Automaticʵ����һ��ʱ���Զ�����
	 * @param xml
	 * @return
	 */
	private static int stateNum=1;//���ڱ�ʶstate��id
	private static int tranNum=1;//���ڱ�ʶtransition��id
	public static Automatic getAutomatic(String xml) {
		XML2UppaalUtil util = new XML2UppaalUtil(new File(xml));
		UppaalTemPlate temPlate = util.getTemplates().get(0);
		
		Automatic automatic=new Automatic();
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//automatic�е�ת������
		ArrayList<State> StateSet = new ArrayList<State>();//automatic�е�״̬����

		ArrayList<String> Clocks=temPlate.getClocks();//��ȡʱ���Զ����е�ʱ�Ӽ���
		
		ArrayList<UppaalLocation> locations=temPlate.getLocations();//��ȡʱ���Զ����е�����״̬
	
		for(UppaalLocation loc:locations){//��������״̬
		
			if(loc.getInvariant().size()!=0){
				ArrayList<String> invar=loc.getInvariant();
				ArrayList<String> invariant=new ArrayList<String>();
				for(String i:invar){
					String[] s=i.split("s");
					invariant.add(s[0]);
				}
			
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, invariant));//��״̬�еĲ���ʽת��DBM����
				
				State state=new State();
				if(new Integer(state.getId()).equals(null)){
					state.setId(stateNum++);
				}
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				state.setFinalState(loc.isFinalState());
				StateSet.add(state);
			}
			else{
				ArrayList<String> invariant=new ArrayList<String>();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//��״̬�еĲ���ʽΪ�գ���DBM����Ϊȫ��
				
				State state=new State();
				state.setId(stateNum++);
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				StateSet.add(state);
			}
			
		}
		automatic.setStateSet(StateSet);//�趨automatic�е�״̬����
		
		ArrayList<UppaalTransition> transitions=temPlate.getTransitions();//��ȡtemplate�е�����ת��
		for(UppaalTransition tran:transitions){//����ת������
			if(tran.getConstraint().size()!=0){//Լ��Ϊ��
				ArrayList<String> cons=tran.getConstraint();//��ȡת���е�Լ��
				ArrayList<String> constraint=new ArrayList<String>();
				for(String c:cons){
					String[] s=c.split("s");
					constraint.add(s[0]);
				}
				
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, constraint));//��ת���е�Լ��ת��DBM����
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
				
						
				ArrayList<String> events=new ArrayList<String>();
				if(tran.getEvents().size()!=0){
					String event=new String();
					
					for(String e:tran.getEvents())
					{
						event+=e+";";   //////
					}
					events.add(event);
				}
				transition.setEventSet(events);
				transition.setName(tran.getName());
				System.out.println("^^^^^^^^^^^"+tran.getName());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				transition.setTypeIds(tran.getTypeIds());
				transition.setTypes(tran.getTypes());
				transition.setId(tranNum++);
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
				
				ArrayList<String> events=new ArrayList<String>();
				if(tran.getEvents().size()!=0){
					String event=new String();
					
					for(String e:tran.getEvents())
					{
						event+=e+";";
					}
					events.add(event);
				}
				transition.setEventSet(events);
				transition.setName(tran.getName());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				transition.setTypeIds(tran.getTypeIds());
				transition.setTypes(tran.getTypes());
				transition.setId(tranNum++);
				TransitionSet.add(transition);
			}
			
		}
		automatic.setTransitionSet(TransitionSet);//�趨automatic�е�ת������
		
		
		
		ArrayList<String> ClockSet=temPlate.getClocks();
		automatic.setClockSet(ClockSet);//�趨autotimatic�е�ʱ�Ӽ���
		//////////////////////////////////////////
		//�趨automatic�еĳ�ʼ״̬
		State initstate=new State();
		initstate.setFinalState(temPlate.getInitState().isFinalState());
		initstate.setName(temPlate.getInitState().getName());
		initstate.setId(1);//////////////
		initstate.setType("start");////////////
		if(temPlate.getInitState().getInvariant().size()!=0){
			initstate.setInvariantDBM(DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, temPlate.getInitState().getInvariant())));
			initstate.setAddClockRelationDBM(initstate.getInvariantDBM());
			initstate.setPosition(temPlate.getInitState().getName());
		}
		else{
			ArrayList<String> invariant=new ArrayList<String>();
			DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//��״̬�еĲ���ʽת��DBM����
			initstate.setInvariantDBM(DBM);
			initstate.setAddClockRelationDBM(initstate.getInvariantDBM());
			initstate.setPosition(temPlate.getInitState().getName());
		}
		automatic.setInitState(initstate);
		////////////////////////////////////////////
		//�趨automatic��name
		String name=temPlate.getName();
		automatic.setName(name);
		
		
		ArrayList<State> States=automatic.getStateSet();
		for(State s:States){//���������ʱ�Ӹ�λ���ʱ��Լ��
			s.setAddClockRelationDBM(s.getInvariantDBM());
		}
		//��ʼ״̬��ʱ�Ӹ�λ���Լ��Ҳ������
		//automatic.getInitState().setAddClockRelationDBM(States.get(0).getAddClockRelationDBM());
		
		/*System.out.println("ʱ���Զ�������:"+automatic.getName());
		System.out.println("ʱ���Զ���ʱ�Ӽ��ϣ�");
		for(String c:automatic.getClockSet()){
			System.out.println(c);
		}
		State iniState=automatic.getInitState();
		System.out.println("��ʼ״̬���֣�"+iniState.getName());
		System.out.println(iniState.getPosition());
		System.out.println(iniState.isFinalState());
		DBM_element[][] DBM=iniState.getInvariantDBM();
		for(int i=0;i<automatic.getClockSet().size()+1;i++){
			for(int j=0;j<automatic.getClockSet().size()+1;j++){
				DBM_element cons=DBM[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());					
			}
		}*/
		/*
		System.out.println("״̬������"+automatic.getStateSet().size());
		int k=0;
		for(State state:automatic.getStateSet()){
			System.out.println("��"+k+"��״̬");
			k++;
			DBM_element[][] dbm=state.getInvariantDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println(state.getName());
			DBM_element[][] adddbm=state.getAddClockRelationDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=adddbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			//System.out.println(state.getPosition());
			//System.out.println(state.isFinalState());
			System.out.println("--------------------");
		}*/
		/*
		System.out.println("Ǩ�Ƹ���"+automatic.getTransitionSet().size());
		int p=0;
		for(Transition tran:automatic.getTransitionSet()){
			System.out.println("��"+p+"��Ǩ��");
			p++;
			DBM_element[][] dbm=tran.getConstraintDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			
			System.out.println("Դ:"+tran.getSource());
			System.out.println("Ŀ�ģ�"+tran.getTarget());
			
			ArrayList<String> events=tran.getEventSet();
			//System.out.println(events.size());
			for(String e:events){
				System.out.println("�¼���"+e);
			}
			
			ArrayList<String> reset=tran.getResetClockSet();
			for(String r:reset){
				System.out.println("���õ�ʱ�ӣ�"+r);
			}
			
			ArrayList<String> typeid=tran.getTypeIds();
			for(String i:typeid){
				System.out.println("typeid:"+i);
			}
			
			ArrayList<String> type=tran.getTypes();
			for(String t:type){
				System.out.println("type:"+t);
			}
			
			
			System.out.println("********************");
		}*/
		
		return automatic;
		
		
	}
}
