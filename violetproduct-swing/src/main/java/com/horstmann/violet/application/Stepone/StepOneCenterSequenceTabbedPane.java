package com.horstmann.violet.application.Stepone;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.horstmann.violet.workspace.IWorkspace;

public class StepOneCenterSequenceTabbedPane extends JTabbedPane{
	private List<JPanel> sequenceDiagramTabbedPane;
	private JPanel sequencePanel;
//	private JPanel timingDiagramTabbedPane;
//	private JPanel markovDiagramTabbedPane;
//	private JPanel usecaseDiagramTabbedPane;
	/*
	 * ���4����ͬ����ͼ��TabbedPane
	 */
	public StepOneCenterSequenceTabbedPane()
	{
//		this.setUI(new EclipseTabbedPaneUI());
		sequenceDiagramTabbedPane = new ArrayList<JPanel>();
//		timingDiagramTabbedPane=new JPanel();
//		markovDiagramTabbedPane=new JPanel();
//		usecaseDiagramTabbedPane=new JPanel();
//		sequenceDiagramTabbedPane.setBorder(BorderFactory.createLineBorder(Color.white));
//		sequenceDiagramTabbedPane.setBackground(Color.white);
//		timingDiagramTabbedPane.setBorder(BorderFactory.createLineBorder(Color.white));
//		timingDiagramTabbedPane.setBackground(Color.white);
//		

//		sequencePanel.setBackground(new Color(233,233,233));
//		sequenceDiagramTabbedPane.setLayout(new GridBagLayout());
//		timingDiagramTabbedPane.setLayout(new GridBagLayout());
//		markovDiagramTabbedPane.setLayout(new GridBagLayout());
//		usecaseDiagramTabbedPane.setLayout(new GridBagLayout());
//		this.add("ʱ��ͼ",timingDiagramTabbedPane);
//		this.add("Markov",markovDiagramTabbedPane);
//		this.add("����ͼ",usecaseDiagramTabbedPane);
		
	}
	/*
	 * ͨ������Iworkspace������ȷ�������ĸ�TabbedPane�������ͼ��
	 */
    public JPanel getUMLTabbedPane(IWorkspace workspace , int size)
    {
    	//������������
    	//1���½�:�ж��ǲ���������N.XXX����
    	//2������:�ж��ļ�����׺�ǲ���.XXX.violet.xml
       if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
    		   ||workspace.getTitle().toString().substring(2,4).equals("Se"))
    	{
    		return sequenceDiagramTabbedPane.get(size);
        }
//    	if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
//    			||workspace.getTitle().toString().substring(2,4).equals("Us"))
//		{
//	        return this.getUsecaseDiagramTabbedPane();
//		}
//    	if(workspace.getTitle().toString().endsWith(".timing.violet.xml")
//    ||workspace.getTitle().toString().substring(2,4).equals("Ti"))
//		{
//	        return this.getTimingDiagramTabbedPane();
//		}
//    	if(workspace.getTitle().toString().endsWith(".markov.violet.xml")
//    			||workspace.getTitle().toString().substring(2,4).equals("Ma"))
//		{
//	        return this.getMarkovDiagramTabbedPane();
//		}
    	    return null;
    }
	public List<JPanel> getSequenceDiagramTabbedPane() {
//		for(JPanel panel : sequenceDiagramTabbedPane)
//			panel.setLayout(new GridBagLayout());
		return sequenceDiagramTabbedPane;
	}
//	public void setSequenceDiagramTabbedPane(JPanel sequenceDiagramTabbedPane) {
//		this.sequenceDiagramTabbedPane = sequenceDiagramTabbedPane;
//	}
//	public JPanel getTimingDiagramTabbedPane() {
//		return timingDiagramTabbedPane;
//	}
//	public JPanel getMarkovDiagramTabbedPane() {
//		return markovDiagramTabbedPane;
//	}	
//	public JPanel getUsecaseDiagramTabbedPane() {
//		return usecaseDiagramTabbedPane;
//	}
	
}
