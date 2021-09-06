package com.greedygame.sdkxinterstitialdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.greedygame.core.interstitial.general.GGInterstitialAd;
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener;
import com.greedygame.core.models.general.AdErrors;

public class AutoRefreshingInterstitialExampleActivity extends AppCompatActivity {
    GGInterstitialAd ggInterstitialAd;
    private Button showAd;
    private InterstitialEventListener eventListener = new InterstitialEventListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ggInterstitialAd  = new GGInterstitialAd(this,"float-4839");
        showAd = findViewById(R.id.showAd);
        showAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAd();
            }
        });

    }


    private void showAd(){
        if(ggInterstitialAd.isAdLoaded()){
            ggInterstitialAd.show();
        }
        else{
            loadIntersitialAd();
        }
    }

    private void loadIntersitialAd(){
        ggInterstitialAd.setListener(eventListener);
        ggInterstitialAd.loadAd();
    }



    class InterstitialEventListener implements GGInterstitialEventsListener {
        @Override
        public void onAdShowFailed() {
            Log.d("GGAD","Ad Show failed");
        }
        @Override
        public void onAdClosed() {
            // Setting flag to false to not show the ad again. This covers the case of opening
            // and ad that is already loaded
            Log.d("GGAD","Ad Closed");
            ggInterstitialAd.loadAd(); // used to auto-refresh
        }
        @Override
        public void onAdOpened() {
            //Called when ad has been opened

        }
        @Override
        public void onAdLoadFailed(AdErrors cause) {
            Toast.makeText(AutoRefreshingInterstitialExampleActivity.this, "Ad Load failed "+cause.toString(), Toast.LENGTH_SHORT).show();
            //Called when the ad load failed. The reason is available in cause variable

        }
        @Override
        public void onAdLoaded() {
        }
    }
}
