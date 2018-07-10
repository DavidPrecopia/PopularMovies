package com.example.android.popularmovies.model.model_movies.remote;

import com.example.android.popularmovies.model.datamodel.MovieDetails;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface MovieDbService {
	@GET(UrlManager.SORT_RELATIVE_URL)
	Single<MovieDbResponse> sortedMovies(@Query(UrlManager.SORT_BY_QUERY) String sortBy);
	
	@GET(UrlManager.SINGLE_RELATIVE_URL)
	Single<MovieDetails> singleMovie(@Path(UrlManager.SINGLE_MOVIE_ID) int movieId);
}
