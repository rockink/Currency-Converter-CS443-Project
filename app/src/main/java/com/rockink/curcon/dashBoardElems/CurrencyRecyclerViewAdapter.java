package com.rockink.curcon.dashBoardElems;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rockink.curcon.MainActivity;
import com.rockink.curcon.R;
import com.rockink.curcon.dummy.DummyContent.DummyItem;
import com.rockink.curcon.entities.LatestData;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link ItemFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CurrencyRecyclerViewAdapter extends RecyclerView.Adapter<CurrencyRecyclerViewAdapter.ViewHolder> {

    static int colorId;

    private final List<LatestData.Rates> mValues;
    private final ItemFragment.OnListFragmentInteractionListener mListener;
    private String initialCurrency;
    private Integer initialColor;
    private int[] graphColors;

    public CurrencyRecyclerViewAdapter(List<LatestData.Rates> items, ItemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public CurrencyRecyclerViewAdapter(ArrayList<LatestData.Rates> rates, ItemFragment.OnListFragmentInteractionListener mListener, Integer initialColor, String initialCurrency) {
        this(rates, mListener);
        this.initialCurrency = initialCurrency;
        this.initialColor = initialColor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getCurrency());
        holder.mContentView.setText(mValues.get(position).getRate()+"");

        if (initialCurrency != null && holder.mItem.getCurrency().equals(initialCurrency)){
            holder.mView.setBackgroundColor((int)initialColor);
            initialColor = null;
            initialCurrency = null;
            holder.setSelectedState(true);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.


                    if(!(mListener instanceof MainActivity)){
                        holder.setSelectedState(!holder.selectedState);
                    }

                    if(graphColors == null) {
                        graphColors = new int[]{
                                ContextCompat.getColor(v.getContext(), R.color.indigo),
                                ContextCompat.getColor(v.getContext(), R.color.red),
                                ContextCompat.getColor(v.getContext(), R.color.teal),
                                ContextCompat.getColor(v.getContext(), R.color.graphA)
                        };

                    }

                    int color = !holder.selectedState ? ContextCompat.getColor(v.getContext(), R.color.white) :  graphColors[++colorId % 4];

                    if((mListener instanceof MainActivity)){
                        color = graphColors[colorId % 4];
                    }else {
                        holder.mView.setBackgroundColor(color);
                    }


                    if(mListener instanceof MainActivity) {
                        holder.setSelectedState(true);
                        holder.mView.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.white));
                    }

                    mListener.onCurrencyClicked(mValues.get(position), holder.selectedState, color);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public LatestData.Rates mItem;
        boolean selectedState = false;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        public void setSelectedState(boolean selectedState) {
            this.selectedState = selectedState;
            mView.setBackgroundColor(ContextCompat.getColor(mContentView.getContext(),selectedState ?  R.color.colorPrimary : R.color.white));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
