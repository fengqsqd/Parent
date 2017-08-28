package com.horstmann.violet.application.StepThreeTestCase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.horstmann.violet.application.StepTwoModelExpand.TabeListener;

public class ExpandTableMode extends AbstractTableModel 
{         /**         *  @author С��         */ 
    private  Vector TableData;//������ű�����ݵ����Ա�
    private Vector TableTitle;//���� �б���

    private List<String> stateNames;
    private List<String> stateLabels;
    
    private List<String> parameterName;
    private List<String> parameterDomainType;
    private List<String> parameterDomain;
	private DecimalFormat  df   = new DecimalFormat("######0.00");   
    //ע�⹹�캯���ǵ�һ��ִ�еģ����ڳ�ʼ�� TableData��TableTitle
    public ExpandTableMode(List<String> stateNames,List<String> stateLabels)
    {

           this.stateNames = stateNames;
           this.stateLabels = stateLabels;
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
           TableTitle.add("״̬����");
           TableTitle.add("״̬��Ϣ");
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ���б���,��3��

           initData(stateNames,stateLabels);
    }
    public ExpandTableMode(List<String> parameterName,List<String> parameterDomain,List<String> parameterDomainType)
    {

           this.stateNames = stateNames;
           this.stateLabels = stateLabels;
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
           TableTitle.add("��������");
           TableTitle.add("��������");
           TableTitle.add("��������");
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ���б���,��3��

           initData(parameterName,parameterDomain,parameterDomainType);
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
    private void initData(List<String> stateNames,List<String> stateLabels)
    {
    	 int size = TableData.size();
    		 for(int j = 0;j < stateNames.size();j++)
    		 {   
    			 String[] line4 = new String[2];
    	   		 line4[0] = stateNames.get(j);
    	   		 line4[1] = stateLabels.get(j);
    	   		 TableData.add(line4);
    	   		 line4 = null;
    		 }
    	 fireTableRowsInserted(size, size);
    }
    private void initData(List<String> parameterName,List<String> parameterDomain,List<String> parameterDomainType)
    {
    	 int size = TableData.size();
    		 for(int j = 0;j < parameterName.size();j++)
    		 {   
    			 String[] line4 = new String[3];
    	   		 line4[0] = parameterName.get(j);
    	   		 line4[1] = parameterDomain.get(j);
    	   		 line4[2] = parameterDomainType.get(j);
    	   		 TableData.add(line4);
    	   		 line4 = null;
    		 }
    	 fireTableRowsInserted(size, size);
    }
    public Vector getTableTitle() {
		return TableTitle;
	}  
}

