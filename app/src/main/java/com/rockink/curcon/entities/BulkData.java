package com.rockink.curcon.entities;

import com.rockink.curcon.urlRequests.Point;

import java.util.HashMap;

/**
 * BulkData is the data that we get from XML from ECB.
 *
*/

public class BulkData implements Point {

    public int date;
    public double val;
    public String currency;

    public BulkData(int date, double val, String currency) {
        this.date = date;
        this.val = val;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public float getX() {
        return date;
    }

    @Override
    public float getY() {
        return (float) val;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)",date,val);
    }

}
