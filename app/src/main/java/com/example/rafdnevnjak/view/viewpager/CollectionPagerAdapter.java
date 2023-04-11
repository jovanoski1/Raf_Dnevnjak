package com.example.rafdnevnjak.view.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.view.fragments.ObligationDetailsFragment;

import java.util.List;

public class CollectionPagerAdapter extends FragmentStatePagerAdapter {

    private List<Duty> duties;

    public CollectionPagerAdapter(@NonNull FragmentManager fm, List<Duty> duties) {
        super(fm);
        this.duties = duties;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ObligationDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("object", duties.get(position));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return duties.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + duties.get(position);
    }
}
