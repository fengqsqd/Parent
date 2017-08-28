package com.horstmann.violet.application.StepTwoModelExpand;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class GradientProgressBarUI extends BasicProgressBarUI{

        @Override  
        protected void paintDeterminate(Graphics g, JComponent c) {  
            Graphics2D graphics2d = (Graphics2D) g;  
              
            Insets b = progressBar.getInsets();   
            // JProgressBar�ı߽�����  
              
            int width = progressBar.getWidth();  
            int height = progressBar.getHeight();  
            int barRectWidth = width - (b.right + b.left);  
            int barRectHeight = height - (b.top + b.bottom);  
            int arcSize = height / 2 - 1;  
              
            int amountFull = getAmountFull(b, barRectWidth, barRectHeight);  
            //����ɵĽ���  
              
            graphics2d.setColor(Color.WHITE);  
            graphics2d.fillRoundRect(0, 0, width - 1, height, arcSize,  
                    arcSize);  
            //����JProgressBar�ı���  
              
            //��GradientPaint����ʵ�ֽ���ɫ��ʾ����  
            //�����˿�ʼ�����ֹ�㣬�����ú������������ɫ��ϵͳ���Զ��������������ý���ɫ�����  
            Point2D start = new Point2D.Float(0, 0);  
            Point2D end = new Point2D.Float(amountFull, barRectHeight);  
            //�������õ���ֹ���ǵ�ǰ�Ѿ���ɵĽ��ȵ��Ǹ���  
  
            GradientPaint gradientPaint = new GradientPaint(  
                    start, new Color(5,177,36), end, new Color(0,211,40));  
              
            graphics2d.setPaint(gradientPaint);  
      
            graphics2d.fillRoundRect(b.left, b.top, amountFull,  
                    barRectHeight, 0, 0);//����ʵ�ֵ���Բ�ǵ�Ч����arcSize����0����ʵ�־���Ч��  
        }  

}
