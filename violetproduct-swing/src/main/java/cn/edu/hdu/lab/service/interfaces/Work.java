package cn.edu.hdu.lab.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.parser.InvalidTagException;

public interface Work {
	//��ʼ������UMLģ�͵�XML�ļ�
	public void transInitial(String xmlFileName) throws InvalidTagException;
   
	//���ݽ�����Ϣ�ṩ����ִ��˳���ϵ
	public Map<String, List<InterfaceUCRelation>> provideUCRelation();
	
	//���������µĳ�����Ϣ
	public List<InterfaceIsogenySD> provideIsogencySD();
	
	//���ݽ����ṩ��ÿ������������ִ��˳���ϵ��д�ľ��󼯺ϻ���ÿ�������ĳ�����д�ľ��󼯺ϣ��������
	//List ��һ��ֵΪ��������ʾ���ڶ������Ϊ�������
	public List calculateProb(List<double[][]> proMatrixList);
	
	//����ĳ������� ���ظ�ֵ
	public void assignmentPro(List<InterfaceIsogenySD> IISDList);
	
	//��֤
	public List transVerify() throws InvalidTagException;
	public void transToMarckov(Map<String, List<InterfaceUCRelation>> UCRMap);
	public void probabilityTest();
	public void writeMarkov(String mcXMLFileName) throws IOException;

}
