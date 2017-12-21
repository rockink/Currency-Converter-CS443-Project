package com.rockink.curcon;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rockink.curcon.dashBoardElems.ConversionFragment;
import com.rockink.curcon.dashBoardElems.ItemFragment;
import com.rockink.curcon.dashBoardElems.LatestDataLoader;
import com.rockink.curcon.dashBoardElems.LineGraphView;
import com.rockink.curcon.entities.LatestData;
import com.rockink.curcon.individualCurrency.IndividualActivity;
import com.rockink.curcon.urlRequests.DataLoader;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Entrance of the program...
 * Check fragment for what it does, or report to see how interaction is done!!
 *
 */
public class MainActivity extends AppCompatActivity implements LatestDataLoader, ConversionFragment.OnFragmentInteractionListener, ItemFragment.OnListFragmentInteractionListener {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //a thing in android
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                }, 1);

        //demo
        DataLoader.loadLatestData();

        DataLoader.currentSession = true;

        //our initial setop involves a fragmentList that is a list
        //of the currency that one can see



        LatestDataLoader latestDataLoader = this;
        try {
            DataLoader.processLatest(DataLoader.LATEST_DATA_API, latestDataLoader);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        LineGraphView graphView = new LineGraphView(this);
        LinearLayout view = (LinearLayout) findViewById(R.id.some_layout);
        view.addView(graphView);

//        setContentView(graphView);


    }

    @Override
    public void onDataLoaded(LatestData latestData) {
        ItemFragment listFragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        ConversionFragment conversionFragment = (ConversionFragment) getSupportFragmentManager().findFragmentById(R.id.convert);

        if(listFragment == null) {
            listFragment = new ItemFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, listFragment, "listFragment")
                    .commit();

        }

        conversionFragment.updateAdapterList(convert(latestData.getRates()));
        listFragment.setData(latestData.getRates(), null, null);

    }


    public void findConversion(View view){
        ConversionFragment conversionFragment = (ConversionFragment) getSupportFragmentManager().findFragmentById(R.id.convert);
        conversionFragment.doConversion();
    }

    private ArrayList<String> convert(ArrayList<LatestData.Rates> rates) {
        ArrayList<String> string = new ArrayList<>();
        for(LatestData.Rates rate : rates){
            string.add(rate.getCurrency());
        }
        return string;
    }

    @Override
    public void onCurrencyClicked(LatestData.Rates rates, boolean selectedState, int colorId) {
        Intent intent = new Intent(this, IndividualActivity.class);
        intent.putExtra(IndividualActivity.CURRENCY, rates.getCurrency());
        intent.putExtra(IndividualActivity.CURRENCY_VAL, rates.getRate()+"");
        intent.putExtra(IndividualActivity.CURRENCY_COLOR, colorId);
        startActivity(intent);
    }
}
