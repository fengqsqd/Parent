package cn.edu.hdu.lab.service.parserHDU;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cn.edu.hdu.lab.dao.uml.DiagramsData;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.REF;
import cn.edu.hdu.lab.dao.uml.SDRectangle;

public class FixTool {
	@SuppressWarnings("unchecked")
	public static void fixFragmentsOfOneDiagram(ArrayList<Fragment> allFrags,DiagramsData dd) 
	{
		ArrayList<Fragment> fragments = allFrags;		
		Collections.sort(fragments, new SortByTopWithFrag()); //�����������,�߶���С�������Ƭ�δ��ϵ���
		for(Fragment frag:fragments)
		{
			Collections.sort(frag.getOperands(), new SortByTopWithOper()); //�����������,�߶���С�������Ƭ�δ��ϵ���
						
		}
		//������Ƭ�η�װ��������		
		encapsulateOperWithRef(allFrags,dd.getRefArray());		
		//�����Ƭ�εĲ������װ��Ϣ
		fixMessageInOperand(allFrags,dd.getMessageArray());
		//**********���������������Ƭ�ε�Ƕ�׵����
		fixFragments(fragments);
		
	}
	@SuppressWarnings("unchecked")
	public static void sortMesses(ArrayList<Message> messageList) {
			Collections.sort(messageList, new SortByYWithMess());		
	}
	@SuppressWarnings("unchecked")
	private static void  encapsulateOperWithRef(ArrayList<Fragment> fragments,ArrayList<REF> refs)
	{
		Collections.sort(refs, new SortByTopWithRef());//������Ƭ�ν�������
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
		
	}
	/**
	 * �������꣬����ϢǶ�׽���Ӧ�Ĳ�����
	 * @param allFrags
	 * @param messes
	 */
	private static void fixMessageInOperand(ArrayList<Fragment> allFrags,ArrayList<Message> messes)
	{
		
		for (Message m:messes) {			
			for(int i=allFrags.size()-1;i>=0;i--)
			{
				int f1=0;
				if(m.getPointY()>=allFrags.get(i).getRectangle().getTop()
						&&m.getPointY()<=allFrags.get(i).getRectangle().getBottom())
				{
					for(int j=allFrags.get(i).getOperands().size()-1;j>=0;j--)
					{
						if(m.getPointY()>=allFrags.get(i).getOperands().get(j).getRectangle().getTop()
								&& m.getPointY()<=allFrags.get(i).getOperands().get(j).getRectangle().getBottom())
						{
							
							m.setInFragment(true);
							m.setFragmentId(allFrags.get(i).getId());
							m.setFragType(allFrags.get(i).getType());
							m.setOperandId(allFrags.get(i).getOperands().get(j).getId());		
							allFrags.get(i).getOperands().get(j).getMessages().add(m);
							f1=1;
							break;
						}
					}
				}
				if(f1==1)
				{
					break;
				}
			}
		}
	}
	/**
	 * ���������������Ƭ�ε�Ƕ�����
	 * @param fragments
	 */
	private static void fixFragments(ArrayList<Fragment> fragments)
	{
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
			
			//ʵ��һ����Ϣ�ıȽ���
			@SuppressWarnings("rawtypes")
			static class SortByYWithMess implements Comparator {
				 public int compare(Object o1, Object o2) {
					 Message m1 = (Message) o1;
					 Message m2 = (Message) o2;
				  return m1.getPointY()> m2.getPointY() ? 1 : -1;
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
