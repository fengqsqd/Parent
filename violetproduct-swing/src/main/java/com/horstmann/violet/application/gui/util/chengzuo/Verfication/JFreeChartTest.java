package com.horstmann.violet.application.gui.util.chengzuo.Verfication;
import java.awt.Font;
import java.awt.Panel;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChartTest {
	//����һ��jpanel
  public  static JPanel getJFreeChartTest(List<TestCase> list) {
	  //���ڼ�¼�ɹ���ʧ��
	  int i=0;
	  int j=0;
	  //���������ɹ�����ʧ�ܵĸ���ͳ��
	  for(TestCase info:list){
		  String str=info.getResult();
		  if("�ɹ�".equals(str)){
			  i++;
		  }
		  else{
			  j++;
		  }
	  }
	  JPanel jp=new JPanel(); 
	  DefaultPieDataset dpd=new DefaultPieDataset();
	  if(i==0&&j!=0){//���ֻ�гɹ�
		  dpd.setValue("�ɹ�", 100);
	  }
	  else if(i!=0&&j==0){//���ֻ��ʧ��
		  dpd.setValue("ʧ��", 100);
	  }
	  else if(i!=0&&j!=0){//ʧ�ܺͳɹ��Ķ���
		  double a=i/(double)(i+j)*100;
		  double b=j/(double)(i+j)*100;
		  DecimalFormat df=new DecimalFormat(".##");
		  String sa=df.format(a);
		  String sb=df.format(b);
		  a=Double.parseDouble(sa);
		  b=Double.parseDouble(sb);
		  dpd.setValue("�ɹ�",a);
		  dpd.setValue("ʧ��",b);
	  }
   //  dpd.setValue("������Ա",45);
     //dpd.setValue("������Ա", 10);
  
     JFreeChart chart=ChartFactory.createPieChart("��������ʵ������֤����",dpd,true,true,false); 
     //������ı���
     Font font = new Font("", 10, 20);
     TextTitle txtTitle = null;
     txtTitle = chart.getTitle();
     txtTitle.setFont(font);
//     this.add(chart);
     ChartPanel cp=new ChartPanel(chart);
     jp.add(cp);
     ChartFrame chartFrame=new ChartFrame("��������ʵ������֤����",chart); 
     //�����������
     Font font2 = new Font("", 10, 16);
     PiePlot pieplot = (PiePlot)chart.getPlot();
     pieplot.setLabelFont(font2); 
     chart.getLegend().setItemFont(font2); 
     
    // chartҪ����Java��������У�ChartFrame�̳���java��Jframe�ࡣ�õ�һ�������������Ƿ��ڴ������Ͻǵģ��������м�ı��⡣
     //chartFrame.pack(); //�Ժ��ʵĴ�Сչ��ͼ��
    // chartFrame.setVisible(true);//ͼ���Ƿ�ɼ�
//     this.pack();
//     this.setVisible(true);
     
     return jp;
  }
}
