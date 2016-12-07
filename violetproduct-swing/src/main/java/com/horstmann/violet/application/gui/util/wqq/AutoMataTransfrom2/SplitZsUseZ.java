package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;

import java.util.ArrayList;

public class SplitZsUseZ {
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
			DBMs=SplitZuseZ.splitZuseZ(zone, Floyds.floyds(Z));//��ZҲ�淶��
			for(DBM_element[][] dbm:DBMs){
				Zones.add(dbm);
			}
			
		}
		
		return Zones;
	}
}
