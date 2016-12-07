package com.horstmann.violet.application.menu.xiaole.SequenceTransfrom;
import java.awt.color.CMMException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.horstmann.violet.application.menu.xiaole.SequenceTransfrom.PackageInfo;
public class ReadVioletSequence {
   SAXReader reader = new SAXReader();
   private final String Diagram_id="EAID_D7EF9D40_325E_4007_B081_7B1019A7070F";
   PackageInfo packageInfo =new PackageInfo();
   List<LifeLineInfo> Lifelines=new ArrayList<LifeLineInfo>();//������
   List<MessageInfo> Messages=new ArrayList<MessageInfo>();//��Ϣ
   List<BaseFragmentInfo> Fragments =new ArrayList<BaseFragmentInfo>();//�ײ�Ƭ��
   List<CombinedFragmentInfo> CombinedFragments=new ArrayList<CombinedFragmentInfo>();//���Ƭ��
   List<FragmentPartInfo> FragmentParts=new ArrayList<FragmentPartInfo>();
   //��ȡ������Ƕ�׵����Ƭ��ID�������ҳ���߼�������Ƭ��
   List<String> NestingCombinedFragmentsID=new ArrayList<String>();
   List<Double> MessageLocationY=new ArrayList<Double>();
   private int c=0;
   private int r=0;
   private int C=0;
   List<String> Activationid =new ArrayList<String>();
   List<String> calledgestartId =new ArrayList<String>();
   List<String> calledgeendId =new ArrayList<String>();
   List<String> returnedgestartId =new ArrayList<String>();
   List<String> returnedgeendId =new ArrayList<String>();
   List<String> Lifelineid = new ArrayList<String>();//lifeline id
   HashMap<String,String> FragmentLifelineid=new HashMap<String, String>();
   HashMap<String,String> CalledgeStartLifelineid=new HashMap<String, String>();
   HashMap<String,String> CalledgeEndLifelineid=new HashMap<String, String>();
   HashMap<String,String> ReturnedgeEndLifelineid=new HashMap<String, String>();
   //����4��Hashmap����ӳ��
   HashMap<String,String> ReturnedgeStartLifelineid=new HashMap<String, String>(); 
   List<String> Text=new ArrayList<String>();//Text�������ÿ��lifeline��name
   //List<List<String>> ActivationidTemp =new ArrayList<List<String>>();
   HashMap<String,String > LifelineConcludeActivation=new HashMap<String,String>();
   //����hashMap.�ж���Ϣ������2�������߷��ͺͽ���
   public void find() throws DocumentException{
	   Document dom=reader.read("ExampleVioletSD.seq.violet.xml");//��ȡViolet XML�ļ�
	   Element root = dom.getRootElement();
	   Element nodes=root.element("nodes");
	   Element Eages=root.element("edges");
	   List<Element> lifelines = nodes.elements("LifelineNode");
	   List<Element> Calledges=Eages.elements("CallEdge");//������Ϣ
  	   List<Element> Returnedges=Eages.elements("ReturnEdge");//������Ϣ
  	   List<Element> combinedfragments=nodes.elements("CombinedFragment");
   	   int Calledgesize=Calledges.size();
  	   int Returnedgesize=Returnedges.size();
 	   for(Element combinedfragment : combinedfragments)
 	   {
 		   CombinedFragments.add(new CombinedFragmentInfo());//����CombinedFragment����
 		   
 	   }
 	   for(Element lifeline : lifelines)
 	   {
 		   Lifelines.add(new LifeLineInfo());//����lifelineInfo����
 		  
 	   }	
 	   for(int temp1=0;temp1<Calledgesize+Returnedgesize;temp1++)
 	   {
 		   Messages.add(new MessageInfo());//����MsgFragment����
 		   Fragments.add(new BaseFragmentInfo());
		   Fragments.add(new BaseFragmentInfo());
 	   }
 	   
 	 for(Element lifelineElement : lifelines)//������������Ϣ
	   {
 		 int lifelineIndex=lifelines.indexOf(lifelineElement);//lifeline�±�
 		 Lifelines.get(lifelineIndex).setlifeLineId(transVioletIdToEAID(lifelineElement.attributeValue("id")));//����ID
 		 Lifelines.get(lifelineIndex).setlifeLineName(lifelineElement.element("name").element("text").getText());//����Name
 	
 	     String geometryString="Left="+lifelineElement.element("location").attributeValue("x")+";"
 	    		 +"Top="+"50"+";"//�������EA���� Ĭ��TopΪ50
 	    		 +"Right="+String.valueOf(Double.parseDouble(lifelineElement.element("location").attributeValue("x"))
 	    				 +Double.parseDouble(lifelineElement.element("width").getText()))+";"
 	    		 +"Bottom="+"600";//������Ĭ������Ϊ600;
 	     Lifelines.get(lifelineIndex).setGeometry(geometryString);
 	     Lifelines.get(lifelineIndex).setType("uml:Lifeline");
 	         
 	  
 	   
 	 
 	   
 	  

 	   
 	  
	   Element lifelineschildren=lifelineElement.element("children"); 
	   List<Element> activations=lifelineschildren.elements("ActivationBarNode");//�Է�����Ϣ
	   int SIZE =activations.size();
	   Lifelineid.add(Lifelines.get(lifelineIndex).getlifeLineId());//�����е�lifelineID����Lifelineid�ṹ��
	   Text.add(lifelineElement.element("name").element("text").getText());
	   //��ȡÿ���������е��ӽڵ㣬��activationBarNode
       for(int m=0;m<SIZE;m++)//ÿ���������ϵ�activations
       {//����ÿ���ӽڵ��е�ActivationBarNode,�Ի�ȡÿ�������������е�ActivationBarNode��id
//       	 Element element=activations.get(m).element("children");     	
//       	if(	element)
//       		 flag = 1;  	 
//       	 if(flag==1)
//            {
//         id.add(element.element("ActivationBarNode").attribute("id").getValue());
//         //������Է�����Ϣ��ActivationBarNode.��ȡ��ID
//            }
//      	 else
       	 Activationid.add(transVioletIdToEAID(activations.get(m).attribute("id").getValue()));
       	 LifelineConcludeActivation.put(Activationid.get(m),Lifelineid.get(lifelineIndex));//��activationbarId��lifelineID���Ӧ       	 	         	
       }         
       Activationid= new ArrayList<String>();
	   }
 	 
 	 
 		  
 		  
 	  
 	  for(Element calledgeElement :Calledges)//��������Ϣ�ıߵ���Ϣ
 	   {  
 		 
 		
 		  
 		 int calledgeElementIndex=Calledges.indexOf(calledgeElement);
 		 Messages.get(calledgeElementIndex).setID(calledgeElement.elementText("ID"));//SpecialID
 		 Messages.get(calledgeElementIndex).setIsNavigable(false);//��������Ϣ�ı�����������ʶ	
 		 Messages.get(calledgeElementIndex).setConnectorId(transVioletIdToEAID(calledgeElement.attributeValue("id")));//����ID
 		 Messages.get(calledgeElementIndex).setName(calledgeElement.elementText("middleLabel"));//����Name
 		 Messages.get(calledgeElementIndex).setMessageSort("synchCall");//
 		 Messages.get(calledgeElementIndex).setDiagram_id(Diagram_id);
 		 Messages.get(calledgeElementIndex).setEa_type("Sequence");
 		 Messages.get(calledgeElementIndex).setType("Sequence"); 
 		 String Sequence_points="PtStartX="+calledgeElement.element("startLocation").attributeValue("x")+";"
 				 +"PtStartY=-"+calledgeElement.element("startLocation").attributeValue("y")+";"
 				 +"PtEndX="+calledgeElement.element("endLocation").attributeValue("x")+";"
 				 +"PtEndY=-"+calledgeElement.element("endLocation").attributeValue("y")+";";
 		 Messages.get(calledgeElementIndex).setLocationY(calledgeElement.element("endLocation").attributeValue("y"));
 		 MessageLocationY.add(Double.parseDouble(calledgeElement.element("endLocation").attributeValue("y")));
 		 Messages.get(calledgeElementIndex).setSequence_points(Sequence_points);   
 		 Messages.get(calledgeElementIndex).setPrivatedata5("");//���ﻹû�����
	   	 calledgestartId.add(transVioletIdToEAID(calledgeElement.element("start").attribute("reference").getValue()));
	   	 //��ȡActionBarNodeId
 	   	 calledgeendId.add(transVioletIdToEAID(calledgeElement.element("end").attribute("reference").getValue()));
 	   	 //��ȡ���з�����Ϣ�ıߵ�start ID�������ActivationBarNode) 
 	   	 String StartID= LifelineConcludeActivation.get(calledgestartId.get(calledgeElementIndex));
 	   	//��Ӧ��lifeline��ID
 	   	 String EndID= LifelineConcludeActivation.get(calledgeendId.get(calledgeElementIndex));
 	   	
 	   	 Fragments.get(C).setId("EAID_"+UUID.randomUUID().toString().replace("-", "_")); 
 	   	 
 	   	 Fragments.get(C).setCovered(StartID);
 	   	 Fragments.get(C+1).setId("EAID_"+UUID.randomUUID().toString().replace("-", "_"));
 	   	 Fragments.get(C+1).setCovered(EndID);
 	   	 CalledgeStartLifelineid.put(transVioletIdToEAID(Calledges.get(c).attribute("id").getValue()),StartID);
 	   	 CalledgeEndLifelineid.put(transVioletIdToEAID(Calledges.get(c).attribute("id").getValue()), EndID);
 	   	 //MsgFragments.get(c).PtStartX=Calledges.get(c).element("startLocation").attribute("x").getValue();
         Messages.get(calledgeElementIndex).setSendEvent(Fragments.get(C).getId()); 
         Messages.get(calledgeElementIndex).setReceiveEvent(Fragments.get(C+1).getId());
         Messages.get(calledgeElementIndex).setSourceId(StartID);
         Messages.get(calledgeElementIndex).setTragetId(EndID);
 		 c++;
 		// System.out.println(Fragments.get(C).getId());
 		
   		C+=2;
 	   }
 	 
 	   while(r<Returnedgesize)
 	   {
 	   int temp3=c+r; 
      Messages.get(temp3).isNavigable=true;
      Messages.get(temp3).setID(Returnedges.get(r).elementText("ID"));
 	  Messages.get(temp3).connectorId=transVioletIdToEAID(Returnedges.get(r).attribute("id").getValue());
 	  Messages.get(temp3).name=Returnedges.get(r).element("middleLabel").getText();
 	  Messages.get(temp3).messageSort="reply";
 	  Messages.get(temp3).ea_type="Sequence";
 	  Messages.get(temp3).diagram_id="EAID_D7EF9D40_325E_4007_B081_7B1019A7070F";
 	  Messages.get(temp3).type="Sequence";
 	  String Sequence_points="PtStartX="+Returnedges.get(r).element("startLocation").attributeValue("x")+";"
				 +"PtStartY=-"+Returnedges.get(r).element("startLocation").attributeValue("y")+";"
				 +"PtEndX="+Returnedges.get(r).element("endLocation").attributeValue("x")+";"
				 +"PtEndY=-"+Returnedges.get(r).element("endLocation").attributeValue("y")+";";
 	   Messages.get(temp3).setLocationY(Returnedges.get(r).element("endLocation").attributeValue("y"));
	   MessageLocationY.add(Double.parseDouble(Returnedges.get(r).element("endLocation").attributeValue("y")));
 	   Messages.get(temp3).sequence_points=Sequence_points;
 	   Messages.get(temp3).privatedata5="";//��ʱ������
       returnedgestartId.add(transVioletIdToEAID(Returnedges.get(r).element("start").attribute("reference").getValue()));
       returnedgeendId.add(transVioletIdToEAID(Returnedges.get(r).element("end").attribute("reference").getValue()));
  	   	 //��ȡ���з�����Ϣ�ıߵ�start ID�������ActivationBarNode) 
  	   	 String id1= LifelineConcludeActivation.get(returnedgestartId.get(r));
  	   	 String id2= LifelineConcludeActivation.get(returnedgeendId.get(r));
  		 Fragments.get(C).setId("EAID_"+UUID.randomUUID().toString().replace("-", "_"));
 	   	 Fragments.get(C).setCovered(id1);
 	   	 Fragments.get(C+1).setId("EAID_"+UUID.randomUUID().toString().replace("-", "_"));
 	   	 Fragments.get(C+1).setCovered(id2);
  	   	 ReturnedgeStartLifelineid.put(transVioletIdToEAID(Returnedges.get(r).attribute("id").getValue()),id1);
  	   	 ReturnedgeEndLifelineid.put(transVioletIdToEAID(Returnedges.get(r).attribute("id").getValue()), id2);
  	     Messages.get(temp3).setSendEvent(Fragments.get(C).getId()); 
  	     Messages.get(temp3).setReceiveEvent(Fragments.get(C+1).getId());
  	     Messages.get(temp3).setSourceId(id1);
  	     Messages.get(temp3).setTragetId(id2);
  	    
  		 r++; 
  		 //System.out.println(Fragments.get(C).getId());
  		 C+=2;
 	   } 
 for(Element combinedfragmentElement : combinedfragments)
 		  
 	  {
 		  FragmentParts=new ArrayList<FragmentPartInfo>();
 		  int index=combinedfragments.indexOf(combinedfragmentElement);
 		  CombinedFragments.get(index).setId(transVioletIdToEAID(combinedfragmentElement.attributeValue("id")));		  
 		  CombinedFragments.get(index).setName("Xiao");
 		  CombinedFragments.get(index).setType("alt");//������ȫ������Ϊalt
 		  CombinedFragments.get(index).setID(combinedfragmentElement.elementText("ID"));
  	      String geometryString="Left="+combinedfragmentElement.element("location").attributeValue("x")+";"
  	    		 +"Top="+String.valueOf(Double.parseDouble(combinedfragmentElement.element("location").attributeValue("y"))+50)+";"//
  	    		 +"Right="+String.valueOf(Double.parseDouble(combinedfragmentElement.element("location").attributeValue("x"))
  	    				 +Double.parseDouble(combinedfragmentElement.element("width").getText()))+";"
  	    		 +"Bottom="+String.valueOf(Double.parseDouble(combinedfragmentElement.element("location").attributeValue("y"))
  	    				 +Double.parseDouble(combinedfragmentElement.element("height").getText())+50);//
 		  CombinedFragments.get(index).setGeometry(geometryString);
 		  List<Element> fragmentParts=combinedfragmentElement.element("fragmentParts").
 				  elements("com.horstmann.violet.product.diagram.abstracts.property.FragmentPart");
 		 
 		  for(Element fragmentpart : fragmentParts)
 		  {
 			  FragmentParts.add(new FragmentPartInfo());
 		  }
 
 		  for(Element fragmentpart : fragmentParts)
 		  {
 			 
 		  int Index=fragmentParts.indexOf(fragmentpart); 
 		  FragmentParts.get(Index).setConditionText(fragmentpart.elementText("conditionText"));
 		  Element coveredMessages=fragmentpart.element("coveredMessagesID");
 		  Element nestingChilds=fragmentpart.element("nestingChildNodesID");
 		  List<Element> EdgesID=coveredMessages.elements("string");		 
 		  List<Element> nestingChildNodesID=nestingChilds.elements("string");		 
 		  Element size=fragmentpart.element("size");
 		  FragmentParts.get(Index).setSize(size.getText());
 	
 		  for(Element EdgeID : EdgesID )
 		  {
 			  for(MessageInfo message : Messages)
 			  { 
 			  if(EdgeID.getText().equals(message.getID()))
 				  
 			   {
 			
 		     FragmentParts.get(Index).AddConcluedmessages(message);
 		     
 		       }
 			  }
 		  }
 		  for(Element nestingChildNodeID : nestingChildNodesID)
 		  {
 			 
 			  for(CombinedFragmentInfo combinedfragment : CombinedFragments)
 			  {
 				  if(nestingChildNodeID.getText().equals(combinedfragment.getID()))
 				  {
 					  FragmentParts.get(Index).AddNestingchilds(combinedfragment);
 				     NestingCombinedFragmentsID.add(combinedfragment.getID());
 				  }
 			  }
 		  }
 		}  
 		CombinedFragments.get(index).setFragmentParts(FragmentParts);  		  	     
 	  }
 		  
  } 
   public void writeTiming(String filename){
		  
		  Document doc = DocumentHelper.createDocument();
		    Element XMI = doc.addElement("xmi:XMI");
		    XMI.addAttribute("xmi:version", "2.1");
		    XMI.addNamespace("uml", "http://schema.omg.org/spec/UML/2.1");
		    XMI.addNamespace("xmi", "http://schema.omg.org/spec/XMI/2.1");
		    Element Documentation=XMI.addElement("xmi:Documentation");
		    Documentation.addAttribute("exporter", "Enterprise Architect");
		    Documentation.addAttribute("exporterVersion", "6.5");
		   //ͷ
		    	    
		    Element Model=XMI.addElement("uml:Model");
		    Model.addAttribute("xmi:type","uml:Model");
		    Model.addAttribute("name","EA_Model");
		   //Model ��ǩ 
		    
		    Element packagedElement=Model.addElement("packagedElement");
		    packagedElement.addAttribute("xmi:type","uml:Package");
		    packagedElement.addAttribute("xmi:id",packageInfo.id);//������Ҫ�����ֶ���������ԭʼ���ļ��л�ȡ
		   // packagedElement.addAttribute("name", "");//ͬ��
		    //packagedElement��ǩ
	   
		     
		    Element ownedBehavior=packagedElement.addElement("ownedBehavior");
		    ownedBehavior.addAttribute("xmi:type","uml:Interaction");
		    ownedBehavior.addAttribute("xmi:id", "EAID_IN000000_C5A_D3F7_4321_B37B_01F132FAA48");//���Ǹ���
		    ownedBehavior.addAttribute("name", "EA_Interaction1");
		    //ownedBehavior
		    
		    
		    //����<lifeline>
		    for(LifeLineInfo temp:Lifelines){
		    	Element lifeline=ownedBehavior.addElement("lifeline");
		    	lifeline.addAttribute("xmi:type","uml:Lifeline");
		    	lifeline.addAttribute("xmi:id", temp.getlifeLineId());
		    	lifeline.addAttribute("name", temp.getlifeLineName()); 		       
		    }
		    //����<fragment>	
		    //�����2�������һ����û�����Ƭ�ε�˳��ͼ
		    //��һ���������Ƭ�ε�˳��ͼ
		    //����Ĭ��Ϊ�����Ƭ��
		    for(CombinedFragmentInfo combinedfragment : CombinedFragments)
		    {
		    	//��һ�����жϸ����Ƭ���ǲ�����߼����Ƭ�Σ��������߼���2�����
		    	//1.Ƕ���������Ƭ��
		    	//2.���������Ƭ��
		    //���ȶ�Ƕ���������Ƭ�ε���߼����Ƭ�ν��д���
		    //������е�NestingCombinedFragmentsID���ж���������ǰ�����Ƭ��ID
		    //��ǰ�����Ƭ�μ�Ϊ��߼����Ƭ��
		
		    if(!NestingCombinedFragmentsID.contains(combinedfragment.getID())){	 
		 
		       Element fragment=ownedBehavior.addElement("fragment");
		       fragment.addAttribute("xmi:type", "uml:CombinedFragment");
		       fragment.addAttribute("xmi:id", combinedfragment.getId());
		        
		       fragment.addAttribute("interactionOperator", combinedfragment.getType());
		       //���������Ƭ�ε�Part
		       for(FragmentPartInfo fragmentpart : combinedfragment.getFragmentParts())
		       {
		       //�������һ��operand��ǩ
		       Element operand=fragment.addElement("operand");
		       operand.addAttribute("xmi:type","uml:InteractionOperand");
		       operand.addAttribute("xmi:id", generateEAID());//���Ǹ�����Ψһ����		       
		       Element specification=operand.addElement("specification");
		       specification.addAttribute("xmi:type", "uml:OpaqueExpression");
		       specification.addAttribute("xmi:id",generateEAID());//���Ǹ�����Ψһ����
		       specification.addAttribute("body", fragmentpart.getConditionText());
		       //��������part���п���Ƕ�׵����Ƭ��
		       AddNestingCombinedFragements(fragmentpart,operand);		    	   
		       }
		    }	
		       //�����2�������һ�������Ƭ��Ƕ����һ�����Ƭ��
		       //��һ���ǵ�һ���Ƭ��		    		       
		    }		       		       		             		       		        		   		    			
		    //����<message>
		    for(MessageInfo message:Messages){
		    	Element messageElement=ownedBehavior.addElement("message");
		    	messageElement.addAttribute("xmi:type","uml:Message");
		    	messageElement.addAttribute("xmi:id", message.getConnectorId());
		    	messageElement.addAttribute("name", message.getName());
		    	messageElement.addAttribute("messageSort", message.getMessageSort());
		    	messageElement.addAttribute("sendEvent", message.getSendEvent());
		    	messageElement.addAttribute("receiveEvent", message.getReceiveEvent());
		    }
		    
		    //����<Extension>
		    Element Extension=XMI.addElement("xmi:Extension");
		    //����<elements>
		    Element elements=Extension.addElement("elements");
		    //����elements�µ�<element>����������Ϣ
		    Element packageElement=elements.addElement("element");
		 
		    packageElement.addAttribute("xmi:idref", packageInfo.id);
		    packageElement.addAttribute("xmi:type", "uml:Package");
		    for(CombinedFragmentInfo combinedfragment : CombinedFragments)
		    {
		    	String SpeicalString="";
		    	String beforeString="$XREFPROP=$XID=";//��ͷ��XID
		    	String XIDString = "{"+UUID.randomUUID().toString()+"}$XID"+";";
		        String middleString="$NAM=Partitions$NAM;$TYP=element property$TYP;$VIS=Public$VIS;$PAR=0$PAR;$DES=";
		        String ParString="";
		        List<FragmentPartInfo> ReverseParts=new ArrayList<FragmentPartInfo>();
		        for(FragmentPartInfo fragmentPartInfo : combinedfragment.getFragmentParts())
		        {
		        	
		        	int index=combinedfragment.getFragmentParts().indexOf(fragmentPartInfo);
		        	ReverseParts.add(index, combinedfragment.getFragmentParts().get(combinedfragment.getFragmentParts().size()-index-1));
		        }
		        for(FragmentPartInfo fragmentPart : ReverseParts)
		        {
//		        	int fragmentpartIndex=combinedfragment.getFragments().indexOf(fragmentPart);
		        	ParString+="@PAR;"+"Name="+fragmentPart.getConditionText()+";"
		        	+"Size="+fragmentPart.getSize().substring(0,fragmentPart.getSize().length()-2)+";"+"@ENDPAR;";		        			        			        		        	
		        }
		        String CLTString =combinedfragment.getId().substring(5, combinedfragment.getId().length()).replace("_", "-");
		        String afterString="$DES;$CLT={"+CLTString+"}$CLT;$SUP=&lt;none&gt;$SUP;$ENDXREF;";
		    	SpeicalString=beforeString+XIDString+middleString+ParString+afterString;
		    	Element element = elements.addElement("element");
		    	element.addAttribute("xmi:idref",combinedfragment.getId() );//���Ǹ���������ʶ��combinedfragment
		    	element.addAttribute("xmi:type", "uml:InteractionFragment");
		    	element.addAttribute("name", combinedfragment.getName());//���Ǹ���
		    	Element xrefs=element.addElement("xrefs");
		    	xrefs.addAttribute("value", SpeicalString);
		    	
		    }
		 
		    //����<connectors>
		    Element connectors=Extension.addElement("connectors");
		    Collections.sort(MessageLocationY);
		    for(MessageInfo message:Messages){
		    	Element connector=connectors.addElement("connector");
		    	connector.addAttribute("xmi:idref",message.getConnectorId());
		    	//����<source>
		    	Element source=connector.addElement("source");
		    	source.addAttribute("xmi:idref", message.getSourceId());
		    	Element modifiers=source.addElement("modifiers");
		    	modifiers.addAttribute("isNavigable","false");//���Ǹ���
		    	//����<target>
		        
		    	Element target=connector.addElement("target");
		    	target.addAttribute("xmi:idref", message.getTragetId());
		    	Element modifiers1=target.addElement("modifiers");
		    	modifiers1.addAttribute("isNavigable", "true");//���Ǹ���
		    	Element apperance=connector.addElement("appearance");
		    	
		    	apperance.addAttribute("seqno",String.valueOf(MessageLocationY.indexOf(Double.parseDouble(message.getLocationY()))+1));//����Y����Ĵ�С��������
		    	//����extendedProperties
		    	Element extendedProperties=connector.addElement("extendedProperties");
		    	if(message.isNavigable==true)
		    	{extendedProperties.addAttribute("diagram", message.getDiagram_id()).
		    	//addAttribute("privatedata5",message.getPrivatedata5()).
		    	addAttribute("privatedata4", "1").
		    	addAttribute("sequence_points",message.getSequence_points());
		    	
		    	}//���ǽo��
		    	else 
		    		{
	    		extendedProperties.addAttribute("diagram", message.getDiagram_id()).
		    	//addAttribute("privatedata5",message.getPrivatedata5()).
		    	addAttribute("privatedata4", "0").//���ǽo��
		    	addAttribute("sequence_points",message.getSequence_points());
	    		} 
		    }
		    
		    
		  //����<diagrams>
		    Element diagrams=Extension.addElement("diagrams");  
		    Element diagram=diagrams.addElement("diagram");
		    diagram.addAttribute("xmi:id",Diagram_id);
		    Element model=diagram.addElement("model");
		    model.addAttribute("owner", packageInfo.id);
		    Element properties=diagram.addElement("properties");
		    properties.addAttribute("type", "Sequence");
		    diagram.addElement("style2");
		    Element dia_elements=diagram.addElement("elements");
		  
		    for(MessageInfo message :Messages)
		    {
		    	Element messageElement=dia_elements.addElement("element");
		    	messageElement.addAttribute("subject", message.getConnectorId());
		    }
		    
		    for(LifeLineInfo temp:Lifelines){
		    	Element dia_element=dia_elements.addElement("element");
		    	dia_element.addAttribute("geometry", temp.getGeometry());
		    	dia_element.addAttribute("subject", temp.getlifeLineId());		    			    	
		    }
		    for(CombinedFragmentInfo combinedfragment : CombinedFragments)
		    {
		    	Element combinedfragmentelement=dia_elements.addElement("element");
		    	combinedfragmentelement.addAttribute("geometry", combinedfragment.getGeometry());
		    	combinedfragmentelement.addAttribute("subject", combinedfragment.getId());
		    	
		    }
		  outputXml(doc, filename);
	}
    public String transVioletIdToEAID(String id)
    {
    	if(id.length()==1)
    	return "EAID_"+"9CE39211_2A09_4b09_B755_27EE1A169D7"+id;
    	else
    	return "EAID_"+"9CE39211_2A09_4b09_B755_27EE1A169D"+id;
    	
    }
    public String generateEAID()
    {
    	String EAID= "EAID_"+UUID.randomUUID().toString();
    	return EAID.replace("-", "_");
    }
    public void AddNestingCombinedFragements(FragmentPartInfo fragmentpart,Element operand)
    {
    if(fragmentpart.getNestingchilds().size()>0)//�����жϻ���û��Ƕ�׵����Ƭ��
    {         		      		    			           
       for(CombinedFragmentInfo nestingcombinedfragment: fragmentpart.getNestingchilds())
     	  //�����,��������Ƕ�׵����Ƭ��
    	  //���ǵ�һ���������Ϊ��߼����Ƭ���еĺ���Ƕ�����Ƭ�ε����
      { 
     for(FragmentPartInfo fragmentPartInfo :nestingcombinedfragment.getFragmentParts())
    {  
     Element nestingfragment=operand.addElement("fragment");
 	  nestingfragment.addAttribute("xmi:type", "uml:CombinedFragment");
 	  nestingfragment.addAttribute("xmi:id", nestingcombinedfragment.getId());//���Ǹ�����Ψһ����
 	  nestingfragment.addAttribute("interactionOperator",nestingcombinedfragment.getType());	    	 
 	  Element nestingoperand=nestingfragment.addElement("operand");
 	  nestingoperand.addAttribute("xmi:id", generateEAID());//���Ǹ�����Ψһ����
 	  Element nestingspecification=nestingoperand.addElement("specification");
 	  nestingspecification.addAttribute("xmi:type", "uml:OpaqueExpression");
 	  nestingspecification.addAttribute("xmi:id", generateEAID());//���Ǹ�����Ψһ����
 	  nestingspecification.addAttribute("body",fragmentPartInfo.getConditionText());
 	
 	  AddNestingCombinedFragements(fragmentPartInfo, nestingoperand); 	  
 	  //�������
      }	
      }
   } 		       
 if(fragmentpart.getConcluedmessages().size()>0)//ֱ�������ײ�fragment	
    {
//�Ƴ�Ƕ�׵����Ƭ�����Ѿ����е���Ϣ
	for(CombinedFragmentInfo combinedFragmentInfo : fragmentpart.getNestingchilds())
	{
		for(FragmentPartInfo fragmentPartInfo:combinedFragmentInfo.getFragmentParts())
		{
			for(MessageInfo messageInfo : fragmentPartInfo.getConcluedmessages())
			{
				fragmentpart.getConcluedmessages().remove(messageInfo);
			}
		}
	}
	
//	for(BaseFragmentInfo baseFragmentInfo :Fragments)
//	{
//		System.out.println(baseFragmentInfo.getId());
//	}
	 
 	  for(MessageInfo concluedmessage : fragmentpart.getConcluedmessages())
		    {			
 		
 			
 		       
		    	Element fragment1=operand.addElement("fragment");		    	
		        fragment1.addAttribute("xmi:type","uml:OccurrenceSpecification");
		        int index=Messages.indexOf(concluedmessage);
		    	fragment1.addAttribute("xmi:id", Fragments.get(2*index).getId());		    
		  //  System.out.println(concluedmessage.getSourceId());		    	
		    	fragment1.addAttribute("covered",concluedmessage.getSourceId());	
		    	Element fragment2=operand.addElement("fragment");			   
		        fragment2.addAttribute("xmi:type","uml:OccurrenceSpecification");
		    	fragment2.addAttribute("xmi:id", Fragments.get(2*index+1).getId());
		  //  	 System.out.println(concluedmessage.getTragetId());	//		    
		    	fragment2.addAttribute("covered",concluedmessage.getTragetId());	
		    	index+=2;
		    	//System.out.println(index);
		    }
 	
 	} 
 }	    
 	   
    
	public void outputXml(Document doc, String filename) {
	    try {
	      //�����������Ŀ�ĵ�
	      FileWriter fw = new FileWriter(filename);
	       
	      //���������ʽ���ַ���
	      OutputFormat format 
	        = OutputFormat.createPrettyPrint();
	      format.setEncoding("windows-1252");
	       
	      //�����������xml�ļ���XMLWriter����
	      XMLWriter xmlWriter 
	        = new XMLWriter(fw, format);
	      xmlWriter.write(doc);//*****
	      xmlWriter.close(); 
	    } catch (IOException e) {
	      e.printStackTrace();
	    }   
	  }

}

