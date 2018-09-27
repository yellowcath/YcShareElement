package us.pinguo.shareelementdemo.advanced.list;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import us.pinguo.shareelementdemo.advanced.BaseData;

/**
 * Created by huangwei on 2018/9/20.
 */
public abstract class BaseListCell<DATA extends BaseData,VH extends RecyclerView.ViewHolder> extends BaseRecyclerCell<DATA,VH> {

    protected OnCellClickListener mOnCellClickListener;

    public BaseListCell(DATA data) {
        super(data);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnCellClickListener!=null){
                    mOnCellClickListener.onCellClick(BaseListCell.this);
                }
            }
        });
    }

    public void setOnCellClickListener(OnCellClickListener onCellClickListener) {
        this.mOnCellClickListener = onCellClickListener;
    }

    public abstract Bitmap getThumbnail();

    public interface OnCellClickListener{
        void onCellClick(BaseListCell cell);
    }

    @Override
    public DATA getData() {
        return super.getData();
    }

    public abstract View getShareElement();
}
