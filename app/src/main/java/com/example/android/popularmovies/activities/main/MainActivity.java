package com.example.android.popularmovies.activities.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.detail.DetailActivity;
import com.example.android.popularmovies.databinding.ActivityMainBinding;
import com.example.android.popularmovies.databinding.ListItemCardViewBinding;
import com.example.android.popularmovies.datamodel.Movie;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
	
	private MainViewModel viewModel;
	private ActivityMainBinding binding;
	
	private RecyclerView recyclerView;
	private MovieAdapter movieAdapter;
	private Parcelable layoutManagerSavedState;
	
	private int lastSelectedSort;
	private static final int POPULAR = 100;
	private static final int HIGHEST_RATED = 200;
	private static final int FAVORITES = 300;
	
	private SwipeRefreshLayout swipeRefreshLayout;
	private FloatingActionMenu floatingActionMenu;
	private ProgressBar progressBar;
	private TextView tvError;
	
	private MenuItem refreshMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		init(savedInstanceState);
	}
	
	
	private void init(Bundle savedInstanceState) {
		setUpView();
		displayLoading();
		setUpViewModel();
		observeViewModel();
		if (savedInstanceState == null) {
			lastSelectedSort = POPULAR;
			viewModel.getPopularMovies();
		} else {
			getSavedValues(savedInstanceState);
			resortTitle();
			restoreRecyclerView();
		}
	}
	
	
	private void getSavedValues(Bundle savedInstanceState) {
		layoutManagerSavedState = savedInstanceState.getParcelable(getString(R.string.key_layout_manager_saved_state));
		lastSelectedSort = savedInstanceState.getInt(getString(R.string.key_last_selected_sort));
	}
	
	private void resortTitle() {
		String title;
		switch (lastSelectedSort) {
			case POPULAR:
				title = getPopularTitle();
				break;
			case HIGHEST_RATED:
				title = getHighestRatedTitle();
				break;
			case FAVORITES:
				title = getFavoritesTitle();
				break;
			default:
				throw new IllegalArgumentException("Unknown \"last selected sort\" value");
		}
		setActionBarTitle(title);
	}
	
	/**
	 * In case the ViewModel was destroyed due to
	 * known issue with rotation (https://issuetracker.google.com/issues/72690424#comment10)
	 * or running out of memory
	 */
	private void restoreRecyclerView() {
		if (viewModel.getMovies().getValue() == null) {
			switch (lastSelectedSort) {
				case POPULAR:
					viewModel.getPopularMovies();
					break;
				case HIGHEST_RATED:
					viewModel.getHighestRatedMovies();
					break;
			}
		}
		recyclerView.getLayoutManager().onRestoreInstanceState(layoutManagerSavedState);
	}
	
	
	private void setUpViewModel() {
		MainViewModelFactory factory = new MainViewModelFactory(getApplication());
		viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
	}
	
	private void observeViewModel() {
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
		if (lastSelectedSort != FAVORITES) {
			return;
		}
		if (favoriteMovies == null || favoriteMovies.isEmpty()) {
			displayError(getString(R.string.error_no_favorite_movies));
			return;
		}
		movieAdapter.replaceData(favoriteMovies);
		onChangedCommonSteps();
	}
	
	private void onChangedCommonSteps() {
		hideError();
		hideLoading();
	}
	
	private void observeError() {
		viewModel.getErrorMessage().observe(this, this::displayError);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_refresh:
				onRefresh();
				recyclerView.smoothScrollToPosition(0);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	@Override
	public void onRefresh() {
		displayLoading();
		swipeRefreshLayout.setRefreshing(false);
		switch (lastSelectedSort) {
			case POPULAR:
				viewModel.getPopularMovies();
				break;
			case HIGHEST_RATED:
				viewModel.getHighestRatedMovies();
				break;
			case FAVORITES:
				processFavorites(viewModel.getFavoriteMovies().getValue());
				break;
		}
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
		setUpRefreshLayoutListener();
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
		recyclerView.setHasFixedSize(true);
		this.movieAdapter = new MovieAdapter(new ArrayList<>());
		recyclerView.setAdapter(movieAdapter);
	}
	
	private int gridLayoutSpanCount() {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		float displayWidth = displayMetrics.widthPixels / displayMetrics.density;
		int spanCount = (int) (displayWidth / 180);
		return spanCount < 2 ? 2 : spanCount;
	}
	
	
	private void setUpFloatingActionMenu() {
		floatingActionMenu.setIconAnimated(false);
		floatingActionMenu.setClosedOnTouchOutside(true);
		binding.fabSortFavoriteActivity.setOnClickListener(fabFavoritesListener());
		binding.fabSortPopular.setOnClickListener(fabPopularListener());
		binding.fabSortRated.setOnClickListener(fabRatedListener());
	}
	
	private View.OnClickListener fabFavoritesListener() {
		return view -> {
			setActionBarTitle(getFavoritesTitle());
			lastSelectedSort = FAVORITES;
			fabListenerCommonSteps();
			processFavorites(viewModel.getFavoriteMovies().getValue());
		};
	}
	
	private View.OnClickListener fabPopularListener() {
		return view -> {
			setActionBarTitle(getPopularTitle());
			lastSelectedSort = POPULAR;
			viewModel.getPopularMovies();
			fabListenerCommonSteps();
		};
	}
	
	private View.OnClickListener fabRatedListener() {
		return view -> {
			setActionBarTitle(getHighestRatedTitle());
			lastSelectedSort = HIGHEST_RATED;
			viewModel.getHighestRatedMovies();
			fabListenerCommonSteps();
		};
	}
	
	private void fabListenerCommonSteps() {
		floatingActionMenu.close(false);
		displayLoading();
		recyclerView.smoothScrollToPosition(0);
	}
	
	private void setUpRefreshLayoutListener() {
		swipeRefreshLayout.setOnRefreshListener(this);
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
		outState.putParcelable(getString(R.string.key_layout_manager_saved_state), recyclerView.getLayoutManager().onSaveInstanceState());
		outState.putInt(getString(R.string.key_last_selected_sort), lastSelectedSort);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		refreshMenuItem = menu.findItem(R.id.menu_item_refresh);
		return true;
	}
	
	
	private void openDetailActivity(int movieId) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(DetailActivity.class.getSimpleName(), movieId);
		startActivity(intent);
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