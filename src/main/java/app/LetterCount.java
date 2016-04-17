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
public class LetterCount {
   private List<LetterStatistic> lettersAndAmountsOfText = new ArrayList();

    public LetterCount(List<Character> lettersOfText) {
        HashSet<Character> uniqueLetters = new HashSet<Character>(lettersOfText);
        uniqueLetters.forEach(letter
                -> lettersAndAmountsOfText.add(generateLetterStistic(lettersOfText, letter))
        );
    }

    private LetterStatistic generateLetterStistic(List<Character> lettersOfText, char letter) {

        LetterStatistic ls = new LetterStatistic(letter);
        ls.setAmount(getLetterCountFromText(lettersOfText, letter));
        ls.setFrequency(ls.getAmount() / (double) lettersOfText.size());
        return ls;
    }

    public List<LetterStatistic> getLettersAndAmounts() {
        return lettersAndAmountsOfText;
    }

    private long getLetterCountFromText(List<Character> letters, char letter) {
        long amount = 0;
        for (char letterInText : letters) {
            if (letter == letterInText) {
                amount++;
            }
        }
        return amount;
    } 
}
