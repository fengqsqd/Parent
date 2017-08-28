package cn.edu.hdu.lab.service.parserEA;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import cn.edu.hdu.lab.dao.uml.DiagramsData;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.REF;
import cn.edu.hdu.lab.dao.uml.SDRectangle;
public class FixFragmentTool {
	// <��ͬͼԪ��Ϣ����ID�����Ƭ��ID������ID������ID����ϢID���� ��Ϣ�����Ƭ�Σ����ã�������Ϣ������>    �ɶԴ��
	//���������˳��ͼ��ͼԪ������Ϣ
	public static HashMap<String, SDRectangle> rectangleById = new HashMap<>();	
	
	
	// <���Ƭ��ID��������������������Ϣ�ַ���> �ɶԴ��
	//���������˳��ͼ���Ƭ���²������������Ϣ�ַ��������ַ�����������
	public static HashMap<String, String> xrefValueById = new HashMap<>();
	
	/**
	 * ��������Ϣ֮������λ��
	 * @param ref
	 * @param diagramsData
	 * @return
	 */
	public static int refIndexInDiagram(REF ref, DiagramsData diagramsData) {
		int index = 0;
		double refTop = ref.getRectangle().getTop();
		for(Message message : diagramsData.getMessageArray()) {
			if (refTop > message.getPointY()) {
				index ++;
			} else {
				break;
			}
		}
		return index;
	}
	
	/**
	 * ֱϵ���������ƬƬ��֮������λ��
	 * @param ref
	 * @param diag
	 * @return
	 */
	public static int refIndexBetweenFragsInDiagram(REF ref, DiagramsData diag) {
		int Findex = 0;
		
		for(Fragment frag:diag.getFragmentArray())
		{
			if(ref.getRectangle().getTop()>frag.getRectangle().getTop())
			{
				Findex ++;	
			}
			else
			{
				break;
			}
		}
		return Findex;
	}
	
	public static double pointYFromValueString(String value) {
		if (value.contains("PtStartY=-")) {
			return Double.parseDouble(value.split("PtStartY=-")[1].split(";PtEndX=")[0]);
		}
		return 0;
	}
	public static String handleFromTimeConstraint(String str)
	{
		if(str!=null&&str.contains("DCBMGUID"))//������ʼ״̬��ʱ��Լ��
		{
			//System.out.println("ʱ��Լ����������������"+str);
			//System.out.println(str.split("DCBM=")[1].split(";DCBM")[0]);
			return str.split("DCBM=")[1].split(";DCBM")[0];
		}		
		return null;		
	}
	//��ȡһ��˳��ͼ��������Ϣ�����Ƭ�Σ����ã�������Ϣ��������
	public static SDRectangle rectangleFromValueString(String value) {
		double l = 0,t = 0,r = 0,b = 0;
		if (value.contains("Left")) {
			//�����ַ���
			String[] strs = value.split(";");
			for(String str : strs) {
				if (str.contains("Left")) {
					l = Double.parseDouble(str.split("Left=")[1]);
					continue;
				} else  if (str.contains("Top")) {
					t = Double.parseDouble(str.split("Top=")[1]);
					continue;
				} else  if (str.contains("Right")) {
					r = Double.parseDouble(str.split("Right=")[1]);
					continue;
				} else  if (str.contains("Bottom")) {
					b = Double.parseDouble(str.split("Bottom=")[1]);
					continue;
				}
			}
		} 
		
		return new SDRectangle(l, t, r, b);
	}


