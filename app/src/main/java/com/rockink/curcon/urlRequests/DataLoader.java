package com.rockink.curcon.urlRequests;

import com.rockink.curcon.dashBoardElems.BulkDataLoader;
import com.rockink.curcon.dashBoardElems.LatestDataLoader;
import com.rockink.curcon.dummy.Dummy;
import com.rockink.curcon.entities.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 *
 *  Load data from ECB. Quite heavy data!!
 *
 */

public class DataLoader {
    static URLConnection connection;
    public static HashMap<String, BulkDataContainer> bulkDataContainer = null;


    static LatestData latestData = null;

    public static LatestData loadLatestData(){

        try {
            JSONObject localData = new JSONObject(Dummy.data());
            String base = localData.getString("base"); //EUR
            String date = localData.getString("date");
            ArrayList<LatestData.Rates> rates = LatestDataRequest.getAllRates(localData.getJSONObject("rates"));

            LatestData latestDataObject =  new LatestData(base, date, rates);
            return latestDataObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     *
     * This stores the state... currentSession does that!!
     *
     *
     */
    public static boolean currentSession = false;
    public static final String LATEST_DATA_API = "https://api.fixer.io/latest";
    public static final String BULK_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml?d956912d840040e801674e45f00b1f0c";

    /**
     * Latest Data Loader...
     * @param strm
     * @param latestDataLoader
     * @throws JSONException
     */
    public static void processLatest(String strm, final LatestDataLoader latestDataLoader) throws JSONException {



        if(latestData != null) {
            latestDataLoader.onDataLoaded(latestData); //caching
            return;
        }

        //callback
        OnDataLoaded onDataLoaded = new OnDataLoaded() {

            @Override
            public void onCompletion(LatestData latestData) {
                DataLoader.latestData = latestData;
                latestDataLoader.onDataLoaded(latestData);
            }
        };

        new LatestDataRequest(strm, latestDataLoader, onDataLoaded);
    }


    public static String readString(InputStream strm) {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(strm);
        while (scanner.hasNextLine()){
            sb.append(scanner.nextLine());
        }
        return sb.toString();
    }


    private static InputStream init(String myUrl) throws IOException {
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        return conn.getInputStream();
    }


    public static LatestData.Rates processConvert(String from, String to) {
        try {

            InputStream inputStream = init(String.format("https://api.fixer.io/latest?base=%s&symbols=%s", from, to));
            JSONObject jsonObject = new JSONObject(convert(inputStream));

            String base = jsonObject.getString("base"); //EUR
            String date = jsonObject.getString("date");
            LatestData.Rates rate = getAllRate(jsonObject.getJSONObject("rates"));
            return rate;


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static LatestData.Rates getAllRate(JSONObject rates) {

        Iterator<String> keys = rates.keys();
        String key = "";
        try {
            while ((key = keys.next()) != null)
                return  new LatestData.Rates(key, rates.getDouble(key));
        }catch (Exception ignored){

        }
        return null;
    }


    static String convert(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()){
            sb.append(scanner.nextLine());
        }

        return sb.toString();
    }


    public static void load90DaysData(final String currency, final BulkDataLoader bulkDataLoader) {
        if (bulkDataContainer != null && bulkDataContainer.get(currency) != null) {
           bulkDataLoader.onCurrency90DaysDataFound(bulkDataContainer.get(currency));
           return;
        }

        OnBulkDataLoaded onBulkDataLoaded = new OnBulkDataLoaded() {
            @Override
            public void onBulkDataLoadCompletion(HashMap<String, BulkDataContainer> bulkData) {
                DataLoader.bulkDataContainer = bulkData;
                bulkDataLoader.onCurrency90DaysDataFound(bulkData.get(currency));
            }
        };
        processBulkData(onBulkDataLoaded);
    }


    static void processBulkData(OnBulkDataLoaded onBulkDataLoaded) throws DataNotFoundException{
        new BulkDataRequest(BULK_URL, onBulkDataLoaded);
    }

    public static interface OnDataLoaded{
        public void onCompletion(LatestData latestData);
    }

    public static interface OnBulkDataLoaded{
        public void onBulkDataLoadCompletion(HashMap<String, BulkDataContainer>  bulkData);
    }


}
