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

public class GetFromGalleryRA extends RecyclerView.Adapter<GetFromGalleryRA.ViewHolder> implements View.OnClickListener {

    private List<String> imageLists;
    private Context context;
    private View.OnClickListener onClickItem;
    private boolean dataType;
    private Realm realm;

    String photoUri;

    public GetFromGalleryRA(Context context, List<String> imageLists, View.OnClickListener onClickItem, boolean dataType) {
        this.context = context;
        this.imageLists = imageLists;
        this.onClickItem = onClickItem;
        this.dataType = dataType;
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

        holder.pick_ImagesView.setOnClickListener(this);


        Log.d("어댑터 들어옴", item);
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }


    @Override
    public void onClick(View v) {
        if (dataType) {
            makeDeleteDialog(true, photoUri);

        }else{
            makeDeleteDialog(false, photoUri);

            // 새로운 사진


        }
    }


    public void makeDeleteDialog(boolean dataType, String photoUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("사진 삭제")        // 제목 설정
                .setMessage("목록에서 사진을 삭제할까요?")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", (dialog, whichButton) -> {
                    if (dataType) {
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                DevicePhotoDTO photoDTO = realm.where(DevicePhotoDTO.class).equalTo("photoUri", photoUri).findFirst();
                                if (photoDTO.isValid()) {
                                    photoDTO.deleteFromRealm();
                                }
                            }
                        });

                    } else {
                        // new Photos

                    }
                    //삭제
                }).setNegativeButton("취소", (dialog, whichButton) -> dialog.cancel());

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pick_ImagesView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            pick_ImagesView = itemView.findViewById(R.id.pick_ImagesView);
        }
    }
}