	/*
	 * ͨ�����Ƭ�ε�ID���ҵ������Ƭ�ζ�Ӧ��������Ϣ��
	 * Ȼ��ͨ�������Ƭ��ID�ҵ���Ӧ�Ĳ�����������Ϣ��������н������㡣
	 */
	public static void operandRectangle(Fragment fragment) {
		SDRectangle fatherRectangle = FixFragmentTool.rectangleById.get(fragment.getId());
		if(fragment.getOperands().size()==1)
		{
			fragment.getOperands().get(0).setRectangle(fatherRectangle);
		}
		else //�����������������꣬��������и�ֵ
		{
			String value = xrefValueById.get(fragment.getId());
			String[] strs = value.split(";Name=");
			
			ArrayList<String> nameList = new ArrayList<>();
			ArrayList<Double> sizeList = new ArrayList<>(); //��ŵĲ�����߶������Ƭ�����������ߵĸ߶Ȳ�
			for(String str: strs) {
				//����;Size=100;~~~~~~~~
				if (str.contains(";Size=")) {
					nameList.add(str.split(";Size=")[0]);
					//System.out.println(str.split(";Size=")[1].split(";GUID=")[0]);
					sizeList.add(Double.parseDouble(str.split(";Size=")[1].split(";GUID=")[0].split(";")[0 ]));				
				}
			}
			
			for(Operand oper:fragment.getOperands())
			{
				int index = 0; //�±�
				double sumH = 0;//�߶Ⱥ�
				for(int i = 0; i < nameList.size(); i++) 
				{
					sumH += sizeList.get(i);
					if(nameList.get(i).equals(oper.getCondition())){
							index = i;
							break;
						}
					}
				
				//��ȡ�������Ƭ���²���������µľ��Ը߶�
				SDRectangle rectangle = new SDRectangle(fatherRectangle); //�̳��������ߵ����ݣ��޸��ϸ��µ�
				
				if (index == 0) {//�����һ�� ֻ��Ҫ�޸�top
					rectangle.setTop(rectangle.getBottom()-sizeList.get(0));
					
					
				} else if (index == nameList.size() - 1){//�ǵ�һ�� ֻ��Ҫ�޸�bottom
					rectangle.setBottom(rectangle.getTop()+sizeList.get(index));
					
				} else {//���м��һ�� 
					rectangle.setTop(rectangle.getBottom()-sumH);
					rectangle.setBottom(rectangle.getTop()+sizeList.get(index));
				}
				oper.setRectangle(rectangle);
				
			}
		}
	} 
	

	
	
