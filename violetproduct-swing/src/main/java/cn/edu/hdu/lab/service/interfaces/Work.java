package cn.edu.hdu.lab.service.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.horstmann.violet.application.gui.MainFrame;
import com.l2fprod.common.demo.Main;

import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.parserEA.InvalidTagException;

public interface Work {
	//��ʼ������UMLģ�͵�XML�ļ�
	public void transInitial(String xmlFileName) throws InvalidTagException, Exception;
   
	//��ʼ�����UMLģ��XML�ļ�
	public void transInitialHDU(String xmlFileName) throws Throwable;
	
	//���ݽ�����Ϣ�ṩ����ִ��˳���ϵ
	public Map<String, List<InterfaceUCRelation>> provideUCRelation();
	
	//���������µĳ�����Ϣ
	public List<InterfaceIsogenySD> provideIsogencySD();
	
	//���ݽ����ṩ��ÿ������������ִ��˳���ϵ��д�ľ��󼯺ϻ���ÿ�������ĳ�����д�ľ��󼯺ϣ��������
	//List ��һ��ֵΪ��������ʾ���ڶ������Ϊ�������
	public List<Object> calculateProb(List<double[][]> proMatrixList);
	
	//����ĳ������� ���ظ�ֵ
	public void assignmentPro(List<InterfaceIsogenySD> IISDList);
	
	//��֤
	public List<Object> transVerify() throws InvalidTagException;
	public void transToMarckov(Map<String, List<InterfaceUCRelation>> UCRMap) throws Exception;
	public void probabilityAndReachableTest() throws Exception;
	public void writeMarkov(String mcXMLFileName,MainFrame mainFrame,List<String> seqNames,List<String> ucNames) throws IOException, Exception;

}
