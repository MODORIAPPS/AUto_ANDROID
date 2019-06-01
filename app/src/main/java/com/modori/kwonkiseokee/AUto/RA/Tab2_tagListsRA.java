package com.modori.kwonkiseokee.AUto.RA;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.Util.OpenDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Tab2_tagListsRA extends RecyclerView.Adapter<Tab2_tagListsRA.ViewHolder> {

    private List<String> tagLists;

    private Context mContext;
    private OpenDialog openDialog;

    public Tab2_tagListsRA(Context context, List<String> tagLists) {
        this.mContext = context;
        this.tagLists = tagLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taglists_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String item = tagLists.get(position);
        holder.tags.setText(item);

        holder.tagShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currTag = holder.tags.getText().toString();

                Log.d("선택된 TAG 와 position", currTag + " | " + position);

                String title = "사진 검색 키워드 수정";
                String subtitle = "기존의 " + currTag + " 키워드를 수정합니다.";

                // 화면 조정
                DisplayMetrics dm = mContext.getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
                int width = dm.widthPixels; //디바이스 화면 너비
                int height = dm.heightPixels; //디바이스 화면 높이


                openDialog = new OpenDialog(mContext,position, title, subtitle);
                WindowManager.LayoutParams wm = openDialog.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
                wm.copyFrom(openDialog.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
                wm.width = width;  //화면 너비의 절반
                wm.height = height / 3;  //화면 높이의 1/3

                openDialog.show();



            }
        });

    }

    private void tagSet(){

    }

    @Override
    public int getItemCount() {
        return tagLists.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        View tagShape;
        TextView tags;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tagShape = itemView.findViewById(R.id.tagShape);
            tags = itemView.findViewById(R.id.tags);
        }
    }
}
