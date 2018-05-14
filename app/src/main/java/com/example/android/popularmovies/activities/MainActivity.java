package com.example.android.popularmovies.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
	
	SwipeRefreshLayout swipeRefreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Replace with DataBinding
		setContentView(R.layout.activity_main);
		
		
		Toolbar toolbar = findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(R.string.fab_label_popular);
		
		
		swipeRefreshLayout = findViewById(R.id.swipe_refresh);
		swipeRefreshLayout.setOnRefreshListener(this);
		
		
		FloatingActionMenu fam = findViewById(R.id.fab_base);
		fam.setIconAnimated(false);
		fam.setClosedOnTouchOutside(true);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_refresh, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh_item:
				Toast.makeText(this, "Placeholder for Menu Refresh", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onRefresh() {
		Toast.makeText(this, "Placeholder for SwipeRefresh", Toast.LENGTH_SHORT).show();
		swipeRefreshLayout.setRefreshing(false);
	}
}
