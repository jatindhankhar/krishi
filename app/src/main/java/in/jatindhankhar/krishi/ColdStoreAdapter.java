package in.jatindhankhar.krishi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by jatin on 8/9/16.
 */
public class ColdStoreAdapter extends RecyclerView.Adapter<ColdStoreAdapter.ViewHolder> {

    private ArrayList<ColdStoreModel> dataSet;
    //private final ColdStoreAdapter. mListener;
    private Context mContext;
    private int lastPosition = -1;

    public ColdStoreAdapter(ArrayList<ColdStoreModel> data,Context context)
    {
        dataSet = data;
        mContext = context;
    }

    @Override
    public ColdStoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cold_store_item,parent,false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ColdStoreAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
