
package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test_Id {
	public List<String> exchangeTestCase(String xml) {
		//String xml="UAVForXStream.xml";
		//read_radioForXStream.xml    ����xml�ĵ�
		Automatic auto=GetAutomatic.getAutomatic(xml);//���ԭʼ��ʱ���Զ���
		Automatic automatic=AddType.addType(auto);
		List<String> list=new ArrayList<String>();//���ڷ��صĲ��������ļ���
		String stringCase="";//����ÿһ����Ҫ����Ĳ�������
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(automatic);//�������״̬���ǵĳ����������
		System.out.println("����������и�����"+testCase.size());
		for(Automatic a :testCase){
			
			System.out.println("������������:"+a.getName());
		
			List<Transition> listTran=a.getTransitionSet();
			List<State> listState=a.getStateSet();
			String source;
			String target;
			int sourceId;
			int targetId;
			//System.out.println("����"+listTran.size());
//			for(int j=0;j<listState.size();j++){
//				//System.out.print(listState.get(j).getId()+",");
//			}
			//System.out.println();
			for(int i=0;i<listTran.size();i++){
				//System.out.print(listTran.get(i).getId()+",");
				if(i==0){//��һ���ڵ㵥������
					source=listTran.get(0).getSource();
					for(int j=0;j<listState.size();j++){
						if(listState.get(j).getName().equals(source)){
							sourceId=listState.get(j).getId();
							stringCase="s"+sourceId+",t"+listTran.get(i).getId();
							break;
						}
					}
					target=listTran.get(0).getTarget();
					for(int j=0;j<listState.size();j++){
						if(listState.get(j).getName().equals(target)){
							targetId=listState.get(j).getId();
							stringCase+=",s"+targetId;
							break;
						}
					}
				}//if
				else{//�����Ľڵ�
					stringCase+=",t"+listTran.get(i).getId();
					target=listTran.get(i).getTarget();
					for(int j=0;j<listState.size();j++){
						if(listState.get(j).getName().equals(target)){
							targetId=listState.get(j).getId();
							stringCase+=",s"+targetId;
							break;
						}
					}
				}//for else
				
			}//for TranList
		//��ÿһ������������string��ӵ�list������
		list.add(stringCase);
        
		}//for testCase
		return list;
	    //����list�����
//		System.out.println();
//		System.out.println("***");
//	    for(int h=0;h<list.size();h++){
//	    	System.out.println(list.get(h));
//	    }
	    }
}

