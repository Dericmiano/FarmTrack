package com.example.farmtrack.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmtrack.R;
import com.example.farmtrack.model.Tips;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TipRecyclerAdapter extends RecyclerView.Adapter<TipRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Tips> tipsList;

    public TipRecyclerAdapter(Context context, List<Tips> tipsList) {
        this.context = context;
        this.tipsList = tipsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.tip_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tips tips = tipsList.get(position);
        String imageUrl;
        holder.topic.setText(tips.getTopic());
        holder.title.setText(tips.getTitle());
        holder.date.setText(tips.getDate());
        holder.description.setText(tips.getDescription());
        imageUrl = tips.getImageUrl();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_three)
                .fit()
                .into(holder.image);



    }

    @Override
    public int getItemCount() {
        return tipsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topic,description,title,date;
        public ImageView image;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;
            topic = itemView.findViewById(R.id.tip_row_topic);
            description = itemView.findViewById(R.id.tip_row_description);
            title = itemView.findViewById(R.id.tip_row_title);
            date = itemView.findViewById(R.id.tip_row_date);
            image = itemView.findViewById(R.id.tip_row_image);

        }
    }
}
