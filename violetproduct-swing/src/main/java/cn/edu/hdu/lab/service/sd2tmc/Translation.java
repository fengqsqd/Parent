package cn.edu.hdu.lab.service.sd2tmc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.horstmann.violet.application.gui.DisplayForm;

import java.util.Set;

import cn.edu.hdu.lab.config.StaticConfig;
import cn.edu.hdu.lab.dao.interfacedao.InterfaceUCRelation;
import cn.edu.hdu.lab.dao.tmc.State;
import cn.edu.hdu.lab.dao.tmc.Tmc;
import cn.edu.hdu.lab.dao.tmc.TransFlag;
import cn.edu.hdu.lab.dao.tmc.Transition;
import cn.edu.hdu.lab.dao.uml.Fragment;
import cn.edu.hdu.lab.dao.uml.Message;
import cn.edu.hdu.lab.dao.uml.Operand;
import cn.edu.hdu.lab.dao.uml.SD;
import cn.edu.hdu.lab.dao.uml.UseCase;
import cn.edu.hdu.lab.service.write.Write;

public class Translation {

	//private String startId=null,endId=null;
	private SD sdSet;
	private List<SD> sdSets=new ArrayList<SD>();
	private Tmc tmc;
	private List<Tmc> tmcs = new ArrayList<Tmc>();
	private List<Tmc> seqTmcList=new ArrayList<Tmc>();
	
	private State currentState=new State();
	private State start=new State("S0");
	private List<State> finalStates;
	
	private List<Message> messages;
	
	private int count=0;
	
	Tmc F_Tmc=new Tmc();
	
	private static int seqCount=1;
	private static int ucCount=1;
	
	public Translation(){}
	@SuppressWarnings("rawtypes")
	public Tmc UMLTranslationToMarkovChain(List<UseCase> useCases,Map<String, List<InterfaceUCRelation>> UCRMap,List<String> seqNames,List<String> ucNames) throws Exception
	{
		
		//F_Tmc=new Tmc();
		//tmcs = new ArrayList<Tmc>();
		
		/*
		 * ������ǰ��������ʹ�ø��ʣ�����ÿ��������ǰ�������ҵ���һ������
		 */
		for(UseCase useCase:useCases)
		{
			
			if(useCase.getSdSets()!=null)
			{	
				//seqTmcList=new ArrayList<Tmc>();
				tmc=new Tmc();
				
				tmc.setTmcType("UC_Markov");
				tmc.setNames(useCase.getUseCaseName());
				tmc.setOwned("Software");
				finalStates=new ArrayList<State>();
				tmc.setNotation(useCase.getPreCondition());
				tmc.setPro(useCase.getUseCasePro());          //������ʹ�ø���
				if(useCase.equals(useCases.get(0)))
				{
					start.setTmcID("0");
					start.setName("S0");
					start.setLabel("start");
					tmc.setStart(this.start);
					tmc.statesAddState(start);
					count++;
					this.currentState=start;
				}
				else
				{
					State state=createNewState();
					tmc.setStart(state);
					start=state;//����ȫ�ֱ��������ں�����Ӧ�ã�
					tmc.statesAddState(state);
					count++;
					this.currentState=state;
				}
				
				sdSets=useCase.getSdSets();
				sdSet=sdSets.get(0);
										
					messages=null;
					messages=sdSet.getMessages();
					for(Message message:messages)
					{
						if(message.isTranslationed()==true)
							continue;
						message.setProb(sdSet.getProb());
						if(message.isInFragment()==false)
						{
							State state=createNewState();
							if(message.isLast()==true)
							{
								state.setLabel("Final");
								state.setNotation(sdSet.getPostSD());
								finalStates.add(state);
							}
							TransFlag transFlag=new TransFlag(message);
							Transition transition=createTransition(state,transFlag); 
							transition.setNotation(null);
							transition.setNote(null);
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
							message.setTranslationed(true);
						}
						
						//�����Ƭ���е���Ϣ��ת�����Ƭ��
						if(message.isInFragment()==true)
						{
							
							if(sdSet.getFragments()!=null)
							{
								for(Fragment frag:sdSet.getFragments())
								{
									if(frag.isTranslationed())
										continue;
									//��Ϣ������������Ƭ�Σ�������ҪѰ����Ϣ�������Ƭ�ε�·��
									List<String> fragmentIds=retrieveFragmentIds(frag,message);
									
									if(fragmentIds!=null)
									{
										//ת�������Ƭ��  EAID_BE70699D_E756_4df9_B055_37203BB246FC
										State tempState = new State();
										tempState = currentState;
										//�ҵ�
										List sdList=fragmentTranslation(frag,tempState);
										message.setTranslationed(true);
										frag.setTranslationed(true);
										State tempLastState=(State)sdList.get(0);
										Message tempLastMessage=(Message)sdList.get(1);
										if(tempLastMessage.getId().equals(messages.get(messages.size()-1).getId()))
										{
											tempLastState.setLabel("LastFinal");
											tempLastState.setNotation(sdSet.getPostSD());
											finalStates.add(tempLastState);
										}
										break;
									}
								}
							}
						}
						//��һ�������ĵ�һ��˳��ͼִ�����
					}
					
					System.out.println("\n~~~~~~~~~MC~~~~~~~~~~~~~~");
					
					DisplayForm.mainFrame.getOutputinformation().geTextArea().append("\n~~~~~~~~~MC~~~~~~~~~~~~~~"+  "\n");
					int length = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length(); 
					DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);
					Tmc seqTmc=new Tmc();
					this.assignmentTmc(seqTmc, tmc);
					
					seqTmc.setTmcType("Seq_Markov");
					seqTmc.setNames(sdSet.getName());
					seqTmc.setOwned(useCase.getUseCaseName());
//					seqTmc.printTmc();
					//��̬��ȡMC��XML�ļ�
					writeSeqMC(seqTmc,seqNames);
					seqTmcList.add(seqTmc);//��ӵ�����markovģ�ͼ��ϵ���
					
					sdSetsMergeMarkov(seqNames);//�������������Markov chain
					tmc.setEnds(finalStates);
					normalizeOnFinalStates(tmc); //��ͬ����������ĩ״̬��һ
					
					tmc.printTmc();
					 
					writeUcMC(tmc,ucNames);
					tmcs.add(tmc);
					/*
					 * �Լ��ɺ��All������Markov���е�Ǩ������һ������
					 * 
					 *��һ��ԭ��ʹ����ÿ��״̬�ĳ���������ʺ�Ϊ1.0
					 *
					 */
					normalizeOnProbability();
				}
		} //��������ִ�н�����
		
		SoftwareMergeMarkov(tmcs,UCRMap); //�������Markov��
		
		//System.out.println("��������������������������Markov��ʹ��ģ�͡���������������������������������");
		//F_Tmc.printTmc();//��������Markov��ʹ��ģ��
		
