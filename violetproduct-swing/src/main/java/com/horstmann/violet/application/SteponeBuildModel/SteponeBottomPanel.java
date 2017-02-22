package com.horstmann.violet.application.SteponeBuildModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class SteponeBottomPanel extends JPanel{
	private MainFrame mainFrame;
	private JButton backButton;
	private JButton nextbutton;
	private JPanel leftpanel;
	private JLabel stepTwoLabel;
	public SteponeBottomPanel(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
		
	}
	public void init()
	{
		initButton();
		this.setBackground(new Color(233,233,233));
		GridBagLayout layout=new GridBagLayout();
		this.setLayout(layout);
		layout.setConstraints(leftpanel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.94, 1));
		layout.setConstraints(backButton, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.03, 1).setInsets(20, 0, 20, 10));
		layout.setConstraints(nextbutton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.03, 1).setInsets(20, 0, 20, 20));
		this.add(backButton);
		this.add(nextbutton);
		this.add(leftpanel);
		buttonlisten();
	}
	public void initButton()
	{
		//��ʼ����ť
		backButton = new JButton();
		nextbutton = new JButton();
		leftpanel = new JPanel();
		backButton.setText("��һ��");
		nextbutton.setText("��һ��");
		backButton.setFont(new Font("����",Font.PLAIN,16));
		nextbutton.setFont(new Font("����",Font.PLAIN,16));
        leftpanel.setBackground(new Color(233,233,233)); 
	}
	public void buttonlisten()
	{
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updatePanelUI();
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getHeadTitle());
				mainFrame.getbotoomJSplitPane().setDividerSize(0);
				mainFrame.getCenterTabPanel().updateUI();
				mainFrame.getOpreationPart().updateUI();
				mainFrame.getStepJLabel().updateUI();
				mainFrame.getconsolepartPanel().updateUI();
				mainFrame.getinformationPanel().updateUI();
				mainFrame.getpanel().updateUI();
			}
		});
		nextbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initStepTwoPanel();
				mainFrame.getTitlePanel().getBigTitle().setText("�ڶ���:umlģ��ת����Markovģ��");
				mainFrame.getTitlePanel().getSmallTitle().setText("�Ե�һ��ģ�ͽ�����չ��������ת����Markov��ģ��");
				
				mainFrame.getStepTwoPanel().validate();
				mainFrame.getStepTwoPanel().revalidate();
				
				mainFrame.getOpreationPart().add(mainFrame.getStepTwoExpand());
				mainFrame.getOpreationPart().updateUI();
				
				mainFrame.getconsolepartPanel().add(mainFrame.getStepTwoBottomPanel());
				mainFrame.getconsolepartPanel().updateUI();
				
				mainFrame.getReduceOrEnlargePanel().add(mainFrame.getStepTwoCenterRightPanel());
				mainFrame.getReduceOrEnlargePanel().updateUI();
				
				mainFrame.getpanel().add(mainFrame.getStepTwoModelOperation());
				stepTwoLabel = new JLabel("������ģ�ͽ�����չ�����û���д��������ִ�и����Լ�ͬԴ���������ղ�ͬʹ��Ƶ�ʽ��������Աȴ��");
				stepTwoLabel.setFont(new Font("����", Font.PLAIN, 16));
				mainFrame.getOperationButton().getOtherPanel().setLayout(new GridBagLayout());
				mainFrame.getOperationButton().getOtherPanel().removeAll();
				mainFrame.getOperationButton().getOtherPanel().add(stepTwoLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,0,10,10));
				mainFrame.getpanel().updateUI();
				
				mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoModelExpandTabbedPane());

			}
		});
	}
	private void updatePanelUI()
	{
		mainFrame.getStepJLabel().setVisible(false);
		mainFrame.getconsolepartPanel().setVisible(false);
		mainFrame.getinformationPanel().setVisible(false);
        mainFrame.getpanel().setVisible(false);
		mainFrame.getOpreationPart().setVisible(false);
		mainFrame.getReduceOrEnlargePanel().setVisible(false);
	}
	private void initStepTwoPanel()
	{
		mainFrame.getStepTwoPanel().setVisible(true);
		mainFrame.getStepTwoPanel().setBackground(new Color(200,200,200));
		mainFrame.getStepTwoPanel().setLayout(new GridLayout(1, 1));
		
		mainFrame.getOpreationPart().removeAll();
		mainFrame.getOpreationPart().setLayout(new GridLayout(1, 1));
		
		mainFrame.getconsolepartPanel().removeAll();
		mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
		
		mainFrame.getReduceOrEnlargePanel().removeAll();
		mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
		
		mainFrame.getpanel().removeAll();
		mainFrame.getpanel().setLayout(new GridLayout(1, 1));
		
		mainFrame.getCenterTabPanel().removeAll();
	}
	public JButton getNextbutton() {
		return nextbutton;
	}
	
	
}
