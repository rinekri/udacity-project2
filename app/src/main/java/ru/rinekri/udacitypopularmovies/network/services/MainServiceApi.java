package ru.rinekri.udacitypopularmovies.network.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.rinekri.udacitypopularmovies.network.models.ResponseGetMovies;
import ru.rinekri.udacitypopularmovies.network.models.ResponseGetVideos;

public interface MainServiceApi {
  @GET("movie/top_rated")
  Call<ResponseGetMovies> getTopRatedMovies();

  @GET("movie/popular")
  Call<ResponseGetMovies> getPopularMovies();

  @GET("movie/{movie_id}/videos")
  Call<ResponseGetVideos> getMovieVideos(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/reviews")
  Call<ResponseGetMovies> getMovieReviews(@Path("movie_id") String movieId);
}