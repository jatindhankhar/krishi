package in.jatindhankhar.krishi;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
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
    private int defaultId = R.id.sort_commodity;
    private seeditemInteractionListener mListener;
    public SeedFragment() {
        // Required empty public constructor
    }



    public static SeedFragment newInstance() {

        return new SeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingSearchView floatingSearchView = (FloatingSearchView) getActivity().findViewById(R.id.floating_search_view);
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                String query = newQuery.toLowerCase();
                final ArrayList<SeedModel> filteredList = new ArrayList<SeedModel>();
                if(query.isEmpty() || query.trim().isEmpty())
                {
                    filteredList.addAll(mResponseList);
                }
                else {
                    for (SeedModel el : mResponseList) {
                        String query_field = el.cropDetails.getCropNames();
                        switch (defaultId)
                        {
                            case R.id.sort_commodity :
                                query_field = el.cropDetails.getCropNames();
                                break;
                            case  R.id.sort_district:
                                query_field = el.addressDetails.getDistrict();
                                break;
                            case R.id.sort_state:
                                query_field = el.addressDetails.getState();
                                break;
                            default:
                                query_field = el.cropDetails.getCropNames();
                        }
                        if (query_field.toLowerCase().contains(query)) {
                            filteredList.add(el);
                        }
                        mAdapter = new SeedAdapter(filteredList,getContext(),mListener);
                        mRecyclerView.setAdapter(mAdapter);
                        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
                        if(mAdapter.getItemCount() > 0) {
                            fab.animate().rotation(0).start();
                            fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_check));
                        }
                        else{
                            fab.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_clear));

                        }
                    }
                }
            }
        });
        floatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                Toast.makeText(getContext(), "Filtering " +item.getTitle(), Toast.LENGTH_SHORT).show();

                defaultId = item.getItemId();
            }
        });


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
                            mAdapter = new SeedAdapter(mResponseList,getContext(),mListener);
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

    public interface seeditemInteractionListener{
        void seeditemInteraction(SeedModel seedModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof seeditemInteractionListener) {
            mListener = (seeditemInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
