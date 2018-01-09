package com.horstmann.violet.application.gui.util.yangjie;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.horstmann.violet.application.gui.DisplayForm;


public class BestAssign {
	int actualTcNumber = 0;

	public void assign(Markov markov, Element root) {

		List<Route> routeList = markov.getRouteList();
		RandomCase randomCase = new RandomCase();
		for (Route route : routeList) {
			// �������ر�Сֻ������0���Ĳ���·�����ٷ���һ������

			actualTcNumber += (route.getNumber() == 0 ? 1 : route.getNumber());
			List<Transition> transitionList = route.getTransitionList();
			List<Stimulate> stimulateList = transform(transitionList, route);
			String testSequence = getTestSequence(stimulateList);
			String stimulateSequence = getStimulateSequence(stimulateList);
			TCDetail.getInstance().setTestSequence(testSequence);
			TCDetail.getInstance().setStimulateSequence(stimulateSequence);

			if (route.getNumber() == 0) {
				route.setNumber(1);
			}

			for (int i = 0; i < route.getNumber(); i++) {
//				System.out.print("����������");
				randomCase.getCase(stimulateList, root);
			}
		}
		System.out.println("\nʵ�����ɵ��ܲ�����������Ϊ��" + actualTcNumber);
		
		System.out.println("DbCoverage: " + markov.getDbCoverage());
		System.out.println("Deviation:" + markov.getDeviation());
		
		DisplayForm.mainFrame.getOutputinformation().geTextArea().append("\nʵ�����ɵ��ܲ�����������Ϊ��" + actualTcNumber+  "\n");
		int length = DisplayForm.mainFrame.getOutputinformation().geTextArea().getText().length(); 
		DisplayForm.mainFrame.getOutputinformation().geTextArea().setCaretPosition(length);

		printBaseTestSequence(routeList);

		// System.out.println("\n����ŷ�Ͼ������������ģ�����ƶȣ�"
		// + CalculateSimilarity.statistic_1(markov));
	}

	/**
	 * �Ӽ������ϻ�ȡ��������
	 * 
	 * @param stimulateList
	 * @return
	 */
	private String getStimulateSequence(List<Stimulate> stimulateList) {
		String stimulateSequence = "";
		for (int i = 0; i < stimulateList.size(); i++) {
			if (i != stimulateList.size() - 1) {
				stimulateSequence = stimulateSequence
						+ stimulateList.get(i).toString() + "-->>";
				// System.out.print(oneCaseExtend.get(i).toString() + "-->>");
			} else {
				stimulateSequence = stimulateSequence
						+ stimulateList.get(i).toString();
				// System.out.println(oneCaseExtend.get(i).toString());
			}
		}
		return stimulateSequence;
	}

	/**
	 * �Ӽ������ϻ�ȡ��������
	 * 
	 * @param stimulateList
	 * @return
	 */
	private String getTestSequence(List<Stimulate> stimulateList) {
		String testSequence = "";
		for (int i = 0; i < stimulateList.size(); i++) {
			if (i != stimulateList.size() - 1) {
				testSequence = testSequence + stimulateList.get(i).getName()
						+ "-->>";
				// System.out.print(oneCaseExtend.get(i).toString() + "-->>");
			} else {
				testSequence = testSequence + stimulateList.get(i).getName();
				// System.out.println(oneCaseExtend.get(i).toString());
			}
		}
		return testSequence;
	}

	/**
	 * ��ӡ���е�·����������
	 * 
	 * @param routeList
	 */
	private void printBaseTestSequence(List<Route> routeList
			 ) {
		System.out.println("\nMarkov���Ļ����������м���������" + routeList.size() + "����");

		for (Route route : routeList) {
			String testSequence = "";
			List<Transition> transitionList = route.getTransitionList();
			for (Transition transition : transitionList) {
				testSequence += transition.getName() + "����";
			}
			testSequence = testSequence.substring(0, testSequence.length() - 2);
			route.setTcSequence(testSequence);
			route.setActualPercent(route.getNumber() * 1.0 / actualTcNumber);
			System.out.println("			�������У�" + testSequence
					+ "	 ·������(ָ��-�ɿ��Բ����������ɱ���" + route.getActualPercent()
					+ "):   " + route.getRouteProbability() + "	������������"
					+ route.getNumber() + "��");
		}
	}

	/**
	 * ��Ǩ�Ƽ���ת���ɼ�������
	 * 
	 * @param transitionList
	 * @param routeNumber
	 * @return
	 */
	private List<Stimulate> transform(List<Transition> transitionList,
			Route route) {
		int routeNumber = route.getNumber();
		List<Stimulate> stimulateList = new ArrayList<Stimulate>();
		for (Transition transition : transitionList) {
			stimulateList.add(transition.getStimulate());
			transition.setAccessTimes(transition.getAccessTimes()
					+ (routeNumber == 0 ? route.getRouteProbability()
							: routeNumber));
		}
		return stimulateList;
	}
}
