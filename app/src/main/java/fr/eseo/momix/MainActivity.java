package fr.eseo.momix;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.ActionMenuView;
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
    private int actualLevel = 1;

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

        MenuItem item = menu.findItem(R.id.level);
        if(item != null) {
            if(DatabaseHandler.dicoPerso) {
                item.setTitle(getResources().getString(R.string.level));
            } else {
                item.setTitle("Niveau " + actualLevel);
            }
        }

        // Reset of the level UI
        refreshLevelUI();

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
            actualLevel = 1;
            generateAnagram();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void generateAnagram() {

        // Reset of the level UI
        refreshLevelUI();

        // Pick a word
        List<Word> words = dh.getDictionary();
        if(words.size() >  0) {
            int index = 0;
            Word w;
            if(DatabaseHandler.dicoPerso) {
                index = (int) (Math.random() * words.size());
                w = words.get(index);
            } else {
                w = dh.getWord(actualLevel);
            }

            // Creation of the anagram
            Anagram a = new Anagram(w.getText().toUpperCase());
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
                letter.setBackgroundColor(getResources().getColor(R.color.answer_background));
                letter.setTextColor(getResources().getColor(R.color.answer_text));


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

    public void incActualLevel() {

        List<Word> words = dh.getDictionary();
        if(actualLevel >= words.size()) {
            actualLevel = 1;
        } else {
            actualLevel++;
        }
        refreshLevelUI();
    }

    public void refreshLevelUI() {
        MenuView.ItemView itemView = (MenuView.ItemView) findViewById(R.id.level);

        if(itemView != null) {
            if(DatabaseHandler.dicoPerso) {
                itemView.setTitle(getResources().getString(R.string.level));
            } else {
                itemView.setTitle("Niveau " + actualLevel);
            }
        }
    }

    public DatabaseHandler getDatabaseHandler() {
        return dh;
    }

    public int getActualLevel() {
        return actualLevel;
    }

}
