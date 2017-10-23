package ru.rinekri.udacitypopularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import dagger.Module;

@Module
public class DatabaseModule {
  public SQLiteOpenHelper provideDatabaseHelper(Context context) {
    return new DatabaseHelper(context);
  }
}