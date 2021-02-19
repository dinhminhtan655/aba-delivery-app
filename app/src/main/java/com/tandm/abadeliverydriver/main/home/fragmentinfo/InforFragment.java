package com.tandm.abadeliverydriver.main.home.fragmentinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentinfo.adapter.UserRatingAdapter;
import com.tandm.abadeliverydriver.main.home.fragmentinfo.model.UserRating;
import com.tandm.abadeliverydriver.main.home.fragmentinfo.viewmodel.ViewModel;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class InforFragment extends Fragment {

    View view;
    Unbinder unbinder;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvMaNV)
    TextView tvMaNV;
    @BindView(R.id.tvViTri)
    TextView tvViTri;
    @BindView(R.id.tvMonthYearPicker)
    TextView tvMonthYearPicker;
    @BindView(R.id.rvRating)
    RecyclerView rvRating;
    @BindView(R.id.tvTongSaoTrongThang)
    TextView tvTongSaoTrongThang;

    UserRatingAdapter userRatingAdapter;

    private static ViewModel viewModel;
    List<UserRating> list;
    String strName, strUsername, strMaNV, strViTri, strToken;
    Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_infor, container, false);
        unbinder = ButterKnife.bind(this, view);
        calendar = Calendar.getInstance();
        strToken = LoginPrefer.getObject(getContext()).access_token;
        strName = LoginPrefer.getObject(getContext()).fullName;
        strUsername = LoginPrefer.getObject(getContext()).userName;
        strMaNV = LoginPrefer.getObject(getContext()).MaNhanVien;
        strViTri = LoginPrefer.getObject(getContext()).Position;

        tvName.setText(strName);
        tvUsername.setText(strUsername);
        tvMaNV.setText(strMaNV);
        tvViTri.setText(strViTri);

        tvMonthYearPicker.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));

        viewModel = ViewModelProviders.of(getActivity()).get(ViewModel.class);

        list = populateList((calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR));

        return view;


    }

    @OnClick(R.id.tvMonthYearPicker)
    public void pickMonthYear() {
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(),
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) { // on date set
                        tvMonthYearPicker.setText((selectedMonth + 1) + "/" + selectedYear);
                        list.clear();
                        list = populateList(selectedMonth + 1, selectedYear);
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth((today.get(Calendar.MONTH)))
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2030)
                .setTitle("Chọn tháng năm")
                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) { // on month selected

                    }
                })
                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear) { // on year selected
                    }
                })
                .build()
                .show();
    }


    private List<UserRating> populateList(int month, int year) {
        list = new ArrayList<>();

        viewModel.getUserRating(getContext(), strToken, strMaNV, month, year).observe(this, new Observer<List<UserRating>>() {
            @Override
            public void onChanged(List<UserRating> userRatings) {
                list = userRatings;
                userRatingAdapter = new UserRatingAdapter(new RecyclerViewItemClick<UserRating>() {
                    @Override
                    public void onClick(UserRating item, int position, int number) {
                        switch (number) {
                            case 0:
                                Toast.makeText(getContext(), "huhu", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onLongClick(UserRating item, int position, int number) {

                    }
                });
                userRatingAdapter.setUserRatings(getContext(), list);
                rvRating.setAdapter(userRatingAdapter);
                int iTotalStar = 0;
                for (int i = 0; i < userRatings.size() - 1; i++){
                    iTotalStar += userRatings.get(i).znsRatingValue;
                }

                tvTongSaoTrongThang.setText(String.valueOf(iTotalStar));
            }
        });
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

