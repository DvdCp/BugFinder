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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences sp;
    private TextView bugTypeFULL, bugName, bugOrdine, bugProvenienza, bugDescription, bugDifese, loginText;

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
            String nome = sp.getString("name", "");
            loginText.setText("Ciao "+nome);
        }
    }


    // HANDLING BACK BUTTON
    @Override
    public void onBackPressed() {
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

        LatLngBounds boundsItaly = new LatLngBounds(new LatLng(40.666397,10.172663), new LatLng(42.0914, 15.120292)); //SUD, OVEST - NORD, EST

        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.setOnMarkerClickListener(MainActivity.this);
        googleMap.setMinZoomPreference(5.5f);

        googleMap.addMarker(new MarkerOptions().position(rome).title("Ciao1").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        googleMap.addMarker(new MarkerOptions().position(ischia).title("Ciao4").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(rome));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(6),400, null);

        // Constrain the camera target to the Adelaide bounds.
        //googleMap.setLatLngBoundsForCameraTarget(boundsItaly);
    }


    public void onClickMenuItem(View v)
    {
        int optionID = v.getId();
        switch (optionID)
        {
            case R.id.user_area:
                login();
                break;
            case R.id.bug_book:
                bugBook();
                break;
            case R.id.search_bug:
                Toast.makeText(getApplicationContext(), "SEARCH BUG: "+optionID, Toast.LENGTH_LONG ).show();
                break;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "SETTINGS: "+optionID, Toast.LENGTH_LONG ).show();
                break;

        }
    }

    public void newReport(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Report.class);

        startActivity(intent);
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