package com.rockink.curcon.urlRequests;

import com.rockink.curcon.entities.BulkData;

import java.util.ArrayList;

/**
 *
 * Container, VALUE for hashmap. See implementation. Not that hard...
 *
 * */

public class BulkDataContainer {

    ArrayList<Point> bulkDatas;
    double maxX = Integer.MIN_VALUE, minX = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE, minY = Integer.MAX_VALUE;
    private String currency;

    int colorId;

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }

    public BulkDataContainer(){
        bulkDatas = new ArrayList<>();
    }

    public void addData(BulkData data){
        if (data.date > maxX) maxX = data.date;
        if (data.date < minX) minX = data.date;

        if (data.val > maxY) maxY = (float) data.val;
        if (data.val < minY) minY = (float) data.val;
        if(bulkDatas.size() == 0)
            this.currency = data.getCurrency();
        bulkDatas.add(data);
    }

    public int size(){
        if(bulkDatas == null) return 0;
        return bulkDatas.size();
    }

    ArrayList<Point> getPoints(){
        return bulkDatas;
    }

    public ArrayList<Point> getBulkDatas() {
        return bulkDatas;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public String getCurrency() {
        return currency;
    }
}
