package us.pinguo.shareelementdemo.simple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class Simple1Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_s1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View imgView = view.findViewById(R.id.s1_img);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionSet simple2Transition = new TransitionSet();
                simple2Transition.addTransition(new ChangeImageTransform());
                simple2Transition.addTransition(new ChangeBounds());

                Simple2Fragment simple2Fragment = new Simple2Fragment();
                simple2Fragment.setSharedElementEnterTransition(simple2Transition);
                simple2Fragment.setSharedElementReturnTransition(simple2Transition);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addSharedElement(imgView, imgView.getTransitionName());
                fragmentTransaction.replace(R.id.simple_container, simple2Fragment, "simple2");
                fragmentTransaction.addToBackStack("simple2");
                fragmentTransaction.commit();
            }
        });
    }
}
