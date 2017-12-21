package com.rockink.curcon.entities;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 *
*/

public class LatestData {

    private ArrayList<Rates> rates;
    private String date;
    private String base;

    public LatestData(String base, String date, @NonNull ArrayList<Rates> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public static class Rates {
        String currency;
        Double rate;

        public Rates(@NonNull  String currency, @NonNull Double rate){
            this.currency = currency;
            this.rate = rate;
        }

        public @NonNull String getCurrency() {
            return currency;
        }

        public @NonNull Double getRate() {
            return rate;
        }
    }

    public String getDate() {
        return date;
    }

    public String getBase() {
        return base;
    }

    public ArrayList<Rates> getRates() {
        return rates;
    }
}
