package app;
import java.awt.*;
import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Frame;
//import java.awt.GradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javafx.scene.chart.CategoryAxis;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.*;
import javax.swing.RowSorter;
import javax.swing.event.TableModelListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.TextAnchor;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.russianStemmer;

//import java.io.OptionHandler;
//import java.io. RevisionHandler;
//import java.io.Stemmer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Юлия
 */
public class NewJFrame extends javax.swing.JFrame {

    private String[] words = {};
    ArrayList<String> wordList = new ArrayList<String>(Arrays.asList(words));
    private TextStatistics stats = new TextStatistics(wordList);
    private List<WordStatistic> was = stats.getWordsAndAmounts();
    private JComboBox cb = new JComboBox();
    private JPanel panelJPanel = new JPanel();
    private JPanel panelJComboBox = new JPanel();
    private Plot pl = new Plot();
    private JFreeChart chart = null;
    private char[] chars = {};

    public XYDataset createDataset(ArrayList<Double> frequency, ArrayList<Integer> step) { //double[][] frequency

        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries c2
                = new XYSeries("с2");
        //ArrayList<Double> frequencyList = new ArrayList<Double>(frequency);
        //ArrayList<Integer> stepList = new ArrayList<Integer>(step);

        /* final XYSeries c3
         = new XYSeries("с3");
         final XYSeries c4
         = new XYSeries("с4");*/
        for (int i = 0; i < 200; i++) {
            c2.add(step.get(i), frequency.get(i));

            //System.out.println(frequency.get(i));
            // c3.add(frequency[i], frequency[i]);
            //c4.add(frequency[i], frequency[i]);
        }
        dataset.addSeries(c2);
        // dataset.addSeries(c3);
        // dataset.addSeries(c4);
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

    private void fillInComboBox() {
        cb.removeAllItems();
        for (int i = 0; i < was.size(); i++) {
            cb.addItem(was.get(i).getWord());
        }

        cb.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbItemStateChanged(evt);
            }
        });
    }

    private void showElements(List<WordStatistic> was, ArrayList<Integer> step, int textLength) {

        chart = createPlot(was, step, cb.getSelectedItem(), textLength);
        plotDesign(chart);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(false);

        pl.setVisible(true);

        panelJPanel.removeAll();
        panelJComboBox.removeAll();
        /*panelJPanel.revalidate();
         panelJComboBox.revalidate();*/
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        JLabel label = new JLabel();
        label.setText("Выберите символ ");
        panelJPanel.add(chartPanel);
        panelJComboBox.add(label);
        panelJComboBox.add(cb);
        JPanel panelWindow = new JPanel();
        panelWindow.add(panelJPanel);
        panelWindow.add(panelJComboBox);
        pl.setContentPane(panelWindow);
        //pl.setContentPane(panelJComboBox);

    }

    private void plotDesign(JFreeChart chart) {
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesPaint(0, Color.black);
        plot.setRenderer(renderer);

    }

    private JFreeChart createPlot(List<WordStatistic> was, ArrayList<Integer> step, Object item, int textLength) {

        for (int i = 0; i < was.size(); i++) {
            if (was.get(i).getWord().equals(item)) {
                chart = ChartFactory.createXYLineChart("", "Количество символов в тексте", "Частота появления символа",
                        createDataset(was.get(i).partFrequency, step), PlotOrientation.VERTICAL, false, true, false);
                chart.setBackgroundPaint(Color.white);
                //Создаем аннотацию указывая ее текст и координаты в единицах координатной системы самого графика
                XYTextAnnotation c2 = new XYTextAnnotation("СИМВОЛ '" + item.toString().toUpperCase() + "'", textLength - (textLength / 4), was.get(i).getPartFrequency().get(150) - 0.001);
                //Присваивем аннотации якорь; т.е. указываем в какую сторону от указанных координат будет простираться текст
                c2.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
                //Наносим аннотацию на график
                chart.getXYPlot().addAnnotation(c2);
            }
        }
        return chart;
    }

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        mInstance = this;
        initComponents();
        NewJFrame obj = NewJFrame.getInstance();
        obj.setDefaultCloseOperation(obj.EXIT_ON_CLOSE);

    }

    public static NewJFrame getInstance() {
        return mInstance;
    }

    static NewJFrame mInstance;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabel1.setText("Считать данные из файла");

        jButton1.setText("Открыть");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Расчет частоты:");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("по основам слов");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("по словосочетаниям. Количество слов");

        jButton2.setText("Вычислить");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title3"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("Очистить");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("N-граммы. Количество символов");

        jButton4.setText("График");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Гистограмма");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jSpinner1.setToolTipText("");
        jSpinner1.setValue(2);

        jSpinner2.setValue(1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton5))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(jButton1))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jRadioButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jRadioButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    File file = new File("");
    StringBuilder sb = new StringBuilder();
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int returnVal = jFileChooser1.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser1.getSelectedFile();
            sb = new StringBuilder();
            try {
                //Объект для чтения файла в буфер
                FileInputStream stream = new FileInputStream(file.getAbsoluteFile());
                InputStreamReader reader = new InputStreamReader(stream, "Cp1251");
                BufferedReader in = new BufferedReader(reader);
                try {
                    //В цикле построчно считываем файл
                    String s;
                    while ((s = in.readLine()) != null) {
                        sb.append(s);
                        //sb.append("\n");
                    }
                } finally {
                    //Также не забываем закрыть файл
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!file.exists()) {
            JOptionPane.showMessageDialog(jButton1, "Вы не выбрали файл!");
            return;
        } else {
            if (jRadioButton1.isSelected()) {
                //основы слова
                //String regexp = "\\s+\\-\\s+[,;:.!?\\s]+";
                String regexp = "^[^а-я\\,;_\\-:()–«»\".!?\\s\\d]|[\\,;\\-:()–«»\".!?\\s\\d]+";

                words = sb.toString().toLowerCase().trim().split(regexp);
                russianStemmer stemmer = new russianStemmer();
                for (int i = 0; i < words.length; i++) {
                    stemmer.setCurrent(words[i]);
                    if (stemmer.stem()) {
                        words[i] = stemmer.getCurrent();
                    }
                }
                wordList.clear();
                was.clear();
                wordList = new ArrayList(Arrays.asList(words));

                stats = new TextStatistics(wordList);

                was = stats.getWordsAndAmounts();

                TableModel model = new WordStatisticTableModel(was);
                jTable1.setModel(model);
                RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                jTable1.setRowSorter(sorter);
                was.sort(null);

            } else if (jRadioButton2.isSelected()) {
                //словосочетание
                String regexp = "^[^а-я\\,;_\\-:()–«»\".!?\\s\\d]|[\\,;\\-:()–«»\".!?\\s\\d]+";

                words = sb.toString().toLowerCase().trim().split(regexp);
                int N = (int) jSpinner1.getValue();
                String[] phrases = new String[words.length - N];
                for (int i = 0; i < phrases.length; i++) {
                    phrases[i] = "";
                    for (int j = i; j < i + N; j++) {
                        phrases[i] = phrases[i].concat(" ").concat(words[j]);
                    }
                    phrases[i] = phrases[i].substring(1);
                }
                wordList.clear();
                wordList = new ArrayList(Arrays.asList(phrases));

                was.clear();
                stats = new TextStatistics(wordList);
                was = stats.getWordsAndAmounts();
                //сортировка таблицы
                TableModel model = new WordStatisticTableModel(was);
                jTable1.setModel(model);
                RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                jTable1.setRowSorter(sorter);
                was.sort(null);

            } else if (jRadioButton3.isSelected()) {
                //N-граммы
                chars = sb.toString().toLowerCase().toCharArray();
                int N = (int) jSpinner2.getValue();
                String[] ngramms = new String[sb.length() - N];
                for (int i = 0; i < sb.length() - N; i++) {
                    ngramms[i] = sb.toString().substring(i, i + N);
                }
                wordList = new ArrayList(Arrays.asList(ngramms));
                stats = new TextStatistics(wordList);
                was = stats.getWordsAndAmounts();

                TableModel model = new WordStatisticTableModel(was);
                jTable1.setModel(model);
                RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                jTable1.setRowSorter(sorter);
                was.sort(null);
                /*
                 int step = bigramms.length / 200;
                 int minN = 0;
                 int[] amount = {0, 0, 0, 0};
                 double[][] frequency = new double[5][201];
                 for (int i = 0; i < 4; i++) {
                 frequency[i][0] = 0;
                 }
                 for (int i = 1; i < 201; i++) {
                 frequency[4][i] = step;
                 for (int j = minN; j < step; j++) {
                 if (bigramms[j].equals("ст")) {
                 amount[0]++;
                 } else if (bigramms[j].equals("но")) {
                 amount[1]++;
                 } else if (trigramms[j].equals("бан")) {
                 amount[2]++;
                 } else if (trigramms[j].equals("аци")) {
                 amount[3]++;
                 }
                 }
                 frequency[0][i] = amount[0] / (double) step;
                 frequency[1][i] = amount[1] / (double) step;
                 frequency[2][i] = amount[2] / (double) step;
                 frequency[3][i] = amount[3] / (double) step;
                 minN = step;
                 step += bigramms.length / 200;
                 }

                 //гистограмма
                 /*final String series1 = "First";
                 final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                 was.sort(null);
                 for (int i = 0; i < 100; i++) {
                 dataset.addValue(was.get(i).getFrequency(), series1, Integer.toString(i));
                 }
                 final JFreeChart chart = ChartFactory.createBarChart(
                 "", // chart title
                 "Биграмма", // domain axis label
                 "Частота появления биграммы", // range axis label
                 dataset, // data
                 PlotOrientation.VERTICAL, // orientation
                 false, // include legend
                 true, // tooltips?
                 false // URLs?
                 );
                 final CategoryPlot plot = chart.getCategoryPlot();
                 final BarRenderer renderer = (BarRenderer) plot.getRenderer();
                 renderer.setDrawBarOutline(false);
                 renderer.setItemMargin(0.10);
           
                 renderer.setShadowVisible(false);
                 //renderer.setMaximumBarWidth(0.10);
                 final GradientPaint gp0 = new GradientPaint(
                 0.0f, 0.0f, Color.black,
                 0.0f, 0.0f, Color.black
                 );
                 renderer.setSeriesPaint(0, gp0);
                 plot.setBackgroundPaint(Color.white);
                 renderer.setItemLabelsVisible(false);
                 /*final ItemLabelPosition p = new ItemLabelPosition(
                 ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT,
                 TextAnchor.CENTER_RIGHT, -Math.PI / 2.0
                 );*/
                //renderer.setPositiveItemLabelPosition(p);
                /*final ItemLabelPosition p2 = new ItemLabelPosition(
                 ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT,
                 TextAnchor.CENTER_LEFT, -Math.PI / 2.0
                 );
                 renderer.setPositiveItemLabelPositionFallback(p2);
                 final CategoryAxis domainAxis = plot.getDomainAxis();
                 domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                 //plot.setDomainGridlinePaint(Color.black);
                 plot.setRangeGridlinePaint(Color.black);
                 plot.setRenderer(renderer);
                 final ChartPanel chartPanel = new ChartPanel(chart);
                 chartPanel.setPreferredSize(new java.awt.Dimension(400, 400));
                 setContentPane(chartPanel);*/
                //график
                /*ChartPanel chartPanel1;
                 JFreeChart chart
                 = ChartFactory.createXYLineChart("", "Количество символов в тексте", "Частота появления символа",
                 createDatasetNgramm(frequency), PlotOrientation.VERTICAL, false, true, false);
                 chart.setBackgroundPaint(Color.white);
                 //Создаем аннотацию указывая ее текст и координаты в единицах координатной системы самого графика
                 XYTextAnnotation c2 = new XYTextAnnotation("БИГРАММА 'СТ'", 25000, 0.019);

                 //Присваивем аннотации якорь; т.е. указываем в какую сторону от указанных координат будет простираться текст
                 c2.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);

                 //Наносим аннотацию на график
                 chart.getXYPlot().addAnnotation(c2);
                 XYTextAnnotation c3 = new XYTextAnnotation("БИГРАММА 'НО'", 25000, 0.013);
                 c3.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
                 chart.getXYPlot().addAnnotation(c3);
                 XYTextAnnotation c4 = new XYTextAnnotation("ТРИГРАММА 'БАН'", 25000, 0.0026);
                 c4.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
                 chart.getXYPlot().addAnnotation(c4);
                 XYTextAnnotation c5 = new XYTextAnnotation("ТРИГРАММА 'АЦИ'", 30000, 0.0085);
                 c5.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
                 chart.getXYPlot().addAnnotation(c5);
                 final XYPlot plot = chart.getXYPlot();
                 plot.setBackgroundPaint(Color.white);
                 plot.setDomainGridlinePaint(Color.black);
                 plot.setRangeGridlinePaint(Color.black);
                 final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
                 for (int i = 0; i < 4; i++) {
                 renderer.setSeriesShapesVisible(i, false);
                 renderer.setSeriesPaint(i, Color.black);
                 }
                 plot.setRenderer(renderer);
                 final ChartPanel chartPanel = new ChartPanel(chart);
                 chartPanel.setPreferredSize(new java.awt.Dimension(400, 400));
                 setContentPane(chartPanel);*/
            }
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTable1.setModel(new DefaultTableModel());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        final String series1 = "Символ";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        was.sort(null);
        for (int i = 0; i < 40; i++) {
            dataset.addValue(was.get(i).getFrequency(), series1, was.get(i).getWord());
        }
        final JFreeChart chart = ChartFactory.createBarChart(
                "", // chart title
                "Символ", // domain axis label
                "Частота появления символа", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                false, // include legend
                true, // tooltips?
                false // URLs?
        );
        final CategoryPlot plot = chart.getCategoryPlot();
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.10);
        renderer.setShadowVisible(false);
        final GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, Color.black,
                0.0f, 0.0f, Color.black
        );
        renderer.setSeriesPaint(0, gp0);
        plot.setBackgroundPaint(Color.white);
        renderer.setItemLabelsVisible(false);
        final ItemLabelPosition p = new ItemLabelPosition(
                ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT,
                TextAnchor.CENTER_RIGHT, -Math.PI / 2.0
        );
        //renderer.setPositiveItemLabelPosition(p);
        final ItemLabelPosition p2 = new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT,
                TextAnchor.CENTER_LEFT, 45
        );
        renderer.setPositiveItemLabelPositionFallback(p2);
              //   final CategoryAxis domainAxis = plot.getDomainAxis();
        //domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        //plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.setRenderer(renderer);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(650, 450));
        panelJPanel.removeAll();
        panelJComboBox.removeAll();
        pl.setVisible(true);
        panelJPanel.add(chartPanel);
        pl.setContentPane(panelJPanel);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
            ArrayList<Integer> step = createStep(words.length);
            fillInComboBox();
            showElements(was, step, words.length);
        } else if (jRadioButton3.isSelected()) {
            ArrayList<Integer> step = createStep(chars.length);
            fillInComboBox();
            showElements(was, step, chars.length);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cbItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
        if (jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
            ArrayList<Integer> step = createStep(words.length);
            chart = createPlot(was, step, cb.getSelectedItem(), words.length);
        } else if (jRadioButton3.isSelected()) {
            ArrayList<Integer> step = createStep(chars.length);
            chart = createPlot(was, step, cb.getSelectedItem(), chars.length);
        }
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        plotDesign(chart);
        /*chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
         jPanel.add(chartPanel);
         pl.remove(getContentPane());
        
         pl.setContentPane(jPanel);*/

        chartPanel.setSize(panelJPanel.getSize());
        panelJPanel.removeAll();
        panelJPanel.revalidate();
        panelJPanel.add(chartPanel);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
