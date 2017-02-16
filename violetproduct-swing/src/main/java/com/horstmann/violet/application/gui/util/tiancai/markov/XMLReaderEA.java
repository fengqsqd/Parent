package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class XMLReaderEA {

	private String xmlFile;
	private Element root;
	
	private ArrayList<UseCase> useCases=new ArrayList<UseCase>();
	//private ArrayList<Behavior> behaviors;
	
	private ArrayList<Diagram> diagrams=new ArrayList<Diagram>();	
	private ArrayList<LifeLine> lifeLines=new ArrayList<LifeLine>();
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); //˳��������Ƭ�Σ�
	
	public XMLReaderEA(String xmlFile) {
		
		// TODO Auto-generated constructor stub
		this.xmlFile=xmlFile;
		
		//ͬʱ��ȡ���ڵ�
		try
		{
			SAXReader reader=new SAXReader();
			
			Document dom=reader.read(new File(xmlFile)); 
			//�˾������reader.read(xmlname),��Ϊ��ʹ����·��ͨ��������ᱨ��
			//InputStream ifile = new FileInputStream(xmlFile); 
			//InputStreamReader ir = new InputStreamReader(ifile, "UTF-8"); 
			//Document dom = reader.read(ir);// ��ȡXML�ļ�
			root=dom.getRootElement();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	/////////////////����retrieveuseCases()��retrieveDiagrams()��������ͼ��ִ�еĺ���
	/*
	 * ��ȡ������
	 * ��ȡ��ÿ��������id,name,��Ϊ��������Diagrams��������preConditionû�л�ȡ��
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<UseCase> retrieveuseCases() throws InvalidTagException
	{
		//System.out.println(root.attributeValue("version"));
		ArrayList<Element> elementList=new ArrayList<Element>();
		elementList.addAll(root.element("Model").elements("packagedElement"));
		
		ArrayList<Element> extensionElementList=new ArrayList<Element>();//���ڻ�ȡ����ǰ������
		extensionElementList.addAll(root.element("Extension").element("elements").elements("element"));
		
		for(Iterator<Element> it=elementList.iterator();it.hasNext();)
		{
			Element e=it.next();
			if(!e.attribute("name").getValue().equals("Primary Use Cases"))
				continue;
			
			ArrayList<Element> useCasesList=new ArrayList<Element>();//����������ڵİ�
			useCasesList.addAll(e.elements("packagedElement"));
			for(Iterator<Element> useCaseIterator=useCasesList.iterator();useCaseIterator.hasNext();)
			{
				Element ue=useCaseIterator.next();
				if(ue.attribute("type").getText().equals("uml:UseCase"))
				{
					UseCase useCase=new UseCase();
					useCase.setUseCaseID(ue.attribute("id").getText());
					useCase.setUseCaseName(ue.attribute("name").getText());
					
					
					if(ue.elements("nestedClassifier").size()>0)
					{
						ArrayList<Behavior> behaviors=new ArrayList<Behavior>();//����ͼ��Ӧ��һϵ����Ϊ
						
						ArrayList<Element> behaviorList=new ArrayList<Element>();						
						behaviorList.addAll(ue.elements("nestedClassifier"));
						for(Iterator<Element> behaviorIterator=behaviorList.iterator();behaviorIterator.hasNext();)
						{			
							Element be=behaviorIterator.next();
							Behavior behavior=new Behavior();
							behavior.setBehaviorID(be.element("ownedBehavior").attribute("id").getText());
							behavior.setBehaviorName(be.element("ownedBehavior").attribute("name").getText());
							behaviors.add(behavior);
						}
						
						useCase.setBehaviors(behaviors);
					}
					
					for(Element extensionElement:extensionElementList)
					{
						
						if(extensionElement.attributeValue("type").equals("uml:UseCase")
								&&extensionElement.attributeValue("idref").equals(useCase.getUseCaseID()))
						{
							ArrayList<Element> constraintList=new ArrayList<Element>();
							constraintList.addAll(extensionElement.element("constraints").elements("constraint"));
							for(Element ec:constraintList)
							{
								if(ec.attributeValue("name").equals("preCondition")&&ec.attributeValue("type").equals("Pre-condition"))
								{
									useCase.setPreCondition(ec.attributeValue("description"));
									
								}
								if(ec.attributeValue("name").equals("probability"))
								{
									String pro_String=ec.attributeValue("description");
									//double tempPro=Double.parseDouble(pro_String);
									useCase.setUseCasePro(Double.parseDouble(pro_String));
								}
							}
						}
					}
					
					useCases.add(useCase);
				}
			}
		}
		return this.useCases;
	}

	/*
	 * ��ȡUseCase������ÿ����������Ϊ������Ӧ��˳��ͼDiagram��
	 * ����ͨ��BUseCase.nestedClassifier.ownedBehavior-----Diagram.modle.parent;
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Diagram> retrieveuseDiagrams() throws InvalidTagException
	{
		//��ȡ���е�Interaction
		ArrayList<Element> interactionList=new ArrayList<Element>();
		interactionList.addAll(root.element("Extension").element("elements").elements("element"));
		
		//��ȡDiagram���а�
		ArrayList<Element> diagramList=new ArrayList<Element>();
		diagramList.addAll(root.element("Extension").element("diagrams").elements("diagram"));
		for(Iterator<Element> it=diagramList.iterator();it.hasNext();)
		{
			Element diag=it.next();
			//�ж����Diagram�����˳��ͼ���͵ľͽ���˳��ͼ��Ӧ��Diagram
			if("Sequence".equals(diag.element("properties").attribute("type").getText()))
			{
				
				Diagram diagram=new Diagram();
				diagram.setDiagramID(diag.attribute("id").getValue());
				diagram.setDiagramName(diag.element("properties").attribute("name").getValue());
				diagram.setDiagramType(diag.element("properties").attribute("type").getValue());
				diagram.setBehaviorID(diag.element("model").attribute("parent").getValue());
				for(Element element:interactionList)
				{
					if(element.attributeValue("type").equals("uml:Interaction")&&element.attributeValue("idref").equals(diagram.getBehaviorID()))
					{
						
						ArrayList<Element> constraintList=new ArrayList<Element>();
						constraintList.addAll(element.element("constraints").elements("constraint"));
						
						for(Element eConstraint:constraintList)
						{
							if(eConstraint.attributeValue("name").equals("postCondition"))
							{
								diagram.setNotation(eConstraint.attributeValue("description"));
							}
							if(eConstraint.attributeValue("name").equals("probability"))
							{
								try{
									diagram.setProb(Double.parseDouble(eConstraint.attributeValue("description")));
								}
								catch(Exception e)
								{
									System.out.println("��������Լ��ֵ����������������UMLģ�Ͷ�Ӧ����Լ���м���޸ģ���������Ϊ˫����С�����ͣ�");
									e.printStackTrace();
								}
							}
							
						}
						
					}
				}
				//diagram.setNotation(diag.element("properties").attributeValue("document"));
				
				//diagram.setProb(0.5);
				diagrams.add(diagram);
			}
		}
		return this.diagrams; //Ҫ�����Ƿ���ݱ�ǽ�����behaviorID��ÿ��diagrams��ӵ���Ӧ�������¡�������������������^^^6^^^^^^^^^^^^^^^^^^
	}
	
	//���·���                    ����Diagram��Ӧ��˳��ͼ�л�ȡ��Ϣ�ķ�������ʱroot�Ѿ��任��
	/* 
	 * ���Ȼ�ȡ˳��ͼ�¶�Ӧ��������������Ϣ
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<LifeLine> retrieveLifeLines() throws InvalidTagException
	{
		
		ArrayList<Element> lifeLineList=new ArrayList<Element>();
		lifeLineList.addAll(root.element("Model").element("packagedElement").
				element("packagedElement").element("ownedBehavior").elements("lifeline"));
		for(Iterator<Element> it=lifeLineList.iterator();it.hasNext();)
		{
			Element life=it.next();
			if(life.attribute("type").getText().equals("uml:Lifeline"))
			{
				LifeLine lifeLine=new LifeLine();
				lifeLine.setId(life.attribute("id").getText());
				lifeLine.setName(life.attribute("name").getText());
				
				/*
				 * lifeLine�ϵ�λ��û������
				 * �����Ƿ��������ϵĽ���װ���������ϡ���������������������������������������???
				 */ 
				//lifeLine.setNodes(nodes);
				
				lifeLines.add(lifeLine);
			}
		}
		
		return this.lifeLines;
	}
	
	/**
	 * ��ȡXML�ļ����������µ�λ����Ϣ
	 * λ�㣨�ڵ㣩��һ��ڵ�+���Ƭ���нڵ�
	 * @return Node ����
	 * @throws InvalidTagException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Node> retrieveNodes() throws InvalidTagException
	{
		ArrayList<Element> fragmentList=new ArrayList<Element>();
		fragmentList.addAll(root.element("Model").element("packagedElement").
				element("packagedElement").element("ownedBehavior").elements("fragment"));
		for(Iterator<Element> it=fragmentList.iterator();it.hasNext();)
		{
			Element frag=it.next();
			//�����һ���˳��λ��
			if("uml:OccurrenceSpecification".equals(frag.attribute("type").getText()))
			{
				
				Node node=new Node();
				node.setId(frag.attribute("id").getText());
				node.setCoverdID(frag.attribute("covered").getText());
				for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
				{
					LifeLine lifeLine=lifeLineIt.next();
					if(lifeLine.getId().equals(frag.attribute("covered").getText()))
						node.setLifeLineName(lifeLine.getName());
				}
				nodes.add(node);
			}
			
			//��������Ƭ���ϵ�λ��,�����ȡ����
			
			if(frag.attribute("type").getText().contains("CombinedFragment"))
			{
				ArrayList<Element> operandList=new ArrayList<Element>();
				operandList.addAll(frag.elements("operand"));
				for(Iterator<Element> operandIt=operandList.iterator();operandIt.hasNext();)
				{
					Element oper=operandIt.next();
					Operand operand=new Operand();
					operand.setId(oper.attribute("id").getText());
					
					//�����µ�λ�㼯��
					ArrayList<Element> operFragment=new ArrayList<Element>();
					operFragment.addAll(oper.elements("fragment"));
					for(Element oe:operFragment)//һ�����Ƭ�Σ���������ͨ��㣬��Ҫ���¸ı࡭��������������������������
					{
						Node node=new Node();
						node.setId(oe.attribute("id").getText());
						node.setCoverdID( oe.attribute("covered").getText());
						for(LifeLine lifeLine:lifeLines)
						{
							if(lifeLine.getId().equals(oe.attribute("covered").getText()))
							{
								node.setLifeLineName(lifeLine.getName());
							}
						}
						nodes.add(node);
					} 
				}
			}
		}
		return this.nodes;
	}
	
	/**
	 * ��ȡXML�ļ��е����Ƭ����Ϣ��������������������������������������������ͬʱ�������е�nodes
	 * id,type,����������,��װ�˲����򼯺�
	 * @return Fragment ����
	 * @throws InvalidTagException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Fragment> retrieveFragments() throws InvalidTagException
	{		
		ArrayList<Element> firstFragmentsList=new ArrayList<Element>();
		
		firstFragmentsList.addAll(root.element("Model").element("packagedElement")
				.element("packagedElement").element("ownedBehavior").elements("fragment"));
		
		for (Iterator<Element> fragmentIterator = firstFragmentsList.iterator(); fragmentIterator.hasNext();) 
		{
			Element elFragment = fragmentIterator.next();
			if (elFragment.attribute("type").getValue().contains("OccurrenceSpecification")) 
			{
				Node node=new Node();
				node.setId(elFragment.attribute("id").getText());
				node.setCoverdID(elFragment.attribute("covered").getText());
				for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
				{
					LifeLine lifeL=lifeLineIt.next();
					if(lifeL.getId().equals(elFragment.attribute("covered").getText()))
						node.setLifeLineName(lifeL.getName());
				}
				nodes.add(node);
			}
			
			if(elFragment.attribute("type").getText().contains("CombinedFragment"))
			{
				//�������Ƭ�ε�id,name,type,����������                                          
				Fragment localFragment = new Fragment();
				localFragment.setId(elFragment.attribute("id").getValue());
				localFragment.setName(elFragment.attributeValue("name"));
				localFragment.setType(elFragment.attribute("interactionOperator").getValue());
				
				ArrayList<String> coveredIDs = new ArrayList<String>();
				ArrayList<Element> coveredList = new ArrayList<Element>();
				coveredList.addAll(elFragment.elements("covered"));
				for (Iterator<Element> coveredIterator = coveredList.iterator(); coveredIterator.hasNext();) 
				{
					Element elCovered = coveredIterator.next();
					String coveredID = elCovered.attribute("idref").getValue();
					coveredIDs.add(coveredID);
				}
				localFragment.setCoveredIDs(coveredIDs);
				
				//���������װ��������
				ArrayList<Operand> localOperands = new ArrayList<Operand>();
				ArrayList<Element> operandList = new ArrayList<Element>();
				operandList.addAll(elFragment.elements("operand"));
				//����ÿ���������id,condition,������λ�㼯,������λ��Id��;��������������������������������������Ϣû�з�װ;
				//����λ����ӵ���nodes��
				for (Iterator<Element> operandIterator = operandList.iterator(); operandIterator.hasNext();) 
				{
					
					Element elOperand = operandIterator.next();
					Operand operand = new Operand();
					operand.setId(elOperand.attribute("id").getValue());
					if (elOperand.element("guard").element("specification").attribute("body") != null)
					{
						operand.setCondition(elOperand.element("guard").element("specification").attribute("body").getValue());
					}
					
					ArrayList<Node> localNodes = new ArrayList<Node>();
					ArrayList<String> localNodeIds = new ArrayList<String>();
					ArrayList<Fragment> fragmentsInOperand=new ArrayList<Fragment>();
					ArrayList<Element> childFragmentInOperandList = new ArrayList<Element>();
					childFragmentInOperandList.addAll(elOperand.elements("fragment"));
					for (Iterator<Element> elmentIterator = childFragmentInOperandList.iterator(); elmentIterator.hasNext();)
					{
						Element childFragmentInOperand = elmentIterator.next();
						if(childFragmentInOperand.attributeValue("type").contains("OccurrenceSpecification"))
						{
							Node node = new Node();
							node.setId(childFragmentInOperand.attribute("id").getValue());
							node.setCoverdID(childFragmentInOperand.attribute("covered").getValue());
							for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
							{
								LifeLine lifeLine=lifeLineIt.next();
								if(lifeLine.getId().equals(childFragmentInOperand.attribute("covered").getText()))
									node.setLifeLineName(lifeLine.getName());
							}
							localNodes.add(node);
							nodes.add(node);
							localNodeIds.add(node.getId());
						}
						if(childFragmentInOperand.attributeValue("type").contains("CombinedFragment"))
						{
							/*
							 * ����������������Ƭ�Σ����ñ�ǣ��ݹ�������Ƭ�λ�ȡ����
							 */
							operand.setHasFragment(true);
							if(childFragmentInOperand!=null)
							{
								fragmentsInOperand.add(retrieveChildFragment(childFragmentInOperand));
								
							}
						
						}
						
					}
					operand.setNodeIds(localNodeIds);
					operand.setNodes(localNodes);
					if(fragmentsInOperand!=null)
					{
						operand.setFragments(fragmentsInOperand);
					}
					//����messagesû�з�װ
					localOperands.add(operand);
				}
				localFragment.setOperands(localOperands);
				this.fragments.add(localFragment);
			}
		}
		return this.fragments;
	}
	
	
	//������Ƭ��Ƕ��,  �Ӵ˿�ʼѭ�������Ƭ�η�װ--------------
		//�����µĸ��ڵ� ����λ������Ƭ�λ�ȡ����
		
		
		 /*
		  * 
		  *^^^^^^^^^^^^^^^^^^^^^^^^�������µ�λ��Ƭ��+���Ƭ��
		  * 
		  * ��ʼ����ǰ���Ƭ�μ��϶���localFrgment��
		  * ��localFragment����id,name,type,����������
		  *
				  * ��ʼ��operands;
				  * ��ȡ����operand���ڵİ���
				  * for(������ǰ��Ƕ�׵�ÿһ��������operand)
				  *         ���ò���ID
				  *         ���ò���ִ��Condition
				  *         ��ʼ�� localNodes���ڴ洢��ǰ��������һ���λ��
				  *         ��ʼ�� localNodeIds���ڴ洢��ǰ�������е�һ��λ��ID
				  *         ��ȡ�ò������µ�����fragment
				  *         for(����ÿһ��fragment)
				  *            �����һ��λ��
				  *               ����λ�����localNodes;
				  *               ����λ�����ȫ��nodes��;
				  *               ����λ����ӦID����localNodeIds;
				  *            ��������Ƭ��
				  *               ����ǰ�������isHasFragment=true;(Ĭ��Ϊfalse)
				  *               ���ú���encapsulationFragment(��ǰfragment���Ƭ�����ڵİ����),
				  *                                    ���ص����Ƭ����ӵ���ǰ�������µ����Ƭ�μ���;������
				  *         end;
				  *         ��localNodes��װ��operand;
				  *         ��localNodeIds��װ��operand;
				  * end
				  * ��operands��װ�뵱ǰ��localFragment�� 
			    *��localFragment��װ��localFrgments;
			end
		  * 
		  * 
		  */
	
	@SuppressWarnings("unchecked")
	public Fragment retrieveChildFragment(Element newRoot)
	{
		Fragment localFragment = new Fragment();
		localFragment.setId(newRoot.attribute("id").getValue());
		localFragment.setName(newRoot.attributeValue("name"));
		localFragment.setType(newRoot.attribute("interactionOperator").getValue());
		
		ArrayList<String> coveredIDs = new ArrayList<String>();
		ArrayList<Element> coveredList = new ArrayList<Element>();
		
		coveredList.addAll(newRoot.elements("covered"));
		for (Iterator<Element> coveredIterator = coveredList.iterator(); coveredIterator.hasNext();) 
		{
			Element elCovered = coveredIterator.next();
			String coveredID = elCovered.attribute("idref").getValue();
			coveredIDs.add(coveredID);
		}
		localFragment.setCoveredIDs(coveredIDs);
		
		ArrayList<Operand> localOperands = new ArrayList<Operand>();
		ArrayList<Element> operandList = new ArrayList<Element>();
		operandList.addAll(newRoot.elements("operand"));
		for (Iterator<Element> operandIterator = operandList.iterator(); operandIterator.hasNext();) 
		{
			//���ò�����id,condition
			Element elOperand = operandIterator.next();
					
			Operand operand = new Operand();
			ArrayList<Fragment> fragmentsInOperand=new ArrayList<Fragment>();   //�Ķ�
			operand.setId(elOperand.attribute("id").getValue());
			if (elOperand.element("guard").element("specification").attribute("body") != null)
			{
				operand.setCondition(elOperand.element("guard").element("specification").attribute("body").getValue());
			}
			ArrayList<Node> localNodes = new ArrayList<Node>();
			ArrayList<String> localNodeIds = new ArrayList<String>();		
			ArrayList<Element> childFragmentInOperandList = new ArrayList<Element>();
			childFragmentInOperandList.addAll(elOperand.elements("fragment"));
			for (Iterator<Element> elmentIterator = childFragmentInOperandList.iterator(); elmentIterator.hasNext();)
			{
				Element childFragmentInOperand = elmentIterator.next();
				if(childFragmentInOperand.attributeValue("type").contains("OccurrenceSpecification"))
				{
					Node node = new Node();
					node.setId(childFragmentInOperand.attribute("id").getValue());
					node.setCoverdID(childFragmentInOperand.attribute("covered").getValue());
					for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
					{
						LifeLine lifeLine=lifeLineIt.next();
						if(lifeLine.getId().equals(childFragmentInOperand.attribute("covered").getText()))
							node.setLifeLineName(lifeLine.getName());
					}
					localNodes.add(node);
					nodes.add(node);
					localNodeIds.add(node.getId());
				}
				if(childFragmentInOperand.attributeValue("type").equals("uml:CombinedFragment"))
				{
							/*
							 * ����������������Ƭ�Σ����ñ�ǣ��ݹ�������Ƭ�λ�ȡ����
							 */
					operand.setHasFragment(true);
					fragmentsInOperand.add(retrieveChildFragment(childFragmentInOperand));		
				}	
			}
			
			operand.setNodeIds(localNodeIds);
			operand.setNodes(localNodes);
			if(operand.isHasFragment()==true)
			{
				operand.setFragments(fragmentsInOperand);
			}
			//����messagesû�з�װ
			localOperands.add(operand);
			
		}
		
		localFragment.setOperands(localOperands);
		return localFragment;
		
	}
	
	/*
	 * ��ȡ������Ϣmessage
	 * �������Ƭ����������������Ӧ����Ϣ��װ��������
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Message> retrieveMessages(double pro) throws InvalidTagException 
	{
		
		ArrayList<Element> messageList = new ArrayList<Element>();
		messageList.addAll(root.element("Model").element("packagedElement")
				.element("packagedElement").element("ownedBehavior").elements("message"));
		
		//����ÿ����ϢƬ��
		for (Iterator<Element> messageIterator = messageList.iterator(); messageIterator.hasNext();) 
		{
			Element elMessage = messageIterator.next();

			/*
			 * ʵ������Ϣ�� ����ȡ������Ϣ(id,name,senderID,receiverID,����,��������)
			 */
			Message message = new Message();
			Stimulate stimulate=new Stimulate();
			message.setId(elMessage.attribute("id").getValue());
			message.setName(elMessage.attribute("name").getValue());
			message.setProb(pro);
			message.setSenderID(elMessage.attribute("sendEvent").getValue());
			message.setReceiverID(elMessage.attribute("receiveEvent").getValue());
			if(elMessage==messageList.get(messageList.size()-1))
				message.setLast(true);//���һ����Ϣ����isLastΪ�棻
			
			if(elMessage.hasContent())
			{
				String name=message.getName();
				int tag=0;
				for(int i=0;i<name.length();i++)
				{
					if(name.charAt(i)=='('&&name.charAt(i+1)!=')')
					{
					  tag=1;
					}
				}
				//�����ֵ���Ѱ��   ��������
				if(tag==1)
				{
					int k=0;
					for(int i=0;i<(message.getName()).length();i++)
					{
						if(message.getName().charAt(i)=='(')
						{
							k=i;
							break;
						}
						
					}
					String parametersTypeStr=message.getName().substring(k+1,message.getName().length()-1)+",";
					stimulate.setParameterTypeList(SerachParametersType(parametersTypeStr));
					
				}
				//Ѱ������
				List<Element> argumentElements=new ArrayList<Element>();
				argumentElements.addAll(elMessage.elements());
				for(Element e:argumentElements)
				{
					if(e.getName().equals("argument"))
					{
						stimulate.addParameterName(e.attributeValue("name"));
					}
				}
				
			}
			/*
			 * ��ȡ��Ϣ�ķ��͡����ܶ�������(sender,receiver)
			 * �������н��
			 * ����ýڵ�id������Ϣ���ͽ��id,�򽫸ýڵ��������������Ƹ�����Ϣ������sender��
			 * ͬʱ��ͬ���ķ���������Ϣ�����ߣ�
			 */
			for (Node node : nodes)  //������Ϣ�ϵĽ�������������ҵ���Ϣ�������ķ��������������ߣ�
			{
				if (message.getSenderID().equals(node.getId())) 
				{
					message.setSender(node.getLifeLineName());
				}
				if (message.getReceiverID().equals(node.getId())) 
				{
					message.setReceiver(node.getLifeLineName());
				}
			}
			
			/*��ȡ��Ϣ(infragment,operandId)
			 * ��ÿ����Ϣ������һ�����Ƭ�Σ��������Ƭ�������еĲ�����
			 * ���������Ľ��id=��Ϣ�ķ��ͽ��id,��ô��˵������Ϣ�����Ƭ�����棬������Ϣ����InFragment��Ǻ��������Ƭ�ε�id
			 */
			for (Fragment fragment : fragments) 
			{
				for (Operand operand : fragment.getOperands()) 
				{
					if(message.isInFragment()==false)
					{
						if(operand.isHasFragment()==true)
						{
							//��Ϊ�˴���Ϣ�Ǿֲ���������Ҫ����һ���й�����Ϣ��ֵ��
							isInChildFragment(message,operand,fragment);
							
						}
						
						//����㡭������
						else
						{
							for (Node node : operand.getNodes()) 
							{
								if (node.getId().equals(message.getSenderID())) 
								{
									message.setInFragment(true);
									message.setFragmentId(fragment.getId());
									message.setFragType(fragment.getType());
									message.setOperandId(operand.getId());
								}
							}
						}
					}
				}
			}
			/*
			 * ��ȡ��Ϣ�����Ƭ���еĽ�������
			 * �������е�Connector�������connector���ļ�����ID=����Ϣ��ID��
			 * ��ô����Ϣ�����Ƭ��   �������   ���ó�value���µ�ֵ����inAlt,outAlt,inPar,outPar�ȣ�
			 * ***********Ѱ�����Լ�����ʽ(���ߵĴ�����û�й�ϵ)
			 */
			ArrayList<Element> connectorList = new ArrayList<Element>();
			connectorList.addAll(root.element("Extension").element("connectors").elements("connector"));

			for (Iterator<Element> connectorIterator = connectorList.iterator(); connectorIterator.hasNext();) 
			{
				Element elConnector = connectorIterator.next();
				//if��Ϣ��id�����ӵ�id���
				// ��һ���ж���Ϣ�����ĸ����Ƭ��
				//�ڶ������Ի�ȡ��ǩlabel���ַ����еķ���ֵ�ͷ���ֵ����
				if (message.getId().equals(elConnector.attribute("idref").getValue())) 
				{
					if (elConnector.element("documentation").hasContent())//�Ƿ���Ҫ�仯�ж�������ȥ��̾�ţ�������������кβ�ͬ�ж�һ�£�������������������������������
					{
						if (elConnector.element("documentation").attribute("value") != null) 
						{
							message.setFragFlag(elConnector.element("documentation").attribute("value").getValue());
						}
					}
					Element extendedProperties=elConnector.element("extendedProperties");
					if(extendedProperties.attribute("conditional")!=null)
					{
						String domainsStr=extendedProperties.attribute("conditional").getText()+",";
						stimulate.setDomains(SerachParametersType(domainsStr));
					}
					if(extendedProperties.attribute("constraint")!=null)
					{
						String expressionsStr=extendedProperties.attribute("constraint").getText()+",";
						stimulate.setConstraintExpresstions(SerachParametersType(expressionsStr));
					}
					//��ȡ��Ϣ����ֵ�������ͺ���Ϣ���ַ���������������ֵ�ͷ���ֵ����
					String labelStr=elConnector.element("labels").attributeValue("mt");
					if(labelStr.contains("="))
					{
						message.setReturnValue(labelStr.substring(0, labelStr.indexOf("=")));
					}
					if(labelStr.contains(":"))
					{
						message.setReturnValueType(labelStr.substring(labelStr.indexOf(":")+1,labelStr.length()));
					}
				}//��Ϣ��������������û������(probability,exectime,notation,fragmentId,fragType,islast)
			}
			message.setStimulate(stimulate);
			messages.add(message);
		}
		encapsulationMessages(this.fragments);//�����Ƭ������Ĳ�����װ��Ϣ
		return this.messages;
	}
	
	//���������Ϣ�Ƿ������Ƭ�Σ����Ƭ��ID�����Ƭ�����ͣ���������ID��
	public void isInChildFragment(Message message,Operand operand,Fragment fragment)
	{
		
		for (Node node : operand.getNodes()) 
		{
			if (node.getId().equals(message.getSenderID())) 
			{
				
				message.setInFragment(true);
				message.setFragmentId(fragment.getId());
				message.setFragType(fragment.getType());
				message.setOperandId(operand.getId());
				
			}
				
		}
		if(message.isInFragment()==false)
		{
			if(operand.isHasFragment()==true)
			{
				ArrayList<Fragment> messageFragments=new ArrayList<Fragment>();
				messageFragments=operand.getFragments();
				for(Fragment messageFragment:messageFragments)
				{
					ArrayList<Operand> messageOperands=new ArrayList<Operand>();
					messageOperands=messageFragment.getOperands();
					for(Operand messageOperand:messageOperands)
					{
						isInChildFragment(message,messageOperand,messageFragment);
					}
				}
				
			}
		}
	}
	
	/*
	 * ��������Ϣ��װ����Ӧ�Ĳ���������
	 * ����ÿһ��������ÿ��һ��������Ҫ����һ�����е���Ϣ���жϸ���Ϣ�Ƿ����ڸò�����
	 */
	public void encapsulationMessages(ArrayList<Fragment> newFragments) 
	{
				
		for (Fragment fragment : newFragments) 
		{
			for (Operand operand : fragment.getOperands()) 
			{
				ArrayList<Message> operandMessages = new ArrayList<Message>();
				for (Message message : messages) 
				{
					if (operand.getNodeIds().contains(message.getSenderID())
							&& operand.getNodeIds().contains(message.getReceiverID()))
					{
						operandMessages.add(message);
					}
				}
				//System.out.println(operandMessages);
				operand.setMessages(operandMessages);
				
				if(operand.isHasFragment()==true)
				{
					ArrayList<Fragment> childFragments=new ArrayList<Fragment>();
					childFragments.addAll(operand.getFragments());
					encapsulationMessages(childFragments);
				}
			}
		}
		
	}
	public List<String> SerachParametersType(String typeStr)
	{
		List<String> types=new ArrayList<String>();
		int k=0;//��ʼλ�ã�������,��ֹͣ;
		for(int i=0;i<typeStr.length();i++)
		{
			if(typeStr.charAt(i)==',')
			{
				types.add(typeStr.substring(k,i)); //���ƴӵ�k��λ�õ�i-1λ�õ��ַ�����
				k=i+1;
			}
		}
		return types;
	}
	
	
	
	public String getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}


	public Element getRoot() {
		return root;
	}
	public void setRoot(Element root) {
		this.root = root;
	}

	public ArrayList<UseCase> getUseCases() {
		return useCases;
	}
	public void setUseCases(ArrayList<UseCase> useCases) {
		this.useCases = useCases;
	}

	public ArrayList<Diagram> getDiagrams() {
		return diagrams;
	}
	public void setDiagrams(ArrayList<Diagram> diagrams) {
		this.diagrams = diagrams;
	}

	public ArrayList<LifeLine> getLifeLines() {
		return lifeLines;
	}
	public void setLifeLines(ArrayList<LifeLine> lifeLines) {
		this.lifeLines = lifeLines;
	}


	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	public ArrayList<Fragment> getFragments() {
		return fragments;
	}
	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}

	
	
}
