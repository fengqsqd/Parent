package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;

import java.util.ArrayList;
import java.util.List;

public class FragmentPartInfo {

	
	private String conditionText;
	private String size;//�����sizeָ���Ƿֿ�����߶�
	private List<CombinedFragmentInfo> nestingchilds=new ArrayList<CombinedFragmentInfo>();//Ƕ�׵����Ƭ��
	private List<MessageInfo> concluedmessages=new ArrayList<MessageInfo>();//���ǵ���Ϣ
	public String getConditionText() {
		return conditionText;
	}
	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public List<CombinedFragmentInfo> getNestingchilds() {
		return nestingchilds;
	}
	public void AddNestingchilds(CombinedFragmentInfo nestingchilds) {
		this.nestingchilds.add(nestingchilds);
	}
	public List<MessageInfo> getConcluedmessages() {
		return concluedmessages;
	}
	public void AddConcluedmessages(MessageInfo concluedmessages) {
		this.concluedmessages.add(concluedmessages);
	}
	
	
	
	
}
