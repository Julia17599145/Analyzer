/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Юлия
 */
public class GenerateTable {
    
    NewJFrame formAnalazer; //analazer
    private List<WordStatistic> was;

    public GenerateTable(List<WordStatistic> wass, NewJFrame jf){
        was = wass;
        this.formAnalazer = jf;
    }
    
    public Map<String, Short> readTable(File file) {
        StringBuilder readBuffer = new StringBuilder();
        Map<String, Short> dictionary = new HashMap<String, Short>();
        Short code = 0;
        try {
            //Объект для чтения файла в буфер
            FileInputStream stream = new FileInputStream(file.getAbsoluteFile());
            InputStreamReader reader = new InputStreamReader(stream, "Cp1251");
            BufferedReader in = new BufferedReader(reader);
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    readBuffer.append(s);
                }
                String[] splitFile = readBuffer.toString().trim().split("(~\\|)");

                for (int i = 0; i < splitFile.length; i += 2) {
                    dictionary.put(splitFile[i], Short.parseShort(splitFile[i + 1]));
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dictionary;
    }

    public Map<String, Short> startTable() {
        Map<String, Short> dictionary = new HashMap<String, Short>();
        Short code = 0;
        //задаем русский, английский алфавиты и спец символы
        //спецсимволы и английский алфавит
        for (int i = 32; i < 127; i++) {
            dictionary.put("" + (char) i, code++);
        }
        for (int i = 1040; i < 1104; i++) {
            dictionary.put("" + (char) i, code++);
        }
        //добавления №
        dictionary.put("" + (char) 8470, code++);
        //добавление символа ё
        dictionary.put("" + (char) 1105, code++);
        //добавление символа Ё
        dictionary.put("" + (char) 1025, code++);
        //символы открывающихся / закрывающихся ковычек
        dictionary.put("" + (char) 171, code++);
        dictionary.put("" + (char) 187, code++);
        //перенос
        dictionary.put("" + (char) 182, code++);
        return dictionary;
    }

    public Map<String, Short> generateCode(Short code, Map<String, Short> dictionary) {
        if (formAnalazer.getRadioButtonGenFrequence().isSelected()) {
            //генерируем коды лексических едениц до определенной частоты встречания
            for (int i = 0; i < was.size(); i++) {
                if (was.get(i).getFrequency() > Double.parseDouble(formAnalazer.getComboBoxFrequency().getSelectedItem().toString()) && code < 4096 && !dictionary.containsKey(was.get(i).getWord())) {
                    dictionary.put(was.get(i).getWord(), code++);
                }
            }
        }
        if (formAnalazer.getRadiobuttonGenNumber().isSelected()) {
            //генерируем коды до лексической еденицы с определенным номером
            for (int i = 0; i < was.size(); i++) {
                if (i < Integer.parseInt(formAnalazer.getSpinnerNumber().getValue().toString()) && code < 4096 && !dictionary.containsKey(was.get(i).getWord())) {
                    dictionary.put(was.get(i).getWord(), code++);
                }
            }
        }
        return dictionary;
    }

    public void writeTableInFile(Map<String, Short> dictionary) {
        File writeFile = new File("../CoderDecoder/bankcode.txt");
        try {
            //проверяем, что если файл не существует то создаем его
            if (!writeFile.exists()) {
                writeFile.createNewFile();
            }
            PrintWriter out = new PrintWriter(writeFile.getAbsoluteFile(), "Cp1251");

            try {
                //out.print(dictionary);
                for (Map.Entry<String, Short> entry : dictionary.entrySet()) {
                    out.print(entry.getKey() + "~|" + entry.getValue() + "~|");
                }
            } finally {
                out.close();
                JOptionPane.showMessageDialog(formAnalazer.getButtonOpenFile(), "Сгенерированные коды сохранены в файл");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
