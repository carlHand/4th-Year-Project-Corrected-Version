package com.projectyr4x00091174.carl.traingain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by carl on 07/04/2015.
 */
public class HomeFragment extends Fragment {

    public static final String EXTRA_ITEM_INDEX = "EXTRA_ITEM_INDEX";
    private String itemTitle;

    public HomeFragment() {
        // required for re-initialization
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.tv_description);
        int itemIndex = getArguments().getInt(EXTRA_ITEM_INDEX);
        itemTitle = getResources().getStringArray(R.array.nav_slider_items)[itemIndex];
        textView.setText(itemTitle);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(itemTitle);
    }
}