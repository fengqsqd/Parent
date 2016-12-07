
package com.horstmann.violet.product.diagram.abstracts.edge;


import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.thoughtworks.xstream.annotations.XStreamAlias;
//�������һ��ע�⣬��Ϊ��freamWork������޷�ʵ�����������͵�horizontal,
//�����������ϲ������ʵ�֣��ʽ�ԭʼ�ڵ��ǩ��Ϊhorizontalchild,Ϊ��֮����XML����.
public class HorizontalChild implements IHorizontalChild{
	
	private Point2D startPoint;
	private Point2D endPoint;
	private String state;
	private String condition;
	private String continuetime;
	//by tan ���ڼӵ�
	private String name;
	private String TimeConstraint;
    private int startPointTime;
    private int endPointTime;

	

	public int getStartPointTime() {
		return startPointTime;
	}

	public void setStartPointTime(int startPointTime) {
		this.startPointTime = startPointTime;
	}

	public int getEndPointTime() {
		return endPointTime;
	}

	public void setEndPointTime(int endPointTime) {
		this.endPointTime = endPointTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point2D getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point2D startPoint) {
		this.startPoint = startPoint;
	}

	public Point2D getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point2D endPoint) {
		this.endPoint = endPoint;
	}

	public String getTimeConstraint() {
		return TimeConstraint;
	}

	public void setTimeConstraint(String timeConstraint) {
		TimeConstraint = timeConstraint;
	}

	public HorizontalChild() {
		// TODO Auto-generated constructor stub
		
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getContinuetime() {
		return continuetime;
	}

	public void setContinuetime(String continuetime) {
		this.continuetime = continuetime;
	}
    @Override
	public void setStart(Point2D startpoint) {
		// TODO Auto-generated method stub
		this.startPoint=startpoint;
	}
    @Override
	public void setEnd(Point2D endpoint) {
		// TODO Auto-generated method stub
		this.endPoint=endpoint;
	}
    @Override
	public Point2D getStart() {
		// TODO Auto-generated method stub
		return startPoint;
	}
    @Override
	public Point2D getEnd() {
		// TODO Auto-generated method stub
		return endPoint;
	}

	

}
