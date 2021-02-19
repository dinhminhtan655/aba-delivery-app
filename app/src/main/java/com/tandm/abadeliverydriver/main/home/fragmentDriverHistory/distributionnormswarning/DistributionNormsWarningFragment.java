package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.distributionnormswarning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.distributionnormswarning.adapter.DistributionNormsWarningAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.distributionnormswarning.model.DistributionNormsWarning;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DistributionNormsWarningFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = "DistributionNormsWarnin";
    View view;
    private Unbinder unbinder;
    @BindView(R.id.rcDisNormsWarning)
    RecyclerView rcDisNormsWarning;
    DistributionNormsWarningAdapter adapter;
    String strToken, strDriverID;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_distribution_norms_warning, container, false);
        unbinder = ButterKnife.bind(this,view);
        strToken = LoginPrefer.getObject(getContext()).access_token;
        strDriverID = LoginPrefer.getObject(getContext()).MaNhanVien;
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        getDisNormWarning(strDriverID);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(true);
                    getDisNormWarning(strDriverID);
                }

            }
        });

        return view;
    }

    private void getDisNormWarning(String strDriverID) {
        swipeRefreshLayout.setRefreshing(true);

        if (!WifiHelper.isConnected(getActivity())) {
            swipeRefreshLayout.setRefreshing(false);
            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).getDistributionNormsWarning(strToken, strDriverID).enqueue(new Callback<List<DistributionNormsWarning>>() {
            @Override
            public void onResponse(Call<List<DistributionNormsWarning>> call, Response<List<DistributionNormsWarning>> response) {
                if (response.isSuccessful() && response.body() != null){
                    adapter = new DistributionNormsWarningAdapter();
                    adapter.replace(response.body());
                    if (rcDisNormsWarning != null){
                        rcDisNormsWarning.setAdapter(adapter);
                    }

                }
                if (swipeRefreshLayout != null){
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<List<DistributionNormsWarning>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        getDisNormWarning(strDriverID);
    }
}