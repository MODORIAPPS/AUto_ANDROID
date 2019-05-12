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
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.modori.kwonkiseokee.AUto.PhotoDetail;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.util.List;

public class ListPhotoRA extends RecyclerView.Adapter<ListPhotoRA.ViewHolder> {

    private List<Results> data;
    Context context;

    public ListPhotoRA(Context context, List<Results> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ListPhotoRA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new ListPhotoRA.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPhotoRA.ViewHolder holder, final int position) {
        final Results item = data.get(position);

        // https://gun0912.tistory.com/17
        String regularPhoto = item.getUrls().getRegular();
        String smallPhoto = item.getUrls().getSmall();
        String thumbPhoto = item.getUrls().getThumb();

        YoYo.with(Techniques.FadeIn).playOn(holder.imageCleanView);
        YoYo.with(Techniques.FadeIn).playOn(holder.photoCardofList);

        Glide.with(context).load(regularPhoto).centerCrop().into(holder.imageCleanView);

        holder.photoCardofList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Log.d("ListPhotoRA", String.valueOf(position));

                Intent intent = new Intent(context, PhotoDetail.class);
                intent.putExtra("id", item.getId());
                Log.d("id",item.getId());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
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
