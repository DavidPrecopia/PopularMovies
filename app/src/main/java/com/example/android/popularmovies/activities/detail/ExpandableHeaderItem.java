package com.example.android.popularmovies.activities.detail;

import android.support.annotation.NonNull;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.databinding.ExpandableHeaderBinding;
import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.ExpandableItem;
import com.xwray.groupie.databinding.BindableItem;

final class ExpandableHeaderItem extends BindableItem<ExpandableHeaderBinding> implements ExpandableItem {
	
	private ExpandableGroup expandableGroup;
	private final String title;
	
	ExpandableHeaderItem(String title) {
		this.title = title;
	}
	
	
	@Override
	public void setExpandableGroup(@NonNull ExpandableGroup onToggleListener) {
		this.expandableGroup = onToggleListener;
	}
	
	@Override
	public void bind(@NonNull ExpandableHeaderBinding viewBinding, int position) {
		viewBinding.tvTitle.setText(title);
		bindImage(viewBinding);
		viewBinding.expandableHeaderRoot.setOnClickListener(view -> {
			expandableGroup.onToggleExpanded();
			bindImage(viewBinding);
		});
	}
	
	private void bindImage(ExpandableHeaderBinding viewBinding) {
		viewBinding.imageView.setImageResource(
				expandableGroup.isExpanded() ?
						R.drawable.ic_arrow_up_24px :
						R.drawable.ic_arrow_down_24px
		);
	}
	
	@Override
	public int getLayout() {
		return R.layout.expandable_header;
	}
}
