package com.horstmann.violet.application.gui.util.xiaole.UppaalTransfrom;

import org.dom4j.DocumentException;

public class TransToVioletUppaal {

	public static String TransToViolet() throws DocumentException {
		// TODO Auto-generated method stub
		WriteVioletUppaal writevioletuppaal=new WriteVioletUppaal();
		//����wjת����ʱ���Զ�����xml(�ҵ�������Ҫ����Ϣ)
    	writevioletuppaal.find();
    	//��ʱ���Զ�����xml-->ת�������ǵ�xml(�������ǻ�ȡ����Ϣ����)
  	    writevioletuppaal.writeVioletUppaal("D:\\ModelDriverProjectFile\\UPPAL"
  	    		+ "\\2.UML_Model_Transfer\\uppaalTest1.uppaal.violet.xml"
  	    		);
  	    return "uppaalTest1.uppaal.violet.xml";
	}

}
