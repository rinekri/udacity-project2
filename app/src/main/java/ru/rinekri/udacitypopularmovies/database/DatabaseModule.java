package ru.rinekri.udacitypopularmovies.database;

import android.content.ContentResolver;
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

  @Provides
  @ApplicationScope
  public ContentResolver provideContentResolver(Context context) {
    return context.getContentResolver();
  }
}