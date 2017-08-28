package cn.edu.hdu.lab.service.parserEA;

import java.util.ArrayList;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.uml.Behavior;
import cn.edu.hdu.lab.dao.uml.Diagram;
import cn.edu.hdu.lab.dao.uml.LifeLine;
import cn.edu.hdu.lab.dao.uml.Node;
import cn.edu.hdu.lab.dao.uml.SD;
import cn.edu.hdu.lab.dao.uml.UseCase;

/*
 * ��Ϣ��ȡ��
 * ���ļ����ƺ�XML����Ľ���������ȡUMLģ��
 * ���ȣ��������UseCase��Ϣ��
 * ���������������Ϊ��ϢBehavior����ı����Ϣ������Extension��չ�¶�Ӧ��DiagramƬ��
 * Ȼ�����Diagram����ı����Ϣ��ȷ��˳��ͼ��Ӧ����Ϣ�����ͣ����ƣ�ID��
 * Ȼ�����˳��ͼ����ϢѰ�Ҷ�Ӧ��˳��ͼ�ļ�
 * 
 *��Σ��ҵ�˳��ͼ�ļ��󣬻�ȡ˳��ͼ�������ϸ��Ϣ
 */
public class InfoRetrieve {
	private String xmlFile;
	private UMLReader parser; //��������������������ļ���xmlFile;
	
	private ArrayList<UseCase> useCases;
	private ArrayList<Diagram> diagrams;
	private SD sdset=new SD();
	private ArrayList<LifeLine> lifeLines;
	private ArrayList<Node> nodes;
	

