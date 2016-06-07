/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2008 Cay S. Horstmann (http://horstmann.com)
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

package com.horstmann.violet.application.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.horstmann.violet.application.consolepart.ConsolePart;
import com.horstmann.violet.application.help.AboutDialog;
import com.horstmann.violet.application.menu.MenuFactory;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.theme.ITheme;
import com.horstmann.violet.framework.theme.ThemeManager;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.node.AbstractNode;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;
import com.horstmann.violet.product.diagram.abstracts.property.BentStyle;
import com.horstmann.violet.product.diagram.abstracts.property.LineStyle;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.IWorkspaceListener;
import com.horstmann.violet.workspace.Workspace;

/**
 * This desktop frame contains panes that show graphs.
 * 
 * @author Alexandre de Pellegrin
 */
@ResourceBundleBean(resourceReference = AboutDialog.class)
public class MainFrame extends JFrame
{
    /**
     * Constructs a blank frame with a desktop pane but no graph windows.
     * 
     */
    public MainFrame()
    {
        BeanInjector.getInjector().inject(this);
        ResourceBundleInjector.getInjector().inject(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.dialogFactory.setDialogOwner(this);
        decorateFrame();
        setInitialSize();
        createMenuBar();
        getContentPane().add(this.getMainPanel());
      
    }

    /**
     * Sets initial size on startup
     */
    private void setInitialSize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setBounds(screenWidth / 16, screenHeight / 16, screenWidth * 7 / 8, screenHeight * 7 / 8);
        // For screenshots only -> setBounds(50, 50, 850, 650);
    }

    /**
     * Decorates the frame (title, icon...)
     */
    private void decorateFrame()
    {
        setTitle(this.applicationName);
        setIconImage(this.applicationIcon);
    }

    /**
     * Creates menu bar
     */
    private void createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(this.themeManager.getTheme().getMenubarFont());
        MenuFactory menuFactory = getMenuFactory();
        menuBar.add(menuFactory.getFileMenu(this));
        menuBar.add(menuFactory.getEditMenu(this));
        menuBar.add(menuFactory.getViewMenu(this));
        menuBar.add(menuFactory.getHelpMenu(this));
        setJMenuBar(menuBar);
    }

    /**
     * Adds a tabbed pane (only if not already added)
     * 
     * @param c the component to display in the internal frame
     */
    public void addTabbedPane(final IWorkspace workspace )
    {
      // replaceWelcomePanelByTabbedPane();  
//        if (this.workspaceList.contains(workspace))
//        {
//            return;
//        }
//        this.workspaceList.add(workspace);       
       // this.getTabbedPane().add(workspace.getTitle(),workspace.getAWTComponent());       	   
        this.getStepOneCenterTabbedPane().getUMLTabbedPane(workspace).
        add(workspace.getTitle(),workspace.getAWTComponent());
     //   listenToDiagramPanelEvents(workspace);
        // Use invokeLater to prevent exception
//        SwingUtilities.invokeLater(new Runnable()        {
//            public void run()
//            {
//                getTabbedPane().setSelectedIndex(workspaceList.size() - 1);
//                workspace.getEditorPart().getSwingComponent().requestFocus();
//            }
//        });
    }

    /**
     * Add a listener to perform action when something happens on this diagram
     * 
     * @param diagramPanel
     */
    private void listenToDiagramPanelEvents(final IWorkspace diagramPanel)
    {
        diagramPanel.addListener(new IWorkspaceListener()
        {
            public void titleChanged(String newTitle)
            {
                int pos = workspaceList.indexOf(diagramPanel);
                getTabbedPane().setTitleAt(pos, newTitle);
            }

            public void graphCouldBeSaved()
            {
                // nothing to do here
            }

            public void mustOpenfile(IFile file)
            {
                try
                {
                    IGraphFile graphFile = new GraphFile(file);
                    IWorkspace newWorkspace = new Workspace(graphFile);
                    addTabbedPane(newWorkspace);
                }
                catch (IOException e)
                {
                    DialogFactory.getInstance().showErrorDialog(e.getMessage());
                }
            }
        });
    }

    private void replaceWelcomePanelByTabbedPane()
    {
        WelcomePanel welcomePanel = this.getWelcomePanel();
        //JTabbedPane tabbedPane = getTabbedPane();     
        getMainPanel().remove(welcomePanel);        
       // getMainPanel().add(tabbedPane, new GBC(1,1,1,1).setFill(GBC.BOTH).setWeight(1, 1));
        repaint();
    }

    private void replaceTabbedPaneByWelcomePanel()
    {
        this.welcomePanel = null;
        WelcomePanel welcomePanel = this.getWelcomePanel();
        JTabbedPane tabbedPane = getTabbedPane();
        getMainPanel().remove(tabbedPane);
        getMainPanel().add(welcomePanel, BorderLayout.CENTER);
        repaint();
    }

    /**
     * @return the tabbed pane that contains diagram panels
     */
    public JTabbedPane getTabbedPane()
    {
        if (this.tabbedPane == null)
        {
            this.tabbedPane = new JTabbedPane()
            {
                public void paint(Graphics g)
                {
                    Graphics2D g2 = (Graphics2D) g;
                    Paint currentPaint = g2.getPaint();
                    ITheme LAF = themeManager.getTheme();
                    GradientPaint paint = new GradientPaint(getWidth() / 2, -getHeight() / 4, LAF.getWelcomeBackgroundStartColor(),
                            getWidth() / 2, getHeight() + getHeight() / 4, LAF.getWelcomeBackgroundEndColor());
                    g2.setPaint(paint);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setPaint(currentPaint);
                    super.paint(g);
                }
            };
            this.tabbedPane.setOpaque(false);
            MouseWheelListener[] mouseWheelListeners = this.tabbedPane.getMouseWheelListeners();
            for (int i = 0; i < mouseWheelListeners.length; i++)
            {
                this.tabbedPane.removeMouseWheelListener(mouseWheelListeners[i]);
            }
        }
        return this.tabbedPane;
    }

    /**
     * Removes a diagram panel from this editor frame
     * 
     * @param diagramPanel
     */
    public void removeDiagramPanel(IWorkspace diagramPanel)
    {
        if (!this.workspaceList.contains(diagramPanel))
        {
            return;
        }
        JTabbedPane tp = getTabbedPane();
        int pos = this.workspaceList.indexOf(diagramPanel);
        tp.remove(pos);
        this.workspaceList.remove(diagramPanel);
        if (tp.getTabCount() == 0)
        {
            replaceTabbedPaneByWelcomePanel();
        }
    }

    /**
     * Looks for an opened diagram from its file path and focus it
     * 
     * @param diagramFilePath diagram file path
     */
    public void setActiveDiagramPanel(IFile aGraphFile)
    {
        if (aGraphFile == null) return;
        for (IWorkspace aDiagramPanel : this.workspaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.workspaceList.indexOf(aDiagramPanel);
                JTabbedPane tp = getTabbedPane();
                tp.setSelectedIndex(pos);
                return;
            }
        }
    }

    /**
     * @return true if at least a diagram is displayed
     */
    public boolean isThereAnyDiagramDisplayed()
    {
        return !this.workspaceList.isEmpty();
    }

    public List<IWorkspace> getWorkspaceList()
    {
        return workspaceList;
    }

    /**
     * @return selected diagram file path (or null if not one is selected; that should never happen)
     */
    public IWorkspace getActiveWorkspace()
    {
        JTabbedPane tp = getTabbedPane();
        int pos = tp.getSelectedIndex();
        if (pos >= 0)
        {
            return this.workspaceList.get(pos);
        }
        throw new RuntimeException("Error while retreiving current active diagram panel");
    }
   
   public WelcomePanel getWelcomePanel()
    {
        if (this.welcomePanel == null)
        {
            this.welcomePanel = new WelcomePanel(this.getMenuFactory().getFileMenu(this));
        }
        return this.welcomePanel;
    }
   public HomePanel getHomePanel()
   {
   	if(this.homepanel==null)
   	{
   		this.homepanel=new HomePanel();
   	}
   	return this.homepanel;
   }
    private StepButtonPanel getStepButton()
    {
    	if(this.stepButton==null)
    	{
    		this.stepButton=new StepButtonPanel(this);
    	}
    	return this.stepButton;
    }
    public ConsolePart getConsolePart()
    {
    	if(this.consolePart==null)
    	{
    		this.consolePart=new ConsolePart(this);
    	}
    	return this.consolePart;
    }
    public ProjectTree getProjectTree()
    {
    	if(this.projectTree==null)
    	{
    		this.projectTree=new ProjectTree(this.getMenuFactory().getFileMenu(this),this);
    	}
    	return this.projectTree;
    }
    public ModelTransformationPanel getModelTransformationPanel()
    {
    	if(this.modelTransformationPanel==null)
    	{
    		this.modelTransformationPanel=new ModelTransformationPanel(this);
    		
    	}
    	return this.modelTransformationPanel;
    }
    public JPanel getMainPanel() {//主面板布局
        if (this.mainPanel == null) {
        	GridBagLayout layout=new GridBagLayout();
            this.mainPanel = new JPanel(layout);
            this.mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));                                                 
            this.mainPanel.add(this.getStepButton());
            this.mainPanel.add(this.getStepJLabel());
            this.mainPanel.add(this.getConsolePart());
            this.mainPanel.add(this.getOpreationPart());
            this.mainPanel.add(this.getCenterTabPanel());
            this.getCenterTabPanel().setLayout(new GridLayout(1, 1));
            this.getCenterTabPanel().add(this.getHomePanel());//默认添加首页
            this.getStepJLabel().add(new JLabel("项目演示区"),JLabel.CENTER);
                 
            layout.setConstraints(this.getStepButton(),new GBC(0, 0, 1, 3).setFill(GBC.BOTH).setWeight(0, 1));                                    
            layout.setConstraints(this.getStepJLabel(), new GBC(1,0,1,1).setFill(GBC.BOTH).setWeight(1, 0));
            layout.setConstraints(this.getConsolePart(), new GBC(1,2,1,1).setFill(GBC.BOTH).setWeight(1, 0));
            layout.setConstraints(this.getOpreationPart(), new GBC(2,1,1,2).setFill(GBC.BOTH).setWeight(0, 1));
            layout.setConstraints(this.getCenterTabPanel(), new GBC(1,1,1,1).setFill(GBC.BOTH).setWeight(1, 1));
		    		
           
                
           // this.mainPanel.add(this.getProjectTree(),BorderLayout.EAST);
           // JPanel bottomBorderPanel = new JPanel();
