package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;
import java.util.ArrayList;
public class Minimization {
	public static void main(String[] args) {
		Automatic automatic=Test_split_01_new.getAutomatic();
		ArrayList<State> new_stateSet=minimization(automatic);
		System.out.println("��ֺ��״̬������ "+new_stateSet.size());
		for(State s:new_stateSet){
			System.out.println("״̬name: "+s.getName());
			System.out.println("״̬position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
			DBM_element[][] fDBM=Floyds.floyds(DBM);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=fDBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}
		
	}
	/**
	 * ��С���㷨������һ���ȶ���״̬��
	 * @param automatic
	 * @return
	 */
	public static ArrayList<State> minimization(Automatic automatic) {
		State Init_State=automatic.getInitState();//���ʱ���Զ�����ʼ״̬
		ArrayList<State> StateSet=automatic.getStateSet();//���ʱ���Զ���״̬����
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();//���ʱ���Զ���Ǩ�Ƽ���
		ArrayList<String> ClockSet=automatic.getClockSet();//���ʱ���Զ���ʱ�Ӽ���
		
		ArrayList<State> P=new ArrayList<State>();//P����ʱ���Զ�����״̬���ϣ���ֹʱ���Զ�����״̬���ϱ��ı�
		for(State s:StateSet){
			State ss=new State();
			ss.setName(s.getName());
			ss.setPosition(s.getPosition());
			ss.setInvariantDBM(s.getInvariantDBM());
			ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
			P.add(ss);
		}
		
		State s0=new State();//s0Ϊ��һ������ֵ�״̬������ʱ���Զ����ĳ�ʼ״̬����ֹʱ���Զ����ĳ�ʼ״̬���ı�
		s0.setName(Init_State.getName());
		s0.setPosition(Init_State.getPosition());
		s0.setInvariantDBM(Init_State.getInvariantDBM());
		s0.setAddClockRelationDBM(Init_State.getAddClockRelationDBM());
		
		ArrayList<State> accessible=new ArrayList<State>();//�ɴ��״̬����
		accessible.add(s0);
		
		ArrayList<State> stable=new ArrayList<State>();//�ȶ���״̬����
		
		while(accessible.size()!=stable.size()){//���ɴＯ�Ϻ��ȶ����ϲ���ͬʱ
			State x=accessibleNostable(accessible, stable);//ѡȡ�ɴＯ���е�һ�������ȶ����ϵ�״̬���в��
			ArrayList<State> new_X=SplitSuseSs_new1.splitSuseSs(x, P, TransitionSet, ClockSet);//new_xΪx����ֺ��״̬����
			//x��ֺ��״̬����
			/*System.out.println("new_X size: "+new_X.size());
			for(State s:new_X){
				System.out.println("״̬name: "+s.getName());
				System.out.println("״̬position: "+s.getPosition());
				DBM_element[][] DBM=s.getInvariantDBM();
				DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						DBM_element cons=add_clock_DBM[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());									
					}
				}
				System.out.println("*******************************");
			}*/
			if(new_X.size()==1){//���xû�б����
				
				State x_copy=new State();
				x_copy.setName(x.getName());
				x_copy.setPosition(x.getPosition());
				x_copy.setInvariantDBM(x.getInvariantDBM());
				x_copy.setAddClockRelationDBM(x.getAddClockRelationDBM());
				stable.add(x_copy);//��x�����ȶ���(�ȶ����п϶�û��x)
				
				ArrayList<State> posts=PostAndPre.post(x, P, TransitionSet, ClockSet);//��ȡx�ĺ��
				//���x�ĺ��
				/*System.out.println("posts size: "+posts.size());
				for(State s:posts){
					System.out.println("״̬name: "+s.getName());
					System.out.println("״̬position: "+s.getPosition());
					DBM_element[][] DBM=s.getInvariantDBM();
					DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
					for(int i=0;i<3;i++){
						for(int j=0;j<3;j++){
							DBM_element cons=add_clock_DBM[i][j];
							//System.out.println("DBM_i:"+cons.getDBM_i());
							//System.out.println("DBM_j:"+cons.getDBM_j());
							System.out.println("value:"+cons.getValue());
							System.out.println("Strictness:"+cons.isStrictness());									
						}
					}
					System.out.println("*******************************");
				}*/
				ArrayList<State> diff=posts_differ_access(accessible, posts);//��ú�̼�����ɴＯ��ͬ��״̬����
				for(State s:diff){//����̼���ɴＯ
					State ss=new State();
					ss.setName(s.getName());
					ss.setPosition(s.getPosition());
					ss.setInvariantDBM(s.getInvariantDBM());
					ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
					accessible.add(ss);
				}
			}
			else{//x�����
				for(int i=0;i<accessible.size();i++){//�ɴＯ��ɾ��x
					if(accessible.get(i).getName().equals(x.getName())){
						accessible.remove(i);
					}
				}
				for(State s:new_X){//�����ֵõ���״̬�а�����㣬�����ɴＯ
					DBM_element[][] zone=s.getInvariantDBM();
					if(includeZero(zone)==1){
						State ss=new State();
						ss.setName(s.getName());
						ss.setPosition(s.getPosition());
						ss.setInvariantDBM(s.getInvariantDBM());
						ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
						accessible.add(ss);
					}
				}
				ArrayList<State> pres=PostAndPre.pre(x, P, TransitionSet, ClockSet);//���x��ǰ������
				//���x��ǰ��
			/*	System.out.println("pres size: "+pres.size());
				for(State s:pres){
					System.out.println("״̬name: "+s.getName());
					System.out.println("״̬position: "+s.getPosition());
					DBM_element[][] DBM=s.getInvariantDBM();
					DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
					for(int i=0;i<3;i++){
						for(int j=0;j<3;j++){
							DBM_element cons=add_clock_DBM[i][j];
							//System.out.println("DBM_i:"+cons.getDBM_i());
							//System.out.println("DBM_j:"+cons.getDBM_j());
							System.out.println("value:"+cons.getValue());
							System.out.println("Strictness:"+cons.isStrictness());									
						}
					}
					System.out.println("*******************************");
				}*/
				for(int i=0;i<stable.size();i++){//�ȶ�����ɾ��x��ǰ�����ϣ��ȶ���һ������ǰ��״̬��
					for(int j=0;j<pres.size();j++){
						if(stable.get(i).getName().equals(pres.get(j).getName())){
							stable.remove(i);
						}
					}
				}
				for(int i=0;i<P.size();i++){//״̬����ɾ��x
					if(P.get(i).getName().equals(x.getName())){
						P.remove(i);
					}
				}
				for(State s:new_X){//״̬���м����ֵõ�����״̬
					State ss=new State();
					ss.setName(s.getName());
					ss.setPosition(s.getPosition());
					ss.setInvariantDBM(s.getInvariantDBM());
					ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
					P.add(s);
				}
			}
			//���x
			//System.out.println("x name: "+x.getName());
			/*System.out.println("x position: "+x.getName());
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=x.getInvariantDBM()[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}*/
		
			
			//����ɴＯ
			/*System.out.println("accessible size: "+accessible.size());
			for(State s:accessible){
				System.out.println("״̬name: "+s.getName());
				System.out.println("״̬position: "+s.getPosition());
				DBM_element[][] DBM=s.getInvariantDBM();
				DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						DBM_element cons=add_clock_DBM[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());									
					}
				}
				System.out.println("*******************************");
			}*/
			//����ȶ���
			//System.out.println("stable size: "+stable.size());
			/*for(State s:stable){
				System.out.println("״̬name: "+s.getName());
				System.out.println("״̬position: "+s.getPosition());
				DBM_element[][] DBM=s.getInvariantDBM();
				DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						DBM_element cons=add_clock_DBM[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());									
					}
				}
				System.out.println("*******************************");
			}*/
			//���P
			/*System.out.println("P size: "+P.size());
			for(State s:P){
				System.out.println("״̬name: "+s.getName());
				System.out.println("״̬position: "+s.getPosition());
				DBM_element[][] DBM=s.getInvariantDBM();
				DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
				for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						DBM_element cons=add_clock_DBM[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());									
					}
				}
				System.out.println("*******************************");
			}*/
		}
		
		return stable;
	}
	
	/**
	 * �����ڿɴＯ�ж������ȶ����еĵ�һ��״̬ �����ص���һ���µ�״̬
	 * @param accessible
	 * @param stable
	 * @return
	 */
	public static State accessibleNostable(ArrayList<State> accessible,ArrayList<State> stable) {
		int accessible_len=accessible.size();
		int Stable_len=stable.size();
		State s=new State();
		for(int i=0;i<accessible_len;i++){
			int flag=1;
			for(int j=0;j<Stable_len;j++){
				if(accessible.get(i).getName().equals(stable.get(j).getName())){
					flag=0;
					break;}	
			}
			if(flag==1){
				s=accessible.get(i);
				break;
			}
		}
		
		State accNostable=new State();
		accNostable.setName(s.getName());
		accNostable.setPosition(s.getPosition());
		accNostable.setInvariantDBM(s.getInvariantDBM());
		accNostable.setAddClockRelationDBM(s.getAddClockRelationDBM());
		
		return accNostable;
	}
	
	/**
	 * zone������㷵��1����������㷵��0
	 * @param zone
	 * @return
	 */
	public static int includeZero(DBM_element[][] zone){
		int len=zone.length;
		DBM_element[][] zero=new DBM_element[len][len];
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(0);
				ele.setStrictness(true);
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				zero[i][j]=ele;	
			}
		}
		
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(zone), Floyds.floyds(zero)))==1){//���������ཻΪ�գ����DBM��������㣬����0
			return 0;
		}
		else return 1;//���������ཻ��Ϊ�գ����DBM������㣬����1
	}
	
	/**
	 * ���غ�̼�����ɴＯ��ͬ��״̬
	 * @param accessible
	 * @param posts
	 * @return
	 */
	public static ArrayList<State> posts_differ_access(ArrayList<State> accessible,ArrayList<State> posts) {
		ArrayList<State> diff=new ArrayList<State>();//�����̼�����ɴＯ��ͬ��״̬
		
		for(int i=0;i<posts.size();i++){
			int flag=1;
			for(int j=0;j<accessible.size();j++){
				if(posts.get(i).getName().equals(accessible.get(j).getName())){
					flag=0;
					break;
				}
			}
			if(flag==1){
				State state=new State();
				state.setName(posts.get(i).getName());
				state.setPosition(posts.get(i).getPosition());
				state.setInvariantDBM(posts.get(i).getInvariantDBM());
				state.setAddClockRelationDBM(posts.get(i).getAddClockRelationDBM());
				diff.add(state);
			}
		}
		return diff;
	}
}
