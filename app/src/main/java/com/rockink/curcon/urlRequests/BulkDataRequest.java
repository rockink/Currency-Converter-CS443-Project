package com.rockink.curcon.urlRequests;

import android.os.AsyncTask;


import com.rockink.curcon.dashBoardElems.BulkParser;
import com.rockink.curcon.entities.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 *
 *
*
 *
 */

public class BulkDataRequest {

    private final DataLoader.OnBulkDataLoaded latestDataLoader;
    boolean notImplemented = false;

    public BulkDataRequest(String strm, DataLoader.OnBulkDataLoaded latestDataLoader) {

        this.latestDataLoader = latestDataLoader;
        new DataExtractTask().execute(strm);

    }

    public class DataExtractTask extends AsyncTask<String, String, HashMap<String, BulkDataContainer>> {


        @Override
        protected HashMap<String, BulkDataContainer>  doInBackground(String... stringUrls) {

            if(notImplemented)
                return mockedData();

            try {

                InputStream inputStream = init(stringUrls[0]);

                BulkParser bulkParser = new BulkParser(inputStream);
                return bulkParser.getMap();

//                JSONObject latestData = null;
//                try {
//                    latestData = new JSONObject(readString);
//                    String base = latestData.getString("base"); //EUR
//                    String date = latestData.getString("date");
//                    ArrayList<LatestData.Rates> rates = getAllRates(latestData.getJSONObject("rates"));
//
//                    LatestData latestDataObject =  new LatestData(base, date, rates);
//                    return latestDataObject;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//



            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(HashMap<String, BulkDataContainer> data) {
            latestDataLoader.onBulkDataLoadCompletion(data);
        }
    }

    private HashMap<String, BulkDataContainer> mockedData() {
        HashMap<String, BulkDataContainer> data = new HashMap<>();

        BulkDataContainer bulkDataContainer = new BulkDataContainer();
        ArrayList<BulkData> bulkDatas = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            bulkDataContainer.addData(new BulkData(i, i * i, "bill"));
        }

        data.put("demo", bulkDataContainer);
        return data;
    }


    private static ArrayList<LatestData.Rates> getAllRates(JSONObject rates) throws JSONException {
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
