package in.jatindhankhar.krishi;

import android.graphics.Color;
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

    public SeedAdapter(ArrayList<SeedModel> data)
    {
        dataSet = data;
    }
    @Override
    public SeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seed_item_layout,parent,false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(SeedAdapter.ViewHolder holder, int position) {
        holder.companyName.setText(dataSet.get(position).companyDetails.getName());
        //holder.productName.setText(dataSet.get(position).cropDetails.getCropNames());

        String compnyName = dataSet.get(position).companyDetails.getName().toString();
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(compnyName.substring(0,1));
        TextDrawable drawable = TextDrawable.builder().
                beginConfig().height(80).width(80).
                endConfig()
                .buildRoundRect("ABC",color,60);
        TextDrawable drawable1 = TextDrawable.builder().beginConfig().height(60).width(40).endConfig()
                .buildRoundRect("A", Color.RED, 10); // radius in px

        holder.nameImage.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView companyName;
        ImageView nameImage;

        public ViewHolder(View itemView) {
            super(itemView);
          //  productName = (TextView) itemView.findViewById(R.id.product_type);
            companyName = (TextView) itemView.findViewById(R.id.company_name);
            nameImage = (ImageView) itemView.findViewById(R.id.name_image);
        }
    }
}
