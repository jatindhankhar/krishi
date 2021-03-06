package in.jatindhankhar.krishi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
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
    private itemInteractionListener mListener;

    private int defaultId = R.id.sort_commodity;
    public MarketFragment() {

    }

    public interface floatingSearchBarListenrer
    {
        public void configureSearch();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof itemInteractionListener) {
            mListener = (itemInteractionListener) context;
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

    public static MarketFragment newInstance()
    {
        return new MarketFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view =  inflater.inflate(R.layout.market_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.market_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        mResponseList = new ArrayList<DataModel>();


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Market Prices");
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
                    mAdapter = new AdvancedAdapter(mResponseList,mListener,getContext());
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
        FloatingSearchView floatingSearchView = (FloatingSearchView) getActivity().findViewById(R.id.floating_search_view);
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                String query = newQuery.toLowerCase();
                final ArrayList<DataModel> filteredList = new ArrayList<DataModel>();
                if(query.isEmpty() || query.trim().isEmpty())
                {
                    filteredList.addAll(mResponseList);
                }
                else {
                    for (DataModel el : mResponseList) {
                        String query_field = el.mcommodity;
                        switch (defaultId)
                        {
                            case R.id.sort_commodity :
                                query_field = el.mcommodity;
                                break;
                            case  R.id.sort_district:
                                query_field = el.mdistrict;
                                break;
                            case R.id.sort_state:
                                query_field = el.mstate;
                                break;
                            default:
                                query_field = el.mcommodity;
                        }
                        if (query_field.toLowerCase().contains(query)) {
                            filteredList.add(el);
                        }
                        mAdapter = new AdvancedAdapter(filteredList, mListener, getContext());
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

    public interface itemInteractionListener{
        void itemInteraction(DataModel dataModel);
    }

}

