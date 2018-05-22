package com.example.android.popularmovies.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.contracts.IDetailPresenterContract;
import com.example.android.popularmovies.contracts.IDetailViewContract;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;
import com.example.android.popularmovies.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements IDetailViewContract {
	
	private IDetailPresenterContract presenter;
	private ActivityDetailBinding binding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		
		presenter = new DetailPresenter(this);
		presenter.load(movieFromIntent());
	}
	
	private Movie movieFromIntent() {
		return new Gson().fromJson(
				getIntent().getStringExtra(DetailActivity.class.getSimpleName()),
				Movie.class
		);
	}
	
	
	@Override
	public void setUpView() {
		setUpToolbar();
	}
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbarDetailActivity);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
	}
	
	
	@Override
	public void setTitle(String title) {
		binding.collapsingToolbarLayout.setTitle(title);
	}
	
	@Override
	public void setBackdrop(String imageUrl) {
		Picasso.get().load(imageUrl).into(binding.ivPosterDetailActivity);
	}
	
	@Override
	public void setUserRating(float rating) {
		binding.userRating.setRating(rating);
	}
	
	@Override
	public void setReleaseDate(String date) {
		binding.releaseDate.setText(date);
	}
	
	@Override
	public void setDescription(String description) {
		binding.description.setText(description);
	}
}