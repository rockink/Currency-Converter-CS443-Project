package com.rockink.curcon.individualCurrency.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rockink.curcon.R;
import com.rockink.curcon.dashBoardElems.LineGraphView;
import com.rockink.curcon.urlRequests.BulkDataContainer;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class NinetyDaysCurrencyGraphFragment extends Fragment {

    HashMap<String, BulkDataContainer> currencyToVals = new HashMap<>();
    private TextView dateView;


    private static String TAG = "GraphTAG";

    public NinetyDaysCurrencyGraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ninety_days_currency_graph, container, false);

        SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setMax(90);
        seekBar.setProgress(90);

        dateView = ((TextView) view.findViewById(R.id.dateView));

        LineGraphView lineGraphView = ((LineGraphView) view.findViewById(R.id.graph));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int days = seekBar.getProgress();
                updateDaysInGraph(days);
            }
        });

        return view;

    }

    private void updateDaysInGraph(int days) {
        LineGraphView lineGraphView = getView().findViewById(R.id.graph);
        dateView.setText(days+"");
        lineGraphView.updateDays(days);

    }

    public void updateGraphInfo(BulkDataContainer bulkDatas, boolean selectedState, int colorId) {
        bulkDatas.setColorId(colorId);

        LineGraphView lineGraphView = getView().findViewById(R.id.graph);
        if(bulkDatas == null || bulkDatas.size() == 0){
            return;
        }
        if (selectedState) currencyToVals.put(bulkDatas.getCurrency(), bulkDatas);
        else currencyToVals.remove(bulkDatas.getCurrency());

        lineGraphView.updateGraph(currencyToVals);
    }
}
