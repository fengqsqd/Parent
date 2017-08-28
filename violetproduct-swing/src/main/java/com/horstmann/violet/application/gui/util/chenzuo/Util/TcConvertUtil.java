package com.horstmann.violet.application.gui.util.chenzuo.Util;

import com.horstmann.violet.application.gui.util.chenzuo.Bean.*;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * �������� ����������
 *
 * @author geek
 */
public class TcConvertUtil {

	/**
	 * ���ַ������� ����ƥ�䣬��ȡ���
	 * 
	 * @param current
	 *            ƥ�䴮
	 * @param regEx
	 *            ������ʽ
	 * @return
	 */
	public static List<String> stringRegEx(String current, String regEx) {
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(current);
		List<String> result = new ArrayList<String>();
		while (mat.find()) {
			result.add(mat.group(1).replace("[", " ").replace("]", " ").trim());
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map testCaseStatistics(List<TestCase> testCases) {
		// ����ܵ�ͳ�ƽ��
		Map finallStatisticsResult = new HashMap();

		// �Ƚ���
		Comparator<String> comp = new Comparator<String>() {
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1) - Integer.parseInt(o2);
			}
		};
		List<Pair> hs = new ArrayList(), ts = new ArrayList();
		Map<String, List<Pair>> hb = new TreeMap(comp), ht = new TreeMap(comp);
		List<Pair> tmp, tmp2;
		for (TestCase testCase : testCases) {
			String speed = testCase.getResult().getWind_speed(), high = testCase.getResult().getTakeoff_alt(),
					battery = testCase.getResult().getBattery_remaining(), time = testCase.getResult().getTime();
			if ("0%".equals(battery)) {
				hs.add(new Pair(speed, high));
				ts.add(new Pair(speed, time));
			}

			if (!hb.containsKey(speed) || !ht.containsKey(speed)) {
				tmp = new ArrayList();
				tmp.add(new Pair(high, battery));
				hb.put(speed, tmp);

				tmp2 = new ArrayList();
				tmp2.add(new Pair(high, time));
				ht.put(speed, tmp2);
			} else {
				tmp = hb.get(speed);
				tmp.add(new Pair(high, battery));

				tmp2 = ht.get(speed);
				tmp2.add(new Pair(high, time));
			}

		}

		// 1.�߶ȡ����ٹ�ϵ
		finallStatisticsResult.put("high-speed", hs);
		// 2.���١�ʱ���ϵ
		finallStatisticsResult.put("time-speed", ts);
		// 3.�������߶ȹ�ϵ
		finallStatisticsResult.put("high-battery", hb);
		// 4.�߶ȡ�ʱ���ϵ
		finallStatisticsResult.put("high-time", ht);

		return finallStatisticsResult;

	}

	/**
	 * ���ܲ���ͳ�ƹ���
	 * 
	 * @param testCases
	 *            ��������ʵ����
	 * @return Map ���� ˵�� : 1.������������,�޷���Ӧ��ִ�г���! 2.����ִ�гɹ�!��ʱ: 3.������ֳ�����ѭ�������׳��쳣!
	 * 
	 */
	public static Map<String, Object> functionStatistics(List<TestCase> testCases) {
		// ����ܵ�ͳ�ƽ��
		Map<String, Object> finallStatisticsResult = new HashMap();
		// �ɹ���ʧ��������ID
		List<Integer> caseSuccess = new ArrayList(), caseFailed = new ArrayList();
		Map<Integer, List<Integer>> tmpMap;
		List<Map<Integer, List<Integer>>> tmpList;
		/*
		 * ��������ͳ�� Map��ʽ: Map<��������,LIST< MAP< ������,������IDͳ�� > > >
		 */
		Map<String, List<Map<Integer, List<Integer>>>> failedStatistics = new HashMap();

		for (TestCase testCase : testCases) {
			String tmpStr = testCase.getResult().getResultDetail();
			int id = Integer.parseInt(testCase.getTestCaseID());
			String keyTmp = null;
			// �������� ��¼��ͳ��
			if (tmpStr.contains("������������") || tmpStr.contains("������ֳ�����ѭ�������׳��쳣")) {

				// �����¼
				caseFailed.add(id);

				// ����ͳ��
				if (tmpStr.contains("������������")) {
					keyTmp = "������������";
				} else {
					keyTmp = "������ֳ�����ѭ�������׳��쳣";
				}
				if (!failedStatistics.containsKey(keyTmp)) {
					tmpList = new ArrayList();
					failedStatistics.put(keyTmp, tmpList);
				}
				tmpList = failedStatistics.get(keyTmp);
				// �����Ӧ��������������ļ���ID����װ��List
				tmpMap = new HashMap();
				List<Integer> value = new ArrayList();
				for (myProcess m : testCase.getProcessList()) {
					if (!m.isProcessExec())
						value.add(m.getProcessID());
				}
				tmpMap.put(id, value);
				tmpList.add(tmpMap);

			} else {
				// �ɹ�������¼
				caseSuccess.add(id);
			}
		}
		// �ɹ�����
		finallStatisticsResult.put("caseSuccess", caseSuccess);
		// ʧ������
		finallStatisticsResult.put("caseFailed", caseFailed);
		// �������ͳ��
		finallStatisticsResult.put("failedStatistics", failedStatistics);
		return finallStatisticsResult;
	}

