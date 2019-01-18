package com.example.android.avisadorterremotos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EqAdapter extends ArrayAdapter<Earthquake> {
    //Varriables globales
    private ArrayList<Earthquake> eqList;
    private Context context;
    private int layoutId;

    public EqAdapter(@NonNull Context context, int resource, @NonNull List<Earthquake> objects) {
        super(context, resource, objects);
        eqList = new ArrayList<>(objects);
        this.context = context;
        this.layoutId = resource;
    }

    //coger datos de los terremotos e insertarlos en los textView de nuestro layout


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Clase para crear views a partir de un archivo xml
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView=inflater.inflate(R.layout.eq_list_view,null);
        //Ya tenemos el layout y podemos acceder a sus hijose
        TextView magnitudeTextView =(TextView)rootView.findViewById(R.id.eq_list_item_magnitude);
        TextView placeTextView =(TextView)rootView.findViewById(R.id.eq_list_item_place);

        Earthquake earthquake=eqList.get(position);
        magnitudeTextView.setText(String.valueOf(earthquake.getMagnitud()));
        placeTextView.setText(earthquake.getPlace());

        return rootView;

    }


}
