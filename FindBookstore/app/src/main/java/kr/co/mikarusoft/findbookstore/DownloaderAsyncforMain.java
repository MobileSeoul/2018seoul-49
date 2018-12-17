package kr.co.mikarusoft.findbookstore;

import android.content.Context;
import android.os.AsyncTask;

import android.widget.TextView;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class DownloaderAsyncforMain extends AsyncTask<Void, Void, String> {
    Context context;
    String urlAddress;
    TextView textView;



    public DownloaderAsyncforMain(Context context, String urlAddress, TextView textView) {
        this.context = context;
        this.urlAddress = urlAddress;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        textView.setText(context.getResources().getString(R.string.PleaseWait));

    }




    @Override
    protected String doInBackground(Void... voids) {


        Object connection = ConnectorDB.connect(urlAddress);
        if(connection.toString().startsWith("Error"))
        {
            return connection.toString();
        }


        try{
            HttpURLConnection con = (HttpURLConnection) connection;
            InputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuffer jsonData  = new StringBuffer();



            while ((line = br.readLine()) != null)
            {
                jsonData.append(line);
            }

            br.close();
            is.close();

            return jsonData.toString();

        }catch (IOException e){
            e.printStackTrace();
            return "Error "+e.getMessage();

        }
    }



    @Override
    protected void onPostExecute(String jsonData)
    {
        super.onPostExecute(jsonData);


        if(jsonData.startsWith("Error"))
        {

            textView.setText(context.getResources().getString(R.string.Unsuccessful) + jsonData);


        }else
        {
            textView.setText(jsonData);
        }
    }
}
