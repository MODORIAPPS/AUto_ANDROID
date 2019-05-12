package com.modori.kwonkiseokee.AUto.Util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.tab2_frag;

public class OpenDialog extends Dialog {

    EditText inputText;
    Button positiveBtn, negativeBtn;
    TextView title_dia, subTitle_dia, warning_dia;
    Context mContext;

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
                    // 값 저장
                    //Toast.makeText(context,"진입", Toast.LENGTH_SHORT).show();
                    MakePreferences.getInstance().setSettings(mContext);

                    String inputTag = inputText.getText().toString();
                    if (!Frag2.overLapTagCheck(context, inputTag)) {
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
                        dismiss();

                    } else {
                        warning_dia.setVisibility(View.VISIBLE);
                        warning_dia.setText("태그가 중복됩니다. 다른 태그를 입력하세요.");
                    }

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


}
