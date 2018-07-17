package com.example.android.popularmovies.activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies.databinding.ListItemCardViewBinding;
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.ArrayList;
import java.util.List;

public final class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
	
	private final List<Movie> moviesList;
	private final ViewHolderClickListener viewHolderClickListener;
	
	public MovieAdapter(List<Movie> movies, ViewHolderClickListener viewHolderClickListener) {
		this.moviesList = new ArrayList<>(movies);
		this.viewHolderClickListener = viewHolderClickListener;
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
	
	public void replaceData(List<Movie> newMovies) {
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
			viewHolderClickListener.onViewHolderClick(
					moviesList.get(getAdapterPosition()).getMovieId()
			);
		}
	}
	
	
	public interface ViewHolderClickListener {
		void onViewHolderClick(int movieId);
	}
}
