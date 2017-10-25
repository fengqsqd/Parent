package com.horstmann.violet.framework.file.persistence;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.ManagedBean;
import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.framework.plugin.PluginRegistry;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.HorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;
import com.horstmann.violet.product.diagram.abstracts.property.BentStyle;
import com.horstmann.violet.product.diagram.abstracts.property.LineStyle;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

@ManagedBean(registeredManually=true)
public class XStreamBasedPersistenceService implements IFilePersistenceService {
	
	@InjectedBean
	private PluginRegistry pluginRegistry;
	
	public XStreamBasedPersistenceService() {
		BeanInjector.getInjector().inject(this);
	}

	@Override
	public IGraph read(InputStream in) throws IOException {
		InputStreamReader reader = new InputStreamReader(in);
		XStream xStream = new XStream(new DomDriver("UTF-8"));//XStream��һ��xml���л�����
		//�á�UTF-8������ķ�ʽ��дxml�ļ�
		xStream = getConfiguredXStream(xStream);
		//xStream.alias("horizontalchild",HorizontalChild.class);//�ڷ����л���ʱ��Ҳ��Ҫ�������ĳɶ�Ӧ����
		Object fromXML = xStream.fromXML(reader);//�����л�reader.Ҳ�������������
		IGraph graph = (IGraph) fromXML;//����������ݻ�ԭ��һ��graph
		Collection<INode> allNodes = graph.getAllNodes();
		for (INode aNode : allNodes) {
			aNode.setGraph(graph);
		}//���»�ԭgraphͼ
		reader.close();
		return graph;
	}

	@Override
	public void write(IGraph graph, OutputStream out) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(out);
			
//			XStream xStream = new XStream(new D��omDriver("UTF-8"));
			XStream xStream = new XStream(new XppDriver(new NoNameCoder()) {    
	            @Override  
	            public HierarchicalStreamWriter createWriter(Writer out) {  
	                return new PrettyPrintWriter(out) {  
	                    boolean cdata = true;  
	                    @Override  
	                    @SuppressWarnings("rawtypes")  
	                    public void startNode(String name, Class clazz) {  
	                        super.startNode(name, clazz);  
	                    }  
	  
	                    @Override  
	                    public String encodeNode(String name) {  
	                        return name;  
	                    }  
	  
	  
	                    @Override         
	                    protected void writeText(QuickWriter writer, String text) {  
	                        if (cdata) {  
	                            writer.write("<![CDATA[");  
	                            writer.write(text);  
	                            writer.write("]]>");  
	                        } else {  
	                            writer.write(text);  
	                        }  
	                    }  
	                };  
	            }  
	        });  
			
			xStream = getConfiguredXStream(xStream);
			xStream.toXML(graph, writer);			
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}	
	private XStream getConfiguredXStream(XStream xStream) {
		xStream.autodetectAnnotations(true);
		xStream.setMode(XStream.ID_REFERENCES);//id������
		xStream.useAttributeFor(Point2D.Double.class, "x");
		xStream.useAttributeFor(Point2D.Double.class, "y");
    	xStream.alias("Point2D.Double", Point2D.Double.class);
        //xStream.processAnnotations(HorizontalChild.class);       
		//xStream.addImmutableType(ArrowHead.class);
        //xStream.addImmutableType(LineStyle.class);
        //xStream.addImmutableType(BentStyle.class);
		List<IDiagramPlugin> diagramPlugins = this.pluginRegistry.getDiagramPlugins();
		for (IDiagramPlugin aPlugin : diagramPlugins) {
			Class<? extends IGraph> graphClass = aPlugin.getGraphClass();
			xStream.alias(graphClass.getSimpleName(), graphClass);
			try {
				IGraph aDummyGraph = graphClass.newInstance();
				List<IEdge> edgePrototypes = aDummyGraph.getEdgePrototypes();
				List<INode> nodePrototypes = aDummyGraph.getNodePrototypes();
				for (IEdge anEdgePrototype : edgePrototypes) {
					Class<? extends IEdge> edgeClass = anEdgePrototype.getClass();
					xStream.alias(edgeClass.getSimpleName(), anEdgePrototype.getClass());
					
				}
				for (INode aNodePrototype : nodePrototypes) {
					Class<? extends INode> nodeClass = aNodePrototype.getClass();
					xStream.alias(nodeClass.getSimpleName(), aNodePrototype.getClass());
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return xStream;
	}

}
