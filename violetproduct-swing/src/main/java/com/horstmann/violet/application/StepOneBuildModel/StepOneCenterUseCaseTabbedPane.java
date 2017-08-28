package com.horstmann.violet.application.StepOneBuildModel;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;



public class StepOneCenterUseCaseTabbedPane extends JTabbedPane{
	private List<JPanel> usecaseDiagramTabbedPane;
	private MainFrame mainFrame;
	/*
	 * ���4����ͬ����ͼ��TabbedPane
	 */
	public StepOneCenterUseCaseTabbedPane(MainFrame mainFrame)
	{
		    usecaseDiagramTabbedPane = new ArrayList<JPanel>();
		    this.mainFrame = mainFrame;
		    this.addChangeListener(new MyListener());
//		    listen();
				
	}
	/*
	 * ͨ������Iworkspace������ȷ�������ĸ�TabbedPane�������ͼ��
	 */
    public JPanel getUMLTabbedPane(IWorkspace workspace, int size)
    {
    	//������������
    	//1���½�:�ж��ǲ���������N.XXX����
    	//2������:�ж��ļ�����׺�ǲ���.XXX.violet.xml

    	if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
    			||workspace.getTitle().toString().substring(2,4).equals("Us"))
		{
	        return this.getUsecaseDiagramTabbedPane().get(size);
		}

    	    return null;
    }
	public List<JPanel> getUsecaseDiagramTabbedPane() {
		return usecaseDiagramTabbedPane;
	}
	class MyListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			mainFrame.renewPanel();
		}
	}
	
}
