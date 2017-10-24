package ru.rinekri.udacitypopularmovies.database.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.rinekri.udacitypopularmovies.ui.utils.ContextUtils;

public class MovieInfoProvider extends ContentProvider {

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
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri,
                    @Nullable ContentValues values) {
    return null;
  }

  @Override
  public int delete(@NonNull Uri uri,
                    @Nullable String selection,
                    @Nullable String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(@NonNull Uri uri,
                    @Nullable ContentValues values,
                    @Nullable String selection,
                    @Nullable String[] selectionArgs) {
    return 0;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }
}