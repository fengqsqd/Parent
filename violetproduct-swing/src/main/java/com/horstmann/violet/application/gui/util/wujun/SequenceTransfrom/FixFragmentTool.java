package com.horstmann.violet.application.gui.util.wujun.SequenceTransfrom;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class FixFragmentTool {
	public static HashMap<String, WJRectangle> rectangleById = new HashMap<String, WJRectangle>();
	public static HashMap<String, String> xrefValueById = new HashMap<String, String>();
	
	public static int refIndexInDiagram(REF ref, WJDiagramsData diagramsData) {
		int index = 0;
		double refTop = ref.rectangle.top;
		for(WJMessage message : diagramsData.getMessageArray()) {
			if (refTop > message.getPointY()) {
				index ++;
			} else {
				break;
			}
		}
		return index;
	}
	
	public static double pointYFromValueString(String value) {
		if (value.contains("PtStartY=-")) {
			return Double.parseDouble(value.split("PtStartY=-")[1].split(";PtEndX=")[0]);
		}
		return 0;
	}
	
	public static WJRectangle rectangleFromValueString(String value) {
		//Left=653;Top=50;Right=743;Bottom=868;
		//SX=0;SY=0;EX=0;EY=0;Path=;
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
		
		return new WJRectangle(l, t, r, b);
	}


	public static WJRectangle operandRectangle(WJFragment fragment) {//fragmentһ���ǲ�����
		WJRectangle fatherRectangle = FixFragmentTool.rectangleById.get(fragment.comId);
		String value = xrefValueById.get(fragment.comId);
		String[] strs = value.split(";Name=");
		
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<Double> sizeList = new ArrayList<Double>(); 
		double[] size;
		for(String str: strs) {
			//����;Size=100;~~~~~~~~
			if (str.contains(";Size=")) {
				nameList.add(str.split(";Size=")[0]);
				sizeList.add(Double.parseDouble(str.split(";Size=")[1].split(";GUID=")[0]));
				
			}
		}
		int index = 0;
		double sumH = 0;
		for(int i = 0; i < nameList.size(); i++) {
			sumH += sizeList.get(i);
			if (nameList.get(i).equals(fragment.fragCondition)) {
				index = i;
				break;
			}
		}
		
		WJRectangle rectangle = new WJRectangle(fatherRectangle);
		if (index == 0) {//�����һ�� ֻ��Ҫ�޸�top
			rectangle.top = rectangle.bottom - sizeList.get(0);
		} else if (index == nameList.size() - 1){//�ǵ�һ�� ֻ��Ҫ�޸�bottom
			rectangle.bottom = rectangle.top + sizeList.get(index);
		} else {//���м��һ�� 
			rectangle.top = rectangle.bottom - sumH;
			rectangle.bottom = rectangle.top + sizeList.get(index);
		}
		rectangleById.put(fragment.fragId, rectangle);
 		return rectangle;
	}

	static class SortByTop implements Comparator {
		 public int compare(Object o1, Object o2) {
			 WJFragment f1 = (WJFragment) o1;
			 WJFragment f2 = (WJFragment) o2;
		  return f1.rectangle.top > (f2.rectangle.top) ? 1 : -1;
		 }
	}
	public static void fixFragmentsOfOneDiagram(WJDiagramsData diagramData) {
		ArrayList<WJFragment> fragments = diagramData.getFragmentArray();
		ArrayList<REF> refs = diagramData.getRefArray();
		
		Collections.sort(fragments, new SortByTop());
		for (int i = 1; i < fragments.size(); i++) {
			WJFragment fragmentI = fragments.get(i);
			for (int j = i - 1; j >= 0; j--) {
				WJFragment fragmentJ = fragments.get(j);
				if (rectangleI_in_rectangleJ(fragmentI.rectangle, fragmentJ.rectangle)) {
					fragmentI.BigId = fragmentJ.fragId;
					break;
				}
			}
		}
		
		for (int i = 0; i < refs.size(); i++) {
			
			REF ref = refs.get(i);
			
			for (int j = fragments.size() - 1; j >= 0; j--) {
				WJFragment fragmentJ = fragments.get(j);
				if (rectangleI_in_rectangleJ(ref.rectangle, fragmentJ.rectangle)) {
					ref.inFragID = fragmentJ.fragId;
					ref.inFragName = fragmentJ.fragType;
					break;
				}
			}
		}
	}


	private static boolean rectangleI_in_rectangleJ(WJRectangle rectangleI, WJRectangle rectangleJ) {
		if (rectangleI.top < rectangleJ.top) {
			return false;
		}
		if (rectangleI.left < rectangleJ.left) {
			return false;
		}
		if (rectangleI.bottom > rectangleJ.bottom) {
			return false;
		}
		if (rectangleI.right > rectangleJ.right) {
			return false;
		}
		return true;
	}
}
