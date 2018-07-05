package com.example.android.popularmovies.activities.detail_activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ActivityDetailBinding;

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
		setUpToolbar();
		setUpViewModel();
		setUpBinding();
	}
	
	private void setUpToolbar() {
		setSupportActionBar(binding.toolbarDetailActivity);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
	}
	
	private void setUpViewModel() {
		ViewModelFactory factory = new ViewModelFactory(getApplication(), getMovieId());
		viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
	}
	
	private int getMovieId() {
		return getIntent().getIntExtra(getClass().getSimpleName(), 0);
	}
	
	private void setUpBinding() {
		binding.setViewModel(viewModel);
		binding.setLifecycleOwner(this);
	}
}