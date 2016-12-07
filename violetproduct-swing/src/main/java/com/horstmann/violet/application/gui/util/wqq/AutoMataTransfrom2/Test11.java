package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

	public class Test11 {
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			//String xml="UAVForXStream.xml";
			String xml="read_radioForXStream.xml";
			Automatic automatic=GetAutomatic.getAutomatic(xml);//���ԭʼ��ʱ���Զ���
			/*//ArrayList<State> new_stateSet=Minimization__1.minimization(automatic);
			Automatic new_automatic=IPR__1.iPR(automatic);//��ò�ֺ��ʱ���Զ���
			Automatic aTDRTAutomatic=ATDTR__1.aTDRT(new_automatic,automatic);//���ȥ������ʱ��Ǩ�ƺ��ʱ���Զ���
			//Automatic DFStree=StateCoverage__1.DFSTree(aTDRTAutomatic);
			ArrayList<Automatic> testCase=StateCoverage__1.testCase(aTDRTAutomatic);//�������״̬���ǵĳ����������
			ArrayList<ArrayList<String>> all_inequalitys=Get_inequality__1.get_AllInequalitys(testCase);//ÿ���������������һ������ʽ��
			*/
			ArrayList<Automatic> testCase=StateCoverage__1.testCase(automatic);//�������״̬���ǵĳ����������

			System.out.println("����������и�����"+testCase.size());
			for(Automatic aut:testCase){
				
				System.out.println("������������:"+aut.getName());

				
				for(Transition tran:aut.getTransitionSet()){

					 String str = tran.getEventSet().toString().replace("[", "").replace("]", "");
					 if(str.length()>0){
						 Queue<String> queue = new LinkedList<String>();
						 String[] strs=str.split(";");
						 for(int i=0;i<strs.length;i++){
							 queue.add(strs[i]);
						 }
						 String a=queue.poll();
						 //�洢cycle 
						 Map<String, String> map1 = new HashMap<String, String>();
						 //�洢str���ܵģ�map1
						 Map<String, Map> map = new HashMap<String, Map>();
						 Map<String, List> map01 = new HashMap<String, List>();
						 //����list
						 List<String> list=new ArrayList<String>();
						 while(a!=null){
							 int index=a.indexOf(":");
							 String out=a.substring(index+1, a.length());
							  if(out.contains(",")){
								   String[] str01 =out.split(",");
								   for(int j=0;j<str01.length;j++){
									   if(out.contains("r")&&out.contains("m")){
									   int x=str01[j].indexOf("r");
									   int y=str01[j].indexOf("m");
					//				   
									   String str1 = str01[j].substring(x, y+1);
									   list.add(str1);
									   }
								   }
								   map1.put(str01[0], out);
								   map.put(str, map1);
								   map01.put(str, list);
								   // System.out.println(out);
							   }
							 else if(out.contains("<")){
								if(out.contains("&&")){
									int index1=out.indexOf("&");
									out=out.substring(0, index1-1);
								}
								  int index1=out.indexOf("<");
								   String str1=out.substring(0,index1);
								   if(str1.length()<2){
									   str1=out.substring(index1+2, out.lastIndexOf("<"));
								   }
								   list.add(str1);
								   map1.put(str1, out);
								   map.put(str, map1);
								   map01.put(str, list);
							 } 
							 else if(out.contains(">")){
									if(out.contains("&&")){
										int index1=out.indexOf("&");
										out=out.substring(0, index1-1);
									}
								 int index1=out.indexOf(">");
								 String str1=out.substring(0,index1);
								 list.add(str1);
								 map1.put(str1, out);
								 map.put(str, map1);
								 map01.put(str, list);
								 //System.out.println(out);
							 }
							   else if(out.contains("!=")){
								   String[] str01 = out.split("!=");
								   for(int j=0;j<str01.length;j++){
									   list.add(str01[j]);
								   }
								   map1.put(str01[0], out);
								   map.put(str, map1);
								   map01.put(str, list);
								   // System.out.println(out);
							   }
							 else if(out.contains("!")){
									if(out.contains("&&")){
										int index1=out.indexOf("&");
										out=out.substring(0, index1-1);
									}
									if(out.contains("||")){
										break;
									}
								 int index1=out.indexOf("!");
								 String str1=out.substring(0,index1);
								 list.add(str1);
								 map1.put(str1, out.replace("!=", "<"));
								 map.put(str, map1);
								 map01.put(str, list);
								 //System.out.println(out);
							 }
							 else if(out.contains("==")){
								 //==�����			 
								 if(out.contains("true")==false&&out.contains("false")==false){		 
									 int index1=out.indexOf("==");
									 String str1=out.substring(0,index1);
									 list.add(str1);
									 map1.put(str1, out);
									 if(out.contains("STABILIZE")){
										 String str2 = "STABILIZE";
										 list.add(str2);
									 }
									 map.put(str, map1);
									 map01.put(str, list);
									 //System.out.println(out);
								 }
							 }
							 else if(out.contains("=")){
								 int index1=out.indexOf("=");
								 String str1=out.substring(0,index1);
								 list.add(str1);
								 if(out.contains("cycle")){
									 out = out.replace("=", "==");
								 }
								 if(out.contains("rc_input.throttle_control_in")){
									 out = out.replace("=", "==");
								 }
								 map1.put(str1, out);
								 map.put(str, map1);
								 map01.put(str, list);
								 //System.out.println(out);
							 }
							 else if(out.contains("&&")){
								 int index1=out.indexOf("&&");
								 String str1=out.substring(0,index1);
								 list.add(str1);
								 map1.put(str1, out);
								 map.put(str, map1);
								 map01.put(str, list);
								 // System.out.println(out);
							 }
							 
							 a=queue.poll();
						 }//while
						 Map map2=map.get(str);
						 List<String> list01 = map01.get(str);
						 int i=list01.size();
						 for(String l : list01) {
							 if(i==1){
								 //System.out.print(map2.get(l).toString().replace("ms", "").replace("0.2s", "0.2").replace("0.4s", "0.2").replace("0.5s", "0.2"));
								 System.out.print(l.trim());

							 }
							 //System.out.print("                           ");
							 else{ 
								 //System.out.print(map2.get(l).toString().replace("ms", "").replace("0.2s", "0.2").replace("0.4s", "0.2").replace("0.5s", "0.2")+",");
								 System.out.print(l.trim()+",");
							 }
							 i--;
						 }
						 System.out.println();
						 //System.out.println(map2.get("cycle"));
						 //System.out.println(map2.get("rc_input.new_input"));
						 
					 }
				}
				System.out.println("*********************************");
			}
			
			
			
		}
	}
