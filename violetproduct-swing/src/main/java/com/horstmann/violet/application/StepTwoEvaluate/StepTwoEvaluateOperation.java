package com.horstmann.violet.application.StepTwoEvaluate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import cn.edu.hdu.lab.dao.interfacedao.InterfaceIsogenySD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceSD;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.service.interfaces.Work;



import com.horstmann.violet.application.StepTwoModelExpand.ExchangeNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.EvaluateNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.ExpandNodeLabel;
import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.ScenceTabelPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoTabelPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;

public class StepTwoEvaluateOperation extends JPanel{
       private JLabel topLabel;
       private JProgressBar evaluateBar;
       private JButton evaluateButton;
       private JButton reEvaluateButton;
       private JPanel gapPanel;
       
       private MainFrame mainFrame;
       private Work worker;
       private EvaluateNodePanel evaluateNodePanel;
       
       private List list;
       
       private List<String> sceneNameList;
       private List<String> constraintName;
       private List<String> constraintValue;
       private String Model_Name;
       
       private JTree stepTwoEvaluateTree;
       private DefaultTreeModel EvaluateTreeModel;
   	   private DefaultMutableTreeNode EvaluateTreerootnode;
   	   
   	  private ScenceTabelPanel reachableTabel;
   	  private List<RoutePanel> routePanels;
       
       private boolean ispolarity = false;
       private boolean isconfirm = false;
       private boolean isreachable = false;
       
       private int progressBarIndex;
       
