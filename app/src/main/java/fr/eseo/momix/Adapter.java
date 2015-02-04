package fr.eseo.momix;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by etudiant on 03/02/2015.
 */
public class Adapter extends BaseAdapter {

    private final DictionaryActivity context;
    private final ArrayList<Modele> modelsArrayList;

    public Adapter(DictionaryActivity context, ArrayList<Modele> modelsArrayList) {
        super();
        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public int getCount() {
        return modelsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final LayoutInflater inflater = LayoutInflater.from(context);

        View rowView = null;
        rowView = inflater.inflate(R.layout.list_single, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

        titleView.setText(modelsArrayList.get(position).getTitle());

        ImageView supprIcon = (ImageView) rowView.findViewById(R.id.item_icon);

        OnSupprClick onSupprClick = new OnSupprClick(context, modelsArrayList.get(position).getWord());
        supprIcon.setOnClickListener(onSupprClick);

        return rowView;
    }
}
