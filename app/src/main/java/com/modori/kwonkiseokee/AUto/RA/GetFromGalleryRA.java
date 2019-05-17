package com.modori.kwonkiseokee.AUto.RA;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;

public class GetFromGalleryRA extends RecyclerView.Adapter<GetFromGalleryRA.ViewHolder>{

    private List<String> imageLists;
    private Context context;
    private View.OnClickListener onClickItem;
    private Realm realm;

    String photoUri;

    public GetFromGalleryRA(Context context, List<String> imageLists, View.OnClickListener onClickItem) {
        this.context = context;
        this.imageLists = imageLists;
        this.onClickItem = onClickItem;
    }

    public GetFromGalleryRA(Context context, List<String> imageLists) {
        this.context = context;
        this.imageLists = imageLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picked_items, parent, false);
        realm = Realm.getDefaultInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = imageLists.get(position);

        Glide.with(context).load(Uri.parse(item)).into(holder.pick_ImagesView);
        photoUri = item;
        holder.pick_ImagesView.setOnClickListener(onClickItem);


        Log.d("어댑터 들어옴", item);
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }

    public String getPhotoUri(){

        return photoUri;
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pick_ImagesView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            pick_ImagesView = itemView.findViewById(R.id.pick_ImagesView);
        }
    }
}
