package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class Test_ckt {
	public static void main(String[] args) {
		String xml="UAVForXStream.xml";
		Automatic auto=GetAutomatic.getAutomatic(xml);//���ԭʼ��ʱ���Զ���
		Automatic automatic=AddType.addType(auto);
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(automatic);//�������״̬���ǵĳ����������

		
		System.out.println("����������и�����"+testCase.size());
		for(Automatic a:testCase){			
			System.out.println("������������:"+a.getName());
			System.out.print("Ǩ��idΪ��");
			for(Transition tran:a.getTransitionSet()){
				System.out.print(tran.getId()+",");//+"%%%%%%%%%"+tran.getSource()+"---->"+tran.getTarget()+"Լ���� "+tran.getEventSet());
				//System.out.println(tran.getId()+"%%%%%%%%%%%%%%%%%%%%%%%%");
			}
			System.out.println("");
			System.out.print("״̬idΪ��");
			for(State s:a.getStateSet()){
				System.out.print(s.getId()+",");
			}

			System.out.println("");
			System.out.println("*********************************");
		}
		
	
	}
}
