package us.pinguo.shareelementdemo.advanced.content.viewpager;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhangkaiyu on 2016/3/28.
 */
public class BasePagerViewHolder {

    private SparseArray<View> mViews;
    public View itemView;

    public BasePagerViewHolder(View itemView) {
        this.itemView = itemView;
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        text = text == null ? "" : text;
        textView.setText(text);
    }

    public void setVisible(int viewId, int vis) {
        View textView = getView(viewId);
        textView.setVisibility(vis);
    }

    public void setText(int viewId, int textResId) {
        TextView textView = getView(viewId);
        textView.setText(textResId);
    }

    public void setEnabled(int viewId, boolean enabled) {
        View view = getView(viewId);
        view.setEnabled(enabled);
    }


    public void hide(int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public void show(int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
    }


    public void setBackgroundRes(int viewId, int resId) {
        if (resId != 0) {
            View view = getView(viewId);
            view.setBackgroundResource(resId);
        }
    }

    public void setImageResource(int viewId, int resId) {
        if (resId != 0) {
            ImageView imageView = getView(viewId);
            imageView.setImageResource(resId);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        }
    }

    public void setSelected(int viewId, boolean selected) {
        getView(viewId).setSelected(selected);
    }

    public void setTag(int id, String tag) {
        getView(id).setTag(tag);
    }

}
