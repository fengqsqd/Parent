package cn.edu.hdu.lab.dao.interfacedao;
/*
 * �������ִ��˳���ϵ�Լ���Ӧ�ĸ���
 */
public class InterfaceUCRelation {
	private InterfaceUC startUC;  
	private InterfaceUC endUC;
	private String UCRelation;
	private double UCRelProb;
	public InterfaceUCRelation(){}
	public InterfaceUC getStartUC() {
		return startUC;
	}
	public void setStartUC(InterfaceUC startUC) {
		this.startUC = startUC;
	}
	public InterfaceUC getEndUC() {
		return endUC;
	}
	public void setEndUC(InterfaceUC endUC) {
		this.endUC = endUC;
	}
	public String getUCRelation() {
		return UCRelation;
	}
	public void setUCRelation(String uCRelation) {
		UCRelation = uCRelation;
	}
	public double getUCRelProb() {
		return UCRelProb;
	}
	public void setUCRelProb(double uCRelProb) {
		UCRelProb = uCRelProb;
	}
	
}
