package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


public class ATDTR__1 {
	public static void main(String[] args) {
		/*Automatic automatic=Test_split_01_new.getAutomatic();
		Automatic newAutomatic=IPR.iPR(automatic);
		Automatic aTDRTAutomatic=aTDRT(newAutomatic,automatic);  */
	}
	
	public static Automatic aTDRT(Automatic auto,Automatic original_auto) {
		//ȥ������ʱ���ӳ�Ǩ��
		ArrayList<Transition> auto_Transition=auto.getTransitionSet();//���ʱ���Զ���Ǩ�Ƽ���
		
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//����Լ����Ǩ�Ƽ���
		
		for(Transition t:auto_Transition){//����ʱ���Զ���Ǩ�Ƽ��ϣ�װ��TransitionSet��
			Transition tran=new Transition();
			tran.setSource(t.getSource());
			tran.setTarget(t.getTarget());
			tran.setEventSet(t.getEventSet());
			tran.setTypeIds(t.getTypeIds());
			tran.setTypes(t.getTypes());
			
			TransitionSet.add(tran);
		}
		
		ArrayList<Transition> ET=new ArrayList<Transition>();//��ȡ��Ϊ����ʱ��Ǩ�Ƶ�Ǩ�Ƽ���
		for(Transition tran:TransitionSet){
			if(tran.getEventSet().size()!=0){
				if(tran.getEventSet().get(0).equals("*")){
					ET.add(tran);
				}
			}
		}	
		//System.out.println(ET.size());
		
		while(ET.size()>0){
			
			ListIterator<Transition> ET_iterator = ET.listIterator();
			while(ET_iterator.hasNext()){
				Transition X = ET_iterator.next();
				ET_iterator.remove();
				//System.out.println(ET.size());
				
				ArrayList<Transition> TT=new ArrayList<Transition>();//�����XΪĿ��ΪԴ��Ǩ�Ƽ���
				for(Transition t:TransitionSet){
					if(t.getSource().equals(X.getTarget())){
						TT.add(t);
					}
				}
				for(Transition tt:TT){//����TT���ж��±��Ƿ������Ǩ�Ƽ�����
					int flag=1;
					for(Transition tran:TransitionSet){
						if(X.getSource().equals(tran.getSource())&&tt.getTarget().equals(tran.getTarget())&&tt.getEventSet().get(0).equals(tran.getEventSet().get(0))){
							flag=0;
							break;
						}
						
					}
					if(flag==1){//����±߲�����Ǩ�Ƽ����У������Ǩ�Ƽ���
						Transition tran=new Transition();
						tran.setSource(X.getSource());
						tran.setTarget(tt.getTarget());
						ArrayList<String> events=new ArrayList<String>();
						events.add(tt.getEventSet().get(0));
						tran.setEventSet(events);
						tran.setTypeIds(tt.getTypeIds());
						tran.setTypes(tt.getTypes());
						
						TransitionSet.add(tran);//���±߼���Ǩ�Ƽ���
						if(tran.getEventSet().get(0).equals("*")){//����±��Ǻ�����ʱ��Ǩ�Ƶıߣ������ET	
							ET_iterator.add(tran);
						}//������*���±߼���ET	
						
					}
				}
							
				Iterator<Transition> t_iterator = TransitionSet.iterator();//��Ǩ�Ƽ������Ƴ�X
				while(t_iterator.hasNext()){
					Transition tran = t_iterator.next();
					if(tran.equals(X)){					
						t_iterator.remove();
					}
				}
			}			
		}
		/*System.out.println("ʣ������� "+TransitionSet.size());
		for(Transition t:TransitionSet){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println("-------------");
		}	*/	
		
		
		
		//ȥ�����ɴ�״̬�����Ǩ��
		ArrayList<State> auto_StateSet=auto.getStateSet();//���ʱ���Զ���״̬����
		ArrayList<State> StateSet=new ArrayList<State>();//����Լ����״̬����
		
		for(State s:auto_StateSet){//����ʱ���Զ�����״̬���ϣ�װ��StateSet
			State state=new State();
			state.setName(s.getName());
			state.setPosition(s.getPosition());
			state.setInvariantDBM(s.getInvariantDBM());
			state.setAddClockRelationDBM(s.getAddClockRelationDBM());
			StateSet.add(state);
		}
		State intstate=auto.getInitState();//���ʱ���Զ����ĳ�ʼ״̬
		ArrayList<State> SV=new ArrayList<State>();
		for(State state:StateSet){//��ò��ɴ�״̬
			int i=1;
			for(Transition tran:TransitionSet){
				if(tran.getTarget().equals(state.getName())){
					i=0;
					break;
				}
			}
			if(i==1&&!state.getName().equals(intstate.getName())){
				SV.add(state);
			}
		}
		for(State state:SV){//�������ɴ�״̬��ɾ�����ɴ�״̬������ص�Ǩ��
			StateSet.remove(state);	
			 ListIterator<Transition> iterator = TransitionSet.listIterator();
		        while(iterator.hasNext()){
		        	Transition tran = iterator.next();
		            if(tran.getSource().equals(state.getName())){
		            	iterator.remove();  
		            }
		            
		        }			
		}
		
		/*System.out.println("ʣ������� "+TransitionSet.size());
		for(Transition t:TransitionSet){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println("-------------");
		}*/
		
		//ÿ��Ǩ���ϼ�һ��ʱ�����ti
		int k=1;
		for(Transition tran:TransitionSet){
			ArrayList<String> events=tran.getEventSet();
			
			ArrayList<String> es=new ArrayList<String>();
			for(String s:events){
				es.add(s);
			}
			
			String e="t"+k;
			es.add(e);
			tran.setEventSet(es);
			k++;
		}
		/*System.out.println("ʣ������� "+TransitionSet.size());
		for(Transition t:TransitionSet){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println(t.getEventSet().get(1));
			System.out.println("-------------");
		}*/
		/*System.out.println("ʣ��״̬���� "+StateSet.size());
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
		Automatic atstr=new Automatic();
		atstr.setClockSet(auto.getClockSet());
		atstr.setName("ATDTR");
		atstr.setStateSet(StateSet);
		atstr.setTransitionSet(TransitionSet);
		for(State s:StateSet){
			if(s.getName().equals(auto.getInitState().getName())&&Minimization__1.includeZero(s.getInvariantDBM())==1){
				atstr.setInitState(s);//����ʱ���Զ����ĳ�ʼ״̬
			}
		}
		
		
		ArrayList<Transition> original_transet=original_auto.getTransitionSet();//��ԭʱ���Զ����е�ʱ�Ӹ�λ������ӵ���ֺ�õ���ͼ����Ӧ�ı���
		for(Transition t:atstr.getTransitionSet()){
			String source_position=new String();
			String target_position=new String();
			for(State s:atstr.getStateSet()){
				if(t.getSource().equals(s.getName())){
					source_position=s.getPosition();
				}
				if(t.getTarget().equals(s.getName())){
					target_position=s.getPosition();
				}
			}
			
			for(Transition original_t:original_transet){
				if(original_t.getSource().equals(source_position)&&original_t.getTarget().equals(target_position)){
					ArrayList<String> ResetClockSet=new ArrayList<String>();
					if(original_t.getResetClockSet()!=null){
						ResetClockSet=original_t.getResetClockSet();
					}
					t.setResetClockSet(ResetClockSet);
				}
			}
		}
		
		/*System.out.println("ʣ������� "+atstr.getTransitionSet().size());
		for(Transition t:atstr.getTransitionSet()){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println(t.getEventSet().get(1));
			if(t.getResetClockSet().size()!=0){
				System.out.println(t.getResetClockSet().get(0));
			}
			System.out.println("-------------");
		}*/
		return atstr;
	}
}
