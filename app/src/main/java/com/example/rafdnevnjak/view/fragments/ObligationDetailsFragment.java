package com.example.rafdnevnjak.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.Duty;

public class ObligationDetailsFragment extends Fragment {

    private TextView titleTv;
    private Duty duty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_detail_obligation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        duty = ((Duty)args.getSerializable("object"));
        //System.out.println(((Duty)args.getSerializable("object")).getTitle());

        titleTv = view.findViewById(R.id.detail_title);
        titleTv.setText(duty.getTitle());
    }
}


