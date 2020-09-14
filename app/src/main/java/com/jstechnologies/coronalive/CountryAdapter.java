package com.jstechnologies.coronalive;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.World;
import com.google.android.gms.ads.NativeExpressAdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    ArrayList<DataModel>models;
    Context ctx;

    public CountryAdapter(Context context,ArrayList<DataModel> models) {
        this.models = models;
        this.ctx=context;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_countries, parent, false);

        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CountryViewHolder holder, final int position) {

        holder.id.setText((position+1)+".");
        holder.name.setText(models.get(position).getCountry_name());
        holder.cases.setText(models.get(position).getCases());
        holder.flag.setImageResource(World.getFlagOf(models.get(position).getCountry_name()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ctx,DetailsActivity.class);
                i.putExtra("country_name",models.get(position).getCountry_name());
                i.putExtra("cases",models.get(position).getCases());
                i.putExtra("deaths",models.get(position).getDeaths());
                i.putExtra("region",models.get(position).getRegion());
                i.putExtra("recovered",models.get(position).getTotal_recovered());
                i.putExtra("new_death",models.get(position).getNew_deaths());
                i.putExtra("new_case",models.get(position).getNew_cases());
                i.putExtra("critical",models.get(position).getSerious_critical());
                i.putExtra("active",models.get(position).getActive_cases());
                i.putExtra("density",models.get(position).getTotal_cases_per_1m_population());
                ctx.startActivity(i);

            }
        });
        if(position%10==0) {

        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
