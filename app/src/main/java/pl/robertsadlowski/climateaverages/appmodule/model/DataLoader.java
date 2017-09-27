package pl.robertsadlowski.climateaverages.appmodule.model;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import pl.robertsadlowski.climateaverages.R;
import pl.robertsadlowski.climateaverages.appmodule.model.entity.City;

public class DataLoader {

    private Resources resources;
    private Map<String,City> resultData = new HashMap<String,City>();
    private List<String> cityList = new ArrayList();
    private List<String> countryList = new ArrayList();
    private Map<String,String[]> countryCities = new HashMap<String,String[]>();

    public DataLoader (Resources resources) {
        this.resources = resources;
    }

    public Map<String,City> getCityData() {
        InputStream inputStream = resources.openRawResource(R.raw.weather);
        return loadCityData(inputStream);
    }

    private Map<String,City> loadCityData(InputStream inputStream) {
        Map<String, String> countryByCode = new HashMap<>();
        String csvLine;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((csvLine = br.readLine()) != null) {
                String[] dataArray = csvLine.split(";");
                City city = new City(dataArray);
                resultData.put(city.getCity(),city);
                if (!countryList.contains(city.getCountry())) countryList.add(city.getCountry());
                cityList.add(city.getCity());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
        return resultData;

    }

    public Map getMap(){
        return resultData;
    }

    public List getCityList(){
        Collections.sort(cityList);
        return cityList;
    }

    public List getCountryList(){
        Collections.sort(countryList);
        return countryList;
    }

    public String[] getCountryArray(){
        String[] countryArray = countryList.toArray(new String[countryList.size()]);
        return countryArray;
    }

    public Map getCityArray(){
        List<String> cityListFiltered = new ArrayList();
        String[] cityArray;
        String citytest;
        String krajtest;

        for (Iterator<String> kraj = countryList.iterator(); kraj.hasNext();)  {
            krajtest = kraj.next();
            for (Iterator<String> miasto=cityList.iterator(); miasto.hasNext();) {
                citytest = miasto.next();
                City testCityObject = resultData.get(citytest);
                if (krajtest.equals(testCityObject.getCountry())) cityListFiltered.add(citytest);
            }
            cityArray = cityListFiltered.toArray(new String[cityListFiltered.size()]);
            cityListFiltered.clear();
            countryCities.put(krajtest,cityArray);
        }
        return countryCities;
    }

}
