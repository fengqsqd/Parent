package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UseCaseProbability {
	/*
	 * ��ȡ��������,ȷ������ִ�й�ϵ�ĸ��ʣ�Ȼ��洢�����ݽṹ�У����ʾ��󣩣�
	 * ��ת��Markov������������Markov����ʱ�򸳸���Ӧ��Ǩ��
	 * (��Ҫ�����ݽṹ�а���������ID������ѯ��Ӧ������ִ��˳���ϵ��Ӧ��ִ�и���)
	 *
	 */
	private double[][] ucRelaProMatrix;  //���ʾ��󣬴洢����֮���ִ�й�ϵ����
	private ConGraph graph;
	public UseCaseProbability() throws InvalidTagException
	{
		Initialize();
	}
	
	/*
	 * ��ʼ������ȡ:
	 * �ڽӾ���(��ʾ������֮���ִ�й�ϵ)
	 * ��
	 * �����ڵ�(VertexList�к���:����ID���������ơ������ڵ�ı��ID)
	 */
	public void Initialize() throws InvalidTagException 
	{
		ConWork work=new ConWork();
		work.Initialize();
		graph=new ConGraph(work.getConUseCases());
		//�����ڽӾ�����������ڵ�
		ucRelaProMatrix=new double[graph.getNum()][graph.getNum()];
		
		
	}
	public void MergeProMatrix()
	{
		//���ʾ���ucRelaProMatrix
		//������ϵ����
		int num=graph.getNum();
		int[][] relaMatrix=graph.getAdjoinMatrix();
		
		
		for(int i=0;i<num-1;i++)
		{
			List<Integer> rearNodeList=new ArrayList<Integer>();
			for(int j=1;j<num;j++)
			{
				if(relaMatrix[i][j]==1)
				{
					 rearNodeList.add(j);
				}
			}
			graph.getVertexList().get(i).setRearNodeList(rearNodeList);
			//��ȡ��ʼ�������������;
			String currentUcName="Default Uc Name";
			for(ConVertex v:graph.getVertexList())
			{
				if(v.getNickID()==i)
				{
					currentUcName=v.getName();
				}
			}
			
			if(rearNodeList.size()==0)
			{
				System.out.println(currentUcName+"�����޺��������");
			}
			else if(rearNodeList.size()==1) 
			{	
				String tempUcName="Default rearUc Name";
				for(ConVertex v:graph.getVertexList())
				{
					if(v.getNickID()==rearNodeList.get(0))
					{
						tempUcName=v.getName();
					}
				}
				System.out.println(currentUcName+"������ 1�� �������"+tempUcName+" ��ִ�и���Ϊ1.0");
				ucRelaProMatrix[i][rearNodeList.get(0)]=1.0;
			}
			else if(rearNodeList.size()==2)
			{
				List<String> tempUcNameList=new ArrayList<String>();
				for(int k=0;k<=1;k++)
				{
					for(ConVertex v:graph.getVertexList())
					{
						if(v.getNickID()==rearNodeList.get(k))
						{
							tempUcNameList.add(v.getName());
						}
					}
				}
				System.out.println(currentUcName+"������ 2�� �������"+tempUcNameList.get(0)
						+"��"+tempUcNameList.get(1)+"  ��ִ�и��ʿɸ���ר��ֱ�Ӵ�ֵõ���");
				Scanner cin=new Scanner(System.in);
				double x=cin.nextDouble();
				double y=cin.nextDouble();
				cin.close();
				if(x+y==1.0)
				{
					System.out.println("���߸��������һ��");
					ucRelaProMatrix[i][rearNodeList.get(0)]=x;
					ucRelaProMatrix[i][rearNodeList.get(1)]=y;
					
				}
				else
				{
					System.out.println("���߸��ʲ������һ��");
				}
				
			}
			else if(rearNodeList.size()>=3)
			{
				List<String> tempUcNameList=new ArrayList<String>();
				for(int k=0;k<=1;k++)
				{
					for(ConVertex v:graph.getVertexList())
					{
						if(v.getNickID()==rearNodeList.get(k))
						{
							tempUcNameList.add(v.getName());
						}
					}
				}
				System.out.println(currentUcName+"������ "+rearNodeList.size()+"�� �������");
				
				for(int k=0;k<tempUcNameList.size();k++)
				{
					System.out.print(currentUcName+" ---> "+tempUcNameList.get(k)
							+"("+rearNodeList.get(k)+")�� ");
				}
				
				//���
				MergeProbability mp=new MergeProbability(rearNodeList.size());
				mp.mergeProbability();
				List<Double> proList=mp.getProList();
				
				//����ʾ����Ӧλ�ø�ֵ
				for(int j=0;j<rearNodeList.size();j++)
				{
					ucRelaProMatrix[i][rearNodeList.get(j)]=proList.get(j);
				}
				
			}
		}
	}
	
	
	
}
