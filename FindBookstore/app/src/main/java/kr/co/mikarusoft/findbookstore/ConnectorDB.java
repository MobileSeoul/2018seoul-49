package kr.co.mikarusoft.findbookstore;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by KokoroNihonn3DGame on 2018-08-07.
 */

public class ConnectorDB {
    public static Object connect(String urlAddress)
    {
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setDoInput(true);

            return connection;


        }catch (MalformedURLException e)
        {
            e.printStackTrace();;
            return "Error " + e.getMessage();

        }catch (IOException e){
            e.printStackTrace();;
            return "Error " + e.getMessage();
        }
    }
}
