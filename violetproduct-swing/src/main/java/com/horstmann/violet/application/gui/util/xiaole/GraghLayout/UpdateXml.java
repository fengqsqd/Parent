package com.horstmann.violet.application.gui.util.xiaole.GraghLayout;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

import com.horstmann.violet.application.gui.util.xiaole.GraghLayout.TestGraph;

import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
public class UpdateXml {
	Document dom;
	Element root;
	SAXReader reader = new SAXReader();	
	String filename;
	public UpdateXml(String file) throws DocumentException
	{
		this.filename=file;
		dom=reader.read(new File(file));
	}
    public void Update(int a) throws Exception{
		
		int i = 0, j=0,n = 0, m = 0, p = 0, xxxx, yyyy, YYYY,k = 0;
		String XXX[] = new String[30];
		String YYY[] = new String[30];
		String xxx[] = new String[30];
		String yyy[] = new String[30];
		int[] A = new int[30];
	
	    root=dom.getRootElement();
		List<Element> LIST=root.elements("template");//LIST��ʾXML�м���Template
		List<Element> list=LIST.get(a).elements("transition");
 		List<Element> List=LIST.get(a).elements("location");//List�����ŵ�ļ���
		Element Template=List.get(0);//Templateָ���
        Element template1=list.get(0);
 		Iterator it = List.iterator();
 		Iterator IT = list.iterator();
			// ͨ�õĵ�����iterator,��list����it��	
		    while(it.hasNext()) {// �ж��Ƿ�����һ��Ԫ��
					it.next();
					i++;
				}
		    while(IT.hasNext())
		    {
		    	IT.next();
		    	j++;//j������ÿ��Template�м�����
		    }
         A=new TestGraph(filename).init(a);
    
			for(int I=0;I<2*i;I++)
		
			A[I]*=1.5;//�������ǰ�ÿ�����X��Y���궼������ͬ�ı��������ڲ��ı�����ͼ��ֵ�ǰ���£��Ŵ�����ͼ�����¼��ٱ�ǩ���ص�����Ϊ��ǩ�г���
		    //i�����Ŷ��ٸ���		
			while (n < i) {
				Template  = List.get(n);
				String xx = String.valueOf((int) A[m]);
				String yy = String.valueOf((int) A[m + 1] );
				String XX = String.valueOf((int) A[m]-30);
				String YY = String.valueOf((int) A[m + 1]-30 );
				Template.attribute("y").setText(xx); 
				Template.attribute("x").setText(yy);
				m += 2;
				n++;
				Template.element("name").attribute("y").setText(XX);
				Template.element("name").attribute("x").setText(YY);
				System.out.println("�ڵ������óɹ�������");
            }                                    	
			//Template �����ŵ�List��template1�����ű�list				
			n = 0;
			while (p < j && n < i) {
				template1 = list.get(p);
				Template = List.get(n);
				if (template1.element("source").attribute("ref").getValue()
						.equals(Template.attribute("id").getValue())) {	
					XXX[p] = Template.attribute("x").getValue();
					YYY[p] = Template.attribute("y").getValue();
					n = 0;
					p++;
				} else
					n++;
			}
			n = 0;
			p = 0;
			while (p < j && n < i) {
				template1 = list.get(p);
				Template = List.get(n);
				if (template1.element("target").attribute("ref").getValue()
						.equals(Template.attribute("id").getValue())) {
					xxx[p] = Template.attribute("x").getValue();
					yyy[p] = Template.attribute("y").getValue();
					n = 0;
					p++;
				} else
					n++;
			}
			while (k < j) {
				template1 = list.get(k);	
				if(template1.element("source").attribute("ref").getValue().equals
						(template1.element("target").attribute("ref").getValue()))//����е��Լ��ı�.Ĭ�ϳ�ʼͼ����nail��ǩ������Ͳ����Լ����Լ�
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
				
				System.out.println("���Լ��бߵĵ�ı�ǩ���óɹ�������");
				k++;
				}	   
				else 
				{
				xxxx = (Integer.valueOf(xxx[k]) + Integer.valueOf(XXX[k])) / 2-20;
				yyyy = (Integer.valueOf(yyy[k]) + Integer.valueOf(YYY[k])) / 2-20;//��ǩ�г���
				String xxxxx = String.valueOf(xxxx);
				String yyyyy = String.valueOf(yyyy);
				System.out.println("��ǩ���óɹ�������");
				template1.element("label").attribute("y").setText(yyyyy);//���������ֿ�ָ���������Ϊ����û�б�ǩ
				template1.element("label").attribute("x").setText(xxxxx);
				k++;
				}
			}
			n = 0;//n�����ű�
			p = 0;//P�����ŵ�
			k = 0;//k�����ű�
			m = 0;//m����������ĳ�ʼ
			int B[][]=new ReadXml(filename).find(a);
			int temp1=0,temp2=0;
			String x1=null,y1=null,x2=null,y2=null;
			for(int M=0;M<i;M++)
			for(int N=0;N<i;N++)
			{
				if(M!=N&&B[M][N]==B[N][M]&&B[M][N]==1)//�ж��������ڵĵ��Ƿ��л�·
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
				    }//��ȡ��·�ĵ�һ������transition��ǩ�е�λ�ã�����������
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
				}//��ȡ��·�ĵڶ�������transition��ǩ�е�λ�ã����������꣨��������ʵ���Ͼ��ǵ�һ���ߵ��յ㣩
				template1=list.get(temp1);
					int X1=Integer.valueOf(x1);
					int X2=Integer.valueOf(x2);
					int Y1=Integer.valueOf(y1)+20;
					int Y2=Integer.valueOf(y2)-20;
					int X=(X1+X2)/2;
					String XX=String.valueOf(X);
					String YY1=String.valueOf(Y1);
					String YY2=String.valueOf(Y2);				
					template1.addElement("nail").addAttribute("y", YY1);//�����Զ����nail��ǩ
					template1.element("nail").addAttribute("x", XX);					
					template1.element("label").attribute("y").setText(YY1);
					template1.element("label").attribute("x").setText(XX);
					template1=list.get(temp2);			
				    template1.addElement("nail").addAttribute("y", YY2);//�����Զ����nail��ǩ
				    template1.element("nail").addAttribute("x", XX);
				    template1.element("label").attribute("y").setText(YY2);
					template1.element("label").attribute("x").setText(XX);
					System.out.println("��·��ǩ���óɹ�������");
			    }	
				
				B[M][N]=B[N][M]=0;
			}	
		    XMLWriter writer = new XMLWriter(new FileOutputStream("stabilize_run.xml"),
		    OutputFormat.createPrettyPrint());	
		    writer.write(dom);
		    writer.close();		
		  }//whileѭ��      
       }

	

	