package com.horstmann.violet.application.gui.util.wujun.SequenceTransfrom;

public class REF{

	String lastMessageID;//3�����init REF:XXXXX EAXXXX

	String inFragName;//����
	String inFragID;//����
	String diagramName;//����
	String refID;//��ʱû�õ�����
	
	WJRectangle rectangle;
	int index;
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	public String getInFragName() {
		return inFragName;
	}
	public void setInFragName(String inFragName) {
		this.inFragName = inFragName;
	}
	public String getDiagramName() {
		return diagramName;
	}
	public void setDiagramName(String diagramName) {
		this.diagramName = diagramName;
	}
	public String getLastMessageID() {
		return lastMessageID;
	}
	public void setLastMessageID(String lastMessageID) {
		this.lastMessageID = lastMessageID;
	}
	public String getInFragID() {
		return inFragID;
	}
	public void setInFragID(String inFragID) {
		this.inFragID = inFragID;
	}
	
	
}
