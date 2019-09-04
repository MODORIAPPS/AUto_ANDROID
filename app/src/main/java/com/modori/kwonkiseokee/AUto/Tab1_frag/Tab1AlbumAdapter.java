package com.modori.kwonkiseokee.AUto.Tab1_frag;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.modori.kwonkiseokee.AUto.AlbumDetail;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.Service.SetWallpaperJob;

import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class Tab1AlbumAdapter extends RecyclerView.Adapter<Tab1AlbumAdapter.ViewHolder> {

    private List<AlbumDTO> data;
    private Context context;

    public Tab1AlbumAdapter(Context context, List<AlbumDTO> albumDAta) {
        this.context = context;
        this.data = albumDAta;
    }

    @NonNull
    @Override
    public Tab1AlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        return new Tab1AlbumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tab1AlbumAdapter.ViewHolder holder, final int position) {
        AlbumDTO item = data.get(position);
        holder.albumTitle.setText(item.getAlbumTitle());
        holder.albumContent.setText(item.getAlbumContent());
        holder.albumCnt.setText(item.getPhotoCnt() + " 개의 사진 있음");

        if(item.isAvail())
            holder.downloadMark.setVisibility(View.GONE);


        // 재생 및 일시정지 컨트롤
        holder.playView.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("");
            builder.setMessage("기존에 실행중인 변경 서비스가 모두 작동중지 됩니다. 계속하시겠어요?");
            builder.setPositiveButton("확인", (dialog, which) -> {
                unSetAutoChangeSlide();
                holder.playView.setVisibility(View.GONE);
                holder.pauseView.setVisibility(View.VISIBLE);

            });
            builder.setNegativeButton("취소", (dialog, which) -> {
                dialog.dismiss();
            });

            builder.setCancelable(false);
            builder.show();




        });
        holder.pauseView.setOnClickListener(v -> {


            holder.playView.setVisibility(View.VISIBLE);
            holder.pauseView.setVisibility(View.GONE);

        });

        holder.mainItem_View.setOnClickListener(v -> {
            Intent intent = new Intent(context, AlbumDetail.class);
            intent.putExtra("AlbumDTO", item);
            context.startActivity(intent);
        });

        holder.downloadBtn.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("사진 다운로드");
            builder.setMessage("사진 목록을 인터넷에서 다운로드 받습니다. 고용량의 데이터가 필요하므로 셀룰러 네트워크 환경에서는" +
                    "권장하지 않습니다. 계속하시겠어요?");
            builder.setPositiveButton("확인", (dialog, which) -> {

            });
            builder.setNegativeButton("취소", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.setCancelable(false);
            builder.show();

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View mainItem_View;
        View downloadMark;
        TextView albumTitle;
        TextView albumContent;
        TextView albumCnt;
        View playView;
        View pauseView;
        View deleteView;
        ImageView presenterImage;
        ImageView downloadBtn;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainItem_View = itemView.findViewById(R.id.mainItem_View);
            albumTitle = itemView.findViewById(R.id.albumTitle);
            albumContent = itemView.findViewById(R.id.albumContent);
            playView = itemView.findViewById(R.id.playView);
            pauseView = itemView.findViewById(R.id.pauseView);
            albumCnt = itemView.findViewById(R.id.albumCnt);
            deleteView = itemView.findViewById(R.id.deleteView);
            presenterImage = itemView.findViewById(R.id.presenterImage);
            downloadMark = itemView.findViewById(R.id.downloadMark);
            downloadBtn = itemView.findViewById(R.id.downloadBtn);

        }
    }

    private void unSetAutoChangeSlide() {
        Intent intent = new Intent(context, SetWallpaperJob.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(sender);


    }
}
