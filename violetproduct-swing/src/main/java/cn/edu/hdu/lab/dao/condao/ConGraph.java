package cn.edu.hdu.lab.dao.condao;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hdu.lab.config.StaticConfig;

public class ConGraph {
	private static int num; //�ڵ���;
	private static List<ConVertex> vertexList=new ArrayList<ConVertex>(); //�����,�洢�ڵ㣨��Ӧ����������Ϣ
	private static int adjoinMatrix[][]; //�ڽӾ��󣬱�ʾ����֮��Ĺ�ϵ
	
	public ConGraph(List<ConUseCase> conUseCases)
	{
		graphInitial(conUseCases);
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<ConVertex> getVertexList() {
		return vertexList;
	}

	public void setVertexList(List<ConVertex> vertexList) {
		this.vertexList = vertexList;
	}

	public int[][] getAdjoinMatrix() {
		return adjoinMatrix;
	}

	public void setAdjoinMatrix(int[][] adjoinMatrix) {
		this.adjoinMatrix = adjoinMatrix;
	}

	public void graphInitial(List<ConUseCase> conUseCases)
	{
		num=1;
		//����ǰ������ ��ʼ��һ����״̬���;
		ConVertex initVertex=new ConVertex("000000","Initial",0);
		vertexList.add(initVertex);
		
		for(ConUseCase uc:conUseCases)
		{
			ConVertex vertex=new ConVertex(uc.getID(),uc.getUseCaseName(),num);
			num++;
			vertexList.add(vertex);
		}
		//��ʼ��һ��β�ڵ㣻��ʾ���ִ�н���;
		ConVertex exitVertex=new ConVertex("999999","Exit",num++);
		
		vertexList.add(exitVertex);
		adjoinMatrix=new int[num][num];
		for(int i=0;i<num;i++)
		{
			for(int j=0;j<num;j++)
			{
					adjoinMatrix[i][j]=0;//��ʼ���ڵ�֮��û����ϵ��
			}
		}
		//��������ǰ��������˳��ͼ�ĺ������������ڽӾ�������ϵ��
		//�ڵ���nickID��Ӧ�ڽӾ�����������
//		for(ConVertex vertex:vertexList)
//		{
//			System.out.println(vertex.getID());
//		}
		for(int i=0;i<num;i++)
		{
			//��Ԫ�ڵ㣬�����ʼִ��Ѱ�Һ�������
			if(i==0)
			{
				for(ConUseCase uc:conUseCases)
				{
					if(uc.getPreCondition()!=null&&uc.getPreCondition().contains(StaticConfig.initialCondition))
					{
						for(ConVertex vertex:vertexList)
						{
							if(vertex.getID()!=null&&vertex.getID().equals(uc.getID()))
							{
								adjoinMatrix[0][vertex.getNickID()]=1;
							}
						}
						
					}
				}
			}
			else if(i!=(num-1))
			{
				//��������֮�佨����ϵ;
				for(ConUseCase uc:conUseCases)
				{
					int tempX = 0;
					for(ConVertex vertex:vertexList)
					{
						
						if(vertex.getID()!=null&&vertex.getID().equals(uc.getID()))
						{
							tempX=vertex.getNickID();
							break;
						}
					}
					
					for(ConSD sd:uc.getConSDset())
					{
						for(ConUseCase tempUc:conUseCases)
						{
							if(tempUc.getPreCondition()!=null&&sd.getPostCondition().contains(tempUc.getPreCondition()))
							{
								for(ConVertex vertex:vertexList)
								{
									if(vertex.getID()!=null&&tempUc.getID().equals(vertex.getID()))
									{
										adjoinMatrix[tempX][vertex.getNickID()]=1;
									}
								}
								
							}
						}
					}
				}
			}
			else//Ѱ�ҽ����ڵ��ǰ��������������ϵ
			{
				for(ConUseCase uc:conUseCases)
				{
					for(ConSD sd:uc.getConSDset())
					{
						if(sd.getPostCondition().contains("SoftwareFinish"))  //�����ж�*************����XML��Ϣ�޸�*****************
						{
							for(ConVertex vertex:vertexList)
							{
								if(vertex.getID()!=null&&uc.getID().equals(vertex.getID()))
								{
									adjoinMatrix[vertex.getNickID()][num-1]=1;
								}
							}
							
						}
					}
				}
			}
		}
		
	}

	public void printGraph()
	{
		System.out.println("����ִ�й�ϵ���ڽӾ����ʾ���£�");
		System.out.print(" ");
		for(int i=0;i<num;i++)
		{
			System.out.print(" "+i);
		}
		System.out.println(); 
		for(int i=0;i<num;i++)
		{
			System.out.print(i+" ");
			for(int j=0;j<num;j++)
			{
				System.out.print(adjoinMatrix[i][j]+" ");
			}
			System.out.println();
		}
		
	}

	//�ж������ڵ�֮���Ƿ������ͨ·��
	public Boolean isHavePath(int start,int end)
	{
		List<Integer> queue=new ArrayList<Integer>();
		List<Integer> visited=new ArrayList<Integer>();
		queue.add(start);
		while(!queue.isEmpty())
		{
			for(int i=0;i<num;i++)
			{
				if(adjoinMatrix[start][i]==1&&!visited.contains(i))
				{
					queue.add(i);
				}
			}
			if(queue.contains(end))
			{
				return true;
			}
			else
			{
				visited.add(queue.get(0));
				queue.remove(0);
				if(!queue.isEmpty())
				{
					start=queue.get(0);
				}
			}
		}		
		return false;
	}
}
