package com.horstmann.violet.application.gui.util.yangjie;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

public class Mathematica2 {
	private static String parameter1;    //����ʽ���
	private static String parameter2;    //����
	private static String result;        //����Ľ�

	private static Map<String, String> varToVoc;

	public static String getSolution(String param1, String param2) {

		KernelLink ml = null;

		String path = "-linkmode launch -linkname 'D:\\Mathematica\\10.2\\MathKernel.exe'";
		try {

			ml = MathLinkFactory.createKernelLink(path);
		} catch (MathLinkException e) {
			System.out.println("Fatal error opening link: " + e.getMessage());
			return null;
		}

		try {
			// Get rid of the initial InputNamePacket the kernel will send
			// when it is launched.
			ml.discardAnswer();

			ml.evaluate("<<MyPackage.m");
			ml.discardAnswer();

			ml.putFunction("FindInstance", 3);

			ml.endPacket();
			ml.waitForAnswer();
			String result1 = ml.getString();

			// If you want the result back as a string, use evaluateToInputForm
			// or evaluateToOutputForm. The second arg for either is the
			// requested page width for formatting the string. Pass 0 for
			// PageWidth->Infinity. These methods get the result in one
			// step--no need to call waitForAnswer.
			String strResult = ml.evaluateToOutputForm(
					"A = SetAccuracy[FindInstance[" + param1 + ", " + param2
							+ ", 50], 3]; A[[RandomInteger[{1, Length[A]}]]]",
					0);
			// System.out.println(strResult);
			return strResult;

		} catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			ml.close();
		}
		return null;

	}
	/**
	 * ���������ֵ��
	 * @param param1  bds
	 * @param param2  cs
	 * @return
	 */
	public static String getSolution2(String param1, String param2) {

		KernelLink ml = null;

		String path = "-linkmode launch -linkname 'D:\\Mathematica\\10.2\\MathKernel.exe'";
		try {

			ml = MathLinkFactory.createKernelLink(path);
		} catch (MathLinkException e) {
			System.out.println("Fatal error opening link: " + e.getMessage());
			return null;
		}

		try {
			// Get rid of the initial InputNamePacket the kernel will send
			// when it is launched.
			ml.discardAnswer();

			ml.evaluate("<<MyPackage.m");
			ml.discardAnswer();

			ml.putFunction("FindInstance", 3);

			ml.endPacket();
			ml.waitForAnswer();
			String result1 = ml.getString();

			// If you want the result back as a string, use evaluateToInputForm
			// or evaluateToOutputForm. The second arg for either is the
			// requested page width for formatting the string. Pass 0 for
			// PageWidth->Infinity. These methods get the result in one
			// step--no need to call waitForAnswer.

			// ��param1��param2�в�����ʶ����˳��ȫ���滻Ϊ26����ĸ,��ֹ��������������mathematica�������޷�����
			replace(param1, param2);
			//System.out.println(parameter1 + "======" + parameter2);
//			System.out.println("����ʽ��"+parameter1 + "----�����������" + parameter2);
			String strResult = ml.evaluateToOutputForm(
					"SetAccuracy[FindInstance[{" + parameter1 + "}, {"
							+ parameter2 + "}, 1], 3]", 0);
			// System.out.println(strResult);
			// �ٽ��滻�Ĳ�����ʶ���滻��ԭ��
			recovery(strResult);
			//System.out.println(result);
			return result;

		} catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			ml.close();
		}
		return null;
	}
	/**
	 * ������Ĳ�����
	 * @param param1  boolbds
	 * @param param2  boolcs
	 * @return
	 */
	public static String getSolution3(String param1, String param2) {

		KernelLink ml = null;

		String path = "-linkmode launch -linkname 'D:\\Mathematica\\10.2\\MathKernel.exe'";
		try {

			ml = MathLinkFactory.createKernelLink(path);
		} catch (MathLinkException e) {
			System.out.println("Fatal error opening link: " + e.getMessage());
			return null;
		}

		try {
			// Get rid of the initial InputNamePacket the kernel will send
			// when it is launched.
			ml.discardAnswer();

			ml.evaluate("<<MyPackage.m");
			ml.discardAnswer();

			ml.putFunction("FindInstance", 3);

			ml.endPacket();
			ml.waitForAnswer();
			String result1 = ml.getString();

			// If you want the result back as a string, use evaluateToInputForm
			// or evaluateToOutputForm. The second arg for either is the
			// requested page width for formatting the string. Pass 0 for
			// PageWidth->Infinity. These methods get the result in one
			// step--no need to call waitForAnswer.

			// ��param1��param2�в�����ʶ����˳��ȫ���滻Ϊ26����ĸ,��ֹ��������������mathematica�������޷�����
			replace(param1, param2);
			//System.out.println(parameter1 + "======" + parameter2);
//			System.out.println("����ʽ��"+parameter1 + "----�����������" + parameter2);
			String strResult = ml.evaluateToOutputForm(
					"FindInstance[{" + parameter1 + "}, {"
							+ parameter2 + "},Booleans, 1]", 0);
			// System.out.println(strResult);
			// �ٽ��滻�Ĳ�����ʶ���滻��ԭ��
			recovery(strResult);
			//System.out.println(result);
			return result;

		} catch (MathLinkException e) {
			System.out.println("MathLinkException occurred: " + e.getMessage());
		} finally {
			ml.close();
		}
		return null;
	}

	
	
	

	/**
	 * ���������еı�����ԭΪ�����Ĳ�����ʶ��
	 * 
	 * @param strResult
	 */
	private static void recovery(String strResult) {
		Set<String> keySet = varToVoc.keySet();
		for (String key : keySet) {
			strResult = strResult.replace(varToVoc.get(key) + " ", key.trim());
		}
		result = strResult;
	}

	/**
	 * ��������ʶ������ĸ���滻
	 * 
	 * @param param1
	 * @param param2
	 */
	private static void replace(String param1, String param2) {
		String[] vars = param2.split(",");
		varToVoc = new HashMap<String, String>();
		char vocabulary = 'A';
		int j=0;
		int k=0; //�ж��Ƿ�����ͬ�Ĳ���
		for (String var : vars) {
			for(int i=0;i<j;i++){
				if(var==vars[j]){
					k=1;
				}
			}
			if(k==0){
				param2 = param2.replace(var.trim(), vocabulary + "");
				param1 = param1.replace(var.trim(), vocabulary + "");
				varToVoc.put(var.trim(), vocabulary + "");
				vocabulary = (char) (vocabulary + 1);
			}
			
			
			j++;
		}
		parameter1 = param1;
		parameter2 = param2;
		// System.out.println(param1 + "---" + param2);

	}

}
