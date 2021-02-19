package com.tandm.abadeliverydriver.main.recycleviewadapter;

import android.widget.TextView;

import me.rishabhkhanna.customtogglebutton.CustomToggleButton;

public interface RecyclerViewItemListener3<T>{

    void onClick(int position, T item, CustomToggleButton customToggleButton, TextView textView, int key);
    void onLongClick(int position);
}
