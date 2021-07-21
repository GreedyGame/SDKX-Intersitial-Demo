package com.greedygame.sdkxinterstitialdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.greedygame.core.AppConfig;
import com.greedygame.core.GreedyGameAds;
import com.greedygame.core.app_open_ads.general.AdOrientation;
import com.greedygame.core.app_open_ads.general.AppOpenAdsEventsListener;
import com.greedygame.core.app_open_ads.general.GGAppOpenAds;
import com.greedygame.core.interstitial.general.GGInterstitialAd;
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener;
import com.greedygame.core.models.general.AdErrors;


public class MainActivity extends AppCompatActivity {
    GGInterstitialAd ggInterstitialAd;


    private InterstitialEventListener eventListener = new InterstitialEventListener();
    private Button loadAdAgainButton;
    private Button loadAd;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Initializing the SDK
         */
        AppConfig appConfig = new AppConfig.Builder(this)
                .withAppId("89221032")
                .enableFacebookAds(true)
                .build();
        GreedyGameAds.initWith(appConfig,null);
        GGAppOpenAds.setListener(new AppOpenAdsEventsListener() {

            @Override
            public void onAdShowFailed(){

            }
            @Override
            public void onAdLoaded(){
                GGAppOpenAds.show();
            }

            @Override
            public void onAdLoadFailed(AdErrors cause){

            }

            @Override
            public void onAdOpened(){

            }

            @Override
            public void onAdClosed(){

            }
        });

        GGAppOpenAds.setOrientation(AdOrientation.PORTRAIT);
        GGAppOpenAds.loadAd("float-test_appopen");
        ggInterstitialAd  = new GGInterstitialAd(this,"float-4839");

        progressBar = findViewById(R.id.progressBar);
        loadAdAgainButton = findViewById(R.id.loadAdAgain);
        loadAd = findViewById(R.id.loadAd);
        loadAdAgainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showAdAgain();
            }
        });
        loadAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                loadIntersitialAd();
            }
        });
    }

    private void showAdAgain(){
        if(ggInterstitialAd.isAdLoaded()){
            /*
             SDKX automatically refresh the ad automatically when ad is closed. If its already
             loaded, we show it here.
             */
            ggInterstitialAd.show();

        }
        else{
            /*
            Incase the ad is not loaded by the time this function is called again,
            we  make the flag true and load the ad again, the ad will be shown automatically
            once it's loaded
             */
            loadIntersitialAd();
        }
    }

    private void loadIntersitialAd(){
        progressBar.setVisibility(View.VISIBLE);
        ggInterstitialAd.setListener(eventListener);
        ggInterstitialAd.loadAd();
    }
    class InterstitialEventListener implements GGInterstitialEventsListener{

        @Override
        public void onAdShowFailed() {

        }

        @Override
        public void onAdClosed() {
            // Setting flag to false to not show the ad again. This covers the case of opening
            // and ad that is already loaded
            Log.d("GGAD","Ad Closed - 1");
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
            finish();
        }
        @Override
        public void onAdOpened() {
            //Called when ad has been opened

        }
        @Override
        public void onAdLoadFailed(AdErrors cause) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Ad Load failed "+cause.toString(), Toast.LENGTH_SHORT).show();
            //Called when the ad load failed. The reason is available in cause variable

        }
        @Override
        public void onAdLoaded() {
            progressBar.setVisibility(View.GONE);
                ggInterstitialAd.show();

        }
    }


}

