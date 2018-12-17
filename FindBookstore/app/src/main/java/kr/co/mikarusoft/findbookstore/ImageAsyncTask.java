package kr.co.mikarusoft.findbookstore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageAsyncTask  extends AsyncTask<String,Void,Bitmap> {

    ImageView imageView;
    List_Item list_item;

    //이미지 뒤에 비트맵으로 받아서 배열에 넣어준다.

    public ImageAsyncTask(ImageView imageView, List_Item list_item) {
        this.imageView = imageView;
        this.list_item = list_item;
    }

    @Override
    protected Bitmap doInBackground(String... url) {


        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(url[0]).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            list_item.setBitmapimg(bitmap);

        } catch (java.net.MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {


        //이미지 사이즈 줄일것!!
        if (bitmap == null) {
            imageView.setImageBitmap(bitmap);
        }
        else {
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            imageView.setImageBitmap(resizedBitmap);
        }

        imageView.setImageBitmap(bitmap);

    }
}
