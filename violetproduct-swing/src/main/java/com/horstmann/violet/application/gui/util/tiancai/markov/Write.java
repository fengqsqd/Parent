package com.horstmann.violet.application.gui.util.tiancai.markov;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Write {
	
	public Write(){}
	
	//*******��ܰ�*****************************************************************************
	public void writeMarkovToXMLofYangJie(Tmc tmc) throws IOException
	{
		//1.����document���󣬴��������ĵ�
		Document document=DocumentHelper.createDocument();
		//2.�������ڵ㲢δ���ڵ��������
		Element model=document.addElement("markov");
		model.addAttribute("version", "1.0");
		model.addAttribute("authorNmae", "Terence");
		//3.����ӽڵ�
		Element states=model.addElement("states");
		for(State tmc_state:tmc.getStates())
		{
			Element state=states.addElement("state");
			if(tmc_state.getLabel()!=null)
			{
				state.addAttribute("Label", tmc_state.getLabel());
			}
			Element stateName=state.addElement("name");
			stateName.setText(tmc_state.getName());
			Element arcs=state.addElement("arcs");
			for(Transition transition:tmc.getTransitions())
			{
				if(transition.getFrom().equals(tmc_state))
				{
					Element arc=arcs.addElement("arc");
					
					Element stimulate=arc.addElement("stimulate");
					Element stimulateName=stimulate.addElement("name");
					if(transition.getTransFlag().getName()==null)
					{
						stimulateName.setText("null");
						Element parameters=stimulate.addElement("parameters");
						Element constraints=parameters.addElement("constraints");
						//���û��Լ��
						//��д����
						//д��Լ������
						constraints.setText("");
					}
					else
					{
						stimulateName.setText(transition.getTransFlag().getName());
						Element parameters=stimulate.addElement("parameters");
						for(int i=0;i<3;i++)
						{
							Element parameter=parameters.addElement("parameter");
							Element paramName=parameter.addElement("paramName");
							paramName.setText("������");
							Element paramType=parameter.addElement("paramType");
							paramType.setText("��������");
							Element domain=parameter.addElement("domain");
							domain.addAttribute("type", "serial");
							domain.setText("��");
							Element constraints=parameters.addElement("constraints");
							for(int j=0;j<2;j++)
							{
								Element constraint=constraints.addElement("constraint");
								constraint.setText("Լ������");
							}
						}
					}
					//�Ӽ�������
					
					//��to�ڵ�
					Element to=arc.addElement("to");
					to.setText(transition.getTo().getName());
					Element probability=arc.addElement("probability");
					probability.setText(transition.getTransFlag().getProb()+"");
					
				}
			}
			
		}
		
		OutputFormat format=OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		File file=new File("MarkovModelOfYangJie.xml");
		XMLWriter writer;
		try {
			writer=new XMLWriter(new FileOutputStream(file),format);
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeMarkov2XML(Tmc tmc ,String fileName)
	{

		//1.����document���󣬴��������ĵ�
		Document document=DocumentHelper.createDocument();
		//2.�������ڵ㲢δ���ڵ��������
		Element model=document.addElement("Model");
		model.addAttribute("version", "1.0");
		model.addAttribute("xmi:type", "uml:Model");
		model.addAttribute("name", "Markov_Model");
		model.addAttribute("visibility", "public");
		model.addAttribute("authorNmae", "Terence");
		//3.����ӽڵ�
		Element type=model.addElement("ModelType");
		type.setText(tmc.getTmcType());
		Element owned=model.addElement("Owned");
		owned.setText(tmc.getOwned());
		Element name=model.addElement("name");
		name.setText(tmc.getNames());
		
		for(State tmc_state:tmc.getStates())
		{
			Element state=model.addElement("state");
			Element stateName=state.addElement("name");
			if(tmc_state.getLabel()!=null&&tmc_state.getLabel().contains("Initial"))
			{
				state.addAttribute("label", "initial");
				stateName.setText(tmc_state.getName());
			}
			else
				if(tmc_state.getLabel()!=null&&tmc_state.getLabel().contains("Exit"))
				{
					System.out.println(tmc_state.getName()+"%%%%%"+tmc_state.getLabel());
					state.addAttribute("label", "final");
					stateName.setText("Exit");
				}
			else if(tmc_state.getLabel()!=null&&tmc_state.getLabel().contains("timeDelay"))
				{
					state.addAttribute("label", "timeDelay");
					stateName.setText(tmc_state.getName());
					Element time=state.addElement("time");
					time.setText("lowTime,highTime");
				}
				else
				{
					stateName.setText(tmc_state.getName());
				}
			
			/*
			 * �жϸýڵ�ĳ�Ǩ���Ƿ��Ǿ���ʱ��Լ����Ǩ��
			 * yes����Ҫ���arc,�������1.0��Ǩ�ƣ����ÿ��innerArc;
			 * no����Ӹ���arc���ɣ�
			 */
			
			//���    ��Ǩ�ƾ���ʱ��Լ��
			for(Transition transition:tmc.getTransitions())
			{
				if(transition.getFrom().equals(tmc_state))
				{
					Element arc=state.addElement("arc");
					if(transition.getLabel()!=null&&transition.getLabel().contains("time"))
					{
						arc.addAttribute("label", "time");
					}
					else
					{
						arc.addAttribute("label", "prob");
					}
					Element arcName=arc.addElement("name");
					if(transition.getTransFlag().getName()!=null)
					{
						arcName.setText(transition.getFrom().getName()+
								"_"+transition.getTo().getName()+
								"_"+transition.getTransFlag().getName());
					}
					else
					{
						arcName.setText(transition.getFrom().getName()+
								"_"+transition.getTo().getName());
					}
					if(transition.getTransFlag().getAssignValue()!=null&&!"".equals(transition.getTransFlag().getAssignValue().trim()))
					{
						Element assignValue=arc.addElement("assignValue");
						assignValue.setText(transition.getTransFlag().getAssignValue());
					}
					if(transition.getTransFlag().getAssignType()!=null&&!"".equals(transition.getTransFlag().getAssignType().trim()))
					{
						Element assignType=arc.addElement("assignType");
						assignType.setText(transition.getTransFlag().getAssignType());
					}
					if(transition.getLabel()!=null&&transition.getLabel().contains("time"))
					{
						Element time=arc.addElement("time");
						time.addAttribute("key", "time");
						time.setText("lowTime,highTime");
					}
					else//������ʱ��Լ��
					{
						Element prob=arc.addElement("prob");
						prob.setText(transition.getTransFlag().getProb()+"");
						
						if(transition.getTransFlag().getName()!=null)
						{
							//����Ҫ���������ĸ�����Ȼ����������Ϣ	
							if(transition.getTransFlag().getStimulate()!=null
									&&transition.getTransFlag().getStimulate().getParameterNameList().size()!=0)
							{
								Element stimulate=arc.addElement("stimulate");
								
								Stimulate tempStimulate=transition.getTransFlag().getStimulate();
								if(tempStimulate.getParameterNameList()!=null)
								{
									for(int i=0;i<tempStimulate.getParameterNameList().size();i++)
									{
										Element parameter=stimulate.addElement("parameter");
										Element paramName=parameter.addElement("paramName");
										paramName.setText(tempStimulate.getParameterNameList().get(i));
										Element paramType=parameter.addElement("paramType");
										paramType.setText(tempStimulate.getParameterTypeList().get(i));
										if(tempStimulate.getDomains()!=null)
										{
											for(String domainStr:tempStimulate.getDomains())
											{
												if(domainStr.contains(tempStimulate.getParameterNameList().get(i)))
												{
													Element domain=parameter.addElement("domain");
													domain.addAttribute("type", "serial");
													domain.setText(domainStr);
												}
											}
										}
										
									}
									
								}
								//��α�������Լ�����ʽ�ĸ���������Լ�����ʽ��Ϣ��
								if(tempStimulate.getConstraintExpresstions()!=null)
								{
									for(Iterator<String> it=tempStimulate.getConstraintExpresstions().iterator();it.hasNext();)
									{
										String str=it.next();
										Element constraint=stimulate.addElement("constraint");
										constraint.setText(str);
									}
									
								}
								
							}
							
							
							
						}//���򲻴��ڼ���
					}
					
					Element to=arc.addElement("to");
					if(transition.getTo().getLabel()!=null&&transition.getTo().getLabel().contains("Exit"))
					{
						to.setText("Exit");
					}
					else
					{
						to.setText(transition.getTo().getName());
					}
				}
			}
		}
		OutputFormat format=OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		File file=new File(fileName);
		XMLWriter writer;
		try {
			writer=new XMLWriter(new FileOutputStream(file),format);
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
