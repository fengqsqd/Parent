package com.horstmann.violet.application.gui.util.wujun.SequenceTransfrom;

public class UppaalTransition 
{
	String sourceId;//Դ��״̬id
	String targetId;//Ŀ��״̬id
	
	int id;
	String Kind = "synchronisation";
	String nameText;//��Ϣ��������������
	String nameS="null";//Դ��״̬����
	String nameT="null";//Ŀ��״̬����
	
	String receiveOrSend;
	String typeAndCondition;
	String typeId;
	
	String SEQDC="null";//1
	String DCBM="null";//2   ��Ӧ״̬�ϵ�ʱ��Լ��
	String SEQDO="null";//3 ��Ϣ�ϵ�ʱ��Լ��
	String SEQTC="null";//4
	String SEQTO="null";//5
	
	String inString="null";//����
	String outString="null";//���
	
	String RESET="null";//ʱ�Ӹ�λ
	
	String use="null";
	String def="null";
	
	String T1 = "0";	
	String T2 = "0";
	
	
	public String getRESET() {
		return RESET;
	}
	public void setRESET(String rESET) {
		RESET = rESET;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getReceiveOrSend() {
		return receiveOrSend;
	}
	public void setReceiveOrSend(String receiveOrSend) {
		this.receiveOrSend = receiveOrSend;
	}
	public String getTypeAndCondition() {
		return typeAndCondition;
	}
	public void setTypeAndCondition(String typeAndCondition) {
		this.typeAndCondition = typeAndCondition;
	}
	public String getInString() {
		return inString;
	}
	public void setInString(String inString) {
		this.inString = inString;
	}
	public String getOutString() {
		return outString;
	}
	public void setOutString(String outString) {
		this.outString = outString;
	}
	public String getSEQDC() {
		return SEQDC;
	}
	public void setSEQDC(String sEQDC) {
		SEQDC = sEQDC;
	}
	public String getSEQDO() {
		return SEQDO;
	}
	public void setSEQDO(String sEQDO) {
		SEQDO = sEQDO;
	}
	public String getSEQTC() {
		return SEQTC;
	}
	public void setSEQTC(String sEQTC) {
		SEQTC = sEQTC;
	}
	public String getSEQTO() {
		return SEQTO;
	}
	public void setSEQTO(String sEQTO) {
		SEQTO = sEQTO;
	}
	public String getDCBM() {
		return DCBM;
	}
	public void setDCBM(String dCBM) {
		DCBM = dCBM;
	}
	public String getT1() {
		return T1;
	}
	public void setT1(String t1) {
		T1 = t1;
	}
	public String getT2() {
		return T2;
	}
	public void setT2(String t2) {
		T2 = t2;
	}
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id=id;
	}
	public String getSourceId() 
	{
		return sourceId;
	}
	public void setSourceId(String sourceId) 
	{
		this.sourceId = sourceId;
	}
	public String getTargetId() 
	{
		return targetId;
	}
	public void setTargetId(String targetId) 
	{
		this.targetId = targetId;
	}
	public String getKind() 
	{
		return Kind;
	}
	public void setKind(String kind) 
	{
		Kind = kind;
	}
	public String getNameText() 
	{
		return nameText;
	}
	public void setNameText(String nameText) 
	{
		this.nameText = nameText;
	}
	public String getNameS() 
	{
		return nameS;
	}
	public void setNameS(String nameS) 
	{
		this.nameS = nameS;
	}
	public String getNameT() 
	{
		return nameT;
	}
	public void setNameT(String nameT) 
	{
		this.nameT = nameT;
	}
}