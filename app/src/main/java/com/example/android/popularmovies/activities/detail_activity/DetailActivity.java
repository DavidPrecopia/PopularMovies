package com.example.android.popularmovies.activities.detail_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
	
	private DetailViewModel viewModel;
	private ActivityDetailBinding binding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		init();
	}
	
	public void init() {
		setUpViewModel();
		setUpToolbar();
	}
	
	private void setUpViewModel() {
		ViewModelFactory factory = new ViewModelFactory(getApplication(), getMovieId());
		viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
		observeMovie();
	}
	
	private int getMovieId() {
		return getIntent().getIntExtra(getClass().getSimpleName(), 0);
	}
	
	private void observeMovie() {
		Log.i("DetailActivity", "observeMovie()");
		viewModel.getMovieDetails().observe(this, movieDetails -> binding.setMovie(movieDetails));
	}
	
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbarDetailActivity);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
	}
}