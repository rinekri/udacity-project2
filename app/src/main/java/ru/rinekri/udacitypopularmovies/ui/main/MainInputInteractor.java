package ru.rinekri.udacitypopularmovies.ui.main;

import android.support.annotation.NonNull;

import java.util.List;

import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.network.services.MainServiceApi;
import ru.rinekri.udacitypopularmovies.ui.base.models.MovieSortType;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;

public class MainInputInteractor implements SyncInteractor<MovieSortType, MainMvp.PM> {

  private MainServiceApi mMainServiceApi;

  public MainInputInteractor(MainServiceApi mainServiceApi) {
    mMainServiceApi = mainServiceApi;
  }

  @Override
  public MainMvp.PM getData(@NonNull MovieSortType type) throws Exception {
    List<MovieInfo> movies = null;

    switch (type) {
      case TopRated:
        movies = mMainServiceApi.getTopRatedMovies().execute().body().results();
        break;
      case Popular:
        movies = mMainServiceApi.getPopularMovies().execute().body().results();
        break;
    }
    return MainMvp.PM.create(movies);
  }
}