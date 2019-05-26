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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modori.kwonkiseokee.AUto.RA.GetFromGalleryRA;
import com.modori.kwonkiseokee.AUto.data.DevicePhotoDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class tab1_frag extends Fragment {

    RecyclerView pickedRV;
    Context mContext;
    TextView noImagesWarning2;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_frag, container,false);
        mContext = getActivity();
        Realm.init(mContext);

        pickedRV = view.findViewById(R.id.pickedRV);
        noImagesWarning2 = view.findViewById(R.id.noPickedImagesWarning);
        setupPickedRV();


        return view;
    }

    private void setupPickedRV(){
        Realm realm = Realm.getDefaultInstance();
        List<String> onlyPhotoUri = new ArrayList<>();
        RealmResults<DevicePhotoDTO> realmResults = realm.where(DevicePhotoDTO.class).findAll();
        for (int i = 0; i < realmResults.size(); i++) {
            onlyPhotoUri.add(realmResults.get(i).getPhotoUri_d());
        }

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        //linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        pickedRV.setLayoutManager(gridLayoutManager);

        if(onlyPhotoUri.isEmpty()){
            noImagesWarning2.setVisibility(View.VISIBLE);
        }

        if (onlyPhotoUri.size() != 0) {
            noImagesWarning2.setVisibility(View.GONE);
            pickedRV.setLayoutManager(new GridLayoutManager(mContext, 3));
            GetFromGalleryRA adapter = new GetFromGalleryRA(mContext, onlyPhotoUri, true);
            pickedRV.setAdapter(adapter);
        }
    }
}
