package com.modori.kwonkiseokee.AUto.RA;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.tab2_frag;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import static com.modori.kwonkiseokee.AUto.tab2_frag.PREFS_FILE;

public class Tab2_tagListsRA extends RecyclerView.Adapter<Tab2_tagListsRA.ViewHolder> {

    private ArrayList<String> tagLists;

    private Context mContext;

    public Tab2_tagListsRA(Context context, ArrayList<String> tagLists) {
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
                final SharedPreferences settings = mContext.getSharedPreferences(PREFS_FILE, 0);
                final SharedPreferences.Editor editor = settings.edit();

                String currTag = holder.tags.getText().toString();

                Log.d("선택된 TAG 와 position", currTag + " | " + position);

                final EditText edittext = new EditText(mContext);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("사진 검색 키워드 수정");
                builder.setMessage("기존의 " + currTag + " 키워드를 수정합니다.");
                builder.setView(edittext);
                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(mContext, edittext.getText().toString() ,Toast.LENGTH_LONG).show();

                                if (true) {
                                    Log.d("TAG 처리중", "진입됨");

                                    switch (position) {

                                        case 0:
                                            editor.putString("tag1", edittext.getText().toString());
                                            break;
                                        case 1:
                                            editor.putString("tag2", edittext.getText().toString());
                                            break;
                                        case 2:
                                            editor.putString("tag3", edittext.getText().toString());
                                            break;
                                        case 3:
                                            editor.putString("tag4", edittext.getText().toString());
                                            break;
                                        case 4:
                                            editor.putString("tag5", edittext.getText().toString());
                                            break;
                                        case 5:
                                            editor.putString("tag6", edittext.getText().toString());
                                            break;

                                        default:
                                            Log.d("TAG 처리중 오류", "예기치 않은 오류 발생");

                                    }
                                    editor.apply();

                                }


                            }
                        });
                builder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();

            }
        });

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
