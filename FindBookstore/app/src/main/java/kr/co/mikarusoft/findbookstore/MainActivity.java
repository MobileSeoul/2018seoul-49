package kr.co.mikarusoft.findbookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton upload, list, map;
    private Intent intent;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upload = (ImageButton)findViewById(R.id.uploadButton);
        list = (ImageButton)findViewById(R.id.listButton);
        map = (ImageButton)findViewById(R.id.mapButton);

        textView = (TextView)findViewById(R.id.number);

        new DownloaderAsyncforMain(this,"http://kanjangramen999.cafe24.com/bookstore_upload/numberofbooksote.php",textView).execute();

        upload.setOnClickListener(this);
        list.setOnClickListener(this);
        map.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == upload){
            intent = new Intent(this, BookstoreInfoUploader.class);
            startActivity(intent);

            finish();
        }else if(view == list){
            intent = new Intent(this, DataList.class);
            startActivity(intent);

            finish();
        }else if (view == map) {
            intent = new Intent(this, BookstoreMap.class);
            startActivity(intent);

            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
