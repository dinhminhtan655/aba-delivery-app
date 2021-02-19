package com.tandm.abadeliverydriver.main.utilities;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationUtil {

    public static Animation anim(Context context, int id){
        Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), id);
        return animation;
    }

}
