package com.horstmann.violet.application.gui.util.chengzuo.Verfication;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientSocket {

	// ���ӷ�������IP�Լ��˿ں�
	protected String IP;
	protected int PORT;

	// �����׽���
	private Socket socket = null;

	// ���������߳��Լ��߳���
	Thread recThread = null;
	ClientRecThread clientRecThread = null;

	// �ļ������߳��Լ��߳���
	Thread fileThread = null;
	ClientFileThread clientFileThread = null;
	// �ļ�����
	File[] file = null;

	// �����׽���
	public ClientSocket(String ip, int port) {
		this.IP = ip;
		this.PORT = port;
	}

	// ��ʼ�������߳�
	public void initThread() {
		clientRecThread = new ClientRecThread(socket);
		clientRecThread.keepRunning = true;
		recThread = new Thread(clientRecThread);
		recThread.start();

		clientFileThread = new ClientFileThread(socket);
		fileThread = new Thread(clientFileThread);
	}

	/**
	 * �ж����Ӻ���
	 * 
	 * @return
	 */
	public boolean isConnect() {
		if (socket == null || socket.isClosed()) {
			return false;
		}
		return true;
	}

	/**
	 * ����socket����
	 */
	public void Connection() {
		// 1.���� socket
		try {
			socket = new Socket(IP, PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 2.��ʼ���߳�
		initThread();
		// 3.������ʾ
		JOptionPane.showMessageDialog(null, "�ɹ����ӵ�������", null, JOptionPane.OK_CANCEL_OPTION);
	}

	/**
	 * �ر�socket����
	 */
	public void ConnectionClose() {
		try {
			// 0.���͹ر��źţ��رշ�����������
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.write("exit".getBytes());
			out.flush();
			clientRecThread.keepRunning = false;
			// 1. ��������������Ĺر�
			out.close();
			clientFileThread.close();
			clientRecThread.close();
			// 2.socket�ر�
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ļ����亯��
	 */
	public void sendFile(File[] files) {
		try {
			if (files.length > 0) {
				clientFileThread.setFiles(files);
				fileThread.join();
				fileThread.start();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
