package net.ricerich.wanteat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button btnlogin = (Button) findViewById(R.id.btnLogin);
        Button btnjoin = (Button) findViewById(R.id.btnJoin);
        EditText edtId = (EditText) findViewById(R.id.edtId);
        EditText edtPwd = (EditText) findViewById(R.id.edtPwd);

        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent join = new Intent(MainActivity.this, join.class);
                startActivity(join);

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();
                String pwd = edtPwd.getText().toString().trim();

                new NetworkLogin(MainActivity.this).execute(id, pwd);

                Intent login = new Intent(MainActivity.this, ActivityForMap.class);

                login.putExtra("MainToMap_id", id);
                startActivity(login);

            }
        });//로그인 버튼


    }
}



