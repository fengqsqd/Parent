package cn.edu.hdu.lab.service.consisFunc;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hdu.lab.service.parserEA.InvalidTagException;

public class ConFunc {

	/**
	 * @param args
	 * @throws InvalidTagException 
	 */
	public static void main(String[] args) throws InvalidTagException {
		ConWork work=new ConWork();
		work.Initialize();
		
		//List verifyReList= new ArrayList(); //������������T/F����֤�������
		//verifyReList=work.ConsistencyCheck();
		
		/*boolean result=work.ConsistencyCheck();
		if(result)
		{
			System.out.println("��UMLģ������һ����Ҫ�󣡿�ת��Ϊ��Ӧ��Markov��ʹ��ģ�ͣ�");
		}
		else
		{
			System.out.println("UMLģ�Ͳ�����һ����Ҫ��");
		}
		
		
		if(verifyReList!=null)
		{
			if((boolean)verifyReList.get(0)==true)
			{
				System.out.println("��UMLģ������һ����Ҫ�󣡿�ת��Ϊ��Ӧ��Markov��ʹ��ģ�ͣ�");
			}
			else
			{
				System.out.println("UMLģ�Ͳ�����һ����Ҫ��");
			}
			
			System.out.println((String)verifyReList.get(1));
		}
		*/
		
	}

}
