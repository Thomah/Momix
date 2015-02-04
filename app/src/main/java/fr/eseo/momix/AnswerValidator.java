package fr.eseo.momix;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AnswerValidator implements View.OnClickListener {

    private MainActivity activity;
    private Anagram anagram;

    public AnswerValidator(MainActivity activity) {
        this.activity = activity;
    }

    public Anagram getAnagram() {
        return anagram;
    }

    public void setAnagram(Anagram anagram) {
        this.anagram = anagram;
    }

    @Override
    public void onClick(View v) {

        ToggleButton b = (ToggleButton) v;

        TextView answerField = (TextView) activity.findViewById(R.id.answer);
        String answer = answerField.getText().toString();
        char[] input = stringToChar(answer);
        char[] output = new char[input.length];
        char characterSelected = b.getText().toString().charAt(0);

        int k;
        if(b.isChecked()) {
            k = 0;
            boolean characterFound = false;
            while(!characterFound && k < input.length) {
                characterFound = input[k] == ' ' && anagram.getAnswer().charAt(k) != ' ';
                k++;
            }
            if(characterFound) {
                int indexInserted = k-1;
                output[indexInserted] = characterSelected;
                for(; k < input.length ; k++) {
                    output[k] = input[k];
                }
                for(k = indexInserted-1;k >= 0 ; k--) {
                    output[k] = input[k];
                }
            } else {
                for(k = 0;k < input.length ; k++) {
                    output[k] = input[k];
                }
            }
        } else {
            k = input.length - 1;
            boolean characterFound = false;
            while(!characterFound && k >= 0) {
                characterFound = characterSelected == input[k];
                k--;
            }
            if(characterFound) {
                int indexDeleted = k+1;
                for(;k >= 0 ; k--) {
                    output[k] = input[k];
                }
                for(k = indexDeleted; k < input.length-1 ; k++) {
                    if(anagram.getAnswer().charAt(k+1) == ' ') {
                        output[k] = input[k+2];
                        output[k+1] = ' ';
                        k++;
                    } else {
                        output[k] = input[k + 1];
                    }
                }
                output[k] = ' ';
            } else {
                for(k = 0;k < input.length ; k++) {
                    output[k] = input[k];
                }
            }
        }
        String actualAnswer = charToString(output);
        answerField.setText(charToString(output));

        if(actualAnswer.compareTo(anagram.getAnswer()) == 0) {

            activity.incActualLevel();

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(R.string.won_content)
                    .setTitle(R.string.won);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    activity.generateAnagram();
                }
            });
            builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }

    private char[] stringToChar(String s) {
        char[] text = new char[s.length()];
        for (int i=0; i<s.length(); i++) {
            text[i] = s.charAt(i);
        }
        return text;
    }

    private String charToString(char[] c) {
        String s = "";
        for (int i=0; i<c.length; i++) {
            s+= c[i];
        }
        return s;
    }

}
