package com.example.android.popularmovies.activities.detail_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
	
	private static final String LOG_TAG = DetailActivity.class.getSimpleName();
	
	private DetailViewModel viewModel;
	private ActivityDetailBinding binding;
	
	private ProgressBar progressBar;
	private TextView tvError;
	private AppBarLayout title;
	private NestedScrollView details;
	
	private MenuItem favoriteMenuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		init();
	}
	
	private void init() {
		setViewReferences();
		binding.tvOpenTrailer.setOnClickListener(view -> playTrailer());
		displayLoading();
		setUpToolbar();
		setUpViewModel();
	}
	
	private void setViewReferences() {
		progressBar = binding.progressBar;
		tvError = binding.tvError;
		title = binding.appBarLayout;
		details = binding.details;
	}
	
	private void setUpViewModel() {
		DetailViewModelFactory factory = new DetailViewModelFactory(getApplication(), getMovieId());
		viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
		observeMovieDetails();
		observeIsFavorite();
		observeErrorMessage();
	}
	
	private int getMovieId() {
		return getIntent().getIntExtra(getClass().getSimpleName(), 0);
	}
	
	private void observeMovieDetails() {
		viewModel.getMovieDetails().observe(this, movieDetails -> {
			binding.setMovie(movieDetails);
			hideError();
			stopLoading();
		});
	}
	
	private void observeIsFavorite() {
		viewModel.getIsFavorite().observe(this, this::setFavoriteIcon);
	}
	
	
	private void observeErrorMessage() {
		viewModel.getErrorMessage().observe(this, this::displayError);
	}
	
	
	private void playTrailer() {
		Intent intent = new Intent(Intent.ACTION_VIEW, getTrailerUri());
		if (intent.resolveActivity(getPackageManager()) == null) {
			Toast.makeText(this, R.string.error_cannot_open_trailer, Toast.LENGTH_SHORT).show();
		}
		startActivity(intent);
	}
	
	private Uri getTrailerUri() {
		return Uri.parse(UrlManager.YOUTUBE_BASE_URL
				+ Objects.requireNonNull(viewModel.getMovieDetails().getValue()).getYouTubeTrailerId());
	}
	
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbarDetailActivity);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
	}
	
	
	private void displayLoading() {
		progressBar.setVisibility(View.VISIBLE);
		title.setVisibility(View.INVISIBLE);
		details.setVisibility(View.INVISIBLE);
	}
	
	private void stopLoading() {
		progressBar.setVisibility(View.INVISIBLE);
		title.setVisibility(View.VISIBLE);
		details.setVisibility(View.VISIBLE);
	}
	
	
	private void displayError(String errorMessage) {
		tvError.setText(errorMessage);
		tvError.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
		title.setVisibility(View.INVISIBLE);
		details.setVisibility(View.INVISIBLE);
	}
	
	private void hideError() {
		tvError.setVisibility(View.GONE);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_detail, menu);
		favoriteMenuItem = menu.findItem(R.id.menu_item_favorite);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
			case R.id.menu_item_favorite:
				if (Objects.requireNonNull(viewModel.getIsFavorite().getValue())) {
					deleteFromFavorites();
				} else {
					addToFavorites();
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void addToFavorites() {
		viewModel.addToFavorites();
		toastMessage(getString(R.string.toast_msg_favorite_added));
	}
	
	private void deleteFromFavorites() {
		viewModel.deleteFromFavorites();
		toastMessage(getString(R.string.toast_msg_favorite_removed));
	}
	
	private void toastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	private void setFavoriteIcon(boolean isFavorite) {
		if (favoriteMenuItem == null) {
			return;
		}
		
		if (isFavorite) {
			favoriteMenuItem.setIcon(R.drawable.ic_star_filled_24px);
		} else {
			favoriteMenuItem.setIcon(R.drawable.ic_star_empty_24px);
		}
	}
}