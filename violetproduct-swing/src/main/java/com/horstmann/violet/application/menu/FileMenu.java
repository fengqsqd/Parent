/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.application.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.horstmann.violet.application.ApplicationStopper;
import com.horstmann.violet.application.Stepone.SequenceTabPanel;
import com.horstmann.violet.application.Stepone.UsecaseTabPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.CreateActivityDiagramEAXml;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.CreateActivityDiagramVioletXML;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.CreateClassDiagramEAXML;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.CreateClassDiagramVioletXML;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.CreateStateDiagramEAXml;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.CreateStateDiagramVioletXML;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.CreateUseCaseDiagramEAXml;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.CreateUseCaseDiagramVioletXml;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.readActivityXMLFormViolet;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.readActivityXMLFromEA;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.readClassXMLFormViolet;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.readClassXMLFromEA;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.readStateXMLFormViolet;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.readStateXMLFromEA;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.readUcaseXMLFormViolet;
import com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom.readUseCaseXMLFromEA;
import com.horstmann.violet.application.menu.xiaole.SequenceTransfrom.MainTransEAToViolet;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.LocalFile;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.file.export.FileExportService;
import com.horstmann.violet.framework.file.naming.ExtensionFilter;
import com.horstmann.violet.framework.file.naming.FileNamingService;
import com.horstmann.violet.framework.file.persistence.IFilePersistenceService;
import com.horstmann.violet.framework.file.persistence.IFileReader;
import com.horstmann.violet.framework.file.persistence.IFileWriter;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.framework.plugin.PluginRegistry;
import com.horstmann.violet.framework.userpreferences.UserPreferencesService;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;
import com.thoughtworks.xstream.io.StreamException;

/**
 * Represents the file menu on the editor frame
 * 
 * @author Alexandre de Pellegrin
 * 
 */
@ResourceBundleBean(resourceReference = MenuFactory.class)
public class FileMenu extends JMenu
{

    /**
     * Default constructor
     * 
     * @param mainFrame
     */
    @ResourceBundleBean(key = "file")
    public FileMenu(MainFrame mainFrame)
    {
        ResourceBundleInjector.getInjector().inject(this);
        BeanInjector.getInjector().inject(this);
        this.mainFrame = mainFrame;
        createMenu();
        addWindowsClosingListener();
        
    }

    /**
     * @return 'new file' menu
     */
    public JMenu getFileNewMenu()
    {
        return this.fileNewMenu;
    }

    /**
     * @return recently opened file menu
     */
    public JMenu getFileRecentMenu()
    {
        return this.fileRecentMenu;
    }

    /**
     * Initialize the menu
     */
    private void createMenu()
    {
        initFileNewMenu();
        initFileOpenItem();
        initFileCloseItem();
        initFileRecentMenu();
        initFileSaveItem();
        initFileSaveAsItem();
        initFileExportMenu();
        initFilePrintItem();
        initFileExitItem();
        initFileDsaveItem();//�Ž��¼�
        this.add(this.fileDsaveItem);//�Զ��屣��
        this.add(this.fileNewMenu);
        this.add(this.fileOpenItem);
        this.add(this.fileCloseItem);
        this.add(this.fileRecentMenu);
        this.add(this.fileSaveItem);
        this.add(this.fileSaveAsItem);
        this.add(this.fileExportMenu);
        this.add(this.filePrintItem);
        this.add(this.fileExitItem);

    }

