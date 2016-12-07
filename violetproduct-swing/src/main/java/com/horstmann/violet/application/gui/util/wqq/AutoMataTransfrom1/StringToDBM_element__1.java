package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1;

import java.util.ArrayList;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.DBM_element;

public class StringToDBM_element__1 {
	/**
	 * �µķ������ɵķ���Ҳ����
	 * @param ClockSet
	 * @param constraintANDinvariant
	 * @return
	 */
	public static ArrayList<DBM_element> stringToDBM_element(ArrayList<String> ClockSet,ArrayList<String> constraintANDinvariant){
		ArrayList<DBM_element> constraints=new ArrayList<DBM_element>();//һ��ArrayList<DBM_element>��Ӧһ��DBM
		
		for(String cons:constraintANDinvariant){
			//��һ�����x-y<=9
			if(cons.contains("<=")&&cons.contains("-")){
				String[] s1=cons.split("<=");
				int value=Integer.parseInt(s1[1]);//ֵ
				String[] s2=s1[0].split("-");
				String clock1=s2[0];//��һ��ʱ��
				String clock2=s2[1];//�ڶ���ʱ��
				
				DBM_element constraint =new DBM_element();
				constraint.setValue(value);
				constraint.setStrictness(true);
				
				for(int i=0;i<ClockSet.size();i++){
					if(clock1.equals(ClockSet.get(i))){
						constraint.setDBM_i(i+1);
					}
					if(clock2.equals(ClockSet.get(i))){
						constraint.setDBM_j(i+1);
					}
				}	
				constraints.add(constraint);
			}
			//�ڶ������x-y<9
			else if(cons.contains("<")&&cons.contains("-")){
				String[] s1=cons.split("<");
				int value=Integer.parseInt(s1[1]);//ֵ
				String[] s2=s1[0].split("-");
				String clock1=s2[0];//��һ��ʱ��
				String clock2=s2[1];//�ڶ���ʱ��
				
				DBM_element constraint =new DBM_element();
				constraint.setValue(value);
				constraint.setStrictness(false);
				
				for(int i=0;i<ClockSet.size();i++){
					if(clock1.equals(ClockSet.get(i))){
						constraint.setDBM_i(i+1);
					}
					if(clock2.equals(ClockSet.get(i))){
						constraint.setDBM_j(i+1);
					}
				}	
				constraints.add(constraint);
			}
			//���������x<=9
			else if(cons.contains("<=")){
				String[] s1=cons.split("<=");
				int value=Integer.parseInt(s1[1]);//ֵ
				String clock=s1[0];//ʱ��
				
				DBM_element constraint =new DBM_element();
				constraint.setValue(value);
				constraint.setStrictness(true);
				
				for(int i=0;i<ClockSet.size();i++){
					if(clock.equals(ClockSet.get(i))){
						constraint.setDBM_i(i+1);
						constraint.setDBM_j(0);
					}
				}	
				constraints.add(constraint);
			}
			//���������x>=9
			else if(cons.contains(">=")){
				String[] s1=cons.split(">=");
				int value=Integer.parseInt(s1[1]);//ֵ
				String clock=s1[0];//ʱ��
				
				DBM_element constraint =new DBM_element();
				constraint.setValue(-value);
				constraint.setStrictness(true);
				
				for(int i=0;i<ClockSet.size();i++){
					if(clock.equals(ClockSet.get(i))){
						constraint.setDBM_i(0);
						constraint.setDBM_j(i+1);
					}
				}	
				constraints.add(constraint);
			}
			//���������x<9
			else if(cons.contains("<")){
				String[] s1=cons.split("<");
				int value=Integer.parseInt(s1[1]);//ֵ
				String clock=s1[0];//ʱ��
				
				DBM_element constraint =new DBM_element();
				constraint.setValue(value);
				constraint.setStrictness(false);
				
				for(int i=0;i<ClockSet.size();i++){
					if(clock.equals(ClockSet.get(i))){
						constraint.setDBM_i(i+1);
						constraint.setDBM_j(0);
					}
				}	
				constraints.add(constraint);
			}
			//���������x>9
			else if(cons.contains(">")){
				String[] s1=cons.split(">");
				int value=Integer.parseInt(s1[1]);//ֵ
				String clock=s1[0];//ʱ��
				
				DBM_element constraint =new DBM_element();
				constraint.setValue(-value);
				constraint.setStrictness(false);
				
				for(int i=0;i<ClockSet.size();i++){
					if(clock.equals(ClockSet.get(i))){
						constraint.setDBM_i(0);
						constraint.setDBM_j(i+1);
					}
				}	
				constraints.add(constraint);
			}
		}
		
		/*for(String cons:constraintANDinvariant){
			int len=cons.length();
			
			if(len==3){
				String operator=cons.substring(1, 2);//���Լ���������
				String clock=cons.substring(0, 1);//���Լ���е�ʱ��
				
				DBM_element constraint =new DBM_element();
				//x<2
				if(operator.equals("<")){
					int value=Integer.parseInt(cons.substring(2, 3));
					constraint.setValue(value);
					constraint.setStrictness(false);		
					for(int i=0;i<ClockSet.size();i++){
						if(clock.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
							constraint.setDBM_j(0);
						}
					}	
					constraints.add(constraint);
				}
				//x>2
				if(operator.equals(">")){
					int value=Integer.parseInt(cons.substring(2, 3));
					constraint.setValue(-value);
					constraint.setStrictness(false);		
					for(int i=0;i<ClockSet.size();i++){
						if(clock.equals(ClockSet.get(i))){
							constraint.setDBM_i(0);
							constraint.setDBM_j(i+1);
						}
					}	
					constraints.add(constraint);
				}
			}
			
			if(len==4){
				String operator=cons.substring(1, 3);//���Լ��������� <=,>=
				String clock=cons.substring(0, 1);//���Լ���е�ʱ��
				
				DBM_element constraint =new DBM_element();
				//x<=2 x<=y
				if(operator.equals("<=")){
					if(isNumeric(cons.substring(3, 4))==1){
						int value=Integer.parseInt(cons.substring(3, 4));
						constraint.setValue(value);
						constraint.setStrictness(true);		
						for(int i=0;i<ClockSet.size();i++){
							if(clock.equals(ClockSet.get(i))){
								constraint.setDBM_i(i+1);
								constraint.setDBM_j(0);
							}
						}
					}
					else{
						String var=cons.substring(3, 4);
						constraint.setValue(0);
						constraint.setStrictness(true);		
						for(int i=0;i<ClockSet.size();i++){
							if(clock.equals(ClockSet.get(i))){
								constraint.setDBM_i(i+1);
							}
						}
						for(int j=0;j<ClockSet.size();j++){
							if(var.equals(ClockSet.get(j))){
								constraint.setDBM_j(j+1);
							}
						}
					}
					
					constraints.add(constraint);
				}
				//x>=2 x>=y
				if(operator.equals(">=")){
					if(isNumeric(cons.substring(3, 4))==1){
						int value=Integer.parseInt(cons.substring(3, 4));
						constraint.setValue(-value);
						constraint.setStrictness(true);		
						for(int i=0;i<ClockSet.size();i++){
							if(clock.equals(ClockSet.get(i))){
								constraint.setDBM_i(0);
								constraint.setDBM_j(i+1);
							}
						}
					}
					else{
						String var=cons.substring(3, 4);
						constraint.setValue(0);
						constraint.setStrictness(true);		
						for(int j=0;j<ClockSet.size();j++){
							if(clock.equals(ClockSet.get(j))){
								constraint.setDBM_j(j+1);
							}
						}
						for(int i=0;i<ClockSet.size();i++){
							if(var.equals(ClockSet.get(i))){
								constraint.setDBM_i(i+1);
							}
						}
					}
						
					constraints.add(constraint);
				}
			}
			
			//x-y<6
			if(len==5){
				String operator=cons.substring(3, 4);//���Լ��������� <,>
				
				DBM_element constraint =new DBM_element();
				//x-y<6
				if(operator.equals("<")){
					int value=Integer.parseInt(cons.substring(4, 5));
					constraint.setValue(value);
					constraint.setStrictness(false);	
					String clock1=cons.substring(0, 1);//��ȡ��һ��ʱ��
					//System.out.println("clock1:"+clock1);
					String clock2=cons.substring(2, 3);//��ȡ�ڶ���ʱ��
					//System.out.println("clock2:"+clock2);
					
					for(int i=0;i<ClockSet.size();i++){
						if(clock1.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
						}
						if(clock2.equals(ClockSet.get(i))){
							constraint.setDBM_j(i+1);
						}
					}	
					constraints.add(constraint);
				}
				//x-y>6
				if(operator.equals(">")){
					int value=Integer.parseInt(cons.substring(4, 5));
					constraint.setValue(-value);
					constraint.setStrictness(false);	
					String clock1=cons.substring(0, 1);//��ȡ��һ��ʱ��
					//System.out.println("clock1:"+clock1);
					String clock2=cons.substring(2, 3);//��ȡ�ڶ���ʱ��
					//System.out.println("clock2:"+clock2);
					
					for(int i=0;i<ClockSet.size();i++){
						if(clock2.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
						}
						if(clock1.equals(ClockSet.get(i))){
							constraint.setDBM_j(i+1);
						}
					}	
					constraints.add(constraint);
				}
			}
			
			//x-y<=1
			if(len==6){
				String operator=cons.substring(3, 5);//���Լ��������� <,>
				
				DBM_element constraint =new DBM_element();
				//x-y<=1
				if(operator.equals("<=")){
					int value=Integer.parseInt(cons.substring(5, 6));
					constraint.setValue(value);
					constraint.setStrictness(true);	
					String clock1=cons.substring(0, 1);//��ȡ��һ��ʱ��
					//System.out.println("clock1:"+clock1);
					String clock2=cons.substring(2, 3);//��ȡ�ڶ���ʱ��
					//System.out.println("clock2:"+clock2);
					
					for(int i=0;i<ClockSet.size();i++){
						if(clock1.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
						}
						if(clock2.equals(ClockSet.get(i))){
							constraint.setDBM_j(i+1);
						}
					}
					constraints.add(constraint);
				}
				//x-y>=1
				if(operator.equals(">=")){
					int value=Integer.parseInt(cons.substring(5, 6));
					constraint.setValue(-value);
					constraint.setStrictness(true);	
					String clock1=cons.substring(0, 1);//��ȡ��һ��ʱ��
					//System.out.println("clock1:"+clock1);
					String clock2=cons.substring(2, 3);//��ȡ�ڶ���ʱ��
					//System.out.println("clock2:"+clock2);
					
					for(int i=0;i<ClockSet.size();i++){
						if(clock2.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
						}
						if(clock1.equals(ClockSet.get(i))){
							constraint.setDBM_j(i+1);
						}
					}
					constraints.add(constraint);
				}
			}
		}*/
		return constraints;
	}
	
	/**
	 * �ж�һ���ַ��Ƿ�Ϊ����������ǣ�����1��������������0
	 * @param str
	 * @return
	 */
	public static int isNumeric(String str){  
		   for(int i=str.length();--i>=0;){  
		      int chr=str.charAt(i);  
		      if(chr<48 || chr>57)  
		         return 0;  
		   }  
		   return 1;  
		} 
	public static void main(String[] args) {
		//System.out.println(isNumeric("3"));
		ArrayList<String> ClockSet=new ArrayList<String>();
		ClockSet.add("t");
		ArrayList<String> constraintANDinvariant=new ArrayList<String>();
		constraintANDinvariant.add("t<=120");
		//constraintANDinvariant.add("t<=30");
		ArrayList<DBM_element> constraints=stringToDBM_element(ClockSet, constraintANDinvariant);
		constraints.size();
	}
}
