package kr.co.mikarusoft.findbookstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KokoroNihonn3DGame on 2018-08-07.
 */

public class DataParser extends AsyncTask<Void, Void, Boolean> {

    Context context;
    String jsonData;
    ListView listView;

    final String URL_mini_img = "http://kanjangramen999.cafe24.com/bookstore_upload/uploads/mini_img/";

    //어댑터 생성 후 데이터를 담아준다.

    ProgressDialog progressDialog;
    ArrayList<List_Item> list_items;

    public DataParser(Context context, String jsonData, ListView listView) {
        this.context = context;
        this.jsonData = jsonData;
        this.listView = listView;
        list_items = new ArrayList<>();
    }

    public DataParser(Context context, String jsonData, ListView listView, ArrayList<List_Item> list_items) {
        this.context = context;
        this.jsonData = jsonData;
        this.listView = listView;
        this.list_items = list_items;
    }


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Parse");
        progressDialog.setMessage("Waiting....");

        //progressDialog.show();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return this.parseData();
    }


    @Override
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if(result)
        {

            ListAdopter adapter=new ListAdopter(list_items);
            listView.setAdapter(adapter);

        }
    }


    private Boolean parseData()
    {
        try{
            JSONArray ja = new JSONArray(jsonData);
            JSONObject jsonObject;

            list_items.clear();

            //int i = 0; i < ja.length(); i++

            for (int i = ja.length()-1; i >=0 ; i--) {

                //알맞게 적재 적소에 데이터를 배치해준다.
                jsonObject = ja.getJSONObject(i);

                String name = jsonObject.getString("name");
                String id = jsonObject.getString("id");
                String url = URL_mini_img + jsonObject.getString("bookstore_image");
                String category = jsonObject.getString("category");
                String address = jsonObject.getString("address");
                String bookstore_ex = jsonObject.getString("bookstore_ex");

                String gpsLat = jsonObject.getString("gps_lat");
                String gpsLng = jsonObject.getString("gps_lng");

                List_Item list_item = new List_Item(id, null, url, null, null, name, gpsLat, gpsLng, category, bookstore_ex, address);

                list_items.add(list_item);

            }
            return true;



        }catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

}
