package com.mposyandu.mposyandu.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mposyandu.mposyandu.activity.AnggotaActivity;
import com.mposyandu.mposyandu.data.UserModel;
import com.mposyandu.mposyandu.R;

public class HomeAnggota extends Fragment {
    private UserModel user;
    private FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_anggota, container, false);
        final Bundle bundle = getArguments();
        user = bundle.getParcelable("user");
        LinearLayout profile = view.findViewById(R.id.anggota_profile);
        LinearLayout balita = view.findViewById(R.id.anggota_balita);
        LinearLayout ibu = view.findViewById(R.id.anggota_ibu);
        LinearLayout jadwal = view.findViewById(R.id.anggota_jadwal);
        TextView welcome = view.findViewById(R.id.welcomeback2);
        welcome.setText(user.getNama());

        fab  = getActivity().findViewById(R.id.fab);
        fab.hide();
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnggotaActivity)getActivity()).findId(0);
            }
        });

        balita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnggotaActivity)getActivity()).findId(1);
            }
        });
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnggotaActivity)getActivity()).findId(3);
            }
        });


        ibu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnggotaActivity)getActivity()).findId(2);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fab.show();
    }
}
