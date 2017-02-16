package com.horstmann.violet.framework.propertyeditor.customeditor;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

public class UsecaseTableMode extends AbstractTableModel
{         /**         *  @author С��         */ 
    private static Vector TableData;//������ű�����ݵ����Ա�
    private Vector TableTitle;//���� �б���

    //ע�⹹�캯���ǵ�һ��ִ�еģ����ڳ�ʼ�� TableData��TableTitle
    public UsecaseTableMode()
    {
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ�� �б���,��3��
           TableTitle.add("Լ������");
           TableTitle.add("Լ������");
           TableTitle.add("Լ������");
     
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
           //�������ʽ����ÿ����Ԫ��ı༭���Ե�
           //�������AbstractTableModel�Ѿ�ʵ�֣�Ĭ�ϵ��� ������༭״̬
           //������������Ϊ����༭״̬
           return true;//super.isCellEditable(rowIndex, columnIndex);
    }
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
    public void add()
    {
    	 int size = TableData.size();
    	 String[] Line4 = {"","",""};
    	 TableData.add(Line4);
    	 fireTableRowsInserted(size, size);
    }
    public void delete(int selectRow)
    {
    	int size = TableData.size();
    	TableData.remove(selectRow);
    	fireTableRowsInserted(size, size);
    }
}

