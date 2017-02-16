package com.horstmann.violet.application.StepTwoExpand;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepTwoVerificationOperation extends JPanel{
       private JLabel Verificationlabel;
       private JProgressBar VerificationProgressBar;
       private JButton startVerification;
       private JButton restartVerification;
       private JPanel gapPanel;
       private MainFrame mainFrame;
       public StepTwoVerificationOperation(MainFrame mainFrame)
       {
            this.mainFrame = mainFrame;
            init();
            this.setLayout(new GridBagLayout());
            this.add(Verificationlabel, new GBC(0, 0,  3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 0, 44));
            this.add(VerificationProgressBar,new GBC(0, 1, 3, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 0, 44));
            this.add(startVerification,new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 44, 10, 420));
            this.add(gapPanel, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));
            this.add(restartVerification, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 420, 10, 44));
       }
       public void init()
       {
    	   Verificationlabel = new JLabel("����ģ���б���ѡȡ��Ҫ������ģ��!");
    	   Verificationlabel.setFont(new Font("����",Font.PLAIN,16));
    	   
    	   VerificationProgressBar = new JProgressBar();
    	   VerificationProgressBar.setUI(new ProgressUI(VerificationProgressBar, Color.green));
    	   VerificationProgressBar.setPreferredSize(new Dimension(600, 30));
    	   
    	   startVerification = new JButton("��ʼ����");
//    	   startVerification.setFont(new Font("����",Font.PLAIN,10));
    	   restartVerification = new JButton("��������");
//    	   restartVerification.setFont(new Font("����",Font.PLAIN,10));
    	   gapPanel = new JPanel();
       }
       
}
