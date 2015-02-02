package fr.eseo.momix;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private AnswerValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateAnagram();

        EditText answer = (EditText) findViewById(R.id.answer);
        answer.addTextChangedListener(validator);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dictionnary) {
            return true;
        } else if(id == R.id.action_new) {
            generateAnagram();

            TextView wonText = (TextView) findViewById(R.id.won);
            wonText.setVisibility(View.INVISIBLE);
            TextView anagram = (TextView) findViewById(R.id.anagram);
            anagram.setText(validator.getAnagram().getGenerated());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void generateAnagram() {
        Anagram a = new Anagram("Le petit chaperon rouge");
        validator = new AnswerValidator(this);
        validator.setAnagram(a);
    }

}
