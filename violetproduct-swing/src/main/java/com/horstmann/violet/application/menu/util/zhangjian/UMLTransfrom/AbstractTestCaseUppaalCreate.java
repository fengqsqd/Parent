package com.horstmann.violet.application.menu.util.zhangjian.UMLTransfrom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1.GetAutomatic;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.AddType;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Automatic;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.State;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Test_Id;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractState;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTestCase;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTransition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;


public class AbstractTestCaseUppaalCreate {
	private List<AbstractState> abStateList =new ArrayList<AbstractState>();
	private List<AbstractTransition> abTransList =new ArrayList<AbstractTransition>();
	private List<AbstractTestCase> abstractTestCaseList =new ArrayList<AbstractTestCase>();
	String name="name";

	
	private AbstractTestCase testCase=null;
	public void createXML(String fileName,String targetPath){
		//���wqq����ص���Ϣ
		Automatic automatic=GetAutomatic.getAutomatic(fileName);
		Automatic am=AddType.addType(automatic);
		ArrayList<State> stateList=am.getStateSet();//���State�ļ���
		ArrayList<Transition> tranList = am.getTransitionSet();//���transition�ļ���
		//int num =DataBaseUtil.getObjNum("select count(*) from abstract_state");
		//int num=0;
		//���state�Ĳ������ݿ�
		int num1;
		List<AbstractState> list1=DataBaseUtil.getAllState("select * from abstract_state order by sid desc");
		if(list1==null||list1.isEmpty()){
			num1=0;
		}
		else{
			num1=list1.get(0).getSid();
		}

		for(State s :stateList){
			//��wqq����ص���Ϣ--->ת��Ϊzhangjian����ص���Ϣ(state)
			AbstractState abState =new AbstractState();
			abState.setSid(s.getId());//��ѯ���ݿ�����״̬�ڵ�ĸ���
			abState.setSname(s.getName());
			abState.setPosition(s.getPosition());
			abState.setType(s.getType());
			abState.setInvariantDBM(s.getInvariantDBM().toString());
			abStateList.add(abState);
			
		}
		//���transition�������ݿ�����
		int num2;
		List<AbstractTransition> list2=DataBaseUtil.getAllTranstion("select * from abstract_transition order by tid desc");
		if(list2==null||list2.isEmpty()){
			num2=0;
		}
		else{
			num2=list2.get(0).getTid();
		}
		
		System.out.println(num2);
		for(Transition t :tranList){
			//��wqq����ص���Ϣ--->ת��Ϊzhangjian����ص���Ϣ(transition)
			AbstractTransition abTrans =new AbstractTransition();
			abTrans.setTid(t.getId());
			abTrans.setSourceID(this.getStateIdByName(abStateList, t.getSource())+"");
			
			abTrans.setSource(t.getSource());
			abTrans.setTargetID(this.getStateIdByName(abStateList, t.getTarget())+"");
			abTrans.setTarget(t.getTarget());
			
			abTrans.setType(t.getTypes().toString());
			StringBuilder sb =new StringBuilder();
			for(int i=0;i<t.getResetClockSet().size();i++){
				sb.append(t.getResetClockSet().get(i));
				if(i!=t.getResetClockSet().size()-1){
					sb.append(";");
				}
			}
			abTrans.setResetClockSet(sb.toString());
			abTrans.setConstraintDBM(t.getConstraintDBM().toString());
			//System.out.println(t.getTypes()+"**"+t.getSource()+"**"+t.getTarget()+"**"+t.getResetClockSet()+"**"+t.getConstraintDBM());
			abTransList.add(abTrans);
		}
		int num3 ;
		List<AbstractTestCase> list3=DataBaseUtil.getAllAbstractTestCase("select * from abstract_testcase order by id desc");
		if(list1==null||list1.isEmpty()){
			num3=0;
		}
		else{
			num3=list3.get(0).getId();
		}                                                                                    
		List<String>list=new Test_Id().exchangeTestCase(fileName);//��xmlת��Ϊ����Ҫ�ĳ�����Ե�����wqq->zhangjian
		Iterator<String> iterator=list.iterator();
		while(iterator.hasNext()){
			num3++;
			testCase=new AbstractTestCase();
			testCase.setId(num3);
			testCase.setName(name+num3);
			testCase.setTestRouter((String)iterator.next());
		    abstractTestCaseList.add(testCase);
		}
		//��zhangjian��abstractTestCase�洢�����ݿ���
		DataBaseUtil.insert2State(abStateList, "insert into abstract_state(sid,sname,invariantDBM,position,stype) values(?,?,?,?,?)",list1);
		DataBaseUtil.insert2Transition(abTransList, "insert into abstract_transition(tid,ttype,tname,targetid,target,sourceid,source,constraintDBM,ResetClockSet) "
				+ "values(?,?,?,?,?,?,?,?,?)",list2);
		DataBaseUtil.insert2AbstractTestCase(abstractTestCaseList, "insert into abstract_testcase(id,name,TestRoute) values(?,?,?)",list3);
		//����CreateAbstractUppaalXML�࣬������������������Զ�����XML�ļ�
		CreateAbstractUppaalXML c =new CreateAbstractUppaalXML(abStateList, abTransList);
		try {
			 c.create(targetPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public int getStateIdByName(List<AbstractState> list ,String name){
		int num=0;
		for(AbstractState s :list){
			if(name.equals(s.getSname())){
				num=s.getSid();
			}
		}
		return num;
	}
}
