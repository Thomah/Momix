package fr.eseo.momix;

import android.view.View;

/**
 * Created by etudiant on 04/02/2015.
 */
public class OnSupprClick implements View.OnClickListener {

    private DictionaryActivity dA;
    private Word word;

    public OnSupprClick(DictionaryActivity dictionaryActivity, Word word) {
        this.dA = dictionaryActivity;
        this.word = word;
    }

    @Override
    public void onClick(View v)
    {
        dA.getDh().deleteWord(word);
        dA.afficherDictionnaire();
    }
}
