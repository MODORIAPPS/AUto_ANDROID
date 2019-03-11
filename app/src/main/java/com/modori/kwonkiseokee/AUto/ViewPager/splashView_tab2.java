package com.modori.kwonkiseokee.AUto.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.modori.kwonkiseokee.AUto.MainActivity;
import com.modori.kwonkiseokee.AUto.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class splashView_tab2 extends Fragment {

    Button goSkipBtn;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.splashview_tab2, container, false);

        goSkipBtn = view.findViewById(R.id.goSkipBtn);
        goSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return view;
    }
}
