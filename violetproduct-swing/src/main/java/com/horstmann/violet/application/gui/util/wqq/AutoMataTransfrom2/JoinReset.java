package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

public class JoinReset {
	/**
	 * ����ȡ��ʱ�Ӹ�λ�����Ϣ���뵽DBM������
	 * @param dbm1 ֻ������λ��ʱ����Ϣ�ľ���
	 * @param dbm2Ҫ����ʱ�Ӹ�λ����Ϣ�ľ���
	 * @return ���ؼ���ʱ�Ӹ�λ����Ϣ�ľ���
	 */
	public static DBM_element[][] joinClock( DBM_element[][] dbm1,DBM_element[][] dbm2) {
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
