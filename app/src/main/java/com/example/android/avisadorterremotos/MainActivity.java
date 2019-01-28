package com.example.android.avisadorterremotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadAsyncTask.DownloadEqsInterface{
    private ListView earthquake_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earthquake_list_view = (ListView) findViewById(R.id.earthquake_list_view);
       /* ArrayList<String> paises =new ArrayList<>();
        paises.add("Narnia");
        paises.add("Mordor");
        paises.add("Hobbiton");
        paises.add("TheShire");
        //Adapter->puente entre los datos y la pantalla
        ArrayAdapter<String> adapterPaises=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,paises);
        earthquake_list_view.setAdapter(adapterPaises);


        eqList.add(new Earthquake("4,2", "32 km S de Pamplona"));
        eqList.add(new Earthquake("2", "32 km S de Pamplona"));
        eqList.add(new Earthquake("3", "32 km S de Pamplona"));
        eqList.add(new Earthquake("10", "32 km S de New York"));*/

        DownloadAsyncTask downloadAsyncTask=new DownloadAsyncTask();
        //vamos a decir quien es el delegate que va a implementar el método onEqsDownloaded
        downloadAsyncTask.delegate=this;

        downloadAsyncTask.execute();

    }


    @Override
    public void onEqsDowloaded(String data) {
        ArrayList<Earthquake> eqList = new ArrayList<>();
        try{
            JSONObject jsonObject=new JSONObject(data);
            JSONArray featuresJsonArray=jsonObject.getJSONArray("features");
            for(int i=0; i<featuresJsonArray.length();i++){
                JSONObject featuresJsonObject=featuresJsonArray.getJSONObject(i);
                JSONObject propertiesJsonObject=featuresJsonObject.getJSONObject("properties");
                double magnitude=propertiesJsonObject.getDouble("mag");
                String place=propertiesJsonObject.getString("place");
                eqList.add(new Earthquake(Double.toString(magnitude),place));

            }
            EqAdapter eqAdapter = new EqAdapter(this, R.layout.eq_list_view, eqList);
            earthquake_list_view.setAdapter(eqAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("JSONMAIN",data);



    }
}
