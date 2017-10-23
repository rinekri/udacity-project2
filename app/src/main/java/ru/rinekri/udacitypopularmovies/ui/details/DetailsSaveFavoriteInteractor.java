package ru.rinekri.udacitypopularmovies.ui.details;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;

class DetailsSaveFavoriteInteractor implements SyncInteractor<MovieShortInfo, Object> {
  @NonNull
  private SQLiteDatabase database;

  DetailsSaveFavoriteInteractor(SQLiteOpenHelper dbHelper) {
    this.database = dbHelper.getWritableDatabase();
  }

  @Override
  public Object getData(@NonNull MovieShortInfo movieInfo) throws Exception {
    ContentValues values = new ContentValues();
    values.put(MovieInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID, movieInfo.id());
    values.put(MovieInfoContract.MovieInfoEntry.COLUMN_MOVIE_TITLE, movieInfo.title());
    database.replaceOrThrow(MovieInfoContract.MovieInfoEntry.TABLE_NAME, null, values);
    return new Object();
  }
}