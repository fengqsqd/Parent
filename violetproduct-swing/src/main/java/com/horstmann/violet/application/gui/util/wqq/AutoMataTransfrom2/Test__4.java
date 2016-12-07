package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2;
//���ĸ����ӣ�����·��
import java.util.ArrayList;

public class Test__4 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xml="throttle_zero_flagForXStream.xml";
		Automatic auto=GetAutomatic.getAutomatic(xml);//���ԭʼ��ʱ���Զ���
		Automatic automatic=AddType.addType(auto);
		/*//ArrayList<State> new_stateSet=Minimization__1.minimization(automatic);
		Automatic new_automatic=IPR__1.iPR(automatic);//��ò�ֺ��ʱ���Զ���
		Automatic aTDRTAutomatic=ATDTR__1.aTDRT(new_automatic,automatic);//���ȥ������ʱ��Ǩ�ƺ��ʱ���Զ���
		//Automatic DFStree=StateCoverage__1.DFSTree(aTDRTAutomatic);
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(aTDRTAutomatic);//�������״̬���ǵĳ����������
		ArrayList<ArrayList<String>> all_inequalitys=Get_inequality__1.get_AllInequalitys(testCase);//ÿ���������������һ������ʽ��
		*/
		ArrayList<Automatic> testCase=StateCoverage__1.testCase(automatic);//�������״̬���ǵĳ����������
		/*System.out.println("ʱ���Զ�������:"+automatic.getName());
		System.out.println("ʱ���Զ���ʱ�Ӽ��ϣ�");*/
		/*for(String c:automatic.getClockSet()){
			System.out.println(c);
		}
		State iniState=automatic.getInitState();
		System.out.println("��ʼ״̬���֣�"+iniState.getName());
		System.out.println(iniState.getPosition());
		System.out.println(iniState.isFinalState());
		DBM_element[][] DBM=iniState.getInvariantDBM();
		for(int i=0;i<automatic.getClockSet().size()+1;i++){
			for(int j=0;j<automatic.getClockSet().size()+1;j++){
				DBM_element cons=DBM[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());					
			}
		}*/
		/*System.out.println("״̬������"+automatic.getStateSet().size());
		int k=0;
		for(State state:automatic.getStateSet()){
			System.out.println("��"+k+"��״̬");
			k++;
			DBM_element[][] dbm=state.getInvariantDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println("״̬����: "+state.getName());
			System.out.println("״̬λ��: "+state.getPosition());
			System.out.println("�Ƿ�Ϊ��ֹ״̬ : "+state.isFinalState());
			System.out.println("Type�����Ƿ�Ϊ��ʼ��"+state.getType());
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			DBM_element[][] adddbm=state.getAddClockRelationDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=adddbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println("--------------------");
		}*/
		
		/*System.out.println("Ǩ�Ƹ���"+automatic.getTransitionSet().size());
		int p=0;
		for(Transition tran:automatic.getTransitionSet()){
			System.out.println("��"+p+"��Ǩ��");
			p++;
			if(tran.getConstraintDBM()!=null){
				DBM_element[][] dbm=tran.getConstraintDBM();
				for(int i=0;i<automatic.getClockSet().size()+1;i++){
					for(int j=0;j<automatic.getClockSet().size()+1;j++){
						DBM_element cons=dbm[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());					
					}
				}
			}
			else System.out.println("ʱ��Լ��Ϊ��");
			
			System.out.println("Դ:"+tran.getSource());
			System.out.println("Ŀ�ģ�"+tran.getTarget());
			
			
			if(tran.getEventSet()==null){
				System.out.println("�¼�Ϊ��");
			}
			else if(tran.getEventSet().size()==0){
				System.out.println("�¼�Ϊ���գ�����û������");
			}
			else{
				ArrayList<String> events=tran.getEventSet();
				for(String e:events){
					System.out.println("�¼���"+e);
				}
			}
			
			if(tran.getResetClockSet()==null){
				System.out.println("����ʱ��Ϊ��");
			}
			else if(tran.getResetClockSet().size()==0){
				System.out.println("����ʱ��Ϊ���գ�����û������");
			}
			else{
				ArrayList<String> reset=tran.getResetClockSet();
				for(String r:reset){
					System.out.println("���õ�ʱ�ӣ�"+r);
				}
			}
			
			if(tran.getTypeIds()==null){
				System.out.println("typeIDΪ��");
			}
			else if(tran.getTypeIds().size()==0){
				System.out.println("typeIDΪ���գ�����û������");
			}
			else{
				ArrayList<String> typeid=tran.getTypeIds();
				for(String i:typeid){
					System.out.println("typeid:"+i);
				}
			}
			
			if(tran.getTypes()==null){
				System.out.println("typesΪ��");
			}
			else if(tran.getTypes().size()==0){
				System.out.println("typesΪ���գ�����û������");
			}
			else{
				ArrayList<String> type=tran.getTypes();
				for(String t:type){
					System.out.println("types:"+t);
				}
			}
			
			System.out.println("********************");
		}*/
		
		
		
		
		
		System.out.println("����������и�����"+testCase.size());
		for(Automatic a:testCase){
			
			System.out.println("������������:"+a.getName());
			/*//System.out.println("ʱ���Զ���ʱ�Ӽ��ϣ�");
			for(String c:a.getClockSet()){
				System.out.println(c);
			}
			State iniState=a.getInitState();
			System.out.println("��ʼ״̬���֣�"+iniState.getName());
			System.out.println(iniState.getPosition());
			System.out.println(iniState.isFinalState());
			DBM_element[][] DBM=iniState.getInvariantDBM();
			for(int i=0;i<a.getClockSet().size()+1;i++){
				for(int j=0;j<a.getClockSet().size()+1;j++){
					DBM_element cons=DBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println("-----------------");*/
			
			/*System.out.println("״̬������"+a.getStateSet().size());
			int k=0;
			for(State state:a.getStateSet()){
				System.out.println("��"+k+"��״̬");
				k++;
				DBM_element[][] dbm=state.getInvariantDBM();
				for(int i=0;i<a.getClockSet().size()+1;i++){
					for(int j=0;j<a.getClockSet().size()+1;j++){
						DBM_element cons=dbm[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());					
					}
				}
				System.out.println("״̬����: "+state.getName());
				System.out.println("״̬λ��: "+state.getPosition());
				System.out.println("�Ƿ�Ϊ��ֹ״̬ : "+state.isFinalState());
				DBM_element[][] adddbm=state.getAddClockRelationDBM();
				for(int i=0;i<a.getClockSet().size()+1;i++){
					for(int j=0;j<a.getClockSet().size()+1;j++){
						DBM_element cons=adddbm[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());					
					}
				}
				System.out.println("------");
			}
			System.out.println("*****************");*/
			
			/*System.out.println("Ǩ�Ƹ���"+a.getTransitionSet().size());
			int p=0;
			for(Transition tran:a.getTransitionSet()){
				System.out.println("��"+p+"��Ǩ��");
				p++;
				if(tran.getConstraintDBM()!=null){
					DBM_element[][] dbm=tran.getConstraintDBM();
					for(int i=0;i<a.getClockSet().size()+1;i++){
						for(int j=0;j<a.getClockSet().size()+1;j++){
							DBM_element cons=dbm[i][j];
							//System.out.println("DBM_i:"+cons.getDBM_i());
							//System.out.println("DBM_j:"+cons.getDBM_j());
							System.out.println("value:"+cons.getValue());
							System.out.println("Strictness:"+cons.isStrictness());					
						}
					}
				}
				else System.out.println("ʱ��Լ��Ϊ��");
				
				System.out.println("Դ:"+tran.getSource());
				System.out.println("Ŀ�ģ�"+tran.getTarget());
				
				
				if(tran.getEventSet()==null){
					System.out.println("�¼�Ϊ��");
				}
				else if(tran.getEventSet().size()==0){
					System.out.println("�¼�Ϊ���գ�����û������");
				}
				else{
					ArrayList<String> events=tran.getEventSet();
					for(String e:events){
						System.out.println("�¼���"+e);
					}
				}
				
				if(tran.getResetClockSet()==null){
					System.out.println("����ʱ��Ϊ��");
				}
				else if(tran.getResetClockSet().size()==0){
					System.out.println("����ʱ��Ϊ���գ�����û������");
				}
				else{
					ArrayList<String> reset=tran.getResetClockSet();
					for(String r:reset){
						System.out.println("���õ�ʱ�ӣ�"+r);
					}
				}
				
				if(tran.getTypeIds()==null){
					System.out.println("typeIDΪ��");
				}
				else if(tran.getTypeIds().size()==0){
					System.out.println("typeIDΪ���գ�����û������");
				}
				else{
					ArrayList<String> typeid=tran.getTypeIds();
					for(String i:typeid){
						System.out.println("typeid:"+i);
					}
				}
				
				if(tran.getTypes()==null){
					System.out.println("typesΪ��");
				}
				else if(tran.getTypes().size()==0){
					System.out.println("typesΪ���գ�����û������");
				}
				else{
					ArrayList<String> type=tran.getTypes();
					for(String t:type){
						System.out.println("types:"+t);
					}
				}
				
				System.out.println("********************");
			}
			System.out.println("_________________");*/
			
			for(Transition tran:a.getTransitionSet()){
				System.out.println(tran.getSource()+"---->"+tran.getTarget()+"Լ���� "+tran.getEventSet());
			}
			System.out.println("*********************************");
		}
		
		
		
		
		/*System.out.println("�ܹ�"+all_inequalitys.size()+"������ʽ��");
		int e=1;
		for(ArrayList<String> inequalitys:all_inequalitys){
			System.out.println("��"+e+"������ʽ��");
			for(String s:inequalitys){
				System.out.println(s);
			}
			System.out.println("***************");
			e++;
		}*/
		
	}

}
