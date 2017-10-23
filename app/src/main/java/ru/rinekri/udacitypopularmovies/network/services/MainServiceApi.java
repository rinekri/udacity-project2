package ru.rinekri.udacitypopularmovies.network.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.models.ResponseGetCharacters;
import ru.rinekri.udacitypopularmovies.network.models.ResponseGetKeywords;
import ru.rinekri.udacitypopularmovies.network.models.ResponseGetMovies;
import ru.rinekri.udacitypopularmovies.network.models.ResponseGetReviews;
import ru.rinekri.udacitypopularmovies.network.models.ResponseGetTitles;
import ru.rinekri.udacitypopularmovies.network.models.ResponseGetVideos;

public interface MainServiceApi {
  @GET("movie/top_rated")
  Call<ResponseGetMovies> getTopRatedMovies();

  @GET("movie/popular")
  Call<ResponseGetMovies> getPopularMovies();

  @GET("movie/{movie_id}")
  Call<MovieInfo> getMovieDetails(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/videos")
  Call<ResponseGetVideos> getMovieVideos(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/reviews")
  Call<ResponseGetReviews> getMovieReviews(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/alternative_titles")
  Call<ResponseGetTitles> getMovieTitles(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/credits")
  Call<ResponseGetCharacters> getMovieCharacters(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/keywords")
  Call<ResponseGetKeywords> getMovieKeywords(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/recommendations")
  Call<ResponseGetMovies> getRecommendedMovies(@Path("movie_id") String movieId);

  @GET("movie/{movie_id}/similar")
  Call<ResponseGetMovies> getSimilarMovies(@Path("movie_id") String movieId);
}