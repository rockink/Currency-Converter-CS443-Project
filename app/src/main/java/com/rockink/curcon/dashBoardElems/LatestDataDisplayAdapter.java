package com.rockink.curcon.dashBoardElems;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rockink.curcon.R;
import com.rockink.curcon.entities.LatestData;

import java.util.List;
import java.util.Map;

/**
 *
*/

public class LatestDataDisplayAdapter extends ArrayAdapter<LatestData.Rates> implements AdapterView.OnItemClickListener {

    private final int[] graphColors;
    List<LatestData.Rates> latestData;

    public LatestDataDisplayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LatestData.Rates> latestData) {

        super(context, resource);

        graphColors = new int[]{
                ContextCompat.getColor(getContext(), R.color.indigo),
                ContextCompat.getColor(getContext(), R.color.red),
                ContextCompat.getColor(getContext(), R.color.teal),
                ContextCompat.getColor(getContext(), R.color.graphA)
        };



        this.latestData = latestData;
    }


    @Override
    public int getCount() {
        return latestData.size();
    }

    @Nullable
    @Override
    public LatestData.Rates getItem(int position) {
        return latestData.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.show_currency_latest, null);
            ((TextView)convertView.findViewById(R.id.curName)).setText(getItem(position).getCurrency());
            ((TextView)convertView.findViewById(R.id.value)).setText(getItem(position).getRate()+"");
        }
        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(getContext(), null);
//        getContext().startActivity(intent);
        Toast.makeText(getContext(), "clicked " + position, Toast.LENGTH_SHORT).show();
    }


}
