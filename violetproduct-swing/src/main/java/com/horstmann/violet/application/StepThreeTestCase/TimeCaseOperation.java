package com.horstmann.violet.application.StepThreeTestCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.horstmann.violet.application.StepTwoModelExpand.GradientProgressBarUI;
import com.horstmann.violet.application.StepTwoModelExpand.ProgressUI;
import com.horstmann.violet.application.StepTwoModelExpand.StepTwoMatrixPanel;
import com.horstmann.violet.application.gui.GBC;
import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.application.gui.util.yangjie.Calculate;
import com.horstmann.violet.application.gui.util.yangjie.CalculateDistribution;
import com.horstmann.violet.application.gui.util.yangjie.CalculateSimilarity;
import com.horstmann.violet.application.gui.util.yangjie.GenerateCases;
import com.horstmann.violet.application.gui.util.yangjie.Markov;
import com.horstmann.violet.application.gui.util.yangjie.Parameter;
import com.horstmann.violet.application.gui.util.yangjie.RandomCase;
import com.horstmann.violet.application.gui.util.yangjie.ReadMarkov2;
import com.horstmann.violet.application.gui.util.yangjie.State;
import com.horstmann.violet.application.gui.util.yangjie.Stimulate;
import com.horstmann.violet.application.gui.util.yangjie.TCDetail;
import com.horstmann.violet.application.gui.util.yangjie.Transition;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;

public class TimeCaseOperation extends JPanel {
	private JLabel topLabel;
	private JLabel label1;
	private JLabel label2;

	private JProgressBar progressBar;
	private JTextField textField;
	private JButton button;

	private JPanel gapPanel;
	private MainFrame mainFrame;

	private String testSequence;// ��������
	private String excitation; // ��������
	private String testCase;

	private List<String> paramterNameList;
	private List<String> paramterValueList;

	private ReadMarkov2 rm;
	private Markov markov;
	private GenerateCases gc;
	private Element root;
	private Document dom;
	private List<Transition> transitions;

	private int progressBarIndex;
	private Callable<Integer> maincallable;
	private FutureTask<Integer> maintask;
	private Thread mainthread;
	private Callable<Integer> callable;
	private FutureTask<Integer> task;
	private Thread thread;
	private Callable<Integer> callable1;
	private FutureTask<Integer> task1;
	private Thread thread1;

	private BigDecimal bigDecimal;
	private DecimalFormat df = new DecimalFormat();
	private String pattern = "#0.0000000";

	private int i;
	private String ModelName;
	private String TimeMarkovRoute;
	private String route;

	private String quota;

