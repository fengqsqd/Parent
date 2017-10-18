package com.horstmann.violet.framework.propertyeditor.customeditor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.horstmann.violet.product.diagram.abstracts.property.SceneConstraint;
import com.horstmann.violet.product.diagram.abstracts.property.SequenceConstraint;
import com.horstmann.violet.product.diagram.abstracts.property.UseConstraint;
import com.horstmann.violet.product.diagram.abstracts.property.Usecaseconstraint;

public class SequenceConstraintEditor extends PropertyEditorSupport{
	public boolean supportsCustomEditor()
    {
        return true;
    } 

    public Component getCustomEditor()
    {     
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,1));
        panel.add(getOtherComponent());
        return panel;
    }

	private Component getOtherComponent() {
		// TODO Auto-generated method stub
		scenceConstraint = (SceneConstraint)getValue();
		constrainttable = new SequenceJtabel(constraintTableModel)
				{
			public String getToolTipText(MouseEvent e) {  
                int row=constrainttable.rowAtPoint(e.getPoint());  
                int col=constrainttable.columnAtPoint(e.getPoint());  
                String tiptextString=null;  
                if(row>-1 && col>-1){  
                    Object value=constrainttable.getValueAt(row, col);  
                    if(null!=value && !"".equals(value))  
                        tiptextString=value.toString();//悬浮显示单元格内容  
                }  
                return tiptextString;  
            } 
				};
		constrainttable.setRowHeight(25);
		constrainttable.setFont(new Font("宋体",Font.PLAIN,14));
		DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
		constrainttable.getTableHeader().setFont(new Font("宋体",Font.PLAIN,14));
        constrainttable.getTableHeader().setVisible(true);
        constrainttable.getTableHeader().setReorderingAllowed(true);
        constrainttable.getTableHeader().setResizingAllowed(true);        
        constrainttable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        constrainttable.getTableHeader().setPreferredSize(new Dimension(400, 25));       
        initconstraint();
        constraintAddButton.setText("新建");
        constraintAddButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt)
        	{ 
        		constrainttable.addcolumn();        
        		// firePropertyChange();
        	}
        });
        constraintSaveButton.setText("保存");
        constraintSaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
                saveConstraint();
			}
		});
        constraintDeleteButton.setText("删除");
        constraintDeleteButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt)
        	{
        		if(constrainttable.getRowCount() != 0)
        		constrainttable.deletecolumn();
        	}
        });
        jScrollPane.setBorder(BorderFactory.createEtchedBorder());
        jScrollPane.setViewportView(constrainttable);        
        jScrollPane.setPreferredSize(new Dimension(500, 400));
//        GroupLayout panelLayout=new GroupLayout(panel);
//        panel.setLayout(panelLayout);
//        panelLayout.setHorizontalGroup(
//        	panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//        	.addGroup(panelLayout.createSequentialGroup()       	    	    
//        	       .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
//        		       .addGroup(panelLayout.createSequentialGroup()
//        		           .addContainerGap()
//        		           .addComponent(constraintAddButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
//        		           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//        		           .addComponent(constraintDeleteButton, GroupLayout.PREFERRED_SIZE, 60,GroupLayout.PREFERRED_SIZE)
//        		           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//        		           .addComponent(constraintSaveButton,GroupLayout.PREFERRED_SIZE,60,GroupLayout.PREFERRED_SIZE))
//        		           .addComponent(jScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)))
//        		                		                 		          
//        		);
//       panelLayout.setVerticalGroup(
//        	      panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//        	      .addGroup(panelLayout.createSequentialGroup()
//        	    		  
//        	        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
//        	          .addGroup(panelLayout.createSequentialGroup()          
//        	          .addComponent(jScrollPane,GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))       	         
//        	          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//        	          .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//        	          .addComponent(constraintDeleteButton)
//        	          .addComponent(constraintAddButton)
//        	          .addComponent(constraintSaveButton))
//        	        .addContainerGap())
//        	    );
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(jScrollPane,new GBC(0, 0, 4, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(0, 0, 20, 0));
        panel.add(new JPanel(),new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));
        panel.add(constraintAddButton,new GBC(1, 1, 1, 1).setFill(GBC.BOTH).setWeight(0, 0));
        panel.add(constraintDeleteButton,new GBC(2, 1, 1, 1).setFill(GBC.BOTH).setWeight(0, 0));
        panel.add(constraintSaveButton,new GBC(3, 1, 1, 1).setFill(GBC.BOTH).setWeight(0, 0));
        return panel;
	    }
	    public void initconstraint()
	    {
	    	
	    	if(constrainttable.isEditing())
	    		constrainttable.getCellEditor().stopCellEditing();
	    	
	    	int count = scenceConstraint.getConstraints().size();
	    	List<SequenceConstraint> constraints = scenceConstraint.getConstraints();
	    	for(int i = 0; i < count; i++)
	    	{
	    		constrainttable.addcolumn();
	    		constrainttable.getModel().setValueAt(constraints.get(i).getSequenceName(), i, 0);
	    		constrainttable.getModel().setValueAt(constraints.get(i).getName(), i, 1);
	    		constrainttable.getModel().setValueAt(constraints.get(i).getContent(), i,  2);
	    		constrainttable.getModel().setValueAt(constraints.get(i).getType(), i,  3);
	    	}
//	    	int count = allConstraint.getNameList().size();
//	    	for(int i = 0; i < count; i++)
//	    	{
//	    		constrainttable.addcolumn();
//	    		constrainttable.getModel().setValueAt(allConstraint.getNameList().get(i), i, 0);
//	    		constrainttable.getModel().setValueAt(allConstraint.getContentList().get(i), i,  1);
//	    		constrainttable.getModel().setValueAt(allConstraint.getTypeList().get(i), i,  2);
//	    	}
	    }
	    public void saveConstraint()
	    {
	    	int count = constrainttable.getRowCount();
	    	scenceConstraint.getConstraints().clear();
	    	scenceConstraint.getSequenceName().clear();
	    	if(count != 0)
	    	{
	    		for(int i = 0;i < count ;i++)
	    		{
	    			SequenceConstraint constraint = new SequenceConstraint();
	    			constraint.setSequenceName((String) constrainttable.getModel().getValueAt(i, 0));
	    			scenceConstraint.getSequenceName().add((String) constrainttable.getModel().getValueAt(i, 0));
	    			constraint.setName((String) constrainttable.getModel().getValueAt(i, 1));
	    			constraint.setContent((String) constrainttable.getModel().getValueAt(i, 2));
	    			constraint.setType((String) constrainttable.getModel().getValueAt(i, 3));
	    			scenceConstraint.getConstraints().add(constraint);
	    		}
	    	}
	    }
	    private SceneConstraint scenceConstraint;
	    private SequenceJtabel constrainttable;
	    private JButton constraintAddButton=new JButton();
	    private JButton constraintDeleteButton=new JButton();
	    private JButton constraintSaveButton=new JButton();
	    private JScrollPane jScrollPane=new JScrollPane(); 
	    private SequenceTabelModel constraintTableModel = new SequenceTabelModel(); 

}
