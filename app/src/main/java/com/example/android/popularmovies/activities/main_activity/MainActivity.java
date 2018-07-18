package com.example.android.popularmovies.activities.main_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.detail_activity.DetailActivity;
import com.example.android.popularmovies.databinding.ActivityMainBinding;
import com.example.android.popularmovies.databinding.ListItemCardViewBinding;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
	
	private MainViewModel viewModel;
	private ActivityMainBinding binding;
	
	private RecyclerView recyclerView;
	private MovieAdapter movieAdapter;

	private boolean favoritesLastSelected;
	
	private SwipeRefreshLayout swipeRefreshLayout;
	private FloatingActionMenu floatingActionMenu;
	private ProgressBar progressBar;
	private TextView tvError;
	
	private MenuItem refreshMenuItem;
	
	private Parcelable layoutState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		
		if (savedInstanceState != null) {
			layoutState = savedInstanceState.getParcelable(getString(R.string.recycler_view_saved_state_key));
		}
		
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
		observeFavorites();
		observeError();
	}
	
	private void observeMovies() {
		viewModel.getMovies().observe(this, movies -> {
			movieAdapter.replaceData(movies);
			onChangedCommonSteps();
		});
	}
	
	private void observeFavorites() {
		viewModel.getFavoriteMovies().observe(this, this::processFavorites);
	}
	
	private void processFavorites(final List<Movie> favoriteMovies) {
		if (favoriteMovies == null || favoriteMovies.isEmpty()) {
			displayError(getString(R.string.error_no_favorite_movies));
			return;
		}
		if (favoritesLastSelected) {
			movieAdapter.replaceData(favoriteMovies);
			onChangedCommonSteps();
		}
	}
	
	private void onChangedCommonSteps() {
		hideError();
		hideLoading();
	}
	
	private void observeError() {
		viewModel.getErrorMessage().observe(this, this::displayError);
	}
	
	
	private void openDetailActivity(int movieId) {
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
		return getString(R.string.title_popular);
	}
	
	private String getHighestRatedTitle() {
		return getString(R.string.title_highest_rated);
	}
	
	private String getFavoritesTitle() {
		return getString(R.string.title_favorites);
	}
	
	
	private void displayLoading() {
		hideError();
		enableRefreshing(false);
		listVisibility(View.INVISIBLE);
		famVisibility(View.INVISIBLE);
		progressBarVisibility(View.VISIBLE);
	}
	
	private void hideLoading() {
		progressBarVisibility(View.INVISIBLE);
		listVisibility(View.VISIBLE);
		famVisibility(View.VISIBLE);
		enableRefreshing(true);
	}
	
	
	private void displayError(String message) {
		progressBarVisibility(View.INVISIBLE);
		listVisibility(View.INVISIBLE);
		famVisibility(View.VISIBLE);
		enableRefreshing(true);
		tvError.setText(message);
		tvError.setVisibility(View.VISIBLE);
	}
	
	private void hideError() {
		tvError.setVisibility(View.GONE);
	}
	
	
	private void enableRefreshing(boolean enable) {
		swipeRefreshLayout.setVisibility(
				enable ? View.VISIBLE : View.INVISIBLE
		);
		
		if (refreshMenuItem != null) {
			refreshMenuItem.setVisible(enable);
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
		swipeRefreshLayout = binding.swipeRefresh;
		floatingActionMenu = binding.fabBase;
		progressBar = binding.progressBar;
		tvError = binding.tvError;
	}
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbar);
	}
	
	private void setUpRecyclerView() {
		recyclerView = binding.recyclerView;
		recyclerView.setLayoutManager(new GridLayoutManager(this, gridLayoutSpanCount()));
		if (layoutState != null) {
			recyclerView.getLayoutManager().onRestoreInstanceState(layoutState);
		}
		recyclerView.setHasFixedSize(true);
		this.movieAdapter = new MovieAdapter(new ArrayList<>());
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
		binding.fabStartFavoriteActivity.setOnClickListener(fabFavoritesListener());
		binding.fabSortPopular.setOnClickListener(fabPopularListener());
		binding.fabSortRated.setOnClickListener(fabRatedListener());
	}
	
	private View.OnClickListener fabFavoritesListener() {
		return view -> {
			setActionBarTitle(getFavoritesTitle());
			fabListenerCommonSteps();
			favoritesLastSelected = true;
			processFavorites(viewModel.getFavoriteMovies().getValue());
		};
	}
	
	private View.OnClickListener fabPopularListener() {
		return view -> {
			setActionBarTitle(getPopularTitle());
			favoritesLastSelected = false;
			viewModel.getPopularMovies();
			fabListenerCommonSteps();
		};
	}
	
	private View.OnClickListener fabRatedListener() {
		return view -> {
			setActionBarTitle(getHighestRatedTitle());
			favoritesLastSelected = false;
			viewModel.getHighestRatedMovies();
			fabListenerCommonSteps();
		};
	}
	
	private void fabListenerCommonSteps() {
		floatingActionMenu.close(false);
		displayLoading();
		recyclerView.smoothScrollToPosition(0);
	}
	
	
	private void progressBarVisibility(int visibility) {
		progressBar.setVisibility(visibility);
	}
	
	private void famVisibility(int visibility) {
		floatingActionMenu.setVisibility(visibility);
	}
	
	private void listVisibility(int visibility) {
		recyclerView.setVisibility(visibility);
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(getString(R.string.recycler_view_saved_state_key), recyclerView.getLayoutManager().onSaveInstanceState());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		refreshMenuItem = menu.findItem(R.id.menu_item_refresh);
		return true;
	}
	
	
	public final class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
		
		private final List<Movie> moviesList;
		
		MovieAdapter(List<Movie> movies) {
			this.moviesList = new ArrayList<>(movies);
		}
		
		@NonNull
		@Override
		public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new MovieViewHolder(
					ListItemCardViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
			);
		}
		
		@Override
		public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
			ListItemCardViewBinding binding = holder.binding;
			binding.setMovie(moviesList.get(holder.getAdapterPosition()));
			binding.executePendingBindings();
		}
		
		void replaceData(List<Movie> newMovies) {
			moviesList.clear();
			moviesList.addAll(newMovies);
			notifyDataSetChanged();
		}
		
		@Override
		public int getItemCount() {
			return moviesList.size();
		}
		
		
		final class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			
			private final ListItemCardViewBinding binding;
			
			MovieViewHolder(ListItemCardViewBinding binding) {
				super(binding.getRoot());
				this.binding = binding;
				binding.getRoot().setOnClickListener(this);
			}
			
			@Override
			public void onClick(View v) {
				openDetailActivity(
						moviesList.get(getAdapterPosition()).getMovieId()
				);
			}
		}
	}
}