	public TimeCaseOperation(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		init();
		this.setBackground(new Color(233, 233, 233));
		this.setLayout(new GridBagLayout());
		this.add(topLabel, new GBC(0, 0, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(10, 15, 10, 0));
		this.add(progressBar, new GBC(0, 1, 4, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 15, 10, 15));
		this.add(label1, new GBC(0, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 15, 10, 5));
		this.add(textField, new GBC(1, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 0).setInsets(0, 0, 10, 5));
		this.add(gapPanel, new GBC(2, 2, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 0, 10, 5));
		this.add(button, new GBC(3, 2, 1, 1).setFill(GBC.BOTH).setWeight(0, 1).setInsets(0, 0, 10, 15));
	}

	private void init() {
		topLabel = new JLabel("�ɿ��Բ��������������");
		label1 = new JLabel("��ֹ������ֵ:");

		progressBar = new JProgressBar();

		textField = new JTextField();
		textField.setText("0.1");
		textField.setEditable(false);

		button = new JButton("��ʼ����");
		gapPanel = new JPanel();

		paramterNameList = new ArrayList<String>();
		paramterValueList = new ArrayList<String>();

		textField.setPreferredSize(new Dimension(40, 30));
		textField.setMinimumSize(new Dimension(40, 30));
		textField.setMaximumSize(new Dimension(40, 30));
		
		topLabel.setFont(new Font("����", Font.PLAIN, 16));
		label1.setFont(new Font("����", Font.PLAIN, 16));

		progressBar.setMinimumSize(new Dimension(800, 30));
		progressBar.setPreferredSize(new Dimension(800, 30));
		progressBar.setMaximumSize(new Dimension(800, 30));
		progressBar.setUI(new GradientProgressBarUI());
		progressBar.setValue(0);

		TimeMarkovRoute = mainFrame.getBathRoute() + "/ExtendMarkov/";
		listen();
	}

	private void listen() {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				initThread();
				mainthread.start();
				thread.start();
			}
		});
	}

	private void initThread() {
		maincallable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub

				progressBarIndex = 0;
				progressBar.setValue(0);
				progressBar.setValue(progressBarIndex);
				while (progressBarIndex < 60) {
					if (task.isDone()) {
						progressBarIndex++;
						progressBar.setValue(progressBarIndex);
						Thread.sleep(10);
					} else {
						progressBarIndex++;
						progressBar.setValue(progressBarIndex);
						Thread.sleep(10000);
					}
				}

				while (true) {
					if (task.isDone()) {
						thread1.start();
						break;
					}
				}
				return 1;
			}
		};
		maintask = new FutureTask<>(maincallable);
		mainthread = new Thread(maintask);

		callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				button.setEnabled(false);
				mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(false);
				mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(false);
				mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(false);
				mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(false);

				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new GridBagLayout());

				mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();
				mainFrame.getStepThreeTimeTabbedPane().getTestData().repaint();
				mainFrame.renewPanel();

				progressBarIndex = 0;

				markov = mainFrame.getTimeSeqOperation().getMarkov();
				gc = mainFrame.getTimeSeqOperation().getGc();
				root = mainFrame.getTimeSeqOperation().getRoot();
				dom = mainFrame.getTimeSeqOperation().getDom();

				try {
					topLabel.removeAll();
					topLabel.setText("�������ɿɿ��Բ�������(�ù�����Ҫ�Ͼ�ʱ��,�����ĵȴ�)....");
					Thread.sleep(100);
					RandomCase randomCase = new RandomCase();
					Calculate.getAllTransValues(markov);

					for (int i = 0; i < gc.testCasesExtend.size(); i++) {
						TCDetail.getInstance().setTestSequence(gc.abstractTS.get(i));
						String stimulateSequence = getStimulateSeq(gc.testCasesExtend.get(i));
						TCDetail.getInstance().setStimulateSequence(stimulateSequence);
						randomCase.getCase(gc.testCasesExtend.get(i), root);
					}

					OutputFormat format = OutputFormat.createPrettyPrint();
					XMLWriter writer = new XMLWriter(
							new FileOutputStream(
									mainFrame.getBathRoute() + "\\TestCase\\" + ModelName + "_Similarity#3.xml"),
							format);
					writer.write(dom);
					writer.close();

					mainFrame.renewPanel();
				} catch (Exception e) {
					// TODO: handle exception

					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

					topLabel.removeAll();
					topLabel.setText("���ɿɿ��Բ������ݳ���!");

					mainthread.stop();
					mainFrame.renewPanel();
				}
				return 1;
			}
		};

		task = new FutureTask<>(callable);
		thread = new Thread(task);

		callable1 = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				try {
					List<TCDetail> lists = DataBaseUtil.showTCDetailAll("select * from tcdetail");

					
					mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();
					CasePagePanel casePagePanel = new CasePagePanel(lists, mainFrame);
					mainFrame.getStepThreeTimeTabbedPane().getTestData().add(casePagePanel);
                    mainFrame.renewPanel();
					
					int index = 500;
					if (lists.size() < 500) {
						index = lists.size();
					}

					// ���ɲ�������
					JPanel TestDataPanel = new JPanel();
					TestDataPanel.setLayout(new GridBagLayout());
					
					casePagePanel.getCasePanel().add(new JPanel(),
							new GBC(0, index).setFill(GBC.BOTH).setWeight(1, 1));

					for (int i = 0; i < index; i++) {
						StepThreeTabelPanel testTabelPanel = new StepThreeTabelPanel(lists.get(i).getTestCase(), 2,
								mainFrame);
						casePagePanel.getCasePanel().add(testTabelPanel,
								new GBC(0, i).setFill(GBC.BOTH).setWeight(1, 0));
						casePagePanel.getCasePanel().repaint();
						mainFrame.getStepThreeTimeTabbedPane().getTestData().repaint();
						
						
						progressBar.setValue(60 + (int) (((double) (i + 1) / index) * 40));
						Thread.sleep(10);
						mainFrame.renewPanel();
					}
					casePagePanel.getPageTestField().setText("1");
					mainFrame.getStepThreeTimeTabbedPane().getTestData().removeAll();
					mainFrame.getStepThreeTimeTabbedPane().getTestData().add(casePagePanel);
					mainFrame.getStepThreeTimeTabbedPane().getTestData().updateUI();
					mainFrame.renewPanel();

					topLabel.removeAll();
					bigDecimal = new BigDecimal(markov.getDeviation());
					String ii = bigDecimal.toPlainString();
					double d = Double.valueOf(ii);
					topLabel.setText("�ɿ��Բ���������Ϣ�������,������" + lists.size() + "���ɿ��Բ������ݡ�" + " �ɿ��Բ��������⸲����:"
							+ df.format(markov.getDbCoverage()) + "  �ɿ��Կɿ��Բ����������ɱ�����ʹ��ģ��ʵ��ʹ�ø���ƽ��ƫ��:"
							+ df.format(d));
					mainFrame.getStepThreeTimeTabbedPane().setSelectedIndex(0);

					TimeTestCaseNode timeTestCaseLabel = new TimeTestCaseNode(ModelName + "_���ƶ�", mainFrame);
					quota = "�ɿ��Բ���������Ϣ�������,������" + lists.size() + "���ɿ��Բ������ݡ�" + " �ɿ��Բ��������⸲����:"
							+ df.format(markov.getDbCoverage()) + "  �ɿ��Բ����������ɱ�����ʹ��ģ��ʵ��ʹ�ø���ƽ��ƫ��:" + df.format(d);
					timeTestCaseLabel.setQuota(quota);
					timeTestCaseLabel.setCasePagePanel(casePagePanel);

					mainFrame.getStepThreeLeftButton().getTimeCaseNodePanel().insertNodeLabel(timeTestCaseLabel,
							casePagePanel, quota);
					mainFrame.getStepThreeLeftButton().getTimeNode().repaint();
					
					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

					mainFrame.renewPanel();
				} catch (Exception e) {
					// TODO: handle exception
					button.setEnabled(true);
					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getModelExpand().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeModelLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeExpandLabel().setEnabled(true);
					mainFrame.getStepThreeLeftButton().getTimeSeq().setEnabled(true);

					mainFrame.getStepThreeLeftButton().getChoosePatternLabel().setEnabled(true);

					topLabel.removeAll();
					topLabel.setText("���ɿɿ��Բ������ݳ���!");

					mainthread.stop();
					mainFrame.renewPanel();
				}
				return 1;
			}
		};
		task1 = new FutureTask<>(callable1);
		thread1 = new Thread(task1);
	}

	private static boolean isSufficient(Markov markov) {

		for (State state : markov.getStates()) {

			for (Transition outTransition : state.getOutTransitions()) {

				if (outTransition.getAccessTimes() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	private static String getStimulateSeq(List<Stimulate> oneCaseExtend) {
		String stimulateSequence = "";
		for (int i = 0; i < oneCaseExtend.size(); i++) {
			if (i != oneCaseExtend.size() - 1) {
				stimulateSequence = stimulateSequence + oneCaseExtend.get(i).toString() + "-->>";
				// System.out.print(oneCaseExtend.get(i).toString() + "-->>");
			} else {
				stimulateSequence = stimulateSequence + oneCaseExtend.get(i).toString();
				// System.out.println(oneCaseExtend.get(i).toString());
			}
		}
		return stimulateSequence;
	}

	public JLabel getTopLabel() {
		return topLabel;
	}

	public String getModelName() {
		return ModelName;
	}

	public void setModelName(String modelName) {
		ModelName = modelName;
	}

}
