package ru.rinekri.udacitypopularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import dagger.Module;
import dagger.Provides;
import ru.rinekri.udacitypopularmovies.annotations.ApplicationScope;

@Module
public class DatabaseModule {
  @Provides
  @ApplicationScope
  public SQLiteOpenHelper provideDatabaseHelper(Context context) {
    return new DatabaseHelper(context);
  }
}