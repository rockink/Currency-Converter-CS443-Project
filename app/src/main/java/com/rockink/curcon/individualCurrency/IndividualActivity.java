package com.rockink.curcon.individualCurrency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rockink.curcon.R;
import com.rockink.curcon.dashBoardElems.BulkDataLoader;
import com.rockink.curcon.dashBoardElems.ItemFragment;
import com.rockink.curcon.dashBoardElems.LatestDataLoader;
import com.rockink.curcon.dashBoardElems.LineGraphView;
import com.rockink.curcon.entities.LatestData;
import com.rockink.curcon.individualCurrency.fragments.IndividualInfoFragment;
import com.rockink.curcon.individualCurrency.fragments.NinetyDaysCurrencyGraphFragment;
import com.rockink.curcon.urlRequests.BulkDataContainer;
import com.rockink.curcon.urlRequests.DataLoader;

import org.json.JSONException;

public class IndividualActivity extends AppCompatActivity implements IndividualInfoFragment.OnFragmentInteractionListener, ItemFragment.OnListFragmentInteractionListener, LatestDataLoader, BulkDataLoader {

    public static final String CURRENCY = "1";
    public static final String CURRENCY_VAL = "2";
    public static String CURRENCY_COLOR = "COLORID";
    String currency = "";

    boolean demo = false;
    private int LINE_GRAPH_VIEW = 2;
    private LineGraphView graphView;
    private String currencyVal;
    private int initialColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);



        graphView = new LineGraphView(this);
        graphView.setTag(LINE_GRAPH_VIEW);
        LinearLayout view = (LinearLayout) findViewById(R.id.activity_individual);

        view.addView(graphView);

        currency = getIntent().getStringExtra(CURRENCY);
        currencyVal = getIntent().getStringExtra(CURRENCY_VAL);
        initialColor = getIntent().getIntExtra(CURRENCY_COLOR, 0);

        try {
            DataLoader.processLatest(DataLoader.LATEST_DATA_API, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BulkDataLoader bulkDataLoader = this;
        DataLoader.load90DaysData(demo ? "demo " : currency, bulkDataLoader); //because  stuff haven't been implemented

        distributeInfoToInfoFragment(currency, currencyVal);

    }

    private void distributeInfoToInfoFragment(String currency, String currencyVal) {
        //reference the fragment first ,
        IndividualInfoFragment individualInfoFragment = (IndividualInfoFragment) getSupportFragmentManager()
                .findFragmentById(R.id.individualInfoFragment);
        individualInfoFragment.setInfo(currency, currencyVal);
    }


    @Override
    public void onCurrencyClicked(LatestData.Rates rates, boolean selectedState, int colorId) {
        //on currency Clicked.....

        Log.d("currency  ", "onCurrencyClicked: state.... " + selectedState);
        //the fragment dealing with graph is graphFragment
        NinetyDaysCurrencyGraphFragment ninetyDaysCurrencyGraphFragment = (NinetyDaysCurrencyGraphFragment) getSupportFragmentManager()
                .findFragmentById(R.id.graph_fragment);

        ninetyDaysCurrencyGraphFragment.updateGraphInfo(DataLoader.bulkDataContainer.get(rates.getCurrency()), selectedState, colorId);

    }

    @Override
    public void onDataLoaded(LatestData latestData) {
        LatestData.Rates rates = findCurrentCurrencyData(latestData);

        ItemFragment listFragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.listfragment);
        if(listFragment == null) {
            listFragment = new ItemFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, listFragment, "listFragment")
                    .commit();

        }


        listFragment.setData(latestData.getRates(), initialColor, currency);

        //we are sure to find some rate... so null pointer exception check can be avoided...
        //once the data has been loaded,
        //we can utilize this data
        distributeInfoToInfoFragment(rates.getCurrency(), rates.getRate()+"");

    }

    private LatestData.Rates findCurrentCurrencyData(LatestData latestData) {
        for(LatestData.Rates currency : latestData.getRates()){
            if (currency.getCurrency().equals(this.currency))
            return currency;
        }
        return null;

    }

    @Override
    public void onCurrency90DaysDataFound(BulkDataContainer bulkDatas) {
        //this means that we found out the bulk data...


        TextView loadGraph = (TextView) findViewById(R.id.graphMsg);
        loadGraph.setVisibility(View.GONE);

        if(bulkDatas == null) return;

        //the fragment dealing with graph is graphFragment
        NinetyDaysCurrencyGraphFragment ninetyDaysCurrencyGraphFragment = (NinetyDaysCurrencyGraphFragment) getSupportFragmentManager()
                .findFragmentById(R.id.graph_fragment);
        ninetyDaysCurrencyGraphFragment.updateGraphInfo(bulkDatas, true, (initialColor));


    }
}
