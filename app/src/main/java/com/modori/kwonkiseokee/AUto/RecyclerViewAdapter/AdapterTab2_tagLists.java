package com.modori.kwonkiseokee.AUto.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.modori.kwonkiseokee.AUto.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterTab2_tagLists extends RecyclerView.Adapter<AdapterTab2_tagLists.ViewHolder> {

    private ArrayList<String> tagLists;

    private Context context;
    private View.OnClickListener onClickItem;

    public AdapterTab2_tagLists(Context context, ArrayList<String> tagLists, View.OnClickListener onClickItem) {
        this.context = context;
        this.tagLists = tagLists;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taglists_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = tagLists.get(position);
        holder.tags.setText(item);
        holder.tags.setOnClickListener(onClickItem);

    }

    @Override
    public int getItemCount() {
        return tagLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tags;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tags = itemView.findViewById(R.id.tags);
        }
    }
}
