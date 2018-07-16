package com.example.android.popularmovies.activities.detail_activity;

import android.support.annotation.NonNull;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.datamodel.Review;
import com.xwray.groupie.databinding.BindableItem;

final class ReviewItem extends BindableItem<Review> {
	
	
	@Override
	public void bind(@NonNull Review viewBinding, int position) {
	
	}
	
	@Override
	public int getLayout() {
		return R.layout.list_item_review;
	}
}
