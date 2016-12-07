package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class Ee {
	public static void main(String[] args) {
		ArrayList<String> invariant=new ArrayList<String>();
		invariant.add("x<=2");
		invariant.add("x<=y");
		invariant.add("x>=y");
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
			
		DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//��״̬�еĲ���ʽת��DBM����
		DBM_element[][] fDBM=Floyds.floyds(DBM);
		DBM_element[][] rDBM=Reset_1.reset(DBM, Clocks, "y");
		DBM_element[][] frDBM=Floyds.floyds(rDBM);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=frDBM[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());									
			}
		}
		System.out.println("*******************************");
	
	}
}
