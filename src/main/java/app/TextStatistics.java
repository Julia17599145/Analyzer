/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Юлия
 */
public class TextStatistics {

    private List<WordStatistic> wordsAndAmountsOfText = new ArrayList();

    public TextStatistics(List<String> wordsOfText) {
        HashSet<String> uniqueWords = new HashSet<String>(wordsOfText);
        uniqueWords.forEach(word
                -> wordsAndAmountsOfText.add(generateWordStistic(wordsOfText, word))
        );
    }

    private WordStatistic generateWordStistic(List<String> wordsOfText, String word) {
        WordStatistic ws = new WordStatistic(word);
        ws.setAmount(getWordCountFromText(wordsOfText, word));
        ws.setFrequency(ws.getAmount() / (double) wordsOfText.size());
        ws.setPartFrequency(getWordPartFrequency(wordsOfText, word));
        return ws;
    }

    public List<WordStatistic> getWordsAndAmounts() {
        return wordsAndAmountsOfText;
    }

    //частота встречания слова в тексте
    private long getWordCountFromText(List<String> words, String word) {
        long amount = 0;
        for (String wordInText : words) {
            if (word.equals(wordInText)) {
                amount++;
            }
        }
        return amount;
    }

    private ArrayList<Double> getWordPartFrequency(List<String> words, String word) {
        int step = words.size() / 200;
        int amount = 0;
        int minN = 1;

        ArrayList<Double> partFrequency = new ArrayList<Double>();
        partFrequency.add(0.0);
        for(int j = 1; j < 200; j++){
        for (int i = minN; i < step; i++) {
            if (words.get(i).equals(word)) {
                amount++;
            }
        }
            minN = step;
            
            partFrequency.add(amount / (double) step);
            step += words.size() / 200;
        }
        return partFrequency;
    }
}
