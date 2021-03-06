
package com.horstmann.violet.workspace.sidebar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.PanelUI;

import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.theme.ThemeManager;
import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

@ResourceBundleBean(resourceReference=SideBar.class)
public class SideBarUI extends PanelUI
{

    public SideBarUI(SideBar sideBar)
    {
        this.sideBar = sideBar;
        ResourceBundleInjector.getInjector().inject(this);
    }

    @Override
    public void installUI(JComponent c)
    {
        c.removeAll();
        c.setLayout(new BorderLayout());
        this.taskPane = new JTaskPane();//可以隐藏控件
        this.taskPane.setLayout(new GridBagLayout());
        this.taskPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        addElementToTaskPane(this.sideBar.getEditorToolsBar().getAWTComponent(), "图形编辑   ", 1);
        addElementToTaskPane(this.sideBar.getGraphToolsBar().getAWTComponent(), "图形工具     ", 2);
        addElementToTaskPane(this.sideBar.getOptionalToolsBar().getAWTComponent(), "其他功能     ", 3);
        addElementToTaskPane(this.sideBar.getColorChoiceBar().getAWTComponent(), "颜色选择       ", 4);
        //addElementToTaskPane(new ProjectTree().getjTree(),"project", 0);
      //添加4个组件
        int i = 3;
        for (ISideBarElement anExternalElement : this.sideBar.getExternalContributionElements().keySet()) {
            String externalElementTitle = this.sideBar.getExternalContributionElements().get(anExternalElement);
            addElementToTaskPane(anExternalElement.getAWTComponent(), externalElementTitle, i++);
        }//扩展，可以在后面继续添加组件
        c.add(taskPane, BorderLayout.NORTH);//
        c.setBorder(new MatteBorder(0, 1, 0, 0, ThemeManager.getInstance().getTheme().getSidebarBorderColor()));
        //fixWidth();
        this.sideBar.doLayout();
        JRootPane rootPane = SwingUtilities.getRootPane(this.sideBar);
        if (rootPane != null) {
            rootPane.repaint();
        }
    }

    private void fixWidth()
    {
        JLabel sizer = new JLabel();
        sizer.setPreferredSize(new Dimension(215, 1));
        this.taskPane.add(sizer);
    }

    private void addElementToTaskPane(final Component c, String title, int order)
    {
        JTaskPaneGroup group = new JTaskPaneGroup();
        Font font = group.getFont().deriveFont(Font.PLAIN);
        group.setFont(font);
        group.setTitle(title);
        group.setFont(new Font("微软黑体", Font.PLAIN, 14));
        group.setLayout(new BorderLayout());
        group.add(c, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = order;
        this.taskPane.add(group,gbc);
    }

    private SideBar sideBar;
    private JTaskPane taskPane;

    @ResourceBundleBean(key = "title.standardbuttons.text")
    private String standardButtonsTitle;

    @ResourceBundleBean(key = "title.diagramtools.text")
    private String diagramToolsTitle;

    @ResourceBundleBean(key = "title.extendedfunctions.text")
    private String extendedFunctionsTitle;
    
    @ResourceBundleBean(key = "title.colortools.text")
    private String colorToolsTitle;

}
