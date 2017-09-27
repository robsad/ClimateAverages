package pl.robertsadlowski.climateaverages.viewmodule.graphs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import pl.robertsadlowski.climateaverages.appmodule.model.entity.City;

public class GraphView {

    private Canvas canvas;
    private Paint paint;

    public GraphView (Canvas canvas, Paint paint) {
        this.canvas = canvas;
        this.paint = paint;
    }

    public void rysuj(City miasto) {
        int h = 1100;
        int d = 1400;
        double zeroYaxis = 0.85*h;
        double zeroXaxis = 0.08*d;
        double distXaxis = 0.01*d;
        double januaryX = 0.115*d;
        double monthJump = 0.07*d;
        double lineStep = 0.1*h;
        double step = 0.02*h;
        double firstline = 0.05*h;
        double textSize = h/30;
        String[] axis1test = { "40C", "35C", "30C", "25C", "20C", "15C", "10C", "5C", "0C", "-5C", "-10C" };
        String[] axis2test = { "12h", "35cm", "9h", "25cm", "6h", "15cm", "3h", "5cm", "0h", " ", " " };

        //clear(h,d);  //czyszczenie widoku
        //osie
        paint.setTextSize( Math.round(textSize));
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawRect(Math.round(zeroXaxis), Math.round(zeroYaxis-1), Math.round(d-zeroXaxis), Math.round(zeroYaxis+1), paint);
        for( int i = 0 ; i < 11 ; i++ ) {
            canvas.drawLine(Math.round(zeroXaxis), Math.round(firstline + (i * lineStep)), Math.round(d - zeroXaxis), Math.round(firstline + (i * lineStep)), paint);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(axis1test[i], Math.round(zeroXaxis-distXaxis), Math.round(firstline + (i * lineStep)+(textSize*0.4)), paint);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(axis2test[i], Math.round(d - zeroXaxis+distXaxis), Math.round(firstline + (i * lineStep)+(textSize*0.4)), paint);
        }
        paint.setStrokeWidth(3);
        for( int i = 0 ; i < 13 ; i++ ) {
            canvas.drawLine(Math.round(zeroXaxis+(i*monthJump)), Math.round(zeroYaxis-6),
                    Math.round(zeroXaxis+(i*monthJump)), Math.round(zeroYaxis+6), paint);
        }


        //temperatury
        Paint wallpaint = new Paint();
        wallpaint.setARGB(80, 50, 50, 200);
        wallpaint.setStyle(Paint.Style.FILL);
        Path wallpath = new Path();
        wallpath.reset();
        wallpath.moveTo(Math.round(januaryX), Math.round(zeroYaxis-(step*miasto.getTemp_min(0))));
        for( int i = 1 ; i < 12 ; i++ ) {
            wallpath.lineTo(Math.round(januaryX+(i*monthJump)), Math.round(zeroYaxis-(step*miasto.getTemp_min(i))));
        }
        for( int i = 11 ; i > -1 ; i-- ) {
            wallpath.lineTo(Math.round(januaryX+(i*monthJump)), Math.round(zeroYaxis-(step*miasto.getTemp_max(i))));
        }
        wallpath.close();
        canvas.drawPath(wallpath, wallpaint);

        //slonce
        paint.setColor(Color.parseColor("#CD5C5C"));
        paint.setARGB(155, 255, 0, 200);
        for( int i = 0 ; i < 12 ; i++ ) {
            if (i>0) canvas.drawLine(Math.round(januaryX+((i-1)*monthJump)), Math.round(zeroYaxis-(miasto.getSun_hours(i-1) * lineStep / 1.5)),
                    Math.round(januaryX+(i*monthJump)), Math.round(zeroYaxis-(miasto.getSun_hours(i) * lineStep / 1.5)), paint);
            canvas.drawCircle(Math.round(januaryX+(i*monthJump)),  Math.round(zeroYaxis-(miasto.getSun_hours(i) * lineStep / 1.5)), 10, paint);
        }

        //opady
        paint.setARGB(100, 100, 0, 100);
        for( int i = 0 ; i < 12 ; i++ ) {
            canvas.drawRect(Math.round(januaryX+(i*monthJump)-20), Math.round(zeroYaxis-(miasto.getRain_mm(i) * step/10)),
                    Math.round(januaryX+(i*monthJump)+20), Math.round(zeroYaxis), paint);
        }

        //dni opadów
        paint.setARGB(50, 100, 0, 100);
        for( int i = 0 ; i < 12 ; i++ ) {
            canvas.drawCircle(Math.round(januaryX+(i*monthJump)),  Math.round(zeroYaxis-(miasto.getRain_days(i) * step)), 20, paint);
        }


        paint.setARGB(255, 0, 0, 0);
        paint.setTextSize(Math.round(h/15));
        canvas.drawText(miasto.getCity(), Math.round(h/12), Math.round(h/8), paint);
    }

    public void clear(int h, int d) {
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(0,0,d,h,clearPaint);
    }


}
