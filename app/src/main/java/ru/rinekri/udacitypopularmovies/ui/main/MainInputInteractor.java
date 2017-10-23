package ru.rinekri.udacitypopularmovies.ui.main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;
import ru.rinekri.udacitypopularmovies.ui.base.models.MovieSortType;

class MainInputInteractor implements SyncInteractor<MovieSortType, MainMvp.PM> {
  @NonNull
  private MainServiceApi mainServiceApi;
  @NonNull
  private SQLiteDatabase database;

  MainInputInteractor(@NonNull MainServiceApi mainServiceApi,
                      @NonNull SQLiteOpenHelper dbHelper) {
    this.mainServiceApi = mainServiceApi;
    database = dbHelper.getReadableDatabase();
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

  //TODO: Transfer to ContentProvider
  @NonNull
  private List<MovieInfo> getFavoriteMovies() throws Exception {
    List<MovieInfo> movies = new ArrayList<>();

    Cursor request = database.query(MovieInfoContract.MovieInfoEntry.TABLE_NAME,
      null, null, null, null, null, null);
    try {
      while (request.moveToNext()) {
        String movieId = request.getString(request.getColumnIndex(MovieInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID));
        movies.add(mainServiceApi.getMovieDetails(movieId).execute().body());
      }
      request.close();
    } finally {
      request.close();
    }
    return movies;
  }
}