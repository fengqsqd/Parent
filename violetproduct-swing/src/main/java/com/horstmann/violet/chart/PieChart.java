package com.horstmann.violet.chart;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleEdge;


/**
 * 
 * @author ccw
 * @date 2014-6-11
 *       <p>
 *       ����ͼ���裺<br/>
 *       1���������ݼ���<br/>
 *       2������Chart��<br/>
 *       3:���ÿ���ݣ���ֹ������ʾ�����<br/>
 *       4:�����ӽ�����Ⱦ��<br/>
 *       5:���������ֽ�����Ⱦ<br/>
 *       6:ʹ��chartPanel����<br/>
 * 
 *       </p>
 */
public class PieChart {
	Integer[] integers;
	public PieChart(Integer[] integers) {
		this.integers = integers;
	}

	public DefaultPieDataset createDataset() {
		String[] categories = { "�ɹ�����", "ʧЧ����"};
		Object[] datas = integers;
		DefaultPieDataset dataset = ChartUtils.createDefaultPieDataset(categories, datas);
		return dataset;
	}

	public ChartPanel createChart() {
		// 2������Chart[������ͬͼ��]
		JFreeChart chart = ChartFactory.createPieChart3D("", createDataset());
		// 3:���ÿ���ݣ���ֹ������ʾ�����
		ChartUtils.setAntiAlias(chart);// �����
		// 4:�����ӽ�����Ⱦ[������ͬͼ��]
		ChartUtils.setPieRender(chart.getPlot());
		
		/**
		 * ����ע�Ͳ���
		 */
//		 ((PiePlot)chart.getPlot()).setSimpleLabels(true);//�򵥱�ǩ,����������
//		 ((PiePlot)chart.getPlot()).setLabelGenerator(null);//����ʾ����
		// ���ñ�ע�ޱ߿�
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		// ��עλ���Ҳ�
		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		// 6:ʹ��chartPanel����
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}

	public static void main(String[] args) {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 420);
		frame.setLocationRelativeTo(null);

//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				// ����ͼ��
//				ChartPanel chartPanel = new PieChart().createChart();
//				frame.getContentPane().add(chartPanel);
//				frame.setVisible(true);
//			}
//		});

	}

}
