package fr.eseo.momix;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.GridLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private static final int nbButtonsPerRow = 7;
    private static final int marginButtons = 5;
    private DatabaseHandler dh;
    private AnswerValidator validator;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get screen width and height
        if(android.os.Build.VERSION.SDK_INT < 10) {
            Display display = getWindowManager().getDefaultDisplay();
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            screenWidth = metrics.widthPixels;
            screenHeight = metrics.heightPixels;
        }
        screenWidth-= 2 * (int) getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        screenHeight-= 2 * (int) getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);

        // Generate anagram
        dh = new DatabaseHandler(this);
        generateAnagram();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_dictionnary) {
            Intent intent = new Intent(this, DictionaryActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_new) {
            generateAnagram();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void generateAnagram() {

        // Pick a word
        List<Word> words = dh.getDictionary();
        if(words.size() >  0) {
            int index = (int) (Math.random() * words.size());

            // Creation of the anagram
            Anagram a = new Anagram(words.get(index).getText());
            validator = new AnswerValidator(this);
            validator.setAnagram(a);

            // Setup of the anagram on the view
            TextView anagram = (TextView) findViewById(R.id.anagram);
            anagram.setText(validator.getAnagram().getGenerated());

            // Initialisation of the answer
            TextView answerView = (TextView) findViewById(R.id.answer);
            String answer = "";
            for (int k = 0; k < validator.getAnagram().getAnswer().length(); k++) {
                answer += ' ';
            }
            answerView.setText(answer);

            // Remove previous buttons
            GridLayout lettersLayout = (GridLayout) findViewById(R.id.letters);
            lettersLayout.removeAllViews();

            // Setup of the answer buttons
            int x, y;
            int kButton = 0, kLetter = 0;
            char c = 0;
            int lengthString = validator.getAnagram().getGenerated().length();
            int widthButton = (screenWidth - marginButtons * (nbButtonsPerRow - 1)) / nbButtonsPerRow;
            while (kLetter < lengthString) {

                // Add a button only if it is not a space
                do {
                    c = validator.getAnagram().getGenerated().charAt(kLetter);
                    kLetter++;
                } while (c == ' ' && kLetter < lengthString);

                // Creation of the button
                ToggleButton letter = new ToggleButton(this);

                // Parameters of the buttons
                x = kButton % nbButtonsPerRow;
                y = kButton / nbButtonsPerRow;
                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = widthButton;
                param.rightMargin = marginButtons;
                param.topMargin = marginButtons;
                param.columnSpec = GridLayout.spec(x);
                param.rowSpec = GridLayout.spec(y);
                letter.setLayoutParams(param);

                letter.setText("" + c);
                letter.setTextOn("" + c);
                letter.setTextOff("" + c);
                letter.setOnClickListener(validator);

                lettersLayout.addView(letter);

                kButton++;
            }
        } else {
            // Setup of the anagram on the view
            TextView anagram = (TextView) findViewById(R.id.anagram);
            anagram.setText(R.string.dictionnary_empty);
        }

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    private char[] stringToChar(String s) {
        char[] text = new char[s.length()];
        for (int i=0; i<s.length(); i++) {
            text[i] = s.charAt(i);
        }
        return text;
    }

}
