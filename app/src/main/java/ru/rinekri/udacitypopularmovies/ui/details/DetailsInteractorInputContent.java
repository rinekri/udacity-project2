package ru.rinekri.udacitypopularmovies.ui.details;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.List;

import ru.rinekri.udacitypopularmovies.network.models.MovieCharacter;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.models.MovieKeyword;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieTitle;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;
import ru.rinekri.udacitypopularmovies.ui.utils.LangUtils;

import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Provider.movieInfoUriWithId;

class DetailsInteractorInputContent implements SyncInteractor<MovieShortInfo, DetailsMvp.PM> {
  @NonNull
  private MainServiceApi serviceApi;
  @NonNull
  private ContentResolver contentResolver;

  DetailsInteractorInputContent(@NonNull MainServiceApi serviceApi,
                                @NonNull ContentResolver contentResolver) {
    this.serviceApi = serviceApi;
    this.contentResolver = contentResolver;
  }

  @Override
  public DetailsMvp.PM getData(@NonNull MovieShortInfo movieInfo) throws Exception {
    List<MovieVideo> movieVideos = serviceApi.getMovieVideos(movieInfo.id()).execute().body().results();
    List<MovieReview> movieReviews = serviceApi.getMovieReviews(movieInfo.id()).execute().body().results();
    List<MovieTitle> movieTitles = serviceApi.getMovieTitles(movieInfo.id()).execute().body().results();
    List<MovieCharacter> movieCharacters = serviceApi.getMovieCharacters(movieInfo.id()).execute().body().results();
    List<MovieInfo> recommendedMovies = serviceApi.getRecommendedMovies(movieInfo.id()).execute().body().results();
    List<MovieInfo> similarMovies = serviceApi.getSimilarMovies(movieInfo.id()).execute().body().results();
    List<MovieKeyword> keywords = serviceApi.getMovieKeywords(movieInfo.id()).execute().body().results();
    return DetailsMvp.PM.create(movieInfo, movieVideos, movieReviews, movieTitles,
      movieCharacters, recommendedMovies, similarMovies, keywords, isMovieInFavorite(movieInfo.id()));
  }

  private boolean isMovieInFavorite(String movieId) throws Exception {
    Cursor cursor = contentResolver.query(movieInfoUriWithId(movieId), null, null, null, null);
    LangUtils.check(cursor != null);
    try {
      return cursor.moveToFirst();
    } finally {
      cursor.close();
    }
  }
}