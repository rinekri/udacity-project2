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

import ru.rinekri.udacitypopularmovies.database.DatabaseHelper;
import ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract;
import timber.log.Timber;

import static android.R.attr.id;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Provider.PATH_AUTHORITY;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Provider.PATH_MOVIE_INFO;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Provider.parseMovieInfoId;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Entry.COLUMN_MOVIE_ID;
import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Entry.TABLE_NAME;

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
    dbHelper = new DatabaseHelper(getContext());
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
        cursor = queryMovieInfoAll(projection, selection, selectionArgs, sortOrder);
        break;
      case (CODE_MOVIE_INFO_ONE):
        cursor = queryMovieInfoOne(uri, projection, sortOrder);
        break;
      default:
        throw new RuntimeException("query for the wrong type = " + uri);
    }
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  private Cursor queryMovieInfoOne(@NonNull Uri uri,
                                   @Nullable String[] projection,
                                   @Nullable String sortOrder) {
    String request = MovieInfoContract.Entry.COLUMN_MOVIE_ID + "=?";
    return dbHelper.getReadableDatabase()
      .query(MovieInfoContract.Entry.TABLE_NAME, projection, request,
        new String[]{parseMovieInfoId(uri)}, null, null, sortOrder);
  }

  private Cursor queryMovieInfoAll(@Nullable String[] projection,
                                   @Nullable String selection,
                                   @Nullable String[] selectionArgs,
                                   @Nullable String sortOrder) {
    return dbHelper.getReadableDatabase()
      .query(TABLE_NAME, projection, selection,
        selectionArgs, null, null, sortOrder);
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri,
                    @Nullable ContentValues values) {
    switch (URI_MATCHER.match(uri)) {
      case (CODE_MOVIE_INFO_ALL):
        if (insertMovieInfo(uri, values)) {
          getContext().getContentResolver().notifyChange(uri, null);
          return ContentUris.withAppendedId(uri, id);
        }
        return null;
      default:
        throw new RuntimeException("insert for the wrong type = " + uri);
    }
  }

  private boolean insertMovieInfo(@NonNull Uri uri,
                                  @Nullable ContentValues values) {
    long id = dbHelper.getWritableDatabase()
      .replace(TABLE_NAME, null, values);
    if (id == -1) {
      Timber.e("Failed to insert row for %s", uri);
      return false;
    }
    return true;
  }

  @Override
  public int delete(@NonNull Uri uri,
                    @Nullable String selection,
                    @Nullable String[] selectionArgs) {
    int rowsDeleted;
    switch (URI_MATCHER.match(uri)) {
      case (CODE_MOVIE_INFO_ALL):
        rowsDeleted = deleteMovieInfoAll(selection, selectionArgs);
        break;
      case (CODE_MOVIE_INFO_ONE): {
        rowsDeleted = deleteMovieInfoOne(uri);
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

  private int deleteMovieInfoOne(@NonNull Uri uri) {
    int rowsDeleted;
    rowsDeleted = dbHelper.getWritableDatabase()
      .delete(TABLE_NAME, COLUMN_MOVIE_ID + " = " + parseMovieInfoId(uri), null);
    return rowsDeleted;
  }

  private int deleteMovieInfoAll(@Nullable String selection,
                                 @Nullable String[] selectionArgs) {
    int rowsDeleted;
    rowsDeleted = dbHelper.getWritableDatabase()
      .delete(TABLE_NAME, selection, selectionArgs);
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