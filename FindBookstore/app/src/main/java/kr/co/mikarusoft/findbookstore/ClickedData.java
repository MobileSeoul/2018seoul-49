package kr.co.mikarusoft.findbookstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ClickedData extends AppCompatActivity {

    private TextView text_name, text_address, text_ex, text_cate;
    private ImageView bookstore_image;

    private ImageButton go_to_map, go_to_pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_data);

        bookstore_image = (ImageView)findViewById(R.id.profile);
        text_name = (TextView)findViewById(R.id.text_name);
        text_address = (TextView)findViewById(R.id.text_address);
        text_ex = (TextView)findViewById(R.id.text_bookstore_ex);
        text_cate = (TextView)findViewById(R.id.text_category);



        Intent intent = new Intent(this.getIntent());

        final String id = intent.getStringExtra("id");

        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String ex = intent.getStringExtra("bookstore_ex");
        String cate = intent.getStringExtra("category");
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("IMG");

        final String lng = intent.getStringExtra("lng");
        final String lat = intent.getStringExtra("lat");

        text_name.setText(name);
        text_address.setText(address);
        text_cate.setText(cate);
        text_ex.setText(ex);
        bookstore_image.setImageBitmap(bitmap);

        go_to_map = (ImageButton)findViewById(R.id.go_to_map);
        go_to_pictures = (ImageButton)findViewById(R.id.go_to_pictures);

        go_to_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), BookstoreMap.class);
                intent.putExtra("id", id);
                intent.putExtra("lng", lng);
                intent.putExtra("lat", lat);

                startActivity(intent);

                finish();
            }
        });

        go_to_pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), DetailPicturesActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

                finish();
            }
        });
    }
}
