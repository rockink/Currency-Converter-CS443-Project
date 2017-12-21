package com.rockink.curcon.dashBoardElems;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rockink.curcon.R;
import com.rockink.curcon.urlRequests.DataLoader;
import com.rockink.curcon.entities.LatestData;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConversionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConversionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String prevFromCurrency = "", prevToCurrency = "";
    private LatestData.Rates rates;
    private String selectOne = "CLICK TO SELECT ONE";


    public ConversionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.convert, container, false);
    }



    public void updateAdapterList(ArrayList<String> convert){
        convert.add(0, selectOne);
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(
                getContext(), android.R.layout.simple_spinner_item,  convert.toArray()
        );
        ((Spinner) getView().findViewById(R.id.toSpinner)).setAdapter(adapter);
        ((Spinner) getView().findViewById(R.id.fromSpinner)).setAdapter(adapter);
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
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

    public void doConversion() {
        View parentView = getView();


        String fromCurrency = ((Spinner) parentView.findViewById(R.id.fromSpinner)).getSelectedItem().toString();
        String toCurrency = ((Spinner) parentView.findViewById(R.id.toSpinner)).getSelectedItem().toString();
        EditText number = ((EditText)parentView.findViewById(R.id.convertNumber));
        double numberUnit = Double.valueOf(number.getText().toString());

        if(fromCurrency.equals(selectOne) || toCurrency.equals(selectOne)){
            Toast.makeText(getContext(), "Please select currency.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(prevFromCurrency.equals(fromCurrency) && prevToCurrency.equals(toCurrency)){
            convertCurrency(numberUnit);
            return;
        }


        prevToCurrency = toCurrency;
        prevFromCurrency = fromCurrency;
        number.setText("1");
        new ConvertTask().execute(fromCurrency, toCurrency);

    }

    private void convertCurrency(double numberUnit) {
        if(rates != null) {
            ((TextView) getView().findViewById(R.id.convertedText)).setText((rates.getRate() * numberUnit) + "");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }


    class ConvertTask extends AsyncTask<String, Void, LatestData.Rates>{


        @Override
        protected LatestData.Rates doInBackground(String... params) {

            LatestData.Rates rate = DataLoader.processConvert(params[0], params[1]);
            return rate;
        }


        @Override
        protected void onPostExecute(LatestData.Rates ratesmate) {
            rates = ratesmate;
            ((TextView) getView().findViewById(R.id.convertedText)).setText(rates.getRate()+"");
        }
    }
}
