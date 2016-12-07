package com.horstmann.violet.application.gui.util.xiaole.UppaalTransfrom;

import java.io.File;
import java.io.IOException;

import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.LocalFile;

public class ImportByDoubleClick {
	public static GraphFile importFileByDoubleClick(String type,String name){
    	GraphFile graphFile=null;
    
    	String base="D://ModelDriverProjectFile";
    	//����type�ҵ���Ӧ���ļ���
    	if("ClassDiagram".equals(type)){
    		base+="\\ClassDiagram\\Violet";
    	
    	}else if("StateDiagram".equals(type)){
    		base+="\\StateDiagram\\Violet";
    	
    	}else if("UseCaseDiagram".equals(type)){
    		
    		base+="\\UseCaseDiagram\\Violet";
    	
    	}else if("ActivityDiagram".equals(type)){
    		
    		base+="\\ActivityDiagram\\Violet";
    	
    	}else if("ObjectDiagram".equals(type)){
    		
    		base+="\\ObjectDiagram\\Violet";
    	
    	}else if("TimingDiagram".equals(type)){
    		
    		base+="\\TimingDiagram\\Violet";
    	
    	}else if("UPPAAL".equals(type)){
    		System.out.println("-------�ҵ�Ҫת����XML�ļ�-------");
    		base+="\\UPPAL\\2.UML_Model_Transfer\\";
    		
    	
    	}else if("SequenceDiagram".equals(type)){

    		base+="\\SequenceDiagram\\Violet";    		
    	}
    	//�������ֵ����ļ���������ļ�
    	System.out.println(base+name);
    	 File file =new File(base);    
    	 //�����ļ��������ж��ٵ��ļ�
		 File[] fList=file.listFiles();//D: ModelDriverProjectFile UPPAL 2.UML_Model_Transfer
		 System.out.println("��Ŀ:"+fList.length);
		 for(int i=0;i<fList.length;i++){
			 System.out.println("���֣�"+fList[i].getName()+"ad");
		 }
		 for (int i = 0; i < fList.length; i++) {
			
			 String fname =fList[i].getName();
			 System.out.println("fname:"+fname);
			 if(name.equals(fname)){
				 try {
					 System.out.println("**"+fList[i]);
					 IFile file1 =  new LocalFile(fList[i]);
					 System.out.println(111);
					graphFile=new GraphFile(file1);
					System.out.println(graphFile.getFilename());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }	
		}
		 if(graphFile==null){
			 System.out.println("��");
		 }
		 System.out.println("Filename��"+graphFile.getFilename()+"&&");
    	
    	return graphFile;
    }
}
