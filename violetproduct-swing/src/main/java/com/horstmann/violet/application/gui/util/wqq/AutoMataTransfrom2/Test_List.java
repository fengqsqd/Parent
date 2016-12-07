package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTestCase;


public class Test_List {
	public List<String> exchangeTestCaseList(String xml) {
		// TODO Auto-generated method stub
		//="throttle_zero_flagForXStream.xml";
		//String xml="UAVForXStream.xml";	
		Automatic auto=GetAutomatic.getAutomatic(xml);//���ԭʼ��ʱ���Զ���
		Automatic automatic=AddType.addType(auto);
		/*//ArrayList<State> new_stateSet=Minimization__1.minimization(automatic);
		Automatic new_automatic=IPR__1.iPR(automatic);//��ò�ֺ��ʱ���Զ���
		Automatic aTDRTAutomatic=ATDTR__1.aTDRT(new_automatic,automatic);//���ȥ������ʱ��Ǩ�ƺ��ʱ���Զ���
		//Automatic DFStree=StateCoverage__1.DFSTree(aTDRTAutomatic);
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(aTDRTAutomatic);//�������״̬���ǵĳ����������
		ArrayList<ArrayList<String>> all_inequalitys=Get_inequality__1.get_AllInequalitys(testCase);//ÿ���������������һ������ʽ��
		*/
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(automatic);//�������״̬���ǵĳ����������
		
				
		/*System.out.println("����������и�����"+testCase.size());
		for(Automatic a:testCase){		
			System.out.println("������������:"+a.getName());	
			for(Transition tran:a.getTransitionSet()){
				System.out.println(tran.getSource()+"---->"+tran.getTarget()+"Լ���� "+tran.getEventSet());
			}
			System.out.println("*********************************");
		}*/
		
		
		ArrayList<String> list = new ArrayList<String>();  
		System.out.println("����������и�����"+testCase.size());
		String str = "";
		for(Automatic aut:testCase){
			System.out.println("������������:"+aut.getName()); 
			int k=0;
			for(Transition tran:aut.getTransitionSet()){
				
				//String str2 = tran.getEventSet().toString().replace("[", "").replace("]", "").replace(";", "");
				String str2 = tran.getEventSet().toString().replace("[", "").replace("]", "");
				String str1 = tran.getSource().toString();
				String str3 = tran.getTarget().toString();

				if(k==0){
					str = str+str1+","+str2+","+str3;
					k=1;
				}
				else{
					str = str+","+str2+","+str3;
				}
			}
			list.add(str);
			System.out.println("list:"+list);
			}
		
		//System.out.println("list�ĳ���Ϊ��"+list.size());
		/*try {
			System.setOut(new PrintStream("D:\\eclipse\\workspace\\Automatic_43\\a.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*for(int i=0;i<list.size();i++){
		System.out.println("list"+i+"Ϊ:"+list.get(i));
		}*/
		System.out.println("list�ĳ���Ϊ��"+list.size());
		return list;
		}

			
	}



		
		
		
		
