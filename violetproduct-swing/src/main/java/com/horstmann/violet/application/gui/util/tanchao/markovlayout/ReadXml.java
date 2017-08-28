package com.horstmann.violet.application.gui.util.tanchao.markovlayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadXml {
  String filename;
  int A[][]=new int[500][500];//Ĭ��ͼ��20���ڵ� 
  SAXReader reader=new SAXReader();
  Document dom;
  Element root;
  int TransitionNum=0;//���ڼ�¼�ߵ���Ŀ
  int LocationNum=0;//���ڼ�¼�����Ŀ
  List<Element> transitionList=new ArrayList<Element>();
  List<Element> locationList=new ArrayList<Element>();
  
  public ReadXml(String filename){
	  this.filename=filename;
  }
  
  /**
   * 
   * @param fileName��Ҫ�����ļ�
   * @return  ����ͼ�����ӵ����
   * @throws DocumentException
   */
  public int[][] find(String fileName)  {
	  try {
		dom=reader.read(fileName);
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  root=dom.getRootElement();
	  Element nodes=root.element("nodes");
	  Element edges=root.element("edges");
	  int m=0,n=0,N=0;//m n��Ӧ��ά����ĺ������� ��N��ʾ�����ڵ�λ��
	  transitionList=edges.elements("MarkovTransitionEdge");
	  Element MarkovStartNode=nodes.element("MarkovStartNode");//����ʼ�Ľڵ�
	  List<Element> nodeList=nodes.elements("MarkovNode");//�����Ľڵ����
	  locationList.add(MarkovStartNode);
	  Iterator it=nodeList.iterator();
	  while(it.hasNext()){
		  locationList.add((Element)it.next());
	  }
	  for(Element transition:transitionList){
		  String start=transition.element("start").attribute("reference").getValue();
//		  System.out.println("start");
		  String end=transition.element("end").attribute("reference").getValue();
//		  System.out.println("end");
		  for(Element location:locationList){
			  if(start.equals(location.attribute("id").getValue())==true){
				  N=locationList.indexOf(location);
				  m=N;
			  }
			  if(end.equals(location.attribute("id").getValue())==true){
				  N=locationList.indexOf(location);
				  n=N;
			  }
		  }
		  A[m][n]=1;
	  }
	  return A;
  }
  /**
   * 
   * @param filename ��ȡ���ļ���
   * @return  ���ض�Ӧ�ڵ�����ݣ����������ʽ
   * @throws DocumentException
   */
  public String[] getIdValue(String filename) {
	  try {
		dom=reader.read(filename);
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  root=dom.getRootElement();
	  Element nodes=root.element("nodes");
	  List<Element> nodelist=new ArrayList<Element>();
	  List<Element> nodelist1=nodes.elements("MarkovNode");
	  Element startNode=nodes.element("MarkovStartNode");
//	  nodelist.add(startNode);
	  nodelist.add(startNode);
	  for(int count=0;count<nodelist1.size();count++){
		  nodelist.add(nodelist1.get(count));
	  }
	  int j=0;
	  String[] VertexID=new String[500];
	  for(Element node:nodelist){
		  VertexID[j]=node.attribute("id").getValue();
		  j++;
	  }
	  return VertexID;
  }
  
  /**
   * 
   * @param filename��ȡ��xml�ļ�
   * @return ��ýڵ����Ŀ
   */
  public int getNodeNum(String filename){
	  try {
		dom=reader.read(filename);
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  root=dom.getRootElement();
	  Element nodes=root.element("nodes");
	  List<Element> nodelist=new ArrayList<Element>();
	  List<Element> nodelist1=nodes.elements("MarkovNode");
	  Element startNode=nodes.element("MarkovStartNode");
//	  nodelist.add(startNode);
	  nodelist.add(startNode);
	  for(int count=0;count<nodelist1.size();count++){
		  nodelist.add(nodelist1.get(count));
	  }
	  return nodelist.size();
	  
  }
  /**
   * 
   * @param filename��ȡxml�ļ�
   * @return ���xml�бߵ���Ŀ
   */
  public int getEdgeNum(String filename){
	  try {
		dom=reader.read(filename);
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  root=dom.getRootElement();
	  Element edges=root.element("edges");
	  int m=0,n=0,N=0;//m n��Ӧ��ά����ĺ������� ��N��ʾ�����ڵ�λ��
	  transitionList=edges.elements("MarkovTransitionEdge");
	  return transitionList.size();
	  
  }
}
