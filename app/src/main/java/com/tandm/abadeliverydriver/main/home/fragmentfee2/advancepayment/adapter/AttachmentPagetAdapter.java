package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.AttachmentFragment;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.AttachExpenses;
import com.tandm.abadeliverydriver.main.utilities.SmartFragmentStatePagerAdapter;

import java.util.List;

public class AttachmentPagetAdapter extends SmartFragmentStatePagerAdapter {

    private List<AttachExpenses> list;

    public AttachmentPagetAdapter(@NonNull FragmentManager fm, List<AttachExpenses> list) {
        super(fm);
        this.list = list;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return AttachmentFragment.newInstance(list.get(position).attachName, String.valueOf(position+1),  String.valueOf(list.size()), list.get(position).rowPointer);
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View page = inflater.inflate(R.layout.item_image_fee, null);
//        return super.instantiateItem(container, position);
//    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Ảnh " + position + "/" + list.size();
    }

    public void deletePage(int position) {
        if (canDelete()) {
            list.remove(position);
            notifyDataSetChanged();
        }
    }

    boolean canDelete() {
        return list.size() > 0;
    }

}
