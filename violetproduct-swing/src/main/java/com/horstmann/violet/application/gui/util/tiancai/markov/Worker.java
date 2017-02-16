package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Worker {
	private List<UseCase> useCases=new ArrayList<UseCase>();
	private List<Tmc> seqTmcs=new ArrayList<Tmc>();//��������Markov
	private List<Tmc> ucTmcs=new ArrayList<Tmc>(); //��������Markov��
	private Tmc f_Tmc;//��������Markov chain
	public Worker(){}
	
	public List<UseCase> getUseCases() {
		return useCases;
	}

	public void setUseCases(List<UseCase> useCases) {
		this.useCases = useCases;
	}


	public Tmc getF_Tmc() {
		return f_Tmc;
	}


	public void setF_Tmc(Tmc f_Tmc) {
		this.f_Tmc = f_Tmc;
	}


	public void transInitial(String xmlFileName) throws InvalidTagException
	{
		InfoRetrieve api=new InfoRetrieve(xmlFileName);
		api.initialize();
		api.getUseCasesInfo(); //��װ��������Ϣ
		System.out.println("useCases Informations:");
		api.print_useCases(); //�����Ϣ
		this.useCases=api.getUseCases();
	}
	public void transVerify() throws InvalidTagException
	{
		ConWork work=new ConWork(this.useCases);
		work.Initialize();
		@SuppressWarnings("rawtypes")
		List verifyReList= work.ConsistencyCheck(); //������������T/F����֤�������
		if(verifyReList!=null)
		{
			if((Boolean)verifyReList.get(0)==true)
			{
				System.out.println("��UMLģ������һ����Ҫ�󣡿�ת��Ϊ��Ӧ��Markov��ʹ��ģ�ͣ�");
			}
			else
			{
				System.out.println("UMLģ�Ͳ�����һ����Ҫ��");
			}
			System.out.println((String)verifyReList.get(1));//�����֤�����ʾ
		}
	}
	
	public void transToMarckov()
	{
		Translation translation=new Translation();
		f_Tmc=translation.UMLTranslationToMarkovChain(useCases); //�õ������Markov
		
		seqTmcs=translation.getSeqTmcList();//�õ������𲽵��ӵ�Markov
		ucTmcs=translation.getTmcs();    //�õ�����Markov
		
		f_Tmc.printTmc();
	}
	public void probabilityTest()
	{
		double proSum=0;
		for(State state:f_Tmc.getStates())
		{
			proSum=0;
			for(Transition tr:f_Tmc.getTransitions())
			{
				if(tr.getFrom().equals(state))
				{
					proSum+=tr.getTransFlag().getProb();
				}
			}
			if(proSum!=1.0)
			{
				System.out.println(state.getName());
			}
		}
	}
	public void writeMarkov(String mcXMLFileName) throws IOException
	{
		String McName="MarkovChainModel";
		int count=1;
		for(Tmc tmc:seqTmcs)
		{
			String fileName=mcXMLFileName+"Seq_"+McName+count+".xml";
			Write.writeMarkov2XML(tmc, fileName);
			count++; 
		}
		count=1;
		for(Tmc tmc:ucTmcs) //��������
		{
			String fileName=mcXMLFileName+"UC_"+McName+count+".xml";
			Write.writeMarkov2XML(tmc,fileName);
			count++;
		}
		
		Write.writeMarkov2XML(f_Tmc,(mcXMLFileName+"Sofeware_"+McName+".xml"));   
	}
	
	
}
