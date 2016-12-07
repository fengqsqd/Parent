package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class PostAndPre_old {
	public static void main(String[] args) {
		//����a(Z��z)��Zone
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("x<=y");
		ar1.add("x>=y");
		ar1.add("x<2");
		ar1.add("x>1");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		
		ArrayList<String> ar3 =new ArrayList<String>();
		ar3.add("y<=2");
		ar3.add("x-y<2");
		ar3.add("x-y>1");
		DBM_element DBM3[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar3));
		
		DBM_element[][] fDBM1=Floyds.floyds(DBM1);
		DBM_element[][] fDBM2=Floyds.floyds(DBM2);
		DBM_element[][] fDBM3=Floyds.floyds(DBM3);
		
		String clock="y";
		DBM_element[][] judgePost=judgePost(DBM1, DBM2, DBM3, Clocks, clock);
		DBM_element[][] fjudgePost=Floyds.floyds(judgePost);
		
		//����post
		State x=new State();
		x.setName("l0");
		x.setInvariantDBM(DBM1);
		x.setPosition("l0");
		
		ArrayList<State> ps = new ArrayList<State>();
		State p=new State();
		p.setName("l1");
		p.setInvariantDBM(DBM3);
		p.setPosition("l1");
		ps.add(p);
		
		ArrayList<Transition> trans=new ArrayList<Transition>();
		Transition t=new Transition();
		t.setConstraintDBM(DBM2);
		ArrayList<String> ResetClocks=new ArrayList<String>();
		ResetClocks.add(clock);
		t.setResetClockSet(ResetClocks);
		t.setSource("l0");
		t.setTarget("l1");
		
		ArrayList<State> posts=post(x, ps, trans, Clocks);
		System.out.println(posts.size());
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fjudgePost[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				
								
			}
		}
	}
	/**
	 * ��ȡ״̬x��״̬��P�еĺ�̼���
	 * @param x
	 * @param ps
	 * @param trans
	 * @param ClockSet
	 * @return
	 */
	public static ArrayList<State> post(State x,ArrayList<State> ps,ArrayList<Transition> trans,ArrayList<String> ClockSet) {
		ArrayList<State> posts=new ArrayList<State>();//��̼���
		String x_position=x.getPosition();//��ȡx�ı�־λ
		String x_name=x.getName();//��ȡ״̬x������
		DBM_element x_DBM[][] =x.getInvariantDBM();//��ȡ״̬x�Ĳ���ʽ
		
		for(State p:ps){
			String p_position=p.getPosition();//��ȡp�ı�־λ
			String p_name=p.getName();//��ȡ״̬p������
			DBM_element p_DBM[][] =p.getInvariantDBM();//��ȡ״̬p�Ĳ���ʽ
			
			for(Transition t:trans){
				DBM_element t_DBM[][]=t.getConstraintDBM();//��ȡǨ��t�ϵ�ʱ��Լ��
				if(t.getSource().equals(x_name)&&t.getTarget().equals(p_name)){//����x-->p
					posts.add(p);
				}
				else{//������x-->p
					if(t.getSource().equals(x_position)&&t.getTarget().equals(p_position)){//����x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							if(Godirectly_2.goDirectly(x_DBM, p_DBM)!=null){posts.add(p);}	
						}
						else{//x.position!=p.position
							if(t.getResetClockSet()!=null){
								if(judgePost(x_DBM, t_DBM, p_DBM, ClockSet, t.getResetClockSet().get(0))!=null){posts.add(p);}
							}
							else{judgePost_1(x_DBM, t_DBM, p_DBM);}		
						}
					}
					else{//������x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							if(Godirectly_2.goDirectly(x_DBM, p_DBM)!=null){posts.add(p);}	
						}
					}
				}
			}		
		}
		return posts;
	}
	
	
	/**
	 * ��ȡ״̬x��״̬��P�е�ǰ������
	 * @param x
	 * @param ps
	 * @param trans
	 * @param ClockSet
	 * @return
	 */
	public static ArrayList<State> pre(State x,ArrayList<State> ps,ArrayList<Transition> trans,ArrayList<String> ClockSet) {
		ArrayList<State> posts=new ArrayList<State>();//��̼���
		String x_position=x.getPosition();//��ȡx�ı�־λ
		String x_name=x.getName();//��ȡ״̬x������
		DBM_element x_DBM[][] =x.getInvariantDBM();//��ȡ״̬x�Ĳ���ʽ
		
		for(State p:ps){
			String p_position=p.getPosition();//��ȡp�ı�־λ
			String p_name=p.getName();//��ȡ״̬p������
			DBM_element p_DBM[][] =p.getInvariantDBM();//��ȡ״̬p�Ĳ���ʽ
			
			for(Transition t:trans){
				DBM_element t_DBM[][]=t.getConstraintDBM();//��ȡǨ��t�ϵ�ʱ��Լ��
				if(t.getSource().equals(p_name)&&t.getTarget().equals(x_name)){
					posts.add(p);
				}
				else{
					if(t.getSource().equals(p_position)&&t.getTarget().equals(x_position)){
						if(x_position.equals(p_position)){
							if(Godirectly_2.goDirectly(p_DBM, x_DBM)!=null){posts.add(p);}	
						}
						else{
							if(t.getResetClockSet()!=null){
								if(judgePost(p_DBM, t_DBM, x_DBM, ClockSet, t.getResetClockSet().get(0))!=null){posts.add(p);}
							}
							else{judgePost_1(p_DBM, t_DBM, x_DBM);}		
						}
					}
					else{
						if(x_position.equals(p_position)){
							if(Godirectly_2.goDirectly(p_DBM, x_DBM)!=null){posts.add(p);}	
						}
					}
				}
			}		
		}
		return posts;
	}
	
	
	
	/**
	 * ��a(Z��z)��Zone
	 * @param Z
	 * @param z
	 * @param Zone
	 * @param ClockSet
	 * @param clock
	 * @return
	 */
	public static DBM_element[][] judgePost(DBM_element[][] Z,DBM_element[][] z,DBM_element[][] Zone,ArrayList<String> ClockSet,String clock) {
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(Z), Floyds.floyds(z)))==1){
			return null;
		}
		else{
			DBM_element[][] and=AndDBM.andDBM(Z, z);
			DBM_element[][] reset=Reset_1.reset(and, ClockSet, clock);
			if(reset!=null){
				if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(reset), Floyds.floyds(Zone)))==-1){
					return AndDBM.andDBM(reset, Zone);
				}
				else return null;
				
			}
			else return null;
		}
	}
	/**
	 * ��Z��z��Zone
	 * @param Z
	 * @param z
	 * @param Zone
	 * @return
	 */
	public static DBM_element[][] judgePost_1 (DBM_element[][] Z,DBM_element[][] z,DBM_element[][] Zone) {
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(Z), Floyds.floyds(z)))==1){
			return null;
		}
		else{
			DBM_element[][] and=AndDBM.andDBM(Z, z);
			if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(and), Floyds.floyds(Zone)))==-1){
				return AndDBM.andDBM(and, Zone);
			}
			else return null;
		}
	}
}
