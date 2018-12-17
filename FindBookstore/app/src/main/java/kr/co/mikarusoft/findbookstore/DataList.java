package kr.co.mikarusoft.findbookstore;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DataList extends ActionBarActivity{

    private ListView listView;

    ListAdopter listAdopter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        listView = (ListView)findViewById(R.id.list);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);


        findViewById(R.id.go_to_main_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Downloader downloader = new Downloader(this, "http://kanjangramen999.cafe24.com/bookstore_upload/sendbookstore.php",listView);
        downloader.execute();

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Downloader(getApplicationContext(),"http://kanjangramen999.cafe24.com/bookstore_upload/sendbookstore.php",listView).execute();

                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // 메뉴버튼이 처음 눌러졌을 때 실행되는 콜백메서드
        // 메뉴버튼을 눌렀을 때 보여줄 menu 에 대해서 정의
        getMenuInflater().inflate(R.menu.list_menu, menu);
        Log.d("test", "onCreateOptionsMenu - 최초 메뉴키를 눌렀을 때 호출됨");
        return true;
    }
}
