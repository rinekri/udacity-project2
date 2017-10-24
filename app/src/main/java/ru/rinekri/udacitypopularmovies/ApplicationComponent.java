package ru.rinekri.udacitypopularmovies;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import dagger.Component;
import ru.rinekri.udacitypopularmovies.annotations.ApplicationScope;
import ru.rinekri.udacitypopularmovies.database.DatabaseModule;
import ru.rinekri.udacitypopularmovies.network.NetworkModule;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class, DatabaseModule.class})
public interface ApplicationComponent {
  Context context();
  MainServiceApi mainServiceApi();
  SQLiteOpenHelper databaseHelper();
  ContentResolver contentResolver();
}