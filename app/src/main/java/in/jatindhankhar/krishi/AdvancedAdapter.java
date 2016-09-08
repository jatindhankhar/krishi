package in.jatindhankhar.krishi;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by jatin on 21/8/16.
 */

public class AdvancedAdapter extends RecyclerView.Adapter<AdvancedAdapter.ViewHolder> {

        private ArrayList<DataModel> dataSet;
        private final MarketFragment.itemInteractionListener mListener;
        private Context mContext;
        private int lastPosition = -1;

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
            ImageView mCommodityIcon;
            LinearLayout mContainer;
            final View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            //this.mState = (TextView) itemView.findViewById(R.id.state);
            this.mDistrict = (TextView) itemView.findViewById(R.id.district);
            this.mMarket = (TextView) itemView.findViewById(R.id.market);
            //this.mCommodity = (TextView) itemView.findViewById(R.id.commodity);
            //this.mVariety = (TextView) itemView.findViewById(R.id.variety);
            this.mArrivalDate = (TextView) itemView.findViewById(R.id.arrival_date);
            //this.mMinPrice = (TextView) itemView.findViewById(R.id.min_price);
           // this.mMaxPrice = (TextView) itemView.findViewById(R.id.max_price);
            this.mModalPrice = (TextView) itemView.findViewById(R.id.modal_price);
            this.mCommodityIcon = (ImageView) itemView.findViewById(R.id.commodity_icon);
            this.mContainer = (LinearLayout) itemView.findViewById(R.id.container_view);






        }



    }

    public AdvancedAdapter(ArrayList<DataModel> data, MarketFragment.itemInteractionListener itemInteractionListener,Context context)
    {
        dataSet = data;

        mListener = itemInteractionListener;

        mContext = context;
    }


    public AdvancedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modern_market_item,parent,false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final AdvancedAdapter.ViewHolder holder, final int position) {

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();

        TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                .useFont(Typeface.MONOSPACE).bold().fontSize(60).
                        width(120).height(120).endConfig()
                .buildRoundRect(dataSet.get(position).mcommodity.substring(0,1),color1,160);
        //holder.mState.setText( "State: " +dataSet.get(position).mstate);
        holder.mDistrict.setText(dataSet.get(position).mdistrict);
        holder.mDistrict.setBackgroundColor(color1);
        holder.mMarket.setText( "Market: " + dataSet.get(position).mmarket);
        //holder.mCommodity.setText( "Commodity: " +dataSet.get(position).mcommodity);
        //holder.mVariety.setText( "Variety: " + dataSet.get(position).mvariety);
        SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
        SimpleDateFormat dfInput = new SimpleDateFormat("dd/MM/yyyy");
        String releasedDate = null;
        try {
        releasedDate = df.format(dfInput.parse(dataSet.get(position).marrival_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mArrivalDate.setText(releasedDate);
        //holder.mMinPrice.setText( "Min Price: " + "\u20B9"+ dataSet.get(position).mmin_price);
        //holder.mMaxPrice.setText( "Max Price: "+ "\u20B9" +dataSet.get(position).mmax_price);
        holder.mModalPrice.setText( dataSet.get(position).mmodal_price);
        holder.mCommodityIcon.setImageDrawable(textDrawable);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                {
                    mListener.itemInteraction(dataSet.get(position));
                }
            }
        });

        animate(holder.mContainer,position);

    }

    public void animate(View view,int position) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.bounce_interpolator);
        //viewHolder.itemView.setAnimation(animAnticipateOvershoot);
        if (position > lastPosition)
        {view.startAnimation(animAnticipateOvershoot);
        lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}



