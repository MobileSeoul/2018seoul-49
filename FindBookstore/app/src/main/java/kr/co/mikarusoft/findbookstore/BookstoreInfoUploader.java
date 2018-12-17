package kr.co.mikarusoft.findbookstore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class BookstoreInfoUploader extends AppCompatActivity implements View.OnClickListener{

    private static final String UPLOAD_URL = "http://kanjangramen999.cafe24.com/bookstore_upload/upload_booksoteinfo.php";
    private static final int IMAGE_REQUEST_CODE = 3;
    //외장 드라이브 마이크로 SD카드 들어갔다 나올때
    private static final int STORAGE_PERMISSION_CODE = 123;

    private ImageView imageView;
    private EditText etName;
    private EditText etAddress;

    private Spinner category;

    private String category_name = "종합서점";

    private EditText addCategory;

    private TextView file_info;
    private EditText etEx;
    private Button btnUpload;

    private Bitmap bitmap;
    //실제 이미지 저장 경로
    private Uri filePath;

    private GeoDegree geoDegree;

    private String image_path;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore_info_uploader);

        imageView = (ImageView)findViewById(R.id.imageView2);
        etName = (EditText)findViewById(R.id.editText2);
        etAddress = (EditText)findViewById(R.id.editText3);
        etEx = (EditText)findViewById(R.id.editText4);

        //Spinner 설정
        category = (Spinner)findViewById(R.id.spinner);

        new CategoryListAsyncTask(category, this).execute();

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "선택했음.", Toast.LENGTH_SHORT).show();

                CategoryItem item = (CategoryItem)parent.getAdapter().getItem(position);

                String cate = item.getCate();

                addCategory.setText(cate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Upload
        btnUpload = (Button)findViewById(R.id.button);

        //File_path_info
        file_info = (TextView)findViewById(R.id.file_into_text);

        addCategory = (EditText)findViewById(R.id.editCategory);

        requestStoragePermission();

        imageView.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

        findViewById(R.id.go_to_main_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {

                image_path = getPath(filePath);

                geoDegree = new GeoDegree(new ExifInterface(image_path ));

                ExifInterface exif = new ExifInterface(image_path);
                String myAttribute = "[Exif information] \n\n";

                myAttribute += getTagString(ExifInterface.TAG_DATETIME, exif);
                myAttribute += getTagString(ExifInterface.TAG_FLASH, exif);
                myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
                myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
                myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
                myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
                myAttribute += getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
                myAttribute += getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
                myAttribute += getTagString(ExifInterface.TAG_MAKE, exif);
                myAttribute += getTagString(ExifInterface.TAG_MODEL, exif);
                myAttribute += getTagString(ExifInterface.TAG_ORIENTATION, exif);
                myAttribute += getTagString(ExifInterface.TAG_WHITE_BALANCE, exif);

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                file_info.setText("Path: ".concat(getPath(filePath).concat(myAttribute))); //이미지 정보 가르키기
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getTagString(String tag, ExifInterface exif) {
        return (tag + " : " + exif.getAttribute(tag) + "\n");
    }

    public void uploadMultipart() {

        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String bookstore_ex = etEx.getText().toString().trim();
        String category_string;
        if (addCategory.getText().toString().isEmpty()){
            category_string = category_name;
        } else {
            category_string = addCategory.getText().toString().trim();
        }
        category_string = category.getSelectedItem().toString().trim();

        //위도 경도 -> 소수점
        //핸드폰 자체 GPS 정보를 가져오게 하자.
        String gps_lat = geoDegree.getLatitude().toString();
        String gps_lng = geoDegree.getLongitude().toString();

        //Uploading code
        try {
            //getting the actual path of the image
            image_path = getPath(filePath);

            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, UPLOAD_URL).setUtf8Charset()
                    .addFileToUpload(image_path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .addParameter("address", address) //Adding text parameter to the request
                    .addParameter("category", category_string) //Adding text parameter to the request
                    .addParameter("bookstore_ex", bookstore_ex) //Adding text parameter to the request
                    .addParameter("gps_lat", gps_lat) //Adding text parameter to the request
                    .addParameter("gps_lng", gps_lng) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

            Intent intent = new Intent(this, UploadSuccessActivity.class);
            startActivity(intent);
            //그냥 업로드가 성공했음을 알리는 activity를 보여주고, 거기에서 리스트로 넘어가게 한다.

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == imageView){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
        }else if(v == btnUpload){
            uploadMultipart();

        }
    }
}
