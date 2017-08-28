package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;



import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.yangjie.BestAssign;
import com.horstmann.violet.application.gui.util.yangjie.Calculate;
import com.horstmann.violet.application.gui.util.yangjie.CalculateDistribution;
import com.horstmann.violet.application.gui.util.yangjie.CalculateSimilarity;
import com.horstmann.violet.application.gui.util.yangjie.CollectRoute;
import com.horstmann.violet.application.gui.util.yangjie.Constant;
import com.horstmann.violet.application.gui.util.yangjie.Markov;
import com.horstmann.violet.application.gui.util.yangjie.Parameter;
import com.horstmann.violet.application.gui.util.yangjie.ReadMarkov2;
import com.horstmann.violet.application.gui.util.yangjie.Route;
import com.horstmann.violet.application.gui.util.yangjie.State;
import com.horstmann.violet.application.gui.util.yangjie.Stimulate;
import com.horstmann.violet.application.gui.util.yangjie.TCDetail;
import com.horstmann.violet.application.gui.util.yangjie.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;

public class NoTimeCaseOperation1 extends JPanel{
       private JLabel topLabel;
       private JLabel label1;
       private JLabel label2;

       private JProgressBar progressBar;
       private JTextField textField;
       private JButton button;
       
       private JPanel gapPanel;
       private MainFrame mainFrame;
       
       private String testSequence;//��������
       private String excitation;  //��������
       private String testCase;    //��������
       
       private List<String> paramterNameList;
       private List<String> paramterValueList;
       
       private int min;
       private int N;
       private double p;
   	   private double c;
   	   private int i;
   	   
   	   private List<String> constraintNameString;
       private List<Double> pros;
       private List<Integer> numbers;
       private List<Double> actualPercentsDoubles;
       private Document dom;
       private Markov markov;
       private Element root;
       private double[] PI;
       private int minSeq;
       private XMLWriter writer;
       
       private int progressBarIndex = 0;
       
       private Callable<Integer> maincallable;
       private FutureTask<Integer> maintask;
   	   private Thread mainthread; 

	   
	   private List<FutureTask<Integer>> futuretasklist;
	   private List<Thread> threadlist;
	   
	   private BigDecimal bigDecimal;
	   private DecimalFormat  df = new DecimalFormat();
	   private String pattern = "#0.0000";
	   
	   private String ModelName;
	   private String NoTimeMarkovRoute;
	   private String route;
	   
