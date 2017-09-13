package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JTable;
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
import com.horstmann.violet.application.gui.util.yangjie.Calculate;
import com.horstmann.violet.application.gui.util.yangjie.CalculateDistribution;
import com.horstmann.violet.application.gui.util.yangjie.CalculateSimilarity;
import com.horstmann.violet.application.gui.util.yangjie.GenerateCases;
import com.horstmann.violet.application.gui.util.yangjie.Markov;
import com.horstmann.violet.application.gui.util.yangjie.Parameter;
import com.horstmann.violet.application.gui.util.yangjie.RandomCase;
import com.horstmann.violet.application.gui.util.yangjie.ReadMarkov2;
import com.horstmann.violet.application.gui.util.yangjie.State;
import com.horstmann.violet.application.gui.util.yangjie.Stimulate;
import com.horstmann.violet.application.gui.util.yangjie.TCDetail;
import com.horstmann.violet.application.gui.util.yangjie.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;
import com.horstmann.violet.framework.util.GrabberUtils;

public class NoTimeSeqOperation extends JPanel{
       private JLabel topLabel;
       private JLabel label1;
       private JLabel label2;
       
       private JProgressBar progressBar;
       private JTextField textField;
       private JButton button;
       
       private JPanel gapPanel;
       private MainFrame mainFrame;
       
       private String testSequence;//��������
       
       private List<String> paramterNameList;
       private List<String> paramterValueList;
       
       private int i;
       
       private ReadMarkov2 rm;
       private Markov markov;
       private GenerateCases gc;
       private Element root;
       private Document dom;
       private List<Transition> transitions;
       
       private int progressBarIndex = 0;
       private Callable<Integer> maincallable;
       private FutureTask<Integer> maintask;
   	   private Thread mainthread; 
       private Callable<Integer> callable1;
	   private FutureTask<Integer> task1;
	   private Thread thread1;
	   private Callable<Integer> callable2;
	   private FutureTask<Integer> task2;
	   private Thread thread2;
   	   
   	   private BigDecimal bigDecimal;
   	   private DecimalFormat  df = new DecimalFormat();
	   private String pattern = "#0.000";
	   
	   private String ModelName;
	   private String NoTimeMarkovRoute;
	   private String route;
	   
