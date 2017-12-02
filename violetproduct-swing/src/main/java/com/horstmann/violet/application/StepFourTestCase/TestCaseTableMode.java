package com.horstmann.violet.application.StepFourTestCase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import com.horstmann.violet.application.StepTwoModelExpand.TabeListener;

public class TestCaseTableMode extends AbstractTableModel 
{         /**         *  @author С��         */ 
    private  Vector TableData;//������ű�����ݵ����Ա�
    private Vector TableTitle;//���� �б���
    
    private List<Integer> processID;
    private List<String> processName;
    private List<String> processParam;
    private List<String> processStatus;
    private List<Boolean> processExec;  
    
    private String TestCaseID;
    private String State;
    private String ExeState;
    
    private int success;
    private int fail;
    private int total;
    
    private int failType;
    private int failTotal;
    
    //ע�⹹�캯���ǵ�һ��ִ�еģ����ڳ�ʼ�� TableData��TableTitle
    public TestCaseTableMode(List<Integer> processID,List<String> processName,
    		List<String> processParam,List<String> processStatus,List<Boolean> processExec)
    {

           this.processID = processID;
           this.processName = processName;
           this.processParam = processParam;
           this.processStatus = processStatus;
           this.processExec = processExec;
           //��new һ��
           TableData = new Vector();
           TableTitle= new Vector();
            
           TableTitle.add("����ID");
           TableTitle.add("��������");
           TableTitle.add("��������");
           TableTitle.add("����״̬");
           TableTitle.add("����ִ�����");
           //�������Ǽ��赱ǰ�ı���� 3x3��
           //�ȳ�ʼ���б���,��3��

           initData(processID,processName,processParam,processStatus,processExec);
    }
    public TestCaseTableMode(String TestCaseID,String State,String ExeState,int flag)
    {
    	this.TestCaseID = TestCaseID;     
 	    this.State = State;
 	    this.ExeState = ExeState;
 	    
 	    TableData = new Vector();
        TableTitle= new Vector();
        
        if(flag == 1)
        {
          TableTitle.add("");
          TableTitle.add(ExeState);
          TableTitle.add(State);
        }
        else {
        	TableTitle.add(TestCaseID);
            TableTitle.add("���Ժ�ʱ:" + ExeState + "ms");
            TableTitle.add(State);
		}
        
    }
    
    public TestCaseTableMode(int success,int fail,int total)
    {
    	 this.success = success;     
  	     this.fail = fail;
  	     this.total = total;
  	     
  	     TableData = new Vector();
         TableTitle= new Vector();
         
         TableTitle.add("");
         TableTitle.add("");
         TableTitle.add("");
         TableTitle.add("");
         
//         TableData.add("�ϼ�");
//         TableData.add(String.valueOf(success));
//         TableData.add(String.valueOf(fail));
//         TableData.add(String.valueOf(total));
         countData(success, fail, total);
    }
    public TestCaseTableMode(int failType,int failTotal)
    {
    	 this.failType = failType;     
  	     this.failTotal = failTotal;
  	     
  	     TableData = new Vector();
         TableTitle= new Vector();
         
         TableTitle.add("");
         TableTitle.add("");
         TableTitle.add("");
         TableTitle.add("");
         
//         TableData.add("�ϼ�");
//         TableData.add(String.valueOf(success));
//         TableData.add(String.valueOf(fail));
//         TableData.add(String.valueOf(total));
         countFailData(failType, failTotal);
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
    private void initData(List<Integer> processID,List<String> processName,
    		List<String> processParam,List<String> processStatus,List<Boolean> processExec)
    {
    	 int size = TableData.size();
    		 for(int j = 0;j < processID.size();j++)
    		 {   
    			 String[] line4 = new String[5];
    	   		 line4[0] = String.valueOf(processID.get(j));
    	   		 line4[1] = processName.get(j);
    	   		 line4[2] = processParam.get(j);
    	   		 line4[3] = processStatus.get(j);
    	   		 line4[4] = String.valueOf(processExec.get(j));
    	   		 TableData.add(line4);
    	   		 line4 = null;
    		 }
    	 fireTableRowsInserted(size, size);
    }
    
    public void countData(int success,int fail,int total)
    {
    	 int size = TableData.size();  
		 String[] line4 = new String[4];
	   	 line4[0] = "�ϼ�";
	   	 line4[1] = String.valueOf(success);
	   	 line4[2] = String.valueOf(fail);
	     line4[3] = String.valueOf(total);
	   	 TableData.add(line4);
	   	 
	   	 String[] line5 = new String[4];
	   	 line5[0] = "�ٷֱ�";
	   	 line5[1] = countPercentage(success, total);
	   	 line5[2] = countPercentage(fail, total);
	     line5[3] = "100%";

	   	 TableData.add(line5);
	 fireTableRowsInserted(size, size);
    }
    public void countFailData(int failType,int failTotal)
    {
    	 int size = TableData.size();  
		 String[] line4 = new String[4];
	   	 line4[0] = "�ϼ�";
	   	 line4[1] = String.valueOf(failTotal);
	   	 line4[2] = String.valueOf(failType);
	     line4[3] = String.valueOf(failTotal);
	   	 TableData.add(line4);
	   	 
	   	 String[] line5 = new String[4];
	   	 line5[0] = "�ٷֱ�";
	   	 line5[1] = "100%";
	   	 if(failTotal == 0)
	   	 {
	   		line5[2] = "0%";
	   	 }
	   	 else {
	   		line5[2] = countPercentage(failType, failTotal);
		}
	     line5[3] = "100%";

	   	 TableData.add(line5);
	 fireTableRowsInserted(size, size);
    }
    public Vector getTableTitle() {
		return TableTitle;
	}  
    
    public String countPercentage(int num1,int num2)
    {
		// ����һ����ֵ��ʽ������

		NumberFormat numberFormat = NumberFormat.getInstance();

		// ���þ�ȷ��С�����2λ

		numberFormat.setMaximumFractionDigits(2);

		String result = numberFormat.format((float) num1 / (float) num2 * 100);

		return  result + "%";
    }
}

