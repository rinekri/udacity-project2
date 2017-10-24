package ru.rinekri.udacitypopularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract;

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final String DB_NAME = "movie.db";
  private static final int DB_VERSION = 1;

  public DatabaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(MovieInfoContract.Query.CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(MovieInfoContract.Query.DROP_TABLE);
    onCreate(db);
  }
}