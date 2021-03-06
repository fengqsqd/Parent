package com.horstmann.violet.application.gui.util.tanchao.markovlayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import com.horstmann.violet.application.gui.MainFrame;
import org.dom4j.io.XMLWriter;
import java.io.File;
import java.io.FileOutputStream;

public class UpdateXml {
	String route;
	Document dom;
	Element root;
	SAXReader reader = new SAXReader();	
	String filename;
	String newFileName;
	public UpdateXml(String route,String file,String newFileName,MainFrame mainFrame) throws DocumentException
	{
		this.route = route;
		this.filename=file;
		this.newFileName = newFileName;
		File file2 = new File(file);
		dom=reader.read(file2);    
	}
    public void update() throws Exception{
		
		int i = 0, j=0,n = 0, m = 0, p = 0, xxxx, yyyy, YYYY,k = 0;
		String XXX[] = new String[500];
		String YYY[] = new String[500];
		String xxx[] = new String[500];
		String yyy[] = new String[500];
		int[] A = new int[30];
	
	    root=dom.getRootElement();
	    Element nodes=root.element("nodes");
		Element edges=root.element("edges");
		List<Element> list=edges.elements("MarkovTransitionEdge");
		Element MarkovStartNode=nodes.element("MarkovStartNode");//处理开始的节点
		List<Element> nodeList=nodes.elements("MarkovNode");//其他的节点情况
//		List.add(MarkovStartNode);
		List<Element> List=new ArrayList<Element>();
		List.add(MarkovStartNode);
		for(int count=0;count<nodeList.size();count++){
			List.add(nodeList.get(count));
		}
		Element midElement;
		Element midElement1;
//		List<Element> LIST=root.elements("template");//LIST表示XML有几个Template
//		List<Element> list=LIST.get(a).elements("transition");
// 		List<Element> List=LIST.get(a).elements("location");//List代表着点的集合
//		Element Template=List.get(0);//Template指向点
//      Element template1=list.get(0);
 		Iterator it = List.iterator();
 		Iterator IT = list.iterator();
			// 通用的迭代器iterator,把list放入it中	
		    while(it.hasNext()) {// 判断是否有下一个元素
					it.next();
					i++;
				}
		    while(IT.hasNext())
		    {
		    	IT.next();
		    	j++;//j代表着每个Template有几条边
		    }
		    
         A=new TestGraph(filename).init();
//         System.out.println("A"+A.length);
         for (int I = 0; I < 2 * i; I+=2) {
    		 A[I] += 200;//这里面是把每个点的X坐标下移100像素
    		 A[I+1]+=100;
    		 }
    
		 for (int I = 0; I < 2 * i; I++) {
		 A[I] *= 3;//这里面是把每个点的X，Y坐标都乘以相同的倍数，以在不改变有向图格局的前提下，放大有向图，以致减少标签的重叠，因为标签有长度
		 }
			while (n < i) {
//				Template  = List.get(n);
				midElement=List.get(n);
//				System.out.println(midElement.getClass().toString()+"88888");
				String xx = String.valueOf((int) A[m]);
				String yy = String.valueOf((int) A[m + 1] );
				String XX = String.valueOf((int) A[m]-30);
				String YY = String.valueOf((int) A[m + 1]-30 );
//				Template.attribute("y").setText(xx); 
//				Template.attribute("x").setText(yy);
				midElement.element("location").attribute("y").setText(xx); 
				midElement.element("location").attribute("x").setText(yy);
				m += 2;
				n++;
//				Template.element("name").attribute("y").setText(XX);
//				Template.element("name").attribute("x").setText(YY);
//				midElement.element("name").attribute("y").setText(xx);
//				midElement.element("name").attribute("x").setText(yy);
//				System.out.println("节点名设置成功！！！");
            }                                    	
			//Template 代表着点List，template1代表着边list				
			n = 0;
			while (p < j && n < i) {
//				template1 = list.get(p);
//				Template = List.get(n);
//				if (template1.element("source").attribute("ref").getValue()
//						.equals(Template.attribute("id").getValue())) {	
//					XXX[p] = Template.attribute("x").getValue();
//					YYY[p] = Template.attribute("y").getValue();
				midElement1 = list.get(p);//表示midElement1表示边
				midElement = List.get(n);//midElement表示边表示点
				if (midElement1.element("start").attribute("reference").getValue()
						.equals(midElement.attribute("id").getValue())) {	
					XXX[p] = midElement.element("location").attribute("x").getValue();
					YYY[p] = midElement.element("location").attribute("y").getValue();
					n = 0;
					p++;
				} else
					n++;
			}
			n = 0;
			p = 0;
			while (p < j && n < i) {
				midElement1 = list.get(p);
				midElement = List.get(n);
				if (midElement1.element("end").attribute("reference").getValue()
						.equals(midElement.attribute("id").getValue())) {
					xxx[p] = midElement.element("location").attribute("x").getValue();
					yyy[p] = midElement.element("location").attribute("y").getValue();
					n = 0;
					p++;
				} else
					n++;
			}
			while (k < j) {
				midElement1 = list.get(k);	
				if(midElement1.element("start").attribute("reference").getValue().equals
						(midElement1.element("end").attribute("reference").getValue()))//如果有到自己的边.默认初始图中有nail标签，否则就不是自己到自己
				{
					xxxx=Integer.valueOf(xxx[k])+30;
					yyyy=Integer.valueOf(xxx[k])-30;
					YYYY=Integer.valueOf(yyy[k])-30;
					String xxxxxx =String.valueOf(xxxx);
					String yyyyyy =String.valueOf(yyyy);
//					String xxxxxxx=String.valueOf(YYYY);	
//				    Element nail1=template1.addElement("nail");
//				    Element nail2=template1.addElement("nail");
//				nail1.addAttribute("x").setText(xxxxxx);
//				nail1.addAttribute("y").setText(xxxxxxx);
//				nail2.addAttribute("x").setText(yyyyyy);
//				nail2.addAttribute("y").setText(xxxxxxx);
				
//				System.out.println("到自己有边的点的标签设置成功！！！");
				k++;
				}	   
				else 
				{
				xxxx = (Integer.valueOf(xxx[k]) + Integer.valueOf(XXX[k])) / 2-20;
				yyyy = (Integer.valueOf(yyy[k]) + Integer.valueOf(YYY[k])) / 2-20;//标签有长度
				String xxxxx = String.valueOf(xxxx);
				String yyyyy = String.valueOf(yyyy);
//				System.out.println("标签设置成功！！！"+xxxxx+"  "+yyyyy);
				if(xxxxx!=null||yyyyy!=null){
					
				//midElement1.element("location").attribute("y").setText(yyyyy);//这段如果出现空指针错误，是因为边上没有标签
				//midElement1.element("location").attribute("x").setText(xxxxx);
				}
				k++;
				}
			}
			n = 0;//n控制着边
			p = 0;//P控制着点
			k = 0;//k控制着边
			m = 0;//m控制着数组的初始
			int B[][]=new ReadXml(filename).find(filename);
			int temp1=0,temp2=0;
			String x1=null,y1=null,x2=null,y2=null;
			for(int M=0;M<i;M++)
			for(int N=0;N<i;N++)
			{
				if(M!=N&&B[M][N]==B[N][M]&&B[M][N]==1)//判断两个相邻的点是否有回路
				{
					for(int c=0;c<j;c++)
					{
						String ID=List.get(M).attribute("id").getValue();
						String id=List.get(N).attribute("id").getValue();
						String Ref=list.get(c).element("source").attribute("ref").getValue();
						String ref=list.get(c).element("target").attribute("ref").getValue();
					if(ID.equals(Ref)&&id.equals(ref))
		                 {
						 temp1=c;		                 
					         x1= List.get(M).attribute("x").getValue();
					          y1= List.get(M).attribute("y").getValue();
		                 }
				    }//获取回路的第一条边在transition标签中的位置，和起点的坐标
				for(int c=0;c<j;c++)
				{
					String ID=List.get(M).attribute("id").getValue();
					String id=List.get(N).attribute("id").getValue();
					String Ref=list.get(c).element("target").attribute("ref").getValue();
					String ref=list.get(c).element("source").attribute("ref").getValue();
				if(ID.equals(Ref)&id.equals(ref))
	                 {
					temp2=c;              
				         x2= List.get(N).attribute("x").getValue();
				          y2= List.get(N).attribute("y").getValue();
	                 }
				}//获取回路的第二条边在transition标签中的位置，和起点的坐标（这里的起点实际上就是第一条边的终点）
				midElement1=list.get(temp1);
					int X1=Integer.valueOf(x1);
					int X2=Integer.valueOf(x2);
					int Y1=Integer.valueOf(y1)+20;
					int Y2=Integer.valueOf(y2)-20;
					int X=(X1+X2)/2;
					String XX=String.valueOf(X);
					String YY1=String.valueOf(Y1);
					String YY2=String.valueOf(Y2);				
					midElement1.addElement("nail").addAttribute("y", YY1);//这里自动添加nail标签
					midElement1.element("nail").addAttribute("x", XX);					
					midElement1.element("label").attribute("y").setText(YY1);
					midElement1.element("label").attribute("x").setText(XX);
					midElement1=list.get(temp2);			
					midElement1.addElement("nail").addAttribute("y", YY2);//这里自动添加nail标签
					midElement1.element("nail").addAttribute("x", XX);
					midElement1.element("label").attribute("y").setText(YY2);
					midElement1.element("label").attribute("x").setText(XX);
			    }	
				
				B[M][N]=B[N][M]=0;
			}	
		    XMLWriter writer = new XMLWriter(new FileOutputStream(route+newFileName),//("stabilize_run.xml"),
		    OutputFormat.createPrettyPrint());	
		    writer.write(dom);
		    writer.close();		
		  }//while循环      
       }

	

	