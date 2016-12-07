package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

public class Copy {
	/**
	 * ��DBM�����еĵ�j��ʱ�Ӹ��Ƹ���i��ʱ��  ����x=y
	 * @param DBM
	 * @param i
	 * @param j
	 * @return
	 */
	public static DBM_element[][] copy(DBM_element[][] DBM,int i,int j){
		for(int k=0;k<DBM.length;k++){
			DBM[i][k]=DBM[j][k];
			DBM[k][i]=DBM[k][j];
		}
		DBM_element ele=new DBM_element();
		ele.setDBM_i(i);
		ele.setDBM_j(j);
		ele.setStrictness(true);
		ele.setValue(0);
		DBM[i][j]=ele;
		DBM_element elem=new DBM_element();
		elem.setDBM_i(j);
		elem.setDBM_j(i);
		elem.setStrictness(true);
		elem.setValue(0);
		DBM[j][i]=elem;
		
		return DBM;
	}
}
