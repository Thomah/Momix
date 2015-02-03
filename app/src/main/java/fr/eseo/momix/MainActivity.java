package fr.eseo.momix;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.GridLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends ActionBarActivity {

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
        screenWidth-= dpToPx(2 * 16);
        screenHeight-= dpToPx(2 * 16);

        // Generate anagram
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
            return true;
        } else if(id == R.id.action_new) {
            generateAnagram();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void generateAnagram() {

        // Creation of the anagram
        Anagram a = new Anagram("Le petit chaperon rouge");
        validator = new AnswerValidator(this);
        validator.setAnagram(a);

        // Setup of the anagram on the view
        TextView anagram = (TextView) findViewById(R.id.anagram);
        anagram.setText(validator.getAnagram().getGenerated());

        // Initialisation of the answer
        TextView answerView = (TextView) findViewById(R.id.answer);
        String answer = "";
        for(int k = 0 ; k < validator.getAnagram().getAnswer().length() ; k++) {
            answer+= ' ';
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
        while(kLetter < lengthString) {

            // Add a button only if it is not a space
            do {
                c = validator.getAnagram().getGenerated().charAt(kLetter);
                kLetter++;
            } while(c == ' ' && kLetter < lengthString);

            // Creation of the button
            ToggleButton letter = new ToggleButton(this);

            // Parameters of the buttons
            x = kButton % (screenWidth / 105);
            y = kButton / (screenWidth / 105);
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = GridLayout.LayoutParams.WRAP_CONTENT;
            param.width = 100;
            param.rightMargin = 5;
            param.topMargin = 5;
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
