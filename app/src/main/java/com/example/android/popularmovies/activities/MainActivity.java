package com.example.android.popularmovies.activities;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.contracts_font.IMainPresenterContract;
import com.example.android.popularmovies.activities.contracts_font.IMainViewContract;
import com.example.android.popularmovies.databinding.ActivityMainBinding;
import com.example.android.popularmovies.databinding.ListItemBinding;
import com.example.android.popularmovies.model.datamodel.Movie;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
		implements SwipeRefreshLayout.OnRefreshListener, IMainViewContract {
	
	private IMainPresenterContract presenter;
	
	private ActivityMainBinding binding;
	private MovieAdapter movieAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		
		presenter = new MainPresenter(this);
		presenter.load();
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
		RecyclerView recyclerView = binding.recyclerView;
		recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
		recyclerView.setHasFixedSize(true);
		this.movieAdapter = new MovieAdapter(new ArrayList<>());
		recyclerView.setAdapter(movieAdapter);
	}
	
	private void setUpFloatingActionMenu() {
		FloatingActionMenu fam = binding.fabBase;
		fam.setIconAnimated(false);
		fam.setClosedOnTouchOutside(true);
		binding.fabSortRated.setOnClickListener(fabRatedListener());
		binding.fabSortPopular.setOnClickListener(fabPopularListener());
	}
	
	private View.OnClickListener fabRatedListener() {
		return v -> presenter.getHighestRatedMovies();
	}
	
	private View.OnClickListener fabPopularListener() {
		return v -> presenter.getPopularMovies();
	}
	
	private void setUpSwipeRefresh() {
		binding.swipeRefresh.setOnRefreshListener(this);
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
		binding.progressBar.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideLoading() {
		binding.progressBar.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void showError(String message) {
		TextView error = binding.tvError;
		error.setVisibility(View.VISIBLE);
		error.setText(message);
	}
	
	@Override
	public void hideError() {
		binding.tvError.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void showList() {
		binding.recyclerView.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideList() {
		binding.recyclerView.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void showFab() {
		binding.fabBase.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void hideFab() {
		binding.fabBase.setVisibility(View.INVISIBLE);
	}
	
	
	@Override
	public void replaceData(List<Movie> newMovies) {
		movieAdapter.replaceData(newMovies);
	}
	
	
	@Override
	public void openSpecificMovie(Movie movie) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(DetailActivity.class.getSimpleName(), new Gson().toJson(movie));
		startActivity(intent);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_refresh, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh_item:
				presenter.onRefresh();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onRefresh() {
		binding.swipeRefresh.setRefreshing(false);
		presenter.onRefresh();
	}
	
	
	private class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
		
		private List<Movie> movies;
		
		MovieAdapter(List<Movie> movies) {
			this.movies = new ArrayList<>();
			this.movies = movies;
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
			this.movies = newMovies;
			notifyDataSetChanged();
		}
		
		@Override
		public int getItemCount() {
			return movies == null ? 0 : movies.size();
		}
		
		
		class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			
			private ListItemBinding binding;
			private Movie movie;
			
			MovieViewHolder(ListItemBinding binding) {
				super(binding.getRoot());
				binding.getRoot().setOnClickListener(this);
				this.binding = binding;
			}
			
			
			private void bind(Movie movie) {
				this.movie = movie;
				bindImage();
				bindTitle();
				binding.executePendingBindings();
			}
			
			private void bindImage() {
				Picasso.get().load(movie.getPosterUrl()).into(binding.ivPosterListItem);
			}
			
			private void bindTitle() {
				binding.tvTitleMain.setText(movie.getTitle());
			}
			
			
			@Override
			public void onClick(View v) {
				presenter.onListItemClicked(movie);
			}
		}
	}
}
