package com.horstmann.violet.application.StepFourTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepFourBottom extends JPanel{
	private MainFrame mainFrame;
	private JButton backButton;
	private JButton nextbutton;
	private JPanel leftpanel;
	
	private int stepThree;
	public StepFourBottom(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
		
	}
	public void init()
	{
		initButton();
		this.setBackground(new Color(233,233,233));
		GridBagLayout layout=new GridBagLayout();
		this.setLayout(layout);
		layout.setConstraints(leftpanel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 1));
		layout.setConstraints(backButton, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(15, 0, 15, 10));
		layout.setConstraints(nextbutton, new GBC(2, 0, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(15, 0, 15, 10));
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
		backButton.setPreferredSize(new Dimension(88, 34));
		backButton.setMinimumSize(new Dimension(88, 34));
		backButton.setMaximumSize(new Dimension(88, 34));
		nextbutton.setPreferredSize(new Dimension(88, 34));
		nextbutton.setMinimumSize(new Dimension(88, 34));
		nextbutton.setMaximumSize(new Dimension(88, 34));
		leftpanel = new JPanel();
		backButton.setText("��һ��");
		nextbutton.setText("�˳�");
		backButton.setFont(new Font("����",Font.PLAIN,12));
		nextbutton.setFont(new Font("����",Font.PLAIN,12));
        leftpanel.setBackground(new Color(233,233,233)); 
	}
	public void buttonlisten()
	{
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainFrame.getTitlePanel().getBigTitle().setText("������:�ɿ��Բ�����������");
				mainFrame.getTitlePanel().getSmallTitle().setText("�Եڶ������ɵ�Mavkovģ�ͽ������ɲ�������");			
				
				mainFrame.getOpreationPart().removeAll();
				mainFrame.getOpreationPart().setLayout(new GridLayout(1, 1));
				mainFrame.getOpreationPart().add(mainFrame.getStepThreeJScrollPane());
				mainFrame.getOpreationPart().updateUI();
				
				mainFrame.getconsolepartPanel().removeAll();
				mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
				mainFrame.getconsolepartPanel().add(mainFrame.getStepThreeBottom());
				
				stepThree = mainFrame.getStepThreeLeftButton().getStepThree();
				JPanel mainPanel = mainFrame.getMainPanel();
				switch (stepThree) {
				case 1:
					mainFrame.getStepTwoExpandBottom().getNextbutton().doClick();
					break;
                case 2:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 15));
    					}
    				}
    				mainFrame.getinformationPanel().setVisible(false);
    				mainFrame.getReduceOrEnlargePanel().setVisible(false);
    				if(mainFrame.getStepThreeChoosePattern().getselectString().equals("�Զ������������������"))
    				{
    					if(mainFrame.getStepThreeChoosePattern().getConfidence() == null || 
    							mainFrame.getStepThreeChoosePattern().getReliabilityIndex() == null)
    					{
    						          //������ӵ�����
    					}
    				}
    				mainFrame.getpanel().setVisible(false);
    				mainFrame.getTimeMarkovFileRadio().initFile();
    				mainFrame.getNoTimeMarkovFileRadion().initFile();
    				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeChooseModelPattern());
    				mainFrame.renewPanel();
					break;
                case 3:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getNoTimeCaseOperation());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
					mainFrame.renewPanel();
					break;
                case 4:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getNoTimeCaseOperation1());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
					mainFrame.renewPanel();
	                break;
                case 5:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
    				mainFrame.getpanel().add(mainFrame.getTimeExpandOperation());
    				mainFrame.getCenterTabPanel().add(mainFrame.getTimeExpandTabbedPane());
    				mainFrame.renewPanel();
	                break;
                case 6:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getTimeCaseOperation());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
					mainFrame.renewPanel();
	                break;
                case 7:
                	mainFrame.getpanel().removeAll();
            		mainFrame.getCenterTabPanel().removeAll();
                	
                	for(int i = 0;i < mainPanel.getComponentCount();i++)
    				{
    					if(mainPanel.getComponent(i).equals(mainFrame.getworkpanel()))
    					{
    						mainPanel.remove(mainPanel.getComponent(i));
    						mainPanel.add(mainFrame.getworkpanel(),new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0.98).setInsets(10, 15, 20, 0));
    					}
    				}
    				mainFrame.getReduceOrEnlargePanel().setVisible(true);
    				mainFrame.getpanel().setVisible(true);
					mainFrame.getpanel().add(mainFrame.getTimeCaseOperation1());
					mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeNoTimeTabbedPane());
					mainFrame.renewPanel();
				default:
					break;
				}
			}
		});
		nextbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	public JButton getBackButton() {
		return backButton;
	}
}
