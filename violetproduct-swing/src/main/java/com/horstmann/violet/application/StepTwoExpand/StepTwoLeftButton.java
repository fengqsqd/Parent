package com.horstmann.violet.application.StepTwoExpand;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.horstmann.violet.application.gui.MainFrame;

public class StepTwoLeftButton extends JPanel{
      private JLabel expandlLabel;
      private JLabel evaluatelLabel;
      private JLabel exchangeLabel;
      private List<String> nameList; //��֤
      private List<String> modelNameList; //ģ��ת��
      private MainFrame mainFrame;
      public StepTwoLeftButton(MainFrame mainFrame)
      {
    	  this.mainFrame = mainFrame;
    	  init();
    	  this.setBackground(new Color(200,200,200));
    	  this.setLayout(new GridLayout(9, 1));
    	  this.add(expandlLabel);
          this.add(evaluatelLabel);
          this.add(exchangeLabel);
      }
      private void init()
      {
    	  expandlLabel = new JLabel("ģ����չ",JLabel.CENTER);
          evaluatelLabel = new JLabel("ģ������",JLabel.CENTER);
          exchangeLabel = new JLabel("Markovת��",JLabel.CENTER);
          expandlLabel.setFont(new Font("΢���ź�",Font.BOLD,16));
          evaluatelLabel.setFont(new Font("΢���ź�",Font.PLAIN,16));
          exchangeLabel.setFont(new Font("΢���ź�",Font.PLAIN,16));
          
          expandlLabel.setHorizontalTextPosition(JLabel.CENTER);
          expandlLabel.setVerticalTextPosition(JLabel.BOTTOM);
          
          evaluatelLabel.setHorizontalTextPosition(JLabel.CENTER);
          evaluatelLabel.setVerticalTextPosition(JLabel.BOTTOM);
          
          exchangeLabel.setHorizontalTextPosition(JLabel.CENTER);
          exchangeLabel.setVerticalTextPosition(JLabel.BOTTOM);
          
          expandlLabel.setIcon(new ImageIcon("resources/icons/64x64/expand.png"));
          evaluatelLabel.setIcon(new ImageIcon("resources/icons/64x64/evaluate.png"));
          exchangeLabel.setIcon(new ImageIcon("resources/icons/64x64/change.png"));
          
          nameList = new ArrayList<String>();
          nameList.add("use_case1");
          
          modelNameList = new ArrayList<String>();
          modelNameList.add("usecase1");
          labelListener();
      }
      private void labelListener()
      {
    	  expandlLabel.addMouseListener(new MouseAdapter() {
    		  @Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			//���ּӴ�
    			  expandlLabel.setFont(new Font("΢���ź�",Font.BOLD,16));
    	          evaluatelLabel.setFont(new Font("΢���ź�",Font.PLAIN,16));
    	          exchangeLabel.setFont(new Font("΢���ź�",Font.PLAIN,16));
    	          
    			  mainFrame.getsteponebottmopanel().getNextbutton().doClick();
    		}
		});
    	  evaluatelLabel.addMouseListener(new MouseAdapter() {
    		  @Override
    		public void mousePressed(MouseEvent e) {
    			// TODO Auto-generated method stub
    			  updatePanel(); 
    			  //���ּӴ�
    			  expandlLabel.setFont(new Font("΢���ź�",Font.PLAIN,16));
    	          evaluatelLabel.setFont(new Font("΢���ź�",Font.BOLD,16));
    	          exchangeLabel.setFont(new Font("΢���ź�",Font.PLAIN,16));
    			  //�б�����
	  
    			  mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoVerificationTabbedPane());
    			  mainFrame.getCenterTabPanel().updateUI();
    			  
    			  mainFrame.getpanel().add(mainFrame.getStepTwoVerificationOperation());
    			  mainFrame.getpanel().updateUI();
    		}
		});
    	  exchangeLabel.addMouseListener(new MouseAdapter() {
                  @Override
                public void mousePressed(MouseEvent e) {
                	// TODO Auto-generated method stub
                	updatePanel();
                	
                	 //���ּӴ�
      			  expandlLabel.setFont(new Font("΢���ź�",Font.PLAIN,16));
      	          evaluatelLabel.setFont(new Font("΢���ź�",Font.PLAIN,16));
      	          exchangeLabel.setFont(new Font("΢���ź�",Font.BOLD,16));
      	          
      	          mainFrame.setModelNameList(modelNameList);
      	          
      	          mainFrame.getpanel().add(mainFrame.getStepTwoExchangeOperation());
      	          
      	          mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoExchangeTabbedPane());
      	          
                }
		});
      }
      private void updatePanel()
      {  
    	  mainFrame.getOpreationPart().removeAll();
    	  mainFrame.getCenterTabPanel().removeAll();
    	  mainFrame.getpanel().removeAll();
      }
}
