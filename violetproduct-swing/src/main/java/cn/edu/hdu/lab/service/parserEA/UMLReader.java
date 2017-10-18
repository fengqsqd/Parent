package cn.edu.hdu.lab.service.parserEA;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.edu.hdu.lab.dao.uml.Behavior;
import cn.edu.hdu.lab.dao.uml.Diagram;
import cn.edu.hdu.lab.dao.uml.DiagramsData;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.LifeLine;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Node;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.REF;
import cn.edu.hdu.lab.dao.uml.SDRectangle;
import cn.edu.hdu.lab.dao.uml.Stimulate;
import cn.edu.hdu.lab.dao.uml.UseCase;


public class UMLReader {

	private String xmlFile;
	private Element root;
	private String sdName;
	
	private ArrayList<UseCase> useCases=new ArrayList<UseCase>();
	//private ArrayList<Behavior> behaviors;
	
	private ArrayList<Diagram> diagrams=new ArrayList<Diagram>();	
	private ArrayList<LifeLine> lifeLines=new ArrayList<LifeLine>();
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); //˳��������Ƭ�Σ�
	private ArrayList<REF> umlREF = new ArrayList<REF>(); //������Ϣ
	static ArrayList<DiagramsData> umlAllDiagramData = new ArrayList<DiagramsData>();
	
	private DiagramsData completeSD=new DiagramsData();
	private static int idCount=0;
    
	public UMLReader(String xmlFile) {		
		// TODO Auto-generated constructor stub
		this.xmlFile=xmlFile;
		load();		
	}
	
	public UMLReader(String xmlFile,String fileName) {
		
		// TODO Auto-generated constructor stub
		this.xmlFile=xmlFile;
		this.sdName=fileName;
		load();	
		
	}
	/**
	 * ����UMLģ���ļ�
	 */
	public void load()
	{
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

	/*
	 * ����ͼ��
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
							//��Ҫ�ж����ε���Լ�����
							if(extensionElement.element("constraints")!=null)
							{
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
					}
					
					useCases.add(useCase);
				}
			}
		}
		return this.useCases;
	}

	/*
	 *����ͼ��
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
	
	//���·�������Diagram��Ӧ��˳��ͼSD�л�ȡ��Ϣ�ķ�������ʱroot�Ѿ��任��
	
	/*
	 *һ����ȡ���е�ͬһ��Diagram�µ�������Diagram�Լ�������Ϣ
	 *������ȡ��������Ը߶���Ϣ���������������Ƭ��ID�ͺ��и߶���Ϣ���ַ����������
	 */
	@SuppressWarnings("unchecked")
	public void retrieveAllDiagramElements()
	{
		umlAllDiagramData=new ArrayList<DiagramsData>();
		ArrayList<Element> EADiagramsList = new ArrayList<Element>();//��Ŷ�ȡ�õ���element		
		//1.ȡ�����е�diagram 
		EADiagramsList.addAll(root.element("Extension").element("diagrams").elements("diagram"));		
		//2.����EADiagramIDsList
		for(Iterator<Element>  EADiagramsListIterator=EADiagramsList.iterator();EADiagramsListIterator.hasNext();)
		{
			//ȡ�õ�i��ͼ
			Element diagramI=EADiagramsListIterator.next();
			
			//�������ͼ����elements 
			ArrayList <Element> elements = new ArrayList <Element>();
			try {
				elements.addAll(diagramI.element("elements").elements("element"));
			} catch (Exception e) {
				System.out.println("exception:ͼԪ��");			
			}
			
			//����elements ����ids(������Ϣ��ID)
			ArrayList <String> ids = new ArrayList<String>();//��Ŷ����ID	
			for(Iterator<Element>  elementsIterator=elements.iterator();elementsIterator.hasNext();)
			{
				Element elementI = elementsIterator.next();
				elementI.attributeValue("id");
				String id = elementI.attributeValue("subject");
				String value = elementI.attributeValue("geometry");
				//ids.add(id.substring(13));//ȡ��13λ֮���id���� ��Ϊactor��idֻ�к���13λ�������
				ids.add(id);
				//����fragments/refs/objects/messages����Ϣ�����꣬������Щ����Ͷ�Ӧ������ID�ɶԵĴ������
				SDRectangle rectangle = FixFragmentTool.rectangleFromValueString(value);
				FixFragmentTool.rectangleById.put(id, rectangle);
			}
			
			//����DiagramsData����
			DiagramsData diagramData = new DiagramsData();
			diagramData.setDiagramID(diagramI.attributeValue("id")); //ͼID
			String name = diagramI.element("properties").attributeValue("name");
			diagramData.setName(name);  //ͼ��
			diagramData.setIds(ids);    //ͼ��������������Ϣ�����ƬƬ�Σ����ã�������Ϣ����ID
			
			//lastMessageIDByKeyWithDiagramID.put(diagramData.diagramID, "init");
			//��DiagramsData���� ��ӵ���Ա����umlAllDiagramData��
			
			umlAllDiagramData.add(diagramData);
		}
		
		
//������������Ը߶���Ϣ****************************************
		ArrayList<Element> EAElements = new ArrayList<>();
		EAElements.addAll(root.element("Extension").element("elements").elements("element"));
		for(Element element : EAElements) 
		{
			if(element.attributeValue("idref")!=null)
			{
				//����ַ������в�����ĸ߶�size
				FixFragmentTool.xrefValueById.put(element.attributeValue("idref"), element.element("xrefs").attributeValue("value"));
			}
		}
	}
	
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
				 * lifeLine�ϵ�λ��û�з�װ
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
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Node> retrieveNodes() throws Exception
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
				nodes.add(node);
			}
			
			//��������Ƭ���ϵ�λ��,�����ȡ������������Ƭ������Ƕ���Ƭ������ѭ���ݹ�
			
			if(frag.attribute("type").getText().contains("CombinedFragment"))
			{
				retrieveNodesInFragments(frag);
			}
		}
		
		handleInfoLifeLineAndNodes(lifeLines,nodes);
		
		return this.nodes;
	}
	@SuppressWarnings("unchecked")
	private void retrieveNodesInFragments(Element fragElement) throws Exception
	{
		ArrayList<Element> operandList=new ArrayList<Element>();
		if(fragElement.elements("operand").size()>0)
		{
			operandList.addAll(fragElement.elements("operand"));
			for(Iterator<Element> operandIt=operandList.iterator();operandIt.hasNext();)
			{
				Element oper=operandIt.next();				
				//�����µ�λ�㼯��
				ArrayList<Element> operFragmentList=new ArrayList<Element>();
				operFragmentList.addAll(oper.elements("fragment"));
				for(Element of:operFragmentList)//һ�����Ƭ�Σ���������ͨ��㣬��Ҫ���¸ı࡭��������������������������
				{
					if("uml:OccurrenceSpecification".equals(of.attribute("type").getText()))
					{
						Node node=new Node();
						node.setId(of.attribute("id").getText());
						node.setCoverdID( of.attribute("covered").getText());
						nodes.add(node);
					}
					if(of.attribute("type").getText().contains("CombinedFragment"))
					{
						retrieveNodesInFragments(of);
					}
				}
				
			}
		}
		else
		{
			throw new Exception("���Ƭ����û�в����򣬼�û��ִ������");
		}
		
	}
	
	/**
	 * ���������߼��ͽ�㼯����Ϣ��װ����
	 * @param lifeLines  ˳��ͼ������������������
	 * @param nodes ˳��ͼ�����������н��
	 */
	private void handleInfoLifeLineAndNodes(ArrayList<LifeLine> lifeLines,ArrayList<Node> nodes)
	{
		//���Ͻ��������߶�����໥��װ���������߶�Ӧ�Ľ�㼯�Ϸ�װ���������ϡ���������������ߵ����Ʒ�װ�ڽ����������
		
		for(Iterator<LifeLine> lifeLineIt=lifeLines.iterator();lifeLineIt.hasNext();)
		{
			LifeLine lifeLine=lifeLineIt.next();
			for(Node node:nodes)
			{
				if(node.getCoverdID().equals(lifeLine.getId()))
				{
					node.setLifeLineName(lifeLine.getName());
					lifeLine.addNode(node);
				}
			}
				
		}
	}
	/**
	 * һ����ȡ����Ƭ����Ϣ
	 * ������ȡXML�ļ��е��������Ƭ����Ϣ�����г��ֵ����Ƭ�κ�Ƕ�׳��ֵ����Ƭ�Σ����з���һ���������棩
	 * ������ȡ�����Ϣ��������Ӧ����װ���������棩
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
			if("uml:OccurrenceSpecification".equals(elFragment.attribute("type").getText()))
			{
				Node node=new Node();
				node.setId(elFragment.attribute("id").getText());
				node.setCoverdID(elFragment.attribute("covered").getText());
				nodes.add(node);
			}
			
			if (elFragment.attribute("type").getValue().equals("uml:InteractionUse")) {//�����һ��ref
				REF ref = new REF();
				ref.setRefID(elFragment.attributeValue("id"));
				ref.setRefName(elFragment.attributeValue("name"));
				
				ArrayList<Element> elCoveredIDs=new ArrayList<Element>();
				elCoveredIDs.addAll(elFragment.elements("covered"));
				if(elCoveredIDs.size()>0)
				{
					for(Element e:elCoveredIDs)
					{
						ref.getCoveredIDs().add(e.attributeValue("idref"));
					}
				}
				umlREF.add(ref);
			}
			
			if(elFragment.attribute("type").getText().contains("CombinedFragment"))
			{
				//�������Ƭ�ε�id,name,type,���������� 
				Fragment localFragment = new Fragment();
				localFragment.setId(elFragment.attribute("id").getValue());
				localFragment.setName(elFragment.attributeValue("name"));
				localFragment.setType(elFragment.attribute("interactionOperator").getValue());
				
				ArrayList<String> coveredIDs = new ArrayList<String>();
				List<Element> coveredList = new ArrayList<Element>();
				coveredList.addAll(elFragment.elements("covered"));
				for (Iterator<Element> coveredIterator = coveredList.iterator(); coveredIterator.hasNext();) 
				{
					Element elCovered = coveredIterator.next();
					String coveredID = elCovered.attribute("idref").getValue();
					coveredIDs.add(coveredID);
				}
				localFragment.setCoveredIDs(coveredIDs);
				
				//���������װ�����Ƭ��
				ArrayList<Operand> localOperands = new ArrayList<Operand>();
				ArrayList<Element> operandList = new ArrayList<Element>();
				operandList.addAll(elFragment.elements("operand"));
				//����ÿ���������id,condition,������λ�㼯,������λ��Id��(δ��װ��Ϣ����Ƕ���Ƭ��)
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
					
					ArrayList<Element> childFragmentInOperandList = new ArrayList<Element>();
					childFragmentInOperandList.addAll(elOperand.elements("fragment"));
					for (Iterator<Element> elmentIterator = childFragmentInOperandList.iterator(); elmentIterator.hasNext();)
					{
						Element childFragmentInOperand = elmentIterator.next();						
						if(childFragmentInOperand.attributeValue("type").contains("CombinedFragment"))
						{/*
							 * ����������������Ƭ�Σ����ñ�ǣ��ݹ�������Ƭ�λ�ȡ����
							 */
							operand.setHasFragment(true);
							if(childFragmentInOperand!=null)
							{
								//��ȡ��Ƕ���Ƭ��
								retrieveChildFragment(childFragmentInOperand); 
							}
						}
						if("uml:OccurrenceSpecification".equals(childFragmentInOperand.attribute("type").getText()))
						{
							
							Node node=new Node();
							node.setId(childFragmentInOperand.attribute("id").getText());
							node.setCoverdID(childFragmentInOperand.attribute("covered").getText());
							
							operand.getNodes().add(node);//��װ���ò���
							
							nodes.add(node);
						}
						if (childFragmentInOperand.attribute("type").getValue().equals("uml:InteractionUse")) {//�����һ��ref
							REF ref = new REF();
							ref.setRefID(childFragmentInOperand.attributeValue("id"));
							ref.setRefName(childFragmentInOperand.attributeValue("name"));
							
							ArrayList<Element> elCoveredIDs=new ArrayList<Element>();
							elCoveredIDs.addAll(childFragmentInOperand.elements("covered"));
							if(elCoveredIDs.size()>0)
							{
								for(Element e:elCoveredIDs)
								{
									ref.getCoveredIDs().add(e.attributeValue("idref"));
								}
							}
							umlREF.add(ref);
						}
						
					}
					//����messagesû�з�װ
					localOperands.add(operand);
				}
				localFragment.setOperands(localOperands);
				this.fragments.add(localFragment);
			}
			
			
		}
		
		////���������ߺͽڵ����Ϣ�������ߺͽ��Ļ���Ƕ��(�Ѿ���ȡ�˽ڵ���Ϣ)
		
		for(Node node:nodes)
		{
			for(LifeLine lifeLine : lifeLines) {
				if(node.getCoverdID().contains(lifeLine.getId()))
				{
					node.setLifeLineName(lifeLine.getName());
					lifeLine.getNodes().add(node);
				}
			}
		}
		/*System.out.println("��㣺"+nodes.size());
		System.out.println("�����ߣ�"+lifeLines.size());
		for(LifeLine life:lifeLines)
		{
			life.print_LifeLine();
		}
		for(Node node:nodes)
		{
			node.print_node();
		}
		System.out.println(":::::::::::::::::::::::::::::::::::");*/
		//������Ƭ�κ����Ƭ�θ�������Ϣ		
		for(REF ref:umlREF)
		{
			ref.setRectangle(FixFragmentTool.rectangleById.get(ref.getRefID()));
		}
		for(int i=0;i<umlREF.size();i++)
		{
			umlREF.get(i).setCount(i);
		}
		for(Fragment frag:fragments)
		{
			frag.setRectangle(FixFragmentTool.rectangleById.get(frag.getId()));//���Ƭ�ε�������Ϣ��ȡ
			
			FixFragmentTool.operandRectangle(frag) ;//��������Ϣ��ȡ
		}
		
		
		/* System.out.println("\n����Ƭ����Ϣ������"+umlREF.size());
		 for(REF ref:umlREF)
			{
				System.out.println("Ƭ�����ƣ�"+ref.getRefName()+"---"+ref.getRefID());
				System.out.println(ref.getRectangle().toString());
			}
		 System.out.println("*********************************************");*/
		 
		return this.fragments;
	}
	
	/**
	 * Ѱ����Ƕ���Ƭ�Σ����з��ڼ�������
	 * Ѱ�Ҳ����µĽ�㣬��������ڶ�Ӧ����
	 * @param newRoot
	 */
	@SuppressWarnings("unchecked")
	private void retrieveChildFragment(Element newRoot)
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
			
			operand.setId(elOperand.attribute("id").getValue());
			if (elOperand.element("guard").element("specification").attribute("body") != null)
			{
				operand.setCondition(elOperand.element("guard").element("specification").attribute("body").getValue());
			}
				
			ArrayList<Element> childFragmentInOperandList = new ArrayList<Element>();
			childFragmentInOperandList.addAll(elOperand.elements("fragment"));
			for (Iterator<Element> elmentIterator = childFragmentInOperandList.iterator(); elmentIterator.hasNext();)
			{
				Element childFragmentInOperand = elmentIterator.next();				
				if(childFragmentInOperand.attributeValue("type").equals("uml:CombinedFragment"))
				{
					/* 
					 * ����������������Ƭ�Σ����ñ�ǣ��ݹ�������Ƭ�λ�ȡ����
				     */
					operand.setHasFragment(true);
					retrieveChildFragment(childFragmentInOperand);		
				}
				if("uml:OccurrenceSpecification".equals(childFragmentInOperand.attribute("type").getText()))
				{
					
					Node node=new Node();
					node.setId(childFragmentInOperand.attribute("id").getText());
					node.setCoverdID(childFragmentInOperand.attribute("covered").getText());
					operand.getNodes().add(node);
					nodes.add(node);
				}
				if (childFragmentInOperand.attribute("type").getValue().equals("uml:InteractionUse")) {//�����һ��ref
					REF ref = new REF();
					ref.setRefID(childFragmentInOperand.attributeValue("id"));
					ref.setRefName(childFragmentInOperand.attributeValue("name"));
					
					ArrayList<Element> elCoveredIDs=new ArrayList<Element>();
					elCoveredIDs.addAll(childFragmentInOperand.elements("covered"));
					if(elCoveredIDs.size()>0)
					{
						for(Element e:elCoveredIDs)
						{
							ref.getCoveredIDs().add(e.attributeValue("idref"));
						}
					}
					umlREF.add(ref);
				}
			}
			//����messagesû�з�װ
			localOperands.add(operand);
		}
		localFragment.setOperands(localOperands);
		this.fragments.add(localFragment);
		
	}
	
	
	/*
	 * ��ȡ������Ϣmessage
	 * ������Ӧ����Ϣ��װ�����Ƭ�εĲ�����
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
//			if(elMessage==messageList.get(messageList.size()-1))
//				message.setLast(true);//���һ����Ϣ����isLastΪ�棻
			
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
				//�����ֵ���Ѱ�� ��������
				if(tag==1)
				{
					int k=0;
					for(int i=0;i<message.getName().length();i++)
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
				//Ѱ�Ҳ�������
				List<Element> argumentElements=new ArrayList<Element>();
				argumentElements.addAll(elMessage.elements());
				for(Element e:argumentElements)
				{
					if(e.getName().contains("argument"))
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
			 * ������ϢǶ�׽������������
			 * message ��ʱ���ٸ�������������Ƭ�β�����
			 */
			//System.out.println("���Ƭ��������"+fragments.size());
			for (Fragment fragment : fragments) 
			{
				for (Operand operand : fragment.getOperands())
				{
					if(message.isInFragment()==false)
					{
						if(operand.getNodes().size()>0)
						{
							for (Node node : operand.getNodes()) 
							{
								if (node.getId().contains(message.getSenderID())) 
								{
									message.setInFragment(true);
									message.setFragmentId(fragment.getId());
									message.setFragType(fragment.getType());
									message.setOperandId(operand.getId());
									operand.getMessages().add(message);
								
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
			 * Ѱ����Ϣ��ʼ״̬��ʱ��Լ������
			 */
			ArrayList<Element> connectorList = new ArrayList<Element>();
			connectorList.addAll(root.element("Extension").element("connectors").elements("connector"));

			for (Iterator<Element> connectorIterator = connectorList.iterator(); connectorIterator.hasNext();) 
			{
				Element elConnector = connectorIterator.next();
				//if��Ϣ��id�����ӵ�id���
				// ��һ���ж���Ϣ�����ĸ����Ƭ��
				//�ڶ������Ի�ȡ��ǩlabel���ַ����еķ���ֵ�ͷ���ֵ����
				if(message.getId().equals(elConnector.attribute("idref").getValue())) 
				{
					
					//����Notes�Զ����������ȡ�������ͺͲ����б�		
					if (elConnector.element("documentation").attribute("value")!= null) 
					{						
						String str=elConnector.element("documentation").attributeValue("value");
						setStimulate(stimulate, str);					
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
					//��ȡ��Ϣ�ĸ߶�����
					message.setPointY(FixFragmentTool.pointYFromValueString(elConnector.element("extendedProperties").attributeValue("sequence_points")));
				
					//��ȡ��Ϣ��ʼ״̬��ִ��ʱ��
					
					message.setFromTimeConstraint(FixFragmentTool.handleFromTimeConstraint(elConnector.element("style").attributeValue("value")));
				}//��Ϣ��������������û������(probability,exectime,notation,fragmentId,fragType,islast)
			}
			message.setStimulate(stimulate);
			
			messages.add(message);
		}
		return this.messages;
	}
	
	
	/*
	 * �����Ƭ�Σ������ߣ���Ϣ��������������ͼ����
	 * 
	 */
	public void assembleInfo2DiffDiagram() throws Exception
	{
		
		for(DiagramsData diagramData : umlAllDiagramData) 
		{
			for(LifeLine lifeline : lifeLines) {
				for(String str:diagramData.getIds())
				{
					if(str.contains(lifeline.getId().substring(13)))
					{
						diagramData.getLifelineArray().add(lifeline);
					}
				}
			}
			for(LifeLine lifeline : lifeLines) {
				if (diagramData.getIds().contains(lifeline.getId())) {
					diagramData.getLifelineArray().add(lifeline);
				}
			}
			for(Fragment fragment : fragments) {
				if (diagramData.getIds().contains(fragment.getId())) {
					diagramData.getFragmentArray().add(fragment);
				}
			}
			
			for(Message message : messages) {
				if (diagramData.getIds().contains(message.getId())) {
					diagramData.getMessageArray().add(message);
				}
			}
			for(REF ref : umlREF) {
				if (diagramData.getIds().contains(ref.getRefID())) {
					diagramData.getRefArray().add(ref);										
				}
			}
			
			diagramData.getTempRefArray().addAll(diagramData.getRefArray()); //�������е����Ƭ�ε�ַ���ã�һ���Ե�+��Ƕ�ģ���
			//������������ ÿ����ͼ�� fragment֮���Ƕ�׹�ϵ  +  fragment��ref֮���Ƕ�׹�ϵ
			//��top left right bottom �ҵ���һ���fragment
			FixFragmentTool.fixFragmentsOfOneDiagram(diagramData,fragments);
		}
		
	/*	System.out.println("\n\n������ͼ����Ϣ��");
		for(DiagramsData diagramData : umlAllDiagramData) 
		{
			System.out.println("\n���ƣ�"+diagramData.getName());
			for(LifeLine lifeline : diagramData.getLifelineArray()) {
				lifeline.print_LifeLine();	
			}
			
			for(Fragment fragment : diagramData.getFragmentArray()) {
				fragment.print_Fragment();
			}
			
			System.out.println("����Ϣ��"+diagramData.getMessageArray().size());
			for(Message message : diagramData.getMessageArray()) {
				message.print_Message();
			}
			System.out.println("����Ƭ�Σ�"+diagramData.getRefArray().size());
			for(REF ref : diagramData.getRefArray()) {
				System.out.println(ref.toString());
			}
		}
		*/
		/*
		 * ͼ������
		 * �ҵ���˳��ͼ��˳����ͼ ���ݹ飬��ref���д��� �ϲ���ͼ����ͼ
		 * 		
		 */
		//
		for(DiagramsData diagramData : umlAllDiagramData) {
			if(diagramData.getName().equals(sdName))
			{
				//System.out.println("\n��ͼ��"+diagramData.getName());
				DFSDiagramByREF(diagramData);				
				//��Ϣָ���ض���
				redirectMessage(diagramData);
				//β��Ϣ��־����
				diagramData.getMessageArray().get(diagramData.getMessageArray().size()-1).setLast(true);
				
				//ȷ����Ϣ������ִ������
				searchOperConditionOfMess(diagramData);
				//������˳��ͼ�ı�������
				completeSD=diagramData;
				break;
			}
		}
		System.out.println("\n*******************��ȫͼ��Ϣ*********************");		
		for(DiagramsData diagramData : umlAllDiagramData) {
			if(diagramData.getName().equals(sdName))
			{
				System.out.println("˳��ͼ���ƣ�"+diagramData.getName()+"---"+sdName);
				for(LifeLine l:diagramData.getLifelineArray())
				{
					l.print_LifeLine();
				}
				for(Message m:diagramData.getMessageArray())
				{
					m.print_Message();
				}
				for(Fragment f:diagramData.getFragmentArray())
				{
					f.print_Fragment();
				}
				for(REF r:diagramData.getRefArray())
				{
					System.out.println(r.toString());
				}				
				break;
			}
		}
	}	
	
	public static void searchOperConditionOfMess(DiagramsData d) throws Exception
	{
		for(Message message:d.getMessageArray())
		{
			if(message.isInFragment())
			{
				//System.out.println(message.getName());
				for(Fragment frag:d.getFragmentArray())
				{
					/*List<String> fragmentIds=retrieveFragmentIds(frag,message);
					if(fragmentIds!=null&&fragmentIds.size()>0)
					{
						for(String str:fragmentIds)
						{
							System.out.println(str);
						}
					}*/
					List<String> operConditions=retrieveOperConditions(frag,message);
					if(operConditions!=null&&operConditions.size()>0)
					{
						String condition=new String("");
						for(String str:operConditions)
						{
							if(str==null)
								throw new Exception("\n˳��ͼ��"+d.getName()+"\n���Ƭ�Σ�"+frag.getName()+"\nȱ��������");
							if(str==operConditions.get(operConditions.size()-1))
							{
								condition+=str.trim();
							}
							else
							{
								condition=condition+str.trim()+",";
							}
						}
						message.setNotation(message.getNotation()+condition);
						break;
					}
				}
			}
			//message.print_Message();
		}
		
	}

	
	public static List<String> retrieveOperConditions(Fragment frag,Message message)
	{
		
		//List<String> fragmentIds=new ArrayList<String>();
		List<String> operConditions=new ArrayList<String>();
		if(frag.getId().equals(message.getFragmentId()))
		{
			//fragmentIds.add(frag.getId());
			for(Operand oper:frag.getOperands())
			{
				int flag=0;
				for(Message mess:oper.getMessages())
				{
					if(mess.getId().equals(message.getId()))
					{
						operConditions.add(oper.getCondition());
						flag=1;
						break;
					}
				}
				if(flag==1)
				{
					break;
				}
			}
			return operConditions;
//			return fragmentIds;
		}
		else 
		{
			int tag=0;
			//fragmentIds.add(frag.getId());
			
			//List<String> newFragmentIds=null;
			
			for(Operand oper:frag.getOperands())
			{
				
				List<String> newOperConditions =null;
				int inTag=0;
				if(oper.isHasFragment()==true)
				{
					for(Fragment newFrag: oper.getFragments())
					{
						newOperConditions=retrieveOperConditions(newFrag,message);
						if(newOperConditions!=null)
						{
							inTag=1;
							break;
						}
					}
				}
				if(inTag==1)
				{
					operConditions.add(oper.getCondition());
					operConditions.addAll(newOperConditions);
					tag=1;
					break;
				}
			}
			if(tag==1)
			{
				return operConditions;
			}
			else return null;
		}
		
	}
	
	
	/**
	 * ���������б�
	 * ������������б�Ϊ�գ�����ֹ
	 * ����������б�Ϊ1�����ҽ����Ǹ�����","���򷵻ؿ�
	 * @param typeStr
	 * @return
	 */
	private List<String> SerachParametersType(String typeStr)
	{
		
		List<String> types=new ArrayList<String>();
		if(typeStr.length()==0)
			return types;
		else
			if(typeStr.length()==1&&typeStr.equals(","))
			{
				return types;
			}
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
	
	public void setStimulate(Stimulate stimulate,String str)
	{
		String[] strs=str.replaceAll("\r|\n","").split("#");
		for(String s:strs)
		{
			if(!s.equals(""))
			{
				String tempStr=s.substring(s.indexOf('(')+1, s.indexOf(')'));
				if(s.substring(0, 9).equals("Condition"))
				{				
					stimulate.setDomains(SerachSubString(tempStr));
					continue;
				}
				if(s.substring(0, 10).equals("Constraint"))
				{				
					stimulate.setConstraintExpresstions(SerachSubString(tempStr));
					continue;
				}
				if(s.substring(0, 4).equals("Time"))
				{				
					stimulate.setTimeConstraints(SerachSubString(tempStr));
					continue;
				}
			}
			
		}
	}
	public List<String> SerachSubString(String str)
	{
		List<String> strs=new ArrayList<String>();
		
		String[] tempStrs=str.split(",");
		
		for(int i=0;i<tempStrs.length;i++)
		{
			if(tempStrs[i].substring(0,4).equals("bool"))
			{
				tempStrs[i]=tempStrs[i].substring(0,tempStrs[i].indexOf("["))+"[true,false]";
			}
		}
		Collections.addAll(strs, tempStrs);	
		
		return strs;
	}
	
	/**
	 * �ݹ�������ͼ�������Ϣ
	 * @param diagramData
	 */
	public static void DFSDiagramByREF(DiagramsData diagramData) {
		//System.out.println("~~~~~~~~~~"+diagramData.getName()+"--�������ã�"+diagramData.getTempRefArray().size());
		if(diagramData.getTempRefArray().size()==0)
		{
			return;
		}
		
		//System.out.println("!!!!!!!!!!!!!!!��ͼ���ƣ�"+diagramData.getName());
		//System.out.println("�����ã�"+diagramData.getTempRefArray().size());
		//�����ֱϵ���û�����Ƕ����
		if(diagramData.getTempRefArray().size()>0)
		{
			/**
			 * 1:ֻ��һ�����ã�ʲô��û�У�ֱ�ӵ�����ȸ��ƺ�����������ͼ����ȸ���
			 */
			//if( diagramData.getRefArray().size()==)
			/**
			 * 2��ֻ�� ���Ƭ��+��Ƕ���ã�ֻ�������Ƭ�ν�����ȸ���
			 */
			
			/**
			 * 3:ֱϵ����+���Ƭ��
			 */
			
			/**
			 * 4����Ϣ+����
			 *   
			 *   ��Ϣ+���Ƭ��+ֱ������+���Ƭ����Ƕ����
			 */
			
			//���ø�������˳��ͼ�е��������λ��(Mindex��Findex)
			for(REF ref:diagramData.getTempRefArray())
			{
				ref.setIndex(FixFragmentTool.refIndexInDiagram(ref, diagramData));
				if(!ref.isInFragFlag())
				{
					ref.setFindex(FixFragmentTool.refIndexBetweenFragsInDiagram(ref, diagramData));
				}
			}
			
			/*
			 * һ���ȴ���˳��ͼ��ֱϵ����Ƭ�Σ�Ƕ�׶������ȸ��ƣ�
			 * ȷ��ͼ��ֱϵref������index(Mindex(������Ϣ)��Findex(�������Ƭ��))
			 * ��Ϣ��ӣ�����index����λ�ã�Ȼ�����ʣ�µ�ref����Ϣ�е�Mindex(index+��һ��ref�к��е���Ϣ����)
			 * ���Ƭ�εĲ��룬���ݵ�ǰref�����Ƭ��֮���λ��Findex����frags
			 * ���º���ref��Findex(Findex+��ǰref�����frags�е�����)
			 * Ϊ�˷���������Ƭ����messages���룬��Ҫ����frag��Mindex(����frag�͵�ǰref�����λ��ȷ���Ƿ����frag�µ�Mindex,�Ѿ�ÿ����Ƕ���Ƭ�ε�Mindex)
			 */
			for(REF ref : diagramData.getRefArray()) 
			{
				//System.out.println("--ֱ�����ã�"+ref.getRefName());
				DiagramsData childDiagram = findDiagramByName(ref.getRefName());
				if(childDiagram.getTempRefArray().size()>0)
				{
					DFSDiagramByREF(childDiagram);//������ͼ ʹ���������õ���ȫͼ
				}
				
				//����Mindex��������Ƭ�����������ͼ :��ȸ�����ͼ
				fixDiagramDataWithRef(diagramData,childDiagram,ref);
				
				//�޸�ͼ����������(ֱϵ+��Ƕ)�������Mindex ��ֱϵ���õ�Findex��ֵ				
				for(REF refi:diagramData.getTempRefArray())
				{
					refi.setIndex(refi.getIndex() + childDiagram.getMessageArray().size());
					if(!ref.isInFragFlag())
					{
						refi.setFindex(refi.getFindex()+childDiagram.getFragmentArray().size());	
					}
				}
				diagramData.getTempRefArray().remove(ref);
			}
			diagramData.getRefArray().clear();
			
			/*
			 * �����ٴ���ǰ��ͼ��frags�µ���Ƕ����Ƭ��(���ø������Ƭ��)(���ڵڶ���)
			 * ����������frags���Ƿ���ref,���У�����䣺
			 * RMessage���뵽��ǰ˳��ͼ��messages�У�*********Mindex�Ѿ������仯�����ȷ����********��
			 * RMessages��û�������Ƭ���е�messes���뵽��ǰoper�£�ͬʱ�޸���Ϣ���Ƭ�α�־Ϊtrue;
			 * RMessages��frags���뵽��ǰ������
			 */
			//System.out.println("-------��ͼ���ƣ�"+diagramData.getName());
			//System.out.println("&&&&&&&��ͼ���ƣ�"+diagramData.getName());
			for(Fragment frag:diagramData.getFragmentArray()) 
			{
				//System.out.println("****���Ƭ�Σ�"+frag.getName());
				//��ǰ����û�����ã���Ѱ���Ƿ�����Ƕ���Ƭ�Σ����У���Ѱ����Ƕ���Ƭ�����Ƿ�������		
				for(Operand oper:frag.getOperands())
				{
					if(oper.getRef()==null&&oper.getFragments().size()>0)
					{
						for(Fragment f:oper.getFragments())
						{
							//System.out.println("���Ƭ�Σ�"+f.getName());  
							handleRefInOper(diagramData,f);							
						}
					}
					else
					if(oper.getRef()!=null)
					{
						
						/*
						 * �Ը���Ƕ���ý��д���
						 */
						//System.out.println("$$$$$$$$��Ƕ����Ƭ�Σ�"+oper.getRef().getRefName());
						DiagramsData childDiagram = findDiagramByName(oper.getRef().getRefName());
						if(childDiagram.getTempRefArray().size()>0)
						{
							DFSDiagramByREF(childDiagram);//�ص�����������ͼ
						}
						fixFragmentWithRef(diagramData,frag,oper,oper.getRef());												
						
						diagramData.getTempRefArray().remove(oper.getRef());
						oper.setRef(null);//�������Ƭ�ε����ã����ڵݹ�ķ����ж�
					}								
				}
			}
			
		}
		
		//System.out.println("###############33��Ϻ����Ϣ���ϣ�"+diagramData.getName());
		/*for(Message m:diagramData.getMessageArray())
		{
			m.print_Message();
		}*/		
				
	}
	
	private static void handleRefInOper(DiagramsData diagramData,Fragment f)
	{
		//System.out.println("���Ƭ�Σ�"+f.getName()+"---��������"+f.getOperands().size());
		for(Operand op:f.getOperands())
		{
			if(op.getRef()==null&&op.getFragments().size()>0)
			{
				for(Fragment frag:op.getFragments())
				{
					//System.out.println("���Ƭ�Σ�"+frag.getName());
					handleRefInOper(diagramData,frag);
				}
			}
			if(op.getRef()!=null)
			{
				//System.out.println("\n���Ƭ����Ƕ���ã�"+op.getRef().getRefName());
				DiagramsData childDiagram = findDiagramByName(op.getRef().getRefName());
				if(childDiagram.getTempRefArray().size()>0)
				{
					//System.out.println("����"+op.getRef().getRefName()+"���������ͼ������������"+childDiagram.getTempRefArray().size());

					DFSDiagramByREF(childDiagram);//�ص�����������ͼ
					
				}
					//System.out.println("��ȸ��ƣ�"+op.getRef().getRefName());
					fixFragmentWithRef(diagramData,f,op,op.getRef());
					diagramData.getTempRefArray().remove(op.getRef());
					op.setRef(null);//�������Ƭ�ε����ã����ڵݹ�ķ����ж�
								
				
			}
			
		}
		
		//System.out.println("#####��Ϻ����Ϣ���ϣ�"+diagramData.getName());
//		for(Message m:diagramData.getMessageArray())
//		{
//			m.print_Message();
//		}
	}
	
	/**
	 * ��ȸ���ͼ��ֱ���������������ͼ���޸�id
	 * @param diagramData
	 * @param childDiagram
	 * @param ref
	 */
	private static void fixDiagramDataWithRef(DiagramsData diagramData, DiagramsData childDiagram, REF ref) {
		//System.out.println("11111����ֱ�����õ���Ϣ��"+diagramData.getName());
		
		idCount++;//����id��׺
		//���lifeline
		for(LifeLine lifeline : childDiagram.getLifelineArray())
		{
			if (!diagramData.getLifelineArray().contains(lifeline)) {//��Ӹ�ͼû�е�lifeline
				diagramData.getLifelineArray().add(lifeline);
			}
		}
		//�Ƿ�����Node????????????????????????????????????///
		
		//�ȸ���һ����ͼ��messageArray
		if(childDiagram.getMessageArray().size()>0)
		{
			ArrayList<Message> copyMessageArray = new ArrayList<>(); 
			for(Message message : childDiagram.getMessageArray()) {
						Message copyMessage = (Message) message.clone();
						//ͨ������Ϣ��Ӻ�׺���ı�Id
						copyMessage.setId(copyMessage.getId()+idCount);
						copyMessageArray.add(copyMessage);
			}
			diagramData.getMessageArray().addAll(ref.getIndex(), copyMessageArray);			
		}

		//�޸�������fragment ����һ�ݵ���ͼ��
		if(childDiagram.getFragmentArray().size()>0)
		{
			ArrayList<Fragment> copyFragmentArray = new ArrayList<>();
			for(Fragment fragment : childDiagram.getFragmentArray()) {//������е����Ƭ��
				//���Ƭ������ѭ��Ƕ��������Ҫ�ж������и��ơ�
				Fragment copyFragment = (Fragment) fragment.clone(); 
				changeAllIdByIdCount(diagramData,copyFragment,idCount);
				copyFragmentArray.add(copyFragment);	
			}
			diagramData.getFragmentArray().addAll(ref.getFindex(), copyFragmentArray);
		}
	}
	
	/**
	 * �������Ƭ��id,����id,�����µ���Ϣid,�Լ���Ƕ����Щid���Ӻ�׺��ʹId��ö�һ�޶���
	 * @param copyFragment ���Ƶõ��������Ƭ��
	 * @param postFix id��׺
	 */
	private static void changeAllIdByIdCount(DiagramsData diagramData,Fragment frag,int postFix)
	{
		frag.setId(frag.getId()+postFix);
		for(Operand oper:frag.getOperands())
		{
			oper.setId(oper.getId()+postFix);
			for(Message mess:oper.getMessages())
			{
				mess.setId(mess.getId()+postFix);
				
				mess.setFragmentId(frag.getId());
				mess.setOperandId(oper.getId());
				
				//Ȼ��Ѱ��˳��ͼ�����Ƭ���������Ƭ�����ŵ���ͬmessage��ͬ��ϣ��ַ����Ϣ���޸�fragmentId;
				for(Message m:diagramData.getMessageArray())
				{
					if(m.getId().equals(mess.getId()))
					{
						m.setFragmentId(mess.getFragmentId());
						m.setOperandId(oper.getId());
						break;
					}
				}
			}
			if(oper.isHasFragment()==true||oper.getFragmentIDs().size()>0||oper.getFragments().size()>0)
			{
				oper.getFragmentIDs().clear();
				for(Fragment f:oper.getFragments())
				{
					changeAllIdByIdCount(diagramData,f,postFix);
					oper.getFragmentIDs().add(f.getId());
				}
			}
						
		}
	}
	
	/**
	 * �������µ�����Ƭ�δ������ͼ��ȸ��ƽ���ǰ������
	 * @param diagramData ��ǰͼ
	 * @param frag ��ǰ���Ƭ��
	 * @param oper  ��ǰ�������õĲ���
	 * @param ref ��ǰ���е�����
	 */
	private static void fixFragmentWithRef(DiagramsData diagramData,Fragment frag,Operand oper,REF ref)
	{
		//System.out.println("��������������������������ȸ��ƣ�"+oper.getRef().getRefName());
		/*
		 * �ҵ�ref��Ӧ����ͼ
		 * RMessage���뵽��ǰ˳��ͼ��messages��
		 * RMessages��û�������Ƭ���е�messes���뵽��ǰoper�£�ͬʱ�޸���Ϣ���Ƭ�α�־Ϊtrue;
		 * RMessages��frags���뵽��ǰ������
		 * ������������Mindex��Findex;
		 */
		idCount++;
		for(DiagramsData childDiagram :umlAllDiagramData)
		{
			if(ref.getRefName().equals(childDiagram.getName())) //�ҵ���Ӧ��˳��ͼ
			{	
				
					//���lifeline
					for(LifeLine lifeline : childDiagram.getLifelineArray())
					{
						if (!diagramData.getLifelineArray().contains(lifeline)) {//��Ӹ�ͼû�е�lifeline
							diagramData.getLifelineArray().add(lifeline);
						}
					}
					//���ݵ�ǰ���ø��º��Mindex���� ��ȸ��� ����Ϣ
					if(childDiagram.getMessageArray().size()>0)
					{
						//��ֵ������Ϣ
						ArrayList<Message> copyMessageArray = new ArrayList<>(); 
						ArrayList<Message> copyMessesInOper = new ArrayList<>(); 
						for(Message message : childDiagram.getMessageArray()) 
						{
							Message copyMessage = (Message) message.clone();
							copyMessage.setId(copyMessage.getId()+idCount);
							copyMessageArray.add(copyMessage);
							if(!copyMessage.isInFragment())
							{
								copyMessage.setInFragment(true);
								copyMessage.setFragmentId(frag.getId());
								copyMessage.setFragType(frag.getType());
								copyMessage.setOperandId(oper.getId());
								copyMessesInOper.add(copyMessage);
							}		
						}
						diagramData.getMessageArray().addAll(ref.getIndex(), copyMessageArray);	
						//���ƽ���ǰ����
						if(copyMessesInOper.size()>0)
						{
							oper.getMessages().addAll(copyMessesInOper);
						}
					}

					//����ͼ���������Ƭ�μ�����ȸ�ֵ������ǰ����
					if(childDiagram.getFragmentArray().size()>0)
					{
						//System.out.println(childDiagram.getFragmentArray().size());
						//System.out.println("δ����ʱ���������Ƭ��������"+oper.getFragments().size());
						ArrayList<Fragment> copyFragmentArray = new ArrayList<Fragment>();
						ArrayList<String> copyFragmentIds=new ArrayList<String>();
						for(Fragment fragment : childDiagram.getFragmentArray()) {//������е����Ƭ��
							//���Ƭ������ѭ��Ƕ��������Ҫ�ж������и��ơ�
							Fragment copyFragment = (Fragment) fragment.clone(); 
							changeAllIdByIdCount(diagramData,copyFragment,idCount);
							copyFragmentIds.add(copyFragment.getId());
							copyFragmentArray.add(copyFragment);	
						}
						//System.out.println(copyFragmentArray.size());
						oper.getFragmentIDs().addAll(copyFragmentIds);
						oper.getFragments().addAll(copyFragmentArray);
						if(copyFragmentArray.size()>0)
						{
							oper.setHasFragment(true);
						}
						//System.out.println("������������Ƭ��������"+oper.getFragments().size());
					} 
					
					for(REF refi:diagramData.getTempRefArray())
					{
						refi.setIndex(refi.getIndex() + childDiagram.getMessageArray().size());
						if(!ref.isInFragFlag())
						{
							refi.setFindex(refi.getFindex()+childDiagram.getFragmentArray().size());	
						}
					}
					break;				
				
			}			
		}
	}
	
	
	private static DiagramsData findDiagramByName(String refName) {
		
		for(DiagramsData diagramsData :umlAllDiagramData)
		{
			if (diagramsData.getName().equals(refName))//���������id
				return diagramsData;
		}
		return null;
	}
	/**
	 * ����Ϻ����ͼ������Ϣָ����ض����ͷ���ȸ��ƵĶ���message
	 * ������ϢId�������Ƭ���е���Ϣ  ���� ��ͼ��Ϣ�����ж�Ӧ����Ϣ
	 * @param diagramData
	 */
	public static void redirectMessage(DiagramsData diagramData)
	{
		for(Fragment frag:diagramData.getFragmentArray())
		{
			redirectMessageInFragment(frag,diagramData.getMessageArray());
		}		
	}
	private static void redirectMessageInFragment(Fragment frag,ArrayList<Message> messes)
	{
		for(Operand oper:frag.getOperands())
		{
			for(Message m:oper.getMessages())
			{
				for(int i=0;i<messes.size();i++)
				{
					if(m.getId().equals(messes.get(i).getId()))
					{
						//m.setId(m.getId()+"��ID");
						messes.remove(i);
						messes.add(i, m);
					}
				}
				
			}
			if(oper.getFragments().size()>0)
			{
				for(Fragment f:oper.getFragments())
				{
					redirectMessageInFragment(f,messes);
				}
			}				
		}
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

	public ArrayList<REF> getUmlREF() {
		return umlREF;
	}

	public void setUmlREF(ArrayList<REF> umlREF) {
		this.umlREF = umlREF;
	}

	public  DiagramsData getCompleteSD() {
		return completeSD;
	}

	public  void setCompleteSD(DiagramsData completeSD) {
		this.completeSD = completeSD;
	}
	
	
	
}