	/**
	 * ʱ����Թ���
	 * 
	 * @param testCases
	 * @return
	 */
	public static Time timeStatistics(String detail) {

		// 1.��ȡ���ز��Խ��
		String result = detail;

		// 2.����ʽ ��ȡ�������ֵ���ֵ
		String[] tmp = result.split("\\|");

		Time time = new Time();
		// 2.1 ��ȡԭʼʱ�䲻��ʽ
		time.setOriginal(tmp[0]);
		if (tmp.length > 2) {
			// 2.2 ��ȡ�����б�
			time.setError(tmp[2]);
		}
		// 2.3 ��װӳ���
		time.setMapping(tmp[1]);

		// 2.4��װ���
		time.setShowMap();
		return time;
	}

	/**
	 * �����������ַ���ת���ɼ�������ʵ��
	 * 
	 * @param processList
	 *            ���������ַ���
	 * @return
	 */
	public static List<myProcess> string2ProcessList(String processList) {
		List<myProcess> list = new ArrayList<myProcess>();
		// 1.���ָ������������ַ���
		String[] tmp = processList.split("\\)");
		// 2.��ÿ�������ڵ���д���
		for (String process : tmp) {
			// 2.1.�ַ�����ʽ����
			process += ")";
			process = process.trim();
			// 2.2. �����ڵ�����
			myProcess my = new myProcess();
			// ���� �Ƿ�ɹ�ִ�� # ��ʾ�ɹ���& ��ʾʧ��
			if (process.contains("#")) {
				my.setProcessExec(true);
			} else {
				my.setProcessExec(false);
			}
			// 2.3.����ID
			my.setProcessID(Integer.parseInt(stringRegEx(process, "ProcessID:([\\s|\\S]*?)ProcessName:").get(0)));
			// 2.4.����Name
			my.setProcessName(stringRegEx(process, "ProcessName:([\\s|\\S]*?)\\(").get(0));
			// 2.5.��������
			my.setProcessParam(stringRegEx(process, "ProcessParameter:([\\s|\\S]*?)ProcessStatus:").get(0));
			// 2.6.����״̬
			my.setProcessStatus(stringRegEx(process, "ProcessStatus:([\\s|\\S]*?)\\)").get(0));
			// System.out.println(my);
			list.add(my);
		}
		return list;
	}

