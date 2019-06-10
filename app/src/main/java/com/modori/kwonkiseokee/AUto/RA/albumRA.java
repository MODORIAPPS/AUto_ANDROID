package com.modori.kwonkiseokee.AUto.RA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.data.AlbumDTO;

import java.util.List;

public class albumRA extends RecyclerView.Adapter<albumRA.ViewHolder> {

    private List<AlbumDTO> data;
    private Context context;

    public albumRA(Context context, List<AlbumDTO> albumDAta) {
        this.context = context;
        this.data = albumDAta;
    }

    @NonNull
    @Override
    public albumRA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new albumRA.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull albumRA.ViewHolder holder, final int position) {
        AlbumDTO item = data.get(position);
        holder.albumTitle.setText(item.getAlbumTitle());
        holder.albumContent.setText(item.getAlbumContent());
        holder.albumCnt.setText(item.getPhotoCnt() + " 개의 사진 있음");


        // 재생 및 일시정지 컨트롤
        holder.playView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.playView.setVisibility(View.GONE);
                holder.pauseView.setVisibility(View.VISIBLE);


            }
        });
        holder.pauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.playView.setVisibility(View.VISIBLE);
                holder.pauseView.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView albumTitle;
        TextView albumContent;
        TextView albumCnt;
        View playView;
        View pauseView;
        View deleteView;
        ImageView presenterImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            albumTitle = itemView.findViewById(R.id.albumTitle);
            albumContent = itemView.findViewById(R.id.albumContent);
            playView = itemView.findViewById(R.id.playView);
            pauseView = itemView.findViewById(R.id.pauseView);
            albumCnt = itemView.findViewById(R.id.albumCnt);
            deleteView = itemView.findViewById(R.id.deleteView);
            presenterImage = itemView.findViewById(R.id.presenterImage);

        }
    }
}
