package com.horstmann.violet.application.StepTwoEvaluate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepTwoEvaluateOperation extends JPanel{
       private JLabel promptLabel;
       private JProgressBar evaluateBar;
       private JButton evaluateButton;
       private JButton reEvaluateButton;
       private JPanel gapPanel;
       
       private MainFrame mainFrame;
       private Map<String, ScenceTabelPanel> tabelResultMap;
       private Map<String, ScenceTabelPanel> caseTabelMap;
       private String modelName;
       private List list;
       public StepTwoEvaluateOperation(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   init();
    	   this.setLayout(new GridBagLayout());
    	   this.add(promptLabel, new GBC(0, 0,3,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 28, 10, 10));
    	   this.add(evaluateBar, new GBC(0, 1,3,1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 28, 10, 39));
    	   this.add(evaluateButton,new GBC(0, 2,1,1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 28, 10, 0));
    	   this.add(gapPanel,new GBC(1, 2,1,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
    	   this.add(reEvaluateButton,new GBC(2, 2,1,1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 39));
       }
       private void init()
       {
    	   promptLabel = new JLabel();
    	   promptLabel.setFont(new Font("����", Font.PLAIN, 16));
    	   promptLabel.setText("��ǰģ��Ϊ:Primary��������֤ģ�͵�һ���ԣ���ģ�͵Ĺ�һ�ԡ�ȷ�����Լ��ɴ��ԡ�");
    	   
    	   evaluateBar = new JProgressBar();
    	   evaluateBar.setUI(new ProgressUI(evaluateBar,Color.green));
    	   evaluateBar.setPreferredSize(new Dimension(800,30));
    	   
    	   evaluateButton = new JButton("��ʼ����");
    	   reEvaluateButton = new JButton("��������");
    	   gapPanel = new JPanel();
    	   
    	   buttonListen();
       }
       private void buttonListen()
       {
    	   evaluateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getStepTwoEvaluateTabbedPane().getEvaluateResults().removeAll();
				//������ӹ�һ����Ϣ
				tabelResultMap = mainFrame.getStepTwoModelOperation().getTabelResultMap();
				caseTabelMap = mainFrame.getStepTwoCaseOperation().getCaseTabelMap();
				modelName = mainFrame.getStepTwoModelOperation().getModel_name();
				StepTwoTabelPanel stepTwoTabelPanel = new StepTwoTabelPanel();
				stepTwoTabelPanel.getTitleLabel().setText(modelName+"ģ�͹�һ����֤");
				//��������г���Ǩ����Ϣ
				ScenceTabelPanel caseResultPanel = caseTabelMap.get(modelName); //������������
				StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
				stepTwoMatrixPanel.getTabelPanel().add(caseResultPanel);
				stepTwoMatrixPanel.getTitleLabel().setText(modelName+"����ģ���г�����������֮��Ϊ1");
				
				ScenceTabelPanel relationResultPanel = tabelResultMap.get(modelName); //����Ǩ�Ƹ���
				StepTwoMatrixPanel relationMatrixPanel = new StepTwoMatrixPanel();
				relationMatrixPanel.getTabelPanel().add(relationResultPanel);
				relationMatrixPanel.getTitleLabel().setText(modelName+"����ģ���к������Ǩ�Ƹ���֮��Ϊ1");
				
				stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				stepTwoTabelPanel.getTabelPanel().add(relationMatrixPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));
				
				mainFrame.getStepTwoEvaluateTabbedPane().getEvaluateResults().add(stepTwoTabelPanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				
				//���ȷ������Ϣ
				list = mainFrame.getStepTwoModelOperation().getVerList();
				
			}
		});
       }
}
