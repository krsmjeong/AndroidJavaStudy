package net.ricerich.wanteat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;


public class join extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_join);

        Button dupChk = (Button) findViewById(R.id.dupChk);
        Button btnjoin = (Button) findViewById(R.id.join);
        Button cancel = (Button) findViewById(R.id.cancel);

        dupChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ((EditText) findViewById(R.id.join_id)).getText().toString();
                Button btnjoin = (Button) findViewById(R.id.join);

                try {
                    String res = new NetworkJoinCheck(join.this).execute(id).get();
                    int count = JsonParser.getResultJson(res);
                    if (count == 1)
                    {
                        Toast.makeText(join.this, "이미 존재하는 id 입니다", Toast.LENGTH_SHORT).show();
                        btnjoin.setEnabled(false);
                        return;
                    }
                    else
                    {
                        Toast.makeText(join.this, "사용 가능한 id 입니다", Toast.LENGTH_SHORT).show();
                        btnjoin.setEnabled(true);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                btnjoin.setEnabled(true);
            }
        });

        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ((EditText) findViewById(R.id.join_id)).getText().toString();
                String pwd = ((EditText) findViewById(R.id.join_pwd)).getText().toString();
                String name = ((EditText) findViewById(R.id.join_name)).getText().toString();
                String age = ((EditText) findViewById(R.id.join_age)).getText().toString();
                String phone = ((EditText) findViewById(R.id.join_phone)).getText().toString();

                if(id.equals("") || pwd.equals("") || name.equals("") || age.equals("") || phone.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(join.this);
                    builder.setMessage("빈 칸 없이 입력해주세요.");
                    builder.setNegativeButton("확인", null);
                    builder.create();
                    builder.show();
                    return;
                }
                else
                {
                    new NetworkJoin(join.this).execute(id, pwd, name, age, phone);
                }
                Intent back = new Intent(join.this, MainActivity.class);
                startActivity(back);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(join.this, MainActivity.class);
                startActivity(back);
            }
        });

    }
}