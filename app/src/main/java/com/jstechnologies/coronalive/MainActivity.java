package com.jstechnologies.coronalive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blongho.country_data.World;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView newcase,totalcase,deaths,recovered;
    SwipeRefreshLayout swipeLayout;
    Button request;
    private AdView mAdView;
    ArrayList<DataModel> models= new ArrayList<>();
    private RecyclerView recyclerView;
    private CountryAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newcase= findViewById(R.id.new_cases);
        totalcase=findViewById(R.id.total_cases);
        deaths=findViewById(R.id.deaths);
        recovered=findViewById(R.id.recovered);
        swipeLayout=findViewById(R.id.swipeLayout);
        LoadBannerAd();
        GetCountryList();
        GetLiveStats();
        World.init(getApplicationContext());
        MobileAds.initialize(this,"YOUR ADMOB ID");
        recyclerView = (RecyclerView) findViewById(R.id.countries_recycler);

        mAdapter = new CountryAdapter(this,models);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // set the adapter
        recyclerView.setAdapter(mAdapter);


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCountryList();
                GetLiveStats();
            }
        });
    }

    public void GetCountryList()
    {

        new AsyncTask<String,String,String>(){
            OkHttpClient client = new OkHttpClient();
            @Override
            protected String doInBackground(String... strings) {
                Request request = new Request.Builder()
                        .url("https://coronavirus-monitor.p.rapidapi.com/coronavirus/cases_by_country.php")
                        .get()
                        .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "YOUR RAPID API KEY")
                        .build();


                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Toast.makeText(getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                            final String myResponse = response.body().string();

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        JSONObject json = new JSONObject(myResponse);
                                        FetchDataFromJson(json);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });

                return "No Result";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
            }
        }.execute();


    }

    public void FetchDataFromJson(JSONObject Rawdata)
    {
        models.clear();
        try {
            JSONArray array=Rawdata.getJSONArray("countries_stat");
           for(int i=0;i<array.length();i++)
           {
               models.add((DataModel)DataModel.ParseData(array.getJSONObject(i)));

           }
            mAdapter.notifyDataSetChanged();
            swipeLayout.setRefreshing(false);
           Toast.makeText(this,"countries data: "+models.size(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void GetLiveStats()
    {
        new AsyncTask<String,String,String>(){
            OkHttpClient client = new OkHttpClient();
            @Override
            protected String doInBackground(String... strings) {
                Request request = new Request.Builder()
                        .url("https://coronavirus-monitor.p.rapidapi.com/coronavirus/worldstat.php")
                        .get()
                        .addHeader("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "YOUR RAPID API KEY")
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Toast.makeText(getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                        final String myResponse = response.body().string();

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    JSONObject json = new JSONObject(myResponse);
                                    UpdateView(json);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });

                return "No Result";
            }
        }.execute();
    }

    private void UpdateView(JSONObject json) {

        try {
            totalcase.setText(json.getString("total_cases"));
            newcase.setText(json.getString("new_cases"));
            deaths.setText(json.getString("total_deaths"));
            recovered.setText(json.getString("total_recovered"));
            swipeLayout.setRefreshing(false);

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
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
