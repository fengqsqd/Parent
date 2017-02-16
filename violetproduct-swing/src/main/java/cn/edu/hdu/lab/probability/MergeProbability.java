package cn.edu.hdu.lab.probability;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
public class MergeProbability {

	private int number;
	List<Double> proList=new ArrayList<Double>();
	public MergeProbability(int num)
	{
		number=num;
	}
	
	public List<Double> getProList() {
		return proList;
	}

	public  void mergeProbability()
	{
		 
		DecimalFormat f=new DecimalFormat("0.000");
		//�����жϾ���
		NormalMatrixDao normalMatrixDao=(NormalMatrixDao)ValueOf.mergeMatrixDao(number);
		//Matrix m=normalMatrixDao.getM();
		double[][] array={{1.0,2.5,6.6667,8.6667},{0.3056,1.0,5.0,4.3333},
						       {0.1508,0.2,1.0,3.6667},{0.1157,0.2333,0.2833,1.0}};
		Matrix m=new Matrix(array);
		//��ʾ�жϾ���
		m.print(4, 3);
		System.out.println("����ֵ����");
		m.eig().getD().print(4, 3);
		System.out.println("��Ӧ������������");
		m.eig().getV().print(4, 3);
		System.out.println("����ά����"+m.getColumnDimension());
		//��֤����һ����
		ProMatrix M=(ProMatrix)ValueOf.mergeJudgeMatrix(m);
		
		if(M.getCR()>0.1)
		{
			System.out.println("�жϾ���һ����ָ��CR="+f.format(M.getCR())+">0.1,���꣡");
		}
		else//���ɳ����������ֵ��Ӧ������������һ����Ľ����
		{
			proList=M.getProList();
			System.out.println("�жϾ���һ����ָ�� CR="+f.format(M.getCR()));
			System.out.println("���������ĸ���Ϊ��"+M.getProList());
		}
	}
	
	
}
