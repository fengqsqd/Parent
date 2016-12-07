package com.horstmann.violet.application.gui.util.chengzuo.Verfication;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClientRecThread implements Runnable {

	volatile boolean keepRunning;
	// ���߳��������socket����Ӧ��������
	private DataInputStream dis = null;
	private String content = null;
	public List<TestCase> testCaseList = Collections.synchronizedList(new ArrayList<TestCase>());

	public ClientRecThread(Socket socket) {
		try {
			this.dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر� ������
	 */
	public void close() {
		try {
			this.dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ַ��������װ��ģ�ͣ����洢��List��
	 *
	 */
	public void model() {
		String[] str = content.split("\n");
		TestCase testcase = new TestCase();
		int len = 0;
		len = "��������ID: ".length();
		testcase.setTestCaseID(str[0].substring(len));
		String s = "";
		for (int i = 2; i < str.length - 2; i++) {
			len = "	�� ����ID : 1    �������� : ".length();
			s += str[i].substring(len);
		}
		testcase.setContent(s);
		len = "  -->����ִ��״̬: [ ".length();
		testcase.setState(str[str.length - 2].substring(len, str[str.length - 2].length() - 2));
		len = "  -->���״̬: [ ".length();
		testcase.setResult(str[str.length - 1].substring(len, str[str.length - 1].length() - 2));

		System.out.println(testcase);
		testCaseList.add(testcase);
	}

	@Override
	public void run() {
		System.out.println("���ս�������");
		int bufferSize = 918;
		byte[] buf = new byte[bufferSize];
		try {
			// �����̵߳Ľ��ܻ�������Ϊ��ʱ��
			while(keepRunning){
				while (dis.read(buf) != -1) {
					// 1.�������Է��������ַ���
					content = new String(buf, "UTF-8").trim();
					System.out.println("....."+content);
					// 2.�ַ��������װ��ģ�ͣ����洢��List��
					model();
					// 3.����ֽ�����
					Arrays.fill(buf, (byte) 0);
				}
			}
			
			System.out.println("���ս��̽���");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}