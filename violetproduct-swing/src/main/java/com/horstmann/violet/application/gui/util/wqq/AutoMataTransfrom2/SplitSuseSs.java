package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class SplitSuseSs {
	public static ArrayList<State> splitSuseSs(State x,ArrayList<State> Ps,ArrayList<Transition> trans,ArrayList<String> ClockSet) {
		ArrayList<State> States=new ArrayList<State>();
		String x_position=x.getPosition();//��ȡx�ı�־λ
		String x_name=x.getName();//��ȡ״̬x������
		DBM_element x_DBM[][] =x.getInvariantDBM();//��ȡ״̬x�Ĳ���ʽ
		
		ArrayList<State> posts=PostAndPre.post(x, Ps, trans, ClockSet);//��ȡx�ĺ�̼���
		updatePosts(x, posts, trans, ClockSet);//��x�ĺ���Ǽ���ʱ�Ӹ�λ��Ϣ
		
		ArrayList<DBM_element[][]> Zs=getZs(x, posts, trans, ClockSet);//���ݺ�̣���ȡ���x��Zs
		ArrayList<DBM_element[][]> X_DBMs=SplitZuseZs_2.splitZuseZs(x_DBM, Zs);
		
		int size=X_DBMs.size();
		if(size==1){//xû�б����
			States.add(x);
			return States;
		}
		else{//x�����Ϊ���״̬
			for(int i=0;i<size;i++){
				State s=new State();
				s.setPosition(x_position);
				s.setInvariantDBM(X_DBMs.get(i));//״̬�Ĳ���ʽΪX_DBMs.get(i)
				s.setName(x_name+i);//״̬������Ϊԭ���Ƽ�i
				States.add(s);
			}
			return States;
		}
	}
	
	/**
	 * ��x�ĺ��posts����ʱ�Ӹ�λ��Ϣ
	 * @param x
	 * @param posts
	 * @param trans
	 * @param ClockSet
	 */
	public static void updatePosts(State x,ArrayList<State> posts,ArrayList<Transition> trans,ArrayList<String> ClockSet) {
		String x_position=x.getPosition();//��ȡx�ı�־λ
		String x_name=x.getName();//��ȡ״̬x������
		DBM_element x_DBM[][] =x.getInvariantDBM();//��ȡ״̬x�Ĳ���ʽ
		
		for(State p:posts){
			String p_position=p.getPosition();//��ȡp�ı�־λ
			String p_name=p.getName();//��ȡ״̬p������
			DBM_element p_DBM[][] =p.getInvariantDBM();//��ȡ״̬p�Ĳ���ʽ
			
			for(Transition t:trans){
				DBM_element t_DBM[][]=t.getConstraintDBM();//��ȡǨ��t�ϵ�ʱ��Լ��
				String clock=null;
				if(t.getResetClockSet()!=null){
					clock=t.getResetClockSet().get(0);//��ȡǨ��t�ϵ�ʱ�Ӹ�λ��Ϣ
				}		
				if(t.getSource().equals(x_name)&&t.getTarget().equals(p_name)){//����x-->p
					if(clock!=null){//Ǩ������ʱ�Ӹ�λ��Ϣ
						DBM_element[][] updateclock=updateClok(x_DBM, t_DBM, p_DBM, ClockSet, clock);
						p.setAddClockRelationDBM(updateclock);
					}
					else{//Ǩ����û��ʱ�Ӹ�λ��Ϣ
						DBM_element[][] updateclock=updateClok_1(x_DBM, p_DBM);
						p.setAddClockRelationDBM(updateclock);
					}
					
				}
				else{//������x-->p
					if(t.getSource().equals(x_position)&&t.getTarget().equals(p_position)){//����x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							DBM_element[][] updateclock=updateClok_1(x_DBM, p_DBM);
							p.setAddClockRelationDBM(updateclock);
						}
						else{//x.position!=p.position
							if(clock!=null){//Ǩ������ʱ�Ӹ�λ��Ϣ
								DBM_element[][] updateclock=updateClok(x_DBM, t_DBM, p_DBM, ClockSet, clock);
								p.setAddClockRelationDBM(updateclock);
							}
							else{//Ǩ����û��ʱ�Ӹ�λ��Ϣ
								DBM_element[][] updateclock=updateClok_1(x_DBM, p_DBM);
								p.setAddClockRelationDBM(updateclock);
							}	
						}
					}
					else{//������x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							DBM_element[][] updateclock=updateClok_1(x_DBM, p_DBM);
							p.setAddClockRelationDBM(updateclock);
						}
					}
				}
			}		
		}
		
	}
	
	/**
	 * ��a(Z��z),��ȡ����ʱ�Ӽ��ϵ��������ʱ�Ӽ��ϵ����Zone
	 * @param Z
	 * @param z
	 * @param Zone
	 * @param ClockSet
	 * @param clock
	 * @return
	 */
	public static DBM_element[][] updateClok(DBM_element[][] Z,DBM_element[][] z,DBM_element[][] Zone,ArrayList<String> ClockSet,String clock) {
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(Z), Floyds.floyds(z)))==-1){//Z��z��Ϊ��
			DBM_element[][]and=AndDBM.andDBM(Z, z);
			DBM_element[][] reset_and=Reset_1.reset(and, ClockSet, clock);
			if(reset_and!=null){//ʱ�Ӹ�λ��Ϊ��
				DBM_element[][] exe_reset_and=ExtractReset.extract(reset_and);
				if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(exe_reset_and), Floyds.floyds(Zone)))==-1){//��ȡ��ʱ�Ӹ�λ��Ϣ��Zone��Ϊ��
					return AndDBM.andDBM(Zone, exe_reset_and);
				}
				else return Zone;
			}
			else return Zone;
		}
		else return Zone;	
	}
	

	/**
	 * ��ȡZ��ʱ�Ӽ��ϵ��������ʱ�Ӽ��ϵ����Zone
	 * @param Z
	 * @param Zone
	 * @return
	 */
	public static DBM_element[][] updateClok_1(DBM_element[][] Z,DBM_element[][] Zone) {
		DBM_element[][] exe_Z=ExtractReset.extract(Z);
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(exe_Z), Floyds.floyds(Zone)))==-1){
			return AndDBM.andDBM(Zone, exe_Z);
		}
		else return Zone;
	}
	
	
	/**
	 * ��z��a-1(Zone),����aΪ�յ����
	 * @param z ���ϵ�ʱ��Լ��
	 * @param Zone ״̬�еĲ���ʽ
	 * @param Clocks
	 * @param clock ���ϵĸ�λʱ��
	 * @return
	 */
	public static DBM_element[][] difference(DBM_element[][] z,DBM_element[][] Zone,ArrayList<String> Clocks,String clock) {
		if(clock==null){//����û��ʱ�Ӹ�λ
			if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(z), Floyds.floyds(Zone)))==-1){
				DBM_element[][] and=AndDBM.andDBM(z, Zone);
				return and;
			}
			else return null;
		}
		else{//������ʱ�Ӹ�λ
			DBM_element[][] befor=BeforeReset.beforeReset(Zone, Clocks, clock);
			if(befor!=null){
				if((IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(z), Floyds.floyds(befor))))==-1){
					DBM_element[][] and=AndDBM.andDBM(z, befor);
					return and;
				}
				return null;
			}
			else return null;
		}
	}
	
	
	public static ArrayList<DBM_element[][]> getZs(State x,ArrayList<State> posts,ArrayList<Transition> trans,ArrayList<String> ClockSet){
		ArrayList<DBM_element[][]> Zs=new ArrayList<DBM_element[][]>();
		
		String x_position=x.getPosition();//��ȡx�ı�־λ
		String x_name=x.getName();//��ȡ״̬x������
		DBM_element x_DBM[][] =x.getInvariantDBM();//��ȡ״̬x�Ĳ���ʽ
		
		for(State p:posts){
			String p_position=p.getPosition();//��ȡp�ı�־λ
			String p_name=p.getName();//��ȡ״̬p������
			DBM_element p_DBM[][] =p.getAddClockRelationDBM();//��ȡ״̬p�������ʱ�Ӹ�λ��Ϣ��ʱ��Լ��
			
			for(Transition t:trans){
				DBM_element t_DBM[][]=t.getConstraintDBM();//��ȡǨ��t�ϵ�ʱ��Լ��
				String clock=null;
				if(t.getResetClockSet()!=null){
					clock=t.getResetClockSet().get(0);//��ȡǨ��t�ϵ�ʱ�Ӹ�λ��Ϣ
				}
				if(t.getSource().equals(x_name)&&t.getTarget().equals(p_name)){//����x-->p
					DBM_element[][] zi=difference(t_DBM, p_DBM, ClockSet, clock);//��t_DBM �� a-1(p_DBM)
					if(IsEmpty.isEmpty(zi)==-1){
						Zs.add(zi);
					}
					
				}
				else{//������x-->p
					if(t.getSource().equals(x_position)&&t.getTarget().equals(p_position)){//����x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							DBM_element[][] zi=Godirectly_2.goDirectly(x_DBM, p_DBM);//��x_DBM �������� p_DBM
							if(IsEmpty.isEmpty(zi)==-1){
								Zs.add(zi);
							}
						}
						else{//x.position!=p.position
							DBM_element[][] zi=difference(t_DBM, p_DBM, ClockSet, clock);//��t_DBM �� a-1(p_DBM)
							if(IsEmpty.isEmpty(zi)==-1){
								Zs.add(zi);
							}
						}
					}
					else{//������x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							DBM_element[][] zi=Godirectly_2.goDirectly(x_DBM, p_DBM);//��x_DBM �������� p_DBM
							if(IsEmpty.isEmpty(zi)==-1){
								Zs.add(zi);
							}
						}
					}
				}
			}		
		}
		return Zs;
	}
		
}
 