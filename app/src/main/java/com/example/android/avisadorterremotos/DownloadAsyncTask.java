package com.example.android.avisadorterremotos;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class DownloadAsyncTask extends AsyncTask<Void,Void,String> {

    public DownloadEqsInterface delegate;

    public interface DownloadEqsInterface{
        void onEqsDowloaded(String data);
    }

    @Override
    protected String doInBackground(Void... voids) {
        String jsonresponse="";
        try{
            jsonresponse=downloadData(new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson"));
        }catch (IOException e){
            e.printStackTrace();
        }
        return jsonresponse;
    }

    @Override

    protected void onPostExecute(String jsonresponse) {
        super.onPostExecute(jsonresponse);
        delegate.onEqsDowloaded(jsonresponse);
        Log.d("RESPUESTAJSON",jsonresponse);
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
           e.printStackTrace();

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
