package com.example.android.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.R;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {
	
	FloatingActionMenu fam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		fam = findViewById(R.id.fab_base);
		fam.setIconAnimated(false);
		fam.setClosedOnTouchOutside(true);
	}
}
