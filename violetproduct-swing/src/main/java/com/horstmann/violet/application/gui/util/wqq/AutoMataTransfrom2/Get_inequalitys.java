package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class Get_inequalitys {
	public static ArrayList<String> get_Inequalitys(Automatic auto){
		ArrayList<String> clockset=auto.getClockSet();//�������е�ʱ�Ӽ���
		int clock_number=clockset.size();//�������е�ʱ�Ӹ���
		ArrayList<Transition> TransitionSet=auto.getTransitionSet();//�������еı߼���
		ArrayList<State> StateSet=auto.getStateSet();//�������е�״̬����
		ArrayList<String> Inequalitys=new ArrayList<String>();//Ҫ���صĲ���ʽ��
		
		int resetNumber=0;
		for(Transition t:TransitionSet){//��ò���������ʱ�Ӹ�λ�Ĳ�������
			resetNumber=resetNumber+t.getResetClockSet().size();
		}
		
		//���һ:����������û�б���λ��ʱ��,����ϵͳ����1��ʱ�ӻ�������ʱ��
		if(resetNumber==0){
			noReset(TransitionSet, StateSet, clock_number, Inequalitys);
		}
		
		//�������������������2��ʱ�ӣ�ֻ��1��ʱ�Ӹ�λ
		if(resetNumber==1&&clock_number==2){
			
			int k=0;//��ñ���λ��ʱ����ʱ�Ӽ����е�λ��
			for(Transition ts:TransitionSet){
				if(ts.getResetClockSet().size()!=0){
					String resetclock=ts.getResetClockSet().get(0);
					for(int i=0;i<clockset.size();i++){
						if(resetclock.equals(clockset.get(i))){
							k=i;
							break;
						}
					}
					break;
				}
			}
			
			for(Transition t1:TransitionSet){
				ArrayList<Transition> Ts=new ArrayList<Transition>();//��ȡ����·���е�һ���ߵ��ñߵı߼���
				for(Transition t2:TransitionSet){
					if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
						Ts.add(t2);
					}
					if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
						break;
					}
				}
				Ts.add(t1);
				
				
				DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//���t1Ŀ��״̬��ʱ��Լ������
				for(State s:StateSet){
					if(t1.getTarget().equals(s.getName())){
						DBM=Floyds.floyds(s.getInvariantDBM());
					}
				}
				
				boolean flag=false;//�жϵ�һ���ߵ��ñߵıߵļ������Ƿ���ʱ�Ӹ�λ				
				for(Transition ts:Ts){
					  if(ts.getResetClockSet().size()!=0){
						  flag=true;
						  break;
					  }
				}
				if(flag==false){//��һ���ߵ��ñߵıߵļ�����û��ʱ�Ӹ�λ
					ArrayList<String> label=new ArrayList<String>();//���Ts�и����ϵ�ʱ����
					for(Transition ts:Ts){
						label.add(ts.getEventSet().get(1));
					}
					int upper=0;//ʱ��Լ�����Ͻ�
					String upper_label=new String();
					int lower=0;//ʱ��Լ�����½�
					String lower_label=new String();
					if(k==0){//x����λ��ȡy�����½�
						lower=-DBM[0][2].getValue();
						if(DBM[0][2].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						
						upper=DBM[2][0].getValue();
						if(DBM[2][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
					}
					if(k==1){//y����λ��ȡx�����½�
						lower=-DBM[0][1].getValue();
						if(DBM[0][1].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						upper=DBM[1][0].getValue();
						if(DBM[1][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
					}
					
					
					String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
					String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
					for(int i=0;i<label.size();i++){
						if(i!=label.size()-1){
							Inequality1=Inequality1+label.get(i)+"+";
							Inequality2=Inequality2+label.get(i)+"+";
						}
						else{
							Inequality1=Inequality1+label.get(i);
							Inequality2=Inequality2+label.get(i);
						}
					}
					
					if(upper!=88888){
						Inequality1=Inequality1+upper_label+upper;
						Inequalitys.add(Inequality1);
					}
					Inequality2=Inequality2+lower_label+lower;
					Inequalitys.add(Inequality2);
				}
				if(flag==true){//��һ���ߵ��ñߵıߵļ�������ʱ�Ӹ�λ
					ArrayList<Transition> tt=new ArrayList<Transition>();//��ô�ʱ�Ӹ�λ����һ���ߵ��ñߵļ���
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							for(int j=i+1;j<Ts.size();j++){
								tt.add(Ts.get(j));
							}
						}
					}
					//��ô�ʱ�Ӹ�λ����һ���ߵ��ñߵĲ���ʽ
					if(tt.size()>0){//ʱ�Ӹ�λ����һ���ߵ��ñߵļ����������
						ArrayList<String> label=new ArrayList<String>();//��ô�ʱ�Ӹ�λ����һ���ߵ��ñߵļ����и����ϵ�ʱ����
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//ʱ��Լ�����Ͻ�
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//ʱ��Լ�����½�
						if(k==0){//x����λ��ȡx�����½�
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k==1){//y����λ��ȡy�����½�
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
						String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
					//��ôӵ�һ���ߵ��ñߵĲ���ʽ
					ArrayList<String> label1=new ArrayList<String>();//���Ts�и����ϵ�ʱ����
					for(Transition ts:Ts){
						label1.add(ts.getEventSet().get(1));
					}
					int upper1=0;//ʱ��Լ�����Ͻ�
					int lower1=0;//ʱ��Լ�����½�
					if(k==0){//x����λ��ȡy�����½�
						lower1=-DBM[0][2].getValue();
						upper1=DBM[2][0].getValue();
					}
					if(k==1){//y����λ��ȡx�����½�
						lower1=-DBM[0][1].getValue();
						upper1=DBM[1][0].getValue();
					}
					
					
					String Inequality3=new String();//��ʾ�Ͻ�Ĳ���ʽ
					String Inequality4=new String();//��ʾ�½�Ĳ���ʽ
					for(int i=0;i<label1.size();i++){
						if(i!=label1.size()-1){
							Inequality3=Inequality3+label1.get(i)+"+";
							Inequality4=Inequality4+label1.get(i)+"+";
						}
						else{
							Inequality3=Inequality3+label1.get(i);
							Inequality4=Inequality4+label1.get(i);
						}
					}
					
					if(upper1!=88888){
						Inequality3=Inequality3+"<"+upper1;
						Inequalitys.add(Inequality3);
					}
					Inequality4=Inequality4+">"+lower1;
					Inequalitys.add(Inequality4);
					
				}
				
				
			}
		}
	
		//�������������������1��ʱ�ӣ�1��ʱ�Ӹ�λ
		if(resetNumber==1&&clock_number==1){
			for(Transition t1:TransitionSet){
				ArrayList<Transition> Ts=new ArrayList<Transition>();//��ȡ����·���е�һ���ߵ��ñߵı߼���
				for(Transition t2:TransitionSet){
					if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
						Ts.add(t2);
					}
					if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
						break;
					}
				}
				Ts.add(t1);
				
				
				DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//���t1Ŀ��״̬��ʱ��Լ������
				for(State s:StateSet){
					if(t1.getTarget().equals(s.getName())){
						DBM=Floyds.floyds(s.getInvariantDBM());
					}
				}
				
				boolean flag=false;//�жϵ�һ���ߵ��ñߵıߵļ������Ƿ���ʱ�Ӹ�λ				
				for(Transition ts:Ts){
					  if(ts.getResetClockSet().size()!=0){
						  flag=true;
						  break;
					  }
				}
				if(flag==false){//��һ���ߵ��ñߵıߵļ�����û��ʱ�Ӹ�λ  ��õ�һ���ߵ� �ñߵĲ���ʽ
					ArrayList<String> label=new ArrayList<String>();//���Ts�и����ϵ�ʱ����
					for(Transition ts:Ts){
						label.add(ts.getEventSet().get(1));
					}
					int upper=0;//ʱ��Լ�����Ͻ�
					String upper_label=new String();
					int lower=0;//ʱ��Լ�����½�
					String lower_label=new String();
					lower=-DBM[0][1].getValue();
					if(DBM[0][1].isStrictness()==false){
						lower_label=">";
					}
					else lower_label=">=";
					
					upper=DBM[1][0].getValue();
					if(DBM[1][0].isStrictness()==false){
						upper_label="<";
					}
					else upper_label="<=";
					
					
					String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
					String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
					for(int i=0;i<label.size();i++){
						if(i!=label.size()-1){
							Inequality1=Inequality1+label.get(i)+"+";
							Inequality2=Inequality2+label.get(i)+"+";
						}
						else{
							Inequality1=Inequality1+label.get(i);
							Inequality2=Inequality2+label.get(i);
						}
					}
					
					if(upper!=88888){
						Inequality1=Inequality1+upper_label+upper;
						Inequalitys.add(Inequality1);
					}
					Inequality2=Inequality2+lower_label+lower;
					Inequalitys.add(Inequality2);
				}
				if(flag==true){//��һ���ߵ��ñߵıߵļ�������ʱ�Ӹ�λ
					ArrayList<Transition> tt=new ArrayList<Transition>();//��ô�ʱ�Ӹ�λ�ıߵ��ñߵļ���
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							for(int j=i;j<Ts.size();j++){
								tt.add(Ts.get(j));
							}
						}
					}
					//��ô�ʱ�Ӹ�λ�ıߵ��ñߵĲ���ʽ
					if(tt.size()>0){//ʱ�Ӹ�λ�ıߵ��ñߵļ����������
						ArrayList<String> label=new ArrayList<String>();//��ô�ʱ�Ӹ�λ�ıߵ��ñߵļ����и����ϵ�ʱ����
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//ʱ��Լ�����Ͻ�
						String upper_label=new String();
						int lower=0;//ʱ��Լ�����½�
						String lower_label=new String();
						
						lower=-DBM[0][1].getValue();
						if(DBM[0][1].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						upper=DBM[1][0].getValue();
						if(DBM[1][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
						
						String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
						String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
				}
				
				
			}
					
				
		}
		//����ģ�������������1��ʱ�ӣ�2��ʱ�Ӹ�λ
		if(resetNumber==2&&clock_number==1){
			for(Transition t1:TransitionSet){
				ArrayList<Transition> Ts=new ArrayList<Transition>();//��ȡ����·���е�һ���ߵ��ñߵı߼���
				for(Transition t2:TransitionSet){
					if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
						Ts.add(t2);
					}
					if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
						break;
					}
				}
				Ts.add(t1);
				
				
				DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//���t1Ŀ��״̬��ʱ��Լ������
				for(State s:StateSet){
					if(t1.getTarget().equals(s.getName())){
						DBM=Floyds.floyds(s.getInvariantDBM());
					}
				}
				
				int reset=0;//��ôӵ�һ���ߵ��ñߵ�·�����м���ʱ�Ӹ�λ		
				for(Transition ts:Ts){
					  if(ts.getResetClockSet().size()!=0){
						  reset++;
					  }
				}
				if(reset==0){//��һ���ߵ��ñߵıߵļ�����û��ʱ�Ӹ�λ  ��õ�һ���ߵ� �ñߵĲ���ʽ
					ArrayList<String> label=new ArrayList<String>();//���Ts�и����ϵ�ʱ����
					for(Transition ts:Ts){
						label.add(ts.getEventSet().get(1));
					}
					int upper=0;//ʱ��Լ�����Ͻ�
					String upper_label=new String();
					int lower=0;//ʱ��Լ�����½�
					String lower_label=new String();
					lower=-DBM[0][1].getValue();
					if(DBM[0][1].isStrictness()==false){
						lower_label=">";
					}
					else lower_label=">=";
					
					upper=DBM[1][0].getValue();
					if(DBM[1][0].isStrictness()==false){
						upper_label="<";
					}
					else upper_label="<=";
					
					
					String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
					String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
					for(int i=0;i<label.size();i++){
						if(i!=label.size()-1){
							Inequality1=Inequality1+label.get(i)+"+";
							Inequality2=Inequality2+label.get(i)+"+";
						}
						else{
							Inequality1=Inequality1+label.get(i);
							Inequality2=Inequality2+label.get(i);
						}
					}
					
					if(upper!=88888){
						Inequality1=Inequality1+upper_label+upper;
						Inequalitys.add(Inequality1);
					}
					Inequality2=Inequality2+lower_label+lower;
					Inequalitys.add(Inequality2);
				}
				if(reset==1){//��һ���ߵ��ñߵıߵļ�������1��ʱ�Ӹ�λ
					ArrayList<Transition> tt=new ArrayList<Transition>();//��ô�ʱ�Ӹ�λ�ıߵ��ñߵļ���
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							for(int j=i;j<Ts.size();j++){
								tt.add(Ts.get(j));
							}
						}
					}
					//��ô�ʱ�Ӹ�λ�ıߵ��ñߵĲ���ʽ
					if(tt.size()>0){//ʱ�Ӹ�λ�ıߵ��ñߵļ����������
						ArrayList<String> label=new ArrayList<String>();//��ô�ʱ�Ӹ�λ�ıߵ��ñߵļ����и����ϵ�ʱ����
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//ʱ��Լ�����Ͻ�
						String upper_label=new String();
						int lower=0;//ʱ��Լ�����½�
						String lower_label=new String();
						
						lower=-DBM[0][1].getValue();
						if(DBM[0][1].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						upper=DBM[1][0].getValue();
						if(DBM[1][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
						
						String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
						String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
				}
				if(reset==2){//��һ���ߵ��ñߵļ�������2��ʱ�Ӹ�λ
					ArrayList<Transition> tt=new ArrayList<Transition>();//��ôӵڶ���ʱ�Ӹ�λ�ıߵ��ñߵļ���
					int r=0;
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							r=r+1;
							if(r==2){
								for(int j=i;j<Ts.size();j++){
									tt.add(Ts.get(j));
								}
							}
							
						}
					}
					//��ôӵڶ���ʱ�Ӹ�λ�ıߵ��ñߵĲ���ʽ
					if(tt.size()>0){//ʱ�Ӹ�λ�ıߵ��ñߵļ����������
						ArrayList<String> label=new ArrayList<String>();//��ô�ʱ�Ӹ�λ�ıߵ��ñߵļ����и����ϵ�ʱ����
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//ʱ��Լ�����Ͻ�
						String upper_label=new String();
						int lower=0;//ʱ��Լ�����½�
						String lower_label=new String();
						
						lower=-DBM[0][1].getValue();
						if(DBM[0][1].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						upper=DBM[1][0].getValue();
						if(DBM[1][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
						
						String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
						String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
				}
				
			}
		}
		//����壺������������2��ʱ�ӣ�2��ʱ�Ӹ�λ
		if(resetNumber==2&&clock_number==2){
			int k1=0;//��õ�һ������λ��ʱ����ʱ�Ӽ����е�λ��
			int k2=0;//��õڶ�������λ��ʱ����ʱ�Ӽ����е�λ��
			ArrayList<Transition> u=new ArrayList<Transition>();
			for(Transition ts:TransitionSet){
				if(ts.getResetClockSet().size()!=0){
					u.add(ts);
				}
			}
			for(int i=0;i<clockset.size();i++){
				if(u.get(0).equals(clockset.get(i))){
					k1=i;
					break;
				}
			}
			for(int i=0;i<clockset.size();i++){
				if(u.get(1).equals(clockset.get(i))){
					k2=i;
					break;
				}
			}
			
			for(Transition t1:TransitionSet){
				ArrayList<Transition> Ts=new ArrayList<Transition>();//��ȡ����·���е�һ���ߵ��ñߵı߼���
				for(Transition t2:TransitionSet){
					if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
						Ts.add(t2);
					}
					if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
						break;
					}
				}
				Ts.add(t1);
				
				
				DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//���t1Ŀ��״̬��ʱ��Լ������
				for(State s:StateSet){
					if(t1.getTarget().equals(s.getName())){
						DBM=Floyds.floyds(s.getInvariantDBM());
					}
				}
				
				int reset=0;//��ôӵ�һ���ߵ��ñߵ�·�����м���ʱ�Ӹ�λ		
				for(Transition ts:Ts){
					  if(ts.getResetClockSet().size()!=0){
						  reset++;
					  }
				}
				
				if(reset==0){//��һ���ߵ��ñߵıߵļ�����û��ʱ�Ӹ�λ
					ArrayList<String> label=new ArrayList<String>();//���Ts�и����ϵ�ʱ����
					for(Transition ts:Ts){
						label.add(ts.getEventSet().get(1));
					}
					int upper=0;//ʱ��Լ�����Ͻ�
					String upper_label=new String();
					int lower=0;//ʱ��Լ�����½�
					String lower_label=new String();
					
					lower=-DBM[0][1].getValue();
					if(DBM[0][1].isStrictness()==false){
						lower_label=">";
					}
					else lower_label=">=";
					upper=DBM[1][0].getValue();
					if(DBM[1][0].isStrictness()==false){
						upper_label="<";
					}
					else upper_label="<=";
					
					String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
					String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
					for(int i=0;i<label.size();i++){
						if(i!=label.size()-1){
							Inequality1=Inequality1+label.get(i)+"+";
							Inequality2=Inequality2+label.get(i)+"+";
						}
						else{
							Inequality1=Inequality1+label.get(i);
							Inequality2=Inequality2+label.get(i);
						}
					}
					
					if(upper!=88888){
						Inequality1=Inequality1+upper_label+upper;
						Inequalitys.add(Inequality1);
					}
					Inequality2=Inequality2+lower_label+lower;
					Inequalitys.add(Inequality2);
				}
				if(reset==1){//��һ���ߵ��ñߵıߵļ�����1��ʱ�Ӹ�λ
					ArrayList<Transition> tt=new ArrayList<Transition>();//��ô�ʱ�Ӹ�λ����һ���ߵ��ñߵļ���
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							for(int j=i+1;j<Ts.size();j++){
								tt.add(Ts.get(j));
							}
						}
					}
					//��ô�ʱ�Ӹ�λ����һ���ߵ��ñߵĲ���ʽ
					if(tt.size()>0){//ʱ�Ӹ�λ����һ���ߵ��ñߵļ����������
						ArrayList<String> label=new ArrayList<String>();//��ô�ʱ�Ӹ�λ����һ���ߵ��ñߵļ����и����ϵ�ʱ����
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//ʱ��Լ�����Ͻ�
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//ʱ��Լ�����½�
						if(k1==0){//x����λ��ȡx�����½�
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k1==1){//y����λ��ȡy�����½�
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
						String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
					//��ôӵ�һ���ߵ��ñߵĲ���ʽ
					ArrayList<String> label1=new ArrayList<String>();//���Ts�и����ϵ�ʱ����
					for(Transition ts:Ts){
						label1.add(ts.getEventSet().get(1));
					}
					int upper1=0;//ʱ��Լ�����Ͻ�
					int lower1=0;//ʱ��Լ�����½�
					if(k1==0){//x����λ��ȡy�����½�
						lower1=-DBM[0][2].getValue();
						upper1=DBM[2][0].getValue();
					}
					if(k1==1){//y����λ��ȡx�����½�
						lower1=-DBM[0][1].getValue();
						upper1=DBM[1][0].getValue();
					}
					
					
					String Inequality3=new String();//��ʾ�Ͻ�Ĳ���ʽ
					String Inequality4=new String();//��ʾ�½�Ĳ���ʽ
					for(int i=0;i<label1.size();i++){
						if(i!=label1.size()-1){
							Inequality3=Inequality3+label1.get(i)+"+";
							Inequality4=Inequality4+label1.get(i)+"+";
						}
						else{
							Inequality3=Inequality3+label1.get(i);
							Inequality4=Inequality4+label1.get(i);
						}
					}
					
					if(upper1!=88888){
						Inequality3=Inequality3+"<"+upper1;
						Inequalitys.add(Inequality3);
					}
					Inequality4=Inequality4+">"+lower1;
					Inequalitys.add(Inequality4);
					
				}
				if(reset==2){//��һ���ߵ��ñߵıߵļ�����1��ʱ�Ӹ�λ
				if(k1==k2){//����λ������ʱ�ӷֱ�Ϊxx��yy
					ArrayList<Transition> tt=new ArrayList<Transition>();//��ôӵڶ���ʱ�Ӹ�λ����һ���ߵ��ñߵļ���
					int r=0;
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							r=r+1;
							if(r==2){
								for(int j=i+1;j<Ts.size();j++){
									tt.add(Ts.get(j));
								}
							}
							
						}
					}
					//��ôӵڶ���ʱ�Ӹ�λ����һ���ߵ��ñ߲���ʽ
					if(tt.size()>0){//�ڶ���ʱ�Ӹ�λ�ĵ���һ���ߵ��ñߵļ����������
						ArrayList<String> label=new ArrayList<String>();//��ôӵڶ���ʱ�Ӹ�λ�ļ����и����ϵ�ʱ����
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//ʱ��Լ�����Ͻ�
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//ʱ��Լ�����½�
						if(k2==0){//x����λ��ȡx�����½�
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k2==1){//y����λ��ȡy�����½�
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
						String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
					//��ôӵ�һ���ߵ��ñߵĲ���ʽ
					ArrayList<String> label1=new ArrayList<String>();//���Ts�и����ϵ�ʱ����
					for(Transition ts:Ts){
						label1.add(ts.getEventSet().get(1));
					}
					int upper1=0;//ʱ��Լ�����Ͻ�
					int lower1=0;//ʱ��Լ�����½�
					if(k1==0){//x����λ��ȡy�����½�
						lower1=-DBM[0][2].getValue();
						upper1=DBM[2][0].getValue();
					}
					if(k1==1){//y����λ��ȡx�����½�
						lower1=-DBM[0][1].getValue();
						upper1=DBM[1][0].getValue();
					}
					
					
					String Inequality3=new String();//��ʾ�Ͻ�Ĳ���ʽ
					String Inequality4=new String();//��ʾ�½�Ĳ���ʽ
					for(int i=0;i<label1.size();i++){
						if(i!=label1.size()-1){
							Inequality3=Inequality3+label1.get(i)+"+";
							Inequality4=Inequality4+label1.get(i)+"+";
						}
						else{
							Inequality3=Inequality3+label1.get(i);
							Inequality4=Inequality4+label1.get(i);
						}
					}
					
					if(upper1!=88888){
						Inequality3=Inequality3+"<"+upper1;
						Inequalitys.add(Inequality3);
					}
					Inequality4=Inequality4+">"+lower1;
					Inequalitys.add(Inequality4);
				}
				if(k1!=k2){//����λ������ʱ��Ϊxy��yx
					ArrayList<Transition> tt=new ArrayList<Transition>();//��ôӵڶ���ʱ�Ӹ�λ����һ���ߵ��ñߵļ���
					int r=0;
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							r=r+1;
							if(r==2){
								for(int j=i+1;j<Ts.size();j++){
									tt.add(Ts.get(j));
								}
							}
							
						}
					}
					//��ôӵڶ���ʱ�Ӹ�λ����һ���ߵ��ñ߲���ʽ
					if(tt.size()>0){//�ڶ���ʱ�Ӹ�λ�ĵ���һ���ߵ��ñߵļ����������
						ArrayList<String> label=new ArrayList<String>();//��ôӵڶ���ʱ�Ӹ�λ�ļ����и����ϵ�ʱ����
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//ʱ��Լ�����Ͻ�
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//ʱ��Լ�����½�
						if(k2==0){//x����λ��ȡx�����½�
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k2==1){//y����λ��ȡy�����½�
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
						String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
					
					//
					ArrayList<Transition> tt1=new ArrayList<Transition>();//��ôӵ�һ��ʱ�Ӹ�λ����һ���ߵ��ñߵļ���
					int r1=0;
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							r1=i;
						}
					}
					for(int j=r1+1;j<Ts.size();j++){
						tt.add(Ts.get(j));
					}
					//��ôӵ�һ��ʱ�Ӹ�λ����һ���ߵ��ñ߲���ʽ
					if(tt1.size()>0){//��һ��ʱ�Ӹ�λ�ĵ���һ���ߵ��ñߵļ����������
						ArrayList<String> label=new ArrayList<String>();//��ôӵ�һ��ʱ�Ӹ�λ�ļ����и����ϵ�ʱ����
						for(Transition ts:tt1){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//ʱ��Լ�����Ͻ�
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//ʱ��Լ�����½�
						if(k1==0){//x����λ��ȡx�����½�
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k1==1){//y����λ��ȡy�����½�
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
						String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
				}
				
				}
			}
		}
		
		return Inequalitys;
		
	}
	/**
	 * ���һ��û��ʱ�Ӹ�λ�Ĳ������еĲ���ʽ��
	 * @param TransitionSet
	 * @param StateSet
	 * @param clock_number
	 * @param Inequalitys
	 * @return
	 */
	public static ArrayList<String> noReset(ArrayList<Transition> TransitionSet,ArrayList<State> StateSet,int clock_number,ArrayList<String> Inequalitys){
		for(Transition t1:TransitionSet){
			ArrayList<Transition> Ts=new ArrayList<Transition>();//��ȡ����·���е�һ���ߵ��ñߵı߼���
			for(Transition t2:TransitionSet){
				if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
					Ts.add(t2);
				}
				if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
					break;
				}
			}
			Ts.add(t1);
			ArrayList<String> label=new ArrayList<String>();//���Ts�и����ϵ�ʱ����
			for(Transition ts:Ts){
				label.add(ts.getEventSet().get(1));
			}
			
			DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//���t1Ŀ��״̬��ʱ��Լ������
			for(State s:StateSet){
				if(t1.getTarget().equals(s.getName())){
					DBM=Floyds.floyds(s.getInvariantDBM());
				}
			}
				
			int upper=0;//ʱ��Լ�����Ͻ�
			String upper_label=new String();
			int lower=0;//ʱ��Լ�����½�
			String lower_label=new String();
			
			lower=-DBM[0][1].getValue();
			if(DBM[0][1].isStrictness()==false){
				lower_label=">";
			}
			else lower_label=">=";
			
			upper=DBM[1][0].getValue();
			if(DBM[1][0].isStrictness()==false){
				upper_label="<";
			}
			else upper_label="<=";
			
			String Inequality1=new String();//��ʾ�Ͻ�Ĳ���ʽ
			String Inequality2=new String();//��ʾ�½�Ĳ���ʽ
			for(int i=0;i<label.size();i++){
				if(i!=label.size()-1){
					Inequality1=Inequality1+label.get(i)+"+";
					Inequality2=Inequality2+label.get(i)+"+";
				}
				else{
					Inequality1=Inequality1+label.get(i);
					Inequality2=Inequality2+label.get(i);
				}
			}
			
			if(upper!=88888){
				Inequality1=Inequality1+upper_label+upper;
				Inequalitys.add(Inequality1);
			}
			Inequality2=Inequality2+lower_label+lower;
			Inequalitys.add(Inequality2);
			
		}
		return Inequalitys;
	}
	
	public static void main(String[] args) {
		Automatic automatic=Test_split_01_new.getAutomatic();
		Automatic newAutomatic=IPR.iPR(automatic);
		Automatic aTDRTAutomatic=ATDTR.aTDRT(newAutomatic,automatic);  
		//Automatic aaa=DFSTree(aTDRTAutomatic);
		ArrayList<Automatic> testcaseSet=StateCoverage.testCase(aTDRTAutomatic);
		Automatic auto0=testcaseSet.get(0);
		ArrayList<String> Inequalitys0=get_Inequalitys(auto0);
		System.out.println("t3<2");
		for(int i=1;i<Inequalitys0.size();i++){
			if(i!=10){
				System.out.println(Inequalitys0.get(i));
			}
			else System.out.println("t4+t2+t8>=2");
		}
		Automatic auto1=testcaseSet.get(1);
		ArrayList<String> Inequalitys1=get_Inequalitys(auto1);
		System.out.println("--------------------");
		System.out.println("t3<2");
		for(int i=1;i<Inequalitys1.size();i++){
			System.out.println(Inequalitys1.get(i));
		}
	}
}
