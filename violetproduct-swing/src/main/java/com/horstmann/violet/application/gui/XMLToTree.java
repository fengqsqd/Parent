package com.horstmann.violet.application.gui;

import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
//xmlת��Ϊ��
public class XMLToTree {
    static Document doc;
    static Element root;
    static DefaultMutableTreeNode rootTree ;
    static DefaultMutableTreeNode root1;
    static DefaultMutableTreeNode root2;
    static DefaultTreeCellRenderer demoRenderer;
    private static JScrollPane jScrollPane;
    public static JScrollPane  getTree(String path) {
    	JPanel jp=new JPanel();
        try {
        	//���SAXReader����
            SAXReader saxReader=new SAXReader();
            //���domcument�Ķ���
            doc=saxReader.read(path);     
            //��ø��ڵ�
            root = doc.getRootElement();//ʹ��dom4j�ṩ��API���XML�ĸ��ڵ�
            //����Jtree����ģ�͵ĸ��ڵ�
            root1 =new DefaultMutableTreeNode(getAttriString(root));
            getTreenodeNames(root,root1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //����</TimingDiagramGraph>�Ĵ���
        String ss1=getAttriString(root).split(" ")[0];
        String ss2=ss1.replaceAll("<", "</ ")+">";
        root2=new DefaultMutableTreeNode(ss2);
        rootTree =new DefaultMutableTreeNode("message");
        rootTree.add(root1);
        rootTree.add(root2);
        //������(������һ������)
        JTree jTree=new JTree(rootTree);
        //������Ĭ�ϲ���ʱ��ʾ��ͼƬ�ĳ�������Ҫ��ͼƬ
        demoRenderer = new DefaultTreeCellRenderer();
        demoRenderer.setClosedIcon(new ImageIcon("resources/icons/22x22/collapsed.png"));
        demoRenderer.setOpenIcon(new ImageIcon("resources/icons/22x22/expanded.png"));
        demoRenderer.setLeafIcon(null);
        demoRenderer.setClosedIcon(null);
        demoRenderer.setOpenIcon(null);
        jTree.setCellRenderer(demoRenderer);
        jTree.putClientProperty("JTree.lineStyle","None"); 
        jp.setLayout(new GridLayout());
        jp.add(jTree);
        
        jScrollPane = new JScrollPane(jp);
       return jScrollPane;
    }
    
    
    //��õ�ǰElement ������ǰ��node���ӽڵ�
    public static void getTreenodeNames(Element e,DefaultMutableTreeNode node) {
        //��õ�ǰelement����Ԫ�صĵ�����
        Iterator iter = e.elementIterator();
        while (iter.hasNext()) {
        	//�����Ԫ��
            Element childEle = (Element) iter.next();
            //ȡ���ӽڵ�ļ���
            List<Element> listEle=childEle.elements();
            //������ں���Ԫ��
            DefaultMutableTreeNode child=null;
//            if(listEle.size()!=0){
            	
            	//ȡ����Ԫ�ص���Ϣ�����ɵ�ǰ��node�µ��ӽڵ�
               child = new DefaultMutableTreeNode(getAttriString(childEle));
            	//�����ɵĽڵ���ӵ���ģ����
            	node.add(child);
            	
            	//����һ�����Ӧ��</>�Ľڵ�  �����ֽڵ㵥������
            	DefaultMutableTreeNode child1=null;
            	if(listEle.size()!=0){
            		String s1=getAttriString(childEle).split(" ")[0];
            		String s2=s1.replace("<", "/ ");
            		String s3="<"+s2+" >";
            		child1 = new DefaultMutableTreeNode(s3);
            		node.add(child1);
            	}
            //������ǰ��Ԫ��û����Ԫ�أ�������һ��Ԫ��
            if (childEle.nodeCount()==0) {
                continue;
            }
            //�����ǰ��Ԫ������Ԫ�أ��ݹ���Ԫ��
            else{
            	//ֻ�ݹ��һ��Ԫ�ص���Ԫ��
                getTreenodeNames(childEle,child);//����
            }

        }
    }
    //������е�����---ֵ�Ľ��, ���� <startPoint class="Point2D.Double" id="8" x="150.0" y="412.0"/>  
    //���û������
    public static String getAttriString(Element element){
    	List<Attribute> list=element.attributes();
    	String s1=null;
    	String s2=null;
    	//������Ϊ�յ�ʱ��  <startPointTime>13</startPointTime>
    	if(list.size()==0){
    		String sName="< "+element.getName()+" >"+element.getText()+"</ "+element.getName()+" >";
    		return sName;
    	}
    	//ȡ����ǰElement�ĺ���element
    	List<Element> listEle=element.elements();
    	//�������Ϊ�գ�����û���ı�
    	if(listEle.size()==0 ){
    		String sName1="<"+element.getName();
        	for(Attribute attri:list){
        		s1=attri.getName();
        		s2=attri.getValue();
        		sName1+=" "+s1+"="+s2;
    	}
        	sName1+=" />";
        	return sName1;
    	}
    	
    	
    	String s="<"+element.getName();
    	for(Attribute attri:list){
    		s1=attri.getName();
    		s2=attri.getValue();
    		s+=" "+s1+"="+s2;
    	}
    	s+=">";
    	return s;
    } 

//    public static void main(String[] args) {
//
//        JFrame frame = new JFrame("test");
//        frame.setVisible(true);
//        frame.setBounds(200, 200, 400, 300);
//        JPanel jp=XMLToTree.getTree("C:\\Users\\Admin\\Desktop\\111.timing.violet.xml");
//        frame.add(jp);
//        frame.pack();
//    }

}