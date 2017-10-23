package ru.rinekri.udacitypopularmovies.database.contracts;

import android.provider.BaseColumns;

public final class MovieInfoContract {
  public static final class MovieInfoEntry implements BaseColumns {
    public static final String TABLE_NAME = "movie_info";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";
  }

  public static final class MovieInfoQuery {
    public static final String CREATE_TABLE =
      "CREATE "
        + MovieInfoEntry.TABLE_NAME + " ("
        + MovieInfoEntry.COLUMN_MOVIE_ID + " TEXT PRIMARY KEY, "
        + MovieInfoEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL"
        + ");";

    public static final String DROP_TABLE =
      "DROP TABLE IF EXISTS " + MovieInfoEntry.TABLE_NAME;
  }
}