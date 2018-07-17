package com.example.android.popularmovies.model.model_movies.remote;

import com.example.android.popularmovies.model.datamodel.MovieDetails;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface MovieDbService {
	@GET(UrlManager.SORT_RELATIVE_URL)
	Single<MovieDbResponse> sortedMovies(@Path(UrlManager.SORT_BY) String sortBy);
	
	@GET(UrlManager.SINGLE_RELATIVE_URL)
	Single<MovieDetails> singleMovie(@Path(UrlManager.SINGLE_MOVIE_ID) int movieId);
}
