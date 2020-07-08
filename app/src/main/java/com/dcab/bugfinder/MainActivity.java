package com.dcab.bugfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences sp;
    private LinearLayout user_area, reports, logout;
    private TextView bugTypeFULL, bugName, bugOrdine, bugProvenienza, bugDescription, bugDifese, loginText, okTW;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_maps);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        if((sp.getBoolean("logged", false)) == true)
            sp.edit().clear().commit();

        drawerLayout = findViewById(R.id.drawer_layout_main_screen);
        navigationView = findViewById(R.id.nav_view_side_menu);
        loginText = findViewById(R.id.loginText);

        loginText.setText("");

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if((sp.getBoolean("logged", false)) == true)
        {
            drawerLayout.closeDrawer(GravityCompat.START);
            String nome = sp.getString("name", "");
            loginText.setText("Ciao "+nome);

            reports = findViewById(R.id.reports);
            logout = findViewById(R.id.logout);

            reports.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
        }
    }


    // HANDLING BACK BUTTON
    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        // Add a marker in Rome, Australia,
        // and move the map's camera to the same location.
        LatLng rome = new LatLng(41.981807, 12.0819104);
        LatLng ischia = new LatLng(40.953945, 15.624557);

        int height = 70;
        int width = 70;

        BitmapDrawable bitmapdraw = (BitmapDrawable) getDrawable(R.drawable.bug_rilevation);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.setOnMarkerClickListener(MainActivity.this);
        googleMap.setMinZoomPreference(5.5f);

        googleMap.addMarker(new MarkerOptions().position(rome).title("Ciao1").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        googleMap.addMarker(new MarkerOptions().position(ischia).title("Ciao4").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(rome));
    }

    public void onClickMenuItem(View v)
    {
        int optionID = v.getId();
        switch (optionID)
        {
            case R.id.user_area:
                if( (sp.getBoolean("logged", false)) )
                {
                    toUserArea();
                } else {
                    login();
                }
                break;
            case R.id.bug_book:
                bugBook();
                break;
            case R.id.search_bug:
                Toast.makeText(getApplicationContext(), "FUNZIONALITA' IN SVILUPPO", Toast.LENGTH_LONG ).show();
                break;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "FUNZIONALITA' IN SVILUPPO", Toast.LENGTH_LONG ).show();
                break;
            case R.id.reports:
                toMyReports();
                break;
            case R.id.logout:
                logout();
                break;
        }
    }


    public void newReport(View v)
    {
        // controllare se l'utente è loggato
        if( (sp.getBoolean("logged", false)) )
        {
            Intent intent = new Intent(getApplicationContext(), Report.class);
            startActivity(intent);
        } else {
            login();
        }

    }

    public void bugBook()
    {
        Intent intent = new Intent(getApplicationContext(), BugBook.class);

        startActivity(intent);
    }

    public void login()
    {
        Intent intent = new Intent(getApplicationContext(), Login.class);

        startActivity(intent);
    }

    public void logout()
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_ok);
        Log.d("DEBUG", "logout effettuato");
        okTW = dialog.findViewById(R.id.okTW);
        okTW.setText("Logout effettuato");
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setTitle("");
        dialog.setCancelable(true);

        new CountDownTimer(3000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish()
            {
                dialog.dismiss();
                sp.edit().clear().commit();
                recreate();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }.start();

        //now that the dialog is set up, it's time to show it
        dialog.show();
    }


    public void toUserArea()
    {
        Intent intent = new Intent(getApplicationContext(), UserArea.class);

        startActivity(intent);
    }

    public void toMyReports()
    {
        Intent intent = new Intent(getApplicationContext(), ListReports.class);
        Log.d("DEBUG", "Start activity reports");
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        BugUIBean bugBean = null;

        for(BugUIBean bugUIBean : bugBeans)
        {
            if(bugUIBean.getNome().equals(marker.getTitle()))
            {
                bugBean = new BugUIBean();

                bugBean.setNome(bugUIBean.nome);
                bugBean.setDescription(bugUIBean.description);
                bugBean.setDifese(bugUIBean.difese);
                bugBean.setOrdine(bugUIBean.ordine);
                bugBean.setProvenienza(bugUIBean.provenienza);
                bugBean.setType(bugUIBean.type);
            }
        }

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.insetto_page);

        bugTypeFULL = dialog.findViewById(R.id.bugTypeFULL);
        bugName = dialog.findViewById(R.id.bugName);
        bugOrdine = dialog.findViewById(R.id.bugOrdine);
        bugProvenienza = dialog.findViewById(R.id.bugProvenienza);
        bugDescription = dialog.findViewById(R.id.bugDescription);
        bugDifese = dialog.findViewById(R.id.bugDifese);

        bugTypeFULL.setText(bugBean.getType());
        bugName.setText(bugBean.getNome());
        bugOrdine.setText(bugBean.getOrdine());
        bugProvenienza.setText(bugBean.getProvenienza());
        bugDescription.setText(bugBean.getDescription());
        bugDifese.setText(bugBean.getDifese());


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setTitle("");
        dialog.setCancelable(true);

        //set up button
        ImageView backArrow = (ImageView) dialog.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        //now that the dialog is set up, it's time to show it
        dialog.show();

        return true;
    }

    BugUIBean[] bugBeans = {
            new BugUIBean("Ciao1", "Ciao1", "Ciao1", "Ciao1", "Ciao1", "Ciao1"),
            new BugUIBean("Ciao2", "Ciao2", "Ciao2", "Ciao2", "Ciao2", "Ciao2"),
            new BugUIBean("Ciao3", "Ciao3", "Ciao1", "Ciao3", "Ciao3", "Ciao3"),
            new BugUIBean("Ciao4", "Ciao4", "Ciao4", "Ciao4", "Ciao4", "Ciao4"),
            new BugUIBean("Ciao5", "Ciao5", "Ciao5", "Ciao5", "Ciao5", "Ciao5"),
            new BugUIBean("Ciao6", "Ciao6", "Ciao6", "Ciao6", "Ciao6", "Ciao6"),
            new BugUIBean("Ciao7", "Ciao7", "Ciao7", "Ciao7", "Ciao7", "Ciao7"),
    };

}