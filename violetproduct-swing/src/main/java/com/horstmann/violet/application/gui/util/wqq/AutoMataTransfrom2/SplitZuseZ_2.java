package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class SplitZuseZ_2 {
	/**
	 * �þ���Z2��־���Z1��Z1��Z2��û�й淶��(�ж����������ཻ�Ƿ�Ϊ���ǣ�Ҫ����������Ĺ淶�������ཻ)
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
		
		
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(copyZ1), Floyds.floyds(Z2)))==1){//���Z1��Z2Ϊ�գ��򷵻�Z1����ΪZ1���ñ�Z2���(�ж����������ཻ�Ƿ�Ϊ���ǣ�Ҫ����������Ĺ淶�������ཻ)
			Zones.add(Z1);
		}
		
		else{//���Z1��Z2��Ϊ��
			Zones.add(AndDBM.andDBM(Z1, Z2));//��Z1��Z2����Zones
			ArrayList<DBM_element[][]> complements=Complement_2.complement(Z2);//��Z2�Ĳ���
			for(DBM_element[][] com:complements){//����Z2�Ĳ���
				if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(copyZ1), Floyds.floyds(com)))==1){//���һ��������Z1�ཻΪ�գ���ʲô��������
					
				}
				else{
					Zones.add(AndDBM.andDBM(copyZ1, com));//���һ��������copyZ1�ཻ��Ϊ�գ������Ľ������zones
					ArrayList<DBM_element[][]> com_com=Complement_2.complement(com);//����������Ĳ���
					copyZ1=AndDBM.andDBM(copyZ1, com_com.get(0));//��������ʣ�µĲ����ǲ����Ĳ�����copyZ1�Ľ�					
				}
			
			}
		}
		return Zones;
	}

}
