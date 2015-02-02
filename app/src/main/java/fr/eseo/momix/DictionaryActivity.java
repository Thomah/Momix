package fr.eseo.momix;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import java.util.List;


public class DictionaryActivity extends ActionBarActivity {

    private DatabaseHandler dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionnary);
        dh = new DatabaseHandler(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dictionnary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void ajouterUnMot(View view) {
        EditText editText = (EditText) findViewById(R.id.motPropose);
        String motPropose = editText.getText().toString();
        dh.addWord(motPropose);


        EditText responseText = (EditText) findViewById(R.id.responseText);

        List<Word> dictionary = dh.getDictionary();
        String r = "";
        for (Word w : dictionary) {

            String log = " Name: " + w.getText();
            r += (String) w.getText().toString();
        }
        responseText.setText(r);
    }




}
