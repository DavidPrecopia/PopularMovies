package com.example.android.popularmovies.activities.main_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.android.popularmovies.databinding.ListItemBinding;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
	
	private MainViewModel viewModel;
	private ActivityMainBinding binding;
	private MovieAdapter movieAdapter;
	
	private RecyclerView recyclerView;
	private SwipeRefreshLayout swipeRefreshLayout;
	private FloatingActionMenu floatingActionMenu;
	private ProgressBar progressBar;
	private TextView tvError;
	
	private MenuItem menuRefreshItem;
	
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
		viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		observeViewModel();
		observeError();
	}
	
	private void observeViewModel() {
		viewModel.getMovies().observe(this, movies -> {
			movieAdapter.replaceData(movies);
			hideError();
			hideLoading();
		});
	}
	
	private void observeError() {
		viewModel.getErrorMessage().observe(this, this::displayError);
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
	
	
	private void openDetailActivity(int movieId) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(DetailActivity.class.getSimpleName(), movieId);
		startActivity(intent);
	}
	
	
	private void setUpView() {
		getViewReferences();
		setUpToolbar();
		setUpRecyclerView();
		setUpFloatingActionMenu();
		setUpSwipeRefresh();
	}
	
	private void getViewReferences() {
		recyclerView = binding.recyclerView;
		swipeRefreshLayout = binding.swipeRefresh;
		floatingActionMenu = binding.fabBase;
		progressBar = binding.progressBar;
		tvError = binding.tvError;
	}
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbarMainActivity);
	}
	
	private void setUpRecyclerView() {
		recyclerView.setLayoutManager(new GridLayoutManager(this, gridLayoutSpanCount(), LinearLayoutManager.VERTICAL, false));
		recyclerView.setHasFixedSize(true);
		
		movieAdapter = new MovieAdapter(new ArrayList<>());
		recyclerView.setAdapter(movieAdapter);
	}
	
	private int gridLayoutSpanCount() {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			return 4;
		} else {
			return 2;
		}
	}
	
	private void setUpFloatingActionMenu() {
		floatingActionMenu.setIconAnimated(false);
		floatingActionMenu.setClosedOnTouchOutside(true);
		binding.fabSortPopular.setOnClickListener(fabPopularListener());
		binding.fabSortRated.setOnClickListener(fabRatedListener());
	}
	
	private View.OnClickListener fabPopularListener() {
		return view -> {
			viewModel.getPopularMovies();
			setActionBarTitle(getPopularTitle());
			fabListenerCommonSteps();
		};
	}
	
	private View.OnClickListener fabRatedListener() {
		return view -> {
			viewModel.getHighestRatedMovies();
			setActionBarTitle(getHighestRatedTitle());
			fabListenerCommonSteps();
		};
	}
	
	private void fabListenerCommonSteps() {
		floatingActionMenu.close(false);
		displayLoading();
	}
	
	private void setUpSwipeRefresh() {
		swipeRefreshLayout.setOnRefreshListener(this);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menuRefreshItem = menu.findItem(R.menu.menu_refresh);
		getMenuInflater().inflate(R.menu.menu_refresh, menu);
		return true;
	}
	
	
	private void displayLoading() {
		showProgressBar();
		disableRefreshing();
		hideList();
		hideFab();
	}
	
	private void hideLoading() {
		hideProgressBar();
		enableRefreshing();
		showList();
		showFab();
	}
	
	private void displayError(String message) {
		hideProgressBar();
		enableRefreshing();
		hideList();
		hideFab();
		showErrorTextView(message);
	}
	
	private void hideError() {
		tvError.setVisibility(View.INVISIBLE);
	}
	
	private void enableRefreshing() {
		swipeRefreshLayout.setVisibility(View.VISIBLE);
		if (menuRefreshItem != null) {
			menuRefreshItem.setVisible(true);
		}
	}
	
	private void disableRefreshing() {
		swipeRefreshLayout.setVisibility(View.INVISIBLE);
		if (menuRefreshItem != null) {
			menuRefreshItem.setVisible(false);
		}
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
	
	
	private void showProgressBar() {
		progressBar.setVisibility(View.VISIBLE);
	}
	
	private void hideProgressBar() {
		progressBar.setVisibility(View.INVISIBLE);
	}
	
	
	private void showList() {
		recyclerView.setVisibility(View.VISIBLE);
	}
	
	private void hideList() {
		recyclerView.setVisibility(View.INVISIBLE);
	}
	
	private void showFab() {
		floatingActionMenu.setVisibility(View.VISIBLE);
	}
	
	private void hideFab() {
		floatingActionMenu.setVisibility(View.INVISIBLE);
	}
	
	private void showErrorTextView(String message) {
		tvError.setText(message);
		tvError.setVisibility(View.VISIBLE);
	}
	
	
	private class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
		
		private final List<Movie> movies;
		
		MovieAdapter(List<Movie> movies) {
			this.movies = new ArrayList<>(movies);
		}
		
		@NonNull
		@Override
		public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			return new MovieViewHolder(
					ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
			);
		}
		
		@Override
		public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
			ListItemBinding binding = holder.binding;
			binding.setMovie(movies.get(holder.getAdapterPosition()));
			binding.executePendingBindings();
		}
		
		private void replaceData(List<Movie> newMovies) {
			this.movies.clear();
			this.movies.addAll(newMovies);
			notifyDataSetChanged();
			recyclerView.smoothScrollToPosition(0);
		}
		
		@Override
		public int getItemCount() {
			return movies.size();
		}
		
		
		class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			
			private final ListItemBinding binding;
			
			MovieViewHolder(ListItemBinding binding) {
				super(binding.getRoot());
				this.binding = binding;
				binding.getRoot().setOnClickListener(this);
			}
			
			@Override
			public void onClick(View v) {
				openDetailActivity(
						movies.get(getAdapterPosition()).getId()
				);
			}
		}
	}
}