       private Callable<Integer> maincallable;
       private FutureTask<Integer> maintask;
   	   private Thread mainthread; 
       private Callable<Integer> callable1;
       private FutureTask<Integer> task1;
   	   private Thread thread1;
   	   private boolean isAlive = true;
       public StepTwoEvaluateOperation(MainFrame mainFrame)
       {
    	   this.mainFrame = mainFrame;
    	   this.stepTwoEvaluateTree = mainFrame.getStepTwoEvaluateTree().getjTree();
    	   this.EvaluateTreeModel =  mainFrame.getStepTwoEvaluateTree().getEvaluateTreeModel();
    	   this.EvaluateTreerootnode =  mainFrame.getStepTwoEvaluateTree().getEvaluateTreerootnode();
    	   init();
    	   this.setLayout(new GridBagLayout());
    	   this.add(topLabel, new GBC(0, 0,3,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
    	   this.add(evaluateBar, new GBC(0, 1,3,1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 15, 10, 15));
    	   this.add(evaluateButton,new GBC(0, 2,1,1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 15, 10, 0));
    	   this.add(gapPanel,new GBC(1, 2,1,1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 0));
    	   this.add(reEvaluateButton,new GBC(2, 2,1,1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
       }
       private void init()
       {
    	   evaluateNodePanel = new EvaluateNodePanel(mainFrame);
    	   topLabel = new JLabel();
    	   topLabel.setFont(new Font("����", Font.PLAIN, 16));
//    	   promptLabel.setText("��ǰģ��Ϊ:"+Model_Name+"������֤ģ�͵�һ���ԣ���ģ�͵Ĺ�һ�ԡ�ȷ�����Լ��ɴ��ԡ�");
    	   
    	   evaluateBar = new JProgressBar()           ;
    	   evaluateBar.setUI(new ProgressUI(evaluateBar,Color.green));
    	   evaluateBar.setPreferredSize(new Dimension(800,30));
    	   evaluateBar.setUI(new GradientProgressBarUI());
    	   
    	   evaluateButton = new JButton("��ʼ����");
    	   reEvaluateButton = new JButton("ֹͣ����");
    	   reEvaluateButton.setEnabled(false);
    	   gapPanel = new JPanel();
    	   
    	   sceneNameList = new ArrayList<String>();
    	   constraintName = new ArrayList<String>();
    	   constraintValue = new ArrayList<String>();
    	  
    	   buttonListen();
       }
       private void initThread()
       {
    	   progressBarIndex = 0;
    	   evaluateBar.setValue(0);
    	   
   		
    	   maincallable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				JPanel HomogeneityPanel = new JPanel();
				JPanel CertaintyPanel = new JPanel();
				JPanel AccessibilityPanel = new JPanel();
				HomogeneityPanel.setLayout(new GridBagLayout());
				CertaintyPanel.setLayout(new GridBagLayout());
				AccessibilityPanel.setLayout(new GridBagLayout());
				mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(false);
				mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(false);
				mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(false);
				
				mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(false);
				mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(false);
				evaluateButton.setEnabled(false);
				reEvaluateButton.setEnabled(true);
				
				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().updateUI();
				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().updateUI();
				mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().updateUI();
				
				mainFrame.getStepTwoExchangeTabbedPane().getExchangeResport().removeAll();
				mainFrame.getStepTwoExchangeTabbedPane().getExchangeResults().removeAll();
				mainFrame.renewPanel();
				
				sceneNameList.clear();
				constraintName.clear();
				constraintValue.clear();
				
				topLabel.removeAll();
				topLabel.setText("��ʼ��֤��һ����Ϣ....");
				Thread.sleep(200);
				//������ӹ�һ����Ϣ
				worker = mainFrame.getStepTwoModelOperation().getWorker();
				list = worker.transVerify(); 
				
				StepTwoTabelPanel stepTwoTabelPanel = new StepTwoTabelPanel();
				stepTwoTabelPanel.getTitleLabel().setText(Model_Name+"����ģ�ͺ������Ǩ�Ƹ��ʺ�Ϊ1.0�Լ����г�����ת�Ƹ���֮��Ϊ1.0,��һ����֤ͨ����");
				stepTwoTabelPanel.getTitleLabel().setFont(new Font("΢���ź�", Font.BOLD, 18));
				stepTwoTabelPanel.getTitlePanel().setBackground(new Color(211, 211, 211));
				stepTwoTabelPanel.getGapPanel().setBackground(new Color(211, 211, 211));
				
				topLabel.removeAll();
				topLabel.setText("������֤�����г�������....");
				while (progressBarIndex < 15) {	
					progressBarIndex++;
					evaluateBar.setValue(progressBarIndex);
					Thread.sleep(100);
				}
				//��������г���Ǩ����Ϣ
				JPanel caseResultPanel = mainFrame.getStepTwoCaseOperation().getMatrixPanel(); //������������
				StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
				stepTwoMatrixPanel.getTabelPanel().add(caseResultPanel);
				stepTwoMatrixPanel.getTitleLabel().setText(Model_Name+"����ģ���г�����������֮��Ϊ1");
				stepTwoMatrixPanel.getTitleLabel().setFont(new Font("΢���ź�",Font.BOLD,17));
				while (progressBarIndex < 30) {
					topLabel.removeAll();
					topLabel.setText("������֤����ģ���к������Ǩ�Ƹ���....");
					Thread.sleep(100);
					progressBarIndex++;
					evaluateBar.setValue(progressBarIndex);
				}
				JPanel relationResultPanel = mainFrame.getStepTwoModelOperation().getMatrixPanel(); //����Ǩ�Ƹ���
				StepTwoMatrixPanel relationMatrixPanel = new StepTwoMatrixPanel();
				relationMatrixPanel.getTabelPanel().add(relationResultPanel);
				relationMatrixPanel.getTitleLabel().setText(Model_Name+"����ģ���к������Ǩ�Ƹ���֮��Ϊ1");
				relationMatrixPanel.getTitleLabel().setFont(new Font("΢���ź�",Font.BOLD,17));
				ispolarity = true;
				stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				stepTwoTabelPanel.getTabelPanel().add(relationMatrixPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));
				
				HomogeneityPanel.add(stepTwoTabelPanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				HomogeneityPanel.add(new JPanel(),new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().add(HomogeneityPanel);
				mainFrame.renewPanel();
				
				topLabel.removeAll();
				topLabel.setText("��ʼ��֤ȷ������Ϣ....");
				Thread.sleep(500);
				
				//ȷ������Ϣ
				List<InterfaceIsogenySD> IISDList = mainFrame.getStepTwoModelOperation().getIISDList();
//				System.out.println("ģ������====================");
//				for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
//				{
//					System.out.println(interfaceIsogenySD.getUcName() + "������������: " + interfaceIsogenySD.getISDList().size());
//					for(InterfaceSD interfaceSD : interfaceIsogenySD.getISDList())
//					{
//						System.out.println("�������ƣ� " + interfaceSD.getName());
//					}
//				}
				for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
				{
					List<InterfaceSD> ISDList = interfaceIsogenySD.getISDList();
					for(InterfaceSD interfaceSD : ISDList)
					{
						topLabel.removeAll();
						topLabel.setText("������֤ "+interfaceSD.getName()+"����������Ϣ....");
						Thread.sleep(200);
						sceneNameList.add(interfaceSD.getName());
						constraintName.add("PostCondition");
						constraintValue.add(interfaceSD.getpostCondition());
					}
				}
				while (progressBarIndex < 65) {
					topLabel.removeAll();
					topLabel.setText("��������ȷ������Ϣ.....");
					Thread.sleep(100);
					progressBarIndex++;
					evaluateBar.setValue(progressBarIndex);
				}
				StepTwoTabelPanel confirmPanel = new StepTwoTabelPanel();
				confirmPanel.getTitleLabel().setText(Model_Name+"����ģ�������г�����������Ψһ��ȷ������֤ͨ����");
				confirmPanel.getTitleLabel().setFont(new Font("΢���ź�", Font.BOLD, 18));
				confirmPanel.getTitlePanel().setBackground(new Color(211, 211, 211));
				confirmPanel.getGapPanel().setBackground(new Color(211, 211, 211));
				
				isconfirm =true;
				ScenceTabelPanel scenceTabelPanel = new ScenceTabelPanel(sceneNameList, constraintName, constraintValue,mainFrame);
				StepTwoMatrixPanel confirmMatrixPanel = new StepTwoMatrixPanel();
				confirmMatrixPanel.getTabelPanel().add(scenceTabelPanel);
				confirmMatrixPanel.getTitleLabel().setText("������������");
				confirmMatrixPanel.getTitleLabel().setFont(new Font("΢���ź�", Font.BOLD, 17));
				confirmPanel.getTabelPanel().add(confirmMatrixPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				
				CertaintyPanel.add(confirmPanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
				CertaintyPanel.add(new JPanel(),new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().removeAll();
				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().add(CertaintyPanel);
//				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().add(confirmPanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
//				mainFrame.getStepTwoEvaluateTabbedPane().getCertaintyResults().add(new JPanel(),new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
				mainFrame.getStepTwoEvaluateTabbedPane().setSelectedIndex(1);
				mainFrame.renewPanel();
				
				topLabel.removeAll();
				topLabel.setText("��ʼ��֤�ɴ�����Ϣ....");
				Thread.sleep(500);
				
				while (progressBarIndex <= 100) {
					topLabel.removeAll();
					topLabel.setText("������������ִ�й�ϵ�ڽӾ���....");
					Thread.sleep(100);
					progressBarIndex++;
					evaluateBar.setValue(progressBarIndex);
				}
				//�ɴ�����Ϣ
//				list = mainFrame.getStepTwoModelOperation().getVerList();
				if((boolean)list.get(0)==true)
				{
					isreachable = true;
					StepTwoTabelPanel reachablePanel = new StepTwoTabelPanel();
					reachablePanel.getTitleLabel().setText(Model_Name+"����ģ����ÿ�����������Ա�ִ�е�,�ɴ�����֤ͨ����");
					confirmPanel.getTitleLabel().setFont(new Font("΢���ź�", Font.BOLD, 18));
					reachablePanel.getTitlePanel().setBackground(new Color(211, 211, 211));
					reachablePanel.getGapPanel().setBackground(new Color(211, 211, 211));
					//��ȡ·��
					Map<?,?> prePath=(Map<?,?>) list.get(3);
					Map<?,?> postPath=(Map<?, ?>) list.get(4);
					routePanels = new ArrayList<RoutePanel>();
					StepTwoMatrixPanel prePathPanel = getRoute(prePath, "��ʼ״̬��ÿ��������·��");
					StepTwoMatrixPanel postPathPanel = getRoute(postPath, "ÿ������������״̬��·��");
					reachablePanel.getTabelPanel().add(prePathPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
					reachablePanel.getTabelPanel().add(postPathPanel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));
					
					//��ȡ����
					List<String> nameList = new ArrayList<String>();
					nameList.add(Model_Name); //�������ͼģ������
					nameList.add("Initial");
					for(InterfaceIsogenySD interfaceIsogenySD : IISDList)
					{
						nameList.add(interfaceIsogenySD.getUcName());
					}
					nameList.add("Exit");
					reachableTabel = new ScenceTabelPanel(nameList, (int[][])list.get(2));
					StepTwoMatrixPanel reachableMatrixPanel = new StepTwoMatrixPanel();
					reachableMatrixPanel.getTabelPanel().add(reachableTabel);
					reachableMatrixPanel.getTitleLabel().setText("����ִ�й�ϵ�ڽӾ���");
					reachableMatrixPanel.getTitleLabel().setFont(new Font("΢���ź�", Font.BOLD, 17));
					reachablePanel.getTabelPanel().add(reachableMatrixPanel, new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 0));
					CaseSuspension caseSuspension = new CaseSuspension();
//					caseSuspension.getTitleLabel().setText("��������������,��ǰ����ִ���꣬Ȼִ����һ��������ִ�е��������");
//					reachablePanel.getTabelPanel().add(caseSuspension,new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 0));
					
					AccessibilityPanel.add(reachablePanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
					AccessibilityPanel.add(new JPanel(),new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
					
					mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().removeAll();
					mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().add(AccessibilityPanel);
					mainFrame.getStepTwoEvaluateTabbedPane().setSelectedIndex(2);
					
//					mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().add(reachablePanel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0));
//					mainFrame.getStepTwoEvaluateTabbedPane().getAccessibilityResults().add(new JPanel(),new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1));
					
					mainFrame.renewPanel();
				}
				if(isreachable == false)
				{
                    topLabel.removeAll();	
                	topLabel.setText(Model_Name+"����ģ�Ͳ�����һ����Ҫ����������չ��");
					evaluateButton.setEnabled(true);
					reEvaluateButton.setEnabled(false);
					mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
					mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
					
					mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(true);
					mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(true);
				}
				else if(ispolarity == true && isconfirm == true && isreachable == true)
                {
                	topLabel.removeAll();
                	
                	topLabel.setText(Model_Name+"����ģ������һ����Ҫ�󣡿���ת��ΪMarkovģ��");
                	EvaluateNodeLabel evaluateNodeLabel = new EvaluateNodeLabel(Model_Name,mainFrame);            	
                	evaluateNodePanel.insertNodeLabel(evaluateNodeLabel,HomogeneityPanel,CertaintyPanel,AccessibilityPanel);
    				
//    				mainFrame.getStepTwoEvaluateTabbedPane().getHomogeneityResults().add(mainPanel);
    				evaluateButton.setEnabled(true);
    				reEvaluateButton.setEnabled(false);
    				
    				mainFrame.getStepTwoModelOperation().getStartExpandButton().setEnabled(true);
    				mainFrame.getStepTwoCaseOperation().getStartExpandButton().setEnabled(true);
    				
                    mainFrame.getsteponeButton().getExpandModelLabel().setEnabled(true);
    				mainFrame.getsteponeButton().getExpandCaseModel().setEnabled(true);
    				mainFrame.getStepTwoExpand().getExchangeLabel().setEnabled(true);	
    				mainFrame.renewPanel();
                }
                
				return 1;
			}
			
		};
		maintask = new FutureTask<>(maincallable);
		mainthread = new Thread(maintask);
       }
       private void buttonListen()
       {
    	   evaluateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
               initThread();
               mainthread.start();
			}
		});
    	   reEvaluateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("before: " + mainthread.isAlive());
				if(!isAlive){
					reEvaluateButton.setText("ֹͣ����");
					mainthread.resume();
					isAlive = true;
				}
				else {
					reEvaluateButton.setText("��ʼ����");
					mainthread.suspend();
					isAlive = false;
				}
			}
		});
       }
    //���·��   
    public StepTwoMatrixPanel getRoute(Map<?,?> prePath,String title) {
    	StepTwoMatrixPanel stepTwoTabelPanel = new StepTwoMatrixPanel();
    	stepTwoTabelPanel.getTitleLabel().setText(title);
    	stepTwoTabelPanel.getTitleLabel().setFont(new Font("΢���ź�", Font.BOLD, 17));
    	stepTwoTabelPanel.getTabelPanel().setLayout(new GridBagLayout());
    	int i = 0;
    	for(Object en:prePath.keySet())
		{
    		StepTwoMatrixPanel stepTwoMatrixPanel = new StepTwoMatrixPanel();
    		stepTwoMatrixPanel.getTitleLabel().setText(en.toString());
    		
    		RoutePanel routePanel = new RoutePanel(mainFrame);
    		
    		StringBuilder str = new StringBuilder();
    		String[] path = prePath.get(en).toString().replace("[","").replace("]", "").split(",");
    		for(int j = 0;j < path.length;j++)
    		{
    			if(j != path.length-1)
    			{
    				str.append(path[j]).append("-->");
    			}
    			else{
    				str.append(path[j]);
    			}
    		}
    		routePanel.getRoute().setText(str.toString());
    		
    		stepTwoMatrixPanel.getTabelPanel().add(routePanel);
    		stepTwoMatrixPanel.getTabelPanel().setBackground(Color.white);
    		
    		stepTwoTabelPanel.getTabelPanel().add(stepTwoMatrixPanel,new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
			i++;
			
			routePanels.add(routePanel);
		}
    	return stepTwoTabelPanel;
	}
	public String getModel_Name() {
		return Model_Name;
	}
	public void setModel_Name(String model_Name) {
		Model_Name = model_Name;
	}
	public JLabel getTopLabel() {
		return topLabel;
	}
	public EvaluateNodePanel getEvaluateNodePanel() {
		return evaluateNodePanel;
	}
	public JButton getEvaluateButton() {
		return evaluateButton;
	}
	public ScenceTabelPanel getReachableTabel() {
		return reachableTabel;
	}
	public List<RoutePanel> getRoutePanels() {
		return routePanels;
	}  
}
