package net.ricerich.wanteat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class foodListView extends AppCompatActivity {

    private Button refreshBtn, backBtn;
    private ListView listView;
    private Custom_Adapter adapter;
    String gid, gid1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlistview);
        Intent Id = getIntent();
        gid = Id.getStringExtra("MenuToList_id");
        Intent Id1 = getIntent();
        gid1 = Id1.getStringExtra("StoreToList_id");

        if(gid != null){
            setTitle(gid + "님의 맛집 리스트");
        }
        else if (gid1 != null)
        {
            setTitle(gid1 + "님의 맛집 리스트");
        }


        listView = (ListView) findViewById(R.id.listView);
        adapter = new Custom_Adapter(foodListView.this, R.layout.adapter_storeinfo, new ArrayList<UserInfo>());
        listView.setAdapter(adapter);

        refreshBtn = (Button) findViewById(R.id.refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Id = getIntent();
                gid = Id.getStringExtra("MenuToList_id");
                Intent Id1 = getIntent();
                gid1 = Id1.getStringExtra("StoreToList_id");
                System.out.println(gid);
                System.out.println(gid1);

                if(gid != null) {
                    new NetworkGet((Custom_Adapter) listView.getAdapter()).execute(gid);
                }
                else
                {
                    new NetworkGet((Custom_Adapter) listView.getAdapter()).execute(gid1);
                }


                refreshBtn.setText("새로고침");
            }
        });

        backBtn = (Button) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Id = getIntent();
                gid = Id.getStringExtra("MenuToList_id");
                Intent Id1 = getIntent();
                gid1 = Id1.getStringExtra("StoreToList_id");

                Intent listview = new Intent(foodListView.this, ActivityForMap.class);
                if(gid != null)
                {
                    listview.putExtra("ListToMap_id", gid);
                }
                else
                {
                    listview.putExtra("ListToMap_id", gid1);
                }
                startActivity(listview);
            }
        });

    }
}
