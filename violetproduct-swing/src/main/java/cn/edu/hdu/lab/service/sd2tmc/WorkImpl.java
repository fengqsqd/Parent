package cn.edu.hdu.lab.service.sd2tmc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.horstmann.violet.application.gui.DisplayForm;
import com.horstmann.violet.application.gui.MainFrame;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUC;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.dao.tmc.State;
import cn.edu.hdu.lab.dao.tmc.Tmc;
import cn.edu.hdu.lab.dao.tmc.Transition;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.SD;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.consisFunc.ConWork;
import cn.edu.hdu.lab.service.interfaces.Work;
import cn.edu.hdu.lab.service.parserEA.InfoRetrieve;
import cn.edu.hdu.lab.service.parserEA.InvalidTagException;
import cn.edu.hdu.lab.service.parserHDU.XMLReaderHDU;
import cn.edu.hdu.lab.service.probability.MergeProbability;
import cn.edu.hdu.lab.service.write.Write;

public class WorkImpl implements Work  {
	private List<UseCase> useCases=new ArrayList<UseCase>();
	private List<Tmc> seqTmcs=new ArrayList<Tmc>();//��������Markov
	private List<Tmc> ucTmcs=new ArrayList<Tmc>(); //��������Markov��
	private Tmc f_Tmc;//��������Markov chain
	public WorkImpl(){}
	
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

