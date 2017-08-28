package com.horstmann.violet.application.StepTwoCaseExpand;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class CaseTableMode extends AbstractTableModel 
{         /**         *  @author С��         */ 
    private  Vector TableData;//������ű�����ݵ����Ա�
    private Vector TableTitle;//���� �б���

	private List<String> titleList;
    
	private DecimalFormat  df   = new DecimalFormat("######0.00");   
    //ע�⹹�캯���ǵ�һ��ִ�еģ����ڳ�ʼ�� TableData��TableTitle
    public CaseTableMode(List<String> titleList)
    {

           this.titleList = titleList;
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
//           TableTitle.add("Ǩ�ƹ�ϵ");
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ���б���,��3��
           for(int i = 0;i < titleList.size();i++)
           {
        	   TableTitle.add(titleList.get(i));  
           }
           initData(titleList);
    }
    public CaseTableMode(List<String> titleList,List<Double> dataList,int flag)
    {
           this.titleList = titleList;
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
//           TableTitle.add("Ǩ�ƹ�ϵ");
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ���б���,��3��
           if(flag == 1){
        	   TableTitle.add("����Ǩ������");
               TableTitle.add("����Ǩ�Ƹ���");
           }
           else {
        	   TableTitle.add("��������");
               TableTitle.add("��������");
		}
           initData(titleList,dataList);
    }

    @Override
    public int getRowCount()
    {
           //�����Ǹ�֪���Ӧ���ж����У����Ƿ���TableData�Ϲҵ�String�������
           return TableData.size();
    }

    @Override
    public int getColumnCount()
    {
           //��֪�������ñ�������Ĵ�С,����������TableData.size()�У�TableTitle.size()����
           return TableTitle.size();
    }
    //��ȡ���� ����Ҫ û��������������޷�����
    public String getColumnName(int column){
        return (String) TableTitle.get(column);
}
  
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
           //��ȡ�˱��Ĵ�С����Ȼ��Ҫ��ȡ���ݣ���������ֱ�ӷ��ض�Ӧ������
           //С�� ���Ǵ� 0��ʼ�ģ�С���±�Խ�� ������
           //����֮ǰ�ǽ� String[]�ҵ������Ա��ϣ�����Ҫ�Ȼ�ȡ��String[]
           //
           //��ȡÿһ�ж�Ӧ��String[]����
           String LineTemp[] = (String[])this.TableData.get(rowIndex);
           //��ȡ���� Ӧ������
           return LineTemp[columnIndex];
            
           //��������ǽ� ���Ա�Vector�ҵ���Vector��Ҫע��ÿ�����ǻ�ȡ���� һ�����Ա�
           //����
           //return ((Vector)TableData.get(rowIndex)).get(columnIndex);
    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex)
//    {
//           //�������ʽ����ÿ����Ԫ��ı༭���Ե�
//           //�������AbstractTableModel�Ѿ�ʵ�֣�Ĭ�ϵ��� ������༭״̬
//           //������������Ϊ����༭״̬
//           return true;//super.isCellEditable(rowIndex, columnIndex);
//    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
    	
           //����Ԫ������ݷ����ı��ʱ����øú������赥Ԫ�������
           //������һ�£������Ƿ���TableData �еģ�˵�����޸����ݾ����޸ĵ�
           //TableData�е����ݣ��������ǽ����ڴ˴���TableData�Ķ�Ӧ�����޸ļ��� 
    	
    		((String[])this.TableData.get(rowIndex))[columnIndex]=(String)aValue; 
            super.setValueAt(aValue, rowIndex, columnIndex);
        	   
           //
           //��ʵ����super�ķ����ǵ�����fireTableCellUpdated()ֻ��Ӧ������
           //��Ӧ��Ԫ�������
           //fireTableCellUpdated(rowIndex, columnIndex);
    }
    private void initData(List<String> titleList)
    {
    	 int size = TableData.size();
    	 for(int i = 1;i < titleList.size();i++)
    	 {
    		 String[] line4 = new String[titleList.size()];
    		 for(int j = 0;j < titleList.size();j++)
    		 {   
    			 if(j == 0)
    			 {
    				 System.out.println("==========: " + titleList.get(i));
    				 line4[j] = titleList.get(i);
    			 }
    			 else {
    				 line4[j] = " ";
				} 
    		 }
    		 TableData.add(line4);
    		 line4 = null;
    	 }
    	 fireTableRowsInserted(size, size);
    }
    private void initData(List<String> titleList,List<Double> dataList)
    {
    	int size = TableData.size();
    	for(int i = 0;i < titleList.size();i++)
    	{
    	 String[] line4 = new String[2];
   		 line4[0] = titleList.get(i);
   		 line4[1] = String.valueOf(dataList.get(i));
   		 TableData.add(line4);
   		 line4 = null;
    	}
    	fireTableRowsInserted(size, size);
    }
    
    public Vector getTableTitle() {
		return TableTitle;
	}  
}

