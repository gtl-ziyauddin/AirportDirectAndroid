package com.nimius.airportdirect.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nimius.airportdirect.utility.Constants;
import com.nimius.airportdirect.firebase.FirebaseConstant;
import com.nimius.airpotdirect.R;
import com.nimius.airpotdirect.databinding.FragmentToursActivitiesBinding;


public class ToursActivitiesFragment extends BaseFragment {
    FragmentToursActivitiesBinding toursActivitiesBinding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setView(R.layout.fragment_tours_activities, true);

    }


    /**
     * INNIT VARIABLES
     */
    @Override
    public void initVariable() {
        toursActivitiesBinding = getBinding();
    }

    @Override
    public void loadData() {
        toursActivitiesBinding.webToursActivities.getSettings().setJavaScriptEnabled(true); // enable javascript
        toursActivitiesBinding.webToursActivities.setWebViewClient(new MyWebViewClient());
        showProgressbar();
        toursActivitiesBinding.webToursActivities.loadUrl(Constants.TOURS_ACITIVITIES);


        /***
         * FIREBASE EVENT
         */
        firebaseAnalytics("", FirebaseConstant.SCREEN_TOURS_ACTIVITIES, FirebaseConstant.LABEL_TOURS_ACTIVITIES);
    }


    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);


            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            hideProgressbar();

        }
    }


}
