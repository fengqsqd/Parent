package com.horstmann.violet.application;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.userpreferences.UserPreferencesService;
import com.horstmann.violet.workspace.IWorkspace;

public class ApplicationStopper
{

    public ApplicationStopper()
    {
        BeanInjector.getInjector().inject(this);
        ResourceBundleInjector.getInjector().inject(this);
    }

    /**
     * Exits the program if no graphs have been modified or if the user agrees to abandon modified graphs or save its.
     */
    public void exitProgram(MainFrame mainFrame)
    {
        boolean ok = isItReadyToExit(mainFrame);
        if (ok)
        {
        	//ɾ��ִ���ļ�
        	File[] files = new File(mainFrame.getBathRoute()).listFiles();
        	for(File file : files)
        	{
        		if(!file.getName().equals("TestCase") && !file.getName().equals("FailTestCase"))
        		{
        			DeleteFileUtil.delete(file.getAbsolutePath());
        		}
        	}
        	
        	
//        	File ucaseEAFile = new File(mainFrame.getBathRoute() + "/UseCaseDiagram"); 
//        	if(ucaseEAFile.listFiles() != null){
//        		for(File file : ucaseEAFile.listFiles())
//            	{
//            		file.delete();
//            	}
//        	}
//
//        	
//        	File seqEAFile = new File(mainFrame.getBathRoute() + "/SequenceDiagram"); 
//        	if(seqEAFile.listFiles() != null){
//        	for(File file : seqEAFile.listFiles())
//        	{
//        		file.delete();
//        	}
//        	}
//        	
//        	File notimeMarkovFile = new File(mainFrame.getBathRoute() + "/NoTimeMarkov");
//        	if(notimeMarkovFile.listFiles() != null){
//        	for(File file : notimeMarkovFile.listFiles())
//        	{
//        		file.delete();
//        	}
//        	}
//        	File timeMarkovFile = new File(mainFrame.getBathRoute() + "/TimeMarkov");
//        	if(timeMarkovFile.listFiles() != null){
//        	for(File file : timeMarkovFile.listFiles())
//        	{
//        		file.delete();
//        	}
//        	}
//        	File testcaseFile = new File(mainFrame.getBathRoute() + "/TestCase");
//        	if(testcaseFile.listFiles() != null){
//        	for(File file : testcaseFile.listFiles())
//        	{
//        		file.delete();
//        	}
//        	}
        	
//        	DeleteFileUtil.delete(mainFrame.getBathRoute());
            System.exit(0);
        }
    }

    /**
     * Asks user to save changes before exit.
     * 
     * @return true is all is saved either false
     */
    private boolean isItReadyToExit(MainFrame mainFrame)
    {
        List<IWorkspace> dirtyWorkspaceList = new ArrayList<IWorkspace>();
 
        List<IWorkspace> SequenceworkspaceList = mainFrame.getSequenceWorkspaceList();
        List<IWorkspace> UseCaseworkspaceList = mainFrame.getUseCaseWorkspaceList();
      
        for (IWorkspace aWorkspacel : SequenceworkspaceList)
        {
            IGraphFile graphFile = aWorkspacel.getGraphFile();
        	if (graphFile.isSaveRequired())
            {
                dirtyWorkspaceList.add(aWorkspacel);
            }
        }
        for (IWorkspace aWorkspacel : UseCaseworkspaceList)
        {
            IGraphFile graphFile = aWorkspacel.getGraphFile();
        	if (graphFile.isSaveRequired())
            {
                dirtyWorkspaceList.add(aWorkspacel);
            }
        }
        int unsavedCount = dirtyWorkspaceList.size();
        IWorkspace activeWorkspace = mainFrame.getActiveWorkspace();
        if (unsavedCount > 0)
        {
            // ask user if it is ok to close
            String message = MessageFormat.format(this.dialogExitMessage, new Object[]
            {
                new Integer(unsavedCount)
            });
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.CLOSED_OPTION, JOptionPane.YES_NO_CANCEL_OPTION,
                    this.dialogExitIcon);
            dialogFactory.showDialog(optionPane, this.dialogExitTitle, true);

            int result = JOptionPane.YES_OPTION;
            if (!JOptionPane.UNINITIALIZED_VALUE.equals(optionPane.getValue()))
            {
                result = ((Integer) optionPane.getValue()).intValue();
            }

            if (result == JOptionPane.CANCEL_OPTION)
            {
                return true;
            }
            if (result == JOptionPane.YES_OPTION)
            {
                for (IWorkspace aDirtyWorkspace : dirtyWorkspaceList)
                {
                    aDirtyWorkspace.getGraphFile().save();
                }
                this.userPreferencesService.setActiveDiagramFile(activeWorkspace.getGraphFile());
                return true;
            }
            if (result == JOptionPane.NO_OPTION)
            {

//                this.userPreferencesService.setActiveDiagramFile(activeWorkspace.getGraphFile());
                return true;
            }
        }
        if (unsavedCount == 0)
        {
            if (activeWorkspace != null)
            {
                this.userPreferencesService.setActiveDiagramFile(activeWorkspace.getGraphFile());
            }
            return true;
        }
        return false;
    }

    @ResourceBundleBean(key = "dialog.exit.icon")
    private ImageIcon dialogExitIcon;

    @ResourceBundleBean(key = "dialog.exit.ok")
    private String dialogExitMessage;

    @ResourceBundleBean(key = "dialog.exit.title")
    private String dialogExitTitle;

    @InjectedBean
    private DialogFactory dialogFactory;

    @InjectedBean
    private UserPreferencesService userPreferencesService;

}
