package ru.rinekri.udacitypopularmovies.ui.details;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;

import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Provider.URI_MOVIE_INFO;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Provider.movieInfoUriWithId;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Entry.COLUMN_MOVIE_ID;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Entry.COLUMN_MOVIE_TITLE;

class DetailsInteractorChangeFavorite implements SyncInteractor<DetailsMvp.PM, DetailsMvp.PM> {
  @NonNull
  private ContentResolver contentResolver;

  DetailsInteractorChangeFavorite(@NonNull ContentResolver contentResolver) {
    this.contentResolver = contentResolver;
  }

  @Override
  public DetailsMvp.PM getData(@NonNull DetailsMvp.PM pm) throws Exception {
    ContentValues values = new ContentValues();
    values.put(COLUMN_MOVIE_ID, pm.movieInfo().id());
    values.put(COLUMN_MOVIE_TITLE, pm.movieInfo().title());
    if (pm.isInFavorite()) {
      contentResolver.delete(movieInfoUriWithId(pm.movieInfo().id()), null, null);
    } else {
      contentResolver.insert(URI_MOVIE_INFO, values);
    }
    return pm.withInFavorite(!pm.isInFavorite());
  }
}