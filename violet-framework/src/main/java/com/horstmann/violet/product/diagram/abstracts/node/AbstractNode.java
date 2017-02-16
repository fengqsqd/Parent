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

package com.horstmann.violet.product.diagram.abstracts.node;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.horstmann.violet.product.diagram.abstracts.AbstractGraph;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.Id;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.ISequenceTimeEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.SEdge;
import com.horstmann.violet.product.diagram.abstracts.property.Condition;
import com.horstmann.violet.product.diagram.abstracts.property.SceneConstraint;
import com.horstmann.violet.product.diagram.abstracts.property.UseConstraint;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentPart;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * A class that supplies convenience implementations for a number of methods in the Node interface
 * 
 * @author Cay Horstmann
 */
public abstract class AbstractNode implements INode
{
    /**
     * Constructs a node with no parents or children at location (0, 0).
     */
    public AbstractNode()
    {
        // Nothing to do
    	
    }
   
    @Override
	public MultiLineString getState0() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiLineString getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void AddNestingChild(INode nestingChildNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SEdge getChild() {
		// TODO Auto-generated method stub
		return this.schildren;
	}
    @Override
	public String getID() {
		return ID;
	}
    @Override
	public void setID(String iD) {
		ID = iD;
	}

	@Override
	public void addchild(SEdge edge) {
		
		this.schildren=edge;
	}

	@Override
	public void setFlag(boolean flag) {
		
		this.flag=flag;
	}

	@Override
	public boolean getFlag() {
		
		return flag;
	}

	/**
     * @return currently connected edges
     */
    protected List<IEdge> getConnectedEdges()
    {
        List<IEdge> connectedEdges = new ArrayList<IEdge>();
        IGraph currentGraph = getGraph();
        for (IEdge anEdge : currentGraph.getAllEdges())
        {
            INode start = anEdge.getStart();
            INode end = anEdge.getEnd();
            if (this.equals(start) || this.equals(end))
            {
                connectedEdges.add(anEdge);
            }
        }
        return connectedEdges;
    }

    @Override
    public Point2D getLocation()
    {
        if (this.location == null) {
        	this.location = new Point2D.Double(0, 0);
        }
    	return this.location;
    }

    @Override
    public Point2D getLocationOnGraph()
    {
        INode parentNode = getParent();
        if (parentNode == null)
        {
            return getLocation();
        }
        Point2D parentLocationOnGraph = parentNode.getLocationOnGraph();
        Point2D relativeLocation = getLocation();
        Point2D result = new Point2D.Double(parentLocationOnGraph.getX() + relativeLocation.getX(), parentLocationOnGraph.getY()
                + relativeLocation.getY());
        return result;
    }

    @Override
    public void setLocation(Point2D aPoint)
    {
        this.location = aPoint;
    }

    @Override
    public Id getId()
    {
        if (this.idN == null) {
        	this.idN = new Id();
        }
    	return this.idN;
    }

    @Override
    public void setId(Id id)
    {
        this.idN = id;
    }
    
    @Override
    public Integer getRevision()
    {
        if (this.revision == null) {
        	this.revision = new Integer(0);
        }
    	return this.revision;
    }

    @Override
    public void setRevision(Integer newRevisionNumber)
    {
        this.revision = newRevisionNumber;
    }

    @Override
    public void incrementRevision()
    {
        int i = getRevision().intValue();
        i++;
        this.revision = new Integer(i);
    }

    @Override
    public void translate(double dx, double dy)
    {
        Point2D newLocation = new Point2D.Double(getLocation().getX() + dx, getLocation().getY() + dy);
        setLocation(newLocation);
    }
    
    @Override
    public boolean addConnection(IEdge e)
    {
        return e.getEnd() != null;
    }

    @Override
    public void removeConnection(IEdge e)
    {
    }

    @Override
    public void removeChild(INode node)
    {
        if (node.getParent() != this) return;
        getChildren().remove(node);
    }

    @Override
    public boolean addChild(INode n, Point2D p)
    {
        return false;
    }

    @Override
	public ArrayList<INode> getNestingChild() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public INode getParent()
    {
        return parent;
    }

    @Override
    public void setParent(INode node)
    {
        parent = node;
    }

    @Override
    public List<INode> getChildren()
    {
        if (this.children == null) {
        	this.children = new ArrayList<INode>();
        }
    	return children;
    }

    @Override
    public boolean addChild(INode node, int index)
    {
        INode oldParent = node.getParent();//oldParntΪnode�ĸ��ڵ�
        if (oldParent != null) oldParent.removeChild(node);
        //����и��ڵ㣬��node�Ӿɵĸ��ڵ�ɾ��
        getChildren().add(index, node);//��node�ڵ��Ϊ������(this)���ӽڵ�
        node.setParent(this);
        node.setGraph(getGraph());
        return true;
    }
    
    //+++++========================
    
    
    //+++++========================
    

    /**
     * @return the shape to be used for computing the drop shadow
     */
    public Shape getShape()
    {
        return new Rectangle2D.Double(0, 0, 0, 0);
    }

    @Override
	public Condition getCondition() {
		// TODO Auto-generated method stub
		return null;
	}
    public UseConstraint getConstraint()
    {
    	return null;
    }
    public SceneConstraint getSceneConstraint()
    {
    	return null;
    }
    
	@Override
	public List<FragmentPart> getFragmentParts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFragmentParts(List<FragmentPart> fragmentParts) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public AbstractNode clone()
    {
        try
        {
            AbstractNode cloned = (AbstractNode) super.clone();
            cloned.idN = getId().clone();
            cloned.children = new ArrayList<INode>();
            cloned.location = (Point2D.Double) getLocation().clone();
            for (INode child : getChildren())
            {
                INode clonedChild = child.clone();
                cloned.children.add(clonedChild);
                clonedChild.setParent(cloned);
            }
            return cloned;
        }
        catch (CloneNotSupportedException exception)
        {
            return null;
        }
    }

    @Override
    public void setGraph(IGraph g)
    {
        this.graph = g;
        for (INode aChild : getChildren()) {
        	aChild.setGraph(g);
        }
    }

    @Override
    public IGraph getGraph()
    {
        if (this.graph == null) {
        	this.graph = new AbstractGraph()
            {
                @Override
                public List<INode> getNodePrototypes()
                {
                    return new ArrayList<INode>();
                }

                @Override
                public List<IEdge> getEdgePrototypes()
                {
                    return new ArrayList<IEdge>();
                }
              
            };
        }
    	return this.graph;
    }

    @Override
    public int getZ()
    {
        return z;
    }
//    @Override
//    public double getHeight()
//    {
//    	return height;
//    }
    @Override
    public void setZ(int z)
    {
        this.z = z;
    }

    /**
     * Sets node tool tip
     * 
     * @param label
     */
    public void setToolTip(String s)
    {
        this.toolTip = s;
    }

    @Override
    public String getToolTip()
    {
        if (this.toolTip == null) {
        	this.toolTip = "";
        }
    	return this.toolTip;
    }
    
   

	private ArrayList<INode> children;
    private INode parent;
    private SEdge schildren;
    private transient IGraph graph;
    private Point2D location;
    private transient String toolTip;
    private transient int z; 
    private String ID;
    @XStreamOmitField
    private boolean flag;
    
//    private double height;
    /** Node's current id (unique in all the graph) */
    private Id idN;

    /** Node's current revision */
    private transient Integer revision;
}
