package com.horstmann.violet.application.StepFourTestCase;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.wujun.TDVerification.GB;
import com.horstmann.violet.workspace.IWorkspace;

public class StepFourTabbedPane extends JTabbedPane{
	private MainFrame mainFrame;
	private JPanel testCaseResults;
	private JPanel testCaseResport;
	private JPanel results;
	private JPanel wrongtestCaseResults;
	private JPanel wrongResults;
    private JScrollPane jScrollPane;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    
    private TestCaseReportTableHeaderPanel tableHeaderPanel;
    private TestCaseReportTableHeaderPanel wrongtableHeaderPanel;
	public StepFourTabbedPane(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		init();
		this.addTab("��֤��Ϣ",results);
		this.addTab("��֤����",jScrollPane2);
		this.addTab("��֤������Ϣ", wrongResults);
	}
	
	private void init()
	{
		testCaseResults = new JPanel();
		testCaseResults.setLayout(new GridLayout());
		testCaseResport = new JPanel();
		testCaseResport.setLayout(new GridBagLayout());
		wrongtestCaseResults = new JPanel();
		wrongtestCaseResults.setLayout(new GridLayout());
		jScrollPane = new JScrollPane(testCaseResults);
		jScrollPane.setBorder(null);
		jScrollPane2 = new JScrollPane(testCaseResport);
		jScrollPane2.setBorder(null);
		
		//�ܲ�������
		JPanel gapPanel = new JPanel();
		gapPanel.setMinimumSize(new Dimension(1, 1));
		gapPanel.setMaximumSize(new Dimension(1, 1));
		gapPanel.setPreferredSize(new Dimension(1, 1));
		
		tableHeaderPanel = new TestCaseReportTableHeaderPanel();
		results = new JPanel();
		results.setLayout(new BorderLayout());
		results.add(tableHeaderPanel,BorderLayout.NORTH);
		results.add(testCaseResults,BorderLayout.CENTER);

		//����Ĳ�������
		JPanel gapPanel1 = new JPanel();
		gapPanel1.setMinimumSize(new Dimension(1, 1));
		gapPanel1.setMaximumSize(new Dimension(1, 1));
		gapPanel1.setPreferredSize(new Dimension(1, 1));
		
		wrongtableHeaderPanel = new TestCaseReportTableHeaderPanel();
		wrongResults = new JPanel();
		wrongResults.setLayout(new BorderLayout());
		wrongResults.add(wrongtableHeaderPanel,BorderLayout.NORTH);
		wrongResults.add(wrongtestCaseResults, BorderLayout.CENTER);
		
		listen();
	}
    public void listen()
    {
    	this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    	JScrollBar HorizontalBar = jScrollPane.getHorizontalScrollBar();
		JScrollBar VerticalBar = jScrollPane.getVerticalScrollBar();
		HorizontalBar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		VerticalBar.addAdjustmentListener(new AdjustmentListener() {			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		
		JScrollBar HorizontalBar2 = jScrollPane2.getHorizontalScrollBar();
		JScrollBar VerticalBar2 = jScrollPane2.getVerticalScrollBar();
		HorizontalBar2.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
		VerticalBar2.addAdjustmentListener(new AdjustmentListener() {			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				mainFrame.renewPanel();
			}
		});
    	
    }
	public JPanel getTestCaseResults() {
		return testCaseResults;
	}

	public JPanel getTestCaseResport() {
		return testCaseResport;
	}

	public JPanel getWrongtestCaseResults() {
		return wrongtestCaseResults;
	}

	public JScrollPane getjScrollPane() {
		return jScrollPane;
	}

	public JScrollPane getjScrollPane3() {
		return jScrollPane3;
	}
	
}

