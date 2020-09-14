package com.jstechnologies.coronalive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blongho.country_data.World;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {

    TextView name,cases,deaths,region,tot_recovered,new_death,new_case,critical,active,density;
    CircleImageView flag;
    LinearLayout layout;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        layout=findViewById(R.id.container);
        name=findViewById(R.id.country_name);
        cases=findViewById(R.id.cases);
        deaths=findViewById(R.id.deaths);
        region=findViewById(R.id.region);
        tot_recovered=findViewById(R.id.recovered);
        new_death=findViewById(R.id.new_deaths);
        new_case=findViewById(R.id.new_cases);
        critical=findViewById(R.id.critical);
        active=findViewById(R.id.active);
        density=findViewById(R.id.case_density);
        flag=findViewById(R.id.flagimg);
        LoadBannerAd();
        layout.setOnTouchListener(new OnSwipeTouchListener(this)
        {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                finish();
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeRight();
                finish();
            }
        });

        updateUI();

    }
    public void updateUI()
    {
        Bundle extras= getIntent().getExtras();
        name.setText(extras.getString("country_name"));
        cases.setText(extras.getString("cases"));
        deaths.setText(extras.getString("deaths"));
        region.setText(extras.getString("region"));
        tot_recovered.setText(extras.getString("recovered"));
        new_death.setText(extras.getString("new_death"));
        new_case.setText(extras.getString("new_case"));
        critical.setText(extras.getString("critical"));
        active.setText(extras.getString("active"));
        density.setText(extras.getString("density"));
        flag.setImageResource(World.getFlagOf(extras.getString("country_name")));


    }
    private void LoadBannerAd()
    {
        //Adview
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if(i==AdRequest.ERROR_CODE_INTERNAL_ERROR)
                    Log.w("CORONA_LIVE","Ads internal error");
                if(i==AdRequest.ERROR_CODE_INVALID_REQUEST)
                    Log.w("CORONA_LIVE","Ads invalid request");
                if(i==AdRequest.ERROR_CODE_NETWORK_ERROR)
                    Log.w("CORONA_LIVE","Ads network error");
                if(i==AdRequest.ERROR_CODE_NO_FILL)
                    Log.w("CORONA_LIVE","Ads no fill");

            }
        });
        mAdView.loadAd(adRequest);
    }

}
