package com.example.kwonkiseokee.setwallpaper;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private Context context;
    private final List<dataModel> dataLists;

    public RecyclerviewAdapter(List<dataModel> dataLists) {
        this.dataLists = dataLists;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_items, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {


        final dataModel item = dataLists.get(position);
        viewHolder.view_name.setText(item.getViewName());
        viewHolder.view_num.setText(item.getViewNum());
        viewHolder.view_image.setImageResource(item.getImage_url());




        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context = viewHolder.view_image.getContext();
                setWallpaper setWallpaper = new setWallpaper();
                setWallpaper.setter(viewHolder.view_image, context);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView view_name;
        TextView view_num;
        ImageView view_image;

        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.view = itemView;

            view_name = itemView.findViewById(R.id.view_name);
            view_num = itemView.findViewById(R.id.view_number);
            view_image = itemView.findViewById(R.id.view_imageView);


        }
    }
}
