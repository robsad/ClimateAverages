package pl.robertsadlowski.climateaverages.viewmodule.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.robertsadlowski.climateaverages.appmodule.model.entity.City;
import pl.robertsadlowski.climateaverages.viewmodule.graphs.GraphView;

public class GraphFragment extends Fragment {

    City miasto;


    public static GraphFragment newInstance(City citydata) {
        GraphFragment fragmentFirst = new GraphFragment();
        Bundle args = new Bundle();
        args.putSerializable("Miasto", citydata);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miasto = (City) getArguments().getSerializable("Miasto");
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.item_layout, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        View wykres = new DrawView(this.getActivity());
        return wykres;
    }



    private class DrawView extends View {
        Paint paint = new Paint();

        public DrawView(Context context) {
            super(context);
            paint.setColor(Color.BLACK);
        }
        public DrawView(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setColor(Color.BLACK);
        }
        public DrawView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            paint.setColor(Color.BLACK);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            GraphView wykres = new GraphView(canvas, paint);
            wykres.rysuj(miasto);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            this.setMeasuredDimension(parentWidth, parentHeight);
        }
    }




}
