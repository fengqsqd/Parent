package com.horstmann.violet.workspace.sidebar.edgecoloretools;

import java.awt.Color;
//����һ���ߵ���ɫ����
public class EdgeColorChoice {
   private Color edgeColor; 
   
   public EdgeColorChoice(Color edgeColor){
	   this.edgeColor=edgeColor;
   }

public Color getEdgeColor() {
	return edgeColor;
}

public void setEdgeColor(Color edgeColor) {
	this.edgeColor = edgeColor;
}
   
}
