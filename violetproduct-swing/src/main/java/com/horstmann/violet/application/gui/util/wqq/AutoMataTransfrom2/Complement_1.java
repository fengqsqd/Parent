package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class Complement_1 {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x<=3");
		ar2.add("y>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		
		
		ArrayList<DBM_element[][]> complements=complement(DBM2);
		System.out.println(complements.size());
		for(DBM_element[][] dbm:complements){
			DBM_element fdbm[][]=Floyds.floyds(dbm);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=dbm[i][j];
					System.out.println("DBM_i:"+cons.getDBM_i());
					System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());
					
									
				}
			}
			System.out.println("***********");
		}
	
	}
	
	/**
	 * ��һ������Ĳ�����������һ�����󼯺�,�����еľ���û�й淶��
	 * @param DBM ����DBM�淶�� 
	 * @return
	 */
	public static ArrayList<DBM_element[][]> complement(DBM_element[][] DBM){
		int dimension=DBM.length;//��ȡDBM��ά��
		//DBM_element[][] fDBM=Floyds.floyds(DBM);//����DBM�淶�� 
		ArrayList<DBM_element[][]> complements=new ArrayList<DBM_element[][]>();
	
		for(int i=0;i<dimension;i++){
			for(int j=0;j<dimension;j++){
				
				if(i==0&&DBM[i][j].getValue()!=0){//�����һ��Ԫ��
					DBM_element com_DBM[][]=new DBM_element [dimension][dimension];
					DBM_element d=new DBM_element();
					d.setDBM_i(j);
					d.setDBM_j(i);
					d.setStrictness(!DBM[i][j].isStrictness());
					d.setValue(-DBM[i][j].getValue());
					com_DBM[j][i]=d;	
					
					complements.add(com_DBM);
				}
				
				if(i>0&&i!=j&&DBM[i][j].getValue()!=88888){//����ʣ�µ�Ԫ��(�Խ�����Ԫ�ز�����)
					DBM_element com_DBM[][]=new DBM_element [dimension][dimension];
					DBM_element d=new DBM_element();
					d.setDBM_i(j);
					d.setDBM_j(i);
					d.setStrictness(!DBM[i][j].isStrictness());
					d.setValue(-DBM[i][j].getValue());
					com_DBM[j][i]=d;	
					
					complements.add(com_DBM);
				}
				
			}
		}
		
		for(DBM_element[][] dbm:complements){	
			for(int i=0;i<dimension;i++){
				for(int j=0;j<dimension;j++){
					if(i==0&&dbm[i][j]==null){//���õ�һ��Ԫ��	
						DBM_element firstline=new DBM_element();
						firstline.setDBM_i(0);
						firstline.setDBM_j(j);
						firstline.setStrictness(true);
						firstline.setValue(0);
						dbm[i][j]=firstline;
					}
					
					if(i==j){//���öԽ�����Ԫ��
						DBM_element diagonal=new DBM_element();
						diagonal.setDBM_i(i);
						diagonal.setDBM_j(j);
						diagonal.setStrictness(true);
						diagonal.setValue(0);
						dbm[i][j]=diagonal;
					}
					
					
					if(dbm[i][j]==null){//����DBM���޹�Ԫ�ص�ֵ,��ֵ����Ϊ88888
						DBM_element unrelated=new DBM_element();
						unrelated.setDBM_i(i);
						unrelated.setDBM_j(j);
						unrelated.setValue(88888);
						unrelated.setStrictness(false);
						dbm[i][j]=unrelated;		
					}
				}
			}
		}
			
		
		return complements;
	}
}
