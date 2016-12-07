package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class Godirectly_2 {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=1");
		ar1.add("x-y<2");
		ar1.add("x-y>1");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x<=3");
		ar2.add("y>1");
		ar2.add("x-y<2");
		ar2.add("x-y>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		
		DBM_element[][] fDBM1=Floyds.floyds(DBM1);
		DBM_element[][] fDBM2=Floyds.floyds(DBM2);
		
		DBM_element[][] goDirectly=goDirectly(DBM1, DBM2);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=goDirectly[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				
								
			}
		}						
		
	}
	
	/**
	 * ����DBM1��ֱ�ӵ���DBM2�Ĳ��֣����û�й淶��
	 * @param DBM1
	 * @param DBM2
	 * @return
	 */
	public static DBM_element[][] goDirectly(DBM_element[][] DBM1,DBM_element[][] DBM2) {
		if(Commonboundary.commonboudary(DBM1, DBM2)!=null){
			DBM_element[][] comboundary=Commonboundary.commonboudary(DBM1, DBM2);//��ȡDBM1, DBM2�Ĺ����߽�
			DBM_element[][] comboundary_down=Down.down(comboundary);//��ù����߽��ǰ��
			DBM_element[][] direct=AndDBM.andDBM(DBM1, comboundary_down);//�����߽��ǰ����DBM1�ཻ����DBM1����ֱ�ӵ���DBM2�Ĳ���	
			return direct;
		}
		else return null;
		
	}
}
