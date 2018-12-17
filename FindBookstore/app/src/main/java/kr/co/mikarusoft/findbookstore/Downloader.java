package kr.co.mikarusoft.findbookstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by KokoroNihonn3DGame on 2018-08-07.
 */

public class Downloader extends AsyncTask<Void, Void, String> {

    Context context;
    String urlAddress;
    ListView listView;
    //서버에서 json data(글자, 아이디만)를 싹 다 받아서, DataParser로 보낸다.

    ProgressDialog progressDialog;
    ArrayList<List_Item> list_items = new ArrayList<>();

    public Downloader(Context context, String urlAddress, ListView listView) {
        this.context = context;
        this.urlAddress = urlAddress;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getResources().getString(R.string.Retrieve)+" START");
        progressDialog.setMessage(context.getResources().getString(R.string.PleaseWait));
        //progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        //받아온다.
        return this.downloadData();

    }

    @Override
    protected void onPostExecute(String jsonData)
    {
        super.onPostExecute(jsonData);

        progressDialog.dismiss();

        if(jsonData.startsWith("Error"))
        {
            Toast.makeText(context, context.getResources().getString(R.string.Unsuccessful) + jsonData,Toast.LENGTH_SHORT ).show();

        }else
        {
            //보내기
            new DataParser(context,jsonData,listView,list_items).execute();
        }
    }


    private String downloadData()
    {
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

            br.close();;
            is.close();

            return jsonData.toString();

        }catch (IOException e){
            e.printStackTrace();
            return "Error "+e.getMessage();

        }
    }

}
