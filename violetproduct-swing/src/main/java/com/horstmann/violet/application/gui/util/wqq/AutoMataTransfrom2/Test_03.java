package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
/**
 * ����copy��������
 * @author Administrator
 *
 */
public class Test_03 {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		
		ArrayList<String> c1=new ArrayList<String>();
		c1.add("x<2");
		ArrayList<String> c2=new ArrayList<String>();
		c2.add("x>1");
		
		ArrayList<String> c3=new ArrayList<String>();
		c3.add("x<2");
		c3.add("x<=y");
		c3.add("x>=y");
		
		DBM_element[][] DBM1=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, c1));//��״̬�еĲ���ʽת��DBM����
		DBM_element[][] copyDBM1=Copy.copy(DBM1, 2, 1);//DBM1����������y=x
		DBM_element[][] fcopyDBM1=Floyds.floyds(copyDBM1);//��copyDBM1�淶��
		DBM_element[][] DBM3=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, c3));//��״̬�еĲ���ʽת��DBM����
		//DBM_element[][] copyDBM3=Copy.copy(DBM3, 2, 1);//DBM3����������y=x
		DBM_element[][] fDBM3=Floyds.floyds(DBM3);//��DBM3�淶��
		
		//DBM_element[][] DBM2=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, c2));//��״̬�еĲ���ʽת��DBM����
		//DBM_element[][] fDBM2=Floyds.floyds(DBM2);//��DBM2�淶��
		//DBM_element[][] fDBM3ANDfDBM2=AndDBM.andDBM(fDBM3, fDBM2);//fcopyDBM1��DBM2
		//System.out.println(IsEmpty.isEmpty(DBM3));//�ж�DBM3�Ƿ�Ϊ��
		//DBM_element[][] fAnd=Floyds.floyds(fDBM3ANDfDBM2);//�����Ϊ�գ���fDBM3ANDfDBM2���滯
		
		int len=DBM1.length;
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				System.out.println(i+" "+j+": ");
				DBM_element cons=fDBM3[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				System.out.println("***********");
								
			}
		}
		
	}
}
