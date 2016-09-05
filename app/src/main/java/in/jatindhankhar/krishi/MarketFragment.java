package in.jatindhankhar.krishi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by jatin on 5/9/16.
 */
public class MarketFragment extends Fragment {
    private ArrayList<DataModel> mResponseList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public MarketFragment() {

    }

    public void initData()
    {

    }
    public static MarketFragment newInstance()
    {
        MarketFragment fragment = new MarketFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view =  inflater.inflate(R.layout.market_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.market_list);
        //recyclerView.setHasFixedSize(true);
        mResponseList = new ArrayList<DataModel>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        Ion.with(getContext()).load("https://data.gov.in/api/datastore/resource.json?resource_id=9ef84268-d588-465a-a308-a864a43d0070&api-key=533060153c848cf636069b6cbcb3e841").
                asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                ((ProgressBar) view.findViewById(R.id.marketProgressBar)).setVisibility(View.GONE);
                if(e == null)
                {
                    JsonArray records = result.getAsJsonArray("records");
                    for(JsonElement el : records)
                    {

                        Gson gson = new Gson();
                        mResponseList.add(gson.fromJson(el,DataModel.class));

                    }
                    //((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.GONE);
                    mAdapter = new AdvancedAdapter(mResponseList);
                    mRecyclerView.setAdapter(mAdapter);

                }
                else
                {
                    Toast.makeText(getContext(), "Sorry, there was some error parsing response!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}