	public InfoRetrieve(String xmlFile)
	{
		this.xmlFile=xmlFile;
	}
	public void initialize() throws InvalidTagException
	{
		this.parser=new UMLReader(this.xmlFile);
		
	}
	/*
	 * Ѱ�����������������Behaviors�ҵ���Ӧ��diagrams
	 * ����Diagrams ��ȡ ��Ӧ��˳��ͼSDSets
	 * ��ÿ�������� ��װDiagram��Ӧ�ĳ���sdSets
	 */
	public void getUseCasesInfo() throws Exception
	{
		//��ȡ����
		this.parser.retrieveuseCases();
		this.setUseCases(this.parser.getUseCases());
		this.setDiagrams(this.parser.retrieveuseDiagrams());
		
		
		for(UseCase useCase:useCases)
		{
			if(useCase.getBehaviors()==null)
			{
				continue;
			}
			ArrayList<Diagram> ownDiagrams=new ArrayList<Diagram>();
			for(Behavior behavior: useCase.getBehaviors())
			{
				for(Diagram diagram:diagrams)
				{
					if(behavior.getBehaviorID().equals(diagram.getBehaviorID()))
					{
						ownDiagrams.add(diagram);
					}
				}
			}
			useCase.setDiagrams(ownDiagrams);
			
			ArrayList<SD> sdSets=new ArrayList<SD>();
			/*for(Diagram diagram:useCase.getDiagrams())
			{
				
				String sdFileName=StaticConfig.umlPathPrefix+diagram.getDiagramName()+".xml";			
				XMLReaderEA sdParser=new XMLReaderEA(sdFileName);
				
				SD sd=new SD();
				
				//id,name,lifeLines,messages,fragments
				sd.setId(diagram.getDiagramID());
				sd.setName(diagram.getDiagramName());
				//��ʱ�Ѿ���ȡ�˰������������ڵ�����������
				
			    sd.setLifeLines(sdParser.retrieveLifeLines()); 
			   
			    //�������˳���ܱ仯���������
			    
			    //λ������Ƭ��ͬʱ��ȡ
			    sdParser.retrieveFragments();	   
			    sd.setFragments(sdParser.getFragments());
			    sd.setNodes(sdParser.getNodes());
			    sd.setProb(diagram.getProb());
			    sd.setMessages(sdParser.retrieveMessages(sd.getProb()));
			    
			    sd.setProb(diagram.getProb());
			    sd.setPostSD(diagram.getNotation());

				sdSets.add(sd);
			}*/
			
			for(Diagram diagram:useCase.getDiagrams())
			{
				
				String sdFileName=StaticConfig.umlPathPrefix+diagram.getDiagramName()+".xml";			
				UMLReader sdParser=new UMLReader(sdFileName,diagram.getDiagramName());
				
				SD sd=new SD();
				sdSets.add(sd);
				
				//id,name,lifeLines,messages,fragments
				sd.setId(diagram.getDiagramID());
				sd.setName(diagram.getDiagramName());
				
				/*System.out.println(sd.getId());
				System.out.println(sd.getName());*/
				
				//׼���׶Σ���ȡ˳��ͼ��������˳��ͼ��Ϣ  �Լ�  ��˳��ͼ������ͼԪ�����Ƭ�Σ����ã�������Ϣ����ID��������Ϣ
				sdParser.retrieveAllDiagramElements();
				/*
				{
					System.out.println("\n������ͼ����Ϣ:");
					for(DiagramsData d:sdParser.umlAllDiagramData)
					{
						System.out.println(d.getName()+"----"+d.getDiagramID());
						System.out.println(d.toString());
					}
					System.out.println("*********************************************");
				}*/
				
				//��ʱ�Ѿ���ȡ�˰������������ڵ�����������
			    sd.setLifeLines(sdParser.retrieveLifeLines());
			   /* System.out.println("\n��������Ϣ��");
			    for(LifeLine l:sd.getLifeLines())
			    {
			    	System.out.println(l.toString());
			    }
			    System.out.println("*********************************************");*/
			    
			   /* System.out.println("\n������Ϣ����������"+FixFragmentTool.rectangleById.size());
				Iterator iter = FixFragmentTool.rectangleById.entrySet().iterator();
				while (iter.hasNext()) 
				{
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					System.out.println(key+"---"+val);
				}
				System.out.println("*********************************************");*/
			    //�������˳���ܱ仯���������
			    
			    //λ������Ƭ��ͬʱ��ȡ
			    //��ʱû�и���sd
			    sdParser.retrieveFragments();	   
			   
			    //sd.setFragments(sdParser.getFragments());//�˲����Ƭ���ǲ��д��ڵ�
			    
			    sd.setNodes(sdParser.getNodes());
			    /*
			    System.out.println("\n�ڵ���Ϣ��");
			    System.out.println(sd.getNodes().size());*/
			    
			    
			    sd.setProb(diagram.getProb());			    
			    sdParser.retrieveMessages(sd.getProb());
			    //sd.setMessages(sdParser.retrieveMessages(sd.getProb()));			    
			    //���������Ϣ			    
			    sdParser.assembleInfo2DiffDiagram();
			    sd.setMessages(sdParser.getCompleteSD().getMessageArray());
			    sd.getMessages().get(sd.getMessages().size()-1).setLast(true);
			    sd.setId(sdParser.getCompleteSD().getDiagramID());
			    sd.setLifeLines(sdParser.getCompleteSD().getLifelineArray());
			    sd.setFragments(sdParser.getCompleteSD().getFragmentArray());
				//����
				/* System.out.println("\n���Ƭ����Ϣ��");	
				for(Fragment frag:sdParser.getFragments())
				{
					System.out.println("���ͣ�"+frag.getType()+"---"+frag.getName()+"----"+frag.getId());
					System.out.println("���Ƭ������"+frag.getRectangle().toString());
					System.out.println("���������꣺");
					for(Operand oper:frag.getOperands())
					{
						System.out.println(oper.getRectangle().toString());
					}
				}
				System.out.println("*********************************************");*/
			    
			    sd.setProb(diagram.getProb());
			    sd.setPostSD(diagram.getNotation());			    
			}
			useCase.setSdSets(sdSets);
		}
		for(UseCase useCase:useCases)
		{
			if(useCase.getDiagrams().size()==0)
			{
				useCases.remove(useCase);
			}
		}
		
	}
	
	//�������������Ϣ(����name,id,behaviors,diagrams)
	public void print_useCases()
	{
		for(UseCase useCase:this.useCases)
		{
			useCase.print_useCase();
		}
	}
	
	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public UMLReader getParser() {
		return parser;
	}

	public void UMLReader(UMLReader parser) {
		this.parser = parser;
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

	public SD getSdset() {
		return sdset;
	}

	public void setSdset(SD sdset) {
		this.sdset = sdset;
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
	

}
