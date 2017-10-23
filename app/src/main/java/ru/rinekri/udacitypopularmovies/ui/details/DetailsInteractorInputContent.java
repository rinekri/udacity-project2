package ru.rinekri.udacitypopularmovies.ui.details;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.List;

import ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract;
import ru.rinekri.udacitypopularmovies.network.models.MovieCharacter;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.models.MovieKeyword;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieTitle;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;

class DetailsInteractorInputContent implements SyncInteractor<MovieShortInfo, DetailsMvp.PM> {
  @NonNull
  private MainServiceApi serviceApi;
  @NonNull
  private SQLiteDatabase datataBase;

  DetailsInteractorInputContent(@NonNull MainServiceApi serviceApi,
                                @NonNull SQLiteOpenHelper dbHelper) {
    this.serviceApi = serviceApi;
    datataBase = dbHelper.getReadableDatabase();
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

  //  TODO: Move logic to ContentProvider
  private boolean isMovieInFavorite(String movieId) throws Exception {
    Cursor cursor = datataBase.rawQuery("SELECT * FROM " + MovieInfoContract.MovieInfoEntry.TABLE_NAME + " WHERE " +
      MovieInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID + "=?", new String[]{movieId});
    try {
      return cursor.moveToFirst();
    } finally {
      cursor.close();
    }
  }
}