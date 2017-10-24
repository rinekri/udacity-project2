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

import ru.rinekri.udacitypopularmovies.R;
import ru.rinekri.udacitypopularmovies.network.models.MovieInfo;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpPresenter;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;
import ru.rinekri.udacitypopularmovies.ui.base.models.MovieSortType;
import ru.rinekri.udacitypopularmovies.ui.details.MovieShortInfo;

import static ru.rinekri.udacitypopularmovies.database.contracts.MovieInfoContract.Provider.URI_MOVIE_INFO;
import static ru.rinekri.udacitypopularmovies.ui.utils.LangUtils.safeInvokeOrThrow;

@InjectViewState
public class MainPresenter extends BaseMvpPresenter<MainMvp.PM, MainMvp.View> {
  private static final List<MovieSortType> SORT_TYPES = Arrays.asList(MovieSortType.values());
  private static final MovieSortType INIT_SORT_TYPE = MovieSortType.Popular;
  @NonNull
  private SyncInteractor<MovieSortType, MainMvp.PM> interactor;
  @NonNull
  private ContentResolver contentResolver;

  private MovieSortType currentSortType = INIT_SORT_TYPE;
  @Nullable
  private ContentObserver contentObserver;
  @Nullable
  private MainMvp.Router router;

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
    showInitContent();
    loadViewContent();
    contentObserver = new ContentObserver(new Handler()) {
      @Override
      public void onChange(boolean selfChange, Uri uri) {
        if (currentSortType == MovieSortType.Favorites) {
          loadViewContent();
        }
      }
    };
    contentResolver.registerContentObserver(URI_MOVIE_INFO, true, contentObserver);
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
    showInitContent();
    loadViewContent();
  }

  private void loadViewContent() {
    abortAsyncRequests();
    elceAsyncRequestLS(() -> interactor.getData(currentSortType), pm -> {
      if (pm.movies().isEmpty()) {
        int messageRes = currentSortType == MovieSortType.Favorites
          ? R.string.main_empty_no_favorites
          : R.string.main_empty_no_movies;
        getViewState().showEmpty(messageRes);
      } else {
        getViewState().showViewContent(pm);
      }
    });
  }

  private void showInitContent() {
    MainMvp.IM initModel = MainMvp.IM.create(SORT_TYPES, currentSortType);
    getViewState().showInitContent(initModel);
  }
}