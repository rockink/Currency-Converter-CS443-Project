package com.rockink.curcon.dashBoardElems;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.rockink.curcon.R;

/**
 *
*/

public class LatestDataListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.latest_data_fragment_list,container, false);
        ListView listView = inflateView.findViewById(android.R.id.list);
        listView.setOnItemClickListener(((LatestDataDisplayAdapter)getListAdapter()));
        return inflateView;
    }


    @Override
    public void setListAdapter(ListAdapter adapter) {
        super.setListAdapter(adapter);
    }

}
