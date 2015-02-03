package fr.eseo.momix;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by etudiant on 03/02/2015.
 */
public class Adapter extends ArrayAdapter<Modele> {

    private final Context context;
    private final ArrayList<Modele> modelsArrayList;

    public Adapter(Context context, ArrayList<Modele> modelsArrayList) {

        super(context, R.layout.list_single, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        if(!modelsArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.list_single, parent, false);

            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

            // 4. Set the text for textView
            titleView.setText(modelsArrayList.get(position).getTitle());
        }

        return rowView;
    }
}
