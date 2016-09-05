package in.jatindhankhar.krishi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jatin on 21/8/16.
 */

public class AdvancedAdapter extends RecyclerView.Adapter<AdvancedAdapter.ViewHolder> {

        private ArrayList<DataModel> dataSet;

        public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewVersion;
            TextView mState;
            TextView mDistrict;
            TextView mMarket;
            TextView mCommodity;
            TextView mVariety;
            TextView mArrivalDate;
            TextView mMinPrice;
            TextView mMaxPrice;
            TextView mModalPrice;
        public ViewHolder(View itemView) {
            super(itemView);

            this.mState = (TextView) itemView.findViewById(R.id.state);
            this.mDistrict = (TextView) itemView.findViewById(R.id.district);
            this.mMarket = (TextView) itemView.findViewById(R.id.market);
            this.mCommodity = (TextView) itemView.findViewById(R.id.commodity);
            this.mVariety = (TextView) itemView.findViewById(R.id.variety);
            this.mArrivalDate = (TextView) itemView.findViewById(R.id.arrival_date);
            this.mMinPrice = (TextView) itemView.findViewById(R.id.min_price);
            this.mMaxPrice = (TextView) itemView.findViewById(R.id.max_price);
            this.mModalPrice = (TextView) itemView.findViewById(R.id.modal_price);






        }



    }
    public AdvancedAdapter(ArrayList<DataModel> data)
    {
        dataSet = data;
    }


    public AdvancedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout,parent,false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AdvancedAdapter.ViewHolder holder, int position) {



        holder.mState.setText( "State: " +dataSet.get(position).mstate);
        holder.mDistrict.setText( "District: " + dataSet.get(position).mdistrict);
        holder.mMarket.setText( "Market: " + dataSet.get(position).mmarket);
        holder.mCommodity.setText( "Commodity: " +dataSet.get(position).mcommodity);
        holder.mVariety.setText( "Variety: " + dataSet.get(position).mvariety);
        holder.mArrivalDate.setText( "Arrival Date: " +dataSet.get(position).marrival_date);
        holder.mMinPrice.setText( "Min Price: " + "\u20B9"+ dataSet.get(position).mmin_price);
        holder.mMaxPrice.setText( "Max Price: "+ "\u20B9" +dataSet.get(position).mmax_price);
        holder.mModalPrice.setText("Modal Price: "+ "\u20B9"+ dataSet.get(position).mmodal_price);


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}



