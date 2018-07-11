package com.example.android.popularmovies.activities.favorites_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.MovieAdapter;
import com.example.android.popularmovies.activities.detail_activity.DetailActivity;
import com.example.android.popularmovies.databinding.ActivityFavoritesBinding;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity implements MovieAdapter.ViewHolderClickListener {
	
	private ActivityFavoritesBinding binding;
	private FavoriteViewModel viewModel;
	
	private RecyclerView recyclerView;
	private MovieAdapter movieAdapter;
	
	private ProgressBar progressBar;
	private TextView errorTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_favorites);
		
		init();
	}
	
	private void init() {
		setUpView();
		displayLoading();
		setUpViewModel();
	}
	
	
	private void setUpViewModel() {
		FavoritesViewModelFactory factory = new FavoritesViewModelFactory(getApplication());
		viewModel = ViewModelProviders.of(this, factory).get(FavoriteViewModel.class);
		observeMovies();
		observeErrorMessage();
	}
	
	
	private void observeMovies() {
		viewModel.getFavoriteMovies().observe(this, favoriteMovies -> {
			movieAdapter.replaceData(favoriteMovies);
			hideError();
			hideLoading();
			recyclerView.smoothScrollToPosition(0);
		});
	}
	
	private void observeErrorMessage() {
		viewModel.getErrorMessage().observe(this, this::displayError);
	}
	
	
	@Override
	public void onViewHolderClick(int movieId) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(DetailActivity.class.getSimpleName(), movieId);
		startActivity(intent);
	}
	
	
	private void displayLoading() {
		hideError();
		recyclerView.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
	}
	
	private void hideLoading() {
		progressBar.setVisibility(View.INVISIBLE);
		recyclerView.setVisibility(View.VISIBLE);
	}
	
	
	private void displayError(String errorMessage) {
		recyclerView.setVisibility(View.INVISIBLE);
		errorTextView.setText(errorMessage);
		errorTextView.setVisibility(View.VISIBLE);
	}
	
	private void hideError() {
		errorTextView.setVisibility(View.GONE);
	}
	
	
	private void setUpView() {
		setViewReferences();
		setUpToolbar();
		setUpRecyclerView();
	}
	
	private void setViewReferences() {
		recyclerView = binding.recyclerView;
		progressBar = binding.progressBar;
		errorTextView = binding.tvError;
	}
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbar);
	}
	
	private void setUpRecyclerView() {
		recyclerView.setLayoutManager(new GridLayoutManager(this, gridLayoutSpanCount()));
		recyclerView.setHasFixedSize(true);
		movieAdapter = new MovieAdapter(new ArrayList<>(), this);
		recyclerView.setAdapter(movieAdapter);
	}
	
	private int gridLayoutSpanCount() {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			return 4;
		} else {
			return 2;
		}
	}
}
