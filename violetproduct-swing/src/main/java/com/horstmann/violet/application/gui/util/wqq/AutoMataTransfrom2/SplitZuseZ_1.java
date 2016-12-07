package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class SplitZuseZ_1 {
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
		
		ArrayList<DBM_element[][]> com_and=Complement_1.complement(and);
		//System.out.println(com_and.size());
		
		ArrayList<DBM_element[][]> zones=splitZuseZ(DBM1,and);
		System.out.println(zones.size());
		
		
		for(DBM_element[][] dbm:zones){
			DBM_element fdbm[][]=Floyds.floyds(dbm);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=fdbm[i][j];
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
	 * �þ���Z2��־���Z1��Z1��Z2��û�й淶��
	 * @param Z1
	 * @param Z2
	 * @return Z1����ֳɶ�����󣬷���һ�����󼯺ϣ����صľ�����û�����滯�ģ����DBMs�ж����ཻ��
	 */
	public static ArrayList<DBM_element[][]> splitZuseZ(DBM_element[][] Z1,DBM_element[][] Z2) {
		ArrayList<DBM_element[][]> Zones=new ArrayList<DBM_element[][]>();
		int n=Z1.length;
		DBM_element[][] copyZ1=new DBM_element[n][n];//��Z1����copyZ1������������ɺ�Z1�ı�
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(Z1[i][j].getValue());
				ele.setStrictness(Z1[i][j].isStrictness());
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				copyZ1[i][j]=ele;	
			}
		}
		
		
		if(IsEmpty.isEmpty(AndDBM.andDBM(copyZ1, Z2))==1){//���Z1��Z2Ϊ�գ��򷵻�Z1����ΪZ1���ñ�Z2���
			Zones.add(Z1);
		}
		
		else{//���Z1��Z2��Ϊ��
			Zones.add(AndDBM.andDBM(Z1, Z2));//��Z1��Z2����Zones
			ArrayList<DBM_element[][]> complements=Complement_1.complement(Z2);//��Z2�Ĳ���
			for(DBM_element[][] com:complements){//����Z2�Ĳ���
				if(IsEmpty.isEmpty(AndDBM.andDBM(copyZ1, com))==1){//���һ��������Z1�ཻΪ�գ���ʲô��������
					
				}
				else{
					Zones.add(AndDBM.andDBM(copyZ1, com));//���һ��������copyZ1�ཻ��Ϊ�գ������Ľ������zones
					ArrayList<DBM_element[][]> com_com=Complement_1.complement(com);//����������Ĳ���
					copyZ1=AndDBM.andDBM(copyZ1, com_com.get(0));//��������ʣ�µĲ����ǲ����Ĳ�����copyZ1�Ľ�					
				}
			
			}
		}
		return Zones;
	}

}
