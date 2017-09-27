package pl.robertsadlowski.climateaverages.viewmodule.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager ;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

import pl.robertsadlowski.climateaverages.appmodule.model.entity.City;
import pl.robertsadlowski.climateaverages.viewmodule.fragments.GraphFragment;

public class GraphPagerAdapter extends FragmentStatePagerAdapter {

    private Map<String, City> weatherData = new HashMap<String, City>();
    String[] cityList;

    public GraphPagerAdapter(FragmentManager fm, String[] cityList, Map<String, City> weatherData) {
        super(fm);
        this.weatherData = weatherData;
        this.cityList = cityList;
    }

    @Override
    public Fragment getItem(int position) {
        City citydata;
        citydata = weatherData.get(cityList[position]);
        return GraphFragment.newInstance(citydata);
    }

    @Override
    public int getCount() {
        return this.cityList.length;
    }

}

