package com.horstmann.violet.application.SteponeBuildModel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;

import com.horstmann.violet.application.consolepart.ConsoleMessageTabbedPane;
import com.horstmann.violet.application.consolepart.ConsolePart;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class HeadTitle extends JPanel{
	private MainFrame mainFrame;
	private JButton homebutton;
	private JPanel rightPanel;
	private JPanel leftpicturePanel;
	private JPanel rightpicturePanel;
	private JPanel buttonPanel;
	
	private JLabel firstLabel;
	private JLabel secondLabel;
	private JLabel thirdLabel;
	private JLabel fourthLabel;
	
	private JLabel sequenceLabel;
	public HeadTitle(MainFrame mainFrame){
		this.mainFrame = mainFrame;
		init();
	}
	
	public void init() {
		initButton();
	    GridBagLayout layout = new GridBagLayout();
	    this.setLayout(layout);
	    this.add(leftpicturePanel);
	    this.add(rightPanel);
	    layout.setConstraints(leftpicturePanel, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.4, 1));
	    layout.setConstraints(rightPanel, new GBC(1, 0, 1, 1).setFill(GBC.BOTH).setWeight(0.6, 1));
		SetButtonListener();
		
	}
	public void initButton() {	
		
		firstLabel = new JLabel();
		secondLabel = new JLabel();
		thirdLabel = new JLabel();
		fourthLabel = new JLabel();
		homebutton =new JButton();
		rightPanel = new JPanel();
		Icon openIcon = new ImageIcon("resources/icons/72x72/start.png");
		homebutton.setIcon(openIcon);
		homebutton.setHorizontalTextPosition(SwingConstants.CENTER);
		homebutton.setVerticalTextPosition(SwingConstants.BOTTOM);
		homebutton.setFocusPainted(false);
		homebutton.setMargin(new Insets(0, 0, 0, 0));
		homebutton.setContentAreaFilled(false);
		homebutton.setBorderPainted(false);
		homebutton.setFocusable(true);
		rightpicturePanel = new ImagePanel("resources\\flowchart.png",0,0);
		leftpicturePanel = new JPanel(){
		public void paint(Graphics g){
            super.paint(g);
            java.awt.Rectangle rect = this.getBounds();
            int width = (int) rect.getWidth() - 1;
            int height = (int) rect.getHeight() - 1;
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(1f));
            g2.setColor(Color.white);
            g2.drawLine(30,235,width - 30,235);
          }
		};
		leftpicturePanel.setBackground(new Color(218,218,218));
		rightpicturePanel.setBackground(Color.white);
		rightPanel.setBackground(Color.white);
		rightPanel.setLayout(new GridBagLayout());
		rightPanel.add(rightpicturePanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
		rightPanel.add(homebutton,new GBC(0, 1).setFill(GBC.VERTICAL).setInsets(10, 0, 20, 0));
		
		firstLabel.setText("��һ��:uml��ģ");
		secondLabel.setText("�ڶ���:umlģ��ת��������Ʒ���ģ��");
		thirdLabel.setText("������:������������");
		fourthLabel.setText("���Ĳ�:����������֤");
		
		firstLabel.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		secondLabel.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		thirdLabel.setFont(new Font("΢���ź�", Font.PLAIN, 18));
		fourthLabel.setFont(new Font("΢���ź�", Font.PLAIN, 18));

		leftpicturePanel.setLayout(null);
		leftpicturePanel.add(firstLabel);
		leftpicturePanel.add(secondLabel);
		leftpicturePanel.add(thirdLabel);
		leftpicturePanel.add(fourthLabel);
		
		firstLabel.setBounds(30, 20, 200, 60);
		secondLabel.setBounds(30, 90, 350, 60);
		thirdLabel.setBounds(30, 160, 200, 60);
		fourthLabel.setBounds(30, 230, 200, 60);

//		sequenceLabel = new JLabel("˳��ͼ�ǽ�������ϵ��ʾΪһ����άͼ��������ʱ���ᣬʱ���������������졣�������������Э���и������������Ԫ��ɫ��");
		sequenceLabel = new JLabel("��д������Ǩ�Ƹ��ʣ���Ϊ��ǰ����ִ����������������������ִ�и��ʡ�");
		sequenceLabel.setFont(new Font("����", Font.PLAIN, 16));
	}
	private void SetButtonListener() {
		homebutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updatePanel();
				//��ӱ���
				JPanel labelpanel=mainFrame.getStepJLabel();
				JPanel picturePanel = new JPanel();				
				JPanel leftlabelpanel = new JPanel();
				JPanel panel = new ImagePanel("resources/icons/72x72/steponeimage.png", 0, 0);
				Icon icon = new ImageIcon("resources/icons/72x72/violet.png");
				JLabel jLabel2 = new JLabel();
				jLabel2.setIcon(icon);
				JLabel jLabel=new JLabel();
				jLabel.setText("��һ��:uml����ģ��");
				jLabel.setFont(new Font("΢���ź�", Font.PLAIN, 22));
				jLabel.setForeground(Color.black);
				leftlabelpanel.setLayout(new GridBagLayout());
				leftlabelpanel.setBackground(new Color(222,222,222));
				picturePanel.setBackground( new Color(222,222,222));
				picturePanel.setLayout(new GridLayout(1,1));
				picturePanel.add(jLabel2);
				JLabel jLabel1 = new JLabel();
				jLabel1.setText("��umlͼ�ν�����ȷ���ơ�");
				jLabel1.setFont(new Font("����", Font.PLAIN, 16));
				leftlabelpanel.add(picturePanel, new GBC(0,0,1,2).setFill(GBC.BOTH));
				leftlabelpanel.add(jLabel, new GBC(1,0,1,1).setFill(GBC.BOTH).setInsets(10, 0, 0, 250));
				leftlabelpanel.add(jLabel1, new GBC(1,1,1,1).setFill(GBC.BOTH).setInsets(17, 28, 0, 0));
				labelpanel.setLayout(new GridBagLayout());
				labelpanel.removeAll();   //�Ƴ�ԭ�б�ǩ
				labelpanel.validate();
				labelpanel.add(leftlabelpanel,new GBC(0, 0).setWeight(0, 0).setInsets(15,30,30,80));
				labelpanel.add(panel,new GBC(1, 0).setFill(GBC.BOTH).setWeight(1, 1));
				labelpanel.revalidate();	
				
				//�޸Ĳ�������
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getOpreationPart().removeAll();
				mainFrame.getOpreationPart().setLayout(new GridLayout(1, 1));
				mainFrame.getOpreationPart().add(mainFrame.getsteponeButton());
				mainFrame.getOpreationPart().updateUI();
				
				//��Ӳ�������
				mainFrame.getconsolepartPanel().removeAll();
				mainFrame.getconsolepartPanel().setLayout(new GridLayout(1, 1));
				mainFrame.getconsolepartPanel().add(mainFrame.getsteponebottmopanel());
				firstLabel.setFont(new Font("΢���ź�", Font.BOLD, 16));
				
				wakeupPanel();
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterSequenceTabbedPane());
				mainFrame.getCenterTabPanel().updateUI();

				mainFrame.getpanel().removeAll();
				mainFrame.getpanel().setLayout(new GridLayout(1, 1));
				mainFrame.getpanel().add(mainFrame.getOperationButton());
				mainFrame.getOperationButton().getOtherPanel().removeAll();
				mainFrame.getOperationButton().getOtherPanel().setLayout(new GridBagLayout());
				mainFrame.getOperationButton().getOtherPanel().add(sequenceLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,10,10,10));
				mainFrame.getpanel().updateUI();
				
				mainFrame.getinformationPanel().removeAll();
				mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
				mainFrame.getinformationPanel().add(mainFrame.getouOutputinformation());
				
				mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
				mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
				
			}
		});
	}

	private void updatePanel()
	{
		mainFrame.getStepJLabel().setVisible(true); //��ʼ����ͷ��������
		mainFrame.getconsolepartPanel().setVisible(true); 
		mainFrame.getOpreationPart().setVisible(true);
		mainFrame.getsteponebottmopanel().setVisible(true);
//		if(mainFrame.getsteponeButton().getbottompanel().getComponentCount() != 0)
//		mainFrame.getsteponeButton().getbottompanel().getComponent(0).setVisible(false);
	}
	 private void wakeupPanel()
	   	{
	   		mainFrame.getpanel().setVisible(true);
	   		mainFrame.getinformationPanel().setVisible(true);
	   		mainFrame.getbotoomJSplitPane(). setDividerLocation(0.5);
	   		mainFrame.getbotoomJSplitPane().setDividerSize(4);
	   		mainFrame.getReduceOrEnlargePanel().setVisible(true);
	   		mainFrame.getReduceOrEnlargePanel().removeAll();
	   		if(mainFrame.getStepOneCenterSequenceTabbedPane().getComponentCount() == 0)
	   		{
	   		mainFrame.getStepOneCenterSequenceTabbedPane().setVisible(false);
	   		mainFrame.getStepOneCenterUseCaseTabbedPane().setVisible(false);
	   		}
//	   		if(mainFrame.getsteponeButton().getbottompanel().getComponentCount() != 0)
//	   			mainFrame.getsteponeButton().getbottompanel().getComponent(0).setVisible(true);
	   	}

	public JButton getHomebutton() {
		return homebutton;
	}
	 
	
}
