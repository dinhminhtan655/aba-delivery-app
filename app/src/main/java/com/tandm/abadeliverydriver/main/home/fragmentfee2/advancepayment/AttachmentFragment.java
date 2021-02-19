package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttachmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttachmentFragment extends Fragment {
    private static final String TAG = "AttachmentFragment";
    private String imgUrl, num, totalNum, rowPointer, strToken;


    public static AttachmentFragment newInstance(String imgUrl, String num, String totalNum, String rowPointer) {
        AttachmentFragment attachmentFragment = new AttachmentFragment();
        Bundle args = new Bundle();
        args.putString("img", imgUrl);
        args.putString("num", num);
        args.putString("totalnum", totalNum);
        args.putString("rowpointer", rowPointer);
        attachmentFragment.setArguments(args);
        return attachmentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strToken = "Bearer " + LoginPrefer.getObject(getActivity()).access_token;
        imgUrl = getArguments().getString("img");
        num = getArguments().getString("num");
        totalNum = getArguments().getString("totalnum");
        rowPointer = getArguments().getString("rowpointer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attachment, container, false);
        PhotoView imgAttachmentExpenses = view.findViewById(R.id.imgAttachmentExpenses);
        TextView tvPageNumber = view.findViewById(R.id.tvPageNumber);

        tvPageNumber.setText(num + "/" + totalNum);
        String imgLink = Utilities.getURLImage(getContext()) + imgUrl;
        Glide.with(AttachmentFragment.this).load(imgLink).into(imgAttachmentExpenses);
        return view;
    }


}