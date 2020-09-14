package com.jstechnologies.coronalive;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CountryViewHolder extends RecyclerView.ViewHolder {

    LinearLayout layout;
    TextView id,name,cases;
    CircleImageView flag;

    public CountryViewHolder(@NonNull View itemView) {
        super(itemView);
        layout=itemView.findViewById(R.id.layout);
        id=itemView.findViewById(R.id.number);
        name=itemView.findViewById(R.id.name);
        cases=itemView.findViewById(R.id.cases);
        flag=itemView.findViewById(R.id.flag);
    }
}
