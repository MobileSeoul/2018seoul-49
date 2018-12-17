package kr.co.mikarusoft.findbookstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class CategoryListAsyncTask extends AsyncTask<Void, Void, String> {

    Context context;

    Spinner spinner;

    ArrayList<CategoryItem> categoryItems;
    CategoryBaseAdapter categoryBaseAdapter;

    public CategoryListAsyncTask(Spinner spinner, Context context) {
        this.spinner = spinner;
        this.context = context;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        categoryItems=new ArrayList<>();

    }

    @Override
    protected String doInBackground(Void... params) {

        Object connection = ConnectorDB.connect("http://kanjangramen999.cafe24.com/bookstore_upload/category_send.php");
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

    @Override
    protected void onPostExecute(String jsonData)
    {
        super.onPostExecute(jsonData); //html만 안 나오면 된다.

        if(jsonData.startsWith("Error"))
        {
            Toast.makeText(context, context.getResources().getString(R.string.Unsuccessful_category) + jsonData,Toast.LENGTH_SHORT ).show();

        }else
        {
            try{
                JSONArray ja = new JSONArray(jsonData);
                JSONObject jsonObject;

                categoryItems.clear();

                for (int i = 0; i < ja.length(); i++) {

                    jsonObject = ja.getJSONObject(i);

                    String name_cate = jsonObject.getString("name_cate");
                    String id = jsonObject.getString("id");

                    CategoryItem categoryItem = new CategoryItem(name_cate, Integer.parseInt(id));

                    categoryItems.add(categoryItem);

                }

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        categoryBaseAdapter = new CategoryBaseAdapter(categoryItems, context);
        spinner.setAdapter(categoryBaseAdapter);

    }
}
