/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Юлия
 */
public class LetterStatisticTableModel implements TableModel {
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<LetterStatistic> lettersAndAmountsOfText;

    public LetterStatisticTableModel(List<LetterStatistic> lettersAndAmountsOfText) {
        this.lettersAndAmountsOfText = lettersAndAmountsOfText;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return long.class;
            case 2:
                return double.class;
        }
        return String.class;
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Буква";
            case 1:
                return "Количество";
            case 2:
                return "Частота";
        }
        return "";
    }

    public int getRowCount() {
        return lettersAndAmountsOfText.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        LetterStatistic lettersAndAmounts = lettersAndAmountsOfText.get(rowIndex);
        DecimalFormat f = new DecimalFormat("#,#####0.00000");
        switch (columnIndex) {
            case 0:
                return lettersAndAmounts.getLetter();
            case 1:
                return lettersAndAmounts.getAmount();
            case 2:
                return f.format(lettersAndAmounts.getFrequency());
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }
}
