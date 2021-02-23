package net.ricerich.wanteat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfo extends AppCompatActivity {
    String id, name, memo, lati, longi;
    public UserInfo(String id, String name, String memo, String lati, String longi) {
        this.id    = id;
        this.name  = name;
        this.memo  = memo;
        this.lati  = lati;
        this.longi = longi;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodlistview);
    }
}

