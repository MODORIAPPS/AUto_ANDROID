package com.modori.kwonkiseokee.AUto.RA;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.modori.kwonkiseokee.AUto.PhotoDetail;
import com.modori.kwonkiseokee.AUto.R;

import java.util.List;

public class ListPhotoRA extends RecyclerView.Adapter<ListPhotoRA.ViewHolder> {

    private List<String> photoUrl;
    private List<String> photoID;
    Context context;

    public ListPhotoRA(Context context, List<String> data, List<String> photoID) {
        this.context = context;
        this.photoUrl = data;
        this.photoID = photoID;
    }

    @NonNull
    @Override
    public ListPhotoRA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ListPhotoRA.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPhotoRA.ViewHolder holder, final int position) {
        String photoUrl = this.photoUrl.get(position);
        final String photoID = this.photoID.get(position);


        Glide.with(context).load(photoUrl).centerCrop().into(holder.imageCleanView);

        holder.photoCardofList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Log.d("ListPhotoRA", String.valueOf(position));

                Intent intent = new Intent(context, PhotoDetail.class);
                intent.putExtra("id", photoID);
                Log.d("id", photoID);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return photoUrl.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageCleanView;
        CardView photoCardofList;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoCardofList = itemView.findViewById(R.id.photoCardofList);
            imageCleanView = itemView.findViewById(R.id.imageCleanView);
        }
    }
}
