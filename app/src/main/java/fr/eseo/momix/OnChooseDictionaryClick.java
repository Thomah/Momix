package fr.eseo.momix;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

/**
 * Created by etudiant on 04/02/2015.
 */
public class OnChooseDictionaryClick implements View.OnClickListener {

    private DictionaryActivity dA;

    public OnChooseDictionaryClick(DictionaryActivity dictionaryActivity) {
        this.dA = dictionaryActivity;
    }

    @Override
    public void onClick(View v)
    {
        CheckBox checkBox = (CheckBox) v;

        SharedPreferences settings = dA.getSharedPreferences("MomixPrefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        boolean dictionaryPerso = checkBox.isChecked();
        editor.putBoolean("dicoPerso", dictionaryPerso);


        Button buttonAjouter = (Button) dA.findViewById(R.id.button2);
        buttonAjouter.setEnabled(!buttonAjouter.isEnabled());
        ListView listView = (ListView) dA.findViewById(R.id.listViewDictionary);
        listView.setEnabled(!listView.isEnabled());
        EditText editText = (EditText) dA.findViewById(R.id.motPropose);
        editText.setEnabled(!editText.isEnabled());
        dA.setUtiliserDicoPerso(!checkBox.isChecked());
        dA.getDh().setDicoPerso(!checkBox.isChecked());
        dA.afficherDictionnaire();

    }
}
