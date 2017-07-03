package ru.rinekri.udacitypopularmovies.ui.details;

import android.support.annotation.NonNull;

import java.util.List;

import ru.rinekri.udacitypopularmovies.network.models.MovieCharacter;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieTitle;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;

public class DetailsInputInteractor implements SyncInteractor<MovieShortInfo, DetailsMvp.PM> {
  private MainServiceApi serviceApi;

  public DetailsInputInteractor(MainServiceApi serviceApi) {
    this.serviceApi = serviceApi;
  }

  @Override
  public DetailsMvp.PM getData(@NonNull MovieShortInfo movieInfo) throws Exception {
    List<MovieVideo> movieVideos = serviceApi.getMovieVideos(movieInfo.id()).execute().body().results();
    List<MovieReview> movieReviews = serviceApi.getMovieReviews(movieInfo.id()).execute().body().results();
    List<MovieTitle> movieTitles = serviceApi.getMovieTitles(movieInfo.id()).execute().body().results();
    List<MovieCharacter> movieCharacters = serviceApi.getMovieCharacters(movieInfo.id()).execute().body().results();
    List<MovieInfo> recommendedMovies = serviceApi.getRecommendedMovies(movieInfo.id()).execute().body().results();
    List<MovieInfo> similarMovies = serviceApi.getSimilarMovies(movieInfo.id()).execute().body().results();
    return DetailsMvp.PM.create(movieInfo, movieVideos, movieReviews, movieTitles, movieCharacters, recommendedMovies, similarMovies);
  }
}