       public NoTimeSeqOperation(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
    	   this.setBackground(new Color(233,233,233));
    	   this.setLayout(new GridBagLayout());
    	   this.add(topLabel,new GBC(0, 0, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
    	   this.add(progressBar,new GBC(0, 1, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
    	   this.add(label1,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 15, 10, 5));
    	   this.add(textField,new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
    	   this.add(gapPanel,new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 5));
    	   this.add(button,new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
       }
       private void init()
       {
    	   df.applyPattern(pattern);
    	   topLabel = new JLabel();
    	   label1 = new JLabel("��ֹ������ֵ:");

    	   progressBar = new JProgressBar();
    	   textField = new JTextField();
    	   button = new JButton("��ʼ����");
    	   gapPanel = new JPanel();
   	   
    	   paramterNameList = new ArrayList<String>();
    	   paramterValueList = new ArrayList<String>();
    	   
    	   textField.setPreferredSize(new Dimension(40,30));
    	   topLabel.setFont(new Font("����", Font.PLAIN, 16));
    	   label1.setFont(new Font("����", Font.PLAIN, 16));
    	   
    	   progressBar.setPreferredSize(new Dimension(800, 30));
    	   progressBar.setUI(new GradientProgressBarUI());
    	   progressBar.setValue(0);
    	   
    	   NoTimeMarkovRoute = mainFrame.getBathRoute()+"/NoTimeMarkov/";
    	   //��ʼ���߳�
    	   initThread();
    	   listen();
       }
       private void listen()
       {
    	   button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initThread();
				mainthread.start();	
				thread1.start();
			}
		});
       }
       private void initThread()
       {
    	   maincallable = new Callable<Integer>() {
   			@Override
   			public Integer call() throws Exception {
   				// TODO Auto-generated method stub
   				progressBarIndex = 0;
   				progressBar.setValue(0);
   				progressBar.setValue(progressBarIndex);
   				while (progressBarIndex < 40) {
   					if(task1.isDone())
   					{
   						progressBarIndex++;
   						progressBar.setValue(progressBarIndex);
   						Thread.sleep(100);
   					}
   					else{
   						progressBarIndex++;
   						progressBar.setValue(progressBarIndex);
   						Thread.sleep(500);
   					}
   				}
   			    
   				while(true){
   					if(task1.isDone())
   	   				{
   	   					thread2.start();
   	   					break;
   	   				}
   				}
   				return 1;
   			}
   		};
   		maintask = new FutureTask<>(maincallable);
   		mainthread = new Thread(maintask);
   		
   		callable1 = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
				button.setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(false);
   				mainFrame.getStepThreeLeftButton().getNoTimeCase().setEnabled(false);
   				progressBarIndex = 0;
   				
   				mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
				File files = new File(NoTimeMarkovRoute);
				for(File selectFile : files.listFiles())
				{
					if(selectFile.getName().replace(".xml", "").equals(ModelName))
						route = selectFile.getAbsolutePath();
				}
				
   				ReadMarkov2 rm = new ReadMarkov2();
				markov = rm.readMarkov(route);
				
				dom = DocumentHelper.createDocument();
				root = dom.addElement("TCS");
				
				double[] PI = CalculateDistribution.stationaryDistribution(markov);
				
				double similarity = 999991;
				boolean sufficiency = false;
				gc = new GenerateCases();
				boolean flag = true;

				do {
					int numberOfTestCases = gc.generate(markov, root);
					// System.out.println(numberOfTestCases);
					if (flag) {

						sufficiency = isSufficient(markov);
					}
					// Ǩ�ƻ���״̬���ǰٷְ�

					if (!sufficiency) {
						continue;
					}
                    
					flag = false;
					similarity = CalculateSimilarity.statistic(markov, PI);
					markov.setDeviation(similarity);
					markov.setActualNum(numberOfTestCases);
                    
				} while (similarity > 0.1);
				}catch (RuntimeException e) {
					// TODO: handle exception
					topLabel.removeAll();
   					topLabel.setText(e.getLocalizedMessage());
					
					button.setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
				}
				return 1;
			}
		};
		task1 = new FutureTask<>(callable1);
   		thread1 = new Thread(task1);
   		
   		callable2 = new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {

					JPanel seqPanel = new JPanel();
					seqPanel.setLayout(new GridBagLayout());
					
					mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().removeAll();
					mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().setLayout(new GridBagLayout());
					int i = 0;
					for(String seq : gc.abstractTS)
					{
						StepThreeTabelPanel testTabelPanel1 = new StepThreeTabelPanel(seq, 1,mainFrame);
						seqPanel.add(testTabelPanel1, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						
						StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(seq, 1,mainFrame);
						mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().add(testTabelPanel, new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						i++;
						progressBar.setValue(40 + (int)(((double)i/gc.abstractTS.size())*60));
						mainFrame.getStepThreeNoTimeSeqTabbedPane().getAbstractSequence().repaint();
						mainFrame.renewPanel();
						Thread.sleep(10);
					}
	                
					
					button.setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getNoTimeCase().setEnabled(true);
					mainFrame.getNoTimeCaseOperation().setModelName(ModelName);
					
					NoTimeSeqNode noTimeSeqNode = new NoTimeSeqNode(ModelName+"_���ƶ�", mainFrame);
					noTimeSeqNode.setAbstractSequencePanel(seqPanel);
					
					mainFrame.getStepThreeLeftButton().getNoTimeSeqNodePanel().insertNodeLabel(noTimeSeqNode, seqPanel);
					mainFrame.getStepThreeLeftButton().getNoTimeSeqNode().repaint();
				}catch (RuntimeException e) {
					// TODO: handle exception
					topLabel.removeAll();
   					topLabel.setText(e.getLocalizedMessage());
					
					button.setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
	   				mainFrame.getStepThreeLeftButton().getNoTimeModelLabel().setEnabled(true);
				}
				return 1;
			}
		};
		task2 = new FutureTask<>(callable2);
   		thread2 = new Thread(task2);
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
public JLabel getTopLabel() {
	return topLabel;
}
public String getModelName() {
	return ModelName;
}
public void setModelName(String modelName) {
	ModelName = modelName;
}
public JButton getButton() {
	return button;
}
public Markov getMarkov() {
	return markov;
}
public GenerateCases getGc() {
	return gc;
}
public Element getRoot() {
	return root;
}
public Document getDom() {
	return dom;
}
}
