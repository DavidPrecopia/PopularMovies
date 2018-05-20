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
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ActivityMainBinding;
import com.example.android.popularmovies.databinding.ListItemBinding;
import com.example.android.popularmovies.model.Movie;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
	
	private ActivityMainBinding binding;
	
	private SwipeRefreshLayout swipeRefreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		
		setSupportActionBar(binding.toolbarMainActivity);
		Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.fab_label_popular);
		
		
		swipeRefreshLayout = binding.swipeRefresh;
		swipeRefreshLayout.setOnRefreshListener(this);


		final FloatingActionMenu fam = binding.fabBase;
		fam.setIconAnimated(false);
		fam.setClosedOnTouchOutside(true);
		
		
		RecyclerView recyclerView = binding.recyclerView;
		recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
		recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(new MovieAdapter(new ArrayList<Movie>()));
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
				Toast.makeText(this, "Placeholder for Menu Refresh", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onRefresh() {
		Toast.makeText(this, "Placeholder for SwipeRefresh", Toast.LENGTH_SHORT).show();
		swipeRefreshLayout.setRefreshing(false);
	}
	
	
	private class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
		
		private List<Movie> movies;
		
		MovieAdapter(List<Movie> movies) {
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
		
		@Override
		public int getItemCount() {
			return movies.size();
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
				// TODO Bind list_item
				
				this.movie = movie;
				bindImage();
				
				// TODO bindImmediately method
			}
			
			private void bindImage() {
				Picasso.get().load(movie.getPosterUrl()).into(binding.ivPosterListItem);
			}
			
			
			@Override
			public void onClick(View v) {
				// Should pass notice to Presenter
				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				intent.putExtra(DetailActivity.class.getSimpleName(), new Gson().toJson(movies.get(getAdapterPosition())));
				startActivity(intent);
			}
		}
	}
}
