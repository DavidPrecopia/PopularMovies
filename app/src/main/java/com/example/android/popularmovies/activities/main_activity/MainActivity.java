package com.example.android.popularmovies.activities.main_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.MovieAdapter;
import com.example.android.popularmovies.activities.detail_activity.DetailActivity;
import com.example.android.popularmovies.activities.favorites_activity.FavoritesActivity;
import com.example.android.popularmovies.databinding.ActivityMainBinding;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
		implements SwipeRefreshLayout.OnRefreshListener, MovieAdapter.ViewHolderClickListener {
	
	private MainViewModel viewModel;
	private ActivityMainBinding binding;
	
	private RecyclerView recyclerView;
	private MovieAdapter movieAdapter;
	
	private SwipeRefreshLayout swipeRefreshLayout;
	private FloatingActionMenu floatingActionMenu;
	private ProgressBar progressBar;
	private TextView tvError;
	
	private MenuItem refreshMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		init();
	}
	
	
	private void init() {
		setUpView();
		displayLoading();
		setActionBarTitle(getPopularTitle());
		setUpViewModel();
	}
	
	
	private void setUpViewModel() {
		MainViewModelFactory factory = new MainViewModelFactory(getApplication());
		viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
		observeMovies();
		observeError();
	}
	
	private void observeMovies() {
		viewModel.getMovies().observe(this, movies -> {
			movieAdapter.replaceData(movies);
			hideError();
			hideLoading();
			recyclerView.smoothScrollToPosition(0);
		});
	}
	
	private void observeError() {
		viewModel.getErrorMessage().observe(this, this::displayError);
	}
	
	
	@Override
	public void onViewHolderClick(int movieId) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(DetailActivity.class.getSimpleName(), movieId);
		startActivity(intent);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_refresh:
				onRefresh();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	@Override
	public void onRefresh() {
		displayLoading();
		swipeRefreshLayout.setRefreshing(false);
		viewModel.onRefresh();
	}
	
	
	private void setActionBarTitle(String title) {
		Objects.requireNonNull(getSupportActionBar()).setTitle(title);
	}
	
	private String getPopularTitle() {
		return getString(R.string.fab_label_popular);
	}
	
	private String getHighestRatedTitle() {
		return getString(R.string.fab_label_highest_rated);
	}
	
	
	private void displayLoading() {
		hideError();
		actionOverflowRefresh(false);
		everythingVisibility(View.INVISIBLE);
		progressBarVisibility(View.VISIBLE);
	}
	
	private void hideLoading() {
		progressBarVisibility(View.INVISIBLE);
		everythingVisibility(View.VISIBLE);
		actionOverflowRefresh(true);
	}
	
	
	private void displayError(String message) {
		progressBar.setVisibility(View.INVISIBLE);
		everythingVisibility(View.INVISIBLE);
		actionOverflowRefresh(true);
		tvError.setText(message);
		tvError.setVisibility(View.VISIBLE);
	}
	
	private void hideError() {
		tvError.setVisibility(View.GONE);
	}
	
	
	private void actionOverflowRefresh(boolean enabled) {
		if (refreshMenuItem != null) {
			refreshMenuItem.setVisible(enabled);
		}
	}
	
	
	private void setUpView() {
		setViewReferences();
		setUpToolbar();
		setUpRecyclerView();
		setUpFloatingActionMenu();
		swipeRefreshLayout.setOnRefreshListener(this);
	}
	
	private void setViewReferences() {
		recyclerView = binding.recyclerView;
		swipeRefreshLayout = binding.swipeRefresh;
		floatingActionMenu = binding.fabBase;
		progressBar = binding.progressBar;
		tvError = binding.tvError;
	}
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbar);
	}
	
	private void setUpRecyclerView() {
		recyclerView.setLayoutManager(new GridLayoutManager(this, gridLayoutSpanCount()));
		recyclerView.setHasFixedSize(true);
		this.movieAdapter = new MovieAdapter(new ArrayList<>(), this);
		recyclerView.setAdapter(movieAdapter);
	}
	
	private int gridLayoutSpanCount() {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			return 2;
		} else {
			return 4;
		}
	}
	
	private void setUpFloatingActionMenu() {
		floatingActionMenu.setIconAnimated(false);
		floatingActionMenu.setClosedOnTouchOutside(true);
		binding.fabStartFavoriteActivity.setOnClickListener(fabOpenFavoritesActivity());
		binding.fabSortPopular.setOnClickListener(fabPopularListener());
		binding.fabSortRated.setOnClickListener(fabRatedListener());
	}
	
	private View.OnClickListener fabOpenFavoritesActivity() {
		return view -> {
			floatingActionMenu.close(false);
			startActivity(new Intent(this, FavoritesActivity.class));
		};
	}
	
	private View.OnClickListener fabPopularListener() {
		return view -> {
			setActionBarTitle(getPopularTitle());
			viewModel.getPopularMovies();
			fabListenerCommonSteps();
		};
	}
	
	private View.OnClickListener fabRatedListener() {
		return view -> {
			setActionBarTitle(getHighestRatedTitle());
			viewModel.getHighestRatedMovies();
			fabListenerCommonSteps();
		};
	}
	
	private void fabListenerCommonSteps() {
		floatingActionMenu.close(false);
		displayLoading();
	}
	
	
	private void everythingVisibility(int visibility) {
		swipeRefreshLayout.setVisibility(visibility);
		recyclerView.setVisibility(visibility);
		floatingActionMenu.setVisibility(visibility);
	}
	
	private void progressBarVisibility(int visibility) {
		progressBar.setVisibility(visibility);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		refreshMenuItem = menu.findItem(R.id.menu_item_refresh);
		return true;
	}
}