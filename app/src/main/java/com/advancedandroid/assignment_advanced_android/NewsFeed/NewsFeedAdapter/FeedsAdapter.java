package com.advancedandroid.assignment_advanced_android.NewsFeed.NewsFeedAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.advancedandroid.assignment_advanced_android.NewsFeed.Model.FeedItem;
import com.advancedandroid.assignment_advanced_android.NewsFeed.WebViewActivity;
import com.advancedandroid.assignment_advanced_android.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder>{

    ArrayList<FeedItem> feedItems;
    private Context context;

    private View view;
    public FeedsAdapter(Context context, ArrayList<FeedItem>feedItems){
        this.feedItems=feedItems;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custum_row_news_item,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        YoYo.with(Techniques.FadeIn).playOn(holder.cardView);
        FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        holder.Date.setText(current.getPubDate());
        Picasso.get().load(current.getThumbnailUrl()).into(holder.Thumbnail);
//        Log.e("ImageLink", "onBindViewHolder: "+current.getThumbnailUrl());holder.cardView.setOnClickListener(new View.OnClickListener() {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebViewActivity.class);
                Bundle bundle = new Bundle();
                String link = feedItems.get(position).getLink();
                bundle.putString("LINK",link);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    public FeedItem getItem(int position) {
        return feedItems.get(position);
    }

    @Override
    public int getItemCount() {

        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView Title,Description,Date;
        ImageView Thumbnail;
        CardView cardView;
        public MyViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();
            Title= itemView.findViewById(R.id.title_text);
            Description= itemView.findViewById(R.id.description_text);
            Date= itemView.findViewById(R.id.date_text);
            Thumbnail= itemView.findViewById(R.id.thumb_img);
            cardView= itemView.findViewById(R.id.cardview);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
