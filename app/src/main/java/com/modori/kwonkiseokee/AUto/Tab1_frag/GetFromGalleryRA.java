package com.modori.kwonkiseokee.AUto.Tab1_frag;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.Util.FileManager;
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;

public class GetFromGalleryRA extends RecyclerView.Adapter<GetFromGalleryRA.ViewHolder> {

    private List<String> imageLists;
    private Context context;
    private Realm realm;
    private int type;
    private int index;

    String photoUri;

    public GetFromGalleryRA(Context context, List<String> imageLists, int type) {
        this.context = context;
        this.imageLists = imageLists;
        this.type = type;
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

        Glide.with(context).load(Uri.parse(item))
                .override(450, 450)
                .centerCrop()
                .into(holder.pick_ImagesView);
        photoUri = item;
        holder.pick_ImagesView.setOnClickListener(v -> {
            if (type == 0) {
                // oldphoto
                makeDeleteDialog(type, position, item);
            } else if (type == 1) {
                // newphoto
                makeDeleteDialog(type, position, item);


            }else{
                makeDeleteDialog(type, position, item);

            }
        });


        Log.d("어댑터 들어옴", item);
    }

    private void makeDeleteDialog(final int dataType, int position, String photoUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("사진 삭제")        // 제목 설정
                .setMessage("목록에서 사진을 삭제할까요?")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", (dialog, whichButton) -> {
                    if (dataType == 0) {

                        realm.beginTransaction();
                        RealmResults<DevicePhotoDTO> photoDTOS = realm.where(DevicePhotoDTO.class).equalTo("photoUri_d", photoUri).findAll();
                        Log.d("PhotosDTOS", photoDTOS.toString());
                        photoDTOS.deleteAllFromRealm();
                        realm.commitTransaction();

                        imageLists.remove(position);
                        notifyDataSetChanged();


                    } else if (dataType == 1) {
                        // new Photos
//                            pickedLists.remove(photoNewAdapter.getPhotoUri());
//                            photoNewAdapter.notifyDataSetChanged();
//                            newPhotosCnt.setText(pickedLists.size() + "");

                        imageLists.remove(position);
                        notifyDataSetChanged();

                    }else if(dataType == 2){
                        try {
                            File file = new File(FileManager.getPath(context,Uri.parse(photoUri)));
                            file.delete();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    //삭제
                }).setNegativeButton("취소", (dialog, whichButton) -> dialog.cancel());

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }

    public String getPhotoUri() {
        Log.d("getPhotoUri", index + " 가져옴");
        return imageLists.get(index);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pick_ImagesView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            pick_ImagesView = itemView.findViewById(R.id.pick_ImagesView);
        }
    }
}
