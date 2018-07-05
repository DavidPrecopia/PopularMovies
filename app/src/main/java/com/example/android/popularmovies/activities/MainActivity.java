package com.example.android.popularmovies.activities;

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
import com.example.android.popularmovies.activities.contracts_front.IMainPresenterContract;
import com.example.android.popularmovies.activities.contracts_front.IMainViewContract;
import com.example.android.popularmovies.databinding.ActivityMainBinding;
import com.example.android.popularmovies.databinding.ListItemBinding;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
		implements IMainViewContract, SwipeRefreshLayout.OnRefreshListener {
	
	private IMainPresenterContract presenter;
	
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
		
		recyclerView = binding.recyclerView;
		swipeRefreshLayout = binding.swipeRefresh;
		floatingActionMenu = binding.fabBase;
		progressBar = binding.progressBar;
		tvError = binding.tvError;
		
		presenter = new MainPresenter(this, new NetworkUtil(getApplicationContext()));
		presenter.start();
	}
	
	
	@Override
	public void setUpView() {
		setToolbar();
		setUpRecyclerView();
		setUpFloatingActionMenu();
		setUpSwipeRefresh();
	}
	
	private void setToolbar() {
		setSupportActionBar(binding.toolbarMainActivity);
		Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.fab_label_popular);
	}
	
	private void setUpRecyclerView() {
		recyclerView.setLayoutManager(new GridLayoutManager(this, gridLayoutSpanCount(), LinearLayoutManager.VERTICAL, false));
		recyclerView.setHasFixedSize(true);
		this.movieAdapter = new MovieAdapter(new ArrayList<>());
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
		return v -> {
			floatingActionMenu.close(false);
			presenter.getPopularMovies();
		};
	}
	
	private View.OnClickListener fabRatedListener() {
		return v -> {
			floatingActionMenu.close(false);
			presenter.getHighestRatedMovies();
		};
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
	
	
	@Override
	public void setTitle(String title) {
		Objects.requireNonNull(getSupportActionBar()).setTitle(title);
	}
	
	@Override
	public String getPopularTitle() {
		return getString(R.string.fab_label_popular);
	}
	
	@Override
	public String getHighestRatedTitle() {
		return getString(R.string.fab_label_highest_rated);
	}
	
	
	@Override
	public void showLoading() {
		progressBar.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideLoading() {
		progressBar.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void enableRefreshing() {
		swipeRefreshLayout.setVisibility(View.VISIBLE);
		if (menuRefreshItem != null) {
			menuRefreshItem.setVisible(true);
		}
	}
	
	@Override
	public void disableRefreshing() {
		swipeRefreshLayout.setVisibility(View.INVISIBLE);
		if (menuRefreshItem != null) {
			menuRefreshItem.setVisible(false);
		}
	}
	
	
	@Override
	public void showError(String message) {
		tvError.setText(message);
		tvError.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideError() {
		tvError.setVisibility(View.INVISIBLE);
	}
	
	
	@Override
	public void showList() {
		recyclerView.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideList() {
		recyclerView.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void showFab() {
		floatingActionMenu.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideFab() {
		floatingActionMenu.setVisibility(View.INVISIBLE);
	}
	
	
	@Override
	public void replaceData(List<Movie> newMovies) {
		movieAdapter.replaceData(newMovies);
	}
	
	
	@Override
	public void openSpecificMovie(Movie movie) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(DetailActivity.class.getSimpleName(), movie);
		startActivity(intent);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_refresh:
				presenter.onRefresh();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onRefresh() {
		swipeRefreshLayout.setRefreshing(false);
		presenter.onRefresh();
	}
	
	@Override
	protected void onPause() {
		presenter.stop();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		presenter.destroy();
		presenter = null;
		super.onDestroy();
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
			holder.bind(movies.get(holder.getAdapterPosition()));
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
			
			
			private void bind(Movie movie) {
				bindTitle(movie.getTitle());
				bindPoster(movie.getPosterUrl());
				binding.executePendingBindings();
			}
			
			private void bindTitle(String title) {
				binding.tvTitleMain.setText(title);
			}
			
			private void bindPoster(String specificPosterUrl) {
				GlideApp.with(MainActivity.this)
						.load(UrlManager.POSTER_URL + specificPosterUrl)
						.placeholder(R.drawable.black_placeholder)
						.error(R.drawable.black_placeholder)
						.into(binding.ivPosterListItem);
			}
			
			
			@Override
			public void onClick(View v) {
				presenter.onListItemClicked(
						movies.get(getAdapterPosition())
				);
			}
		}
	}
}