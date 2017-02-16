package cn.edu.hdu.lab.service.probability;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
public class MergeProbability {

	private int number;
	
	List<double[][]> normalMatrixs;
	public MergeProbability(List<double[][]> proMatrixList)
	{
		number=proMatrixList.get(0).length;
		this.normalMatrixs=proMatrixList;
	}

	@SuppressWarnings("unchecked")
	public  List mergeProbability()
	{
		List resultList=new ArrayList<>();
		double[] proArray=new double[number];
		
		DecimalFormat f=new DecimalFormat("0.000");
		//�����жϾ���:ƽ������
		MergeAveMatrix mam=new MergeAveMatrix(normalMatrixs);		
		Matrix m=mam.merge();
		
		//��ʾ�жϾ���
		m.print(4, 3);
		System.out.println("����ֵ����");
		m.eig().getD().print(4, 3);
		System.out.println("��Ӧ������������");
		m.eig().getV().print(4, 3);
		System.out.println("����ά����"+m.getColumnDimension());
		//��֤����һ����
		ProMatrix M=new ProMatrix(m);
		if(M.getCR()>0.1)//CR����������CR��ʾ�͸���Ϊ0��
		{
			resultList.add("�жϾ���һ����ָ��CR="+f.format(M.getCR())+">0.1,���꣡");
			for(int i=0;i<proArray.length;i++)
			{
				proArray[i]=0;
			}
			resultList.add(proArray);
			return resultList;
		}
		else//���ɳ����������ֵ��Ӧ������������һ����Ľ����
		{
			proArray=M.getProList();
			System.out.println("�жϾ���һ����ָ�� CR="+f.format(M.getCR()));
			System.out.println("���������ĸ���Ϊ��"+M.getProList());
		}
		return resultList;//��������ʾ+�������

	}
	
	
}
