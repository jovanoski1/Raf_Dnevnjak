package com.example.rafdnevnjak.view.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rafdnevnjak.R;
import com.example.rafdnevnjak.model.Duty;
import com.example.rafdnevnjak.view.viewpager.CollectionPagerAdapter;

import java.util.Collections;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private List<Duty> duties;

    private ViewPager viewPager;
    private CollectionPagerAdapter collectionPagerAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        duties = (List<Duty>) getIntent().getSerializableExtra("obligations");
        int currInd = getIntent().getExtras().getInt("currentItem");

        collectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager(), duties );
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(collectionPagerAdapter);
        viewPager.setCurrentItem(currInd);
    }
}