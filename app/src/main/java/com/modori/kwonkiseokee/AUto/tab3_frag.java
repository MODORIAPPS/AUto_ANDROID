package com.modori.kwonkiseokee.AUto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.ALARM_SERVICE;

public class tab3_frag extends Fragment {

    View view;
    View mainlayout;

    public static final int SELECT_FOLDER = 6541;
    public static final String PREFS_FILE = "PrefsFile";


    String SelectedPath;
    boolean isShuffleMode;
    boolean isActivate;


    //xml 항목
    Button saveBtn;
    Button showDirBtn;

    TextView showCurrDir;

    CheckBox actCheckBox;
    CheckBox shuffleCheckBox;

    EditText inputCycle;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3_frag, container, false);

        initWorks();

        Boolean temp;
        String cycleStr;

        // load preferences
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
        SelectedPath = settings.getString("SelectedPath", "/system");
        isShuffleMode = settings.getBoolean("ShuffleMode", false);

        //Activated
        temp = settings.getBoolean("Activated", false);
        actCheckBox.setChecked(temp);

        //ShuffleMode
        actCheckBox.setChecked(isShuffleMode);

        //Cycle
        cycleStr = settings.getString("Cycle", "5");
        inputCycle.setText(cycleStr);

        //Location
        showCurrDir.setText(SelectedPath);

        actCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!actCheckBox.isChecked()){
                    actCheckBox.setText(R.string.tab3_ActStateDisabled);
                }else{
                    actCheckBox.setText(R.string.tab3_ActStateActivated);
                }
            }
        });

        shuffleCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!shuffleCheckBox.isChecked()){
                    shuffleCheckBox.setText(R.string.tab3_ShuffleStateOff);
                }else{
                    shuffleCheckBox.setText(R.string.tab3_ShuffleStateOn);
                }
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActivate = actCheckBox.isChecked();

                if (isActivate) {
                    int cycle = Integer.parseInt(inputCycle.getText().toString());

                    if (cycle == 0)
                        Snackbar.make(mainlayout, "주기는 0이 될 수 없습니다.", Snackbar.LENGTH_SHORT).show();

                    isShuffleMode = shuffleCheckBox.isChecked();
                    SelectedPath = showCurrDir.getText().toString();

                    //설정 쓰기
                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
                    SharedPreferences.Editor editor = settings.edit();

                    editor.putString("SelectedPath", SelectedPath);
                    editor.putBoolean("ShuffleMode", isShuffleMode);
                    editor.apply();

                    setAutoChangeSlide(cycle);

                    //save 활동 끝
                    Snackbar.make(mainlayout, "변경사항이 저장되었습니다.", Snackbar.LENGTH_SHORT).show();
                } else {
                    unSetAutoChangeSlide();
                    Snackbar.make(mainlayout, "변경사항이 저장되었습니다.", Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        showDirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), SelectFolder.class);
                    startActivityForResult(intent, SELECT_FOLDER);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case SELECT_FOLDER:
                    if (resultCode != getActivity().RESULT_CANCELED) {
                        SelectedPath = data.getStringExtra("SelectedPath");
                        showCurrDir.setText(SelectedPath);
                    }

            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onStop() {
        super.onStop();

        boolean temp;
        String tempStr;

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();

        temp = actCheckBox.isChecked();
        editor.putBoolean("Activated", temp);

        temp = shuffleCheckBox.isChecked();
        editor.putBoolean("ShuffleMode", temp);

        tempStr = inputCycle.getText().toString();
        editor.putString("Cycle", tempStr);

        SelectedPath = showCurrDir.getText().toString();
        editor.putString("SelectedPath", SelectedPath);

        // Commit the edits!
        editor.apply();

    }

    private void setAutoChangeSlide(int _cycle) {
        Intent intent = new Intent(getActivity(), SetWallpaperJob.class);
        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

        Calendar calendar = Calendar.getInstance();

        AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        calendar.add(Calendar.MINUTE, _cycle);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), _cycle * 60000, sender);
    }

    private void unSetAutoChangeSlide() {
        Intent intent = new Intent(getActivity(), SetWallpaperJob.class);
        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

        AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    private void initWorks() {
        mainlayout = view.findViewById(R.id.mainlayout);
        saveBtn = view.findViewById(R.id.saveBtn);
        showDirBtn = view.findViewById(R.id.showDirBtn);
        showCurrDir = view.findViewById(R.id.showCurrDir);
        actCheckBox = view.findViewById(R.id.actCheckBox);
        shuffleCheckBox = view.findViewById(R.id.shuffleCheckBox);
        inputCycle = view.findViewById(R.id.inputCycle);

    }
}
