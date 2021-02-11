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
import com.greedygame.core.adview.modals.AdRequestErrors;
import com.greedygame.core.interstitial.general.GGInterstitialAd;
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener;

import java.util.logging.Logger;


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
                .build();
        GreedyGameAds.initWith(appConfig,null);
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
        public void onAdLeftApplication() {

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
        public void onAdLoadFailed(AdRequestErrors cause) {
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

