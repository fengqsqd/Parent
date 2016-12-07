package com.horstmann.violet.application.gui;

import java.awt.GridBagLayout;
import java.io.SequenceInputStream;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.horstmann.violet.workspace.IWorkspace;

public class StepOneCenterTabbedPane extends JTabbedPane{
	private JPanel sequenceDiagramTabbedPane;
	private JPanel timingDiagramTabbedPane;
	private JPanel stateDiagramTabbedPane;
	private JPanel usecaseDiagramTabbedPane;
	/*
	 * ���4����ͬ����ͼ��TabbedPane
	 */
	public StepOneCenterTabbedPane()
	{
		sequenceDiagramTabbedPane=new JPanel();
		timingDiagramTabbedPane=new JPanel();
		stateDiagramTabbedPane=new JPanel();
		usecaseDiagramTabbedPane=new JPanel();
		sequenceDiagramTabbedPane.setLayout(new GridBagLayout());
		timingDiagramTabbedPane.setLayout(new GridBagLayout());
		stateDiagramTabbedPane.setLayout(new GridBagLayout());
		usecaseDiagramTabbedPane.setLayout(new GridBagLayout());
		this.addTab("˳��ͼ",sequenceDiagramTabbedPane);
		this.add("ʱ��ͼ",timingDiagramTabbedPane);
		this.add("״̬ͼ",stateDiagramTabbedPane);
		this.add("����ͼ",usecaseDiagramTabbedPane);
		
	}
	/*
	 * ͨ������Iworkspace������ȷ�������ĸ�TabbedPane�������ͼ��
	 */
    public JPanel getUMLTabbedPane(IWorkspace workspace)
    {
    	//������������
    	//1���½�:�ж��ǲ���������N.XXX����
    	//2������:�ж��ļ�����׺�ǲ���.XXX.violet.xml
       if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
    		   ||workspace.getTitle().toString().substring(2,4).equals("Se"))
    	{
    		return this.getSequenceDiagramTabbedPane();
        }
    	if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
    			||workspace.getTitle().toString().substring(2,4).equals("Us"))
		{
	        return this.getUsecaseDiagramTabbedPane();
		}
    	if(workspace.getTitle().toString().endsWith(".timing.violet.xml")
    ||workspace.getTitle().toString().substring(2,4).equals("Ti"))
		{
	        return this.getTimingDiagramTabbedPane();
		}
    	if(workspace.getTitle().toString().endsWith(".state.violet.xml")
    			||workspace.getTitle().toString().substring(2,4).equals("St"))
		{
	        return this.getStateDiagramTabbedPane();
		}
    	    return null;
    }
	public JPanel getSequenceDiagramTabbedPane() {
		return sequenceDiagramTabbedPane;
	}
	public void setSequenceDiagramTabbedPane(JPanel sequenceDiagramTabbedPane) {
		this.sequenceDiagramTabbedPane = sequenceDiagramTabbedPane;
	}
	public JPanel getTimingDiagramTabbedPane() {
		return timingDiagramTabbedPane;
	}
	public JPanel getStateDiagramTabbedPane() {
		return stateDiagramTabbedPane;
	}	
	public JPanel getUsecaseDiagramTabbedPane() {
		return usecaseDiagramTabbedPane;
	}
	
}
