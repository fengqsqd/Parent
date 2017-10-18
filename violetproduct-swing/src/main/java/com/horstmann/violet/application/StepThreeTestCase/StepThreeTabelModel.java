package com.horstmann.violet.application.StepThreeTestCase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.table.AbstractTableModel;

public class StepThreeTabelModel extends AbstractTableModel
{         /**         *  @author С��         */ 
    private  Vector TableData;//������ű�����ݵ����Ա�
    private Vector TableTitle;//���� �б���

	private List<String> paramterNameList;
    private List<String> paramterValueList;
    
    private String testSequence;
    private String excitation;
    private String testCase;
    
    private List<String> constraintNameString;
    private List<Double> pros;
    private List<Integer> numbers;
    private List<Double> actualPercentsDoubles;
    private DecimalFormat  df = new DecimalFormat();
	private String pattern = "#0.000";
    //ע�⹹�캯���ǵ�һ��ִ�еģ����ڳ�ʼ�� TableData��TableTitle
    public StepThreeTabelModel(List<String> paramterNameList,List<String> paramterValueList)
    {
           this.paramterNameList = paramterNameList;
           this.paramterValueList = paramterValueList;
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
//           TableTitle.add("Ǩ�ƹ�ϵ");
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ���б���,��3��
           TableTitle.add("Լ������");
           TableTitle.add("��⼯��");
           initData(paramterNameList,paramterValueList);
    }
    public StepThreeTabelModel(List<String> constraintNameString,List<Double> actualPercentsDoubles,List<Double> pros,List<Integer> numbers )
    {
           this.constraintNameString = constraintNameString;
           this.pros = pros;
           this.numbers =numbers;
           this.actualPercentsDoubles = actualPercentsDoubles;
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
//           TableTitle.add("Ǩ�ƹ�ϵ");
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ���б���,��3��
           //��ܲ��ֽ���
           TableTitle.addElement("���");
           TableTitle.addElement("��������()·��");
           TableTitle.addElement("·������");
           TableTitle.addElement("��ȡ����");
           
//           TableTitle.add("�������м�");
//           TableTitle.add("����������Ϣ");
           initData(constraintNameString, actualPercentsDoubles,pros, numbers);
    }
    public StepThreeTabelModel(String testSequence,int flag)
    {
           this.testSequence = testSequence;
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
//           TableTitle.add("Ǩ�ƹ�ϵ");
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ���б���,��3��
           TableTitle.add("");
           TableTitle.add("");
           if(flag == 1)
           {
        	   initabstract(testSequence);
           }
           else {
			inittest(testSequence);
		}
           
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
           return false;//super.isCellEditable(rowIndex, columnIndex);
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
    private void initData(List<String> paramterNameList,List<String> paramterValueList)
    {
    	int size = TableData.size();
    	for(int i = 0;i < paramterNameList.size();i++)
    	{
    		String[] line4 = new String[2];
   		 line4[0] = paramterNameList.get(i);
   		 line4[1] = paramterValueList.get(i);
   		 TableData.add(line4);
   		 line4 = null;
    	}
    	fireTableRowsInserted(size, size);
    }
    private void initData(List<String> constraintNameString,List<Double> actualPercentsDoubles,List<Double> pros,List<Integer> number)
    {
    	df.applyPattern(pattern);
    	int size = TableData.size();
    	for(int i = 0;i < constraintNameString.size();i++)
    	{
    		String[] line4 = new String[4];
   		 line4[0] = String.valueOf(i);
   		 line4[1] = constraintNameString.get(i);
   		 line4[2] = String.valueOf(pros.get(i));
   		 line4[3] = String.valueOf(number.get(i));
//   		 line4[1] = "·������Ϊ(�ɿ��Բ����������ɱ���"+df.format(actualPercentsDoubles.get(i))+"):  "+df.format(pros.get(i))+" "+"������������"+String.valueOf(number.get(i))+"��";
   		 TableData.add(line4);
   		 line4 = null;
    	}
    	fireTableRowsInserted(size, size);
    }
    private void initabstract(String testSequence)
    {
    	 int size = TableData.size();
    	 String[] line1 = new String[2];
   		 line1[0] = "��������";
   		 line1[1] = testSequence;
   		 TableData.add(line1);

    	
    	fireTableRowsInserted(size, size);
    }
    private void inittest(String testSequence)
    {
    	 int size = TableData.size();
   	     String[] line1 = new String[2];
  		 line1[0] = "��������";
  		 line1[1] = testSequence;
  		 TableData.add(line1);
    }
    
    public Vector getTableTitle() {
		return TableTitle;
	}
    
}

