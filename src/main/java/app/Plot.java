/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Юлия
 */
public class Plot {
    private ArrayList<Integer> stepList = new ArrayList<>();
    public Plot(int textLength) {
        stepList = createStep(textLength);
    }

    public XYDataset createDataset(ArrayList<Integer> step, ArrayList<Double> frequency) {

        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries c2
                = new XYSeries("с2");
        for (int i = 0; i < 200; i++) {
            c2.add(step.get(i), frequency.get(i));
        }
        dataset.addSeries(c2);
        return dataset;
    }

    private ArrayList<Integer> createStep(int textLength) {
        ArrayList<Integer> step = new ArrayList<Integer>();
        step.add(0);
        int stepPrev = 0;
        for (int i = 1; i < 200; i++) {
            step.add(stepPrev + textLength / 200);
            stepPrev += textLength / 200;
        }
        return step;
    }

    public void showElements(List<WordStatistic> was, JComboBox cb) {

        JFreeChart chart = createPlot(was, cb.getSelectedItem());
        plotDesign(chart);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(false);
        PlotWindow plot = new PlotWindow();
        plot.setVisible(true);
        JPanel panetJTitle = new JPanel();
        JPanel panelJPanel = new JPanel();
        JPanel panelJComboBox = new JPanel();
        panelJPanel.removeAll();
        panelJComboBox.removeAll();
        JLabel title = new JLabel();
        title.setText("График зависимости частоты появление лексической единицы '" + cb.getSelectedItem().toString() + "' от длины текста");
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        JLabel label = new JLabel();
        label.setText("Выберите лексическую единицу");
        panetJTitle.add(title);
        panelJPanel.add(chartPanel);
        panelJComboBox.add(label);
        panelJComboBox.add(cb);
        JPanel panelWindow = new JPanel();
        panelWindow.add(panetJTitle);
        panelWindow.add(panelJPanel);
        panelWindow.add(panelJComboBox);
        plot.setContentPane(panelWindow);

    }

    public void plotDesign(JFreeChart chart) {
         XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesPaint(0, Color.black);
        plot.setRenderer(renderer);
    }

    public JFreeChart createPlot(List<WordStatistic> was, Object item) {
        JFreeChart chart = null;
        for (int i = 0; i < was.size(); i++) {
            if (was.get(i).getWord().equals(item)) {
                chart = ChartFactory.createXYLineChart("", "Длина текста", "Частота появления лексической единицы",
                        createDataset(stepList, was.get(i).getPartFrequency()), PlotOrientation.VERTICAL, false, true, false);
                chart.setBackgroundPaint(Color.white);
            }
        }
        return chart;
    }
}
