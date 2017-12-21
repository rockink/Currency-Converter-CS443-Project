package com.rockink.curcon.urlRequests;

import android.os.AsyncTask;

import com.rockink.curcon.dashBoardElems.LatestDataLoader;
import com.rockink.curcon.entities.LatestData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
*/

public class LatestDataRequest {

    private final LatestDataLoader latestDataLoader;
    private final DataLoader.OnDataLoaded onDataLoaded;

    public LatestDataRequest(String strm, LatestDataLoader latestDataLoader, DataLoader.OnDataLoaded onDataLoaded) {

        this.latestDataLoader = latestDataLoader;
        this.onDataLoaded = onDataLoaded;
        new DataExtractTask().execute(strm);

    }

    public class DataExtractTask extends AsyncTask<String, String, LatestData> {


        @Override
        protected LatestData doInBackground(String... stringUrls) {

            try {

                InputStream inputStream = init(stringUrls[0]);
                String readString = DataLoader.readString(inputStream);
                JSONObject latestData = null;
                try {
                    latestData = new JSONObject(readString);
                    String base = latestData.getString("base"); //EUR
                    String date = latestData.getString("date");
                    ArrayList<LatestData.Rates> rates = getAllRates(latestData.getJSONObject("rates"));

                    LatestData latestDataObject =  new LatestData(base, date, rates);
                    return latestDataObject;
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(LatestData latestData) {
            latestDataLoader.onDataLoaded(latestData);
            onDataLoaded.onCompletion(latestData);
        }
    }


    static ArrayList<LatestData.Rates> getAllRates(JSONObject rates) throws JSONException {
        ArrayList<LatestData.Rates> rateList = new ArrayList<>();

        Iterator<String> keys = rates.keys();
        String key = "";
        try {
            while ((key = keys.next()) != null)
                rateList.add(new LatestData.Rates(key, rates.getDouble(key)));
        }catch (Exception ignored){

        }

        return rateList;
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



}
