package in.jatindhankhar.krishi;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
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

import java.util.ArrayList;

/**
 * Created by jatin on 8/9/16.
 */
public class ColdStoreAdapter extends RecyclerView.Adapter<ColdStoreAdapter.ViewHolder> {

    private ArrayList<ColdStoreModel> dataSet;
    private final ColdStoreFragment.OnFragmentInteractionListener mListener;
    private Context mContext;
    private int lastPosition = -1;

    public ColdStoreAdapter(ArrayList<ColdStoreModel> data,Context context,ColdStoreFragment.OnFragmentInteractionListener listener)
    {
        dataSet = data;
        mContext = context;
        mListener = listener;
    }

    @Override
    public ColdStoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cold_store_item_alt,parent,false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ColdStoreAdapter.ViewHolder holder, final int position) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();

        TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                .useFont(Typeface.MONOSPACE).bold().fontSize(60).
                        width(120).height(120).endConfig()
                .buildRoundRect(dataSet.get(position).store_name.substring(0,1),color1,160);
        holder.mDistrict.setText(dataSet.get(position).district);
        holder.mDistrict.setBackgroundColor(color1);
        String state = dataSet.get(position).state.toLowerCase();
        state = Character.toString(state.charAt(0)).toUpperCase() + state.substring(1);
        holder.mState.setText(state);
        holder.mStoreName.setText(dataSet.get(position).store_name);
        holder.mName.setText(dataSet.get(position).name);
        holder.mStoreIcon.setImageDrawable(textDrawable);
        holder.mAddress.setText(dataSet.get(position).address);
        String phoneNumber = dataSet.get(position).number;
        if (phoneNumber.isEmpty() || phoneNumber.length() != 10)
        {
            holder.mNumber.setText("No number");
            holder.mPhoneIcon.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.phone_hangup));
        }
        else
        {
            holder.mPhoneIcon.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_phone_default));
            holder.mNumber.setText("+91" +phoneNumber);
            //holder.mNumber.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        }

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null)
                {
                    mListener.onFragmentInteraction(dataSet.get(position));
                }
            }
        });*/
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mDistrict;
        TextView mState;
        TextView mName;
        TextView mNumber;
        TextView mStoreName;
        TextView mAddress;
        ImageView mStoreIcon;
        ImageView mPhoneIcon;
        LinearLayout mContainer;
        View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.mView = itemView;
            this.mContainer = (LinearLayout) itemView.findViewById(R.id.container_view);
            this.mState = (TextView) itemView.findViewById(R.id.state);
            this.mName = (TextView) itemView.findViewById(R.id.name);
            this.mNumber = (TextView) itemView.findViewById(R.id.number);
            this.mStoreName = (TextView) itemView.findViewById(R.id.store_name);
            this.mStoreIcon = (ImageView) itemView.findViewById(R.id.store_icon);
            this.mPhoneIcon = (ImageView) itemView.findViewById(R.id.phone_icon);
            this.mDistrict = (TextView) itemView.findViewById(R.id.district);
            this.mAddress = (TextView) itemView.findViewById(R.id.address);


        }
    }
}
