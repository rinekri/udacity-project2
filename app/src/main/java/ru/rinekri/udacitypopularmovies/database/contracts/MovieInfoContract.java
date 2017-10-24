package ru.rinekri.udacitypopularmovies.database.contracts;

import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import ru.rinekri.udacitypopularmovies.BuildConfig;

public final class MovieInfoContract {
  public static final class MovieInfoEntry implements BaseColumns {
    public static final String TABLE_NAME = "movie_info";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";
  }

  public static final class MovieInfoQuery {
    public static final String CREATE_TABLE =
      "CREATE TABLE "
        + MovieInfoEntry.TABLE_NAME + " ("
        + MovieInfoEntry.COLUMN_MOVIE_ID + " TEXT PRIMARY KEY, "
        + MovieInfoEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL"
        + ");";

    public static final String DROP_TABLE =
      "DROP TABLE IF EXISTS " + MovieInfoEntry.TABLE_NAME;
  }

  public static final class MovieInfoContent {
    public static final String PATH_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String PATH_MOVIE_INFO = "movie_info";

    private static final Uri URI_BASE = Uri.parse("content://" + PATH_AUTHORITY);
    public static final Uri URI_MOVIE_INFO = Uri.withAppendedPath(URI_BASE, PATH_MOVIE_INFO);

    public static Uri withId(@NonNull Uri contentUri,
                             @NonNull String id) {
      return contentUri.buildUpon().appendEncodedPath(id).build();
    }

    public static String parseId(@NonNull Uri contentUri) {
      String movieId = contentUri.getLastPathSegment();
      return movieId == null ? "-1" : movieId;
    }
  }
}