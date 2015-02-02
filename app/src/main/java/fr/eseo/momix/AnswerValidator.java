package fr.eseo.momix;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AnswerValidator implements TextWatcher {

    private MainActivity activity;
    private Anagram anagram;

    public AnswerValidator(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        EditText answerField = (EditText) activity.findViewById(R.id.answer);
        String answer = answerField.getText().toString();
        if(answer.compareTo(anagram.getAnswer()) == 0) {
            TextView wonText = (TextView) activity.findViewById(R.id.won);
            wonText.setVisibility(View.VISIBLE);
        }
    }

    public Anagram getAnagram() {
        return anagram;
    }

    public void setAnagram(Anagram anagram) {
        this.anagram = anagram;
    }
}
