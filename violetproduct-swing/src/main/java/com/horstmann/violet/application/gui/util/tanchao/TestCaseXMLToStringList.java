package com.horstmann.violet.application.gui.util.tanchao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class TestCaseXMLToStringList {
	   private BufferedReader in=null;
	   private BufferedWriter out=null;
	   //��xml �ļ� �����ַ����ļ���
		public String getStrings(String name){
			String s="";
			try {
				File file=new File(name);
				in=new BufferedReader(new FileReader(file));
				String lineS="";//��ǰ�е��ַ���
				while((lineS=in.readLine())!=null){
					if(lineS.contains("UTF-8")){
					    lineS="<?xml version=\"1.0\" encoding=\"UTF8\"?>";
					}
					s+=(lineS+"\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return s;
		}
		
		//����list�ļ�������xml�ļ�
		public void createXml(String s,String path){
			try {
				File file=new File(path);
				if(!file.exists()){
					file.createNewFile();
				}
				out=new BufferedWriter(new FileWriter(file));
					out.write(s);
					out.flush();

				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void exchangeUTF(String filename) {
			TestCaseXMLToStringList t=new TestCaseXMLToStringList();
		    String s=t.getStrings(filename);
		    t.createXml(s,filename);
		}
	}
