package com.example.android.popularmovies.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;
import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
	
	private ActivityDetailBinding binding;
	private Movie movie;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		
		// TODO Bind the other Views
		// via Presenter??
		setToolbar();
		
		bindTitle();
		bindImage();
	}
	
	private void setToolbar() {
		setSupportActionBar(binding.toolbarDetailActivity);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
	}
	
	private void bindTitle() {
		binding.collapsingToolbarLayout.setTitle(movie.getTitle());
	}
	
	private void bindImage() {
		String imageUrl = TextUtils.isEmpty(movie.getBackdropUrl()) ? movie.getPosterUrl() : movie.getBackdropUrl();
		Picasso.get().load(imageUrl).into(binding.ivPosterDetailActivity);
	}
}