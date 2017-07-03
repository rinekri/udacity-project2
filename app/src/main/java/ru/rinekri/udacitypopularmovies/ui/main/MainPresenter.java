package ru.rinekri.udacitypopularmovies.ui.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;

import java.util.Arrays;
import java.util.List;

import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpPresenter;
import ru.rinekri.udacitypopularmovies.ui.base.models.MovieSortType;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;
import ru.rinekri.udacitypopularmovies.ui.details.MovieShortInfo;
import ru.rinekri.udacitypopularmovies.ui.utils.LangUtils;

@InjectViewState
public class MainPresenter extends BaseMvpPresenter<MainMvp.PM, MainMvp.View> {
  private static final List<MovieSortType> SORT_TYPES = Arrays.asList(MovieSortType.values());
  private static final MovieSortType INIT_SORT_TYPE = MovieSortType.Popular;

  @Nullable
  private MainMvp.Router router;
  private SyncInteractor<MovieSortType, MainMvp.PM> interactor;

  MainPresenter(SyncInteractor<MovieSortType, MainMvp.PM> interactor) {
    this.interactor = interactor;
  }

  void setRouter(@NonNull MainMvp.Router router) {
    this.router = router;
  }

  @Override
  protected void onFirstViewAttach() {
    super.onFirstViewAttach();
    showInitContent(INIT_SORT_TYPE);
    loadViewContent(INIT_SORT_TYPE);
  }

  @Override
  public void destroyView(MainMvp.View view) {
    this.router = null;
    super.destroyView(view);
  }

  void onMoviePosterClicked(MovieInfo movieInfo) {
    LangUtils.safeInvokeOrThrow(router, r -> r.showDetailInfo(MovieShortInfo.from(movieInfo)));
  }

  void onMoviePosterLongClicked(MovieInfo movieInfo) {
    LangUtils.safeInvokeOrThrow(router, r -> r.showMessage(movieInfo.originalTitle()));
  }

  void onMovieSortChanged(MovieSortType sortType) {
    showInitContent(sortType);
    loadViewContent(sortType);
  }

  private void loadViewContent(MovieSortType sortType) {
    abortAsyncRequests();
    elceAsyncRequestL(() -> interactor.getData(sortType));
  }

  private void showInitContent(MovieSortType sortType) {
    getViewState().showInitContent(MainMvp.IM.create(SORT_TYPES, sortType));
  }
}