package com.modori.kwonkiseokee.AUto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.modori.kwonkiseokee.AUto.CalTools.calTimes;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.ALARM_SERVICE;

public class tab3_frag extends Fragment {

    static Context context;

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

    View actView_layout;
    TextView showCurrDir;
    TextView numPickerView;
    TextView viewCycle;
    TextView viewBootLaunch;

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

    public static int _DAY = 0;
    public static int _HOUR = 0;
    public static int _MIN = 0;

    private void initWorks() {
        context = getActivity();
        mainlayout = view.findViewById(R.id.mainlayout);
        saveBtn = view.findViewById(R.id.saveBtn);
        actView_layout = view.findViewById(R.id.actView_layout);
        showDirBtn = view.findViewById(R.id.showDirBtn);
        showCurrDir = view.findViewById(R.id.showCurrDir);
        viewCycle = view.findViewById(R.id.viewCycle);
        viewBootLaunch = view.findViewById(R.id.viewBootLaunch);

        bootLaunchSwitch = view.findViewById(R.id.bootLaunchSwitch);
        actCheckSwitch = view.findViewById(R.id.actSwitch);
        shuffleSwitch = view.findViewById(R.id.shuffleSwitch);

        numPickerView = view.findViewById(R.id.numPickerView);

        //NumberPickers
        inputCycleDay = view.findViewById(R.id.inputCycleDay);
        inputCycleHour = view.findViewById(R.id.inputCycleHour);
        inputCycleMin = view.findViewById(R.id.inputCycleMin);

    }

    private void initSet() {
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
        //int cycle = settings.getInt("Cycle", 5);

        if (settings.getBoolean("BootLaunch", false)) {
            viewBootLaunch.setText("부팅후 자동실행함");
        } else {
            viewBootLaunch.setText("부팅후 자동실행 안함");
        }

        _DAY = settings.getInt("_DAY", 0);
        _HOUR = settings.getInt("_HOUR", 0);
        _MIN = settings.getInt("_MIN", 0);

        viewCycle.setText(  _DAY+" 일 | " +_HOUR + " 시간 | " + _MIN + " 분");

//        if(cycle < 60){
//            viewCycle.setText(cycle + " 분");
//        }else if(cycle >= 60){
//
//        }


        numPickerView.setText(_DAY + " 일 | " + _HOUR + " 시간 | " + _MIN + " 분");

    }

