package ru.rinekri.udacitypopularmovies.ui.main;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;

import java.util.Arrays;
import java.util.List;

import ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpPresenter;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;
import ru.rinekri.udacitypopularmovies.ui.base.models.MovieSortType;
import ru.rinekri.udacitypopularmovies.ui.details.MovieShortInfo;

import static ru.rinekri.udacitypopularmovies.ui.utils.LangUtils.safeInvokeOrThrow;

@InjectViewState
public class MainPresenter extends BaseMvpPresenter<MainMvp.PM, MainMvp.View> {
  private static final List<MovieSortType> SORT_TYPES = Arrays.asList(MovieSortType.values());
  private static final MovieSortType INIT_SORT_TYPE = MovieSortType.Popular;

  @Nullable
  private MainMvp.Router router;
  private SyncInteractor<MovieSortType, MainMvp.PM> interactor;
  private ContentResolver contentResolver;

  private MovieSortType currentSortType = INIT_SORT_TYPE;
  @Nullable
  private ContentObserver contentObserver;

  MainPresenter(@NonNull SyncInteractor<MovieSortType, MainMvp.PM> interactor,
                @NonNull ContentResolver contentResolver) {
    this.interactor = interactor;
    this.contentResolver = contentResolver;
  }

  void setRouter(@NonNull MainMvp.Router router) {
    this.router = router;
  }

  @Override
  protected void onFirstViewAttach() {
    super.onFirstViewAttach();
    showInitContent(currentSortType);
    loadViewContent(currentSortType);
    contentObserver = new ContentObserver(new Handler()) {
      @Override
      public void onChange(boolean selfChange, Uri uri) {
        loadViewContent(currentSortType);
      }
    };
    contentResolver.registerContentObserver(MovieInfoContract.Content.URI_MOVIE_INFO, true, contentObserver);
  }

  @Override
  public void destroyView(MainMvp.View view) {
    this.router = null;
    super.destroyView(view);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (contentObserver != null) {
      contentResolver.unregisterContentObserver(contentObserver);
    }
  }

  void onMoviePosterClicked(MovieInfo movieInfo) {
    safeInvokeOrThrow(router, r -> r.showDetailInfo(MovieShortInfo.from(movieInfo)));
  }

  void onMoviePosterLongClicked(MovieInfo movieInfo) {
    safeInvokeOrThrow(router, r -> r.showMessage(movieInfo.originalTitle()));
  }

  void onMovieSortChanged(MovieSortType sortType) {
    currentSortType = sortType;
    showInitContent(currentSortType);
    loadViewContent(currentSortType);
  }

  private void loadViewContent(@NonNull MovieSortType sortType) {
    abortAsyncRequests();
    elceAsyncRequestL(() -> interactor.getData(sortType));
  }

  private void showInitContent(@NonNull MovieSortType sortType) {
    MainMvp.IM initModel = MainMvp.IM.create(SORT_TYPES, sortType);
    getViewState().showInitContent(initModel);
  }
}