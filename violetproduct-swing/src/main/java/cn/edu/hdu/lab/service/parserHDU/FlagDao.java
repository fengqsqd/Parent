package cn.edu.hdu.lab.service.parserHDU;

public class FlagDao {
	private String ID;             //����Ψһ��ʶ
	private boolean enFlag=false;  //�Ƿ񱻷�װ��Ƕ��
	private int count=0;           //���ִ���
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public boolean isEnFlag() {
		return enFlag;
	}
	public void setEnFlag(boolean enFlag) {
		this.enFlag = enFlag;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

}
