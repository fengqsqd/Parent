package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
import java.util.Stack;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class StateCoverage__1 {
	public static void main(String[] args) {
		/*Automatic automatic=Test_split_01_new.getAutomatic();
		Automatic newAutomatic=IPR.iPR(automatic);
		Automatic aTDRTAutomatic=ATDTR.aTDRT(newAutomatic,automatic);  
		//Automatic aaa=DFSTree(aTDRTAutomatic);
		ArrayList<Automatic> testcaseSet=testCase(aTDRTAutomatic);*/
       
	}
	/**
	 *����ȥ������Ǩ�Ƶ�ͼ�����������������
	 * @param auto
	 * @return
	 */
	public static Automatic DFSTree(Automatic auto){
		ArrayList<Transition> auto_Transition=auto.getTransitionSet();//���ʱ���Զ���Ǩ�Ƽ���
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//����Լ��������Ǩ�Ƽ���
		ArrayList<State> auto_StateSet=auto.getStateSet();//���ʱ���Զ���״̬����
		ArrayList<State> StateSet=new ArrayList<State>();//�������������״̬���ϣ������ѷ��ʹ���״̬���ϣ�
		
		Stack<State> sstack=new Stack<State>();//״̬ջ
		State intstate=auto.getInitState();//���ʱ���Զ����ĳ�ʼ״̬
		sstack.push(intstate);//��ʼ״̬��ջ
		StateSet.add(intstate);//����ʼ״̬������������״̬���ϣ�Ҳ��ʾΪ�ѷ��ʹ�
		while(!sstack.isEmpty()){
			State X=sstack.peek();//���ջ��Ԫ�أ�������ջ
			int flag=0;//��־X���ڽӵ㶼�����ʹ�
			for(Transition tran:auto_Transition){//�ж�Ŀ��״̬�Ƿ��ѱ�����
				if(X.getName().equals(tran.getSource())){
					int k=0;
					for(State state:StateSet){
						if(tran.getTarget().equals(state.getName())){
							k=1;
							break;
						}
					}
					if(k==0){//Ŀ��״̬û�б����ʹ���Ŀ��״̬����ջ��������StateSet����������Ǩ�Ƽ���TransitionSet
						flag=1;
						for(State s:auto_StateSet){
							if(tran.getTarget().equals(s.getName())){
								sstack.push(s);//�����Ŀ��״̬����ջ
								/*State ss=new State();
								ss.setName(s.getName());
								ss.setPosition(s.getPosition());
								ss.setInvariantDBM(s.getInvariantDBM());*/
								StateSet.add(s);//�����Ŀ��״̬��־Ϊ�ѷ��ʹ�
								/*Transition t=new Transition();
								t.setSource(tran.getSource());
								t.setTarget(tran.getTarget());
								ArrayList<String> events=new ArrayList<String>();
								events.add(tran.getEventSet().get(0));
								events.add(tran.getEventSet().get(1));
								t.setEventSet(events);
								ArrayList<String> ResetClockSet=new ArrayList<String>();
								if(tran.getResetClockSet()!=null){
									for(String set:tran.getResetClockSet()){
										ResetClockSet.add(set);
									}
								}
								t.setResetClockSet(ResetClockSet);*/
								TransitionSet.add(tran);//������Ǩ�Ƽ���Ǩ�Ƽ���
								break;
							}
						}
					}
					if(flag==1){break;}//�������Ǩ�Ƶ�Ŀ��״̬û�б����ʹ�������������Ǩ�Ƶ�ѭ��
					if(k==1){//�������Ǩ�Ƶ�Ŀ��״̬�ѱ����ʹ����������һ��Ǩ��
						continue;
					}
				}
			}
			if(flag==0){sstack.pop();}//��־X���ڽӵ㶼�����ʹ������ջ
		}
		
	/*	System.out.println("������ "+TransitionSet.size());
		for(Transition t:TransitionSet){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println(t.getEventSet().get(1));
			System.out.println("-------------");
		}*/
		
		/*System.out.println("״̬���� "+StateSet.size());
		for(State s:StateSet){
			System.out.println(s.getName());
			System.out.println(s.getPosition());
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=s.getInvariantDBM()[i][j];
					
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("-------------");
		}*/
		
		Automatic aaa=new Automatic();
		aaa.setClockSet(auto.getClockSet());
		aaa.setName("aaa");
		aaa.setStateSet(StateSet);
		aaa.setTransitionSet(TransitionSet);
		for(State s:StateSet){
			if(s.getName().equals(auto.getInitState().getName())&&Minimization__1.includeZero(s.getInvariantDBM())==1){
				aaa.setInitState(s);//����ʱ���Զ����ĳ�ʼ״̬
			}
		}
		return aaa;
		
	}
	/**
	 * �������������������������״̬����׼��Ĳ������м���
	 * @param a
	 * @return
	 */
	public static ArrayList<Automatic> testCase(Automatic a){
		Automatic aaa=DFSTree(a);
		ArrayList<Transition> aaa_Transition=aaa.getTransitionSet();//���ʱ���Զ���Ǩ�Ƽ���
		ArrayList<State> aaa_StateSet=aaa.getStateSet();//���ʱ���Զ���״̬����
		
		ArrayList<State> visitedState=new ArrayList<State>();//�������ѷ��ʹ���״̬���ϣ�
		ArrayList<ArrayList<State>> S=new ArrayList<ArrayList<State>>();//�������м��ϣ�״̬��
		ArrayList<ArrayList<Transition>> T=new ArrayList<ArrayList<Transition>>();//�������м��ϣ��ߣ�
		
		Stack<State> sstack=new Stack<State>();//״̬ջ
		Stack<Transition> tstack=new Stack<Transition>();//Ǩ��ջ
		State intstate=aaa.getInitState();//���ʱ���Զ����ĳ�ʼ״̬
		sstack.push(intstate);//��ʼ״̬��ջ
		visitedState.add(intstate);//����ʾΪ�ѷ��ʹ�
		while(!sstack.isEmpty()){
			State X=sstack.peek();//���ջ��Ԫ�أ�������ջ
			
			int leaf=1;
			for(Transition t:aaa_Transition){//�ж�X�Ƿ���Ҷ�ӽڵ�
				if(t.getSource().equals(X.getName())){
					leaf=0;
					break;
				}
			}
			if(leaf==0){//X����Ҷ�ӽڵ�
				int flag=0;//��־X���ڽӵ㶼�����ʹ�
				for(Transition tran:aaa_Transition){//�ж�Ŀ��״̬�Ƿ��ѱ�����
					if(X.getName().equals(tran.getSource())){
						int k=0;
						for(State state:visitedState){
							if(tran.getTarget().equals(state.getName())){
								k=1;
								break;
							}
						}
						if(k==0){//Ŀ��״̬û�б����ʹ���Ŀ��״̬����ջ��������StateSet����������Ǩ�Ƽ���TransitionSet
							flag=1;
							for(State s:aaa_StateSet){
								if(tran.getTarget().equals(s.getName())){
									sstack.push(s);//�����Ŀ��״̬����ջ
									/*State ss=new State();
									ss.setName(s.getName());
									ss.setPosition(s.getPosition());
									ss.setInvariantDBM(s.getInvariantDBM());*/
									visitedState.add(s);//�����Ŀ��״̬��־Ϊ�ѷ��ʹ�
									tstack.push(tran);//������Ǩ������ջ
									break;
								}
							}
						}
						if(flag==1){break;}//�������Ǩ�Ƶ�Ŀ��״̬û�б����ʹ�������������Ǩ�Ƶ�ѭ��
						if(k==1){//�������Ǩ�Ƶ�Ŀ��״̬�ѱ����ʹ����������һ��Ǩ��
							continue;
						}
					}
				}
				if(flag==0){//��־X���ڽӵ㶼�����ʹ������ջ
					sstack.pop();
					if(!tstack.isEmpty()){tstack.pop();}
					
				}
			}
			if(leaf==1){//X��Ҷ�ӽڵ�,���״̬ջ��״̬��Ǩ��ջ��Ǩ�ƣ�����һ���������У�����������м���
				ArrayList<State> StateSet=new ArrayList<State>();//һ�����������е�״̬
				for(State s:sstack){
					StateSet.add(s);
				}
				S.add(StateSet);
				/*for(State s:StateSet){
					System.out.println(s.getName());
				}
				System.out.println("-------");*/
				
				ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//һ�����������еı�
				for(Transition t:tstack){
					TransitionSet.add(t);
				}
				T.add(TransitionSet);
				/*for(Transition t:TransitionSet){
					System.out.println(t.getSource()+"-->"+t.getTarget());
				}
				System.out.println("***********");*/
				
				sstack.pop();
				tstack.pop();
			}
			
		}
		
		
		//System.out.println(S.size());
		int n=S.size();//������������
		ArrayList<Automatic> testcaseSet=new ArrayList<Automatic>();//������������
		for(int i=0;i<n;i++){
			Automatic test_case=new Automatic();
			test_case.setClockSet(a.getClockSet());
			test_case.setName("��������"+(i+1));
			test_case.setStateSet(S.get(i));
			test_case.setTransitionSet(T.get(i));
			test_case.setInitState(aaa.getInitState());
			testcaseSet.add(test_case);
		}
		/*System.out.println("��������������"+testcaseSet.size());
		for(Automatic auto:testcaseSet){
			System.out.println(auto.getName());
			for(State s:auto.getStateSet()){
				System.out.println(s.getName());
				//System.out.println(s.getPosition());
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						DBM_element cons=s.getInvariantDBM()[i][j];
						
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());									
					}
				}
				System.out.println("-------------");
			}
			for(Transition t:auto.getTransitionSet()){
				System.out.println(t.getSource()+"-->"+t.getTarget());			
				System.out.println(t.getEventSet().get(0));
				System.out.println(t.getEventSet().get(1));
				if(t.getResetClockSet().size()!=0){
					System.out.println(t.getResetClockSet().get(0));
				}
				System.out.println("-------------");
			}
		}*/
		
		return testcaseSet;
	}
}
