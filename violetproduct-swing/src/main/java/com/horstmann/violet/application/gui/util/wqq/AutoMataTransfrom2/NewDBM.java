package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

public class NewDBM {
	public static void main(String[] args) {
		DBM_element[][] newDBM=newDBM();
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=newDBM[i][j];
				System.out.println("DBM_i:"+cons.getDBM_i());
				System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				
								
			}
		}
	}
	/**
	 * ����һ��û��Լ����DBM����
	 * @return
	 */
	public static DBM_element[][] newDBM() {
		int dimension=3;
		DBM_element DBM[][]=new DBM_element [dimension][dimension];
		
		for(int i=0;i<dimension;i++){//���öԽ����ϵ�Ԫ��Ϊ(0,true)
			for(int j=0;j<dimension;j++){
				if(i==j){
					DBM_element diagonal=new DBM_element();
					diagonal.setDBM_i(i);
					diagonal.setDBM_j(j);
					diagonal.setStrictness(true);
					diagonal.setValue(0);
					DBM[i][j]=diagonal;
				}
			}
		}
		
		for(int j=0;j<dimension;j++){//���õ�һ��Ԫ��Ϊ(0,true)		
			DBM_element firstline=new DBM_element();
			firstline.setDBM_i(0);
			firstline.setDBM_j(j);
			firstline.setStrictness(true);
			firstline.setValue(0);
			DBM[0][j]=firstline;						
		}
		
		for(int i=0;i<dimension;i++){//����DBM���޹�Ԫ�ص�ֵ,��ֵ����Ϊ88888
			for(int j=0;j<dimension;j++){
				if(DBM[i][j]==null){
					DBM_element unrelated=new DBM_element();
					unrelated.setDBM_i(i);
					unrelated.setDBM_j(j);
					unrelated.setValue(88888);
					unrelated.setStrictness(false);
					DBM[i][j]=unrelated;		
				}
			}
		}
		return DBM;
	}
}
