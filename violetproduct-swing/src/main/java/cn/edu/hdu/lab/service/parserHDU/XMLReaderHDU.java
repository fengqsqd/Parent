package cn.edu.hdu.lab.service.parserHDU;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.uml.Diagram;
import cn.edu.hdu.lab.dao.uml.DiagramsData;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.LifeLine;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Node;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.REF;
import cn.edu.hdu.lab.dao.uml.SD;
import cn.edu.hdu.lab.dao.uml.SDRectangle;
import cn.edu.hdu.lab.dao.uml.Stimulate;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.parserEA.FixFragmentTool;
import cn.edu.hdu.lab.service.parserEA.UMLReader;

/**
 * ��ȡƽ̨UMLģ��
 * @author Terence
 *
 */
public class XMLReaderHDU {
	private String xmlFile; //����ͼ·����
	private String fileName;//UMLģ�����ڰ���
	private Element root;  //XML�ļ����ڵ�
	
	
	//private ArrayList<Behavior> behaviors;
	
	private ArrayList<Diagram> diagrams=new ArrayList<Diagram>();	
	private ArrayList<LifeLine> lifeLines=new ArrayList<LifeLine>();
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	
	private ArrayList<REF> umlREF = new ArrayList<REF>(); //������Ϣ
	static ArrayList<DiagramsData> umlAllDiagramData = new ArrayList<DiagramsData>();	
	private static int idCount=0;
	
	public XMLReaderHDU(){}

	/**
	 * 
	 * @param xmlFile ����ͼ·������
	 * @throws Exception
	 */
	public XMLReaderHDU(String xmlFile) throws Exception {
		super();
		this.xmlFile = xmlFile;
		this.fileName=StaticConfig.umlPathPrefixHDU;
		try
		{
			SAXReader reader=new SAXReader();
			Document dom=reader.read(xmlFile);
			root=dom.getRootElement();
		}
		catch(Exception e)
		{
			throw new Exception("�ļ���ȡ�쳣����鿴�ļ��Ƿ���ڣ���·�����Ƿ���ȷ��\n����ԭ��"+e);
		}
	}

