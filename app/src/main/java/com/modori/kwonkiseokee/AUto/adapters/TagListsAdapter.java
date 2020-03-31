package com.modori.kwonkiseokee.AUto.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.data.Tag;
import com.modori.kwonkiseokee.AUto.utilities.OpenDialog;
import com.modori.kwonkiseokee.AUto.viewmodels.TagListViewModel;

import java.util.List;

public class TagListsAdapter extends RecyclerView.Adapter<TagListsAdapter.ViewHolder> {

    private List<Tag> tagLists;

    private Context mContext;
    private OpenDialog openDialog;
    private TagListViewModel tagViewModel;

    public TagListsAdapter(Context context, List<Tag> tagList, TagListViewModel viewModel) {
        this.mContext = context;
        this.tagLists = tagList;
        this.tagViewModel = viewModel;
    }

    public TagListsAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taglists_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String item = tagLists.get(position).getTag();
        holder.tags.setText(item);

        holder.tagShape.setOnClickListener(v -> {

            String currTag = holder.tags.getText().toString();

            Log.d("선택된 TAG 와 position", currTag + " | " + position);

            String title = mContext.getResources().getString(R.string.openDialog_title);
            String subtitle = mContext.getString(R.string.openDialog_content) + " '" + currTag + "' " + mContext.getString(R.string.openDialog_content2);

            // 화면 조정
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
            int width = dm.widthPixels; //디바이스 화면 너비
            int height = dm.heightPixels; //디바이스 화면 높이


            openDialog = new OpenDialog(mContext, position, title, subtitle, tagLists, tagViewModel);
            WindowManager.LayoutParams wm = openDialog.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
            wm.copyFrom(openDialog.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
            wm.width = width;  //화면 너비의 절반
//            wm.height = height / 3;  //화면 높이의 1/3

            openDialog.show();


        });

    }

    public void setTagLists(List<Tag> tagLists) {
        this.tagLists = tagLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (tagLists != null)
            return tagLists.size();
        else return 0;
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
