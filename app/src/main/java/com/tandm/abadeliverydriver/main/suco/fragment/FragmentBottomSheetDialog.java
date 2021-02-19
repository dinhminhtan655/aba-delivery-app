package com.tandm.abadeliverydriver.main.suco.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.ViewModel.MainViewModel;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemListener;
import com.tandm.abadeliverydriver.main.suco.SuCoAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBottomSheetDialog extends BottomSheetDialogFragment {
    private static final String TAG = "FragmentBottomSheetDialog";
    View view;
    Unbinder unbinder;
    @BindView(R.id.rvProblem)
    RecyclerView rvProblem;
    ProgressDialog progressDialog;
    SuCoAdapter adapter;
    MainViewModel mainViewModel;
    List<ProblemChild> list;

    private Listener listener;




    public FragmentBottomSheetDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_bottom_sheet_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        list = new ArrayList<ProblemChild>();
        adapter = new SuCoAdapter(new RecyclerViewItemListener() {
            @Override
            public void onClick(int position, String strTitle, int iProblem) {
                listener.passTitle(strTitle, iProblem);
                dismiss();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        rvProblem.setAdapter(adapter);

        getAllEmployee();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

    private void getAllEmployee() {
        mainViewModel.getAllProblem(getContext()).observe(this, new Observer<List<ProblemChild>>() {
            @Override
            public void onChanged(List<ProblemChild> problemChildren) {
                list = problemChildren;
                adapter.setProblemChildren(problemChildren);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface Listener{
        void passTitle(String title, int iProblem);
    }




}