	public List<UseCase> parser() throws Throwable
	{
		
		List<UseCase> useCases=new ArrayList<UseCase>(); 
		useCases=readUCInformation();
		readSequencesInformation(useCases);
		return useCases;
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @return ������������
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	private List<UseCase> readUCInformation() throws Throwable
	{
		List<UseCase> useCases=new ArrayList<UseCase>(); 
		if(root==null)
		{
			throw new Exception("Exception:The document's root is null");
			
		}
		else
		{
			List<Element> ucElementsList=new ArrayList<Element>();
			ucElementsList.addAll(root.element("nodes").elements("UseCaseNode"));
			
			
			for(Iterator<Element> it=ucElementsList.iterator();it.hasNext();)
			{
				UseCase uc=new UseCase();
				
				//�趨 ������ID��name��preCondition��probability;
				Element e=it.next();
				//�������ID Name ǰ������
				uc.setUseCaseID(e.attributeValue("id"));
				uc.setUseCaseName(e.element("name").elementText("text"));
				
				//�������Լ������
				Element constraintsElement=e.element("useConstraint").element("constraints");
				if(constraintsElement.hasContent())
				{
					List<Element> constraintElementList=constraintsElement.elements("com.horstmann.violet.product.diagram.abstracts.property.Usecaseconstraint");
					for(Element e1:constraintElementList)
					{
						if(e1.hasContent())
						{
							//�������ǰ������
							//if(e1.element("type").getText().equals("pre-condition")&&
									if(e1.element("name").getText().equals("preCondition"))
							{
								uc.setPreCondition(e1.elementText("content"));
								break;
							}
							else
							{
								throw new Exception("����Լ��������ȡ�쳣������ԭ��Լ�����ͻ�Լ�����ƴ���");
							}
						}
					}										
				}
				
				//������ ��װ������Ϣ�����޸�
				//ƽ̨����ͼ��û��Diagrams�����Դ���Ϊ��
				if(e.element("sceneConstraint").hasContent()
						&&e.element("sceneConstraint").element("constraints").hasContent()
						&&e.element("sceneConstraint").element("constraints").element("com.horstmann.violet.product.diagram.abstracts.property.SequenceConstraint").hasContent())
				{
					List<Element> constraintsElementList=e.element("sceneConstraint").element("constraints").elements();
					
					List<Element> seqList=e.element("sceneConstraint").element("sequenceName").elements("string");
					if(seqList!=null&&seqList.size()>0)
					{
						ArrayList<SD> sdSets=new ArrayList<SD>();
						for(Element tempE:seqList)
						{
							if(tempE.getName().equals("string"))
							{
								SD sd=new SD();
								sd.setId(e.element("sceneConstraint").element("sequenceName").attributeValue("id")+"_"+tempE.getText().trim());
								sd.setName(tempE.getText().trim());
								if(constraintsElementList.size()>0)
								{
									for(Element conE:constraintsElementList)
									{
										if(conE.hasContent()
												&&conE.element("sequenceName").getText()!=null
												&&conE.element("name").getText()!=null
												&&conE.element("content").getText()!=null)
										{
											if(sd.getName().equals(conE.element("sequenceName").getText()))
											{
												if(conE.element("name").getText().equals("postCondition"))
												{
													sd.setPostSD(conE.element("content").getText());
												}
												if(conE.element("name").getText().equals("probability"))
												{
													sd.setProb(Double.parseDouble(conE.element("content").getText()));
												}
											}
										}
									}
								}
								sdSets.add(sd);
							}						
						}
						uc.setSdSets(sdSets);						
					}				
				}
//				System.out.print("������"+uc.getUseCaseName());
//				System.out.println("----����:"+uc.getSdSets().get(0).getName());				
      			useCases.add(uc);	
				
			}
			return useCases;			
		}
	}
	
	/**
	 * ����������Ϣ��Ѱ�Ҷ�Ӧ������������Ӧ����XML�ļ����������װ
	 * @param useCases
	 * @throws Throwable
	 */
	private void readSequencesInformation(List<UseCase> useCases) throws Throwable 
	{
		System.out.println("=================================��������===============================");
		for(UseCase uc:useCases)
		{
			File file=new File(fileName);
			File[] tempFileList=file.listFiles(); //��ȡ�ð��µ������ļ�			
			
			for(File f:tempFileList)
			{
				if(f.isFile()&&f.getName().contains("seq.violet.xml"))
				{
					
					String sequenceFileName=fileName+"\\"+f.getName();
					//System.out.println(sequenceFileName);
					DiagramsData dd=parserSequence2DiagramData(sequenceFileName);
					//System.out.println(f.getName().substring(0, f.getName().indexOf(".seq.violet.xml")));
					dd.setName(f.getName().substring(0, f.getName().indexOf(".seq.violet.xml")));
					umlAllDiagramData.add(dd);
				}
			}
			
			/*for(DiagramsData diagramData : umlAllDiagramData) 
			{
				System.out.println("\n˳��ͼ���ƣ�"+diagramData.getName());
					for(LifeLine l:diagramData.getLifelineArray())
					{
						l.print_LifeLine();
					}
					for(Message m:diagramData.getMessageArray())
					{
						m.print_Message();
					}
					for(Fragment frag:diagramData.getFragmentArray())
					{
						frag.print_Fragment();
					}
					System.out.println("������Ϣ��");
					for(REF r:diagramData.getRefArray())
					{
						System.out.println(r.toString());
					}	
					System.out.println("*****����������Ϣ��");
					for(REF r:diagramData.getTempRefArray())
					{
						System.out.println(r.toString());
					}
					
			}*/
			
			//��ȸ��ƣ����˳��ͼ������ͼǶ�׽���ͼ����			
			for(SD sd:uc.getSdSets())
			{
				
				assembleInfo2DiffDiagram(sd);
				
				System.out.println("*****************��������Ϣ**************");				
				sd.print_SDSet();			
				
			}
		}
	}
	
	/**
	 * �������е�˳��ͼ��Ӧ�ļ���
	 * @param sequenceFileName ˳��ͼ��Ӧ���ļ�ȫ��
	 * @return ÿ��˳��ͼ��Ӧ�Ĵ洢�ṹ
	 * @throws Exception 
	 */
	private DiagramsData parserSequence2DiagramData(String sequenceFileName) throws Exception
	{
		DiagramsData dd=new DiagramsData();
		//ʶ���˱���ļ���ֻ��ʶ��xml�ļ�
		SAXReader reader=new SAXReader();
		Document dom=reader.read(sequenceFileName);
		Element sdRoot=dom.getRootElement();
		if(sdRoot==null)
		{
			throw new Exception("�ļ������쳣.");
		}
		else
		{ 
			//umlAllDiagramData			
			
			if(sdRoot.getName().contains("SequenceDiagramGraph"))
			{
				if(sdRoot.element("nodes").hasContent())
				{
					//��ȡ�����߼���
					dd.setLifelineArray(retrieveLifeLine(sdRoot));
					
					//��ȡ��㼯��
					dd.setNodes(retrieveNodes(dd.getLifelineArray()));
					
					//��ȡ��Ϣ����
					dd.setMessageArray(retrieveMessages(sdRoot,dd));	
					
					//��ȡ����Ƭ������
					dd.getRefArray().addAll(retrieveRefs(sdRoot));
					dd.getTempRefArray().addAll(dd.getRefArray()); //������������
					
					//��ȡ����(���Ƭ��֮���Ƕ�ף����ú����Ƭ��֮���Ƕ��)������Ƭ�μ���
					dd.setFragmentArray(retrieveFragments(sdRoot,dd));	
				}
			}
			return dd;
		}
	}
		
		/**
		 * 
		 * @param root :SequenceDiagramGraph���
		 * @return �������߼���
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<LifeLine> retrieveLifeLine(Element root) throws Exception
		{
			List<Element> lifeElementList=root.element("nodes").elements("LifelineNode");
			ArrayList<LifeLine> lifeLineList=new ArrayList<LifeLine>();
			if(lifeElementList.size()>0)
			{				
				for(Iterator<Element> it=lifeElementList.iterator();it.hasNext();)
				{
					Element tempE=it.next();
					LifeLine lifeLine=new LifeLine(tempE.attributeValue("id"),
							tempE.element("name").elementText("text"));					
					
					//��װ���������ϰ����Ľ��
					ArrayList<Node> ownedNodeList=retrieveLifeLineNodes(tempE);					
					lifeLine.setNodes(ownedNodeList);			
					lifeLineList.add(lifeLine);
				}
			}
			else
			{
				throw new Exception("��������Ϣ�쳣");
			}
			return lifeLineList;
		}
		
		/**
		 * ��ȡ�������������н��
		 * @param root :�����߽��
		 * @return �����н�㼯��
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<Node> retrieveLifeLineNodes(Element lifeLineRoot) 
		{
			ArrayList<Node> nodesList=new ArrayList<Node>();
			String lifeLineID=lifeLineRoot.attributeValue("id");
			String lifeLineName=lifeLineRoot.element("name").elementText("text");
			if(lifeLineRoot.element("children").hasContent())				
			{
				List<Element> nodeElementList=lifeLineRoot.element("children").elements("ActivationBarNode");
				for(Element e:nodeElementList)
				{
					Node node=new Node(e.attributeValue("id"),lifeLineID) ;
					node.setLifeLineName(lifeLineName);
					nodesList.add(node);
					if(e.element("children").hasContent())
					{
						nodesList.addAll(dfsSearchNodes(e,lifeLineID,lifeLineName));
						
					}
				}				
			}
			return nodesList;
		}
		private ArrayList<Node> dfsSearchNodes(Element e,String lifeLineID,String lifeLineName)
		{
			ArrayList<Node> nodesList=new ArrayList<Node>();
			@SuppressWarnings("unchecked")
			List<Element> nodeElementList=e.element("children").elements("ActivationBarNode");
			for(Element e2:nodeElementList)
			{
				Node node=new Node(e2.attributeValue("id"),lifeLineID) ;
				node.setLifeLineName(lifeLineName);
				nodesList.add(node);
				if(e2.element("children").hasContent())
				{
					nodesList.addAll(dfsSearchNodes(e2,lifeLineID,lifeLineName));					
				}
			}
			return nodesList;
		}
		/**
		 * ���������������µĽ�㣬��ȡ���н��
		 * @param lifeLineList
		 * @return
		 */
		private ArrayList<Node> retrieveNodes(ArrayList<LifeLine> lifeLineList) 
		{
			ArrayList<Node> nodesList=new ArrayList<Node>();
			if(lifeLineList.size()>0)
			{
				for(LifeLine lifeLine:lifeLineList)
				{
					if(lifeLine.getNodes().size()>0)
					{
						for(Node node:lifeLine.getNodes())
						{
							nodesList.add(node);
						}
					}
				}
			}
			return nodesList;			
		}
		
		/**
		 * ��ȡ��Ϣ
		 * @param root
		 * @param sd
		 * @return ÿ��˳��ͼ�е���Ϣ
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<Message> retrieveMessages(Element root,DiagramsData sd) throws Exception
		{			
			//��װ��Ϣ
			ArrayList<Element> messageElementList=(ArrayList<Element>) root.element("edges").elements("CallEdge");
			ArrayList<Element> returnMessageElementList=(ArrayList<Element>) root.element("edges").elements("ReturnEdge");
			ArrayList<Message> messageList=new ArrayList<Message>();
			
			for(Element e:messageElementList)
			{
				
				Message message=new Message();
				message.setId(e.elementText("ID"));
				message.setName(e.elementText("message"));				
				message.setSenderID(searchLifeLine(e.element("start"),sd).getId());
				
				message.setReceiverID(searchLifeLine(e.element("end"),sd).getId());
				
				message.setSender(searchLifeLine(e.element("start"),sd).getName());
				message.setReceiver(searchLifeLine(e.element("end"),sd).getName());
								
				message.setReturnValue(e.elementText("assign"));
				message.setReturnValueType(e.elementText("returnvalue"));
				message.setNoteID(e.elementText("ID"));
				
				//�������ݡ���
				Stimulate stimulate=new Stimulate();
				
				if(e.element("message").getText()!=null
						&&e.element("parameters").getText()!=null)
				{
					//���������б�
					String parametersTypeStr=handleParameterStr(message.getName().trim());
					stimulate.setParameterTypeList(SerachParametersType(parametersTypeStr));
//					System.out.println(message.getName()+"----"+stimulate.getParameterTypeList().size());
					//���������б�
					stimulate.setParameterNameList(SerachParametersType(e.element("parameters").getText()+","));
					
					//����������
					if(e.element("condition").getText()!=null)
					{
						setStimulate(stimulate,e.element("condition").getText());
					}
					else
					{
						throw new Exception("����û�ж����򣬶�Ӧ��ϢΪ��"+message.getName());
					}
				}
				message.setStimulate(stimulate);
				if(e.element("timeconstraint").hasContent())
				{
					message.setFromTimeConstraint(e.element("timeconstraint").getText());
				}
				
				message.setPointY(Double.parseDouble(e.element("startLocation").attributeValue("y")));
				
				//message.setProb(sd.getProb());
				/*if(e.equals(messageElementList.get(messageElementList.size()-1)))
				{
					message.setLast(true);
				}*/
				
				//��Ϣ�Ƿ������Ƭ���У���Ϣ�������Ƭ�����ͣ��������Ƭ��ID���������Ƭ�β���ID���������Ƭ�εı��
				
				//��Ϣ���Բ�ȫ
				//������������
				//ִ��ʱ��				
				messageList.add(message);
			}
			for(Element e:returnMessageElementList)
			{
				
				Message message=new Message();
				message.setId(e.elementText("ID"));
				message.setName(e.elementText("message"));				
				message.setSenderID(searchLifeLine(e.element("start"),sd).getId());
				
				message.setReceiverID(searchLifeLine(e.element("end"),sd).getId());
				
				message.setSender(searchLifeLine(e.element("start"),sd).getName());
				message.setReceiver(searchLifeLine(e.element("end"),sd).getName());
								
				message.setReturnValue(e.elementText("assign"));
				message.setReturnValueType(e.elementText("returnvalue"));
				message.setNoteID(e.elementText("ID"));
				
				//�������ݡ���
				Stimulate stimulate=new Stimulate();
				
				if(e.element("message").getText()!=null
						&&e.element("parameters").getText()!=null)
				{
					//���������б�
					String parametersTypeStr=handleParameterStr(message.getName().trim());
					stimulate.setParameterTypeList(SerachParametersType(parametersTypeStr));
//					System.out.println(message.getName()+"----"+stimulate.getParameterTypeList().size());
					//���������б�
					stimulate.setParameterNameList(SerachParametersType(e.element("parameters").getText()+","));
					
					//����������
					if(e.element("condition").getText()!=null)
					{
						setStimulate(stimulate,e.element("condition").getText());
					}
					else
					{
						throw new Exception("����û�ж����򣬶�Ӧ��ϢΪ��"+message.getName());
					}
				}
				message.setStimulate(stimulate);
				if(e.element("timeconstraint").hasContent())
				{
					message.setFromTimeConstraint(e.element("timeconstraint").getText());
				}
				message.setPointY(Double.parseDouble(e.element("startLocation").attributeValue("y")));
				
				//message.setProb(sd.getProb());
				if(e.equals(messageElementList.get(messageElementList.size()-1)))
				{
					message.setLast(true);
				}
				
				//��Ϣ�Ƿ������Ƭ���У���Ϣ�������Ƭ�����ͣ��������Ƭ��ID���������Ƭ�β���ID���������Ƭ�εı��
				
				//��Ϣ���Բ�ȫ
				//������������
				//ִ��ʱ��				
				messageList.add(message);
			}
			//����Ϣ�����������һ������
			FixTool.sortMesses(messageList);
			return messageList;
		}
		
		/**
		 * ��ȡ˳��ͼ�����е�����Ƭ��
		 * @param sdRoot
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<REF> retrieveRefs(Element sdRoot) throws Exception
		{
			ArrayList<REF> refs=new ArrayList<REF>();
			List<Element> refNodes=new ArrayList<Element>();
			refNodes.addAll(sdRoot.element("nodes").elements("RefNode"));
			for(Element e:refNodes)
			{
				REF ref=new REF();
				ref.setRefID(e.elementText("ID"));
				ref.setRefName(e.element("text").elementText("text"));
				ref.setRectangle(retrieveFragmentSDRectangle(e));
				refs.add(ref);
			}
			return refs;
			
		}
		/**
		 * ��ȡ˳��ͼ��һ�����Ƭ�Σ���һ�����Ƭ���Ѿ�Ƕ����ϣ�
		 * @param root��SequenceDiagramGraph���ڵ�
		 * @param sd������
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private ArrayList<Fragment> retrieveFragments(Element root,DiagramsData dd)
		{
			ArrayList<Fragment> firstFragmentsList=new ArrayList<Fragment>();
			List<Element> firstLevelFragmentsList=new ArrayList<Element>();
			firstLevelFragmentsList.addAll(root.element("nodes").elements("CombinedFragment"));
			for(Element e:firstLevelFragmentsList)
			{
				//���Ƭ��id,name,type
				Fragment fragment=new Fragment();
				firstFragmentsList.add(fragment);
				
				fragment.Set(e.elementText("ID"),e.elementText("fragmentType").toLowerCase());
				fragment.setName(e.elementText("name"));
				
				//�������ϣ���װ����
				ArrayList<Operand> operandList=new ArrayList<Operand>();
				fragment.setOperands(operandList);//��������ǰ����������,��Ϊ����������ã����ǿռ�洢ֵ��
				
				ArrayList<Element> operandElementList= new ArrayList<Element>();
				operandElementList.addAll(e.element("fragmentParts").elements("com.horstmann.violet.product.diagram.abstracts.property.FragmentPart"));
				for(Element operandE:operandElementList)
				{
					Operand operand =new Operand();
					operandList.add(operand);
					operand.setCondition(operandE.elementText("conditionText"));
					operand.setId(operandE.attributeValue("id"));					
					operand.setRectangle(retrieveOperandSDRectangle(operandE));
					
				}
				//���Ƭ�������ȡ
				fragment.setRectangle(retrieveFragmentSDRectangle(e));
				
			}
			//�����Ƭ�η�װ��Ϣ�����������Ƭ��Ƕ�׹�ϵ�� ����Ƭ�������Ƭ�β����Ĺ�ϵ
			FixTool.fixFragmentsOfOneDiagram(firstFragmentsList,dd);
			return firstFragmentsList;
		}
		
		
		/**
		 * ����Լ�������ַ���
		 * @param name
		 * @return
		 */
		private String handleParameterStr(String name)
		{
			name=name.trim();
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
				for(int i=0;i<name.length();i++)
				{
					if(name.charAt(i)=='(')
					{
						k=i;
						break;
					}
					
				}
				String parametersTypeStr=name.substring(k+1,name.length()-1)+",";	
				return parametersTypeStr;
			}
			return "";
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
		@SuppressWarnings("null")
		private void setStimulate(Stimulate stimulate,String str)
		{
			if(str==null||str.trim().equals(""))
			{
				//System.out.println("null");
				return;
			}
			
//			System.out.println(stimulate);
//			System.out.println("***********"+str.trim());
			String[] strs=str.trim().replaceAll("\r|\n","").split("#");
			for(String s:strs)
			{
				if(!s.equals(""))
				{
//					System.out.println("*"+s);
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
		private List<String> SerachSubString(String str)
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
		 * Ѱ�Ҹýڵ���������������
		 * @param e 
		 * @param sd ������Ӧ��˳��ͼ
		 * @return 
		 */
		private LifeLine searchLifeLine(Element e,DiagramsData sd)
		{
			
			String ref=e.attributeValue("reference");
			for(LifeLine l: sd.getLifelineArray())
			{
				if(l.getNodes().size()>0)
				{
					for(Node node:l.getNodes())
					{
						if(ref.equals(node.getId()))
						{
						   return l;	
						}
					}
				}				
			}		
			return null;
		}
		
		
	/**
	 * ������Ϣ�ĸ���ID����ȡ˳��ͼ�ж�Ӧ����Ϣ
	 * @param e :
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private ArrayList<Message>searchMessagesByID(Element e,DiagramsData sd)
	{
		ArrayList<Message> messageList=new ArrayList<Message>();
		ArrayList<String> messageIDs=new ArrayList<String>();
		List<Element> messageIDElementList=e.elements("string");		
		//���ݴ�IDѰ�Ҷ�Ӧ��Message
		for(Element strIDElement:messageIDElementList)
		{
			messageIDs.add(strIDElement.getText());
		}
		for(String str:messageIDs)
		{
//			System.out.println("��������Ϣ������"+sd.getMessages().size());
			for(Message mess:sd.getMessageArray())
			{
				if(str.equals(mess.getNoteID()))
				{
					messageList.add(mess);
				}
			}
		}
		return messageList;
	}
	
	/**
	 * ����װ�����Ƭ���ڵ�
	 * ���Ƭ�μ�����Ϣ������㼯�����id��
	 * @param firstFragmentsList ��������������Ƭ�μ���
	 */
	@SuppressWarnings("unused")
	private void resetFragmentsInfo(ArrayList<Fragment> firstFragmentsList)
	{
		
		//���ã���װ���Ƭ��
		
		for(Fragment fragment:firstFragmentsList)
		{
			//�ݹ�Ѱ��ÿ�����Ƭ�β����°������Ƭ�εĴ���������count=1�����Ƭ�η�װ���ò����¡�				
			for(Operand oper:fragment.getOperands())
			{
				if(!oper.isHasFragment()) continue;		
				
				//������ǰ������Ƕ�ױ�־���飬��ͳ�ư������Ƭ��ID���ֵĴ���
				FlagDao[] fd=new FlagDao[oper.getFragmentIDs().size()];
				for(String str:oper.getFragmentIDs())
				{
					fd[oper.getFragmentIDs().indexOf(str)].setCount(1);
					fd[oper.getFragmentIDs().indexOf(str)].setID(str);
				}
				for(String strID:oper.getFragmentIDs())
				{
					// �ҵ�������ÿ��ID��Ӧ���Ƭ���µĲ����е�ID���ϣ�ͳ�ƴ���
					countFragments(strID,fd,firstFragmentsList);
				}
				
				//������IDͳ�ƴ���Ϊ1�ĵ����Ƭ�η�װ�ڸò�����;
				for(int i=0;i<fd.length;i++)
				{
				    if(fd[i].getCount()==1)
				    {
				    	for(Fragment f:firstFragmentsList)
				    	{
				    		if(fd[i].getID().equals(f.getId()))
				    		{
				    		    oper.addFragment(f);
				    		    f.setEnFlag(true);
				    			break;
				    		}
				    	}
				    }
				}
			}		
		}		
		//���˵��ǳ���ֱϵ���Ƭ�β��֡�	
		for(Fragment fragment:firstFragmentsList)
		{
			if(fragment.isEnFlag())
			{
				firstFragmentsList.remove(fragment);
			}
		}
	}
	/**
	 * ͳ�Ƶ�ǰ���Ƭ���²����ж�Ӧ���Ƭ��ID������+1��
	 * @param fd��ͳ�Ʊ��
	 * @param firstFragmentsList���������Ƭ��
	 */
	private void countFragments(String fragID,FlagDao[] fd,ArrayList<Fragment> firstFragmentsList)
	{
		for(Fragment f:firstFragmentsList)
		{
			if(fragID.equals(f.getId()))
			{
				for(Operand oper:f.getOperands())
				{
					if(!oper.isHasFragment()) continue;	
					for(String strID:oper.getFragmentIDs())
					{
						for(int i=0;i<fd.length;i++)
						{
							if(strID.equals(fd[i].getID()))
							{
								fd[i].setCount(fd[i].getCount()+1);
								break;
							}
						}
					}	
					
				}
				break;				
			}
		}
		
	}
	
	/**
	 * ��װ��Ϣ����˳��ͼ��ͼǶ�׽���ͼ����
	 * @param sd
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	private static void assembleInfo2DiffDiagram(SD sd) throws Exception
	{
		for(DiagramsData diagramData : umlAllDiagramData) 
		{
			if(diagramData.getName().equals(sd.getName()))
			{

				//System.out.println("\n��ͼ��"+diagramData.getName());
				DFSDiagramByREF(diagramData);
				diagramData.getMessageArray().get(diagramData.getMessageArray().size()-1).setLast(true);
				//��Ϣָ���ض���
				redirectMessage(diagramData);
				//β��Ϣ��־����
				diagramData.getMessageArray().get(diagramData.getMessageArray().size()-1).setLast(true);
				
				//tagLastMessage(diagramData)
				//�ռ���Ϣ������ִ������
				UMLReader.searchOperConditionOfMess(diagramData);
				//������˳��ͼ�ı�������
				sd.setLifeLines(diagramData.getLifelineArray());
				sd.setMessages(diagramData.getMessageArray());
				
				sd.setFragments(diagramData.getFragmentArray());
				sd.setNodes(diagramData.getNodes());
				break;
			}
		}
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
			
			//����������˳��ͼ�е��������λ��(Mindex��Findex)
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
				//����Mindex��������Ƭ�����������ͼ
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
	private static DiagramsData findDiagramByName(String refName) {
		
		for(DiagramsData diagramsData :umlAllDiagramData)
		{
			if (diagramsData.getName().equals(refName))//���������id
				return diagramsData;
		}
		return null;
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
				//System.out.println("��ȸ��ƣ�"+op.getRef().getRefName());
				DiagramsData childDiagram = findDiagramByName(op.getRef().getRefName());
				if(childDiagram.getTempRefArray().size()>0)
				{
					DFSDiagramByREF(childDiagram);//�ص�����������ͼ
				}
				fixFragmentWithRef(diagramData,f,op,op.getRef());
				diagramData.getTempRefArray().remove(op.getRef());
				op.setRef(null);//�������Ƭ�ε����ã����ڵݹ�ķ����ж�
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
			if(ref.getRefName().equals(childDiagram.getName()))
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
	
	/*private static void searchOperConditionOfMess(DiagramsData d)
	{
		for(Message message:d.getMessageArray())
		{
			if(message.isInFragment())
			{
				for(Fragment frag:d.getFragmentArray())
				{
					List<String> operConditions=retrieveOperConditions(frag,message);
					if(operConditions!=null&&operConditions.size()>0)
					{
						String condition=new String("");
						for(String str:operConditions)
						{
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
		}
		for(Message message:d.getMessageArray())
		{
			if(message.isInFragment()||(message.getFragmentId()!=null||message.getFragmentId()!=""))
			{
				
			}
		}		
	}*/

	/**
	 * ��ȡ���Ƭ������
	 * @param e
	 * @return
	 */
	private SDRectangle retrieveFragmentSDRectangle(Element e)
	{
		SDRectangle rec=new SDRectangle();
		rec.setLeft(Double.parseDouble(e.element("location").attributeValue("x")));
		rec.setTop(Double.parseDouble(e.element("location").attributeValue("y")));
		rec.setRight(rec.getLeft()+Double.parseDouble(e.element("width").getText()));
		rec.setBottom(rec.getTop()+Double.parseDouble(e.element("height").getText()));		
		return rec;		
	}
	
	/**
	 * ��ȡ����������
	 * @return
	 */
	private SDRectangle retrieveOperandSDRectangle(Element operE)
	{
		SDRectangle rec=new SDRectangle();
		rec.setLeft(Double.parseDouble(operE.element("borderline").elementText("x1")));
		rec.setTop(Double.parseDouble(operE.element("borderline").elementText("y1")));
		rec.setRight(Double.parseDouble(operE.element("borderline").elementText("x2")));
		rec.setBottom(Double.parseDouble(operE.element("size").getText())+Double.parseDouble(operE.element("borderline").elementText("y2")));		
		return rec;
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
