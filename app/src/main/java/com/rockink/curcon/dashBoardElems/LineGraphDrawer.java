package com.rockink.curcon.dashBoardElems;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.rockink.curcon.urlRequests.BulkDataContainer;
import com.rockink.curcon.urlRequests.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for drawing the graph. run method does the work.
 * */

public class LineGraphDrawer { //extends Thread{


    private final Paint textPaint;
    private final Canvas canvas;
    private final Paint linePaint;
    private final int days;
    private final int[] graphColors;
    //    SurfaceHolder surfaceHolder;
    boolean isRunning = true;

//    ArrayList<Point> points = null;

    HashMap<String, BulkDataContainer> currencyToPoints = new HashMap<>();


    float maxX = Integer.MIN_VALUE, minX = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE, minY = Integer.MAX_VALUE;

    public LineGraphDrawer(Canvas canvas, Paint textPaint, Paint linePaint, HashMap<String, BulkDataContainer> currencyToData, int days, int[] graphColors) {
        this.days = days;

        this.graphColors = graphColors;

        this.textPaint = textPaint;
        this.canvas = canvas;
        this.linePaint = linePaint;


        maxX = 0;
        maxY = 0;
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        this.currencyToPoints = currencyToData;

        BulkDataContainer datas;
        for (Map.Entry<String, BulkDataContainer> currencyVals : currencyToData.entrySet()) {
            datas = currencyVals.getValue();

            if (datas.getMaxX() > maxX) maxX = (float) datas.getMaxX();
            if (datas.getMinX() < minX) minX = (float) datas.getMinX();

            if (datas.getMaxY() > maxY) maxY = (float) datas.getMaxY();
            if (datas.getMinY() < minY) minY = (float) datas.getMinY();

        }
        maxX = Math.min(maxX, days);
    }



    //@Override
    public void run(int whiteColor) {
//        Canvas canvas = surfaceHolder.lockCanvas();

        Path path = new Path();
        canvas.drawColor(whiteColor);
        draw(canvas);
//        surfaceHolder.unlockCanvasAndPost(canvas);

    }

    private void draw(Canvas canvas) {

        ArrayList<Point> points;
        int count = 0;
        for(Map.Entry<String, BulkDataContainer> currentPointSet : currencyToPoints.entrySet()){
            linePaint.setColor(currentPointSet.getValue().getColorId());
            points = currentPointSet.getValue().getBulkDatas();
            drawPoints(canvas, points);
        }


    }



    private void drawPoints(Canvas canvas, ArrayList<Point> points) {

        if(points.size() == 0){
            drawLoading(canvas);
            return;
        }



        int i = 0;

        Point firstPoint = points.get(0);



        //lets get width to scaleX x,
        float scaleX = (canvas.getWidth()) / (maxX-minX);
        float scaleY = (maxY == minY) ?  (canvas.getHeight() /2 )  : (canvas.getHeight()) / (maxY-minY);


        canvas.drawText(firstPoint.toString(),
                firstPoint.getX() * scaleX,
                canvas.getHeight() - (firstPoint.getY() - minY) * scaleY, textPaint);
        while (i < Math.min(days, points.size()) - 1){

            Point point = points.get(i);
            Point nextPoint = points.get(i + 1);

            canvas.drawLine(
                    point.getX() * scaleX,
                    canvas.getHeight() - (point.getY() - minY) * scaleY,
                    nextPoint.getX() * scaleX,
                    canvas.getHeight() - (nextPoint.getY() - minY) * scaleY, linePaint);


            canvas.drawPoint(nextPoint.getX() * scaleX, canvas.getHeight() - (nextPoint.getY() - minY) * scaleY, textPaint);

            i++;
        }

    }

    private void drawLoading(Canvas canvas) {
        canvas.drawText("LOADING GRAPH.....",10,101,linePaint);
    }

    public void end() {
        isRunning = false;
    }
}
