package com.example.android.popularmovies.activities.detail_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;
import com.example.android.popularmovies.model.datamodel.Movie;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
	
	private DetailViewModel viewModel;
	ActivityDetailBinding binding;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
		setUpView();
	}
	
	public void setUpView() {
		setUpViewModel();
		setUpBinding();
		setUpToolbar();
	}
	
	private void setUpViewModel() {
		ViewModelFactory factory = new ViewModelFactory(movieFromIntent());
		viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
	}
	
	private Movie movieFromIntent() {
		return getIntent().getParcelableExtra(getClass().getSimpleName());
	}
	
	// Will replace movieFromIntent()
	private int getMovieId() {
		return getIntent().getIntExtra(getClass().getSimpleName(), 0);
	}
	
	// TODO Not sure if this is subscribing
	private void setUpBinding() {
		binding.setMovie(viewModel.getMovie().getValue());
		binding.setLifecycleOwner(this);
	}
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbarDetailActivity);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
	}
}