	// ������������ ����ͼ�� fragment ֮���Ƕ�׹�ϵ  +  fragment��ref֮���Ƕ�׹�ϵ 
	//������Ƕ�ױ�������
	@SuppressWarnings("unchecked")
	public static void fixFragmentsOfOneDiagram(DiagramsData diagramData,ArrayList<Fragment> allFrags) 
	{
		ArrayList<Fragment> fragments = diagramData.getFragmentArray();		
		Collections.sort(fragments, new SortByTopWithFrag()); //�����������,�߶���С�������Ƭ�δ��ϵ���
		for(Fragment frag:fragments)
		{
			Collections.sort(frag.getOperands(), new SortByTopWithOper()); //�����������,�߶���С�������Ƭ�δ��ϵ���
		}
		
		ArrayList<REF> refs = diagramData.getRefArray();
		Collections.sort(refs, new SortByTopWithRef());//������Ƭ�ν�������
	/*	System.out.println("���������ã�");
		for(REF r:refs)
		{
			System.out.println(r.toString());
		}*/
		
		//System.out.println("***********��ͼ���ƣ�"+diagramData.getName());
		//System.out.println("���������Ƭ�Σ�");
		/*if(fragments.size()>0)
		{
			for(Fragment f:fragments)
			{
				System.out.println(f.getName());
			}
			
		}
		System.out.println("���к������Ƭ�Σ�");
		if(refs.size()>0)
		{
			for(REF r:refs)
			{
				System.out.println(r.getRefName());
			}
			
		}*/
		
		//�����������ò��ɵߵ�

//����   ����Ƭ��ref ����������ID***************************************************************
//������������Ƭ�β�����
		
		for (int i = 0; i < refs.size(); i++) 
		{			
			REF ref = refs.get(i);			
			int flag=0;
			for (int j = fragments.size() - 1; j >= 0; j--) 
			{				
				Fragment fragmentJ = fragments.get(j);
				for(int k=fragmentJ.getOperands().size()-1;k>=0;k--)
				{
					Operand oper=fragmentJ.getOperands().get(k);
					if (rectangleI_in_rectangleJ(ref.getRectangle(), oper.getRectangle())) {						
						ref.setInFragFlag(true);
						ref.setInID(oper.getId());   
						ref.setInName(oper.getCondition());
						oper.setRef(ref);
						refs.remove(ref);
						i--;//�����䶯������
						flag=1;
						break;
					}
				}
				if(flag==1)
				{
					break;
				}
			}
		}
	
//���������������Ƭ�ε�Ƕ�׵����
		for (int i = 1; i < fragments.size(); i++) {
			int flag=0;
			Fragment fragmentI = fragments.get(i);
			for (int j = i - 1; j >= 0; j--) {
				Fragment fragmentJ = fragments.get(j);
				for(int k=fragmentJ.getOperands().size()-1;k>=0;k--)
				{
					Operand oper=fragmentJ.getOperands().get(k);
					if (rectangleI_in_rectangleJ(fragmentI.getRectangle(), oper.getRectangle())) {
//�������Ƭ������������ID*********************************************************************
						fragmentI.setInID(oper.getId());
						oper.getFragmentIDs().add(fragmentI.getId());//����������������Ƭ�ε�id
						oper.getFragments().add(fragmentI);
						oper.setHasFragment(true);
						fragmentI.setEnFlag(true);
						flag=1;
						break;
					}
				}
				if(flag==1)
				{
					break;
				}			
			}
		}
		

		
		//ȥ��Ƕ�׵����Ƭ��  
		//i--����д��Ϊ�˸ı����������ⱨ�������޸��쳣����)
		for(int i=0;i<fragments.size();i++)
		{
			if(fragments.get(i).isEnFlag())
			{
				allFrags.remove(fragments.get(i));
				fragments.remove(i);
				i--;
			}
		}
	}

	private static boolean rectangleI_in_rectangleJ(SDRectangle rectangleI, SDRectangle rectangleJ) {
		if (rectangleI.getTop() < rectangleJ.getTop()) {
			return false;
		}
		if (rectangleI.getLeft() < rectangleJ.getLeft()) {
			return false;
		}
		if (rectangleI.getBottom() > rectangleJ.getBottom()) {
			return false;
		}
		if (rectangleI.getRight() > rectangleJ.getRight()) {
			return false;
		}
		return true;
	}
	
	//ʵ��һ�����Ƭ�εıȽ���
		@SuppressWarnings("rawtypes")
		static class SortByTopWithFrag implements Comparator {
			 public int compare(Object o1, Object o2) {
				 Fragment f1 = (Fragment) o1;
				 Fragment f2 = (Fragment) o2;
			  return f1.getRectangle().getTop() > f2.getRectangle().getTop() ? 1 : -1;
			 }
		}
		//ʵ��һ����������ıȽ���
		@SuppressWarnings("rawtypes")
		static class SortByTopWithOper implements Comparator {
			 public int compare(Object o1, Object o2) {
				 Operand oper1 = (Operand) o1;
				 Operand oper2 = (Operand) o2;
			  return oper1.getRectangle().getTop() > oper2.getRectangle().getTop() ? 1 : -1;
			 }
		}
		//ʵ��һ������Ƭ�εıȽ���
		@SuppressWarnings("rawtypes")
		static class SortByTopWithRef implements Comparator {
			 public int compare(Object o1, Object o2) {
				 REF r1 = (REF) o1;
				 REF r2 = (REF) o1;
			  return r1.getRectangle().getTop() > r2.getRectangle().getTop() ? -1 : 1;
			 }
		}
}
