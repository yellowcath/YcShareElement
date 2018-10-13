package com.hw.ycshareelement.transition;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by huangwei on 2018/10/7.
 */
public class TextViewStateSaver extends ViewStateSaver {


    public float getTextSize(Bundle bundle){
        return bundle.getFloat("textSize");
    }

    public int getTextColor(Bundle bundle){
        return bundle.getInt("textColor");
    }

    @Override
    protected void captureViewInfo(View view, Bundle bundle) {
        super.captureViewInfo(view, bundle);
        if (view instanceof TextView) {
            bundle.putFloat("textSize", ((TextView) view).getTextSize());
            bundle.putInt("textColor", ((TextView) view).getCurrentTextColor());
        }
    }
}
