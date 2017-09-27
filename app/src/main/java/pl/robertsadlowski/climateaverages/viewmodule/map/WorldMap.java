package pl.robertsadlowski.climateaverages.viewmodule.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import pl.robertsadlowski.climateaverages.R;
import pl.robertsadlowski.climateaverages.appmodule.model.entity.City;

public class WorldMap {

    private Canvas canvas;
    private Paint paint;
    private Bitmap tlo;
    private Bitmap bg;
    private Map<String, City> weatherData = new HashMap<String, City>();

    public void init(LinearLayout ll, Resources res, Map weatherData) {
        this.weatherData = weatherData;
        paint = new Paint();
        tlo = BitmapFactory.decodeResource(res, R.drawable.world);

        int height = tlo.getHeight();
        int width = tlo.getWidth();
        double y = Math.sqrt(400000 / (((double) width) / height));
        double x = (y / height) * width;
        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(tlo, (int) x, (int) y, true);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(tlo, 1720, 756, true);
        tlo.recycle();
        tlo = scaledBitmap;
        bg = Bitmap.createBitmap(1720, 756, Bitmap.Config.ARGB_8888);
        // bg = tlo.copy(Bitmap.Config.ARGB_8888, true);
        canvas = new Canvas(bg);
        ll.setBackground(new BitmapDrawable(res, bg));
    }


    public void rysuj(String country, String city, String[] cityList) {

        int h = 756;
        int d = 1720;
        double x;
        double y;
        City citydata;

        clear(h,d);
        for(int i =0; i < cityList.length; i++) {
            citydata = weatherData.get(cityList[i]);
            paint.setARGB(255, 0, 255, 0);
            x = citydata.getX()*2;
            y = citydata.getY()*2;
            canvas.drawCircle(Math.round(x), Math.round(y), 12, paint);
        }
        citydata = weatherData.get(city);
        paint.setARGB(255, 255, 0, 0);
        x = citydata.getX()*2;
        y = citydata.getY()*2;
        canvas.drawCircle(Math.round(x), Math.round(y), 12, paint);
    }

    public void clear(int h, int d) {
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(0,0,d,h,clearPaint);
        canvas.drawBitmap(tlo, 0, 0, null);
    }

}
