package ru.rinekri.udacitypopularmovies.database.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract;
import ru.rinekri.udacitypopularmovies.ui.utils.ContextUtils;
import timber.log.Timber;

import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.MovieInfoContent.PATH_AUTHORITY;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.MovieInfoContent.PATH_MOVIE_INFO;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.MovieInfoEntry.TABLE_NAME;

public class MovieInfoProvider extends ContentProvider {
  private static final int CODE_MOVIE_INFO_ALL = 100;
  private static final int CODE_MOVIE_INFO_ONE = 101;

  private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    URI_MATCHER.addURI(PATH_AUTHORITY, PATH_MOVIE_INFO, CODE_MOVIE_INFO_ALL);
    URI_MATCHER.addURI(PATH_AUTHORITY, PATH_MOVIE_INFO + "/#", CODE_MOVIE_INFO_ONE);
  }

  private SQLiteOpenHelper dbHelper;

  @Override
  public boolean onCreate() {
    dbHelper = ContextUtils.appComponent(getContext()).databaseHelper();
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri,
                      @Nullable String[] projection,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs,
                      @Nullable String sortOrder) {
    Cursor cursor;
    switch (URI_MATCHER.match(uri)) {
      case (CODE_MOVIE_INFO_ALL):
        cursor = dbHelper.getReadableDatabase()
          .query(TABLE_NAME, projection, selection,
            selectionArgs, null, null, sortOrder);
        break;
      case (CODE_MOVIE_INFO_ONE):
        String request = MovieInfoContract.MovieInfoEntry.COLUMN_MOVIE_ID + "=?";
        String movieId = uri.getLastPathSegment();
        cursor = dbHelper.getReadableDatabase()
          .query(MovieInfoContract.MovieInfoEntry.TABLE_NAME, projection, request, new String[]{movieId},
            null, null, sortOrder);
        break;
      default:
        throw new RuntimeException("query for the wrong type = " + uri);
    }
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri,
                    @Nullable ContentValues values) {
    switch (URI_MATCHER.match(uri)) {
      case (CODE_MOVIE_INFO_ALL):
        long id = dbHelper.getWritableDatabase()
          .replace(TABLE_NAME, null, values);

        if (id == -1) {
          Timber.e("Failed to insert row for %s", uri);
          return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
      default:
        throw new RuntimeException("insert for the wrong type = " + uri);
    }
  }

  @Override
  public int delete(@NonNull Uri uri,
                    @Nullable String selection,
                    @Nullable String[] selectionArgs) {
    int rowsDeleted;

    switch (URI_MATCHER.match(uri)) {
      case (CODE_MOVIE_INFO_ALL):
        rowsDeleted = dbHelper.getWritableDatabase()
          .delete(TABLE_NAME, selection, selectionArgs);
        break;
      case (CODE_MOVIE_INFO_ONE): {
        String movieId = uri.getLastPathSegment();
        rowsDeleted = dbHelper.getWritableDatabase()
          .delete(TABLE_NAME, COLUMN_MOVIE_ID + " = " + movieId, null);
        break;
      }
      default:
        throw new RuntimeException("insert for the wrong type = " + uri);
    }

    if (rowsDeleted != 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return rowsDeleted;
  }

  @Override
  public int update(@NonNull Uri uri,
                    @Nullable ContentValues values,
                    @Nullable String selection,
                    @Nullable String[] selectionArgs) {
    throw new RuntimeException("update for the wrong type = " + uri);
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }
}