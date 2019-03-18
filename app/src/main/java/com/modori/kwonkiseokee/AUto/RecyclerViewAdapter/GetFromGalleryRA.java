package com.modori.kwonkiseokee.AUto.RecyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.modori.kwonkiseokee.AUto.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GetFromGalleryRA extends RecyclerView.Adapter<GetFromGalleryRA.ViewHolder> {

    private ArrayList<String> imageLists;
    private Context context;
    private View.OnClickListener onClickItem;

    public GetFromGalleryRA(Context context, ArrayList<String> imageLists, View.OnClickListener onClickItem) {
        this.context = context;
        this.imageLists = imageLists;
        this.onClickItem = onClickItem;
    }

    public GetFromGalleryRA(Context context, ArrayList<String> imageLists) {
        this.context = context;
        this.imageLists = imageLists;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picked_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = imageLists.get(position);
        //Glide.with(context).load(item).into(holder.pick_ImagesView);
        holder.pick_ImagesView.setImageURI(Uri.parse(item));
        Log.d("어댑터 들어옴", item);
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pick_ImagesView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pick_ImagesView = itemView.findViewById(R.id.pick_ImagesView);
        }
    }
}
