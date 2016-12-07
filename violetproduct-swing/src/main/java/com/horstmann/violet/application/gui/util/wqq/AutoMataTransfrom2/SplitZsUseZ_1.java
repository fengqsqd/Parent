package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class SplitZsUseZ_1 {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=2");
		ar1.add("x-y>1");
		ar1.add("x-y<2");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x<=3");
		ar2.add("y>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
	
		ArrayList<String> ar3 =new ArrayList<String>();
		ar3.add("x-y>1");
		ar3.add("x-y<2");
		DBM_element DBM3[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar3));
		DBM_element fDBM3[][]=Floyds.floyds(DBM3);
		
		DBM_element and[][]=AndDBM.andDBM(DBM2, DBM3);
		DBM_element fand[][]=Floyds.floyds(and);
		
		//���and�Ĳ���
		ArrayList<DBM_element[][]> com_and=Complement_1.complement(and);
		//System.out.println(com_and.size());
		
		//���and���DBM1�ļ���
		ArrayList<DBM_element[][]> zones=SplitZuseZ_1.splitZuseZ(DBM1,and);
		//System.out.println(zones.size());
		
		ArrayList<String> ar4 =new ArrayList<String>();
		ar4.add("x-y<2");
		ar4.add("x-y>1");
		ar4.add("y<=2");
		ar4.add("y>=2");
		DBM_element DBM4[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar4));
		DBM_element fDBM4[][]=Floyds.floyds(DBM4);
		
		//���DBM4�Ĳ���
		ArrayList<DBM_element[][]> com_DBM4=Complement_1.complement(DBM4);
		//System.out.println(com_DBM4.size());
		
		ArrayList<DBM_element[][]> ZsZ=splitZsUseZ(zones,DBM4);
		System.out.println(ZsZ.size());
		
		for(DBM_element[][] dbm:ZsZ){
			DBM_element fdbm[][]=Floyds.floyds(dbm);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());														
				}
			}
			System.out.println("***********");
		}
	}
	/**
	 * ��Z���{Z1,Z2....}
	 * @param Zs
	 * @param Z
	 * @return ����һ�����󼯺ϣ����صľ��󼯺���û�й淶����
	 */
	public static ArrayList<DBM_element[][]> splitZsUseZ(ArrayList<DBM_element[][]> Zs,DBM_element[][] Z) {
		ArrayList<DBM_element[][]> Zones=new ArrayList<DBM_element[][]>();
		
		ArrayList<DBM_element[][]>fZs=new ArrayList<DBM_element[][]>();
		for(DBM_element[][] z:Zs){//�Ƚ�Zs�еľ���淶��
			fZs.add(Floyds.floyds(z));
		}
		
		for(DBM_element[][] zone:fZs){
			ArrayList<DBM_element[][]> DBMs=new ArrayList<DBM_element[][]>();
			DBMs=SplitZuseZ_1.splitZuseZ(zone, Floyds.floyds(Z));
			for(DBM_element[][] dbm:DBMs){
				Zones.add(dbm);
			}
			
		}
		
		return Zones;
	}
}
