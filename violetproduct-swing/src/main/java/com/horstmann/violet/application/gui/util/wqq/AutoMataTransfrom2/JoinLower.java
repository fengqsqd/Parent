package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;
public class JoinLower {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		//l0�е�ʱ�Ӳ���ʽ
		ArrayList<String> l0 =new ArrayList<String>();
		l0.add("x<2");
		l0.add("x<=y");
		l0.add("y<=x");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, l0));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		
		//e0���ϵ�Լ��
		ArrayList<String> e0 =new ArrayList<String>();
		e0.add("x>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, e0));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		
		//l0��e0�ཻ
		DBM_element andDBM[][]=AndDBM.andDBM(fDBM1, fDBM2);
		DBM_element fandDBM[][]=Floyds.floyds(andDBM);
		
		//��fandDBM��y��λ
		DBM_element rDBM[][]=Reset.reset(fandDBM, Clocks, "y");
		DBM_element frDBM[][]=Floyds.floyds(rDBM);
		
		//��ȡfrDBM��ʱ�Ӹ�λ��Ϣ
		DBM_element extDBM[][]=ExtractReset.extract(frDBM);
		DBM_element fextDBM[][]=Floyds.floyds(extDBM);
		
		//��fextDBM���뵽l1�Ĳ���ʽ��
		ArrayList<String> l1 =new ArrayList<String>();
		l1.add("y<=2");
		DBM_element DBM3[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, l1));
		DBM_element fDBM3[][]=Floyds.floyds(DBM3);
		
		DBM_element joinclock[][]=JoinReset.joinClock(fDBM3, fextDBM);
		DBM_element fjoinclock[][]=Floyds.floyds(joinclock);
		//��ȡe0��ʱ���½���Ϣ
		DBM_element extractLower[][]=ExtractLower.extractLower(fDBM2);
		DBM_element fextractLower[][]=Floyds.floyds(extractLower);
		//��fextractLower���뵽fjoinclock
		DBM_element jionlower[][]=joinLower(fjoinclock,fextractLower);
		DBM_element fjionlower[][]=Floyds.floyds(jionlower);
		
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fjionlower[i][j];
				System.out.println("DBM_i:"+cons.getDBM_i());
				System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				System.out.println("***********");
								
			}
		}
	}
	
	/**
	 * ��ʱ���½���Ϣ���뵽״̬����ʽ��
	 * @param dbm1 ʱ���½����
	 * @param dbm2  ����ʱ�Ӹ�λ��Ϣ��״̬����ʽ����
	 * @return ����ʱ���½��ʱ�Ӹ�λ��Ϣ�ľ���
	 */
	public static DBM_element[][] joinLower(DBM_element[][]dbm1,DBM_element[][]dbm2) {
		int dimension=dbm1.length;
		DBM_element andDBM[][]=new DBM_element [dimension][dimension];
	  
		for(int i=0;i<dimension;i++){
			for(int j=0;j<dimension;j++){
				DBM_element ele=new DBM_element();
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				ele.setStrictness(AndDBM.andDBM(dbm1, dbm2)[i][j].isStrictness());
				ele.setValue(AndDBM.andDBM(dbm1, dbm2)[i][j].getValue());
				andDBM[i][j]=ele;		
			}
		}
		
		return andDBM;
	}
}
