package com.example.android.popularmovies.model.remote;

import com.example.android.popularmovies.model.datamodel.ResultsHolder;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MovieDbApi {
	@GET(UrlManager.SORT_BY_URL)
	Single<ResultsHolder> getMovies(@Query(UrlManager.QUERY) String sortBy);
}
