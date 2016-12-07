package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class NextAdjVex {
	public static void main(String[] args) {
		Automatic a=AutomaticIBean.automic();
		int next=nextAdjVex(a,3,1);
		System.out.println(next);
	}
	/**
	 * ����ʱ���Զ�����v�������w����һ���ڽӵ�,���w�����һ���ڽӵ㣬����-1��
	 * @param automatic
	 * @param v
	 * @param w
	 * @return
	 */
	public static int nextAdjVex(Automatic automatic,int v,int w) {
		ArrayList<State> StateSet=automatic.getStateSet();
		State state=StateSet.get(v);
		State state_w=StateSet.get(w);
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<Transition> v_trans=new ArrayList<Transition>();
		for(Transition t:TransitionSet){//����ԴΪv�ı߼���
			if (t.getSource().equals(state.getName())) {
				v_trans.add(t);
			}
		}
		
		
		if (v_trans.size()>0) {
			Transition tran=new Transition();
			for(int i=0;i<v_trans.size();i++){
				if(v_trans.get(i).getTarget().equals(state_w.getName())){
					int j=i+1;
					if(j>=v_trans.size()){return -1;}//���w��v�����һ���ڽӵ㣬�򷵻�-1
					else tran=v_trans.get(j);//��һ���ߵ�Ŀ��״̬����v����һ���ڽӵ�
				}
			}
			for(int k=0;k<StateSet.size();k++){
				if(tran.getTarget().equals(StateSet.get(k).getName())){
					return k;
				}
			}
		}
		return -1;
	}
}
