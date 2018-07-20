package com.example.android.popularmovies.activities.detail;

import android.support.annotation.NonNull;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ListItemReviewBinding;
import com.example.android.popularmovies.datamodel.Review;
import com.xwray.groupie.databinding.BindableItem;

final class ReviewItem extends BindableItem<ListItemReviewBinding> {
	
	private final Review review;
	
	ReviewItem(Review review) {
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
