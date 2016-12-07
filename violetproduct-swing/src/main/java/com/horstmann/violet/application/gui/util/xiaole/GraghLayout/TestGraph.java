package com.horstmann.violet.application.gui.util.xiaole.GraghLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.graph.DirectedGraph;
import org.eclipse.draw2d.graph.DirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.Node;
import org.jgraph.JGraph;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.EdgeBean;
import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.NodeBean;
import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.ReadXml;
import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.UnionEdge;
import com.realpersist.gef.layout.NodeJoiningDirectedGraphLayout;

/**
 * @author walnut
 * @version ����ʱ�䣺2007-7-18
 */
public class TestGraph extends JApplet {

	public Map gefNodeMap = null;//����gefͼ,��ʼ��

	public Map graphNodeMap = null;//����graphͼ����ʼ��

	public List edgeList = null;//�߳�ʼ��

	DirectedGraph directedGraph = null;//

	JGraph graph = null;
	
	String filename;
		
	public TestGraph(String filename) {
     	this.filename=filename;
	}
	public int[] init(int a){
		
		graphInit();
		return (paintGraph(a));			
	}
	public int[] paintGraph(int a) {//a��ʾĳһ��template
		int EdgePosition[]=new int[100];
		try {		
		//��������
			ReadXml read=new ReadXml(filename);
			int i=0,I,J;
			String[] VertexID=read.GetID(a);
			int Vertexcount=read.getLocationNum(a);		
			int Edgecount=read.getTransitionNum(a);		
		    List edgeBeanList =new ArrayList();
			List<NodeBean> list=new ArrayList<NodeBean>();//�����Ϣ
			List<EdgeBean> List=new ArrayList<EdgeBean>();//�ߵ���Ϣ
			int A[][]=new ReadXml(filename).find(a);
			/*for(int m=0;m<Vertexcount;m++)		
				for(int n=0;n<Vertexcount;n++)
					System.out.println(A[m][n]);*/
			int B[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
			int flag=0;//��ʾ��·��
			int Flag=0;//��ʾ�Լ����Լ��ı�
		while(i<Vertexcount)
		{
 			list.add(new NodeBean(VertexID[i]));//���node
 			i++;
		}
			for( I=0;I<Vertexcount;I++)			
			{	
				for(J=0;J<Vertexcount;J++)
					if(A[I][J]==1&&A[I][J]!=A[J][I]&&J!=I)//�ҵ����ڵı�,���Ҳ��ǶԽ����ϵ�edge
		           {      //���edge
						List.add(new EdgeBean(list.get(I),list.get(J),new Long(100)));//�ȼ�
		        	}
					else if(A[I][J]==1&&A[I][J]==A[J][I]&&J!=I&&B[I]!=1)
					{//���ڻ�·�ı���B[]��¼һ�� ��flag��¼һ�»�·�ߵ���Ŀ
						List.add(new EdgeBean(list.get(I),list.get(J),new Long(100)));//���
						B[I]=B[J]=1;
						flag++;
					}
					else if(A[I][J]==1&&I==J)//��node(���ڸ�node����node�ı�)
						Flag++;				       
			}				
			for(int K=0;K<Edgecount-flag-Flag;K++)
			{//��Ϊǰ�����Ⱥ�ӵ����Բ��ܹ����
				edgeBeanList.add(List.get(K));		
			}
			// �������ݣ�����ͼ
			gefNodeMap = new HashMap();
			graphNodeMap = new HashMap();
			edgeList = new ArrayList();
			directedGraph = new DirectedGraph();
			GraphModel model = new DefaultGraphModel();
			graph.setModel(model);
			Map attributes = new Hashtable();
			// Set Arrow
			Map edgeAttrib = new Hashtable();
			GraphConstants.setLineEnd(edgeAttrib, GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(edgeAttrib, true);
			graph.setJumpToDefaultPort(true);	
			if (edgeBeanList == null || edgeBeanList.size() == 0) {
				graph.repaint();
				return null;
			}
			Iterator edgeBeanIt = edgeBeanList.iterator();
			while (edgeBeanIt.hasNext()) {
				EdgeBean edgeBean = (EdgeBean) edgeBeanIt.next();
				NodeBean sourceAction = edgeBean.getsourceNodeBean();
				NodeBean targetAction = edgeBean.gettargetNodeBean();
				Long ageLong = edgeBean.getStatCount();
				String edgeString = "(" + ageLong +  ")";
				addEdge(sourceAction, targetAction, 100, edgeString);
			}
			// �Զ����� ������DirectedGraphLayout��������쳣��ͼ����������ͨ�ģ������NodeJoiningDirectedGraphLayout
			// ���߿��ԶԷ���ͨͼ���в���������㣬��Ч������ǰ�ߺã�������ѡǰ�ߣ���ǰ�߲����Դ���ʱ���ú���
			try{
				new DirectedGraphLayout().visit(directedGraph);
			}catch(Exception e1){
				new NodeJoiningDirectedGraphLayout().visit(directedGraph);
			}	
			int self_x=50;
			int self_y=50;
			int base_y=10;
			if(graphNodeMap!=null&&gefNodeMap!=null&&graphNodeMap.size()>gefNodeMap.size()){
				base_y=self_y+GraphProp.NODE_HEIGHT;
			}		
			// ��ͼ����ӽڵ�node
			Collection nodeCollection = graphNodeMap.values();
			if (nodeCollection != null) {
				Iterator nodeIterator = nodeCollection.iterator();
				if (nodeIterator != null) {
					while (nodeIterator.hasNext()) {
						DefaultGraphCell node = (DefaultGraphCell) nodeIterator.next();
						NodeBean userObject = (NodeBean) node.getUserObject();
						if (userObject == null) {
							continue;
						}
						Node gefNode = (Node) gefNodeMap.get(userObject);			
						if(gefNode==null){
							// ������Ϊ��һ���ڵ���һ��ָ������ıߵ�ʱ��������gefGraph�в�û�м��������ߣ�gefGraph���ܼ������ָ���Լ��ߵĲ��֣���
							// ���Ե�����Ҫ����ͼ�иýڵ�ֻ��һ��ָ������ıߵ�ʱ��������gefNodeMap�о��Ҳ�����Ӧ�ڵ���
							// ���ʱ�������ֹ������ýڵ�� X,Y ����
							gefNode=new Node();
							gefNode.x=self_x;
							gefNode.y=self_y-base_y;
							self_x+=(10+GraphProp.NODE_WIDTH);
						}
						for(int h=0;h<Vertexcount;h++)
						{
							if(list.get(h).equals(userObject))						
						{
							EdgePosition[2*h]=gefNode.x;
							EdgePosition[2*h+1]=gefNode.y;
						}							
						}					
						Map nodeAttrib = new Hashtable();
						GraphConstants.setBorderColor(nodeAttrib, Color.black);
						Rectangle2D Bounds = new Rectangle2D.Double(gefNode.x,gefNode.y+base_y, GraphProp.NODE_WIDTH,GraphProp.NODE_HEIGHT);
						GraphConstants.setBounds(nodeAttrib, Bounds);
						attributes.put(node, nodeAttrib);						
					}// while		
				}
			}
			for(int L=0;L<Vertexcount;L++)
						System.out.println(EdgePosition[L]);			
			// ��ͼ����ӱ�  
			if (edgeList == null) {
				//logger.error("edge list is null");
				return null ;
			}
			for (int i1 = 0; i1 < edgeList.size(); i1++) {
				UnionEdge unionEdge = (UnionEdge) edgeList.get(i1);
				if (unionEdge == null) {
					//logger.error("union edge is null");
					continue;
				}
				ConnectionSet cs = new ConnectionSet(unionEdge.getEdge(),unionEdge.getSourceNode().getChildAt(0), unionEdge.getTargetNode().getChildAt(0));
				Object[] cells = new Object[] 
						{ unionEdge.getEdge(),
						  unionEdge.getSourceNode(), 
						  unionEdge.getTargetNode() };
				attributes.put(unionEdge.getEdge(), edgeAttrib);
				model.insert(cells, attributes, cs, null, null);
			}
		//	graph.repaint();		
	} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return EdgePosition;
	}
	
	private void graphInit() {

		// Construct Model and Graph
		GraphModel model = new DefaultGraphModel();
	    graph = new JGraph(model);
		graph.setSelectionEnabled(true);
		
		// ��ʾapplet
		JScrollPane scroll=new JScrollPane(graph);
		this.getContentPane().add(scroll);
		
		this.setSize(new Dimension(800, 800));

	}


	/**
	 * @param source
	 * @param target
	 */
	private void addEdge(NodeBean source, NodeBean target, int weight,String edgeString) {

		if (source == null || target == null) {
			return;
		}
		if (gefNodeMap == null) {
			gefNodeMap = new HashMap();
		}
		if (graphNodeMap == null) {
			graphNodeMap = new HashMap();
		}
		if (edgeList == null) {
			edgeList = new ArrayList();
		}
		if (directedGraph == null) {
			directedGraph = new DirectedGraph();
		}

		// ����GEF�� node edge������������graph node��layout
		addEdgeGef(source, target,weight,edgeString);
		
		// ��������Ҫ�õ�graph�� node edge
		DefaultGraphCell sourceNode = null;
		DefaultGraphCell targetNode = null;
		sourceNode = (DefaultGraphCell) graphNodeMap.get(source);
		if (sourceNode == null) {
			sourceNode = new DefaultGraphCell(source);
			sourceNode.addPort();
			graphNodeMap.put(source, sourceNode);
		}
		targetNode = (DefaultGraphCell) graphNodeMap.get(target);
		if (targetNode == null) {
			targetNode = new DefaultGraphCell(target);
			targetNode.addPort();
			graphNodeMap.put(target, targetNode);
		}
		DefaultEdge edge = new DefaultEdge(edgeString);
		UnionEdge unionEdge = new UnionEdge();
		unionEdge.setEdge(edge);
		unionEdge.setSourceNode(sourceNode);
		unionEdge.setTargetNode(targetNode);
		edgeList.add(unionEdge);
	}
	/**
	 * @param source
	 * @param target
	 * @param weight
	 * @param edgeString
	 */
	private void addEdgeGef(NodeBean source, NodeBean target, int weight, String edgeString) {

		if(source.equals(target)){//�����ʼ�ĵ�==��ֹ�ĵ�
			return;
		}
		// ����GEF�� node edge������������graph node��layout
		Node gefSourceNode = null;
		Node gefTargetNode = null;
		gefSourceNode = (Node) gefNodeMap.get(source);
		if (gefSourceNode == null) {
			gefSourceNode = new Node();
			gefSourceNode.width = GraphProp.NODE_WIDTH;
			gefSourceNode.height = GraphProp.NODE_WIDTH;
			//gefSourceNode.setPadding(new Insets(GraphProp.NODE_TOP_PAD,GraphProp.NODE_LEFT_PAD, GraphProp.NODE_BOTTOM_PAD,GraphProp.NODE_RIGHT_PAD));
			directedGraph.nodes.add(gefSourceNode);
			gefNodeMap.put(source, gefSourceNode);
		}
		
		gefTargetNode = (Node) gefNodeMap.get(target);
		if (gefTargetNode == null) {
			gefTargetNode = new Node();
			gefTargetNode.width = GraphProp.NODE_WIDTH;
			gefTargetNode.height = GraphProp.NODE_WIDTH;
			//gefTargetNode.setPadding(new Insets(GraphProp.NODE_TOP_PAD,GraphProp.NODE_LEFT_PAD, GraphProp.NODE_BOTTOM_PAD,GraphProp.NODE_RIGHT_PAD));
			directedGraph.nodes.add(gefTargetNode);
			gefNodeMap.put(target, gefTargetNode);
		}
		
		Edge gefEdge1=null;
		try{
			gefEdge1 = new Edge(gefSourceNode, gefTargetNode);
			gefEdge1.weight = weight;
			directedGraph.edges.add(gefEdge1);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	

}