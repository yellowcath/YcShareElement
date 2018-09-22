package us.pinguo.shareelementdemo.advanced;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import us.pinguo.shareelementdemo.advanced.content.BaseContentCell;
import us.pinguo.shareelementdemo.advanced.content.ImageContentCell;
import us.pinguo.shareelementdemo.advanced.content.VideoContentCell;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerAdapter;
import us.pinguo.shareelementdemo.transform.YcShareElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class AdvancedContentFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private BasePagerAdapter mAdapter;
    private VideoContentCell mPlayingCell;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new ViewPager(container.getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view;
        mViewPager.setBackgroundColor(0xFF323232);
        mAdapter = new BasePagerAdapter();
        mViewPager.addOnPageChangeListener(this);
        initCells();
        mViewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mViewPager.getViewTreeObserver().removeOnPreDrawListener(this);
                YcShareElement.onShareElementReady(getActivity());
                return false;
            }
        });
    }

    private void initCells() {
        ArrayList<Parcelable> dataList = getArguments().getParcelableArrayList("data");
        int select = getArguments().getInt("select", 0);
        List<BaseContentCell> cellList = new ArrayList<>(dataList.size());
        for (Parcelable data : dataList) {
            if (data instanceof Image) {
                cellList.add(new ImageContentCell((Image) data));
            } else if (data instanceof Video) {
                cellList.add(new VideoContentCell((Video) data));
            }
        }
        mAdapter.addItems(cellList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(select);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mPlayingCell != null) {
            GSYVideoManager.instance().stop();
        }
        if (mAdapter.getItem(position) instanceof VideoContentCell) {
            mPlayingCell = ((VideoContentCell) mAdapter.getItem(position));
            mPlayingCell.startPlay();
        } else {
            mPlayingCell = null;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPause() {
        if (mPlayingCell != null) {
            GSYVideoManager.instance().pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mPlayingCell != null) {
            GSYVideoManager.instance().start();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.instance().releaseMediaPlayer();
    }

}
