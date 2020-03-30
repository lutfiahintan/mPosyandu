package com.mposyandu.mposyandu.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mposyandu.mposyandu.Controller;
import com.mposyandu.mposyandu.data.BalitaModel;
import com.mposyandu.mposyandu.data.UserModel;
import com.mposyandu.mposyandu.R;
import com.mposyandu.mposyandu.tools.AlertBuilder;
import com.mposyandu.mposyandu.tools.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeIbu extends Fragment {
    private UserModel user;
    private BalitaModel balita;
    private FloatingActionButton fab;
    private Integer usi;
    private TextView nama, ibu, ayah, posyandu, usia, alamat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_ibu, container, false);
        final Bundle bundle =getArguments();
        user = bundle.getParcelable("user");
        balita = bundle.getParcelable("balita");
        fab = getActivity().findViewById(R.id.fab);
        LinearLayout kesehatan = view.findViewById(R.id.ibu_kesehatan);
        LinearLayout imunisasi = view.findViewById(R.id.ibu_imunisasi);
        LinearLayout vitamin = view.findViewById(R.id.ibu_vitamin);
        LinearLayout grafik = view.findViewById(R.id.ibu_grafik);
        LinearLayout jadwal = view.findViewById(R.id.ibu_jadwal);
        nama = view.findViewById(R.id.balita_ibu_nama);
        ibu = view.findViewById(R.id.balita_ibu_namaibu);
        ayah = view.findViewById(R.id.balita_ibu_namaayah);
        posyandu = view.findViewById(R.id.balita_ibu_posyandu);
        usia = view.findViewById(R.id.balita_ibu_usia);
        alamat = view.findViewById(R.id.balita_ibu_alamat);
        request();
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new Reminder();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_ibu, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        kesehatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new ListPertumbuhan();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_ibu, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        imunisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new ListImunisasi();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_ibu, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        vitamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new ListVitamin();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_ibu, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        grafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new GrafikBerat();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_ibu, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    public void request() {
        Map<String, Integer> params = new HashMap<>();
        params.put("balita_id", balita.getId());
        String url = Database.getUrl()+"/user/"+user.getId()+"/posyandu/"+user.getPosyandu_id()+"/baby/"+
                balita.getId()+"/condition/fetch";
        JsonObjectRequest RequestKondisi = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer error = response.getInt("msg");
                    switch (error) {
                        case 0:
                            JSONArray obj_arr = response.getJSONArray("data");
                            usi = obj_arr.length();
                            setText();

                            break;
                        case 1 :
                            AlertBuilder builder = new AlertBuilder("Terdapat Error Saat Mengambil Data", "Error", getActivity());
                            builder.show();
                            break;
                    }
                }
                catch (JSONException e) {
                    e.getStackTrace();
                    AlertBuilder builder = new AlertBuilder(e.getMessage(), "Error", getActivity());
                    builder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
                AlertBuilder builder = new AlertBuilder("Error Pada Koneksi", "Error", getActivity());
                builder.show();
            }
        });
        Controller.getmInstance().addToRequestQueue(RequestKondisi);
    }

    public void setText() {
        nama.setText(balita.getNama());
        ibu.setText(balita.getIbu());
        ayah.setText(balita.getAyah());
        posyandu.setText(balita.getPosyandu());
        usia.setText(String.valueOf(usi)+" Bulan");
        alamat.setText(user.getAlamat());
    }
}