    /**
     * Add frame listener to detect closing request
     */
    private void addWindowsClosingListener()
    {
        this.mainFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent event)
            {
                stopper.exitProgram(mainFrame);
            }
        });
    }

    /**
     * Init exit menu entry
     */
    private void initFileExitItem()
    {
        this.fileExitItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                stopper.exitProgram(mainFrame);
            }
        });
        if (this.fileChooserService == null) this.fileExitItem.setEnabled(false);
    }

    /**
     * Init export submenu
     */
    private void initFileExportMenu()
    {
        initFileExportToImageItem();
        initFileExportToClipboardItem();
        initFileExportToJavaItem();
        initFileExportToPythonItem();

        this.fileExportMenu.add(this.fileExportToImageItem);
        this.fileExportMenu.add(this.fileExportToClipBoardItem);
       // this.fileExportMenu.add(this.fileExportToJavaItem);
       // this.fileExportMenu.add(this.fileExportToPythonItem);

        if (this.fileChooserService == null) this.fileExportMenu.setEnabled(false);
    }

    /**
     * Init export to python menu entry
     */
    private void initFileExportToPythonItem()
    {
        this.fileExportToPythonItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                	
                }
            }
        });
    }

    /**
     * Init export to java menu entry
     */
    private void initFileExportToJavaItem()
    {
        this.fileExportToJavaItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                }
            }
        });
    }

    /**
     * Init export to clipboard menu entry
     */
    private void initFileExportToClipboardItem()
    {
        this.fileExportToClipBoardItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    workspace.getGraphFile().exportToClipboard();
                }
            }
        });
    }

    /**
     * Init export to image menu entry
     */
    private void initFileExportToImageItem()
    {
        this.fileExportToImageItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    try
                    {
                        ExtensionFilter[] exportFilters = fileNamingService.getImageExtensionFilters();
                        IFileWriter fileSaver = fileChooserService.chooseAndGetFileWriter(exportFilters);
                        OutputStream out = fileSaver.getOutputStream();
                        if (out != null)
                        {
                            String filename = fileSaver.getFileDefinition().getFilename();
                            for (ExtensionFilter exportFilter : exportFilters)
                            {
                                String extension = exportFilter.getExtension();
                                if (filename.toLowerCase().endsWith(extension.toLowerCase()))
                                {
                                    String format = extension.replace(".", "");
                                    workspace.getGraphFile().exportImage(out, format);
                                    break;
                                }
                            }
                        }
                    }
                    catch (Exception e1)
                    {
                        throw new RuntimeException(e1);
                    }
                }
            }
        });
    }

    /**
     * Init 'save as' menu entry
     */
    private void initFileSaveAsItem()
    {
        this.fileSaveAsItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    IGraphFile graphFile = workspace.getGraphFile();
                    graphFile.saveToNewLocation();
                    userPreferencesService.addRecentFile(graphFile);
                }
            }
        });
        if (this.fileChooserService == null) this.fileSaveAsItem.setEnabled(false);
    }

    /**
     * Init save menu entry
     */
    private void initFileSaveItem()
    {
        this.fileSaveItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    IGraphFile graphFile = workspace.getGraphFile();
                    graphFile.save();
                    userPreferencesService.addRecentFile(graphFile);
                }
            }
        });
        if (this.fileChooserService == null || (this.fileChooserService != null && this.fileChooserService.isWebStart()))
        {
            this.fileSaveItem.setEnabled(false);
        }
    }

    /**
     * Init print menu entry
     */
    private void initFilePrintItem()
    {
        this.filePrintItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                IWorkspace workspace = (Workspace) mainFrame.getActiveWorkspace();
                if (workspace != null)
                {
                    workspace.getGraphFile().exportToPrinter();
                }
            }
        });
        if (this.fileChooserService == null) this.filePrintItem.setEnabled(false);
    }

    /**
     * Init close menu entry
     */
    private void initFileCloseItem()
    {
        this.fileCloseItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                IWorkspace workspace = null;
                try
                {
                    workspace = (Workspace) mainFrame.getActiveWorkspace();
                }
                catch (RuntimeException e)
                {
                    // If no diagram is opened, close app
                    stopper.exitProgram(mainFrame);
                }
                if (workspace != null)
                {
                    IGraphFile graphFile = workspace.getGraphFile();
                    if (graphFile.isSaveRequired())
                    {
                        JOptionPane optionPane = new JOptionPane();
                        optionPane.setMessage(dialogCloseMessage);
                        optionPane.setOptionType(JOptionPane.YES_NO_CANCEL_OPTION);
                        optionPane.setIcon(dialogCloseIcon);
                        dialogFactory.showDialog(optionPane, dialogCloseTitle, true);

                        int result = JOptionPane.CANCEL_OPTION;
                        if (!JOptionPane.UNINITIALIZED_VALUE.equals(optionPane.getValue()))
                        {
                            result = ((Integer) optionPane.getValue()).intValue();
                        }

                        if (result == JOptionPane.YES_OPTION)
                        {
                            String filename = graphFile.getFilename();
                            if (filename == null)
                            {
                                graphFile.saveToNewLocation();
                                userPreferencesService.addRecentFile(graphFile);
                            }
                            if (filename != null)
                            {
                                graphFile.save();
                            }
                            if (!graphFile.isSaveRequired())
                            {
                                mainFrame.removeDiagramPanel(workspace);
                                userPreferencesService.removeOpenedFile(graphFile);
                            }
                        }
                        if (result == JOptionPane.NO_OPTION)
                        {
                            mainFrame.removeDiagramPanel(workspace);
                            userPreferencesService.removeOpenedFile(graphFile);
                        }
                    }
                    if (!graphFile.isSaveRequired())
                    {
                        mainFrame.removeDiagramPanel(workspace);
                        userPreferencesService.removeOpenedFile(graphFile);
                    }
                   // List<IWorkspace> workspaceList = mainFrame.getWorkspaceList();
//                    if (workspaceList.size() == 0)
//                    {
//                        mainFrame.requestFocus();
//                    }
                }
            }
        });
    }
    /*
     * �Ž��¼�  selectedFile(ѡ�е��ļ�)--->graphFile(ת��������ƽ̨�ĸ�ʽ) flag����ʾ���Ǵ򿪵���ea���������ǵ�ƽ̨xml
     */
    public IFile exchangeFile( IFile selectedFile,IGraphFile graphFile,boolean flag) throws Exception{
    	String url =selectedFile.getDirectory()+"\\"+selectedFile.getFilename();
//    	String base="D:\\ModelDriverProjectFile";
    	 File ffff =FileSystemView.getFileSystemView().getHomeDirectory();
    	 //���Ŀ¼
 		String s =ffff .getAbsolutePath();
// 		 String baseUrl ="D://ModelDriverProjectFile";
 		String base=s+"\\ModelDriverProjectFile";
    	String type=(selectedFile.getFilename().split("\\."))[1];//��һ����ʾ�����ļ�������
    	String path = null;
    	File ff=null;//����������d�����ļ�
    	if(flag==true){//ѡ����ļ���ƽ̨������ļ�XML��ʽ
    		 graphFile = new GraphFile(selectedFile);
    		
        	if("class".equals(type)){
        		path=base+"/ClassDiagram/";
        	 	graphFile.AutoSave(selectedFile, path+"Violet/");
        		readClassXMLFormViolet rc =new readClassXMLFormViolet(url);
        		CreateClassDiagramEAXML c =new CreateClassDiagramEAXML();
        		 ff =new File(path+"EA");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		StringBuffer name = dealEAFileName(selectedFile);
        		c.create(rc, path+"EA/"+name);
        	}else if("ucase".equals(type)){
        		path=base+"/UseCaseDiagram/";
        		graphFile.AutoSave(selectedFile, path+"Violet/");
        		readUcaseXMLFormViolet ru =new readUcaseXMLFormViolet(url);
        		CreateUseCaseDiagramEAXml cu =new CreateUseCaseDiagramEAXml();
        		ff =new File(path+"EA");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		StringBuffer name = dealEAFileName(selectedFile);
        		cu.create(ru, path+"EA/"+name);
        	}else if("seq".equals(type)){
        		path=base+"/SequenceDiagram/";
        	}else if("state".equals(type)){
        		path=base+"/StateDiagram/";
        		graphFile.AutoSave(selectedFile, path+"Violet/");
        		readStateXMLFormViolet rs =new readStateXMLFormViolet(url);
        		CreateStateDiagramEAXml cs =new CreateStateDiagramEAXml();
        		ff =new File(path+"EA/");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		StringBuffer name = dealEAFileName(selectedFile);
        		cs.create(rs, path+"EA/"+name);
        	}else if("timing".equals(type)){
        		path=base+"/TimingDiagram/";
        	}else if("uppaal".equals(type)){
        		path=base+"/UPPAL/";
        	}else if("activity".equals(type)){
        		path=base+"/ActivityDiagram/";
        		graphFile.AutoSave(selectedFile, path+"Violet/");
        		readActivityXMLFormViolet ra =new readActivityXMLFormViolet(url);
        		CreateActivityDiagramEAXml ca =new CreateActivityDiagramEAXml();
        		ff =new File(path+"EA/");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		StringBuffer name = dealEAFileName(selectedFile);
        		ca.create(ra, path+"EA/"+name);
        	}else if("object".equals(type)){
        		path=base+"/ObjectDiagram/";
        		graphFile.AutoSave(selectedFile, path+"Violet/");
        		readStateXMLFormViolet rs =new readStateXMLFormViolet(url);
        		CreateStateDiagramEAXml cs =new CreateStateDiagramEAXml();
        		 ff =new File(path+"EA/");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		StringBuffer name = dealEAFileName(selectedFile);
        		cs.create(rs, path+"EA/"+name);
        	}
    	}else if(flag==false){ 		
    		System.out.println(type);
    		String name="";
    		//ѡ����ļ���EA��ʽ���ļ� 
    		if("class".equals(type)){
        		path=base+"/ClassDiagram/";
        		
        		readClassXMLFromEA rc =new readClassXMLFromEA(url,selectedFile);
        	
        		CreateClassDiagramVioletXML c =new CreateClassDiagramVioletXML();
        		ff =new File(path+"Violet/");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        	
        		name=selectedFile.getFilename().replaceAll("EA", "");	
        		c.create(rc, path+"Violet/"+name);
        		
        		
        	}else if("ucase".equals(type)){
        		path=base+"/UseCaseDiagram/";
        		readUseCaseXMLFromEA ru =new readUseCaseXMLFromEA(url,selectedFile);
        		CreateUseCaseDiagramVioletXml cu =new CreateUseCaseDiagramVioletXml();
        		ff =new File(path+"Violet/");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		 name=selectedFile.getFilename().replaceAll("EA", "");	
        		cu.create(ru, path+"Violet/"+name);
        		
        		
        	}else if("seq".equals(type)){
        		path=base+"/SequenceDiagram/";
        		
        		ff =new File(path+"Violet/");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		name=selectedFile.getFilename().replaceAll("EA","");
        		directory=selectedFile.getDirectory();
        		fileName=selectedFile.getFilename();
        		System.out.println("@@"+directory);
        		MainTransEAToViolet.TransEAToViolet(url,path+"Violet/"+name);
        		
        	}else if("state".equals(type)){
        		path=base+"/StateDiagram/";
        		readStateXMLFromEA rs =new readStateXMLFromEA(url,selectedFile);
        		CreateStateDiagramVioletXML cs =new CreateStateDiagramVioletXML();
        		ff =new File(path+"Violet");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		name=selectedFile.getFilename().replaceAll("EA", "");	
        		cs.create(rs, path+"Violet/"+name);
        	}else if("timing".equals(type)){
        		path=base+"/TimingDiagram/";
        	}else if("uppaal".equals(type)){
        		path=base+"/UPPAL/";
        	}else if("activity".equals(type)){
        		path=base+"/ActivityDiagram/";
        		readActivityXMLFromEA ra =new readActivityXMLFromEA(url,selectedFile);
        		CreateActivityDiagramVioletXML ca =new CreateActivityDiagramVioletXML();
        		ff =new File(path+"Violet");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		name=selectedFile.getFilename().replaceAll("EA", "");	
        		ca.create(ra, path+"Violet/"+name);
        	}else if("object".equals(type)){
        		path=base+"/ObjectDiagram/";
        		readStateXMLFromEA rs =new readStateXMLFromEA(url,selectedFile);
        		CreateStateDiagramVioletXML cs =new CreateStateDiagramVioletXML();
        		ff =new File(path+"Violet");
        		if(!ff.exists()){
        			ff.mkdirs();
        		}
        		name=selectedFile.getFilename().replaceAll("EA", "");	
        		cs.create(rs, path+"Violet/"+name);
        	}
    		File f =new File(path+"Violet/"+name);		
    		deleteFileFirstLine(f);
    		selectedFile =new LocalFile(f);
//    		System.out.println("�ı���ļ�"+selectedFile.getDirectory()+"\\"+selectedFile.getFilename());
//    		graphFile =new GraphFile(selectedFile);
    	}
    	
    	return selectedFile;
    }
    /*
     * �Ž�
     */
    private void deleteFileFirstLine(File f){
    	try {
			BufferedReader br =new BufferedReader(new FileReader(f));
			StringBuffer sb =new StringBuffer(4096);
//			int line=0;
//			int num=0;
			String temp =null;
			while((temp =br.readLine())!=null){
//				line++;
//				if(line==num) continue;
//				sb.append(temp).append( "\r\n ");
				if(temp.toString().contains("encoding")&&temp.toString().contains("version"))
					continue;
					sb.append(temp).append( "\r\n ");
			}
			br.close();
			BufferedWriter bw =new BufferedWriter(new FileWriter(f));
			bw.write(sb.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /*
     * �Ž�   ����ea�ļ� ����EASequence.seq.violet���Ƶ��ļ���
     */
    private StringBuffer dealEAFileName(IFile selectedFile) {
		String[] ss =selectedFile.getFilename().split("\\.");
		StringBuffer name=new StringBuffer();
		name.append("EA"+ss[0]);
		for (int i = 1; i < ss.length-2; i++) {
			name.append("."+ss[i]);
		}
		name.append("."+ss[ss.length-1]);
		return name;
	}
    /**
     * Init open menu entry���Ž��Ѹ�
     */
   public void initFileOpenItem()
    {
//        this.fileOpenItem.addActionListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent event)
//            {           	
//                IFile selectedFile = null;
//                try
//                { 
//                    ExtensionFilter[] filters = fileNamingService.getFileFilters();                 
//                    IFileReader fileOpener = fileChooserService.chooseAndGetFileReader(filters);
//                   
//                    if (fileOpener == null)
//                    {
//                        // Action cancelled by user
//                        return;
//                    }
//                 
//                    selectedFile = fileOpener.getFileDefinition();
//                     
//                    IGraphFile graphFile = new GraphFile(selectedFile);
//                
//                    IWorkspace workspace = new Workspace(graphFile);
//                    
//                    mainFrame.addTabbedPane(workspace);
//                  
//                    userPreferencesService.addOpenedFile(graphFile);
//                    userPreferencesService.addRecentFile(graphFile);
//                }
//                catch (StreamException se)
//                {
//                    dialogFactory.showErrorDialog(dialogOpenFileIncompatibilityMessage);
//                }
//                catch (Exception e)
//                {
//                    dialogFactory.showErrorDialog(dialogOpenFileErrorMessage + " : " + e.getMessage());
//                }
//            }
//        });
//        if (this.fileChooserService == null) this.fileOpenItem.setEnabled(false);
	   this.fileOpenItem.addActionListener(new ActionListener()
       {
           public void actionPerformed(ActionEvent event)
           {	
               IFile selectedFile = null;
               try
               {
                   ExtensionFilter[] filters = fileNamingService.getFileFilters();
                   IFileReader fileOpener = fileChooserService.chooseAndGetFileReader(filters);//�����ļ���
                   if (fileOpener == null)
                   {
                       // Action cancelled by user
                       return;
                   }
                   selectedFile = fileOpener.getFileDefinition();//����һ������·�����ļ�   
                   boolean flag=!(selectedFile.getFilename().contains("EA"));//��EA��ʽ���ļ�
                   //directory = selectedFile.getDirectory();
                  // System.out.println(directory+"@@<>");
                
                   //�����ƽ̨�����XML�ļ�
                   IGraphFile graphFile = null;
//                 //����ת���ķ���11
             
                   selectedFile= exchangeFile(selectedFile, graphFile , flag);
             
                   graphFile = new GraphFile(selectedFile);
            
                   //��ʾ�ļ�ͼ��
                   IWorkspace workspace = new Workspace(graphFile);
                   mainFrame.addTabbedPane(workspace);
                   
                   String name=(selectedFile.getFilename().split("\\."))[0]; //��������
                   String type=(selectedFile.getFilename().split("\\."))[1]; //��������
                   if("seq".equals(type)){
                	   if(mainFrame.getListSequenceTabPanel().size() != 0){
                   		for(SequenceTabPanel sequenceTabPanel:mainFrame.getListSequenceTabPanel())
                   		{
                   			sequenceTabPanel.getPanel().setBackground(new Color(246,246,246));
                   			sequenceTabPanel.getDeletelabel().setIcon(null);
                   		}
                   		mainFrame.getListSequenceTabPanel().get(mainFrame.getListSequenceTabPanel().size()-1).getPanel().setBackground(Color.white);
                   		mainFrame.getListSequenceTabPanel().get(mainFrame.getListSequenceTabPanel().size()-1).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
                   		}
                	   JTree sequencetree = mainFrame.getsequencetree().getSequencetree();
                   	   DefaultTreeModel sequencetreemodel = mainFrame.getsequencetree().getSequencetreemodel();
                   	   DefaultMutableTreeNode sequencetreerootnode = mainFrame.getsequencetree().getSequencetreerootnode();
                   	
						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
						sequencetreemodel.insertNodeInto(node, sequencetreerootnode, sequencetreerootnode.getChildCount());
						TreePath path=new TreePath(sequencetreerootnode.getPath());
						if(!sequencetree.isVisible(path)){
							sequencetree.makeVisible(path);
						}
						sequencetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
						Map<DefaultMutableTreeNode, JPanel> hashMap = mainFrame.getsequencetree().getHashMap();
						int length = mainFrame.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().size() - 1;
						hashMap.put(node, mainFrame.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().get(length));
						int count = mainFrame.getStepOneCenterSequenceTabbedPane().getTabCount();
						mainFrame.getListSequenceTabPanel().get(count - 1).getTitlelabel().setText(name+"  ");
						mainFrame.getStepOneCenterSequenceTabbedPane().setSelectedComponent(hashMap.get(node));	
						
						//�л�����
						JLabel sequenceLabel =  new JLabel("˳��ͼ�ǽ�������ϵ��ʾΪһ����άͼ��������ʱ���ᣬʱ���������������졣�������������Э���и������������Ԫ��ɫ��");
						sequenceLabel.setFont(new Font("����", Font.PLAIN, 16));
						mainFrame.getOpreationPart().validate();
						mainFrame.getpanel().setVisible(true);
				   		mainFrame.getinformationPanel().setVisible(true);
				   		mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
				   		mainFrame.getbotoomJSplitPane().setDividerSize(4);
				   		mainFrame.getReduceOrEnlargePanel().setVisible(true);
						mainFrame.getCenterTabPanel().removeAll();
						mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterSequenceTabbedPane());
						mainFrame.getCenterTabPanel().updateUI();
						mainFrame.getpanel().removeAll();
						mainFrame.getpanel().setLayout(new GridLayout(1, 1));
						mainFrame.getpanel().add(mainFrame.getOperationButton());
						mainFrame.getOperationButton().getOtherPanel().removeAll();
						mainFrame.getOperationButton().getOtherPanel().setLayout(new GridBagLayout());
						mainFrame.getOperationButton().getOtherPanel().add(sequenceLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,10,10,10));
						mainFrame.getpanel().updateUI();
						mainFrame.getinformationPanel().removeAll();
						mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
						mainFrame.getinformationPanel().add(mainFrame.getouOutputinformation());
						mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
						mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
						mainFrame.getOpreationPart().revalidate();
                	   
                   }
                   if("ucase".equals(type)){
                	   if(mainFrame.getListUsecaseTabPanel().size() != 0){
                   		for(UsecaseTabPanel usecaseTabPanel:mainFrame.getListUsecaseTabPanel())
                   		{
                   			usecaseTabPanel.getPanel().setBackground(new Color(246,246,246));
                   			usecaseTabPanel.getDeletelabel().setIcon(null);
                   		}
                   		mainFrame.getListUsecaseTabPanel().get(mainFrame.getListUsecaseTabPanel().size()-1).getPanel().setBackground(Color.white);
                   		mainFrame.getListUsecaseTabPanel().get(mainFrame.getListUsecaseTabPanel().size()-1).getDeletelabel().setIcon(new ImageIcon("resources\\icons\\22x22\\beforeClose.png"));
                   		}
                	    JTree usecasetree = mainFrame.getUsecaseTree().getUsecasetree();
                   	    DefaultTreeModel usecasetreemodel = mainFrame.getUsecaseTree().getUsecasetreemodel();
                      	DefaultMutableTreeNode usecasetreerootnode = mainFrame.getUsecaseTree().getUsecasetreerootnode();                        
						DefaultMutableTreeNode node=new DefaultMutableTreeNode(name);
						usecasetreemodel.insertNodeInto(node, usecasetreerootnode, usecasetreerootnode.getChildCount());
						TreePath path=new TreePath(usecasetreerootnode.getPath());
						if(!usecasetree.isVisible(path)){
							usecasetree.makeVisible(path);
						}
						usecasetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
						Map<DefaultMutableTreeNode, JPanel> hashMap = mainFrame.getUsecaseTree().getHashMap();
						int length = mainFrame.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().size() - 1;
						hashMap.put(node, mainFrame.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(length));
						int count = mainFrame.getStepOneCenterUseCaseTabbedPane().getTabCount();
						mainFrame.getListUsecaseTabPanel().get(count - 1).getTitlelabel().setText(name+"  ");
						mainFrame.getStepOneCenterUseCaseTabbedPane().setSelectedComponent(hashMap.get(node));
						mainFrame.getStepOneCenterUseCaseTabbedPane().updateUI();
						
						//�л�����
						JLabel usecasejJLabel = new JLabel("����ͼ��ָ�ɲ����ߣ�Actor����������Use Case���Լ�����֮��Ĺ�ϵ���ɵ���������ϵͳ���ܵ���ͼ��");
						usecasejJLabel.setFont(new Font("����", Font.PLAIN, 16));
						mainFrame.getOpreationPart().validate();
						mainFrame.getpanel().setVisible(true);
				   		mainFrame.getinformationPanel().setVisible(true);
				   		mainFrame.getbotoomJSplitPane().setDividerLocation(0.7);
				   		mainFrame.getbotoomJSplitPane().setDividerSize(4);
				   		mainFrame.getReduceOrEnlargePanel().setVisible(true);
						mainFrame.getCenterTabPanel().removeAll();
						mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterUseCaseTabbedPane());
						mainFrame.getCenterTabPanel().updateUI();	
						mainFrame.getpanel().removeAll();
						mainFrame.getpanel().setLayout(new GridLayout(1, 1));
						mainFrame.getpanel().add(mainFrame.getOperationButton());
						mainFrame.getOperationButton().getOtherPanel().removeAll();
						mainFrame.getOperationButton().getOtherPanel().setLayout(new GridBagLayout());
						mainFrame.getOperationButton().getOtherPanel().add(usecasejJLabel,new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10,10,10,10));
						mainFrame.getpanel().updateUI();
						mainFrame.getinformationPanel().removeAll();
						mainFrame.getinformationPanel().setLayout(new GridLayout(1, 1));
						mainFrame.getinformationPanel().add(mainFrame.getouOutputinformation());
						mainFrame.getReduceOrEnlargePanel().setLayout(new GridLayout(1, 1));
						mainFrame.getReduceOrEnlargePanel().add(mainFrame.getstepOneCenterRightPanel());
						mainFrame.getOpreationPart().revalidate();
						
                   }
                   userPreferencesService.addOpenedFile(graphFile);
                   userPreferencesService.addRecentFile(graphFile);
               }
               catch (StreamException se)
               {
                   dialogFactory.showErrorDialog(dialogOpenFileIncompatibilityMessage);
               }
               catch (Exception e)
               {
                   dialogFactory.showErrorDialog(dialogOpenFileErrorMessage + " : " + e.getMessage());
               }
           }
       });
       if (this.fileChooserService == null) 
    	   this.fileOpenItem.setEnabled(false);

    }
      
    /**
     * Init new menu entry
     */
    public void initFileNewMenu()
    {
        List<IDiagramPlugin> diagramPlugins = this.pluginRegistry.getDiagramPlugins();

        // Step 1 : sort diagram plugins by categories and names
        SortedMap<String, SortedSet<IDiagramPlugin>> diagramPluginsSortedByCategory = new TreeMap<String, SortedSet<IDiagramPlugin>>();
        for (final IDiagramPlugin aDiagramPlugin : diagramPlugins)
        {
            String category = aDiagramPlugin.getCategory();
            if (!diagramPluginsSortedByCategory.containsKey(category))
            {
                SortedSet<IDiagramPlugin> newSortedSet = new TreeSet<IDiagramPlugin>(new Comparator<IDiagramPlugin>()
                {
                    @Override
                    public int compare(IDiagramPlugin o1, IDiagramPlugin o2)
                    {
                        String n1 = o1.getName();
                        String n2 = o2.getName();
                        return n1.compareTo(n2);
                    }
                });
                diagramPluginsSortedByCategory.put(category, newSortedSet);
            }
            SortedSet<IDiagramPlugin> aSortedSet = diagramPluginsSortedByCategory.get(category);
            aSortedSet.add(aDiagramPlugin);
        }

        // Step 2 : populate menu entry
        for (String aCategory : diagramPluginsSortedByCategory.keySet())
        {
            String categoryName = aCategory.replaceFirst("[0-9]*\\.", "");
            JMenu categorySubMenu = new JMenu(categoryName);
            Dimension preferredSize = categorySubMenu.getPreferredSize();
            preferredSize = new Dimension((int) preferredSize.getWidth() + 30, (int) preferredSize.getHeight());
            categorySubMenu.setPreferredSize(preferredSize);
            fileNewMenu.add(categorySubMenu);
            SortedSet<IDiagramPlugin> diagramPluginsByCategory = diagramPluginsSortedByCategory.get(aCategory);
            for (final IDiagramPlugin aDiagramPlugin : diagramPluginsByCategory)
            {
                String name = aDiagramPlugin.getName();
                name = name.replaceFirst("[0-9]*\\.", "");
                final JMenuItem item = new JMenuItem(name);
                ImageIcon sampleDiagramImage = getSampleDiagramImage(aDiagramPlugin);
                if (sampleDiagramImage != null)
                {
                    item.setRolloverIcon(sampleDiagramImage);
                }
                item.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent event)
                    {
                    	String itemname=item.getText().substring(0, 2);
                    	if(itemname.equals("Us")){
                    		if(mainFrame.getListUsecaseTabPanel().size() != 0){
                    		for(UsecaseTabPanel usecaseTabPanel:mainFrame.getListUsecaseTabPanel())
                    		{
                    			usecaseTabPanel.getPanel().setBackground(new Color(246,246,246));
                    			usecaseTabPanel.getDeletelabel().setIcon(null);
                    		}
                    		}
                    	}
                    	if(itemname.equals("Se")){
                    		if(mainFrame.getListSequenceTabPanel().size() != 0){
                    		for(SequenceTabPanel sequenceTabPanel:mainFrame.getListSequenceTabPanel())
                    		{
                    			sequenceTabPanel.getPanel().setBackground(new Color(246,246,246));
                    			sequenceTabPanel.getDeletelabel().setIcon(null);
                    		}
                    		}
                    	}
                    	Class<? extends IGraph> graphClass = aDiagramPlugin.getGraphClass();
                    	IGraphFile graphFile = new GraphFile(graphClass);
                        IWorkspace diagramPanel = new Workspace(graphFile);
                        mainFrame.addTabbedPane(diagramPanel);
                    	if(itemname.equals("Se")){
                        	Icon icon = new ImageIcon("resources/icons/22x22/open.png");
							String str = (String) JOptionPane.showInputDialog(null,"������˳��ͼ����:\n","title",JOptionPane.PLAIN_MESSAGE,icon,null,"��������");
                        	mainFrame.getouOutputinformation().geTextArea().append("�½�˳��ͼ: "+str+".seq.violet.xml\n");
							
                        	JTree sequencetree = mainFrame.getsequencetree().getSequencetree();
                        	DefaultTreeModel sequencetreemodel = mainFrame.getsequencetree().getSequencetreemodel();
                        	DefaultMutableTreeNode sequencetreerootnode = mainFrame.getsequencetree().getSequencetreerootnode();
                        	
							DefaultMutableTreeNode node=new DefaultMutableTreeNode(str);
							sequencetreemodel.insertNodeInto(node, sequencetreerootnode, sequencetreerootnode.getChildCount());
							TreePath path=new TreePath(sequencetreerootnode.getPath());
							if(!sequencetree.isVisible(path)){
								sequencetree.makeVisible(path);
							}
							sequencetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
							Map<DefaultMutableTreeNode, JPanel> hashMap = mainFrame.getsequencetree().getHashMap();
							int length = mainFrame.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().size() - 1;
							hashMap.put(node, mainFrame.getStepOneCenterSequenceTabbedPane().getSequenceDiagramTabbedPane().get(length));
							int count = mainFrame.getStepOneCenterSequenceTabbedPane().getTabCount();
							mainFrame.getListSequenceTabPanel().get(count - 1).getTitlelabel().setText(str+"  ");
							mainFrame.getStepOneCenterSequenceTabbedPane().setSelectedComponent(hashMap.get(node));
							mainFrame.getStepOneCenterSequenceTabbedPane().updateUI();	
							mainFrame.getStepOneCenterSequenceTabbedPane().setVisible(true);
                    	}    
                    	if(itemname.equals("Us")){
                        	Icon icon = new ImageIcon("resources/icons/22x22/open.png");
							String str = (String) JOptionPane.showInputDialog(null,"������˳��ͼ����:\n","title",JOptionPane.PLAIN_MESSAGE,icon,null,"��������");
							mainFrame.getouOutputinformation().geTextArea().append("�½�����ͼ: "+str+".seq.violet.xml\n");
							
                        	JTree usecasetree = mainFrame.getUsecaseTree().getUsecasetree();
                        	DefaultTreeModel usecasetreemodel = mainFrame.getUsecaseTree().getUsecasetreemodel();
                        	DefaultMutableTreeNode usecasetreerootnode = mainFrame.getUsecaseTree().getUsecasetreerootnode();                        
							DefaultMutableTreeNode node=new DefaultMutableTreeNode(str);
							usecasetreemodel.insertNodeInto(node, usecasetreerootnode, usecasetreerootnode.getChildCount());
							TreePath path=new TreePath(usecasetreerootnode.getPath());
							if(!usecasetree.isVisible(path)){
								usecasetree.makeVisible(path);
							}
							usecasetree.getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
							Map<DefaultMutableTreeNode, JPanel> hashMap = mainFrame.getUsecaseTree().getHashMap();
							int length = mainFrame.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().size() - 1;
							hashMap.put(node, mainFrame.getStepOneCenterUseCaseTabbedPane().getUsecaseDiagramTabbedPane().get(length));
							int count = mainFrame.getStepOneCenterUseCaseTabbedPane().getTabCount();
							mainFrame.getListUsecaseTabPanel().get(count - 1).getTitlelabel().setText(str+"  ");
							mainFrame.getStepOneCenterUseCaseTabbedPane().setSelectedComponent(hashMap.get(node));
							mainFrame.getStepOneCenterUseCaseTabbedPane().updateUI();
							mainFrame.getStepOneCenterUseCaseTabbedPane().setVisible(true);
                    	}
                    	
                    }
                    
                });
                categorySubMenu.add(item);
            }
        }
    }

    /**
     * Init recent menu entry
     */
    public void initFileRecentMenu()
    {
        // Set entries on startup
        refreshFileRecentMenu();
        // Refresh recent files list each time the global file menu gets the focus
        this.addFocusListener(new FocusListener()
        {

            public void focusGained(FocusEvent e)
            {
                refreshFileRecentMenu();
            }

            public void focusLost(FocusEvent e)
            {
                // Nothing to do
            }

        });
        if (this.fileChooserService == null || (this.fileChooserService != null && this.fileChooserService.isWebStart()))
        {
            this.fileRecentMenu.setEnabled(false);
        }
    }

    /**
     * Updates file recent menu
     */
    private void refreshFileRecentMenu()
    {
        fileRecentMenu.removeAll();
        for (final IFile aFile : userPreferencesService.getRecentFiles())
        {
            String name = aFile.getFilename();
            JMenuItem item = new JMenuItem(name);
            fileRecentMenu.add(item);
            item.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    try
                    {
                        IGraphFile graphFile = new GraphFile(aFile);
                        IWorkspace workspace = new Workspace(graphFile);
                        mainFrame.addTabbedPane(workspace);
                        userPreferencesService.addOpenedFile(aFile);
                        userPreferencesService.addRecentFile(aFile);
                    }
                    catch (Exception e)
                    {
                        dialogFactory.showErrorDialog(dialogOpenFileErrorMessage + " : " + e.getMessage());
                        userPreferencesService.removeOpenedFile(aFile);
                    }
                }
            });
        }
    }

    /**
     * @param diagramPlugin
     * @return an image exported from the sample diagram file attached to each plugin or null if no one exists
     * @throws IOException
     */
    private ImageIcon getSampleDiagramImage(IDiagramPlugin diagramPlugin)
    {
        try
        {
            String sampleFilePath = diagramPlugin.getSampleFilePath();
            InputStream resourceAsStream = getClass().getResourceAsStream("/" + sampleFilePath);
            if (resourceAsStream == null) {
                return null;
            }
            IGraph graph = this.filePersistenceService.read(resourceAsStream);
            BufferedImage image = FileExportService.getImage(graph);
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setIcon(new ImageIcon(image));
            label.setSize(new Dimension(600, 550));
            label.setBackground(Color.WHITE);
            label.setOpaque(true);
            Dimension size = label.getSize();
            BufferedImage image2 = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image2.createGraphics();
            label.paint(g2);
            return new ImageIcon(image2);
        }
        catch (Exception e)
        {
        	
            // Failed to load sample. It doesn"t matter.
            return null;
        }
    }
   /*
    * �Ž��¼�
    */
        private void initFileDsaveItem()
        {
            this.fileDsaveItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    IWorkspace workspace = mainFrame.getActiveWorkspace();
                    if (workspace != null)
                    {
                        IGraphFile graphFile = workspace.getGraphFile();
                        graphFile.dsave();
                    }
                }
            });
            if (this.fileChooserService == null || (this.fileChooserService != null && this.fileChooserService.isWebStart()))
            {
                this.fileDsaveItem.setEnabled(false);
            }
        }
   
    
    public static String getDirectory() {
		return directory;
	}
    public static String getFileName() {
		return fileName;
	}

	public static String fileName;
	public static String directory;
	 
	/** The file chooser to use with with menu */
    @InjectedBean
    public IFileChooserService fileChooserService;

    /** Application stopper */
    private ApplicationStopper stopper = new ApplicationStopper();

    /** Plugin registry */
    @InjectedBean
    private PluginRegistry pluginRegistry;

    /** DialogBox handler */
    @InjectedBean
    private DialogFactory dialogFactory;

    /** Access to user preferences */
    @InjectedBean
    private UserPreferencesService userPreferencesService;

    /** File services */
    @InjectedBean
    private FileNamingService fileNamingService;
    
    /** Service to convert IGraph to XML content (and XML to IGraph of course) */
    @InjectedBean
    private IFilePersistenceService filePersistenceService;

    /** Application main frame */
    private MainFrame mainFrame;

    @ResourceBundleBean(key = "file.new")
    private JMenu fileNewMenu;

    @ResourceBundleBean(key = "file.open")
    public JMenuItem fileOpenItem;

    @ResourceBundleBean(key="file.dsave")
    private JMenuItem fileDsaveItem;
    
    @ResourceBundleBean(key = "file.recent")
    private JMenu fileRecentMenu;

    @ResourceBundleBean(key = "file.close")
    private JMenuItem fileCloseItem;

    @ResourceBundleBean(key = "file.save")
    private JMenuItem fileSaveItem;

    @ResourceBundleBean(key = "file.save_as")
    private JMenuItem fileSaveAsItem;

    @ResourceBundleBean(key = "file.export_to_image")
    private JMenuItem fileExportToImageItem;

    @ResourceBundleBean(key = "file.export_to_clipboard")
    private JMenuItem fileExportToClipBoardItem;

    @ResourceBundleBean(key = "file.export_to_java")
    private JMenuItem fileExportToJavaItem;

    @ResourceBundleBean(key = "file.export_to_python")
    private JMenuItem fileExportToPythonItem;

    @ResourceBundleBean(key = "file.export")
    private JMenu fileExportMenu;

    @ResourceBundleBean(key = "file.print")
    private JMenuItem filePrintItem;

    @ResourceBundleBean(key = "file.exit")
    private JMenuItem fileExitItem;

    @ResourceBundleBean(key = "dialog.close.title")
    private String dialogCloseTitle;

    @ResourceBundleBean(key = "dialog.close.ok")
    private String dialogCloseMessage;

    @ResourceBundleBean(key = "dialog.close.icon")
    private ImageIcon dialogCloseIcon;

    @ResourceBundleBean(key = "dialog.open_file_failed.text")
    private String dialogOpenFileErrorMessage;

    @ResourceBundleBean(key = "dialog.open_file_content_incompatibility.text")
    private String dialogOpenFileIncompatibilityMessage;

}
