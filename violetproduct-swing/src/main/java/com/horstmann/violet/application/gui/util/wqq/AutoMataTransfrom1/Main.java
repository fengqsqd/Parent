package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1;

import java.io.File;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.UppaalTemPlate;
/*
		 * 1.xml��ʽ��֮ǰ�Ĳ�һ��
		 * 2.ʹ��ʱ ��Ҫ����xstream-1.4.8.jar  ��Ҫ��util��bean
		 * 3.�޸���UppaalTemPlate���� Clocks��set������������setClockSet -> setClocks��
		 * 4.ʹ�÷�������
		 * */
public class Main {
	public static void main() {
		// xml file)")
		XML2UppaalUtil util = new XML2UppaalUtil(new File("Draw MoneyForXStream(2).xml"));
		UppaalTemPlate temPlate = util.getTemplates().get(0);
		temPlate.getName();
	}
}
