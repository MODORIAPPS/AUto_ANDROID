package com.modori.kwonkiseokee.AUto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

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


    //xml widgets
    Button saveBtn;
    ImageView showDirBtn;

    TextView showCurrDir;
    TextView numPickerView;

    //Switch 위에서 아래 순서
    Switch actCheckSwitch;
    Switch bootLaunchSwitch;
    Switch shuffleSwitch;

    NumberPicker inputCycleDay;
    NumberPicker inputCycleHour;
    NumberPicker inputCycleMin;

    int day;
    int hour;
    int min;

    private void initWorks() {
        mainlayout = view.findViewById(R.id.mainlayout);
        saveBtn = view.findViewById(R.id.saveBtn);
        showDirBtn = view.findViewById(R.id.showDirBtn);
        showCurrDir = view.findViewById(R.id.showCurrDir);
        actCheckSwitch = view.findViewById(R.id.actSwitch);
        shuffleSwitch = view.findViewById(R.id.shuffleSwitch);

        numPickerView = view.findViewById(R.id.numPickerView);

        //NumberPickers
        inputCycleDay = view.findViewById(R.id.inputCycleDay);
        inputCycleHour = view.findViewById(R.id.inputCycleHour);
        inputCycleMin = view.findViewById(R.id.inputCycleMin);

    }

    private void timePickerSetup(){
        inputCycleDay.setMinValue(0);
        inputCycleDay.setMaxValue(5);
        inputCycleDay.setWrapSelectorWheel(false);

        inputCycleHour.setMinValue(0);
        inputCycleHour.setMaxValue(23);
        inputCycleHour.setWrapSelectorWheel(false);

        inputCycleMin.setMinValue(1);
        inputCycleMin.setMaxValue(60);
        inputCycleMin.setWrapSelectorWheel(false);

        day = inputCycleDay.getValue();
        hour = inputCycleHour.getValue();
        min = inputCycleMin.getValue();


        // 변화 리스너
        inputCycleDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                day = inputCycleDay.getValue();
                numPickerView.setText(day+" 일 | "+ hour +" 시간 | "+ min + " 분");
            }
        });

        inputCycleHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = inputCycleHour.getValue();
                numPickerView.setText(day+" 일 | "+ hour +" 시간 | "+ min + " 분");
            }
        });

        inputCycleMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = inputCycleMin.getValue();
                numPickerView.setText(day+" 일 | "+ hour +" 시간 | "+ min + " 분");
            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3_frag, container, false);

        initWorks();
        timePickerSetup();

        actCheckSwitch.setText(R.string.tab3_ActStateDisabled);


        Boolean temp;
        String cycleStr;

        // load preferences
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
        SelectedPath = settings.getString("SelectedPath", "/system");
        isShuffleMode = settings.getBoolean("ShuffleMode", false);

        //Activated
        temp = settings.getBoolean("Activated", false);
        actCheckSwitch.setChecked(temp);

        //ShuffleMode
        actCheckSwitch.setChecked(isShuffleMode);

        //Cycle
        cycleStr = settings.getString("Cycle", "5");
        //inputCycle.setText(cycleStr);

        //Location
        showCurrDir.setText(SelectedPath);

        actCheckSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!actCheckSwitch.isChecked()){
                    actCheckSwitch.setText(R.string.tab3_ActStateDisabled);
                }else{
                    actCheckSwitch.setText(R.string.tab3_ActStateActivated);
                }
            }
        });

        shuffleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!shuffleSwitch.isChecked()){
                    shuffleSwitch.setText(R.string.tab3_ShuffleStateOff);
                }else{
                    shuffleSwitch.setText(R.string.tab3_ShuffleStateOn);
                }
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActivate = actCheckSwitch.isChecked();

                if (isActivate) {
                    int cycle;
                    //cycle = Integer.parseInt(inputCycle.getText().toString());

//                    if (cycle == 0)
//                        Snackbar.make(mainlayout, "주기는 0이 될 수 없습니다.", Snackbar.LENGTH_SHORT).show();

                    isShuffleMode = shuffleSwitch.isChecked();
                    SelectedPath = showCurrDir.getText().toString();

                    //설정 쓰기
                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
                    SharedPreferences.Editor editor = settings.edit();

                    editor.putString("SelectedPath", SelectedPath);
                    editor.putBoolean("ShuffleMode", isShuffleMode);
                    editor.apply();

                    //setAutoChangeSlide(cycle);

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

        temp = actCheckSwitch.isChecked();
        editor.putBoolean("Activated", temp);

        temp = shuffleSwitch.isChecked();
        editor.putBoolean("ShuffleMode", temp);

//        tempStr = inputCycle.getText().toString();
//        editor.putString("Cycle", tempStr);

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


}
