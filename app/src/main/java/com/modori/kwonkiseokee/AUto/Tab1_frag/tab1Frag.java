package com.modori.kwonkiseokee.AUto.Tab1_frag;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.modori.kwonkiseokee.AUto.R;
import com.modori.kwonkiseokee.AUto.RA.GetFromGalleryRA;
import com.modori.kwonkiseokee.AUto.Util.ColumnQty;
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class tab1Frag extends Fragment {

    private RecyclerView pickedRV;
    private RecyclerView albumRV;
    private Context mContext;
    TextView noImagesWarning2;

    String[] albumKorea = {"e5QfGsrdgK4","HjsWTyyVDgg","V_2UbSfVigw","xPMiKjHE784"};

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
        //AdView adView2 = view.findViewById(R.id.adView2_frag1);

        // 잠시 막아놓음
        //adView.loadAd(adRequest);

        pickedRV = view.findViewById(R.id.pickedRV);
        noImagesWarning2 = view.findViewById(R.id.noPickedImagesWarning);
        setupPickedRV();

        setUpAlbumRV();



        return view;
    }


    private void setupPickedRV(){
        //int mNoOfColumns = ColumnQty.calculateNoOfColumns(getApplicationContext(), 120);

        Realm realm = Realm.getDefaultInstance();
        List<String> onlyPhotoUri = new ArrayList<>();
        RealmResults<DevicePhotoDTO> realmResults = realm.where(DevicePhotoDTO.class).findAll();
        for (int i = 0; i < realmResults.size(); i++) {
            onlyPhotoUri.add(realmResults.get(i).getPhotoUri_d());
        }

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        //linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        int mNoOfColumns = ColumnQty.calculateNoOfColumns(getApplicationContext(),130);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mNoOfColumns);
        pickedRV.setLayoutManager(gridLayoutManager);

        if(onlyPhotoUri.isEmpty()){
            noImagesWarning2.setVisibility(View.VISIBLE);
        }

        if (onlyPhotoUri.size() != 0) {
            noImagesWarning2.setVisibility(View.GONE);
            pickedRV.setLayoutManager(new GridLayoutManager(mContext, mNoOfColumns));
            GetFromGalleryRA adapter = new GetFromGalleryRA(mContext, onlyPhotoUri, true);
            pickedRV.setAdapter(adapter);
        }
    }



    private void setUpAlbumRV(){

        List<AlbumDTO> albumData = new ArrayList<>();
        albumData.add(new AlbumDTO("오사카","오사카에서 느끼는 일본의 청량함","/",5, true,true));
        albumData.add(new AlbumDTO("오사카","오사카에서 느끼는 일본의 청량함","/",5, true,true));
        albumData.add(new AlbumDTO("오사카","오사카에서 느끼는 일본의 청량함","/",5, true,true));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        albumRV.setLayoutManager(linearLayoutManager);
        albumRV.setAdapter(new Tab1AlbumAdapter(getActivity(), albumData));

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
