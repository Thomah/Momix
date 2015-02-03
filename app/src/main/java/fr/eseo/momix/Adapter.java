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

/**
 * Created by etudiant on 03/02/2015.
 */
public class Adapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Modele> modelsArrayList;

    public Adapter(Context context, ArrayList<Modele> modelsArrayList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = LayoutInflater.from(context);

        // 2. Get rowView from inflater

        View rowView = null;
        rowView = inflater.inflate(R.layout.list_single, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.item_title);

        titleView.setText(modelsArrayList.get(position).getTitle());

        return rowView;
    }
}
