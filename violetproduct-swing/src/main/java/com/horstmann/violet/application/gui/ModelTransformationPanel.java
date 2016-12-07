package com.horstmann.violet.application.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;

import com.horstmann.violet.application.consolepart.ConsolePartTextArea;
import com.horstmann.violet.application.gui.util.xiaole.UppaalTransfrom.ImportByDoubleClick;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.LocalFile;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;
import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

public class ModelTransformationPanel extends JPanel{
	 //ת���������ı���ʾ
		
	public  MainFrame mainFrame;
	private JDialog messageDialog;
     
    public DefaultMutableTreeNode allDiagram;
    public DefaultMutableTreeNode timingDiagram;
    public DefaultMutableTreeNode sequenceDiagram;
    public DefaultMutableTreeNode uppaalDiagram;
    public DefaultMutableTreeNode node;
    public JTree UMLDiagramTree;
	private List<String> tdlists=new ArrayList<String>();
	public JTree UppaalDiagramTree;
	private List<String> sdlists=new ArrayList<String>();
	private List<String> uppaallists=new ArrayList<String>();	     
		
	public ModelTransformationPanel(MainFrame mainFram){	
		this.mainFrame=mainFram;
		initFileList();
		initUI();
		this.setLayout(new GridBagLayout());
		JPanel umlDiagramPanel=new JPanel();
		umlDiagramPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black)
	        		,"UMLģ���ļ�",TitledBorder.CENTER,TitledBorder.ABOVE_TOP,
	        		new Font("����",Font.BOLD,20),new Color(60, 60, 60)));
		umlDiagramPanel.setLayout(new GridBagLayout());
		umlDiagramPanel.add(UMLDiagramTree,new GBC(0,0).setFill(GBC.BOTH).setWeight(1, 1));
		
		JPanel uppaalDiagramPanel=new JPanel();
		uppaalDiagramPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black)
        		,"TASģ���ļ�",TitledBorder.CENTER,TitledBorder.ABOVE_TOP,
        		new Font("����",Font.BOLD,20),new Color(60, 60, 60)));
		uppaalDiagramPanel.setLayout(new GridBagLayout());
		uppaalDiagramPanel.add(UppaalDiagramTree,new GBC(0,0).setFill(GBC.BOTH).setWeight(1, 1));
		this.add(umlDiagramPanel,new GBC(0,0).setFill(GBC.BOTH).setWeight(1, 1));
		this.add(uppaalDiagramPanel,new GBC(0,1).setFill(GBC.BOTH).setWeight(1, 1));
		initDoubleClick();		
	}  		   
	    private void initDoubleClick() {
	    	UMLDiagramTree.addTreeSelectionListener(new TreeSelectionListener() {
				
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					// TODO Auto-generated method stub
					 
						node =(DefaultMutableTreeNode)UMLDiagramTree.getLastSelectedPathComponent();
						
				}
			});
	    	
	    	UMLDiagramTree.addMouseListener(new MouseAdapter() {
	    		
	    		 @Override
	    		public void mousePressed(MouseEvent e) {
	    			
	    			// TODO Auto-generated method stub
	    		   if(e.getClickCount()==2)
	    		   {
	    			  
	    			   if(node!=null&&node.isLeaf()){
	    			   GraphFile fGraphFile=ImportByDoubleClick.importFileByDoubleClick("SequenceDiagram",node.toString());
	    			   IWorkspace workspace=new Workspace(fGraphFile);
	    			   mainFrame.addTabbedPane(workspace,1);
	    			 }
	    		   }
	    		}
	    	});    
		// TODO Auto-generated method stub
	    		
	}
		public void initUI() {
			// TODO Auto-generated method stub		 		   			 
		       allDiagram = new DefaultMutableTreeNode("Ҫת����UMLģ���ļ�");
		       uppaalDiagram=new DefaultMutableTreeNode("���ɵ�ʱ���Զ����ļ�");
		       sequenceDiagram =new DefaultMutableTreeNode("˳��ͼģ���ļ�");
		       timingDiagram=new DefaultMutableTreeNode("ʱ��ͼģ���ļ�");
		       for(String td:tdlists)
		       {
		    	   timingDiagram.add(new DefaultMutableTreeNode(td));
		    	  
		       }
		       for(String sd:sdlists)
		       {
		    	   sequenceDiagram.add(new DefaultMutableTreeNode(sd));
		    	  
		       }
		       for(String up :uppaallists){
		    	   uppaalDiagram.add(new DefaultMutableTreeNode(up));
		       }
	           allDiagram.add(sequenceDiagram);
	           allDiagram.add(timingDiagram);
	           UMLDiagramTree=new JTree(allDiagram);
	           UppaalDiagramTree=new JTree(uppaalDiagram);
	         
	           
	    }	
	    

	public void initFileList() {
		File[] sdFilelists = getAllFileByDiagramType("sequence");
	//	File[] tdFilelists= getAllFileByDiagramType("timing");	
	   // File[] uppaalFilelists=getAllFileByDiagramType("UPPAAL2");
	    for(File sdFile : sdFilelists)
	    {
	    	String fileName=sdFile.getName();
	    	sdlists.add(fileName);
	    }
//	    for(File tdFile : tdFilelists)
//	    {
//	    	String fileName=tdFile.getName();
//	    	tdlists.add(fileName);
//	    }
//	    for(File uppaalFile:uppaalFilelists)
//	    {
//	    	String fileName=uppaalFile.getName();
//	    	uppaallists.add(fileName);
//	    }
	}
	/**
	  * �������ͻ�ȡ�ļ����µ������ļ�
	  * @param type
	  * @return
	  */
	 public   File[] getAllFileByDiagramType(String type){
		 File f =FileSystemView.getFileSystemView().getHomeDirectory();
		String s =f.getAbsolutePath();
//		 String baseUrl ="D://ModelDriverProjectFile";
		String baseUrl =s+"//ModelDriverProjectFile";
//		File bFile = new File(baseUrl);
//		if(!bFile.exists()){
//			bFile.mkdirs();
//		}
		 File[] fList =null;
		 File file=null;
		 if("sequence".equals(type)){
			 file =new File(baseUrl+"\\SequenceDiagram\\Violet");
			 fList= file.listFiles();
		 }else if("timing".equals(type)){
			file =new File(baseUrl+"\\TimingDiagram\\Violet");
			 fList= file.listFiles();
		 }else if("UPPAAL2".equals(type)){
			 //�ڶ�����UPPAAL�漰���Զ���
			 file =new File(baseUrl+"\\UPPAAL\\2.UML Model Transfer");
			 fList=file.listFiles();
		 }else if("UPPAAL3".equals(type)){
			 //��������UPPAAL�漰���Զ���
			 file =new File(baseUrl+"\\UPPAAL\\3.Abstract TestCase");
			 fList= file.listFiles();
		 }else if("UPPAAL4".equals(type)){
			 //���Ĳ���UPPAAL�漰���Զ���
			 file =new File(baseUrl+"\\UPPAAL\\4.Real TestCase");
			 fList=file.listFiles();
		 }else if("state".equals(type)){
			 file =new File(baseUrl+"\\StateDiagram\\Violet");
			 fList=file.listFiles();
		 }else if("usecase".equals(type)){
			 file =new File(baseUrl+"\\UsecaseDiagram\\Violet");
			 fList= file.listFiles();
		 }else if("class".equals(type)){
			 file =new File(baseUrl+"\\ClassDiagram\\Violet");
			 fList= file.listFiles();
		 }else if("activity".equals(type)){
			 file =new File(baseUrl+"\\ActivityDiagram\\Violet");
			 fList=file.listFiles();
		 }
		 return fList;
	}
}
	


