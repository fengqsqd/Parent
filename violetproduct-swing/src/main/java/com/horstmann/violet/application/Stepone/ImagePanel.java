package com.horstmann.violet.application.Stepone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	ImageIcon icon;
	Image img;
	int widthgap;
	int heightgap;
	public ImagePanel(String soruce,int widthgap,int heightgap)
	{
		super();
		icon = new ImageIcon(soruce);
		img=icon.getImage();
		this.widthgap = widthgap;
		this.heightgap = heightgap;
	}
	 public void paintComponent(Graphics g) {  
	        super.paintComponent(g);  
	        //����������Ϊ�˱���ͼƬ���Ը��洰�����е�����С�������Լ����óɹ̶���С  
	        g.drawImage(img, 0, 0,this.getWidth() - widthgap, this.getHeight() - heightgap, this);  
	    }  
    @Override
    public void setBackground(Color bg) {
    	// TODO Auto-generated method stub
    	super.setBackground(bg);
    }
}
