/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.GradientPaint;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Юлия
 */
public class Histogram {
    
    //NewJFrame formAnalazer;
    
    public Histogram(List<WordStatistic> was){
        createHistogram(was);
        //this.formAnalazer = form;
    }
    
    public void createHistogram(List<WordStatistic> was) {
        final String series1 = "Лексическая единица";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        was.sort(null);
        for (int i = 0; i < 40; i++) {
            dataset.addValue(was.get(i).getFrequency(), series1, was.get(i).getWord());
        }
        final JFreeChart chart = ChartFactory.createBarChart(
                "Гистограмма распределения частоты появления лексических единиц в тексте", 
                "Лексическая единица",
                "Частота появления лексической единицы", 
                dataset, 
                PlotOrientation.VERTICAL, 
                false, 
                true, 
                false 
        );
        histogramDesign(chart);
    }

    public void histogramDesign(JFreeChart chart) {
        final CategoryPlot plot = chart.getCategoryPlot();
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.10);
        renderer.setShadowVisible(false);
        
        final GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, Color.black,
                0.0f, 0.0f, Color.black
        );
        
        final org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(1)
        );
        
        renderer.setSeriesPaint(0, gp0);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.black);
        plot.setRenderer(renderer);
        
        final ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(650, 500));
        
        JPanel panelJPanel = new JPanel();
        panelJPanel.removeAll();
        //panelJComboBox.removeAll();
        PlotWindow hist = new PlotWindow();
        hist.setVisible(true);
       // HistogtamWindow f = new HistogtamWindow();
       // HistogtamWindow f2 = f.getInstance();
       // f2.setVisible(true);
       // f2.getHistogramPanel().add(chartPanel);
        panelJPanel.add(chartPanel);
        hist.setContentPane(panelJPanel);
    }
}
