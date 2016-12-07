package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

/**
 * ��ȡ�����е�ʱ�Ӹ�λ��Ϣ������һ��ֻ������λ��ʱ����Ϣ�ľ���
 * @author Seryna
 *
 */
public class ExtractReset {
	public static DBM_element[][] extract(DBM_element[][] dbm) {
		int dimension=dbm.length;//��ȡDBM��ά��
		 DBM_element[][] fdbm=Floyds.floyds(dbm);//��dbm�淶��������ȡʱ�Ӹ�λ��Ϣ
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
		
		for(int i=1;i<dimension;i++){//��ȡʱ�Ӹ�λ�����Ϣ
			for(int j=1;j<dimension;j++){
				if(i!=j){
					DBM_element con=new DBM_element();
					con.setDBM_i(i);
					con.setDBM_j(j);
					con.setValue(fdbm[i][j].getValue());
					con.setStrictness(fdbm[i][j].isStrictness());
					DBM[i][j]=con;
				}
			}
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
