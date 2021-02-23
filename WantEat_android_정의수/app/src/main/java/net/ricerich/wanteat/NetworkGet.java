package net.ricerich.wanteat;

import android.app.Activity;
import android.os.AsyncTask;
import android.telephony.CarrierConfigManager;
import android.util.Log;
import android.widget.Toast;


import net.ricerich.wanteat.Custom_Adapter;
import net.ricerich.wanteat.JsonParser;
import net.ricerich.wanteat.UserInfo;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NetworkGet extends AsyncTask<String, Void, String>/*execute는 DoInBackground이다.*/ {
    private URL Url;
    private String URL_Address = "http://192.168.123.192/JessProject/Wanteat_StoreInfo.jsp";
    private Custom_Adapter adapter;
    Activity mAct;

    public NetworkGet(Custom_Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String[] strings) {
        String res = "";
        try{
            Url = new URL(URL_Address);
            HttpURLConnection conn = (HttpURLConnection) Url.openConnection();

            conn.setDefaultUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

            StringBuffer buffer = new StringBuffer();
            buffer.append("id").append("=").append(strings[0]);

            OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(),"utf-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();

            StringBuilder builder= new StringBuilder();
            BufferedReader in = new BufferedReader((new InputStreamReader(conn.getInputStream(),"utf-8")));
            String line;
            while ((line = in.readLine()) != null){
                builder.append(line + "\n");
            }
            res = builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Get Result",res);
        return res;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);

        ArrayList<UserInfo> userList = new ArrayList<UserInfo>();
        int count = 0;
        try{
            count = JsonParser.getUserInfoJson(s, userList);
        }catch (JSONException e){
            e.printStackTrace();
        }
        if(count ==0){
        }else{
            adapter.setDatas(userList);
            adapter.notifyDataSetInvalidated();
        }
    }
}


//execute -> DoInBackground -> onPostExcute 순서로 실행됨