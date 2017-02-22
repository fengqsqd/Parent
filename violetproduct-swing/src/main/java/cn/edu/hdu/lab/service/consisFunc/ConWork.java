package cn.edu.hdu.lab.service.consisFunc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.hdu.lab.dao.condao.ConGraph;
import cn.edu.hdu.lab.dao.condao.ConSD;
import cn.edu.hdu.lab.dao.condao.ConUseCase;
import cn.edu.hdu.lab.dao.tmc.Tmc;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.parser.InvalidTagException;
//import org.omg.PortableServer.POAManagerPackage.State;
public class ConWork {
	private List<ConUseCase> conUseCases;
	private ConSD conSD;
	private List<UseCase> useCases;
	private Tmc f_Tmc;
	//��ȡ����ͼ��Ϣ;
	public ConWork(){}
	public ConWork(List<UseCase> useCases)
	{
		this.useCases=useCases;
	}
	public void Initialize() throws InvalidTagException
	{
		conUseCases=(new ConInfoParse()).conInfoParse(useCases);
	}
	public List<Object> ConsistencyCheck()
	{
		List<Object> verifyReList=new ArrayList<Object>();
		
	/*ȷ����;
		1��������������Ψһ;
		2�������Ȼִ�н���;
		3.����ǰ�����������������г�����ǰ������
		 * */	
		//�����Ȼִ�н���
		ConGraph tempGraph=new ConGraph(conUseCases);
		System.out.println("�ڽӾ���ڵ�����"+tempGraph.getNum());
		tempGraph.printGraph();
		boolean isOver=false;
		for(int i=0;i<tempGraph.getNum();i++)
		{
			if(tempGraph.getAdjoinMatrix()[i][tempGraph.getNum()-1]==1)
			{
				isOver=true;
				break;
			}
		}
		if(isOver==false)
		{
			verifyReList.add(false);
			verifyReList.add("'����ض�ִ�н���' ��֤��ͨ����");
			return verifyReList;
		}
		
	//һ����һ�ԣ�
		//1.����ͼ���ʹ�һ����֤
		for(Iterator<ConUseCase> it=conUseCases.iterator();it.hasNext();)
		{
			ConUseCase conUseCase=it.next();
			double Sprob=0;
			for(Iterator<ConSD> sdIt=conUseCase.getConSDset().iterator();sdIt.hasNext();)
			{
				ConSD conSD=sdIt.next();
				Sprob+=conSD.getProb();
			}
			if(Sprob!=1.0)
			{
				verifyReList.add(false);
				verifyReList.add("'����ͼ���ʹ�һ' ��֤��ͨ����");
				return verifyReList;
			}
		}
		
	    /*
	     * �ɴ���
	     * ͼ�ı����㷨
	     *---////��������||�Ͻ�˹����
	     */
		ConGraph graph=new ConGraph(conUseCases);
		//graph.printGraph();
		/*
		 * ��ÿ���ڵ�(���˳�ʼ�ڵ��β�ڵ�)
		 * ��֤�Ƿ����ǰ��������������ʼ״ִ̬�е��ýڵ�
		 * ��֤�Ƿ���ں��������ڵ����ִ�е���������ڵ�
		 */
		//��֤1-5�������ڵ��Ƿ���ǰ�ýڵ�ͺ��ýڵ㣬ͬʱӵ����ͨ����֤������True;
		for(int i=1;i<graph.getNum()-1;i++)
		{
			boolean isAccess=false;
			boolean accessPreAdjoinVertex=false;
			boolean accessPostAdjoinVertex=false;
			for(int j=0;j<graph.getNum();j++)
			{
				if(graph.getAdjoinMatrix()[j][i]==1)
				{
					accessPreAdjoinVertex=true;
					break;
				}
			}
			for(int j=0;j<graph.getNum();j++)
			{
				if(graph.getAdjoinMatrix()[i][j]==1)
				{
					accessPostAdjoinVertex=true;
					break;
				}
			}
			if(accessPreAdjoinVertex==true&&accessPostAdjoinVertex==true)
			{
				isAccess=true;
			}
			if(isAccess==false)
			{
				verifyReList.add(false);
				verifyReList.add("'UMLģ�Ϳɴ���' ��֤��ͨ����");
				return verifyReList;
			}
		}
		
		//�жϴ������ʼ״̬��ÿ���ڵ㶼����ִ��·������ִ�е�ÿ���ڵ㣻-
		for(int i=1;i<graph.getNum();i++)
		{
			if(graph.isHavePath(0, i)==false) 
			{
				verifyReList.add(false);
				verifyReList.add("'UMLģ�Ϳɴ���' ��֤��ͨ����");
				return verifyReList;
			}
		}
		//�жϴӸýڵ㿪ʼ�����ִ�н����ڵ����·����
		for(int i=1;i<graph.getNum();i++)
		{
			if(graph.isHavePath(i, graph.getNum()-1)==false) 
			{
				verifyReList.add(false);
				verifyReList.add("'UMLģ�Ϳɴ���' ��֤��ͨ����");
				return verifyReList;
			}
		}
		
		verifyReList.add(true);
		verifyReList.add("һ������֤ͨ����");
		verifyReList.add(graph.getAdjoinMatrix());
		return verifyReList;
		
	}
	public List<ConUseCase> getConUseCases() {
		return conUseCases;
	}

}
