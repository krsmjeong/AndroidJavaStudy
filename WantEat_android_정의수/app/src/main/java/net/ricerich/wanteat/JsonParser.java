 package net.ricerich.wanteat;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    static public int getUserInfoJson(String response, ArrayList<UserInfo> userList) throws JSONException {
                                        //꽉찬거           여기에 넣음
        String id, name, memo, lati, longi;

        JSONObject rootJSON = new JSONObject(response);
        JSONArray jsonArray = new JSONArray(rootJSON.getString("datas"));

        for(int i=0; i<jsonArray.length();i++){
            JSONObject jsonObj = (JSONObject)jsonArray.get(i);

            if(jsonObj.getString("ID").toString().equals("null"))
                id="-";
            else
                id=jsonObj.getString("ID").toString();

            if(jsonObj.getString("NAME").toString().equals("null"))
                name="-";
            else
                name=jsonObj.getString("NAME").toString();

            if(jsonObj.getString("MEMO").toString().equals("null"))
                memo="-";
            else
                memo=jsonObj.getString("MEMO").toString();

            if (jsonObj.getString("LATI").toString().equals("null"))
                lati="-";
            else {
                lati = jsonObj.getString("LATI").toString();
            }

            if (jsonObj.getString("LONGI").toString().equals("null"))
                longi="-";
            else {
                longi = jsonObj.getString("LONGI").toString();
            }
            userList.add(new UserInfo(id, name, memo, lati, longi));
            //이 작업을 통해서 형태 안에 꽉 채워 넣음
        }
        return jsonArray.length();
    }

    static public int getResultJson(String response) throws JSONException{
        JSONArray jsonArray = new JSONArray(response);
        JSONObject jsonObject = new JSONObject(jsonArray.getString(0));
        return Integer.parseInt(jsonObject.getString("RESULT_OK"));
    }
}
