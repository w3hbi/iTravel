package com.example.itravel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.itravel.Model.Post;
import com.example.itravel.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    List<Post> postData;
    public PostAdapter(List<Post> postData) {
        this.postData = postData;
    }

    @NonNull
    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.MyViewHolder holder, int position) {
    // binding our data name.gt ..set ..
        holder.place_txt_v.setText(postData.get(position).getPlace());
        holder.lat_txt_v.setText(postData.get(position).getLatitude());
        holder.lng_txt_v.setText(postData.get(position).getLongitude());
        Glide.with(holder.itemView.getContext())
                .load(postData.get(position).getImage())
                .into(holder.post_img_v);


    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView post_img_v;
        TextView place_txt_v,lat_txt_v,lng_txt_v;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            post_img_v = itemView.findViewById(R.id.post_img_v);
            place_txt_v = itemView.findViewById(R.id.place_txt_v);
            lat_txt_v = itemView.findViewById(R.id.lat_txt_v);
            lng_txt_v = itemView.findViewById(R.id.lng_txt_v);
        }
    }
}