//            ITheme cLAF = this.themeManager.getTheme();
//            this.getConsolePart().setBackground(cLAF.getMenubarBackgroundColor().darker());
//            this.getConsolePart().setBorder(new EmptyBorder(0, 0, 0, 0));
//            this.getConsolePart().setSize(getContentPane().getWidth(), 8);
           // this.mainPanel.add(bottomBorderPanel, BorderLayout.SOUTH);
           
        }
        return this.mainPanel;
    }
    
    private JPanel getControlPanel() {
		// TODO Auto-generated method stub
		JPanel controlpanel=new JPanel();
		controlpanel.setLayout(new BoxLayout(controlpanel, BoxLayout.X_AXIS));
		controlpanel.add(new JButton("开始"));
		controlpanel.add(new JButton("暂停"));
		return controlpanel;
	}
    public StepOneCenterTabbedPane getStepOneCenterTabbedPane()
    {
    if (this.stepOneCenterTabbedPane== null)
    {
       stepOneCenterTabbedPane=new StepOneCenterTabbedPane();
    }
    return this.stepOneCenterTabbedPane;
    	
    }
    public StepTwoCenterTabbedPane getStepTwoCenterTabbedPane()
    {
    if (this.stepTwoCenterTabbedPane== null)
    {
       stepTwoCenterTabbedPane=new StepTwoCenterTabbedPane();
    }
    return this.stepTwoCenterTabbedPane;
    	
    }
    
	public JPanel getOpreationPart() {
		// TODO Auto-generated method stub
		return this.opreationpanel;
	}
	public JPanel getCenterTabPanel(){
		// TODO Auto-generated method stub
		return this.centerTabPanel;
	}
	/**
     * @return the menu factory instance
     */
    public MenuFactory getMenuFactory()
    {
        if (this.menuFactory == null)
        {
            menuFactory = new MenuFactory();
        }
        return this.menuFactory;
    }

    public JPanel getStepJLabel() {
		return stepJLabel;
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public void setStepJLabel(JPanel stepJLabel) {
		this.stepJLabel = stepJLabel;
	}

	/**
     * Tabbed pane instance
     */
    private JTabbedPane tabbedPane;

    /**
     * Panel added is not diagram is opened
     */
    private WelcomePanel welcomePanel;
    private HomePanel homepanel;
    private JPanel stepJLabel=new JPanel();
    private ModelTransformationPanel modelTransformationPanel;
    private StepOneCenterTabbedPane stepOneCenterTabbedPane;
    private StepTwoCenterTabbedPane stepTwoCenterTabbedPane;
    private ConsolePart consolePart;
    private ProjectTree projectTree;
    private StepButtonPanel stepButton;
    private JPanel centerPanel;
    private JPanel opreationpanel=new JPanel();
    private JPanel centerTabPanel=new JPanel();
    /**
     * Main panel
     */
    private JPanel mainPanel;

    /**
     * Menu factory instance
     */
    private MenuFactory menuFactory;

    /**
     * GUI Theme manager
     */
    @InjectedBean
    private ThemeManager themeManager;

    /**
     * Needed to display dialog boxes
     */
    @InjectedBean
    private DialogFactory dialogFactory;

    /**
     * Needed to open files
     */
    @InjectedBean
    private IFileChooserService fileChooserService;
    
    @ResourceBundleBean(key="app.name")
    private String applicationName;
    
    @ResourceBundleBean(key="app.icon")
    private Image applicationIcon;

    /**
     * All disgram workspaces
     */
    private List<IWorkspace> workspaceList = new ArrayList<IWorkspace>();

    // workaround for bug #4646747 in J2SE SDK 1.4.0
    private static java.util.HashMap<Class<?>, BeanInfo> beanInfos;
    static
    {
        beanInfos = new java.util.HashMap<Class<?>, BeanInfo>();
        Class<?>[] cls = new Class<?>[]
        {
                Point2D.Double.class,
                BentStyle.class,
                ArrowHead.class,
                LineStyle.class,
                IGraph.class,
                AbstractNode.class,
        };
        for (int i = 0; i < cls.length; i++)
        { 
            try
            {
                beanInfos.put(cls[i], java.beans.Introspector.getBeanInfo(cls[i]));
            }
            catch (java.beans.IntrospectionException ex)
            {
            }
        }
    }
}
