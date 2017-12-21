package com.rockink.curcon.dashBoardElems;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
*/

public class DataExtractTask extends AsyncTask<String, String, InputStream> {


    private InputStream inputStream = null;

    private static InputStream init(String myUrl) throws IOException {
        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        return conn.getInputStream();

    }

    @Override
    protected InputStream doInBackground(String... stringUrls) {

        try {

            inputStream = init(stringUrls[0]);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;

    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);


    }
}
