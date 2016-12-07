//package com.horstmann.violet.application.gui;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Font;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.GridLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JSplitPane;
//import javax.swing.JTabbedPane;
//import javax.swing.JTextArea;
//import javax.swing.JViewport;
//import javax.swing.SwingUtilities;
//
//import org.dom4j.DocumentException;
//
//import com.horstmann.violet.application.consolepart.ConsoleMessageTabbedPane;
//import com.horstmann.violet.application.consolepart.ConsolePart;
//import com.horstmann.violet.application.consolepart.ConsolePartDetailInfoTable;
//import com.horstmann.violet.application.consolepart.ConsolePartTextArea;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.ClientRecThread;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.ClientSocket;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.ConsolePartTestCaseInfoTable;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.JFreeChartTest;
//import com.horstmann.violet.application.gui.util.chengzuo.Verfication.TestCase;
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.GetAutomatic;
//import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.Main;
//import com.horstmann.violet.application.gui.util.wujun.SequenceTransfrom.SD2UppaalMain;
//import com.horstmann.violet.application.gui.util.wujun.TDVerification.ExistTest;
//import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.LayoutUppaal;
//import com.horstmann.violet.application.gui.util.xiaole.UppaalTransfrom.ImportByDoubleClick;
//import com.horstmann.violet.application.gui.util.xiaole.UppaalTransfrom.TransToVioletUppaal;
//import com.horstmann.violet.application.menu.FileMenu;
//import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractState;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.AbstractTestCaseInsertByTan;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.AbstractTestCaseUppaalCreate;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.AbstractTestCaseUppaalSequenceCreate;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.AbstractTrasitionAndStateInsertByTan;
//import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.RealTestCaseXMLRead;
//import com.horstmann.violet.framework.file.GraphFile;
//import com.horstmann.violet.framework.file.IGraphFile;
//import com.horstmann.violet.workspace.IWorkspace;
//import com.horstmann.violet.workspace.Workspace;
//
//public class StepButtonPanel extends JPanel {
//	private JButton homebutton;
//	private JButton step1button;
//	private JButton step2button;
//	//by tan
//	private JButton ceshiButton;
//	private JButton step3button;
//	private JButton step4button;
//	private JButton step5button;
//	private List<JButton> stepButtonGroup;
//	private MainFrame mainFrame;
//    private ConsolePart consolePart;
//    private JPanel operationPanel;
//    StepThreeCenterTabbedPane stepThreeCenterTabbedPane;
//    StepSixCenterTabbedPane stepSixCenterTabbedPane=new StepSixCenterTabbedPane();
//    StepFourCenterTabbedPane stepFourCenterTabbedPane=new StepFourCenterTabbedPane();
//    private JButton Threestart=new JButton("��ʼ");
//    private JButton Fourstart=new JButton("��ʼ");
//    private JButton Twostart=new JButton("��ʼ");
//    private JButton fivestart=new JButton("��ʼ");
//    private JButton fiveshow=new JButton("��ʾ");
//    private JButton Sixstart=new JButton("��ʼ");
//	JTextArea StepTwoArea=new JTextArea();
//	JTextArea StepThreeArea=new JTextArea();
//	JTextArea StepFourArea=new JTextArea();
//	JTextArea StepFiveArea=new JTextArea();
//	JTextArea StepSixArea=new JTextArea();
//	public StepButtonPanel(MainFrame mainFrame) {
//		this.setBackground(Color.DARK_GRAY);
//		this.mainFrame=mainFrame;	
//		init();
//		stepThreeCenterTabbedPane=mainFrame.getStepThreeCenterTabbedPane();
//		
//		
//	}
//    
//	private void init() {
//		GridBagLayout layout = new GridBagLayout();
//		this.setLayout(layout);
//		initButton();
//		GridBagConstraints s = new GridBagConstraints();// ����һ��GridBagConstraints��
//		// ������������ӽ����������ʾλ��
//		s.fill = GridBagConstraints.BOTH;
//		s.anchor = GridBagConstraints.FIRST_LINE_START;
//		s.insets = new Insets(0, 0, 0,0);
//        this.add(homebutton);
//		this.add(step1button);
//		this.add(step2button);
//		this.add(ceshiButton);
//		this.add(step3button);
//		this.add(step4button);
//		this.add(step5button);
//		s.gridx = 0;
//		s.gridy = 0;
//		s.weighty = 1;
//		s.weightx=1;
//		layout.setConstraints(homebutton, s);// �������
//		s.gridx = 0;
//		s.gridy = 1;
//		layout.setConstraints(step1button, s);
//		s.gridx = 0;
//		s.gridy = 2;
//		layout.setConstraints(step2button, s);
//		s.gridx = 0;
//		s.gridy = 3;
//		layout.setConstraints(step3button, s);
//		s.gridx = 0;
//		s.gridy = 4;
//		layout.setConstraints(step4button, s);
//		s.gridx = 0;
//		s.gridy = 5;
//		layout.setConstraints(step5button, s);
//		s.gridx = 0;
//		s.gridy = 6;
//		layout.setConstraints(ceshiButton, s);
//		// TODO Auto-generated method stub
//
//		SetButtonListener();
//
//	}
//
//	private void initButton() {
//		homebutton =new JButton();
//		step1button = new JButton();
//		step2button = new JButton();
//		ceshiButton=new JButton();
//		step3button = new JButton();
//		step4button = new JButton();
//		step5button = new JButton(); 
//		
//	    homebutton.setText("��ҳ");
//	    homebutton.setForeground(Color.RED);
//		step1button.setText("��һ��:UMLģ�ͽ���������");
//		step1button.setBackground(Color.BLUE);
//		step2button.setText("�ڶ���:UMLģ��ת��ʱ���Զ���");
//		step3button.setText("������:���������������");
//		step4button.setText("���Ĳ�:��������ʵ����");
//		step5button.setText("���岽:��������ʵ������֤");
//		ceshiButton.setText("һ���Բ���");
//		stepButtonGroup = new ArrayList<JButton>();
//		stepButtonGroup.add(homebutton);
//		stepButtonGroup.add(step1button);
//		stepButtonGroup.add(step2button);
//		stepButtonGroup.add(step3button);
//		stepButtonGroup.add(step4button);
//		stepButtonGroup.add(step5button);
//		stepButtonGroup.add(ceshiButton);
//		operationPanel=mainFrame.getOpreationPart();
//		operationPanel.setLayout(new GridLayout(1,1));
//		consolePart=mainFrame.getConsolePart();	
//		// TODO Auto-generated method stub
//        step2button.setEnabled(false);//��ʼ�������谴ť�����ɵ��
//        step3button.setEnabled(false);
//        step4button.setEnabled(false);
//        step5button.setEnabled(false);
//	}
//	//��ʼ���׶�
//	public void clearSelection() {
//		for (JButton stepButton : stepButtonGroup) {			
//			stepButton.setForeground(Color.BLACK);
//		}
//	}
//	public void clearConsolePart(){
//		this.consolePart.getContentPane().removeAll();;
//	}
//  private void ClearOpreationPanel()
//  {
//	  this.operationPanel.removeAll();
//  }
//    //���ü�����
//	private void SetButtonListener() {
//		Twostart.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				StepTwoArea.append("UMLģ������ת����......\n");	
//				// TODO Auto-generated method stub
//			   	try {
//			   		//�¼��ַ��߳�(gum�����¼��ͻ�ͼ��ʱ��)
//			   		SwingUtilities.invokeLater(new Runnable() {
//						
//						@Override
//						public void run() {
//							try {
//								//umlת�����¼��Զ���
//                                 //���ڻ�õ�ǰ������sequence
//								String name=FileMenu.getFileName();//sequence.getGraphFile().getFilename();
//								String directoryPath=FileMenu.getDirectory();
//								String path=directoryPath+"\\"+name;//�˵������ļ���·��
//								System.out.println(path+"·��");
//								if(name.contains("EA")){//��eaƽ̨��xml�ļ�
//									SD2UppaalMain.transEA(path);//��Ҫ�ǽ�ea��xmlת�������ǵ�wujun��xml(����������·��)
//									//����d����д���ļ�������·�������������Ƕ�̬���ɵ���Ҫ�޸�
//									LayoutUppaal.layout("D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\UseCase1-Sequence1-Normal.xml");//("sequence.xml");
//								    String filename1=TransToVioletUppaal.TransToViolet();
//									//String filename1="uppaalTest1.uppaal.violet.xml";
//								    GraphFile fGraphFile1=ImportByDoubleClick.importFileByDoubleClick("UPPAAL",filename1);
//				    			    IWorkspace workspace1=new Workspace(fGraphFile1);  
//				    			    mainFrame.addTabbedPane(workspace1,2);
//				    			    mainFrame.repaint();
//				    			    Thread.sleep(5000);
//				    				//String filename2=TransToVioletUppaal.TransToViolet();
//				    			
//								   // GraphFile fGraphFile2=ImportByDoubleClick.importFileByDoubleClick("UPPAAL",filename2);
//				    			    //IWorkspace workspace2=new Workspace(fGraphFile2);  			    			  
//				    			    StepTwoArea.append("UMLģ�͵�ʱ���Զ���ģ���Ѿ�ת�����!\n");
//								}
//								else{//������ƽ̨��xml�ļ�
//									
//								}
////								SD2UppaalMain.transEA(path);//��Ҫ�ǽ�ea��xmlת�������ǵ�wujun��xml(����������·��)
////							    String filename1=TransToVioletUppaal.TransToViolet();
////							    GraphFile fGraphFile1=ImportByDoubleClick.importFileByDoubleClick("UPPAAL",filename1);
////			    			    IWorkspace workspace1=new Workspace(fGraphFile1);  
////			    			    mainFrame.addTabbedPane(workspace1,2);
////			    			    mainFrame.repaint();
////			    			    Thread.sleep(5000);
//								//�Ƚ��в���
//			    			    //��ʱ���Զ���չʾ�����ǵ�ƽ̨��
////								LayoutUppaal.layout
////								("C:\\Users\\Admin\\Desktop\\��Ŀ���´���\\violetumleditor-master\\violetproduct-swing\\sequence.xml");//("stabilize_run.xml");
////								String filename2=TransToVioletUppaal.TransToViolet();
////							    GraphFile fGraphFile2=ImportByDoubleClick.importFileByDoubleClick("UPPAAL",filename2);
////			    			    IWorkspace workspace2=new Workspace(fGraphFile2);  			    			  
////			    			    StepTwoArea.append("UMLģ�͵�ʱ���Զ���ģ���Ѿ�ת�����!\n");
//			    			    //mainFrame.addTabbedPane(workspace1,2);
//			    			   
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}														
//					}});			   				  		   
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
//Threestart.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				StepThreeArea.append("����ʱ��Ǩ�Ƶ�ʱ���Զ�����������.....\n");
//				
//				SwingUtilities.invokeLater(new Runnable() {
//					
//					@Override
//					public void run() {
//						
//						//GetAutomatic.getAutomatic("Draw MoneyForXStream(2).xml");
//						//by tan ���������������state���뵽���ݿ���
//						//new AbstractStateInsertByTan().wqq2zhangExchange("Draw MoneyForXStream(2).xml");
//						//BY TAN ǿ������Ե�Ǩ��trasition���뵽���ݿ���
//						//new AbstractTrasitionAndStateInsertByTan().w2zExchange("Draw MoneyForXStream(2).xml");
//						
//						System.out.println("----------------------------------");
//						try {
//							//��ȡ�������Զ����Ľڵ�ͱߵ���Ϣ�������squence��xml�У��ڵ��ò����㷨�����ɽڵ����ꡣ�����stabilize_run.xml�С�
//							new AbstractTestCaseUppaalSequenceCreate().createXML("D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\UseCase1-Sequence1-NormalForXStream.xml");
//							LayoutUppaal.layout
//							("C:\\Users\\Admin\\Desktop\\��Ŀ���´���\\violetumleditor-master\\violetproduct-swing\\sequence.xml");
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						};
//						System.out.println("-----------------------------------");
//						 //����Ϣ�������ݿ�
//						new AbstractTestCaseUppaalCreate().createXML("D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\UseCase1-Sequence1-NormalForXStream.xml", "D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\abs.uppaal.violet.xml");
//						//new AbstractTestCaseInsertByTan().w2zAbstractTestCaseExchange("D:\\ModelDriverProjectFile\\UPPAL\\2.UML_Model_Transfer\\sequenceForXStream.xml");//�Ѿ����ϵ�����ķ�����
//						//String absfilename="abs.uppaal.violet.xml";
//				        //String no_time_absfilename="no_time_abs.uppaal.violet.xml";
//					    
//					    //String filename2=TransToVioletUppaal.TransToViolet();
//						
//						GraphFile absfGraphFile=ImportByDoubleClick.importFileByDoubleClick("UPPAAL","abs.uppaal.violet.xml");
//						//GraphFile no_time_absfGraphFile=ImportByDoubleClick.importFileByDoubleClick("UPPAAL","a.xml");
//		 			    IWorkspace absworkspace=new Workspace(absfGraphFile);
//		 			    //IWorkspace no_time_absworkspace=new Workspace(no_time_absfGraphFile);
//		 			    //չʾʱ��Ǩ�Ƶ��Զ���
//		 			    mainFrame.addTabbedPane(absworkspace,3);
//		 			    
//		 			    StepThreeArea.append("����ʱ��Ǩ�Ƶ�ʱ���Զ����������!\n");
//		 			    StepThreeArea.append("����ʱ��Ǩ�Ƶ�ʱ���Զ�����������.....\n");
//		 			    //չʾȥʱ��Ǩ�Ƶ��Զ���
//		 			    //mainFrame.addTabbedPane(no_time_absworkspace,3);
//		 			    
//		 			    StepThreeArea.append("����ʱ��Ǩ�Ƶ�ʱ���Զ����������!\n");
//		 			    StepThreeArea.append("�������������������.....\n");
//						//չʾ�����������������
//		 			    stepThreeCenterTabbedPane.getConsolePartScrollPane()
//						.getViewport().add(new ConsolePartDetailInfoTable(0));			
//					    stepThreeCenterTabbedPane.getConsolePartScrollPane().getViewport().repaint();
//					    StepThreeArea.append("������������������!.....\n");
//						
//					}
//				});
//				
//			}
//		});
//Fourstart.addActionListener(new ActionListener() {
//	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		StepFourArea.append("ʵ��������������������.....\n");
//		SwingUtilities.invokeLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				new RealTestCaseXMLRead("tcs.xml");
//				stepFourCenterTabbedPane.getConsolePartScrollPane()
//				.getViewport().add(new ConsolePartDetailInfoTable(1));			
//			    stepFourCenterTabbedPane.getConsolePartScrollPane().getViewport().repaint();
//			    StepFourArea.append("ʵ���������������ɳɹ�!");
//			}
//		});
//		
//		
//	}
//});
//
//fivestart.addActionListener(new ActionListener() {
//	      
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		StepFiveArea.append("ʵ��������������֤���ڽ�����.....\n"); 
//		StepFiveArea.append("���ڵ����������.....\n");
//		
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				ClientSocket clientSocket= new ClientSocket("192.168.150.142",5555);
//				clientSocket.Connection();
//				JFileChooser jfc=new JFileChooser();
//				jfc.setMultiSelectionEnabled(true);
//				jfc.showDialog(new JLabel(),"ѡ���������");
//				File[] files=jfc.getSelectedFiles();
//				StepFiveArea.append("���ڷ�������.....\n");
//				clientSocket.sendFile(files);
//                StepFiveArea.append("�����������!\n");
//                StepFiveArea.append("���ڻ������.....\n");
//                StepFiveArea.append("�����Ѿ����!\n");
////                List<TestCase> list= ClientRecThread.getTestCaseList();
//                List<TestCase> list=new ArrayList<TestCase>() ;
//                for(int i=1;i<4;i++){
//                	TestCase tc=new TestCase();
//                	if(i==1){
//                		tc.setTestCaseID(String.valueOf(1));
//                		tc.setContent("content"+1);
//                		tc.setState("state"+1);
//                		tc.setResult("ʧ��");
//                	}
//                	if(i==2){
//                		tc.setTestCaseID(String.valueOf(2));
//                		tc.setContent("content"+2);
//                		tc.setState("state"+2);
//                		tc.setResult("�ɹ�");
//                	}
//                	if(i==3){
//                		tc.setTestCaseID(String.valueOf(3));
//                		tc.setContent("content"+3);
//                		tc.setState("state"+3);
//                		tc.setResult("�ɹ�");
//                	}
//                	list.add(tc);
//                }
//                System.out.println(list.size()+"��******��");
//                for(TestCase tc:list){
//                	System.out.println(tc.getTestCaseID()+"  "+tc.getContent()+" "+tc.getState()+"  "+tc.getResult());
//                }
//                //mainFrame.getStepFiveCenterTabbedPane().getTestcaseFile().setLayout(new GridLayout(1, 1));
//                //���root������
//                JPanel jp=mainFrame.getStepFiveCenterTabbedPane().getTestcaseFile();
//                //��ñ��������
//                JPanel jp1=JFreeChartTest.getJFreeChartTest(list);
//                //�����ŵ�JScrollPane������
//                JScrollPane jsp=new JScrollPane(new ConsolePartTestCaseInfoTable(list));
//                JSplitPane js=new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//                		jsp,jp1);
//        		js.setDividerLocation(300);
////			    jp.add(new ConsolePartTestCaseInfoTable(list), BorderLayout.NORTH);
////			    jp.add(jp1,BorderLayout.CENTER);
//        		jp.add(js);
//                mainFrame.getStepFiveCenterTabbedPane().getTestcaseFile().updateUI();
//
//			}
//		});
//	}
//});
//
//Sixstart.addActionListener(new ActionListener() {
//	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		StepSixArea.append("һ���Բ������ڽ���.....\n");
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					JSplitPane jp=ExistTest.existTest();
//					mainFrame.getStepSixCenterTabbedPane().getConsolePartScrollPane().setLayout(new BorderLayout());
//					mainFrame.getStepSixCenterTabbedPane().getConsolePartScrollPane().validate();
//					mainFrame.getStepSixCenterTabbedPane().getConsolePartScrollPane().add(jp,BorderLayout.CENTER);
//					mainFrame.getStepSixCenterTabbedPane().getConsolePartScrollPane().revalidate();
//					 StepSixArea.append("һ���Բ��Բ��Գɹ�!");
//					//System.out.println(jvp);
//					//.add(jp);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//			   
//			}
//		});
//		
//		
//	}
//});
//		homebutton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();		
//				homebutton.setForeground(Color.RED);	
//				JLabel jLabel=new JLabel();
//				jLabel.setText(homebutton.getText());
//				jLabel.setFont(new Font("����", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));				     				
//			    mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getHomePanel());
//				mainFrame.getConsolePart().setVisible(false);
//				mainFrame.getOpreationPart().setVisible(false);	
//				mainFrame.getContentPane().repaint();
//			}
//		});						
//		step1button.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();
//				//��ʾ�������Ͳ������			  			
//				//����һ��ť����			
//				step1button.setForeground(Color.RED);							
//				//�����Ƴ���ӭ����
//			
//				//�޸�ԭ���ı������
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step1button.getText());
//				jLabel.setFont(new Font("����", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//													
//			    //��Ӳ������
//				ClearOpreationPanel();
//				operationPanel.add(mainFrame.getProjectTree());													
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterTabbedPane());	
//				
//				//��ӹ�����Ϣ���	
//			    clearConsolePart();				  
//			    consolePart.setTitle("UMLģ�ͽ���������Ϣ");
//				consolePart.add(new ConsoleMessageTabbedPane("��ϸ��Ϣ",new JTextArea("UMLģ�����ڽ�����......\n\n\n\n\n\n")));				
//				wakeupUI();
//				mainFrame.getContentPane().repaint();
//				step2button.setEnabled(true);//��һ�����֮�󣬵ڶ����ɵ��
//			}
//		});
//		step2button.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();
//				step2button.setForeground(Color.RED);
//				//����
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step2button.getText());
//				jLabel.setFont(new Font("����", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//				labelpanel.add(Twostart,new GBC(1, 0));
//				labelpanel.add(new JButton("��ͣ"),new GBC(2, 0));
//				
//				ClearOpreationPanel();
//			    operationPanel.add(mainFrame.getModelTransformationPanel());
//			    
//			    clearConsolePart();			    
//			    consolePart.setTitle("UMLģ��ת��ʱ���Զ���������Ϣ");
//				
//				consolePart.add(new ConsoleMessageTabbedPane("��ϸ��Ϣ",StepTwoArea));
//						
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoCenterTabbedPane());
//			
//				
//							
//				mainFrame.getContentPane().repaint();
//				step3button.setEnabled(true);
//			}
//		});
//		step3button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();
//			 
//				step3button.setForeground(Color.RED);
//			
//				mainFrame.getCenterTabPanel().removeAll();
//				
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeCenterTabbedPane());
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step3button.getText());
//				jLabel.setFont(new Font("����", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));																			
//				labelpanel.add(Threestart,new GBC(1, 0));
//				labelpanel.add(new JButton("��ͣ"),new GBC(2, 0));	
//				
//				ClearOpreationPanel();//����
//				operationPanel.add(new AbstractTestCaseGenerationPanel());				
//				clearConsolePart();//����̨			    
//				consolePart.setTitle("��������������ɹ�����Ϣ");
//			    consolePart.add(new ConsoleMessageTabbedPane("��ϸ��Ϣ",StepThreeArea));			
//				mainFrame.getContentPane().repaint();
//				step4button.setEnabled(true);
//			}
//		});
//		step4button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();
//			
//				step4button.setForeground(Color.RED);
//				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
//				
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step4button.getText());
//				jLabel.setFont(new Font("����", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//				labelpanel.add(Fourstart,new GBC(1, 0));
//				labelpanel.add(new JButton("��ͣ"),new GBC(2, 0));
//				
//				ClearOpreationPanel();
//				operationPanel.add(new TestCaseInstantiationPanel());
//				
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(stepFourCenterTabbedPane);
//				
//				clearConsolePart();	
//			
//				consolePart.setTitle("�����������ʵ����������Ϣ");
//				consolePart.add(new ConsoleMessageTabbedPane("��ϸ��Ϣ",StepFourArea));
//				mainFrame.getContentPane().repaint();
//				step5button.setEnabled(true);
//			}
//		});
//		step5button.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();		
//				step5button.setForeground(Color.RED);
//				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
//				
//				JLabel jLabel=new JLabel();
//				jLabel.setText(step5button.getText());
//				jLabel.setFont(new Font("����", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//				labelpanel.add(fivestart,new GBC(1, 0));
//				labelpanel.add(fiveshow,new GBC(2, 0));
//				
////				ClearOpreationPanel();
////				operationPanel.add(new TestCaseConfirmationPanel());
//				
//				
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepFiveCenterTabbedPane());
//				clearConsolePart();
//			
//				consolePart.setTitle("��������ʵ����֤������Ϣ");
//    			consolePart.add(new ConsoleMessageTabbedPane("��ϸ��Ϣ",StepFiveArea));	
//							
//				mainFrame.getContentPane().repaint();
//			}
//		});
//		//���԰�ť�Ĵ����¼�
//		ceshiButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				clearSelection();		
//				step5button.setForeground(Color.RED);
//				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
//				
//				JLabel jLabel=new JLabel();
//				jLabel.setText(ceshiButton.getText());
//				jLabel.setFont(new Font("����", Font.BOLD, 20));
//				jLabel.setForeground(Color.white);
//				JPanel labelpanel=mainFrame.getStepJLabel();
//				labelpanel.setLayout(new GridBagLayout());
//				labelpanel.removeAll();
//				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
//				labelpanel.add(Sixstart,new GBC(1, 0));
//				//labelpanel.add(new JButton("��ͣ"),new GBC(2, 0));
//				
//				ClearOpreationPanel();
//				//operationPanel.add(new TestExistenceTabbedPane());
//				
//				
//				mainFrame.getCenterTabPanel().removeAll();
//				mainFrame.getCenterTabPanel().add(mainFrame.getStepSixCenterTabbedPane());
//				clearConsolePart();
//			
//				consolePart.setTitle("һ���Ե���֤");
//    			consolePart.add(new ConsoleMessageTabbedPane("��ϸ��Ϣ",StepSixArea));	
//							
//				mainFrame.getContentPane().repaint();
//			}
//		});
//	}
//	/*
//	 * ʹԭ�����ɼ��Ľ������¿ɼ�(������ҳ,����Ҫ���¿ɼ�,��ҳ��ʹ�������治�ɼ�)
//	 */
//	public void wakeupUI()
//	{
//		mainFrame.getConsolePart().setVisible(true);
//		mainFrame.getOpreationPart().setVisible(true);
//	}
//}