		writeSoftwareMC(F_Tmc);
		return F_Tmc;		
	}
	
	
	//��ʣ�µ�˳��ͼ��ӵ�����Ʒ�����
	public void sdSetsMergeMarkov(List<String> seqNames) throws Exception
	{
		if(sdSets.size()<2)
			System.out.println("����ֻ��һ��˳��ͼ");
		else
		{
			for(int i=1;i<sdSets.size();i++)
			{
				sdSet=sdSets.get(i);
				currentState=tmc.getStart();//ָ���ƶ������ף�				
				
				messages=null;
				messages=sdSet.getMessages();
				for (Message message : messages)
				{
					if(message.isTranslationed()==true)
						continue;
					message.setProb(sdSet.getProb());//����ִ�и��ʸ�����������Ϣ�ĸ���
					if (message.isInFragment()==false) 
					{ 
						notFragmentTranslationIntegate(message);
					}
					else 
					{
						//���ø���Ϣ�������������Ƭ����Ϣ
						if(sdSet.getFragments()!=null)
						{
							for(Fragment frag:sdSet.getFragments())
							{
								if(frag.isTranslationed())
									continue;
								//��Ϣ������������Ƭ�Σ�������ҪѰ����Ϣ�������Ƭ�ε�·��
								List<String> fragmentIds=retrieveFragmentIds(frag,message);
								if(fragmentIds!=null)
								{
									//ת�������Ƭ��
									State tempState = new State();
									tempState = currentState;//ָ��ǰ���
							
									@SuppressWarnings("rawtypes")
									List sdList=fragmentTranslationIntegate(frag,tempState);
									message.setTranslationed(true);
									frag.setTranslationed(true);
									State tempLastState=(State)sdList.get(0);
									Message tempLastMessage=(Message)sdList.get(1);
									if(tempLastMessage.equals(messages.get(messages.size()-1)))
									{
										tempLastState.setLabel("Final---Last");
										tempLastState.setNotation(sdSet.getPostSD());
										finalStates.add(tempLastState);
									}
									break;
								}
							}
						}
					}
				} 
				Tmc seqTmc=new Tmc();
				this.assignmentTmc(seqTmc, tmc);
				seqTmc.setTmcType("Seq_Markov");
				seqTmc.setOwned(tmc.getNames());
				seqTmc.setNames(seqTmcList.get(seqTmcList.size()-1).getNames()+"; "+sdSet.getName());				
				seqTmcList.add(seqTmc);				
				writeSeqMC(seqTmc,seqNames);
			}
		}
	}
 
	private static void normalizeOnFinalStates(Tmc tempTmc) //��ͬ����������ĩ״̬��һ
	{
		Map<String,State> pCList=new HashMap<String,State>();
		for(State s:tempTmc.getEnds())
		{
			if(pCList.containsKey(s.getNotation()))
			{
				continue;
			}
			else
			{
				pCList.put(s.getNotation(), s);
			}
		}	
		List<State> tList=new ArrayList<State>();
		for(State s:tempTmc.getEnds())
		{
			for(Entry<String,State> e:pCList.entrySet())
			{
				int f=0;
				if(e.getKey().equals(s.getNotation()))
				{
					if(e.getValue().equals(s))
					{
						continue;
					}
					else
					{
						f=1;
						//��������Ǩ�ƣ��ض���to���
						for(Transition tr:tempTmc.getTransitions())
						{
							if(tr.getTo().equals(s))
							{
								tr.setTo(e.getValue());
								tList.add(s);
							}
						}
					}
				}
				if(f==1)
				{
					continue;
				}
			}
		}	
		for(State s:tList)
		{
			tempTmc.getEnds().remove(s);
			tempTmc.getStates().remove(s);
		}
		tList.clear();
		pCList.clear();
		
		
	}
	//�����Ϣ�������Ƭ����
	public void notFragmentTranslationIntegate(Message message)
	{
		boolean issame = false;
		Iterator<Transition> transiterator = tmc.getTransitions().iterator();
		while (transiterator.hasNext()) 
		{
			Transition transition = transiterator.next();
			
			if (currentState.equals(transition.getFrom())
					&&message.getSender().equals(transition.getTransFlag().getSender())
					&& message.getReceiver().equals(transition.getTransFlag().getReceiver())
					&& message.getName().equals(transition.getTransFlag().getName())) 
			{
				transition.getTransFlag().setProb(transition.getTransFlag().getProb() + message.getProb());
				currentState = transition.getTo();
				issame = true;
				break;
			}
		}
		if (!issame) 
		{
			State state = createNewState();
			TransFlag transFlag=new TransFlag(message);
			Transition newTransition = createTransition(state,transFlag);
			newTransition.setNotation(null);
			if (message.isLast()) 
			{
				state.setLabel("Final");
				state.setNotation(sdSet.getPostSD());
				finalStates.add(state);
			}
			tmc.statesAddState(state);
			tmc.transitionsAddtransition(newTransition);
			currentState = newTransition.getTo();
			count++;      //ȫ�ֵ�
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List fragmentTranslationIntegate(Fragment frag,State tempState)
	{
		Message lastMessage=null;
		State finalState = null;
        List<Object> fragFlagList=retrieveFragFlag(frag.getType());
		
		if ((int)fragFlagList.get(0)==0) //loop����
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
			
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,oper));
				
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;
					// ����Ϣ�ڵ�ǰ���Ƭ���У�ֱ��ת��
					if (mess.getOperandId().equals(oper.getId())==true) 
					{
						boolean issame = false;
						Iterator<Transition> transiterator = tmc.getTransitions().iterator();
						while (transiterator.hasNext()) 
						{
							Transition transition = transiterator.next();
							if (currentState.equals(transition.getFrom())
									&&mess.getSender().equals(transition.getTransFlag().getSender())
									&& mess.getReceiver().equals(transition.getTransFlag().getReceiver())
									&& mess.getName().equals(transition.getTransFlag().getName()))  
							{
								
								transition.getTransFlag().setProb(transition.getTransFlag().getProb() + mess.getProb());
								mess.setTranslationed(true);
								currentState = transition.getTo();
								issame = true;
								if(mess.equals(operMessages.get(operMessages.size()-1)))
								{
									lastMessage=mess;
									Transition nextTr=tmc.getTransitions().get(tmc.getTransitions().indexOf(transition)+1);
									finalState=nextTr.getTo();
									currentState=nextTr.getTo();
								}
								break;
							}
						}
					if(!issame)
					{
						//����ǵ�ǰ��������ߡ���
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							
							State state=createNewState();
							/*if(mess.isLast()==true)
							{
								state.setLabel("Final");
								state.setNotation(sdSet.getPostSD());
								finalStates.add(state);
							}*/
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"inLoop");//������
							transition.setNote(oper.getCondition());//���ִ������
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
						}
						//����ǳ�
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
							/*if(mess.isLast()==true)
							{
								state.setLabel("Final");
								state.setNotation(sdSet.getPostSD());
								finalStates.add(state);
							}*/
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNote(oper.getCondition());//���ִ������
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
						}
						//�ǳ��ߣ����������
						if( mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
							//δ״̬Ϊ����Ҫ����δ״̬,������رߣ�
							//ֻ��һ��������ĩ�ǳ��ߣ�ĩ״̬�϶�Ϊ��
							if(finalState==null)
							{	State state=createNewState();
								/*if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}*/
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								if(mess.equals(operMessages.get(0))==true)
								{
									
									transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1)+"-out"+fragFlagList.get(1));//�����ߺͳ���
								}
								if(mess.equals(operMessages.get(0))==false)
									transition.setNotation(transition.getNotation()+"out"+fragFlagList.get(1));//��ӳ���
								transition.setNote(oper.getCondition());//���ִ������
								
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;
								count++;

								State outState=createNewState();
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								Transition outTransition=createTransition(outState,outTransFlag); 
								outTransition.setNotation(null);//�ձ�
								outTransition.setNote(null);//������ִ��
								
								tmc.statesAddState(outState);
								tmc.transitionsAddtransition(outTransition);
								count++;
								currentState=outState;
								finalState=outState;
								
								//����ر�
								TransFlag loopBackFlag=new TransFlag(null,null,null,null,null,1,null);
								Transition loopBackTransition=createTransition(tempState,loopBackFlag);
								loopBackTransition.setNotation("back"+fragFlagList.get(1));
								loopBackTransition.setNote(oper.getCondition());//���ִ������
								tmc.transitionsAddtransition(loopBackTransition);
								}
							}			
						mess.setTranslationed(true);
					}
				}
					//���ڵ�ǰ���Ƭ����
					else
					{
						
						if(mess.isTranslationed()==true)
							continue;
						State newFinalState=new State();
						State newTempState=currentState;
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								if(newFrag.isTranslationed()==true)
									continue;
								//��Ϣ������������Ƭ�Σ�������ҪѰ����Ϣ�������Ƭ�ε�·��
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									//ת�������Ƭ��
									List newList=fragmentTranslationIntegate(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);									//����������һ����Ϣ�����������
									//��������һ����Ϣ(û�к����Ϣ)���������²���
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										
										lastMessage=tempMessage;
										//����newFinalStateȷ�������Ѿ��еĿ�Ǩ�ƣ��Ӷ�ȷ����Ǩ�Ƶ�Ŀ��״̬
										boolean tag=false;
										for(Transition tr:tmc.getTransitions())
										{
											String str=tr.getNotation();
											if(str==null)
												str="str";
											if(tr.getFrom().equals(newFinalState)
													&&tr.getTransFlag().getName()==null
													&&tr.getTransFlag().getProb()==1.0
													&&str.equals("backLoop"))//����жϲ��ɸ���
											{
												finalState=tr.getTo();//�ҵ�ԭ�е�ĩ״̬
												tag=true;
												break;
											}
										}		
										
										if(!tag)
										{
											State outState=createNewState();
											TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
											Transition outTransition=createTransition(outState,outTransFlag); 
											outTransition.setNotation(null);//�ձ�
											outTransition.setNote(null);//������ִ��
											
											tmc.statesAddState(outState);
											tmc.transitionsAddtransition(outTransition);
											count++;
											currentState=outState;
											finalState=outState;
											
											//����ر�
											TransFlag loopBackFlag=new TransFlag(null,null,null,null,null,1,null);
											Transition loopBackTransition=createTransition(tempState,loopBackFlag);
											loopBackTransition.setNotation("back"+fragFlagList.get(1));
											loopBackTransition.setNote(oper.getCondition());//���ִ������
											tmc.transitionsAddtransition(loopBackTransition);
										}
										currentState=finalState;
										
//										System.out.println("ִ�е�����"+mess.getName());
//										System.out.println("CS״̬����"+currentState.getName());
//										System.out.println("tempState״̬����"+tempState.getName());
//										if(finalState!=null)
//											System.out.println("finalState״̬����"+finalState.getName());
									}
									break;
								}
							}
						}
					}
				  
				}
			}
		}
		else
			if ((int)fragFlagList.get(0)==1)  //opt����
			{
				List<Message> currentMessageList = retrieveCurrentMessages(frag);
				
				for (Operand oper : frag.getOperands()) 
				{
					List<Message> operMessages = new ArrayList<Message>();
					operMessages.addAll(retrieveOperandMessages(currentMessageList,oper));
					
					for (Message mess : operMessages) 
					{
						if(mess.isTranslationed()==true)
							continue;
						// ����Ϣ�ڵ�ǰ���Ƭ���У�ֱ��ת��
						if (mess.getOperandId().equals(oper.getId())==true) 
						{
							boolean issame = false;
							Iterator<Transition> transiterator = tmc.getTransitions().iterator();
							while (transiterator.hasNext()) 
							{
								Transition transition = transiterator.next();
								if (currentState.equals(transition.getFrom())
										&&mess.getSender().equals(transition.getTransFlag().getSender())
										&& mess.getReceiver().equals(transition.getTransFlag().getReceiver())
										&& mess.getName().equals(transition.getTransFlag().getName()))  
								{
									
									transition.getTransFlag().setProb(transition.getTransFlag().getProb() + mess.getProb());
									mess.setTranslationed(true);
									currentState = transition.getTo();
									issame = true;
									if(mess.equals(operMessages.get(operMessages.size()-1)))
									{
										lastMessage=mess;
										Transition nextTr=tmc.getTransitions().get(tmc.getTransitions().indexOf(transition)+1);
										finalState=nextTr.getTo();
										currentState=nextTr.getTo();
									}
									break;
								}
							}
						if(!issame)
						{
							//����ǵ�ǰ��������ߡ���
							if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
							{
								
								State state=createNewState();
								/*if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}*/
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"inLoop");//������
								transition.setNote(oper.getCondition());//���ִ������
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;//ָ������
								count++; 
							}
							//����ǳ�
							if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
							{
								State state=createNewState();
								/*if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}*/
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNote(oper.getCondition());//���ִ������
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;//ָ������
								count++; 
							}
							//�ǳ��ߣ����������
							if( mess.equals(operMessages.get(operMessages.size()-1))==true)
							{
								lastMessage=mess;
								//δ״̬Ϊ����Ҫ����δ״̬,������رߣ�
								//ֻ��һ��������ĩ�ǳ��ߣ�ĩ״̬�϶�Ϊ��
								if(finalState==null)
								{	State state=createNewState();
									/*if(mess.isLast()==true)
									{
										state.setLabel("Final");
										state.setNotation(sdSet.getPostSD());
										finalStates.add(state);
									}*/
									TransFlag transFlag=new TransFlag(mess);
									Transition transition=createTransition(state,transFlag); 
									if(mess.equals(operMessages.get(0))==true)
									{
										transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1)+"-out"+fragFlagList.get(1));//�����ߺͳ���
									}
									if(mess.equals(operMessages.get(0))==false)
										transition.setNotation(transition.getNotation()+"out"+fragFlagList.get(1));//��ӳ���
									transition.setNote(oper.getCondition());//���ִ������
									
									tmc.statesAddState(state);
									tmc.transitionsAddtransition(transition);
									currentState=state;
									count++;

									State outState=createNewState();
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									Transition outTransition=createTransition(outState,outTransFlag); 
									outTransition.setNotation(null);//�ձ�
									outTransition.setNote(null);//������ִ��
									
									tmc.statesAddState(outState);
									tmc.transitionsAddtransition(outTransition);
									count++;
									currentState=outState;
									finalState=outState;
									
									//opt���Ͳ��ü���ر�
									}
								}			
							mess.setTranslationed(true);
						}
					}
						//���ڵ�ǰ���Ƭ����
						else
						{
							
							if(mess.isTranslationed()==true)
								continue;
							State newFinalState=new State();
							State newTempState=currentState;
							if(oper.getFragments()!=null)
							{
								for(Fragment newFrag:oper.getFragments())
								{
									if(newFrag.isTranslationed()==true)
										continue;
									//��Ϣ������������Ƭ�Σ�������ҪѰ����Ϣ�������Ƭ�ε�·��
									List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
									if(fragmentIds!=null)
									{
										//ת�������Ƭ��
										List newList=fragmentTranslationIntegate(newFrag,newTempState);
										newFinalState=(State)newList.get(0);
										Message tempMessage=(Message)newList.get(1);
										newFrag.setTranslationed(true);									//����������һ����Ϣ�����������
										//��������һ����Ϣ(û�к����Ϣ)���������²���
										if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
										{
											lastMessage=tempMessage;
											//����newFinalStateȷ�������Ѿ��еĿ�Ǩ�ƣ��Ӷ�ȷ����Ǩ�Ƶ�Ŀ��״̬
											boolean tag=false;
											for(Transition tr:tmc.getTransitions())
											{
												String str=tr.getNotation();
												if(str==null)
													str="str";
												if(tr.getFrom().equals(newFinalState)
														&&tr.getTransFlag().getName()==null
														&&tr.getTransFlag().getProb()==1.0
														&&str.equals("backLoop"))//����жϲ��ɸ���
												{
													finalState=tr.getTo();//�ҵ�ԭ�е�ĩ״̬
													tag=true;
													break;
												}
											}		
											
											if(!tag)
											{
												State outState=createNewState();
												TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
												Transition outTransition=createTransition(outState,outTransFlag); 
												outTransition.setNotation(null);//�ձ�
												outTransition.setNote(null);//������ִ��
												
												tmc.statesAddState(outState);
												tmc.transitionsAddtransition(outTransition);
												count++;
												currentState=outState;
												finalState=outState;
												
												//opt����,���ü���ر�
											}
											currentState=finalState;											
										}
										break;
									}
								}
							}
						}
					  
					}
				}
			}
		else
		//if ((int)fragFlagList.get(0)!=0)    //par alt �������Ƭ��
		{
			

			List<Message> currentMessageList = retrieveCurrentMessages(frag);
			
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,	oper));
				
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;

					// ����Ϣ�ڵ�ǰ�����У�ֱ��ת��
					if (mess.getOperandId().equals(oper.getId())==true) 
					{
						
						
						boolean issame = false;
						Iterator<Transition> transiterator = tmc.getTransitions().iterator();
						
						while (transiterator.hasNext())
						{
							Transition transition = transiterator.next();
							
							if (currentState.equals(transition.getFrom())
									&& mess.getSender().equals(transition.getTransFlag().getSender())
									&& mess.getReceiver().equals(transition.getTransFlag().getReceiver())
									&& mess.getName().equals(transition.getTransFlag().getName())) 
							{
								
								transition.getTransFlag().setProb(transition.getTransFlag().getProb() + mess.getProb());
								mess.setTranslationed(true);
								currentState = transition.getTo();
								issame = true;
								if(mess.equals(operMessages.get(operMessages.size()-1)))
								{
									
									lastMessage=mess;
									Transition nextTr=tmc.getTransitions().get(tmc.getTransitions().indexOf(transition)+1);
									finalState=nextTr.getTo();
									if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
										currentState=nextTr.getTo();
									else
										currentState=tempState;
									
								}
								break;
							}
						}
						if(issame==false)
						{	
							//��������ߡ�
							if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
							{
								currentState=tempState;
								State state=createNewState();
								/*if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}*/
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1));//������
							
								transition.setNote(oper.getCondition());//���ִ������
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;//ָ������
								count++; 
							}
							//����ǳ�
							if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
							{
								State state=createNewState();
								/*if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}*/
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								transition.setNote(oper.getCondition());//���ִ������
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;//ָ������
								count++; 
							}
							//��������ǳ���
							if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==true)
							{
								
								currentState=tempState;
								lastMessage=mess;
									State state=createNewState();
									/*if(mess.isLast()==true)
									{
										state.setLabel("Final");
										state.setNotation(sdSet.getPostSD());
										finalStates.add(state);
									}*/
									TransFlag transFlag=new TransFlag(mess);
									Transition transition=createTransition(state,transFlag); 
									
									transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1)+"-out"+fragFlagList.get(1));//�����ߺͳ���
									transition.setNote(oper.getCondition());//���ִ������
									tmc.statesAddState(state);
									tmc.transitionsAddtransition(transition);
									count++; 
									currentState=state;
									
									//δ״̬Ϊ����Ҫ����δ״̬�����ں�����һ����ĩ״̬��
									if(finalState==null)
									{
										//����ĩ״̬�Ϳ�Ǩ�ƣ���Ǩ��ִ�и���Ϊ1���Զ����䵽ĩ״̬
										State outState=createNewState();
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										
										Transition outTransition=createTransition(outState,outTransFlag); 
										outTransition.setNotation(null);//�ձ�
										outTransition.setNote(null);//������ִ��
										
										tmc.statesAddState(outState);
										tmc.transitionsAddtransition(outTransition);
										count++;
										finalState=outState;
									}
									//ĩ״̬��Ϊ�ձ�ʾִ�е�����Ĳ�������Ҫ����ĩ״̬��һ�������ò���״̬��ֱ�ӽ���Ǩ��ָ��finalState��
									else
									{
										
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										Transition outTransition=createTransition(finalState,outTransFlag); 
										outTransition.setNotation(null);//�ձ�
										outTransition.setNote(null);//������ִ��
										tmc.transitionsAddtransition(outTransition);
									}
									
									if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
										{
										  currentState=finalState;
										}
									else
										{
										 currentState=tempState;
										}
									
							}
							//�����ǳ���
							if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==true)
							{
								
								lastMessage=mess;
								State state=createNewState();
								/*if(mess.isLast()==true)
								{
									state.setLabel("Final");
									state.setNotation(sdSet.getPostSD());
									finalStates.add(state);
								}*/
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"out"+fragFlagList.get(1));//�����ߺͳ���
								transition.setNote(oper.getCondition());//���ִ������
								
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								count++; 
								currentState=state;
								
								//δ״̬Ϊ����Ҫ����δ״̬�����ں�����һ����ĩ״̬��
								if(finalState==null)
								{
									//����ĩ״̬�Ϳ�Ǩ�ƣ���Ǩ��ִ�и���Ϊ1���Զ����䵽ĩ״̬
									State outState=createNewState();
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									
									Transition outTransition=createTransition(outState,outTransFlag); 
									outTransition.setNotation(null);//�ձ�
									outTransition.setNote(null);//������ִ��
									
									tmc.statesAddState(outState);
									tmc.transitionsAddtransition(outTransition);
									count++;
									finalState=outState;
								}
								//ĩ״̬��Ϊ�ձ�ʾִ�е�����Ĳ�������Ҫ����ĩ״̬��һ�������ò���״̬��ֱ�ӽ���Ǩ��ָ��finalState��
								else
								{
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									Transition outTransition=createTransition(finalState,outTransFlag); 
									outTransition.setNotation(null);//�ձ�
									outTransition.setNote(null);//������ִ��
									tmc.transitionsAddtransition(outTransition);
								}
								
								if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
									currentState=finalState;
								else
									currentState=tempState;
								
							}
							mess.setTranslationed(true);
						}
					}
					 else // ���ڵ�ǰ���Ƭ��den��ǰ�����У��ø���Ϣ��ȡ������Ƭ�κͶ�Ӧ��Ϣ
					{

							State newTempState=currentState;
							State newFinalState=new State();
							if(oper.getFragments()!=null)
							{
								for(Fragment newFrag:oper.getFragments())
								{
									//��Ϣ������������Ƭ�Σ�������ҪѰ����Ϣ�������Ƭ�ε�·��
									List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
									if(fragmentIds!=null)
									{
										if(newFrag.isTranslationed()==true)
											continue;
										//ת�������Ƭ��
										List newList=fragmentTranslationIntegate(newFrag,newTempState);
										newFinalState=(State)newList.get(0);
										Message tempMessage=(Message)newList.get(1);
										newFrag.setTranslationed(true);
										//����������һ����Ϣ�����������
										//��������һ����Ϣ(û�к����Ϣ)���������²���
										if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
										{

											lastMessage=tempMessage;
											if(oper.equals(frag.getOperands().get(0)))
											{
												boolean tag=false;
												for(Transition tr:tmc.getTransitions())
												{
													String str=tr.getNotation();
													if(str==null)
														str="str";
													if(tr.getFrom().equals(newFinalState)
															&&tr.getTransFlag().getName()==null
															&&tr.getTransFlag().getProb()==1.0
															&&!str.equals("backLoop"))
													{
														finalState=tr.getTo();//�ҵ�ԭ�е�ĩ״̬//��Ǩ���Ѿ����ڣ�����Ҫ�ٽ�����
														currentState=tempState;
														tag=true;
														break;
													}
												}	
												if(!tag)//����������ɵĲ�����֧
												{
													State outState=createNewState();
													TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
													
													Transition outTransition=createTransition(outState,outTransFlag); 
													outTransition.setNotation(null);//�ձ�
													outTransition.setNote(null);//������ִ��
													
													tmc.statesAddState(outState);
													tmc.transitionsAddtransition(outTransition);
													count++;
													finalState=outState;
												}
												
											}
											else
											{
												//����������ɵĲ���·��������Ҫ������Ǩ�����ӵ�finalState
												//
												//���򣬿�Ǩ���Ѿ�����,CS�Զ����䵽ĩ״̬
												//(���һ������·��CS�Ƶ�finalState���м����·��CS�ƶ���tempState)
												//
												boolean tag=false;
												for(Transition tr:tmc.getTransitions())
												{
													String str=tr.getNotation();
													if(str==null)
														str="str";
													if(tr.getFrom().equals(newFinalState)
															&&tr.getTransFlag().getName()==null
															&&tr.getTransFlag().getProb()==1.0
															&&!str.equals("backLoop"))
													{
														tag=true;
														break;
													}
													
												}
												//System.out.println(currentState.getName()+"****"+finalState.getName()+"***"+tempState.getName());
												if(!tag)
												{
													TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
													Transition outTransition=createTransition(finalState,outTransFlag); 
													outTransition.setNotation(null);//�ձ�
													outTransition.setNote(null);//������ִ��
													tmc.transitionsAddtransition(outTransition);
													
												}
												if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
												{
													currentState=finalState;
												}
												else
												{
													currentState=tempState;
												}
													
											}
										}
										break;
									}
								}
							}
						}// ���ڵ�ǰ���Ƭ����
					}//��Ϣ��������
			 }
		}
		

		List list=new ArrayList();
		list.add(finalState);
		list.add(lastMessage);
		return list;
	
	}
	
	/**
	 * 
	 * @param frag ��ǰ���Ƭ��
	 * @param tempState ��ǰ���Ƭ�ε�ǰ��״̬
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List fragmentTranslation(Fragment frag,State tempState)
	{
		/* * ���ж��Ƿ���˳��ͼ�µ�һ�����Ƭ�Σ�����id�жϣ�
		 * 	����ڵ�һ�����Ƭ�Σ��������Ϣ����Id,ȷ����������;
		 * 		��Ϣ�϶��Ǹõ�һ���������Ƭ��֮ǰ�������������������
		 * 		 ��ȡ��ǰ���Ƭ��������Ϣ�������ڲ���Ϣ������˳��ͼ����Ϣ˳����ӣ�
					 * ����˳��ͼ����Ϣ���ϣ���ȡ�����Ƭ����������Ϣ���������Ƭ������Ϣ����
					 *���Ȼ�ȡ�����Ƭ���µ���Ϣ�������ڲ����Ƭ���е���Ϣ�������������ϼ�����A�У�Ȼ����˳��ͼ����Ϣһһ�����Ƚϣ������A�У�����ӵ�B�����У�
		 */
		Message lastMessage=null;
		State finalState = null;
		
		/*
		 * List���������
		 * ��һ����int����  ���Ƭ�������±�
		 * �ڶ�����String ���ش�д��ͷ�����Ƭ�����ͣ������д���Ƭ�����͵ĳ����־
		 */
		List<Object> fragFlagList=retrieveFragFlag(frag.getType());
		
		
		if((int)fragFlagList.get(0)==0)  //loop����
		{
			
			List<Message> currentMessageList = retrieveCurrentMessages(frag);			
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,oper));
				
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;
					// ����Ϣ�ڵ�ǰ���Ƭ���У�ֱ��ת��
					if (mess.getOperandId().equals(oper.getId())==true && mess.isTranslationed()==false) 
					{
						//����ǵ�ǰ��������ߡ���
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							
							State state=createNewState();
							
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1));//������
							transition.setNote(oper.getCondition());//���ִ������
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
						}
						//����ǳ�
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNote(oper.getCondition());//���ִ������
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
						}
						//�ǳ��ߣ����������
						if( mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
							//δ״̬Ϊ����Ҫ����δ״̬,������رߣ�
							//ֻ��һ��������ĩ�ǳ��ߣ�ĩ״̬�϶�Ϊ��
							if(finalState==null)
							{	State state=createNewState();
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								if(mess.equals(operMessages.get(0))==true)
								{
									
									transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1)+"-out"+fragFlagList.get(1));//�����ߺͳ���
								}
								
								if(mess.equals(operMessages.get(0))==false)
								{
									transition.setNotation(transition.getNotation()+"out"+fragFlagList.get(1));//��ӳ���
								}
								transition.setNote(oper.getCondition());//���ִ������
								
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;
								count++;

								State outState=createNewState();
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								
								Transition outTransition=createTransition(outState,outTransFlag); 
								transition.setNotation(null);//�ձ�
								transition.setNote(null);//������ִ��
								
								tmc.statesAddState(outState);
								tmc.transitionsAddtransition(outTransition);
								count++;
								currentState=outState;
								finalState=outState;
								
								//����ر�
								TransFlag loopBackFlag=new TransFlag(null,null,null,null,null,1.0,null);
								Transition loopBackTransition=createTransition(tempState,loopBackFlag);
								loopBackTransition.setNotation("back"+fragFlagList.get(1));
								loopBackTransition.setNote(oper.getCondition());//���ִ������
								tmc.transitionsAddtransition(loopBackTransition);
								
							}
						}			
						mess.setTranslationed(true);
					}
					
					//���ڵ�ǰ���Ƭ����
					else
					{
						if(mess.isTranslationed()==true)
							continue;
						State newFinalState=new State();
						State newTempState=currentState;
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								if(newFrag.isTranslationed()==true)
									continue;
								//��Ϣ������������Ƭ�Σ�������ҪѰ����Ϣ�������Ƭ�ε�·��
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									
									//ת�������Ƭ��
									List newList=fragmentTranslation(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);									//����������һ����Ϣ�����������
									//��������һ����Ϣ(û�к����Ϣ)���������²���
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										//�����Ǩ��
										currentState=newFinalState;
										State outState=createNewState();
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										Transition outTransition=createTransition(outState,outTransFlag); 
										outTransition.setNotation(null);//�ձ�
										outTransition.setNote(null);//������ִ��
										
										tmc.statesAddState(outState);
										tmc.transitionsAddtransition(outTransition);
										count++;
										currentState=outState;
										finalState=outState;
										lastMessage=tempMessage;
										
										//����ر�
										TransFlag loopBackFlag=new TransFlag(null,null,null,null,null,1.0,null);
										
										Transition loopBackTransition=createTransition(tempState,loopBackFlag);
										loopBackTransition.setNotation("back"+fragFlagList.get(1));
										loopBackTransition.setNote(oper.getCondition());//���ִ������
										tmc.transitionsAddtransition(loopBackTransition);
									}
									break;
								}
							}
						}
					}
				}
			}
		}
		
		else if((int)fragFlagList.get(0)==1) //opt����
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
			
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,oper));
				
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;
					// ����Ϣ�ڵ�ǰ���Ƭ���У�ֱ��ת��
					if (mess.getOperandId().equals(oper.getId())==true && mess.isTranslationed()==false) 
					{
						//����ǵ�ǰ��������ߡ���
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							
							State state=createNewState();
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1));//������
							transition.setNote(oper.getCondition());//���ִ������
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
						}
						//����ǳ�
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNote(oper.getCondition());//���ִ������
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
						}
						//�ǳ��ߣ����������
						if( mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
							//δ״̬Ϊ����Ҫ����δ״̬,������رߣ�
							//ֻ��һ��������ĩ�ǳ��ߣ�ĩ״̬�϶�Ϊ��
							if(finalState==null)
							{	State state=createNewState();
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								if(mess.equals(operMessages.get(0))==true)
								{
									
									transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1)+"-out"+fragFlagList.get(1));//�����ߺͳ���
								}
								
								if(mess.equals(operMessages.get(0))==false)
								{
									transition.setNotation(transition.getNotation()+"out"+fragFlagList.get(1));//��ӳ���
								}
								transition.setNote(oper.getCondition());//���ִ������
								
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								currentState=state;
								count++;

								State outState=createNewState();
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								
								Transition outTransition=createTransition(outState,outTransFlag); 
								transition.setNotation(null);//�ձ�
								transition.setNote(null);//������ִ��
								
								tmc.statesAddState(outState);
								tmc.transitionsAddtransition(outTransition);
								count++;
								currentState=outState;
								finalState=outState;
								
								//opt���ͣ�һ��������������ر�
								
							}
						}			
						mess.setTranslationed(true);
					}
					
					//���ڵ�ǰ���Ƭ����
					else
					{
						if(mess.isTranslationed()==true)
							continue;
						State newFinalState=new State();
						State newTempState=currentState;
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								if(newFrag.isTranslationed()==true)
									continue;
								//��Ϣ������������Ƭ�Σ�������ҪѰ����Ϣ�������Ƭ�ε�·��
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									
									//ת�������Ƭ��
									List newList=fragmentTranslation(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);									//����������һ����Ϣ�����������
									//��������һ����Ϣ(û�к����Ϣ)���������²���
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										//�����Ǩ��
										currentState=newFinalState;
										State outState=createNewState();
										TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
										Transition outTransition=createTransition(outState,outTransFlag); 
										outTransition.setNotation(null);//�ձ�
										outTransition.setNote(null);//������ִ��
										
										tmc.statesAddState(outState);
										tmc.transitionsAddtransition(outTransition);
										count++;
										currentState=outState;
										finalState=outState;
										lastMessage=tempMessage;
										//opt���ͣ�������ر�
									}
									break;
								}
							}
						}
					}
				}
			}
		}
		else 
		//if ((int)fragFlagList.get(0)!=0) //alt  par ����
		{
			List<Message> currentMessageList = retrieveCurrentMessages(frag);
//			// ���������������
//			System.out.println("\n**********���Ƭ���µ���Ϣ********");
//			  for(Message mess:currentMessageList) 
//			  { mess.print_Message(); }
//			
//			  System.out.println("##########"+frag.getName()+"���Ƭ����"+frag.getOperands().size()+"������");
//			// ��Բ�ͬ������ȡ��Ӧ��Ϣ
			for (Operand oper : frag.getOperands()) 
			{
				List<Message> operMessages = new ArrayList<Message>();
				operMessages.addAll(retrieveOperandMessages(currentMessageList,	oper));
				for (Message mess : operMessages) 
				{
					if(mess.isTranslationed()==true)
						continue;
					// ����Ϣ�ڵ�ǰ���Ƭ���У�ֱ��ת��
					if (mess.getOperandId().equals(oper.getId())==true) 
					{
						//����ǵ�ǰ��������ߡ���
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
							
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1));//������
							transition.setNote(oper.getCondition());//���ִ������
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
						}
						//����ǳ�
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==false)
						{
							State state=createNewState();
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNote(oper.getCondition());//���ִ������
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							currentState=state;//ָ������
							count++; 
							
						}
						//��������ǳ���
						if(mess.equals(operMessages.get(0))==true && mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
								State state=createNewState();
								TransFlag transFlag=new TransFlag(mess);
								Transition transition=createTransition(state,transFlag); 
								
								transition.setNotation(transition.getNotation()+"in"+fragFlagList.get(1)+"-out"+fragFlagList.get(1));//�����ߺͳ���
								transition.setNote(oper.getCondition());//���ִ������
								tmc.statesAddState(state);
								tmc.transitionsAddtransition(transition);
								count++; 
								currentState=state;
								
								//δ״̬Ϊ����Ҫ����δ״̬�����ں�����һ����ĩ״̬��
								if(finalState==null)
								{
									//����ĩ״̬�Ϳ�Ǩ�ƣ���Ǩ��ִ�и���Ϊ1���Զ����䵽ĩ״̬
									State outState=createNewState();
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									
									Transition outTransition=createTransition(outState,outTransFlag); 
									outTransition.setNotation(null);//�ձ�
									outTransition.setNote(null);//������ִ��
									
									tmc.statesAddState(outState);
									tmc.transitionsAddtransition(outTransition);
									count++;
									finalState=outState;
								}

								//ĩ״̬��Ϊ�ձ�ʾִ�е�����Ĳ�������Ҫ����ĩ״̬��һ�������ò���״̬��ֱ�ӽ���Ǩ��ָ��finalState��
								else
								{
									
									TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
									Transition outTransition=createTransition(finalState,outTransFlag); 
									outTransition.setNotation(null);//�ձ�
									outTransition.setNote(null);//������ִ��
									tmc.transitionsAddtransition(outTransition);
								}
								
								if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
									{
									  currentState=finalState;
									  
									}
								else
									{
									 currentState=tempState;
									}
						}
						//�����ǳ���
						if(mess.equals(operMessages.get(0))==false && mess.equals(operMessages.get(operMessages.size()-1))==true)
						{
							lastMessage=mess;
							State state=createNewState();
							TransFlag transFlag=new TransFlag(mess);
							Transition transition=createTransition(state,transFlag); 
							
							transition.setNotation(transition.getNotation()+"out"+fragFlagList.get(1));//�����ߺͳ���
							transition.setNote(oper.getCondition());//���ִ������
							
							tmc.statesAddState(state);
							tmc.transitionsAddtransition(transition);
							count++; 
							currentState=state;
							
							//δ״̬Ϊ����Ҫ����δ״̬�����ں�����һ����ĩ״̬��
							if(finalState==null)
							{
								//����ĩ״̬�Ϳ�Ǩ�ƣ���Ǩ��ִ�и���Ϊ1���Զ����䵽ĩ״̬
								State outState=createNewState();
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								
								Transition outTransition=createTransition(outState,outTransFlag); 
								outTransition.setNotation(null);//�ձ�
								outTransition.setNote(null);//������ִ��
								
								tmc.statesAddState(outState);
								tmc.transitionsAddtransition(outTransition);
								count++;
								finalState=outState;
							}

							//ĩ״̬��Ϊ�ձ�ʾִ�е�����Ĳ�������Ҫ����ĩ״̬��һ�������ò���״̬��ֱ�ӽ���Ǩ��ָ��finalState��
							else
							{
								TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
								Transition outTransition=createTransition(finalState,outTransFlag); 
								outTransition.setNotation(null);//�ձ�
								outTransition.setNote(null);//������ִ��
								tmc.transitionsAddtransition(outTransition);
							}
							//System.out.println(currentState);
							
							if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
								currentState=finalState;
							else
								currentState=tempState;
						}
						mess.setTranslationed(true);
					} 
					else  // ���ڵ�ǰ���Ƭ���У��ø���Ϣ��ȡ������Ƭ�κͶ�Ӧ��Ϣ
					{
						State newTempState=currentState;
						State newFinalState=new State();
						if(oper.getFragments()!=null)
						{
							for(Fragment newFrag:oper.getFragments())
							{
								//��Ϣ������������Ƭ�Σ�������ҪѰ����Ϣ�������Ƭ�ε�·��
								List<String> fragmentIds=retrieveFragmentIds(newFrag,mess);
								if(fragmentIds!=null)
								{
									if(newFrag.isTranslationed()==true)
										continue;
									//ת�������Ƭ��
									List newList=fragmentTranslation(newFrag,newTempState);
									newFinalState=(State)newList.get(0);
									Message tempMessage=(Message)newList.get(1);
									newFrag.setTranslationed(true);
									//����������һ����Ϣ�����������
									//��������һ����Ϣ(û�к����Ϣ)���������²���
									if(tempMessage.equals(operMessages.get(operMessages.size()-1)))
									{
										lastMessage=tempMessage;
										if(finalState==null)
										{
											finalState=newFinalState;
											//����ĩ״̬�Ϳ�Ǩ�ƣ���Ǩ��ִ�и���Ϊ1���Զ����䵽ĩ״̬
											State outState=createNewState();
											TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
											
											Transition outTransition=createTransition(outState,outTransFlag); 
											outTransition.setNotation(null);//�ձ�
											outTransition.setNote(null);//������ִ��
											
											tmc.statesAddState(outState);
											tmc.transitionsAddtransition(outTransition);
											count++;
											finalState=outState;
											currentState=tempState;
										}
										else
										{
											if(oper.equals(frag.getOperands().get(frag.getOperands().size()-1)))
											{

												TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
												Transition outTransition=createTransition(finalState,outTransFlag); 
												outTransition.setNotation(null);//�ձ�
												outTransition.setNote(null);//������ִ��
												tmc.transitionsAddtransition(outTransition);
												currentState=finalState;
											}
											else
											{
												TransFlag outTransFlag=new TransFlag(null,null,null,null,null,1.0,null);
												Transition outTransition=createTransition(finalState,outTransFlag); 
												outTransition.setNotation(null);//�ձ�
												outTransition.setNote(null);//������ִ��
												tmc.transitionsAddtransition(outTransition);
												currentState=tempState;
											}
												
										}
									}
									break;
								}
							}
						}
					}// ���ڵ�ǰ���Ƭ����
				}
			}
		}
		

		
		List list=new ArrayList();
		list.add(finalState);
		list.add(lastMessage);
		return list;
	}

	/**
	 * 
	 * @param fragType ���Ƭ������
	 * @return 
	 */
	public List<Object> retrieveFragFlag(String fragType)
	{
		List<Object> fragFlagTypeList=new ArrayList<Object>();
		
		for(int i=0;i<StaticConfig.fragmentsTypes.length;i++)
		{
			if(fragType.equals(StaticConfig.fragmentsTypes[i]))
			{
				fragFlagTypeList.add(i);
				
				String flag=Character.toUpperCase(StaticConfig.fragmentsTypes[i].charAt(0))+
						StaticConfig.fragmentsTypes[i].substring(1,StaticConfig.fragmentsTypes[i].length());
				fragFlagTypeList.add(flag);
			}
			
		}
		return fragFlagTypeList;
	}

	//�������޸ģ����õڶ���������������ִ��˳���ϵ
	public void SoftwareMergeMarkov(List<Tmc> Tmcs,Map<String, List<InterfaceUCRelation>> UCRMap)
	{
		
		F_Tmc.setTmcType("Software_Markov");
		F_Tmc.setOwned("Software_Itself");
		F_Tmc.setNames("Software_MarkovChainModel");
		//��һ��������ǰ������------"correctiveness"
		//��һ����
		F_Tmc.setNotation(StaticConfig.initialCondition);//���ִ�е� ǰ����������������ھ���״̬����ȷ״̬
 		//�����������Ʒ�ģ��de ��ʼ״̬q0;
		State initialState=createNewState();
		initialState.setTmcID("-1");
		initialState.setName("Q0");
		initialState.setLabel("InitialSate");
		initialState.setNotation(StaticConfig.initialCondition);
		
		F_Tmc.setStart(initialState);
		F_Tmc.statesAddState(initialState);
		//�ڶ�����������ʼ������
		for(Tmc tmc:Tmcs)
		{
			//Ѱ�ҵ�һ������������Ǩ�ƽ���ʼ״̬Q0��S0����������
			if(tmc.getNotation().equals(F_Tmc.getStart().getNotation())) //�����ʼ״̬
			{
				State rearState=tmc.getStart();
				
				
				Set<String> keys=UCRMap.keySet();				
				for(String key:keys)
				{
					int flag=0;
					if(key.equals("InitialState")) //������ʼ��������ƥ�䵽������ִ��˳���ϵ
					{
						//System.out.print(key+"-------");
						flag=1;
						for(InterfaceUCRelation IUR:UCRMap.get(key))
						{
							if(tmc.getNames().equals(IUR.getEndUC().getName()))
							{
								//System.out.println("-------"+IUR.getEndUC().getName());
								IntegateMarkovChain(initialState,rearState,tmc,Tmcs,
										IUR.getUCRelProb(),UCRMap);
								
							}
						}
						
					}
					if(flag==1)
						continue;					
				}			
			}
		}
		//��Markov ������ֹ״̬��һ
		unifyFinalStates();
	}
	
	public void IntegateMarkovChain(State frontState,State rearState,Tmc rearTmc,
			List<Tmc> Tmcs,double pro,Map<String, List<InterfaceUCRelation>> UCRMap)
	{
		TransFlag transFlag=new TransFlag(null,null,null,null,null,pro,null); //��������ִ��˳���ϵ�滻����
		Transition Tr=new Transition(frontState,rearState,transFlag);
		Tr.setNotation("connectUseCase");
		Tr.setProb(pro);
		F_Tmc.transitionsAddtransition(Tr);
		
		if(rearTmc.isCombine()==false)
		{
			F_Tmc.statesAddStates(rearTmc.getStates());
			F_Tmc.endsAddStates(rearTmc.getEnds());
			F_Tmc.transitionsAddtransitions(rearTmc.getTransitions());
		}
		
		//Ѱ��tmc��β״̬�ĺ�����������е���----------------------------1.19�Ӵ˴���ʼ����
		
		if(!rearTmc.isCombine())
		{
			for(State state:rearTmc.getEnds())   //rearTmc--tempTmc
			{
				int flag=0;
				for(Tmc tempTmc:Tmcs)
				{
					if(state.getNotation().equals(tempTmc.getNotation()))
					{
						flag=1;
						double prob=searchUCRelProb(rearTmc.getNames(),tempTmc.getNames(),UCRMap);//������Ӧ�ĸ���
						//System.out.println(rearTmc.getNames()+"-"+tempTmc.getNames());
						IntegateMarkovChain(state,tempTmc.getStart(),tempTmc,Tmcs,prob,UCRMap);
						tempTmc.setCombine(true);
					}
					
				}
				if(flag==1)
				{
					if(!state.getName().equals("Q0"))//���label��notation��
					{
						state.setLabel(null);
						state.setNotation(null);
						F_Tmc.endsRemoveState(state);//ɾ��β״̬
					}
				}
		     } 
		}
	}
	//Ѱ�Ҷ�Ӧ����ִ��˳���ϵ��ִ�и���
	public double searchUCRelProb(String frontUCName,String rearUCName,Map<String, List<InterfaceUCRelation>> UCRMap)
	{
		double pro=0.0;
		for(String key:UCRMap.keySet())
		{
			
			int flag=0;
			if(key.equals(frontUCName)) //������ʼ��������ƥ�䵽������ִ��˳���ϵ
			{
				flag=1;
				for(InterfaceUCRelation IUR:UCRMap.get(key))
				{
					if(rearUCName.equals(IUR.getEndUC().getName()))
					{
						pro=IUR.getUCRelProb();
						break;
					}
				}
			}
			if(flag==1)
				break;		
		}
		//System.out.println(pro);
		return pro;
		
	}
	//��������Markovģ�ͣ�����ֹ״̬��һ
		public void unifyFinalStates()
		{
			State newState=createNewState();
			newState.setLabel("Exit");
			newState.setNotation(StaticConfig.endCondition);
			F_Tmc.statesAddState(newState);
			
			//����ִ�и���Ϊ1.0�Ŀ�Ǩ�� ��currentStateָ��newState;
			for(State state: F_Tmc.getEnds() )
			{
				if(state.getNotation().trim().equals(StaticConfig.endCondition.trim()))
				{
					currentState=state;
					TransFlag transFlag=new TransFlag(null,null,null,null,null,1.0,null);
					Transition transition=createTransition(newState,transFlag);
					transition.setNotation(null);
					transition.setProb(1.0);
					F_Tmc.transitionsAddtransition(transition);
				}
				
			}
			F_Tmc.endsAddState(newState);
		}
		
	public State createNewState()
	{
		State state=new State();
		String id=count+"";
		state.setTmcID(id);
		state.setName("S"+id);
		return state;
	}
	public Transition createTransition(State state,TransFlag transFlag)
	{
		currentState.setStopTimeConstraint(transFlag.getStateStopTimeCons());
		if(currentState.getStopTimeConstraint()!=null&&currentState.getStopTimeConstraint().length()>0)
		{
			System.out.println(currentState.getStopTimeConstraint());
			if(currentState.getLabel()!=null)
			{
				currentState.setLabel(state.getLabel()+";timeDelay");
			}
			else
			{
				currentState.setLabel("timeDelay");
			}
			
		}
		Transition transition=new Transition();
		transition.setFrom(currentState);
		transition.setTo(state);
		transition.setTransFlag(transFlag);
		return transition;
	}
	/*
	 * ��״̬�ĸ��ʹ�һ
	 */
	public void normalizeOnProbability()
	{
		for(Tmc tempTmc:tmcs)  //�ӿڶ��壬��װ��ʵ��
		{
			List<State> tempStates=tempTmc.getStates();
			List<Transition> tempTransitions=tempTmc.getTransitions();
			/*
			 *����ÿ��״̬���Ҹ�״̬��Ӧ�����г��ߣ��洢��Ȼ������һ����
			 */
			for(State tempState:tempStates)
			{
				List<Transition> temp2_Transitions=new ArrayList<Transition>();
				for(Transition tr:tempTransitions)
				{
					if(tr.getFrom().equals(tempState))
					{
						temp2_Transitions.add(tr);
					}
				}
				if(temp2_Transitions.size()>0)
				{
					double tempPro=0;
					for(Transition t:temp2_Transitions)
					{
						tempPro+=t.getTransFlag().getProb();
					}
					for(Transition t:temp2_Transitions)
					{
						t.getTransFlag().setProb(t.getTransFlag().getProb()/tempPro);
					}
				}
				
			}
		
		}
	}
	

	
	private static List<String> retrieveFragmentIds(Fragment frag,Message message)
	{
		
		List<String> fragmentIds=new ArrayList<String>();
		if(frag.getId().equals(message.getFragmentId()))
		{
			fragmentIds.add(frag.getId());
			return fragmentIds;
		}
		else 
		{
			int tag=0;
			fragmentIds.add(frag.getId());
			
			List<String> newFragmentIds=null;			
			for(Operand oper:frag.getOperands())
			{
				int inTag=0;
				if(oper.isHasFragment()==true)
				{
					for(Fragment newFrag: oper.getFragments())
					{
						newFragmentIds=retrieveFragmentIds(newFrag,message);
						if(newFragmentIds!=null)
						{
							inTag=1;
							break;
						}
					}
				}
				if(inTag==1)
				{
					tag=1;
					break;
				}
			}
			if(tag==1)
			{
				fragmentIds.addAll(newFragmentIds);
				return fragmentIds;
			}
			else return null;
		}
		
	}
	
	//��ȡ��ǰ���Ƭ���µ���Ϣ������˳��ͼ����Ϣ��������
	public List<Message> retrieveCurrentMessages(Fragment frag)
	{
		List<Message> messageList1=retrieveChaosCurrentMessages(frag);//
		
		List<Message> messageList2=new ArrayList<Message>();
		messageList2.addAll(retrieveSortedCurrentMessages(messageList1));
		return messageList2;
	}
	public List<Message> retrieveChaosCurrentMessages(Fragment frag)
	{
		List<Message> messageList1=new ArrayList<Message>(); 
		for(Operand oper:frag.getOperands())
		{
			messageList1.addAll(oper.getMessages());
			if(oper.isHasFragment()==true && oper.getFragments()!=null)
			{
				for(Fragment currentFragment:oper.getFragments())
				{
					messageList1.addAll(retrieveChaosCurrentMessages(currentFragment));
				}
			}
		}
		
		return messageList1;
	}
	public List<Message> retrieveSortedCurrentMessages(List<Message> messageList1)
	{
		List<Message> messageList2=new ArrayList<Message>();
		for(Message message1:messages)
		{
			if(message1.isTranslationed()==false)
			{
				int Tab=0;
				for(Message message2:messageList1)
				{
					if(message1.equals(message2))
					{
						Tab=1;
						break;
					}
				}
				if(Tab==1)
					messageList2.add(message1);
			}
		}
		return messageList2;
	}
	
	//��ȡ�����µ���Ϣ��Ȼ����ݵ�ǰ���Ƭ���е���Ϣ����������
	public List<Message> retrieveOperandMessages(List<Message> currentMessageList,Operand oper )
	{
		List<Message> messageList1=retrieveChaosOperandMessages(oper);
		List<Message> messageList2=retrieveSortedOperandMessages(currentMessageList,messageList1);
		return messageList2;
	}
	public List<Message> retrieveChaosOperandMessages(Operand oper)
	{
		List<Message> messageList1=new ArrayList<Message>(); 
		messageList1.addAll(oper.getMessages());
		if(oper.isHasFragment()==true && oper.getFragments()!=null)
		{
			for(Fragment currentFragment:oper.getFragments())
			{
				for(Operand newOper:currentFragment.getOperands())
				{
					messageList1.addAll(retrieveChaosOperandMessages(newOper));
				}
			}
		}
		return messageList1;
	}
	public List<Message> retrieveSortedOperandMessages(List<Message> currentMessageList, List<Message> messageList1)
	{
		List<Message> messageList2=new ArrayList<Message>();
		for(Message message1:currentMessageList)
		{
			if(message1.isTranslationed()==false)
			{
				int Tab=0;
				for(Message message2:messageList1)
				{
					if(message1.equals(message2))
					{
						Tab=1;
						break;
					}
				}
				if(Tab==1)
					messageList2.add(message1);
			}
		}
		return messageList2;
	}
	
	public void assignmentTmc(Tmc tmc1,Tmc tmc2)
	{
		tmc1.setTmcType(tmc2.getTmcType());
		tmc1.setNames(tmc2.getNames());
		tmc1.setStart(tmc2.getStart());
		tmc1.setStates(tmc2.getStates());
		tmc1.setEnds(tmc2.getEnds());
		tmc1.setTransitions(tmc2.getTransitions());
		tmc1.setNotation(tmc2.getNotation());
		tmc1.setPro(tmc2.getPro());
		tmc1.setCombine(tmc2.isCombine());
	}
	public void writeSeqMC(Tmc tmc,List<String> seqNames) throws Exception
	{
		System.out.println("tmc: " + tmc.getNames());
//		String fileName=StaticConfig.mcPathPrefix+"Seq_MarkovChainModel"+seqCount+".xml";
//		String fileName="Seq_MarkovChainModel"+tmc.getNames()+seqCount+".xml";
		String[] names = tmc.getNames().split(";");
		int length = names.length - 1;
		String seq = names[length].trim();
		String fileName=seq+".xml";
		
		seqNames.add(fileName);
		Write.writeMarkov2XML(tmc, fileName,DisplayForm.mainFrame);
		seqCount++;
	}
	public void writeUcMC(Tmc tmc,List<String> ucNames) throws Exception
	{
//		String fileName=StaticConfig.mcPathPrefix+"UC_MarkovChainModel"+ucCount+".xml";
//		String fileName="UC_MarkovChainModel"+tmc.getNames()+ucCount+".xml";
		
		String fileName=tmc.getNames()+".xml";
		ucNames.add(fileName);
		Write.writeMarkov2XML(tmc, fileName,DisplayForm.mainFrame);
		ucCount++;
	}
	public void writeSoftwareMC(Tmc tmc) throws Exception
	{
//		String fileName=StaticConfig.mcPathPrefix+"Software_MarkovChainModel"+".xml";
//		String fileName="Software_MarkovChainModel"+".xml";
		
//		String fileName=tmc.getNames()+".xml";
//		Write.writeMarkov2XML(tmc, fileName,DisplayForm.mainFrame);
	}
	public List<Tmc> getTmcs() {
		return tmcs;
	}
	public List<Tmc> getSeqTmcList() {
		return seqTmcList;
	}
	
}//������


