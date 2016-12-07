package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class Split_l0 {
	public static void main(String[] args) {
		Automatic automatic=AutomaticIBean.automic();
		ArrayList<String> ClockSet=automatic.getClockSet();
		State initstate=new State();
		initstate=automatic.getInitState();
		ArrayList<DBM_element[][]> DBMSet=new ArrayList<DBM_element[][]>();
		
			for(Transition t:automatic.getTransitionSet()){//����ת��
				if(t.getSource().equals(initstate.getName())){//��ȡ��l0�����ıߵ�DBM	
					
					DBM_element[][] andDBM=new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
					DBM_element[][] fandDBM=new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
					DBM_element[][] andcomDBM=new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
					DBM_element[][] fandcomDBM=new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
					//���״̬�еĲ���ʽ��Ϊ��
					if(initstate.getInvariantDBM()!=null){
						DBM_element[][] initDBM=new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
						initDBM=initstate.getInvariantDBM();
						
						//���ת������ʱ��Լ��
						if(t.getConstraintDBM()!=null){
							DBM_element[][] tranDBM=new DBM_element[ClockSet.size()+1][ClockSet.size()+1];	
							tranDBM=t.getConstraintDBM();
							andDBM=AndDBM.andDBM(initDBM, tranDBM);//����ϵ�Լ���ཻ
							fandDBM=Floyds.floyds(andDBM);//�����Ľ�����滯
							DBMSet.add(andDBM);
							
							/*ArrayList<DBM_element[][]> com_constraint=t.getCom_constraint();
							for(DBM_element[][] com:com_constraint){
								andcomDBM=AndDBM.andDBM(initDBM, com);//����ϵ�Լ���Ĳ����ཻ	
								fandcomDBM=Floyds.floyds(andcomDBM);//�����Ľ�����滯	
								DBMSet.add(andcomDBM);
							}	*/
						}
						//���ת����Լ��Ϊ��
						else{
							andDBM=initDBM;
							fandDBM=Floyds.floyds(andDBM);		
							DBMSet.add(andDBM);
						}		
					}
					//���״̬�еĲ���ʽΪ��
					else{
						//���ת������ʱ��Լ��
						if(t.getConstraintDBM()!=null){
							DBM_element[][] tranDBM=new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
							tranDBM=t.getConstraintDBM();
							for(int i=0;i<tranDBM.length;i++){//����״̬��û�в���ʽ�����Խ��Ľ�����Ǳ��ϵ�Լ��
								for(int j=0;j<tranDBM.length;j++){
									DBM_element ele=new DBM_element();
									ele.setValue(tranDBM[i][j].getValue());
									ele.setStrictness(tranDBM[i][j].isStrictness());
									ele.setDBM_i(i);
									ele.setDBM_j(j);
									andDBM[i][j]=ele;	
								}
							}
							fandDBM=Floyds.floyds(andDBM);//�����Ľ�����滯
							DBMSet.add(andDBM);
										
							/*ArrayList<DBM_element[][]> com_constraint=t.getCom_constraint();
							System.out.println(com_constraint.size());
							for(DBM_element[][] com:com_constraint){
								for(int i=0;i<com.length;i++){//����״̬��û�в���ʽ�����Բ����Ľ��Ľ�����Ǳ��ϵ�Լ���Ĳ���
									for(int j=0;j<com.length;j++){
										DBM_element ele=new DBM_element();
										ele.setValue(com[i][j].getValue());
										ele.setStrictness(com[i][j].isStrictness());
										ele.setDBM_i(i);
										ele.setDBM_j(j);
										andcomDBM[i][j]=ele;	
									}
								}
								fandcomDBM=Floyds.floyds(andcomDBM);//�����Ľ�����滯
								DBMSet.add(andcomDBM);				
							}	*/				
						}
						
						//���ת����Լ��Ϊ��
						else{
							andDBM=null;
							fandDBM=null;					
						}
					}
					

					//����ʱ�Ӹ�λ
					ArrayList<String> ResetClockSet=t.getResetClockSet();//���ת���ϸ�λ��ʱ��
					if(ResetClockSet!=null){
						DBM_element[][] resetDBM = new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
						for(String clock:ResetClockSet){
							resetDBM=Reset.reset(andDBM, ClockSet, clock);//������ϵ�ʱ�Ӹ�λ���ϲ�Ϊ�գ������Ӧʱ�Ӹ�λ
						}
						//���ʱ�临λ���ʱ�Ӽ��Լ����������һ��DBM���󣬿յ�λ������<��,���������������ת����Ŀ��״̬��Լ���ཻ���Ϳɽ�ʱ�Ӹ�λ���µ�ʱ��Լ������״̬��
						DBM_element [][] clockConstraint= new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
						for(int i=0;i<ClockSet.size()+1;i++){//��ʼ��clockConstraint
							for(int j=0;j<ClockSet.size()+1;j++){
								DBM_element ele=new DBM_element();
								ele.setDBM_i(i);
								ele.setDBM_j(j);
								ele.setValue(88888);
								ele.setStrictness(false);
								clockConstraint[i][j]=ele;
							}
						}
						for(int i=1;i<ClockSet.size()+1;i++){//����clockConstraint
							for(int j=1;j<ClockSet.size()+1;j++){
								if(i!=j){
									DBM_element ele=new DBM_element();
									ele.setDBM_i(i);
									ele.setDBM_j(j);
									ele.setValue(resetDBM[i][j].getValue());
									ele.setStrictness(resetDBM[i][j].isStrictness());
									clockConstraint[i][j]=ele;
								}
							}
						}							
					}
					
				}
			}	
			System.out.println("DBMSet.size�� "+DBMSet.size());
			int m=1;
			for(DBM_element [][] DBM:DBMSet){
				State state=new State();
				state.setName(initstate.getName()+m);
				state.setInvariantDBM(DBM);
				m=m+1;
				System.out.println("name: "+state.getName());	
			}
	}	
}

/*							for(int i=0;i<len;i++){
								for(int j=0;j<len;j++){
									DBM_element cons=fandcomDBM[i][j];
									System.out.println("DBM_i:"+cons.getDBM_i());
									System.out.println("DBM_j:"+cons.getDBM_j());
									System.out.println("value:"+cons.getValue());
									System.out.println("Strictness:"+cons.isStrictness());
									System.out.println("***********");
													
								}
							}*/