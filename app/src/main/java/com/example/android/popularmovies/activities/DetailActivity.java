package com.example.android.popularmovies.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
	
		setSupportActionBar(binding.toolbarDetailActivity);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
		
		// Title of movie
		binding.collapsingToolbarLayout.setTitle("Intersteller");
		
		// Uses backdrop or poster
		Picasso.get().load("http://image.tmdb.org/t/p/w780/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(binding.ivPosterDetailActivity);
	}
}
