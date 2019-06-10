package com.modori.kwonkiseokee.AUto;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.modori.kwonkiseokee.AUto.RA.albumRA;
import com.modori.kwonkiseokee.AUto.data.AlbumDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class tab1_frag extends Fragment {

    private RecyclerView albumRV;
    private Context mContext;
    TextView noImagesWarning2;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_frag, container,false);
        mContext = getActivity();
        Realm.init(mContext);

        albumRV = view.findViewById(R.id.albumRV);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        String ads_app = getResources().getString(R.string.ads_app);
        MobileAds.initialize(mContext, ads_app);
        AdView adView = view.findViewById(R.id.adView_frag1);
        adView.loadAd(adRequest);

        setupPickedRV();


        return view;
    }

    private void setupPickedRV(){
//        Realm realm = Realm.getDefaultInstance();
//        List<String> onlyPhotoUri = new ArrayList<>();
//        RealmResults<DevicePhotoDTO> realmResults = realm.where(DevicePhotoDTO.class).findAll();
//        for (int i = 0; i < realmResults.size(); i++) {
//            onlyPhotoUri.add(realmResults.get(i).getPhotoUri_d());
//        }
        List<AlbumDTO> albumData = new ArrayList<>();
        albumData.add(new AlbumDTO("중국", "아름다운 중국의 배경의 경치", 5,true, false));
        albumData.add(new AlbumDTO("한국", "한국에서만 느낄 수 있는 기분", 10,true, false));
        albumData.add(new AlbumDTO("일본", "일본 도시의 청량함", 5,true, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        albumRV.setLayoutManager(linearLayoutManager);
        albumRV.setAdapter(new albumRA(getActivity(), albumData));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