    private void timePickerSetup() {
        inputCycleDay.setMinValue(0);
        inputCycleDay.setMaxValue(5);
        inputCycleDay.setWrapSelectorWheel(false);

        inputCycleHour.setMinValue(0);
        inputCycleHour.setMaxValue(23);
        inputCycleHour.setWrapSelectorWheel(false);

        inputCycleMin.setMinValue(1);
        inputCycleMin.setMaxValue(59);
        inputCycleMin.setWrapSelectorWheel(false);

        day = inputCycleDay.getValue();
        hour = inputCycleHour.getValue();
        min = inputCycleMin.getValue();


        // 변화 리스너
        inputCycleDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                day = inputCycleDay.getValue();
                numPickerView.setText(day + " 일 | " + hour + " 시간 | " + min + " 분");
            }
        });

        inputCycleHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = inputCycleHour.getValue();
                numPickerView.setText(day + " 일 | " + hour + " 시간 | " + min + " 분");
            }
        });

        inputCycleMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = inputCycleMin.getValue();
                numPickerView.setText(day + " 일 | " + hour + " 시간 | " + min + " 분");
            }
        });


    }

    private void actCheckSwichControl() {

        if (actCheckSwitch.isChecked()) {

            actCheckSwitch.setText(R.string.tab3_ActStateActivated);
            actView_layout.setBackgroundResource(R.drawable.background_theme_title_act);

        } else {

            actCheckSwitch.setText(R.string.tab3_ActStateDisabled);
            actView_layout.setBackgroundResource(R.drawable.background_theme_title_disable);
        }
    }

    private void shuffleModeSwitchControl() {
        if (shuffleSwitch.isChecked() || isShuffleMode) {
            shuffleSwitch.setText(R.string.tab3_ShuffleStateOn);
            shuffleSwitch.setChecked(true);
        } else {
            shuffleSwitch.setText(R.string.tab3_ShuffleStateOff);
            shuffleSwitch.setChecked(false);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3_frag, container, false);

        initWorks();
        timePickerSetup();
        initSet();

        Boolean temp;
        String cycleStr;

        // load preferences
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
        SelectedPath = settings.getString("SelectedPath", "/system");
        isShuffleMode = settings.getBoolean("ShuffleMode", false);

        //Activated
        temp = settings.getBoolean("Activated", false);
        isActivate = settings.getBoolean("Activated", false);
        actCheckSwitch.setChecked(temp);

        //actCheckSwichControl();
        shuffleModeSwitchControl();

        if (settings.getBoolean("Activated", false)) {
            actCheckSwitch.setChecked(true);
            actCheckSwitch.setText(R.string.tab3_ActStateActivated);
            actView_layout.setBackgroundResource(R.drawable.background_theme_title_act);
        } else {
            actCheckSwitch.setText(R.string.tab3_ActStateDisabled);
            actCheckSwitch.setChecked(false);
            actView_layout.setBackgroundResource(R.drawable.background_theme_title_disable);

        }

        if (settings.getBoolean("BootLaunch", false)) {
            //설정된 경우
            bootLaunchSwitch.setChecked(true);

        } else {
            //설정이 안되어있는 경우
            bootLaunchSwitch.setChecked(false);
        }

        //ShuffleMode
        actCheckSwitch.setChecked(isShuffleMode);

        //Cycle
        cycleStr = settings.getString("Cycle", "5");


        calTimes.minToThreeTypes(Integer.valueOf(cycleStr));

        Toast.makeText(getActivity(), cycleStr, Toast.LENGTH_SHORT).show();

        inputCycleDay.setValue(_DAY);
        inputCycleHour.setValue(_HOUR);
        inputCycleMin.setValue(_MIN);

        numPickerView.setText(_DAY + " 일 | " + _HOUR + " 시간 | " + _MIN + " 분");


        //Location
        showCurrDir.setText(SelectedPath);

        actCheckSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actCheckSwichControl();
            }
        });

        shuffleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shuffleSwitch.isChecked()) {
                    shuffleSwitch.setText(R.string.tab3_ShuffleStateOff);
                } else {
                    shuffleSwitch.setText(R.string.tab3_ShuffleStateOn);
                }
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFunction();
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

    public static void setAutoChangeSlide(int _cycle) {
        Intent intent = new Intent(context, SetWallpaperJob.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        calendar.add(Calendar.MINUTE, _cycle);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), _cycle * 60000, sender);
    }

    private void unSetAutoChangeSlide() {
        Intent intent = new Intent(getActivity(), SetWallpaperJob.class);
        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

        AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

    public void saveFunction() {
        isActivate = actCheckSwitch.isChecked();

        if (isActivate) {
            int cycle;


            //cycle = Integer.parseInt(inputCycle.getText().toString());
            cycle = calTimes.calToMin(day, hour, min);

//                    if (cycle == 0)
//                        Snackbar.make(mainlayout, "주기는 0이 될 수 없습니다.", Snackbar.LENGTH_SHORT).show();

            isShuffleMode = shuffleSwitch.isChecked();
            SelectedPath = showCurrDir.getText().toString();

            //설정 쓰기
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
            SharedPreferences.Editor editor = settings.edit();

            //Toast.makeText(getActivity(), cycle, Toast.LENGTH_SHORT).show();
            Log.d("save  : ", String.valueOf(cycle));
            editor.putString("Cycle", String.valueOf(cycle));
            editor.putString("SelectedPath", SelectedPath);
            editor.putBoolean("ShuffleMode", isShuffleMode);
            editor.putBoolean("BootLaunch", bootLaunchSwitch.isChecked());

            editor.putInt("_DAY", day);
            editor.putInt("_HOUR", hour);
            editor.putInt("_MIN", min);

            initSet();

            editor.apply();


            //save 활동 끝
            setAutoChangeSlide(cycle);
            Snackbar.make(mainlayout, "변경사항이 저장되었습니다. 서비스를 시작합니다.", Snackbar.LENGTH_SHORT).show();
        } else {
            unSetAutoChangeSlide();
            Snackbar.make(mainlayout, "변경사항이 저장되었습니다.", Snackbar.LENGTH_SHORT).show();

        }
    }

}