	/***
	 * �����ַ��� ���������������
	 * 
	 * @param str
	 *            �ӷ�������ȡ���ַ���
	 * @return
	 */
	public static List<TestCase> buildTestCaseList(String type, String fileName) {

		List<TestCase> list = new ArrayList<>();
		String str = readFileByLines(fileName);
		// 1.��*�Ž�������������
		String[] tmp = str.split("\\*");
		// 2.��ÿ�����������ַ������н�����װ
		for (String s : tmp) {
			TestCase testCase = new TestCase();
			// 2.1.�ַ�����ʽ����
			s = s.replace("\n", "");
			// 2.2.��ȡ��������ID
			testCase.setTestCaseID(stringRegEx(s, "testcCaseID:([\\s|\\S]*?)-->processList:").get(0));
			// 2.3.���켤������
			String processList = stringRegEx(s, "processList:([\\s|\\S]*?)-->execStatus").get(0);
			testCase.setProcessList(string2ProcessList(processList));
			// 2.4.��������ִ��״̬
			/*
			 * �������� : ���� ˵�� : 1.������������,�޷���Ӧ��ִ�г����Ҳ��Ժ�ʱ:[��׼ȷ] 2.���Ժ�ʱ:
			 * 3.����ִ�й����г�����ѭ�������׳��쳣! ʱ��Լ��: [x:x] ��һ����ʾ���ü���ִ����� 1 ����2 ����
			 * �ڶ�����ʾ�Ƿ�����Լ������ʽ 1 ������ ��2 ����
			 */
			TestCaseResult testCaseResult = new TestCaseResult();
			String exeState = "", t = stringRegEx(s, "execStatus:([\\s|\\S]*?)-->resultStatus:").get(0);
			if (t.contains(":")) {
				String[] r = t.split(":");
				// ʱ��Լ��
				if (type == "Time") {
					String tStatus, eStatus;
					if ("1".equals(r[0])) {
						tStatus = "������������,�޷���Ӧ��ִ�г���";
					} else {
						tStatus = "����������ȷִ��";
					}

					if ("1".equals(r[1])) {
						eStatus = "������ʱ��Լ��";
					} else {
						eStatus = "����ʱ��Լ��";
					}
					exeState = tStatus + ",��" + eStatus;
				} else {
					// 功能、�?�能
					switch (Integer.valueOf(r[0])) {
					case 1:
						exeState = "������������,�޷���Ӧ��ִ�г����Ҳ��Ժ�ʱ:" + r[1] + "[��׼ȷ]";
						break;
					case 2:
						exeState = "���Ժ�ʱ:" + r[1];
						break;
					}
					if (type != "Function") {
						testCaseResult.setExeTime(Double.valueOf(r[1]));
						testCaseResult.setTakeoff_alt(Double.valueOf(r[2].substring("takeoff_alt".length())));
						testCaseResult
								.setBattery_remaining(Double.valueOf(r[3].substring("battery_remaining".length())));
						testCaseResult.setTime(Double.valueOf(r[4].substring("time".length())));
						testCaseResult.setWind_speed(Double.valueOf(r[5].substring("wind_speed".length())));
					}

				}
			} else {
				exeState = "����ִ�й����г�����ѭ�������׳��쳣!";
			}
			testCase.setState(exeState);
			// 2.5.�����������
			/*
			 * �������� : ���� ˵�� : 1.������������,�޷���Ӧ��ִ�г���! 2.����ִ�гɹ�!��ʱ:
			 * 3.������ֳ�����ѭ�������׳��쳣! ʱ��Լ��: 1.����ʱ,����Ĳ���ʽ 2.��ȷʱ,ԭԼ������ʽ 3.������ѭ��
			 */
			String result = "";
			// ʱ��Լ��
			if (type == "Time") {
				result = stringRegEx(s, "resultStatus:([\\s|\\S]*?)]").get(0).split(":")[1];
				if (result == "3") {
					result = "������ֳ�����ѭ�������׳��쳣!";
				} else {
					testCaseResult.setTimeLimit(timeStatistics(result));
				}

			} else {
				// ��������
				t = stringRegEx(s, "resultStatus:([\\s|\\S]*?)]").get(0);
				if (!t.contains(":")) {
					switch (Integer.valueOf(t)) {
					case 1:
						result = "������������,�޷���Ӧ��ִ�г���!";
						break;
					case 3:
						result = "������ֳ�����ѭ�������׳��쳣!";
						break;
					}
				} else {
					result = "����ִ�гɹ�!��ʱ:" + t.split(":")[1];
				}
			}

			testCaseResult.setResultDetail(result);
			testCase.setResult(testCaseResult);
			// 2.6.�����������ָ�ʽ
			testCase.setDetail(testCase.showTestCase());
			// 2.7.���������������
			list.add(testCase);
		}

		// ���ܲ��Գ�ȥ���0%
		if (type == "Performance") {
			// ������0%
			for (int i = 0; i < list.size(); i++) {
				if (i + 1 < list.size()) {
					if (list.get(i).getResult().getBattery_remaining().equals("0%")
							&& list.get(i + 1).getResult().getBattery_remaining().equals("0%")) {
						list.remove(i);
					}
				}
			}
		}
		return list;
	}

	/**
	 * �ַ���д���ļ� ���ڲ�������̫����Ҫͨ���������ļ�����ʽ�鿴
	 * 
	 * @param content
	 */
	public static void string2File(String content) {
		System.out.println(content);
		try {
			File file = new File("testFile.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(content);
			bufferWritter.close();

			System.out.println("File Save Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
	 */
	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 0;
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((tempString = reader.readLine()) != null) {
				// ��ʾ�к�
				line++;
				sb.append(tempString);
				sb.append("\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out.println(TestCaseConvertUtil.class.getResource("/").getPath());//user.dirָ���˵�ǰ��·��
		String str = readFileByLines(
				"E://��Ŀ//SVN//�������ƽ̨����//Lab603Projects//violetproduct-swing//src//main//java//com//horstmann//violet//application//gui//util//chengzuo//Util//result.txt");

		List<TestCase> list = new ArrayList();
		// buildTestCaseList("time",list, str);
		for (TestCase t : list) {
			System.out.println(t);
		}
		Map m = testCaseStatistics(list);
		Map<String, List<Pair>> hb = (Map<String, List<Pair>>) m.get("high-battery");
		for (Map.Entry<String, List<Pair>> entry : hb.entrySet()) {
			System.out.println(entry.getKey());
			for (Pair pair : entry.getValue()) {
				System.out.println("\t" + pair);
			}

		}
	}
}
