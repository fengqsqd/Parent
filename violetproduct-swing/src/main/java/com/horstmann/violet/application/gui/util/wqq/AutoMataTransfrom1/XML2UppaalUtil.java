package com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.UppaalLocation;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.UppaalTemPlate;
import com.horstmann.violet.application.gui.util.wqq.AutoMataTransfrom2.UppaalTransition;

public class XML2UppaalUtil {
	
	private Automata automata;
	
	public XML2UppaalUtil(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			byte[] buf = new byte[is.available()];
			is.read(buf);
			this.automata = XStreamTransformUtil.toBean(Automata.class, buf);
		} catch (Exception e) {
			System.out.println("��ȡ�쳣");
		} finally {
			try{
				is.close();
			} catch (Exception e) {
				System.out.println("�ر��쳣");
			}
		}
	}
	
	public ArrayList<UppaalTemPlate> getTemplates() {
		ArrayList<UppaalTemPlate> templateList = new ArrayList<UppaalTemPlate>();
		ArrayList<String>allTimes = new ArrayList<String>();
		
		for(Template template : automata.getTemplateList()) {
			// template
			UppaalTemPlate uppaalTemPlate = new UppaalTemPlate();
			uppaalTemPlate.setName(template.getName());
			
			// locations
			setLocations(template, uppaalTemPlate, allTimes);
			// transitions
			setTransitions(template, uppaalTemPlate, allTimes);
			
			// Clocks
			setClocks(uppaalTemPlate, allTimes);
			
			templateList.add(uppaalTemPlate);
		}
		
		return templateList;
	}
	
	private void setClocks(UppaalTemPlate uppaalTemPlate, ArrayList<String> allTimes) {
		HashSet<String> hashSet = new HashSet<String>();
		ArrayList<String> Clocks = new ArrayList<String>();
		for(int i = 0; i < allTimes.size(); i++) {
			String string = "\\w+(?=[<>=])";
			Pattern pattern = Pattern.compile(string);
			Matcher matcher = pattern.matcher(allTimes.get(i));
			if(matcher.find()) {
				hashSet.add(matcher.group(0));
			}
		}
		Clocks.addAll(hashSet);
		uppaalTemPlate.setClockSet(Clocks);
	}

	private void setTransitions(Template template, UppaalTemPlate uppaalTemPlate, ArrayList<String> allTimes) {
		ArrayList<UppaalTransition> uppaalTransitions = new ArrayList<UppaalTransition>();
		
		for(Transition transition : template.getTransitionList()) {
			UppaalTransition uppaalTransition = new UppaalTransition();
			// Events : in out condition
			ArrayList<String> Events = new ArrayList<String>();
			if (!transition.getIn().equals("null")) {
				Events.add("in:" + transition.getIn());
			}
			if (!transition.getOut().equals("null")) {
				Events.add("out:" + transition.getOut());
			}
			if (!transition.getTypeAndCondition().equals("null")) {
				String conditonValue = transition.getTypeAndCondition().split("/")[1];
				String[] conditions = conditonValue.split("--");
				for (int i = 0; i < conditions.length; i++) {
					Events.add("condition:" + conditions[i]);
				} 
				
				String typeValue = transition.getTypeAndCondition().split("/")[0];
				String[] types = typeValue.substring(1, typeValue.length()-1).split("-");
				
				
			}
			uppaalTransition.setEvents(Events);
			
			// target
			uppaalTransition.setTarget(transition.getTarget());
			
			// source
			uppaalTransition.setSource(transition.getSource());
			
			// ResetClocks ʱ�Ӹ�λ
			ArrayList<String> ResetClocks = new ArrayList<String>();
			if (!transition.getRESET().equals("null")) {
				String resetValue = transition.getRESET();
				String[] resets = resetValue.split("-");
				for (int i = 0; i < resets.length; i++) {
					ResetClocks.add(resets[i]);
				}
			}
			uppaalTransition.setResetClocks(ResetClocks);
			
			// constraint ʱ��Լ��
			ArrayList<String> constraint = new ArrayList<String>();
			if (!transition.getTimeDuration().equals("null")) {
				String timeValue = transition.getTimeDuration();
				String[] times = timeValue.split(",");
				for (int i = 0; i < times.length; i++) {
					
					String time = times[i].replaceAll(" ", "");
					constraint.add(time);
					allTimes.add(time);
				}
			}
			uppaalTransition.setConstraint(constraint);
			
			// type 
			ArrayList<String> types = new ArrayList<String>();
			if (!transition.getTypeAndCondition().equals("null")) {
				String typeValue = transition.getTypeAndCondition().split("/")[0];
				String[] typeStrings = typeValue.substring(1, typeValue.length()-1).split("-");
				for (int i = 0; i < typeStrings.length; i++) {
					types.add(typeStrings[i]);
				}
			}
			uppaalTransition.setTypes(types);
			
			// typeId
			ArrayList<String> typeIds = new ArrayList<String>();
			if (!transition.getTypeId().equals("null")) {
				String typeIdValue = transition.getTypeId();
				String[] typeIdStrings = typeIdValue.split("-");
				for (int i = 0; i < typeIdStrings.length; i++) {
					typeIds.add(typeIdStrings[i]);
				}
			}
			uppaalTransition.setTypeIds(typeIds);
			// add transition
			uppaalTransitions.add(uppaalTransition);
		}
		uppaalTemPlate.setTransitions(uppaalTransitions);
		
	}

	private void setLocations(Template template, UppaalTemPlate uppaalTemPlate, ArrayList<String> allTimes) {
		ArrayList<UppaalLocation> uppaalLocations = new ArrayList<UppaalLocation>();
		
		for(Location location : template.getLocationList()) {
			UppaalLocation uppaalLocation = new UppaalLocation();
			// id
			uppaalLocation.setName(location.getId());
			
			// final
			uppaalLocation.setFinalState(location.isFinl());
			
			// ��ʼ״̬
			if (location.getId().equals("loc_id_init")) { 
				uppaalTemPlate.setInitState(uppaalLocation);
			}
			
			// invariant ʱ��Լ��
			ArrayList<String> invariant = new ArrayList<String>();
			if (!location.getTimeDuration().equals("null")) {
				String[] times = location.getTimeDuration().split(",");
				for (int i = 0; i < times.length; i++) {
					String time = times[i].replaceAll(" ", "");
					invariant.add(time);
					allTimes.add(time);
				}
			}
			uppaalLocation.setInvariant(invariant);
			
			//add locaiton
			uppaalLocations.add(uppaalLocation);
		}
		uppaalTemPlate.setLocations(uppaalLocations);
	}
	
	public Automata getAutomata() {
		return automata;
	}
	
	
}
