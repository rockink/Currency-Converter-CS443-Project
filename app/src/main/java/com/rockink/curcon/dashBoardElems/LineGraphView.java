package com.rockink.curcon.dashBoardElems;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.rockink.curcon.R;
import com.rockink.curcon.urlRequests.BulkDataContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 *
 *  Responsible for showing the graph.
 *
 */

public class LineGraphView extends SurfaceView implements SurfaceHolder.Callback {

    private final int[] graphColors;
    private Paint linePaint = null;
    private Paint textPaint = null;

    LineGraphDrawer lineGraphDrawer;
    private HashMap<String, BulkDataContainer> currencyTobulkDatas = new HashMap<>();
    private int days = 90;

    //this baiscally means to run the thread again...
    public void  updateGraph(HashMap<String, BulkDataContainer> bulkDatas) {

        this.currencyTobulkDatas = bulkDatas;

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);


        invalidate();

    }

    public void addCurrency() {
        Log.d(TAG, "addCurrency: Adding Currency");
    }

    public void updateDays(int days) {
        this.days = days;
        invalidate();

    }

    public static class CurrencyData{
        public int day;
        public float curVal;

        public CurrencyData(int day, float val) {
            this.day = day;
            this.curVal = val;;
        }
    }


    public LineGraphView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();

        graphColors = new int[]{
                ContextCompat.getColor(getContext(), R.color.indigo),
                ContextCompat.getColor(getContext(), R.color.red),
                ContextCompat.getColor(getContext(), R.color.teal),
                ContextCompat.getColor(getContext(), R.color.graphA)
        };

    }
    public LineGraphView(Context context) {
        super(context);
        init();

        graphColors = new int[]{
                ContextCompat.getColor(getContext(), R.color.indigo),
                ContextCompat.getColor(getContext(), R.color.red),
                ContextCompat.getColor(getContext(), R.color.teal),
                ContextCompat.getColor(getContext(), R.color.graphA)
        };


    }



    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(12);

        textPaint = new Paint();
        textPaint.setColor(Color.BLUE);
        textPaint.setStrokeWidth(10);


        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);


//        lineGraphDrawer = new LineGraphDrawer(surfaceHolder, textPaint, linePaint);

    }

    float scaleX, scaleY;


    ArrayList<CurrencyData> currencyDatas = new ArrayList<>();
    {
        Random random = new Random(800);
        for(int i = 0; i < 10; i++) {
            currencyDatas.add(new CurrencyData(i, random.nextInt(56)));
        }
    }

    Rect bounds = new Rect();
    Random random = new Random();

    @Override
    protected void onDraw(Canvas canvas) {
        lineGraphDrawer = new LineGraphDrawer(canvas, textPaint, linePaint,currencyTobulkDatas, days, graphColors);
        lineGraphDrawer.run(ContextCompat.getColor(getContext(), R.color.white));

    }

    /**
     *  @param currency
     * @param bounds
     * @param maxHeight
     *
     */
    private float[] scaleTheData(CurrencyData currency, Rect bounds, int maxHeight) {
        float xPoint = scaleX * currency.day;
        float yPoint = scaleY * currency.curVal;
        return new float[]{xPoint, yPoint};
    }


    public static String TAG  = "LineGraphView";
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
        Log.d(TAG, "surfaceCreated: " + "sufraceCreated ");
        Canvas c = holder.lockCanvas();
        draw(c);
        holder.unlockCanvasAndPost(c);
     }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Canvas c = holder.lockCanvas();
        draw(c);
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        lineGraphDrawer.end();
//        Thread dummyThread = lineGraphDrawer;
//        lineGraphDrawer = null;
//        dummyThread.interrupt();
    }
}
