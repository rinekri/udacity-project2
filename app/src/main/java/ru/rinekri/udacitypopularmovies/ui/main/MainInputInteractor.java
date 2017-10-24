package ru.rinekri.udacitypopularmovies.ui.main;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;
import ru.rinekri.udacitypopularmovies.ui.base.models.MovieSortType;
import ru.rinekri.udacitypopularmovies.ui.utils.LangUtils;

class MainInputInteractor implements SyncInteractor<MovieSortType, MainMvp.PM> {
  @NonNull
  private MainServiceApi mainServiceApi;
  @NonNull
  private ContentResolver contentResolver;

  MainInputInteractor(@NonNull MainServiceApi mainServiceApi,
                      @NonNull ContentResolver contentResolver) {
    this.mainServiceApi = mainServiceApi;
    this.contentResolver = contentResolver;
  }

  @Override
  public MainMvp.PM getData(@NonNull MovieSortType type) throws Exception {
    List<MovieInfo> movies = null;

    switch (type) {
      case TopRated:
        movies = mainServiceApi.getTopRatedMovies().execute().body().results();
        break;
      case Popular:
        movies = mainServiceApi.getPopularMovies().execute().body().results();
        break;
      case Favorites: {
        movies = getFavoriteMovies();
        break;
      }
    }
    return MainMvp.PM.create(movies);
  }

  @NonNull
  private List<MovieInfo> getFavoriteMovies() throws Exception {
    List<MovieInfo> movies = new ArrayList<>();
    Cursor cursor = contentResolver.query(MovieInfoContract.Provider.URI_MOVIE_INFO,
      null, null, null, null, null);
    LangUtils.check(cursor != null);
    try {
      while (cursor.moveToNext()) {
        String movieId = cursor.getString(cursor.getColumnIndex(MovieInfoContract.Entry.COLUMN_MOVIE_ID));
        movies.add(mainServiceApi.getMovieDetails(movieId).execute().body());
      }
      cursor.close();
    } finally {
      cursor.close();
    }
    return movies;
  }
}