package com.hw.ycshareelement.transform;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by huangwei on 2018/10/7.
 */
public class TextViewStateSaver extends ViewStateSaver {

    @Override
    public void setViewState(View view, Bundle bundle) {
        super.setViewState(view, bundle);
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(bundle.getFloat("textSize"));
            ((TextView) view).setTextColor(bundle.getInt("textColor"));
        }
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
