package net.ricerich.wanteat;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.net.InterfaceAddress;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;

public class Custom_Adapter extends BaseAdapter {
    Activity mAct;
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<UserInfo> mUserInfoObjArr;
    int mListLayout;

    public Custom_Adapter(Activity a, int listLayout, ArrayList<UserInfo> userInfoObjArrayListT){
        mAct = a;
        mListLayout = listLayout;
        mUserInfoObjArr = userInfoObjArrayListT;
        mInflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDatas(ArrayList<UserInfo> arrayList){
        mUserInfoObjArr = arrayList;
    }

    @Override
    public int getCount() {
        return mUserInfoObjArr.size();
    }

    @Override
    public Object getItem(int i) {
        return mUserInfoObjArr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view = mInflater.inflate(mListLayout,viewGroup, false);

        final TextView tvId = (TextView) view.findViewById(R.id.tv_id);
        tvId.setText(mUserInfoObjArr.get(i).id);

        final TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        tvName.setText(mUserInfoObjArr.get(i).name);

        final TextView tvMemo = (TextView) view.findViewById(R.id.tv_memo);
        tvMemo.setText(mUserInfoObjArr.get(i).memo);

        final TextView tvLati = (TextView) view.findViewById(R.id.tv_lati);
        tvLati.setText(mUserInfoObjArr.get(i).lati);

        final TextView tvLongi = (TextView) view.findViewById(R.id.tv_longi);
        tvLongi.setText(mUserInfoObjArr.get(i).longi);

        Button btnmap = (Button) view.findViewById(R.id.btnmap);
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id   = tvId.getText().toString();
                String sLat = tvLati.getText().toString();
                String sLng = tvLongi.getText().toString();
                double dLat = Double.parseDouble(sLat);
                double dLng = Double.parseDouble(sLng);

                Intent coordiNid = new Intent(mAct.getApplicationContext(), ActivityForMap.class);
                if(dLat != 0.0 && dLng != 0.0) {
                    coordiNid.putExtra("AdapToMap_id", id);
                    coordiNid.putExtra("Lat", dLat);
                    coordiNid.putExtra("Lng", dLng);
                    System.out.println(id);
                    System.out.println(dLat);
                    System.out.println(dLng);
                }
                mAct.startActivity(coordiNid);
            }
        });
        
        Button btndel = (Button) view.findViewById(R.id.btndel);
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String id = tvId.getText().toString();
                    String sLat = tvLati.getText().toString();
                    String sLng = tvLongi.getText().toString();
                    new NetworkDelete(Custom_Adapter.this).execute(id, sLat, sLng);
                    Toast.makeText(mAct.getApplicationContext(), "항목이 삭제되었습니다. \n새로고침을 눌러주세요", Toast.LENGTH_SHORT).show();
            }
        });

    return view;
    }
}