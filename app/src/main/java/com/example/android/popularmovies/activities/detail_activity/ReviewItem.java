package com.example.android.popularmovies.activities.detail_activity;

import android.support.annotation.NonNull;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ListItemReviewBinding;
import com.example.android.popularmovies.model.datamodel.Review;
import com.xwray.groupie.databinding.BindableItem;

final class ReviewItem extends BindableItem<ListItemReviewBinding> {
	
	private final Review review;
	
	ReviewItem(long id, Review review) {
		super(id);
		this.review = review;
	}
	
	@Override
	public void bind(@NonNull ListItemReviewBinding viewBinding, int position) {
		viewBinding.setReview(review);
	}
	
	@Override
	public int getLayout() {
		return R.layout.list_item_review;
	}
}
