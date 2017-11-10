package com.horstmann.violet.application.gui.util.tanchao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
//Ĭ��xml�ĵ�һ��state�ǿ�ʼ�ڵ�
//�����ȶ�״̬(������Ϣ)������id
//�ٶ���Ϣ��ʱ�����ȷ����Ϣ�Ŀ�ʼ�ͽ�β��״̬��id
public class TianWriteToVioletMarkov {
   SAXReader reader =new SAXReader();
   //״̬�ļ���
   List<TanchaoMarkovNode> markovNodeInfo=new ArrayList<TanchaoMarkovNode>();
   //��Ϣ�ļ���
   List<TanchaoMarkovTransitionEdge> markovTransitionEdgeInfo=new ArrayList<TanchaoMarkovTransitionEdge>();
   //��ʼ��״̬
   TanchaoMarkovStartNode TanchaoMarkovStartNode=new TanchaoMarkovStartNode();
   int statesId=0;//���ڱ���states���ϵ��α�
   int i,j=0;//�ֱ���״̬�� list�ļ��ϵ��α꣬��Ϣlist���α�
   //�ȹ̶����꣬������չ�� (��Ϊ��ǩ������String����)
   String location_x="200.0";
   String location_y="200.0";
   
   /**
    * ��ȡ������ɵ�markov����Ϣ����ȡ������Ҫ����Ϣ;
    * @param path ��ȡ�ļ���·��
    * @throws DocumentException
    */
   public void find(String path) throws DocumentException{ 
	   File file = new File(path);
	   Document dom=reader.read(file);
	   Element root= dom.getRootElement();
	   List<Element> states=root.elements("state");
	   int length=states.size();
	   //****�ȴ���state�ļ���ȷ�����е�state��Ψһid****
       for(int i=0,j=0;i<length;i++){
    	   if(i==0){//��һ����ʼnode��������
    		   TanchaoMarkovStartNode.setId(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setChildren_id(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setLocation_id(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setUnderLocation_id(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setName_id(UUID.randomUUID().toString());
    		   TanchaoMarkovStartNode.setName(states.get(0).element("name").getText());
    	   }
    	   else{//������node
    	   markovNodeInfo.add(new TanchaoMarkovNode());
    	   markovNodeInfo.get(j).setId(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setChildren_id(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setLocation_id(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setChildren_id(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setUnderLocation_id(UUID.randomUUID().toString());
    	   markovNodeInfo.get(j).setName(states.get(i).element("name").getText());
    	   j++;
    	   }
       }
	   //*******�ٴ�����Ϣ********
	   //��� ��ʼ��stateNode(�����ʼ��״̬)
       Element startElement=states.get(statesId);
//       markovStartNode.setId(String.valueOf(markovNodeId++));
//	   markovStartNode.setName(startElement.element("name").getText());
	   //�Ը�node���ȵ���Ϣ  ���ȵ���Ϣ�����ж��
	   List<Element> arcs=startElement.elements("arc");
	   for(Element ele:arcs){
//		   List<Element> sonEles=ele.elements();
//		   for(Element e:sonEles){
//			   System.out.println(e.attributeValue("assignType"));
//		   }
		   		   
		   String arc_name=ele.element("name").getText();
		   String arcToNode=ele.element("to").getText();
		   String prob=ele.element("prob").getText();
		   
		   String conditions=ele.element("conditions").getText();
		   String owned=ele.element("owned").getText();
		   
//		   System.out.println(ele.element("assignType").getText());
		   String assignType="";
		   if(ele.element("assignType")!=null){
			   assignType=ele.element("assignType").getText();
		   }
		   
		   String assignValue="";
		   if(ele.element("assignValue")!=null){
			   assignValue=ele.element("assignValue").getText();
		   }
		   
//		   String assignType=ele.element("assignType").getText();
//		   String assignValue=ele.element("assignValue").getText();
		   
		   markovTransitionEdgeInfo.add(new TanchaoMarkovTransitionEdge());
		   markovTransitionEdgeInfo.get(j).setId(UUID.randomUUID().toString());
		   markovTransitionEdgeInfo.get(j).setName(arc_name);
		   markovTransitionEdgeInfo.get(j).setStart_reference(startElement.element("name").getText());
		   markovTransitionEdgeInfo.get(j).setProb(prob);
		   markovTransitionEdgeInfo.get(j).setEnd_reference(arcToNode);
		   markovTransitionEdgeInfo.get(j).setStartLocation_id(UUID.randomUUID().toString());
		   markovTransitionEdgeInfo.get(j).setEndLocation_id(UUID.randomUUID().toString());
		   markovTransitionEdgeInfo.get(j).setUnderLocation_id(UUID.randomUUID().toString());
		   
		   markovTransitionEdgeInfo.get(j).setConditions(conditions);
		   markovTransitionEdgeInfo.get(j).setOwned(owned);
		   markovTransitionEdgeInfo.get(j).setAssignType(assignType);
		   markovTransitionEdgeInfo.get(j).setAssignValue(assignValue);
		   j++;
	   }
	   statesId++;
	   
	   while(statesId<length){
		   //��ø�״̬
		   Element state=states.get(statesId);
		   String state_name=state.element("name").getText();
//		   markovNodeInfo.add(new MarkovNode());
//		   markovNodeInfo.get(i).setId(String.valueOf(markovNodeId++));
//		   markovNodeInfo.get(i).setName(state_name);
		   //��ø�״̬�µ���Ϣ//�����ж��
           List<Element> arcs1=state.elements("arc");
           for(Element ele:arcs1){
        	   
		   String arcName=ele.element("name").getText();//��Ϣname
		   String arcFromNode=state.element("name").getText();//��Ϣ��ʼnode��name
		   String arcToNode1=ele.element("to").getText();//��Ϣ�յ��name
		   
		   String conditions=ele.element("conditions").getText();
		   String owned=ele.element("owned").getText();
		   String assignType="";
		   if(ele.element("assignType")!=null){
			   assignType=ele.element("assignType").getText();
		   }
		   
		   String assignValue="";
		   if(ele.element("assignValue")!=null){
			   assignValue=ele.element("assignValue").getText();
		   }
		   String prob=ele.element("prob").getText();
		   
//		   String arccontent=getContent(ele);//��Ϣ���������(�������װ��)
		   markovTransitionEdgeInfo.add(new TanchaoMarkovTransitionEdge());
		   markovTransitionEdgeInfo.get(j).setId(UUID.randomUUID().toString());
		   markovTransitionEdgeInfo.get(j).setName(arcName);
//		   markovTransitionEdgeInfo.get(j).setContent(arccontent);
		   markovTransitionEdgeInfo.get(j).setStart_reference(arcFromNode);
		   markovTransitionEdgeInfo.get(j).setEnd_reference(arcToNode1);
		   
		   markovTransitionEdgeInfo.get(j).setConditions(conditions);
		   markovTransitionEdgeInfo.get(j).setOwned(owned);
		   markovTransitionEdgeInfo.get(j).setAssignType(assignType);
		   markovTransitionEdgeInfo.get(j).setAssignValue(assignValue);
		   markovTransitionEdgeInfo.get(j).setProb(prob);;
		   j++;
           }
           i++;
           statesId++;
	   }
	   //----------------���
//	   System.out.println("********"+markovStartNode.getName());
//	   System.out.println("$$$$$$$$$"+markovStartNode.getId());
//	   for(MarkovTransitionEdge edge:markovTransitionEdgeInfo){
//		   System.out.println(getId(edge.getStart_reference())+"------>"+getId(edge.getEnd_reference()));
//	   }
	   //----------------
   }
   /**
    * ��õ�ǰ��ǩ�µ����б�ǩ��������(���ڻ����Ϣ�ϵ���Ϣ����String)(��װ)
    * @param state
    * @return
    */
   public static String getContent(Element state){
	   String content="";
	   List<Element> elements=state.elements();
	   for(Element ele:elements){
		   if("to".equals(ele.getName())){//��������to��ʱ����ֹ
			   break;
		   }
//		   else if("stimulate".equals(ele.getName())){//�����ּ�����ʱ�򵥶�����
//			   //���ּ�����ʱ����String��������stimulate{paraName1:value1 paraName2:value2}
//			   content+=ele.getName();
//			   content+="{";
//			   List<Element> parameters=ele.elements();//���parameter��element�ļ���
//			   for(Element eleChild:parameters){
//				   String name="paramName";
//				   content+=name;
//				   content+=":";
//				   String value=eleChild.element("paramName").getText();
//				   content+=value;
//				   content+=" ";
//				   
//				   String typeName="paramType";
//				   content+=typeName;
//				   content+=":";
//				   String typeValue=eleChild.element("paramType").getText();
//				   content+=typeValue;
//				   content+=" ";
//				   
//			   }
//			   String name=ele.getName();
//			   content+=name;
//			   content+="} ";
//		   }
		   else{//����String�������� name1:value1 name2:value2
			   String name=ele.getName();
			   content+=name;
			   content+=":";
			   String value=ele.getText();
			   content+=value;
			   content+=" ";
		   }
		   
	   }
	   return content;
   }
     //��ǰ���õ�xml����Ϣ��ת��Ϊxml
	public void writeVioletMarkov(String toFileName){
		int idcount=0;//����xml�����id
		Document doc =DocumentHelper.createDocument();
		Element MarkovGraph=doc.addElement("MarkovGraph");
		MarkovGraph.addAttribute("id", String.valueOf(idcount++));//1
		//*************************nodes*****************************
		Element nodes=MarkovGraph.addElement("nodes");
		nodes.addAttribute("id", String.valueOf(idcount++));//2
		//����startNode
		Element markovStartNode1=nodes.addElement("MarkovStartNode");
		markovStartNode1.addAttribute("id", TanchaoMarkovStartNode.getId());
		Element children=markovStartNode1.addElement("children");
		children.addAttribute("id", TanchaoMarkovStartNode.getChildren_id());
		Element location=markovStartNode1.addElement("location");
		location.addAttribute("class", "Point2D.Double").addAttribute("id", TanchaoMarkovStartNode.getChildren_id())
		.addAttribute("x", location_x).addAttribute("y", location_y);
		Element id=markovStartNode1.addElement("idN");
		id.addAttribute("id", TanchaoMarkovStartNode.getUnderLocation_id());
		//�����ɫ(3����ɫ)
		setColor(markovStartNode1);
		//���name
		Element name=markovStartNode1.addElement("name");
		name.addAttribute("id", String.valueOf(idcount++));
		name.setText(TanchaoMarkovStartNode.getName());
		
		//����ʣ�µ�node
		for(TanchaoMarkovNode node:markovNodeInfo){
			Element MarkovNode=nodes.addElement("MarkovNode");
			MarkovNode.addAttribute("id", node.getId());
			Element children1=MarkovNode.addElement("children");
			children1.addAttribute("id", node.getChildren_id());
			Element location1=MarkovNode.addElement("location");
      		location1.addAttribute("class", "Point2D.Double").addAttribute("id", node.getLocation_id())
      		    .addAttribute("x", location_x).addAttribute("y", location_y);
      		Element id1=MarkovNode.addElement("idN");
      		id1.addAttribute("id", node.getUnderLocation_id());
      		setColor(MarkovNode);
      		Element name1=MarkovNode.addElement("name");
      		name1.addAttribute("id", node.getName_id());
      		name1.setText(node.getName());
//      		name1.setText(node.getName());
		}
		//*********************edges***************************
		Element edges=MarkovGraph.addElement("edges");
		edges.addAttribute("id", String.valueOf(idcount++));
		for(TanchaoMarkovTransitionEdge edge:markovTransitionEdgeInfo){
			
			Element MarkovTransitionEdge=edges.addElement("MarkovTransitionEdge");
			MarkovTransitionEdge.addAttribute("id", edge.getId());
			Element start=MarkovTransitionEdge.addElement("start");
			start.addAttribute("class", "MarkovNode").addAttribute("reference", getId(edge.getStart_reference()));//��ӿ�ʼnode��id
			Element end=MarkovTransitionEdge.addElement("end");
			end.addAttribute("class", "MarkovNode").addAttribute("reference", getId(edge.getEnd_reference()));//��ӽ���node��id
			Element startLocation=MarkovTransitionEdge.addElement("startLocation");
            startLocation.addAttribute("class", "Point2D.Double").addAttribute("id", String.valueOf(idcount++))
            .addAttribute("x", location_x).addAttribute("y", location_y);
            Element endLocation=MarkovTransitionEdge.addElement("endLocation");
            endLocation.addAttribute("class", "Point2D.Double").addAttribute("id", String.valueOf(idcount++))
            .addAttribute("x", location_x).addAttribute("y", location_y);
            Element edgeId=MarkovTransitionEdge.addElement("idE");
            edgeId.addAttribute("id", edge.getUnderLocation_id());
//            Element probability=MarkovTransitionEdge.addElement("probability");
//            probability.setText(edge.getContent());
            Element conditions=MarkovTransitionEdge.addElement("conditions");
            conditions.setText(edge.getConditions());
            Element owned=MarkovTransitionEdge.addElement("owned");
            owned.setText(edge.getOwned());
            Element assignValue=MarkovTransitionEdge.addElement("assignValue");
            assignValue.setText(edge.getAssignValue());
            Element assignType=MarkovTransitionEdge.addElement("assignType");
            assignType.setText(edge.getAssignType());
            Element pro=MarkovTransitionEdge.addElement("pro");
            pro.setText(edge.getProb());
		}
		outputXML(doc,toFileName);
	}
	
	/**
	 * 
	 * @param name ���ݴ����node ��name���id
	 * @return String ����
	 */
	public String getId(String name){
//		List<Object> list=new ArrayList<Object>();
//		list.add(markovStartNode);
//		list.addAll(1, markovNodeInfo);
//		for(Object o:list){
//			if(name.equals(((MarkovNode)o).getName())){
//				return .getId();//����id(��String�����ͷ���)
//			}
//		}
//		return "";//Ŀǰ����" "�������޸�
		if(name.equals(TanchaoMarkovStartNode.getName()))
			return TanchaoMarkovStartNode.getId();
		else{
			for(TanchaoMarkovNode node:markovNodeInfo){
				if(name.equals(node.getName()))
					return node.getId();
			}
		}
		return " ";
	}
	/**
	 * ������ɫ(��װһ��)
	 * @param node��ʾ���ñ�ǩ����ӱ�ǩ(������ɫ���ӱ�ǩ)
	 * @param id ���ڱ�ʾ��ǩ��id(Ŀ�ģ��ñ�ǩid����)
	 * @return �µ�id����������(��֤id���ظ�)
	 */
	public void setColor(Element node){
		//1 backgroundColor
		Element backgroundColor=node.addElement("backgroundColor");
		backgroundColor.addAttribute("id", UUID.randomUUID().toString());
		Element red=backgroundColor.addElement("red");
		red.addText("255");
		Element green=backgroundColor.addElement("green");
		green.addText("255");
		Element blue=backgroundColor.addElement("blue");
		blue.addText("255");
		Element alpha=backgroundColor.addElement("alpha");
		alpha.addText("255");
		//2 borderColor
		Element borderColor=node.addElement("borderColor");
		borderColor.addAttribute("id", UUID.randomUUID().toString());
		Element red1 =borderColor.addElement("red");
		red1.addText("191");
		Element green1 =borderColor.addElement("green");
		green1.addText("191");
		Element blue1=borderColor.addElement("blue");
		blue1.addText("191");
		Element alpha1 =borderColor.addElement("alpha");
		alpha1.addText("255");
		
		//3 textColor
		Element textColor=node.addElement("textColor");
		textColor.addAttribute("id", UUID.randomUUID().toString());
		Element red2 =textColor.addElement("red");
		red2.addText("51");
		Element green2 =textColor.addElement("green");
		green2.addText("51");
		Element blue2=textColor.addElement("blue");
		blue2.addText("51");
		Element alpha2 =textColor.addElement("alpha");
		alpha2.addText("255");
		
	}
	
	/**
	 * ���xml�ļ�
	 * @param doc
	 * @param fileName ������ļ���
	 */
	public void outputXML(Document doc,String fileName){
		try {
			FileWriter fw=new FileWriter(fileName);
			OutputFormat format=new OutputFormat("  ",true); 
			XMLWriter xmlWriter=new XMLWriter(fw,format);
			xmlWriter.write(doc);
			xmlWriter.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
	
	public static void main(String[] args) {
		TianWriteToVioletMarkov t=new TianWriteToVioletMarkov();
		try {
			t.find("D:\\ModelDriverProjectFile\\NoTimeMarkov\\EADemo2Seq_MarkovChainModel1.xml");
//			t.find("C:\\Users\\Admin\\Desktop\\markov\\Seq_MarkovChainModel2.xml");
			t.writeVioletMarkov("D:\\ModelDriverProjectFile\\NoTimeMarkov\\EADemo2Seq_MarkovChainModel1.markov.violet.xml");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
