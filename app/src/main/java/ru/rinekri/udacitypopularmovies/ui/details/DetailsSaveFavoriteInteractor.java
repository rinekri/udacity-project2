package ru.rinekri.udacitypopularmovies.ui.details;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;

import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.MovieInfoEntry.COLUMN_MOVIE_TITLE;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.MovieInfoEntry.TABLE_NAME;

class DetailsSaveFavoriteInteractor implements SyncInteractor<DetailsMvp.PM, DetailsMvp.PM> {
  @NonNull
  private SQLiteDatabase database;

  DetailsSaveFavoriteInteractor(SQLiteOpenHelper dbHelper) {
    this.database = dbHelper.getWritableDatabase();
  }

  @Override
  public DetailsMvp.PM getData(@NonNull DetailsMvp.PM pm) throws Exception {
    ContentValues values = new ContentValues();
    values.put(COLUMN_MOVIE_ID, pm.movieInfo().id());
    values.put(COLUMN_MOVIE_TITLE, pm.movieInfo().title());
    if (pm.isInFavorite()) {
      database.delete(TABLE_NAME, COLUMN_MOVIE_ID + " = " + pm.movieInfo().id(), null);
    } else {
      database.replaceOrThrow(TABLE_NAME, null, values);
    }
    return pm.withInFavorite(!pm.isInFavorite());
  }
}