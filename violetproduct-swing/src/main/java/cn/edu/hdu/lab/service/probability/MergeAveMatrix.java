package cn.edu.hdu.lab.service.probability;

import java.util.List;

import Jama.Matrix;

public class MergeAveMatrix {
	int m;//����ά��
	int np;//�������
	List<double[][]> Matrixs; //n����Ա�����n������
	public MergeAveMatrix(){}
	public MergeAveMatrix(List<double[][]> Matrixs){
		this.Matrixs=Matrixs;
		m=Matrixs.get(0).length;
		np=Matrixs.size();
	}
	
	public Matrix merge()//����õ�ƽ���жϾ���
	{	
		Matrix M;
		if(Matrixs.size()>1)
			{
				for(int i=1;i<Matrixs.size();i++)
				{
					for(int j=0;j<m;j++)
					{
						for(int k=0;k<m;k++)
						{
							Matrixs.get(0)[j][k]+=Matrixs.get(i)[j][k];
						}
					}
				}
				for(int j=0;j<m;j++)
				{
					for(int k=0;k<m;k++)
					{
						Matrixs.get(0)[j][k]/=np;
					}
				}
				M=new Matrix(Matrixs.get(0));
			}
			else  M=new Matrix(Matrixs.get(0));
		return M;
		
	}
}
