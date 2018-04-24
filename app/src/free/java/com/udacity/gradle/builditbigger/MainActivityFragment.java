package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.example.com.libjokedisplay.JokeDisplayActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {


    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private InterstitialAd interstitialAd;
    private ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = root.findViewById(R.id.progressBar);
        Button tellJoke = root.findViewById(R.id.jokesButton);
        tellJoke.setOnClickListener(onClickListener);

        initInterstitialAd();
        loadInterstitialAd();

        AdView mAdView = root.findViewById(R.id.adView);
        displayBannerAd(mAdView);

        return root;
    }


    public EndpointsAsyncTask.AsyncTaskObserver asyncTaskObserver = new EndpointsAsyncTask.AsyncTaskObserver() {
        @Override
        public void onTaskCompleted(String data) {

            Intent intent = new Intent(getContext(), JokeDisplayActivity.class);
            intent.putExtra("joke", data);
            startActivity(intent);
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //https://developers.google.com/admob/android/interstitial
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {
                Log.e(TAG, getString(R.string.interbla_not_loaded));
            }

            interstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Log.e(TAG, "interstitialAd.onAdFailedToLoad -> error: "+i);
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    loadInterstitialAd();
                    displayJoke();
                }
            });
        }
    };


    @SuppressWarnings("unchecked")
    private void displayJoke() {
        progressBar.setVisibility(View.VISIBLE);
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(asyncTaskObserver);
        endpointsAsyncTask.execute(new Pair<Context, String>(getContext(), null));
    }

    private void displayBannerAd(AdView adview) {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adview.loadAd(adRequest);
    }

    private void initInterstitialAd() {
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
    }

    private void loadInterstitialAd() {
        interstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build());
    }
}
