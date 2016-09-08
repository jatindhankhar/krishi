package in.jatindhankhar.krishi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

    public ColdStoreFragment() {
        // Required empty public constructor
    }


    public static ColdStoreFragment newInstance() {
        return new ColdStoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        final View view =  inflater.inflate(R.layout.fragment_cold_store, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cold_store_list);
        //recyclerView.setHasFixedSize(true);
        mResponseList = new ArrayList<ColdStoreModel>();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        Ion.with(getContext()).load("http://krishi.jatindhankhar.in/coldstore?state=haryana").asJsonArray().setCallback(
                new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        ((ProgressBar) view.findViewById(R.id.coldProgressBar)).setVisibility(View.GONE);
                        if (e == null) {
                            Gson gson = new Gson();
                            for (JsonElement el : result) {
                                //mResponseList.add(gson.fromJson(el, ColdStoreModel.class));
                                ColdStoreModel c = gson.fromJson(el,ColdStoreModel.class);
                                Log.d("Yolopad",c.number);
                            }
                            //mAdapter = new SeedAdapter(mResponseList);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(getContext(), "Sorry, there was some error :(", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        void onFragmentInteraction(Uri uri);
    }
}
