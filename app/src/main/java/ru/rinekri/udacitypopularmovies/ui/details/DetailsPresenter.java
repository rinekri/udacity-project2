package ru.rinekri.udacitypopularmovies.ui.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;

import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpPresenter;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;
import ru.rinekri.udacitypopularmovies.ui.base.models.ErrorConfig;

@SuppressWarnings("ConstantConditions")
@InjectViewState
public class DetailsPresenter extends BaseMvpPresenter<DetailsMvp.PM, DetailsMvp.View> {
  @Nullable
  private DetailsMvp.Router router;

  @NonNull
  private MovieShortInfo movieShortInfo;
  @NonNull
  private SyncInteractor<MovieShortInfo, DetailsMvp.PM> inputInteractor;
  @NonNull
  private SyncInteractor<DetailsMvp.PM, DetailsMvp.PM> changeFavoriteInteractor;

  @Nullable
  private DetailsMvp.PM pm;

  DetailsPresenter(@NonNull MovieShortInfo movieShortInfo,
                   @NonNull SyncInteractor<DetailsMvp.PM, DetailsMvp.PM> changeFavoriteInteractor,
                   @NonNull SyncInteractor<MovieShortInfo, DetailsMvp.PM> inputInteractor) {
    this.movieShortInfo = movieShortInfo;
    this.changeFavoriteInteractor = changeFavoriteInteractor;
    this.inputInteractor = inputInteractor;
  }

  void setRouter(@Nullable DetailsMvp.Router router) {
    this.router = router;
  }

  @Override
  public void destroyView(DetailsMvp.View view) {
    router = null;
    super.destroyView(view);
  }

  @Override
  protected void onFirstViewAttach() {
    super.onFirstViewAttach();
    elceAsyncRequestLS(() -> inputInteractor.getData(movieShortInfo), pm -> {
      this.pm = pm;
      getViewState().showViewContent(pm);
    });
  }

  void onAddToFavoritesClicked() {
    elceAsyncRequest(null,
      () -> changeFavoriteInteractor.getData(pm),
      (pm) -> {
        this.pm = pm;
        getViewState().showViewContent(pm);
      },
      (error) -> getViewState().showError(ErrorConfig.createFrom(error)));
  }

  void onMovieTitleClicked(String fullTitle) {
    router.showMessage(fullTitle);
  }

  void onMovieVideoClicked(MovieVideo movieVideo) {
    router.showTrailer(movieVideo);
  }

  void onMovieOverviewClicked(MovieReview movieReview) {
    router.showReview(movieReview);
  }
}