package in.jatindhankhar.krishi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColdStoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ColdStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColdStoreFragment extends Fragment {

    private ArrayList<ColdStoreModel> mResponseList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private int defaultId = R.id.sort_commodity;
    public ColdStoreFragment() {
        // Required empty public constructor
    }


    public static ColdStoreFragment newInstance() {
        return new ColdStoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingSearchView floatingSearchView = (FloatingSearchView) getActivity().findViewById(R.id.floating_search_view);
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                String query = newQuery.toLowerCase();
                final ArrayList<ColdStoreModel> filteredList = new ArrayList<ColdStoreModel>();
                if(query.isEmpty() || query.trim().isEmpty())
                {
                    filteredList.addAll(mResponseList);
                }
                else {
                    for (ColdStoreModel el : mResponseList) {
                        String query_field = el.store_name;
                        switch (defaultId)
                        {
                            case R.id.sort_commodity :
                                query_field = el.store_name;
                                break;
                            case  R.id.sort_district:
                                query_field = el.district;
                                break;
                            case R.id.sort_state:
                                query_field = el.state;
                                break;
                            default:
                                query_field = el.store_name;
                        }
                        if (query_field.toLowerCase().contains(query)) {
                            filteredList.add(el);
                        }
                        mAdapter = new ColdStoreAdapter(filteredList, getContext(),mListener);
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
        super.onCreateView(inflater, container, savedInstanceState);
        final View view =  inflater.inflate(R.layout.fragment_cold_store, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cold_store_list);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Cold Storage");
        mResponseList = new ArrayList<ColdStoreModel>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        Ion.with(getContext()).load("http://krishi.jatindhankhar.in/coldstore?state=delhi").asJsonArray().setCallback(
                new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        ((ProgressBar) view.findViewById(R.id.coldProgressBar)).setVisibility(View.GONE);
                        if (e == null) {
                            Gson gson = new Gson();
                            for (JsonElement el : result) {
                                mResponseList.add(gson.fromJson(el, ColdStoreModel.class));
                                //ColdStoreModel c = gson.fromJson(el,ColdStoreModel.class);
                                //Log.d("Yolopad",c.number);
                            }
                            mAdapter = new ColdStoreAdapter(mResponseList,getContext(),mListener);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(getContext(), "Sorry, there was some error :(", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );
        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(ColdStoreModel coldStoreModel);
    }
}
