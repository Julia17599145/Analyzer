package app;

//import .*;

import java.util.ArrayList;
import java.util.HashSet;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Юлия
 */
public class WordStatistic implements Comparable<WordStatistic> {

    private final String word;
    private long amount = 0;
    private double frequency = 0;
    private ArrayList<Double> partFrequency = new ArrayList<Double>();
    
    public int compareTo(WordStatistic w) {
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

    public WordStatistic(String word) {
        this.word = word;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getFrequency() {
        return frequency;
    }

    public long getAmount() {
        return amount;
    }

    public String getWord() {
        return word;
    }

    public void setPartFrequency(ArrayList<Double> frequency) {
        this.partFrequency = frequency;
    }
    
    public ArrayList<Double> getPartFrequency() {
        return partFrequency;
    }
    
    public String toString() {
        //return "word: " + this.word + " amount: " + this.amount + " frequecy: " + this.frequency;
        return this.word + " " + this.amount + " " + this.frequency + " " + this.partFrequency;
    }
}
