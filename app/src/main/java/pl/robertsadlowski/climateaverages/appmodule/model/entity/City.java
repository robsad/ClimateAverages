package pl.robertsadlowski.climateaverages.appmodule.model.entity;

import java.io.Serializable;

public class City implements Serializable {
    private String cityName;
    private String country;
    private int x;
    private int y;
    private double[] temp_min = new double[12];
    private double[] temp_max = new double[12];
    private double[] rain_mm = new double[12];
    private double[] rain_days = new double[12];
    private double[] sun_hours = new double[12];

    public City(String[] line) {
        this.country = line[0];
        this.cityName = line[1];
        this.x = Integer.parseInt(line[2]);
        this.y = Integer.parseInt(line[3]);
        for (int i=0; i<12; i++) this.temp_min[i] = Double.parseDouble(line[i+4]);
        for (int i=0; i<12; i++) this.temp_max[i] = Double.parseDouble(line[i+16]);
        for (int i=0; i<12; i++) this.rain_mm[i] = Double.parseDouble(line[i+28]);
        for (int i=0; i<12; i++) if (!(line[i+40].equals(""))) this.rain_days[i] = Double.parseDouble(line[i+40]); else this.rain_days[i]=0;
        for (int i=0; i<12; i++) if (!(line[i+52].equals(""))) this.sun_hours[i] = Double.parseDouble(line[i+52]); else this.sun_hours[i]=0;
    }

    public String getCity() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getTemp_min(int i) {
        return temp_min[i];
    }

    public double getTemp_max(int i) {
        return temp_max[i];
    }

    public double getRain_mm(int i) {
        return rain_mm[i];
    }

    public double getRain_days(int i) {
        return rain_days[i];
    }

    public double getSun_hours(int i) {
        return sun_hours[i];
    }

}
