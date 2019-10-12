package com.modori.kwonkiseokee.AUto.Tab2_frag;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.RetrofitService.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenDialog extends Dialog {

    EditText inputText;
    Button positiveBtn, negativeBtn;
    TextView title_dia, subTitle_dia, warning_dia;
    Context mContext;


    int availCnt;
    int position;

    final String TAG = getClass().getName();

    private TagViewModel tagViewModel;
    private List<Tag> tagList;

    String oldTag;


    public OpenDialog(@NonNull final Context context, final int position, String title, String subtitle,List<Tag> tagLists, TagViewModel viewModel) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_layout);
        this.mContext = context;

        this.tagViewModel = viewModel;

        this.tagList = tagLists;


        title_dia = findViewById(R.id.Title_dia);
        subTitle_dia = findViewById(R.id.subTitle_dia);
        warning_dia = findViewById(R.id.warning_dia);

        inputText = findViewById(R.id.inputText_dia);
        positiveBtn = findViewById(R.id.positiveBtn_dia);
        negativeBtn = findViewById(R.id.negativeBtn_dia);

        title_dia.setText(title);
        subTitle_dia.setText(subtitle);

//        tagViewModel.getTagLists().observe(context, words -> {
//            tagList = words;
//            System.out.println(words.size());
//
//        });

        //System.out.println(tagList.size());

        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputText.getText().toString().isEmpty()) {
                    warning_dia.setVisibility(View.VISIBLE);
                    String str3 = getContext().getResources().getString(R.string.opendialog_error2);
                    warning_dia.setText(str3);
                } else {
                    oldTag = inputText.getText().toString();
                    availableTagCheck(oldTag, position);

                }
            }
        });

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void availableTagCheck(final String inputTag, final int position) {
        final ProgressDialog mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(true);
        String str4 = getContext().getResources().getString(R.string.opendialog_checkAvail);
        mProgressDialog.setMessage(str4);
        mProgressDialog.show();


        ApiClient.getPhotoByKeyword().getPhotobyKeyward(inputTag, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //progressBar_dia.setVisibility(View.GONE);

                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                        availCnt = Integer.valueOf(response.body().getTotal());

                        if (availCnt == 0) {
                            //사진이 없는 경우
                            warning_dia.setVisibility(View.VISIBLE);
                            String str3 = getContext().getResources().getString(R.string.opendialog_noResult);
                            warning_dia.setText(str3);
                        } else {
                            if (!TagTools.overLapTagCheck(tagList, inputTag)) {
                                Log.d("TAG 처리중", "진입됨");
                                warning_dia.setVisibility(View.GONE);

                                switch (position) {

                                    case 0:
                                        tagViewModel.update(inputTag,0);

                                        break;
                                    case 1:
                                        tagViewModel.update(inputTag,1);

                                        break;
                                    case 2:
                                        tagViewModel.update(inputTag,2);

                                        break;
                                    case 3:
                                        tagViewModel.update(inputTag,3);

                                        break;
                                    case 4:
                                        tagViewModel.update(inputTag,4);
                                        break;
                                    case 5:
                                        tagViewModel.update(inputTag,5);

                                        break;

                                    default:
                                        Log.d("TAG 처리중 오류", "예기치 않은 오류 발생");

                                }

                                String str2 = getContext().getResources().getString(R.string.opendialog_newAlert);
                                Toast.makeText(mContext, str2, Toast.LENGTH_SHORT).show();
                                dismiss();

                            } else {
                                warning_dia.setVisibility(View.VISIBLE);
                                String str1 = getContext().getResources().getString(R.string.openDialog_error);
                                warning_dia.setText(str1);
                            }
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {

            }
        });


    }


}
