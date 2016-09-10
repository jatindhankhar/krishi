package in.jatindhankhar.krishi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

/**
 * Created by jatin on 6/9/16.
 */
public class SeedAdapter extends RecyclerView.Adapter<SeedAdapter.ViewHolder> {

    private ArrayList<SeedModel> dataSet;
    private Context mContext;

    public SeedAdapter(ArrayList<SeedModel> data,Context context)
    {
        dataSet = data;
        mContext = context;

    }
    @Override
    public SeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seed_item_layout_alt,parent,false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(SeedAdapter.ViewHolder holder, int position) {
        //holder.companyName.setText(dataSet.get(position).companyDetails.getName());
        //holder.productName.setText(dataSet.get(position).cropDetails.getCropNames());

        String compnyName = dataSet.get(position).companyDetails.getName();
        String seedtype = dataSet.get(position).cropDetails.getCropNames();
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(compnyName.substring(0,1));
        TextDrawable drawable1 = TextDrawable.builder().beginConfig().useFont(Typeface.MONOSPACE).bold().fontSize(60).
                width(120).height(120).endConfig()
                .buildRoundRect(seedtype.substring(0,1), color, 160); // radius in px
        holder.mDistrict.setText(dataSet.get(position).addressDetails.getDistrict());
        holder.mDistrict.setBackgroundColor(color);
        holder.mCompanyNameIcon.setImageDrawable(drawable1);
        holder.mCompanyName.setText(compnyName);
        holder.mBlock.setText(dataSet.get(position).addressDetails.getBlock().toString());
        holder.mSeedType.setText(dataSet.get(position).cropDetails.getCropNames());
        holder.mPersoName.setText(dataSet.get(position).contactDetails.getName());
        String phoneNumber = dataSet.get(position).contactDetails.getNumber();

        if (phoneNumber.isEmpty())
        {
            holder.mPhoneNumber.setText("No number");
            holder.mPhoneIcon.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.phone_hangup));
        }
        else
        {
            holder.mPhoneIcon.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_phone_default));
            holder.mPhoneNumber.setText("+91" +phoneNumber);
            //holder.mNumber.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        }
        holder.mAuthorityType.setText(dataSet.get(position).licenseDetails.getMembership());
     //   holder.nameImage.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mPhoneNumber;
        TextView mPersoName;
        TextView mSeedType;
        TextView mCompanyName;
        TextView mAuthorityType;
        TextView mAddress;
        TextView mBlock;
        TextView mDistrict;
        ImageView mCompanyNameIcon;
        ImageView mPhoneIcon;

        public ViewHolder(View itemView) {
            super(itemView);
          //  productName = (TextView) itemView.findViewById(R.id.product_type);
            //companyName = (TextView) itemView.findViewById(R.id.company_name);
            //nameImage = (ImageView) itemView.findViewById(R.id.name_image);
            mPhoneNumber = (TextView) itemView.findViewById(R.id.phone_number);
            mPersoName = (TextView) itemView.findViewById(R.id.person_name);
            //mAddress = (TextView) itemView.findViewById(R.id.address);
            mBlock = (TextView) itemView.findViewById(R.id.block);
            mAuthorityType = (TextView) itemView.findViewById(R.id.authority);
            mSeedType = (TextView) itemView.findViewById(R.id.seed_type);
            mDistrict = (TextView) itemView.findViewById(R.id.district);
            mPhoneIcon = (ImageView) itemView.findViewById(R.id.phone_icon);
            mCompanyNameIcon = (ImageView) itemView.findViewById(R.id.company_name_icon);
            mCompanyName = (TextView) itemView.findViewById(R.id.company_name);
        }
    }
}
