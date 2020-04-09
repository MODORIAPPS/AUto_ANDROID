//package com.modori.kwonkiseokee.AUto;
//
//import android.app.AlarmManager;
//import android.app.AlertDialog;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.NumberPicker;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SwitchCompat;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//
//import com.google.android.material.snackbar.Snackbar;
//import com.modori.kwonkiseokee.AUto.Service.SetWallpaperJob;
//import com.modori.kwonkiseokee.AUto.utilities.FileManager;
//import com.modori.kwonkiseokee.AUto.utilities.MakePreferences;
//
//import java.util.Calendar;
//
//import io.realm.Realm;
//
//import static android.content.Context.ALARM_SERVICE;
//
//public class AutoFragment extends Fragment {
//
//    static Context context;
//
//   // private PhotoViewModel viewModel;
//
//    View view;
//    View mainlayout;
//
//    public static final String PREFS_FILE = "PrefsFile";
//
//
//    boolean isShuffleMode;
//    boolean isActivate;
//    SharedPreferences settings;
//
//
//    //xml widgets
//    Toolbar toolbar;
//    ImageView goInfo;
//
//    Button saveBtn;
//    ImageView goGetImageSettings;
//
//    private View actView_layout;
//    private TextView numPickerView;
//    private TextView viewCycle;
//    private TextView viewBootLaunch;
//    private TextView actStats;
//    private TextView showGetCnt;
//    private TextView showGetFromWhat;
//
//    //Switch 위에서 아래 순서
//    private SwitchCompat actCheckSwitch;
//    private SwitchCompat bootLaunchSwitch;
//    private SwitchCompat shuffleSwitch;
//
//    private NumberPicker inputCycleDay;
//    private NumberPicker inputCycleHour;
//    private NumberPicker inputCycleMin;
//
//    int day;
//    int hour;
//    int min;
//
//    String _day;
//    String _hour;
//    String _min;
//
//    public static int _DAY = 0;
//    public static int _HOUR = 0;
//    public static int _MIN = 0;
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        initSet();
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        initSet();
//    }
//
//    private void initWorks() {
//
//        context = getActivity();
//
//        //viewModel = ViewModelProvider(getViewModelStore()).get(PhotoViewModel.class);
//
//        showGetFromWhat = view.findViewById(R.id.showGetFromWhat);
//        goInfo = view.findViewById(R.id.goInfo);
//        mainlayout = view.findViewById(R.id.mainlayout);
//        saveBtn = view.findViewById(R.id.saveBtn);
//        actView_layout = view.findViewById(R.id.actView_layout);
//        goGetImageSettings = view.findViewById(R.id.showDirBtn);
//        //showCurrDir = view.findViewById(R.id.showCurrDir);
//        viewCycle = view.findViewById(R.id.viewCycle);
//        actStats = view.findViewById(R.id.actStats);
//        toolbar = view.findViewById(R.id.toolbar3);
//        actCheckSwitch = view.findViewById(R.id.actSwitch);
//        shuffleSwitch = view.findViewById(R.id.shuffleSwitch);
//
//        numPickerView = view.findViewById(R.id.numPickerView);
//
//
//        //NumberPickers
//        inputCycleDay = view.findViewById(R.id.inputCycleDay);
//        inputCycleHour = view.findViewById(R.id.inputCycleHour);
//        inputCycleMin = view.findViewById(R.id.inputCycleMin);
//        showGetCnt = view.findViewById(R.id.showPictGetCnt);
//
//
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
//        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
//        MakePreferences.getInstance().setSettings(context);
//        MakePreferences.getInstance().setSettings(context);
//
//
//    }
//
//    private void initSet() {
//        _day = getResources().getString(R.string.day);
//        _hour = getResources().getString(R.string.hour);
//        _min = getResources().getString(R.string.min);
//
//
//        settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
//        //int cycle = settings.getInt("Cycle", 5);
//
//        if (settings.getBoolean("BootLaunch", false)) {
//            viewBootLaunch.setText(R.string.tab3_title_BootViewEnabled);
//        } else {
//            viewBootLaunch.setText(R.string.tab3_title_BootViewDisabled);
//        }
//
//        _DAY = settings.getInt("_DAY", 0);
//        _HOUR = settings.getInt("_HOUR", 0);
//        _MIN = settings.getInt("_MIN", 0);
//
//
//        viewCycle.setText(_DAY + _day + _HOUR + _hour + _MIN + _min);
//
//        String str = _DAY + "  " + _day + " : " + _HOUR + "  " + _hour + " : " + _MIN + "  " + _min;
//        //String str = " 23fwe";
//
//        numPickerView.setText(str);
//        String getCntT = getString(R.string.tab3_showPictGetCnt);
//
//
//        switch (settings.getInt("GetSetting", 0)) {
//            case 0:
//                showGetFromWhat.setText(getString(R.string.tab3_showGetFromWhat1));
//                showGetCnt.setText(+FileManager.availableImages(context) + " " + getCntT);
//
//                break;
//            case 1:
////                showGetFromWhat.setText(getString(R.string.tab3_showGetFromWhat2));
////
////                Realm realm = Realm.getDefaultInstance();
////                List<String> onlyPhotoUri = new ArrayList<>();
////                RealmResults<DevicePhotoDTO_OLD> realmResults = realm.where(DevicePhotoDTO_OLD.class).findAll();
////                for (int i = 0; i < realmResults.size(); i++) {
////                    onlyPhotoUri.add(realmResults.get(i).getPhotoUri_d());
////                }
////                showGetCnt.setText(onlyPhotoUri.size() + " " + getCntT);
//
//                break;
//
//            case 2:
//        }
//
//
//        // numPickerView.setText(_DAY + "  " + _day + " " + _HOUR + "  " + _hour + " " + _MIN + "  " + _min);
//
//    }
//
//    private void initSet(int day, int hour, int min) {
//        if (bootLaunchSwitch.isChecked()) {
//            viewBootLaunch.setText(R.string.tab3_title_BootViewEnabled);
//        } else {
//            viewBootLaunch.setText(R.string.tab3_title_BootViewDisabled);
//
//        }
//
//        viewCycle.setText(day + _day + hour + _hour + min + _min);
//
//        String str = day + "  " + _day + " : " + hour + "  " + _hour + " : " + min + "  " + _min;
//        //String str = " 23fwe";
//
//        numPickerView.setText(str);
//
//    }
//
//    private void timePickerSetup() {
//        inputCycleDay.setMinValue(0);
//        inputCycleDay.setMaxValue(5);
//        inputCycleDay.setWrapSelectorWheel(false);
//
//        inputCycleHour.setMinValue(0);
//        inputCycleHour.setMaxValue(23);
//        inputCycleHour.setWrapSelectorWheel(false);
//
//        inputCycleMin.setMinValue(1);
//        inputCycleMin.setMaxValue(59);
//        inputCycleMin.setWrapSelectorWheel(false);
//
//        day = inputCycleDay.getValue();
//        hour = inputCycleHour.getValue();
//        min = inputCycleMin.getValue();
//
//
//        // 변화 리스너
//        inputCycleDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//
//                day = inputCycleDay.getValue();
//                hour = inputCycleHour.getValue();
//                min = inputCycleMin.getValue();
//                numPickerView.setText(day + "  " + _day + " : " + hour + "  " + _hour + " : " + min + "  " + _min);
//
//            }
//        });
//
//        inputCycleHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                day = inputCycleDay.getValue();
//                hour = inputCycleHour.getValue();
//                min = inputCycleMin.getValue();
//                numPickerView.setText(day + "  " + _day + " : " + hour + "  " + _hour + " : " + min + "  " + _min);
//            }
//        });
//
//        inputCycleMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//
//                day = inputCycleDay.getValue();
//                hour = inputCycleHour.getValue();
//                min = inputCycleMin.getValue();
//                numPickerView.setText(day + "  " + _day + " : " + hour + "  " + _hour + " : " + min + "  " + _min);
//            }
//        });
//
//
//    }
//
//    private void actCheckSwichControl() {
//
//        if (actCheckSwitch.isChecked()) {
//
//            actStats.setText(R.string.tab3_ActStateActivated);
//            actView_layout.setBackgroundResource(R.drawable.background_theme_title_act);
//
//        } else {
//
//            actStats.setText(R.string.tab3_ActStateDisabled);
//            actView_layout.setBackgroundResource(R.drawable.background_theme_title_disable);
//        }
//    }
//
//    private void shuffleModeSwitchControl() {
//        if (shuffleSwitch.isChecked() || isShuffleMode) {
//            //shuffleSwitch.setText(R.string.tab3_ShuffleStateOn);
//            shuffleSwitch.setChecked(true);
//        } else {
//            //shuffleSwitch.setText(R.string.tab3_ShuffleStateOff);
//            shuffleSwitch.setChecked(false);
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.tab3_frag, container, false);
//        context = getActivity();
//        Realm.init(context);
//        initWorks();
//        timePickerSetup();
//        initSet();
//
//
//        Boolean temp;
//        String cycleStr;
//
//        // load preferences
//        settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
//        isShuffleMode = settings.getBoolean("ShuffleMode", false);
//
//        //Activated
//        temp = settings.getBoolean("Activated", false);
//        isActivate = settings.getBoolean("Activated", false);
//        actCheckSwitch.setChecked(temp);
//
//        //actCheckSwichControl();
//        shuffleModeSwitchControl();
//
//        if (settings.getBoolean("Activated", false)) {
//            actCheckSwitch.setChecked(true);
//            actStats.setText(R.string.tab3_alertStartService);
//            actView_layout.setBackgroundResource(R.drawable.background_theme_title_act);
//        } else {
//            actStats.setText(R.string.tab3_ActStateDisabled);
//            actCheckSwitch.setChecked(false);
//            actView_layout.setBackgroundResource(R.drawable.background_theme_title_disable);
//
//        }
//
//        if (settings.getBoolean("BootLaunch", false)) {
//            //설정된 경우
//            bootLaunchSwitch.setChecked(true);
//
//        } else {
//            //설정이 안되어있는 경우
//            bootLaunchSwitch.setChecked(false);
//        }
//
//        //ShuffleMode
//        actCheckSwitch.setChecked(isShuffleMode);
//
//        //Cycle
//        cycleStr = settings.getString("Cycle", "5");
//
//
//        //calTimes.minToThreeTypes(Integer.valueOf(cycleStr));
//
//        //Toast.makeText(getActivity(), cycleStr, Toast.LENGTH_SHORT).show();
//
//        inputCycleDay.setValue(_DAY);
//        inputCycleHour.setValue(_HOUR);
//        inputCycleMin.setValue(_MIN);
//
//        //numPickerView.setText(_DAY + "  " + R.string.day + " | " + _HOUR + "  " + R.string.hour + " | " + _MIN + "  " + R.string.min);
//
//
//        //Location
//
//        actCheckSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actCheckSwichControl();
//            }
//        });
//
//        shuffleSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!shuffleSwitch.isChecked()) {
//                    //shuffleSwitch.setText(R.string.tab3_ShuffleStateOff);
//                } else {
//                    //shuffleSwitch.setText(R.string.tab3_ShuffleStateOn);
//                }
//            }
//        });
//
//
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveFunction();
//            }
//        });
//
//        goGetImageSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, SetGetImagesDir_layout.class));
//
//            }
//        });
//
//        goInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goAboutThisApp = new Intent(getActivity(), LicenseActivity.class);
//                startActivity(goAboutThisApp);
//            }
//        });
//
//
//        return view;
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        boolean temp;
//        String tempStr;
//
//        settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
//        SharedPreferences.Editor editor = settings.edit();
//
//        temp = actCheckSwitch.isChecked();
//        editor.putBoolean("Activated", temp);
//
//        temp = shuffleSwitch.isChecked();
//        editor.putBoolean("ShuffleMode", temp);
//
//
//        // Commit the edits!
//        editor.apply();
//
//    }
//
//    private static void setAutoChangeSlide(int _cycle) {
//
//        //http://webs.co.kr/index.php?mid=Android&document_srl=3312655 배경화면이 제때 바뀌지 않는 이유
//
//        Intent intent = new Intent(context, SetWallpaperJob.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//
//        Calendar calendar = Calendar.getInstance();
//
//        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//
//        calendar.add(Calendar.MINUTE, _cycle);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), _cycle * 60000, sender);
//    }
//
//    private void unSetAutoChangeSlide() {
//        Intent intent = new Intent(getActivity(), SetWallpaperJob.class);
//        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
//
//        AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
//        am.cancel(sender);
//
//        actCheckSwitch.setChecked(false);
//        actView_layout.setBackgroundResource(R.drawable.background_theme_title_disable);
//        actStats.setText(R.string.tab3_ActStateDisabled);
//
//    }
//
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        initWorks();
//        initSet();
//
//
//    }
//
//    private void saveFunction() {
//
//
//        isActivate = actCheckSwitch.isChecked();
//
//        if (isActivate) {
//            int cycle;
//
//
//            //cycle = Integer.parseInt(inputCycle.getText().toString());
//            cycle = calTimes.calToMin(day, hour, min);
//
////                    if (cycle == 0)
////                        Snackbar.make(mainlayout, "주기는 0이 될 수 없습니다.", Snackbar.LENGTH_SHORT).show();
//
//            isShuffleMode = shuffleSwitch.isChecked();
//            ///SelectedPath = showCurrDir.getText().toString();
//
//            //설정 쓰기
//            settings = getActivity().getSharedPreferences(PREFS_FILE, 0);
//            SharedPreferences.Editor editor = settings.edit();
//
//            //Toast.makeText(getActivity(), cycle, Toast.LENGTH_SHORT).show();
//            Log.d("save  : ", String.valueOf(cycle));
//            editor.putString("Cycle", String.valueOf(cycle));
//            //editor.putString("SelectedPath", SelectedPath);
//            editor.putBoolean("ShuffleMode", isShuffleMode);
//            editor.putBoolean("BootLaunch", bootLaunchSwitch.isChecked());
//
//            editor.putInt("_DAY", day);
//            editor.putInt("_HOUR", hour);
//            editor.putInt("_MIN", min);
//
//            //initSet();
//            initSet(day, hour, min);
//
//            editor.apply();
//
//            String dialog1Title = getResources().getString(R.string.dialog1Title);
//            String dialog1Con = getResources().getString(R.string.dialog1Con);
//            String dialog2Title = getResources().getString(R.string.dialog2Title);
//            String dialog2Con = getResources().getString(R.string.dialog2Con);
//
//            String dialogOkay = getResources().getString(R.string.dialogOkay);
//
//            int imageN;
//            if (MakePreferences.getInstance().getSettings().getInt("GetSetting", 0) == 0) {
//                imageN = FileManager.availableImages(context);
//
//            } else {
////                Realm.init(context);
////                Realm realm = Realm.getDefaultInstance();
////                List<String> onlyPhotoUri = new ArrayList<>();
////                RealmResults<DevicePhotoDTO_OLD> realmResults = realm.where(DevicePhotoDTO_OLD.class).findAll();
////                for (int i = 0; i < realmResults.size(); i++) {
////                    onlyPhotoUri.add(realmResults.get(i).getPhotoUri_d());
////                }
////
////                imageN = onlyPhotoUri.size();
//
//
//                //return realm.where(DevicePhotoDTO.class).findAll();
//            }
//
//            // imageN == 0
//            if (true) {
//                //이미지가 없는 경우
//                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle(dialog1Title).setMessage(dialog1Con);
//                builder.setPositiveButton(dialogOkay, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.show();
//
//
//                unSetAutoChangeSlide();
//
//
//            } else if (imageN <= 2) {
//                //이미지가 1~2개인 경우
//                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle(dialog2Title).setMessage(dialog2Con);
//                builder.setPositiveButton(dialogOkay, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                builder.show();
//                setAutoChangeSlide(cycle);
//                actStats.setText(R.string.tab3_alertStartService);
//                Snackbar.make(mainlayout, R.string.tab3_snackBar1, Snackbar.LENGTH_SHORT).show();
//
//
//            } else {
//                setAutoChangeSlide(cycle);
//                actStats.setText(R.string.tab3_alertStartService);
//                Snackbar.make(mainlayout, R.string.tab3_snackBar1, Snackbar.LENGTH_SHORT).show();
//            }
//            //save 활동 끝
//
//        } else {
//
//            unSetAutoChangeSlide();
//            Snackbar.make(mainlayout, R.string.tab3_snackBar2, Snackbar.LENGTH_SHORT).show();
//
//        }
//    }
//
//}
