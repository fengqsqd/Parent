package com.horstmann.violet.application.gui.util.chenzuo.Bean;
import java.io.Serializable;
import java.util.List;

/***
 * 
 * @author tiffy
 */
public class TestCase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7785205898142381116L;
	//ID
	String  testCaseID;
	//list of process
	List<myProcess> processList;
	//state of testcase
	String  state;
	//result of testcase
	TestCaseResult  result;
	//detail of testcase, String of socket received
	String  detail;
	//ʱ��Լ������ʽ
	List<String> limit;
	
	public TestCase() {
	}

	public TestCase(String testCaseID, String state, TestCaseResult result, String detail) {
		this.testCaseID = testCaseID;
		this.state = state;
		this.result = result;
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTestCaseID() {
		return testCaseID;
	}

	public void setTestCaseID(String testCaseID) {
		this.testCaseID = testCaseID;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



	public TestCaseResult getResult() {
		return result;
	}

	public void setResult(TestCaseResult result) {
		this.result = result;
	}

	public List<myProcess> getProcessList() {
		return processList;
	}

	public void setProcessList(List<myProcess> list) {
		this.processList = list;
	}
	
	public List<String> getLimit() {
		return limit;
	}

	public void setLimit(List<String> limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		String tmp = "TestCase [testCaseID=" + testCaseID + ", processList=\n";
		for (myProcess m : processList) {
			tmp = tmp + "\tmyProcess [processID=" + m.processID + ", processName=" + m.processName + ", processParam=" + m.processParam
					+ ", processStatus=" + m.processStatus + ", processExec=" + m.processExec + "]\n";
		}
		tmp = tmp + ", state=" + state + ", result="+ result + ", detail=" + detail + "]";
		return tmp;
	}
	/***
	 *  show format
	 * @return
	 */
	public String showTestCase(){
		String tmp="��������ID:" + testCaseID + "\n  -->��������: [ ";
		for (myProcess m : processList) {
			tmp = tmp + "\n\t" +(m.isProcessExec() ? "��" : "x")
					+" ����ID : " +m.processID 
					+ " �������� : " + m.processName 
					+ "( �������� : " + ((m.processParam == "NULL")?"��":m.getProcessParam())
					+ " ����״̬ :" + ((m.processStatus == "NULL")?"��":m.getProcessStatus())
					+")";
		}
		tmp = tmp + " ]\n  -->����ִ��״̬: [ " + state + " ]\n  -->���״̬: [ "+ result.getResultDetail()+" ]";
		return tmp;
	}
}