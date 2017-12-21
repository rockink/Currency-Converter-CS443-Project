package com.rockink.curcon.dashBoardElems;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rockink.curcon.R;
import com.rockink.curcon.entities.LatestData;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;

    //setting null in the beginning
    private ArrayList<LatestData.Rates> rates = new ArrayList<>();
    private Integer initialColor;
    private Integer initialCurrency;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_item_list, container, false);

        View view = mainView.findViewById(R.id.recycle_currency_view);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new CurrencyRecyclerViewAdapter(rates, mListener));
        }
        return mainView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setData(ArrayList<LatestData.Rates> rates, Integer initialColor, String initialCurrency) {
        this.rates = rates;
        this.initialColor = initialColor;
        this.initialCurrency = initialColor;


        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_currency_view);
        recyclerView.setAdapter(new CurrencyRecyclerViewAdapter(rates, mListener, initialColor, initialCurrency));
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name

        public void onCurrencyClicked(LatestData.Rates rates, boolean selectedState, int colorId);

    }
}
