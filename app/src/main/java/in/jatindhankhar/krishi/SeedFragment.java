package in.jatindhankhar.krishi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeedFragment extends Fragment {

    private ArrayList<SeedModel> mResponseList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public SeedFragment() {
        // Required empty public constructor
    }



    public static SeedFragment newInstance() {
        SeedFragment fragment = new SeedFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_seed, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.seed_list);
        mResponseList = new ArrayList<SeedModel>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        Ion.with(getContext()).load("http://krishi.jatindhankhar.in/seeds").asJsonArray().setCallback(

                new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        ((ProgressBar) view.findViewById(R.id.seedProgressBar)).setVisibility(View.GONE);
                        if(e == null)
                        {
                            Gson gson = new Gson();
                            for(JsonElement el : result)
                            {
                                mResponseList.add(gson.fromJson(el,SeedModel.class));
                            }
                            mAdapter = new SeedAdapter(mResponseList);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Sorry, there was some error :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        return  view;
    }

}
