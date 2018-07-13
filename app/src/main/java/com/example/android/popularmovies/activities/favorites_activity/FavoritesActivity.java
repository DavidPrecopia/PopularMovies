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
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoritesActivity extends AppCompatActivity implements MovieAdapter.ViewHolderClickListener {
	
	private static final String LOG_TAG = FavoritesActivity.class.getSimpleName();
	
	private static final String NO_FAVORITES_MESSAGE = "No favorites selected";
	
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
		viewModel.getFavoriteMovies().observe(this, this::replaceData);
	}
	
	private void replaceData(final List<Movie> favoriteMovies) {
		if (favoriteMovies == null || favoriteMovies.isEmpty()) {
			displayError(NO_FAVORITES_MESSAGE);
			return;
		}
		movieAdapter.replaceData(favoriteMovies);
		hideError();
		hideLoading();
		recyclerView.smoothScrollToPosition(0);
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
		listVisibility(View.INVISIBLE);
		progressBarVisibility(View.VISIBLE);
	}
	
	private void hideLoading() {
		progressBarVisibility(View.INVISIBLE);
		listVisibility(View.VISIBLE);
	}
	
	
	private void displayError(String errorMessage) {
		progressBarVisibility(View.INVISIBLE);
		listVisibility(View.INVISIBLE);
		errorTextView.setVisibility(View.VISIBLE);
		errorTextView.setText(errorMessage);
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
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
	}
	
	private void setUpRecyclerView() {
		recyclerView.setLayoutManager(new GridLayoutManager(this, gridLayoutSpanCount()));
		recyclerView.setHasFixedSize(true);
		movieAdapter = new MovieAdapter(new ArrayList<>(), this);
		recyclerView.setAdapter(movieAdapter);
	}
	
	private int gridLayoutSpanCount() {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			return 2;
		} else {
			return 4;
		}
	}
	
	
	private void listVisibility(int visibility) {
		recyclerView.setVisibility(visibility);
	}
	
	private void progressBarVisibility(int visibility) {
		progressBar.setVisibility(visibility);
	}
}
