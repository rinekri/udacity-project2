package ru.rinekri.udacitypopularmovies.ui.details;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;

import ru.rinekri.udacitypopularmovies.R;
import ru.rinekri.udacitypopularmovies.network.models.MovieReview;
import ru.rinekri.udacitypopularmovies.network.models.MovieVideo;
import ru.rinekri.udacitypopularmovies.ui.base.BaseMvpPresenter;
import ru.rinekri.udacitypopularmovies.ui.base.SyncInteractor;

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
  private SyncInteractor<MovieShortInfo, Object> saveFavoriteInteractor;

  @Nullable
  private DetailsMvp.PM pm;

  DetailsPresenter(@NonNull MovieShortInfo movieShortInfo,
                   @NonNull SyncInteractor<MovieShortInfo, Object> saveFavoriteInteractor,
                   @NonNull SyncInteractor<MovieShortInfo, DetailsMvp.PM> inputInteractor) {
    this.movieShortInfo = movieShortInfo;
    this.saveFavoriteInteractor = saveFavoriteInteractor;
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
    elceAsyncRequestL(() -> inputInteractor.getData(movieShortInfo));
  }

  void onAddToFavoritesClicked() {
    elceAsyncRequest(null,
      () -> saveFavoriteInteractor.getData(movieShortInfo),
      (Void) -> router.showMessage(R.string.details_save_favorite_success, movieShortInfo.title()),
      (error) -> router.showMessage(R.string.details_save_favorite_error, movieShortInfo.title()));
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