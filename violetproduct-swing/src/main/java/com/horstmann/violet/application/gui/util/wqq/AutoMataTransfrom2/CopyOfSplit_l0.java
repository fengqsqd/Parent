package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class CopyOfSplit_l0 {
	public static void main(String[] args) {
		Automatic automatic=AutomaticIBean.automic();
		ArrayList<String> ClockSet=automatic.getClockSet();
		State initstate=automatic.getInitState();
		//���״̬�еĲ���ʽ��Ϊ��
		//if(initstate.getInvariantDBM()!=null){//��l0���в��
			DBM_element[][] initDBM=initstate.getInvariantDBM();
			int len=initDBM.length;
			
			for(Transition t:automatic.getTransitionSet()){//����ת��
				if(t.getSource().equals(initstate.getName())){//��ȡ��l0�����ıߵ�DBM	
					DBM_element[][] andDBM=null;
					DBM_element[][] fandDBM=null;
					DBM_element[][] andcomDBM=null;
					DBM_element[][] fandcomDBM=null;
					if(t.getConstraintDBM()!=null){//���ת������ʱ��Լ��
						DBM_element[][] tranDBM=t.getConstraintDBM();
						andDBM=AndDBM.andDBM(initDBM, tranDBM);//����ϵ�Լ���ཻ
						fandDBM=Floyds.floyds(andDBM);//�����Ľ�����滯
									
						/*ArrayList<DBM_element[][]> com_constraint=t.getCom_constraint();
						System.out.println(com_constraint.size());
						for(DBM_element[][] com:com_constraint){
							andcomDBM=AndDBM.andDBM(initDBM, com);//����ϵ�Լ���Ĳ����ཻ
							fandcomDBM=Floyds.floyds(andcomDBM);//�����Ľ�����滯
												
						}*/					
					}
					else{//���ת����Լ��Ϊ��
						andDBM=initDBM;
						fandDBM=Floyds.floyds(andDBM);					
					}
					
					//����ʱ�Ӹ�λ
					ArrayList<String> ResetClockSet=t.getResetClockSet();//���ת���ϸ�λ��ʱ��
					if(ResetClockSet!=null){
						DBM_element[][] resetDBM = null;	
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
						
						for(int i=1;i<ClockSet.size()+1;i++){//��ȡresetDBM�е�ʱ��Լ��
							for(int j=1;j<ClockSet.size()+1;j++){
								if(i!=j){
									clockConstraint[i][j]=resetDBM[i][j];
								}
							}
						}							
					}
					
				}
			}
		//}
		//���״̬�еĲ���ʽΪ��
		/*else{
			for(Transition t:automatic.getTransitionSet()){//����ת��
				if(t.getSource().equals(initstate.getName())){//��ȡ��l0�����ıߵ�DBM	
					DBM_element[][] andDBM=null;
					DBM_element[][] fandDBM=null;
					DBM_element[][] andcomDBM=null;
					DBM_element[][] fandcomDBM=null;
					if(t.getConstraintDBM()!=null){//���ת������ʱ��Լ��
						DBM_element[][] tranDBM=t.getConstraintDBM();
						andDBM=AndDBM.andDBM(initDBM, tranDBM);//����ϵ�Լ���ཻ
						fandDBM=Floyds.floyds(andDBM);//�����Ľ�����滯
									
						ArrayList<DBM_element[][]> com_constraint=t.getCom_constraint();
						System.out.println(com_constraint.size());
						for(DBM_element[][] com:com_constraint){
							andcomDBM=AndDBM.andDBM(initDBM, com);//����ϵ�Լ���Ĳ����ཻ
							fandcomDBM=Floyds.floyds(andcomDBM);//�����Ľ�����滯
												
						}					
					}
					else{//���ת����Լ��Ϊ��
						andDBM=initDBM;
						fandDBM=Floyds.floyds(andDBM);					
					}
					
					//����ʱ�Ӹ�λ
					ArrayList<String> ResetClockSet=t.getResetClockSet();//���ת���ϸ�λ��ʱ��
					if(ResetClockSet!=null){
						DBM_element[][] resetDBM = null;	
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
						
						for(int i=1;i<ClockSet.size()+1;i++){//��ȡresetDBM�е�ʱ��Լ��
							for(int j=1;j<ClockSet.size()+1;j++){
								if(i!=j){
									clockConstraint[i][j]=resetDBM[i][j];
								}
							}
						}							
					}
					
				}
			}
		}*/
	}
}

/*for(int i=0;i<len;i++){
								for(int j=0;j<len;j++){
									DBM_element cons=fandcomDBM[i][j];
									System.out.println("DBM_i:"+cons.getDBM_i());
									System.out.println("DBM_j:"+cons.getDBM_j());
									System.out.println("value:"+cons.getValue());
									System.out.println("Strictness:"+cons.isStrictness());
									System.out.println("***********");
													
								}
							}*/