package com.horstmann.violet.product.diagram.sequence;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Dimension2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.xml.transform.Templates;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.node.IResizableNode;
import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;
import com.horstmann.violet.product.diagram.abstracts.property.Condition;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentPart;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentType;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.workspace.Workspace;
import com.horstmann.violet.workspace.WorkspacePanel;
import com.horstmann.violet.workspace.editorpart.EditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.behavior.EditSelectedBehavior;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class CombinedFragment extends RectangularNode 
{	   
   public CombinedFragment()
   {	
	   type= new FragmentType().ALT;
	   fragmentType = "";    
       conditions=new Condition();
       fragmentParts=new ArrayList<FragmentPart>();
       name = "";
   }
@Override
public Condition getCondition() {
	return conditions;
}
public void setCondition(Condition conditions) {
	this.conditions = conditions;	
}

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

@Override
public int getZ() {
	// TODO Auto-generated method stub
	return INFINITE_Z_LEVEL;
}
@Override
   public Rectangle2D getBounds()//�ı��ⲿ�߿�
   {	
       Point2D currentLocation = this.getLocation();
       double x;
       double y;
	   x = currentLocation.getX();
       y =  currentLocation.getY();      
       Rectangle2D currentBounds = new Rectangle2D.Double(x, y, getWidth(), getHeight());
      // Rectangle2D snapperBounds = getGraph().getGridSticker().snap(currentBounds);
       return currentBounds;
   }
 
public double getWidth() {
	return width;
}
public void setWidth(double witdh) {
	this.width = witdh;
}
public double getHeight() {
	return height;
}
public void setHeight(double height) {
	this.height = height;
}
@Override
   public Shape getShape()
   {
       Rectangle2D bounds = getBounds();
       GeneralPath path = new GeneralPath();
       path.moveTo((float) bounds.getX(), (float) bounds.getY()); 
       path.lineTo((float) (bounds.getMaxX()), (float) bounds.getY());
       path.lineTo((float) bounds.getMaxX(), (float) bounds.getY()) ;
       path.lineTo((float) bounds.getMaxX(), (float) bounds.getMaxY());
       path.lineTo((float) bounds.getX(), (float) bounds.getMaxY());
       
       path.closePath();
       return path;
   }
   //==================================================================================
   public FragmentType getType() 
   {
       return type;
   }
public void setType(FragmentType newValue)
   {  
       type= newValue;   
   } 
public String getFragmentType() {

	return fragmentType;
}
public void setFragmentType(String fragmentType) {
	this.fragmentType = fragmentType;
}
//����TimeNode�ڵ�
   @Override
   public void draw(Graphics2D g2)    
   {  	  	 	   
	   SetFragmentPartBorderLine();	
       g2.setColor(Color.black);//�Ѹ�       
       for(FragmentPart fragmentpart: getFragmentParts())
       {
    	 g2.setStroke(DOTTED_STROKE);
    	 g2.draw(fragmentpart.getBorderline());    	   
       } 
       g2.setStroke(Defelt_STROKE);
       for(FragmentPart fragmentpart : getFragmentParts())
       {
    	 int index=fragmentParts.indexOf(fragmentpart);
    	 double startX=fragmentpart.getBorderline().getX1();
    	 double otherstartY=fragmentpart.getBorderline().getY1()+Default_OtherConditionTextDistance;
    	 double firststartY=fragmentpart.getBorderline().getY1()+Default_FirstConditionTextDistance;
    	 if(index==0)
    	 { 
    	     g2.drawString(fragmentpart.getConditionText(),(int) startX, (int)firststartY);
    	 }
    	 else
    	 { 
    		 g2.drawString(fragmentpart.getConditionText(),(int) startX, (int)otherstartY);
    	 } 
    	 if(index+1<getFragmentParts().size()){
    	 FragmentPart nextfragmentpart=getFragmentParts().get(index+1);
		 double boundsHeight=nextfragmentpart.getBorderline().getY1()-fragmentpart.getBorderline().getY1();
		 Rectangle2D bounds=new Rectangle2D.Double(fragmentpart.getBorderline().getX1(),fragmentpart.getBorderline().getY1(),
				 getWidth(),boundsHeight);
		 fragmentpart.setBounds(bounds);
    	 }
		 else
		 {
		  fragmentpart.setBounds(new Rectangle2D.Double(fragmentpart.getBorderline().getX1(),fragmentpart.getBorderline().getY1(),
		  getWidth(),getBounds().getMaxY()-fragmentpart.getBorderline().getY1()));	 
		 } 
       }     
       Rectangle2D bounds = getBounds();
       Shape fold = getShape(bounds);
       Rectangle2D foldBounds = fold.getBounds2D();
       g2.setColor(DEFAULT_COLOR);
       g2.fill(foldBounds);
       g2.setColor(Color.BLACK);
       g2.draw(fold);
       type.drawType(g2, bounds);
       String SplitProperties[]=type.toString().split("\\.");
	   setFragmentType(SplitProperties[8]);
       Shape path = getShape();
       g2.draw(path);
       conditions.setParentNode(this);
   }
   private Shape getShape(Rectangle2D bounds)
   {
	   GeneralPath fold = new GeneralPath();
       fold.moveTo((float) (bounds.getX()+5*d), (float) bounds.getY());  //����������ĳ��
       fold.lineTo((float) bounds.getX()+5*d, (float) bounds.getY()+(2*d));
       fold.moveTo((float) bounds.getX()+5*d, (float) bounds.getY()+(2*d));
       fold.lineTo((float) bounds.getX()+4*d, (float) bounds.getY()+(2.5*d));
       fold.moveTo((float) bounds.getX() +4*d, (float) bounds.getY()+(2.5*d));
       fold.lineTo((float) bounds.getX(), (float) bounds.getY()+(2.5*d));
       fold.closePath();
       return fold;
   }
   
   public Condition getConditions() {
	return conditions;
}
public void setConditions(Condition conditions) {
	this.conditions = conditions; 
}
public List<FragmentPart> getFragmentParts() {
	return fragmentParts;
}
public void setFragmentParts(List<FragmentPart> fragmentParts) {
	this.fragmentParts = fragmentParts;
}
//public void SetFragmentPartConditionText()
//{	
//	   List<String> ConditionTexts=conditions.getConditionTexts();//��ȡ���໤����	 
//	   fragmentParts.clear();
//	   for(String conditiontext : ConditionTexts)
//	   {
//		 int conditionIndex=ConditionTexts.indexOf(conditiontext);
//		    //���ݼ໤�����������½��ֿ�
//		fragmentParts.add(new FragmentPart());
//		fragmentParts.get(conditionIndex).setConditionText(conditiontext);	   	   		 	   
//	   }	   
//}
public void SetFragmentPartBorderLine()
{      
	  for(FragmentPart fragmentpart : getFragmentParts())
	  { 
		  double locationX=getLocation().getX();
		  double locationY=getLocation().getY();
		  int fragmentpartIndex=fragmentParts.indexOf(fragmentpart);
		  double distanceY=fragmentpartIndex*Default_Distance;
		  Point2D lineStartPoint=new Point2D.Double(locationX,locationY+distanceY);
		  Point2D lineEndPoint=new Point2D.Double(locationX+getWidth(),locationY+distanceY);
		  Line2D borderline=new Line2D.Double(lineStartPoint,lineEndPoint);	
		  if(!fragmentpart.isBorderlinehaschanged())
		  {
		  fragmentpart.setBorderline(borderline); 
		  }
	  } 		   		  			  
	  if(fragmentParts.size()*Default_Distance>=getHeight())
	  {
	  if(fragmentParts.size()>2)
	      setHeight(fragmentParts.size()*Default_Distance);
	  }   
}  
@Override
   public CombinedFragment clone()
   {
       CombinedFragment cloned = (CombinedFragment) super.clone(); 
       cloned.conditions=(Condition)conditions.clone();  
       return cloned;
   }		
  
   private FragmentType type;
   private String fragmentType;
   private Condition conditions;//�໤���� 
 
   private List<FragmentPart> fragmentParts;//�ֿ�
   
   private String name;
  
   private static Color DEFAULT_COLOR = new Color(255, 228, 181); // very pale pink
   private static int d =10;
   private double width=200,height=100;
   private static double Default_Distance=50;
   private  static Stroke Defelt_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
   private  static Stroke DOTTED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0.0f, new float[]
		    {
		            3.0f,
		            3.0f 
		    }, 0.0f);
   private static double Default_FirstConditionTextDistance=40;
   private static double Default_OtherConditionTextDistance=20;
   private static int INFINITE_Z_LEVEL = 10002;
}
