package com.example.android.popularmovies.model.remote;

import com.example.android.popularmovies.model.datamodel.MovieDbResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MovieDbService {
	@GET(UrlManager.SORT_RELATIVE_URL)
	Single<MovieDbResponse> sortedMovies(@Query(UrlManager.SORT_BY_QUERY) String sortBy);
}
