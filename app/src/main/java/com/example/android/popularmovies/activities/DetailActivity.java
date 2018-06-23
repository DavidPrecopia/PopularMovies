package com.example.android.popularmovies.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.activities.contracts_front.IDetailPresenterContract;
import com.example.android.popularmovies.activities.contracts_front.IDetailViewContract;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements IDetailViewContract {
	
	private IDetailPresenterContract presenter;
	private ActivityDetailBinding binding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		
		presenter = new DetailPresenter(this);
		presenter.start(movieFromIntent());
	}
	
	private Movie movieFromIntent() {
		return getIntent().getParcelableExtra(DetailActivity.class.getSimpleName());
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
		GlideApp.with(this)
				.load(imageUrl)
				.placeholder(R.drawable.black_placeholder)
				.error(R.drawable.black_placeholder)
				.into(binding.ivPosterDetailActivity);
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
	
	
	@Override
	protected void onDestroy() {
		presenter.destroy();
		presenter = null;
		super.onDestroy();
	}
}