package com.rockink.curcon.individualCurrency.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rockink.curcon.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IndividualInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class IndividualInfoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String info;

    public IndividualInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual_info, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setInfo(String currencyName, String currencyVal) {
        //update unfo
        ((TextView)getView().findViewById(R.id.currency_name)).setText(currencyName);
        ((TextView)getView().findViewById(R.id.currency_val)).setText(currencyVal);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

    }
}
