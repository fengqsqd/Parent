package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;
//�������������
import java.util.ArrayList;

import org.junit.Test;

import com.horstmann.violet.application.gui.util.yangjie.Mathematica2;
import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

public class Get_testcase {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String xml="UAV2ForXStream.xml";
		String xml="read_radio2ForXStream.xml";
		Automatic auto=GetAutomatic.getAutomatic(xml);//���ԭʼ��ʱ���Զ���

		ArrayList<Automatic> testCase=StateCoverage__1.testCase(auto);//�������״̬���ǵĳ����������
		
		System.out.println("����������и�����"+testCase.size());
		int i = 1;
		for(Automatic a:testCase){	
			System.out.println();
			System.out.println("������������:"+a.getName());
			int j=1;
			System.out.println("==================��"+i+"������������ʼ==================");
			for(Transition tran:a.getTransitionSet()){
				System.out.println("                  ==================��"+j+"��Ǩ�ƿ�ʼ==================");
				System.out.println("--------->��������--------> "+tran.getName());
				//System.out.println(tran.getSource()+"---->"+tran.getTarget()+"Լ���� "+tran.getEventSet());
			//δ�����Լ������	
			System.out.println("Լ����"+tran.getEventSet());//Լ������ʽ
			//�����Ͳ���ʽ�Ͳ���
				String bds1=Get_str.get_bds(tran.getEventSet().toString());
				//System.out.println(bds1);  //һ��Ǩ�������ֲ���ʽ
				String cs1=Get_str.get_cs(bds1);
				//System.out.println(cs1);//һ��Ǩ�������ֲ���ʽ�еĲ���
				//System.out.println("bds---------->"+bds);
            //�����Ͳ���ʽ�Ͳ���
				String boolbds=Get_str.get_bool_bds(tran.getEventSet().toString());
				//System.out.println(boolbds);//һ��Ǩ���ϲ�������ʽ
				String boolcs=Get_str.get_bool_cs(boolbds);
				//System.out.println(boolcs);//һ��Ǩ���ϲ�������ʽ�еĲ���
		   //==0�Ĳ���ʽ��Ϊ�� ==��Ϊ=
				String bds0=Get_str.get_bds_0(tran.getEventSet().toString());
				//
				/*if(bds0!=null){
					System.out.println("==0�Ĳ���ʽ��Ϊ��"+bds0);
				}*/
			
			//����mma�����ⷽ����
				if(((bds1==null)&&(cs1==null))&&((boolbds==null)&&(boolcs==null))){
					System.out.println("û��Լ����Ϊ��null");
				}
				if((bds1!=null)&&(cs1!=null)){
					String solution1 = Mathematica2.getSolution2(bds1, cs1);
					System.out.println("��ֵ��Լ����Ϊ��"+solution1);
					//System.out.println("��ֵ��Լ����Ϊ��");
					//System.out.println(solution1);
				}
				if((boolbds!=null)&&(boolcs!=null)){
					String solution2 = Mathematica2.getSolution3(boolbds, boolcs);
					System.out.println("������ֵ��Ϊ��"+solution2);
					//System.out.println("������ֵ��Ϊ��");
					//System.out.println(solution2);
				}
			//==0�Ĳ���ʽ��Ϊ�� ==��Ϊ=	
				if(bds0!=null){
					System.out.println("==0�Ĳ���ʽ��Ϊ��"+bds0);
				}
				
			//����mma�����ⷽ����
				//String solution1 = Mathematica2.getSolution2(bds1, cs1);
				//String solution2 = Mathematica2.getSolution3(boolbds, boolcs);
				
				System.out.println("                  ==================��"+j+"��Ǩ�ƽ���==================");
				j++;
			}
			System.out.println("==================��"+i+"��������������==================");
			i++;
		}
		
		
	
	}

}

