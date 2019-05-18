package com.modori.kwonkiseokee.AUto.Util;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;

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


    public OpenDialog(@NonNull final Context context, final int position, String title, String subtitle) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_layout);
        this.mContext = context;

        title_dia = findViewById(R.id.Title_dia);
        subTitle_dia = findViewById(R.id.subTitle_dia);
        warning_dia = findViewById(R.id.warning_dia);

        inputText = findViewById(R.id.inputText_dia);
        positiveBtn = findViewById(R.id.positiveBtn_dia);
        negativeBtn = findViewById(R.id.negativeBtn_dia);

        title_dia.setText(title);
        subTitle_dia.setText(subtitle);

        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputText.getText().toString().isEmpty()) {
                    warning_dia.setVisibility(View.VISIBLE);
                    warning_dia.setText("잘못된 입력이거나 공백입니다.");
                } else {

                    availableTagCheck(inputText.getText().toString(), position);

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
        mProgressDialog.setMessage("유효한 태그인지 검사하고 있습니다.");
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
                            warning_dia.setText("검색된 결과가 없는 태그입니다.");
                        } else {
                            if (!TagTools.overLapTagCheck(mContext, inputTag)) {
                                Log.d("TAG 처리중", "진입됨");
                                warning_dia.setVisibility(View.GONE);


                                switch (position) {

                                    case 0:
                                        MakePreferences.getInstance().getEditor().putString("tag1", inputTag);
                                        break;
                                    case 1:
                                        MakePreferences.getInstance().getEditor().putString("tag2", inputTag);
                                        break;
                                    case 2:
                                        MakePreferences.getInstance().getEditor().putString("tag3", inputTag);
                                        break;
                                    case 3:
                                        MakePreferences.getInstance().getEditor().putString("tag4", inputTag);
                                        break;
                                    case 4:
                                        MakePreferences.getInstance().getEditor().putString("tag5", inputTag);
                                        break;
                                    case 5:
                                        MakePreferences.getInstance().getEditor().putString("tag6", inputTag);
                                        break;

                                    default:
                                        Log.d("TAG 처리중 오류", "예기치 않은 오류 발생");

                                }
                                MakePreferences.getInstance().getEditor().apply();
                                Toast.makeText(mContext, "새 태그가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                dismiss();

                            } else {
                                warning_dia.setVisibility(View.VISIBLE);
                                warning_dia.setText("태그가 중복됩니다. 다른 태그를 입력하세요.");
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
