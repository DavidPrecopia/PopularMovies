package com.example.android.popularmovies.activities.detail_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
	
	private DetailViewModel viewModel;
	private ActivityDetailBinding binding;
	
	private ProgressBar progressBar;
	private TextView tvError;
	private AppBarLayout title;
	private NestedScrollView details;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		init();
	}
	
	private void init() {
		setViewReferences();
		displayLoading();
		setUpToolbar();
		setUpViewModel();
	}
	
	private void setViewReferences() {
		progressBar = binding.progressBar;
		tvError = binding.tvError;
		title = binding.appBarLayout;
		details = binding.details;
	}
	
	private void setUpViewModel() {
		ViewModelFactory factory = new ViewModelFactory(getApplication(), getMovieId());
		viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
		observeMovie();
		observeErrorMessage();
	}
	
	private int getMovieId() {
		return getIntent().getIntExtra(getClass().getSimpleName(), 0);
	}
	
	private void observeMovie() {
		viewModel.getMovieDetails().observe(this, movieDetails -> {
			binding.setMovie(movieDetails);
			hideError();
			stopLoading();
		});
	}
	
	private void observeErrorMessage() {
		viewModel.getErrorMessage().observe(this, this::displayError);
	}
	
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbarDetailActivity);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
	}
	
	
	private void displayLoading() {
		progressBar.setVisibility(View.VISIBLE);
		title.setVisibility(View.INVISIBLE);
		details.setVisibility(View.INVISIBLE);
	}
	
	private void stopLoading() {
		progressBar.setVisibility(View.INVISIBLE);
		title.setVisibility(View.VISIBLE);
		details.setVisibility(View.VISIBLE);
	}
	
	
	private void displayError(String errorMessage) {
		tvError.setText(errorMessage);
		tvError.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
		title.setVisibility(View.INVISIBLE);
		details.setVisibility(View.INVISIBLE);
	}
	
	private void hideError() {
		tvError.setVisibility(View.GONE);
	}
}