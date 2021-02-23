package net.ricerich.wanteat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

public class FoodStoreInfo extends ActivityForMap {

    String name;
    String memo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodstoreinfo);
        Intent Id = getIntent();
        gid = Id.getStringExtra("MapToStore_id");
        setTitle(gid + "님의 맛집 평가");

        EditText edtName = (EditText) findViewById(R.id.storeName);
        EditText edtmemo = (EditText) findViewById(R.id.storeMemo);

        Button btnsave = (Button) findViewById(R.id.btnSave);
        Button btncancel = (Button) findViewById(R.id.btnCancel);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent get = getIntent();
                String gid = get.getStringExtra("MapToStore_id");
                String mLa = getIntent().getStringExtra("lati");
                String mLo = getIntent().getStringExtra("longi");
                name = edtName.getText().toString();
                memo = edtmemo.getText().toString();

                new NetworkMarkerLatLng(FoodStoreInfo.this).execute(gid, name, memo, mLa, mLo);
                Intent in = new Intent(FoodStoreInfo.this, foodListView.class);
                in.putExtra("StoreToList_id", gid);
                System.out.println(gid);
                startActivity(in);
            }

        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent get = getIntent();
                String gid = get.getStringExtra("MapToStore_id");
                Intent in = new Intent(FoodStoreInfo.this, ActivityForMap.class);
                in.putExtra("StoreToMap_id", gid);
                System.out.println(gid);
                startActivity(in);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                GpsTracker gt = new GpsTracker(net.ricerich.wanteat.FoodStoreInfo.this);
                lati = gt.getLatitude();
                longi = gt.getLongitude();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati, longi), 15));
                return true;

            case 2:
                Intent listview = new Intent(FoodStoreInfo.this, foodListView.class);
                Intent Id = getIntent();
                gid = Id.getStringExtra("MapToStore_id");
                    listview.putExtra("MapToStore_id", gid);
        }
        return true;
    }
}
