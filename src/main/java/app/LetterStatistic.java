/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.util.HashSet;

/**
 *
 * @author Юлия
 */
public class LetterStatistic implements Comparable<LetterStatistic>{

    final char letter;
    long amount = 0;
    double frequency = 0;
    HashSet<Double> partFrequency = new HashSet<Double>();
    
    public int compareTo(LetterStatistic w) {
        if (frequency < w.getFrequency()) {
            /* текущее меньше полученного */
            return 1;
        } else if (frequency > w.getFrequency()) {
            /* текущее больше полученного */
            return -1;
        }
        /* текущее равно полученному */
        return 0;
    }
    
    public LetterStatistic(char letter) {
        this.letter = letter;
    }
    
    public void setAmount(long amount) {
        this.amount = amount;
    }
    
    public long getAmount() {
        return amount;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setPartFrequency(double frequency) {
        this.partFrequency.add(frequency);
    }
    
    public HashSet<Double> getPartFrequency() {
        return partFrequency;
    }
    
    public char getLetter() {
        return letter;
    }

    public String toString() {
        return "letter: " + this.letter + " amount: " + this.amount + " frequecy: " + this.frequency;
    }
}
