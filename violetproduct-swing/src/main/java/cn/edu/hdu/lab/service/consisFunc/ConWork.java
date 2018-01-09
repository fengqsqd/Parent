package cn.edu.hdu.lab.service.consisFunc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.edu.hdu.lab.dao.condao.ConGraph;
import cn.edu.hdu.lab.dao.condao.ConSD;
import cn.edu.hdu.lab.dao.condao.ConUseCase;
import cn.edu.hdu.lab.dao.condao.ConVertex;
import cn.edu.hdu.lab.dao.tmc.Tmc;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.parserEA.InvalidTagException;
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
		conUseCases=new ConInfoParse().conInfoParse(useCases);
	}
	public List<Object> ConsistencyCheck()
	{
		List<Object> verifyReList=new ArrayList<Object>();
		
	/*ȷ����;
		1��������������Ψһ;EAID_OP000000_7032_40ad_908E_AA82C86BFE7C
		2�������Ȼִ�н���;
		3.����ǰ�����������������г�����ǰ������
		 * */	
		//�����Ȼִ�н���
		
		ConGraph tempGraph=new ConGraph(conUseCases);
		
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
			if(Sprob < 0.998 || Sprob > 1.002)
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
		
		//�жϴ������ʼ״̬��ÿ���ڵ㶼����ִ��·������ִ�е�ÿ���ڵ�;
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
		
		
		/*
		 * �ɴ�����֤ͨ����ǰ�������
		 */
		verifyReList.add(true);
		verifyReList.add("һ������֤ͨ����");
		verifyReList.add(graph.getAdjoinMatrix());
		
		/*��ȡ·�� �������
		��һ������ÿ��������ǰ��·�����ӿ�ʼ״̬��������·����
		�ڶ�����ÿ�������ĺ���·����������������״̬��·��*/
		
		verifyReList.add(obtainPrePath(graph));
		verifyReList.add(obtainPostPath(graph));
		
		//�������;��
		return verifyReList;
		
	}
	public List<ConUseCase> getConUseCases() {
		return conUseCases;
	}
	
	/**
	 * ��ȡ�ӿ�ʼ��㵽������·��
	 * @param graph ͼ
	 * @return ����������·��
	 */
	private Map<String,List<String>> obtainPrePath(ConGraph graph)
	{
		System.out.println("����������������������������������ȡ·��������������������������");
		
		for(ConVertex conVertex : graph.getVertexList())
		{
			System.out.println("++++++: " + conVertex.getName());
		}
		
		Map<String,List<String>> prePathMap=new HashMap<String,List<String>>();
		for(int i=1;i<graph.getNum()-1;i++)
		{
			List<String> prePath=new ArrayList<String>();
			boolean[] visited=new boolean[graph.getNum()];//Ĭ��Ϊfalse 
			
			
			List<Integer> pathNums=new ArrayList<Integer>();
			pathNums.add(0);
			pathNums.addAll(searchPath(graph,visited,0,i));
			for(int k=0;k<pathNums.size();k++)
			{
				System.out.println("uc :  " + graph.getVertexList().get(pathNums.get(k)).getName());
				prePath.add(graph.getVertexList().get(pathNums.get(k)).getName());
			}
			prePathMap.put(graph.getVertexList().get(i).getName(),prePath);
		}
		
		/*System.out.println(prePathMap.size());
		for(String en:prePathMap.keySet())
		{
			System.out.println(prePathMap.get(en).toString());
		}*/
		
		
		return prePathMap;
	}
	
	/**
	 * ��ȡ������㵽����״̬��·��
	 * @param graph ͼ
	 * @return ����������·��
	 */
	private Map<String,List<String>> obtainPostPath(ConGraph graph)
	{
		Map<String,List<String>> postPathMap=new HashMap<String,List<String>>();
		
		for(int i=1;i<graph.getNum()-1;i++)
		{
			List<String> Path=new ArrayList<String>();
		    boolean[] visited=new boolean[graph.getNum()];  //Ĭ��ȫ��ΪFalse
		    for(int j=0;j<visited.length;j++)
			{
				visited[j]=false;
			}
			List<Integer> pathNums=new ArrayList<Integer>();
			pathNums.add(i);
			pathNums.addAll(searchPath(graph,visited,i,graph.getNum()-1));
			for(int k=0;k<pathNums.size();k++)
			{
				Path.add(graph.getVertexList().get(pathNums.get(k)).getName());
			}
			postPathMap.put(graph.getVertexList().get(i).getName(),Path);
		}
		/*System.out.println(postPathMap.size());
		for(String en:postPathMap.keySet())
		{
			System.out.println(postPathMap.get(en).toString());
		}*/
		return postPathMap;
	}
	/**
	 * ����·��
	 * @param graph ͼ
	 * @param i ��ʼ���
	 * @param j �������
	 * @return �������֮���·��
	 */
	private List<Integer> searchPath(ConGraph graph, boolean[] visited,int start ,int end)
	{
		
		List<Integer> pathNums=new ArrayList<Integer>();			
		for(int i=0;i<graph.getNum();i++)
		{
			if(graph.getAdjoinMatrix()[start][i]==1&&visited[i]!=true)
			{
				pathNums.add(i);
				visited[i]=true;
				
				List<Integer> a=searchPath(graph,visited,i,end);
				if(a.contains(end))
				{
					pathNums.addAll(a);
					return pathNums;
				}
			}			
		}		
		return pathNums;
	}
}