	//��ʼ��������UMLģ�͵�XML�ļ���Ϣ
	public void transInitial(String xmlFileName) throws Exception
	{
		InfoRetrieve api=new InfoRetrieve(xmlFileName);
		api.initialize();
		api.getUseCasesInfo(); //��װ��������Ϣ
		this.useCases=api.getUseCases();
		
		
	}
	public void transInitialHDU(String xmlFileName) throws Throwable
	{
		XMLReaderHDU reader=new XMLReaderHDU(xmlFileName);
		try {
			this.useCases=reader.parser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{			
			for(UseCase uc:useCases)
			{
				uc.print_useCase();
			}
		}
	}
	/**
	 * ���ݽ�����Ϣ�ṩ����ִ��˳���ϵ(������)
	 * Map<String, List<InterfaceUCRelation>> ����ִ��˳���ϵ
	 * String:��������
	 * List<InterfaceUCRelation>���������ƶ�Ӧ������ִ��˳���ϵ
	 * 
	 */
	@Override
	public Map<String, List<InterfaceUCRelation>> provideUCRelation() {
		
		Map<String,List<InterfaceUCRelation>> UCRelationMap=new HashMap<String,List<InterfaceUCRelation>>();
		
		//������ʼ״̬�ĺ���������ϵ
		List<InterfaceUCRelation> initialIURList=new ArrayList<InterfaceUCRelation>();
		
		InterfaceUC initialStateUC=new InterfaceUC();
		initialStateUC.setID("0");
		initialStateUC.setName("InitialState");//��ʼ״̬	
		
		InterfaceUC endStateUC=new InterfaceUC();
		endStateUC.setID("-1");
		endStateUC.setName("EndState");//����״̬
		System.out.println("*****");
		for(UseCase uc:useCases)
		{
			if(uc.getPreCondition()!=null&&StaticConfig.initialCondition.trim().equals(uc.getPreCondition().trim()))
			{
				InterfaceUC interfaceUC=new InterfaceUC();//��������
				interfaceUC.setID(uc.getUseCaseID());
				interfaceUC.setName(uc.getUseCaseName());	
				
				InterfaceUCRelation IUR=new InterfaceUCRelation();//����ִ��˳���ϵ
				IUR.setStartUC(initialStateUC);
				IUR.setEndUC(interfaceUC);
				IUR.setUCRelation(IUR.getStartUC().getName()+"-"+IUR.getEndUC().getName());
				IUR.setUCRelProb(0);
				initialIURList.add(IUR);
				System.out.println("*****");
			}
			
		}
		//System.out.println("*****"+initialIURList.get(0).getStartUC().getName()+"****"+initialIURList);
		UCRelationMap.put(initialIURList.get(0).getStartUC().getName(), initialIURList);
		
		//System.out.println(UCRelationMap.size()+"**"+UCRelationMap.keySet().toString());
		
		//��������������ÿ�����������ݳ�����������������ǰ����������������ִ��˳���ϵ
		//System.out.println(useCases.size());
		
		for(UseCase uc:useCases)
		{
			List<InterfaceUCRelation> IURList=new ArrayList<InterfaceUCRelation>();
			
			InterfaceUC startUC=new InterfaceUC();//��ʼ����
			startUC.setID(uc.getUseCaseID());
			startUC.setName(uc.getUseCaseName());	
			
			if(uc.getSdSets()!=null)
			{
				for(SD sd:uc.getSdSets())
				{
					for(UseCase postUC:useCases)
					{
						if(postUC.getPreCondition()!=null&&postUC.getPreCondition().trim()!=""&&(sd.getPostSD().equals(postUC.getPreCondition())))
						{
							InterfaceUC endUC=new InterfaceUC();//��������
							endUC.setID(postUC.getUseCaseID());
							endUC.setName(postUC.getUseCaseName());
							
							InterfaceUCRelation IUR=new InterfaceUCRelation();//����ִ��˳���ϵ
							IUR.setStartUC(startUC);
							IUR.setEndUC(endUC);
							IUR.setUCRelation(IUR.getStartUC().getName()+"-"+IUR.getEndUC().getName());
							IUR.setUCRelProb(0);
							
							IURList.add(IUR);
						}
						
					}
					if(sd.getPostSD().trim().equals(StaticConfig.endCondition))
					{
						InterfaceUCRelation IUR=new InterfaceUCRelation();//����ִ��˳���ϵ
						IUR.setStartUC(startUC);
						IUR.setEndUC(endStateUC);
						
						IUR.setUCRelation(IUR.getStartUC().getName()+"-"+IUR.getEndUC().getName());
						IUR.setUCRelProb(0);
						
						IURList.add(IUR);						
					}
				}
			}
			
			if(IURList.size()>0)//���˵�û�к��������Ŀռ��ϣ�size=0��
			{
				UCRelationMap.put(IURList.get(0).getStartUC().getName(), IURList);
			}			
		}
		
		//���˵��ظ��Ĺ�ϵ
		for(Entry<String, List<InterfaceUCRelation>> en:UCRelationMap.entrySet())
		{
			//Stirng:����ִ�й�ϵ��InterfaceUCRelation��Ӧ�Ĺ�ϵ�ṹ
			Map<String,InterfaceUCRelation> list=new HashMap<String,InterfaceUCRelation>();
			for(InterfaceUCRelation iur:en.getValue())
			{
				if(list.containsKey(iur.getUCRelation()))
				{
					continue;					
				}
				else
				{
					list.put(iur.getUCRelation(), iur);					
				}
			}
			//��ʣ���ظ��Ĺ�ϵ�����ں���ɾ��
			List<InterfaceUCRelation> tList=new ArrayList<InterfaceUCRelation>();
			for(InterfaceUCRelation iur:en.getValue())
			{
				for(Entry<String,InterfaceUCRelation> e:list.entrySet())
				{
					int f=0;
					if(e.getKey().equals(iur.getUCRelation()))
					{
						if(e.getValue()==iur)
						{
							continue;
						}
						else
						{
							f=1;							
							tList.add(iur);
								
						}
					}
					if(f==1)
					{
						continue;
					}
				}
			}	
			for(InterfaceUCRelation iur:tList)
			{
				en.getValue().remove(iur);
			}
			tList.clear();
			list.clear();
		}
		
		return UCRelationMap;
	}
	
	//���������µ�ͬԴ������Ϣ(��������Ϣ)
	@Override
	public List<InterfaceIsogenySD> provideIsogencySD()
	{
		
		List<InterfaceIsogenySD> IIDList=new ArrayList<InterfaceIsogenySD>();
		for(UseCase uc:useCases)
		{
			InterfaceIsogenySD IID=new InterfaceIsogenySD();
			IID.setUcID(uc.getUseCaseID());
			IID.setUcName(uc.getUseCaseName());
			List<InterfaceSD> ISDList=new ArrayList<InterfaceSD>();
			if(uc.getSdSets()!=null)
			{
				for(SD sd:uc.getSdSets())
				{
					InterfaceSD ISD=new InterfaceSD();
					ISD.setID(sd.getId());
					ISD.setName(sd.getName());
					ISD.setpostCondition(sd.getPostSD());
					ISDList.add(ISD);
				}
			}
			
			IID.setISDList(ISDList);
			IIDList.add(IID);
		}
		
		return IIDList;
	}
	
	@Override
	public List<Object> calculateProb(List<double[][]> proMatrixList) {
		List<Object> resultList=new ArrayList<Object>();
		if(proMatrixList.size()>0)
		{
			if(proMatrixList.get(0).length==1)
			{
				resultList.add("size=1");
				double[] proArray={0};				
				resultList.add(proArray);
				return resultList;
			}
			else
				if(proMatrixList.get(0).length==2)
				{
					DecimalFormat f=new DecimalFormat("0.000");
					resultList.add("size=2");
					double[] proArray=new double[2];
					for(int k=1;k<proMatrixList.size();k++)
					{
						for(int i=0;i<2;i++)
						{
							for(int j=0;j<2;j++)
							{
								proMatrixList.get(0)[i][j]=proMatrixList.get(k)[i][j];
							}
						}
						
					}
					for(int i=0;i<2;i++)
					{
						for(int j=0;j<2;j++)
						{
							proMatrixList.get(0)[i][j]/=2;
						}
					}
					proArray[0]=Double.valueOf(f.format(1-1/(1+proMatrixList.get(0)[0][1])));
					proArray[1]=Double.valueOf(f.format(1/(1+proMatrixList.get(0)[0][1])));
					resultList.add(proArray);
					return resultList;
				}
				else
				{
					MergeProbability mp=new MergeProbability(proMatrixList);
					resultList=mp.mergeProbability();
					return resultList;
				}
		}
		else
		{
			resultList.add("���ʾ���������������������");
			return resultList;
		}
		
	}
	
	/*
	 * (non-Javadoc)���ʸ�ֵ
	 * @see cn.edu.hdu.lab.service.interfaces.Work#assignmentPro(java.util.List)
	 */
	@Override 
	public void assignmentPro(List<InterfaceIsogenySD> IISDList)
	{
		System.out.println("**********��ÿ�������µĳ���������ֵ**********");
		for(InterfaceIsogenySD IISD:IISDList)
		{
			for(UseCase uc:useCases)
			{
				if(IISD.getUcID().equals(uc.getUseCaseID()))
				{
					for(InterfaceSD ISD:IISD.getISDList())
					{
						for(SD sd:uc.getSdSets())
						{
							if(ISD.getID().equals(sd.getId()))
							{
								//System.out.println(ISD.getPro());
								sd.setProb(ISD.getPro());
								//System.out.println(sd.getName()+"-"+sd.getProb());
							}
						}
					}
					break;
				}
			}
		}

		for(UseCase uc:useCases)
		{
			for(SD sd:uc.getSdSets())
			{
				for(Message mess:sd.getMessages())
				{
					mess.setProb(sd.getProb());
				}
					
			}
		}
	
	}
	
	/*
	 * (non-Javadoc)һ������֤
	 * @see cn.edu.hdu.lab.service.interfaces.Work#transVerify()
	 */
	public List<Object> transVerify() throws InvalidTagException
	{
		ConWork work=new ConWork(this.useCases);
		work.Initialize();
		List<Object> verifyReList= work.ConsistencyCheck(); 
		if(verifyReList!=null)
		{
			if((boolean)verifyReList.get(0)==true)
			{
				System.out.println("��UMLģ������һ����Ҫ�󣡿�ת��Ϊ��Ӧ��Markov��ʹ��ģ�ͣ�");
				DisplayForm.mainFrame.getOutputinformation().geTextArea().append("��UMLģ������һ����Ҫ�󣡿�ת��Ϊ��Ӧ��Markov��ʹ��ģ�ͣ�" + "\n");
				int length1 = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length(); 
				DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length1);
			}
			else
			{
				System.out.println("UMLģ�Ͳ�����һ����Ҫ��");
				DisplayForm.mainFrame.getOutputinformation().geTextArea().append("UMLģ�Ͳ�����һ����Ҫ��" + "\n");
				int length1 = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length(); 
				DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length1);
			}
			System.out.println((String)verifyReList.get(1));//�����֤�����ʾ
		}
		return verifyReList;
	}
	@Override
	public void transToMarckov(Map<String, List<InterfaceUCRelation>> UCRMap,List<String> seqNames,List<String> ucNames) throws Exception
	{
		Translation translation=new Translation();
		/*for(UseCase uc:useCases)
		{
			uc.print_useCase();
		}*/
		f_Tmc=translation.UMLTranslationToMarkovChain(useCases,UCRMap,seqNames,ucNames); //�õ������Markov		
		seqTmcs=translation.getSeqTmcList();//�õ������𲽵��ӵ�Markov
		ucTmcs=translation.getTmcs();    //�õ�����Markov
		
		f_Tmc.printTmc();
	}
	
	//�鿴����ģ�����н���Ǩ�Ƹ���֮���Ƿ�Ϊһ
	public void probabilityAndReachableTest() throws Exception
	{
		double proSum=0;
		for(State state:f_Tmc.getStates())
		{
			if(state.getNotation()!=null&&state.getNotation().contains(StaticConfig.endCondition))
			{
				break;
			}
			proSum=0;
			for(Transition tr:f_Tmc.getTransitions())
			{
				if(tr.getFrom().equals(state))
				{
					proSum+=tr.getTransFlag().getProb();
				}
			}
			if(proSum<0.999 || proSum>1.001)
			{
				System.out.println("proSum: " + proSum);
				throw new Exception("��㣺"+state.getName()+" ��Ǩ�Ƹ��ʲ�Ϊ1");
//				System.out.println("��㣺"+state.getName()+" ��Ǩ�Ƹ��ʲ�Ϊ1");
			}
		}
		
		//�ɴ���
		for(State state:f_Tmc.getStates())
		{
			if(state.getLabel()!=null&&state.getLabel().contains("Exit"))
			{
				break;
			}
			boolean result=DFSSearchFinishState(state,f_Tmc);
			if(!result)
			{
				throw new Exception("�쳣��״̬���ڶ�·���ɴ�����;�쳣��㣺"+state.getName()+"��");
			}
			
		}
	}
	private static boolean  DFSSearchFinishState(State state,Tmc f_Tmc)
	{
		boolean f=false;
		
		for(Transition tr:f_Tmc.getTransitions())
		{
			if(tr.getFrom().equals(state))
			{
				if(tr.getNotation()==null||
						(tr.getNotation()!=null&&!tr.getNotation().contains("backLoop")))
				{
					if(tr.getTo().getNotation()!=null&&tr.getTo().getNotation().contains(StaticConfig.endCondition))
					{
						return true;
					}
					else
					{
						f= DFSSearchFinishState(tr.getTo(),f_Tmc);
						if(f==true)
						{
							break;
						}
					}				
				}
			}
			
			
		}
		return f;
	}
	
	public void writeMarkov(String mcXMLFileName,MainFrame mainFrame,List<String> seqNames,List<String> ucNames) throws Exception
	{
//		String McName="MarkovChainModel";
//		int count=1;
//		for(Tmc tmc:seqTmcs)
//		{
//			String[] names = tmc.getNames().split(";");
//			int length = names.length - 1;
//			String seq = names[length].trim();
//			String fileName=seq+".xml";
//			Write.writeMarkov2XML(tmc, fileName,mainFrame);
//			
//			seqNames.add(fileName);
//		}
//		count=1;
//		for(Tmc tmc:ucTmcs) //��������
//		{
//			String fileName=tmc.getNames()+".xml";
//			Write.writeMarkov2XML(tmc,fileName,mainFrame);
//			
//			ucNames.add(fileName);
//		}
		
		Write.writeMarkov2XML(f_Tmc,mcXMLFileName+".xml",mainFrame);   
	}
	
}
