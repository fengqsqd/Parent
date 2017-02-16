package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.util.ArrayList;

/*
 * ��Ϣ��ȡ��
 * ���ļ����ƺ�XML����Ľ���������ȡUMLģ��
 * ���ȣ��������UseCase��Ϣ��
 * ���������������Ϊ��ϢBehavior����ı����Ϣ������Extension��չ�¶�Ӧ��DiagramƬ��
 * Ȼ�����Diagram����ı����Ϣ��ȷ��˳��ͼ��Ӧ����Ϣ�����ͣ����ƣ�ID��
 * Ȼ�����˳��ͼ����ϢѰ�Ҷ�Ӧ��˳��ͼ�ļ�
 * 
 *��Σ��ҵ�˳��ͼ�ļ��󣬻�ȡ˳��ͼ�������ϸ��Ϣ
 *�������������������������������д���д��
 */
public class InfoRetrieve {
	private String xmlFile;
	private XMLReaderEA parser; //��������������������ļ���xmlFile;
	
	private ArrayList<UseCase> useCases;
	private ArrayList<Diagram> diagrams;
	private SDSet sdset=new SDSet();
	private ArrayList<LifeLine> lifeLines;
	private ArrayList<Node> nodes;
	

	public InfoRetrieve(String xmlFile)
	{
		this.xmlFile=xmlFile;
	}
	public void initialize() throws InvalidTagException
	{
		this.parser=new XMLReaderEA(this.xmlFile);
		
	}
	/*
	 * Ѱ�����������������Behaviors�ҵ���Ӧ��diagrams
	 * ����Diagrams ��ȡ ��Ӧ��˳��ͼSDSets
	 * ��ÿ�������� ��װDiagram��Ӧ�ĳ���sdSets
	 */
	public void getUseCasesInfo() throws InvalidTagException
	{
		this.parser.retrieveuseCases();//��ȡ����
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
			
			ArrayList<SDSet> sdSets=new ArrayList<SDSet>();
			for(Diagram diagram:useCase.getDiagrams())
			{
				
				String sdFileName="ResetMapXML\\"+diagram.getDiagramName()+".xml";			
				XMLReaderEA sdParser=new XMLReaderEA(sdFileName);
				
				SDSet sd=new SDSet();
				//id,name,lifeLines,messages,fragments
				sd.setId(diagram.getDiagramID());
				sd.setName(diagram.getDiagramName());
				//��ʱ�Ѿ���ȡ�˰������������ڵ�����������
			    sd.setLifeLines(sdParser.retrieveLifeLines()); 
			   
			    //�������˳���ܱ仯���������
			    
			    //λ������Ƭ��ͬʱ��ȡ
			    sdParser.retrieveFragments();//		   
			    sd.setFragments(sdParser.getFragments());
			    sd.setNodes(sdParser.getNodes());
			    sd.setProb(diagram.getProb());
			    sd.setMessages(sdParser.retrieveMessages(sd.getProb()));
			    
			    sd.setProb(diagram.getProb());
			    sd.setPostSD(diagram.getNotation());

				sdSets.add(sd);
			}
			useCase.setSdSets(sdSets);
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

	public XMLReaderEA getParser() {
		return parser;
	}

	public void setParser(XMLReaderEA parser) {
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

	public SDSet getSdset() {
		return sdset;
	}

	public void setSdset(SDSet sdset) {
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
