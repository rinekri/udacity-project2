package ru.rinekri.udacitypopularmovies.database.contracts;

import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import ru.rinekri.udacitypopularmovies.BuildConfig;

public final class MovieInfoContract {
  public static final class Entry implements BaseColumns {
    public static final String TABLE_NAME = "movie_info";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";
  }

  public static final class Query {
    public static final String CREATE_TABLE =
      "CREATE TABLE "
        + Entry.TABLE_NAME + " ("
        + Entry.COLUMN_MOVIE_ID + " TEXT PRIMARY KEY, "
        + Entry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL"
        + ");";

    public static final String DROP_TABLE =
      "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;
  }

  public static final class Provider {
    public static final String PATH_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String PATH_MOVIE_INFO = "movie_info";

    private static final Uri URI_BASE = Uri.parse("content://" + PATH_AUTHORITY);
    public static final Uri URI_MOVIE_INFO = Uri.withAppendedPath(URI_BASE, PATH_MOVIE_INFO);

    public static Uri movieInfoUriWithId(@NonNull String id) {
      return URI_MOVIE_INFO.buildUpon().appendEncodedPath(id).build();
    }

    public static String parseMovieInfoId(@NonNull Uri contentUri) {
      String movieId = contentUri.getLastPathSegment();
      return movieId == null ? "-1" : movieId;
    }
  }
}