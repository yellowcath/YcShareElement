package us.pinguo.shareelementdemo.advanced;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.list.AdvancedListAdapter;
import us.pinguo.shareelementdemo.advanced.list.BaseListCell;
import us.pinguo.shareelementdemo.advanced.list.ImageListCell;
import us.pinguo.shareelementdemo.advanced.list.VideoListCell;
import us.pinguo.shareelementdemo.transform.ChangeOnlineImageTransform;
import us.pinguo.shareelementdemo.transform.GetShareElement;
import us.pinguo.shareelementdemo.transform.GlideBitmapSizeCalculator;
import us.pinguo.shareelementdemo.transform.ShareElementInfo;
import us.pinguo.shareelementdemo.transform.ShareImageViewInfo;
import us.pinguo.shareelementdemo.transform.ShareVideoViewInfo;
import us.pinguo.shareelementdemo.transform.YcShareElement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class AdvancedListFragment extends Fragment implements BaseListCell.OnCellClickListener, GetShareElement {
    static final int REQUEST_CONTENT = 223;

    private RecyclerView mRecyclerView;
    private AdvancedListAdapter mAdapter;
    private ArrayList<Parcelable> mDataList = new ArrayList<>();
    private BaseListCell mTransitionCell;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setBackgroundColor(0xFF323232);
        mAdapter = new AdvancedListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.divider)));
        initCells();
    }

    private void initCells() {
        List<BaseListCell> cellList = new LinkedList<>();
        mDataList.add(new Image("http://phototask.c360dn.com/Fl-0qph8x14uLb2JwRzko8fOmfqw", 1024, 738));
        mDataList.add(new Image("http://phototask.c360dn.com/FuOUEYx1YLf9gUAykzueD9TzX8Lq", 1280, 800));
        mDataList.add(new Video("https://phototask.c360dn.com/lm7e8LQIsnHteaGlkv6Q6lu05ri8",
                "https://phototask.c360dn.com/lm7e8LQIsnHteaGlkv6Q6lu05ri8-2018081621-preview.webp",
                720, 1280));
        mDataList.add(new Image("http://phototask.c360dn.com/Fo9D8NQqmbs3AAQASxnkPZRHF5Hv", 720, 1280));
        mDataList.add(new Video("https://phototask.c360dn.com/lm7F9EhP3DVTVtMqOD6rkx7w6TwW",
                "https://phototask.c360dn.com/lm7F9EhP3DVTVtMqOD6rkx7w6TwW-2018040715-preview.webp",
                640, 1138));
        mDataList.add(new Image("http://phototask.c360dn.com/FhGeGKwB9Z6WvuaINQOuc7wm4vvj", 1600, 1280));
        mDataList.add(new Video("https://phototask.c360dn.com/lqYmdnp4cWoz35jp8BTSPXIXfq9s",
                "https://phototask.c360dn.com/lqYmdnp4cWoz35jp8BTSPXIXfq9s-2018082319-preview.webp",
                640, 854));
        mDataList.add(new Image("http://phototask.c360dn.com/Fn8kpRh_rarrSMIoEnnEPadNOuWi", 720, 1280));

        mDataList.add(new Video("https://phototask.c360dn.com/lpNwvBcfABEhJVfEkUvPfPRvA7KF",
                "https://phototask.c360dn.com/lpNwvBcfABEhJVfEkUvPfPRvA7KF-2018082620-preview.webp",
                640, 1138));
        mDataList.add(new Image("http://phototask.c360dn.com/Fn3915H5n7AhYKJdpdlNjSbfPC5e", 1024, 640));
        mDataList.add(new Video("https://phototask.c360dn.com/luqJVmntp51TIcdzGJviz0erj9l9",
                "https://phototask.c360dn.com/luqJVmntp51TIcdzGJviz0erj9l9-2018090108-preview.webp",
                640, 1138));
        mDataList.add(new Image("http://phototask.c360dn.com/FsOmrix9LiKJXKqi4vOU7fbUmlbQ", 1080, 960));

        for (Parcelable data : mDataList) {
            BaseListCell cell = data instanceof Image ? new ImageListCell((Image) data) : new VideoListCell((Video) data);
            cell.setOnCellClickListener(this);
            cellList.add(cell);
        }
        mAdapter.setList(cellList);
    }

    @Override
    public void onCellClick(BaseListCell cell) {
        mTransitionCell = cell;
        Intent intent = new Intent(getActivity(), AdvancedContentActivity.class);
        intent.putParcelableArrayListExtra("data", mDataList);
        intent.putExtra("select", mDataList.indexOf(cell.getData()));
        ChangeOnlineImageTransform.setsBitmapSizeCalculator(new GlideBitmapSizeCalculator());
        Bundle options = YcShareElement.buildOptionsBundle(getActivity(), this);
        startActivityForResult(intent, REQUEST_CONTENT, options);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            BaseListCell cell = (BaseListCell) mAdapter.getItem(i);
            cell.setOnCellClickListener(null);
        }
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        if (mTransitionCell instanceof VideoListCell) {
            ShareVideoViewInfo info = new ShareVideoViewInfo(mTransitionCell.getShareElement(), mTransitionCell.getData(),
                    mTransitionCell.getData().width, mTransitionCell.getData().height);
            return new ShareElementInfo[]{info};
        } else if (mTransitionCell instanceof ImageListCell) {
            ShareImageViewInfo info = new ShareImageViewInfo(mTransitionCell.getShareElement(), mTransitionCell.getData());
            return new ShareElementInfo[]{info};
        }
        return new ShareElementInfo[0];
    }

    public void selectShareElement(ShareElementInfo shareElementInfo) {
        BaseData data = (BaseData) shareElementInfo.getData();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            BaseListCell cell = mAdapter.getItem(i);
            if (cell.getData().equals(data)) {
                mTransitionCell = cell;
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();
                layoutManager.scrollToPosition(i);
                return;
            }
        }

    }
}
