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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.CategoryAxis;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
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

    private File file = new File("");
    private StringBuilder readFile = new StringBuilder();
    private String[] words = {};
    private ArrayList<String> wordList = new ArrayList<String>();
    private TextStatistics stats = new TextStatistics(wordList);
    private List<WordStatistic> was = stats.getWordsAndAmounts();
    private JComboBox cb = new JComboBox();
    private JPanel panetJTitle = new JPanel();
    private JPanel panelJPanel = new JPanel();
    private JPanel panelJComboBox = new JPanel();
    //private Plot pl = new Plot(readFile.length());
    private JFreeChart chart = null;
    private char[] chars = {};

    public String[] wordStat() {
        String regexp = "^[^а-я\\,;_\\-:()–«»\".!?\\s\\d]|[\\,;\\-:()–«»\".!?\\s\\d]+";
        words = readFile.toString().trim().split(regexp);
        russianStemmer stemmer = new russianStemmer();
        for (int i = 0; i < words.length; i++) {
            stemmer.setCurrent(words[i]);
            if (stemmer.stem()) {
                words[i] = stemmer.getCurrent();
            }
        }
        return words;
    }

    public String[] ngramStat() {
        chars = readFile.toString().toCharArray();
        int N = (int) jSpinner2.getValue();
        String[] ngramms = new String[readFile.length() - N];
        for (int i = 0; i < readFile.length() - N; i++) {
            ngramms[i] = readFile.toString().substring(i, i + N);
        }
        return ngramms;
    }

    public String[] phraseStat() {
        String regexp = "^[^а-я\\,;_\\-:()–«»\".!?\\s\\d]|[\\,;\\-:()–«»\".!?\\s\\d]+";
        words = readFile.toString().trim().split(regexp);
        int N = (int) jSpinner1.getValue();
        String[] phrases = new String[words.length - N];
        for (int i = 0; i < phrases.length; i++) {
            phrases[i] = "";
            for (int j = i; j < i + N; j++) {
                phrases[i] = phrases[i].concat(" ").concat(words[j]);
            }
            phrases[i] = phrases[i].substring(1);
        }
        return phrases;
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
    
    private void showTable(List<WordStatistic> was) {
        TableModel model = new WordStatisticTableModel(was);
        jTable1.setModel(model);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        jTable1.setRowSorter(sorter);
        was.sort(null);
    }

    public void readFile() {
        int returnVal = jFileChooser1.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = jFileChooser1.getSelectedFile();
            readFile = new StringBuilder();
            try {
                //Объект для чтения файла в буфер
                FileInputStream stream = new FileInputStream(file.getAbsoluteFile());
                InputStreamReader reader = new InputStreamReader(stream, "Cp1251");
                BufferedReader in = new BufferedReader(reader);
                try {
                    //В цикле построчно считываем файл
                    String line;
                    while ((line = in.readLine()) != null) {
                        readFile.append(line);
                    }
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        mInstance = this;
        initComponents();
        
        NewJFrame obj = NewJFrame.getInstance();
        obj.setDefaultCloseOperation(obj.EXIT_ON_CLOSE);
        informationLabel.setVisible(false);
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
    
    public JButton getButtonOpenFile(){
        return buttonOpenFile;
    }
    
    public JRadioButton getRadiobuttonGenNumber(){
        return radiobuttonGenNumber;
    }
    
    public JRadioButton getRadioButtonGenFrequence(){
        return radioButtonGenFrequence;
    }
    
    public JComboBox getComboBoxFrequency(){
        return comboBoxFrequency;
    }
    
    public JSpinner getSpinnerNumber(){
        return spinnerNumber;
    }
    
    public JRadioButton getRadioButtonPhrase(){
        return radioButtonPhrase;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        buttonOpenFile = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        radioButtonWord = new javax.swing.JRadioButton();
        radioButtonPhrase = new javax.swing.JRadioButton();
        buttonExecute = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonClear = new javax.swing.JButton();
        radioButtonNgram = new javax.swing.JRadioButton();
        buttonPlot = new javax.swing.JButton();
        buttonHistogram = new javax.swing.JButton();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        buttonGenCode = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboBoxFrequency = new javax.swing.JComboBox();
        informationLabel = new javax.swing.JLabel();
        spinnerNumber = new javax.swing.JSpinner();
        radioButtonGenFrequence = new javax.swing.JRadioButton();
        radiobuttonGenNumber = new javax.swing.JRadioButton();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabel1.setText("Считать данные из файла");

        buttonOpenFile.setText("Открыть");
        buttonOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOpenFileActionPerformed(evt);
            }
        });

        jLabel2.setText("Расчет частоты:");

        buttonGroup1.add(radioButtonWord);
        radioButtonWord.setSelected(true);
        radioButtonWord.setText("по основам слов");

        buttonGroup1.add(radioButtonPhrase);
        radioButtonPhrase.setText("по словосочетаниям. Количество слов");

        buttonExecute.setText("Вычислить");
        buttonExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExecuteActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "№", "Лексема", "Количество", "Частота"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(80);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        buttonClear.setText("Очистить");
        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioButtonNgram);
        radioButtonNgram.setText("N-граммы. Количество символов");

        buttonPlot.setText("График");
        buttonPlot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPlotActionPerformed(evt);
            }
        });

        buttonHistogram.setText("Гистограмма");
        buttonHistogram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHistogramActionPerformed(evt);
            }
        });

        jSpinner1.setToolTipText("");
        jSpinner1.setValue(2);

        jSpinner2.setValue(1);

        buttonGenCode.setText("Генерировать");
        buttonGenCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGenCodeActionPerformed(evt);
            }
        });

        jLabel3.setText("Сгенерировать таблицу кодов лексических единиц");

        comboBoxFrequency.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.1", "0.01", "0.007", "0.005", "0.003", "0.001", "0.0001" }));

        informationLabel.setText("jLabel5");

        buttonGroup2.add(radioButtonGenFrequence);
        radioButtonGenFrequence.setSelected(true);
        radioButtonGenFrequence.setText("Генерировать коды с частотой встречания больше");

        buttonGroup2.add(radiobuttonGenNumber);
        radiobuttonGenNumber.setText("Генерировать коды до лексемы с номером");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(radiobuttonGenNumber))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonGenCode))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(radioButtonNgram)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel2)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(buttonExecute)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buttonClear)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buttonPlot)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(buttonHistogram))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(buttonOpenFile))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(radioButtonWord)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(radioButtonPhrase)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(informationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(spinnerNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(radioButtonGenFrequence)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(comboBoxFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(buttonOpenFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioButtonWord)
                    .addComponent(radioButtonPhrase)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioButtonNgram)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonExecute)
                    .addComponent(buttonClear)
                    .addComponent(buttonPlot)
                    .addComponent(buttonHistogram))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(informationLabel)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radioButtonGenFrequence))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radiobuttonGenNumber))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(buttonGenCode))
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOpenFileActionPerformed
        readFile();
    }//GEN-LAST:event_buttonOpenFileActionPerformed

    private void buttonExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExecuteActionPerformed
        if (!file.exists()) {
            JOptionPane.showMessageDialog(buttonOpenFile, "Вы не выбрали файл!");
            return;
        } else {
            informationLabel.setVisible(true);
            informationLabel.setText("Количество символов в файле " + readFile.length());
            if (radioButtonWord.isSelected()) {
                //основы слова
                words = wordStat();

                wordList = new ArrayList(Arrays.asList(words));
                stats = new TextStatistics(wordList);
                was = stats.getWordsAndAmounts();
                showTable(was);

            } else if (radioButtonPhrase.isSelected()) {
                //словосочетание
                String[] phrases = phraseStat();

                wordList = new ArrayList(Arrays.asList(phrases));
                stats = new TextStatistics(wordList);
                was = stats.getWordsAndAmounts();
                //сортировка таблицы
                showTable(was);

            } else if (radioButtonNgram.isSelected()) {
                //N-граммы
                String[] ngramms = ngramStat();

                wordList = new ArrayList(Arrays.asList(ngramms));
                stats = new TextStatistics(wordList);
                was = stats.getWordsAndAmounts();
                showTable(was);
            }
        }

    }//GEN-LAST:event_buttonExecuteActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
        // TODO add your handling code here:
        jTable1.setModel(new DefaultTableModel());
    }//GEN-LAST:event_buttonClearActionPerformed

    private void buttonHistogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHistogramActionPerformed
        // TODO add your handling code here:
        //прорисовка гистограммы
        if (was.isEmpty()) {
            JOptionPane.showMessageDialog(buttonOpenFile, "Нет данных для построения гистограммы!");
            return;
        }
        Histogram hist = new Histogram(was);
        
        //createHistogram();
    }//GEN-LAST:event_buttonHistogramActionPerformed

    private void buttonPlotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPlotActionPerformed
        // TODO add your handling code here:
        if (was.isEmpty()) {
            JOptionPane.showMessageDialog(buttonOpenFile, "Нет данных для построения графика!");
            return;
        }
        fillInComboBox();
        Plot pl = new Plot(readFile.length());
        pl.showElements(was, cb);
       /* if (jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
            ArrayList<Integer> step = createStep(readFile.length());
            fillInComboBox();
            showElements(was, step);
        } else if (jRadioButton3.isSelected()) {
            ArrayList<Integer> step = createStep(readFile.length());
            fillInComboBox();
            showElements(was, step);
        }*/
    }//GEN-LAST:event_buttonPlotActionPerformed



    private void buttonGenCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGenCodeActionPerformed
        File file = new File("../CoderDecoder/bankcode.txt");
        Map<String, Short> dictionary = new HashMap<String, Short>();
        Short code = 0;
        GenerateTable genTable = new GenerateTable(was, this);
        if (file.exists() && file.length() != 0) {
            dictionary = genTable.readTable(file);
        } else {
            dictionary = genTable.startTable();
        }
        Short max = 0;
        for (Map.Entry<String, Short> entry : dictionary.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        code = max;
        dictionary = genTable.generateCode(code, dictionary);
        //записываем в файл
        genTable.writeTableInFile(dictionary);
    }//GEN-LAST:event_buttonGenCodeActionPerformed

    private void cbItemStateChanged(java.awt.event.ItemEvent evt) {
        // TODO add your handling code here:
        //fillInComboBox();
        //Plot pl = new Plot(was, readFile.length(), cb);
        Plot pl = new Plot(readFile.length());
        pl.showElements(was, cb);
        //pl.createPlot(was, cb.getSelectedItem());
        //pl.plotDesign();
        /*if (jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
            ArrayList<Integer> step = createStep(readFile.length());
            chart = createPlot(was, step, cb.getSelectedItem());
        } else if (jRadioButton3.isSelected()) {
            ArrayList<Integer> step = createStep(readFile.length());
            chart = createPlot(was, step, cb.getSelectedItem());
        }
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        plotDesign(chart);
        JLabel title = new JLabel();
        title.setText("График зависимости частоты появление лексической единицы '" + cb.getSelectedItem().toString() + "' от длины текста");

        chartPanel.setSize(panelJPanel.getSize());
        panelJPanel.removeAll();
        panelJPanel.revalidate();
        panetJTitle.removeAll();
        panetJTitle.revalidate();
        panetJTitle.add(title);
        panelJPanel.add(chartPanel);*/
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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClear;
    private javax.swing.JButton buttonExecute;
    private javax.swing.JButton buttonGenCode;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton buttonHistogram;
    private javax.swing.JButton buttonOpenFile;
    private javax.swing.JButton buttonPlot;
    private javax.swing.JComboBox comboBoxFrequency;
    private javax.swing.JLabel informationLabel;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton radioButtonGenFrequence;
    private javax.swing.JRadioButton radioButtonNgram;
    private javax.swing.JRadioButton radioButtonPhrase;
    private javax.swing.JRadioButton radioButtonWord;
    private javax.swing.JRadioButton radiobuttonGenNumber;
    private javax.swing.JSpinner spinnerNumber;
    // End of variables declaration//GEN-END:variables

}
