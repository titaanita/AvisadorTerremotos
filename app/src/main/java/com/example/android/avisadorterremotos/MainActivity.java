package com.example.android.avisadorterremotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView earthquake_list_view = (ListView) findViewById(R.id.earthquake_list_view);
       /* ArrayList<String> paises =new ArrayList<>();
        paises.add("Narnia");
        paises.add("Mordor");
        paises.add("Hobbiton");
        paises.add("TheShire");
        //Adapter->puente entre los datos y la pantalla
        ArrayAdapter<String> adapterPaises=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,paises);
        earthquake_list_view.setAdapter(adapterPaises);*/

        ArrayList<Earthquake> eqList = new ArrayList<>();
        eqList.add(new Earthquake("4,2", "32 km S de Pamplona"));
        eqList.add(new Earthquake("2", "32 km S de Pamplona"));
        eqList.add(new Earthquake("3", "32 km S de Pamplona"));
        eqList.add(new Earthquake("10", "32 km S de New York"));

        EqAdapter eqAdapter = new EqAdapter(this, R.layout.eq_list_view, eqList);
        earthquake_list_view.setAdapter(eqAdapter);

        try{
            downloadData(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String downloadData(URL url) throws IOException {
        String jsonResponse="";
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try{
            //Establecer la conexión
            urlConnection =(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);

            inputStream=urlConnection.getInputStream();
            jsonResponse=readFromStream(inputStream);
        }catch (IOException e){
            Toast.makeText(this,"Error al descargar datos",Toast.LENGTH_LONG).show();

        }finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader= new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader =new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while (line!=null){
                output.append(line);
                line=reader.readLine();
            }
        }

        return output.toString();
    }
}
