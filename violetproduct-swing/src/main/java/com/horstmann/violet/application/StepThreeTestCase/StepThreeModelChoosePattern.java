package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepThreeModelChoosePattern extends JPanel{
	private JLabel label;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JRadioButton jRadioButton;
	private JRadioButton jRadioButton1;
	private ButtonGroup buttonGroup;
	private JPanel label1Panel;
	private JPanel label2Panel;
	private JPanel panel;
	private JPanel panel1;
	private JPanel panel2;
	private JTextField textField1;
	private JTextField textField2;
	
	private MainFrame mainFrame;
    public StepThreeModelChoosePattern(MainFrame mainFrame)
    {
    	this.mainFrame = mainFrame;
    	init();
    	this.setBackground(new Color(233,233,233));
    	this.setLayout(new GridBagLayout());
    	this.add(label, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(15, 10, 30, 0));
    	this.add(jRadioButton,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 40, 20, 0));
    	this.add(label1Panel,new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 60, 20, 0));
    	this.add(jRadioButton1,new GBC(0, 3).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 40, 20, 0));
    	this.add(label2Panel,new GBC(0, 4).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 60, 0, 0));
    	this.add(new JPanel(),new GBC(0, 5).setFill(GBC.BOTH).setWeight(1, 1));
    }
    private void init()
    {
    	label = new JLabel("��ѡ��ɿ��Բ����������ɷ�ʽ");
    	label1 = new JLabel("�����ְ���㷨���ɿɿ��Բ�������(������Ǩ�Ƹ��ʽ��к���Ǩ��ѡ�������ɿɿ��Բ�������)");
    	label2 = new JLabel("�����û����������ɿ���ָ�������Ŷ�����������㵱ǰ�����ָ����������ٿɿ��Բ������ݸ���,");
    	label3 = new JLabel("�ռ�����·����Ϊ�����ɿ��Բ������ݲ������ÿ��·����·������,����ÿ��·���ĸ��ʺ��û������");
    	label4 = new JLabel("�ɿ��Բ������ݸ�����������ɿ��Բ������ݡ�");
    	label5 = new JLabel("��������ɿ���ָ��:");
    	label6 = new JLabel("���Ŷ�:");
    	jRadioButton = new JRadioButton("����ģ�����ƶ�����");
    	jRadioButton1 = new JRadioButton("�Զ���ɿ��Բ������ݸ�������");
    	buttonGroup = new ButtonGroup();
    	
    	label1Panel = new JPanel();
    	label2Panel = new JPanel();
    	panel = new JPanel();
    	panel1 = new JPanel();
    	panel2 = new JPanel();
    	
    	textField1 = new JTextField();
    	textField2 = new JTextField();
    	
    	buttonGroup.add(jRadioButton);
    	buttonGroup.add(jRadioButton1);
    	label.setFont(new Font("΢���ź�", Font.PLAIN, 18));
    	label1.setFont(new Font("΢���ź�", Font.PLAIN, 16));
    	label2.setFont(new Font("΢���ź�", Font.PLAIN, 16));
    	label3.setFont(new Font("΢���ź�", Font.PLAIN, 16));
    	label4.setFont(new Font("΢���ź�", Font.PLAIN, 16));
    	label5.setFont(new Font("΢���ź�", Font.PLAIN, 16));
    	label6.setFont(new Font("΢���ź�", Font.PLAIN, 16));
    	jRadioButton.setFont(new Font("΢���ź�", Font.PLAIN, 18));
    	jRadioButton1.setFont(new Font("΢���ź�", Font.PLAIN, 18));
    	
    	label1Panel.setBackground(new Color(233,233,233));
    	label2Panel.setBackground(new Color(233,233,233));
    	panel.setBackground(new Color(233,233,233));
    	panel1.setBackground(new Color(233,233,233));
    	
    	label1Panel.setLayout(new GridLayout(1, 1));
    	label1Panel.add(label1);
    	
    	panel2.setLayout(new GridBagLayout());
    	panel2.add(label6,new GBC(0, 0).setFill(GBC.BOTH).setWeight(0, 0));
    	panel2.add(textField2,new GBC(1, 0).setFill(GBC.BOTH).setWeight(0.1, 0));
    	
    	label2Panel.setLayout(new GridBagLayout());
    	label2Panel.add(label2, new GBC(0,0,3,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
    	label2Panel.add(label3, new GBC(0,1,3,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
    	label2Panel.add(label4, new GBC(0,2,3,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));

    	label2Panel.add(label5, new GBC(0, 3, 1, 1).setFill(GBC.BOTH).setWeight(0, 0));
    	label2Panel.add(textField1, new GBC(1, 3, 1, 1).setFill(GBC.BOTH).setWeight(0.1, 0));
    	label2Panel.add(panel, new GBC(2, 3, 1, 1).setFill(GBC.BOTH).setWeight(0.9, 0));
    	
    	label2Panel.add(panel2,new GBC(0, 4, 1, 1).setFill(GBC.BOTH).setWeight(0, 0));
    	label2Panel.add(panel1, new GBC(2, 4, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));		
    }
    private void listen()
    {
    	jRadioButton.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    	jRadioButton1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    	jRadioButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    	
    	jRadioButton1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    	
    	textField1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    	
    	textField2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    }
}
