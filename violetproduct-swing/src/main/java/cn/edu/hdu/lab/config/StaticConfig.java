package cn.edu.hdu.lab.config;

/**
 * ��̬����������
 * @author Terence
 *
 */
public class StaticConfig {
	/**
	 * @umlPath UMLģ������ͼ·��
	 * @umlPathPrefix UMLģ��·��ǰ׺�����ڰ���
	 * @mcPathPrefix  Markovģ��·��ǰ׺�����ڰ���
	 * @initialCondition  umlģ��ƥ��ĳ�ʼ����
	 * @endCondition  umlģ��ƥ��Ľ�������
	 */
	//public static String umlPath="resources\\umlModel\\ResetMapXML\\useCases.xml";
	//public static String umlPathPrefix="resources\\umlModel\\ResetMapXML\\";
	//public static String mcPathPrefix="resources\\markovModel\\mcResetMapXML\\";

             
	
	//601ת��·����Ϣ
	//���� ��ʱ��Լ��
	public static String umlPathHDU="resources\\umlModel\\leverUMLHDU\\Primary Use Cases.ucase.violet.xml";  
	//public static String umlPathHDU="resources\\umlModel\\umlCYKHDU\\Parimary.ucase.violet.xml";  
	public static String umlPathPrefixHDU="resources\\umlModel\\leverUMLHDU\\";               
	public static String mcPathPrefixHDU="resources\\markovModel\\leverMCHDU\\"; 
	
	//����ʱ��Լ��
//	public static String umlPathHDU="resources\\umlModel\\umlCYKHDU7-22\\Primary Use Cases.ucase.violet.xml";  
//	//public static String umlPathHDU="resources\\umlModel\\umlCYKHDU\\Parimary.ucase.violet.xml";  
//	public static String umlPathPrefixHDU="resources\\umlModel\\umlCYKHDU7-22\\";               
//	public static String mcPathPrefixHDU="resources\\markovModel\\mcCYKXml7-22\\"; 
	
	
	//EAת��·����Ϣ
	public static String umlPath="resources\\umlModel\\zcUML8-17\\Primary Use Cases.xml";  
	public static String umlPathPrefix="resources\\umlModel\\zcUML8-17\\";               
	public static String mcPathPrefix="resources\\markovModel\\zcMC8-17\\"; 
	
//	public static String umlPath="resources\\umlModel\\����ģ��1\\Primary Use Cases.xml";
//	public static String umlPathPrefix="resources\\umlModel\\����ģ��1\\";               
//	public static String mcPathPrefix="resources\\markovModel\\����ģ��1\\";
	
	//
	public static String initialCondition="Correctiveness";                     
	public static String endCondition="SoftwareFinished";
	
	public static String[] fragmentsTypes={"loop","opt","alt","par"};//˳�򲻿ɸı� {"loop"}{"opt"}{"alt","par"}

	
}
