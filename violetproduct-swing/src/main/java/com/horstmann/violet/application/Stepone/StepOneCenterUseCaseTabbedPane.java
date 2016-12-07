package com.horstmann.violet.application.Stepone;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.horstmann.violet.workspace.IWorkspace;

public class StepOneCenterUseCaseTabbedPane extends JTabbedPane{
//	private JPanel sequenceDiagramTabbedPane;
//	private JPanel timingDiagramTabbedPane;
//	private JPanel markovDiagramTabbedPane;
	private List<JPanel> usecaseDiagramTabbedPane;
	/*
	 * ���4����ͬ����ͼ��TabbedPane
	 */
	public StepOneCenterUseCaseTabbedPane()
	{
////		sequenceDiagramTabbedPane=new JPanel();
////		timingDiagramTabbedPane=new JPanel();
////		markovDiagramTabbedPane=new JPanel();
		usecaseDiagramTabbedPane = new ArrayList<JPanel>();
////		sequenceDiagramTabbedPane.setBorder(BorderFactory.createLineBorder(Color.white));
////		sequenceDiagramTabbedPane.setBackground(Color.white);
//		
////		timingDiagramTabbedPane.setBorder(BorderFactory.createLineBorder(Color.white));
////		timingDiagramTabbedPane.setBackground(Color.white);
////		
////		markovDiagramTabbedPane.setBorder(BorderFactory.createLineBorder(Color.white));
////		markovDiagramTabbedPane.setBackground(Color.white);
//		
////		sequenceDiagramTabbedPane.setLayout(new GridBagLayout());
////		timingDiagramTabbedPane.setLayout(new GridBagLayout());
////		markovDiagramTabbedPane.setLayout(new GridBagLayout());
////		usecaseDiagramTabbedPane.setLayout(new GridBagLayout());
//		    this.add("˳��ͼ",sequenceDiagramTabbedPane);
////		this.add("ʱ��ͼ",timingDiagramTabbedPane);
////		this.add("Markov",markovDiagramTabbedPane);

	}
	/*
	 * ͨ������Iworkspace������ȷ�������ĸ�TabbedPane�������ͼ��
	 */
    public JPanel getUMLTabbedPane(IWorkspace workspace, int size)
    {
    	//������������
    	//1���½�:�ж��ǲ���������N.XXX����
    	//2������:�ж��ļ�����׺�ǲ���.XXX.violet.xml
//       if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
//    		   ||workspace.getTitle().toString().substring(2,4).equals("Se"))
//    	{
//    		return this.getSequenceDiagramTabbedPane();
//        }
    	if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
    			||workspace.getTitle().toString().substring(2,4).equals("Us"))
		{
	        return this.getUsecaseDiagramTabbedPane().get(size);
		}
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
//	public JPanel getSequenceDiagramTabbedPane() {
//		return sequenceDiagramTabbedPane;
//	}
//	public void setSequenceDiagramTabbedPane(JPanel sequenceDiagramTabbedPane) {
//		this.sequenceDiagramTabbedPane = sequenceDiagramTabbedPane;
//	}
//	public JPanel getTimingDiagramTabbedPane() {
//		return timingDiagramTabbedPane;
//	}
//	public JPanel getMarkovDiagramTabbedPane() {
//		return markovDiagramTabbedPane;
//	}	
	public List<JPanel> getUsecaseDiagramTabbedPane() {
		return usecaseDiagramTabbedPane;
	}
	
}
