package fr.eseo.momix;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DictionaryActivity extends AppCompatActivity {

    private DatabaseHandler dh;
    private boolean utiliserDicoPerso = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dictionnary);
        dh = new DatabaseHandler(this);

        afficherDictionnaire();

        CheckBox checkBox = (CheckBox) this.findViewById(R.id.checkBoxDictionnairePredefini);
        checkBox.setChecked(!dh.dicoPerso);
        Button buttonAjouter = (Button) findViewById(R.id.button2);
        buttonAjouter.setEnabled(!checkBox.isChecked());
        ListView listView = (ListView) findViewById(R.id.listViewDictionary);
        listView.setEnabled(!checkBox.isChecked());
        EditText editText = (EditText) findViewById(R.id.motPropose);
        editText.setEnabled(!checkBox.isChecked());

        OnChooseDictionaryClick onChooseDictionaryClick = new OnChooseDictionaryClick(this);
        checkBox.setOnClickListener(onChooseDictionaryClick);

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
        if (id == R.id.action_discard) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.action_suppressQuestion) .setTitle(R.string.action_suppress);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) { // User cancelled the dialog } });
                    viderLeDictionaire();
                }

            });
            builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) { // User cancelled the dialog } });
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void viderLeDictionaire(){
        final List<Word> dictionary = dh.getDictionary();
        for(Word w : dictionary){
            dh.deleteWord(w);
        }
        afficherDictionnaire();
    }

    public void ajouterUnMot(View view) {
        EditText editText = (EditText) findViewById(R.id.motPropose);
        String motPropose = editText.getText().toString();
        dh.addWord(motPropose);
        afficherDictionnaire();
    }

    public void afficherDictionnaire(){
        ArrayList<Modele> data = new ArrayList<>();
        if(dh.dicoPerso) {

            for (Word w : dh.getDictionary()) {
                Modele m = new Modele(w.getText(), w);
                data.add(m);
            }
        }
            ListView listView = (ListView) findViewById(R.id.listViewDictionary);
            Adapter adapter = new Adapter(this, data);
            listView.setAdapter(adapter);

    }



    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public DatabaseHandler getDh() {
        return dh;
    }

    public void setUtiliserDicoPerso(boolean utiliserDicoPerso) {
        this.utiliserDicoPerso = utiliserDicoPerso;
    }
}


