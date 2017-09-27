package pl.robertsadlowski.climateaverages.viewmodule.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.robertsadlowski.climateaverages.R;
import pl.robertsadlowski.climateaverages.appmodule.model.DataLoader;
import pl.robertsadlowski.climateaverages.appmodule.model.entity.City;
import pl.robertsadlowski.climateaverages.viewmodule.adapters.GraphPagerAdapter;
import pl.robertsadlowski.climateaverages.viewmodule.map.WorldMap;

public class MainActivity extends AppCompatActivity {

    Button buttonCountry;
    Button buttonCity;
    private Map<String, City> weatherData = new HashMap<String, City>();
    private List<String> cityList = new ArrayList();
    private List<String> countryList = new ArrayList();
    String[] countryArray;
    Map<String,String[]> countryCities = new HashMap<String,String[]>();
    private String country = "Poland";
    private String city = "Poznan";
    private TextView mTextMessage;
    private PagerAdapter mPagerAdapter;
    private ViewPager pager;
    private WorldMap mapa;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Resources resources = getResources();
        pager = (ViewPager) super.findViewById(R.id.wykrespager);
        DataLoader dataloader = new DataLoader(resources);
        weatherData = dataloader.getCityData();
        countryList = dataloader.getCountryList();
        cityList = dataloader.getCityList();
        countryArray = dataloader.getCountryArray();
        countryCities = dataloader.getCityArray();

        buttonCountry = (Button)findViewById(R.id.countryID);
        buttonCountry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openDialogCountry();
            }});

        buttonCity = (Button)findViewById(R.id.cityID);
        buttonCity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                openDialogCity();
            }});


        setNewAdapter(country);
        mapa = new WorldMap();
        LinearLayout mapaLayout = (LinearLayout) findViewById(R.id.mapa);
        mapa.init(mapaLayout, getResources(), weatherData);
        mapa.rysuj(country, city, countryCities.get(country));
    }

    private void setNewAdapter(final String country) {
        final String[] cityList;
        cityList = countryCities.get(country);
        mPagerAdapter = new GraphPagerAdapter(super.getSupportFragmentManager(), cityList, weatherData);
        mPagerAdapter.notifyDataSetChanged();
        pager.setAdapter(mPagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int arg0) {
            }
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            public void onPageSelected(int currentPage) {
                //currentPage is the position that is currently displayed.
                String citynow = cityList[currentPage];
                //buttonCity.setText(citynow);
                mapa.rysuj(country, citynow, countryCities.get(country));
            }
        });
    }

    private void updatePageCount(ViewPager viewPager) {
        int currentItem = pager.getCurrentItem();
        pager.setAdapter(pager.getAdapter());
        pager.setCurrentItem(currentItem);
    }

    private void openDialogCountry(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(MainActivity.this);
        myDialog.setTitle("Choose country");
        myDialog.setItems(countryArray, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = countryArray[which];
                buttonCountry.setText(item);
                country=item;
                String[] cityArray = countryCities.get(country);
                city=cityArray[0];
                buttonCity.setText(city);
                setNewAdapter(country);
                mapa.rysuj(country, city, countryCities.get(country));
            }});
        myDialog.setNegativeButton("Cancel", null);
        myDialog.show();
    }

    private void openDialogCity(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(MainActivity.this);
        myDialog.setTitle("Choose city");
        String[] cityArray = countryCities.get(country);
        myDialog.setItems(cityArray, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] cityArray = countryCities.get(country);
                String item = cityArray[which];
                //Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                buttonCity.setText(item);
                city = item;
                //rysowanie wykresow

                mapa.rysuj(country, city, countryCities.get(country));
            }});
        myDialog.setNegativeButton("Cancel", null);
        myDialog.show();
    }


}
