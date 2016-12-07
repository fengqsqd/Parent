package com.horstmann.violet.application.gui.util.wujun.TDVerification;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GB extends GridBagConstraints  
{  
   //��ʼ�����Ͻ�λ��  
   public GB(int gridx, int gridy)  
   {  
      this.gridx = gridx;  
      this.gridy = gridy;  
   }  
  
   //��ʼ�����Ͻ�λ�ú���ռ����������  
   public GB(int gridx, int gridy, int gridwidth, int gridheight)  
   {  
      this.gridx = gridx;  
      this.gridy = gridy;  
      this.gridwidth = gridwidth;  
      this.gridheight = gridheight;  
   }  
  
   //���뷽ʽ  
   public GB setAnchor(int anchor)  
   {  
      this.anchor = anchor;  
      return this;  
   }  
  
   //�Ƿ����켰���췽��  
   public GB setFill(int fill)  
   {  
      this.fill = fill;  
      return this;  
   }  
  
   //x��y�����ϵ�����  
   public GB setWeight(double weightx, double weighty)  
   {  
      this.weightx = weightx;  
      this.weighty = weighty;  
      return this;  
   }  
  
   //�ⲿ���  
   public GB setInsets(int distance)  
   {  
      this.insets = new Insets(distance, distance, distance, distance);  
      return this;  
   }  
  
   //�����  
   public GB setInsets(int top, int left, int bottom, int right)  
   {  
      this.insets = new Insets(top, left, bottom, right);  
      return this;  
   }  
  
   //�����  
   public GB setIpad(int ipadx, int ipady)  
   {  
      this.ipadx = ipadx;  
      this.ipady = ipady;  
      return this;  
   }  
}  
