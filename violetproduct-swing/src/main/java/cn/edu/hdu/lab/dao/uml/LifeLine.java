package cn.edu.hdu.lab.dao.uml;
import java.util.ArrayList;
public class LifeLine {
	/* 
	 * �����ߵ�id �� name���Լ����������λ���㣻
	 * 
	 */
	private String id;
	private String name;
	private ArrayList<Node> nodes=new ArrayList<Node>();
	
	public LifeLine() {}
	
	public LifeLine(String id) 
	{
		this.id = id;
	}
	
	public LifeLine(String id, String name) 
	{
		set(id, name);
	}
	public void set(String id, String name) 
	{
		this.id = id;
		this.name = name;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
	public String toString() 
	{
		return "LifeLine: "+ id + "\t" + name;
	}
	
	//�������ַ����д���������
//	public void print_nodes()
//	{
//		for (Node n : this.nodes)
//			n.print_node();
//		
//	}
	
	public void print_LifeLine() {
		System.out.println("Lifeline: name=" +this.name +"\tID="+this.id+"\t��������λ��û�����"); 
		//print_nodes();
	}
	
}
