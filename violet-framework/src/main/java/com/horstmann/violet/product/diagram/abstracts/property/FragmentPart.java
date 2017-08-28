
package com.horstmann.violet.product.diagram.abstracts.property;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import com.horstmann.violet.product.diagram.abstracts.Id;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class FragmentPart implements Serializable{
	/**
	 * Constructs an empty, centered, normal size multiline string that is not
	 * underlined.
	 */
	public FragmentPart() {
	   conditionText="";			  	 
	   borderline=new Line2D.Double();	
	   nestingChildNodesID=new ArrayList<String>();
	   coveredMessagesID=new ArrayList<String>();
	   coveredLifelinedID = new ArrayList<String>();
	}			
	public String getConditionText() {
		return conditionText;
	}
	public void setConditionText(String conditionText) {
		this.conditionText = conditionText;
	}   
	public ArrayList<String> getCoveredMessagesID() {
		return coveredMessagesID;
	}
	public void AddCoveredMessagesID(String coveredMessageID) {
		this.coveredMessagesID.add(coveredMessageID);
	}
	public ArrayList<String> getCoveredLifelinedID() {
		if(this.coveredLifelinedID == null){
			coveredLifelinedID = new ArrayList<String>();
		}
		return coveredLifelinedID;
	}
	public void AddCoveredLifelinedID(String coveredLifelinedID) {
		this.coveredLifelinedID.add(coveredLifelinedID);
	}
	public Line2D getBorderline() {
		return borderline;
	}
	public void setBorderline(Line2D borderline) {
		this.borderline = borderline;
	}
	public boolean isBorderlinehaschanged() {
		return borderlinehaschanged;
	}
	public void setBorderlinehaschanged(boolean borderlinehaschanged) {
		this.borderlinehaschanged = borderlinehaschanged;
	}
	
	public Rectangle2D getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds;
		setSize(bounds.getHeight());
	}
    
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public ArrayList<String> getNestingChildNodesID() {
		return nestingChildNodesID;
	}
	public void AddNestingChildNodesID(String nestingChildNodeID) {
		this.nestingChildNodesID.add(nestingChildNodeID);
	}
	private String conditionText;//�໤����������	
	private ArrayList<String> coveredMessagesID;//���ǵ���Ϣ
	private ArrayList<String> coveredLifelinedID; //���������ߵ���Ϣ
	private Line2D borderline;//Ĭ�Ͻ綨��
	private ArrayList<String> nestingChildNodesID;//Ƕ�׵ĺ���,Ĭ��û��	
	private boolean borderlinehaschanged = false;
	@XStreamOmitField
	private Rectangle2D bounds;
	private double size;
	
}