	   private String quota;
       public NoTimeCaseOperation1(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
    	   this.setBackground(new Color(233,233,233));
    	   this.setLayout(new GridBagLayout());
    	   this.add(topLabel,new GBC(0, 0, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
    	   this.add(progressBar,new GBC(0, 1, 5, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
    	   this.add(label1,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 15, 10, 5));
    	   this.add(textField,new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
    	   this.add(label2,new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
    	   this.add(gapPanel,new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 5));
    	   this.add(button,new GBC(4, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
       }
       private void init()
       {
    	   df.applyPattern(pattern);
    	   
    	   topLabel = new JLabel("");
    	   label1 = new JLabel("���ɲ�����������:");
    	   label2= new JLabel("");
    	   
    	   progressBar = new JProgressBar();
    	   textField = new JTextField();
    	   button = new JButton("��ʼ����");
    	   gapPanel = new JPanel();

    	   paramterNameList = new ArrayList<String>();
    	   paramterValueList = new ArrayList<String>();
           constraintNameString = new ArrayList<String>();
           actualPercentsDoubles = new ArrayList<Double>();
           pros = new ArrayList<Double>();
           numbers = new ArrayList<Integer>();
    	   
    	   textField.setPreferredSize(new Dimension(40,30));
    	   topLabel.setFont(new Font("����", Font.PLAIN, 16));
    	   label1.setFont(new Font("����", Font.PLAIN, 16));
    	   label2.setFont(new Font("����", Font.PLAIN, 16));
    	   
    	   progressBar.setUI(new ProgressUI(progressBar, Color.green));
    	   progressBar.setPreferredSize(new Dimension(800, 30));
    	   progressBar.setUI(new GradientProgressBarUI());
    	   progressBar.setValue(0);
    	  	   
    	   NoTimeMarkovRoute = mainFrame.getBathRoute()+"/NoTimeMarkov/";
    	   initThread();
    	   listen();
       }
       private void listen()
       {
    	   button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub1
//				markov = mainFrame.getNoTimeSeqOperation1().getMarkov();
//				root = mainFrame.getNoTimeSeqOperation1().getRoot();
//				PI = mainFrame.getNoTimeSeqOperation1().getPI();
//				min = mainFrame.getStepThreeLeftButton().getMin();			
//					Calculate.getAllTransValues(markov);
//
//					new BestAssign().assign(markov, root);
//
//					System.out.println("ָ��---�ɿ��Բ����������ݿ⸲����:" + markov.getDbCoverage());
//					markov.setDeviation(CalculateSimilarity.statistic(markov, PI));
//					System.out.println("ָ��---�ɿ��Բ����������ɱ�����ʹ��ģ��ʵ��ʹ�ø���ƽ��ƫ��:"
//							+ markov.getDeviation());
//					System.out.println("����ƽ�ȷֲ��������ʹ��ģ�ͺͲ���ģ�͵Ĳ���ȣ�"
//							+ CalculateSimilarity.statistic(markov, PI));
//					System.out.println("����ƻ�����Ϊ��" + (Constant.maxCircle - 1));
					initThread();
					mainthread.start();
				}
		});
       }
       private void initThread()
       {
    	   maincallable = new Callable<Integer>() {
      			@Override
      			public Integer call() throws Exception {
      				// TODO Auto-generated method stub
      				try {
      				button.setEnabled(false);
       				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
       				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(false);
       				mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(false);
      				
      				markov = mainFrame.getNoTimeSeqOperation1().getMarkov();
      				dom = mainFrame.getNoTimeSeqOperation1().getDom();
    				root = mainFrame.getNoTimeSeqOperation1().getRoot();
    				PI = mainFrame.getNoTimeSeqOperation1().getPI();
    				min = mainFrame.getStepThreeLeftButton().getMin();
    				minSeq = mainFrame.getNoTimeSeqOperation1().getMinSeq();
    				
      				progressBarIndex = 0;
      				progressBar.setValue(0);
    				mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
    				
    				topLabel.removeAll();
    				topLabel.setText("���ڻ�ȡMarkov�в�����Ϣ.......");
      				markov.setTcNumber(minSeq);

      				Calculate.getAllTransValues(markov);

					new BestAssign().assign(markov, root);
					
					//��ȡ���������
					List<Route> routeList = markov.getRouteList();
					constraintNameString.clear();
					pros.clear();
					numbers.clear();
					for(Route route : routeList)
					{
						constraintNameString.add(route.getTcSequence());
						pros.add(route.getRouteProbability());
						numbers.add(route.getNumber());
						actualPercentsDoubles.add(route.getActualPercent());
					}
			
					StepThreeTabelPanel stepThreeTabelPanel = new StepThreeTabelPanel(constraintNameString, actualPercentsDoubles,pros, numbers);
					StepThreeTabelPanel testRoute = new StepThreeTabelPanel(constraintNameString, actualPercentsDoubles,pros, numbers);
//					mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestRoute().removeAll();
//					mainFrame.getStepThreeNoTimeCustomTabbedPane().getTestRoute().add(stepThreeTabelPanel);
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					
						writer = new XMLWriter(new FileOutputStream(
								mainFrame.getBathRoute()+"/TestCase/"+ModelName+"_�Զ���.xml"), format);
						writer.write(dom);
						writer.close();
				

					List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select * from tcdetail");

					//���ɲ�������
					i = 0;
					topLabel.removeAll();
					topLabel.setText("�������ɲ���������Ϣ........");
					
					//����������������������ɵ��
					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().removeAll();
					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().setLayout(new GridBagLayout());

					JPanel TestDataPanel = new JPanel();
					TestDataPanel.setLayout(new GridBagLayout());
				
					for(TCDetail tcDetail : lists)
					{
						StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(tcDetail.getTestCase(),2,mainFrame);
						StepThreeTabelPanel testTabelPanel2 = new StepThreeTabelPanel(tcDetail.getTestCase(),2,mainFrame);
						
						TestDataPanel.add(testTabelPanel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						
						mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(testTabelPanel2, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						mainFrame.getStepThreeNoTimeTabbedPane().getTestData().updateUI();
						mainFrame.getStepThreeNoTimeTabbedPane().getTestDataScroll().getVerticalScrollBar().setValue(
						mainFrame.getStepThreeNoTimeTabbedPane().getTestDataScroll().getVerticalScrollBar().getMaximum());
						i++;
						progressBar.setValue(60 + (int)(((double)i/lists.size())*40));
						mainFrame.renewPanel();
					}
				    
					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().add(new JPanel(), new GBC(0, i+1).setFill(GBC.BOTH).setWeight(1, 1));		
					mainFrame.getStepThreeNoTimeTabbedPane().getTestData().updateUI();
					
					topLabel.removeAll();
					bigDecimal = new BigDecimal(markov.getDeviation());
					String ii = bigDecimal.toPlainString();
					double d = Double.valueOf(ii);
					
    				topLabel.setText("���������������, ������"+textField.getText().toString()+"��!" + "�ɿ��Բ����������ݿ⸲����:"+df.format(markov.getDbCoverage())+"  �ɿ��Բ����������ɱ�����ʹ��ģ��ʵ��ʹ�ø���ƽ��ƫ��:"+df.format(d));
    				
    				NoTimeTestCaseNode noTimeTestCaseLabel = new NoTimeTestCaseNode(ModelName+"_�Զ���", mainFrame);
    				quota = "���������������, ������"+textField.getText().toString()+"��!" + "�ɿ��Բ����������ݿ⸲����:"+df.format(markov.getDbCoverage())+"  �ɿ��Բ����������ɱ�����ʹ��ģ��ʵ��ʹ�ø���ƽ��ƫ��:"+df.format(d);
    				noTimeTestCaseLabel.setQuota(quota);
    				noTimeTestCaseLabel.setTestRoute(testRoute);
    				noTimeTestCaseLabel.setTestDataPanel(TestDataPanel);
    				mainFrame.getStepThreeLeftButton().getNoTimeCaseNodePanel().insertCustomNodeLabel(noTimeTestCaseLabel,TestDataPanel,testRoute,quota);

    				button.setEnabled(true);
       				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
       				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
       				mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(true);
      				} catch (Exception e) {
						// TODO: handle exception
      					topLabel.removeAll();
      					topLabel.setText(e.getLocalizedMessage());
      					
      					button.setEnabled(true);
           				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
           				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
           				mainFrame.getStepThreeLeftButton().getNoTimeSeq().setEnabled(true);
					}
    				return 1;
      			}
      		};
      		maintask = new FutureTask<>(maincallable);
      		mainthread = new Thread(maintask);
       }
private static boolean isSufficient(Markov markov) {

	for (State state : markov.getStates()) {

		for (Transition outTransition : state.getOutTransitions()) {

			if (outTransition.getAccessTimes() == 0) {
				return false;
			}
		}
	}
	return true;
}
private boolean isInt(String str)
{
	   try
	   {
	      Integer.parseInt(str);
	      return true;
	   }
	   catch(NumberFormatException ex){}
	   return false;
	}
public JLabel getLabel2() {
	return label2;
}

public JLabel getTopLabel() {
	return topLabel;
}
public String getModelName() {
	return ModelName;
}
public void setModelName(String modelName) {
	ModelName = modelName;
}
public String getRoute() {
	return route;
}
public void setRoute(String route) {
	this.route = route;
}
public JButton getButton() {
	return button;
}

}
