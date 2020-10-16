package com.greedygame.sdkxinterstitialdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.greedygame.core.adview.modals.AdRequestErrors;
import com.greedygame.core.interstitial.general.GGInterstitialAd;
import com.greedygame.core.interstitial.general.GGInterstitialEventsListener;

public class SecondActivity extends AppCompatActivity {
    GGInterstitialAd ggInterstitialAd;
    private boolean shouldShowAd = true;
    private Button loadAdAgainButton;
    private InterstitialEventListener eventListener = new InterstitialEventListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ggInterstitialAd  = new GGInterstitialAd(this,"float-4839");
        loadAdAgainButton = findViewById(R.id.showAgainButton);
        loadAdAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAdAgain();
            }
        });
        loadIntersitialAd();
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
            shouldShowAd = true;
            loadIntersitialAd();
        }
    }

    private void loadIntersitialAd(){
        ggInterstitialAd.loadAd(eventListener);
    }



    class InterstitialEventListener implements GGInterstitialEventsListener {
        @Override
        public void onAdLeftApplication() {

        }
        @Override
        public void onAdClosed() {
            // Setting flag to false to not show the ad again. This covers the case of opening
            // and ad that is already loaded
            Log.d("GGAD","Ad Closed - 2");
            shouldShowAd = false;
            startActivity(new Intent(SecondActivity.this,MainActivity.class));
            finish();
        }
        @Override
        public void onAdOpened() {
            //Called when ad has been opened

        }
        @Override
        public void onAdLoadFailed(AdRequestErrors cause) {
            Toast.makeText(SecondActivity.this, "Ad Load failed "+cause.toString(), Toast.LENGTH_SHORT).show();
            //Called when the ad load failed. The reason is available in cause variable

        }
        @Override
        public void onAdLoaded() {
            if(shouldShowAd){
                // Setting flag to false to not show the ad again.
                shouldShowAd = false;
                ggInterstitialAd.show();
            }

        }
    }
}