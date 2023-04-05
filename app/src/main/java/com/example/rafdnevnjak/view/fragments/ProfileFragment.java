package com.example.rafdnevnjak.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.view.activites.LoginActivity;
import com.example.rafdnevnjak.view.activites.MainActivity;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;
    private Button logoutBtn;

    public ProfileFragment(){
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        initListeners();
    }
    private void init(View view){
        emailTextView = view.findViewById(R.id.editTextProfileEmail);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        emailTextView.setText(sharedPreferences.getString("email","email@email.com"));
    }
    private void initListeners(){
        logoutBtn.setOnClickListener(e->{
            SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}
