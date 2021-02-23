package net.ricerich.wanteat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityForMap extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private GpsTracker gt;
    double lati, longi;
    String gid, gid1, gid2, gid3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodmap);
        Intent Id = getIntent();
        gid = Id.getStringExtra("MainToMap_id");
        Intent Id1 = getIntent();
        gid1 = Id1.getStringExtra("AdapToMap_id");
        Intent Id2 = getIntent();
        gid2 = Id2.getStringExtra("ListToMap_id");
        Intent Id3 = getIntent();
        gid3 = Id3.getStringExtra("StoreToMap_id");

        if(gid != null)
        {
            setTitle(gid + "님의 Food Map");
        }
        else if (gid1 != null)
        {
            setTitle(gid1 + "님의 Food Map");
        }
        else if (gid2 != null)
        {
            setTitle(gid2 + "님의 Food Map");
        }
        else
        {
            setTitle(gid3 + "님의 Food Map");
        }



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(ActivityForMap.this);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lati = location.getLatitude();
                longi = location.getLongitude();
            }
            public void onStatusChanged(String provider, int status, Bundle extras){}
            public void onProviderEnabled(String provider){}
            public void onProviderDisabled(String provider){}
        };

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        double lat, lng;
        Intent Id = getIntent();
        gid = Id.getStringExtra("MainToMap_id");

        Intent getLL = getIntent();
        lat = getLL.getDoubleExtra("Lat", 0.0);
        lng = getLL.getDoubleExtra("Lng", 0.0);

        if(lat != 0.0 || lng != 0.0){
            LatLng pointerLatLng = new LatLng(lat, lng);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_icon2));
            markerOptions.position(pointerLatLng); //마커위치설정(point = 좌표)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(pointerLatLng));   // 마커생성위치로 이동
            mMap.addMarker(markerOptions); //마커 생성
        }
        else
        {
            gt = new GpsTracker(net.ricerich.wanteat.ActivityForMap.this);
    //        lati = gt.getLatitude();
    //        longi = gt.getLongitude(); // 폰에서 GPS값 받은 위치
            lati = 35.133663;
            longi = 129.059151;//임시 좌표
            LatLng nowLatLng = new LatLng(lati, longi);
            mMap.addMarker(new MarkerOptions().position(nowLatLng).title("현재위치"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati, longi), 15));
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }// 시작할때는 GPS좌표값, 리스트에서 지도로 넘어올때는 지정 좌표값

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_icon2));
                markerOptions.position(point); //마커위치설정(point = 좌표)

                mMap.animateCamera(CameraUpdateFactory.newLatLng(point));   // 마커생성위치로 이동
                mMap.addMarker(markerOptions); //마커 생성

                double markerLa = point.latitude;
                double markerLo = point.longitude; //마커 위치 좌표값 저장


                final View v = getLayoutInflater().inflate(R.layout.dialog_marker, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityForMap.this);
                builder.setTitle("맛집 정보를 입력하시겠습니까?");
                builder.setView(v);
                builder.show(); //롱클릭 다이얼로그 생성

                Button btnsetMarker = (Button) v.findViewById(R.id.marker1);

                btnsetMarker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent Id = getIntent();
                        gid = Id.getStringExtra("MainToMap_id");
                        Intent Id1 = getIntent();
                        gid1 = Id1.getStringExtra("AdapToMap_id");
                        Intent Id2 = getIntent();
                        gid2 = Id2.getStringExtra("ListToMap_id");
                        Intent Id3 = getIntent();
                        gid3 = Id3.getStringExtra("StoreToMap_id");
                        String mLa = Double.toString(markerLa); // String 으로 변환한 값
                        String mLo = Double.toString(markerLo); // String 으로 변환한 값


                        Intent inte = new Intent(ActivityForMap.this, FoodStoreInfo.class);
                        inte.putExtra("lati", mLa);
                        inte.putExtra("longi", mLo);
                        if(gid != null)
                        {
                            inte.putExtra("MapToStore_id", gid);
                        }
                        else if (gid1 != null)
                        {
                            inte.putExtra("MapToStore_id", gid1);
                        }
                        else if (gid2 != null)
                        {
                            inte.putExtra("MapToStore_id", gid2);
                        }
                        else
                        {
                            inte.putExtra("MapToStore_id", gid3);
                        }
                        startActivity(inte);
                    }
                });
            }
        });//마커 생성하고 생성된 위치 좌표값 DB로 전송

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker){
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityForMap.this);
                builder.setMessage("맛집 목록에서 지우시겠습니까?");

                builder.setPositiveButton("네 지울게요.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        marker.remove();//마커 원클릭 삭제
                    }
                });

                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.show();
                return true;
            }
        });//마커 원클릭
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "현재위치 바로가기");
        menu.add(0, 2, 0, "맛집 리스트");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                GpsTracker gt = new GpsTracker(net.ricerich.wanteat.ActivityForMap.this);
                lati = gt.getLatitude();
                longi = gt.getLongitude();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati, longi), 15));
                return true;

            case 2:
                Intent listview = new Intent(ActivityForMap.this, foodListView.class);
                Intent Id = getIntent();
                gid = Id.getStringExtra("MainToMap_id");
                Intent Id1 = getIntent();
                gid1 = Id1.getStringExtra("AdapToMap_id");
                Intent Id2 = getIntent();
                gid2 = Id2.getStringExtra("ListToMap_id");
                Intent Id3 = getIntent();
                gid3 = Id3.getStringExtra("StoreToMap_id");

                if(gid != null)
                {
                    listview.putExtra("MenuToList_id", gid);
                }
                else if (gid1 != null)
                {
                    listview.putExtra("MenuToList_id", gid1);
                }
                else if (gid2 != null)
                {
                    listview.putExtra("MenuToList_id", gid2);
                }
                else
                {
                    listview.putExtra("MenuToList_id", gid3);
                }
                startActivity(listview);
        }
        return false;
    }


}




//Log.v("tag명", "표시할내용 : ");

//Log.v("TEST", "res : " + res );