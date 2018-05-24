package com.example.android.popularmovies.model.remote;

import com.example.android.popularmovies.model.datamodel.ResultsHolder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface MovieDbClient {
	@GET(UrlManager.SORT_BY_URL)
	Call<ResultsHolder> getMovies(@Path(UrlManager.SORT_BY_PARAMETER) String sortBy);
}
